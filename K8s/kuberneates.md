#Kubernetes

## K8S前世今生
对于云计算的交互标准  
Iaas   Infrastructure as a Service 基础设施即服务  
Pasa   Platform as a Service 平台即服务  
Saas   Software as a Service 软件设施即服务  

容器的集群化的资源管理方案   
MESOS(迟暮，Apache的开源协议，分布式的资源管理框架)  
DockerSWARM(轻量，功能少)  
Kubernetes(功能全面，10年的容器化基础架构，borg系统，采用go语言)  
特点：  
轻量级消耗资源少，开源，弹性伸缩，负载均衡IPVS(4层负载均衡)

## 组件说明
高可用集群的节点个数是大于1的奇数个。  
apiserver:所有服务访问的统一入口    
controllerManager:维护副本的期望数目    
Scheduler:负责接收任务，选择合适的节点进行分配任务   
etcd:键值对数据库，存储K8S集群所有的重要信息（持久化）能够为整个分布式集群存储一些关键数据，协助分布式集群的正常运转。  
kubelet：直接干容器引擎交互实现容器的生命周期管理  
kube-proxy:负责写入规则到iptables\ipvs 实现服务映射访问   
coreDNS：为集群中的SVC创建一个域名IP的对应关系解析    
dashboard:给k8s集群提供一个B/S结构的访问体系 
ingress controller ：官方只能实现四层代理，ingress 可以实现七层代理
federation：提供一个可以跨集群中心多K8S统一管理功能
prometheus：提供K8S集群的监控能力
ELK：提供 K8S 集群日志统一分析介入平台 

## Pod
分为自主式Pod和控制器管理的Pod，自主式的Pod不被控制器管理的Pod,Pod死亡之后不会被拉起来,也不会创建副本满足副本的期望值。    
在同一个Pod里面的容器，端口不能重复，他们共用pause的网络栈，也共享pause的存储卷，可以通过localhost相互见面。   

### Pod控制器类型
1、ReplicationController (RC) & ReplicaSet(RS) & Deployment 这三个其实是一种类型   
RC:用来确保容器应用的副本数量始终保持在用户定义的副本数，如果有容器异常退出，会自动创建新的Pod来替代；如果异常多出来的容器也会被自动回收。    
官方建议使用ReplicaSet来取代RC,ReplicaSet支持集合式的selector,但是实际建议使用Deployment来自动管理ReplicaSet，支持滚动更新。     
deployment去创建RS,RS再去创建Pod,假如需要更新镜像的时候，deployment去创建一个RS,RS再去滚动更新一个个镜像。并且支持回滚。   

2、Horizontal Pod Autoscaling (HPA)适用于Deployment和RS,支持根据CPU利用率去扩容，支持根据内存的使用情况和用户定义的值进行扩容。    
比如规定最大镜像是10，最小是1，当CPU利用率超过80%之后就会进行水平扩容。   

3、StatefulSet 解决了有状态的服务问题，对应Deployment和RS是无状态服务而设计的，适用场景有：  
稳定的持久存储：Pod重新调度之后是能访问到相同的持久化数据，基于PVC来实现的  
稳定的网络标志：Pod重新调度之后其PodName和HostName不变，基于Headless Service 来实现  
有序部署，有扩展性：Pod是有序的，在部署或者扩展的时候要一局定义的顺序依次进行，从0到N-1,在下一个Pod运行之前所有的Pod必须是Running和Ready状态，基于init containers来实现  
有序伸缩，有序删除。  
  
有状态服务：把服务抽离出集群之后再放回来就无法正常工作。(Mysql)  
无状态服务：Docker面对的就是无状态服务，对应的就是没有对应的存储需要去实时的保留。(Apache服务，IOS)  

4、DaemonSet确保全部或者一些Node上运行一个Pod副本。当有Node加入集群的时候，也会为他们新增一个Pod。
当有Node从集群中移除的时候，这些Pod也会被回收。删除DaemonSet将会删除它创建的所有Pod  
运行集群存储daemon 例如在每月Node上运行glusterd  ceph   
在每一个Node上运行日志收集daemon 例如fluentd  logstash   
在每一个Node上运行监控daemon 例如Promentheus Node Exporter  

5、Job 负责批处理任务，仅仅执行一次，保证批处理任务的一个或者多个pod成功结束  
Cron Job管理基于事件的定时任务，在指定时间仅仅运行一次或者周期性的在给定时间点运行  
  
### 服务发现  
客户端想要去访问一组Pod的时候，这些Pod如果没有相干性，是不能通过Service统一代理的。  
这些Pod要具有一定的相关性才可以，比如说是用一个RS、RC、Deployment创建，或者是拥有同一组标签，都可以被Service统一代理。  
Service去收集Pod的时候是去通过标签去选择的，这样些被Service发现的Pod组成一个服务，客户端就可以通过访问Service的ip+端口放完到这些Pod了   

## 网络通讯
Kubernetes的网络模型假设了所有Pod都可以在一个可以直接连通的扁平的网络空间中，这在GCE(Google Compute Engine)里面是现成的网络模型。K8s假定这个网络已经存在。
而在私有云里搭建K8s集群，就不能假定这个网络已经存在了。我们需要自己实现这个网络假设，将不同节点上的Docker容器之间的相互访问打通，然后运行K8s。  
扁平的意思就是：在K8s里面，所有的Pod都可以通过对方的Ip直接到达。  
同一个Pod内的多个容器：直接使用localhost就可以访问到  
各个Pod之间的通讯：Overlay Network  
Pod与Service之间的通讯： 各节点的Iptables规则  

Flannel是CoreOS团队针对K8s设计的一个网络规划服务，简单的来说，他的功能是让集群中不同节点的主机创建的Docker容器都具有全集群唯一的虚拟IP地址。  
而且它还能在这些IP地址之间建立一个覆盖网络（OverLay NetWork），通过这个覆盖网络，将数据包原封不动的传递到目标容器内。  

Flannel对于跨主机的访问把数据包进行了二次封装和解封。  
Flannel和ETCD之间的关联：ETCD存储管理Flannel可分配的IP地址段资源, ETCD监控每个Pod的实际地址，并且在内存中维护Pod节点的路由表

### 网络通讯详述 
K8s里面有三层网络  
Service网络：虚拟网络  
Pod网络：虚拟网络  
节点网络：唯一一个真实的物理网络  
#### 1、同一个Pod内存通讯
同一个Pod共享一个网络命名空间，共享同一个Linux协议栈  

#### 2、Pod1至Pod2
Pod1与pod2不再同一个物理主机上，Pod的地址是与docker0在同一个网段的，但是docker0网段与两个宿主机网卡是两个完全不同的IP网段
并且不同的Node之间的通信只能通过宿主机的物理网卡进行，将Pod的IP和所在Node的IP关联起来，让两个Pod能够互相访问。  

Pod1和Pod2在同一台机器，由docker0网桥直接转发请求至Pod2,不需要进过Flannel  

#### 3、Pod至Service
全部是由iptables维护和转发  

#### 4、Pod到外网
Pod像外网发送请求，查找路由表，转发数据包到宿主机的网卡，宿主网卡完成路由的选择，iptables执行masquerade，把源IP更改为宿主网卡的IP,然后向外网服务器发送请求。  

#### 5、外网访问Pod
要经过Service 


## K8s集群安装 
CentOS7才是k8s最好的运行环境

```
# 集群部署规划  
koolshare 192.168.66.0   
master   192.168.66.10  
node1    192.168.66.20  
node2    192.168.66.21  
hurber   192.168.66.100

 
# 静态IP
vim /etc/sysconfig/network-scripts/ifcfg-ens33   
BOOTPROTO=static  
ONBOOT=yes  
IPADDR=192.168.66.10  
NETMiASK=255.255.255.0  
GATEWAY=192.168.66.1  
DNS1=192.168.66.1  
DNS2=114.114.114.114  


# 修改主机名  
hostnamectl set-hostname k8s-master01  
hostnamectl set-hostname k8s-node01  
hostnamectl set-hostname k8s-node02  

vim /etc/hosts  
192.168.66.10 k8s-master01  
192.168.66.20 k8s-node01  
192.168.66.21 k8s-node02  

# 集群之间拷贝  
scp /etc/hosts root@k8s-node01:/etc/hosts  
scp /etc/hosts root@k8s-node02:/etc/hosts  

# 安装依赖包  
yum install -y conntranck ntpdate ntp ipvsadm ipset jq iptables curl sysstat linseccomp wget vim net-tools git
 
# 设置防火墙为Iptables并设置空规则(两步)
systemctl stop firewalld && systemctl disable firewalld

yum -y install iptables-services && systemctl start iptables %% systemctl enable iptables && iptables -F && service iptables save


# 关闭SELinux  
# 关闭虚拟内存  因为k8s在安装的时候会检查是不是关闭了虚拟内存 如果没有关闭的话就会导致pod被安装到虚拟内存中 很耗费性能 所以在生产的时候是要关闭的
swapoff -a && sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab

setenforce 0 && sed -i 's/^SELINUX=.*/SELINUX=disabled/' /etc/selinux/config


# 调整内核参数 把这段话写到文件中即可
cat > kubernetes.conf <<EOF
net.bridge.bridge-nf-call-iptables=1
net.bridge.bridge-nf-call-ip6tables=1
net.ipv4.ip_forward=1
net.ipv4.tcp_tw_recycle=0
vm.swappiness=0 # 禁止使用 swap 空间，只有当系统 OOM 时才允许使用它
vm.overcommit_memory=1 # 不检查物理内存是否够用
vm.panic_on_oom=0 # 开启 OOM
fs.inotify.max_user_instances=8192
fs.inotify.max_user_watches=1048576
fs.file-max=52706963
fs.nr_open=52706963
net.ipv6.conf.all.disable_ipv6=1
net.netfilter.nf_conntrack_max=2310720
EOF
#把这个文件拷贝到/etc/下面 让在开机的时候能够被调用
cp kubernetes.conf /etc/sysctl.d/kubernetes.conf
#手动刷新 立马生效
sysctl -p /etc/sysctl.d/kubernetes.conf


# 调整系统时区
# 设置系统时区为 中国/上海
timedatectl set-timezone Asia/Shanghai
# 将当前的 UTC 时间写入硬件时钟
timedatectl set-local-rtc 0
# 重启依赖于系统时间的服务
systemctl restart rsyslog
systemctl restart crond

# 关闭系统不需要服务
systemctl stop postfix && systemctl disable postfix


# 设置 rsyslogd 和 systemd journald
# 日志方案选择Journald (3步骤)
mkdir /var/log/journal # 持久化保存日志的目录

mkdir /etc/systemd/journald.conf.d
cat > /etc/systemd/journald.conf.d/99-prophet.conf <<EOF
[Journal]
# 持久化保存到磁盘
Storage=persistent
# 压缩历史日志
Compress=yes
SyncIntervalSec=5m
RateLimitInterval=30s
RateLimitBurst=1000
# 最大占用空间 10G
SystemMaxUse=10G
# 单日志文件最大 200M
SystemMaxFileSize=200M
# 日志保存时间 2 周
MaxRetentionSec=2week
# 不将日志转发到 syslog
ForwardToSyslog=no
EOF

systemctl restart systemd-journald

# 升级系统内核为 4.44
CentOS 7.x 系统自带的 3.10.x 内核存在一些 Bugs，导致运行的 Docker、Kubernetes 不稳定，例如： rpm -Uvh
http://www.elrepo.org/elrepo-release-7.0-3.el7.elrepo.noarch.rpm

rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-3.el7.elrepo.noarch.rpm

# 安装完成后检查 /boot/grub2/grub.cfg 中对应内核 menuentry 中是否包含 initrd16 配置，如果没有，再安装
一次！
yum --enablerepo=elrepo-kernel install -y kernel-lt
# 设置开机从新内核启动
grub2-set-default 'CentOS Linux (4.4.225-1.el7.elrepo.x86_64) 7 (Core)'

#如下就安装成功了
[root@k8s-node02 ~]# uname -r
4.4.225-1.el7.elrepo.x86_64
```


```

# kube-proxy开启ipvs的前置条件
modprobe br_netfilter

cat > /etc/sysconfig/modules/ipvs.modules <<EOF
#!/bin/bash
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh
modprobe -- nf_conntrack_ipv4
EOF

chmod 755 /etc/sysconfig/modules/ipvs.modules && bash /etc/sysconfig/modules/ipvs.modules && lsmod | grep -e ip_vs -e nf_conntrack_ipv4


# 安装 Docker 软件
yum install -y yum-utils device-mapper-persistent-data lvm2

## 配置以下阿里云的镜像仓库
yum-config-manager \
--add-repo \
http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

## 安装docker-ce
yum update -y && yum install -y docker-ce

##创建 /etc/docker 目录
mkdir /etc/docker
## 配置 daemon.
cat > /etc/docker/daemon.json <<EOF
{
"exec-opts": ["native.cgroupdriver=systemd"],
"log-driver": "json-file",
"log-opts": {
"max-size": "100m"
}
}
EOF

mkdir -p /etc/systemd/system/docker.service.d

## 重启docker服务
systemctl daemon-reload && systemctl restart docker && systemctl enable docker


# 安装 Kubeadm （主从配置）
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

yum -y install kubeadm-1.15.1 kubectl-1.15.1 kubelet-1.15.1

systemctl enable kubelet.service


#拷贝压缩进项镜像到系统脚本
#!/bin/bash
ls /root/kubeadm-basic.images > /tmp/image-list.txt
cd /root/kubeadm-basic.images
for i in $( cat /tmp/image-list.txt)
do
        docker load -i $i
done
rm -rf /tmp/image-list.txt

#把镜像和脚本传到其他节点上
scp -r kubeadm-basic.images load-images.sh root@k8s-node01:/root/



#初始化主节点
kubeadm config print init-defaults > kubeadm-config.yaml
localAPIEndpoint:
advertiseAddress: 192.168.66.10
kubernetesVersion: v1.15.1
networking:
podSubnet: "10.244.0.0/16"
serviceSubnet: 10.96.0.0/12
---
apiVersion: kubeproxy.config.k8s.io/v1alpha1
kind: KubeProxyConfiguration
featureGates:
SupportIPVSProxyMode: true
mode: ipvs


kubeadm init --config=kubeadm-config.yaml --experimental-upload-certs | tee kubeadm-init.log

#创建文件
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
#查看k8s集群节点状态 还需要一个扁平化的网络
kubectl get node
NAME           STATUS     ROLES    AGE     VERSION
k8s-master01   NotReady   master   7m48s   v1.15.1

mkdir install-k8s

在/root/install-k8s/plugin/flannel 下：
wget https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

如果连接失败 在/etc/hosts文件添加一条 199.232.68.133 raw.githubusercontent.com

下载下来这个文件之后 
kubectl create -f kube-flannel.yml



# 部署网络
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml



在主节点初始化的日志文件的末尾，可以看到子节点加入k8s集群的命令：
kubeadm join 192.168.66.10:6443 --token abcdef.0123456789abcdef \
    --discovery-token-ca-cert-hash sha256:185337c1835565e3a19d07031c5d625cb270c60cfc7ab68951dbe9c33ca1b7df


```
遇到的问题：  
docker拉取flannel镜像失败 
kubectl delete -f kube-flannel.yml  
vim kube-flannel.yml  
kubectl create -f kube-flannel.yml 

下拉镜像：quay.io/coreos/flannel:.....
如果拉取较慢，可以改为：quay-mirror.qiniu.com/coreos/flannel:.....

```
玩一玩
kubectl get pod -n kube-system
命令行管理工具 指定名称空间是 kube-system  系统组件会被默认安装在kube-system名称空间下 不加参数代表查询default名称空间

kubectl get node

kubectl get pod -n kube-system -o wide  查看更加详细的信息

mv install-k8s/ /usr/local

```

```
安装harbor
vim /etc/docker/daemon.json

其他节点也是一样的
{
"exec-opts": ["native.cgroupdriver=systemd"],
"log-driver": "json-file",
"log-opts": {
"max-size": "100m"
},
"registry-mirrors": ["https://svrzwtvh.mirror.aliyuncs.com"],
"insecure-registries": ["https://hub.xiaosha.com"]
}

重启docker
systemctl restart docker

yum -y install lrzsz


mkdir /data/cert

openssl genrsa -des3 -out server.key 2048
openssl req -new -key server.key -out server.csr
cp server.key server.key.org
openssl rsa -in server.key.org -out server.key
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt

chmod -R 777 /data/cert



#日志文件位置
/var/log/harbor/

#停止docker中的全部容器/移除全部容器/删除全部镜像
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi $(docker images -q)


# 推送本地镜像到hub
# 重新改一个标签 这个标签是hub定义的   在push的时候要进行登录
docker login hb.xiaosha.com 
docker tag mysql:latest hub.xiaosha.com/library/mysql:latest
docker push hub.xiaosha.com/library/mysql:latest


#创建
kubectl run nginx-deployment --image=hub.xiaosha.com/library/myapp:v1 --port=80 --replicas=1

#查看
kubectl get deployment     kubectl get rs     kubectl get pod    kubectl get pod -o wide 



kubectl get deployment
NAME               READY   UP-TO-DATE   AVAILABLE   AGE
nginx-deployment   1/1     1            1           37s

kubectl get rs
NAME                          DESIRED   CURRENT   READY   AGE
nginx-deployment-84d55cd494   1         1         1       72s

kubectl get pod
NAME                                READY   STATUS    RESTARTS   AGE
nginx-deployment-84d55cd494-zhdqb   1/1     Running   0          110s

kubectl get pod -o wide
NAME                                READY   STATUS    RESTARTS   AGE     IP           NODE         NOMINATED NODE   READINESS GATES
nginx-deployment-84d55cd494-zhdqb   1/1     Running   0          2m46s   10.244.1.2   k8s-node01   <none>           <none>

扩容到三个
kubectl scale --replicas=3 deployment/nginx-deployment

kubectl get pod
NAME                                READY   STATUS    RESTARTS   AGE
nginx-deployment-84d55cd494-2ch62   1/1     Running   0          40s
nginx-deployment-84d55cd494-b5jq2   1/1     Running   0          40s
nginx-deployment-84d55cd494-zhdqb   1/1     Running   0          9m10s

kubectl get pod -o wide
NAME                                READY   STATUS    RESTARTS   AGE     IP            NODE         NOMINATED NODE   READINESS GATES
nginx-deployment-84d55cd494-2ch62   1/1     Running   0          61s     10.244.2.10   k8s-node02   <none>           <none>
nginx-deployment-84d55cd494-b5jq2   1/1     Running   0          61s     10.244.1.3    k8s-node01   <none>           <none>
nginx-deployment-84d55cd494-zhdqb   1/1     Running   0          9m31s   10.244.1.2    k8s-node01   <none>           <none>

这时候要访问这个服务该怎么办？  k8s已经帮我们实现了代理
kubectl get svc
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   2d23h

kubectl get deployment
NAME               READY   UP-TO-DATE   AVAILABLE   AGE
nginx-deployment   3/3     3            3           12m


kubectl expose deployment nginx-deployment --port=30000 --target-port=80


kubectl get svc
NAME               TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)     AGE
kubernetes         ClusterIP   10.96.0.1        <none>        443/TCP     2d23h
nginx-deployment   ClusterIP   10.101.226.231   <none>        30000/TCP   42s

curl 10.101.226.231:30000
Hello MyApp | Version: v1 | <a href="hostname.html">Pod Name</a>
就访问到服务了

那如何在外部访问到k8s内部的服务？
上面 SVC的默认type是 ClusterIp  

kubectl edit svc nginx-deployment
把ClusterIp 改成 NodePort

再去get svc
kubectl get svc
NAME               TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)           AGE
kubernetes         ClusterIP   10.96.0.1        <none>        443/TCP           2d23h
nginx-deployment   NodePort    10.101.226.231   <none>        30000:31331/TCP   8m8s
给了一个31331的端口

可以先查看一下
netstat -anpt | grep 31331
tcp6       0      0 :::31331                :::*                    LISTEN      3309/kube-proxy

浏览器只要是 k8s-node01:31331即可访问
http://192.168.66.21:31331/
http://192.168.66.20:31331/

完成！

```














































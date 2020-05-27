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











































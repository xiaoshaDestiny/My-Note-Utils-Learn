# Kubernetes  Service

## Service(SVC)
### 概念
扩容更新之后，Pod里面的容器的IP改变，而上层需要实时监控到这些改变。  

SVC:通过标签那选择的方式来匹配一组Pod，再对外访问服务，每一个SVC就是一个微服务。  

Kubernetes Service 定义的是这样的一种抽象：一个Pod的逻辑分组，一种库访问他们的策略。通常为微服务，这一组Pod能够被Service访问到，通常是通过标签选择Label Selector。  
Service能够提供负载均衡能力，只是提供4层的负载均衡（也就是IP+端口），并且是轮询的策略。没有7层负载均衡的能力（也就是主机名和域名的方案），这是默认的情况，后续可以添加的。  

### Service类型
- ClusterIp：默认类型，自动分配一个仅Cluster内部可以访问的虚拟IP。
- NodePort：在ClusterIP的基础上为Service在每台机器上绑定一个端口，这样急可以通过 nodeIp:nodePort 的形式类访问服务。
- LoadBalance：在NodePort的基础上，借助cloud provider创建一个外部的负载均衡器，并将请求转发到 nodeIp:nodePort 上。
- ExternalName：把集群外部的服务引入到集群内部来，在集群内部直接使用。没有任何类型代理被创建，这只有kubernetes1.7或者更高版本的kube-dns才支持。

apiServer去监听服务和端点，通过kubeproxy去完成的，kubeproxy负责去监控与之匹配的pod,写入带iptables的规则里面去。
客户端去访问service的时候，其实就是访问的iptables。

### VIP和Service代理
在kubernetes集群中，每一个Node运行一个kube-proxy进程，kube-proxy负责为Service实现一种VIP（虚拟IP）的形式。而不是ExternalName的形式。在Kubernetes v1.0版本中，代理完全在userspace  
在 v1.1版本，新增了iptables代理，但是并不是默认的运行模式。v1.2开始默认就是iptables代理。在v1.8版本中添加了 ipvs代理。
v1.0版本，Service是4层（TCP/UDP over IP）概念。在kubernetes v1.1版本中，新增了Ingress API 来表示7层（HTTP）服务。  

用DNS做代理的话，因为很多客户端都会缓存这个代理地址。
 
### 代理模式的分类
(1) userspace代理模式： client访问集群的serviceip(防火墙),再进过每一个node上的kube-proxy去找到对应的pod
(2) iptables代理模式： 直接使用防火墙即可。降低kube-proxy的压力。
(3) ipvs代理模式：把防火墙换成ipvs。

#### ipvs
这种模式，kube-proxy会监视Kubernetes Service对象和EndPoint,调用netlink接口以相应地创建ipvs规则并且定期与Kubernetes Service对象和 Endpoint对象同步ipvs规则，以确保ipvs状态与期望的一直。 
访问服务的时候，流量将被重新定向到其中一个后端Pod。
ipvs的负载均衡算法也有很多的选项：
rr 轮询,lc最小连接数,目标哈希,源哈希,最短期望延迟,不排队调度

```
ipvsadm -Ln

[root@k8s-master01 ~]# ipvsadm -Ln
IP Virtual Server version 1.2.1 (size=4096)
Prot LocalAddress:Port Scheduler Flags
  -> RemoteAddress:Port           Forward Weight ActiveConn InActConn
TCP  10.96.0.1:443 rr
  -> 192.168.66.10:6443           Masq    1      3          0         
TCP  10.96.0.10:53 rr
  -> 10.244.0.16:53               Masq    1      0          0         
  -> 10.244.0.17:53               Masq    1      0          0         
TCP  10.96.0.10:9153 rr
  -> 10.244.0.16:9153             Masq    1      0          0         
  -> 10.244.0.17:9153             Masq    1      0          0         
UDP  10.96.0.10:53 rr
  -> 10.244.0.16:53               Masq    1      0          0         
  -> 10.244.0.17:53               Masq    1      0          0 
  
```

### ClusterIP
在每一个Node节点使用自己的iptables，将发向clusterIP对应端口的数据，转发到hube-proxy中，然后kube-proxy自己内部实现有负载均衡的方法，
并可以直接查询到这个service下对应Pod的地址和端口，进而把数据转发给对应的Pod的地址和端口。  
  
- apiserver用户通过kubectl命令向apiserver发送创建service的命令，apiserver接收到请求之后将数据存储到etcd中。
- kube-proxy，K8s的每一个节点都有一个kube-proxy的进程，这个进程负责感知service、pod的变化，并且将变化的信息写入本地的iptables的规则中。
- iptables使用NAT等技术将 virtualIP的流量转至endpoint中。

```
vim svc-deployment.yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-deploy
  namespace: default
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
      release: stabel
  template:
    metadata:
      labels:
        app: myapp
        release: stabel
        env: test
    spec:
      containers:
      - name: myapp
        image: hub.xiaosha.com/library/myapp:v2
        imagePullPolicy: IfNotPresent
        ports:
        - name: http
          containerPort: 80
          
kubectl apply -f svc-deployment.yaml   


vim svc.yaml

apiVersion: v1
kind: Service
metadata:
  name: myapp
  namespace: default
spec:
  type: ClusterIP
  selector:
    app: myapp
    release: stabel
  ports:
  - name: http
    port: 80
    targetPort: 80
    
    
[root@k8s-master01 ~]# kubectl apply -f svc.yaml
service/myapp created

[root@k8s-master01 ~]# kubectl get svc
NAME         TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1       <none>        443/TCP   5d3h
myapp        ClusterIP   10.108.46.110   <none>        80/TCP    12s

[root@k8s-master01 ~]# curl 10.108.46.110
Hello MyApp | Version: v2 | <a href="hostname.html">Pod Name</a>



[root@k8s-master01 ~]# kubectl get pod -o wide
NAME                           READY   STATUS    RESTARTS   AGE     IP            NODE         NOMINATED NODE   READINESS GATES
myapp-deploy-77b578b6d-8jc6t   1/1     Running   0          7m44s   10.244.1.31   k8s-node01   <none>           <none>
myapp-deploy-77b578b6d-d98c2   1/1     Running   0          7m44s   10.244.1.32   k8s-node01   <none>           <none>
myapp-deploy-77b578b6d-q6zlv   1/1     Running   0          7m44s   10.244.2.41   k8s-node02   <none>           <none>

[root@k8s-master01 ~]# ipvsadm -Ln
IP Virtual Server version 1.2.1 (size=4096)
Prot LocalAddress:Port Scheduler Flags
  -> RemoteAddress:Port           Forward Weight ActiveConn InActConn
TCP  10.96.0.1:443 rr
  -> 192.168.66.10:6443           Masq    1      3          0         
TCP  10.96.0.10:53 rr
  -> 10.244.0.16:53               Masq    1      0          0         
  -> 10.244.0.17:53               Masq    1      0          0         
TCP  10.96.0.10:9153 rr
  -> 10.244.0.16:9153             Masq    1      0          0         
  -> 10.244.0.17:9153             Masq    1      0          0         
TCP  10.108.46.110:80 rr
  -> 10.244.1.31:80               Masq    1      0          0         
  -> 10.244.1.32:80               Masq    1      0          0         
  -> 10.244.2.41:80               Masq    1      0          0         
UDP  10.96.0.10:53 rr
  -> 10.244.0.16:53               Masq    1      0          0         
  -> 10.244.0.17:53               Masq    1      0          0      
     
[root@k8s-master01 ~]# kubectl get svc
NAME         TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1       <none>        443/TCP   5d3h
myapp        ClusterIP   10.108.46.110   <none>        80/TCP    5m22s



```






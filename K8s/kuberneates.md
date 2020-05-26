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

### 控制器
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
 








## 网络通讯


 





































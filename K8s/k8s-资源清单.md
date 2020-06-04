# Kubernetes 资源清单
等于是一个剧本一样的东西

## k8s中的资源
分类 （名称空间级别，集群级别，元数据类型）  
  
名称空间级别：仅仅在此名称空间下生效，比如之前使用kubeadm 安装k8s集群的时候，都会把所有的系统组件放在kube-system这个名称空间下。    
所以这些系统组件使用 kubectl get pod 命令是查看不到的。因为这个命令只会查看默认名称空间下的系统组件，相当于 kubectl get pod -n default    
   
集群级别：比如说role，不管在什么名称空间下去定义，在使用的时候不管在什么名称空间下都能看得到。    
  
元数据型：给我们提供一个指标，比如说HPA(通过当前CPU的利用率去进行扩展)  

### 什么是资源？
K8s中的所有内容都抽象为了资源，资源实例化之后(被调用，被执行)，就叫做对象。  

#### 名称空间级别的资源  
1、工作负载型资源(work load)
Pod: K8s中最小的组成部分，跟Pause共享网络栈、共享存储卷。   
ReplicaSet： 也就是RS，是k8s中的调度器，管理Pod的创建，通过标签的选择去控制副本数目。   
Deployment： 也是控制器，控制RS的创建去创建Pod。  
StatefulSet： 只要是为了实现有状态服务去创建的一个管理器。  
DaemonSet:  在每一个节点都运行一个Pod的组件。  
Job/CronJob：实现批处理任务。  

2、服务发现及负载均衡型资源(ServiceDiscovery LoadBalance)   
Service： SVC   
Ingress： 将服务暴露出去   
 
3、配置与存储型资源    
Volume: 存储卷，给Pod提供的持久化存储的能力    
CSI: 容器存储接口，可以扩展各种各样的第三方存储卷  

4、特殊类型的存储卷   
ConfigMap:当配置中心来使用的资源类型   
Secret:保存敏感数据，加密     
DownwardAPI:把外部环境中的信息输出给容器 

#### 集群级别的资源
namespace、Node、Role、ClusterRole、RoleBinding、ClusterRoleBinding  

#### 元数据类型   
HPA、PodTemplate、LimitRange    



## 资源清单
资源清单到底是什么？  
在K8s中，一般使用yaml格式的文件来创建符合我们期望的pod，这样的yaml文件一般就叫做资源清单  

## 常用字段解释说明
### 必须存在的属性
|参数名|字段类型|说明|
|---|---|---|
|version|String|指定的是K8s API的版本，基本上是v1,可以用kubectl api-version 命令查询。RESTful的编程风格，会携带两个信息，一个是组，一个是版本|
|kind|String|指的是yaml文件定义的资源类型和角色，比如Pod，Service，Deployment，看你创建的是什么|
|metadata|Object|元数据对象，固定值就写metadata|
|metadata.name|String|元数据对象的名字，这里由我们编写，比如命名Pod的名字|
|metadata.namespace|String|元数据对象的命名空间，自己定义|
|Spec|Object|详细定于对象，固定值就写Spec|
|Spec.containers[]|Object|Spec对象的容器列表定义，是个列表|
|Spec.containers[].name|list|定义容器的名字|
|Spec.containers[].image|String|要用到的镜像名称|

### 主要对象 
不写也可以会补充默认值    

|参数名|字段类型|说明|
|---|---|---|
|spec.containers[].name|String|定义容器的名字|
|spec.containers[].image|String|定义要用到的镜像名称|
|spec.containers[].imagePullPolicy|String|定义镜像拉取的策略，Aways(每次都尝试重新拉群镜像),Never(仅使用本地镜像),IfNotPresent(如果本地没有就拉取在线的镜像),默认是Aways|
|spec.containers[].command[]|List|指定容器启动命令，因为是数组可以指定多个，不指定就是要镜像打包时候使用的启动命令|
|spec.containers[].args[]|List|指定容器启动的参数，数组可以指定多个|
|spec.containers[].workingDir|String|指定容器的工作目录|




## 容器的生命周期


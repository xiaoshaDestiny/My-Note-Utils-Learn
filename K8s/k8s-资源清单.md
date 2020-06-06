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
|spec.containers[].volumeMounts[]|List|指定容器内部的存储卷配置|
|spec.containers[].volumeMounts[].name|String|指定可以被容器加载的存储卷的路径|
|spec.containers[].volumeMounts[].mountPath|String|指定可以被容器挂载的存储卷的路径|
|spec.containers[].volumeMounts[].readOnly|String|设置存储卷路径的读写模式，true或者false默认认为读写模式|
|spec.containers[].ports[]|List|指定容器需要用到的端口列表|
|spec.containers[].ports[].name|String|指定端口名称|
|spec.containers[].ports[].containerPort|String|指定容器需要监听的端口号|
|spec.containers[].ports[].hostPort|String|指定容器所在主机需要监听的端口号，默认跟上面containerPort相同。注意设置了hostPort通一台主机无法启动该容器的相同副本（因为主机的端口号不能相同，这样会冲突）|
|spec.containers[].ports[].protocol|String|指定端口协议，支持TCP和UDP,默认值为TCP|
|spec.containers[].env[]|List|指定容器运行前需设置的环境变量列表|
|spec.containers[].env[].name|String|指定环境变量的名称|
|spec.containers[].env[].value|String|指定环境变量的值|
|spec.containers[].resources|Object|指定资源限制和资源请求的值(这里开始就是设置容器的资源上限)|
|spec.containers[].resources.limits|Object|指定设置容器运行时资源的运行上限|
|spec.containers[].resources.limits.cpu|String|指定CPU的限制，单位为core数，将用于 docker run --cpu-shares参数|
|spec.containers[].resources.limits.memory|String|指定MEM内存的限制，但是是Mib Gib|
|spec.containers[].resources.requests|Object|指定容器启动和调度时的限制设置|
|spec.containers[].resources.requests.cpu|String|CPU请求，单位为core，容器启动时初始化可用数量|
|spec.containers[].resources.requests.memory|String|内存请求，单位是Mib,Gib,容器启动的初始化可用数量|


### 额外的参数选项
|参数名|字段类型|说明|
|---|---|---|
|spec.restartPolicy|String|定义Pod的重启策略，可选值为Aways,OnFailure,默认值是Aways。Aways：Pod一旦终止运行，则无论容器是怎么样终止的，kubelet都将重启它。OnFailure:只有Pod以非零退出码终止时，kubelet才会重启该容器，如果容器正常结束(退出码是0)，则kubelet将不会重启它。Never:Pod终止之后，kubelet将退出码报告给Master,不会重启该Pod|
|spec.nodeSelector|Object|定义Node的Label过滤标签，以key:value格式指定|
|spec.imagePullSecrets|Object|定义pull镜像时使用secret名称，以name:secretKey格式指定|
|spec.hostNetwork|Boolean|定义是否使用主机网络模式，默认值是false。设置true表示使用宿主机网络，不使用docker网桥，同时设置了true将无法再同一台宿主机上启动第二个副本|





|metadata|Object|元数据对象，固定值就写metadata|
|metadata.name|String|元数据对象的名字，这里由我们编写，比如命名Pod的名字|
|metadata.namespace|String|元数据对象的命名空间，自己定义|
|Spec|Object|详细定于对象，固定值就写Spec|
|Spec.containers[]|Object|Spec对象的容器列表定义，是个列表|
|Spec.containers[].name|list|定义容器的名字|
|Spec.containers[].image|String|要用到的镜像名称|








## 容器的生命周期


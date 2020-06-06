# Kubernetes 资源清单
等于是一个剧本一样的东西

## 一、k8s中的资源
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



## 二、资源清单
资源清单到底是什么？  
在K8s中，一般使用yaml格式的文件来创建符合我们期望的pod，这样的yaml文件一般就叫做资源清单  

## 三、常用字段解释说明
### 3.1、必须存在的属性
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

### 3.2、主要对象 
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


### 3.3、额外的参数选项
|参数名|字段类型|说明|
|---|---|---|
|spec.restartPolicy|String|定义Pod的重启策略，可选值为Aways,OnFailure,默认值是Aways。Aways：Pod一旦终止运行，则无论容器是怎么样终止的，kubelet都将重启它。OnFailure:只有Pod以非零退出码终止时，kubelet才会重启该容器，如果容器正常结束(退出码是0)，则kubelet将不会重启它。Never:Pod终止之后，kubelet将退出码报告给Master,不会重启该Pod|
|spec.nodeSelector|Object|定义Node的Label过滤标签，以key:value格式指定|
|spec.imagePullSecrets|Object|定义pull镜像时使用secret名称，以name:secretKey格式指定|
|spec.hostNetwork|Boolean|定义是否使用主机网络模式，默认值是false。设置true表示使用宿主机网络，不使用docker网桥，同时设置了true将无法再同一台宿主机上启动第二个副本|

### 自己写pod模板
```
pod.yml
#采用哪一个版本进行资源的申请
apiVersion: v1
kind: Pod
metadata: 
    name: myapp-pod
    namespace: default #不写默认也是default
    labels: 
        app: myapp
        version: v1
spec: 
    containers: 
    - name: app
      image: hub.xiaosha.com/library/myapp
    - name: test
      image: hub.xiaosha.com/library/myapp
     
      
上面这个文件：  
一个Pod里面放了两个容器，一个叫app，一个叫test，会先运行一个pause，
让他们共享网路栈，这两个镜像的端口就会报错，因为端口被占用了。

创建Pod
kubectl apply -f pod.yml

kubectl get pod
NAME                                READY   STATUS             RESTARTS   AGE
myapp-pod                           0/2     ImagePullBackOff   0          15s
nginx-deployment-84d55cd494-5h5jn   1/1     Running            0          51m
nginx-deployment-84d55cd494-wbcsx   1/1     Running            0          51m
nginx-deployment-84d55cd494-x967f   1/1     Running            0          51m


查看myapp-pod这个Pod中各个容器的启动情况
kubectl describe pod myapp-pod


查看myapp-pod这Pod中 容器名称是test的这个容器的启动日志
kubectl log myapp-pod -c test


kubectl create -f pod.yml

```
## 容器的生命周期
探测：比如现在一个pod里面运行了两个容器，但是容器中的进程已经死亡了，但是这个容器没有退出
就会造成这个Pod还在处于一个running的状态，这个时候其实服务已经是不可用的了，但是这个Pod还在是可用的。这样就是存在了问题的。

整个Pod从创建到使用的过程：  
最开始的时候，请求指令被下发到api接口，被调度到kubelet上，会进行一个容器环境的初始化 
首先会创建pause这个基础容器，然后就是初始化容器 initC： 容器运行的前提条件是在本机的某个存储位置有文件存在,initC 就会去把这个文件生成，只英语初始化，不会跟随着整个Pod的生命周期而存在  

接下来才到主容器的运行过程：
在容器运行之前是一个start操作，readiness是一个就绪检测，liveness是生存检测，容器退出之后会有一个stop操作。  

**就绪检测**  
Deployment创建了多个Pod之后，在进程没有初始化的时候是不能通过service代理给K8s集群之外的客户端去访问的。这个时候就需要一个就绪检测。
根据命令，TCP连接，Http协议获取状态判断这个服务是否可用。可用之后再把运行状态改成Running。

**生存检测**  
容器内存的进程假死了，也就是这个进程已经不能继续对外提供服务的时候，就需要生存检测来将Pod的状态改变。检测到状态改变之后才能对这个容器进行重启的操作。

kubectl像kubeapi接口发送指令，kubeapi会调度到kubelet(这个调度过程是由etcd去完成的)，
kubelet会去调度cri去完成容器环境的初始化，初始化的过程中会先启动一个pause的基础容器
这个容器是谷歌做的负责网络和存储卷共享的一个容器，在同一个Pod中，所有的容器都是可以共享的。  
接着进行initC的初始化，initC的初始化完成之后就到了mainC的运行过程。
刚启动的时候有一个start命令,结束的时候有一个stop命令。还伴随着readness和liveness检测。

### initC 初始化容器
Pod能够具有多个容器，应用运行在容器里面，但时它也有可能有一个或者多个先于应用容器启动的Init容器。  

Init容器与普通的容器非常像，出来以下两点：Init容器总是运行成功到完成为止；每个Init容器都必须在下一个Init容器启动之前成功完成。  

如果Pod和Init容器失败，Kubernetes会不断的重启该Pod,直到Init容器成功为止。如果Pod对应的restartPolicy为never,它不会重新启动。    

### init容器的作用
因为Init容器具有与应用容器分离的单独镜像，所以他们的启动相关代码具有如下优势：    
1、它们可以包含并运行实用工具，但是出于安全考虑，是不建议在应用程序容器镜像中包含这些实用工具的。  
2、它们可以包含使用工具和定制化代码来安装，但是不能出现在应用程序镜像中。例如。创建镜像没有必要 FROM 另外一个镜像，只需要在安装过程中使用类似sed、awk、python或者dis这样的工具。    
3、应用程序镜像可以分离出创建和部署的角色，而没有必要联合它们构建一个单独的镜像。  
4、Init容器使用Linux Namespace，所以相对应用程序容器来说具有不同的文件系统视图。因此，它们能够具有访问Secret的权限，而应用程序容器则不能。  
假设有一堆文件，是mainC需要调用的，但是mainC没有权限
5、它们必须在应用程序容器启动之前运行完成，而应用程序容器是并行运行的，所以Init容器能提供一种简单阻塞延迟应用容器的启动方法，知道满足了一组先决条件。    

```
init-pod.yaml
首先是运行了一个Pod,Pod里面的主容器是myapp-container,是一个很小的镜像，这个容器在运行成功之后会输入一句话：
The app isrunning! 然后休眠6分钟。然后是定义了一组初始化镜像InitC，有两个，在循环的检测一些service

apiVersion: v1
kind: Pod
metadata:
    name: myapp-pod
    labels:
        app: myapp
spec:
    containers:
    - name: myapp-container
      image: busybox
      command: ['sh', '-c', 'echo The app is running! && sleep 3600']
    initContainers:
    - name: init-myservice
      image: busybox
      command: ['sh', '-c', 'until nslookup myservice; do echo waiting for myservice; sleep 2; done;']
    - name: init-mydb
      image: busybox
      command: ['sh', '-c', 'until nslookup mydb; do echo waiting for mydb; sleep 2; done;']


删除所有Pod的方式
kubectl get svc
kubectl delete deployment --all
kubectl delete pod --all
kubectl delete svc ...


kubectl create -f init-pod.yml


kubectl get pod
NAME        READY   STATUS     RESTARTS   AGE
myapp-pod   0/1     Init:0/2   0          8s

查看日志
kubectl log myapp-pod init-myservice


** server can't find myservice.default.svc.cluster.local: NXDOMAIN
*** Can't find myservice.svc.cluster.local: No answer
*** Can't find myservice.cluster.local: No answer
*** Can't find myservice.default.svc.cluster.local: No answer
*** Can't find myservice.svc.cluster.local: No answer
*** Can't find myservice.cluster.local: No answer
waiting for myservice
Server:		10.96.0.10
Address:	10.96.0.10:53


这个时候就一直在init状态，因为需要的SVC没有被创建，两个Init容器都没有正常的启动
去创建一下这两个service

vim myservice.yaml

kind: Service
apiVersion: v1
metadata:
    name: myservice
spec:
    ports:
      - protocol: TCP
        port: 80
        targetPort: 9376
        

kubectl get pod
NAME        READY   STATUS     RESTARTS   AGE
myapp-pod   0/1     Init:1/2   0          14m


这个时候就会变成一个已经初始化成功，查看svc
kubectl get svc
NAME         TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP   6d15h
myservice    ClusterIP   10.103.58.11   <none>        80/TCP    5m33s

接着创建服务

vim mydb.yaml

kind: Service
apiVersion: v1
metadata:
    name: mydb
spec:
    ports:
    - protocol: TCP
      port: 80
      targetPort: 9377


kubectl get pod
NAME        READY   STATUS    RESTARTS   AGE
myapp-pod   1/1     Running   0          19m
```

### InitC的特殊说明
1、在Pod启动过程中，Init容器会按照顺序在网络和数据卷初始化之后启动(网络和数据卷的初始化是在Pause中去完成的)。每个容器必须在下一个容器启动之前成功推出。   
2、如果由于运行时或失败退出，将导致容器启动失败，它会根据Pod的restartPolocy指定的策略进行重试。  
然而，如果Pod的restartPolicy设置成Always，Init容器失败的时候会使用RestartPolicy策略。   
3、在所有的Init容器没有成功之前，Pod将不会变成Ready状态。Init容器的端口将不会在Service中进行聚集。正在初始化中的Pod处于pending状态，但应该会将Initializing状态设置为true。   
4、如果Pod重启，所有的Init容器会重新执行。  
5、对Init容器spec的修改被限制在容器image字段，修改其他字段都不会生效。更改Init容器的image字段，等价于重启该Pod。  












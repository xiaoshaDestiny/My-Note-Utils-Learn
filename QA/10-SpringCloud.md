# spring-cloud-2020
微服务技术栈2020年版本 - 知识点整理  

## 初级部分  
1、pom依赖：dependencyManagement 一般出现在父工程中，子工程聚合依赖，子工程就可以不指定版本号。只声明依赖，不实现引入。  
2、开发热部署：devtools 热启动 ctrl+alt+shift+/ 生产环境一般不开启。  
3、服务间调用restTemplate： 是Spring提供的用于访问Rest服务的客户端模板工具集。  
4、maven基本使用：公共entity 可以使用maven clean install之后 提供给其他模块使用。  

## Eureka 作为注册中心  
1、Eureka作为服务注册功能的服务区，它是服务的注册中心。可以实现服务调用、负载均衡、服务注册与发现系统中其他的微服务。
使用Eureka的客户端连接到Eureka Server并且为之心跳连接。
就可以通过Eureka Server来监控系统中各个微服务是否正常运行。服务把服务地址、通讯地址以别名的形式方式注册到注册中心上。  
   
2、@EnableEurekaServer 标注在注册中心server上、@EnableEurekaClient 标注在服务提供者、消费者上。   
  
3、微服务RPC远程服务调用的核心是高可用，搭建Eureka注册中心集群，实现负载均衡+故障容错。集群之间互相注册，相互守望。  
  
4、@LoadBalanced 当服务提供者变成集群的时候，需要开启负载均衡功能，否则服务消费者在调用的时候会报错。
也就是在RestTemplate配置类的地方使用该注解开启。  
  
5、@EnableDiscoveryClient 标注在服务提供者的启动类上，对于注册到eureka里面的服务，可以通过服务发现来获得该服务的信息。 
  
6、Eureka自我保护机制：某一个时刻一个微服务不可用了(没有收到心跳)，Eureka不会立即清理，依旧会对改微服务的信息进行保留。
属于CAP理论的AP分支。在自我保护模式中，EurekaServer会保护服务注册表中的信息，不再注销任何微服务实例。    

## Zookeeper 作为注册中心
1、Eureka停更之后，原来使用Dubbo的就选择使用Zookeeper作为服务的注册中心。
微服务注册进入Zookeeper后，服务端能够正常访问。但是当服务挂掉之后，zookeeper在一段时间之后收不到心跳回复，就会删除服务。
再次连上之后，内部节点的id会改变。

2、集群环境的Zookeeper

## consul 做为服务注册中心
1、一套开源的分布式服务发现和配置管理系统，用Go语言开发的。
提供了微服务系统中的服务治理、配置中心、控制总线等功能。这些功能中每一个都可以根据需要单独使用。
也可以一起使用以构建全方位的服务网络，总之Consul提供了一种完整的服务网络解决方案。
  
2、服务发现：提供HTTP和DNS两种发现方式、健康监测：支持多种方式。HTTP,TCP，Docker，Shell脚本定制化。KV存储。多数据中心。可视化web界面。
  
3、consul.exe agent -dev 本地模式启动consul

三个注册中心的异同点  
Eureka  Java语言  AP 可以配置支持健康检查 对外暴露HTTP接口  
Consul  Go语言    CP 支持服务健康检查     对外暴露HTTP/DNS接口  
Zookeeper Java语言 CP  支持服务健康检查  客户端         
C强一致性、A可用性、P分区容错。


## Ribbon 负载均衡
目前进入了维护模式。netflix-eureka天生就带有ribbon。  

1、负载均衡：将请求平均分配到各个服务器上，达到系统的HA。  
Nginx是服务器负载均衡，客户端请求会交给nginx nginx帮助实现请求的转发。  
Ribbon是本地负载均衡，在调用微服务接口的时候，会在注册中心上获取户厕信息服务列表之后缓存到JVM本地，在本地实现RPC远程服务调用。
  
2、集中式LB:在服务消费方和提供方之间是有的独立LB设置。  
进程式LB:消费方从服务注册中心知道有哪些服务地址可用，自己选择一个合适的服务器。
  
IRule接口下面的子实现类：轮询、随机、重试、带权重、跳过被熔断等。
  
3、如何替换：
自定义负载均衡配置不能放在@ComponentScan扫描的包下面。新建配置类，返回IRule下面的一个自类的实例。    
在访问客户端加上注解，指定要调用的服务及负载均衡配置类即可。@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MySelfRule.class)  
  
4、轮询负载均衡算法的实现逻辑
rest接口第几次请求 % 服务器集群总数量 = 实际调用服务器的下标位置，每次服务重启之后rest接口计数从1开始。

## OpenFeign 服务接口调用
1、Feign是一个声明式的web服务客户端，用在消费端。让编写Web服务客户点变得非常容易，只需要创建一个接口并且在接口上议案家注解即可。
使用Ribbon+RestTemplate，对http进行了请求的封装处理，形成一套模板化的调用方案。但是在实际开发中，对服务的调用可能不止一处。
往往一个接口会被多处调用，多以通常会针对每个微服务自行封装一些客户端来包装这些依赖服务的调用。
所以。Feign只需要创建一个接口并且使用注解的港式来配置它。Feign是集成了Ribbon的。
  
2、OpenFeign是SpringCloud在Feign的基础上支持了SpringMVC的注解。
  
3、Feign客户端默认只等待一秒钟，但是服务端处理请求超过了一秒钟，导致Feign客户点不想等待，直接报错。在yml文件中可以指定

## Hystrix 熔断器 （重要）
1、服务链路调用，涉及的服务越来越多，叫扇出。Hystrix是一个用于处理分布式系统延迟和容错的开源库。分布式系统中，许多依赖不可避免的会调用失败、超时、异常等。
它能保证在一个依赖出现问题的情况下，不会导致整体服务失败，避免级联故障。
断路器监控故障，向调用方返回一个符合预期的、可处理的预备响应(FallBack)，而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间的占用，避免故障在分布式系统中的蔓延、乃至雪崩。
  
服务降级 fallback 为了避免长时间的等待而向客户端立刻返回一个友好的fallback提示。会发生服务降级的情况有：程序运行异常、超时、服务熔断触发服务降级、线程池/信号量打满也会导致服务降级。    
服务熔断 break  保险丝，直接拒绝访问，然后调用服务降级的方法返回友好提示。
服务限流 flowlimit 高并发操作，严禁拥挤，排队有序进行。

**服务降级**
对服务提供方进行并发压测。服务消费者也进行压测。Jmeter。小问题最终都会变成大问题。tomcat里面的线程池被打满。
服务提供方：超时、down机时候必须要有服务的降级处理。
服务消费方的调用时间满足不了自我要求，也要进行服务的降级处理。
  
@HystrixCommand标注在服务提供者上。可以设置自身调用超时的时间峰值，峰值之内可以正常运行，超时要有兜底的方法处理，作为服务降级fallback。     
@EnableCircuitBreaker 标注在服务提供方的主启动类上,开区服务提供方的服务降级配置。    
@EnableHystrix 标注在服务消费方的主启动类上。  
  
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")标注在controller类上，指定全局的服务降级兜底方法。
因为消费方都是使用FeignClient指定的调用的服务名称( _@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE")_ )，所以可以为Feign客户端定义一个处理服务降级的处理类就可以完成业务和兜底方法的解耦合。

**服务熔断**  
三种状态： close  open  half-close  
熔断机制是应对雪崩效应的一种微服务链路保护机制。当删除链路的某个微服务出错不可用或者响应时间太长时。
会对服务降级，进而熔断该节点的微服务调用，快速返回报错的响应信息。
当检测到该节点的响应正常之后，恢复链路调用。
Hystrix熔断机制，当失败的调用达到一定的阈值，缺省是5秒内20次调用失败，就会启动熔断机制，使用的注解是@HystrixCommand
  
多次错误，然后慢慢正确，刚开始正确率不满足，即时是正确的也不能进行访问。
熔断打开：请求不再进行调用当前服务，内部设置时钟一般为MTTR 平均故障处理事件，当打开时长达到所设时钟时则进入半熔断状态。
熔断关闭：不再对服务进行熔断。
熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则，就认为当前服务恢复正常，关闭熔断。
  
断路器的三个重要参数：
快照时间窗：断路器是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认是10秒钟。
请求总数阈值：在快照时间窗时间之内，在这个数字之内的请求，即时所有请求都超时或者失败，断路器都不会打开。
错误百分比阈值：当请求总数在快照时间窗之内错误的次数占请求总数的百分比超过了这个数值，断路器就会被打开。
  
断路器开始的条件：
当满足一定的阈值的时候（默认10秒之内超过20个请求）
当失败率达到一定的百分比之后（默认10秒内超过50%的请求失败）
当断路器开启的时候，所有请求都不会进行转发，一段时间之后（默认是5秒钟）这个时候断路器是半开启状态，会让其中一个请求进行转发，如果成功，断路器会关闭。

**服务限流 接后续Sentinel**


**hystrix的工作流程(重要)**
官网上梳理一遍。面试会问。

**服务监控仪表盘 hystrix dashboard**
在仪表盘服务启动类上标注 @EnableHystrixDashboard
在服务提供者添加监控actuator依赖
监控平台 7色1圈1线

## GateWay 服务网关 
zuul 和 gateway  
1、GateWay是SpringCloud自己研发的一个服务网关组件。  
底层使用的是Webflux中的reactor-netty响应式编程组件，底层使用的是Netty通讯框架。
对于高并发和非阻塞式通讯就非常有优势。
  
2、GateWay能做反向代理、鉴权、流量监控、熔断、日志监控等。  
Zuul1是一个阻塞的IO模型，GateWay是非阻塞的响应式IO模型。
  
3、GateWay的三个核心概念是：  
Route路由:构建网关的基本模块，ID,URI，一系列的断言和过滤器组成，如果断言为true则匹配该路由。  
Predicate断言：Java8的函数式编程，开发人员可以匹配HTTP请求中的所有内容，请求头参数等等，如果请求与断言匹配则进行路由。  
Filter过滤：Spring框架中的GateWayFilter实例，使用过滤器，可以在请求路由前或者之后对请求进行修改。  
Filter在pre类型的过滤器之间可以做参数校验、权限校验、流量监控、日志输出、协议转换等,在post类型的过滤器中可以做响应内容、响应头修改、日志输出、流量监控等，有着很重要的作用。  
核心流程就是：路由转发+过滤链

4、默认情况下，GateWay会根据注册中心注册的服务列表，以微服务名称创建动态路由进行转发，从而实现动态路由的功能。
XXX route predicate factory  --->  RoutePredicateFactory

After Route Predicate   -After=2020-04-21T15:51:37.485+8:00[Asia/Shanghai]  
Before Route Predicate    
Between Route Predicate  
Cookie Route Predicate 需要配置两个参数，一个是Cookie name，一个是正则表达式。路由规则会通过获取对应的Cookie name值和正则表达式的值去匹配，如果匹配上就会去执行路由。
Header\Host\Method\Path\Query   
说白了就是实现一组匹配规则，让请求过来找到对应的Route进行处理。
  
5、Filter  
在请求被路由的前或者后，对请求进行修改。

## config 服务配置
1、集中的管理配置文件。不同环境不同配置，动态化的配置更新，分环境部署（dev/test/prod/beat/release）
运行期间动态调整，服务自己向配置中心拉取配置信息。配置改变的时候不需要重启，服务自己感知并且更新应用。配置信息以REST接口的形式暴露。

2、配置文件的读取规则
>三种： label分支名  name服务名  profile环境名
公式1： /{label}/{application}-{profile}.yml  
master分支: http://config-3344.com:3344/master/config-dev.yml  
dev分支: http://config-3344.com:3344/dev/config-dev.yml  
公式2： /{application}-{profile}.yml  
公式3： /{application}/{profile}[/{label}]  

3、GitHub上面配置文件修改，配置中心能够很快的做出改变，但是连接配置中心的client服务端则不能（需要重启）。
怎么解决呢？保证真正的动态刷新，不用重启每一个连接的服务。  
对3355进行升级：  
(1) pom中有actuator监控。  
(2) bootstrap.yml中有暴露监控端点。 managerment......  
(3) 在业务类上加上 @RefreshScope  
(4) 每次修改完毕github上的内容，都发送post请求给3355  http://localhost:3355/actuator/refresh    
尽管能做到不重启服务就能刷新配置，但还是需要给服务单独发送一个post请求，还是会有麻烦，这就需要消息总线的支持。  

## bus 消息总线
Spring Cloud Bus 配合Spring Cloud Config使用，可以实现配置的动态刷新。Bus支持两种消息代理，RabbitMQ和Kafka
所有微服务订阅了一个主题，就能自动化的更新配置。  
  
Bus是一个将分布式系统的节点与轻量级的消息系统链接起来的框架，它整合了Java的事件处理机制和消息中间件的功能。
Bus能管理和传播分布式系统之间的消息，就像一个分布式执行器，可用于关闭状态更改，事件推送等，也可以当做微服务之间的通讯通道。
  
**全局通知  和  精确打击**   
需要安装RabbitMQ。在配置中心服务端和客户端，都添加消息总线RabbitMQ的依赖支持。并且在配置中心添加mq相关的配置。
当配置文件被改动之后，不需要通知每一个微服务，只需要通知配置中心即可。发送一个post请求给配置中心。
```
management:  
  endpoints:  
    web:  
      exposure:  
        include: 'bus-refresh'  #刷新配置 
        
http://localhost:3344/actuator/bus-refresh                 全局通知
http://localhost:3344/actuator/bus-refresh/config-client:3355 （微服务名称:端口） 定点通知，只通知3355
```


## Stream 消息驱动
1、解决的技术痛点：不再关注MQ的细节，一种绑定方式，自动在各种MQ之间切换。屏蔽底层消息中间件的差异，降低切换的成本，统一消息的编程模型。  
它是一个构建消息驱动微服务的架构，程序通过input和output与Binder对象进行交互，而通过配置绑定后，Binder对象与中间件进行交互。目前仅仅支持RabbitMQ和Kafka。
通过定义绑定器Binder作为中间层，实现了应用程序与消息中间件细节之间的隔离。input对应消费者，output对应在生产者。  
  
2、Stream 的流程套路。Binder屏蔽差异，Channel通道，队列的一种抽象，实现存储和转发。Source和Sink输入输出，发布消息就是输出，接收消息就是输入。
  
3、组件注解  
Middleware 中间件，目前仅仅支持RabbitMQ和Kafka  
Binder 是应用与中间件之间的封装。  
@Input 标识输入通道  
@Output 标识输出通道  
@StreamListener 监听队列，消费者的队列消息接收  
@EnableBinding 把Channel和exchange绑定在一起  

消息重复消费  
生产一条消息之后，连接同一个目的地的消费者都会受到消息。假设现在需要让一条消息只会让一个消费者受到消息，就需要用到消息分组来解决。  
Stream中处于同一个group的多个消费者是竞争关系，能够保证一个消息只被一个应用消费一次。处于不同组的是可以全面消费的。    
rabbitmq会分配一个组流水号。当开启了分组之后，mq是默认支持持久化的，当消费者宕机之后重连，是能接收到宕机期间发送的消息的。  

## Sleuth
一个请求最终是由多个微服务节点来共同协调产生最后的结果的，这个调用链路中任何一个环节出现问题都会引起整个请求的最终失败。  
Sleuth负责收集整理，Zipkin负责做数据的展现。  
zipkin从SpringCloud的F版本开始不再需要构建ZipKinServer了，只需要调用Jar包即可。
https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
下载之后：java -jar zipkin-server-2.12.9-exec.jar  在9411端口
在需要进行链路追踪的模块下，引入zipkin的pom依赖。  
```
order服务 调用 payment服务

第一步：都添加依赖  web actuator zipkin
    <!--zipkin-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>
第二步：下载jar包 启动 
https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
java -jar zipkin-server-2.12.9-exec.jar  暴露在9411端口 
      
第三步：都开启配置
spring:
  application:
    name: cloud-payment-service
  zipkin:
    base-url: http://localhost:9411
  sleuth:
    sampler:
      probability: 1  #采样率的值 介于 0-1 之间  1表示全部采集
```

# Spring Cloud Alibaba  
2018年10月31日 alibaba 入驻Spring Cloud官方孵化器。  
它支持服务降级限流：默认支持Servlet\Feign\RestTemplate\Dubbo\RocketMQ限流降级功能接入，可以在运行时通过控制套实时修改限流降级规则，还支持查看限流降级Metrics监控。    
服务注册与发现：适配SpringCloud的服务注册与发现，默认集成了Ribbon的支持。  
分布式管理配置：支持分布式系统的外部化配置，配置更新时自动刷新。  
消息驱动能力：基于Spring Cloud Stream为微服务应用构建消息驱动能力。  
阿里云对象存储、分布式任务调度。

## Nacos
Naming + Configuration + Service   注册命名、配置的服务组件，是一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。(Eureka + Config + Bus)
github地址  https://github.com/alibaba/nacos/releases  
  
**nacos支持从CP-AP的切换**  
C是所有节点在同一时间看到的数据时一致的，A是所有的请求都会收到响应。  

**nacos作为服务配置中心**  
配置规则：${prefix}-${spring.profile.active}.${file-extension}  
  
Namespace Group DataId 之间的关系：  
namesapce: Nacos默认的命名空间是public，namespace主要用来实现隔离。比如说三个环境，开发、测试、生产，就可以创建三个namespace。
group: 可以把不同的微服务划分带同一个分组里面去  
service:一个微服务可以包含多个Cluster集群，Nacos默认Cluster是default,比方说为了容灾，将微服务分别部署在AB两处不同的机房。这时就可以给A地方的service微服务起一个名称。
Instance：微服务实例   

**Nacos集群和持久化配置**
Nacos会自带一个内置的数据库，但是在集群环境下，多个Nacos就分别第多个不一样的配置，数据库也就是多份，不方便统一，所以需要一个数据中心。
Nacos推荐使用的是Naginx集群做虚拟IP,Nacos集群做配置中心避免单点故障，再使用MySQL数据库进行配置的持久化，并且对MySQL数据库进行了主从配置。
  
实际过程中会遇到的问题：  
nacos在多台机器上启动。并且都使用同一个数据库作为持久胡配置。  
nginx代理多个nacos节点，对外统一暴露ip和端口。  
服务注册的链接地址是nginx的地址，实际注册带nacos又nginx去做代理。  

## Sentinel 熔断限流  分布式系统的流量防卫兵
对比Hystrix Sentinel能独立出来形成一个单独的组件，不需要像Eureka、Hystrix那样单独写微服务代码。能提供界面化，细粒度的监控统一配置。  

QPS/线程    
QPS:当每秒钟调用api的QPS达到阈值的之后，进行限流。  
线程数：当调用api的线程数量达到阈值的时候，进行限流。  
流控模式：
(1)直接：快速失败。  
(2)关联:当关联的资源达到阈值的时候，就限流自己，AB两个api,B达到阈值，就限流A。  
(3)链路：多个请求调用同一个微服务。  

流控规则：
(1)快速失败：直接时报，抛出异常  DefaultController  
(2)预热：公式： 阈值除以冷负载因子coldFactor(默认值是3)，进过预热多长时间之后会达到阈值。    
选择Warm up 单击阈值为10  预热时长为5  最大的QPS为10，刚开始的时候QPS是10/3 = 3 ,5秒钟之内慢慢的从3QPS增加到10QPS。    
使用场景：秒杀系统开启的瞬间，会有很多流量上来，很可能会让系统卡死，预热就是为了保护系统，可以慢慢把流量放进来。  
(3)排队等待： 只允许挨个进行处理。让请求以均匀的速度通过，阈值类型必须设置为QPS。  
  
降级规则：
Sentinel熔断降级会在调用链路中某个资源出现不稳定状态的时候(超时，异常比例升高)，就会对这个资源进行限制，让请求快速失败，避免影响到其他的资源而导致级联错误。  
当资源被降级之后，在接下来的降级时间窗口之内，对该资源的调用都自动熔断。  
  
降级：
RT:  将RT配置为200 时间窗口配置为1 表示：如果超过200毫秒没有处理完毕这个请求，在未来的1秒钟时间内，这个服务不可用，断路器处于开启的状态。
异常比例： 0-1之间，如果异常的比例超过这个值，在未来的时间窗口之内，该api接口不可用。
异常数：当资源近一分钟的异常数目超过阈值之后会进行熔断。统计的时间窗口是分钟几倍的。

热点规则：  
@SentinelResource(value = "testHotKey",blockHandler = "dealTestHotKey")  
再加上热点规则的配置，方法testHotKey里面第一个参数只要QPS超过每秒1次，马上进行降级处理。  
兜底的方法是自定义的dealTestHotKey方法。如果没有配置兜底的方法，就会返回一个ErrorPage，不太友好。  

参数例外项：
对参数某些特定的值，可以单独制定热点规则。比如频繁查询的数据，当p1的值是5的时候，它的阈值可以达到200
Sentinel处理的只是控制台配置的违规情况会有blockHandller方法进行配置

系统自适应限流：
对整体应用的入口流量进行限流。CPU使用率，入口的QPS等维度。  

@SentinelResource  
在该注解上可以指定兜底的处理方法位于哪个类的那个方法。可以配置全局的，也可以配置一对一的。

服务熔断  
fallback  管理运行时异常   
blockHandler 管理配置违规   
若请求只配置fallback的兜底方法，则在业务抛出异常的时候会给出友好提示。  
若请求只配置blockHandler的兜底方法，业务抛出异常的时候，不会给出友好提示。但是加上熔断规则，在异常的次数超过规则之后，会到熔断的兜底方法，给出友好提示。  
若同时存在限流，降级兜底和业务异常的兜底，会根据实际情况相互配合，匹配相应的方法。 
   
exceptionsToIgnore = {IllegalArgumentException.class} 忽略配置中的异常。不再有fallback兜底，也不会有降级效果。

Sentinel规则持久化  
当服务重启之后，原先在Sentinel上配置的规则就全部消失了。
将限流规则持久化保存到Nacos，只要刷新某个rest地址，sentinel控制台的流控规则就能看到，并且正常生效。  
只要Nacos里面的配置不删除，流控规则就一直持续有效。

```
1、在pom里面添加 sentinel-datasource-nacos
2、在yml里面添加数据源
    sentinel:
      transport:
        dashboard: localhost:8080
        port: 8719
      datasource:
        ds1:
          nacos:
            server-addr: localhost:8848
            dataId: cloudalibaba-sentinel-service
            groupId:  DEFAULT_GROUP
            data-type:  json
            rule-type:  flow
3、结合Nacos做Sentinel流控的持久化规则 ，在nacos中新建规则即可，json格式    
[{
    "resource":"/testA",
    "limitApp":"default",
    "grade":1,
    "count":1,
    "strategy":0,
    "controlBehavior":0,
    "clusterMode":false
}]       

4、重启服务之后，发现规则是持续生效的
```

## Seata 处理分布式事务  GTS  @GlobalTransactional
分布式事务：跨数据源的一套请求，具有事务的特性。
当单体应用被拆分成为多个独立的应用，并且使用的是三个独立的数据源的时候。  
业务上的操作需要调用三个服务来完成，此时每个服务内部的数据一致性又本地事务来保证，但是全局的数据一致性问题没办法保证。  
一句话：一次业务操作需要跨多个数据源、多个系统进行远程调用，就会产生分布式事务问题。  
  
Seata简介（1+3套件解决分布式事务问题，一ID三组件）
一个全局的事务ID
TC：事务协调器，维护全局事务的运行状态，负责协调并且驱动全局事务的提交或者回滚。
TM:控制全局事务的边界，负责开启一个全局事务，并最终发起全局提交或者全局回滚的决议。
RM:控制分支事务，负责分支注册、状态汇报，并接收事务协调器的指令，驱动分支（本地）事务的提交和回滚。

处理过程  
1、TM向TC申请开启一个全局事务，全局事务创建成功并且生成一个全局的唯一XID  
2、XID在微服务调用链路的上下文中传播  
3、RM向TC发起注册分支事务，将其纳入XID对应全局事务的管辖  
4、TM向TC发起针对XID的全局提交或者回滚决议  
5、TC调度XID下管辖的全部分支事务完成提交或者回滚请求  


业务说明：
下订单-扣库存-减余额-改订单状态
```
1、建库seata 运行db_store.sql

2、建库seata_order 建一张表 t_order
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `money` decimal(11,0) DEFAULT NULL COMMENT '金额',
  `status` int(1) DEFAULT NULL COMMENT '订单状态，0创建，1完结',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

3、建库seata_account 建一张表 t_account
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
  `total` decimal(10,0) DEFAULT NULL COMMENT '总额度',
  `used` decimal(10,0) DEFAULT NULL COMMENT '已用额度',
  `residue` decimal(10,0) DEFAULT NULL COMMENT '剩余可以额度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

4、建库seata_storage 建一张表 t_storage
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `total` int(11) DEFAULT NULL COMMENT '总库存',
  `used` int(11) DEFAULT NULL COMMENT '已用库存',
  `residue` int(11) DEFAULT NULL COMMENT '剩余库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

5、把db_undo_log.sql 在每一个库里面都执行一下 

完成数据库的准备
```

调用测试:不加事务控制，会存在分布式事务问题。    
启动2001、2001、2003    
http://localhost:2001/order/create?userId=1&productId=1&count=10&money=100    

会一直报错，但是不影响业务测试。  
2020-04-28 23:58:47.246 ERROR 13080 --- [imeoutChecker_1] i.s.c.r.netty.NettyClientChannelManager  : no available server to connect.
  
@GlobalTransactional  分布式事务 GTS  
  
**详解TC TM RM**  
2019年1月份蚂蚁金服开源的分布式事务解决方案。  
Seat的全称是：Simple Extensible Autonomous Transaction Architecture 简单可扩展自治事务框架  

TC:全局分布式事务协调管理器，也就是seata服务器。  
TM:事务发起方,标注了@GlobalTransactional注解的服务  
RM:事务参与方,本次事务涉及到的数据库  
  
分布式事务的执行流程：  
TM开启事务（TM向TC注册全局事务）  
按照业务场景，编排数据库、服务等事务内资源（RM向TC汇报资源准备状态）  
TM结束事务，事务一阶段结束（TM通知TC 提交/回滚分布式事务）  
TC汇总事务信息，解决分布式事务是提交还是回滚  
TC通知所有RM提交/回滚资源，事务二阶段结束  

Seata的四种模式
AT:低侵入性的自动补偿事务模式，目前支持MySQL、Oracle的AT模式
TCC:与AT模式并存，灵活性更高
SAGA模式：为长事务提供解决方案
XA模式：开发中
  
一阶段加载：  
解析SQL语义，找到业务SQL要更新的业务数据，在业务数据被更新前，将其保存成 before image （前置镜像快照） 
执行业务SQL,更新业务数据，在业务数据更新后，将其保存为after image （后置镜像快照）最后形成行锁
以上操作全部在一个数据库事务内完成，这样就保证了一阶段操作的原子性。  

二阶段提交：如果是顺利提交，由于业务SQL在一阶段已经提交到了数据库，所以Seata只需要将一阶段保留的快照数据和行锁删掉即可，完成数据的清理。
  
二阶段回滚：提交异步化，非常快速的就能完成。回滚通过一阶段的回滚日志进行反向补偿。  
Seata需要回滚一阶段已经执行的业务SQL，还原业务数据。  
回滚的方式是使用 before image 还原业务数据，但是在还原之前需要先校验脏写，对比数据库当前业务数据和after image，如果两份数据完全一致就说明没有脏写，可以还原业务数据，如果不一致就说明有脏写，这个时候seata就处理不了需要人工处理。  

事务id和分支id
回滚的时候会根据记录的快照信息，进行反向的补偿。反向补偿完毕之后会进行日志的删除。  
官网：seata.io


##雪花算法
分布式全局唯一ID,在分布式系统中，往往需要对大量数据和消息进行唯一标识。需要一个能够生成全局唯一ID的系统。  
ID生成规则部分的硬性要求：1、全局唯一 2、趋势递增 3、单调递增 4、信息安全 5、含时间戳
ID生成系统的可用性要求：1、高可用 2、低延迟 3、高QPS    
在雪花算法之前，单体式应用的id一般是UUID或者是自增。  
**UUID**   
UUID(36位 32位+4位短线):性能比较好，本地生成，没有网络消耗。如果只是保证唯一性，已经足够了。  
但是存在的问题是，UUID是无序的,32位置的也很长，mysql官方推荐主键越短越好，因为索引会占据了很大的一部分空间。作为主键不太好。
最后，UUID索引，会导致B+树索引的分裂。每一次新的数据插入进来之后，为了做查询的优化，都会对索引的b+树进行修改，因为UUID是无序的，所以会导致对这个B+树进行大量的修改。
这样一些中间节点就会产生分裂，也会白白创建出许多不饱和的节点，降低了数据库插入数据的性能。  
  
**数据库自增主键**    
单体应用下是完全可以的，但是在集群情况下，坚决不可以使用自增。
分布式环境下，是使用mysql的自增id和replace into实现的。如果表中主键的值冲突了，就替代原来的记录。
这种方式获取自增主键的值，也不太合适。    
  
**基于Redis生成全局ID**  
Redis默认是单线程的，天生就是原子性，可以使用原子操作INCR和INCRBY来实现。
但是在Redis集群的情况下，同样MySQL一样需要设置不同的增长步长，同时key还要设置有效期。
假设一个集群中有5台机器，初始化key的值是12345，步长都是5，那么ID的情况就是：
A:1,6,11,16......
B:2,7,12,17......
C:3,8,13,18......
D:4,9,14,19......
E:5,10,15,20......
  
**雪花算法**  
twitter的SonwFlake算法解决了分布式唯一自增ID的需求。
最初Twitter把存储系统从MySQL迁移到Cassandra(Facebook开发的一套分布式NoSQL数据库系统)
因为Cassandra没有顺序ID生成机制，所以开发了这样一套全局唯一的ID生成服务。

SnowFlake能每秒产生26万个自增可排序的ID.  
并且满足能够按照时间有序生成;  
计算结果是一个64bit大小的整数，为Long类型，转换成字符串之后长度最多19;  
分布式系统内不会产生ID碰撞（又datacenter和workerid做分区），并且效率很高。

雪花算法
64位  
1位符号位 表示正数
41位时间戳 41个1大约是69年 从1970年到2039年9月7日
10位工作进程单位 5位机房编码和5位机器编码 2的10次方,1024个节点
12位序列号位  同一个毫秒之内产生的不同id  2的12次方就是4095个id,一毫秒内一个机器可以产生4095个id

优缺点：  
优：自增，不依赖于第三方，可靠，性价比很高。也可以根据业务灵活分配比特数位。
缺：依赖机器的时钟，时钟回拨会导致id重复生成，时钟不同步会导致集群整体的id不是完全绝对自增的，只能说是趋势递增。

其他的分布式唯一ID生成器：百度的一个UidGenerator,美团的一个Leaf

<br><br><br><br><br><br>
## 分布式事务
CAP : C一致性，A可用性，P分区容错
保证接口的幂等操作。（同一个接口，给相同的参数，执行任意多次是能够得到相同的业务结果。实现方式可以用redis来做，但是就需要数据有一个全局的唯一key，缓存起来，检测到重复操作之后直接返回上一次处理的结果就好）

### 解决方案1：二阶段提交
每一个事务要给事务管理器TM (Transaction Manager)进行一个准备-确认-提交的过程。  
通过TM就能把多个事务协调成一个统一的事务。但是这种方案会造成数据库资源的一个比较长时间的资源锁定。  
一般在业务系统里面用的比较多。    
Seata用的就是这种方案，TC-TM-RM  

### 解决方案2：补偿事务 TCC
每一个业务都要去写一个业务执行错误的补偿方案，代码入侵性比较强。  
一般在一些分布式事务场景不是很多的系统中去用比较合适。  

### 解决方案3：本地消息表
将一个分布式事务拆分成一个本地事务进行处理    
消息生产方创建一个消息表在本地，然后把消息发送给消息消费方    
消费方收到消息，就去处理自己的逻辑，如果他的本地事务处理失败，就返回消息给生产方   
生产方就会进行回滚   

### 解决方案4：尽最大努力通知
也是借助MQ的消息系统来进行事务控制的，定期校对，实现数据的一致性。  

主动方向被动方发送消息，携带的就是一些业务动作和参数。这个过程是运行消息丢失的，所以会间隔一段时间重复发N次。  
并且把这个消息的状态写到本地的数据库里面。  

被动方正常收到这个消息就可以去处理对应的业务，但是还是会存在一些没有收到的消息，所以主动方就需要提供一个查询接口给被动方，及时的获取没有收到的业务员消息。  

被动方收到消息就去执行，执行成功就修改这个消息的状态，结束自己的本地事务，也通知主动方去结束事务，但这个通知过程也是允许失败的。  
所以被动方也提供一个查询消息状态的接口给主动方，这样就能结束自己的本地事务。失败了回滚，成功了就正常提交。  
这些查询接口都是根据定时策略去查询的。  

这种方案用于哪些对整个业务完成时间不敏感的业务上比较合适。  

## 双写一致性
###  1：先更新缓存，再更新数据库
假设在更新数据库的时候发生了异常导致数据回滚了，那缓存里面的数据就是不正确的，会被其他线程读取到。

###  2：先更新数据库，再更新缓存
线程不安全，最终的结果是和数据库对不起来的
两个线程去更新数据：  
A更新数据库——B更新数据库——B更新缓存——A更新缓存  

###  3：先删除缓存，再更新数据库
A去读取，B去更新，读到的数据永远是原来的  
B删除缓存——A查询旧值——A更新缓存——B更新数据库  
解决办法：**延迟双删**   
在最后，间隔一秒，再去把缓存删除一次    

###  4：先更新数据库，再删除缓存
这种也会有问题，A去读取，B去更新，极端情况会发生脏数据  
缓存刚好失效——A去读取，没有数据，读取数据库，准备把数据写到缓存——B去更新，更新数据库，删除缓存——A把旧数据写到缓存上  
发生的概率比较低，如果要解决这种情况，那再进行一次删除，也就是**延迟双删**也能解决。  

当然这些办法都不能百分百保证缓存跟数据库一定是一致的，在删除缓存的时候都有可能失败，所以缓存是要设置失效时间。重试保障也是有必要的。
（一般不用，用先更新数据库，在删除缓存，延迟之后再删，基本就能保证缓存和数据库的数据一致性了）

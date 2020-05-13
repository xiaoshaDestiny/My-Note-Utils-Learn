### Q:什么是Spring?
Spring是一个很全面的应用开发框架，从发布以来社区就很活跃，结合它强大的社区，发展到今天，已经是一个很成熟的Java一站式应用开发解决方案。
轻量级、与业务的解耦、与其他框架无缝整合是它的特点。

### Q:常用模块?
Spring Core:提供IOC容器，对Bean进行管理。    
Spring Context：继承BeanFactory，提供山下文信息。  
Spring Dao:对JDBC数据库查询的支持，提供事务支持。  
Spring ORM：提供对JPA,Hibernate,MyBatis等ORM映射的支持。  
Spring AOP:代理通知的支持。  
Spring Web\MCV：提供对web开发的支持。  

### Q:说一说Spring Bean的作用域? 
Singleton单例,容器中只存在一个Bean  
prototype原型,每次使用的时候，容器都将创建一个Bean实例  
request,一次request请求创建一个实例  
session,一次http session创建一个实例 
application,全局作用域。跟单例不一样，一个应用可以有多个ApplicationContext  

### Q:说一说SpringBean生命周期? 
从配置文件读取Bean的定义，实例化Bean对象  
设置对象的属性
注入Aware接口，也就是这个bean依赖的其他能在ioc里面找到的bean属性 
执行BeanPostProcessor，自定义的处理（分前置处理和后置处理）  
执行Bean自定义的初始化方法   
Bean对象创建完毕，之后就是使用。  
销毁的时候是执行destroy方法然后去执行自定义的销毁方法。  

https://javadoop.com/post/spring-ioc   

### Q:说一说IOC原理。  
IOC,在应用中将创建对象的权利完全交给Spring去处理，因为在项目中需要用到的服务类有很多，在实例化的时候如果都要去了解这些类的构造方法是什么样的，创建规则是怎么样的，是很头疼的事情。  
而Spring就预先将这些会用到的类通过xml配置文件的形式去描述Bean和Bean之间的关系，在使用到的地方，只需要引入就可以使用了。  
开始的时候是使用xml配置的形式，后来SpringBoot成为主流之后，基于注解的Bean配置就开始流行起来。   
一般情况下，IOC其实说的是一个 singletonObjects 的Map对象，也称为一级缓存，里面放置的是实例化好的单例对象。  
扫描到的bean配置信息，会生成一个Bean配置的注册表信息，叫BeanDefinitionMap。  
Spring就拿着这份注册表，通过Java语言的反射机制去实例化Bean,还解决了这些Bean之间的依赖关系，创建一个基本的可运行环境这是IOC容器最核心的出发点。   
但Spring在完成这些底层Bean创建的工作之后，还提供了bean实例的缓存、生命周期的管理等高级的服务,减轻了很多开发量,也就让Spring社区的开发慢慢成为Java开发的主流分支。  
因为他很好的解决了Bean创建这些对象信息，很多框架在写的时候，首要考虑的就是都能被Spring所整合管理。  

### Q:说一说IOC容器的创建过程，能说多少说多少。（考察源码的熟悉程度）  
首先是将xml配置文件全部扫描到,生成一个配置文件资源的数组。当然不管是xml形式还是SpringBoot注解形式最后都会在refresh()方法里面去完成IOC的创建。  
然后检查是否已经存在一个BeanFactory,有就销毁重新创建一个BeanFactory,而这个BeanFactory里面将存放一个重要的信息，BeanDefinitionMap,初始化IOC容器也就将围绕着它来进行。  
加载Bean进入BeanFactory的过程大致是这样的，首先去遍历每一个Resource文件，也就是xml描述文件,生成一个个的document文档对象，然后解析各个标签。像<mvc><context>这些。  
当然还有bean标签，也就是解析bean，解析后的数据生成一个个的BeanDefinition，里面保存了Bean的重要信息，  
比如这个Bean指向的是哪个类、是否是单例的、是否懒加载、这个Bean依赖了哪些Bean，name属性的定义按照 “逗号、分号、空格” 切分，形成一个别名列表数组。  
后面一步是解决id和别名，没有指定id会默认取这个别名数组的第一个元素，没有id和name就把beanClassName设置为Bean的别名。   
完成这个步骤之后是注册，就是把这个BeanDefinition放到BeanDefinitionMap里面。  
到这里的话应该说BeanFactory已经创建完成。   
  
假如配置了懒加载的话，到这里其实已经完成了IOC创建，但是实际中是不配置懒加载的。懒加载就是用到再去创建，比较麻烦。    
所以Spring很智能的帮我们把所有单例的对象都在创建BeanFactory完成之后去创建好，最终会放到singletonObjects中。  
这一步要解决的核心问题就是循环依赖。  
刚刚已经说了，所有的Bean都会注册到BeanDefinitionMap中，所以只要用反射就可以完成对象的创建工作，但是创建出来的对象只是一个Java对象，还没有被SpringIOC容器管理。  
  
接下来创建对象的过程大致如下：   
1、检查一级缓存singletonObjects有没有这个对象了，没有检查二级缓存earlySingletonObjects。   
一级缓存存放的是所有已经创建好的对象，二级缓存存放的是正在创建的对象，这些对象提前已经曝光了，三级缓存存放的是创建对象的对象工厂。  
2、二级缓存也没有，就用反射创建对象，把对象放到二级缓存中，标注这个对象正在创建。  
3、为新建的bean设置属性，设置属性的时候这个对象先放到三级缓存singletonFactories里面去。  
假设这个时候A的属性是B对象，B的属性是A对象，B创建完成之后发现设置属性的时候，在一级缓存中没有，但是在三级缓存中有，  
即使A的属性还没填充完整，但是A依赖的引用值已经提前知道了，从这里就解决了循环依赖的问题。    
创建了之后，对象加到一级缓存，从二级缓存中删除。  
能被加到三级缓存singletonFactory的前提是这个对象执行了构造方法，所以使用构造器依赖的场景下，Spring是没办法帮我们搞定的。  
当然也有解决办法，就是使用@Lazy注解标识这个对象懒加载，当首次使用的时候再去创建这个对象。  

### Q:如何关闭循环依赖
Bean对象在创建时候的条件是三个。  
1、是否是单例，这个不能动。  
2、对象是不是在将要创建的集合中，这个也是不能动的。  
只能改第3个参数，是否支持循环依赖，Spring默认是支持的。  
关闭这个参数需要在创建的时候不去传递配置文件，这样就不会有Bean被创建。    
然后通过context对象去关闭循环依赖、传递配置文件。最后手动调用一下refresh方法。  

### Q:对象的创建过程
实例化Spring容器 -> 扫描bean配置 -> 解析Bean配置 -> beanDefinition  put map -> bean工厂的后置处理器 -> 验证(单例 懒加载 beanName合法) -> 推断构造方法 -> 反射生成对象 
-> 缓存注解信息  -> 注入属性 -> 回调Aware接口 -> 调用生命周期的回调方法 -> 完成代理aop -> put进入单例池 容器 -> 销毁

### Q:说一说Spring的AOP
AOP就是在运行期间，动态的将某一段代码切入到指定方法的指定位置进行运行的编程方法，对比之前自顶向下的编程方式，就有了很好的扩展性。
把业务逻辑和横切的问题进行分离，解耦，提高代码的重用性。一些经典的应用场景就是，记录日志、监控性能、权限控制、事务管理。
Spring 默认使用的是JDK提供的动态代理技术， 在需要代理的类不是代理接口的时候，Spring会自动切换成Cjlib的代理。
JDK的动态代理使用Proxy.newProxyInstance()方法去代理对象，然后通过反射invoke去调用真实的对象方法。
而Cjlib的动态代理是去继承代理类，实现所有非final的方法，然后就可以在子类中拦截父类方法的调用，拦截之后就织入拦截的代码。

### Q:说一说SpringMVC的处理流程
用户的Request请求首先会到前端控制器DispatcherServlet进行分析处理，然后到RequestMapping中去寻找对应的映射。  
假如不存在对应的映射会去看是不是配置了静态资源,如果没配或者找不到那就返回404。  
如果RequestMapping中有对应的映射，接着会放到一个HandlerExecutionChain异常链中,  
然后调用拦截器的前置方法，接着再去执行Controller里面的逻辑，最后会得到一个ModelAndView对象  
然后调用拦截器的后置方法可以对得到的结果进行修改。  
假如这个过程中存在异常，会使用异常处理组件得到一个新的ModelAndView对象，没有就返回正确的ModelAndVoew对象。  
然后调用ViewResolver组件去解析这个ModelAndViwe得到实际的View  
然后是是渲染视图，调用拦截器的afterCompletion方法释放资源，结束。  

### Q:说一说Spring,SpringMVC，SpringBoot
Spring为应用提供了一个基本的可运行环境，SpringMVC提供的是一个Web层次的MVC框架去处理用户请求，他们都有很多的配置文件需要编写。
并且在构建、运行、调试、部署的时候都比较麻烦，SpringBoot则是提供了一个快速整合项目需要的构建方案，这些组件提供starter依赖的形式构建进入项目中，摒弃了xml配置文件的形式，基本都是基于注解的形式去构建。
只需要在yml文件中配置少量的信息即可。

### Q:SpringBoot在启动时候干了什么？（细节很多，各种listener）
主启动类上标注了@SpringBootApplication注解，SpringApplication.run()方法执行之后，其实后面会有一个@EnableAutoConfiguration注解，  
就会扫描classpath下面所有的配置项，通过反射实例化IOC容器的配置类，然后汇总为一个并加载到IoC容器，所以能直接用web,mysql连接这些bean对象。











































<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
### IOC 原理 创建过程
**启动**  
启动IOC容器是通过 ClassPathXmlApplicationContext根据类路径下的xml文件内容来构建 ApplicationContext这个对象就是我们的IOC容器了。
当然还有其他的方案去构建这个ApplicationContext。FileSystemXmlApplication从文件系统上获取、AnnotationConfigApplicationContext基于注解来配置等。  

**BeanFactory**   
ApplicationContext继承了ListableBeanFactory获取多个Bean,BeanFactory 接口的方法都是获取单个 Bean 的。  
ApplicationContext 继承了HierarchicalBeanFactory,可以在应用中起多个BeanFactory，然后可以将各个BeanFactory设置为父子关系。
ClassPathXmlApplicationContext 的构造方法。
核心方法：refresh()方法会将原来的IOC销毁，重现创建IOC容器。并且是加锁的。
refresh()方法里面调用了很多方法，当它执行完的时候，IOC容器也就初始化完成了。

**创建Bean容器前的准备工作**  
refresh->prepareRefresh()方法：记录启动时间，标记已启动状态，处理配置文件中的占位符，校验xml配置文件。
refresh->obtainFreshBeanFactory()方法：这个方法回去初始化BeanFactory,加载Bean,注册Bean。Bean实例不是在这一步生成的。
refresh->obtainFreshBeanFactory()->refreshBeanFactory();关闭旧的BeanFactory,创建新的BeanFactory,加载Bean定义，注册Bean.
refresh->obtainFreshBeanFactory()->refreshBeanFactory()->customizeBeanFactory(beanFactory);设置 BeanFactory 的两个配置属性：是否允许 Bean 覆盖、是否允许循环引用。
就是在配置文件中定义bean时使用了相同的id或name，默认情况下，如果在同一配置文件中重复了，会抛错，但是如果不是同一配置文件中，会发生覆盖。
默认情况下，Spring允许循环依赖，当然如果你在A的构造方法中依赖B，在B的构造方法中依赖A，Spring是无法解决这种类型的循环依赖的。写在属性上的依赖是能解决的。
  
refresh->obtainFreshBeanFactory()->refreshBeanFactory()->loadBeanDefinitions(beanFactory);加载 Bean 到 BeanFactory 中
这个方法将根据配置文件，加载各个Bean放到BeanFactory中。
加载的过程树循环加载每一个resource文件，也就是Bean定义文件，将xml文件转换为Document对象，然后解析各个标签（<mvc> <text> <context> <bean>等等）
其中就有IOC容器需要重点处理的<bean>标签。processBeanDefinition解析bean标签。

**解析Bean元素**  
将 name 属性的定义按照 “逗号、分号、空格” 切分，形成一个 别名列表数组，
BeanDefinition保存了Bean的重要信息
比如这个Bean指向的是哪个类、是否是单例的、是否懒加载、这个Bean依赖了哪些Bean等等。

如果没有指定id, 那么用name列表的第一个名字作为beanName,就会根据bean标签配置的信息，创建出一个BeanDefinition，然后把配置中的信息都设置到实例中
如果都没有设置 id 和 name，那么此时的 beanName 就会为 null，但是最终会把 beanClassName设置为Bean的别名。
完成了根据 <bean /> 配置创建了一个 BeanDefinitionHolder 实例，这个实例里面也就是一个 BeanDefinition 的实例和它的 beanName、aliases(别名)这三个信息。
得到了这个BeanDefinitionHolder之后，下一步是注册Bean。

**注册Bean**  
所有的 Bean 注册后会放入这个 beanDefinitionMap 中
注册也只是将这些信息都保存到了注册中心(说到底核心是一个 beanName-> beanDefinition的map)

**初始化所有的singleton beans**  
finishBeanFactoryInitialization(beanFactory);这里会负责初始化所有的singleton beans。
应该说BeanFactory已经创建完成，并且所有的实现了BeanFactoryPostProcessor 接口的Bean都已经初始化并且其中的postProcessBeanFactory(factory)方法已经得到回调执行了。而且 Spring 已经“手动”注册了一些特殊的 Bean，如 environment、systemProperties 等。

剩下的就是初始化singleton beans了，我们知道它们是单例的，如果没有设置懒加载，那么Spring会在接下来初始化所有的singleton beans。使用getBean(beanName)进行初始化
会进行一系列的检查，创建过了此 beanName 的 prototype 类型的 bean，那么抛异常，检查这个BeanDefinition 在容器中是否存在，父容器中也没有，才会去执行 doCreateBean创建bean,然后才去设置bean的属性，接着完成bean创建之后的回调。

### IOC 依赖注入的流程
设置不允许懒加载，那么IOC初始化完成之后，会把单例的bean加载到IOC容器中。
不过这个要看是不是设置了懒加载，设置了懒加载会在用户第一次像IOC获取Bean的时候触发。


### 循环引用
循环引用指的是一种强引用关系，也就是在一个类里面有另一个类的引用作为属性。单例模式下，Spring能够解决循环引用的问题。多例环境中是不支持的。
循环依赖发生在对象创建之后，为对象的属性注入的环节，也就是常说的DI(依赖注入)。
所以要从对象的创建说起。

循环加载每一个resource文件，也就是xml配置文件，转化为一个Document对象之后，就分析每一组标签。<mvc><context>这些，当然最重要是<bean>标签。  
<bean>标签解析的最终结果会形成一个BeanDefinition对象，这个对象描述了非常详细的bean属性，class文件，是否懒加载，是不是有父类，有哪些属性，等等很详细的信息。
并且解析完成之后会把这个BeanDefinition放到BeanDefinitionMap中去。这个过程叫做注册，所以这个BeanDefinitionMap就会持有所有Bean的信息。
所以只要用这个Map用反射去创建Bean就好了。在创建的时候看看有没有配置Spring的扩展，有的话就去调用扩展方法。没有的话就去调用Bean中默认的无参数的构造方法，放到Spring的IOC容器中。

beanFactory的BeanDefinitionList记录了Bean的id的顺序，跟配置文件中的是一致的。  
单例模式下，是先创建对象，再去对属性依赖注入。多例模式下是创建bean的时候就要对bean的属性进行赋值，所以会出现循环依赖。

创建Bean的条件：懒加载，不是抽象类，并且是单例。最后会走到一个doGetBean方法。
校验id是不是重复了，重复了会换成别名。
然后去getSingletons，SingletonObject就是IOC容器。

在创建之前会去判断对选哪个是否正在创建，如果需要创建的对象不再IOC容器中，就去把接下来要创建的对象加到马上将要创建的集合中。singletonsCurrentlyInCreation.  
然后在doCreateBean中去创建bean。
创建成功之后，这个对象只是一个Java对象，还没有称为一个SpringIOC容器中的对象。
之后会调用addSingletonFactory，如果IOC容器中没有这个id的Bean。
this.singletonFactories.put(beanName, singletonFactory); 这个集合就是三级缓存
this.earlySingletonObjects.remove(beanName);
this.registeredSingletons.add(beanName);

刚开始创建A对象，A对象创建完毕就会放到三级缓存中。在设置属性依赖的时候，发现需要B对象，这时候它首先尝试从IOC容器中获取这个对象，获取不到再去尝试从三级缓存中获取
三级缓存中也没有才会去创建B对象，B对象创建完成之后他会把B放到三级缓存中。B需要A作为属性依赖，IOC容器中此时还没有A对象，但是三级缓存中已经有了。就能解决B的属性依赖是A。
到这里，B对象创建完毕，放到了IOC容器中，也就是A的属性设置完毕，也会把A放到IOC容器中。


















**什么第Spring?**  
Spring是一个很全面的应用开发框架，从发布以来社区就很活跃，结合它强大的社区，发展到今天，已经是一个很成熟的Java一站式应用开发解决方案。
轻量级、与业务的解耦、与其他框架无缝整合是它的特点。

**常用模块，常用注解**  
Spring Core:提供IOC容器，对Bean进行管理。    
Spring Context：继承BeanFactory，提供山下文信息。  
Spring Dao:对JDBC数据库查询的支持，提供事务支持。  
Spring ORM：提供对JPA,Hibernate,MyBatis等ORM映射的支持。  
Spring AOP:代理通知的支持。  
Spring Web\MCV：提供对web开发的支持。  

**Q:说一说Spring Bean的作用域**  
Singleton单例,容器中只存在一个Bean  
prototype原型,每次使用的时候，容器都将创建一个Bean实例  
request,一次request请求创建一个实例  
session,一次http session创建一个实例  
  
**Q: 说一说SpringBean生命周期**  
A: 
从配置文件读取Bean的定义，实例化Bean对象  
设置对象的属性
注入Aware接口，也就是这个bean依赖的其他能在ioc里面找到的bean属性 
执行BeanPostProcessor，自定义的处理（分前置处理和后置处理）  
执行Bean自定义的初始化方法   
Bean对象创建完毕，之后就是使用。  
销毁的时候是执行destroy方法然后去执行自定义的销毁方法。 



### IOC 原理 创建过程
https://javadoop.com/post/spring-ioc  
**IOC原理**   
将程序中，创建Bean的过程交给Spring去处理，因为在项目中需要用到的服务类有很多，在实例化的时候如果都要去了解这些类的构造方法是什么样的，创建规则是怎么样的，是很头疼的事情。  
而Spring就预先将这些会用到的类通过配置文件的形式去去描述Bean和Bean之间的关系，在用到的地方引用即可。开始的时候是使用xml配置的形式，后来SpringBoot成为主流之后，基于注解的Bean配置就开始流行起来。  
IOC其实就是一个Map,存放的就是各种对象。Spring通过Java语言的反射机制去实例化Bean,并且建立了这些Bean之间的依赖关系，这是IOC容器最核心的出发点。  
但IOC在完成这些底层Bean创建的工作之后，还提供了bean实例的缓存、生命周期的管理等高级的服务。减轻了很多开发量。  
xml文件中的Bean配置信息，会在Spring容器中生成一份相应的Bean配置注册表，根据这个注册表就可以完成Bean的实例化，并且完成Bean之间的依赖关系，为应用提供一个准备就绪的基本运行环境。    

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


### 如何关闭循环依赖
Bean对象在创建时候的条件是三个，1、是否是单例，这个不能动。2、对象是不是在将要创建的集合中，这个也是不能动的。只能改第3个参数，是否支持循环依赖，Spring默认是支持的。
关闭这个参数需要在创建的时候不去传递配置文件，这样就不会有Bean被创建。  
然后通过context对象去关闭循环依赖、传递配置文件。最后手动调用一下refresh方法。

### 对象的创建过程
实例化Spring容器 -> 扫描bean配置 -> 解析Bean配置 -> beanDefinition  put map -> bean工厂的后置处理器 -> 验证(单例 懒加载 beanName合法) -> 推断构造方法 -> 反射生成对象 
-> 缓存注解信息  -> 注入属性 -> 回调Aware接口 -> 调用生命周期的回调方法 -> 完成代理aop -> put进入单例池 容器 -> 销毁


















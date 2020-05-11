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









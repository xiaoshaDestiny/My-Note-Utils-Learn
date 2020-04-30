**什么第Spring?**  
Spring是一个很全面的应用开发框架，从发布以来社区就很活跃，结合它强大的社区，发展到今天，已经是一个很成熟的Java一站式应用开发解决方案。
轻量级、与业务的解耦、java对象容器、面向切面、与其他框架无缝整合是它的特点。

**常用模块，常用注解**  
常用模块ORM,Web,MVC,IOC,AOP等。

**IOC原理**  
Spring通过配置文件的形式去描述Bean和Bean之间的关系，主要是使用Java语言的反射机制去实例化Bean,并且建立了这些Bean之间的依赖关系，这是IOC容器最核心的出发点。
但IOC在完成这些底层Bean创建的工作之后，还提供了bean实例的缓存、生命周期的管理等高级的服务。减轻了很多开发量。
xml文件中的Bean配置信息，会在Spring容器中生成一份相应的Bean配置注册表，根据这个注册表就可以完成Bean的实例化，并且完成Bean之间的依赖关系，为应用提供一个准备就绪的基本运行环境。
  
**Spring Bean的作用域**  
Singleton单例,容器中只存在一个Bean  
prototype原型,每次使用的时候，容器都将创建一个Bean实例  
request,一次request请求创建一个实例  
session,一次http session创建一个实例  
  
**SpringBean生命周期**  
从配置文件读取Bean的定义，实例化Bean对象  
设置Bean的属性  
注入Aware的依赖  
执行通用的方法前置处理postProcessorBeforeInitialization()  
执行afterPropertiesSet()方法  
执行Bean自定义的初始化方法  
执行后置处理方法postProcessorAfterInitialization()  
Bean对象创建完毕，之后就是使用。  
销毁的时候是执行destroy方法然后去执行自定义的销毁方法。  








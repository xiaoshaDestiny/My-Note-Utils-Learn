package learn.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xrb
 * @create 2020-03-10 15:02
 * java.lang.OutOfMemoryError: Metaspace
 *
 * 默认metaspace空间大小
 *  uintx MetaspaceSize    = 21810376     {pd product}
 *
 *  JVM参数演示：
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 * Java8之后使用元空间替代了永久代。
 *
 * Metaspace是方法区在HotSpot中的实现，它与持久代的最大区别在于，
 * MetaSpace并不是在虚拟机内存中而是使用本地内存，也即在java8中，
 * classe metadata(the virtual machines internal presentation of Java class)
 * 被存储在叫做metaspace的Native Memory
 *
 * 存放的信息如下：
 * 虚拟机加载的类信息。
 * 常量池
 * 静态变量
 * 即时编译后的代码
 *
 * 模拟metaspace空间溢出，不断生成类往元空间灌，类占据的空间总是会超过metaspace指定的空间大小。
 *
 */
public class MetaspaceOOMDemo {

    static class OOMTest{ }
    public static void main(String[] args) {
        int i = 0;
        try {
            while (true){
                i++;
                //创建代理类
                Enhancer enhancer=new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invoke(o,args);
                    }
                });
                enhancer.create();
            }
        }catch (Throwable t){
            System.out.println("--------------");System.out.println(i+"次之后发生异常");
            t.printStackTrace();
        }
    }
}

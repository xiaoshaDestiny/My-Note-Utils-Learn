package learn.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xrb
 * @create 2020-03-10 11:37
 */

/**
 * 参数配置演示：
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 * GC回收时间过长时会抛出OutOfMemoryError。
 * 过长的定义是：超出98%的时间用来做GC，并且回收了不到2%的堆内存，连续多次GC,
 * 都只回收了不到2%的极端情况下才会抛出。
 *
 * 假如虚拟机不抛出GC overhead limit 错误会发生什么情况呢？
 * 那就是GC清理的这些内存很快被再次填满，迫使GC再次执行，恶性循环。
 * 现象就是CPU的使用率一直是100%，但是GC却没有任何的效果。
 */
public class GcOverHeadDemo {
    public static void main(String[] args) {
        int i = 0;
        List<String> list =new ArrayList<>();
        try {
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        }catch (Exception e){
            System.out.println("********************");
            System.out.println(i);
            e.printStackTrace();
        }
    }
}

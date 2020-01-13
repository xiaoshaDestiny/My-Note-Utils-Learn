package learn.jvm;

import java.util.Random;

/**
 * @author xrb
 * @create 2020-01-05 12:26
 */
public class Test02HeapMemory {
    public static void main(String[] args){
     /*   //返回 Java 虚拟机试图使用的最大内存量。
        long maxMemory = Runtime.getRuntime().maxMemory() ;
        //返回 Java 虚拟机中的内存总量。
        long totalMemory = Runtime.getRuntime().totalMemory() ;
        System.out.println("MAX_MEMORY = " + maxMemory + "（字节）、" + (maxMemory / (double)1024 / 1024) + "MB");
        System.out.println("TOTAL_MEMORY = " + totalMemory + "（字节）、" + (totalMemory / (double)1024 / 1024) + "MB");
*/
        String str = "www.atguigu.com" ;
        while(true)
        {
            str += str + new Random().nextInt(88888888) + new Random().nextInt(999999999) ;
        }


    }

}

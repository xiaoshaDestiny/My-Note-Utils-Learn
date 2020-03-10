package learn.jvm;

import java.nio.ByteBuffer;

/**
 * @author xrb
 * @create 2020-03-10 14:06
 * Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
 *
 * 直接内存挂掉。MetaSapce不在虚拟机中，而是使用本地内存。
 * 这个异常的出现源于两种技术， netty，底层是一个NIO.
 *
 * 虚拟机参数演示配置：
 * -Xmx10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 *
 * 导致原因：
 * 写NIO程序经常使用到ByteBuffer来读取或写入数据，这是一种基于通道Channel与缓冲区Buffer的IO方式。
 * 它可以使用Native函数库直接分配堆外面的内存，然后通过一个存储在Java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作，
 * 这样能在一些场景中显著提升性能，因为避免了在Java堆和Native对中来回复制数据:
 *      ByteBuffer.allocate(capablity)第一种方式是分配JVM堆内存，属于GC管辖范围，由于需要拷贝所以速度相对较慢。
 *      ByteBuffer.allocateDirect(capability)第一种方式是分配OS内存，不属于GC管辖范畴，由于不需要内存拷贝所以速度相对较快。
 * 但是如果不断分配本地内存，堆内存很少使用，那么JVM就不需要执行GC,DirectByteBuffer对象们就不会被回收，
 * 这时候堆内存充足，但是本地内存可能已经使用光了，再次尝试分配本地内存的时候就会出现OutOfMemoryError，程序直接崩溃。
 *
 * 以前的对象都是分配在JVM的堆内存中，有些对象如果是分配在了操作系统的本地内存中，GC不会回收本地内存中的对象，
 * 如果堆内存足够，GC也根本不会触发。本地内存用光的时候，程序也会爆出OOM。
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory:");
        System.out.println(sun.misc.VM.maxDirectMemory()/1024/1024+"MB");

        try {Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        ByteBuffer bb = ByteBuffer.allocateDirect(6*1024*1024);

    }
}

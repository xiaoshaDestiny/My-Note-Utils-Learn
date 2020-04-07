package com.learn.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author xrb
 * @create 2020-04-07 11:32
 *
 * 缓冲区 Buffer
 * 在Java NIO中 负责数据的存取。缓冲区就是数组，用于存储不同的数据类型。
 * 根据数据类型的不同，提供了相应类型的缓冲区，boolean类型除外。
 * ByteBuffer\CharBuffer\ShortBuffer......
 *
 * 一、获取缓冲区
 * 上述缓冲区的管理方式几乎一致，通过allocate()获取缓冲区
 *
 * 二、存取数据核心方法
 * put()存入数据到缓冲区
 * get()获取缓冲区数据
 *
 * 三、缓冲区核心属性
 * capacity 容量，缓冲区中最大存储数据的容量
 * limit    界限，缓冲区中可以操作数据的大小 limit后面的数据时不能进行读写的
 * position 位置，缓冲区中正在操作数据的位置
 * mark:    标记，表示记录当前position的位置，可以通过reset()恢复到mark位置
 * 大小关系： 0 <= mark <= position <= limit <= capacity
 *
 * 四、方法
 * flip()    切换到读取数据模式
 * rewind()  重复读取
 * clear()   清空缓冲区  但是缓冲区中的数据还在 处于被遗忘状态
 * mark() 标记 标记当前position的位置
 * reset() 恢复到mark位置
 *
 *
 * 五、直接缓冲区与非直接缓冲区
 * 非直接缓冲区：通过allocate()方法分配缓冲区，将缓冲区建立在JVM内存中。
 *   直接缓冲区： 通过allocateDirect()方法分配直接缓冲区，将缓冲区建立在操作系统的物理内存中。
 * 避免内存数据的拷贝，直接在物理内存中开辟空间。效率高，也不好管理。
 * 内核地址空间和用户地址空间的数据copy
 *
 * isDirect()方法可以判断缓冲区是不是直接缓冲区
 *
 *
 * 通道负责连接，缓冲区负责存储。
 */
public class TestBuffer {




    @Test
    public void test03(){
        ByteBuffer buffer =  ByteBuffer.allocateDirect(1024);
        boolean b = buffer.isDirect();
    }

    @Test
    public void test02(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("abcd".getBytes());

        buffer.flip();

        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst,0,2);
        System.out.println(new String(dst,0,2));
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        //mark() 标记 标记当前position的位置
        buffer.mark();
        buffer.get(dst,2,2);
        System.out.println(new String(dst,2,2));
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        //reset() 恢复到mark位置
        buffer.reset();
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        //判断缓冲区中是否还有剩余的数据 如果有看看还剩几个
        System.out.println(buffer.hasRemaining());
        if(buffer.hasRemaining()){
            System.out.println(buffer.remaining());
        }
    }

    @Test
    public void test01(){
        //1、分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("------新建之后 allocate(1024)------");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        //2、put() 方法存入数据到缓冲区中
        String str = "abcd";
        buffer.put(str.getBytes());

        System.out.println("------添加之后 put(4个字节的str)------");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        //3、 flip() 切换到读取数据模式
        buffer.flip();

        System.out.println("------切换到读取数据模式之后 flip()------");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        //4、get() 读取缓冲区数据
        //get一个

        System.out.println("------get()------");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println((char)buffer.get());

        //get一个字节数组
        byte[] dst = new byte[buffer.limit()-1];
        buffer.get(dst);

        System.out.println("------get(byte[])------");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println(new String(dst,0,dst.length));

        //5、rewind()重复读取
        buffer.rewind();
        System.out.println("------rewind()------");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        //6、clear()清空缓冲区  但是缓冲区中的数据还在 处于被遗忘状态
        buffer.clear();
        System.out.println("------clear()------");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        System.out.println((char)buffer.get());
    }
}

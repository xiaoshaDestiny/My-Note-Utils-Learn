package com.learn.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * @author xrb
 * @create 2020-04-07 20:34
 *
 * 一、通道 Channel
 * 用于源节点和目标节点的连接。
 * 在Java负责缓冲区数据的传输，Channel本身不存储数据，需要配合缓冲区进行数据传输。
 *
 * 二、通道的主要实现类
 *  java.nio.channels.Channel接口：
 *   | FileChannel          本地文件
 *   | SocketChannel        套接字 tcp 网络
 *   | ServerSocketChannel
 *   | DatagramChannel      UDP
 *
 * 三、获取通道
 * 1、getChannel()方法
 *    本地IO:
 *    FileInputStream/FileOutputStream
 *    RandomAccessFile 随机存取文件流
 *
 *    网络IO:
 *    Socket
 *    ServerSocket
 *    DatagramSocket
 *
 * 2、JDK1.7中的 NIO.2 针对各个静态方法提供的open()
 *
 * 3、JDK1.7中的 NIO.2 的Files工具类 newByteChannel()
 *
 *
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 *
 * 五、分散和聚集  Scatter 与 Gather
 * 分散读取 Scatter Reads ：将通道中的数据分散到多个缓冲区中
 * 聚集写入 Gather Writes ：将多个缓冲区的数据聚集到通道中
 *
 * 六、字符集 Charset
 * 编码：字符串->字节数组
 * 解码：字节数组->字符串
 *
 */
public class TestChannel {


    @Test
    public void test06() throws CharacterCodingException {
        Charset cs1 = Charset.forName("GBK");

        CharsetEncoder ce = cs1.newEncoder();
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("小傻！");
        buffer.flip();
        
        //编码
        ByteBuffer bBuffer = ce.encode(buffer);

        for (int i = 0; i < 6; i++) {
            System.out.println(bBuffer.get());
        }

        //解码
        bBuffer.flip();
        CharBuffer cBuffer = cd.decode(bBuffer);

        System.out.println(cBuffer.toString());
    }


    //查看有多少字符集
    @Test
    public void test05(){
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        set.forEach(System.out::println);
    }

    @Test
    public void test04() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt","rw");

        //1、获取通道
        FileChannel channel1 = raf1.getChannel();

        //2、分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3、分散读取
        ByteBuffer[] bufs = {buf1,buf2};
        channel1.read(bufs);

        for (ByteBuffer buf : bufs) {
            buf.flip();
        }

        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("-----------------------------");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));


        //4、聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt","rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
    }


    //3、通道之间的数据传输（直接缓冲区）
    @Test
    public void Test03() throws IOException {

        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("4.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

        inChannel.transferTo(0,inChannel.size(),outChannel);

        inChannel.close();
        outChannel.close();

    }

    //2、使用直接缓冲区完成文件的复制（内存映射文件）
    @Test
    public void test02() throws IOException {

        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

        //内存映射文件
        MappedByteBuffer inMappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据读写操作

        byte[] dst = new byte[inMappedBuffer.limit()];
        inMappedBuffer.get(dst);
        outMappedBuffer.put(dst);

        inChannel.close();
        outChannel.close();

    }

    
    //1、利用通道完成文件的复制 (非直接缓冲区)
    @Test
    public void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel foChannel = null;

        try {
            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jpg");

            //1、获取通道
            inChannel = fis.getChannel();
            foChannel = fos.getChannel();

            //传输数据
            //2、分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //3、将通道中的数据存入缓冲区中
            while(inChannel.read(buffer) != -1){
                //切换成读取数据的模式
                buffer.flip();

                //4、将缓冲区中的数据写入通道
                foChannel.write(buffer);

                //清空缓冲区
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
           if(foChannel != null){
               try {
                   foChannel.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           if(inChannel != null){
               try {
                   inChannel.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           if(fos != null){
               try {
                   fos.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           if(fis != null){
               try {
                   fis.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

        }
    }


}

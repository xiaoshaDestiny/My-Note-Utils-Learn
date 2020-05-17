# Netty
## 一、概述
### 1.1 Netty介绍
(1)Netty是一个异步的，基于事件驱动的网络应用框架。根据客户端的一些读写行为来产生相应的事件驱动。
用以快速开发高性能、高可靠性的网络IO模型。异步代表不用等待服务器的回复就可以去做别的事情，等有响应之后触发回调即可。  
(2)Netty主要针对的是TCP协议下，面向Clients端的高并发应用，或者Peer-to-Peer场景下的大量数据持续传输的应用。  
(3)Netty其实是一种NIO框架，适用于服务器通讯相关的多种应用场景。  

### Java的I/O模型
用什么样的通道进行数据的发送和接收，在很大程度上决定了程序通讯的性能。Java支持3种网络编程的IO模式：BIO,NIO,AIO   
(1)BIO:同步阻塞，一个连接一个线程。 适用于连接数目比较少并且固定的框架。  
(2)NIO:同步非阻塞，一个线程处理多个请求，客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有IO请求就进行处理。
适用于连接数目多，并且连接比较短的情况，聊天室、弹幕系统、服务器之间通讯等。    
(3)AIO:异步非阻塞，也叫NIO2，引入异步通道的概念，采样Proactor模式,有效的请求才启动线程。一般用于连接数较多，并且连接时间长的应用。比如一些充分调用操作系统参与并发操作等情况。    

### NIO(重点) java non-blocking IO
常用方法：    
capacity()方法，返回缓冲区容量  
put()方法，添加数据到缓冲区  
hasRemaining()方法，缓冲区是否还有数据  
get()方法，获取缓冲区中的数据，一个一个的读  
flip()    切换到读取数据模式  
rewind()  重复读取  
clear()   清空缓冲区  但是缓冲区中的数据还在 处于被遗忘状态  
mark() 标记当前position的位置  
reset() 恢复到mark位置  
transferFrom() 复制目标通道的数据到当前通道  



三大核心部分：管道Channel、缓冲区Buffer和Selector选择器。面向缓冲区的，数据读取到一个它稍后处理的缓冲区，需要时可以在缓冲区前后移动，增加了处理过程的灵活性。
每一个Channel都会对应着一个Buffer,Selector对应一个线程，一个线程对应多个Channel，程序切换到哪一个Channel是有事件决定的。Selector会根据不同的事件，在各个通道上切换。
Buffer是一个内存块，底层是一个数组，数据的写入是通过Buffer，需要通过flip去切换输入输出，Buffer是双向的。BIO则需要指定输入流或者是输出流。  

#### Buffer缓冲区
缓冲区的本质是一个可以读写数据的内存块，可以理解成是一个含有数组的容器对象，并且提供了一组方法，可以对缓冲区数据进行操作。  
根据数据类型的不同，提供了相应类型的缓冲区，boolean类型除外  
ByteBuffer\CharBuffer\ShortBuffer......通过allocate()获取缓冲区  

capacity 容量，缓冲区中最大存储数据的容量，创建缓冲区的时候被设定并且不可以改变   
limit    界限，缓冲区中可以操作数据的大小 limit后面的数据时不能进行读写的 
position 位置，缓冲区中正在操作数据的位置  
mark:    标记，表示记录当前position的位置，可以通过reset()恢复到mark位置  
大小关系： 0 <= mark <= position <= limit <= capacity  

#### Channel管道
通道可以同时进行读写操作，但是流只能读或者写。通道可以实现异步读写数据  
BIO的Stream流是单向的，NIO的Channel是双向的  
Channel是一个接口，主要的实现子类有FinalChannel 文件数据读写\DatagramChannel UDP数据读写\ServerSocketChannel和SocketChannel 用于TCP数据读写  
ByteBuffer支持类型化的put和get,但是put和get的类型要匹配   
可以将普通Buffer变成只读Buffer  
NIO还提供了MappedByteBuffer，可以让文件直接在内存中进行修改  
NIO还支持多个Buffer完成读写操作，Scattering 和 Gathering 分散聚合：  
Scattering：将数据写入到buffer的时候，可以采用Buffer数组[分散]   
Gathering：从buffer读取数据的时候，可以采用Buffer数组的方式[聚合]  

####  Selector选择器
(1) NIO运用的是非阻塞的IO方式，可以用一个线程处理多个客户端的连接，就会使用到selector选择器。  
(2) Selector能够检测到多个注册的通道上是否有事件发生，如果有，就湖区事件然后对事件进行处理，这样就可以只用一个单线程去管理多个通道。也就管理了多个连接和请求。  
(3) 只有在连接真正有读写事件发生的时候，才会进行读写，避免了开启多个线程和线程之间上下文的切换导致的开销。  
open() 得到一个选择器对象  
select() 监控所有注册的通道，当其中有IO操作可以进行的时候，将其中对应的SelectionKey加入到集合内部中来并返回
selectedKeys 从集合内部得到所有的selectionKey

当客户端连接的时候，会通过ServerSocketChannel得到SocketChannel  
将socketChannel注册到Selector上，一个Selector可以注册多个SocketChannel  
注册之后返回一个SelectionKey，会跟这个Selector关联  
Selector就会进行监听select方法，返回有事件发生的通道的个数，然后获取到各个有事件发生的SelectionKey  
这个SelectionKey就能得到了发生事件的Channel,通过Channel就能得到业务的处理  

#### 零拷贝
零拷贝指的是没有CPU参与的拷贝。零拷贝是网络编程的关键，跟多性能优化都离不开。  
Java程序中，主要的常用的零拷贝有内存映射(mmap)和sendFile。  
传统的IO要进行至少4次拷贝，3次系统状态的切换。很耗费性能。
mmap通过内存映射，将文件映射到内核缓冲区，同时用户空间可以共享内核空间的数据，这样，在进行网络传输的时候，就可以减少内核空间到用户控件的拷贝次数。  
在Linux2.1版本提供了sendFile函数，数据根本不需要进过用户态，直接从内核缓冲区进入到SocketBuffer，同时由于和用户态完全无关，就减少了一次上下文的切换。  
(1)零拷贝值，是从操作系统角度来说的，因为内核缓冲区之间，没有数据是重复的，只有kernel buffer有一份数据。
(2)零拷贝不仅仅带来更少的数据复制还能减少上下文切换，CPU缓存伪共享问题等。  
mmap适合少量的数据嘟咧，sendFile适合大文件传输。mmap需要进过4次上下文的切换，3次数据拷贝。
sendFile需要3次上下文切换，最少需要2次数据拷贝。
sendFile可以利用DMA方式，减少CPU拷贝，mmap则不能，必须从内核拷贝到Socket缓冲区。  

#### 原生NIO存在的问题








  












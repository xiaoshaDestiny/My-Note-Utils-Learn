# Java基础

## 一、集合框架

**Q:说说你知道的集合框架。**<br>
A: 在Collection接口下的List、Set和Queue。<br>
1、List接口：<br>
1.1、ArrayList：<br>
有序排列，可重复，底层是一个Object类型的数组，初始长度是10，长度不够扩容的时候会变成原来的1.5倍+1，线程不安全。<br>
在扩容的时候就会去进行复制数据，复制、移动的代价都比较高，适合用来做遍历，不适合做删除、插入。<br>
<br>
1.2、LinkedList：有序可重复，底层是双向链表，查询慢，增删块，线程不安全。<br>
用链表来做存储，很适合数的插入和删除，遍历和随机访问的速度都比较慢。它有专门对表头和表尾操作的方法，可以做双向队列、队列和栈来用。<br>
<br>
1.3、Vector:是线程安全的版本的ArrayList,在方法上都加上了synchronized。<br>

2、Set接口:<br>
2.1、HashSet:无序不可重复，底层其实是一个HashMap。<br>
存取数据按照元素的Hash值来计算。如果hash值一样，会接着比较equals方法，只有这两个都相同，才会视为同一个元素。<br>
<br>
2.2、TreeSet:使用二叉树实现的。<br>
每次新增一个元素，都会进行排序，将对象插入到二叉树的指定位置。Integer和String可以直接排序插入，其他类型的需要实现Comparable接口。<br>
<br>
2.3、LinkedHashSet：底层是一个LinkedHashMap。<br>

3、Queue也是Collection接口下的：下面的一些子类，有两端出入的List，用数组或者链表实现。<br>
<br>

4、Map接口下面：<br>
4.1、HashMap：使用的最多，键不可以重复，值可以重复，是一个Hash表，线程不安全，键值都可以是null。<br>
4.2、HashTable：是线程安全的，键值都不可以是null。<br>
4.3、TreeMap:是一个二叉树。键不可以重复，值可以重复。<br>
<br><br>




Q:ConcurrentHashMap能说一说吗？
A
   
   
   





  


## 二、

## 三、

## 四、

## 五、
package com.learn.queue;
/**
 * 环形队列实现
 * @author xiaosha
 *
 */
public class RoundQueue {
	
	private String [] queue;    //在队列里面存储String类型的数据
	private int size;			//数组的大小
	private int nltems;			//实际存储数据的数量
	private int font; 			//头的坐标
	private int rear;			//尾的坐标
	
	public RoundQueue(int maxSize) {
		this.size = maxSize;	
		queue = new String[size]; 	//初始化队列
		font = 0 ;					//初始化数据
		rear = -1;
		nltems = 0;
	}
	
	//遍历队列
	public void display() {
		if(isEmpty()) {
			System.out.println("队列为空");
			return ;
		}
		int item = font;
		for(int i=0;i<nltems;i++) {
			System.out.println(queue[item++ % size]);
		}
		System.out.println();
	}
	
	
	//插入一个数据到队列里面
	public void insert(String value) {
		if(isFull()) {
			System.out.println("队列已经满了");
			return ;
		}
		rear = ++rear % size;	 //找到当前尾指针的位置
		queue[rear] = value;
		nltems++;;
	}
	
	//取出队列的头一个，并且删除
	public String remove() {
		if(isEmpty()) {
			String s = "队列现在为空";
			return s;
		}
		nltems --;             //队列实际数据的数量减1
		font = font % size;    //队列的头
		return queue[font++];  //返回队列的头的数据
		
	}
	
	
	//取出来一个，不删除
	public String peek() {
		if(isEmpty()) {
			String s = "队列现在为空";
			return s;
		}
		//返回队列的头
		return queue[font];
	}
	
	//判满
	public boolean isFull() {
		return (nltems == size);
	}
	
	//判空
	public boolean isEmpty() {
		return (nltems == 0); 
	}
	
	//获取队列的长度
	public int size(){
		return nltems;
	}
	

}

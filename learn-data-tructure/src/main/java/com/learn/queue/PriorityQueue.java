package com.learn.queue;
/**
 * 优先级队列实现
 * @author xiaosha
 *
 */
public class PriorityQueue {
	private long[] queue;		//用long类型的数组来实现队列
	private int size;			//队列的长度
	private int nltems;			//队列中的元素的个数
	
	//初始化队列
	public PriorityQueue(int maxSize) {
		this.size = maxSize;
		nltems = 0;
		queue = new long[size];
	}
	
	//插入
	public void insert(long value) {
		if(isFull()) {
			System.out.println("队列已经满");
			return ;
		}
		int j;
		if(nltems == 0) { //队列为空 直接添加
			queue[nltems] = value;
			nltems++;
		}else {
			for(j=nltems-1;j>=0;j--) {
				if(value > queue[j]) {
					queue[j+1] = queue[j];
				}else {
					break;
				}
			}
			queue[j+1] = value;
			nltems++;
		}
	}
	
	//取出队列的头一个 并且删除它
	public long remove() {
		if(isEmpty()) {
			System.out.println("队列为空");
			return 0;
		}
		return queue[--nltems];
	}
	
	//取出队列的头一个 也就是最小的哪一个 不删除
	public long peekMin() {
		return queue[nltems-1];
	}
	
	//遍历这个队列
	public void display() {
		for(int i = nltems-1;i>=0;i--) {
			System.out.println(queue[i]);
		}
		System.out.println();
	}
	
	
	//判空
	public boolean isEmpty() {
		return (nltems == 0);
	}
	
	//判满
	public boolean isFull() {
		return (nltems==size);
	}
	
	//返回数据的长度
	public int size() {
		return size;
	}

}

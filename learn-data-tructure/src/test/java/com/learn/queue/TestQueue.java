package com.learn.queue;

import org.junit.Test;

/**
 * 队列
 */
public class TestQueue {
	
	/**
	 * 测试普通队列
	 */
	@Test
	public void testRoundQueue() {
		RoundQueue roundQueue = new RoundQueue(4);
		
		//向队列中插入数据
		roundQueue.insert("001");
		roundQueue.insert("002");
		roundQueue.insert("003");
		roundQueue.insert("004");
		roundQueue.insert("005");
		
		//输出队列的大小
		System.out.println("此时队列的大小是：" + roundQueue.size());
		System.out.println("==========================");
		System.out.println();
		
		//出队列 不删除  就相当于取队列的头一个
		System.out.println("取第一个数据出队列，不删除：" + roundQueue.peek());
		System.out.println("取第一个数据出队列，不删除：" + roundQueue.peek());
		System.out.println("取第一个数据出队列，不删除：" + roundQueue.peek());
		System.out.println("取第一个数据此时队列的大小是：" + roundQueue.size());
		System.out.println("==========================");
		System.out.println();
		
		//出队列，并且删除
		System.out.println("出队列"+roundQueue.remove());
		System.out.println("此时队列的大小是：" + roundQueue.size());
		System.out.println("出队列"+roundQueue.remove());
		System.out.println("此时队列的大小是：" + roundQueue.size());
		System.out.println("出队列"+roundQueue.remove());
		System.out.println("此时队列的大小是：" + roundQueue.size());
		System.out.println("==========================");
		System.out.println();
		
		//添加元素
		roundQueue.insert("005");
		roundQueue.insert("006");
		roundQueue.insert("007");
		System.out.println("插入数据完毕");
		System.out.println("==========================");
		System.out.println();

		
		//遍历队列
		System.out.println("遍历队列");
		roundQueue.display();
		System.out.println("==========================");
		System.out.println();
	}
	
	
	/**
	 * 测试优先级队列
	 */
	@Test
	public void testPriorityQueue() {
		PriorityQueue priorityQueue = new PriorityQueue(5);
		
		//向队列中插入数据
		priorityQueue.insert(45);
		priorityQueue.insert(25);
		priorityQueue.insert(35);
		priorityQueue.insert(15);
		priorityQueue.insert(75);
		priorityQueue.insert(55);
		
		//输出队列的大小
		System.out.println("此时队列的大小是：" + priorityQueue.size());
		System.out.println("==========================");
		System.out.println();
		
		//出队列 不删除  就相当于取队列的头一个
		System.out.println("取第一个数据出队列，不删除：" + priorityQueue.peekMin());
		System.out.println("取第一个数据出队列，不删除：" + priorityQueue.peekMin());
		System.out.println("取第一个数据出队列，不删除：" + priorityQueue.peekMin());
		System.out.println("取第一个数据此时队列的大小是：" + priorityQueue.size());
		System.out.println("==========================");
		System.out.println();
		
		//出队列，并且删除
		System.out.println("出队列"+priorityQueue.remove());
		System.out.println("此时队列的大小是：" + priorityQueue.size());
		System.out.println("出队列"+priorityQueue.remove());
		System.out.println("此时队列的大小是：" + priorityQueue.size());
		System.out.println("出队列"+priorityQueue.remove());
		System.out.println("此时队列的大小是：" + priorityQueue.size());
		System.out.println("==========================");
		System.out.println();
		
		//添加元素
		priorityQueue.insert(60);
		priorityQueue.insert(200);
		priorityQueue.insert(150);
		System.out.println("插入数据完毕");
		System.out.println("==========================");
		System.out.println();

		
		//遍历队列
		System.out.println("遍历队列");
		priorityQueue.display();
		System.out.println("==========================");
		System.out.println();
	}

}

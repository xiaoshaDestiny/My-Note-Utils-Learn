package com.learn.linkedlist;

import org.junit.Test;

/**
 * 链表测试类
 */
public class TestLinkList {
	
	@Test
	public void testLinkedList() {
		LinkedList link = new LinkedList();
		
		//添加头结点
		link.insertFirst(50);
		//添加元素
		link.insert(10);
		link.insert(12);
		link.insert(5);
		link.insert(9);
		link.insert(11);
		
		//输出链表长度
		System.out.println("此时链表的长度是：" + link.size());
		System.out.println("==========================");
		System.out.println();
		
		//查找结点
		System.out.println("查找链表元素：" + link.find(5).getData());
		System.out.println("查找链表元素：" + link.find(66));
		System.out.println("此时链表的长度是：" + link.size());
		System.out.println("==========================");
		System.out.println();
		
		//删除结点
		System.out.println("删除数据：" + link.delete(5).getData());
		System.out.println("删除数据：" + link.delete(9).getData());
		System.out.println("删除数据：" + link.delete(14));
		System.out.println("此时链表的长度是：" + link.size());
		System.out.println("==========================");
		System.out.println();
		
		//插入一些数据 然后遍历
		link.insert(5);
		link.insert(14);
		link.insert(8);
		link.insert(22);
		link.insert(19);
		System.out.println("插入数据");
		System.out.println("此时链表的长度是：" + link.size());
		System.out.println("==========================");
		System.out.println();
		
		link.display();
		System.out.println("遍历单链表");
		System.out.println("此时链表的长度是：" + link.size());
		System.out.println("==========================");
		System.out.println();
	}
	
	
	@Test
	public void testDoublyLinkedList() {
		DoublyLinkedList link2 = new DoublyLinkedList();
		
		//添加头结点
		link2.insertFirst(50);
		link2.insertFirst(40);
		link2.insertFirst(30);
//		System.out.println(link2.insertAfter(20, 10));
		
		//添加尾结点
		link2.insertLast(100);
		link2.insertLast(90);
		link2.insertLast(80);
		System.out.println("链表初始化...");
		System.out.println("==========================");
		System.out.println();
		
		
		//遍历此时的链表
		System.out.println("遍历此时的链表如下");
		link2.displayForward();
		System.out.println();
		link2.displaybackword();
		System.out.println();
		System.out.println("==========================");
		System.out.println();
		//输出链表长度
		System.out.println("此时链表的长度是：" + link2.size());
		System.out.println("==========================");
		System.out.println();
		
//		
//		//添加元素		//在指定的元素后面插入元素
		System.out.println("在30后面插入节点60,结果为"+link2.insertAfter(30, 60));
		System.out.println("在60后面插入节点60,结果为"+link2.insertAfter(60, 60));
		System.out.println("在100后面插入节点60,结果为"+link2.insertAfter(100, 60));
		System.out.println("在100后面插入节点60,结果为"+link2.insertAfter(100, 60));
		System.out.println("在10后面插入节点60,结果为"+link2.insertAfter(10, 60));
		System.out.println("插入节点元素结束");
		System.out.println("==========================");
		System.out.println();
		
		
		//遍历此时的链表
		System.out.println("遍历此时的链表如下");
		link2.displayForward();
		System.out.println();
		link2.displaybackword();
		System.out.println();
		System.out.println("==========================");
		System.out.println();	
		//输出链表长度
		System.out.println("此时链表的长度是：" + link2.size());
		System.out.println("==========================");
		System.out.println();
		
//		//删除结点
		System.out.println("删除数据" + link2.deleteNode(60).getData());
		//遍历此时的链表
		System.out.println("遍历此时的链表如下");
		link2.displayForward();
		System.out.println();
		link2.displaybackword();
		System.out.println();
		System.out.println("==========================");
		System.out.println();
		//输出链表长度
		System.out.println("此时链表的长度是：" + link2.size());
		System.out.println("==========================");
		System.out.println();
		
		
		System.out.println("删除数据" + link2.deleteNode(60).getData());
		System.out.println("删除数据" + link2.deleteNode(60).getData());
		System.out.println("此时链表的长度是：" + link2.size());
		//遍历此时的链表
		System.out.println("遍历此时的链表如下");
		link2.displayForward();
		System.out.println();
		link2.displaybackword();
		System.out.println();
		System.out.println("==========================");
		System.out.println();
		
	}

}

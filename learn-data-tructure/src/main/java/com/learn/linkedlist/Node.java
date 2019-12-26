package com.learn.linkedlist;


/**
 * 双向链表的数据结点类
 * @author xiaosha
 *
 */
public class Node {
	private long data;
	private Node next;		//后继
	private Node previous;	//前驱
	
	//初始化node
	public Node(long value) {
		data = value;
		next = null;
		previous = null;
	}

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
	//遍历本节点的时候输出的
	public void displayLink() {
		System.out.print("{"+data+"}"+" ");
	}
}

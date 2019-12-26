package com.learn.linkedlist;
/**
 * 单向链表数据结点
 * @author xiaosha
 *
 */
public class Link {
	private int data;
	private Link next;
	
	//构造器
	public Link(int data) {
		this.data = data;
		this.next = null;
	}
	
	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public void getLink() {
		System.out.println("{"+data+"}");
	}
	
	public Link getNext() {
		return next;
	}

	public void setNext(Link next) {
		this.next = next;
	}

}

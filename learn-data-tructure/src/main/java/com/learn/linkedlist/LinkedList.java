package com.learn.linkedlist;
/**
 * 单链表实现，只能从表头到表尾的序列
 * @author Administrator
 *
 */
public class LinkedList {
	private Link first;
	private int numElem; 		//链表中节点的数量
	
	//构造器初始化
	public LinkedList() {
		first = null;
		numElem = 0;
	}
	
	//添加头结点
	public void insertFirst(int value) {
		Link newLink = new Link(value);
		newLink.setNext(first);           //新的Link-->旧的first
		first = newLink;				  //first-->newLink
		numElem++;
	}
	
	//删除现在链表的头结点
	public Link deleteFirst() {
		if(isEmpty()) {
			System.out.println("链表为空");
			return null;
		}
		Link temp = first;
		first = first.getNext();
		numElem--;
		return temp;
	}
	
	//有序链表的插入
	public void insert(int value) {
		Link newLink = new Link(value);
		Link preious = null;
		Link current = first;
		while(current != null && value > current.getData()) {
			preious = current;
			current = current.getNext();
		}
		if(preious == null) {
			first = newLink;
		}else {
			preious.setNext(newLink);
		}
		newLink.setNext(current);
		numElem++;
	}
	
	//查找制定结点
	public Link find(int value) {
		Link current = first;
		while(current.getData() != value) {
			if(current.getNext() == null) {
				System.out.println("查询无果！");
				return null;
			}else {
				current = current.getNext();
			}
		}
		return current;
	}
	
	//删除指定结点
	public Link delete(int value) {
		Link current = first;
		Link previous = first;
		while(current.getData() != value) {
			if(current.getNext() == null) {
				System.out.println("要删除的结点不存在！");
				return null;
			}
			previous = current;
			current = current.getNext();
		}
		if(current == first ) {
			first = first.getNext();
		}else {
			previous.setNext(current.getNext());
		}
		numElem--;
		return current;
	}
	
	//遍历链表
	public void display() {
		if(isEmpty()) {
			System.out.println("链表为空，遍历失败");
			return ;
		}
		Link current = first;
		while(current != null) {
			current.getLink();
			current = current.getNext();
		}
		System.out.println();
	}
	
	
	//判空
	public boolean isEmpty() {
		return (first==null);
	}
	
	//返回链表长度
	public int size() {
		return numElem;
	}	
}

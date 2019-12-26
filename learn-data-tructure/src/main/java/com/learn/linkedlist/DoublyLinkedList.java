package com.learn.linkedlist;
/**
 * 双向链表实现
 * @author 小傻
 *
 */
public class DoublyLinkedList {
	private Node first;		//头节点
	private Node last;		//尾节点
	private int  size;		//链表的大小

	public DoublyLinkedList() {
		first = null;
		last = null;
		size = 0;
	}
	
	//工具方法 返回链表的大小
	public int size() {
		return size;
	}
	
	//工具方法 判空
	public boolean isEmpty() {
		return (first == null);
	}
	
	//插入头结点
	public void insertFirst(long value) {
		Node newLink = new Node(value);
		if(isEmpty()) {
			last = newLink;
		}else {
			first.setPrevious(newLink);
			newLink.setNext(first);
		}
		first = newLink;
		size++;
	}
	
	//插入尾结点
	public void insertLast(long value) {
		Node newLink = new Node(value);
		if(isEmpty()) {
			first = newLink;
		}else {
			last.setNext(newLink);
			newLink.setPrevious(last);
		}
		last = newLink;
		size++;
	}
	
	//删除头结点
	public Node deleteFirst() {
		if(first == null) {
			System.out.println("链表为空...");
			return null;
		}
		Node temp = first;
		if(first.getNext() == null) {
			last = null;
		}else {
			first.getNext().setPrevious(null);;
		}
		first = first.getNext();
		size --;
		return temp;
	}
	
	//删除尾结点
	public Node deleteLast() {
		if(last == null) {
			System.out.println("链表为空...");
			return null;
		}
		Node temp = last;
		if(last.getPrevious() == null) {//只有一个结点
			first = null;
		}else {
			last.getPrevious().setNext(null);
		}
		last = last.getPrevious();
		size--;
		return temp;
	}
	
	//在Key值后面插入值为value的新结点
	public boolean insertAfter(long key,long value) {
		Node current = first;
		while(current.getData() != key) {
			current = current.getNext();
			if(current == null) {
				System.out.println("不存在值为 "+value+" 的节点！");
				return false;
			}
		}
		if(current == last) {
			insertLast(value);
		}else {
			Node newLink = new Node(value);
			newLink.setNext(current.getNext());
			current.getNext().setPrevious(newLink);
			newLink.setPrevious(current);
			current.setNext(newLink);
			size++;
		}
		return true;
	}
	
	//删除指定Key值的节点
	public Node deleteNode(long key) {
		Node current = first;
		while(current.getData() != key) {
			current = current.getNext();
			if(current == null) {
				System.out.println("该节点不存在，不能执行删除操作！");
				return null;
			}
		}
		if(current == first) {
			deleteFirst();
		}else if(current == last) {
			deleteLast();
		}else {
			current.getPrevious().setNext(current.getNext());
			current.getNext().setPrevious(current.getPrevious());
			size--;
		}
		return current;
	}
	
	//从头到尾便利双向链表
	public void displayForward() {
		System.out.println("first-->last");
		Node current = first;
		while(current != null) {
			current.displayLink();
			current = current.getNext();
		}
	}
	
	//从尾到头遍历
	public void displaybackword() {
		System.out.println("last-->first");
		Node current = last;
		while(current != null) {
			current.displayLink();
			current = current.getPrevious();
		}
	}
}

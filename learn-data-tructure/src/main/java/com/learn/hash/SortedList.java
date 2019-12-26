package com.learn.hash;

/**
 * @author xiaosha
 * @create 2018-12-13 16:38
 */
public class SortedList {
    private LinkNode first;
    public SortedList() {
        first = null;
    }
    public boolean isEmpty() {
        return (first == null);
    }
    public void insert(LinkNode node) {
        int key = node.getKey();
        LinkNode previous = null;
        LinkNode current = first;
        while(current != null && current.getKey() < key) {
            previous = current;
            current = current.next;
        }
        if(previous == null) {
            first = node;
        }
        else {
            node.next = current;
            previous.next = node;
        }
    }
    public void delete(int key) {
        LinkNode previous = null;
        LinkNode current = first;
        if(isEmpty()) {
            System.out.println("chain is empty!");
            return;
        }
        while(current != null && current.getKey() != key) {
            previous = current;
            current = current.next;
        }
        if(previous == null) {
            first = first.next;
        }
        else {
            previous.next = current.next;
        }
    }
    public LinkNode find(int key) {
        LinkNode current = first;
        while(current != null && current.getKey() <= key) {
            if(current.getKey() == key) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    public void displayList() {
        System.out.print("List(First->Last):");
        LinkNode current = first;
        while(current != null) {
            current.displayLink();
            current = current.next;
        }
        System.out.println("");
    }
}
class LinkNode {
    private int iData;
    public LinkNode next;
    public LinkNode(int data) {
        iData = data;
    }
    public int getKey() {
        return iData;
    }
    public void displayLink() {
        System.out.print(iData + " ");
    }
}

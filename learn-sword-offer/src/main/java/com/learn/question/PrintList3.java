package com.learn.question;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author xu.rb
 * @since 2020-05-28 17:14
 *
 * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
 */


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

public class PrintList3 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        node1.next=node2;
        ListNode node3 = new ListNode(3);
        node2.next=node3;
        printListFromTailToHead(node1);
    }

    public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }

        ArrayList<Integer> list = new ArrayList<>();
        while (!stack.empty()){
            list.add(stack.pop());
        }

        System.out.println(list);
        return list;
    }
}

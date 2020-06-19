package com.learn.question;

import java.util.Stack;

/**
 * @author xu.rb
 * @since 2020-06-19 09:38
 */
public class MainTest {

    public static void main(String[] args) {
        Node node1 = new Node(2);
        Node node2 = new Node(4);
        Node node3 = new Node(3);
        node1.next = node2;
        node2.next = node3;

        Node node4 = new Node(5);
        Node node5 = new Node(6);
        Node node6 = new Node(4);
        node4.next = node5;
        node5.next = node6;

        Node node = addTwoNumbers(node1, node4);

        while (node.next != null){
            System.out.println(node.val);
            node = node.next;
        }
        System.out.println(node.val);

    }


    public static Node addTwoNumbers(Node l1, Node l2) {
        Node pre = new Node(0);
        Node cur = pre;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;
            cur.next = new Node(sum);

            cur = cur.next;
            if(l1 != null)
                l1 = l1.next;
            if(l2 != null)
                l2 = l2.next;
        }
        if(carry == 1) {
            cur.next = new Node(carry);
        }
        return pre.next;
    }

}


class Node {
    int val;
    Node next;
    Node(int x) { val = x; }
}



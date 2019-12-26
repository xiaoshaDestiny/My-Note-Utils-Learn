package com.learn.hash;
/**
 * @author xiaosha
 * @create 2018-12-13 16:37
 */

/**
 * 在开放地址法中，通过再哈希法寻找一个空位解决冲突问题，另一个方法是在哈希表每个单元中设置链表（即链地址法），
 * 某个数据项的关键字值还是像通常一样映射到哈希表的单元，
 * 而数据项本身插入到这个单元的链表中。其他同样映射到这个位置的数据项只需要加到链表中，不需要在原始的数组中寻找空位
 */
public class HashChain {
    private SortedList[] hashArray; //数组中存放链表
    private int arraySize;
    public HashChain(int size) {
        arraySize = size;
        hashArray = new SortedList[arraySize];
        //new出每个空链表初始化数组
        for(int i = 0; i < arraySize; i++) {
            hashArray[i] = new SortedList();
        }
    }
    public void displayTable() {
        for(int i = 0; i < arraySize; i++) {
            System.out.print(i + ": ");
            hashArray[i].displayList();
        }
    }
    public int hashFunction(int key) {
        return key % arraySize;
    }
    public void insert(int num) {
        LinkNode node = new LinkNode(num);
        int key = node.getKey();
        int hashVal = hashFunction(key);
        hashArray[hashVal].insert(node); //直接往链表中添加即可
    }
    public LinkNode delete(int key) {
        int hashVal = hashFunction(key);
        LinkNode temp = find(key);
        hashArray[hashVal].delete(key);//从链表中找到要删除的数据项，直接删除
        return temp;
    }

    public LinkNode find(int key) {
        int hashVal = hashFunction(key);
        LinkNode node = hashArray[hashVal].find(key);
        return node;
    }
}


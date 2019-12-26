package com.learn.hash;

/**
 * @author xiaosha
 * @create 2018-12-13 16:21
 */
public class HashTable {
    private DataItem[] hashArray; //DateItem类是数据项，封装数据信息
    private int arraySize;
    private int itemNum; //数组中目前存储了多少项
    private DataItem nonItem; //用于删除项的
    public HashTable() {
        arraySize = 13;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem(-1); //deleted item key is -1
    }
    public boolean isFull() {
        return (itemNum == arraySize);
    }
    public boolean isEmpty() {
        return (itemNum == 0);
    }
    public void displayTable() {
        System.out.print("Table:");
        for(int j = 0; j < arraySize; j++) {
            if(hashArray[j] != null) {
                System.out.print(hashArray[j].getKey() + " ");
            }
            else {
                System.out.print("** ");
            }
        }
        System.out.println("");
    }
    public int hashFunction(int key) {
        return key % arraySize;  	//hash function
    }

    public void insert(int num) {
        DataItem item = new DataItem(num);
        if(isFull()) {
            //扩展哈希表
            System.out.println("哈希表已满，重新哈希化..");
            extendHashTable();
        }
        int key = item.getKey();
        int hashVal = hashFunction(key);
        while(hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1) {
            ++hashVal;
            hashVal %= arraySize;
        }
        hashArray[hashVal] = item;
        itemNum++;
    }
    /*
     * 数组有固定的大小，而且不能扩展，所以扩展哈希表只能另外创建一个更大的数组，然后把旧数组中的数据插到新的数组中。
     * 但是哈希表是根据数组大小计算给定数据的位置的，所以这些数据项不能再放在新数组中和老数组相同的位置上，因此不能直接拷贝，
     * 需要按顺序遍历老数组，并使用insert方法向新数组中插入每个数据项。这叫重新哈希化。这是一个耗时的过程，
     * 但如果数组要进行扩展，这个过程是必须的。
     */
    public void extendHashTable() { //扩展哈希表
        int num = arraySize;
        itemNum = 0; //重新记数，因为下面要把原来的数据转移到新的扩张的数组中
        arraySize *= 2; //数组大小翻倍
        DataItem[] oldHashArray = hashArray;
        hashArray = new DataItem[arraySize];
        for(int i = 0; i < num; i++) {
            insert(oldHashArray[i].getKey());
        }
    }
    public DataItem delete(int key) {
        if(isEmpty()) {
            System.out.println("Hash table is empty!");
            return null;
        }
        int hashVal = hashFunction(key);
        while(hashArray[hashVal] != null) {
            if(hashArray[hashVal].getKey() == key) {
                DataItem temp = hashArray[hashVal];
                hashArray[hashVal] = nonItem; //nonItem表示空Item,其key为-1
                itemNum--;
                return temp;
            }
            ++hashVal;
            hashVal %= arraySize;
        }
        return null;
    }

    public DataItem find(int key) {
        int hashVal = hashFunction(key);
        while(hashArray[hashVal] != null) {
            if(hashArray[hashVal].getKey() == key) {
                return hashArray[hashVal];
            }
            ++hashVal;
            hashVal %= arraySize;
        }
        return null;
    }
}
class DataItem {
    private int iData;
    public DataItem (int data) {
        iData = data;
    }
    public int getKey() {
        return iData;
    }
}


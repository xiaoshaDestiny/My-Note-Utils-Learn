package com.learn.hash;

/**
 * @author xiaosha
 * @create 2018-12-13 16:31
 */

/**
 * 为了消除原始聚集和二次聚集，现在需要的一种方法是产生一种依赖关键字的探测序列，而不是每个关键字都一样。即：不同的关键字即使映射到相同的数组下标，
 * 也可以使用不同的探测序列。再哈希法就是把关键字用不同的哈希函数再做一遍哈希化，用这个结果作为步长，对于指定的关键字，步长在整个探测中是不变的，
 * 不同关键字使用不同的步长、经验说明，第二个哈希函数必须具备如下特点：
 *         1. 和第一个哈希函数不同；
 *         2. 不能输出0（否则没有步长，每次探索都是原地踏步，算法将进入死循环）。
 * 专家们已经发现下面形式的哈希函数工作的非常好：stepSize = constant - key % constant; 其中constant是质数，且小于数组容量。
 * 再哈希法要求表的容量是一个质数，假如表长度为15(0-14)，非质数，有一个特定关键字映射到0，步长为5，则探测序列是0,5,10,0,5,10,
 * 以此类推一直循环下去。算法只尝试这三个单元，所以不可能找到某些空白单元，最终算法导致崩溃。如果数组容量为13, 质数，探测序列最终会访问所有单元。
 * 即0,5,10,2,7,12,4,9,1,6,11,3,一直下去，只要表中有一个空位，就可以探测到它
 *
 */
public class HashDouble {
    private DataItem[] hashArray;
    private int arraySize;
    private int itemNum;
    private DataItem nonItem;
    public HashDouble() {
        arraySize = 13;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem(-1);
    }
    public void displayTable() {
        System.out.print("Table:");
        for(int i = 0; i < arraySize; i++) {
            if(hashArray[i] != null) {
                System.out.print(hashArray[i].getKey() + " ");
            }
            else {
                System.out.print("** ");
            }
        }
        System.out.println("");
    }
    public int hashFunction1(int key) { //first hash function
        return key % arraySize;
    }

    public int hashFunction2(int key) { //second hash function
        return 5 - key % 5;
    }

    public boolean isFull() {
        return (itemNum == arraySize);
    }
    public boolean isEmpty() {
        return (itemNum == 0);
    }
    public void insert(int num) {
        DataItem item = new DataItem(num);
        if(isFull()) {
            System.out.println("哈希表已满，重新哈希化..");
            extendHashTable();
        }
        int key = item.getKey();
        int hashVal = hashFunction1(key);
        int stepSize = hashFunction2(key); //用hashFunction2计算探测步数
        while(hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1) {
            hashVal += stepSize;
            hashVal %= arraySize; //以指定的步数向后探测
        }
        hashArray[hashVal] = item;
        itemNum++;
    }
    public void extendHashTable() {
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
        int hashVal = hashFunction1(key);
        int stepSize = hashFunction2(key);
        while(hashArray[hashVal] != null) {
            if(hashArray[hashVal].getKey() == key) {
                DataItem temp = hashArray[hashVal];
                hashArray[hashVal] = nonItem;
                itemNum--;
                return temp;
            }
            hashVal += stepSize;
            hashVal %= arraySize;
        }
        return null;
    }
    public DataItem find(int key) {
        int hashVal = hashFunction1(key);
        int stepSize = hashFunction2(key);
        while(hashArray[hashVal] != null) {
            if(hashArray[hashVal].getKey() == key) {
                return hashArray[hashVal];
            }
            hashVal += stepSize;
            hashVal %= arraySize;
        }
        return null;
    }
}

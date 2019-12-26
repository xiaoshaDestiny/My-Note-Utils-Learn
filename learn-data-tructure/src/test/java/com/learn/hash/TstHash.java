package com.learn.hash;

import org.junit.Test;

/**
 * @author xiaosha
 * @create 2018-12-13 16:23
 */
public class TstHash {

    @Test
    public void testHashTable(){
        HashTable hashTable = new HashTable();

        hashTable.insert(10);
        hashTable.insert(20);
        hashTable.insert(20);
        hashTable.insert(20);
        hashTable.insert(10);
        hashTable.insert(10);
        hashTable.displayTable();
        hashTable.delete(10);
        hashTable.delete(10);
        hashTable.delete(20);
        hashTable.delete(20);
        hashTable.displayTable();
    }

    @Test
    public void testHashDouble(){
        HashDouble hashDouble = new HashDouble();
        hashDouble.insert(10);
        hashDouble.insert(20);
        hashDouble.insert(20);
        hashDouble.insert(20);
        hashDouble.insert(10);
        hashDouble.insert(10);
        hashDouble.displayTable();
        hashDouble.delete(10);
        hashDouble.delete(10);
        hashDouble.delete(20);
        hashDouble.delete(20);
        hashDouble.displayTable();

    }

    @Test
    public void testHashChain(){
        HashChain hashChain = new HashChain(10);
        hashChain.insert(10);
        hashChain.insert(14);
        hashChain.insert(41);
        hashChain.insert(18);
        hashChain.insert(15);
        hashChain.insert(20);
        hashChain.displayTable();
        System.out.println("=============");
        hashChain.delete(10);
        hashChain.delete(20);
        hashChain.displayTable();

    }
}

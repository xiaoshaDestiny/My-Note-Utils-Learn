package com.learn.heap;

import org.junit.Test;

/**
 * @author xiaosha
 * @create 2018-12-14 15:24
 */
public class TestHeap {
    Heap heap = new Heap(10);

    private void inserAndPrint(int key){
        System.out.println();
        System.out.println("==>插入"+key+"后：");
        heap.insert(key);
        heap.displayHeap();
        System.out.println();
    }

    @Test
    public void testHeap01(){

        //测试插入数据
        inserAndPrint(10);
        inserAndPrint(17);
        inserAndPrint(2);
        inserAndPrint(22);
        inserAndPrint(7);
        inserAndPrint(11);
        inserAndPrint(1);


        //测试删除
        heap.remove();
        heap.displayHeap();

    }
}

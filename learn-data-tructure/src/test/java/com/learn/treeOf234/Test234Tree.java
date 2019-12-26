package com.learn.treeOf234;

import com.learn.tree0f234.Tree234;
import org.junit.Test;

/**
 * @author xiaosha
 * @create 2018-12-13 10:41
 */
public class Test234Tree {

    Tree234 tree = new Tree234();


    private void insertAndPrint(int key){
        System.out.println(" ==>插入数据"+key+"后，树的状态是");
        tree.insert(key);
        tree.displayTree();
        System.out.println();
    }
    private void findAndPrint(int key){
        if(tree.find(key) == null){
            return ;
        }
    }

    @Test
    public void test(){
        ////测试插入数据
        insertAndPrint(20);
        insertAndPrint(15);
        insertAndPrint(43);
        insertAndPrint(16);
        insertAndPrint(19);
        insertAndPrint(28);
        insertAndPrint(7);
        insertAndPrint(11);
        insertAndPrint(3);
        insertAndPrint(15);
        insertAndPrint(22);
        insertAndPrint(33);
        insertAndPrint(10);
        insertAndPrint(5);
    }



}



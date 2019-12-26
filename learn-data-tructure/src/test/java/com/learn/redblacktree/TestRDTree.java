package com.learn.redblacktree;

import org.junit.Test;

/**
 * @author xiaosha
 * @create 2018-12-10 11:02
 */
public class TestRDTree {

    RBTree<Integer> tree = new RBTree<Integer>();

    private void insertAndPrint(Integer key){
        tree.insert(key);
        System.out.println("\n=-> 插入数据"+key+"后，此时红黑树的信息:");
        tree.print();
    }

    private void removeAndPrint(Integer key){
        tree.remove(key);
        System.out.println("\n=-> 删除数据"+key+"后，此时红黑树的信息:");
        tree.print();
    }

    private void searchAndPrint(Integer key){
        System.out.print("\n ==->查询"+key+"的结果是：");
        RBTNode result = tree.search(key);
        System.out.println(result);
        if(tree.search(key) == null){
            System.out.println("'查询无果！'");
        }else{
            //RED == false    true == Black
            if(result.color == false){
                System.out.println(" 红色，左孩子是"+result.left+" 右孩子是"+result.right+" 父亲系节点是"+result.parent);
            }
            if(result.color == true){
                System.out.println(" 黑色，左孩子是"+result.left+" 右孩子是"+result.right+" 父亲系节点是"+result.parent);
            }
        }
    }


    @Test
    public void test01(){

        //测试插入数据：
        insertAndPrint(20);
        insertAndPrint(26);
        insertAndPrint(18);
        insertAndPrint(9);
        insertAndPrint(15);
        insertAndPrint(46);
        insertAndPrint(28);
        insertAndPrint(3);
        insertAndPrint(10);
        insertAndPrint(22);
        insertAndPrint(61);
        System.out.println("\n========================================================================\n\n");

        //测试遍历
        System.out.printf("\n=====---> 前序遍历: ");
        tree.preOrder();
        System.out.printf("\n=====---> 中序遍历: ");
        tree.inOrder();
        System.out.printf("\n=====---> 后序遍历: ");
        tree.postOrder();
        System.out.println("\n========================================================================\n\n");

        //寻找最大最小值
        System.out.printf("\n=====---> 最小值: %s\n", tree.minimum());
        System.out.printf("=====---> 最大值: %s\n", tree.maximum());
        System.out.println("========================================================================\n\n");


        //测试删除数据
        removeAndPrint(22);
        removeAndPrint(46);
        removeAndPrint(20);
        System.out.println("========================================================================\n\n");


        //测试查找
        searchAndPrint(22);
        searchAndPrint(18);
        searchAndPrint(9);
        searchAndPrint(10);
        searchAndPrint(28);

    }
}

package com.learn.binarytree;

import org.junit.Test;

/**
 * �������Ĳ�����
 * @author xiaosha
 *
 */
public class TestBinarytree {
	
	
	@Test
	public void Test01() {
		BinaryTree binaryTree = new BinaryTree();
		
		//���Զ������Ĳ�������
		binaryTree.insert(25, 1.0);
		binaryTree.insert(23, 2.0);
		binaryTree.insert(18, 3.0);
		binaryTree.insert(30, 4.0);
		binaryTree.insert(28, 5.0);
		binaryTree.insert(7, 6.0);
		binaryTree.insert(43, 7.0);
		binaryTree.insert(9, 8.0);
		binaryTree.insert(20, 9.0);
		binaryTree.insert(38, 10.0);
		binaryTree.insert(36, 11.0);
		binaryTree.insert(45, 12.0);
		System.out.println("�������ݽ���");
		System.out.println("==========================");
		System.out.println();
		
		//����ǰ�����
		binaryTree.trvaerse(1);
		System.out.println("==========================");
		System.out.println();
		
		//�����������
		binaryTree.trvaerse(2);
		System.out.println("==========================");
		System.out.println();
		
		//���Ժ������
		binaryTree.trvaerse(3);
		System.out.println("==========================");
		System.out.println();
		
		
		//������Сֵ  ���ֵ
		System.out.print("���ֵ�ǣ�" );
		binaryTree.maxNumber().displayNode();
		System.out.println();
		System.out.print("��Сֵ�ǣ�" );
		binaryTree.minNumber().displayNode();
		System.out.println("==========================");
		System.out.println();
	
		
		
		//���Բ�ѯ���
		System.out.println("���Բ�ѯ��㿪ʼ");
		//���Բ����ڵ����
		if(binaryTree.find(10) == null) {
			System.out.println("Ҫ��ѯ�Ľ�㲻���ڣ�...");
		}else {
			binaryTree.find(10).displayNode();
		}
		//���Դ��ڵ����
		if(binaryTree.find(9) == null) {
			System.out.println("Ҫ��ѯ�Ľ��9�����ڣ�...");
		}else {
			System.out.println("�ҵ��ý��10���£�");
			binaryTree.find(9).displayNode();
		}
		System.out.println("==========================");
		System.out.println();
		
		
		
		//����ɾ������
		//1:Ҷ�ӽ��                        ���ڶ�����״̬����  keyֵ�� 28 9  20 36 45 �Ľ����Ҷ�ӽ��
		//2:ֻ��һ���ӽڵ�                                                                   7 38 23
		//3:�������ӽڵ�                                                                     25 18 30 43
		
		//����1
//		System.out.println("ɾ��Ҷ�ӽ��...");
//		System.out.println(binaryTree.delete(9));
//		//����ǰ�����
//		binaryTree.trvaerse(1);
//		System.out.println("==========================");
//		//�����������
//		binaryTree.trvaerse(2);
//		System.out.println("==========================");
//		//���Ժ������
//		binaryTree.trvaerse(3);
//		System.out.println("==========================");
		
		//����2
//		System.out.println("ɾ��ֻ��һ���ӽڵ�Ľ��...");
//		System.out.println(binaryTree.delete(23));
//		//����ǰ�����
//		binaryTree.trvaerse(1);
//		System.out.println("==========================");
//		//�����������
//		binaryTree.trvaerse(2);
//		System.out.println("==========================");
//		//���Ժ������
//		binaryTree.trvaerse(3);
//		System.out.println("==========================");
		
		//����3
//		System.out.println("ɾ���������ӽڵ�Ľ��...");
//		System.out.println(binaryTree.delete(25));
//		//����ǰ�����
//		binaryTree.trvaerse(1);
//		System.out.println("==========================");
//		//�����������
//		binaryTree.trvaerse(2);
//		System.out.println("==========================");
//		//���Ժ������
//		binaryTree.trvaerse(3);
//		System.out.println("==========================");
		
		//����3
		System.out.println("ɾ���������ӽڵ�Ľ��...");
		System.out.println(binaryTree.delete(43));
		//����ǰ�����
		binaryTree.trvaerse(1);
		System.out.println("==========================");
		//�����������
		binaryTree.trvaerse(2);
		System.out.println("==========================");
		//���Ժ������
		binaryTree.trvaerse(3);
		System.out.println("==========================");
		
		
		
	}

}

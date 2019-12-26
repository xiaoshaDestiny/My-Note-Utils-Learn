package com.learn.binarytree;

/**
 * 
 * 二叉排序树实现类
 * @author xiaosha 
 * 在有序数组中可以快速的找到特定的某一个值，但是要插入一个新的数据就必须先找到那个新数据要插入的位置，
 * 然后将数据进行移动，挪出位置来给新数据插入，删除的时候也是一样，这样的数据挪到很浪费时间，所以有很多
 * 插入和删除的操作的时候，就不应该去选用有序数组。
 * 链表中可以快速的添加和删除某一个数据，但是在链表中查找到想要的数据并不太容易，必须从头开始访问链表的
 * 每一个数据项，直到找到了该数据项，过程也很浪费时间。
 *
 * 树这种数据结构，既能像链表那样快速的插入和删除，又能够像链表那样快速查找。 一种特殊的树 ---->
 * 二叉搜索树:一个节点的左节点关键字的值小于这个节点，右边节点的关键字值大于或者等于 这个节点，
 * 插入数据节点的时候需要根据这个规则进行插入。
 * 删除节点是二叉树中最难的操作： 找到要删除的节点，这个节点可能是：
 * 1、叶子节点 没有子节点
 * 	直接改变该节点的父节点对应字段的值就好，由指向该节点改为null,垃圾回收器会自动回收叶节点，不需要自己手动删除。
 * 2、只有一个子节点
 * 
 * 3、有两个子节点
 */
public class BinaryTree {

	private Node root; // 根节点

	public BinaryTree() { // 构造器，初始化耿洁典为Null
		root = null;
	}

	// 插入节点
	public void insert(int key, double value) {
		Node newNode = new Node();
		newNode.key = key;
		newNode.data = value;
		if (root == null) { // 此时的二叉树为空树
			root = newNode;
		} else {
			Node current = new Node();
			Node parent = new Node();
			current = root;
			while (true) {
				parent = current;
				if (key < current.key) { // 转向左边
					current = current.leftChild;
					if (current == null) {
						parent.leftChild = newNode;
						newNode.parent = parent;
						return;
					}
				} else { // 转向右边
					current = current.rightChild;
					if (current == null) {
						parent.rightChild = newNode;
						newNode.parent = parent;
						return;
					}
				}
			}
		}
	}

	// 遍历二叉树 分为前序 中序 后序
	public void trvaerse(int traverseType) {
		switch (traverseType) {
		case 1:
			System.out.println("前序遍历开始...");
			preOrder(root);
			System.out.println("前序遍历结束...");
			break;
		case 2:
			System.out.println("中序遍历开始...");
			inOrder(root);
			System.out.println("中序遍历结束...");
			break;
		case 3:
			System.out.println("后序遍历开始...");
			postOrder(root);
			System.out.println("后序遍历结束...");
			break;
		}
	}

	// 前序遍历二叉树 递归调用两次 先递归一次左边 再递归 一次右边
	private void preOrder(Node localRoot) {
		if (localRoot != null) {
			localRoot.displayNode();
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}

	// 中序遍历二叉树
	private void inOrder(Node localRoot) {
		if (localRoot != null) {
			inOrder(localRoot.leftChild);
			localRoot.displayNode();
			inOrder(localRoot.rightChild);
		}
	}

	// 后向遍历
	private void postOrder(Node localRoot) {
		if (localRoot != null) {
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			localRoot.displayNode();
		}
	}

	// 查找最小值
	// 根据二叉搜索树的存储规则，最小值应该是左边那个没有左子节点的那个节点
	public Node minNumber() {
		Node current = root;
		Node parent = root;
		while (current != null) {
			parent = current;
			current = current.leftChild;
		}
		return parent;
	}

	// 查找最大值
	// 根据二叉搜索树的存储规则，最大值应该是右边那个没有右子节点的那个节点
	public Node maxNumber() {
		Node current = root;
		Node parent = root;
		while (current != null) {
			parent = current;
			current = current.rightChild;
		}
		return parent;
	}

	// 给定key值查找结点 二叉搜索树查找的时间复杂度为O(logN)
	public Node find(int key) {
		Node current = root;
		while (current.key != key) {
			if (key < current.key) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
			if (current == null) {
				return null;
			}
		}
		return current;
	}
	
	

	//删除节点
	/*删除节点在二叉树中是最复杂的，主要有三种情况： 
	  1. 该节点没有子节点（简单）
	  2. 该节点有一个子节点（还行） 
	  3. 该节点有两个子节点（复杂）
	  删除节点的时间复杂度为O(logN)*/
	 
	public boolean delete(int key) {
		Node current = root;
		boolean isLeftChild = true;
		if (current == null){
			return false;
		}
		//寻找要删除的节点
		while (current.key != key){
			if (key < current.key){
				isLeftChild = true;
				current = current.leftChild;
			}
			else{
				isLeftChild = false;
				current = current.rightChild;
			}
			if (current == null){
				return false;
			}
		}
		//找到了要删除的节点，下面开始删除
		//1. 要删除的节点没有子节点,直接将其父节点的左子节点或者右子节点赋为null即可
		if (current.leftChild == null && current.rightChild == null){
			return deleteNoChild(current, isLeftChild);
		}
		//3. 要删除的节点有两个子节点
		else if (current.leftChild != null && current.rightChild != null){
			return deleteTwoChild(current, isLeftChild);
		}
		//2. 要删除的节点有一个子节点，直接将其砍断，将其子节点与其父节点连起来即可，要考虑特殊情况就是删除根节点，因为根节点没有父节点
		else{
			return deleteOneChild(current, isLeftChild);
		}
	}
	

	public boolean deleteNoChild(Node node, boolean isLeftChild){
		if(node == root){
			root = null;
			return true;
		}
		if(isLeftChild){
			node.parent.leftChild =null;
		}else{
			node.parent.rightChild =null;
		}
		return true;
	}

	public boolean deleteOneChild(Node node, boolean isLeftChild){
		if (node.leftChild ==null){
			if (node == root){
				root = node.rightChild;
				node.parent = null;
				return true;
			}
			if(isLeftChild){
				node.parent.leftChild = node.rightChild;
			}else{
				node.parent.rightChild = node.rightChild;
			}
			node.rightChild.parent = node.parent;
		}else{
			if(node == root){
				root = node.leftChild;
				node.parent = null;
				return true;
			}
			if(isLeftChild){
				node.parent.leftChild = node.leftChild;
			}else{
				node.parent.rightChild = node.leftChild;
			}
			node.leftChild.parent = node.parent;
		}
		return true;
	}

	public boolean deleteTwoChild(Node node, boolean isLeftChild) {
		Node successor = getSuccessor(node);
		if(node == root){
			successor.leftChild = root.leftChild;
			successor.rightChild = root.rightChild;
			successor.parent = null;
			root = successor;
		}else if(isLeftChild){
			node.parent.leftChild = successor;
		}else{
			node.parent.rightChild = successor;
		}
		successor.leftChild = node.leftChild;
		return true;
	}
	

	//获得要删除节点的后继节点（中序遍历的下一个节点）
	public Node getSuccessor(Node delNode) {
		Node successor = delNode;
		Node current = delNode.rightChild;
		while(current != null) {
			successor = current;
			current = current.leftChild;
		}
		if(successor != delNode.rightChild){
			successor.parent.leftChild = successor.rightChild;
			if (successor.rightChild != null){
				successor.rightChild.parent = successor.parent;		//删除后续节点在原来的位置
			}
			successor.rightChild = delNode.rightChild;				//将后续节点放到正确位置，与右边连上
		}
		return successor;
	}
	
}

/**
 * 二叉排序树节点类
 */
	class Node {
		public int key;
		public double data;
		public Node parent; // 父亲节点
		public Node leftChild; // 左孩子
		public Node rightChild; // 右孩子

		public void displayNode() {
			System.out.println("{" + key + ":" + data + "}  ");
		}
	}



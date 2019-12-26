package com.learn.stack;
/**
 * 堆栈
 * 用数组实现栈的基本操作
 * @author xiaosha
 *
 */
public class ArrayStack {
	private String[] stack; //用String类型去存放栈中的数据
	private int size;     //栈的大小
	private int top;	  //记录栈顶的位置
	
	
	//构造方法，传入的参数是栈的大小
	public ArrayStack(int maxSize) {
		this.size = maxSize;			//传递参数
		this.stack = new String[size];	//初始化栈 类型为String
		this.top = -1; 					//表示空栈
	}
	
	//入栈
	public void push(String value) {
		if(isFull()) {
			System.out.println("栈中已满");
			return; 		//要加上这一句 不然当栈满的时候就不会继续执行了   导致抛出异常
		}
		top = top +1;
		stack[top] = value;
	}
	
	//出栈 但是栈顶元素不删除
	public String peek() {
		if(isEmpty()) {
			String s = "栈中没有数据";
			return s;
		}
		return stack[top];
	}
	
	//出栈 栈顶数据也一起删除
	public String pop() {
		if(isEmpty()) {
			String s = "栈中没有数据";
			return s;
		}
		//返回之后 top 还要下移
		int t = top;
		top = top -1;
		return stack[t];
		//return stack[top--];    //操作等同于上面
	}
	
	//返回当前的栈有多大
	public int size() {
		return top + 1;
	}
	
	//输出当前栈的所有元素
	public void display() {
		//元素出栈设在最上面谁先输出
		for(int i = top;i>=0;i--) {
			System.out.println(stack[i]);
		}
	}
	
	//工具方法 判断当前栈是否为空
	public boolean isEmpty() {
		return (top == -1);		//当栈为空的时候栈顶的值是-1
	}
	
	//工具方法 判断当前栈是不是满了
	public boolean isFull() {
		return (top == size-1);  //当栈满的时候栈顶的值是size-1 
	}
}

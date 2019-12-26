package com.learn.stack;

import org.junit.Test;

/**
 * 堆栈测试
 */
public class TestStack {
	
	@Test
	public void testArrayStack() {
		ArrayStack arrayStack = new ArrayStack(5);
		
		//入栈
		arrayStack.push("001张三");
		arrayStack.push("002李四");
		arrayStack.push("003王五");
		arrayStack.push("004赵六");
		
		//输出栈的大小
		System.out.println("栈的大小是："+arrayStack.size());
		System.out.println("======================");
		System.out.println();
		
		//出栈顶 不删除
		System.out.println("此时的栈顶元素是："+arrayStack.peek());
		System.out.println("此时的栈顶元素是："+arrayStack.peek());
		System.out.println("栈的大小是："+arrayStack.size());
		System.out.println("======================");
		System.out.println();
		
		//出栈顶 删除
		System.out.println("此时的栈顶元素是："+arrayStack.pop());
		System.out.println("此时的栈顶元素是："+arrayStack.pop());
		System.out.println("此时的栈顶元素是："+arrayStack.pop());
		System.out.println("此时的栈顶元素是："+arrayStack.pop());
		System.out.println("此时的栈顶元素是："+arrayStack.pop());
		System.out.println("栈的大小是："+arrayStack.size());
		System.out.println("======================");
		System.out.println();
		
		//再测试入栈
		arrayStack.push("005小张");
		arrayStack.push("006小李");
		arrayStack.push("007小王");
		arrayStack.push("008小赵");
		arrayStack.push("009小杜");
		arrayStack.push("010小乔");
		System.out.println("栈的大小是："+arrayStack.size());
		
		//全部出栈
		System.out.println("元素全部出栈如下：");
		arrayStack.display();
		System.out.println("======================");
		System.out.println();

	}
	

}

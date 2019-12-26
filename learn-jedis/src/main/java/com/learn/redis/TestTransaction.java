package com.learn.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Redis事务
 * @author xiaosha
 *
 */
public class TestTransaction {

	public boolean transMethod() {
		Jedis jedis = new Jedis("192.168.1.180",6379);
		int balance;// 可用余额
		int debt;// 欠额
		int amtToSubtract = 10;// 实刷额度
		jedis.watch("balance");
		// jedis.set("balance","5");//此句不该出现，讲课方便。模拟其他程序已经修改了该条目

		//模拟高并发情况，在等待的时间里面，其他线程修改了这个balance的值，就返回false
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		balance = Integer.parseInt(jedis.get("balance"));
		if (balance < amtToSubtract) {
			jedis.unwatch();
			System.out.println("modify");
			return false;
		} else {
			System.out.println("***********transaction");
			Transaction transaction = jedis.multi();
			transaction.decrBy("balance", amtToSubtract);
			transaction.incrBy("debt", amtToSubtract);
			transaction.exec();
			balance = Integer.parseInt(jedis.get("balance"));
			debt = Integer.parseInt(jedis.get("debt"));
			System.out.println("*******" + balance);
			System.out.println("*******" + debt);
			return true;
		}
	}

	/**
	 * 通俗点讲，watch命令就是标记一个键，如果标记了一个键， 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况通常可以在程序中 重新再尝试一次。
	 * 首先标记了键balance，然后检查余额是否足够，不足就取消标记，并不做扣减； 足够的话，就启动事务进行更新操作，
	 * 如果在此期间键balance被其它人修改， 那在提交事务（执行exec）时就会报错， 程序中通常可以捕获这类错误再重新执行一次，直到成功。
	 */
	public static void main(String[] args) {
		TestTransaction test = new TestTransaction();
		boolean retValue = test.transMethod();
		System.out.println("main retValue-------: " + retValue);
	}
}



//日常事务API的使用
/*	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.180",6379);

		Transaction transaction = jedis.multi();	//开启事务
		transaction.set("k5", "v5");
		transaction.set("k6", "v6");

		//transaction.exec();						//提交事务
		transaction.discard();						//放弃事务

		//
		//jedis.watch(k4);

	}*/

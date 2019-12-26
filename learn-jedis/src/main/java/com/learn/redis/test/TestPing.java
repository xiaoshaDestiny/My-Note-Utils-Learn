package com.learn.redis.test;

import redis.clients.jedis.Jedis;

/**
 * 测试连接远程的redis数据库
 * @author xiaosha
 */
public class TestPing {

	public static void main(String[] args) {

		Jedis jedis = new Jedis("192.168.1.180",6379);
		System.out.println(jedis.ping());
	}

}

package com.learn.redis;

import redis.clients.jedis.Jedis;

/**
 *    主从复制
 * @author xiaosha
 */
public class TestMS {
	public static void main(String[] args) {

		Jedis jedis_M = new Jedis("192.168.1.180",6379);		//master
		Jedis jedis_S = new Jedis("192.168.1.180",6380);		//slaver

		jedis_S.slaveof("192.168.1.180",6379);					//从机跟随主机

		jedis_M.set("k1", "master/salve");						//主机写数据，从机取数据，读写分离
		String result = jedis_S.get("k1");
		System.out.println("k1 is :" + result);					//会出现null的情况，原因是内存数据库太快了
	}

}

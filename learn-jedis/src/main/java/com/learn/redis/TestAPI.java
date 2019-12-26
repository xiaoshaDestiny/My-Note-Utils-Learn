package com.learn.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * 5+1数据类型
 * @author xiaosha
 *
 */
public class TestAPI {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.180",6379);

		//1、key 键值
		jedis.set("k1", "v1");
		jedis.set("k2", "v2");
		jedis.set("k3", "v3");

		Set keys = jedis.keys("*");
		for(Iterator iterator = keys.iterator();iterator.hasNext();) {
			String key =(String) iterator.next();
			System.out.println(key);
		}

		System.out.println("Jedis.exists-->" + jedis.exists("k2"));
		System.out.println(jedis.ttl("k1"));


		//2、String字符串
		//string是redis最基本的类型，你可以理解成与Memcached一模一样的类型，一个key对应一个value。
		//string类型是二进制安全的。意思是redis的string可以包含任何数据。比如jpg图片或者序列化的对象 。
		//string类型是Redis最基本的数据类型，一个redis中字符串value最多可以是512M
		jedis.append("k1", "xiaosha");
		System.out.println(jedis.get("k1"));
		jedis.set("k4", "k4_redis");
		jedis.mset("str1","v1","str2","v2","str3","v3");
		System.out.println(jedis.mget("str1","str2","str3"));


		//3、list列表
		//Redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素导列表的头部（左边）或者尾部（右边）。它的底层实际是个链表
		jedis.lpush("mylist", "v1","v2","v3","v4");
		List<String> list = jedis.lrange("mylist", 0, -1);
		for(String element:list) {
			System.out.println(element);
		}


		//4、Set集合
		//Redis的Set是string类型的无序集合。它是通过HashTable实现实现的。
		jedis.sadd("orders", "jd001");
		jedis.sadd("orders", "jd002");
		jedis.sadd("orders", "jd003");

		Set set1 = jedis.smembers("orders");
		for (Iterator iterator = set1.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
		jedis.srem("orders", "jd002");
		System.out.println(jedis.smembers("orders").size());


		//5、Hsah
		//Redis hash 是一个键值对集合。Redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象。类似Java里面的Map
		jedis.hset("hash1","userName","lisi");
		System.out.println(jedis.hget("hash1","userName"));
		Map map = new HashMap();
		map.put("telphone","13811814763");
		map.put("address","atguigu");
		map.put("email","abc@163.com");
		jedis.hmset("hash2",map);
		List<String> result = jedis.hmget("hash2", "telphone","email");
		for (String element : result) {
			System.out.println(element);
		}


		//5、zset(sorted set：有序集合)
		//Redis zset 和 set 一样也是string类型元素的集合,且不允许重复的成员。不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。zset的成员是唯一的,但分数(score)却可以重复。

		jedis.zadd("zset01",60d,"v1");
		jedis.zadd("zset01",70d,"v2");
		jedis.zadd("zset01",80d,"v3");
		jedis.zadd("zset01",90d,"v4");
		Set s1 = jedis.zrange("zset01",0,-1);
		for (Iterator iterator = s1.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
	}

}
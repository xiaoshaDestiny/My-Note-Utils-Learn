package com.learn;

import com.alibaba.fastjson.JSON;
import com.learn.eunms.CountryEnum;
import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * @author xu.rb
 * @since 2020-12-20 18:14
 */
public class BuilderTest {

    @Test
    public void testPersonBuilder(){
        Person person = new Person.PersonBuilder().age(10).name("张三").country(CountryEnum.CHINA).build();
        Person person1 = new Person.PersonBuilder().age(10).name("张三").country(100).build();
        Person person2 = new Person.PersonBuilder().age(10).name("张三").country("中国").build();

        Assert.assertEquals(JSON.toJSONString(person),JSON.toJSONString(person1));
        Assert.assertEquals(JSON.toJSONString(person1),JSON.toJSONString(person2));
    }

    @Test
    public void testCatBuilder(){
        CatInfo yellowCat = CatInfo.builder().age(2).color("yellow").countryEnum(CountryEnum.American).build();
        Assert.assertEquals(yellowCat.getAge().intValue(),2);
    }
}
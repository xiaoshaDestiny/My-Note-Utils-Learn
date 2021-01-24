package com.learn;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author xu.rb
 * @since 2020-12-31 00:00
 */
public class GuavaTest {

    @Test
    public void testGuava(){
        List<String> strings = Lists.newArrayList("");
        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();


    }

    @Test
    public void testStringIntern(){
        String str1 = new StringBuilder("he").append("llo").toString();
        System.out.println(str1.intern() == str1);
        //hello这个字符串，在字符串的常量池中没有，添加字符串进常量池，返回引用，和上面创建的一致,结果为true

        String str2 = new StringBuilder("ja").append(" va").toString();
        System.out.println(str1.intern() == str2);
        //java这个字符串，在常量池中已经存在，不是第一次遇到，返回已经有的“java”的引用，和本次创建在java堆里面的引用不一致，结果为false
    }
}

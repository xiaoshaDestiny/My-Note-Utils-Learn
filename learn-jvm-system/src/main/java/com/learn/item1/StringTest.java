package com.learn.item1;

/**
 * @author xrb
 * @create 2020-01-22 22:22
 */
public class StringTest {
    public static void print(StringBuffer sb){
        System.out.println(sb);

    }
    public static void main(String[] args) {
        synchronized (new Object()){
            StringBuffer sb = new StringBuffer();
            sb.append("1");
            sb.append("2");
            sb.append("3");
        }
    }
}

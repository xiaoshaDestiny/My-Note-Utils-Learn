package com.learn.item1;

/**
 * @author xrb
 * @create 2020-01-22 22:22
 */
public class StringTest {
    private static StringBuffer sb = new StringBuffer();

    public static void print(StringBuffer sb){
        sb.append("***");
        System.out.println(sb.toString());
    }
    public static void main(String[] args) {

        synchronized (sb){
            sb.append("a");
            print(sb);
            sb.append("b");
            print(sb);
            sb.append("c");
            print(sb);
        }
    }
}

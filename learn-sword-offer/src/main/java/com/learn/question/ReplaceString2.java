package com.learn.question;

/**
 * @author xu.rb
 * @since 2020-05-28 17:28
 *
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 */
public class ReplaceString2 {
    public static void main(String[] args) {
        String s = replaceSpace(new StringBuffer("aaaa aa b  c"));
        System.out.println(s);
    }

    public static String replaceSpace(StringBuffer str) {
        char[] chars = str.toString().toCharArray();
        String result = "";

        for (int i = 0; i < chars.length; i++) {
            char c = ' ';
            if(c == chars[i]){
                result = result + "%20";
            }else{
                result = result + chars[i];
            }
        }
        return result;
    }
}

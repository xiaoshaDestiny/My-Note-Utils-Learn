package com.test.huawei;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2020-06-11 10:37
 */
public class Store {

    public void test04() {
        //字符串的最长数字子串
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();

            /*String str ="qqxt24aq0s22agqt4879g7f5y6ei0k85lstang77r5ll1fz3dpxx8xkqcbm2zbcw7xvan53n986sp8h110843b110843";*/

            Pattern p = Pattern.compile("[a-zA-z]");
            Matcher matcher = p.matcher(str);

            String result = matcher.replaceAll(",");
            String[] split = result.split(",");
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                if (null != split[i] && !"".equals(split[i])) {
                    list.add(split[i]);
                }
            }

            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                StringBuilder sb = new StringBuilder(s);
                sb = sb.append(i + 1000);
                map.put(sb.toString(), sb.length());
            }

            int max = 0;
            for (String s : map.keySet()) {
                if (s.length() > max) {
                    max = s.length();
                }
            }

            String res = "";
            boolean firstFind = false;
            for (String s : map.keySet()) {
                if (s.length() == max) {
                    res = s.substring(0, s.length() - 4);
                    if (firstFind == false) {
                        System.out.print(res);
                        firstFind = true;
                    } else {
                        System.out.print("," + res);
                    }
                }
            }
            System.out.println("," + (max - 4));
        }
    }

    /**
     * 编写一个程序，将输入字符串中的字符按如下规则排序。
     * 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。
     * 如，输入： Type 输出： epTy
     * 规则 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。
     * 如，输入： BabA 输出： aABb
     * 规则 3 ：非英文字母的其它字符保持原来的位置。

     * 如，输入： By?e 输出： Be?y
     */
    public void test03(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            Pattern p = Pattern.compile("[^a-zA-Z]");

            String s1 = p.matcher(s).replaceAll("0");
            char[] cha1 = s1.toCharArray();

            char[] cha = s.toCharArray();
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i < 26;i++){
                char c = (char)(i + 'A');
                for(int j = 0;j < cha.length;j++){
                    if(cha[j] == c || cha[j] == c + 32){
                        sb.append(cha[j]);
                    }
                }
            }

            String res = sb.toString();

            for (int i = 0; i < cha1.length; i++) {
                StringBuilder sbRes = new StringBuilder();
                if(cha1[i] == '0'){
                    sbRes.append(res.substring(0,i));
                    sbRes.append(cha[i]);
                    sbRes.append(res.substring(i,res.length()));
                    res = sbRes.toString();
                }
            }
            System.out.println(res);
        }
    }


    /**
     * 将一个字符中所有出现的数字前后加上符号“*”，其他字符保持不变
     * 注意：输入数据可能有多行
     */
    public void test02(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();

            Pattern pattern = Pattern.compile("[0-9]");
            Matcher matcher = pattern.matcher(s);
            String s1 = matcher.replaceAll("0");

            char[] chs = s1.toCharArray();
            char[] chsRes = s.toCharArray();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chs.length; i++) {
                if(chs[i] != '0'){
                    sb.append(chsRes[i]);
                }else {
                    sb.append("*"+chsRes[i]+"*");
                }
            }
            System.out.println(sb.toString().replace("**", ""));
        }
    }

    /**
     * 连续输入字符串(输出次数为N,字符串长度小于100)，请按长度为8拆分每个字符串后输出到新的字符串数组，
     *
     * 长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。
     *
     * 首先输入一个整数，为要输入的字符串个数。
     *
     * 例如：
     *
     * 输入：2
     *
     * abc
     *
     * 12345789
     *
     * 输出：abc00000
     *
     * 12345678
     *
     * 90000000
     */
    @Test
    public void test01(){
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()){
            int length = scanner.nextInt();

            List<String> list = new ArrayList<>();
            for (int i = 0; i < length+1 ; i++) {
                list.add(scanner.nextLine());
            }

            List<String> res = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if(null == list.get(i) || "".equals(list.get(i))){
                    continue;
                }
                String str = list.get(i);
                for (int j = 0; j < (str.length()/8)+1; j++) {
                    if(str.length() >= (j+1)*8 ){
                        res.add(str.substring(j*8,(j+1)*8));
                    }else {
                        String s = str.substring(j * 8, str.length());
                        if(s.length()!=0){
                            StringBuilder sb = new StringBuilder(s);
                            for (int k = 0; k <8-s.length(); k++) {
                                sb.append("0");
                            }
                            res.add(sb.toString());
                        }
                    }
                }
            }
            res.forEach(System.out::println);
        }

    }
}

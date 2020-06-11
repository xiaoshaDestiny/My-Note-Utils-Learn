package com.test;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Train {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String[] split = scanner.nextLine().split(" ");
            Integer m = new Integer(split[0]);
            Integer n = new Integer(split[1]);
            System.out.println(f(m,n));
        }
    }

    public static int f(int m,int n){
        if (m==0||n==1){
            return  1;
        }
        if (n>m){
            return f(m,m);
        } else {
            return f(m, n-1) + f(m-n, n);
        }
    }

    public static void test03(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] split = str.split(" ");

        int dnum = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            int num = Integer.parseInt(split[i]);
            if(num >= 0){
                list.add(num);
            }else {
                dnum++;
            }
        }
        System.out.println(dnum);
        if(list.size() == 0){
            System.out.println(0.0);
        }else {
            int total = 0;
            for (int i = 0; i < list.size(); i++) {
                total = total + list.get(i);
            }
            Double v = (double) total / list.size();
            BigDecimal bigDecimal = new BigDecimal(v);
            System.out.println(bigDecimal.setScale(1, BigDecimal.ROUND_DOWN));
        }
    }


    public static void test02(){
        Scanner sc = new Scanner(System.in);
        Double input = sc.nextDouble();
        DecimalFormat df=new DecimalFormat("0.0");
        System.out.println(df.format(Math.pow(input, 1.0/3.0)));
    }


    public static int getNum(int a, int b){
        int size = a * b;
        int start = a;
        if(a > b){
            start = b;
        }
        int result = size;
        for (int i = start; i < size; i++) {
            if(i%a == 0 && i%b == 0){
                result = i;
            }
        }
        return result;
    }
}

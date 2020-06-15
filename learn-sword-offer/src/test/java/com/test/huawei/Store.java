package com.test.huawei;

import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author xu.rb
 * @since 2020-06-11 10:37
 */
public class Store {

    /**
     *
     * 题目三：
     *
     * 先输入一个行数
     * 比如说是2
     * 再输入两个字符串(用逗号隔开)
     * 4,5,4
     * 5,4,5
     * 这就形成一个二维数组
     * [4 5 4]
     * [5 4 5]
     * 然后输入操作的步骤数目
     * 比如说是2，每一次输入标志了上面二维数组里面的两个数字
     * 0,0,0,2   00代表了第1行第一列的4   02和第一行第三列的4  (如果这两个数字能用三根短线连起来，并且相等，那么就把这两个位置上的数字变成0)
     * 1,0,0,1  同上(10是5  01也是5)
     *
     * 如果输入的多组操作，都能正常完成数字的消除，就返回0k
     * 如果有一组操作存在错误，就返回这个操作的下标。分析错误的原因：
     * （操作里面指明的位置在二维数组里面没有,下标越界，两个下标的值不相等，或者就是不能用三个短线把这两个数连起来）
     *
     */
    public void test17(){
        Scanner scanner = new Scanner(System.in);

        //行数
        Integer lines = new Integer(scanner.nextLine());
        String[] split = scanner.nextLine().split(",");
        Integer lie = split.length;
        int[] arr0 = new int[lie];
        for (int i = 0; i < split.length; i++) {
            arr0[i] = new Integer(split[i]);
        }
        int[][] arr = new int[lines][lie];
        arr[0] = arr0;

        for (int i = 0; i < lines-1; i++) {
            String[] sp = scanner.nextLine().split(",");
            int[] arri = new int[lie];
            for (int j = 0; j < sp.length; j++) {
                arri[j] = new Integer(sp[j]);
            }
            arr[i+1] = arri;
        }

        Integer stepNum = new Integer(scanner.nextLine());
        int[][] steps = new int[stepNum][4];
        for (int i = 0; i < stepNum; i++) {
            String[] sp = scanner.nextLine().split(",");
            int[] arri = new int[4];
            for (int j = 0; j < sp.length; j++) {
                arri[j] = new Integer(sp[j]);
            }
            steps[i] = arri;
        }

        //如果是同行，只校验相等和跨界

        boolean flag = false;
        for (int i = 0; i <steps.length ; i++) {
            int numALine = steps[i][0];
            int numBLine = steps[i][2];
            int numAClu = steps[i][1];
            int numBClu = steps[i][3];

            //行越界
            if(numALine >= arr.length || numALine < 0 || numBLine >=arr.length || numBLine < 0){
                System.out.println(i);
                break;
            }

            //列越界
            if(numAClu >= arr[1].length || numBClu < 0 || numAClu >= arr[1].length || numBClu < 0){
                System.out.println(i);
                break;
            }

            int numA = arr[numALine] [numAClu];
            int numB = arr[numBLine] [numBClu];
            //值不等
            if(numA != numB){
                System.out.println(i);
                break;
            }
            //校验完毕


            //如果同行或者同列 指定是ok的  消除
            if(numALine == numBLine){
                arr[numALine] [numAClu] = 0;
                arr[numBLine] [numBClu] = 0;
                continue;
            }

            if(numAClu == numBClu){
                arr[numALine] [numAClu] = 0;
                arr[numBLine] [numBClu] = 0;
                continue;
            }

            //不同行不同列
            //A和B  左边到右边之间应该有0存在
            int start = numAClu;
            int end = numBClu;
            if(numAClu > numBClu){
                numBClu = start;
                numAClu = end;
            }
            if(check(numALine,numAClu,arr) == 1 || check(numBLine,numBClu,arr) == 1){
                System.out.println(i);
                break;
            }

            flag = true;
        }
        if(flag == true){
            System.out.println("ok");
        }
    }
    public static int check(int line,int clu,int arr[][]){

        //上
        if(line-1 >= 0){
            if(arr[line+1][clu] == 0)
                return 0;
        }
        //下
        if(line+1 < arr.length){
            if(arr[line+1][clu] == 0)
                return 0;
        }

        //左
        if(clu-1 >= 0){
            if(arr[line][clu] == 0)
                return 0;
        }

        //左
        if(clu+1 < arr.length){
            if(arr[line][clu] == 0)
                return 0;
        }
        return 1;
    }


    /**
     * 输入一个字符串 和一个标志
     * 例子：  aA1bB2cC3 1
     * 假如这个标志是 1  就把字符串中小写字母反转输出  cA1bB2aC3
     * 假如这个标志是 2  就把字符串中大写写字母反转输出  aC1bB2cA3
     * 假如这个标志是 3  就把字符串中数字的位置反转输出  aA3bB2aC1
     */
    public void test16(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.nextLine();

            String[] split = s.split(" ");
            String  str = new String(split[0]);
            Integer flag = new Integer(split[1]);

            List<Integer> list = new ArrayList<>();
            char[] res = str.toCharArray();


            //1 小写
            if(flag == 1){
                String a1 = Pattern.compile("[a-z]").matcher(str).replaceAll("a");
                char[] cha1 = a1.toCharArray();
                for (int i = 0; i < cha1.length; i++) {
                    if(cha1[i] == 'a'){
                        list.add(i);
                    }
                }
            }
            //2 大写
            if(flag == 2){
                String a1 = Pattern.compile("[A-Z]").matcher(str).replaceAll("A");
                char[] cha1 = a1.toCharArray();
                for (int i = 0; i < cha1.length; i++) {
                    if(cha1[i] == 'A'){
                        list.add(i);
                    }
                }
            }
            //3 数字
            if(flag == 3){
                String a1 = Pattern.compile("[0-9]").matcher(str).replaceAll("1");
                char[] cha1 = a1.toCharArray();
                for (int i = 0; i < cha1.length; i++) {
                    if(cha1[i] == '1'){
                        list.add(i);
                    }
                }
            }
            char temp = '#';
            int size = list.size();
            for (int i = 0; i < size/2 ; i++) {
                temp = res[list.get(i)];
                res[list.get(i)] = res[list.get(size-1-i)];
                res[list.get(size-1-i)] = temp;
            }
            System.out.println(new String(res));
        }
    }




    /**
     * 题目一：
     *
     * 1、输入小明的身高和他们班其他小朋友的个数
     * 比如 100 5
     * 2、输入这5个小朋友的身高 中间用空格隔开（身高的范围是0-200）
     * 98 99 100 101 102
     *
     * 输出：
     * 按照跟小明的身高差去排序，身高差相同，个子小的排在前面
     * 100 99 101 98 102
     */
    public void test15(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){

            String[] split1 = scanner.nextLine().split(" ");
            //小明的身高
            Integer height = new Integer(split1[0]);
            //小朋友的个数
            Integer num = new Integer(split1[1]);
            String str = scanner.nextLine();
            //各个小朋友的身高
            String[] split = str.split(" ");

            List<Integer> list = new ArrayList<>();
            for (int i = 0; i <split.length ; i++) {
                list.add(height - new Integer(split[i]));
            }

            List<Integer> res = new ArrayList<>();
            for (int i = 0; i <= 200; i++) {
                for (int j = 0; j < list.size(); j++) {
                    if(list.get(j) == i){
                        res.add(new Integer(split[j]));
                    }
                }

                for (int j = 0; j < list.size(); j++) {
                    if(i != 0 && list.get(j) == -i){
                        res.add(new Integer(split[j]));
                    }
                }
            }
            for (int i = 0; i < res.size() ; i++) {
                System.out.print(res.get(i) + " ");
            }
        }
    }





    /**
     * 实现删除字符串中出现次数最少的字符，若多个字符出现次数一样，则都删除。输出删除这些单词后的字符串，字符串中其它字符保持原来的顺序。
     * 注意每个输入文件有多组输入，即多个字符串用回车隔开
     * 输入描述:
     * 字符串只包含小写英文字母, 不考虑非法输入，输入的字符串长度小于等于20个字节。
     *
     * 输出描述:
     * 删除字符串中出现次数最少的字符后的字符串。
     *
     * 输入
     * abcdd
     *
     * 输出
     * dd
     */
    public void test14(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            char[] lines = line.toCharArray();

            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i <lines.length ; i++) {
                int cal = 0;
                for (int j = 0; j <lines.length; j++) {
                    if(lines[i] == lines[j]){
                        cal ++;
                    }
                }
                map.put(lines[i],cal);
            }
            Set<Character> set = map.keySet();

            int min = 20;
            for (Character ch :set) {
                if(map.get(ch) < min){
                    min = map.get(ch);
                }
            }

            List<Character> list = new ArrayList<>();
            for (Character ch :set) {
                if(map.get(ch) == min){
                    list.add(ch);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                line = line.replaceAll(list.get(i).toString(), "");
            }
            System.out.println(line);
        }
    }


    public void test13(){

        /*class PrintString{
            private int num = 1;

            public synchronized void printAString(){
                while (num%4 != 1){
                    try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                num++;
                System.out.print("A");
                notifyAll();
            }

            public synchronized void printBString(){
                while (num%4 != 2){
                    try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                num++;
                System.out.print("B");
                notifyAll();
            }

            public synchronized void printCString(){
                while (num%4 != 3){
                    try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                num++;
                System.out.print("C");
                notifyAll();
            }

            public synchronized void printDString(){
                while (num%4 != 0){
                    try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                num++;
                System.out.print("D");
                notifyAll();
            }
        }


        public class Main {
            public static void main(String[] args) {

                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()){
                    int num = new Integer(scanner.nextLine());

                    PrintString p = new PrintString();
                    new Thread(()->{
                        for (int i = 0; i < num; i++) {
                            p.printAString();
                        }
                    }).start();

                    new Thread(()->{
                        for (int i = 0; i < num; i++) {
                            p.printBString();
                        }
                    }).start();

                    new Thread(()->{
                        for (int i = 0; i < num; i++) {
                            p.printCString();
                        }
                    }).start();

                    new Thread(()->{
                        for (int i = 0; i < num; i++) {
                            p.printDString();
                        }
                        System.out.println();
                    }).start();
                }

            }
        }
*/
    }



    @Test
    public void test12(){

        System.out.println(1%4);
        System.out.println(2%4);
        System.out.println(3%4);
        System.out.println(4%4);
        System.out.println(5%4);


    }


    /**
     * 查找和排序
     *
     * 题目：输入任意（用户，成绩）序列，可以获得成绩从高到低或从低到高的排列,相同成绩
     * 都按先录入排列在前的规则处理。
     * 3
     * 0
     * fang 90
     * yang 50
     * ning 70
     *

     * fang 90
     * ning 70
     * yang 50
     *
     */
    public void test11(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int num = new Integer(scanner.nextLine());
            int sort = new Integer(scanner.nextLine());
            List<HashMap<String, Object>> maps = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                String s = scanner.nextLine();
                String[] split = s.split(" ");
                HashMap<String, Object> map = new HashMap<>();
                map.put("name",new String(split[0]));
                map.put("cj",new Integer(split[1]));
                maps.add(map);
            }
            //maps.stream().forEach(System.out::println);

            List<HashMap<String, Object>> result = maps.stream().sorted((x, y) -> {
                if (sort != 0) {
                    return ((int) x.get("cj") >= (int) y.get("cj")) ? 1 : -1;
                }else {
                    return ((int) x.get("cj") >= (int) y.get("cj")) ? -1 : 1;
                }
            }).collect(Collectors.toList());

            for (int i = 0; i <result.size() ; i++) {
                System.out.println(result.get(i).get("name")+" "+result.get(i).get("cj"));
            }

        }
    }


    /**
      8
      1 2 3 4 5 6 7 8

      4
     */
    public void test10(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){

            int length = scanner.nextInt();
            scanner.nextLine();
            String s = scanner.nextLine();

            String[] split = s.split(" ");
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i <split.length ; i++) {
                stack.push(new Integer(split[i]));
            }

            int index = scanner.nextInt();

            if(index == 0){
                System.out.println(0);
            }else {
                for (int i = 0; i < index-1; i++) {
                    stack.pop();
                }
                System.out.println(stack.pop());
            }
        }

    }



    /**
     * 判断短字符串中的所有字符是否在长字符串中全部出现
     *
     * 输入两个字符串。第一个为短字符，第二个为长字符。
     *
     * bc
     * abc
     *
     * true
     */
    public void test09(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String shortStr = scanner.nextLine();
            String longStr = scanner.nextLine();
            char[] sch = shortStr.toCharArray();
            char[] lch = longStr.toCharArray();

            boolean findFlag1 = true;

            for (int i = 0; i < sch.length; i++) {
                boolean findFlag = false;
                for (int j = 0; j < lch.length; j++) {
                    if(lch[j] == sch[i]){
                        findFlag = true;
                        break;
                    }
                }
                if(findFlag == false){
                    findFlag1 = false;
                    System.out.println(false);
                    break;
                }
            }
            if(findFlag1 == true){
                System.out.println(true);
            }
        }
    }


    /**
     * 计算两个字符串的最大公共字串的长度，字符不区分大小写
     */
    public void test08(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str1 = scanner.nextLine();
            String str2 = scanner.nextLine();
            str1 = str1.toLowerCase();
            str2 = str2.toLowerCase();

            List<String> list = new ArrayList<>();
            for (int i = 0; i < str1.length()-1 ; i++) {
                for (int j = i+1; j < str1.length()+1; j++) {
                    String sub = str1.substring(i, j);
                    if(str2.contains(sub)){
                        list.add(sub);
                    }
                }
            }

            int max = 0;
            for (int i = 0; i < list.size(); i++) {
                int len = list.get(i).length();
                if(len > max){
                    max = len;
                }
            }
            System.out.println(max);
        }
    }

    /**
     * 首先输入要输入的整数个数n，然后输入n个整数。输出为n个整数中负数的个数，和所有正整数的平均值，结果保留一位小数。
     */
    public void test07(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){

            int num = scanner.nextInt();

            ArrayList<Integer> pNum = new ArrayList<>();//正数
            ArrayList<Integer> nNum = new ArrayList<>();//负数
            ArrayList<Integer> zNum = new ArrayList<>();//0

            for (int i = 0; i < num; i++) {
                int in = scanner.nextInt();
                if(in == 0){
                    zNum.add(in);
                }
                if(in > 0){
                    pNum.add(in);
                }
                if(in < 0){
                    nNum.add(in);
                }
            }

            System.out.print(nNum.size());
            System.out.print(" ");

 /*       Integer sum = pNum.stream().reduce(Integer::sum).get();
          System.out.printf("%.1f\n",(sum / pNum.size()));*/

            double avg = pNum.stream().mapToInt(m -> m).average().getAsDouble();
            System.out.printf("%.1f\n",avg);
        }

    }

    /**
     * ip合法 0.0.0.0 - 255.255.255.255之间
     *  .  要用  \\.做转义
     */
    public void test06(){
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){

            String s = scanner.nextLine();
            String[] split = s.split("\\.");
            System.out.println(s);
            System.out.println(Arrays.toString(split));

            if(split.length != 4){
                System.out.println("NO");
            }

            boolean flag = true;
            for (int i = 0; i <split.length ; i++) {
                Integer num = new Integer(split[i]);
                if(num < 0 || num >255){
                    flag = false;
                }
            }

            if(flag == true){
                System.out.println("YES");
            }else {
                System.out.println("NO");
            }

        }
    }

    public void test05(){
        //A 是65 a是97     中间隔着32

        String str = "as%%66-JBo7o";

        //把0-9的数字全部换成0
        System.out.println(Pattern.compile("[0-9]").matcher(str).replaceAll("0"));

        //找到字符串里面的所有数字
        String[] split = Pattern.compile("[^0-9]").matcher(str).replaceAll(",")
                .replaceAll(",,",",")
                .replaceAll(",,",",")
                .replaceAll(",,",",")
                .split(",");
        System.out.println(Arrays.toString(split));

        //找到字符串里面的所有连续字符串
        String[] split1 = Pattern.compile("[^a-zA-Z]").matcher(str).replaceAll(",").split(",");
        System.out.println(Arrays.toString(split1));
    }

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

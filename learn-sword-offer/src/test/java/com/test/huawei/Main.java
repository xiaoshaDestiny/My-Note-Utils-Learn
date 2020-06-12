package com.test.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PrintString{
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

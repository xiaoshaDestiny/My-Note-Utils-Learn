package learn.jucprogram;

/**
 * @author xrb
 * @create 2020-04-17 17:22
 */
public class PrintString {
    //一个线程打印1~52，另一个线程打印字母A~Z。打印顺序为12A34B56C........5152Z。要求用线程间的通信
    public static void main(String[] args){
        Printer p = new Printer();
        Thread t1 = new NumberPrinter(p);
        Thread t2 = new CharPrinter(p);
        t1.start();
        t2.start();
    }
}

class Printer{
    private int index = 1;
    synchronized void print(int n){
        while(index%3 == 0){
            try{
                wait();
                /*在其他线程调用此对象的notify方法钱，导致当前线程等待*/
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.print(index);
        index++;
        notifyAll();
    }
    synchronized void print(char c){
        while(index % 3!=0){
            try{
                wait();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.print(c);
        System.out.print(index);
        index++;
        notifyAll();
    }
}
class NumberPrinter extends Thread{
    private Printer p;

    NumberPrinter(Printer p){
        this.p=p;
    }

    @Override
    public void run(){
        for(int i=1;i<=52;i++){
            p.print(i);
        }
    }
}

class CharPrinter extends Thread{
    private Printer p;

    CharPrinter(Printer p){
        this.p=p;
    }

    @Override
    public void run(){
        for(char c='A';c<='Z';c++){
            p.print(c);
        }
    }
}
package com.learn.design.yldz;

/**
 * @author xrb
 * @create 2020-03-27 22:22
 * 依赖倒置
 */
public class DependenceInversion {
    public static void main(String[] args) {
        //接口传递参数实现依赖
        AImpl aimpl = new AImpl();
        aimpl.afun( () ->{
            System.out.println("lambda create B b. this b fun()......");
        });
        aimpl.afun(new BImpl());
        System.out.println("--------------------------------");

        //构造器
        DImpl d = new DImpl();
        CImpl c = new CImpl(d);
        c.cfun();
        System.out.println("--------------------------------");

        //setter
        FImpl f = new FImpl();
        EImpl e = new EImpl();
        e.setF(f);
        e.efun();
    }
}

//接口传递参数实现依赖
interface A{
    void afun(B b);
}
interface B{
    void bfun();
}
class AImpl implements A{
    @Override
    public void afun(B b) {
        b.bfun();
    }
}
class BImpl implements B{
    @Override
    public void bfun() {
        System.out.println("bfun()......");
    }
}





//构造器传递参数实现依赖
interface C{
    void cfun();
}

interface D{
    void dfun();
}

class CImpl implements C{
    private D d;
    //构造器
    public CImpl(D d) {
        this.d = d;
    }
    @Override
    public void cfun() {
        d.dfun();
    }
}

class DImpl implements D{
    @Override
    public void dfun() {
        System.out.println("dfun()........");
    }
}


//setter方法传参
interface E{
    void efun();
    void setF(F f);
}
interface F{
    void ffun();
}

class EImpl implements E{
    private F f;
    @Override
    public void efun() {
        f.ffun();
    }

    @Override
    public void setF(F f) {
        this.f = f;
    }
}

class FImpl implements F{
    @Override
    public void ffun() {
        System.out.println("ffun().........");
    }
}








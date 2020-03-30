package com.learn.design.simplefactory2.pizzacase;

/**
 * @author xrb
 * @create 2020-03-30 16:56
 * 客户端
 *
 * 传统方式：
 * 优点是比较好理解，简单易操作。
 * 缺点是违反了设计模式的 ocp  原则，即对扩展开放，对修改关闭。即当我们给类增加新功能的时候，尽量不修
 * 改代码，或者尽可能少修改代码.
 * 比如我们这时 要新增加一个 Pizza  的种类(Pepper  披萨)，我们需要做如下修改.
 * 如果我们增加一个 Pizza 类，只要是订购 Pizza 的代码都需要修改.
 *
 * 改进的思路分析
 * 分析：修改代码可以接受，但是如果我们在其它的地方也有创建 Pizza 的代码，就意味着，也需要修改，而创建 Pizza
 * 的代码，往往有多处。
 * 思路： 把创建 Pizza  对象封装到一个类中， ， 这样我们有新的 Pizza  种类时， ， 只需要修改该类就可，其它有创建到 Pizza
 *
 * 对象的代码就不需要修改了.->  简单工厂模式
 *
 */
public class PizzaStore {

    public static void main(String[] args) {
//        new OrderPizza();


        new OrderPizza(new SimplePizzaFactory());
    }

}

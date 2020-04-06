package learn.test;

import java.io.Serializable;

/**
 * @author xrb
 * @create 2020-04-03 13:44
 */
public class Cat implements Serializable,Cloneable {
    private String name;
    private Integer age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public Cat(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

package learn.test;

import java.io.*;

/**
 * @author xrb
 * @create 2020-04-03 13:44
 */
public class Dog implements Serializable,Cloneable {
    private String name;
    private Cat cat;


    //方式一
    @Override
    protected Object clone() throws CloneNotSupportedException {
        //先完成基本数据类型的clone
        Dog dog = (Dog) super.clone();

        //单独处理引用类型数据
        dog.cat = (Cat) cat.clone();
        return dog;
    }

    //方式二
    public Object deepClone2(){
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            //序列化
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);


            //反序列化
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            Dog dog = (Dog) ois.readObject();

            return dog;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }finally {
            try {
                bos.close();
                oos.close();
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Dog(String name, Cat cat) {
        this.name = name;
        this.cat = cat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", cat=" + cat +
                '}';
    }
}

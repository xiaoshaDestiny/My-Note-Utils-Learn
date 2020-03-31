package com.learn.design.prototype5;


import java.io.*;

/**
 * @author xrb
 * @create 2020-03-31 17:27
 */
public class DeepProtoType implements Serializable,Cloneable {
    private String name;
    private DeepCloneTarget deepCloneTarget;


    public DeepProtoType(String name, DeepCloneTarget deepCloneTarget) {
        this.name = name;
        this.deepCloneTarget = deepCloneTarget;
    }


    /**
     * 深拷贝实现方式一 通过Object类的clone()方法 需要实现Cloneable接口
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        //先完成基本数据类型的clone
        DeepProtoType deepProtoType = (DeepProtoType) super.clone();

        //单独处理引用类型数据
        deepProtoType.deepCloneTarget = (DeepCloneTarget) deepCloneTarget.clone();
        return deepProtoType;
    }
    //深拷贝的实现方式二  通过序列化方式 (推荐使用)

    public Object deepClone(){
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
            DeepProtoType deepProtoType = (DeepProtoType) ois.readObject();

            return deepProtoType;
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



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeepCloneTarget getDeepCloneTarget() {
        return deepCloneTarget;
    }

    public void setDeepCloneTarget(DeepCloneTarget deepCloneTarget) {
        this.deepCloneTarget = deepCloneTarget;
    }

    @Override
    public String toString() {
        return "DeepProtoType{" +
                "name='" + name + '\'' +
                ", deepCloneTarget=" + deepCloneTarget +
                '}';
    }
}

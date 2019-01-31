package DesignPatterns.prototype;

import java.sql.SQLOutput;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 10:18
 * @Description: 原型模式 --> 用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象
 */
public class PrototypePattern {

    public static void main(String[] args) throws CloneNotSupportedException {
        ProtoType protoType = new ConcreatePrototype("原型模式");
        ProtoType protoType1 = (ProtoType) protoType.clone();

        System.out.println(protoType+ " " + protoType.getName());
        System.out.println(protoType1+" " + protoType1.getName());

    }




}

package DesignPatterns.builder;

/**
 * @Auther: Ragty
 * @Date: 2019/1/30 17:36
 * @Description: 建造者模式 --- 是将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以有不同的表示
 *                 如：去肯德基，汉堡、可乐、薯条、炸鸡翅等是不变的，而其组合是经常变化的，生成出所谓的"套餐"
 */
public class BuuilderPattern {

    public static void main(String[] args) {
        PersonDirector personDirector = new PersonDirector();                       //指挥者(商量好需求，要造人，造头，造胳膊，造腿)
        Person person = personDirector.constructorPerson(new ManBuilder());         //抽象构造(里边有抽象的动作)
        Person person1 = personDirector.constructorPerson(new WomanBuilder());
        System.out.println(person);
        System.out.println(person1);
    }
}

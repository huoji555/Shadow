package DesignPatterns.factoryMethod;

/*
 * @auther: Ragty
 * @describe: 工厂模式--具体的工厂负责产生具体的产品对象
 * @param:
 * @return:
 * @date: 2019/1/30
 */
public class FactoryMethodPatterns {

    public static void main(String[] args) {
        StudentWorkFactory swf = new StudentWorkFactory();
        swf.getWork().doWork();

        TeacherWorkFactory twf = new TeacherWorkFactory();
        twf.getWork().doWork();

    }


}

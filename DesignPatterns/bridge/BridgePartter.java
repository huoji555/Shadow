package DesignPatterns.bridge;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 15:11
 * @Description: 桥接模式 ---> 抽象部分与显示部分分离，使他们都可以独立变化
 * (这里Person是实现化角色，Man和Lady是具体化实现角色，Clothing是抽象化角色，黑白鞋是扩展抽象化角色)
 */
public class BridgePartter {

    public static void main(String[] args) {
        Man man = new Man();
        Lady lady = new Lady();

        Clothing blackShoe = new BlackShoe();
        Clothing whiteShoe = new WhiteShoe();

        blackShoe.personDressCloth(man);
        blackShoe.personDressCloth(lady);

        whiteShoe.personDressCloth(man);
        whiteShoe.personDressCloth(lady);

    }

}

package proxy;

/**
 * @Auther: Ragty
 * @Description:  代理模式 --->所谓代理模式是指客户端并不直接调用实际的对象，而是通过调用代理，来间接的调用实际的对象
 * @date: 2019/2/12 15:39
 */
public class ProxyPattern {

    public static void main(String[] args) {

        Dog dog = new DogProxy();
        dog.eat();

    }


}

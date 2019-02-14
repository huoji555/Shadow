package strategy;

/**
 * @Auther: Ragty
 * @Description: 策略模式 --> 策略模式属于对象的行为模式。其用意是针对一组算法，将每一个算法封装到具有共同接口的独立的类中，从而使得它们可以相互替换。
 * 策略模式使得算法可以在不影响到客户端的情况下发生变化
 * @param:${param}
 * @Date: 2019/2/14 09:48
 */
public class StrategyPattern {

    public static void main(String[] args) {

        TrafficCalculator trafficCalculator = new TrafficCalculator();

        trafficCalculator.TrafficCalculator(new Strategy("公交"),5);

    }

}

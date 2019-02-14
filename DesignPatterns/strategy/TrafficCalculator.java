package strategy;

public class TrafficCalculator {

    CalculateStrategy calculateStrategy;

    public void TrafficCalculator(CalculateStrategy calculateStrategy,Integer km) {
        this.calculateStrategy = calculateStrategy;
        calculateStrategy.calculatePrice(km);
    }
}

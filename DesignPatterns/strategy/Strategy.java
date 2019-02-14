package strategy;

public class Strategy implements CalculateStrategy{

    private String name;

    public Strategy(String name) {
        this.name = name;
    }

    @Override
    public void calculatePrice(Integer km) {

        Integer total = 0;

        if (name == "公交") {
            int extralTol = km-10;
            int extralFactor = extralTol/5;
            int fraction = extralTol%5;
            int price = 1+extralFactor*1;
            total = fraction>0 ? ++price: price;
        } else if (name == "地铁"){
            if (km <= 6) {
                total = 3;
            } else if (km > 6 && km < 12) {
                total = 4;
            } else if (km < 22 && km > 12) {
                total = 5;
            } else if (km < 32 && km > 22) {
                total = 6;
            } else {
                total = 7;
            }
        }

        System.out.println(name+"坐"+km+"公里，需要"+total+"元");

    }
}

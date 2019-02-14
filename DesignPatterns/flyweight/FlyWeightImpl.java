package flyweight;

public class FlyWeightImpl implements FlyWeight{
    @Override
    public void showIndex(Integer index) {
        System.out.println("Index:" + index);
    }
}

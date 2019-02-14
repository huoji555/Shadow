package flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyWeightFactory {

    private static Map<Integer,FlyWeight> map = new HashMap<Integer, FlyWeight>();

    public FlyWeightFactory(Integer index) {
        map.put(index,new FlyWeightImpl());
    }

    public static FlyWeight getFlyWeight(Integer index) {
        if (!map.containsKey(index)) {
            map.put(index,new FlyWeightImpl());
        }
        return map.get(index);
    }

    public static Integer  getSize() {
        return map.size();
    }


}

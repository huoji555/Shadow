package Hash;

import java.util.Map;
import Map.MyBetterMap;


public class SillyString {

    private final String innnerString;

    public SillyString(String innnerString) {
        this.innnerString = innnerString;
    }

    public String toString() {
        return innnerString;
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        Integer total = 0;
        for (int i=0; i<innnerString.length(); i++) {
            total += innnerString.charAt(i);
        }
        System.out.println(total);
        return total;
    }


    public static void main(String[] args) {
        Map<SillyString, Integer> map = new MyBetterMap<SillyString, Integer>();

        map.put(new SillyString("Word1"), 1);
        map.put(new SillyString("Word3"), 2);

        for (SillyString key: map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }


}
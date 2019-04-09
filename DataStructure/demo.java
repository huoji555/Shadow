import java.util.*;

public class demo {

    public static void main(String[] args) {
        String result = "";
        Integer finalSize = 3;
        double math = 3;
        double chinese = 4.2;
        double english = 2;
        double computer = 3;
        double design = 3;
        double practice = 1;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("数学能力",math);
        map.put("语言能力",chinese);
        map.put("英文能力",english);
        map.put("逻辑能力",computer);
        map.put("设计能力",design);
        map.put("体育能力",practice);

        Map<String,Double> good = new HashMap<String, Double>();
        Map<String,Double> normal = new HashMap<String, Double>();
        Map<String,Double> bad = new HashMap<String, Double>();

        for (Map.Entry<String,Object> entry : map.entrySet()) {
            Double score =  (Double) entry.getValue();
            String classes = entry.getKey();
            if (score >= 4) {
                good.put(classes,score);
            } else if (score>=3 && score<4) {
                normal.put(classes,score);
            } else {
                bad.put(classes,score);
            }
        }

        Map<String, Double> map1 = new TreeMap<String, Double>(new MapValueComparator<Double>(good));
        Map<String, Double> map2 = new TreeMap<String, Double>(new MapValueComparator<Double>(normal));
        Map<String, Double> map3 = new TreeMap<String, Double>(new MapValueComparator<Double>(bad));
        map1.putAll(good);
        map2.putAll(normal);
        map3.putAll(bad);

        String[] keys = map1.keySet().toArray(new String[0]);
        String[] keys1 = map2.keySet().toArray(new String[0]);
        String[] keys2 = map3.keySet().toArray(new String[0]);
        System.out.println("测试前:"+map.toString());

        if (good.size() >= 3) {
            System.out.println("基于您的个人能力测试，发现您的【"+Random(keys,result,finalSize)+"】能力很强");
        } else if (good.size()>0 && (normal.size()+good.size()>=3 || bad.size()+good.size()>=3)) {
            for (String xx : keys) {  result += (result == "" || result == null) ? xx : ","+xx;}
            finalSize -= keys.length;
            if (normal.size()+good.size() >= 3) {
                System.out.println("基于您的个人能力测试，发现您的【"+Random(keys1,result,finalSize)+"】比较不错");
            } else if (bad.size()+good.size() >= 3 &&  normal.size() == 0) {
                System.out.println("基于您的个人能力测试，发现您的【"+Random(keys2,result,finalSize)+"】比较不错");
            } else {
                for (String xx : keys1) {  result += (result == "" || result == null) ? xx : ","+xx;}
                finalSize -= keys1.length;
                System.out.println("基于您的个人能力测试，发现您的【"+Random(keys2,result,finalSize)+"】比较不错");
            }
        } else {
            if (keys1.length >= 3) {
                System.out.println("基于您的个人能力测试，发现您的【"+Random(keys1,result,finalSize)+"】比较均衡");
            } else {
                for (String xx : keys1) {  result += (result == "" || result == null) ? xx : ","+xx;}
                finalSize -= keys1.length;
                System.out.println("基于您的个人能力测试，发现您的【"+Random(keys2,result,finalSize)+"】比较均衡");
            }
        }

    }



    /**
     * @Author Ragty
     * @Description  随机科目
     * @Date 11:07 2019/4/8
     **/
    public static String Random(String[] keys,String result,Integer finalSize) {
        for (int i=0; i<finalSize; i++) {
            result += (result == "" || result == null) ? keys[i] : ","+keys[i];
        }
        return result;
    }



    /**
     * @Author Ragty
     * @Description  比较器（按值排序,相同的随机取值）
     * @Date 10:38 2019/4/9
     **/
    public static class MapValueComparator<T extends Comparable<T>> implements Comparator<String> {
        private Map<String, T> map = null;
        public MapValueComparator(Map<String, T> map) {
            this.map = map;
        }

        public int compare(String o1, String o2) {
            int r = map.get(o2).compareTo(map.get(o1));
            if (r != 0) {
                return r;
            }
            int randomOne = (int) (Math.random() * 10);
            int randomTwo = (int) (Math.random() * 10);
            return randomOne - randomTwo;
        }
    }

}
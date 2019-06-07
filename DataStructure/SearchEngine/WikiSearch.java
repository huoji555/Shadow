package SearchEngine;

import Reids.JedisIndex;
import Reids.JedisMaker;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class WikiSearch {

    // 用来映射搜索项的相关性得分
    private Map<String,BigDecimal> map;

    public WikiSearch( Map<String, BigDecimal> map) {
        this.map = map;
    }


    /**
     * @Author Ragty
     * @Description 获取tfidf下的相关性
     * @Date 8:47 2019/6/6
     **/
    private static BigDecimal getRelevance(String url,String term,JedisIndex index) {
        BigDecimal tfidf = index.getTFIDF(url,term);
        return tfidf;
    }


    /**
     * @Author Ragty
     * @Description 返回给出map中搜索词的tfidf
     * @Date 15:21 2019/6/7
     **/
    private BigDecimal getRelevance(String term) {
        BigDecimal tfidf = map.get(term);
        return tfidf;
    }


    /**
     * @Author Ragty
     * @Description 按搜索项频率顺序打印内容
     * @Date 13:46 2019/5/30
     **/
    private void print() {
        List<Entry<String,BigDecimal>> entries = sort();
        for(Entry<String,BigDecimal> entry: entries) {
            System.out.println(entry.getKey()+"         "+entry.getValue());
        }
    }


    /**
     * @Author Ragty
     * @Description 根据相关性对数据排序
     * @Date 13:54 2019/5/30
     **/
    public List<Entry<String,BigDecimal>> sort(){
        List<Entry<String,BigDecimal>>  entries = new LinkedList<Entry<String, BigDecimal>>(map.entrySet());

        Comparator<Entry<String,BigDecimal>> comparator = new Comparator<Entry<String, BigDecimal>>() {
            @Override
            public int compare(Entry<String, BigDecimal> o1, Entry<String, BigDecimal> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };

        Collections.sort(entries,comparator);
        return entries;
    }


    /**
     * @Author Ragty
     * @Description 计算查询项总相关性
     * @Date 14:58 2019/5/30
     **/
    protected BigDecimal totalRelevence(BigDecimal rel1,BigDecimal rel2) {
        return rel1.add(rel2);
    }



    /**
     * @Author Ragty
     * @Description 计算两个搜索结果的并集(更广了)
     * @Date 23:13 2019/5/30
     **/
    public WikiSearch or(WikiSearch that) {
        Map<String,BigDecimal> union = new HashMap<String, BigDecimal>(map);
        for(String term: that.map.keySet()) {
            union.put(term,totalRelevence(this.getRelevance(term),that.getRelevance(term)));
        }
        return new WikiSearch(union);
    }


    /**
     * @Author Ragty
     * @Description 计算搜索项的交集
     * @Date 23:22 2019/5/30
     **/
    public WikiSearch and(WikiSearch that) {
        Map<String,BigDecimal> intersection = new HashMap<String, BigDecimal>();
        for(String term: that.map.keySet()) {
            if(map.containsKey(term)) {
                intersection.put(term,totalRelevence(this.getRelevance(term),that.getRelevance(term)));
            }
        }
        return new WikiSearch(intersection);
    }


    /**
     * @Author Ragty
     * @Description 计算搜索项的差集
     * @Date 23:35 2019/5/30
     **/
    public WikiSearch minus(WikiSearch that) {
        Map<String,BigDecimal> difference = new HashMap<String, BigDecimal>(map);
        for (String term: that.map.keySet()) {
            if (difference.containsKey(term)) {
                difference.remove(term);
            }
        }
        return  new WikiSearch(difference);
    }


    /**
     * @Author Ragty
     * @Description 执行搜索
     * @Date 23:49 2019/5/30
     **/
    public static WikiSearch search(String term,JedisIndex index) {
        Map<String,BigDecimal> map = new HashMap<String, BigDecimal>();
        Set<String> urls = index.getUrls(term);

        for (String url: urls) {
            BigDecimal tfidf = getRelevance(url,term,index).setScale(6,BigDecimal.ROUND_HALF_UP);
            map.put(url,tfidf);
        }

        return new WikiSearch(map);
    }


    public static void main(String[] args) throws IOException {
        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);

        // search for the first term
        String term1 = "java";
        System.out.println("Query: " + term1);
        WikiSearch search1 = search(term1, index);
        search1.print();

        // search for the second term
        String term2 = "programming";
        System.out.println("Query: " + term2);
        WikiSearch search2 = search(term2, index);
        search2.print();

        // compute the intersection of the searches
        System.out.println("Query: " + term1 + " AND " + term2);
        WikiSearch intersection = search1.and(search2);
        intersection.print();

    }


}
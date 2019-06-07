package Reids;

import SearchEngine.TermCounter;
import SearchEngine.WikiFetcher;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class JedisIndex {

    private Jedis jedis;

    public JedisIndex(Jedis jedis) {
        this.jedis = jedis;
    }


    /**
     * @Author Ragty
     * @Description 返回搜索项的Redis key
     * @Date 20:50 2019/5/14
     **/
    private String urlSetKey(String url) {
        return "URLSet:"+url;
    }


    /**
     * @Author Ragty
     * @Description 返回搜索计数器的Redis key
     * @Date 20:53 2019/5/14
     **/
    private String termCounterKey(String term) {
        return "TermCounter:"+term;
    }


    /**
     * @Author Ragty
     * @Description 检查url有没有被索引
     * @Date 20:56 2019/5/14
     **/
    public boolean isIndexed(String url) {
        String redisKey = urlSetKey(url);
        return jedis.exists(redisKey);
    }


    /**
     * @Author Ragty
     * @Description 将url添加到与搜索项相关的集合中去
     * @Date 21:05 2019/5/14
     **/
    public void add(String term, TermCounter tc) {
        jedis.sadd(termCounterKey(term),tc.getLabel());
    }


    /**
     * @Author Ragty
     * @Description 查找搜索词并返回一组url
     * @Date 21:09 2019/5/14
     **/
    public Set<String> getUrls(String term) {
        Set<String> set = jedis.smembers(termCounterKey(term));
        return set;
    }


    /**
     * @Author Ragty
     * @Description 查找搜索词并返回一组计数的集合
     * @Date 21:24 2019/5/14
     **/
    public Map<String,Integer> getCounts(String term) {
        Map<String,Integer> map = new HashMap<String, Integer>();
        Set<String> urls = getUrls(term);

        for (String url: urls) {
            Integer count = getTermCount(url,term);
            map.put(url,count);
        }
        return map;
    }

    /**
     * @Author Ragty
     * @Description 返回搜索项在url中出现的次数
     * @Date 22:12 2019/5/14
     **/
    public Integer getTermCount(String url,String term) {
        String redisKey = urlSetKey(url);
        String count = jedis.hget(redisKey,term);
        return new Integer(count);
    }


    /**
     * @Author Ragty
     * @Description 获取url中的词汇总数
     * @Date 11:18 2019/6/4
     **/
    public Integer getWordCount(String url) {
        String redisKey = urlSetKey(url);
        Map<String,String> map = jedis.hgetAll(redisKey);
        Integer count = 0;

        for(Map.Entry<String, String> entry: map.entrySet()) {
            count += Integer.valueOf(entry.getValue());
        }
        return count;
    }


    /**
     * @Author Ragty
     * @Description 查找搜索词并返回一组计数的集合(里边包括url,count),这个更快的原因是因为采用事务，同时减少对redis的访问
     * @Date 7:49 2019/5/15
     **/
    public Map<String,Integer> getCountsFaster(String term) {
        //将set集合转换为list,以便每次都有相同的遍历顺序
        List<String> urls = new ArrayList<String>();
        urls.addAll(getUrls(term));

        // 创建一个事务执行所有查询
        Transaction t = jedis.multi();
        for (String url: urls) {
            String redisKey = urlSetKey(url);
            t.hget(redisKey,term);
        }
        List<Object> res = t.exec();

        //遍历结果，制造出map
        Map<String,Integer> map = new HashMap<String, Integer>();
        int i=0;
        for (String url: urls) {
            System.out.println(url);
            Integer count = new Integer((String)res.get(i++));
            map.put(url,count);
        }
        return map;
    }


    /**
     * @Author Ragty
     * @Description 索引化一个页面
     * @Date 7:55 2019/5/15
     **/
    public void indexPage(String url, Elements elements) {
        System.out.println("Indexing..." + url);

        TermCounter tc = new TermCounter(url);
        tc.processElements(elements);

        pushTermCounterToRedis(tc);
    }


    /**
     * @Author Ragty
     * @Description 将计数集合持久化到redis中
     * @Date 8:12 2019/5/15
     **/
    public List<Object> pushTermCounterToRedis(TermCounter tc) {
        Transaction t = jedis.multi();

        String url = tc.getLabel();
        String redisKey = urlSetKey(url);
        t.del(redisKey);

        for (String term: tc.keySet()) {
            Integer count = tc.get(term);
            t.hset(redisKey, term, count.toString());
            t.sadd(termCounterKey(term),url);
        }
        List<Object> res = t.exec();
        return res;
    }


    /**
     * @Author Ragty
     * @Description 打印被索引的内容
     * @Date 9:21 2019/5/15
     **/
    public void printIndex() {
       for (String term: termSet()) {
           System.out.println(term);

           Set<String> urls = getUrls(term);
           for (String url: urls) {
               Integer count = getTermCount(url,term);
               System.out.println("   "+url+ " "+count);
           }
       }
    }


    /**
     * @Author Ragty
     * @Description 返回被索引的搜索项的set结构
     * @Date 8:56 2019/5/15
     **/
    public Set<String> termSet() {
        Set<String> keys = urlSetKeys();
        Set<String> terms = new HashSet<String>();
        for (String key: keys) {
            String array[] = key.split(":");
            if (array.length < 2) {
                terms.add("");
            } else {
                terms.add(array[1]);
            }
        }
        return terms;
    }


    /**
     * @Author Ragty
     * @Description 返回已索引的搜索项的集合(term)
     * @Date 8:38 2019/5/15
     **/
    public Set<String> urlSetKeys() {
        return jedis.keys("URLSet:*");
    }


    /**
     * @Author Ragty
     * @Description 返回已索引的为搜索项计数的url
     * @Date 9:21 2019/5/15
     **/
    public Set<String> termCounterKeys() {
        return jedis.keys("TermCounter:*");
    }


    /**
     * @Author Ragty
     * @Description 删除被索引的搜索项
     * @Date 9:25 2019/5/15
     **/
    public void deleteURLSets() {
        Set<String> keys = urlSetKeys();
        Transaction t = jedis.multi();
        for (String key: keys) {
            t.del(key);
        }
        t.exec();
    }


    /**
     * @Author Ragty
     * @Description 删除被索引的搜索项计数器的url
     * @Date 9:26 2019/5/15
     **/
    public void deleteTermCounters() {
        Set<String> keys = termCounterKeys();
        Transaction t = jedis.multi();
        for (String key: keys) {
            t.del(key);
        }
        t.exec();
    }


    /**
     * @Author Ragty
     * @Description 删除库中所有的key
     * @Date 9:28 2019/5/15
     **/
    public void deleteAllKeys() {
        Set<String> keys = jedis.keys("*");
        Transaction t = jedis.multi();
        for (String key: keys) {
            t.del(key);
        }
        t.exec();
    }


    /**
     * @Author Ragty
     * @Description 存储两个页面进行测试使用
     * @Date 9:39 2019/5/15
     **/
    private static void loadIndex(JedisIndex index) throws IOException {
        WikiFetcher wf = new WikiFetcher();
        String url = "https://baike.baidu.com/item/java/85979";
        Elements paragraphs = wf.fetchWikiPedia(url);
        index.indexPage(url, paragraphs);

        url = "https://baike.baidu.com/item/javascript";
        paragraphs = wf.fetchWikiPedia(url);
        index.indexPage(url, paragraphs);
    }



    /**
     * @Author Ragty
     * @Description 获取搜索词的词频(Term Frequency)
     * @Date 11:25 2019/6/4
     **/
    public BigDecimal getTermFrequency(String url,String term) {
        if (!isIndexed(url)) {
            System.out.println("Doesn't indexed.");
            return null;
        }

        Integer documentCount = getWordCount(url);
        Integer termCount = getTermCount(url,term);
        return documentCount==0 ? new BigDecimal(0) :  new BigDecimal(termCount).divide(new BigDecimal(documentCount),6,BigDecimal.ROUND_HALF_UP);
    }


    /**
     * @Author Ragty
     * @Description 获取redis索引文章的总数
     * @Date 19:46 2019/6/5
     **/
    public Integer getUrlCount() {
        Integer count = 0;
        count = urlSetKeys().size();
        return count;
    }


    /**
     * @Author Ragty
     * @Description 获取含有搜索词的文章数
     * @Date 22:42 2019/6/5
     **/
    public Integer getUrlTermCount(String term) {
        Integer count = 0;
        count = getUrls(term).size();
        return count;
    }


    /**
     * @Author Ragty
     * @Description 计算逆文档频率IDF(InverseDocumnetFrequency)
     * @Date 23:32 2019/6/5
     **/
    public BigDecimal getInverseDocumentFrequency(String term) {
        Integer totalUrl = getUrlCount();
        Integer urlTermCount = getUrlTermCount(term);
        Double xx = new BigDecimal(totalUrl).divide(new BigDecimal(urlTermCount),6,BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal idf = new BigDecimal(Math.log10(xx));
        return idf;
    }


    /**
     * @Author Ragty
     * @Description 获取tf-idf值
     * @Date 23:34 2019/6/5
     **/
    public BigDecimal getTFIDF(String url,String term) {
        BigDecimal tf = getTermFrequency(url, term);
        BigDecimal idf = getInverseDocumentFrequency(term);
        BigDecimal tfidf  =tf.multiply(idf);
        return tfidf;
    }


    public static void main(String[] args) throws IOException{

        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);

        //index.deleteAllKeys();
        //loadIndex(index);

       /* Map<String,Integer> map = index.getCountsFaster("c语言");
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.println(entry);
        }*/

       // System.out.println(index.getUrls("java"));

        System.out.println(index.getWordCount("https://baike.baidu.com/item/javascript"));
        System.out.println(index.getTermFrequency("https://baike.baidu.com/item/javascript","4"));
        System.out.println(index.getUrlCount());
        System.out.println(index.getUrlTermCount("4"));
        System.out.println(index.getInverseDocumentFrequency("4"));
        System.out.println(index.getTFIDF("https://baike.baidu.com/item/javascript","4"));

    }











}
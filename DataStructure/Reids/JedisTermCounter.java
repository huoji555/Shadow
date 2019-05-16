package Reids;

import SearchEngine.TermCounter;
import SearchEngine.WikiFetcher;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JedisTermCounter extends TermCounter {

    public JedisTermCounter(String label) {
        super(label);
    }


    /**
     * @Author Ragty
     * @Description 将检索词结果推送到Redis（用Transaction可以加快速度）
    * @Date 15:00 2019/5/10
     **/
    public List<Object> pushToRedis(Jedis jedis) {
        //开启事务，当server端收到multi指令
        //会将该client的命令放入一个队列，然后依次执行，知道收到exec指令
        Transaction t = jedis.multi();

        String hashName = hashName();
        t.del(hashName);

        for (String key: keySet()) {
            Integer count = get(key);
            t.hset(hashName,key,count.toString());
        }

        List<Object> res = t.exec();
        return res;
    }


    /**
     * @Author Ragty
     * @Description 获取标签名
     * @Date 14:48 2019/5/10
     **/
    private String hashName() {
        return "TermCounter:"+getLabel();
    }


    /**
     * @Author Ragty
     * @Description 从Redis中获取
     * @Date 14:54 2019/5/10
     **/
    public Map<String,String> pullFromRedis(Jedis jedis) {
        Map<String,String> map = jedis.hgetAll(hashName());
        return map;
    }


    /**
     * @Author Ragty
     * @Description 检错到对应的词，并持久化
     * @Date 16:36 2019/5/10
     **/
    public static void main(String[] args) throws IOException {
        String url = "https://baike.baidu.com/item/java/85979";

        WikiFetcher wf = new WikiFetcher();
        Elements paragraphs = wf.fetchWikiPedia(url);
        JedisTermCounter counter = new JedisTermCounter(url.toString());
        counter.processElements(paragraphs);

        Jedis jedis = JedisMaker.make();

        counter.pushToRedis(jedis);
        System.out.println("Done pushing.");

        Map<String, String> map = counter.pullFromRedis(jedis);
        for (Map.Entry<String, String> entry: map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        jedis.close();
    }


}
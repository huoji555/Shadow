package HanLP;

import Reids.JedisMaker;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadStopWords {

    private static Jedis jedis = JedisMaker.make();

    /**
    * @Author Ragty
    * @Description  返回中止词的名称
    * @Date   2019/12/27 23:21
    */
    public static String stopWordsSetKey(String StopWords) {return "StopWords:"+StopWords;}



    /**
     *  @author: Ragty
     *  @Date: 2019/12/27 23:22
     *  @Description: 从本地加载中止词文件到Redis中
     */
    public static void ReadStopWordsToRedis(String filePath) throws IOException {

        Transaction t = jedis.multi();
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String temp = null;

        while ((temp = bufferedReader.readLine()) != null) {
            String stopWords = stopWordsSetKey(temp.trim());
            t.sadd(stopWords,temp.trim());
        }

        t.exec();
    }

    /**
     *  @author: Ragty
     *  @Date: 2019/12/27 23:35
     *  @Description: 查看中止词是否被索引过
     */
    public static boolean isIndexed(String stopWords) {
        stopWords = stopWordsSetKey(stopWords);
        return jedis.exists(stopWords);
    }



    public static void main(String[] args) throws IOException{

        String filePath = "D://stopwords.txt";
        //ReadStopWordsToRedis(filePath);
        System.out.println(isIndexed("一"));
        System.out.println(jedis.smembers(stopWordsSetKey("一")).toArray()[0]);
    }


}

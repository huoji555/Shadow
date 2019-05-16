package Reids;

import redis.clients.jedis.Jedis;

public class JedisMaker {

    /**
     * @Author Ragty
     * @Description 创建Redis连接
     * @Date 11:52 2019/5/10
     **/
    public static Jedis make() {
        Jedis jedis = new Jedis("localhost",6379);
        return jedis;
    }


    public static void main(String[] args) {

        Jedis jedis = make();

        // String
        jedis.set("mykey", "myvalue");
        String value = jedis.get("mykey");
        System.out.println("Got value: " + value);

        // Set
        jedis.sadd("myset", "element1", "element2", "element3");
        System.out.println("element2 is member: " + jedis.sismember("myset", "element2"));

        // List
        jedis.rpush("mylist", "element1", "element2", "element3");
        System.out.println("element at index 1: " + jedis.lindex("mylist", 1));

        // Hash
        jedis.hset("myhash", "word1", Integer.toString(2));
        jedis.hincrBy("myhash", "word2", 1);
        System.out.println("frequency of word1: " + jedis.hget("myhash", "word1"));
        System.out.println("frequency of word2: " + jedis.hget("myhash", "word2"));

        jedis.close();
    }



}
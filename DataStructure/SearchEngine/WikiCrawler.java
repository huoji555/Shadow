package SearchEngine;

import Reids.JedisIndex;
import Reids.JedisMaker;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class WikiCrawler {

    // 保存爬取的原始地址
    private final String source;

    //代表爬取总数
    private final int crawlCount = 10000;

    private JedisIndex index;

    private Queue<String> queue = new LinkedList<String>();

    final static WikiFetcher wf = new WikiFetcher();



    public WikiCrawler(String source, JedisIndex index) {
        this.source = source;
        this.index = index;
        queue.offer(source);   //初始化时将原始地址加入队列
    }


    /**
     * @Author Ragty
     * @Description 返回队列里爬取地址的数量
     * @Date 9:50 2019/5/22
     **/
    public int queueSize() {
        return queue.size();
    }

    /**
     * @Author Ragty
     * @Description 从队列中获取Url地址并索引化
     * @Date 11:36 2019/5/22
     **/
    public String crawl(boolean testing) throws IOException {

        if (queue.isEmpty()) {
            return null;
        }
        String url = queue.poll();
        System.out.println("Crawling..."+ url);

        if (testing==false && index.isIndexed(url)) {
            System.out.println("Already indexed.");
            return null;
        }

        Elements para;
        if (testing) {
            para = wf.readWikiPedia(url);
        } else {
            para = wf.fetchWikiPedia(url);
        }
        index.indexPage(url,para);
        queueInternalLinks(para);
        return url;
    }


    /**
     * @Author Ragty
     * @Description 爬取单个网页的所有链接(只更新一次queue)
     * @Date 9:27 2019/5/23
     **/
    public String crawlSingle(boolean testing) throws IOException {

        if (queue.isEmpty()) {
            return null;
        }
        String url = queue.poll();
        System.out.println("Crawling("+queue.size()+")..."+ url);

        if (testing==false && index.isIndexed(url)) {
            System.out.println("Already indexed.");
            return null;
        }

        Elements para;
        if (testing) {
            para = wf.readWikiPedia(url);
        } else {
            para = wf.fetchWikiPedia(url);
        }
        index.indexPage(url,para);
        return url;
    }


    /**
     * @Author Ragty
     * @Description 解析para,并将内部链接添加到队列
     * @Date 11:38 2019/5/22
     **/
    public void queueInternalLinks(Elements paras) {
        for (Element para: paras) {
            queueInternalLinks(para);
        }
    }


    /**
     * @Author Ragty
     * @Description 单个段落解析并添加
     * @Date 11:39 2019/5/22
     **/
    private void queueInternalLinks(Element para) {
        Elements elts = para.select("a[href]");
        for (Element element: elts) {
            String relURL = element.attr("href");

            // need to fix
            if (relURL.startsWith("/item/")) {
                String absURL = "https://baike.baidu.com" + relURL;
                queue.offer(absURL);
            }
        }
    }


    /**
     * @Author Ragty
     * @Description 爬取网页，到最后一个链接时，更新队列，继续爬取，直到一个没有链接的网址
     * @Date 10:21 2019/5/23
     **/
    public static void main(String[] args) throws IOException{
        Jedis jedis =JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);

        String source = "https://baike.baidu.com/item/javascript";
        WikiCrawler wc = new WikiCrawler(source,index);

        Elements paras = wf.fetchWikiPedia(source);
        wc.queueInternalLinks(paras);

        System.out.println("Starting...");
        while (wc.queueSize()>0) {
            String lastLink = wc.crawlSingle(false);
            if (wc.queueSize() == 0) {
                wc = new WikiCrawler(lastLink,index);
                paras = wf.fetchWikiPedia(lastLink);
                wc.queueInternalLinks(paras);
                System.out.println(lastLink);
                System.out.println("更新队列("+wc.queueSize()+")");
            }
        }
        System.out.println("Ending...");


        /*Map<String, Integer> map = index.getCounts("c语言");
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.println(entry);
        }*/


    }







}
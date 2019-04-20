package SearchEngine;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Index {

    // 实例变量index是每个检索词到一组TermCounter对象的映射。每个TermCounter表示检索词出现的页面
    private Map<String, Set<TermCounter>> index = new HashMap<String, Set<TermCounter>>();


    /**
     * @Author Ragty
     * @Description  查找搜索词并返回一组TermCounter(索引字典)
     * @Date 10:45 2019/4/20
     **/
    public Set<TermCounter> get(String term) {
        return index.get(term);
    }


    /**
     * @Author Ragty
     * @Description 添加索引字典
     * @Date 10:52 2019/4/20
     **/
    public void add(String term,TermCounter tc) {
        Set<TermCounter> set = get(term);

        if (get(term) == null) {
            set = new HashSet<TermCounter>();
            index.put(term,set);
        }

        set.add(tc);
    }


    /**
     * @Author Ragty
     * @Description 返回已编制的索引的set
     * @Date 15:38 2019/4/20
     **/
    public Set<String> keySet() {
        return index.keySet();
    }

    /**
     * @Author Ragty
     * @Description 打印当前搜索项的内容
     * @Date 15:46 2019/4/20
     **/
    public void printIndex() {
        for (String term: index.keySet()) {
            System.out.println(term);

            Set<TermCounter> tcs = get(term);
            for (TermCounter tc: tcs) {
                Integer count = tc.get(term);
                System.out.println(tc.getLabel()+ "   "+count);
            }
        }
    }


    /**
     * @Author Ragty
     * @Description  将页面添加到index中(解析页面中的单词并计数)
     * @Date 15:51 2019/4/20
     **/
    public void indexPage(String url, Elements paragraphs) {

        //  生成一个 TermCounter 并统计段落中的检索词
        TermCounter tc = new TermCounter(url);
        tc.processElements(paragraphs);

        // 对于 TermCounter 中的每个检索词，将 TermCounter 添加到索引
        for (String term: tc.keySet()) {
            add(term,tc);
        }

    }




    public static void main(String[] args) throws IOException {

        WikiFetcher wikiFetcher = new WikiFetcher();
        Index indexer = new Index();

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wikiFetcher.fetchWikiPedia(url);
        indexer.indexPage(url,paragraphs);

        String url1 = "https://en.wikipedia.org/wiki/Programming_language";
        Elements paragraphs1 = wikiFetcher.fetchWikiPedia(url);
        indexer.indexPage(url1,paragraphs1);

        indexer.printIndex();

    }


}
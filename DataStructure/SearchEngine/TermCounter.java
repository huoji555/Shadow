package SearchEngine;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author Ragty
 * @Description 将搜索到的东西放到Map里(计数)
 * @Date 14:58 2019/4/17
 **/
public class TermCounter {

    private Map<String,Integer> map;
    private String label;

    public TermCounter(String label) {
        this.map = new HashMap<String, Integer>();
        this.label = label;
    }


    /**
     * @Author Ragty
     * @Description 计算总数
     * @Date 15:02 2019/4/17
     **/
    public  int size() {
        int total = 0;
        for(Integer value : map.values()) {
            total += value;
        }
        return total;
    }

    /**
     * @Author Ragty
     * @Description 获取label
     * @Date 15:05 2019/4/17
     **/
    public String getLabel() {
        return label;
    }

    /**
     * @Author Ragty
     * @Description 获取当前搜索项的计数
     * @Date 15:09 2019/4/17
     **/
    public Integer get(String term) {
        Integer count = map.get(term);
        return count==null ? 0 : count;
    }

    /**
     * @Author Ragty
     * @Description 向map中添加元素
     * @Date 15:12 2019/4/17
     **/
    public void put(String term,int count) {
        map.put(term,count);
    }

    /**
     * @Author Ragty
     * @Description 搜索项自增
     * @Date 15:14 2019/4/17
     **/
    public void incrementTermCount(String term) {
        map.put(term,get(term)+1);
    }


    /**
     * @Author Ragty
     * @Description 分割text并对其计数
     * @Date 0:08 2019/4/18
     **/
    public void processText(String text) {
        // 用空格替换标点符号，转换为小写，并以空格为分隔符
        String[] array = text.replaceAll("\\pP|\\pS"," ")
                            .toLowerCase()
                            .split("\\s+");
        for (String term : array) {
            if (!term.equals("")) {
                incrementTermCount(term);
            }
        }
    }


    /**
     * @Author Ragty
     * @Description 遍历DOM树并计数
     * @Date 0:16 2019/4/18
     **/
    public void processTree(Node root) {
        for (Node node : new WikiNodeIterable(root)) {
            if (node instanceof TextNode) {
                processText(((TextNode) node).text());
            }
        }
    }


    /**
     * @Author Ragty
     * @Description 遍历节点集合并计数(顺序是段落，dom树,再到每个节点的text)
     * @Date 0:21 2019/4/18
     **/
    public void processElements(Elements paragraphs) {
        for (Node node : paragraphs) {
            processTree(node);
        }
    }


    /**
     * @Author Ragty
     * @Description 返回查询的set结构
     * @Date 0:28 2019/4/18
     **/
    public Set<String> keySet() {
        return map.keySet();
    }

    /**
     * @Author Ragty
     * @Description 打印最后的索引数量
     * @Date 0:32 2019/4/18
     **/
    public void printCounts() {
        for (String key : keySet()) {
            Integer count = get(key);
            System.out.println(key +","+ count);
        }
        System.out.println("Total of counts:" + size());
    }


    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        WikiFetcher wf = new WikiFetcher();
        Elements para = wf.fetchWikiPedia(url);

        TermCounter tc = new TermCounter(url);
        tc.processElements(para);
        tc.printCounts();

    }


}
package SearchEngine;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class WikiNodeDemo {

    public static void main(String[] args) throws IOException {

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        //下载并解析元素
        Connection connection = Jsoup.connect(url);
        Document doc = connection.get();

        //选择内容，并解析初其中所有的段落
        Element content = doc.getElementById("mw-content-text");
        Elements paragraphs = content.select("p");
        Element firstPara = paragraphs.get(1);

        iteratorDFS(firstPara);
        System.out.println("--------------------");

        recursiveDFS(firstPara);
        System.out.println("--------------------");

        Iterable<Node> it = new WikiNodeIterable(firstPara);

        for (Node node : it) {
            if (node instanceof TextNode) {
                System.out.println(node);
            }
        }

    }


    /**
     * @Author Ragty
     * @Description 递归实现的DFS
     * @Date 14:11 2019/4/15
     **/
    public static void recursiveDFS (Node node) {
        if (node instanceof TextNode) {
            System.out.println(node);
        }
        for (Node child : node.childNodes()) {
            recursiveDFS(child);
        }
    }



    /**
     * @Author Ragty
     * @Description  迭代方式实现的递归
     * @Date 15:16 2019/4/15
     **/
    public static void iteratorDFS(Node root) {
        Deque<Node> stack = new ArrayDeque<Node>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node instanceof TextNode) {
                System.out.println(node);
            }

            // 这里必须反转，因为开始压栈时，顺序会反，所有需要反转，确保顺序正确
            List<Node> list = new ArrayList<Node>(node.childNodes());
            Collections.reverse(list);
            for (Node child : list) {
                stack.push(child);
            }

        }
    }



}
package SearchEngine;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wk  = new WikiFetcher();

    public static void main(String[] args) throws IOException{
        String destination = "https://en.wikipedia.org/wiki/Philosophy";     // 哲学界面
        String source = "https://en.wikipedia.org/wiki/Python";
        testConjecture(destination,source,10);
    }


    /**
     * @Author Ragty
     * @Description 到达哲学，1.有相同链接，返回 2.无有效链接，返回
     * @Date 10:17 2019/4/17
     **/
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        String url = source;
        for (int i=0; i<limit; i++) {
            if (visited.contains(url)) {
                System.err.println("We're in a loop, exiting.");
                return;
            } else {
                visited.add(url);
            }

            Element element = getFirstLink(url);
            if (element == null) {
                System.err.println("Got to a page not have valid link.");
                return;
            }

            System.out.println("**" + element.text() + "**");
            url = element.attr("abs:href");

            if (url.equals(destination)) {
                System.out.println("到达哲学!");  //到达哲学
                break;
            }

        }

    }


    /**
     * @Author Ragty
     * @Description 获取到第一个有效链接
     * @Date 10:01 2019/4/17
     **/
    public static Element getFirstLink(String url) throws IOException{
        System.out.println(String.format("Fetching %s...", url));
        Elements para = wk.fetchWikiPedia(url);
        WikiParser wp = new WikiParser(para);
        Element element = wp.findFirstLink();
        return element;
    }

}
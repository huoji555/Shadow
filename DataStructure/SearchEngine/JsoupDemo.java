package SearchEngine;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupDemo {

    public static void main(String[] args) throws Exception{
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        //下载并解析元素
        Connection connection = Jsoup.connect(url);
        Document doc = connection.get();

        //选择内容，并解析初其中所有的段落
        Element content = doc.getElementById("mw-content-text");
        Elements paragraphs = content.select("p");

        for (Element xx : paragraphs) {
            System.out.println(xx);
        }

    }

}
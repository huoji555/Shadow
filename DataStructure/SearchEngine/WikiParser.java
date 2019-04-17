package SearchEngine;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class WikiParser {

    private Elements paragraph;                     // 我们应该搜索的段落
    private Deque<String> parenthesisStack;        // 用来检测括号的栈

    public WikiParser(Elements paragraph) {
        this.paragraph = paragraph;
        this.parenthesisStack = new ArrayDeque<String>();
    }



    /**
     * @Author Ragty
     * @Description 判断节点中包含的链接是否有效(可在此定义规则)
     * @Date 14:58 2019/4/16
     **/
    private boolean vaildLink(Element element) {

        // 验证标签名字
        if (!element.tagName().equals("a")) {
            return false;
        }
        // 验证是否为斜体
        if (isItalic(element)) {
            return false;
        }
        // 判断有无括号
        if (isParens(element)) {
            return false;
        }
        // 判断是否为书签
        if (startsWith(element,"#")) {
            return false;
        }
        // 判断是不是维基百科的帮助链接
        if (startsWith(element,"/wiki/Help:")) {
            return false;
        }
        return true;
    }



    /**
     * @Author Ragty
     * @Description 判断所给节点是否包含斜体(标签不包含斜体)
     * @Date 14:44 2019/4/16
     **/
    private boolean isItalic(Element start) {
        for (Element element=start; element!=null; element=element.parent()) {
            if (element.tagName().equals("i") || element.tagName().equals("em")) {
                return true;
            }
        }
        return false;
    }


    /**
     * @Author Ragty
     * @Description 检查堆栈上是否有任何括号(可能嵌套)
     * @Date 14:48 2019/4/16
     **/
    private boolean isParens(Element element) {
        return !parenthesisStack.isEmpty();
    }


    /**
     * @Author Ragty
     * @Description 检测链接是否以给定的某个元素做开头
     * @Date 14:55 2019/4/16
     **/
    private boolean startsWith(Element element, String s) {
        return (element.attr("href").startsWith(s));
    }


    /**
     * @Author Ragty
     * @Description  如果为有效链接，返回节点，不是则返回空
     * @Date 15:40 2019/4/16
     **/
    private Element processElement(Element element) {
        return vaildLink(element) ? element : null;
    }


    /**
     * @Author Ragty
     * @Description 验证符合的节点，主要是做括号验证
     * @Date 16:15 2019/4/16
     **/
    private void processTextNode(TextNode node) {
        StringTokenizer st = new StringTokenizer(node.text()," ()",true); //注意这里集合里有空格，它会自动匹配这三个符号
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals( "(" )) {
                parenthesisStack.push(token);  //入栈
            }
            if (token.equals( ")" )) {
                if (parenthesisStack.isEmpty()) {
                    System.err.println("Warning: unbalanced parentheses.");
                }
                parenthesisStack.pop();        //出栈
            }
        }
    }


    /**
     * @Author Ragty
     * @Description  找到第一个有效连接的段落
     * @Date 16:50 2019/4/16
     **/
    private Element findFirstLinkPara(Node root) {
        Iterable<Node> wk = new WikiNodeIterable(root);

        for (Node node : wk) {
            if (node instanceof TextNode) {
                processTextNode((TextNode) node);  //检测括号
            }
            if (node instanceof Element) {
                Element firstLink = processElement((Element) node);
                if (firstLink != null) {
                    return firstLink;
                }
            }
        }
        return null;
    }


    /**
     * @Author Ragty
     * @Description 寻找有效链接的段落
     * @Date 16:56 2019/4/16
     **/
    public Element findFirstLink() {
        for (Element para: paragraph) {
            Element firstLink = findFirstLinkPara(para);
            if (firstLink != null) {
                return firstLink;
            }
            if (!parenthesisStack.isEmpty()) {  //全局检测
                System.err.println("Warning: unbalanced parentheses.");
            }
        }
        return null;
    }


}
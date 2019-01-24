package JavaSpider.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import JavaSpider.spider.Spider;

public class Imooc {
	public String question;
	public String quesUrl;
	public String quesDescription;
	public Map<String,String> answers;
	public String nextUrl;

	/**
	 * @auther: Ragty
	 * @describe: 爬取慕课问答界面的问题及回答
	 * @param: [url]
	 * @return:
	 * @date: 2019/1/23
	 */
	public Imooc(String url) {
		question="";
		quesUrl=url;
		quesDescription="";
		answers = new HashMap<String,String>();
		nextUrl="";
	
		//获取单个问题页面源码
		String codeSource = Spider.getSource(url);
		
		//正则获取question
		Pattern pattern=Pattern.compile("js-qa-wenda-title.+?>(.+?)</h1>");
	    Matcher matcher=pattern.matcher(codeSource);
	    if(matcher.find()){
	    	question = matcher.group(1);
	    }
	    
		//正则表达式获取问题描述
	    pattern=Pattern.compile("js-qa-wenda.+?rich-text\">(.+?)</div>");
	    matcher=pattern.matcher(codeSource);
	    if(matcher.find()){
	    	quesDescription = matcher.group(1).replace("<p>", "").replace("</p>", "");
	    }
	    
		//正则表达式获取答案列表
	    pattern=Pattern.compile("nickname.+?>(.+?)</a>.+?answer-desc rich-text aimgPreview.+?>(.+?)</div>");
	    matcher=pattern.matcher(codeSource);
	    while(matcher.find()){
	    	String answer = matcher.group(2).replace("<p>", "");
	    	answer = answer.replace("</p>", "");
	    	answer = answer.replace("<br />", "");
	    	String name = matcher.group(1);
	    	answers.put(name.trim(), answer.trim());
	    }
	    
		//正则表达式获取下一个url	爬取获取相关问题的url
	    pattern=Pattern.compile("class=\"r relwenda\".+?href=\"(.+?)\".+?</a>");//获取回答者name
	    matcher=pattern.matcher(codeSource);
	    while(matcher.find()){
	    	nextUrl="http://www.imooc.com"+matcher.group(1);
			//只取第一个推荐
	    	if(!nextUrl.equals(quesUrl)){
	    		break;
	    	}
	    }
	}

	@Override
	public String toString() {
		return "问题为:"+ question +"\n问题地址为:"+quesUrl+
                "\n问题的表述为:"+quesDescription+"\n"
                + "回答的内容为:"+answers+"\n指向下一个链接地址为:"+nextUrl+"\n";
	}

}

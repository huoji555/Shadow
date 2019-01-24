package JavaSpider.spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Spider {
	
	/**
	 * @auther: Ragty
	 * @describe: 爬虫爬取网页源码
	 * @param: [url]
	 * @return: java.lang.String
	 * @date: 2019/1/23
	 */
	public static String getSource (String url) {
		BufferedReader reader = null;
		String result = "";
		try {
			URL realurl = new URL(url);
			URLConnection conn = realurl.openConnection();		//连接外部url

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ( (line = reader.readLine()) != null ) {
				result += line;
			}
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	
}

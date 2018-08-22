package kop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;

public class BaiduExcel {
	

    public static void main(String[] args) throws IOException {
		
    	String accessToken = getAuth("aI62Wj4bu6ZOF46R7taLstZ8", "OUBy6goyji2IU4yWjlvlbFxXF6dKGgcP");
    	String request_id = requestPic("D:/007.jpg",accessToken);
    	
    	//定时器
    	Timer timer=new Timer();   
    	timer.schedule(new TimerTask(){   
    	public void run(){   
    		String url = getUrl(request_id,accessToken);
    		download download = new download();
    		try {
				download.downLoadFromUrl(url, "识别文件.xls", "D://", accessToken);
				System.out.println("识别成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	this.cancel();}},50000);            //延迟10s进行，否则显示进行中（可根据文件大小调整时间）
    	
	}
	
	
	
    /**
     * @author Ragty
     * @param  获取请求id
     * @serialData 2018.8.22
     * @param filePath
     * @return
     */
	public static String requestPic(String filePath,String accessToken) {
		String otherHost = "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/request";

		try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String result = HttpUtil.post(otherHost, accessToken, params);
            JsonAnalyze jsonAnalyze = new JsonAnalyze();
        	
        	Map<String, Object> map = jsonAnalyze.json2Map(result);
        	Object a = map.get("result");
        	String zz = jsonAnalyze.object2Json(a);
        	String zz1 = zz.substring(1, zz.length()-1);
        	jsonAnalyze.json2Map(zz1);
        	String result1 = jsonAnalyze.json2Map(zz1).get("request_id").toString();
        	return result1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取异步请求到的excel地址
	 * @param  2018.8.22
	 * @param request_id
	 * @return
	 */
	public static String getUrl(String request_id,String accessToken) {
		    String otherHost = "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/get_request_result";
	        
	        try {
	           String params = URLEncoder.encode("request_id", "UTF-8") + "=" + URLEncoder.encode(request_id, "UTF-8");
	            /**
	             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	             */
	            String result = HttpUtil.post(otherHost, accessToken, params);
	            
	            JsonAnalyze jsonAnalyze = new JsonAnalyze();
	        	Map<String, Object> map = jsonAnalyze.json2Map(result);
	        	Object a = map.get("result");
	        	String zz = jsonAnalyze.object2Json(a);
	            Map<String, Object> map1 = jsonAnalyze.json2Map(zz);
	        	
	            String url = map1.get("result_data").toString();
	            return url;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		return null;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取当前的token
	 * @serialData 2018.8.22
	 * @param ak
	 * @param sk
	 * @return
	 */
	public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
	

}

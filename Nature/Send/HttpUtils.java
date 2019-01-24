package Nature.Send;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpUtils {


    /*
     * @auther: Ragty
     * @describe: Get请求远程接口
     * @param: [url, parameters]
     * @return: java.lang.String
     * @date: 2019/1/16
     */
    public static String sendGet(String url, Map<String, String> parameters) {

        String result="";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数

        try {
            // 编码请求参数
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }

            String full_url = url + "?" + params;
            System.out.println("请求链接为:"+full_url);
            // 创建URL对象
            URL connURL = new java.net.URL(full_url);
            // 打开URL连接
            HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (in != null) { in.close(); }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result ;

    }



    /*
     * @auther: Ragty
     * @describe: Post请求远程接口
     * @param: [url, parameters]
     * @return: java.lang.String
     * @date: 2019/1/16
     */
    public static String sendPost(String url, Map<String, String> parameters) {

        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数

        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }

            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) { out.close(); }
                if (in != null) { in.close(); }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String[] args) {
        /*Map<String,String> request = new HashMap<String,String>();
        Map<String,String> content = new HashMap<String,String>();

        String path = ReadPorperties.getParams("UrlPath");
        String notifyid = UUID.randomUUID().toString().replace("-","");
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        String date = sdf.format(new Date());
        String sign = Base64.encodeBase64String(DigestUtils.md5Hex(content + ReadPorperties.getParams("warehouseRrs.md5Key").getBytes()) );
        content = URLEncoder.encode(AESUtils.encrypt(),ReadPorperties.getParams("warehouseRrs.aesKey"),"utf-8");

        request.put("notifyid",notifyid);               //消息ID
        request.put("notifytime",date);                 //消息通知时间 YYYY-MM-DD hh:mm:ss
        request.put("butype","rrs_stockquery");         //通知类型，方法接口名
        request.put("source","JWYDZR");                 //来源，根据系统区分
        request.put("type","json");                     //报文格式，分为json和xml
        request.put("sign",sign);                       //签名
        request.put("content",content);                 //消息内容根据具体业务定义，加密后的报文需要URLEncoder
        String result = sendPost(path,request);
        System.out.println(result);*/
    }


}

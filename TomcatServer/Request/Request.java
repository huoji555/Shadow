package TomcatServer.Request;

import TomcatServer.Util.GetParam;
import com.hjy.util.GetParm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private Socket client;                  //客户端
    private BufferedReader reader;
    private String url;                     //请求资源
    private String method;                  //请求方式
    private String protocal;                //请求协议
    private Map<String,String> map;         //参数列表
    private GetParam getParam;              //请求参数分割工具


    public Request() {}

    /*
     * @auther: Ragty
     * @describe: 接收并客户端(Client)发出的请求，获取请求的链接及参数
     * @param: [client]
     * @return:
     * @date: 2019/1/25
     */
    public Request(Socket client) {
        this.client = client;
        map = new HashMap<>();
        getParam = new GetParam();
        try {
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String firstLine = reader.readLine();
            String[] spilt = firstLine.split(" ");

            method = spilt[0];
            url = spilt[1];
            protocal = spilt[2];
            System.out.println(url);

            if (method.equalsIgnoreCase("get")) {
                if(url.contains("?")) {
                    String[] split2 = url.split("[?]");
                    url = split2[0];                               //重新定义url
                    String property = split2[1];
                    map = getParam.getParam(property);             //分割参数
                 }
            } else {
                int length = 0;
                while (reader.ready()) {                           //确保已经缓冲完毕
                    String line = reader.readLine();
                    if (line.contains("Content-Length")) {
                        String[] spilt2 = line.split(" ");
                        length = Integer.parseInt(spilt2[1]);
                    }
                    if (line.equals("")) {                          //Post方法无参数
                        break;
                    }
                }
                String info = null;
                char[] ch = new char[length];
                reader.read(ch , 0, length);
                info = new String(ch, 0, length);
                map = getParam.getParam(info);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Socket getClient() { return client; }
    public void setClient(Socket client) { this.client = client; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getProtocal() { return protocal; }
    public void setProtocal(String protocal) { this.protocal = protocal; }

}

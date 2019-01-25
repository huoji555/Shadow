package TomcatServer.Response;

import java.io.*;
import java.net.Socket;

public class Response {

    private Socket client;
    private PrintStream ps;
    private String path = null;

    public Response() {};

    /*
     * @auther: Ragty
     * @describe: 获取服务端传输给客户端的输出流
     * @param: [client]
     * @return:
     * @date: 2019/1/24
     */
    public Response(Socket client) {
        super();
        this.client = client;
        try {
            ps = new PrintStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /*
     * @auther: Ragty
     * @describe:根据当前路径将服务器上有的资源用流传给客户端
     * @param: []
     * @return: void
     * @date: 2019/1/24
     */
    public void forword() throws FileNotFoundException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(path));
            ps.println("HTTP/1.1 200 OK");
            ps.println();
            byte[] buf = new byte[1024];
            int length = 0;
            while ( (length = fis.read(buf)) != -1 ) {
                ps.write(buf, 0, length);
                ps.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {fis.close();}
                if (ps != null) {ps.close();}
                if (client != null) {client.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    /*
     * @auther: Ragty
     * @describe: 分情况处理请求Url(1.为空，返回默认资源 2.存在，加载该资源 3.不存在，返回错误信息)
     * @param: [url]
     * @return: void
     * @date: 2019/1/24
     */
    public void forword(String url) {

        if (url.equals("/")) {
            path = "src/TomcatServer/source/2.jpg";
        } else {
            path = "src/TomcatServer/source" + url;
            File file = new File(path);
            if (!file.exists()) { path = "src/TomcatServer/source/error.html"; }
        }

        try {
            forword();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}

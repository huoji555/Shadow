package TomcatServer.server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server {

    private ServerSocket server;            //服务
    private int port;                       //端口号
    private static Properties prop;         //用于读取配置文件

    //配置参数读取位置
    static {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("src/TomcatServer/source/property.properties")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    /*
     * @auther: Ragty
     * @describe: 初始化服务器
     * @param: []
     * @return: void
     * @date: 2019/1/24
     */
    public void init() {
        try {
            port = Integer.parseInt(prop.getProperty("port"));
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * @auther: Ragty
     * @describe: 在当前线程内，创建一个Client服务
     * @param: []
     * @return: void
     * @date: 2019/1/25
     */
    public void recive() {
        try {
            Socket client = server.accept();                       //监听服务器，平时等待连接，有client则创建客户端实例(阻塞的)
            ServerThread thread = new ServerThread(client);        //处理客户端请求,并返回请求页面
            thread.start();
            thread.join();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Server() {}


    /*
     * @auther: Ragty
     * @describe: 启动Tomcat服务器
     * @param: [args]
     * @return: void
     * @date: 2019/1/25
     */
    public static void main(String[] args) {
        Server server2 = new Server();
        server2.init();
        while (true) {
            server2.recive();
        }
    }

}



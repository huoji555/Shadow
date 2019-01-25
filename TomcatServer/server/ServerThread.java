package TomcatServer.server;

import TomcatServer.Request.Request;
import TomcatServer.Response.Response;

import java.net.Socket;

public class ServerThread extends Thread{
    private Socket client;
    public ServerThread () {}
    public ServerThread (Socket client) {this.client = client;}


    /**
     * @auther: Ragty
     * @describe: 处理客户端请求并返回结果
     * @param: []
     * @return: void
     * @date: 2019/1/25
     */
    @Override
    public void run() {
        Request request = new Request(client);           //获取请求链接的地址，及参数
        Response response = new Response(client);        //根据请求地址，返回服务器上对应的资源
        response.forword(request.getUrl());
    }

}

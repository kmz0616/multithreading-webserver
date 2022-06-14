package server;

import data.MyData;

import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    public static int PORT;//监听端口

    public Server(int port) {
        this.PORT = port;
    }


    public void run() {//重写run方法
        //主程序
        ServerSocket serverSocket = null;//声明ServerSocket对象
        try {
            serverSocket = new ServerSocket(PORT);//创建serverSocket对象

            System.out.println("开始监听 " + PORT);
            while (MyData.isRun) {
                //在允许服务器运行的情况下监听端口
                Socket socket = serverSocket.accept();//开始监听
                System.out.println("收到请求 ");
                //将socket交给RequestProcess处理
                RequstProcess requstProcess = new RequstProcess(socket);
                requstProcess.start();
            }
            //退出循环 server停止运行
            //关闭ServerSocket
            serverSocket.close();
            serverSocket = null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("端口" + PORT + "监听失败 " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(7777);//创建线程类对象
        new Thread(server).start();//Thread启用线程
    }
}

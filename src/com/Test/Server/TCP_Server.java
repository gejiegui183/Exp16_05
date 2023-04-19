package com.Test.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCP_Server {
    public static ServerSocket serverSocket;
    private static Socket socket;

    public static void main(String[] args) {

        try {
            System.out.println("服务器已经启动....");
            serverSocket = new ServerSocket(10086);
            while (true) {
                //阻塞的。只有当socket 有连接后才会执行后面的程序
                //有可能断开。也有可能多个客户端连接，所以这里使用 死循环，一直接收
                socket = serverSocket.accept();

                new Thread(new Server_getMessage(socket)).start();
                new Thread(new Server_sendMessage(socket)).start();

            }
        } catch (IOException e) {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
    }
}

class Server_getMessage implements Runnable {
    private Socket socket;


    public Server_getMessage(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len=0;
            while ((len=is.read(bytes))!=-1) {  //死循环一直读取消息
                String message = new String(bytes,0,len);
                System.out.println();
                System.out.println("收到消息：" + message);
            }

        } catch (IOException e) {
            try {
                socket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        }
    }
}

class Server_sendMessage implements Runnable {
    private Socket socket;


    public Server_sendMessage(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("已经有客户端连接------");
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            while (true) {   //scanner 默认是阻塞的，所以这个死循环必须得通畅以后才可以继续执行，所以必须输入一次后才可以继续执行
                System.out.print("发送:");
                String message = scanner.next();
                os.write(message.getBytes());
                os.flush();
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        }
    }
}
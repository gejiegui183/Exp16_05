package com.Test.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;



/**
 * Created by kumamon on 2021/5/28.
 */
public class TCP_Client {
    private static Socket socket;
    public static boolean connection_state = false;

    public static void main(String[] args){
        while (!connection_state) {
            connect();
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void connect(){
        try {
            socket = new Socket("127.0.0.1", 10086);
            connection_state = true;

            new Thread(new Client_listen(socket)).start();
            new Thread(new Client_send(socket)).start();
            new Thread(new Client_heart(socket)).start();
        }catch (Exception e){
            e.printStackTrace();
            connection_state = false;
        }
    }

    public static void reconnect(){
        while (!connection_state){
            System.out.println("正在尝试重新链接.....");
            connect();
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

class Client_listen implements Runnable{
    private Socket socket;


    Client_listen(Socket socket){
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            InputStream ois = socket.getInputStream();

            int len=0;
            byte[] bytes = new byte[1024];
            while ((len=ois.read(bytes))!=-1){
                System.out.println(new String(bytes,0,len));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Client_send implements Runnable{
    private Socket socket;


    Client_send(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream oos = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("请输入你要发送的信息：");
                String string = scanner.nextLine();
                oos.write(string.getBytes());
                oos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                socket.close();
                TCP_Client.connection_state = false;
                TCP_Client.reconnect();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
}

class Client_heart implements Runnable{
    private Socket socket;

    Client_heart(Socket socket){
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            OutputStream oos = socket.getOutputStream();
            System.out.println("心跳包线程已启动...");
            int i = 0;
            while (true){
                i += 1;
                Thread.sleep(5000);
                String signal="心跳包.." + i;
                oos.write(signal.getBytes());
                oos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                socket.close();
                TCP_Client.connection_state = false;
                TCP_Client.reconnect();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
}

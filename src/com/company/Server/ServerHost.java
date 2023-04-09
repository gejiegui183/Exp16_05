package com.company.Server;

import com.company.Client.Reciver;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class ServerHost {
    HashMap<String , String> clientList = new HashMap<String, String>();
    ServerSocket serverSocket;
    int port = 26000;

    public void serverStart(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(date) + "#serverHost:服务器已启动");
        ClientInfoGet clientInfoGet = new ClientInfoGet(serverSocket , clientList);
        clientInfoGet.start();
        System.out.println(dateFormat.format(date) + "#serverHost:开始监听客户端");
    }

}

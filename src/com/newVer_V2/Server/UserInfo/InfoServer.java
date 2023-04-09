package com.newVer_V2.Server.UserInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class InfoServer {
    HashMap<String , String> clientList = new HashMap<String, String>();
    ServerSocket infoServerSocket;
    int port = 26000;

    public void serverStarter(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            infoServerSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(date) + "##客户端监听器启动<-InfoServer");
        System.out.println(dateFormat.format(date) + "##开始监听用户端连接<-InfoServer");
        ClientInfoSaver clientInfoSaver = new ClientInfoSaver(infoServerSocket , clientList);
        clientInfoSaver.start();

    }

}

package com.newVer_V2.Server.UserInfo;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ClientInfoSaver extends Thread{
    HashMap<String , String> clientList = new HashMap<String, String>();
    ServerSocket serverSocket;

    public ClientInfoSaver(ServerSocket serverSocket , HashMap<String , String> clientList) {
        this.serverSocket = serverSocket;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(dateFormat.format(date) + "##用户连接监听器启动<-ClientInfoSaver");
        Socket infoAccept = null;
        while (true){
            try {
                infoAccept = serverSocket.accept();
                InputStream clientInfoIn = infoAccept.getInputStream();
                int msgLen = clientInfoIn.read();
                byte [] info = new byte[msgLen];
                clientInfoIn.read(info);
                String readInfo = new String(info , 0 , msgLen);
                JSONObject js = new JSONObject(readInfo);
                String userName = (String) js.get("userName");
                String userID = (String) js.get("userID");
                clientList.put(userName , userID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(dateFormat.format(date) + "##当前客户端连接数量为"+ clientList.size() +"<-ClientInfoSaver");
        }
    }
}

package com.newVer_V3.Server.UserServer;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class OnlineUserSave extends Thread{
    HashMap<String , String> onLineList = new HashMap<>();
    ServerSocket userSaver;

    public OnlineUserSave(ServerSocket serverSocket){
        this.userSaver = serverSocket;
    }

    @Override
    public void run() {
        Socket accept = null;
        while (true) {
            java.util.Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println(dateFormat.format(date) + "正在监听客户端连接情况<-OnlineUserSave");
            try {
                accept = userSaver.accept();
                InputStream in = accept.getInputStream();
                int infoLen = in.read();
                byte [] infoList = new byte[infoLen];
                in.read(infoList);
                String infoMsg = new String (infoList , 0 , infoLen);
                JSONObject js = new JSONObject(infoMsg);
                int loginStatus = (Integer)js.get("loginStatus");
                String userName = (String) js.get("userName");
                String userID = (String) js.get("userID");
                if (loginStatus == 1) {
                    onLineList.put(userID , userName);
                }
                else if(loginStatus == 2){
                    onLineList.remove(userID);
                }
                System.out.println(dateFormat.format(date) + "当前连接用户数量为："+ onLineList.size() + "<-OnlineUserSave");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.newVer_V3.Server.UserServer;

import com.newVer_V3.DataBase.UserInfoDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserConnServer {
    ServerSocket userInfoServer;
    int port = 18000;

    public UserConnServer(){}

    public void startServer(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            userInfoServer = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(date) + "�û���Ϣ����������<-UserConnServer");
        System.out.println(dateFormat.format(date) + "�ͻ��˼�����<-UserConnServer");
        OnlineUserSave onlineUserSave = new OnlineUserSave(userInfoServer);
        onlineUserSave.start();
        System.out.println(dateFormat.format(date) + "�û���Ϣ����������<-UserConnServer");
    }
}

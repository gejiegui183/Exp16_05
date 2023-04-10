package com.newVer_V3.Server.UserServer;

import com.newVer_V3.DataBase.UserInfoDB;

import java.io.IOException;
import java.net.ServerSocket;

public class UserConnServer {
    ServerSocket userInfoServer;
    int port = 18000;

    public UserConnServer(){}

    public void startServer(){
        try {
            userInfoServer = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("用户信息服务器开启<-UserConnServer");

    }
}

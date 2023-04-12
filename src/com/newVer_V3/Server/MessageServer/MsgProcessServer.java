package com.newVer_V3.Server.MessageServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgProcessServer {
    ServerSocket msgReciver;
    int port = 18001;

    public MsgProcessServer(){}

    public void startServer(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(dateFormat.format(date) + "#Server is started<-MsgProcessServer");
        try {
            msgReciver = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(date) + "#Chating Server is start<-MsgProcessServer");
        ClientMsgProcessor clientMsgProcessor = new ClientMsgProcessor(msgReciver);
        clientMsgProcessor.start();
    }
}

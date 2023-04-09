package com.company.MsgServer;

import com.company.Client.Reciver;
import com.company.MsgServer.ClientMessageRecive;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgServer {
    ServerSocket msgServer;
    int msgPort = 26002;

    public void startServer(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            msgServer = new ServerSocket(msgPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(date) + "#MsgServer启动");
        ClientMessageRecive clientMessageRecive = new ClientMessageRecive(msgServer);
//        MessageReciver messageReciver = new MessageReciver(msgServer);
        Reciver reciver = new Reciver(msgServer);
        clientMessageRecive.start();
        reciver.start();
//        messageReciver.start();
        System.out.println(dateFormat.format(date) + "#ClientMessageRecive接收消息中");
    }
}

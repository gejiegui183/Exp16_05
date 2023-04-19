package com.newVer_V2.Server.Message;

import java.sql.*;
import com.newVer_V2.Client.ChatFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageServer {
    ServerSocket messageServerSocket;
    ChatFrame chatFrame;
    int port = 26002;

    public MessageServer(){}

    public MessageServer(ChatFrame chatFrame){
        this.chatFrame = chatFrame;
    }

    public void serverStarter(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(chatFrame + "<-serverStarter");
        try {
            messageServerSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(date)+"##¿Í»§¶Ë¼àÌýÆ÷Æô¶¯<-MessageServer");
        ClientMsgSaver clientMsgSaver = new ClientMsgSaver(messageServerSocket , chatFrame);
        clientMsgSaver.start();
    }
}

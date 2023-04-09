package com.newVer_V2.Server.Message;

import com.newVer_V2.Client.ChatFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageServer {
    ServerSocket messageServerSocket;
    int port = 26002;

    public void serverStarter(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            messageServerSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(date)+"##¿Í»§¶Ë¼àÌýÆ÷Æô¶¯<-MessageServer");
        ClientMsgSaver clientMsgServer = new ClientMsgSaver(messageServerSocket);
        clientMsgServer.start();
    }
}

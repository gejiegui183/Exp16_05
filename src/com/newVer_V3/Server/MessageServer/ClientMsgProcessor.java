package com.newVer_V3.Server.MessageServer;

import com.newVer_V3.ConfigData.Config;
import com.newVer_V3.DataBase.MessageInfoDB;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientMsgProcessor extends Thread implements Config {
    ServerSocket processor;

    public ClientMsgProcessor() {
    }

    public ClientMsgProcessor(ServerSocket processor) {
        this.processor = processor;
    }

    @Override
    public void run() {
        Socket accept = null;
        while (true) {
            java.util.Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println(dateFormat.format(date) + "#Reciving message<-ClientMsgProcessor");
            try {
                accept = processor.accept();
                InputStream in = accept.getInputStream();
                int msgLen = in.read();
                byte [] data = new byte[msgLen];
                in.read(data);
                String reciveMsg = new String(data , 0 , msgLen);
                JSONObject js = new JSONObject(reciveMsg);
                int msgType = (Integer) js.get("msgType");
                String userID = (String) js.get("userID");
                String targetID = (String) js.get("targetID");
                String sendTime = (String) js.get("sendTime");
                String content = (String) js.get("content");
                System.out.println(dateFormat.format(date) + "#DB is saving<-ClientMsgProcessor");
                MessageInfoDB messageInfoDB = new MessageInfoDB();
                messageInfoDB.saveMessage(reciveMsg);
                System.out.println(dateFormat.format(date) + "#Saved successed<-ClientMsgProcessor");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.newVer_V3.Server.MessageServer;

import com.newVer_V3.DataBase.MessageInfoDB;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientMsgProcessor extends Thread{
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
            System.out.println(dateFormat.format(date) + "服务器正在接收聊天信息<-ClientMsgProcessor");
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
                System.out.println(dateFormat.format(date) + "数据库写入数据<-ClientMsgProcessor");
                MessageInfoDB messageInfoDB = new MessageInfoDB();
                messageInfoDB.saveMessage(reciveMsg);
                System.out.println(dateFormat.format(date) + "写入成功<-ClientMsgProcessor");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

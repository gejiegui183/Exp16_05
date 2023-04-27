package com.newVer_V5.Server.MessageServer;

import com.newVer_V3.DataBase.MessageInfoDB;
import com.newVer_V5.DataBase.InfoCheck;
import com.newVer_V5.DataBase.MessageCheck;
import com.newVer_V5.InfoData.Config;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InfoGetter extends Thread implements Config {
    ServerSocket serverSocket;

    public InfoGetter() {
    }

    public InfoGetter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket accept = null;
        while (true) {
            try {
                accept = serverSocket.accept();
                InputStream in = accept.getInputStream();
                int len = in.read();
                byte [] data = new byte[len];
                in.read(data);
                String message = new String(data , 0 , len);
                MessageCheck messageCheck = new MessageCheck();
                messageCheck.saveMessage(message);
                JSONObject js = new JSONObject(message);
                String userID = (String) js.get("userID");
                String friendID = (String) js.get("friendID");
                String content = (String) js.get("content");
                String sendTime = (String) js.get("sendTime");
                InfoCheck info = new InfoCheck();
                String sender = info.getFriendName(friendID);
                if(!msgServerLink.containsKey(userID)){
                    msgServerLink.put(userID , accept);
                }
                String reciveMsg = sendTime + "##" + sender + "##" + content;
                byte [] reciveMessage = reciveMsg.getBytes();
                Socket newAccpet = msgServerLink.get(friendID);
                System.out.println(newAccpet);
                OutputStream out = newAccpet.getOutputStream();
                out.write(reciveMessage.length);
                out.write(reciveMessage);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

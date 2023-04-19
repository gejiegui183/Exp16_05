package com.newVer_V5.Server.MessageServer;

import com.newVer_V3.DataBase.MessageInfoDB;
import com.newVer_V5.DataBase.MessageCheck;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InfoGetter extends Thread{
    ServerSocket serverSocket;

    public InfoGetter() {
    }

    public InfoGetter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            Socket accept = null;
            try {
                accept = serverSocket.accept();
                InputStream in = accept.getInputStream();
                int len = in.read();
                byte [] data = new byte[len];
                in.read(data);
                String message = new String(data , 0 , len);
                MessageCheck messageCheck = new MessageCheck();
                messageCheck.saveMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

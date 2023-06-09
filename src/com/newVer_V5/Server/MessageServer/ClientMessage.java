package com.newVer_V5.Server.MessageServer;

import com.newVer_V5.Time.TimeGetter;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientMessage {
    ServerSocket serverSocket;
    int port = 25000;

    public ClientMessage(){}

    public void initServer(){
        System.out.println(new TimeGetter().getTime() + "MessageSaveServer is started <- ClientMessage");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new InfoGetter(serverSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

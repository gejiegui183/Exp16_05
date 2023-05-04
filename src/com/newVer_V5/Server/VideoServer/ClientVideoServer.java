package com.newVer_V5.Server.VideoServer;


import java.io.IOException;
import java.net.ServerSocket;

public class ClientVideoServer {
    ServerSocket serverSocket;
    int port = 25002;

    public void initServer(){
        try {
            serverSocket = new ServerSocket(25002);
            while (true) {
                new InfoGetter(serverSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.newVer_V4.Server.UserConnect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Connector extends Thread{
    HashMap<String , String> user = new HashMap<>();
    ServerSocket serverSocket;

    public Connector(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket accept = null;
        while (true) {
            try {
                accept = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

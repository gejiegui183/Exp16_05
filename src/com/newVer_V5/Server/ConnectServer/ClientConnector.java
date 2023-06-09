package com.newVer_V5.Server.ConnectServer;

import com.newVer_V4.Server.Connection.InfoProcess;
import com.newVer_V5.Time.TimeGetter;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientConnector {
    ServerSocket serverSocket;
    int port = 24999;

    public ClientConnector(){}

    public void initServer(){
        System.out.println(new TimeGetter().getTime() + "#ConnectServer is started <- ClientConnector");
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

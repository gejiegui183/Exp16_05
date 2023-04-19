package com.newVer_V4.Server.Connection;

import com.newVer_V4.Time.TimeGet;

import java.io.IOException;
import java.net.ServerSocket;

public class UserConnector {
    ServerSocket serverSocket;
    int port;

    public void serverInit(){
        System.out.println(new TimeGet().getTime() + "#ConnectServer is started <- UserConnector");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new InfoProcess(serverSocket).start();
            }
        } catch (IOException e) {
            try {
                serverSocket.close();
            }
            catch (Exception e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}

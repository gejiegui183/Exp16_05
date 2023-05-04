package com.newVer_V5.Server.RequireServer;

import com.newVer_V5.Time.TimeGetter;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientRequire {
    int port = 25001;
    ServerSocket serverSocket;

    public void initServer(){
        System.out.println(new TimeGetter().getTime() + "#RequireServer is start <-ClientRequire");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new InfoGetter(serverSocket).start();
//                new VideoFlue(serverSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

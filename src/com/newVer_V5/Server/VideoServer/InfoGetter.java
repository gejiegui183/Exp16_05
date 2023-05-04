package com.newVer_V5.Server.VideoServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InfoGetter extends Thread{
    ServerSocket serverSocket;

    public InfoGetter(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            int len = 0;
            byte [] data = new byte[1024];
            while ((len = in.read(data)) != -1){
                String msg = new String(data);
                System.out.println(msg + "<<VideoDebug");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

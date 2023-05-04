package com.newVer_V5.Server.RequireServer;

import com.newVer_V5.InfoData.Config;
import com.newVer_V5.Time.TimeGetter;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InfoGetter extends Thread implements Config {
    ServerSocket serverSocket;
    int requireMsg = 0;

    public InfoGetter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void getID(String id){

    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            DataInputStream inFlue = new DataInputStream(in);
            while (true) {
                int width = inFlue.readInt();
                int height = inFlue.readInt();
                int video [][] = new int[width][height];
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        video[i][j] = inFlue.readInt();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

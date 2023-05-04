package com.newVer_V5.Server.MessageServer;

import com.newVer_V5.InfoData.Config;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RequireGetter extends Thread implements Config {
    ServerSocket serverSocket;

    public RequireGetter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        while (true) {
            try {
                socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                int len = in.read();
                byte [] data = new byte[len];
                in.read(data);
                String msg = new String(data);
                JSONObject js = new JSONObject(msg);
                String userID = (String) js.get("userID");
                String friendID = (String) js.get("friendID");
                int msgType = (Integer) js.get("msgType");
                if(msgType == 3){
                    System.out.println("<<debug from require");
                    Socket requireAsk = msgServerLink.get(friendID);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

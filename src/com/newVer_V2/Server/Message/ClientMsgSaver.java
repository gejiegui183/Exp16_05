package com.newVer_V2.Server.Message;

import org.json.JSONObject;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientMsgSaver extends Thread{
    ServerSocket clientMsgSocket;

    public ClientMsgSaver(ServerSocket clientMsgSocket) {
        this.clientMsgSocket = clientMsgSocket;
    }

    @Override
    public void run() {
        Socket messageAccept = null;
        while (true) {
            try {
                messageAccept = clientMsgSocket.accept();
              
                InetAddress targetIP = messageAccept.getInetAddress();
                int targetPort = messageAccept.getPort();
                InputStream messageIn = messageAccept.getInputStream();
                int msgReciverLen = messageIn.read();
                byte [] msgData = new byte[msgReciverLen];
                messageIn.read(msgData);
                String item = new String(msgData , 0 , msgReciverLen);
                new ClientMsgReply(item , targetIP , targetPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

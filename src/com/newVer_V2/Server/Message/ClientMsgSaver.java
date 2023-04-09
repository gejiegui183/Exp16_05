package com.newVer_V2.Server.Message;

import com.newVer_V2.Client.ChatFrame;
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
    ChatFrame chatFrame;

    public ClientMsgSaver(ServerSocket clientMsgSocket , ChatFrame chatFrame) {
        this.clientMsgSocket = clientMsgSocket;
        this.chatFrame = chatFrame;
    }

    @Override
    public void run() {
        Socket messageAccept = null;
        while (true) {
            try {
                System.out.println(chatFrame + "<-ClientMsgSaver");
                messageAccept = clientMsgSocket.accept();
                InetAddress targetIP = messageAccept.getInetAddress();
                int targetPort = messageAccept.getPort();
                InputStream messageIn = messageAccept.getInputStream();
                int msgReciverLen = messageIn.read();
                byte [] msgData = new byte[msgReciverLen];
                messageIn.read(msgData);
                String item = new String(msgData , 0 , msgReciverLen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

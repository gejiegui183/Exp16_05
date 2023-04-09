package com.company.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Reciver extends Thread{
    ServerSocket reciverServer;
    JPanel panel;

    public Reciver() {
    }

    public Reciver(JPanel panel) {
        this.panel = panel;
    }

    public Reciver(ServerSocket reciverServer) {
        this.reciverServer = reciverServer;
    }

    @Override
    public void run() {
        Socket accept = null;
        while (true){
            try {
                accept = reciverServer.accept();
                InputStream reciverIn = accept.getInputStream();
                int reciveMsgLen = reciverIn.read();
                byte [] reciverData = new byte[reciveMsgLen];
                reciverIn.read(reciverData);
                String reciverMsg = new String(reciverData , 0 , reciveMsgLen);
//                System.out.println(reciverMsg + ":Reciver222");
                new newClient().msgProcess(reciverMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

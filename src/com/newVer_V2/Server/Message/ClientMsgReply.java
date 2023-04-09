package com.newVer_V2.Server.Message;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMsgReply {
    String JSONData;
    DatagramSocket sender;
    Socket senderSocket;
    InetAddress targetIP;
    int targetPort;

    public ClientMsgReply(String JSONData , InetAddress targetIP , int targetPort){
        this.JSONData = JSONData;
        this.targetIP = targetIP;
        this.targetPort = targetPort;
        infoProcess();
    }

    public void infoProcess() {
        System.out.println(targetIP + " //" + targetPort + "<-ClientMsgReply");
        byte [] data = JSONData.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(data , data.length , targetIP , targetPort);
            sender.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

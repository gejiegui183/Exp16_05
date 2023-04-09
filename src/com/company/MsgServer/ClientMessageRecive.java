package com.company.MsgServer;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientMessageRecive extends Thread{
    ServerSocket serverSocket;
    Socket accept;

    public ClientMessageRecive(){}

    public ClientMessageRecive(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        while (true) {
            try {
                accept = serverSocket.accept();
                InputStream in = accept.getInputStream();
                int msgLen = in.read();
                byte [] sendMsg = new byte[msgLen];
                in.read(sendMsg);
                String getMsg = new String(sendMsg , 0 , msgLen);
//                ClientMsgSave clientMsgSave = new ClientMsgSave(newGetMsg[1], newGetMsg[0], newGetMsg[2]);
//                System.out.println(dateFormat.format(date) + "#ClientMessageRecive正在存储数据");
//                clientMsgSave.saveMessage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

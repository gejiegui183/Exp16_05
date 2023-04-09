package com.company.Server;

import com.company.Client.FriendList;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ClientInfoGet extends Thread{
    ServerSocket serverSocket;
    HashMap<String , String> clientList = new HashMap<String, String>();
    public ClientInfoGet(ServerSocket serverSocket , HashMap<String , String> clientList) {
        this.serverSocket = serverSocket;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        while (true){
            Socket accept = null;
            try {
                accept = serverSocket.accept();
                InputStream in = accept.getInputStream();
                int msgLen = in.read();
                byte [] recive = new byte[msgLen];
                in.read(recive);
                String msg = new String(recive , 0 , msgLen);
                System.out.println(dateFormat.format(date) + "#ClientInfoGet监听器回显，当前连接为：" + msg);
                String info [] = msg.split("#");
                clientList.put(info[0] , info[1]);
                System.out.println(dateFormat.format(date) + "#ClientInfoGet客户端连接数量为:" + clientList.size());
                new FriendList().setnLineCount(clientList.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.newVer_V4.Client;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class FriendsList {
    Socket socket1 , socket2;
    String ServerIP = "127.0.0.1";
    int port1 = 10000;
    int port2 = 10001;
    String loginInfo;
    static boolean connStatus = false;

    public FriendsList(){}

    public FriendsList(String loginInfo) {
        this.loginInfo = loginInfo;
    }


    public void initFrame(){
        try {
            socket1 = new Socket(ServerIP , port1);
            connStatus = true;
            new Heart(socket1).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

    }
}

class Heart extends Thread{
    Socket socket;

    int port;

    public Heart(){}

    public Heart(Socket socket ){
       this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream out = socket.getOutputStream();
            int i = 0;
            while (true){
                JSONObject js = new JSONObject();
                i += 1;
                Thread.sleep(5000);
                String signal="ÐÄÌø°ü.." + i;
                js.put("msgType" , 0);
                js.put("content" , signal);
                String pak = js.toString();
                out.write(pak.getBytes());
                out.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                socket.close();
                FriendsList.connStatus = false;
//                TCP_Client.reconnect();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
}
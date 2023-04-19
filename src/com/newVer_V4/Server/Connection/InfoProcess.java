package com.newVer_V4.Server.Connection;

import com.newVer_V4.Time.TimeGet;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Stack;

public class InfoProcess extends Thread{
    HashMap<String , String> onLineUser = new HashMap<>();
    ServerSocket serverSocket;

    public InfoProcess(){}

    public InfoProcess(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket accept = null;
        try {
            accept = serverSocket.accept();
            InputStream in = accept.getInputStream();
            int infoLen = in.read();
            byte [] data = new byte[infoLen];
            int len = 0;
            in.read(data);
            while ((len=in.read(data)) != -1) {
                String msg = new String(data , 0 , infoLen);
                JSONObject js = new JSONObject(msg);
                int msgType = (Integer) js.get("msgType");
                if(msgType == 0){
                    String item = (String) js.get("content");
                    System.out.println(new TimeGet().getTime() + "#heartBeat:" + item + " <- InfoProcess");
                }
                if(msgType == 1){
                    int loginStatus = (Integer) js.get("loginStatus");
                    String userID = (String) js.get("userID");
                    String userName = (String) js.get("userName");
                    if(loginStatus == 1 && !onLineUser.containsKey(userID)){
                        onLineUser.put(userID , userName);
                    }
                    if(loginStatus == 0 && onLineUser.containsKey(userID)){
                        onLineUser.remove(userID);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

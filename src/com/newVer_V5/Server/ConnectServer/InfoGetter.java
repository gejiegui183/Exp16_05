package com.newVer_V5.Server.ConnectServer;

import com.newVer_V4.Server.Connection.InfoProcess;
import com.newVer_V5.InfoData.Config;
import com.newVer_V5.Time.TimeGetter;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InfoGetter extends Thread implements Config {
    ServerSocket serverSocket;

    public InfoGetter(){}

    public InfoGetter(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            Socket accept = null;
            try {
                accept = serverSocket.accept();
                InputStream in = accept.getInputStream();
                byte [] data = new byte[1024];
                int len = 0;
                while ((len = in.read(data)) != -1){
                    String message = new String(data , 0 , len);
                    JSONObject js = new JSONObject(message);
                    int msgType = (Integer) js.get("msgType");
                    String userID = (String) js.get("userID");
                    if(msgType == 0){
                        System.out.println(new TimeGetter().getTime() + "recived message:" + userID+"#"+js.get("content"));
                    }
                    if(msgType == 1){
                        String userName = (String) js.get("userName");
                        int loginStatus = (Integer)js.get("loginStatus");
                        if(!onLineList.containsKey(userID) && loginStatus == 1){
                            onLineList.put(userID , userName);
                        }
                        if(onLineList.containsKey(userID) && loginStatus == 0){
                            onLineList.remove(userID);
                        }
                    }
                }
            } catch (IOException e) {
                try {
                    accept.close();
                }
                catch (Exception e2){
                    e2.printStackTrace();
                }
                e.printStackTrace();
            }
        }


    }

}

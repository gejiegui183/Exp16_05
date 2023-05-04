package com.newVer_V5.Server.RequireServer;

import com.newVer_V5.InfoData.Config;
import com.newVer_V5.Time.TimeGetter;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InfoGetter extends Thread implements Config {
    ServerSocket serverSocket;
    int requireMsg = 0;

    public InfoGetter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            int len = 0;
            byte [] data = new byte[1024];
            while ((len = in.read(data)) != -1) {
                String msg = new String(data);
                JSONObject js = new JSONObject(msg);
                int msgType = (Integer)js.get("msgType");
                String userID1 = (String) js.get("userID");
                if(msgType == 1){
                    String userID = (String) js.get("userID");
                    if(!videoRequire.containsKey(userID)){
                        videoRequire.put(userID , socket);
                    }
                }
                if(msgType == 3){
                    String friendID = (String) js.get("friendID");
                    if (videoRequire.containsKey(friendID)) {
                        Socket requireAsk = videoRequire.get(friendID);
                        OutputStream out = requireAsk.getOutputStream();
//                        String friendName = new InfoCheck().getFriendName()
                        byte [] askMsg = "1".getBytes();
//                        out.write(askMsg.length);
//                        out.write(askMsg);
                        out.write(askMsg.length);
                        out.write(askMsg);
                        out.flush();//发送视频请求
                        System.out.println(new TimeGetter().getTime() + "#videoRequire is sended from:" + userID1 + "reciver:" + friendID + "<<Debug01");
                        InputStream requireAns = requireAsk.getInputStream();
//                        int requireLen = requireAns.read();
//                        byte [] reqMsg = new byte[requireLen];
//                        in.read(reqMsg);
//                        String requireMsg = new String(reqMsg);
                        if(requireAns.read() == 0){
                            System.out.println("用户已拒绝 << debug");
                        }
                    }
                    else {
                        System.out.println("用户未上线");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

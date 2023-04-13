package com.newVer_V4.Server.UserConnect;

import com.newVer_V4.TimeGet.TimeStamp;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ConnectorServer {
    ServerSocket server ;
    int port = 18003;

    public ConnectorServer() {
    }

    public void init(){
        System.out.println(new TimeStamp().getCurrentTime() + "#Server starting <-Connector");
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new TimeStamp().getCurrentTime() + "#Server started successfuly<-Connector");
    }


}

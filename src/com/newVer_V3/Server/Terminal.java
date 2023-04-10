package com.newVer_V3.Server;

import com.newVer_V3.Server.MessageServer.MsgProcessServer;
import com.newVer_V3.Server.UserServer.UserConnServer;

public class Terminal {

    public void serverStarter(){
        new UserConnServer().startServer();
        new MsgProcessServer().startServer();
    }

    public static void main(String[] args) {
        new Terminal().serverStarter();
    }
}

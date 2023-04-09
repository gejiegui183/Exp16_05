package com.newVer_V2.Server;

import com.company.MsgServer.MsgServer;
import com.company.Server.ServerHost;
import com.newVer_V2.Server.Message.MessageServer;
import com.newVer_V2.Server.UserInfo.InfoServer;

public class Terminal {
    public void serverInit(){
       new InfoServer().serverStarter();
       new MessageServer().serverStarter();
    }

    public void init(){
        serverInit();
    }

    public static void main(String[] args) {
        new Terminal().init();
    }
}

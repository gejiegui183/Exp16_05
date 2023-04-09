package com.company.Entrance;

import com.company.MsgServer.MsgServer;
import com.company.Server.ServerHost;

import javax.swing.*;

public class Terminal extends JFrame {

    public void serverInit(){
        new ServerHost().serverStart();
        new MsgServer().startServer();
    }

    public void init(){
        serverInit();
    }

    public static void main(String[] args) {
        new Terminal().init();
    }

}

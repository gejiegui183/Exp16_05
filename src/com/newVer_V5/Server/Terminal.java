package com.newVer_V5.Server;

import com.newVer_V2.Server.Message.MessageServer;
import com.newVer_V5.Server.ConnectServer.ClientConnector;
import com.newVer_V5.Server.MessageServer.ClientMessage;
import com.newVer_V5.Server.RequireServer.ClientRequire;

public class Terminal {

    public void log(){
        System.out.println("welcome to");
        System.out.println("   ____  ____  ________          __  _____                          \n" +
                "  / __ \\/ __ \\/ ____/ /_  ____ _/ /_/ ___/___  ______   _____  _____\n" +
                " / / / / / / / /   / __ \\/ __ `/ __/\\__ \\/ _ \\/ ___/ | / / _ \\/ ___/\n" +
                "/ /_/ / /_/ / /___/ / / / /_/ / /_ ___/ /  __/ /   | |/ /  __/ /    \n" +
                "\\___\\_\\___\\_\\____/_/ /_/\\__,_/\\__//____/\\___/_/    |___/\\___/_/     \n" +
                "                                                                    " + "ver_0.5.0");

    }

    public void serverStarter1(){
        new Thread(() -> {
            new ClientConnector().initServer();
        }).start();
        new Thread(() -> {
            new ClientMessage().initServer();
        }).start();
        new Thread(() -> {
            new ClientRequire().initServer();
        }).start();
    }

    public static void main(String[] args)  {
        Terminal terminal = new Terminal();
        terminal.log();
        terminal.serverStarter1();
    }
}

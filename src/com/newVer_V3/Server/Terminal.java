package com.newVer_V3.Server;

import com.newVer_V3.Server.MessageServer.MsgProcessServer;
import com.newVer_V3.Server.UserServer.UserConnServer;

public class Terminal {

    public void serverStarter(){
        new UserConnServer().startServer();
        new MsgProcessServer().startServer();
    }

    public void logoOutPut(){
        System.out.println("welcome to");
        System.out.println("   ____  ____  ________          __  _____                          \n" +
                "  / __ \\/ __ \\/ ____/ /_  ____ _/ /_/ ___/___  ______   _____  _____\n" +
                " / / / / / / / /   / __ \\/ __ `/ __/\\__ \\/ _ \\/ ___/ | / / _ \\/ ___/\n" +
                "/ /_/ / /_/ / /___/ / / / /_/ / /_ ___/ /  __/ /   | |/ /  __/ /    \n" +
                "\\___\\_\\___\\_\\____/_/ /_/\\__,_/\\__//____/\\___/_/    |___/\\___/_/     \n" +
                "                                                                    " + "ver_1.3.0");
    }

    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.logoOutPut();
        terminal.serverStarter();

    }
}

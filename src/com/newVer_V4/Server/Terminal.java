package com.newVer_V4.Server;

import com.newVer_V4.Server.Connection.UserConnector;

public class Terminal {


    public void logoPrint(){
        System.out.println("welcome to");
        System.out.println("   ____  ____  ________          __  _____                          \n" +
                "  / __ \\/ __ \\/ ____/ /_  ____ _/ /_/ ___/___  ______   _____  _____\n" +
                " / / / / / / / /   / __ \\/ __ `/ __/\\__ \\/ _ \\/ ___/ | / / _ \\/ ___/\n" +
                "/ /_/ / /_/ / /___/ / / / /_/ / /_ ___/ /  __/ /   | |/ /  __/ /    \n" +
                "\\___\\_\\___\\_\\____/_/ /_/\\__,_/\\__//____/\\___/_/    |___/\\___/_/     \n" +
                "                                                                    " + "ver_1.4.4");

    }

    public void serverStarter(){
        new UserConnector().serverInit();
    }

    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.logoPrint();
        terminal.serverStarter();
    }
}

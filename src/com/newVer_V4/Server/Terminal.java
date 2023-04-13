package com.newVer_V4.Server;

public class Terminal {

    public void starter(){

    }

    public void logoPrint(){
        System.out.println("welcome to");
        System.out.println("   ____  ____  ________          __  _____                          \n" +
                "  / __ \\/ __ \\/ ____/ /_  ____ _/ /_/ ___/___  ______   _____  _____\n" +
                " / / / / / / / /   / __ \\/ __ `/ __/\\__ \\/ _ \\/ ___/ | / / _ \\/ ___/\n" +
                "/ /_/ / /_/ / /___/ / / / /_/ / /_ ___/ /  __/ /   | |/ /  __/ /    \n" +
                "\\___\\_\\___\\_\\____/_/ /_/\\__,_/\\__//____/\\___/_/    |___/\\___/_/     \n" +
                "                                                                    " + "ver_1.4.0");
    }

    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.starter();
        terminal.logoPrint();
    }
}

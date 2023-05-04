package com.Test3.T1;

public class Client {

    public void videoStart(){
        CamStartTest camStart = new CamStartTest("test");
        camStart.getCam();
        camStart.setWindow();
    }

    public static void main(String[] args) {
        new Client().videoStart();
    }

}

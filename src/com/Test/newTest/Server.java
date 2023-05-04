package com.Test.newTest;

import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {
    ServerSocket serverSocket;
    int port = 30000;
    Graphics g;

    public void setWindows(){
        this.setSize(700,700);
        this.setTitle("QQChat");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
    }

    public void layOut(){
        //¶¥²¿Í¼Æ¬
        JPanel northPanel = new JPanel();
        northPanel.setBounds(0,0,700,700);
        this.add(northPanel,BorderLayout.CENTER);
        g = northPanel.getGraphics();
        g.drawLine(0 , 0 , 100 , 100);

        //ÊäÈë¿ò
    }


    public void initSocket(){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        setWindows();
        layOut();
        this.setVisible(true);
        initSocket();
//        while (true){
//            new Reciver(serverSocket , g).start();
//        }
    }

    public static void main(String[] args) {
        new Server().init();
    }
}

class Reciver extends Thread{
    ServerSocket serverSocket;
    Graphics pen;

    public Reciver(ServerSocket serverSocket , Graphics pen){
        this.serverSocket = serverSocket;
        this.pen = pen;
    }

    @Override
    public void run() {
        Socket accetp = null;

            try {
                accetp = serverSocket.accept();
                InputStream in = accetp.getInputStream();
                DataInputStream inFlow = new DataInputStream(in);
                while (true) {
                    int imageWidth = inFlow.readInt();
                    int imageHeight = inFlow.readInt();
                    int videoMatrix [][] = new int[imageWidth][imageHeight];
                    for (int i = 0; i < imageWidth; i++) {
                        for (int j = 0; j < imageHeight; j++) {
                            videoMatrix[i][j] = inFlow.readInt();
                        }
                    }
                    drawVideo(videoMatrix);
                }
//                int len = 0;
//                byte [] reciver = new byte[1024];
//                while ((len = in.read(reciver)) != -1){
//                    Thread.sleep(1000);
//                    String reply = new String(reciver);
//                    System.out.println(reply);
//                }
//                DataInputStream inFlue = new DataInputStream(in);
//                int width = inFlue.readInt();
//                int height = inFlue.readInt();
//                int video [][] = new int[width][height];
//                for (int i = 0; i < width; i++) {
//                    for (int j = 0; j < height; j++) {
//                        video[i][j] = inFlue.readInt();
//                    }
//                }
//                System.out.println(video + "<< debug");
//                drawVideo(video);
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    public void drawVideo(int matrix[][]){
        BufferedImage image = new BufferedImage(matrix.length , matrix[0].length , BufferedImage.TYPE_INT_RGB);
        pen = image.getGraphics();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                Color color = new Color(matrix[i][j]);
                pen.setColor(color);

            }
        }
        pen.drawLine(0 , 0 , 100 , 100);
    }



}

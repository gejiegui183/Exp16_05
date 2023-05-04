package com.newVer_V5.Client;

import com.Test2.WebcamViewerExample;
import com.newVer_V5.InfoData.Config;
import com.newVer_V5.cam.CamStart;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FriendChatWindow extends JFrame implements Config {
    String JSInfo;
    Socket msgSender;
    int msgPort = 25000;
    int videoPort = 25001;
    String host = "127.0.0.1";
    String friendName;
    String userID;
    JTextField textField;
    JButton send;
    JPanel northPanel;
    JButton video;
    String friendID;
    int videoStatus = 0;
    Socket require;

    public FriendChatWindow(){}

    public FriendChatWindow(String JSInfo){
        this.JSInfo = JSInfo;
        infoDecode();
    }

    public void infoDecode(){
        JSONObject js = new JSONObject(JSInfo);
        this.userID = (String) js.get("userID");
        this.friendID = (String) js.get("friendID");
        this.friendName = (String) js.get("friendName");
    }

    public void videoSocketInit(){
        try {
            msgSender = new Socket(host , msgPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        videoRequireCreater();
//        new VideoRecive(require , friendName , userID).start();
    }

    public void setWindows(){
        this.setSize(680 , 540);
        this.setTitle(friendName);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(1);
    }

    public void layOut(){
        //消息发送面板
        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(510 , 120));
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300 , 45));
        southPanel.add(textField);
        send = new JButton("发送");
        send.setPreferredSize(new Dimension(80 , 30));
        southPanel.add(send);

        video = new JButton("视频通话");
        video.setBounds(80 , 30 , 80 , 80);
        southPanel.add(video);
        this.add(southPanel , BorderLayout.SOUTH);
    }

    public void messagePanelBackGround(){
        northPanel = new JPanel();
        northPanel.setBounds(5 , 5 , 370 , 330);
        northPanel.setBackground(Color.WHITE);
        this.add(northPanel);
    }

    public void buttonSet(){
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageSend();
            }
        });

        video.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                videoSet();
            }
        });
    }

    public void messageSend(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String chat = textField.getText();
        JSONObject msg = new JSONObject();
        msg.put("userID" , userID);
        msg.put("friendID" , friendID);
        msg.put("sendTime" , dateFormat.format(date));
        msg.put("msgType" , 2);
        msg.put("content" , chat);
        msgDisplay(chat , FlowLayout.RIGHT);
        String msgJS = msg.toString();
        try {
            OutputStream out = msgSender.getOutputStream();
            byte [] msgList = msgJS.getBytes();
            out.write(msgList.length);
            out.write(msgList);
            out.flush();
//            out.close();
//            msgSender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new MessageReciver(msgSender , northPanel).start();
        textField.setText("");
    }

    public void msgDisplay(String message , int align){
        JLabel msgLable = new JLabel(message , align);
        msgLable.setForeground(Color.BLACK);
        msgLable.setBackground(Color.cyan);
        msgLable.setSize(50 , 25);
        msgLable.setOpaque(true);

        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(660 , 25));
        itemPanel.add(msgLable);

        FlowLayout layout = (FlowLayout) itemPanel.getLayout();
        layout.setAlignment(align);
        northPanel.add(itemPanel);
        northPanel.updateUI();
    }

    public void videoSet(){
        JFrame tipWindow = new JFrame();
        tipWindow.setTitle("系统提示");
        tipWindow.setSize(300 , 200);
        tipWindow.setLocationRelativeTo(null);
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(220 , 100));
        JLabel label = new JLabel("系统将会使用您设备的摄像头，是否同意？");
        label.setSize(200 , 90);
        panel1.add(label);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(220 , 35));
        JButton jb1 = new JButton("同意");
        jb1.setBounds(70 , 40 , 70 , 30);
        JButton jb2 = new JButton("拒绝");
        jb1.setBounds(160 , 40 , 20 , 30);
        panel2.add(jb1);
        panel2.add(jb2);
        tipWindow.add(panel1 , BorderLayout.NORTH);
        tipWindow.add(panel2 , BorderLayout.SOUTH);
        tipWindow.setDefaultCloseOperation(1);
        tipWindow.setVisible(true);
        tipWindow.setResizable(false);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requireSend();
                tipWindow.dispose();
                tipWindow.setDefaultCloseOperation(1);
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipWindow.dispose();
                tipWindow.setDefaultCloseOperation(1);
            }
        });
    }

    public void setSignal(int sig){
        this.videoStatus = sig;
    }

    public void videoRequireCreater(){
        JSONObject js = new JSONObject();
        js.put("userID" , userID);
        js.put("friendID" , friendID);
        js.put("msgType" , 1);
        String info = js.toString();
        try {
            OutputStream out = require.getOutputStream();
            byte [] requires = info.getBytes();
//            out.write(requires.length);
            out.write(info.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //视频聊天前先发送视频申请
    public void requireSend(){
        JSONObject js = new JSONObject();
        js.put("userID" , userID);
        js.put("friendID" , friendID);
        js.put("sendTime" , "null");
        js.put("msgType" , 3);
        js.put("content" , "1");
        String pak = js.toString();
        try {
            OutputStream out = require.getOutputStream();
            out.write(pak.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoStart();
    }

    public void videoStart(){
//            CamStart camStart = new CamStart(friendName);
//            camStart.getCam();
//            camStart.setWindow();
//        }else {
//            System.out.println("用户已拒绝");
//        }
        CamStart camStart = new CamStart(friendName);
        camStart.getCam();
        camStart.setWindow();
    }

    public void init(){
        videoSocketInit();
        setWindows();
        layOut();
//        new VideoRecive(require , friendName , userID).start();
        messagePanelBackGround();
        buttonSet();
        this.setVisible(true);
    }
}


class MessageReciver extends Thread{
    Socket socket;
    JPanel newPanel;

    public MessageReciver(Socket socket , JPanel newPanel){
        this.socket = socket;
        this.newPanel = newPanel;
    }

    @Override
    public void run() {
        InputStream in  = null;
        while (true) {
            try {
                in = socket.getInputStream();
                int msgLen = in.read();
                byte [] data = new byte[msgLen];
                in.read(data);
                String msg = new String(data);
                msgDisplay(msg , FlowLayout.LEFT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void msgDisplay(String message , int align){
        JLabel msgLable = new JLabel(message , align);
        msgLable.setForeground(Color.BLACK);
        msgLable.setBackground(Color.cyan);
        msgLable.setSize(50 , 25);
        msgLable.setOpaque(true);

        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(660 , 25));
        itemPanel.add(msgLable);

        FlowLayout layout = (FlowLayout) itemPanel.getLayout();
        layout.setAlignment(align);
        newPanel.add(itemPanel);
        newPanel.updateUI();
    }
}

class VideoRecive extends Thread{
    Socket socket;
    int videoStatus = 0;
    JFrame frame;
    String friendName , userName;
    String userID , friendID;

    public VideoRecive(Socket socket , String friendName , String userID) {
        this.socket = socket;
        this.friendName = friendName;
    }

    @Override
    public void run() {
        InputStream in = null;
        System.out.println("VideoReciver is running <<ClientDebug");
        while (true) {
            try {
                in = socket.getInputStream();
                int len = in.read();
                byte [] info = new byte[len];
                in.read(info);
                String str = new String(info);
                int signal = Integer.valueOf(str);
                if(signal == 1){
                    videoAsk();
                }
                else {
                    System.out.println("请求未收到<<debug");
                }
//                byte [] data = new byte[in.read()];
//                in.read(data);
//                String str = new String(data);
//                System.out.println(str + "<<ChatWindow Debug0002");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void videoAsk(){
        JFrame tipWindow = new JFrame();
        tipWindow.setTitle("系统提示");
        tipWindow.setSize(300 , 200);
        tipWindow.setLocationRelativeTo(null);
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(220 , 100));
        JLabel label = new JLabel(friendName + "想与你视频通话，是否同意？");
        label.setSize(200 , 90);
        panel1.add(label);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(220 , 35));
        JButton jb1 = new JButton("同意");
        jb1.setBounds(70 , 40 , 70 , 30);
        JButton jb2 = new JButton("拒绝");
        jb1.setBounds(160 , 40 , 20 , 30);
        panel2.add(jb1);
        panel2.add(jb2);
        tipWindow.add(panel1 , BorderLayout.NORTH);
        tipWindow.add(panel2 , BorderLayout.SOUTH);
        tipWindow.setDefaultCloseOperation(1);
        tipWindow.setVisible(true);
        tipWindow.setResizable(false);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requireReply(1);
                videoStart();
                tipWindow.dispose();
                tipWindow.setDefaultCloseOperation(1);
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requireReply(0);
                tipWindow.dispose();
                tipWindow.setDefaultCloseOperation(1);
            }
        });
    }

    public void requireReply(int reply){
        JSONObject js = new JSONObject();
        js.put("msgType" , 3);
        js.put("userID" , userID);
        js.put("friendID" , friendID);
        js.put("reply" , reply);
        String pak = js.toString();
        try {
            OutputStream out = socket.getOutputStream();
            out.write(pak.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void videoStart(){
        CamStart camStart = new CamStart(friendName);
        camStart.getCam();
        camStart.setWindow();
    }
}
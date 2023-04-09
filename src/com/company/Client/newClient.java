package com.company.Client;

import com.newVer_V2.Client.DisplayPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class newClient extends JFrame {
    Socket msgSender;
    int msgPort = 26002;
    String host = "127.0.0.1";
    String friendName;
    String userID;
    JTextField textField;
    JButton send;
    JPanel northPanel;

    JButton flush;

    String reciverID;

    public newClient(){}


    public newClient(String friendName , String userID , String reciverID ){
        this.friendName = friendName;
        this.userID = userID;
        this.reciverID = reciverID;
    }

    public void setWindows(){
        this.setSize(680 , 540);
        this.setTitle(friendName);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(1);
    }

    public void layOut(){
        //��Ϣ�������
        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(510 , 120));
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300 , 45));
        southPanel.add(textField);
        send = new JButton("����");
        send.setPreferredSize(new Dimension(80 , 30));
        southPanel.add(send);

        flush = new JButton("ˢ��");
//        flush.setPreferredSize(new Dimension(80 , 30));
        flush.setBounds(80 , 30 , 80 , 80);
        southPanel.add(flush);
        this.add(southPanel , BorderLayout.SOUTH);
    }

    public void messagePanelBackGround(){
        northPanel = new JPanel();
        northPanel.setBounds(5 , 5 , 370 , 330);
        northPanel.setBackground(Color.WHITE);
        this.add(northPanel);
    }

    public void setDisplayPanel(){
        JLabel msgLable = new JLabel("Test" , 2);
        msgLable.setForeground(Color.BLACK);
        msgLable.setBackground(Color.cyan);
        msgLable.setSize(50 , 25);
        msgLable.setOpaque(true);

        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(660 , 25));
        itemPanel.add(msgLable);

        FlowLayout layout = (FlowLayout) itemPanel.getLayout();
        layout.setAlignment(2);
    }

    public void buttonSet(){
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageSend();
            }
        });

        flush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    shuffle();
            }
        });
    }

    public void messageSend(){
        new DisplayPanel(textField.getText() , FlowLayout.RIGHT , northPanel).setVisible(true);
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            msgSender = new Socket(host , msgPort);
            OutputStream sender = msgSender.getOutputStream();
            JSONObject sendJS = new JSONObject();
            sendJS.put("messageType","message");
            sendJS.put("sendTime",dateFormat.format(date));
            sendJS.put("senderID",userID);
            sendJS.put("reciverID",reciverID);
            sendJS.put("content",textField.getText());
            String sendMsg = sendJS.toString();
            byte [] sendMsgList = sendMsg.getBytes();
            sender.write(sendMsgList.length);
            sender.write(sendMsgList);
            sender.flush();
            sender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textField.setText("");
    }

    public void shuffle() {

    }

    public void msgProcess(String message){
        System.out.println(message + "/newClient");
    }

    public void init(){
        setWindows();
        layOut();
        messagePanelBackGround();
//        setDisplayPanel();
        buttonSet();
        this.setVisible(true);
    }
}

package com.newVer_V3.Client;

import com.newVer_V2.Server.Message.MessageServer;
import com.newVer_V3.ConfigData.Config;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class friendChatPanel extends JFrame implements Config {
    String tagInfo;
    Socket msgSender;
    int msgPort = 18001;
    String host = "127.0.0.1";
    String friendName;
    String userID;
    JTextField textField;
    JButton send;
    JPanel northPanel;
    JButton flush;
    String targetID;

    public friendChatPanel(String tagInfo) {
        this.tagInfo = tagInfo;
        messageDecode(this.tagInfo);
    }

    public void messageDecode(String js){
        JSONObject item = new JSONObject(js);
        userID = (String) item.get("userID");
        friendName = (String) item.get("targetName");
        targetID = (String) item.get("targetID");
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

        flush = new JButton("刷新");
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
            }
        });
    }

    public void messageSend(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String chat = textField.getText();
        JSONObject msg = new JSONObject();
        msg.put("userID" , userID);
        msg.put("targetID" , targetID);
        msg.put("sendTime" , dateFormat.format(date));
        msg.put("msgType" , 1);
        msg.put("content" , chat);
        msgDisplay(chat , FlowLayout.RIGHT);
        String msgJS = msg.toString();
        try {
            msgSender = new Socket(host , msgPort);
            OutputStream out = msgSender.getOutputStream();
            byte [] msgList = msgJS.getBytes();
            out.write(msgList.length);
            out.write(msgList);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        northPanel.add(itemPanel);
        northPanel.updateUI();
    }

    public void messageCheck(){

    }

    public void init(){
        setWindows();
        layOut();
        messagePanelBackGround();
        buttonSet();
        this.setVisible(true);
    }

}

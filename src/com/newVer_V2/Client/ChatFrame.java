package com.newVer_V2.Client;

import com.newVer_V2.Server.Message.ClientMsgSaver;
import com.newVer_V2.Server.Message.MessageServer;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatFrame extends JFrame implements WindowListener {
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


    public ChatFrame(){

    }

    public ChatFrame(String friendName , String userID , String reciverID ){
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
        msgDisplay(textField.getText() , FlowLayout.RIGHT );
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
//        new ClientMsgSaver(this).start();
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

    public void init(){
        new MessageServer(this);
        setWindows();
        layOut();
        messagePanelBackGround();
        buttonSet();
        this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("1023");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("1024");
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

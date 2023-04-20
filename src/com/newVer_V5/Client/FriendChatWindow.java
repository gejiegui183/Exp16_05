package com.newVer_V5.Client;

import com.newVer_V5.InfoData.Config;
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
    int msgPort = 9001;
    String host = "127.0.0.1";
    String friendName;
    String userID;
    JTextField textField;
    JButton send;
    JPanel northPanel;
    JButton flush;
    String friendID;

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
        msg.put("friendID" , friendID);
        msg.put("sendTime" , dateFormat.format(date));
        msg.put("msgType" , 2);
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
//            msgSender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void msgFeedBack(){
        JSONObject js = new JSONObject();
        js.put("userID" , userID);
        js.put("friendID" , friendID);
        String pak = js.toString();
        new MessageReciver(host , msgPort).start();
    }

    public void init(){
        setWindows();
        layOut();
        messagePanelBackGround();
        buttonSet();
        msgFeedBack();
        this.setVisible(true);
    }
}


class MessageReciver extends Thread{
    Socket reciver;
    int port;
    String IP;

    MessageReciver(String IP , int port){
        this.IP = IP;
        this.port = port;
        init();
    }

    public void init(){
        try {
            this.reciver = new Socket(IP , port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("start");
        InputStream in = null;
        try {
            in = reciver.getInputStream ();
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
        try {
            // ������Ϣ ѭ��
            while(true){
                // ������Ϣ
                byte[] bytes = new byte[1024];
                int len = in.read (bytes);
                String message = new String (bytes, 0, len);
                System.out.println (message);
                // ������Ϣ
                // ����-��Ϣ����
                String[] split = message.split ("-");
                String length = split[0];
                String msgContent = split[1];
                System.out.println ("�յ���Ϣ��" + msgContent);
                // ��ʾ�������� ���ǽ����ϵ�ǰ����Ķ�������Ϣ���͵�ʱ�� ��δ���
//                chatUI.messageArea.append ("\n" + msgContent);

            }
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
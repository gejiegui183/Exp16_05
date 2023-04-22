package com.newVer_V5.Client;

import com.newVer_V3.Client.AllFriendList;
import com.newVer_V3.DataBase.UserInfoDB;
import com.newVer_V5.DataBase.InfoCheck;
import com.newVer_V5.InfoData.Config;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class UseLoginWindow extends JFrame implements Config {
    Socket socket;
    String host = "127.0.0.1";
    int port = 24999;
    JButton login;
    JButton quit;
    JButton register;
    JTextField textField1;
    JPasswordField passwordField;

    //��������
    public void setWindows(){
        this.setSize(loginFrameWidth , loginFrameHeight);
        this.setTitle("QQChat");
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
    }

    public void layOut(){
        //����ͼƬ
        JPanel northPanel = new JPanel();
        ImageIcon icon = new ImageIcon("./Image/QQ_Login.png");
        JLabel titleLabel = new JLabel();
        northPanel.setBounds(0,-2,560,100);
        icon.setImage(icon.getImage().getScaledInstance(northPanel.getWidth(), northPanel.getHeight(), Image.SCALE_DEFAULT));//����ͼ���С
        titleLabel.setIcon(icon);
        northPanel.add(titleLabel);
        this.add(northPanel,BorderLayout.NORTH);

        //�����
        JPanel space = new JPanel();
        space.setLayout(null);
        space.setPreferredSize(new Dimension(140 , 0));
        this.add(space , BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.setPreferredSize(new Dimension(0 , 140));
        JLabel text1 = new JLabel("�û���:");
        centerPanel.add(text1);
        textField1 = new JTextField(10);
        centerPanel.add(textField1);
        JLabel text2 = new JLabel("��   ��:");
        centerPanel.add(text2);
        passwordField = new JPasswordField();
        centerPanel.add(passwordField);
        this.add(centerPanel, BorderLayout.CENTER);

        //��ť����
        JPanel southPanel = new JPanel();
        southPanel.setLayout(null);
        southPanel.setPreferredSize(new Dimension(0 , 240));
        login = new JButton("��¼");
        login.setBounds(300 , 100 , 80 , 40);
        southPanel.add(login);
        quit = new JButton("�˳�");
        quit.setBounds(160 , 100 , 80 , 40);
        southPanel.add(quit);
        register = new JButton("ע��");
        register.setBounds(480 , 220 , 60 , 20);
        southPanel.add(register);
        this.add(southPanel, BorderLayout.SOUTH);
    }


    public void buttonActionListener(){
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });

    }

    //��¼����ȡ���ݿ�
    public void login(){
        String account = textField1.getText();
        String password = new String(passwordField.getPassword());
        InfoCheck infoCheck = new InfoCheck(account , password);
        if(infoCheck.userInfoJudge()){
            JSONObject js = new JSONObject();
            js.put("msgType" , 1);
            js.put("userID" , account);
            js.put("userName" , infoCheck.getUserName());
            js.put("loginStatus" , 1);
            String pak = js.toString();
            sendUserInfo(pak);
            new FriendsListWindow(pak).init();
            this.dispose();
            this.setDefaultCloseOperation(1);
        }
        //��¼�ɹ�
        else {
            System.out.println("��¼ʧ��");
        }
    }

    public void closeWindow(){
        this.dispose();
        this.setDefaultCloseOperation(3);
    }


    //��¼ʱ���͵�¼�û���Ϣ
    public void sendUserInfo(String info){
        try {
            socket = new Socket(host , port);
            OutputStream out = socket.getOutputStream();
            out.write(info.getBytes());
            out.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        setWindows();
        layOut();
        buttonActionListener();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new UseLoginWindow().init();
    }

}

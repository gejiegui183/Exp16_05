package com.newVer_V4.Client;


import com.newVer_V3.DataBase.UserInfoDB;
import com.newVer_V4.ClientData.Config;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class UserLogin extends JFrame implements Config {
    int port = 10000;
    String ServerIP01 = "127.0.0.1";
    Socket sendInfoSocket;
    JButton login;
    JButton quit;
    JButton register;
    JTextField textField1;
    JPasswordField passwordField;

    //窗口设置
    public void setWindows(){
        this.setSize(loginFrameWidth , loginFrameHeight);
        this.setTitle("QQChat");
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
    }

    public void layOut(){
        //顶部图片
        JPanel northPanel = new JPanel();
        ImageIcon icon = new ImageIcon("./Image/QQ_Login.png");
        JLabel titleLabel = new JLabel();
        northPanel.setBounds(0,-2,560,100);
        icon.setImage(icon.getImage().getScaledInstance(northPanel.getWidth(), northPanel.getHeight(), Image.SCALE_DEFAULT));//设置图像大小
        titleLabel.setIcon(icon);
        northPanel.add(titleLabel);
        this.add(northPanel,BorderLayout.NORTH);

        //输入框
        JPanel space = new JPanel();
        space.setLayout(null);
        space.setPreferredSize(new Dimension(140 , 0));
        this.add(space , BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.setPreferredSize(new Dimension(0 , 140));
        JLabel text1 = new JLabel("用户名:");
        centerPanel.add(text1);
        textField1 = new JTextField(10);
        centerPanel.add(textField1);
        JLabel text2 = new JLabel("密   码:");
        centerPanel.add(text2);
        passwordField = new JPasswordField();
        centerPanel.add(passwordField);
        this.add(centerPanel, BorderLayout.CENTER);

        //按钮区域
        JPanel southPanel = new JPanel();
        southPanel.setLayout(null);
        southPanel.setPreferredSize(new Dimension(0 , 240));
        login = new JButton("登录");
        login.setBounds(300 , 100 , 80 , 40);
        southPanel.add(login);
        quit = new JButton("退出");
        quit.setBounds(160 , 100 , 80 , 40);
        southPanel.add(quit);
        register = new JButton("注册");
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

    //登录、拉取数据库
    public void login(){
        String userAccount = textField1.getText();
        String passWord = new String(passwordField.getPassword());
        UserInfoDB userInfoDB = new UserInfoDB(userAccount , passWord);
        if(userInfoDB.userInfoJudge()){
            JSONObject userInfo = new JSONObject();
            int loginStatus = 1;
            userInfo.put("msgType" , 1);
            userInfo.put("userID" , userAccount);
            userInfo.put("userName" , userInfoDB.getUserName());
            userInfo.put("loginStatus" , loginStatus);
            String userInfoStr = userInfo.toString();
            //好友列表标题显示登录用户名字
//            new FriendLists(userInfoStr).initClient();
            loginSignal(userInfoStr);
            this.dispose();
            this.setDefaultCloseOperation(1);
        }
        else {
            System.out.println();
        }
        //获取数据库中用户信息
    }

    public void closeWindow(){
        this.dispose();
        this.setDefaultCloseOperation(3);
    }

    public void loginSignal(String info){
        try {
            sendInfoSocket = new Socket(ServerIP01 , port);
            OutputStream out = sendInfoSocket.getOutputStream();
            byte [] infoList = info.getBytes();
            out.write(infoList.length);
            out.write(infoList);
            out.flush();
            out.close();
            sendInfoSocket.shutdownOutput();
        } catch (IOException e) {
            try {
                sendInfoSocket.close();
            }
            catch (Exception e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void initUI(){
        setWindows();
        layOut();
        buttonActionListener();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new UserLogin().initUI();
    }

}

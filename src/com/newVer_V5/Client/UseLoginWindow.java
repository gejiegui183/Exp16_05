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
        //登录成功
        else {
            System.out.println("登录失败");
        }
    }

    public void closeWindow(){
        this.dispose();
        this.setDefaultCloseOperation(3);
    }


    //登录时发送登录用户信息
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

package com.newVer_V5.Client;

import com.newVer_V3.DataBase.UserInfoDB;
import com.newVer_V5.DataBase.InfoCheck;
import com.newVer_V5.InfoData.Config;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FriendsListWindow extends JFrame implements Config {
    String userInfoJson;
    String userName;
    String userID;
    JFrame frame;
    String host = "127.0.0.1";
    int port = 9000;
    Socket socket;
    String friendNum;
    SystemTray systemTray;
    TrayIcon trayIcon;
    String friendID;
    int loginStatus;
    static boolean connStatus = false;

    public FriendsListWindow(){}

    public FriendsListWindow(String userInfoJson){
        this.userInfoJson = userInfoJson;
        infoDecode();
    }

    public void infoDecode(){
        JSONObject js = new JSONObject(userInfoJson);
        this.userName = (String) js.get("userName");
        this.userID = (String) js.get("userID");
        this.loginStatus = (Integer) js.get("loginStatus");
    }

    public void initSocket(){
        try {
            socket = new Socket(host , port);
            connStatus = true;
            if (connStatus){
                new Heart(socket , userID).start();
            }
            else {
                initSocket();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWindows(){
        frame = new JFrame();
        frame.setSize(friendListWidth , friendListHeight);
        frame.setTitle(userName);
        frame.setResizable(false);
        frame.setIconImage(icon);
        frame.setLocation(20 , 20);
        frame.setVisible(true);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                loginStatus = 0;
                JSONObject userInfo = new JSONObject();
                userInfo.put("userID" , userID);
                userInfo.put("userName" , userName);
                userInfo.put("msgType" , 1);
                userInfo.put("loginStatus" , loginStatus);
                String userInfoStr = userInfo.toString();
                exitSignal(userInfoStr);
                frame.setDefaultCloseOperation(3);
            }

            @Override
            public void windowClosed(WindowEvent e) {

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
        });
        trayIcon = new TrayIcon(trayIconImage);
        systemTray = SystemTray.getSystemTray();
        trayIcon.setImageAutoSize(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                try {
                    systemTray.add(trayIcon);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
                frame.setVisible(false);
            }
        });
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    frame.setExtendedState(NORMAL);
                }
                systemTray.remove(trayIcon);
                frame.setVisible(true);
            }
        });
    }


    public void layOut(){
        //头部布局
        JPanel upLabel = new JPanel();
        upLabel.setBounds(0 , 0 , 0 , 800);
        JLabel text = new JLabel("好友搜索");
        upLabel.add(text);
        JTextField search = new JTextField(20);
        search.setLocation(0 , 0);
        upLabel.add(search);
        JButton determine = new JButton("确认");
        upLabel.add(determine);
        frame.add(upLabel , BorderLayout.NORTH);

        //好友列表布局
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new GridLayout(50 , 1 , 4 , 4));
        JLabel [] jlist = new JLabel[50];
        for (int i = 0; i < jlist.length; i++) {
            jlist[i] = new JLabel(i+1+"",new ImageIcon("./Image/0_32.jpg"),JLabel.LEFT);
            jlist[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount()==1)   //getClickCount() 获取鼠标被单机的次数
                    {
                        //得到该好友的编号
                        //getText() 的意思是：返回数据窗口控件中 悬浮在当前行列之上的
                        friendNum=((JLabel)e.getSource()).getText();
                        friendID = "1000" + friendNum;
                        String friendName  = new InfoCheck().getFriendName(friendID);
                        JSONObject js = new JSONObject();
                        js.put("msgType" , 1);
                        js.put("userID" , userID);
                        js.put("friendID" , friendID);
                        js.put("friendName" , friendName);
                        String pak = js.toString();
                        new FriendChatWindow(pak).init();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel jl=(JLabel)e.getSource();
                    jl.setForeground(Color.red);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel jl=(JLabel)e.getSource();
                    jl.setForeground(Color.black);
                }
            });
            friendPanel.add(jlist[i]);
        }
        JScrollPane friendList = new JScrollPane(friendPanel);
        frame.add(friendList , BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void exitSignal(String info){
        try {
            OutputStream out = socket.getOutputStream();
            out.write(info.getBytes());
            out.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        initSocket();
        setWindows();
        layOut();

    }

}

class Heart extends Thread{
    Socket socket;
    String userID;

    public Heart(){}

    public Heart(Socket socket , String userID){
        this.socket = socket;
        this.userID = userID;
    }

    @Override
    public void run() {
        try {
            OutputStream out = socket.getOutputStream();
            int i = 0;
            while (true) {
                i += 1;
                Thread.sleep(5000);
                String signal = "心跳包..."+i;
                JSONObject js = new JSONObject();
                js.put("msgType" , 0);
                js.put("userID" , userID);
                js.put("content" , signal);
                String pak = js.toString();
                out.write(pak.getBytes());
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
                FriendsListWindow.connStatus = false;
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }


    }
}
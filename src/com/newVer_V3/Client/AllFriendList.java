package com.newVer_V3.Client;

import com.newVer_V3.ConfigData.Config;
import com.newVer_V3.DataBase.UserInfoDB;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class AllFriendList extends JFrame implements Config , ActionListener{
    String userInfoJson;
    String userName;
    String userID;
    JFrame frame;
    String host = "127.0.0.1";
    int port = 18000;
    int port2 = 18001;
    Socket socket;
    String friendNum;
    SystemTray systemTray;
    TrayIcon trayIcon;
    String friendID;

    public AllFriendList() {
    }

    public AllFriendList(String userInfoJson) {
        this.userInfoJson = userInfoJson;
        JSFileDecode();
    }

    public void JSFileDecode(){
        JSONObject jsData = new JSONObject(userInfoJson);
        this.userName = (String) jsData.getString("userName");
        this.userID = (String) jsData.getString("userID");
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
                JSONObject userInfo = new JSONObject();
                int loginStatus = 2;
                userInfo.put("userID" , userID);
                userInfo.put("userName" , userName);
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
                        UserInfoDB userInfoDB = new UserInfoDB();
                        JSONObject chatTag = new JSONObject();
                        chatTag.put("userID" , userID);
                        chatTag.put("targetID" , friendID);
                        chatTag.put("targetName" , userInfoDB.getFriendName(friendID));
                        String chatTagStr = chatTag.toString();
                        startChatFrame(chatTagStr);
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
            socket = new Socket(host , port);
            byte [] signal = info.getBytes();
            OutputStream out = socket.getOutputStream();
            out.write(signal.length);
            out.write(signal);
            out.flush();
            out.close();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signalSend(){
        try{
            socket = new Socket(host , port);
            if (!socket.getKeepAlive()) {
                socket.setKeepAlive(true);
            }
            if (!socket.getOOBInline()) {
                socket.setOOBInline(true);
            }
            OutputStream signalOut = socket.getOutputStream();
            JSONObject js = new JSONObject();
            js.put("loginStatus" , 1);
            js.put("userName" , userName);
            js.put("userID" , userID);
            String signal = js.toString();
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        byte [] data = signal.getBytes();
                        signalOut.write(data.length);
                        signalOut.write(data);
                        signalOut.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initListUI(){
        setWindows();
        signalSend();
        layOut();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void startChatFrame(String tag){
        new friendChatPanel(tag).init();
    }

}

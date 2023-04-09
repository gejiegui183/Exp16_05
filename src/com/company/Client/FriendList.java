package com.company.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class FriendList extends JFrame implements Config , ActionListener {
    String userID , windowsTitle;
    String host = "127.0.0.1";
    int port = 26000;
    Socket socket;
    String friendNum;
    JFrame frame;
    SystemTray systemTray;
    TrayIcon trayIcon;
    int onlineCount;
    String friendName;
    String friendInfo;


    public FriendList(){}

    public FriendList(String userID , String windowsTitle){
        this.userID = userID;
        this.windowsTitle = windowsTitle;
        userInfoSend();
    }

    public void userInfoSend(){
        try {
            socket = new Socket(host , port);
            String info = "A01"+"#"+userID + "#" + windowsTitle;
            OutputStream out = socket.getOutputStream();
            byte [] data = info.getBytes();
            out.write(data.length);
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWindows(){
        frame = new JFrame();
        frame.setSize(friendListWidth , friendListHeight);
        frame.setTitle(windowsTitle);
        frame.setResizable(false);
        frame.setIconImage(icon);
        frame.setLocation(20 , 20);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
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

    public void setnLineCount(int count){
        this.onlineCount = count;
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
                        friendInfo = "1000" + friendNum;
                        InfoCheck infoCheck = new InfoCheck(friendInfo);
                        infoCheck.dbConnect();
                        startChatFrame(infoCheck.getName(),userID , friendInfo);
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

    public void initListUI(){
        setWindows();
        layOut();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }



    public void startChatFrame(String name ,String userID , String reciverID){
        new newClient(name ,userID , reciverID).init();
    }

}

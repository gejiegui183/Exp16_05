package com.test;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 聊天界面
 */
public class ChatUI extends JFrame implements ActionListener {
    // 聊天的消息显示区域 文本域 多行文字显示
    public JTextArea messageArea;

    // 消息发送框
    private JTextArea messageField;

    // 发送按钮
    private JButton sendButton;

    // 好友列表
    private JList<String> friendsList;

    // 群组列表
    private JList<String> groupsList;

    // 客户端对象
    private Client client;
    // 当前的聊天对象
    private String chatName;


    public ChatUI(Client client) {
        super("Chat Application" + client.loginName);
        // 初始化客户端
        this.client = client;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 550);

        // 界面主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel friendsPanel = new JPanel(new BorderLayout());
        // 从服务器上下载当前在线的用户列表 请求好友列表
        JButton btn = new JButton("刷新用户列表");
        friendsList = new JList<>();
        btn.addActionListener(e -> {
            try {
                friendsList.setListData(client.getAllUsersInfo());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //创建好友列表
            JScrollPane friendsScroll = new JScrollPane(friendsList);
            friendsScroll.setPreferredSize(new Dimension(150, 0));
            friendsPanel.add(friendsScroll, BorderLayout.WEST);
            setVisible(true);

        });
        friendsPanel.add(btn, BorderLayout.SOUTH);
        mainPanel.add(friendsPanel, BorderLayout.WEST);


        //创建群组列表
        groupsList = new JList<>(new String[]{"Group 1", "Group 2", "Group 3"});
        JScrollPane groupsScroll = new JScrollPane(groupsList);
        groupsScroll.setPreferredSize(new Dimension(150, 0));
        mainPanel.add(groupsScroll, BorderLayout.EAST);

        // 监听列表操作
        friendsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e.getValueIsAdjusting());
                // 点击之后松开某个list中的选项
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = friendsList.getSelectedValue();
                    chatName = selectedValue;
                    System.out.println(selectedValue);
                    //设置窗体标题
                    setTitle(client.loginName + " Chat With: " + selectedValue);
                    //设置消息显示框的头部信息
                    messageArea.setText("--------" + selectedValue + "---------\n");
                    setVisible(true);
                }
            }
        });


        groupsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e.getValueIsAdjusting());
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = groupsList.getSelectedValue();
                    System.out.println(selectedValue);
                    //创建消息区域
                    setTitle("Chating With: " + selectedValue);
                    chatName = selectedValue;
                    messageArea.setText("-----" + selectedValue + "------\n");
                    setVisible(true);
                }
            }
        });
        // 初始消息显示区域
        messageArea = new JTextArea();
        messageArea.setFont(new Font("黑体", Font.BOLD, 20));
        JScrollPane messageScroll = new JScrollPane(messageArea);
        mainPanel.add(messageScroll, BorderLayout.CENTER);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        //创建消息输入区域
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageField = new JTextArea();
        messagePanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        messagePanel.add(sendButton, BorderLayout.EAST);
        messagePanel.setPreferredSize(new Dimension(0, 200));
        mainPanel.add(messagePanel, BorderLayout.SOUTH);
        //将主面板添加到窗口中
        setContentPane(mainPanel);
//		pack();
        setVisible(true);


    }

    public static void main(String[] args) {
        new ChatUI(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 启动消息接收线程  传入 chatui.  收到消息将消息显示在界面上
        client.startReceiveThread(this);

        if (e.getSource() == sendButton) {
            String message = messageField.getText();
            messageArea.append("Me: " + message + "\n");
            messageField.setText("");
            try {
                client.sendMessage("1", message, chatName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
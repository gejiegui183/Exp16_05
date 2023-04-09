package com.test;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * �������
 */
public class ChatUI extends JFrame implements ActionListener {
    // �������Ϣ��ʾ���� �ı��� ����������ʾ
    public JTextArea messageArea;

    // ��Ϣ���Ϳ�
    private JTextArea messageField;

    // ���Ͱ�ť
    private JButton sendButton;

    // �����б�
    private JList<String> friendsList;

    // Ⱥ���б�
    private JList<String> groupsList;

    // �ͻ��˶���
    private Client client;
    // ��ǰ���������
    private String chatName;


    public ChatUI(Client client) {
        super("Chat Application" + client.loginName);
        // ��ʼ���ͻ���
        this.client = client;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 550);

        // ���������
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel friendsPanel = new JPanel(new BorderLayout());
        // �ӷ����������ص�ǰ���ߵ��û��б� ��������б�
        JButton btn = new JButton("ˢ���û��б�");
        friendsList = new JList<>();
        btn.addActionListener(e -> {
            try {
                friendsList.setListData(client.getAllUsersInfo());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //���������б�
            JScrollPane friendsScroll = new JScrollPane(friendsList);
            friendsScroll.setPreferredSize(new Dimension(150, 0));
            friendsPanel.add(friendsScroll, BorderLayout.WEST);
            setVisible(true);

        });
        friendsPanel.add(btn, BorderLayout.SOUTH);
        mainPanel.add(friendsPanel, BorderLayout.WEST);


        //����Ⱥ���б�
        groupsList = new JList<>(new String[]{"Group 1", "Group 2", "Group 3"});
        JScrollPane groupsScroll = new JScrollPane(groupsList);
        groupsScroll.setPreferredSize(new Dimension(150, 0));
        mainPanel.add(groupsScroll, BorderLayout.EAST);

        // �����б����
        friendsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e.getValueIsAdjusting());
                // ���֮���ɿ�ĳ��list�е�ѡ��
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = friendsList.getSelectedValue();
                    chatName = selectedValue;
                    System.out.println(selectedValue);
                    //���ô������
                    setTitle(client.loginName + " Chat With: " + selectedValue);
                    //������Ϣ��ʾ���ͷ����Ϣ
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
                    //������Ϣ����
                    setTitle("Chating With: " + selectedValue);
                    chatName = selectedValue;
                    messageArea.setText("-----" + selectedValue + "------\n");
                    setVisible(true);
                }
            }
        });
        // ��ʼ��Ϣ��ʾ����
        messageArea = new JTextArea();
        messageArea.setFont(new Font("����", Font.BOLD, 20));
        JScrollPane messageScroll = new JScrollPane(messageArea);
        mainPanel.add(messageScroll, BorderLayout.CENTER);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        //������Ϣ��������
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageField = new JTextArea();
        messagePanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        messagePanel.add(sendButton, BorderLayout.EAST);
        messagePanel.setPreferredSize(new Dimension(0, 200));
        mainPanel.add(messagePanel, BorderLayout.SOUTH);
        //���������ӵ�������
        setContentPane(mainPanel);
//		pack();
        setVisible(true);


    }

    public static void main(String[] args) {
        new ChatUI(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ������Ϣ�����߳�  ���� chatui.  �յ���Ϣ����Ϣ��ʾ�ڽ�����
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
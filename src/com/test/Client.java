package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client{
    // �ͻ��˵�һЩ����
    // �ͻ����ϰ󶨵�Socket
    private Socket socket;
    // �ͻ��˵������ - ������Ϣ
    private OutputStream os;

    // �ͻ��˵������� - ������Ϣ
    private InputStream in;

    public String loginName;

    // ���췽��
    public Client(){
        // ��ʼ�� Socket
        this.socket = initClient ();
        try {
            // ��ʼIO��
            os = this.socket.getOutputStream ();
            in = this.socket.getInputStream ();
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    public Socket initClient(){
        Socket socket = null;
        try {
            socket = new Socket ("localhost", 9999);
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
        return socket;
    }

    // ��¼����
    public String login(String username, String password) throws IOException{
        // ������Ϣ
        // ���͵�¼��Ϣ login-�û���-id-����
        String logininfo = "login#" + username + "#" + username.hashCode () + "#" + password;
        os.write (logininfo.getBytes ());
        os.flush ();
        loginName=username;
        // ������Ϣ
        return username;
    }

    int i = 0;

    // ������Ϣ������
    public void startReceiveThread(ChatUI chatUI){
        if(i == 0)
            // �ͻ��˽�����Ϣ�߳�
//            new ReceiveThread (socket, chatUI).start ();
        i++;
    }

    /**
     * ��Ϣ���ͷ���
     *
     * @param msgTyped
     * 	  �ļ�����
     * @param umsg
     * 	  ��Ϣ��
     * @param friendName
     * 	  �Է�ID
     * @throws IOException
     */
    public void sendMessage(String msgTyped, String umsg, String friendName) throws IOException{
        System.out.println ("��˵��" + umsg);
        int id = friendName.hashCode ();
        if(id < 0){
            System.out.println ("���Ǹ���~~");
        }
        System.out.println (id);
        System.out.println (friendName);
        String msg = msgTyped + "#" + umsg.length () + "#" + friendName + "#" + id + "#" + umsg;
        os.write (msg.getBytes ());
        os.flush ();
    }

    // �رտͻ���
    public void closeClient() throws IOException{
        os.close ();
        in.close ();
        this.socket.close ();
    }


    public String[] getAllUsersInfo() throws IOException{
        os.write ("getAllFriends".getBytes ());
        os.flush ();
        System.out.println ();
//		in.read ();
        byte[] bytes = new byte[1024];
        int len = in.read (bytes);
        String message = new String (bytes, 0, len);
        System.out.println ("�������������������û���Ϣ��" + message);
        // ������ϢЭ��������� ���ȡ��û���-id-����
        String[] split = message.split (",");
        return split;
    }
}
package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client{
    // 客户端的一些属性
    // 客户端上绑定的Socket
    private Socket socket;
    // 客户端的输出流 - 发送消息
    private OutputStream os;

    // 客户端的输入流 - 接收消息
    private InputStream in;

    public String loginName;

    // 构造方法
    public Client(){
        // 初始化 Socket
        this.socket = initClient ();
        try {
            // 初始IO流
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

    // 登录方法
    public String login(String username, String password) throws IOException{
        // 发送消息
        // 发送登录消息 login-用户名-id-密码
        String logininfo = "login#" + username + "#" + username.hashCode () + "#" + password;
        os.write (logininfo.getBytes ());
        os.flush ();
        loginName=username;
        // 接收消息
        return username;
    }

    int i = 0;

    // 启动消息接收器
    public void startReceiveThread(ChatUI chatUI){
        if(i == 0)
            // 客户端接收消息线程
//            new ReceiveThread (socket, chatUI).start ();
        i++;
    }

    /**
     * 消息发送方法
     *
     * @param msgTyped
     * 	  文件类型
     * @param umsg
     * 	  消息体
     * @param friendName
     * 	  对方ID
     * @throws IOException
     */
    public void sendMessage(String msgTyped, String umsg, String friendName) throws IOException{
        System.out.println ("我说：" + umsg);
        int id = friendName.hashCode ();
        if(id < 0){
            System.out.println ("我是负数~~");
        }
        System.out.println (id);
        System.out.println (friendName);
        String msg = msgTyped + "#" + umsg.length () + "#" + friendName + "#" + id + "#" + umsg;
        os.write (msg.getBytes ());
        os.flush ();
    }

    // 关闭客户端
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
        System.out.println ("服务器发送来的所有用户信息：" + message);
        // 按照消息协议解析数据 长度—用户名-id-密码
        String[] split = message.split (",");
        return split;
    }
}
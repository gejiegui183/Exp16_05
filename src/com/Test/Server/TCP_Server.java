package com.Test.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCP_Server {
    public static ServerSocket serverSocket;
    private static Socket socket;

    public static void main(String[] args) {

        try {
            System.out.println("�������Ѿ�����....");
            serverSocket = new ServerSocket(10086);
            while (true) {
                //�����ġ�ֻ�е�socket �����Ӻ�Ż�ִ�к���ĳ���
                //�п��ܶϿ���Ҳ�п��ܶ���ͻ������ӣ���������ʹ�� ��ѭ����һֱ����
                socket = serverSocket.accept();

                new Thread(new Server_getMessage(socket)).start();
                new Thread(new Server_sendMessage(socket)).start();

            }
        } catch (IOException e) {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
    }
}

class Server_getMessage implements Runnable {
    private Socket socket;


    public Server_getMessage(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len=0;
            while ((len=is.read(bytes))!=-1) {  //��ѭ��һֱ��ȡ��Ϣ
                String message = new String(bytes,0,len);
                System.out.println();
                System.out.println("�յ���Ϣ��" + message);
            }

        } catch (IOException e) {
            try {
                socket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        }
    }
}

class Server_sendMessage implements Runnable {
    private Socket socket;


    public Server_sendMessage(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("�Ѿ��пͻ�������------");
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            while (true) {   //scanner Ĭ���������ģ����������ѭ�������ͨ���Ժ�ſ��Լ���ִ�У����Ա�������һ�κ�ſ��Լ���ִ��
                System.out.print("����:");
                String message = scanner.next();
                os.write(message.getBytes());
                os.flush();
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        }
    }
}
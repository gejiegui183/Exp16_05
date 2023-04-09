package com.company.MsgServer;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientMsgSave {
    String userID , targetID;
    String message;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:MySQL://192.168.49.136:3306/QQ";
    String username = "root";
    String dbPassword = "123456";
    Connection conn = null;

    public ClientMsgSave(){}

    public ClientMsgSave(String userID , String targetID , String message){
        this.userID = userID;
        this.targetID = targetID;
        this.message = message;
    }

    public void saveMessage(){
        System.out.println("ClientMsgSave存储");
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            String saveSql = "insert into message value(?,?,?,?);";
            Class.forName(driver);
            conn = DriverManager.getConnection(url , username , dbPassword);
            System.out.println("ClientMsgSave:数据库连接成功，开始存储····");
            PreparedStatement preparedStatement = conn.prepareStatement(saveSql);
            preparedStatement.setString(1 , userID);
            preparedStatement.setString(2 , targetID);
            preparedStatement.setString(3 , dateFormat.format(date));
            preparedStatement.setString(4 , message);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ClientMsgSave:存储成功");
    }

}

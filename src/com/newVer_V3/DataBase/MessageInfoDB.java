package com.newVer_V3.DataBase;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageInfoDB {
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:MySQL://192.168.49.136:3306/QQ";
    String username = "root";
    String dbPassword = "123456";
    Connection conn = null;
    Statement statement;

    public MessageInfoDB(){
        initDataBase();
    }

    public void initDataBase(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url , username , dbPassword);
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMessage(String msgList){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        JSONObject js = new JSONObject(msgList);
        int msgType = (Integer) js.get("msgType");
        String userID = (String) js.get("userID");
        String targetID = (String) js.get("targetID");
        String sendTime = (String) js.get("sendTime");
        String content = (String) js.get("content");
        try {
            String saveSql = "insert into message value(?,?,?,?,?);";
            Class.forName(driver);
            conn = DriverManager.getConnection(url , username , dbPassword);
            PreparedStatement preparedStatement = conn.prepareStatement(saveSql);
            preparedStatement.setString(1 , userID);
            preparedStatement.setString(2 , targetID);
            preparedStatement.setString(3 , sendTime);
            preparedStatement.setInt(4 , msgType);
            preparedStatement.setString(5 , content);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

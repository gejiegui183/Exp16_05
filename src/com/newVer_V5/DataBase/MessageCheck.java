package com.newVer_V5.DataBase;

import org.json.JSONObject;

import java.sql.*;

public class MessageCheck {
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:MySQL://192.168.49.136:3306/QQ";
    String username = "root";
    String dbPassword = "123456";
    Connection conn = null;
    Statement statement;

    public MessageCheck(){
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
        JSONObject js = new JSONObject(msgList);
        int msgType = (Integer) js.get("msgType");
        String userID = (String) js.get("userID");
        String targetID = (String) js.get("friendID");
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
            System.out.println("DataBase is saving <- MessageCheck");
        }
    }

    public String messageGetter(String info){
        JSONObject jsonObject = new JSONObject(info);
        String userID = (String) jsonObject.get("userID");
        String friendID = (String) jsonObject.get("friendID");
        ResultSet rs = null;
        try {
            String sqlSent = "select * from (select * from message where senderID = '"+friendID+"' and recipientsID = '"+userID+"' order by sendTime desc limit 0 , 1) as messageList;";
            rs = statement.executeQuery(sqlSent);
            while(rs.next()) {
                JSONObject js = new JSONObject();
                js.put("sendTime" , rs.getString("sendTime"));
                js.put("content" , rs.getString("content"));
                String msgPak = js.toString();
                return msgPak;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

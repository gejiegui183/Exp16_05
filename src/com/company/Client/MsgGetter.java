package com.company.Client;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgGetter {
    String userID;
    String senderID;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:MySQL://192.168.49.136:3306/QQ";
    String username = "root";
    String dbPassword = "123456";
    Connection conn = null;
    Statement statement;

    public MsgGetter(String userID , String senderID){
        this.userID = userID;
        this.senderID = senderID;
        startDB();
    }

    public void startDB(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url , username , dbPassword);
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMsg(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("MsgGetter：正在查询");
        try {
            String sqlSent = "select * from message where recipientsID = '" + userID + "' and senderID = '" + userID + "';";
            ResultSet rs = statement.executeQuery(sqlSent);
            while(rs.next()) {
                return dateFormat.format(date) + "$$" +rs.getString("message");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("MsgGetter：查询完毕");
        return null;
    }

}

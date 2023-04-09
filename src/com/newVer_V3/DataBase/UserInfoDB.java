package com.newVer_V3.DataBase;

import java.sql.*;

public class UserInfoDB {
    String userID;
    String password;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:MySQL://192.168.49.136:3306/QQ";
    String username = "root";
    String dbPassword = "123456";
    Connection conn = null;
    Statement statement;

    public UserInfoDB(){
        dbInit();
    }

    public UserInfoDB(String userID , String password){
        this.userID = userID;
        this.password = password;
//        dbInit();
    }

    public void dbInit(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url , username , dbPassword);
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //>>--->newLoginFrame.loginAccount
    public boolean userInfoJudge (){
        try {
            String sqlSent = "select * from clientUser where ID = '" + userID + "';";
            ResultSet rs = statement.executeQuery(sqlSent);
            while(rs.next()) {
                if (rs.getString("loginPassword").equals(password)){
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserName(){
        ResultSet rs = null;
        try {
            String sqlSent = "select name from clientUser where ID = '" + userID + "';";
            rs = statement.executeQuery(sqlSent);
            while(rs.next()) {
                return rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package sample;

import dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginModel {

    Connection connection;
    //constructor
    public loginModel(){
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       // if (this.connection ==null) System.exit(1);
    }

    public boolean isDatabaseConnection(){
        if (this.connection ==null)
            return false;
        else {
            return true;
        }

    }

    public boolean isLogin(String username, String password) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "select * from admin where username = ? and password = ?";
        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1,username);
            pr.setString(2,password);

            rs = pr.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
           pr.close();
           rs.close();
        }

    }







}//class

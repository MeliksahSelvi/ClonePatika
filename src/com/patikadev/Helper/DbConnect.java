package com.patikadev.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    private Connection connect=null;

    public Connection connectDB(){
        try {
            this.connect= DriverManager.getConnection(Config.DB_URL,Config.DB_user,Config.DB_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connect;
    }

    public static Connection getInstance(){
        DbConnect db=new DbConnect();
        return db.connectDB();
    }
}

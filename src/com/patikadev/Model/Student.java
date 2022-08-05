package com.patikadev.Model;

import com.patikadev.Helper.DbConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends User{

    public Student(){}
    public Student(int id, String name, String uname, String pass, String type){
        super(id,name,uname,pass,type);
    }

    public static Student getFetch(int id){
        Student obj=null;
        String sql="SELECT * FROM users WHERE id= ?";

        try {
            PreparedStatement pr= DbConnect.getInstance().prepareStatement(sql);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Student(rs.getInt("id"),rs.getString("name"),rs.getString("uname")
                        ,rs.getString("pass"),rs.getString("type"));

            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

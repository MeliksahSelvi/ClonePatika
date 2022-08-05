package com.patikadev.Model;

import com.patikadev.Helper.DbConnect;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {

    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Patika>getList(){
        ArrayList<Patika>patikaList=new ArrayList<>();
        String query="SELECT * FROM patika";

        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                Patika patika=new Patika(rs.getInt("id"),rs.getString("name"));
                patikaList.add(patika);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patikaList;
    }

    public static boolean add(String name){
        String query="INSERT INTO patika(name) VALUES(?)";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setString(1,name);
            return  pr.executeUpdate()!=-1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public static boolean update(int id,String name){
        String query="UPDATE patika SET name=? WHERE id=?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,id);

            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Patika getFetch(int id){
        Patika obj=null;
        String sql="SELECT * FROM patika WHERE id= ?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Patika getFetch(String name){
        Patika obj=null;
        String sql="SELECT * FROM patika WHERE name= ?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,name);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id){
        String sql="DELETE FROM patika WHERE id=?";
        ArrayList<Course> courseList=Course.getList();
        for(Course course:courseList){
            if(course.getPatika().getId()==id){
                Course.delete(course.getId());
            }
        }
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}

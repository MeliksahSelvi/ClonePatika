package com.patikadev.Model;

import com.patikadev.Helper.DbConnect;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;

    public User() {}

    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList(){

        ArrayList<User> userList=new ArrayList<>();
        String query="SELECT * FROM users WHERE NOT type='operator'";

        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String uname=rs.getString("uname");
                String pass=rs.getString("pass");
                String type=rs.getString("type");
                User user=new User(id,name,uname,pass,type);
                userList.add(user);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static ArrayList<User> getList(int id){

        ArrayList<User> userList=new ArrayList<>();
        String query="SELECT * FROM users WHERE id='"+id+"'";

        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                int ID=rs.getInt("id");
                String name=rs.getString("name");
                String uname=rs.getString("uname");
                String pass=rs.getString("pass");
                String type=rs.getString("type");
                User user=new User(ID,name,uname,pass,type);
                userList.add(user);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static ArrayList<User> getEducatorList(){

        ArrayList<User> educatorList=new ArrayList<>();
        String query="SELECT * FROM users WHERE type='educator'";

        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String uname=rs.getString("uname");
                String pass=rs.getString("pass");
                String type=rs.getString("type");
                User user=new User(id,name,uname,pass,type);
                educatorList.add(user);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return educatorList;
    }

    public static boolean add(String name,String uname,String pass,String type){
        String query="INSERT INTO users(name,uname,pass,type) VALUES(?,?,?,?)";

        User findUser=User.getFetch(uname);
        if(findUser!=null){
            Helper.showMsg("Bu kullanıcı adı daha önceden eklenmiş.Lütfen farklı bir kullanıcı adı giriniz. ");
            return false;
        }
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);

            int response=pr.executeUpdate();
            if(response==-1){
                Helper.showMsg("error");
            }
            return  response!=-1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public static User getFetch(String uname){
        User obj=null;
        String sql="SELECT * FROM users WHERE uname= ?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,uname);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new User(rs.getInt("id"),rs.getString("name"),rs.getString("uname")
                        ,rs.getString("pass"),rs.getString("type"));

            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static User getFetch(String uname,String pass){
        User obj=null;
        String sql="SELECT * FROM users WHERE uname= ? AND pass=?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,uname);
            pr.setString(2,pass);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                switch (rs.getString("type")){
                    case "operator":
                        obj=new Operator();
                        break;
                    case "educator":
                        obj=new Educator();
                        break;
                    case "student":
                        obj=new Student();
                        break;
                    default:
                        obj=new User();
                        break;
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static User getFetch(int id){
        User obj=null;
        String sql="SELECT * FROM users WHERE id= ?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new User(rs.getInt("id"),rs.getString("name"),rs.getString("uname")
                        ,rs.getString("pass"),rs.getString("type"));

            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static User getFetchByName(String name){
        User obj=null;
        String sql="SELECT * FROM users WHERE name= ?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,name);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new User(rs.getInt("id"),rs.getString("name"),rs.getString("uname")
                        ,rs.getString("pass"),rs.getString("type"));

            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id){
        String sql="DELETE FROM users WHERE id=?";
        ArrayList<Course>courseList=Course.getListByUser(id);
        for(Course c:courseList){
            Course.delete(c.getId());
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

    public static boolean update(int id,String name,String uname,String pass,String type){

        String sql="UPDATE users SET name=?,uname=?,pass=?,type=? WHERE id=?";
        User findUser=User.getFetch(uname);
        if(findUser!=null && findUser.getId()!=id){
            Helper.showMsg("Bu kullanıcı adı daha önceden eklenmiş.Lütfen farklı bir kullanıcı adı giriniz. ");
            return false;
        }

        if(!type.equals("educator") && !type.equals("student")){

            Helper.showMsg("Kullanıcı türü educator veya student olmalı!");
            return false;
        }
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<User>searchUserList(String query){

        ArrayList<User> userList=new ArrayList<>();

        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                User user=new User(rs.getInt("id"),rs.getString("name"),rs.getString("uname")
                        ,rs.getString("pass"),rs.getString("type"));

                userList.add(user);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static String searchQuery(String name,String uname,String type){

        String query="SELECT * FROM users WHERE uname LIKE '%{{uname}}%' AND name LIKE '%{{name}}%'";
        query=query.replace("{{uname}}",uname);
        query=query.replace("{{name}}",name);

        if(!type.isEmpty()){
            query+="AND type='{{type}}'";
            query=query.replace("{{type}}",type);
        }
        return query;
    }
}

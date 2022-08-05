package com.patikadev.Model;

import com.patikadev.Helper.DbConnect;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int educator_id;
    private String course_name;
    private String head;
    private String descript;
    private String url;

    private Course course;

    public Content(int educator_id,String course_name, String head, String descript, String url) {
        this.educator_id=educator_id;
        this.course_name=course_name;
        this.head = head;
        this.descript = descript;
        this.url = url;
        this.course=Course.getFetch(course_name);
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getEducator_id() {
        return educator_id;
    }

    public void setEducator_id(int educator_id) {
        this.educator_id = educator_id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public static String searchQuery(String course_name,String header,int id){

        String query="SELECT * FROM content";
        if(!course_name.equals(" ")){
            if(!query.contains("WHERE")){
                query+=" WHERE ";
            }
            query+="course_name='{{course_name}}'";
            query=query.replace("{{course_name}}",course_name);
        }
        if(!header.isEmpty()){
            if(!query.contains("WHERE")){
                query+=" WHERE ";
            }
            query+=" AND ";
            query+="header='{{header}}'";
            query=query.replace("{{header}}",header);
        }
        if(course_name.equals(" ") && header.isEmpty() && id!=0){
            query+="educator_id="+id;
        }
        else if(course_name.equals(" ") && header.isEmpty() && id==0){

        }
        return query;
    }

    public static ArrayList<Content>searchContentList(String query){

        ArrayList<Content> contentList=new ArrayList<>();

        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                int id=rs.getInt("educator_id");
                String courseName=rs.getString("course_name");
                String header=rs.getString("header");
                String descript=rs.getString("descript");
                String url=rs.getString("url");
                Content content=new Content(id,courseName,header,descript,url);
                contentList.add(content);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contentList;
    }

    public static ArrayList<Content> getList(int educator_id){
        ArrayList<Content> contentList=new ArrayList<>();
        String query="SELECT * FROM content WHERE educator_id="+educator_id;
        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                int id=rs.getInt("educator_id");
                String courseName=rs.getString("course_name");
                String header=rs.getString("header");
                String descript=rs.getString("descript");
                String url=rs.getString("url");
                Content content=new Content(id,courseName,header,descript,url);
                contentList.add(content);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentList;
    }
    public static ArrayList<Content> getList(){
        ArrayList<Content> contentList=new ArrayList<>();
        String query="SELECT * FROM content";
        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                int id=rs.getInt("educator_id");
                String courseName=rs.getString("course_name");
                String header=rs.getString("header");
                String descript=rs.getString("descript");
                String url=rs.getString("url");
                Content content=new Content(id,courseName,header,descript,url);
                contentList.add(content);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentList;
    }

    public static ArrayList<Content> getList(String course_name){
        ArrayList<Content> contentList=new ArrayList<>();
        String query="SELECT * FROM content WHERE course_name='"+course_name+"'";
        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                int id=rs.getInt("educator_id");
                String courseName=rs.getString("course_name");
                String header=rs.getString("header");
                String descript=rs.getString("descript");
                String url=rs.getString("url");
                Content content=new Content(id,courseName,header,descript,url);
                contentList.add(content);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentList;
    }

    public static boolean add(int educator_id,String course_name,String header,String descript,String url){
        String query="INSERT INTO content(educator_id,course_name,header,descript,url) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setInt(1,educator_id);
            pr.setString(2,course_name);
            pr.setString(3,header);
            pr.setString(4,descript);
            pr.setString(5,url);
            return pr.executeUpdate()!=-1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean update(String oldUrl,String header,String descript,String url){

        String query="UPDATE content SET header=?,descript=?,url=? WHERE url=?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setString(1,header);
            pr.setString(2,descript);
            pr.setString(3,url);
            pr.setString(4,oldUrl);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Content getFetch(String url){
        Content obj=null;
        String sql="SELECT * FROM content WHERE url= ?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,url);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Content(rs.getInt("educator_id"),rs.getString("course_name"),rs.getString("header")
                        , rs.getString("descript"),rs.getString("url") );
            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Content getFetch(String course_name,String header){
        Content obj=null;
        String sql="SELECT * FROM content WHERE course_name= ? AND header=?";
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,course_name);
            pr.setString(2,header);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Content(rs.getInt("educator_id"),rs.getString("course_name"),rs.getString("header")
                        , rs.getString("descript"),rs.getString("url") );
            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(String url){
        String sql="DELETE FROM content WHERE url=?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,url);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean rateContent(String course_name,int student_id,String rate,String comment){
        String query="INSERT INTO rating (student_id,course_name,rate,comment) VALUES(?,?,?,?)";
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setString(2,course_name);
            pr.setInt(1,student_id);
            pr.setString(3,rate);
            pr.setString(4,comment);
            return pr.executeUpdate()!=-1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}

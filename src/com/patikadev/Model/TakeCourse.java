package com.patikadev.Model;

import com.patikadev.Helper.DbConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TakeCourse {
    private int student_id;
    private String course_name;
    private String educator_name;

    private Student student;
    private User educator;
    public TakeCourse(int student_id, String course_name,String educator_name) {
        this.student_id = student_id;
        this.course_name = course_name;
        this.educator_name=educator_name;
        this.student= Student.getFetch(student_id);
        this.educator=User.getFetchByName(educator_name);
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public static boolean add(int student_id,String course_name,String educator_name){
        String query="INSERT INTO takecourse (student_id,course_name,educator_name) VALUES(?,?,?)";

        try {
            PreparedStatement pr= DbConnect.getInstance().prepareStatement(query);
            pr.setInt(1,student_id);
            pr.setString(2,course_name);
            pr.setString(3,educator_name);
            return  pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static ArrayList<TakeCourse> getList(int student_id){
        ArrayList<TakeCourse> takeCourseList=new ArrayList<>();
        String query="SELECT * FROM takecourse WHERE student_id='"+student_id+"'";

        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                TakeCourse takeCourse=new TakeCourse(rs.getInt("student_id"),
                        rs.getString("course_name"), rs.getString("educator_name"));
                takeCourseList.add(takeCourse);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return takeCourseList;
    }
}

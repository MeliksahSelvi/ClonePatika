package com.patikadev.Model;

import com.patikadev.Helper.DbConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private String header_name;
    private String quest_name;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String answer_4;
    private String correct;

    public Quiz(String header_name, String quest_name, String answer_1, String answer_2, String answer_3, String answer_4,String correct) {
        this.header_name=header_name;
        this.quest_name = quest_name;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
        this.correct=correct;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getHeader_name() {
        return header_name;
    }

    public void setHeader_name(String header_name) {
        this.header_name = header_name;
    }

    public String getQuest_name() {
        return quest_name;
    }

    public void setQuest_name(String quest_name) {
        this.quest_name = quest_name;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }

    public static Quiz getFetch(String header,String quest_name){
        Quiz obj=null;
        String sql="SELECT * FROM quiz WHERE header=? AND quest_name=?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,header);
            pr.setString(2,quest_name);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Quiz(rs.getString("header"),rs.getString("quest_name")
                        ,rs.getString("answer_1"),rs.getString("answer_2")
                        ,rs.getString("answer_3"), rs.getString("answer_4"), rs.getString("correct"));
            }
            rs.close();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static ArrayList<Quiz> getList(String head){
        ArrayList<Quiz> quizList=new ArrayList<>();
        String query="SELECT * FROM quiz WHERE header='"+head+"'";
        try {
            Statement st = DbConnect.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                String content_name=rs.getString("header");
                String quest_name=rs.getString("quest_name");
                String answer_1=rs.getString("answer_1");
                String answer_2=rs.getString("answer_2");
                String answer_3=rs.getString("answer_3");
                String answer_4=rs.getString("answer_4");
                String correct=rs.getString("correct");
                Quiz quiz=new Quiz(content_name,quest_name,answer_1,answer_2,answer_3,answer_4,correct);
                quizList.add(quiz);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizList;
    }

    public static boolean add(String header,String quest_name,String answerA,String answerB,String answerC,String answerD,String correct){
        String query="INSERT INTO quiz(header,quest_name,answer_1,answer_2,answer_3,answer_4,correct) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setString(1,header);
            pr.setString(2,quest_name);
            pr.setString(3,answerA);
            pr.setString(4,answerB);
            pr.setString(5,answerC);
            pr.setString(6,answerD);
            pr.setString(7,correct);
            return pr.executeUpdate()!=-1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean delete(String header,String quest_name){
        String sql="DELETE FROM quiz WHERE header=? AND quest_name=?";
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,header);
            pr.setString(2,quest_name);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(String header){
        String sql="DELETE FROM quiz WHERE header=?";
        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(sql);
            pr.setString(1,header);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(String header,String quest_name,String answer_1,String answer_2,String answer_3,String answer_4,String correct){

        String query="UPDATE quiz SET quest_name=?,answer_1=?,answer_2=?,answer_3=?,answer_4=?,correct=? WHERE header=?";

        try {
            PreparedStatement pr=DbConnect.getInstance().prepareStatement(query);
            pr.setString(1,quest_name);
            pr.setString(2,answer_1);
            pr.setString(3,answer_2);
            pr.setString(4,answer_3);
            pr.setString(5,answer_4);
            pr.setString(6,correct);
            pr.setString(7,header);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}

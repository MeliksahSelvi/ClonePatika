package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Student;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_userUname;
    private JPasswordField fld_userPass;
    private JButton btn_login;
    private JButton btn_addStudent;

    public LoginGUI(){
        add(wrapper);
        setSize(400,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setResizable(false);
        setVisible(true);


        btn_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_userUname)||Helper.isFieldEmpty(fld_userPass)){
                Helper.showMsg("fill");
            }
            else{
                User u= User.getFetch(fld_userUname.getText(),fld_userPass.getText());
                if(u==null){
                    Helper.showMsg("Kullanıcı Bulunamadı!");
                }
                else{
                    switch (u.getType()){
                        case "operator":
                            OperatorGUI opGUI=new OperatorGUI((Operator) u);
                            break;
                        case "educator":
                            EducatorGUI edGUI=new EducatorGUI((Educator) u);
                            break;
                        case "student":
                            StudentGUI stGUI=new StudentGUI((Student) u);
                            break;
                    }
                    dispose();
                }
            }
        });
        btn_addStudent.addActionListener(e -> {
            StudentSignGUI studentSignGUI=new StudentSignGUI();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI guı=new LoginGUI();
    }
}

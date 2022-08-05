package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentSignGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_name;
    private JTextField fld_uname;
    private JTextField fld_pass;
    private JButton btn_sign;

    public StudentSignGUI(){
        add(wrapper);
        setSize(400,450);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setResizable(false);
        setVisible(true);

        btn_sign.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_name)|| Helper.isFieldEmpty(fld_uname) || Helper.isFieldEmpty(fld_pass)){
                Helper.showMsg("fill");
            }
            else{
                if(User.add(fld_name.getText(),fld_uname.getText(),fld_pass.getText(),"student")){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }
}

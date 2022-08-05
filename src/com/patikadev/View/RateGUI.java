package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RateGUI extends JFrame{
    private JPanel wrapper;
    private JTextArea area_comm;
    private JComboBox cmb_rate;
    private JLabel lbl_top;
    private JButton btn_rate;
    private Content content;
    private Student student;

    public RateGUI(Content content,Student student){
        this.student=student;
        this.content=content;
        add(wrapper);
        setSize(420, 270);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setVisible(true);

        lbl_top.setText(this.content.getCourse_name());

        btn_rate.addActionListener(e -> {
            if(Helper.isFieldEmpty(area_comm)){
                Helper.showMsg("fill");
            }
            else{
                if(Content.rateContent(this.content.getCourse_name(),this.student.getId(),cmb_rate.getSelectedItem().toString(),area_comm.toString())){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }
}

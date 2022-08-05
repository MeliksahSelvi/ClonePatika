package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;

public class UpdateCourseGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_courseName;
    private JButton btn_update;
    private JTextField fld_courseLang;
    private JComboBox cmb_coursePatika;
    private JComboBox cmb_courseUser;
    private Course course;

    public UpdateCourseGUI(Course course){
        this.course=course;
        add(wrapper);
        setSize(300, 350);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setVisible(true);

        fld_courseName.setText(this.course.getName());
        fld_courseLang.setText(this.course.getLang());
        loadPatikaCombo();
        loadEducatorCombo();

        btn_update.addActionListener(e -> {

            if(Helper.isFieldEmpty(fld_courseName) ||Helper.isFieldEmpty(fld_courseLang)){
                Helper.showMsg("fill");
            }
            else{
                User findUser=User.getFetchByName(cmb_courseUser.getSelectedItem().toString());
                Patika findPatika=Patika.getFetch(cmb_coursePatika.getSelectedItem().toString());
                if(Course.update(this.course.getId(),findUser.getId(),findPatika.getId(),fld_courseName.getText(),fld_courseLang.getText())){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }

    public void loadPatikaCombo(){
        cmb_coursePatika.removeAllItems();
        for(Patika patika :Patika.getList()){
            cmb_coursePatika.addItem(new Item(patika.getId(),patika.getName()));
        }
    }

    public void loadEducatorCombo(){
        cmb_courseUser.removeAllItems();
        for(User user :User.getEducatorList()){
            cmb_courseUser.addItem(new Item(user.getId(),user.getName()));
        }
    }
}

package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;

import javax.swing.*;

public class UpdateContentGUI extends JFrame {

    private JPanel wrapper;
    private JTextField fld_courseHeader;
    private JTextField fld_courseDescript;
    private JTextField fld_courseUrl;
    private JButton btn_update;

    private Content content;

    public UpdateContentGUI(Content content){

        this.content=content;
        add(wrapper);
        setSize(300, 350);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setVisible(true);

        fld_courseHeader.setText(this.content.getHead());
        fld_courseDescript.setText(this.content.getDescript());
        fld_courseUrl.setText(this.content.getUrl());
        String oldUrl=this.content.getUrl();
        btn_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_courseHeader)||Helper.isFieldEmpty(fld_courseDescript)||Helper.isFieldEmpty(fld_courseUrl)){
                Helper.showMsg("fill");
            }
            else{
                if(Content.update(oldUrl,fld_courseHeader.getText(),fld_courseDescript.getText()
                ,fld_courseUrl.getText())){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }
}

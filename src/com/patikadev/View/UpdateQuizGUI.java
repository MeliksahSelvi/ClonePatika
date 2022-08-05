package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Quiz;

import javax.swing.*;

public class UpdateQuizGUI extends JFrame{
    private JPanel wrapper;
    private JComboBox cmb_header;
    private JTextField fld_questName;
    private JTextField fld_answerA;
    private JTextField fld_answerB;
    private JTextField fld_answerC;
    private JTextField fld_answerD;
    private JButton btn_update;
    private JTextField fld_correct;

    private Quiz quiz;
    public UpdateQuizGUI(Quiz quiz){
        this.quiz=quiz;
        add(wrapper);
        setSize(400, 450);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setVisible(true);

        fld_questName.setText(this.quiz.getQuest_name());
        fld_answerA.setText(this.quiz.getAnswer_1());
        fld_answerB.setText(this.quiz.getAnswer_2());
        fld_answerC.setText(this.quiz.getAnswer_3());
        fld_answerD.setText(this.quiz.getAnswer_4());
        fld_correct.setText(this.quiz.getCorrect());


        btn_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_questName)||Helper.isFieldEmpty(fld_answerA)
                    || Helper.isFieldEmpty(fld_answerB) ||Helper.isFieldEmpty(fld_answerC)
                    || Helper.isFieldEmpty(fld_answerD) || Helper.isFieldEmpty(fld_correct)){
                Helper.showMsg("fill");
            }
            else{
                String header=this.quiz.getHeader_name();
                String quest_name=fld_questName.getText();
                String answer_1=fld_answerA.getText();
                String answer_2=fld_answerB.getText();
                String answer_3=fld_answerC.getText();
                String answer_4=fld_answerD.getText();
                String correct=fld_correct.getText();
                if(Quiz.update(header,quest_name,answer_1,answer_2,answer_3,answer_4,correct)){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }

}

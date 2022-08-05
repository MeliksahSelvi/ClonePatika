package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    private final Educator educator;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton Btn_exit;
    private JTabbedPane tap_educator;
    private JPanel pnl_courseList;
    private JTable tbl_courseList;
    private JScrollPane scrl_userList;
    private JComboBox cmb_sh_courseName;
    private JTextField fld_sh_conHead;
    private JButton btn_con_sh;
    private JScrollPane scrl_contentList;
    private JTable tbl_contentList;
    private JPanel pnl_sh;
    private JPanel pnl_content;
    private JTextField fld_questNameAdd;
    private JTextField fld_contentHeader;
    private JTextField fld_contentDescript;
    private JTextField fld_contentUrl;
    private JButton btn_contentAdd;
    private JComboBox cmb_courseName;
    private JPanel pnl_contentAdd;
    private JScrollPane scrl_quizList;
    private JPanel pnl_quizAdd;
    private JTextField fld_answerAadd;
    private JPanel pnl_quiz;
    private JTextField fld_answerBadd;
    private JTextField fld_answerCadd;
    private JTextField fld_answerDadd;
    private JButton btn_quizAdd;
    private JPanel pnl_question;
    private JComboBox cmb_quizID;
    private JTextField fld_questName;
    private JTextField fld_answerA;
    private JTextField fld_answerB;
    private JTextField fld_answerC;
    private JTextField fld_answerD;
    private JButton btn_contentList;
    private JComboBox cmb_course_header;
    private JTextField fld_headerName;
    private JComboBox cmb_quizCourse;
    private JButton btn_courseSelect;
    private JComboBox cmb_sh_course;
    private JComboBox cmb_header;
    private JButton btn_select_course;
    private JButton btn_select_head;
    private JButton btn_delete_quest;
    private JButton btn_update;
    private JTextField fld_correctAnswer;
    private JTextField fld_correctList;
    private DefaultTableModel mdl_courseList;
    private Object[]row_courseList;
    private DefaultTableModel mdl_contentList;
    private Object[]row_contentList;
    private DefaultTableModel mdl_quizList;
    private Object[]row_quizList;
    private JPopupMenu contentMenu;

    public EducatorGUI(Educator educator){
        this.educator=educator;
        add(wrapper);
        setSize(900, 700);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin : " + this.educator.getName());

        //CourseModel
        mdl_courseList=new DefaultTableModel();
        Object[] col_courseList={"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_courseList.setColumnIdentifiers(col_courseList);
        row_courseList=new Object[col_courseList.length];
        loadCourseModel();

        tbl_courseList.setModel(mdl_courseList);
        tbl_courseList.getTableHeader().setReorderingAllowed(false);
        tbl_courseList.getColumnModel().getColumn(0).setMaxWidth(75);
        loadEducatorCombo();
        //ContentModel

        contentMenu=new JPopupMenu();
        JMenuItem updateContentMenu=new JMenuItem("Düzenle");
        JMenuItem deleteContentMenu=new JMenuItem("Sil");
        contentMenu.add(updateContentMenu);
        contentMenu.add(deleteContentMenu);

        updateContentMenu.addActionListener(e -> {
            String url=tbl_contentList.getValueAt(tbl_contentList.getSelectedRow(),3).toString();
            UpdateContentGUI updateGUI=new UpdateContentGUI(Content.getFetch(url));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadContentModel();
                }
            });
        });

        deleteContentMenu.addActionListener(e-> {

            if(Helper.confirm("sure")){
                String url=tbl_contentList.getValueAt(tbl_contentList.getSelectedRow(),3).toString();
                String header=tbl_contentList.getValueAt(tbl_contentList.getSelectedRow(),1).toString();
                if(Content.delete(url)){
                    Helper.showMsg("success");
                    loadContentModel();
                    Quiz.delete(header);
                }
                else{
                    Helper.showMsg("error");
                }
            }
        });

        mdl_contentList=new DefaultTableModel();
        Object[] col_contentList={"Ders Adı","Ders Başlığı","Açıklama","Youtube Ders Linki"};
        mdl_contentList.setColumnIdentifiers(col_contentList);
        row_contentList=new Object[col_contentList.length];

        loadContentModel();

        tbl_contentList.setModel(mdl_contentList);
        tbl_contentList.setComponentPopupMenu(contentMenu);
        tbl_contentList.getTableHeader().setReorderingAllowed(false);
        tbl_contentList.getColumnModel().getColumn(0).setMinWidth(84);
        tbl_contentList.getColumnModel().getColumn(0).setMaxWidth(85);
        tbl_contentList.getColumnModel().getColumn(1).setMinWidth(94);
        tbl_contentList.getColumnModel().getColumn(1).setMaxWidth(95);
        tbl_contentList.getColumnModel().getColumn(2).setMinWidth(229);
        tbl_contentList.getColumnModel().getColumn(2).setMaxWidth(230);
        tbl_contentList.getColumnModel().getColumn(3).setMinWidth(119);
        tbl_contentList.getColumnModel().getColumn(3).setMaxWidth(120);

        Btn_exit.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI=new LoginGUI();
        });
        btn_con_sh.addActionListener(e -> {
            String course_name=cmb_sh_courseName.getSelectedItem().toString();
            String header=fld_sh_conHead.getText();
            String query= Content.searchQuery(course_name,header,this.educator.getId());
            loadContentModel(Content.searchContentList(query));
        });
        btn_contentAdd.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_contentHeader)|| Helper.isFieldEmpty(fld_contentDescript) || Helper.isFieldEmpty(fld_contentUrl)){
                Helper.showMsg("fill");
            }
            else{
                String course_name=cmb_courseName.getSelectedItem().toString();
                String header=fld_contentHeader.getText();
                String descript=fld_contentDescript.getText();
                String url=fld_contentUrl.getText();
                if(Content.add(this.educator.getId(),course_name,header,descript,url)){
                    Helper.showMsg("success");
                    loadContentModel();
                    fld_contentHeader.setText(null);
                    fld_contentDescript.setText(null);
                    fld_contentUrl.setText(null);
                }

            }
        });
        btn_quizAdd.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_questNameAdd) ||Helper.isFieldEmpty(fld_answerAadd)
            || Helper.isFieldEmpty(fld_answerBadd) || Helper.isFieldEmpty(fld_answerCadd)
            || Helper.isFieldEmpty(fld_answerDadd) || Helper.isFieldEmpty(fld_correctAnswer)){
                Helper.showMsg("fill");
            }
            else{
                if(!fld_correctAnswer.getText().equals(fld_answerAadd.getText())
                        && !fld_correctAnswer.getText().equals(fld_answerBadd.getText())
                        && !fld_correctAnswer.getText().equals(fld_answerCadd.getText())
                        && !fld_correctAnswer.getText().equals(fld_answerDadd.getText())){
                    Helper.showMsg("Doğru Cevap Şıklardan Biri Olmalı!");
                }
                else{
                    String header_name= cmb_course_header.getSelectedItem().toString();
                    String quest_name=fld_questNameAdd.getText();
                    String answerA=fld_answerAadd.getText();
                    String answerB=fld_answerBadd.getText();
                    String answerC=fld_answerCadd.getText();
                    String answerD=fld_answerDadd.getText();
                    String correct=fld_correctAnswer.getText();
                    if(Quiz.add(header_name,quest_name,answerA,answerB,answerC,answerD,correct)){
                        Helper.showMsg("success");
                        fld_answerAadd.setText(null);
                        fld_answerBadd.setText(null);
                        fld_answerCadd.setText(null);
                        fld_answerDadd.setText(null);
                        fld_questNameAdd.setText(null);
                    }
                }
            }
        });

        btn_contentList.addActionListener(e -> {
            if(cmb_quizID.getSelectedItem()!=null){
                loadQuizModel();
            }
            else{
                fld_headerName.setText(null);
                fld_questName.setText(null);
                fld_answerA.setText(null);
                fld_answerB.setText(null);
                fld_answerC.setText(null);
                fld_answerD.setText(null);
            }
        });

        btn_courseSelect.addActionListener(e -> {
            loadHeaderCombo();
        });
        btn_select_course.addActionListener(e -> {
            loadCourseSearch();
        });
        btn_select_head.addActionListener(e -> {
            loadQuizCombo();
        });
        btn_delete_quest.addActionListener(e -> {
            if(cmb_header.getSelectedItem()!=null && cmb_quizID.getSelectedItem()!=null){
                if(Quiz.delete(cmb_header.getSelectedItem().toString(),cmb_quizID.getSelectedItem().toString())){
                    Helper.showMsg("success");
                    loadQuizCombo();
                }
                else{
                    Helper.showMsg("error");
                }
            }
            else{
                Helper.showMsg("Silecek Bir Şey Yok!");
            }
        });
        btn_update.addActionListener(e -> {
            UpdateQuizGUI updateGUI=new UpdateQuizGUI(Quiz.getFetch(cmb_header.getSelectedItem().toString()
            ,cmb_quizID.getSelectedItem().toString()));

            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadQuizCombo();
                    loadQuizModel();
                }
            });
        });
    }

    public void loadQuizModel(){
        ArrayList<Quiz> list=Quiz.getList(cmb_header.getSelectedItem().toString());
        for(Quiz quiz:list){
            if(cmb_quizID.getSelectedItem().toString().equals(quiz.getQuest_name())){
                fld_headerName.setText(quiz.getHeader_name());
                fld_questName.setText(quiz.getQuest_name());
                fld_answerA.setText(quiz.getAnswer_1());
                fld_answerB.setText(quiz.getAnswer_2());
                fld_answerC.setText(quiz.getAnswer_3());
                fld_answerD.setText(quiz.getAnswer_4());
                fld_correctList.setText(quiz.getCorrect());
            }
        }
    }
    public void loadContentModel() {

        DefaultTableModel clearModel=(DefaultTableModel) tbl_contentList.getModel();
        clearModel.setRowCount(0);

        for(Content content:Content.getList(this.educator.getId())){
            int i=0;
            this.row_contentList[i++]=content.getCourse_name();
            this.row_contentList[i++]=content.getHead();
            this.row_contentList[i++]=content.getDescript();
            this.row_contentList[i]=content.getUrl();
            this.mdl_contentList.addRow(this.row_contentList);
        }
    }

    public void loadContentModel(ArrayList<Content> list){
        DefaultTableModel clearModel=(DefaultTableModel) tbl_contentList.getModel();
        clearModel.setRowCount(0);

        for(Content content:list){
            int i=0;
            this.row_contentList[i++]=content.getCourse_name();
            this.row_contentList[i++]=content.getHead();
            this.row_contentList[i++]=content.getDescript();
            this.row_contentList[i]=content.getUrl();
            this.mdl_contentList.addRow(this.row_contentList);
        }
    }

    public void loadCourseModel() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_courseList.getModel();
        clearModel.setRowCount(0);

        for(Course course:Course.getList()){
            if(course.getUser_id()==this.educator.getId()){
                int i=0;
                this.row_courseList[i++]=course.getId();
                this.row_courseList[i++]=course.getName();
                this.row_courseList[i++]=course.getLang();
                this.row_courseList[i++]=course.getPatika().getName();
                this.row_courseList[i]=course.getEducator().getName();
                this.mdl_courseList.addRow(this.row_courseList);
            }
        }
    }

    public void loadEducatorCombo(){
        cmb_sh_courseName.removeAllItems();
        cmb_sh_courseName.addItem(" ");
        cmb_courseName.removeAllItems();
        cmb_courseName.addItem(" ");
        cmb_quizCourse.removeAllItems();
        cmb_quizCourse.addItem(" ");
        cmb_sh_course.removeAllItems();
        cmb_sh_course.addItem(" ");
        for(Course course :Course.getListByUser(this.educator.getId())){
            cmb_sh_courseName.addItem(new Item(this.educator.getId(), course.getName()));
            cmb_courseName.addItem(new Item(this.educator.getId(), course.getName()));
            cmb_quizCourse.addItem(new Item(this.educator.getId(), course.getName()));
            cmb_sh_course.addItem(new Item(this.educator.getId(), course.getName()));
        }
    }

    public void loadHeaderCombo(){
        cmb_course_header.removeAllItems();
        cmb_course_header.addItem(" ");

        for(Content content :Content.getList(cmb_quizCourse.getSelectedItem().toString())){
            cmb_course_header.addItem(new Item(this.educator.getId(),content.getHead()));
        }
    }

    public void loadCourseSearch(){
        cmb_header.removeAllItems();
        cmb_header.addItem(" ");
        for(Content content :Content.getList(cmb_sh_course.getSelectedItem().toString())){
            cmb_header.addItem(new Item(this.educator.getId(),content.getHead()));
        }
    }

    public void loadQuizCombo(){
        cmb_quizID.removeAllItems();
        for(Quiz quiz :Quiz.getList(cmb_header.getSelectedItem().toString())){
            cmb_quizID.addItem(quiz.getQuest_name());
        }
    }
}

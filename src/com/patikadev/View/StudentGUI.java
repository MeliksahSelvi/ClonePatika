package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGUI extends JFrame{
    private String answer;
    private JPanel wrapper;
    private JButton btn_exit;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_student;
    private JPanel pnl_patikaList;
    private JScrollPane scrl_patikaList;
    private JTable tbl_patikaList;
    private JPanel pnl_signCourse;
    private JComboBox cmb_patikaList;
    private JComboBox cmb_courseList;
    private JButton btn_signCourse;
    private JComboBox cmb_educatorList;
    private JButton btn_selectPatika;
    private JButton btn_selectCourse;
    private JPanel pnl_sh_content;
    private JPanel pnl_content;
    private JComboBox cmb_course_list;
    private JButton btn_contentList;
    private JTable tbl_contentList;
    private JScrollPane scrl_contentList;
    private JComboBox cmb_courselist;
    private JButton btn_select_course;
    private JComboBox cmb_headerList;
    private JButton btn_select_head;
    private JComboBox cmb_quiz_id;
    private JButton btn_show;
    private JPanel pnl_quest;
    private JPanel pnl_sh;
    private JTextField fld_questname;
    private JComboBox cmb_answer;
    private JButton btn_solve;
    private JButton btn_list;
    private final Student student;
    private DefaultTableModel mdl_patikaList;
    private Object[] row_patikaList;
    private DefaultTableModel mdl_contentList;
    private Object[] row_contentList;
    private JPopupMenu contentMenu;

    public StudentGUI(Student student){
        this.student=student;
        add(wrapper);
        setSize(800, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin : " + this.student.getName());

        //ModelPatikaList

        mdl_patikaList = new DefaultTableModel();
        Object[] col_patikaList={"ID","Patika Adı"};
        mdl_patikaList.setColumnIdentifiers(col_patikaList);
        row_patikaList=new Object[col_patikaList.length];

        loadPatikaModel();

        tbl_patikaList.setModel(mdl_patikaList);
        tbl_patikaList.getTableHeader().setReorderingAllowed(false);
        tbl_patikaList.getColumnModel().getColumn(0).setMaxWidth(75);

        loadPatikaCombo();
        loadCourseListCombo();

        contentMenu=new JPopupMenu();
        JMenuItem rateContentMenu=new JMenuItem("Değerlendir");
        contentMenu.add(rateContentMenu);

        rateContentMenu.addActionListener(e -> {
            String header=tbl_contentList.getValueAt(tbl_contentList.getSelectedRow(),1).toString();
            String course_name=tbl_contentList.getValueAt(tbl_contentList.getSelectedRow(),0).toString();
            RateGUI rateGUI=new RateGUI(Content.getFetch(course_name,header),this.student);
            /*rateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadContentModel();
                }
            });*/
        });



        mdl_contentList=new DefaultTableModel();
        Object[] col_contentList={"Ders","Ders Başlığı","Açıklama","Youtube Ders Linki"};
        tbl_contentList.setComponentPopupMenu(contentMenu);
        mdl_contentList.setColumnIdentifiers(col_contentList);
        row_contentList=new Object[col_contentList.length];

        tbl_contentList.setModel(mdl_contentList);
        tbl_contentList.getTableHeader().setReorderingAllowed(false);

        btn_exit.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI=new LoginGUI();
        });
        btn_selectPatika.addActionListener(e -> {
            loadCourseCombo();
        });
        btn_selectCourse.addActionListener(e -> {
            loadEducatorCombo();
        });
        btn_signCourse.addActionListener(e -> {
            if(TakeCourse.add(this.student.getId(),cmb_courseList.getSelectedItem().toString(),cmb_educatorList.getSelectedItem().toString())){
                Helper.showMsg("success");
                loadCourseListCombo();
            }
            else{
                Helper.showMsg("error");
            }
        });
        btn_contentList.addActionListener(e -> {
            loadContentModel();
        });

        btn_select_course.addActionListener(e -> {
            loadCourseSearch();
        });
        btn_select_head.addActionListener(e -> {
            loadQuizCombo();
        });
        btn_show.addActionListener(e -> {
            if(cmb_quiz_id.getSelectedItem()!=null){
                loadQuizModel();
            }
            else{
                fld_questname.setText(null);
            }
        });
        btn_solve.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_questname)){
                Helper.showMsg("fill");
            }
            else{
                if(cmb_answer.getSelectedItem().toString().equals(this.answer)){
                    Helper.showMsg("Soruyu Dogru Bildiniz!");
                }
                else{
                    Helper.showMsg("Yanlis Cevap!");
                }
            }
        });
    }

    public void loadQuizModel(){
        ArrayList<Quiz> list=Quiz.getList(cmb_headerList.getSelectedItem().toString());
        for(Quiz quiz:list){
            if(cmb_quiz_id.getSelectedItem().toString().equals(quiz.getQuest_name())){
                fld_questname.setText(quiz.getQuest_name());
                cmb_answer.addItem(new Item(1,quiz.getAnswer_1()));
                cmb_answer.addItem(new Item(2,quiz.getAnswer_2()));
                cmb_answer.addItem(new Item(3,quiz.getAnswer_3()));
                cmb_answer.addItem(new Item(4,quiz.getAnswer_4()));
            }
            this.answer= quiz.getCorrect();
        }
    }

    public void loadContentModel() {

        DefaultTableModel clearModel=(DefaultTableModel) tbl_contentList.getModel();
        clearModel.setRowCount(0);

        for(Content content:Content.getList(cmb_course_list.getSelectedItem().toString())){
            int i=0;
            this.row_contentList[i++]=content.getCourse_name();
            this.row_contentList[i++]=content.getHead();
            this.row_contentList[i++]=content.getDescript();
            this.row_contentList[i]=content.getUrl();
            this.mdl_contentList.addRow(this.row_contentList);
        }
    }

    public void loadPatikaCombo(){
        cmb_patikaList.removeAllItems();
        for(Patika patika :Patika.getList()){
            cmb_patikaList.addItem(new Item(patika.getId(),patika.getName()));
        }
    }
    public void loadEducatorCombo(){
        cmb_educatorList.removeAllItems();
        int user_id=Course.getFetch(cmb_courseList.getSelectedItem().toString()).getUser_id();
        for(User user:User.getList(user_id)){
            cmb_educatorList.addItem(new Item(user.getId(),user.getName()));
        }
    }
    public void loadCourseCombo(){
        cmb_courseList.removeAllItems();
        int patika_id=Patika.getFetch(cmb_patikaList.getSelectedItem().toString()).getId();
        for(Course course:Course.getList(patika_id)){
            cmb_courseList.addItem(new Item(course.getId(),course.getName()));
        }
    }

    public void loadCourseSearch(){
        cmb_headerList.removeAllItems();
        cmb_headerList.addItem(" ");
        for(Content content :Content.getList(cmb_courselist.getSelectedItem().toString())){
            cmb_headerList.addItem(new Item(this.student.getId(),content.getHead()));
        }
    }

    public void loadCourseListCombo(){
        cmb_course_list.removeAllItems();
        cmb_courselist.removeAllItems();;
        int patika_id=Patika.getFetch(cmb_patikaList.getSelectedItem().toString()).getId();
        for(TakeCourse takeCourse:TakeCourse.getList(this.student.getId())){
            cmb_course_list.addItem(new Item(takeCourse.getStudent_id(),takeCourse.getCourse_name()));
            cmb_courselist.addItem(new Item(takeCourse.getStudent_id(),takeCourse.getCourse_name()));
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_patikaList.getModel();
        clearModel.setRowCount(0);

        for(Patika patika:Patika.getList()){
            int i=0;
            this.row_patikaList[i++]=patika.getId();
            this.row_patikaList[i++]=patika.getName();
            this.mdl_patikaList.addRow(this.row_patikaList);
        }
    }

    public void loadQuizCombo(){
        cmb_quiz_id.removeAllItems();
        int i=0;
        for(Quiz quiz :Quiz.getList(cmb_headerList.getSelectedItem().toString())){
            cmb_quiz_id.addItem(quiz.getQuest_name());
        }
    }
}

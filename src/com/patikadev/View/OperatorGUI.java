package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private final Operator operator;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_Exit;
    private JPanel pnl_userList;
    private JScrollPane scrl_userList;
    private JTable tbl_userList;
    private JTextField fld_userName;
    private JTextField fld_userUname;
    private JTextField fld_password;
    private JButton btn_userAdd;
    private JComboBox cmb_userType;
    private JTextField fld_userID;
    private JButton btn_userDelete;
    private JTextField fld_sh_userName;
    private JTextField fld_sh_userUname;
    private JComboBox cmb_sh_userType;
    private JButton btn_user_sh;
    private JPanel pnl_patikaList;
    private JScrollPane scrl_patikaList;
    private JTable tbl_patikaList;
    private JPanel pnl_patikaAdd;
    private JTextField fld_patikaName;
    private JButton btn_patikaAdd;
    private JPanel pnl_user_form;
    private JPanel pnl_user_sh;
    private JPanel pnl_courseList;
    private JScrollPane scrl_courseList;
    private JTable tbl_courseList;
    private JPanel pnl_courseAdd;
    private JTextField fld_courseName;
    private JTextField fld_courseLang;
    private JComboBox cmb_coursePatika;
    private JComboBox cmb_courseUser;
    private JButton btn_courseAdd;
    private JPanel pnl_content;
    private JPanel pnl_sh;
    private JComboBox cmb_sh_courseName;
    private JTextField fld_sh_conHead;
    private JButton btn_con_sh;
    private JScrollPane scrl_contentList;
    private JTable tbl_contentList;
    private JPanel pnl_quiz;
    private JScrollPane scrl_quizList;
    private JPanel pnl_question;
    private JTextField fld_questName;
    private JTextField fld_answerA;
    private JTextField fld_answerB;
    private JTextField fld_answerC;
    private JTextField fld_answerD;
    private JTextField fld_headerName;
    private JTextField fld_correctList;
    private JButton btn_contentList;
    private JComboBox cmb_quizID;
    private JComboBox cmb_sh_course;
    private JComboBox cmb_header;
    private JButton btn_select_course;
    private JButton btn_select_head;
    private JButton btn_delete_quest;
    private JButton btn_update;
    private JComboBox cmb_educator;
    private JButton btn_select_educator;
    private DefaultTableModel mdl_userList;
    private Object[] row_userList;
    private DefaultTableModel mdl_patikaList;
    private Object[] row_patikaList;
    private DefaultTableModel mdl_courseList;
    private Object[]row_courseList;
    private DefaultTableModel mdl_contentList;
    private Object[]row_contentList;
    private JPopupMenu patikaMenu;
    private JPopupMenu courseMenu;
    private JPopupMenu contentMenu;

    public OperatorGUI(Operator operator) {
        this.operator = operator;
        add(wrapper);
        setSize(800, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.Project_Title);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin : " + this.operator.getName());


        //ModelUserList
        mdl_userList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {

                if(column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_userList = {"ID", "Ad Soyad", "Kullanici Adi", "Sifre", "Uyelik Tipi"};
        mdl_userList.setColumnIdentifiers(col_userList);

        row_userList=new Object[col_userList.length];
        loadUserModel();

        tbl_userList.setModel(mdl_userList);
        tbl_userList.getTableHeader().setReorderingAllowed(false);

        tbl_userList.getSelectionModel().addListSelectionListener(e -> {

            try {

                String select_user_id=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),0).toString();
                fld_userID.setText(select_user_id);
            }catch (Exception exception){

            }
        });

        tbl_userList.getModel().addTableModelListener(e ->{

            if(e.getType()== TableModelEvent.UPDATE){
                int user_id=Integer.parseInt(tbl_userList.getValueAt(tbl_userList.getSelectedRow(),0).toString());
                String user_name=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),1).toString();
                String user_uname=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),2).toString();
                String user_pass=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),3).toString();
                String user_type=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),4).toString();


                if(User.update(user_id,user_name,user_uname,user_pass,user_type)){
                    Helper.showMsg("success");
                }
                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });

        //ModelPatikaList
        patikaMenu=new JPopupMenu();
        JMenuItem updatePatikaMenu=new JMenuItem("Güncelle");
        JMenuItem deletePatikaMenu=new JMenuItem("Sil");
        patikaMenu.add(updatePatikaMenu);
        patikaMenu.add(deletePatikaMenu);

        updatePatikaMenu.addActionListener(e -> {
            int select_id=Integer.parseInt(tbl_patikaList.getValueAt(tbl_patikaList.getSelectedRow(),0).toString());
            UpdatePatikaGUI updateGUI=new UpdatePatikaGUI(Patika.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });

        deletePatikaMenu.addActionListener(e-> {

            if(Helper.confirm("sure")){
                int select_id=Integer.parseInt(tbl_patikaList.getValueAt(tbl_patikaList.getSelectedRow(),0).toString());
                if(Patika.delete(select_id)){
                    Helper.showMsg("success");
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
                else{
                    Helper.showMsg("error");
                }
            }
        });

        mdl_patikaList = new DefaultTableModel();
        Object[] col_patikaList={"ID","Patika Adı"};
        mdl_patikaList.setColumnIdentifiers(col_patikaList);
        row_patikaList=new Object[col_patikaList.length];
        loadPatikaModel();

        tbl_patikaList.setModel(mdl_patikaList);
        tbl_patikaList.setComponentPopupMenu(patikaMenu);
        tbl_patikaList.getTableHeader().setReorderingAllowed(false);
        tbl_patikaList.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_patikaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point=e.getPoint();
                int selected_row=tbl_patikaList.rowAtPoint(point);
                tbl_patikaList.setRowSelectionInterval(selected_row,selected_row);
            }
        });

        //courseList

        courseMenu=new JPopupMenu();
        JMenuItem updateCourseMenu=new JMenuItem("Güncelle");
        JMenuItem deleteCourseMenu=new JMenuItem("Sil");
        courseMenu.add(updateCourseMenu);
        courseMenu.add(deleteCourseMenu);

        updateCourseMenu.addActionListener(e -> {
            int select_id=Integer.parseInt(tbl_courseList.getValueAt(tbl_courseList.getSelectedRow(),0).toString());
            UpdateCourseGUI updateGUI=new UpdateCourseGUI(Course.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCourseModel();
                }
            });
        });

        deleteCourseMenu.addActionListener(e-> {

            if(Helper.confirm("sure")){
                int select_id=Integer.parseInt(tbl_courseList.getValueAt(tbl_courseList.getSelectedRow(),0).toString());
                if(Course.delete(select_id)){
                    Helper.showMsg("success");
                    loadCourseModel();
                }
                else{
                    Helper.showMsg("error");
                }
            }
        });

        mdl_courseList=new DefaultTableModel();
        Object[] col_courseList={"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_courseList.setColumnIdentifiers(col_courseList);
        row_courseList=new Object[col_courseList.length];
        loadCourseModel();

        tbl_courseList.setModel(mdl_courseList);
        tbl_courseList.getTableHeader().setReorderingAllowed(false);
        tbl_courseList.setComponentPopupMenu(courseMenu);
        tbl_courseList.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_courseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point=e.getPoint();
                int selected_row=tbl_courseList.rowAtPoint(point);
                tbl_courseList.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        loadPatikaCombo();
        loadEducatorCombo();

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

        loadCourseCombo();

        btn_userAdd.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_userName) || Helper.isFieldEmpty(fld_userUname)|| Helper.isFieldEmpty(fld_password)){
                Helper.showMsg("fill");
            }
            else{

                String name=fld_userName.getText();
                String uname=fld_userUname.getText();
                String pass=fld_password.getText();
                String type=cmb_userType.getSelectedItem().toString();
                if(User.add(name,uname,pass,type)){
                    Helper.showMsg("success");
                    loadUserModel();
                    loadEducatorCombo();
                    fld_userName.setText(null);
                    fld_userUname.setText(null);
                    fld_password.setText(null);
                }
            }
        });
        btn_userDelete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_userID)){
                Helper.showMsg("fill");
            }
            else{
                if(Helper.confirm("sure")){
                    int user_id=Integer.parseInt(fld_userID.getText());
                    if(User.delete(user_id)){
                        Helper.showMsg("success");
                        loadUserModel();
                        loadEducatorCombo();
                        loadCourseModel();
                        fld_userID.setText(null);
                    }
                    else{
                        Helper.showMsg("error ");
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String name=fld_sh_userName.getText();
            String uname=fld_sh_userUname.getText();
            String type=cmb_sh_userType.getSelectedItem().toString();
            String query=User.searchQuery(name,uname,type);
            loadUserModel(User.searchUserList(query));
        });
        btn_Exit.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI=new LoginGUI();
        });
        btn_patikaAdd.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_patikaName)){
                Helper.showMsg("fill");
            }
            else{
                String name=fld_patikaName.getText();

                if(Patika.add(name)){
                    Helper.showMsg("success");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patikaName.setText(null);
                }
                else{
                    Helper.showMsg("error");
                }
            }
        });
        btn_courseAdd.addActionListener(e -> {
            Item patikaItem= (Item) cmb_coursePatika.getSelectedItem();
            Item userItem=(Item) cmb_courseUser.getSelectedItem();

            if(Helper.isFieldEmpty(fld_courseName) || Helper.isFieldEmpty(fld_courseLang)){
                Helper.showMsg("fill");
            }
            else{
                if(Course.add(userItem.getKey(),patikaItem.getKey(),fld_courseName.getText(),fld_courseLang.getText())){
                    Helper.showMsg("success");
                    loadCourseModel();
                    fld_courseLang.setText(null);
                    fld_courseName.setText(null);
                }
                else{
                    Helper.showMsg("error");
                }
            }
        });
        btn_con_sh.addActionListener(e -> {
            String course_name=cmb_sh_courseName.getSelectedItem().toString();
            String header=fld_sh_conHead.getText();
            String query= Content.searchQuery(course_name,header,0);
            loadContentModel(Content.searchContentList(query));
        });
        btn_select_educator.addActionListener(e -> {
            loadCourseCmb();
        });
        btn_select_course.addActionListener(e -> {
            loadCourseSearch();
        });
        btn_select_head.addActionListener(e -> {
            loadQuizCombo();
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

    public void loadQuizCombo(){
        cmb_quizID.removeAllItems();
        for(Quiz quiz :Quiz.getList(cmb_header.getSelectedItem().toString())){
            cmb_quizID.addItem(quiz.getQuest_name());
        }
    }

    public void loadCourseSearch(){
        cmb_header.removeAllItems();
        cmb_header.addItem(" ");
        int user_id=User.getFetchByName(cmb_educator.getSelectedItem().toString()).getId();
        for(Content content :Content.getList(cmb_sh_course.getSelectedItem().toString())){
            cmb_header.addItem(new Item(user_id,content.getHead()));
        }
    }
    public void loadContentModel() {

        DefaultTableModel clearModel=(DefaultTableModel) tbl_contentList.getModel();
        clearModel.setRowCount(0);

        for(Content content:Content.getList()){
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

    public void loadCourseCombo(){
        cmb_sh_courseName.removeAllItems();
        cmb_sh_courseName.addItem(" ");
        for(Course course :Course.getList()){
            cmb_sh_courseName.addItem(new Item(course.getId(), course.getName()));
        }
    }

    public void loadCourseModel() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_courseList.getModel();
        clearModel.setRowCount(0);

        for(Course course:Course.getList()){
            int i=0;
            this.row_courseList[i++]=course.getId();
            this.row_courseList[i++]=course.getName();
            this.row_courseList[i++]=course.getLang();
            this.row_courseList[i++]=course.getPatika().getName();
            this.row_courseList[i++]=course.getEducator().getName();
            this.mdl_courseList.addRow(this.row_courseList);
        }
    }

    public void loadPatikaModel() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_patikaList.getModel();
        clearModel.setRowCount(0);

        for(Patika patika:Patika.getList()){
            int i=0;
            this.row_patikaList[i++]=patika.getId();
            this.row_patikaList[i++]=patika.getName();
            this.mdl_patikaList.addRow(this.row_patikaList);
        }
    }

    public void loadUserModel(){
        DefaultTableModel clearModel=(DefaultTableModel) tbl_userList.getModel();
        clearModel.setRowCount(0);

        for(User user:User.getList()){
            int i=0;
            this.row_userList[i++]=user.getId();
            this.row_userList[i++]=user.getName();
            this.row_userList[i++]=user.getUname();
            this.row_userList[i++]=user.getPass();
            this.row_userList[i++]=user.getType();
            this.mdl_userList.addRow(this.row_userList);
        }
    }

    public void loadUserModel(ArrayList<User> list){
        DefaultTableModel clearModel=(DefaultTableModel) tbl_userList.getModel();
        clearModel.setRowCount(0);

        for(User user:list){
            int i=0;
            this.row_userList[i++]=user.getId();
            this.row_userList[i++]=user.getName();
            this.row_userList[i++]=user.getUname();
            this.row_userList[i++]=user.getPass();
            this.row_userList[i++]=user.getType();
            this.mdl_userList.addRow(this.row_userList);
        }
    }

    public void loadPatikaCombo(){
        cmb_coursePatika.removeAllItems();
        for(Patika patika :Patika.getList()){
            cmb_coursePatika.addItem(new Item(patika.getId(),patika.getName()));
        }
    }

    public void loadEducatorCombo(){
        cmb_courseUser.removeAllItems();
        cmb_educator.removeAllItems();
        for(User user :User.getEducatorList()){
            cmb_courseUser.addItem(new Item(user.getId(),user.getName()));
            cmb_educator.addItem(new Item(user.getId(),user.getName()));
        }
    }

    public void loadCourseCmb(){
        cmb_sh_course.removeAllItems();
        cmb_sh_course.addItem(" ");
        int user_id=User.getFetchByName(cmb_educator.getSelectedItem().toString()).getId();
        for(Course course :Course.getListByUser(user_id)){
            cmb_sh_course.addItem(new Item(user_id, course.getName()));
        }
    }
}

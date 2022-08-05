package com.patikadev.Helper;

import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){

        for(UIManager.LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){

            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | IllegalAccessException | UnsupportedLookAndFeelException |
                         InstantiationException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public static int screenCenterPoint(String eksen, Dimension size){

        int point=0;

        switch (eksen){
            case "x":
                point=(Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y":
                point=(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point=0;
                break;
        }

        return  point;
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().length()==0 ? true:false ;
    }
    public static boolean isFieldEmpty(JTextArea field){
        return field.getText().trim().length()==0 ? true:false ;
    }

    public static void showMsg(String str){
        optionPageTR();
        String msg,title;

        switch (str){

            case "fill":
                msg="Lütfen Tüm Alanları Doldurunuz!";
                title="Hata!";
                break;
            case "success":
                msg="İşlem Başarılı.";
                title="Sonuç";
                break;
            case "error":
                msg="Bir Hata Oluştu!";
                title="Hata!";
                break;
            default:
                msg=str;
                title="Mesaj";
        }

        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str){
        optionPageTR();
        String msg;

        switch (str){

            case "sure":
                msg="Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
                break;
            default:
                msg=str;
                break;
        }

        return JOptionPane.showConfirmDialog(null,msg,"Son Kararın Mı ?",JOptionPane.YES_NO_OPTION)==0;
    }

    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }
}

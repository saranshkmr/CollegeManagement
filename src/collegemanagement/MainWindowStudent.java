/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegemanagement;

import static collegemanagement.MainWindowStaff.fid;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.imageio.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Calendar;


/**
 *
 * @author sksha
 */

public class MainWindowStudent extends javax.swing.JFrame {
    
    /**
     * Creates new form MainWindow
     */
ArrayList<String> feedbackFid=new ArrayList<String>();
ArrayList<String> feedbackCid=new ArrayList<String>();
ArrayList<String> registrationRegularCname=new ArrayList<String>();
ArrayList<String> registrationImprovementCid=new ArrayList<String>();
ArrayList<String> registrationRegularSelectedCname=new ArrayList<String>();
ArrayList<Integer> registrationImprovementSelectedCid=new ArrayList<Integer>();
Connection conn=null;
ResultSet rs=null;
PreparedStatement pst=null;
static String sid;
String studentName="",semester="",dob="",studentClass,gender;
ImageIcon imageIcon=null;
Image image;


    public MainWindowStudent() {
        initComponents();
        conn=JavaConnect.connectDb();
   
        btn_save.setVisible(false);
        updateProfile();
        updateFaculty();
        updateChooseSubject();
        updateChooseClass();
        updateRegisteredCourses();
        updateExaminationAdmitCard();
        updateHostelRequest();
        updateHostelGuest();
        updateSemesterListInExamResult();
        updateFeedback();
        updateRegularRegistration();
        updateImprovementRegistration();
         tbl_attendance.setVisible(false);
         DefaultTableModel model = new DefaultTableModel(new String[]{"Course Name", "Marks","Max Marks","Credits Earned","Improvement Number"}, 0);
        tbl_result.setModel(model);
    }
     private void updateProfile()
     {
         try{
           
          Calendar calendar = Calendar.getInstance();

          java.util.Date currentDate = calendar.getTime();
             java.sql.Date date = new java.sql.Date(currentDate.getTime());
             System.out.println("date=" + date);
              lbl_username.setText(sid);
             System.out.println("ok man");
             String sql="select * from student where concat (roll_no_part_1, '/', roll_no_part_2, '/', roll_no_part_3) =?";
             String branchName = "",sql2="select department.dname from studentofdepartment, depaRTMENT WHERE studentofdepartment.did=department.did and studentofdepartment.sid=? ";
             pst=conn.prepareStatement(sql2);
              pst.setString(1,sid);
             rs=pst.executeQuery();
             while(rs.next())
             {
                    branchName=rs.getString("dname");
                  //  JOptionPane.showMessageDialog(null, branchName);
             }
         pst=conn.prepareStatement(sql);
         pst.setString(1,sid);
         //pst.setString(2, sid.sid2);
         //pst.setString(3,Integer.toString(sid.sid3));
         rs=pst.executeQuery();
         if(rs.next())
         {
             System.out.println("i ma here");
             String studentId,firstName,middleName,lastName,cateogory,admission,degree,ftPt,specialization,section;
             studentId=sid;
             semester=rs.getString("semester");
             firstName=rs.getString("FirstName");
             middleName=rs.getString("MiddleName");
             lastName=rs.getString("LastName");
             studentName=firstName+" "+middleName+" "+lastName;
             dob=rs.getString("DOB");
             gender=rs.getString("gender");
             cateogory=rs.getString("category");
             admission=rs.getString("admission");
            String gen=gender.equals("0")?"Male":"Female";
             degree=rs.getString("degree");
             ftPt=rs.getString("ft_pt");
            // specialization=rs.getString("specialization");
             section=rs.getString("section");
             studentClass=branchName+section;
             lbl_student_id.setText(studentId);
             lbl_student_name.setText(studentName);
             lbl_dob.setText(dob);
             lbl_gender.setText(gen);
             lbl_cateogary.setText(cateogory);
             lbl_admission.setText(admission);
             lbl_branch_name.setText(branchName);
             lbl_degree.setText(degree);
             lbl_ft_pt.setText(ftPt);
             //lbl_specialization.setText(specialization);
             lbl_section.setText(section);
             //gender="0";
           
  
                  

             if(gender.equals("0"))
             {
                 imageIcon = new ImageIcon(getClass().getResource("/collegemanagement/boy_fly.jpg"));
             }
             else
             {
              imageIcon = new ImageIcon(getClass().getResource("/collegemanagement/girl_fly.jpg"));
              }
              image = imageIcon.getImage(); 

              Image newimg = image.getScaledInstance(80, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 

              imageIcon = new ImageIcon(newimg);
                 lbl_image.setIcon(imageIcon);
         }
         }catch(SQLException e)
         {
            JOptionPane.showMessageDialog(null, "updateProfile()/n"+e);
         }
     }
     
     private void updateFaculty()
     {
          String sql="SELECT FirstName, MiddleName, LastName FROM staff";
      try{  
          pst=conn.prepareStatement(sql);
         rs=pst.executeQuery();
         while(rs.next())
         {
             String fn,mn,ln;
             fn=rs.getString("FirstName");
             mn=rs.getString("MiddleName");
             ln=rs.getString("LastName");
             
             choice_faculty.add(fn+" "+mn+" "+ln);
         }
      }catch(Exception e)
      {
          JOptionPane.showMessageDialog(null, "updateFaculty()/n"+e);
      }
     }
     
     private void updateChooseSubject()
     {
                  String sql="Select course.Cname " +
"From course,enroll " +
"Where course.cid = enroll.Cid and enroll.Sid =? and"
        +" enroll.semester=(select semester from student where concat(roll_no_part_1,'/',roll_no_part_2,'/',roll_no_part_3)= ?)";
      try{   pst=conn.prepareStatement(sql);
         pst.setString(1,sid);
         pst.setString(2,sid);
         rs=pst.executeQuery();
         while(rs.next())
         {
             choice_subject.add(rs.getString("Cname"));
         }
      }catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, "updateChooseSubject()/n"+e);
      }
        
     }
     
     private void updateChooseClass()
     {
         choice_class.add("COE1");
         choice_class.add("COE2");
         choice_class.add("COE3");
         choice_class.add("ECE1");
         choice_class.add("ECE2");
         choice_class.add("ECE3");
         choice_class.add("IT1");
         choice_class.add("IT2");
         choice_class.add("IT3");
         choice_class.add("MPAE1");
         choice_class.add("MPAE2");
         choice_class.add("MPAE3");

     }
     private void updateHostelRequest()
     {
         String date,status,selected=choice_subject.getSelectedItem();
               String sql="{?= call isHosteller(?)}";
            
  DefaultTableModel model = new DefaultTableModel(new String[]{"ROOM", "HOSTEL","SEMESTER"}, 0);
                try{ 
                   CallableStatement cst = conn.prepareCall(sql);
                   cst.setString(2,sid);
                   cst.registerOutParameter(1,Types.INTEGER);
                   cst.execute();
                    System.out.println("haan");
                   int ret=cst.getInt(1);
                   System.out.println("value of return");
                   if(ret==0)
                   {
                       btn_get_hostel.setVisible(true);
                       tbl_hostel_request.setVisible(false);
                       System.out.println(ret);
                   }
                   else
                   {  
                       System.out.println(ret);
                       tbl_hostel_request.setVisible(true);
                       btn_get_hostel.setVisible(false);
                       sql="Select rooms.roomno,rooms.blockname,rooms.sab , lives.semester, hostel.HostelName " +
                                  "From lives,hostel,roomsinhostel,rooms " +
                                   "Where rooms.roomid=lives.roomid and lives.sid = ? and lives.roomID = roomsinhostel.roomID and roomsinhostel.HostelID = hostel.hostelID ";
                        pst=conn.prepareStatement(sql);
                        pst.setString(1, sid);
                        rs=pst.executeQuery();
                      
                        while(rs.next())
                        {
                           String roomno,blockname,sab,semester,hostelname;
                           roomno=rs.getString("roomno");
                           blockname=rs.getString("blockname");
                           sab=rs.getString("sab");
                           semester=rs.getString("semester");
                           hostelname=rs.getString("hostelname");
                           if(sab.equals("s"))
                               sab="";
                            model.addRow(new Object[]{blockname+roomno+sab,hostelname,semester});
                        }
                       
                   }
      }catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, "updateHostelRequest()/n"+e);
      }
                 tbl_hostel_request.setModel(model);
                        tbl_hostel_request.setDefaultEditor(Object.class,null);
     }
     
     private void updateHostelGuest()
     {
         String date,status,selected=choice_subject.getSelectedItem();
               String sql="SELECT `GuestName`, rooms.RoomNo, rooms.BlockName,hostel.HostelName,`GGender`, `GRelation`, `GContact`, `GPurpose`, `GCheckIn`, `GCheckOut`" +
"FROM `hostel_guests`,books,hostel_guests_in_room,rooms,hostel,roomsinhostel " +
"WHERE hostel_guests.GuestID = books.GuestID and books.BookersID = ? "
                       + " and hostel_guests_in_room.GuestID = books.GuestID "
+ "and rooms.RoomID = hostel_guests_in_room.RoomID and rooms.RoomID = roomsinhostel.RoomID AND roomsinhostel.HostelID = hostel.HostelID";

 DefaultTableModel model = new DefaultTableModel(new String[]{"ROOM", "HOSTEL","GUEST NAME","RELATION","PURPOSE","CHECK IN","CHECK OUT"}, 0);

                try{ 
                  
                        pst=conn.prepareStatement(sql);
                        pst.setString(1, sid);
                        rs=pst.executeQuery();
                        while(rs.next())
                        {
                           String roomNo,blockName,hostelName,guestName,gRelation,gPurpose,checkIn,checkOut;
                           roomNo=rs.getString("roomno");
                           blockName=rs.getString("blockname");
                           hostelName=rs.getString("HostelName");
                           guestName=rs.getString("GuestName");
                           gRelation=rs.getString("GRelation");
                           gPurpose=rs.getString("GPurpose");
                           checkIn=rs.getString("GCheckIn");
                           checkOut=rs.getString("GCheckOut");
                           model.addRow(new Object[]{blockName+roomNo,hostelName,guestName,gRelation,gPurpose,checkIn,checkOut});
                        }
                       
                   }
      catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null,"updateHostelGuest()/n"+ e);
      }
                 tbl_guest_history.setModel(model);
                        tbl_guest_history.setDefaultEditor(Object.class,null);
     }
     
     private void updateRegisteredCourses()
     {
         String sql="Select distinct(course.Cname), (course.CID) " +
                     "From course,enroll,improvementregisteration,student " +
                     "Where course.CID = enroll.Cid and enroll.Sid = ? and "+
                        "enroll.semester = (select semester from student where " +
                 "concat(student.roll_no_part_1,'/',student.roll_no_part_2,'/',student.roll_no_part_3) = ?) " +
                 "or (improvementregisteration.sid = concat(student.roll_no_part_1,'/',student.roll_no_part_2,'/',student.roll_no_part_3) " +
                 "and improvementInSemester = (select semester from student where " +
                 "concat(student.roll_no_part_1,'/',student.roll_no_part_2,'/',student.roll_no_part_3) = ?) " +
                 "and improvementregisteration.cid = course.cid)";
                 //lst_current_registered_courses
               try{
                   pst=conn.prepareStatement(sql);
                   pst.setString(1,sid);
                   pst.setString(2,sid);
                   pst.setString(3,sid);
                   rs=pst.executeQuery();
                   
                   while(rs.next())
                   {
                      lst_current_registered_courses.add(rs.getString("cname"));
                   }
               }catch(SQLException e)
               {
                   JOptionPane.showMessageDialog(null,"updateRegisteredCourses "+ e);
               }
                 
     }
     
     private void updateExaminationAdmitCard()
     {
         lbl_admit_card_name.setText(studentName);
         lbl_admit_card_roll_no.setText(sid);
         lbl_admit_card_semester.setText(semester);
         lbl_admit_card_dob.setText(dob);
          Image newimg = image.getScaledInstance(103, 127,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 

              imageIcon = new ImageIcon(newimg);
         lbl_examination_admit_card_img.setIcon(imageIcon);
         String sql="Select course.Cname, course.CID " +
                     "From course,enroll " +
                     "Where course.CID = enroll.Cid and enroll.Sid = ?";
                 
               try{
                   pst=conn.prepareStatement(sql);
                   pst.setString(1,sid);
                   rs=pst.executeQuery();
                   
                   while(rs.next())
                   {
                      lst_examination_admit_card_courses.add(rs.getString("cname"));
                   }
               }catch(SQLException e)
               {
                   JOptionPane.showMessageDialog(null, "updateExaminationAdmitCard"+ e);
               }
     }
     private void updateSemesterListInExamResult()
     {
         choice_exam_result.add("1");
         choice_exam_result.add("2");
         choice_exam_result.add("3");
         choice_exam_result.add("4");
         choice_exam_result.add("5");
         choice_exam_result.add("6");
         choice_exam_result.add("7");
         choice_exam_result.add("8");
     }
     
     private void updateFeedback()
     {
         choice_feedback_rating.add("1");
         choice_feedback_rating.add("2");
         choice_feedback_rating.add("3");
         choice_feedback_rating.add("4");
         choice_feedback_rating.add("5");
         String sql="{call getProfessorsForFeedback(?)}"; 
        // JOptionPane.showMessageDialog(null,"bahar hai");
         try{
             CallableStatement cst = conn.prepareCall(sql);
           cst.setString(1,sid);
           rs=cst.executeQuery();
          //JOptionPane.showMessageDialog(null,"hello");
           while(rs.next())
           {
               //FID,ProfessorName,courseName,cid
               feedbackFid.add(rs.getString("FID"));
               feedbackCid.add(rs.getString("cid"));
               choice_feedback_faculty.add(rs.getString("ProfessorName")+", "+rs.getString("courseName"));
               
           }
           rs.close();
         }
         catch(SQLException e)
         {
             JOptionPane.showMessageDialog(null,"updateFeedback" +e);
         }
         //getProfessorsForFeedback
     }
     
     private void updateRegularRegistration()
     {
         String sql="{call getAvailableCourses(?)}";
         try{
           CallableStatement cst = conn.prepareCall(sql);
           cst.setString(1,sid);
           rs=cst.executeQuery();
         //  JOptionPane.showMessageDialog(null,"hai yahan");
          /* rs=cst.getResultSet();
           rs.close();
           if(cst.getMoreResults())
           {
            rs=cst.getResultSet();
           rs.close(); 
           if(cst.getMoreResults())
           {
            rs=cst.getResultSet(); 
           }
          
      }*/   registrationRegularCname.clear();
            while(rs.next())
           {
           choice_regular_registration_course.add(rs.getString("CName")+" ,"+rs.getString("CourseType"));
           list_reguar_registration_available_courses.add(rs.getString("CName")+" ,"+rs.getString("CourseType"));
           registrationRegularCname.add(rs.getString("cName"));
          
           }
           // rs.close();
     }catch(SQLException e)
     {
         JOptionPane.showMessageDialog(null,"updateRegularRegistration"+e);
     }
  
     }
     private void updateImprovementRegistration()
     {
         String sql="{call getCoursesForImprovement(?)}";
         try{
           CallableStatement cst = conn.prepareCall(sql);
           cst.setString(1,sid);
           rs=cst.executeQuery();
           /*rs=cst.getResultSet();
           rs.close();
           
           if(cst.getMoreResults())
           {
            rs=cst.getResultSet(); 
           }*/
           registrationImprovementCid.clear();
           while(rs.next())
           {
           choice_improvement_registration_course.add(rs.getString("CName")+" ,"+rs.getString("CourseType"));
           list_improvement_registration_available_courses.add(rs.getString("CName")+" ,"+rs.getString("CourseType"));
           registrationImprovementCid.add(rs.getString("cid"));
     
           }
           // rs.close();
     }catch(SQLException e)
     {
         JOptionPane.showMessageDialog(null,"updateImprovementRegistration"+e);
     }
     }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lbl_image = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        label2 = new java.awt.Label();
        lbl_student_id = new java.awt.Label();
        label4 = new java.awt.Label();
        lbl_student_name = new java.awt.Label();
        label6 = new java.awt.Label();
        lbl_dob = new java.awt.Label();
        label8 = new java.awt.Label();
        lbl_gender = new java.awt.Label();
        label10 = new java.awt.Label();
        lbl_cateogary = new java.awt.Label();
        label12 = new java.awt.Label();
        lbl_admission = new java.awt.Label();
        label14 = new java.awt.Label();
        lbl_branch_name = new java.awt.Label();
        label16 = new java.awt.Label();
        lbl_degree = new java.awt.Label();
        label18 = new java.awt.Label();
        lbl_ft_pt = new java.awt.Label();
        label20 = new java.awt.Label();
        lbl_specialization = new java.awt.Label();
        label22 = new java.awt.Label();
        lbl_section = new java.awt.Label();
        jScrollPane_view = new javax.swing.JScrollPane();
        jScrollPane_view.getVerticalScrollBar().setUnitIncrement(16);
        jPanel_view = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        txt_jee_roll_no = new javax.swing.JTextField();
        label3 = new java.awt.Label();
        txt_student_id = new javax.swing.JTextField();
        label5 = new java.awt.Label();
        txt_name = new javax.swing.JTextField();
        label7 = new java.awt.Label();
        txt_dob = new javax.swing.JTextField();
        label9 = new java.awt.Label();
        txt_gender = new javax.swing.JTextField();
        label11 = new java.awt.Label();
        txt_cateogory = new javax.swing.JTextField();
        label13 = new java.awt.Label();
        txt_sub_cateogory = new javax.swing.JTextField();
        label39 = new java.awt.Label();
        txt_degree = new javax.swing.JTextField();
        label42 = new java.awt.Label();
        txt_semester = new javax.swing.JTextField();
        label40 = new java.awt.Label();
        txt_branch = new javax.swing.JTextField();
        label41 = new java.awt.Label();
        txt_section = new javax.swing.JTextField();
        label15 = new java.awt.Label();
        txt_minority = new javax.swing.JTextField();
        label17 = new java.awt.Label();
        txt_email = new javax.swing.JTextField();
        label19 = new java.awt.Label();
        txt_mobile = new javax.swing.JTextField();
        label21 = new java.awt.Label();
        txt_marital_status = new javax.swing.JTextField();
        label23 = new java.awt.Label();
        txt_blood_group = new javax.swing.JTextField();
        label24 = new java.awt.Label();
        txt_father_name = new javax.swing.JTextField();
        label25 = new java.awt.Label();
        txt_father_mobile = new javax.swing.JTextField();
        label26 = new java.awt.Label();
        txt_father_email = new javax.swing.JTextField();
        label27 = new java.awt.Label();
        txt_mother_name = new javax.swing.JTextField();
        label28 = new java.awt.Label();
        txt_parent_annual_income = new javax.swing.JTextField();
        label29 = new java.awt.Label();
        txt_bank_a_c_number = new javax.swing.JTextField();
        label30 = new java.awt.Label();
        txt_bank_name = new javax.swing.JTextField();
        label31 = new java.awt.Label();
        txt_ifsc_code = new javax.swing.JTextField();
        label32 = new java.awt.Label();
        txt_aadhar_number = new javax.swing.JTextField();
        label33 = new java.awt.Label();
        txt_house_number = new javax.swing.JTextField();
        label34 = new java.awt.Label();
        txt_street_name = new javax.swing.JTextField();
        label35 = new java.awt.Label();
        txt_city = new javax.swing.JTextField();
        label36 = new java.awt.Label();
        txt_state = new javax.swing.JTextField();
        label37 = new java.awt.Label();
        txt_zip_code = new javax.swing.JTextField();
        label38 = new java.awt.Label();
        txt_country = new javax.swing.JTextField();
        btn_view = new java.awt.Button();
        btn_update = new java.awt.Button();
        btn_save = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPaneMyActivities = new javax.swing.JTabbedPane();
        jTabbedPaneTimeTable = new javax.swing.JTabbedPane();
        jPanelMyTimeTable = new javax.swing.JPanel();
        lbl_my_time_table = new javax.swing.JLabel();
        jPanelFacultyTable = new javax.swing.JPanel();
        lbl_faculty_time_table = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        choice_faculty = new java.awt.Choice();
        btn_submit_faculty_time_table = new javax.swing.JButton();
        jPanelClassTimeTable = new javax.swing.JPanel();
        lbl_class_time_table = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        choice_class = new java.awt.Choice();
        btn_submit_class_timt_table = new javax.swing.JButton();
        jPanelAttendance = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_attendance = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        choice_subject = new java.awt.Choice();
        btn_submit_choose_subject = new javax.swing.JButton();
        jTabbedPaneHostel = new javax.swing.JTabbedPane();
        jPanelHostelRequestHistory = new javax.swing.JPanel();
        tbl_hostel_request_history = new javax.swing.JScrollPane();
        tbl_hostel_request = new javax.swing.JTable();
        btn_get_hostel = new javax.swing.JButton();
        jPanelGuestHistory = new javax.swing.JPanel();
        tbl_hostel_request_history1 = new javax.swing.JScrollPane();
        tbl_guest_history = new javax.swing.JTable();
        btn_book_guest_room = new javax.swing.JButton();
        lbl_guest_history = new javax.swing.JLabel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanelCurrentRegisteredCourses = new javax.swing.JPanel();
        lst_current_registered_courses = new java.awt.List();
        jLabel2 = new javax.swing.JLabel();
        jPanelExaminationAdmitCard = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbl_examination_admit_card_img = new javax.swing.JLabel();
        lbl_admit_card_name = new javax.swing.JLabel();
        lst_examination_admit_card_courses = new java.awt.List();
        jLabel11 = new javax.swing.JLabel();
        lbl_admit_card_semester = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_admit_card_dob = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_admit_card_roll_no = new javax.swing.JLabel();
        jPanelExamResult = new javax.swing.JPanel();
        label43 = new java.awt.Label();
        jScrollPane90 = new javax.swing.JScrollPane();
        tbl_result = new javax.swing.JTable();
        choice_exam_result = new java.awt.Choice();
        btn_submit_exam_result = new javax.swing.JButton();
        jPanelFeedback = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        choice_feedback_faculty = new java.awt.Choice();
        choice_feedback_rating = new java.awt.Choice();
        btn_submit_feedback = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPaneRegistration = new javax.swing.JTabbedPane();
        jPanelRegularRegistration = new javax.swing.JPanel();
        lbl_regular_registration = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        list_reguar_registration_available_courses = new java.awt.List();
        jLabel21 = new javax.swing.JLabel();
        list_regular_registration_selected_courses = new java.awt.List();
        choice_regular_registration_course = new java.awt.Choice();
        jLabel22 = new javax.swing.JLabel();
        btn_regular_registration_add = new javax.swing.JButton();
        btn_regular_registration_submit = new javax.swing.JButton();
        jPanelRegularRegistration1 = new javax.swing.JPanel();
        lbl_regular_registration2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        list_improvement_registration_available_courses = new java.awt.List();
        jLabel23 = new javax.swing.JLabel();
        list_improvement_registration_selected_courses = new java.awt.List();
        choice_improvement_registration_course = new java.awt.Choice();
        jLabel24 = new javax.swing.JLabel();
        btn_improvement_registration_submit = new javax.swing.JButton();
        btn_add_improvement = new javax.swing.JButton();
        jPanelSettings = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txt_confirm_password = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_new_password = new javax.swing.JTextField();
        btn_submit = new javax.swing.JButton();
        btn_logout = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 1200, 700));
        setMaximumSize(new java.awt.Dimension(1300, 720));
        setPreferredSize(new java.awt.Dimension(1370, 740));
        setResizable(false);
        setSize(new java.awt.Dimension(1370, 720));

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0), 3));
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1400, 800));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1300, 720));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel1.setMaximumSize(new java.awt.Dimension(1400, 800));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));

        lbl_image.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel6.setLayout(new java.awt.GridLayout(11, 2, 10, 0));

        label2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label2.setMaximumSize(new java.awt.Dimension(56, 20));
        label2.setText("Student ID");
        jPanel6.add(label2);

        lbl_student_id.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_student_id.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_student_id);

        label4.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label4.setText("Student Name");
        jPanel6.add(label4);

        lbl_student_name.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_student_name.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_student_name);

        label6.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label6.setText("DOB");
        jPanel6.add(label6);

        lbl_dob.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_dob.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_dob);

        label8.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label8.setText("Gender");
        jPanel6.add(label8);

        lbl_gender.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_gender.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_gender);

        label10.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label10.setText("Cateogary");
        jPanel6.add(label10);

        lbl_cateogary.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_cateogary.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_cateogary);

        label12.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label12.setText("Admission");
        jPanel6.add(label12);

        lbl_admission.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_admission.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_admission);

        label14.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label14.setText("Branch Name");
        jPanel6.add(label14);

        lbl_branch_name.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_branch_name.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_branch_name);

        label16.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label16.setText("Degree");
        jPanel6.add(label16);

        lbl_degree.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_degree.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_degree);

        label18.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label18.setText("FT/PT");
        jPanel6.add(label18);

        lbl_ft_pt.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_ft_pt.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_ft_pt);

        label20.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label20.setText("Specialization");
        jPanel6.add(label20);

        lbl_specialization.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_specialization.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_specialization);

        label22.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label22.setText("Section");
        jPanel6.add(label22);

        lbl_section.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbl_section.setMinimumSize(new java.awt.Dimension(50, 15));
        jPanel6.add(lbl_section);

        jPanel_view.setMaximumSize(new java.awt.Dimension(788, 400));
        jPanel_view.setLayout(new java.awt.GridLayout(31, 2));

        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setPreferredSize(new java.awt.Dimension(100, 20));
        label1.setText("JEE Roll No");
        jPanel_view.add(label1);

        txt_jee_roll_no.setEditable(false);
        jPanel_view.add(txt_jee_roll_no);

        label3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label3.setPreferredSize(new java.awt.Dimension(100, 20));
        label3.setText("Student ID");
        jPanel_view.add(label3);

        txt_student_id.setEditable(false);
        jPanel_view.add(txt_student_id);

        label5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label5.setPreferredSize(new java.awt.Dimension(100, 20));
        label5.setText("Name");
        jPanel_view.add(label5);

        txt_name.setEditable(false);
        jPanel_view.add(txt_name);

        label7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label7.setPreferredSize(new java.awt.Dimension(100, 20));
        label7.setText("DOB");
        jPanel_view.add(label7);

        txt_dob.setEditable(false);
        jPanel_view.add(txt_dob);

        label9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label9.setPreferredSize(new java.awt.Dimension(100, 20));
        label9.setText("Gender");
        jPanel_view.add(label9);

        txt_gender.setEditable(false);
        jPanel_view.add(txt_gender);

        label11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label11.setPreferredSize(new java.awt.Dimension(100, 20));
        label11.setText("Cateogory");
        jPanel_view.add(label11);

        txt_cateogory.setEditable(false);
        jPanel_view.add(txt_cateogory);

        label13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label13.setPreferredSize(new java.awt.Dimension(100, 20));
        label13.setText("Sub Cateogory");
        jPanel_view.add(label13);

        txt_sub_cateogory.setEditable(false);
        jPanel_view.add(txt_sub_cateogory);

        label39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label39.setPreferredSize(new java.awt.Dimension(100, 20));
        label39.setText("Degree");
        jPanel_view.add(label39);

        txt_degree.setEditable(false);
        jPanel_view.add(txt_degree);

        label42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label42.setPreferredSize(new java.awt.Dimension(100, 20));
        label42.setText("semester");
        jPanel_view.add(label42);

        txt_semester.setEditable(false);
        jPanel_view.add(txt_semester);

        label40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label40.setPreferredSize(new java.awt.Dimension(100, 20));
        label40.setText("Branch");
        jPanel_view.add(label40);

        txt_branch.setEditable(false);
        jPanel_view.add(txt_branch);

        label41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label41.setPreferredSize(new java.awt.Dimension(100, 20));
        label41.setText("section");
        jPanel_view.add(label41);

        txt_section.setEditable(false);
        jPanel_view.add(txt_section);

        label15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label15.setPreferredSize(new java.awt.Dimension(100, 20));
        label15.setText("Minority");
        jPanel_view.add(label15);

        txt_minority.setEditable(false);
        jPanel_view.add(txt_minority);

        label17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label17.setPreferredSize(new java.awt.Dimension(100, 20));
        label17.setText("E-mail");
        jPanel_view.add(label17);

        txt_email.setEditable(false);
        jPanel_view.add(txt_email);

        label19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label19.setPreferredSize(new java.awt.Dimension(100, 20));
        label19.setText("Mobile");
        jPanel_view.add(label19);

        txt_mobile.setEditable(false);
        jPanel_view.add(txt_mobile);

        label21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label21.setPreferredSize(new java.awt.Dimension(100, 20));
        label21.setText("Marital Status");
        jPanel_view.add(label21);

        txt_marital_status.setEditable(false);
        jPanel_view.add(txt_marital_status);

        label23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label23.setPreferredSize(new java.awt.Dimension(100, 20));
        label23.setText("Blood Group");
        jPanel_view.add(label23);

        txt_blood_group.setEditable(false);
        jPanel_view.add(txt_blood_group);

        label24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label24.setPreferredSize(new java.awt.Dimension(100, 20));
        label24.setText("Father's Name");
        jPanel_view.add(label24);

        txt_father_name.setEditable(false);
        jPanel_view.add(txt_father_name);

        label25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label25.setPreferredSize(new java.awt.Dimension(100, 20));
        label25.setText("Father's Mobile");
        jPanel_view.add(label25);

        txt_father_mobile.setEditable(false);
        jPanel_view.add(txt_father_mobile);

        label26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label26.setPreferredSize(new java.awt.Dimension(100, 20));
        label26.setText("Father's E-mail");
        jPanel_view.add(label26);

        txt_father_email.setEditable(false);
        jPanel_view.add(txt_father_email);

        label27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label27.setPreferredSize(new java.awt.Dimension(100, 20));
        label27.setText("Mother's Name");
        jPanel_view.add(label27);

        txt_mother_name.setEditable(false);
        jPanel_view.add(txt_mother_name);

        label28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label28.setPreferredSize(new java.awt.Dimension(100, 20));
        label28.setText("Parent's annual income");
        jPanel_view.add(label28);

        txt_parent_annual_income.setEditable(false);
        jPanel_view.add(txt_parent_annual_income);

        label29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label29.setText("Bank A/C number");
        jPanel_view.add(label29);

        txt_bank_a_c_number.setEditable(false);
        jPanel_view.add(txt_bank_a_c_number);

        label30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label30.setPreferredSize(new java.awt.Dimension(100, 20));
        label30.setText("Bank Name");
        jPanel_view.add(label30);

        txt_bank_name.setEditable(false);
        jPanel_view.add(txt_bank_name);

        label31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label31.setPreferredSize(new java.awt.Dimension(100, 20));
        label31.setText("IFSC code");
        jPanel_view.add(label31);

        txt_ifsc_code.setEditable(false);
        jPanel_view.add(txt_ifsc_code);

        label32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label32.setPreferredSize(new java.awt.Dimension(100, 20));
        label32.setText("Aadhar number");
        jPanel_view.add(label32);

        txt_aadhar_number.setEditable(false);
        jPanel_view.add(txt_aadhar_number);

        label33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label33.setPreferredSize(new java.awt.Dimension(100, 20));
        label33.setText("House number");
        jPanel_view.add(label33);

        txt_house_number.setEditable(false);
        txt_house_number.setToolTipText("");
        jPanel_view.add(txt_house_number);

        label34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label34.setPreferredSize(new java.awt.Dimension(100, 20));
        label34.setText("Street Name");
        jPanel_view.add(label34);

        txt_street_name.setEditable(false);
        txt_street_name.setToolTipText("");
        jPanel_view.add(txt_street_name);

        label35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label35.setPreferredSize(new java.awt.Dimension(100, 20));
        label35.setText("City");
        jPanel_view.add(label35);

        txt_city.setEditable(false);
        txt_city.setToolTipText("");
        jPanel_view.add(txt_city);

        label36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label36.setPreferredSize(new java.awt.Dimension(100, 20));
        label36.setText("State");
        jPanel_view.add(label36);

        txt_state.setEditable(false);
        txt_state.setToolTipText("");
        jPanel_view.add(txt_state);

        label37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label37.setPreferredSize(new java.awt.Dimension(100, 20));
        label37.setText("Zip code");
        jPanel_view.add(label37);

        txt_zip_code.setEditable(false);
        txt_zip_code.setToolTipText("");
        jPanel_view.add(txt_zip_code);

        label38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label38.setPreferredSize(new java.awt.Dimension(100, 20));
        label38.setText("country");
        jPanel_view.add(label38);

        txt_country.setEditable(false);
        txt_country.setToolTipText("");
        jPanel_view.add(txt_country);

        jPanel_view.setVisible(false);

        jScrollPane_view.setViewportView(jPanel_view);

        btn_view.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        btn_view.setLabel("View");
        btn_view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        btn_update.setLabel("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_save.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_save.setText("Save Changes!");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(38, 38, 38)
                        .add(lbl_image, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(106, 106, 106)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(btn_update, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(btn_view, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 249, Short.MAX_VALUE)
                        .add(btn_save, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(61, 61, 61))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 280, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .add(jScrollPane_view, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 439, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(btn_view, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(btn_update, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(btn_save, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(lbl_image, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 319, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
            .add(jScrollPane_view, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Profile", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel2.setMaximumSize(new java.awt.Dimension(1360, 700));
        jPanel2.setPreferredSize(new java.awt.Dimension(1360, 700));

        jTabbedPaneMyActivities.setPreferredSize(new java.awt.Dimension(1360, 700));

        jTabbedPaneTimeTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jTabbedPaneTimeTable.setPreferredSize(new java.awt.Dimension(1200, 579));
        jTabbedPaneTimeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneTimeTableMouseClicked(evt);
            }
        });

        lbl_my_time_table.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        lbl_my_time_table.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_my_time_table.setText("TIME TABLE OF ");

        org.jdesktop.layout.GroupLayout jPanelMyTimeTableLayout = new org.jdesktop.layout.GroupLayout(jPanelMyTimeTable);
        jPanelMyTimeTable.setLayout(jPanelMyTimeTableLayout);
        jPanelMyTimeTableLayout.setHorizontalGroup(
            jPanelMyTimeTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(lbl_my_time_table, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelMyTimeTableLayout.setVerticalGroup(
            jPanelMyTimeTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(lbl_my_time_table, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneTimeTable.addTab("My Time Table", jPanelMyTimeTable);

        lbl_faculty_time_table.setBackground(new java.awt.Color(204, 204, 204));
        lbl_faculty_time_table.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        lbl_faculty_time_table.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_faculty_time_table.setText("TIME TABLE OF");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Choose Faculty");

        btn_submit_faculty_time_table.setText("submit");
        btn_submit_faculty_time_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit_faculty_time_tableActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelFacultyTableLayout = new org.jdesktop.layout.GroupLayout(jPanelFacultyTable);
        jPanelFacultyTable.setLayout(jPanelFacultyTableLayout);
        jPanelFacultyTableLayout.setHorizontalGroup(
            jPanelFacultyTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFacultyTableLayout.createSequentialGroup()
                .add(jPanelFacultyTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelFacultyTableLayout.createSequentialGroup()
                        .add(jPanelFacultyTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelFacultyTableLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(jLabel6))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFacultyTableLayout.createSequentialGroup()
                                .add(24, 24, 24)
                                .add(choice_faculty, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 61, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFacultyTableLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(btn_submit_faculty_time_table)
                        .add(43, 43, 43)))
                .add(lbl_faculty_time_table, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 973, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanelFacultyTableLayout.setVerticalGroup(
            jPanelFacultyTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFacultyTableLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(lbl_faculty_time_table, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 484, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanelFacultyTableLayout.createSequentialGroup()
                .add(51, 51, 51)
                .add(jLabel6)
                .add(22, 22, 22)
                .add(choice_faculty, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(59, 59, 59)
                .add(btn_submit_faculty_time_table)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneTimeTable.addTab("Faculty Time Table", jPanelFacultyTable);

        lbl_class_time_table.setBackground(new java.awt.Color(204, 204, 204));
        lbl_class_time_table.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        lbl_class_time_table.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_class_time_table.setText("TIME TABLE OF");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Choose Class");

        btn_submit_class_timt_table.setText("submit");
        btn_submit_class_timt_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit_class_timt_tableActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelClassTimeTableLayout = new org.jdesktop.layout.GroupLayout(jPanelClassTimeTable);
        jPanelClassTimeTable.setLayout(jPanelClassTimeTableLayout);
        jPanelClassTimeTableLayout.setHorizontalGroup(
            jPanelClassTimeTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelClassTimeTableLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelClassTimeTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(choice_class, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btn_submit_class_timt_table))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 61, Short.MAX_VALUE)
                .add(lbl_class_time_table, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 973, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanelClassTimeTableLayout.setVerticalGroup(
            jPanelClassTimeTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelClassTimeTableLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(lbl_class_time_table, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 484, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanelClassTimeTableLayout.createSequentialGroup()
                .add(51, 51, 51)
                .add(jLabel8)
                .add(22, 22, 22)
                .add(choice_class, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(61, 61, 61)
                .add(btn_submit_class_timt_table)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneTimeTable.addTab("Class Time Table", jPanelClassTimeTable);

        jTabbedPaneTimeTable.setSelectedIndex(-1);

        jTabbedPaneMyActivities.addTab("Time Table", jTabbedPaneTimeTable);

        tbl_attendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_attendance);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel14.setText("Choose subject");

        btn_submit_choose_subject.setText("Submit");
        btn_submit_choose_subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit_choose_subjectActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelAttendanceLayout = new org.jdesktop.layout.GroupLayout(jPanelAttendance);
        jPanelAttendance.setLayout(jPanelAttendanceLayout);
        jPanelAttendanceLayout.setHorizontalGroup(
            jPanelAttendanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelAttendanceLayout.createSequentialGroup()
                .add(30, 30, 30)
                .add(jPanelAttendanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabel14, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(choice_subject, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(btn_submit_choose_subject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 28, Short.MAX_VALUE)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 844, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanelAttendanceLayout.setVerticalGroup(
            jPanelAttendanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
            .add(jPanelAttendanceLayout.createSequentialGroup()
                .add(41, 41, 41)
                .add(jLabel14)
                .add(jPanelAttendanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelAttendanceLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(choice_subject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelAttendanceLayout.createSequentialGroup()
                        .add(1, 1, 1)
                        .add(btn_submit_choose_subject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneMyActivities.addTab("Attendance", jPanelAttendance);

        jTabbedPaneHostel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jTabbedPaneHostel.setMaximumSize(new java.awt.Dimension(327, 32));
        jTabbedPaneHostel.setPreferredSize(new java.awt.Dimension(900, 754));
        jTabbedPaneHostel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPaneHostelFocusGained(evt);
            }
        });

        jPanelHostelRequestHistory.setMaximumSize(new java.awt.Dimension(1180, 720));
        jPanelHostelRequestHistory.setPreferredSize(new java.awt.Dimension(1180, 720));
        jPanelHostelRequestHistory.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelHostelRequestHistoryMouseMoved(evt);
            }
        });

        tbl_hostel_request.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_hostel_request.setMaximumSize(new java.awt.Dimension(300, 64));
        tbl_hostel_request_history.setViewportView(tbl_hostel_request);

        btn_get_hostel.setText("Get Hostel");
        btn_get_hostel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_get_hostelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelHostelRequestHistoryLayout = new org.jdesktop.layout.GroupLayout(jPanelHostelRequestHistory);
        jPanelHostelRequestHistory.setLayout(jPanelHostelRequestHistoryLayout);
        jPanelHostelRequestHistoryLayout.setHorizontalGroup(
            jPanelHostelRequestHistoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelHostelRequestHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .add(btn_get_hostel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 39, Short.MAX_VALUE)
                .add(tbl_hostel_request_history, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 914, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanelHostelRequestHistoryLayout.setVerticalGroup(
            jPanelHostelRequestHistoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tbl_hostel_request_history, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
            .add(jPanelHostelRequestHistoryLayout.createSequentialGroup()
                .add(74, 74, 74)
                .add(btn_get_hostel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneHostel.addTab("Hostel Request History", jPanelHostelRequestHistory);

        jPanelGuestHistory.setMaximumSize(new java.awt.Dimension(1180, 720));
        jPanelGuestHistory.setPreferredSize(new java.awt.Dimension(1180, 720));
        jPanelGuestHistory.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelGuestHistoryMouseMoved(evt);
            }
        });

        tbl_guest_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_guest_history.setMaximumSize(new java.awt.Dimension(300, 64));
        tbl_hostel_request_history1.setViewportView(tbl_guest_history);

        btn_book_guest_room.setText("Book Guest Room");
        btn_book_guest_room.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_book_guest_roomActionPerformed(evt);
            }
        });

        lbl_guest_history.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_guest_history.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        org.jdesktop.layout.GroupLayout jPanelGuestHistoryLayout = new org.jdesktop.layout.GroupLayout(jPanelGuestHistory);
        jPanelGuestHistory.setLayout(jPanelGuestHistoryLayout);
        jPanelGuestHistoryLayout.setHorizontalGroup(
            jPanelGuestHistoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelGuestHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .add(btn_book_guest_room, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 39, Short.MAX_VALUE)
                .add(tbl_hostel_request_history1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 914, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanelGuestHistoryLayout.createSequentialGroup()
                .add(267, 267, 267)
                .add(lbl_guest_history, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 841, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanelGuestHistoryLayout.setVerticalGroup(
            jPanelGuestHistoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelGuestHistoryLayout.createSequentialGroup()
                .add(74, 74, 74)
                .add(btn_book_guest_room, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelGuestHistoryLayout.createSequentialGroup()
                .add(0, 4, Short.MAX_VALUE)
                .add(lbl_guest_history, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(tbl_hostel_request_history1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 399, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPaneHostel.addTab("Guest History", jPanelGuestHistory);

        jTabbedPaneHostel.setSelectedIndex(-1);

        jTabbedPaneMyActivities.addTab("Hostel", jTabbedPaneHostel);

        jTabbedPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        lst_current_registered_courses.setFont(new java.awt.Font("Felix Titling", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Current registered courses are:");

        org.jdesktop.layout.GroupLayout jPanelCurrentRegisteredCoursesLayout = new org.jdesktop.layout.GroupLayout(jPanelCurrentRegisteredCourses);
        jPanelCurrentRegisteredCourses.setLayout(jPanelCurrentRegisteredCoursesLayout);
        jPanelCurrentRegisteredCoursesLayout.setHorizontalGroup(
            jPanelCurrentRegisteredCoursesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelCurrentRegisteredCoursesLayout.createSequentialGroup()
                .add(149, 149, 149)
                .add(jPanelCurrentRegisteredCoursesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(lst_current_registered_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 386, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(629, Short.MAX_VALUE))
        );
        jPanelCurrentRegisteredCoursesLayout.setVerticalGroup(
            jPanelCurrentRegisteredCoursesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelCurrentRegisteredCoursesLayout.createSequentialGroup()
                .add(19, 19, 19)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lst_current_registered_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 409, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane5.addTab("Current Registered Courses", jPanelCurrentRegisteredCourses);

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Name");

        lbl_examination_admit_card_img.setBackground(new java.awt.Color(0, 51, 51));
        lbl_examination_admit_card_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_admit_card_name.setText("Saransh Kumar");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("DOB");

        lbl_admit_card_semester.setText("6");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Roll no.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Semester");

        lbl_admit_card_dob.setText("28-03-1998");

        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel13.setText("Eligible to take exam of following subjects-");

        lbl_admit_card_roll_no.setText("346/co/14");

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(49, 49, 49)
                        .add(jLabel13))
                    .add(lst_examination_admit_card_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 400, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(lbl_examination_admit_card_img, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(81, 81, 81)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(jLabel9)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel7)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel5))
                            .add(jLabel11))
                        .add(61, 61, 61)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lbl_admit_card_dob, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(lbl_admit_card_name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, lbl_admit_card_semester, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, lbl_admit_card_roll_no, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lbl_admit_card_name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lbl_admit_card_roll_no, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lbl_admit_card_semester, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lbl_admit_card_dob, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(lbl_examination_admit_card_img, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel13)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lst_examination_admit_card_courses, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanelExaminationAdmitCardLayout = new org.jdesktop.layout.GroupLayout(jPanelExaminationAdmitCard);
        jPanelExaminationAdmitCard.setLayout(jPanelExaminationAdmitCardLayout);
        jPanelExaminationAdmitCardLayout.setHorizontalGroup(
            jPanelExaminationAdmitCardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelExaminationAdmitCardLayout.createSequentialGroup()
                .add(237, 237, 237)
                .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(503, Short.MAX_VALUE))
        );
        jPanelExaminationAdmitCardLayout.setVerticalGroup(
            jPanelExaminationAdmitCardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelExaminationAdmitCardLayout.createSequentialGroup()
                .add(80, 80, 80)
                .add(jPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane5.addTab("Examination Admit Card", jPanelExaminationAdmitCard);

        label43.setAlignment(java.awt.Label.CENTER);
        label43.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label43.setText("Result");

        tbl_result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane90.setViewportView(tbl_result);

        btn_submit_exam_result.setText("submit");
        btn_submit_exam_result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit_exam_resultActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelExamResultLayout = new org.jdesktop.layout.GroupLayout(jPanelExamResult);
        jPanelExamResult.setLayout(jPanelExamResultLayout);
        jPanelExamResultLayout.setHorizontalGroup(
            jPanelExamResultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelExamResultLayout.createSequentialGroup()
                .add(186, 186, 186)
                .add(label43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 440, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(538, Short.MAX_VALUE))
            .add(jPanelExamResultLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelExamResultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(btn_submit_exam_result)
                    .add(choice_exam_result, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jScrollPane90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 977, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelExamResultLayout.setVerticalGroup(
            jPanelExamResultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelExamResultLayout.createSequentialGroup()
                .addContainerGap()
                .add(label43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jPanelExamResultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelExamResultLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelExamResultLayout.createSequentialGroup()
                        .add(48, 48, 48)
                        .add(choice_exam_result, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(94, 94, 94)
                        .add(btn_submit_exam_result)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Exam Result", jPanelExamResult);

        jTabbedPane5.setSelectedIndex(-1);

        jTabbedPaneMyActivities.addTab("Examination", jTabbedPane5);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Choose Faculty");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Select Rating");

        btn_submit_feedback.setText("Submit");
        btn_submit_feedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit_feedbackActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelFeedbackLayout = new org.jdesktop.layout.GroupLayout(jPanelFeedback);
        jPanelFeedback.setLayout(jPanelFeedbackLayout);
        jPanelFeedbackLayout.setHorizontalGroup(
            jPanelFeedbackLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelFeedbackLayout.createSequentialGroup()
                .add(47, 47, 47)
                .add(jPanelFeedbackLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel10)
                    .add(choice_feedback_faculty, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 288, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(204, 204, 204)
                .add(jPanelFeedbackLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabel12, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(choice_feedback_rating, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(27, 27, 27)
                .add(btn_submit_feedback)
                .addContainerGap(462, Short.MAX_VALUE))
        );
        jPanelFeedbackLayout.setVerticalGroup(
            jPanelFeedbackLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelFeedbackLayout.createSequentialGroup()
                .add(39, 39, 39)
                .add(jPanelFeedbackLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10)
                    .add(jLabel12))
                .add(42, 42, 42)
                .add(jPanelFeedbackLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(choice_feedback_faculty, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(choice_feedback_rating, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btn_submit_feedback))
                .add(0, 359, Short.MAX_VALUE))
        );

        jTabbedPaneMyActivities.addTab("Feedback", jPanelFeedback);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTabbedPaneMyActivities, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jTabbedPaneMyActivities, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 508, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("MyActivities", jPanel2);

        lbl_regular_registration.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_regular_registration.setText("Registeration started!");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel15.setText("Available Courses");

        list_reguar_registration_available_courses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                list_reguar_registration_available_coursesActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel21.setText("Selected Courses");

        jLabel22.setText("choose subject to register");

        btn_regular_registration_add.setText("Add");
        btn_regular_registration_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regular_registration_addActionPerformed(evt);
            }
        });

        btn_regular_registration_submit.setText("Submit");
        btn_regular_registration_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regular_registration_submitActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelRegularRegistrationLayout = new org.jdesktop.layout.GroupLayout(jPanelRegularRegistration);
        jPanelRegularRegistration.setLayout(jPanelRegularRegistrationLayout);
        jPanelRegularRegistrationLayout.setHorizontalGroup(
            jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                .add(jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(list_reguar_registration_available_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                                .add(166, 166, 166)
                                .add(choice_regular_registration_course, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                                .add(237, 237, 237)
                                .add(jLabel22))))
                    .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                        .add(74, 74, 74)
                        .add(jLabel15)))
                .add(jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelRegularRegistrationLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabel21)
                        .add(82, 82, 82))
                    .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                        .add(37, 37, 37)
                        .add(jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(btn_regular_registration_add)
                            .add(btn_regular_registration_submit))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 134, Short.MAX_VALUE)
                        .add(list_regular_registration_selected_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))))
            .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                .add(480, 480, 480)
                .add(lbl_regular_registration, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelRegularRegistrationLayout.setVerticalGroup(
            jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_regular_registration, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(21, 21, 21)
                .add(jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                        .add(jLabel15)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(list_reguar_registration_available_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 372, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                                .add(jPanelRegularRegistrationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(btn_regular_registration_add)
                                    .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                                        .add(jLabel22)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(choice_regular_registration_course, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .add(181, 181, 181)
                                .add(btn_regular_registration_submit))))
                    .add(jPanelRegularRegistrationLayout.createSequentialGroup()
                        .add(jLabel21)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(list_regular_registration_selected_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 372, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(194, Short.MAX_VALUE))
        );

        jTabbedPaneRegistration.addTab("Regular Registration", jPanelRegularRegistration);

        lbl_regular_registration2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_regular_registration2.setText("Registeration started!");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel16.setText("Available Courses");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel23.setText("Selected Courses");

        jLabel24.setText("choose subject to register");

        btn_improvement_registration_submit.setText("Submit");
        btn_improvement_registration_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_improvement_registration_submitActionPerformed(evt);
            }
        });

        btn_add_improvement.setText("add");
        btn_add_improvement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_improvementActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelRegularRegistration1Layout = new org.jdesktop.layout.GroupLayout(jPanelRegularRegistration1);
        jPanelRegularRegistration1.setLayout(jPanelRegularRegistration1Layout);
        jPanelRegularRegistration1Layout.setHorizontalGroup(
            jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                .add(jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(list_improvement_registration_available_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                                .add(166, 166, 166)
                                .add(choice_improvement_registration_course, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                                .add(237, 237, 237)
                                .add(jLabel24))))
                    .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                        .add(74, 74, 74)
                        .add(jLabel16)))
                .add(jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 55, Short.MAX_VALUE)
                        .add(jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelRegularRegistration1Layout.createSequentialGroup()
                                .add(btn_improvement_registration_submit)
                                .add(122, 122, 122))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelRegularRegistration1Layout.createSequentialGroup()
                                .add(btn_add_improvement)
                                .add(78, 78, 78)))
                        .add(list_improvement_registration_selected_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelRegularRegistration1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabel23)
                        .add(82, 82, 82))))
            .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                .add(480, 480, 480)
                .add(lbl_regular_registration2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelRegularRegistration1Layout.setVerticalGroup(
            jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_regular_registration2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(21, 21, 21)
                .add(jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                        .add(jLabel16)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(list_improvement_registration_available_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 372, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                                .add(jLabel24)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanelRegularRegistration1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(choice_improvement_registration_course, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(btn_add_improvement))
                                .add(186, 186, 186)
                                .add(btn_improvement_registration_submit))))
                    .add(jPanelRegularRegistration1Layout.createSequentialGroup()
                        .add(jLabel23)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(list_improvement_registration_selected_courses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 372, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(194, Short.MAX_VALUE))
        );

        jTabbedPaneRegistration.addTab("Improvement Registration", jPanelRegularRegistration1);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPaneRegistration)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jTabbedPaneRegistration, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 686, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registration", jPanel3);

        jPanel7.setBorder(new javax.swing.border.MatteBorder(null));

        txt_confirm_password.setAlignmentX(0.0F);
        txt_confirm_password.setAlignmentY(1.0F);
        txt_confirm_password.setMaximumSize(new java.awt.Dimension(34, 14));
        txt_confirm_password.setMinimumSize(new java.awt.Dimension(34, 14));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("confirm password");
        jLabel4.setAlignmentY(1.0F);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Username");
        jLabel1.setAlignmentY(1.0F);

        lbl_username.setText("user");
        lbl_username.setAlignmentY(1.0F);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("new password");
        jLabel3.setAlignmentY(1.0F);

        txt_new_password.setAlignmentX(0.0F);
        txt_new_password.setAlignmentY(1.0F);
        txt_new_password.setMaximumSize(new java.awt.Dimension(34, 14));
        txt_new_password.setMinimumSize(new java.awt.Dimension(34, 14));

        btn_submit.setText("Submit");

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(18, 18, 18)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel4)
                    .add(jLabel3))
                .add(114, 114, 114)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btn_submit)
                    .add(txt_new_password, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txt_confirm_password, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lbl_username, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(30, 30, 30)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lbl_username)
                    .add(jLabel1))
                .add(29, 29, 29)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txt_new_password, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel3))
                .add(26, 26, 26)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txt_confirm_password, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4))
                .add(46, 46, 46)
                .add(btn_submit)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanelSettingsLayout = new org.jdesktop.layout.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelSettingsLayout.createSequentialGroup()
                .add(301, 301, 301)
                .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(548, Short.MAX_VALUE))
        );
        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelSettingsLayout.createSequentialGroup()
                .add(49, 49, 49)
                .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Setings", jPanelSettings);

        jTabbedPane1.setSelectedIndex(-1);

        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btn_logout.setLabel("Logout");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btn_logout, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(btn_logout, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 542, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(292, 292, 292))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewActionPerformed
        // TODO add your handling code here:
        try{
             String sql="select * from student where concat (roll_no_part_1, '/', roll_no_part_2, '/', roll_no_part_3) =?";
         pst=conn.prepareStatement(sql);
         pst.setString(1,sid);
         rs=pst.executeQuery();
          String sql2="select department.dname from studentofdepartment, depaRTMENT WHERE studentofdepartment.did=department.did and studentofdepartment.sid=? ";
             PreparedStatement pst2;
             pst2=conn.prepareStatement(sql2);
             ResultSet rst2;
              pst2.setString(1,sid);
              rst2=pst2.executeQuery();
              String branchName = null;
              while(rst2.next())
              {
                 branchName=rst2.getString("dname");
              }
         while(rs.next())
         {
             String studentId,studentName,firstName,middleName,lastName,dob,gender,cateogory,admission,degree,ftPt,specialization,section;
             String jeeRollNo,subCateogory,semester,minority,email,mobile,maritalStatus,bloodGroup,fathersName,fathersMobile,fathersEmail,mothersName,parentsAnnualIncome,bankAccountNo,bankName,ifscCode,aadharNo,houseNo,streetName,city,state,zipCode,country;
             studentId=sid;
             firstName=rs.getString("FirstName");
             middleName=rs.getString("MiddleName");
             lastName=rs.getString("LastName");
             studentName=firstName+" "+middleName+" "+lastName;
             dob=rs.getString("DOB");
            // gender=rs.getString("gender");
             cateogory=rs.getString("category");
            // branchName=rs.getString("branch");
             degree=rs.getString("degree");
             section=rs.getString("section");
             jeeRollNo=rs.getString("jeerollno");
             semester=rs.getString("semester");
             minority=rs.getString("minority");
             email=rs.getString("emailid");
             mobile=rs.getString("mobile");
             maritalStatus=rs.getString("maritalstatus");
             bloodGroup=rs.getString("bloodgroup");
             String ff,fm,fl;
             ff=rs.getString("fatherfirstname");
             fm=rs.getString("fathermiddlename");
             fl=rs.getString("fatherlastname");
             fathersName=ff+" "+fm+" "+fl;
             fathersEmail=rs.getString("fatheremailid");
             fathersMobile=rs.getString("fathermobileno");
             mothersName=rs.getString("motherfirstname");
             parentsAnnualIncome=rs.getString("parentannualincome");
             bankAccountNo=rs.getString("bankaccountno");
             bankName=rs.getString("bankname");
             //ifscCode=rs.getString("");
             aadharNo=rs.getString("adhaarno");
             houseNo=rs.getString("houseno");
             streetName=rs.getString("streetname");
             city=rs.getString("city");
             state=rs.getString("state");
             zipCode=rs.getString("zip");
             country=rs.getString("country");
             
             txt_jee_roll_no.setEditable(false);
             txt_student_id.setEditable(false);
             txt_name.setEditable(false);
             txt_dob.setEditable(false);
             txt_gender.setEditable(false);
             txt_cateogory.setEditable(false);
             txt_branch.setEditable(false);
             txt_degree.setEditable(false);
             txt_section.setEditable(false);
             txt_semester.setEditable(false);
             txt_minority.setEditable(false);
             txt_email.setEditable(false);
             txt_mobile.setEditable(false);
             txt_marital_status.setEditable(false);
             txt_blood_group.setEditable(false);
             txt_father_name.setEditable(false);
             txt_father_email.setEditable(false);
             txt_mother_name.setEditable(false);
             txt_parent_annual_income.setEditable(false);
             txt_bank_a_c_number.setEditable(false);
             txt_bank_name.setEditable(false);
             txt_ifsc_code.setEditable(false);
             txt_aadhar_number.setEditable(false);
             txt_house_number.setEditable(false);
             txt_street_name.setEditable(false);
             txt_city.setEditable(false);
             txt_state.setEditable(false);
             txt_zip_code.setEditable(false);
             txt_country.setEditable(false);
             
             txt_email.setBackground(Color.WHITE);
             txt_mobile.setBackground(Color.WHITE);
             txt_marital_status.setBackground(Color.WHITE);
             txt_father_email.setBackground(Color.WHITE);
             txt_parent_annual_income.setBackground(Color.WHITE);
             txt_bank_a_c_number.setBackground(Color.WHITE);
             txt_bank_name.setBackground(Color.WHITE);
             txt_ifsc_code.setBackground(Color.WHITE);
             txt_house_number.setBackground(Color.WHITE);
             txt_street_name.setBackground(Color.WHITE);
             txt_city.setBackground(Color.WHITE);
             txt_state.setBackground(Color.WHITE);
             txt_zip_code.setBackground(Color.WHITE);
             txt_country.setBackground(Color.WHITE);
             
             txt_jee_roll_no.setText(jeeRollNo);
             txt_student_id.setText(studentId);
             txt_name.setText(studentName);
             txt_dob.setText(dob);
             txt_gender.setText("");
             txt_cateogory.setText(cateogory);
             txt_branch.setText(branchName);
             txt_degree.setText(degree);
             txt_section.setText(section);
             txt_semester.setText(semester);
             txt_minority.setText(minority);
             txt_email.setText(email);
             txt_mobile.setText(mobile);
             txt_marital_status.setText(maritalStatus);
             txt_blood_group.setText(bloodGroup);
             txt_father_name.setText(fathersName);
             txt_father_email.setText(fathersEmail);
             txt_mother_name.setText(mothersName);
             txt_parent_annual_income.setText(parentsAnnualIncome);
             txt_bank_a_c_number.setText(bankAccountNo);
             txt_bank_name.setText(bankName);
             txt_ifsc_code.setText("");
             txt_aadhar_number.setText(aadharNo);
             txt_house_number.setText(houseNo);
             txt_street_name.setText(streetName);
             txt_city.setText(city);
             txt_state.setText(state);
             txt_zip_code.setText(zipCode);
             txt_country.setText(country);
              /* show view profile */
          
             jPanel_view.setVisible(true);
                
         }
         }catch(SQLException e)
         {
            JOptionPane.showMessageDialog(null, e);
         }
       
       // jScrollPane_view.setVisible(true);
    }//GEN-LAST:event_btn_viewActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
             
             btn_save.setVisible(true);
             txt_email.setEditable(true);
             txt_mobile.setEditable(true);
             txt_marital_status.setEditable(true);
             txt_father_email.setEditable(true);
             txt_parent_annual_income.setEditable(true);
             txt_bank_a_c_number.setEditable(true);
             txt_bank_name.setEditable(true);
             txt_ifsc_code.setEditable(true);
             txt_house_number.setEditable(true);
             txt_street_name.setEditable(true);
             txt_city.setEditable(true);
             txt_state.setEditable(true);
             txt_zip_code.setEditable(true);
             txt_country.setEditable(true);
             
             
             txt_email.setBackground(Color.cyan);
             txt_mobile.setBackground(Color.cyan);
             txt_marital_status.setBackground(Color.cyan);
             txt_father_email.setBackground(Color.cyan);
             txt_parent_annual_income.setBackground(Color.cyan);
             txt_bank_a_c_number.setBackground(Color.cyan);
             txt_bank_name.setBackground(Color.cyan);
             txt_ifsc_code.setBackground(Color.cyan);
             txt_house_number.setBackground(Color.cyan);
             txt_street_name.setBackground(Color.cyan);
             txt_city.setBackground(Color.cyan);
             txt_state.setBackground(Color.cyan);
             txt_zip_code.setBackground(Color.cyan);
             txt_country.setBackground(Color.cyan);
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        String email,mobile,maritalStatus,fatherEmail,parentAnnualIncome,bankAccountNo,bankName,ifscCode,houseNo,streetName,city,state,zipCode,country;
            email= txt_email.getText();
             mobile=txt_mobile.getText();
             maritalStatus=txt_marital_status.getText();
             fatherEmail=txt_father_email.getText();
             parentAnnualIncome=txt_parent_annual_income.getText();
            bankAccountNo= txt_bank_a_c_number.getText();
             bankName=txt_bank_name.getText();
             ifscCode=txt_ifsc_code.getText();
             houseNo=txt_house_number.getText();
             streetName=txt_street_name.getText();
             city=txt_city.getText();
             state=txt_state.getText();
            zipCode= txt_zip_code.getText();
             country=txt_country.getText();
             try{
             String sql="update student set emailId=?,mobile=?,maritalstatus=?,fatheremailid=?,parentannualincome=?,bankaccountno=?,bankname=?,houseno=?,streetname=?,city=?,state=?,zip=?,country=? where concat (roll_no_part_1, '/', roll_no_part_2, '/', roll_no_part_3) =?";
              pst=conn.prepareStatement(sql);
              pst.setString(1,email);
              pst.setString(2,mobile);
              pst.setString(3,maritalStatus);
              pst.setString(4,fatherEmail);
                pst.setString(5,parentAnnualIncome);
                  pst.setString(6,bankAccountNo);
                    pst.setString(7,bankName);
                      //pst.setString(8,ifscCode);
                        pst.setString(8,houseNo);
                          pst.setString(9,streetName);
                            pst.setString(10,city);
                              pst.setString(11,state);
                                pst.setString(12,zipCode);
                                  pst.setString(13,country);
                                   pst.setString(14,sid);
                                   
              pst.execute();
              JOptionPane.showMessageDialog(null,"Changes Saved!");
             }catch(HeadlessException | SQLException e)
             {
                 JOptionPane.showMessageDialog(null, e);
             }
             
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_submit_choose_subjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit_choose_subjectActionPerformed
        // TODO add your handling code here:
         tbl_attendance.removeAll();
          DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Status"}, 0);
        String date,status,selected=choice_subject.getSelectedItem();
               String sql="Select Date, PresentorAbsent " +
                           "From attendance,course " +
                           "Where course.Cname = ? and attendance.cid = course.cid and attendance.sid = ?";
              
              
                try{   pst=conn.prepareStatement(sql);
         pst.setString(1,selected);
         pst.setString(2,sid);
         rs=pst.executeQuery();
        
        // model.addRow(new Object[]{"01-02-2015", "1"});
         
         while(rs.next())
         {
             date=rs.getString("Date");
             status=rs.getString("PresentorAbsent");
             model.addRow(new Object[]{date, status});
         }
          }catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, e);
      }
                
          tbl_attendance.setModel(model);
          tbl_attendance.setVisible(true);
         // tbl_attendance.setDefaultEditor(Object.class,null);
     
               
    }//GEN-LAST:event_btn_submit_choose_subjectActionPerformed

    private void jTabbedPaneTimeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneTimeTableMouseClicked
        // TODO add your handling code here:
        lbl_my_time_table.setText("TIME TABLE OF "+studentClass);
    }//GEN-LAST:event_jTabbedPaneTimeTableMouseClicked

    private void btn_submit_faculty_time_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit_faculty_time_tableActionPerformed
        // TODO add your handling code here:
        if(choice_faculty.getSelectedIndex()!=-1)
        lbl_faculty_time_table.setText("TIME TABLE OF "+ choice_faculty.getSelectedItem());
    }//GEN-LAST:event_btn_submit_faculty_time_tableActionPerformed

    private void btn_submit_class_timt_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit_class_timt_tableActionPerformed
        // TODO add your handling code here:
        if(choice_class.getSelectedIndex()!=-1)
            lbl_class_time_table.setText("TIME TABLE OF "+choice_class.getSelectedItem());
    }//GEN-LAST:event_btn_submit_class_timt_tableActionPerformed

    private void btn_get_hostelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_get_hostelActionPerformed
        // TODO add your handling code here:
        BookHostelForStudent.main(new String[]{sid});
        updateHostelRequest();
    }//GEN-LAST:event_btn_get_hostelActionPerformed

    private void btn_book_guest_roomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_book_guest_roomActionPerformed
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,"hello");
          //BookHostelForGuest bh= new BookHostelForGuest();
          BookHostelForGuest.main(new String[]{sid});
          updateHostelGuest();
    }//GEN-LAST:event_btn_book_guest_roomActionPerformed

    private void btn_submit_exam_resultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit_exam_resultActionPerformed
        // TODO add your handling code here:
        String sql,select=choice_exam_result.getSelectedItem();
         DefaultTableModel model = new DefaultTableModel(new String[]{"Course Name", "Marks","Max Marks","Credits Earned","Improvement Number"}, 0);
        int sem=Integer.valueOf(select);
        sql="{call getMarksForCoursesEnrolledInSemester(?,?)}"; 
        try{
           CallableStatement cst = conn.prepareCall(sql);
           cst.setString(1,sid);
           cst.setInt(2,sem);
           rs=cst.executeQuery();
           
           while(rs.next())
           {
              // course.CName, marks.marks, marks.maxmarks,creditsEarned ,marks.ImprovementCount
               String cName,marks,maxMarks,credits,impCnt;
               cName=rs.getString("CName");
               marks=rs.getString("marks");
               maxMarks=rs.getString("maxmarks");
               credits=rs.getString("creditsEarned");
               impCnt=rs.getString("ImprovementCount");
               model.addRow(new Object[]{cName,marks,maxMarks,credits,impCnt});
           }
         }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        tbl_result.setModel(model);
    }//GEN-LAST:event_btn_submit_exam_resultActionPerformed

    private void btn_submit_feedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit_feedbackActionPerformed
        // TODO add your handling code here:
        String sql="{call submitRating(?,?,?)}";
        try{
           CallableStatement cst = conn.prepareCall(sql);
           String fid,cid;
           int rating;
           int index;
           index=choice_faculty.getSelectedIndex();
           fid=feedbackFid.get(index);
           cid=feedbackCid.get(index);
           rating=Integer.valueOf(choice_feedback_rating.getSelectedItem());
           cst.setString(1,fid);
           cst.setInt(2,Integer.valueOf(cid));
           cst.setInt(3,rating);
           rs=cst.executeQuery();
           JOptionPane.showMessageDialog(null,"Done!");
           
      }catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, e);
      }
        
    }//GEN-LAST:event_btn_submit_feedbackActionPerformed

    private void btn_regular_registration_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regular_registration_addActionPerformed
        // TODO add your handling code here:
        
        String a=choice_regular_registration_course.getSelectedItem();
        int index;
        index=choice_regular_registration_course.getSelectedIndex();
        
        list_regular_registration_selected_courses.add(a);
        registrationRegularSelectedCname.add(registrationRegularCname.get(index));
        
        
    }//GEN-LAST:event_btn_regular_registration_addActionPerformed

    private void btn_improvement_registration_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_improvement_registration_submitActionPerformed
        // TODO add your handling code here:
       String sql="{call registerCoursesForImprovement(?,?)}";
        int cid;
         try{
             for(int i=0;i<registrationImprovementSelectedCid.size();i++){
                 //JOptionPane.showMessageDialog(null,registrationImprovementSelectedCid.get(i));
                 cid=registrationImprovementSelectedCid.get(i);
            CallableStatement cst = conn.prepareCall(sql);
            cst.setString(1,sid);
             cst.setInt(2,cid);
             cst.executeQuery();
            
             }
              JOptionPane.showMessageDialog(null,"Padhle is baar to!!");
              registrationImprovementSelectedCid.clear();
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_btn_improvement_registration_submitActionPerformed

    private void list_reguar_registration_available_coursesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list_reguar_registration_available_coursesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_list_reguar_registration_available_coursesActionPerformed

    private void btn_regular_registration_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regular_registration_submitActionPerformed
        // TODO add your handling code here:
        String sql="{call registerCourses(?,?)}";
        String cName;
        
            try{
                CallableStatement cst = conn.prepareCall(sql);
         cst.setString(1,sid);
             for(int i=0;i<registrationRegularSelectedCname.size();i++){
                 cName=registrationRegularSelectedCname.get(i);
                 
             cst.setString(2,cName);
             //JOptionPane.showMessageDialog(null,"dekh");
             cst.executeQuery();
           
                 }
             JOptionPane.showMessageDialog(null,"Wohoo! Study Hard");
             registrationRegularSelectedCname.clear();
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
       
    }//GEN-LAST:event_btn_regular_registration_submitActionPerformed

    private void btn_add_improvementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_improvementActionPerformed
        // TODO add your handling code here:
            String a=choice_improvement_registration_course.getSelectedItem();
                  int index;
        index=choice_improvement_registration_course.getSelectedIndex();
        
        list_improvement_registration_selected_courses.add(a);
        registrationImprovementSelectedCid.add(Integer.valueOf(registrationImprovementCid.get(index)));
    }//GEN-LAST:event_btn_add_improvementActionPerformed

    private void jTabbedPaneHostelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPaneHostelFocusGained
        updateHostelRequest();
    }//GEN-LAST:event_jTabbedPaneHostelFocusGained

    private void jPanelHostelRequestHistoryMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelHostelRequestHistoryMouseMoved
         updateHostelRequest();
          updateHostelGuest();
    }//GEN-LAST:event_jPanelHostelRequestHistoryMouseMoved

    private void jPanelGuestHistoryMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelGuestHistoryMouseMoved
         updateHostelRequest();
          updateHostelGuest();
    }//GEN-LAST:event_jPanelGuestHistoryMouseMoved

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
       dispose();
       new LoginWindow().setVisible(true);
    }//GEN-LAST:event_btn_logoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindowStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindowStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindowStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindowStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
         sid=args[0];
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindowStudent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_improvement;
    private javax.swing.JButton btn_book_guest_room;
    private javax.swing.JButton btn_get_hostel;
    private javax.swing.JButton btn_improvement_registration_submit;
    private java.awt.Button btn_logout;
    private javax.swing.JButton btn_regular_registration_add;
    private javax.swing.JButton btn_regular_registration_submit;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_submit;
    private javax.swing.JButton btn_submit_choose_subject;
    private javax.swing.JButton btn_submit_class_timt_table;
    private javax.swing.JButton btn_submit_exam_result;
    private javax.swing.JButton btn_submit_faculty_time_table;
    private javax.swing.JButton btn_submit_feedback;
    private java.awt.Button btn_update;
    private java.awt.Button btn_view;
    private java.awt.Choice choice_class;
    private java.awt.Choice choice_exam_result;
    private java.awt.Choice choice_faculty;
    private java.awt.Choice choice_feedback_faculty;
    private java.awt.Choice choice_feedback_rating;
    private java.awt.Choice choice_improvement_registration_course;
    private java.awt.Choice choice_regular_registration_course;
    private java.awt.Choice choice_subject;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelAttendance;
    private javax.swing.JPanel jPanelClassTimeTable;
    private javax.swing.JPanel jPanelCurrentRegisteredCourses;
    private javax.swing.JPanel jPanelExamResult;
    private javax.swing.JPanel jPanelExaminationAdmitCard;
    private javax.swing.JPanel jPanelFacultyTable;
    private javax.swing.JPanel jPanelFeedback;
    private javax.swing.JPanel jPanelGuestHistory;
    private javax.swing.JPanel jPanelHostelRequestHistory;
    private javax.swing.JPanel jPanelMyTimeTable;
    private javax.swing.JPanel jPanelRegularRegistration;
    private javax.swing.JPanel jPanelRegularRegistration1;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JPanel jPanel_view;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane90;
    private javax.swing.JScrollPane jScrollPane_view;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPaneHostel;
    private javax.swing.JTabbedPane jTabbedPaneMyActivities;
    private javax.swing.JTabbedPane jTabbedPaneRegistration;
    private javax.swing.JTabbedPane jTabbedPaneTimeTable;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label16;
    private java.awt.Label label17;
    private java.awt.Label label18;
    private java.awt.Label label19;
    private java.awt.Label label2;
    private java.awt.Label label20;
    private java.awt.Label label21;
    private java.awt.Label label22;
    private java.awt.Label label23;
    private java.awt.Label label24;
    private java.awt.Label label25;
    private java.awt.Label label26;
    private java.awt.Label label27;
    private java.awt.Label label28;
    private java.awt.Label label29;
    private java.awt.Label label3;
    private java.awt.Label label30;
    private java.awt.Label label31;
    private java.awt.Label label32;
    private java.awt.Label label33;
    private java.awt.Label label34;
    private java.awt.Label label35;
    private java.awt.Label label36;
    private java.awt.Label label37;
    private java.awt.Label label38;
    private java.awt.Label label39;
    private java.awt.Label label4;
    private java.awt.Label label40;
    private java.awt.Label label41;
    private java.awt.Label label42;
    private java.awt.Label label43;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private java.awt.Label lbl_admission;
    private javax.swing.JLabel lbl_admit_card_dob;
    private javax.swing.JLabel lbl_admit_card_name;
    private javax.swing.JLabel lbl_admit_card_roll_no;
    private javax.swing.JLabel lbl_admit_card_semester;
    private java.awt.Label lbl_branch_name;
    private java.awt.Label lbl_cateogary;
    private javax.swing.JLabel lbl_class_time_table;
    private java.awt.Label lbl_degree;
    private java.awt.Label lbl_dob;
    private javax.swing.JLabel lbl_examination_admit_card_img;
    private javax.swing.JLabel lbl_faculty_time_table;
    private java.awt.Label lbl_ft_pt;
    private java.awt.Label lbl_gender;
    private javax.swing.JLabel lbl_guest_history;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JLabel lbl_my_time_table;
    private javax.swing.JLabel lbl_regular_registration;
    private javax.swing.JLabel lbl_regular_registration2;
    private java.awt.Label lbl_section;
    private java.awt.Label lbl_specialization;
    private java.awt.Label lbl_student_id;
    private java.awt.Label lbl_student_name;
    private javax.swing.JLabel lbl_username;
    private java.awt.List list_improvement_registration_available_courses;
    private java.awt.List list_improvement_registration_selected_courses;
    private java.awt.List list_reguar_registration_available_courses;
    private java.awt.List list_regular_registration_selected_courses;
    private java.awt.List lst_current_registered_courses;
    private java.awt.List lst_examination_admit_card_courses;
    private javax.swing.JTable tbl_attendance;
    private javax.swing.JTable tbl_guest_history;
    private javax.swing.JTable tbl_hostel_request;
    private javax.swing.JScrollPane tbl_hostel_request_history;
    private javax.swing.JScrollPane tbl_hostel_request_history1;
    private javax.swing.JTable tbl_result;
    private javax.swing.JTextField txt_aadhar_number;
    private javax.swing.JTextField txt_bank_a_c_number;
    private javax.swing.JTextField txt_bank_name;
    private javax.swing.JTextField txt_blood_group;
    private javax.swing.JTextField txt_branch;
    private javax.swing.JTextField txt_cateogory;
    private javax.swing.JTextField txt_city;
    private javax.swing.JTextField txt_confirm_password;
    private javax.swing.JTextField txt_country;
    private javax.swing.JTextField txt_degree;
    private javax.swing.JTextField txt_dob;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_father_email;
    private javax.swing.JTextField txt_father_mobile;
    private javax.swing.JTextField txt_father_name;
    private javax.swing.JTextField txt_gender;
    private javax.swing.JTextField txt_house_number;
    private javax.swing.JTextField txt_ifsc_code;
    private javax.swing.JTextField txt_jee_roll_no;
    private javax.swing.JTextField txt_marital_status;
    private javax.swing.JTextField txt_minority;
    private javax.swing.JTextField txt_mobile;
    private javax.swing.JTextField txt_mother_name;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_new_password;
    private javax.swing.JTextField txt_parent_annual_income;
    private javax.swing.JTextField txt_section;
    private javax.swing.JTextField txt_semester;
    private javax.swing.JTextField txt_state;
    private javax.swing.JTextField txt_street_name;
    private javax.swing.JTextField txt_student_id;
    private javax.swing.JTextField txt_sub_cateogory;
    private javax.swing.JTextField txt_zip_code;
    // End of variables declaration//GEN-END:variables
}

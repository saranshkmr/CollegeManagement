/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegemanagement;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author sksha
 */
public class BookHostelForStudent extends javax.swing.JFrame {

    /**
     * Creates new form BookHostelForStudent1
     */
    public BookHostelForStudent() {
        initComponents();
         conn=JavaConnect.connectDb();
         updateRoomList();
    }
    String sql;
    static String sid;
    Connection conn=null;
ResultSet rs=null;
PreparedStatement pst=null;
 ArrayList<Integer> list=new ArrayList();

    private void updateRoomList()
    {
   sql="{call getAvailableRooms(?,'student')}"; 
      /*
      CREATE PROCEDURE getAvailableRooms(sid varchar(255), sorf varchar(255))
BEGIN
SELECT hostel.`HostelName`, rooms.`RoomID`, rooms.RoomNo, rooms.`BlockName`, 
      rooms.`SAB`, rooms.`AC_NonAC`, rooms.`Vacant` , 
      hostel.`HostelCaretaker`, hostel.HostelContact FROM `rooms`, 
      `roomsinhostel` , `hostel` WHERE rooms.Vacant = '1'
      and rooms.Guest_Student = sorf AND rooms.RoomID = roomsinhostel.RoomID
      AND roomsinhostel.HostelID = hostel.HostelID 
      AND hostel.`BOYSORGIRLS` = (select Gender FROM student 
      where (concat(roll_no_part_1 , '/' , roll_no_part_2 , '/' , roll_no_part_3) = sid))
END;
   */
      try{
           CallableStatement cst = conn.prepareCall(sql);
           cst.setString(1,sid);
           rs=cst.executeQuery();
           choice_room.removeAll();
           list.clear();
         //JOptionPane.showMessageDialog(null,"yes");
           while(rs.next())
           {
               String hostelName=rs.getString("HostelName");
               String roomId=rs.getString("RoomID");
               String roomNo=rs.getString("RoomNo");
               String blockName=rs.getString("BlockName");
               String sab=rs.getString("SAB");
               if(sab.equals("s"))
                   sab="";
               String acNonAc=rs.getString("AC_NonAC");
               list.add(Integer.valueOf(roomId));
               choice_room.addItem(blockName+roomNo+sab+" , "+hostelName);
           }
      }catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, e);
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

        jLabel1 = new javax.swing.JLabel();
        choice_room = new java.awt.Choice();
        btn_book = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Choose Room");

        btn_book.setText("Book");
        btn_book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(choice_room, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(136, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_book)
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choice_room, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(btn_book)
                .addGap(99, 99, 99))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_bookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bookActionPerformed
        // TODO add your handling code here:
         if(choice_room.getSelectedIndex()!=-1)
        {
            int id=list.get(choice_room.getSelectedIndex());
            sql="{?= Call Book(?,?)}";
            try{
            CallableStatement cst = conn.prepareCall(sql);
            cst.setString(2,sid);
            cst.setInt(3,id);
             cst.registerOutParameter(1,Types.INTEGER);
             cst.execute();
             int ret=cst.getInt(1);
             if(ret==0)
             {
                 JOptionPane.showMessageDialog(null,"Sorry!! This Room has been occupied.\nTry with different room.");
             }
             else
             {
                 JOptionPane.showMessageDialog(null,"Room Booked!");
                
                 super.dispose();
             }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        }
        this.dispose();
    }//GEN-LAST:event_btn_bookActionPerformed

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
            java.util.logging.Logger.getLogger(BookHostelForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookHostelForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookHostelForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookHostelForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
          sid=args[0];
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookHostelForStudent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_book;
    private java.awt.Choice choice_room;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}

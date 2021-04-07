/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegemanagement;

/**
 *
 * @author sksha
 */
import java.sql.*;
import javax.swing.*;
public class JavaConnect {
    Connection conn=null;
    public static Connection connectDb(){
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost/ims_dbms_project","root","unroot");
             System.out.println("connection ok!!!!!");
         return conn;   
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("connection problem");
            return null;
        }
    }
}

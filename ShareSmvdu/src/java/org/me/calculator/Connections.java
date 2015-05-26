/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.calculator;

import java.sql.DriverManager;
import java.sql.Connection;

/**
 *
 * @author knight
 */
public class Connections {

    public static Connection conn() {
        Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/share", "root", "Aayush123");
            //System.out.println("Connection established Successfully");
        } catch (Exception e) {
           System.out.print(e.toString());
        }
    
    return con ;
    }
}



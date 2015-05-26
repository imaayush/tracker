/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.calculator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DKG
 */
public class CountLDRFile {
    
    public static String countLike(int fileid) throws SQLException{
        String query = "select count(idnotification) as count1  from notifications where notification='Liked' AND idfiles=" + fileid;
        Connection con = Connections.conn();
        Statement ps = con.createStatement();
        ResultSet rs = ps.executeQuery(query);
        int count=0;
        while(rs.next()){
            count = rs.getInt("count1");
        }
        return Integer.toString(count);
    }    
    public static String countRecommend(int fileid) throws SQLException{
        String query = "select count(idnotification) as count1  from notifications where notification='Recommended' AND idfiles=" + fileid;
        Connection con = Connections.conn();
        Statement ps = con.createStatement();
        ResultSet rs = ps.executeQuery(query);
        int count=0;
        while(rs.next()){
            count = rs.getInt("count1");
        }
        return Integer.toString(count);
    }  
    public static String countDownload(int fileid) throws SQLException{
        String query = "select count(idnotification) as count1  from notifications where notification='Downloaded' AND idfiles=" + fileid;
        Connection con = Connections.conn();
        Statement ps = con.createStatement();
        ResultSet rs = ps.executeQuery(query);
        int count=0;
        while(rs.next()){
            count = rs.getInt("count1");
        }
        return Integer.toString(count);
    }  
}

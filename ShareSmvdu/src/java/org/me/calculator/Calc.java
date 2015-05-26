/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.calculator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Math.ceil;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author knight
 */
@WebService(serviceName = "Calc")
public class Calc {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + "fuck you" + " !";
    }
    

    @WebMethod(operationName = "authenticateUser")
    public boolean authenticateUser(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        boolean result = false;
        String query = "select Password from user where UserName='" + username + "'";
        Connection con = Connections.conn();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                if (!rs.getString(1).equals(password)) {
                    return result;
                } else {

                    result = true;
                    return result;
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registerUser")
    public boolean registerUser(@WebParam(name = "username") String username, @WebParam(name = "email") String email, @WebParam(name = "password") String password, @WebParam(name = "name") String name, @WebParam(name = "gender") String gender) {
        //TODO write your implementation code here:
        boolean result = false;
        Connection con = Connections.conn();
        Statement st;
        Date date = new java.sql.Date((new Date(System.currentTimeMillis())).getTime());
        String autoPassword = Password.pass();
        String stat = SendEmail.sendEmail(email, "Registration Successfully", "Your password is : " + autoPassword);
        String query = "insert into user(Name, Password, Email, Username, Gender, image,dateuser) values('" + name + "','" + autoPassword  + "', '" + email + "', '" + username + "', '" + gender + "', 'images/profile.jpg','" + date + "')";

        try {
            st = con.createStatement();
            st.executeUpdate(query);
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Invoked");
        return result;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "notification")
    public ArrayList<String> notification(@WebParam(name = "username") String username) {
        Connection con = Connections.conn();
        ArrayList<String> note = new ArrayList<String>();
        String query = "select   notification,notifications.username,files.username ,notifications.idfiles,filedescription,filetags,filename,notificationdatetime,image from circle inner join notifications on notifications.username=circle.username inner join files on notifications.idfiles=files.idfiles inner join user  on circle.username=user.username where circle.circlename='" + username + "'";

        try {

            Statement ps = con.createStatement();

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                String n = null;
                n = rs.getString(2);

                String s = rs.getString(8);
                String s1[] = s.split("\\.");

                n = n + " " + rs.getString(1) + " " + rs.getString("filename");

                n = n + "," + rs.getString(4) + "," + s1[0];
                n = n + "," + rs.getString("filetags");
                //n.setFileid(rs.getString(4));
                // n.setFiledis(rs.getString(5));
                //n.setImage(rs.getString(9));
                note.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return note;
    }

    /**
     * Web service operation
     */
    Blob blob;
    private byte b[];

    public byte[] getB() {
        return b;
    }

    public void setB(byte[] b) {
        this.b = b;
    }
    private static final int BUFFER_SIZE = 8096;

    @WebMethod(operationName = "download")
    public byte[] download(@WebParam(name = "fileid") String fileid) {
        Connection con = Connections.conn();
        int f = 0;
        try {

            String sql = "SELECT file FROM files WHERE idfiles=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, fileid);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                blob = rs.getBlob("file");
                f = 1;
            }

            b = new byte[(int) blob.length()];
            b = blob.getBytes(1, (int) blob.length());

            return b;
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "fileView")
    public String fileView(@WebParam(name = "fileid") String fileid) {
        String result = null;
        int f = 0;
        Connection con = Connections.conn();
        // HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);

        Statement st;
        try {
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select filename,filetags,filedescription,idfiles,viewed from files where idfiles ='" + fileid + "'");

            while (rs.next()) {

                result = rs.getString(1);
                result = result + "," + rs.getString(2);
                result = result + "," + rs.getString(3);
                result = result + "," + rs.getString(4);
                int count = rs.getInt(5);
                result = result + "," + count;
                ++count;
                PreparedStatement ps = con.prepareStatement("update files set viewed=" + count + " where idfiles=" + Integer.parseInt(fileid));
                ps.executeUpdate();
            }

            result = result + "," + CountLDRFile.countLike(Integer.parseInt(fileid));
            result = result + "," + CountLDRFile.countRecommend(Integer.parseInt(fileid));
            result = result + "," + CountLDRFile.countDownload(Integer.parseInt(fileid));

            String query = "select file from files where idfiles=" + fileid;
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                blob = rs.getBlob("file");
                f = 1;
            }

            b = new byte[(int) blob.length()];
            b = blob.getBytes(1, (int) blob.length());
            StringBuilder builder = new StringBuilder();
            int i = 0;
            while (!builder.toString().endsWith("pieces")) {
                i++;
                builder.append((char) b[i]); // It's ASCII anyway.

            }
            String info1 = builder.toString();
            String[] p = info1.split("8:announce36:|8:announce5:", 2);
            info1 = p[1];
            p = info1.split("announce|18:", 2);
            if (p[0].equals("http:")) {
                //setTracker("none");
            } else {
                //setTracker(p[0]);
            }
            info1 = p[1];
            p = info1.split("filesld6:");
            info1 = p[1];
            p = info1.split("eee4:name");
            String temp = p[0];
            info1 = p[1];
            p = info1.split("12:piece|:");
            String temp1;
            temp1 = p[1];

            //setTorrentname(temp1.substring(0, (temp1.length() - 2)));
            info1 = temp;
            p = info1.split("d6:");
            double totalsize1 = 0;
            for (int j = 0; j < p.length; j++) {
                String size, temp2;
                //TorrentInfo t = new TorrentInfo();
                temp2 = p[j];
                String[] x = temp2.split("pathl");
                temp2 = x[1];
                size = x[0];
                size = size.split("e4:|lengthi")[1];

                double size1 = Double.parseDouble(size) / 1024;
                totalsize1 = totalsize1 + size1;
                if (size1 > 1024) {
                    size = ceil(size1 / 1024) + " MB";
                } else {
                    size = ceil(size1) + " KB";
                }

                x = temp2.split(":", 2);
                temp2 = x[1];
                x = temp2.split(":", 2);
                temp2 = x[0];
                temp2 = temp2.substring(0, (temp2.length() - 3));
                //t.setTname(temp2);
                //t.setTsize(size);
                //torrentinfo.add(t);
            }
            result = result + "," + (ceil(totalsize1 / 1024) + " MB");

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "changePassword")
    public boolean changePassword(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {

        try {
            Connection con = Connections.conn();
            String query = "update user set password='" + password + "'  where username = '" + username + "'";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "circle")
    public ArrayList<String> circle(@WebParam(name = "username") String username) {
        Connection con = Connections.conn();
        ArrayList<String> circle = new ArrayList<String>();
        String query = "select name, UserName from user where UserName!='" + username + "'";

        try {

            Statement ps = con.createStatement();

            ResultSet rs = ps.executeQuery(query);
            String n = null;
            while (rs.next()) {

                n = rs.getString("name");
                n = n + "(" + rs.getString("UserName") + ")";
                String query1 = "select * from circle where username='" + rs.getString("UserName") + "' and circlename='" + username + "'";
                //Connection con = Connections.conn();
                Statement st;
                try {
                    st = con.createStatement();
                    ResultSet rs1 = st.executeQuery(query1);
                    if (rs1.next()) {
                        n = n + "," + "Remove";
                    } else {
                        n = n + "," + "Add";
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                //n.setFileid(rs.getString(4));
                // n.setFiledis(rs.getString(5));
                //n.setImage(rs.getString(9));
                circle.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return circle;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "image")
    public byte[] image(@WebParam(name = "username") String username) {
        FileInputStream fileInputStream = null;

        File file = new File("C:\\Users\\knight\\Documents\\NetBeansProjects\\SearchSmvdu\\web\\img\\2011ecs18.jpg");

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char) bFile[i]);
            }

            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bFile;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "checkcircle")
    public boolean checkcircle(@WebParam(name = "username") String username, @WebParam(name = "password") String circle) {
        boolean result = false;
        String[] s = username.split("\\(|\\)");

        String[] s1 = circle.split(",");

        if (s1[0].toString().equals("Add")) {
            String query = "insert into circle(circlename, username) values('" + s1[1] + "','" + s[1] + "')";
            Connection con = Connections.conn();
            Statement st;
            try {
                st = con.createStatement();
                st.executeUpdate(query);

                result = true;

            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        } else {
            String query = "DELETE FROM circle where circlename='" + s1[1] + "'";
            Connection con = Connections.conn();

            try {
                PreparedStatement st = con.prepareStatement("delete from circle where circlename='" + s1[1] + "' AND username='" + s[1] + "'");
                st.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }

        return result;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "search")
    public ArrayList<String> search(@WebParam(name = "username") String searchtext) {
        Connection con = Connections.conn();
        ArrayList<String> note = new ArrayList<String>();
        String query = "select filename,filetags,filedescription ,idfiles,datetime,viewed from files where filename like '%" + searchtext + "' or filetags like '%" + searchtext + "' or filedescription like '%" + searchtext + "%' ORDER BY viewed DESC";

        try {

            Statement ps = con.createStatement();

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                String n = null;
                n = rs.getString("filename");

                n = n + "," + rs.getString("idfiles");

                n = n + ",Tags:" + rs.getString("filetags") + "   Views:" + rs.getString("viewed");

                n = n + "," + rs.getString("filetags");
                //n.setFileid(rs.getString(4));
                // n.setFiledis(rs.getString(5));
                //n.setImage(rs.getString(9));
                note.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return note;
    }

}

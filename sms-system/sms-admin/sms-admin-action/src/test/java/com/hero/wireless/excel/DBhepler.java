package com.hero.wireless.excel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBhepler {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://bj-cdb-9smxilcn.sql.tencentcdb.com:63844/s_business?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    
    
    Connection con = null;
    ResultSet res = null;

    public void DataBase() throws SQLException {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, "root", "asdf5213volcano");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                  System.err.println("装载 JDBC/ODBC 驱动程序失败。" );  
                  con.close();
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
            	con.close();
                System.err.println("无法连接数据库" ); 
                
                e.printStackTrace();
            } 
    }


    // 增删修改
    public int AddU(String sql, String str[]) {
        int a = 0;
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            if (str != null) {
                for (int i = 0; i < str.length; i++) {
                    pst.setString(i + 1, str[i]);
                }
            }
            a = pst.executeUpdate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;
    }

}
package com.example.WordNotes.utils;

import android.util.Log;

import java.io.EOFException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {



    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.d("JDBCUtils", "加载JDBC驱动成功！");
        } catch (ClassNotFoundException e) {
            Log.d("JDBCUtils", "加载JDBC驱动失败！");
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        Connection  conn = null;
//        try {
//            conn= DriverManager.getConnection("jdbc:mysql://填自己的IP:端口/db_words?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8","root","qw901399");
//            Log.d("JDBCUtils", "通过花生壳连接数据库成功!");
//        }catch (Exception exception) {
//            Log.d("JDBCUtils", "通过花生壳连接数据库失败，正在尝试第二种方案！");
//            exception.printStackTrace();
            try {
                conn= DriverManager.getConnection("jdbc:mysql://填自己的IP:3306/db_words?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8","root","qw901399");
                Log.d("JDBCUtils", "通过局域网连接数据库成功！");
            } catch (Exception exception2) {
                Log.d("JDBCUtils", "通过局域网连接数据库失败，正在尝试第三种方案！");
                exception2.printStackTrace();
            try {
                conn= DriverManager.getConnection("jdbc:mysql://填自己的IP:3306/db_words?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8","root","qw901399");
                Log.d("JDBCUtils", "通过ZeroTier one连接数据库成功!");
            } catch (Exception exception3) {
                Log.d("JDBCUtils", "通过ZeroTier one连接数据库失败，请检查网络连接!");
                exception3.printStackTrace();
            }
            }
//        }
        return conn;
    }

    public static void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}



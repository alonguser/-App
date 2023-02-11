package com.example.WordNotes.dao;

import com.example.WordNotes.entity.UserBean;
import com.example.WordNotes.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {


    public boolean  login(String username,String password){
        UserBean user=null;
        String sql = "select * from user where username = ? and password = ?";
        Connection  con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return false;
    }

    public boolean register(String username,String password,String phone,String sex){
        String sql = "insert into user(username,password,phone,sex) values (?,?,?,?)";
        Connection  con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, phone);
            pst.setString(4,sex);

            int value = pst.executeUpdate();

            if(value>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return false;
    }

    public List<UserBean> findUser(String username){
        List<UserBean> stuList=new ArrayList<>();
        String sql = "select * from user where username = ?";
        Connection  con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                UserBean user=new UserBean();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setSex(rs.getString(4));
                user.setPhone(rs.getString(5));
                stuList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return stuList;
    }
}


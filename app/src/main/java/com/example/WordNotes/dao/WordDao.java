package com.example.WordNotes.dao;

import android.util.Log;

import com.example.WordNotes.entity.WordBean;
import com.example.WordNotes.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDao {


    public List<WordBean> showAllWords(String username){
        List<WordBean> wordList=new ArrayList<>();
        String sql = "select * from wordlist where username=?";
        Connection con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                WordBean word=new WordBean();
                word.setWordid(rs.getInt(1));
                word.setWordname(rs.getString(3));
                word.setParaphrase(rs.getString(4));
                word.setWordgroup(rs.getString(5));
                word.setNote(rs.getString(6));
                word.setStar(rs.getInt(7));
                word.setListgroup(rs.getString(8));
                wordList.add(word);
                Log.d("WordDao", "查询单词成功!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.d("WordDao", "查询单词失败!");
        }finally {
            JDBCUtils.close(con);
        }
        return wordList;
    }

    public int deleteWords(String username,String wordname){
        int row=0;
        String sql = "delete from wordlist where username=? and wordname=?";
        Connection con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,wordname);
            row=pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
       return row;
    }

    public int updateWords(String pd,String updata1,int id){
        String sql=null;
        int row=0;
        switch (pd){
            case "title":
                sql = "update wordlist set wordname='"+updata1+"' where wordid="+id;
                break;
            case "content":
                sql = "update wordlist set paraphrase='"+updata1+"' where wordid="+id;
                break;
            case "wordgroup":
                sql = "update wordlist set wordgroup='"+updata1+"' where wordid="+id;
                break;
            case "note":
                sql = "update wordlist set note='"+updata1+"' where wordid="+id;
                break;
            case "listgroup":
                sql = "update wordlist set listgroup='"+updata1+"' where wordid="+id;
                break;
        }
        Connection con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
//            pst.setString(1,updata1);
//            pst.setInt(2,id);
            row=pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return row;
    }

    public int updateStar(int updata1,int id){
        int row=0;
        Log.d("updateStar", "updata1"+updata1);
        String sql = "update wordlist set star=? where wordid=?";
        Connection con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,updata1);
            pst.setInt(2,id);
            row=pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return row;
    }

    public int addWords(WordBean wordBean,String username){
        int row=0;
        String sql = "insert into wordlist(username,wordname,paraphrase,wordgroup,note,star,listgroup)VALUES(?,?,?,?,?,?,?)";
        Connection con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,wordBean.getWordname());
            pst.setString(3,wordBean.getParaphrase());
            pst.setString(4,wordBean.getWordgroup());
            pst.setString(5,wordBean.getNote());
            pst.setInt(6,wordBean.getStar());
            pst.setString(7,wordBean.getListgroup());
            row = pst.executeUpdate();
            Log.d("WordDao", "添加单词成功!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.d("WordDao", "添加单词失败!");
        }finally {
            JDBCUtils.close(con);
        }
        return row;
    }
}

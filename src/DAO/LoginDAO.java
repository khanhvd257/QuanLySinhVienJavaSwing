package DAO;

import Connection.JdbcHelper;

import java.sql.ResultSet;

public class LoginDAO {
    private String tableName = "TaiKhoan";

    public ResultSet login(String username, String password){
        String sql= "SELECT * FROM "+tableName+" WHERE username = ? AND password = ?";
        return JdbcHelper.executeQuery(sql, username, password);
    }

    public ResultSet layThongTinGV(String username){
        String sql= "SELECT * FROM GiangVien WHERE username = ?";
        return JdbcHelper.executeQuery(sql, username);
    }
    public ResultSet layThongTinSV(String username){
        String sql= "SELECT * FROM SinhVien WHERE username = ?";
        return JdbcHelper.executeQuery(sql, username);
    }

}

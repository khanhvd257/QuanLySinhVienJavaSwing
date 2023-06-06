/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.JdbcHelper;
import Model.Khoa;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author vankhanh
 */
public class KhoaDAO {
    private String tableName= "Khoa";
    public ResultSet getAll() {
        String sql = "SELECT * FROM " + tableName + " WHERE deleteFlag = 0";
        return JdbcHelper.executeQuery(sql);
    }

    public ResultSet findName(String name) {
        String sql = "SELECT * FROM " + tableName + " WHERE deleteFlag = 0 AND ten LIKE '%" + name + "%'";
        return JdbcHelper.executeQuery(sql);
    }

    public boolean them(String ten) {
        String[] columns = {"ten"};
        Object[] values = {ten};
        return JdbcHelper.insert(tableName, columns, values);
    }

    public boolean sua(int maKhoa, String ten) {
        String[] columns = {"ten"};
        Object[] values = {ten};
        String dieuKien = "id=" + maKhoa;
        return JdbcHelper.update(tableName, columns, values, dieuKien);

    }

    public void xoa(int id) {
        String sql = "UPDATE " + this.tableName + " SET deleteFlag = 1 WHERE id = ?";
        try {
            JdbcHelper.executeUpdate(sql, id);

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa Dữ liệu: " + e.getMessage());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.JdbcHelper;
import Model.Lop;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/**
 * @author vankhanh
 */
public class LopDAO {
    private final String tableName = "LopHoc";

    public ResultSet getAll() {
        String sql = "SELECT *, k.ten as tenKhoa, k.id as maKhoa FROM "+tableName+" l JOIN Khoa k ON l.maKhoa = k.id WHERE l.deleteFlag = 0";
        return JdbcHelper.executeQuery(sql);
    }

    public ResultSet findName(String name) {
        String sql = "SELECT *, k.ten as tenKhoa, k.id as maKhoa FROM " + tableName + " l JOIN Khoa k ON l.maKhoa = k.id WHERE l.deleteFlag = 0 AND l.ten LIKE '%" + name + "%'";
        return JdbcHelper.executeQuery(sql);
    }

    public boolean them(String ten, String maKhoa) {
        String[] columns = {"ten", "maKhoa"};
        Object[] values = {ten, maKhoa};
        return JdbcHelper.insert(tableName, columns, values);
    }

    public boolean sua(int maLop, String ten, String maKhoa) {
        String[] columns = {"ten", "maKhoa"};
        Object[] values = {ten, maKhoa};
        String dieuKien = "id=" + maLop;
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

    public ArrayList<Lop> getAllLop() {
        ArrayList<Lop> arrLop = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName;
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Lop model = readFromResult(rs);
                    arrLop.add(model);
                }
            } finally {
                Objects.requireNonNull(rs).getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return arrLop;
    }

    private Lop readFromResult(ResultSet rs) throws SQLException {
        Lop lop = new Lop();
        lop.setMalop(rs.getInt("id"));
        lop.setMaKhoa(rs.getInt("maKhoa"));
        lop.setTenLop(rs.getString("ten"));
        return lop;

    }
}

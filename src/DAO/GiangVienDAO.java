package DAO;

import Connection.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class GiangVienDAO {
    private final String tableName = "GiangVien";

    public ResultSet getAll() {
        String sql = "SELECT *, k.id as maKhoa, k.ten AS tenKhoa FROM " + tableName + " gv JOIN Khoa k ON gv.maKhoa = k.id WHERE gv.deleteFlag = 0";
        return JdbcHelper.executeQuery(sql);
    }
    public ResultSet findName(String name) {
        String sql = "SELECT *, k.id as maKhoa, k.ten AS tenKhoa FROM " + tableName + " gv JOIN Khoa k ON gv.maKhoa = k.id WHERE gv.deleteFlag = 0 AND gv.ten LIKE '%"+name+"%'";
        return JdbcHelper.executeQuery(sql);
    }

    public boolean themGiangVien(String ten, String email, String maKhoa, String soDienThoai, String username) {
        String[] columns = {"ten", "email", "maKhoa", "soDienThoai", "username"};
        Object[] values = {ten, email, maKhoa, soDienThoai, username};
        return JdbcHelper.insert(tableName, columns, values);

    }
    public boolean suaGV(int maGV, String ten, String email, String maKhoa, String soDienThoai) {
        String[] columns = {"ten", "email", "maKhoa", "soDienThoai"};
        Object[] values = {ten, email, maKhoa, soDienThoai};
        String dieuKien = "id="+maGV;
        return JdbcHelper.update(tableName, columns, values,dieuKien);

    }
    public boolean suaGiangVien(int maGV, String ten, String email, String soDienThoai) {
        String[] columns = {"ten", "email", "soDienThoai"};
        Object[] values = {ten, email, soDienThoai};
        String dieuKien = "id="+maGV;
        return JdbcHelper.update(tableName, columns, values,dieuKien);

    }
    public void xoaGV(int id) {
        String sql = "UPDATE " + this.tableName + " SET deleteFlag = 1 WHERE id = ?";
        try {
            JdbcHelper.executeUpdate(sql, id);

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa GiangVien: " + e.getMessage());
        }
    }

    public ResultSet traCuuGVTheoMaMon(String maMon){
        String sql ="SELECT *, gv.ten AS tenGV,k.ten AS tenKhoa FROM GiangVienDangKy dky JOIN GiangVien gv ON dky.maGiangVien = gv.id JOIN Khoa k ON k.id = gv.maKhoa WHERE dky.maMonHoc = ?";
        return JdbcHelper.executeQuery(sql,maMon);


    }

}

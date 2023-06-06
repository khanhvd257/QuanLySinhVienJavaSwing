/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vankhanh
 */
public class MonDAO {
    private final String tableName = "MonHoc";

    public ResultSet getAll() {
        String sql = "SELECT * FROM " + tableName + " WHERE deleteFlag = 0";
        return JdbcHelper.executeQuery(sql);
    }
    public ResultSet LayMonDayTheoGiangVien(int maGV){
        String sql = "SELECT * FROM MonHoc m WHERE id IN (SELECT maMonHoc FROM GiangVienDangKy dk WHERE dk.maGiangVien = "+maGV+")";
        return JdbcHelper.executeQuery(sql);
    }

    public ResultSet laySoLuongSVcuaTungMonDK(int maGV){



        // số luợng sinh viên mỗi môn mà gv dạy
        String sql1= "SELECT *,  mh.ten AS tenMon, COUNT(d.maSinhVien) AS soLuongSV " +
                "FROM DiemThi d JOIN GiangVien gv ON gv.id = d.maGiangVien JOIN MonHoc mh ON mh.id = d.maMonHoc " +
                "WHERE gv.id = " + maGV+ " GROUP BY mh.id";
        return JdbcHelper.executeQuery(sql1);
    }
    private ResultSet layMonDayCuaGiangVien(int maGV){
        // tất cả các môn của giảng viên dạy
        String sql = "SELECT mh.ten ,mh.soTinChi, COUNT(d.maSinhVien) AS soLuongSV FROM DiemThi d JOIN(" +
                "SELECT * FROM MonHoc WHERE id IN (SELECT gv.maMonHoc FROM GiangVienDangKy gv WHERE gv.maGiangVien = "+maGV+")) AS mh ON d.maMonHoc = mh.id" +
                " GROUP BY mh.id";
        return JdbcHelper.executeQuery(sql);
    }
    public ResultSet layGiangVienChuaDayTheoMon(int maMon){
        String sql ="SELECT * FROM GiangVien gv WHERE gv.id NOT IN (SELECT maGiangVien FROM GiangVienDangKy dk WHERE maMonHoc = "+maMon+")";
        return JdbcHelper.executeQuery(sql);
    }

    public ResultSet findName(String name) {
        String sql = "SELECT * FROM " + tableName + " WHERE deleteFlag = 0 AND ten LIKE '%" + name + "%'";
        return JdbcHelper.executeQuery(sql);
    }

    public boolean them(String ten, String soTin) {
        String[] columns = {"ten", "soTinChi"};
        Object[] values = {ten, soTin};
        return JdbcHelper.insert(tableName, columns, values);
    }
    public boolean themGiangVienDayHoc(String maGiangVien, String maMonHoc){
        String[] columns = {"maGiangVien", "maMonHoc"};
        String[] values = {maGiangVien, maMonHoc};
        return JdbcHelper.insert("GiangVienDangKy",columns,values);
    }
    public boolean sua(int maMon, String ten, String soTin) {
        String[] columns = {"ten", "soTinChi"};
        Object[] values = {ten, soTin};
        String dieuKien = "id=" + maMon;
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

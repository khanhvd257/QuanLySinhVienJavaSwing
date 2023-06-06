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
public class DiemThiDAO {

    // bảng này chứa cả thông tin đăng kí của sinh viên khi đăng kí môn học
    private final String tableName = "DiemThi";

    public ResultSet layDanhSachDiemThiTheoGiangVien(String maMon, int maGV) {
        String sql = "SELECT *, m.ten AS tenMon, sv.ten AS tenSV FROM DiemThi d JOIN SinhVien sv " +
                "ON  d.maSinhVien = sv.id JOIN MonHoc m ON m.id = d.maMonHoc WHERE  d.deleteFlag = 0 " +
                "AND m.id = '"+maMon+"' AND d.maGiangVien = "+maGV;
        return JdbcHelper.executeQuery(sql);
    }

    public ResultSet findName(String name) {
        String sql = "SELECT *, m.ten AS tenMon, sv.ten AS tenSV FROM DiemThi d JOIN SinhVien sv ON  d.maSinhVien = sv.id JOIN MonHoc m ON m.id = d.maMonHoc WHERE d.deleteFlag = 0 AND sv.ten LIKE '%" + name + "%' ";
        return JdbcHelper.executeQuery(sql);
    }

    /*dành cho sinh viên đăng kí học*/

    public boolean them(String maSV, String maGV, String maMonHoc) {
        String[] columns = {"maSinhVien", "maGiangVien", "maMonHoc"};
        Object[] values = {maSV, maGV, maMonHoc};
        return JdbcHelper.insert(tableName, columns, values);

    }

    public boolean nhapDiem(int maMon, int maSV, String diemChuyenCan, String diemGiuaKy, String diemCuoiKy) {
        String[] columns = {"diemChuyenCan", "diemGiuaKy", "diemCuoiKy"};
        Object[] values = {diemChuyenCan, diemGiuaKy, diemCuoiKy};
        String dieuKien = "maMonHoc=" + maMon + " AND maSinhVien=" + maSV;
        return JdbcHelper.update(tableName, columns, values, dieuKien);

    }

    public ResultSet LayDiemTheoSinhVien(int maSinhVien) {
        String sql = "SELECT *, m.ten AS tenMon, sv.ten AS tenSV FROM DiemThi d JOIN SinhVien sv ON  d.maSinhVien = sv.id JOIN MonHoc m ON m.id = d.maMonHoc "
                + "JOIN (SELECT gv.ten AS tenGV, gv.id FROM GiangVien gv) AS gv1 ON d.maGiangVien = gv1.id WHERE  d.deleteFlag = 0 AND sv.id ="+maSinhVien;
        return JdbcHelper.executeQuery(sql);
    }
    public ResultSet LayDiemTheoMonCuaSinhVien(int maSinhVien, String tenMon) {
        String sql = "SELECT *, m.ten AS tenMon, sv.ten AS tenSV FROM DiemThi d JOIN SinhVien sv " +
                "ON  d.maSinhVien = sv.id JOIN MonHoc m ON m.id = d.maMonHoc WHERE  d.deleteFlag = 0 " +
                "AND sv.id = "+maSinhVien+ " AND m.ten LIKE '%"+tenMon+"%'";
        return JdbcHelper.executeQuery(sql);
    }
//    public void xoaGV(int id) {
//        String sql = "UPDATE " + this.tableName + " SET deleteFlag = 1 WHERE id = ?";
//        try {
//            JdbcHelper.executeUpdate(sql, id);
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Lỗi khi xóa GiangVien: " + e.getMessage());
//        }
//    }

}

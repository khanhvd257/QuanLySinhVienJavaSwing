package DAO;

import Connection.JdbcHelper;

import java.sql.ResultSet;

public class DangKyHocDAO {

    public ResultSet layTatCaMonChuaHoc(int maSV) {
        String sql = "SELECT * FROM MonHoc WHERE id NOT IN (SELECT maMonHoc FROM DiemThi WHERE maSinhVien = "+maSV+")";
        return JdbcHelper.executeQuery(sql);
    }

    public ResultSet findName(int maSV, String tenMon) {
        String sql = "SELECT * FROM MonHoc m WHERE id NOT IN (SELECT maMonHoc FROM DiemThi WHERE maSinhVien = "+maSV+") AND m.ten LIKE '%" + tenMon + "%' ";
        return JdbcHelper.executeQuery(sql);
    }
    public ResultSet giangVienDayMon(int maMon) {
        String sql = "SELECT gv.id, gv.ten FROM GiangVien gv WHERE gv.id IN " +
                "(SELECT dk.maGiangVien FROM GiangVienDangKy dk WHERE maMonHoc ="+maMon+")";
        return JdbcHelper.executeQuery(sql);
    }



    /*dành cho sinh viên đăng kí học*/

    public boolean dangKyHoc(int maSV, String maGV, int maMonHoc) {
        String[] columns = {"maSinhVien", "maGiangVien", "maMonHoc"};
        Object[] values = {maSV, maGV, maMonHoc};
        return JdbcHelper.insert("DiemThi", columns, values);

    }

}

package DAO;

import Connection.JdbcHelper;

import java.sql.ResultSet;

public class HocPhiDAO {
    public ResultSet layHocPhiTheoSinhVien(int maSV) {
        String sql = "SELECT * FROM HocPhi hp JOIN MonHoc mh ON mh.id = hp.maMonHoc WHERE hp.maSinhVien =?";
        return JdbcHelper.executeQuery(sql,maSV);
    }

    public ResultSet tatCaHocPhi(){
        String sql = "SELECT sv.ten AS tenSinhVien, mh.ten AS tenMon, l.ten AS tenLop, k.ten AS tenKhoa, COUNT(hp.maMonHoc) AS tongSoMon," +
                " SUM(hp.HocPhi) AS tongHocPhi, SUM(mh.soTinChi) AS tongTinChi FROM SinhVien sv JOIN LopHoc l ON" +
                " l.id = sv.maLop JOIN Khoa k ON k.id = l.maKhoa JOIN HocPhi hp ON hp.maSinhVien = sv.id JOIN MonHoc mh ON mh.id = hp.maMonHoc GROUP BY hp.maSinhVien";
        return  JdbcHelper.executeQuery(sql);
    }
    public ResultSet tatCaHocPhiTheoSinhVien(String tenSV){
        String sql = "SELECT sv.ten AS tenSinhVien, mh.ten AS tenMon, l.ten AS tenLop, k.ten AS tenKhoa, COUNT(hp.maMonHoc) AS tongSoMon," +
                " SUM(hp.HocPhi) AS tongHocPhi, SUM(mh.soTinChi) AS tongTinChi FROM SinhVien sv JOIN LopHoc l ON" +
                " l.id = sv.maLop JOIN Khoa k ON k.id = l.maKhoa JOIN HocPhi hp ON hp.maSinhVien = sv.id JOIN MonHoc mh ON mh.id = hp.maMonHoc WHERE sv.ten LIKE '%"+tenSV+"%' GROUP BY hp.maSinhVien";
        return  JdbcHelper.executeQuery(sql);
    }

}

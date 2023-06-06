package DAO;

import Connection.JdbcHelper;
import Model.SinhVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SinhVienDAO {

    private final String tableName = "SinhVien";

    public ArrayList<SinhVien> getAllSinhVien() throws SQLException {
        ArrayList<SinhVien> arrSinhVien = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName;
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    SinhVien model = readFromResult(rs);
                    arrSinhVien.add(model);
                }
            } finally {
                Objects.requireNonNull(rs).getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return arrSinhVien;
    }

    public SinhVien getStudentById(int id) throws SQLException {
        SinhVien SV = new SinhVien();
        String sql = "SELECT * FROM " + this.tableName + " WHERE id = ?";
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    SV = readFromResult(rs);
                }
            } finally {
                Objects.requireNonNull(rs).getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return SV;
    }

    public void deleteStudentById(int id) {
        String sql = "DELETE FROM " + this.tableName + " WHERE id = ?";
        try {
            JdbcHelper.executeUpdate(sql, id);

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

    public boolean updateSinhVien(int id,String ten, Date ngaySinh, String gioiTinh, String diaChi, String maLop, String email, String soDienThoai, String username) {
//        String sql = "INSERT INTO " + tableName + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
        String[] columns = {"ten", "ngaySinh", "gioiTinh", "diaChi", "maLop", "email", "soDienThoai", "username"};
        Object[] values = {ten, ngaySinh, gioiTinh, diaChi, maLop, email, soDienThoai, username};
        boolean success = JdbcHelper.update(tableName, columns, values, "id="+id);
        return success;

    }

    public boolean insertStudent(String ten, Date ngaySinh, String gioiTinh, String diaChi, String maLop, String email, String soDienThoai, String username) {
        String[] columns = {"id", "ten", "ngaySinh", "gioiTinh", "diaChi", "maLop", "email", "soDienThoai", "username"};
        Object[] values = {null, ten, ngaySinh, gioiTinh, diaChi, maLop, email, soDienThoai, username};
        return JdbcHelper.insert(tableName, columns, values);
    }

    public ArrayList<SinhVien> findStudentByName(String tenSV) {
        ArrayList<SinhVien> studentData = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE ten LIKE '%" + tenSV + "%'";
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    SinhVien model = readFromResult(rs);
                    studentData.add(model);
                }
            } finally {
                Objects.requireNonNull(rs).getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentData;
    }


    private SinhVien readFromResult(ResultSet rs) throws SQLException {
        SinhVien sv = new SinhVien();
        sv.setMaSV(rs.getInt("id"));
        sv.setTenSinhVien(rs.getString("ten"));
        sv.setNgaySinh(rs.getDate("ngaySinh"));
        sv.setGioiTinh(rs.getString("gioiTinh"));
        sv.setDiaChi(rs.getString("diaChi"));
        sv.setEmail(rs.getString("email"));
        sv.setSoDienThoai(rs.getString("soDienThoai"));
        sv.setUsername(rs.getString("username"));
        sv.setMaLop(rs.getInt("maLop"));
        return sv;

    }

}

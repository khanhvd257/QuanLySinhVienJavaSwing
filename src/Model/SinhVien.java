/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 * @author vankhanh
 */
public class SinhVien {
    private String tenSinhVien;
    private String diaChi;
    private String email;
    private String soDienThoai;
    private String username;
    private String gioiTinh;
    private int maSV, maLop;
    private Date NgaySinh;


    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SinhVien() {
    }

    public SinhVien(String tenSinhVien, String diaChi, String email, String soDienThoai, String username, String gioiTinh, int maSV, int maLop, Date ngaySinh) {
        this.tenSinhVien = tenSinhVien;
        this.diaChi = diaChi;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.username = username;
        this.gioiTinh = gioiTinh;
        this.maSV = maSV;
        this.maLop = maLop;
        NgaySinh = ngaySinh;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "tenSinhVien='" + tenSinhVien + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", maSV=" + maSV +
                ", maLop=" + maLop +
                ", NgaySinh=" + NgaySinh +
                '}';
    }

    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public int getMaLop() {
        return maLop;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        NgaySinh = ngaySinh;
    }
}

package Model;

public class GiangVien {
    private String tenGiangVien, email, soDienThoai;
    private Integer maGiangVien, maKhoa;

    public String getTenGiangVien() {
        return tenGiangVien;
    }

    public void setTenGiangVien(String tenGiangVien) {
        this.tenGiangVien = tenGiangVien;
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

    public Integer getMaGiangVien() {
        return maGiangVien;
    }

    public void setMaGiangVien(Integer maGiangVien) {
        this.maGiangVien = maGiangVien;
    }

    public Integer getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(Integer maKhoa) {
        this.maKhoa = maKhoa;
    }

    public GiangVien() {
    }

    public GiangVien(String tenGiangVien, String email, String soDienThoai, Integer maGiangVien, Integer maKhoa) {
        this.tenGiangVien = tenGiangVien;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.maGiangVien = maGiangVien;
        this.maKhoa = maKhoa;
    }
}

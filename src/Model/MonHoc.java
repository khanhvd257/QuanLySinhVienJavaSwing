package Model;

public class MonHoc {
    private String tenMon;
    private int maMon, maGiangVien, soTinChi;

    @Override
    public String toString() {
        return "MonHoc{" +
                "tenMon='" + tenMon + '\'' +
                ", maSinhVien=" + maMon +
                ", maGiangVien=" + maGiangVien +
                ", soTinChi=" + soTinChi +
                '}';
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getMaSinhVien() {
        return maMon;
    }

    public void setMaSinhVien(int maSinhVien) {
        this.maMon = maSinhVien;
    }

    public int getMaGiangVien() {
        return maGiangVien;
    }

    public void setMaGiangVien(int maGiangVien) {
        this.maGiangVien = maGiangVien;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public MonHoc() {
    }

    public MonHoc(String tenMon, int maMon, int maGiangVien, int soTinChi) {
        this.tenMon = tenMon;
        this.maMon = maMon;
        this.maGiangVien = maGiangVien;
        this.soTinChi = soTinChi;
    }
}

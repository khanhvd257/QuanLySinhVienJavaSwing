package Model;

public class HocPhi {
    private int maHocPhi, maSinhVien, maMonHoc;
    private Double hocPhi;

    @Override
    public String toString() {
        return "HocPhi{" +
                "maHocPhi=" + maHocPhi +
                ", maSinhVien=" + maSinhVien +
                ", maMonHoc=" + maMonHoc +
                ", hocPhi=" + hocPhi +
                '}';
    }

    public int getMaHocPhi() {
        return maHocPhi;
    }

    public void setMaHocPhi(int maHocPhi) {
        this.maHocPhi = maHocPhi;
    }

    public int getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(int maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public int getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(int maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public Double getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(Double hocPhi) {
        this.hocPhi = hocPhi;
    }

    public HocPhi() {
    }
}

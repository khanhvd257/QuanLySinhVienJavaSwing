package Model;

public class DiemThi {
    private int maDiemThi, maSinhVien, maMonHoc;
    private Float diemCuoiKy, diemGiuaKy, diemChuyenCan;

    @Override
    public String toString() {
        return "DiemThi{" +
                "maDiemThi=" + maDiemThi +
                ", maSinhVien=" + maSinhVien +
                ", maMonHoc=" + maMonHoc +
                ", diemCuoiKy=" + diemCuoiKy +
                ", diemGiuaKy=" + diemGiuaKy +
                ", diemChuyenCan=" + diemChuyenCan +
                '}';
    }

    public int getMaDiemThi() {
        return maDiemThi;
    }

    public void setMaDiemThi(int maDiemThi) {
        this.maDiemThi = maDiemThi;
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

    public Float getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(Float diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public Float getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(Float diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public Float getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public void setDiemChuyenCan(Float diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public DiemThi() {
    }

    public DiemThi(int maDiemThi, int maSinhVien, int maMonHoc, Float diemCuoiKy, Float diemGiuaKy, Float diemChuyenCan) {
        this.maDiemThi = maDiemThi;
        this.maSinhVien = maSinhVien;
        this.maMonHoc = maMonHoc;
        this.diemCuoiKy = diemCuoiKy;
        this.diemGiuaKy = diemGiuaKy;
        this.diemChuyenCan = diemChuyenCan;
    }
}

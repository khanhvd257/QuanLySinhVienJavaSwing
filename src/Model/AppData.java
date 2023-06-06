package Model;

public class AppData {
    private static GiangVien giangVien;
    private static SinhVien sinhVien;
    private static QuanLy quanLy;

    public static SinhVien getSinhVien() {
        return sinhVien;
    }

    public static void setSinhVien(SinhVien sinhVien) {
        AppData.sinhVien = sinhVien;
    }

    public static QuanLy getQuanLy() {
        return quanLy;
    }

    public static void setQuanLy(QuanLy quanLy) {
        AppData.quanLy = quanLy;
    }

    public static GiangVien getGiangVien() {
        return giangVien;
    }

    public static void setGiangVien(GiangVien giangVien) {
        AppData.giangVien = giangVien;
    }
}

package Model;

public class Khoa {
    private String tenKhoa;
    private int maKhoa;

    @Override
    public String toString() {
        return "Khoa{" +
                "tenKhoa='" + tenKhoa + '\'' +
                ", maKhoa=" + maKhoa +
                '}';
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public int getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(int maKhoa) {
        this.maKhoa = maKhoa;
    }

    public Khoa() {
    }

    public Khoa(String tenKhoa, int maKhoa) {
        this.tenKhoa = tenKhoa;
        this.maKhoa = maKhoa;
    }
}

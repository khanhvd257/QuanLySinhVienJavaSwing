package Model;

public class Lop {
    private String tenLop;
    private int maKhoa, malop;

    @Override
    public String toString() {
        return "Lop{" +
                "tenLop='" + tenLop + '\'' +
                ", maKhoa=" + maKhoa +
                ", malop=" + malop +
                '}';
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public int getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(int maKhoa) {
        this.maKhoa = maKhoa;
    }

    public int getMalop() {
        return malop;
    }

    public void setMalop(int malop) {
        this.malop = malop;
    }

    public Lop() {
    }

    public Lop(String tenLop, int maKhoa, int malop) {
        this.tenLop = tenLop;
        this.maKhoa = maKhoa;
        this.malop = malop;
    }
}

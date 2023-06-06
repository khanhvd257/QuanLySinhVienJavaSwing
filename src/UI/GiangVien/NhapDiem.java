/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.GiangVien;

import DAO.DiemThiDAO;
import DAO.LopDAO;
import DAO.MonDAO;
import Model.AppData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vankhanh
 */
public class NhapDiem extends javax.swing.JFrame {

    private int maMonSelected;
    private int maSinhVienSelected;
    DefaultTableModel tableModel = null;
    private DiemThiDAO diemThiDAO = null;
    private LopDAO lopDAO = null;
    private MonDAO monHocDAO = null;
    private String ACTION_STATUS = "ALL";

    public NhapDiem() {
        initComponents();
        diemThiDAO = new DiemThiDAO();
        lopDAO = new LopDAO();
        monHocDAO = new MonDAO();
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        initComboBox();
        initData();
        initAction();
        checkAction();
    }

    public void initAction() {
        cmbMonHoc.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    initData();
                }
            }
        });
        
        //Nut lưu thông tin Điểm thi
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
                    String chuyenCan = txtDiemChuyenCan.getText();
                    String giuaKy = txtDiemGiuaKy.getText();
                    String cuoiKy = txtDiemCuoiKy.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn nhâp điểm này không, nếu nhập sai không thể sửa", "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        diemThiDAO.nhapDiem(maMonSelected, maSinhVienSelected, chuyenCan, giuaKy, cuoiKy);
                        resetForm();
                        initData();
                    }
                }
//                if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
//                    String ten = txtTenmon.getText();
//                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn sửa " + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
//                    if (result == JOptionPane.YES_OPTION) {
//                        String soTin = txtSoTin.getText();
//                        if (diemThiDAO.sua(maMonSelected, ten, soTin)){
//                            JOptionPane.showMessageDialog(null, "Cập nhật thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                        }
//                        initData();
//                    }
//                }
                ACTION_STATUS = "ALL";
                // Hiện thị lại các nút action
                checkAction();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_STATUS = "ALL";
                checkAction();
            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        //nút load dữ liệu
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initData();
            }
        });

        // nút thêm
        btnNhapDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = tblBangDiem.getSelectedRow();
                if (selected >= 0) {
                    ACTION_STATUS = "ADD";
                    checkAction();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn sinh viên nhập điểm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        //Nut sua
//        btnSua.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = tblBangDiem.getSelectedRow();
//                if(selectedRow >=0){
//                    ACTION_STATUS = "EDIT";
//                    checkAction();
//                }else {
//                    JOptionPane.showMessageDialog(rootPane,"Chọn Dữ liệu cần sửa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                }
//
//            }
//        });
//        btnXoa.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int seleted = tblBangDiem.getSelectedRow();
//                if(seleted>=0){
//                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn xóa dữ liệu này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//                    if(result == JOptionPane.YES_OPTION){
//                        diemThiDAO.xoa(maMonSelected);
//                        initData();
//                    }
//                }else {
//                    JOptionPane.showMessageDialog(rootPane, "Chọn dữ liệu để xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                ResultSet monHocRS = diemThiDAO.findName(txtTimKiem.getText().trim());
                try {
                    while (monHocRS.next()) {
                        int maMon = monHocRS.getInt("id");
                        String ten = monHocRS.getString("ten");
                        String soTin = monHocRS.getString("soTinChi");
                        Object[] rowData = {maMon, ten, soTin};
                        tableModel.addRow(rowData);
                    }
                    monHocRS.close();
                    tblBangDiem.repaint();

                } catch (SQLException E) {
                    System.out.println(E.getMessage());
                }
            }
        });
        tblBangDiem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedR = tblBangDiem.getSelectedRow();
                if (selectedR >= 0) {
                    maMonSelected = Integer.parseInt(String.valueOf(tblBangDiem.getValueAt(selectedR, 0)));
                    maSinhVienSelected = Integer.parseInt(String.valueOf(tblBangDiem.getValueAt(selectedR, 2)));
                    String chuyenCan = String.valueOf(tblBangDiem.getValueAt(selectedR, 4));
                    String giuaKy = String.valueOf(tblBangDiem.getValueAt(selectedR, 5));
                    String cuoiKy = String.valueOf(tblBangDiem.getValueAt(selectedR, 6));
                    txtDiemChuyenCan.setText(chuyenCan);
                    txtDiemGiuaKy.setText(giuaKy);
                    txtDiemCuoiKy.setText(cuoiKy);
                }
            }
        });

    }

    private void resetForm() {
        txtDiemChuyenCan.setText("");
        txtDiemCuoiKy.setText("");
        txtDiemGiuaKy.setText("");
    }

    public void checkAction() {
        if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
            panDiem.setVisible(true);
            panAction.setVisible(false);
        } else if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
            panDiem.setVisible(true);
            panAction.setVisible(false);
        } else {
            panDiem.setVisible(false);
            panAction.setVisible(true);
        }
    }

    //lay dữ liệu lớp học và môn
    private void initComboBox() {

        cmbMonHoc.removeAllItems();
        int maGiangVien = AppData.getGiangVien().getMaGiangVien();
        ResultSet monRS = monHocDAO.LayMonDayTheoGiangVien(maGiangVien);
        try {
            while (monRS.next()) {
                String monHoc = monRS.getString("ten") + "_" + monRS.getInt("id");
                cmbMonHoc.addItem(monHoc);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                monRS.close();
            } catch (Exception E) {
                E.getMessage();
            }
        }
    }

    private void initData() {
        tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) tblBangDiem.getModel();
        tableModel.setRowCount(0);

        try {
            if (cmbMonHoc.getSelectedItem() != null) {
                String maMon = cmbMonHoc.getSelectedItem().toString().substring(cmbMonHoc.getSelectedItem().toString().indexOf("_") + 1).trim();
                int maGV = AppData.getGiangVien().getMaGiangVien();
                ResultSet monHocRS = diemThiDAO.layDanhSachDiemThiTheoGiangVien(maMon, maGV);
                while (monHocRS.next()) {
                    int maMonHoc = monHocRS.getInt("maMonHoc");
                    String tenMon = monHocRS.getString("tenMon");
                    String maSV = monHocRS.getString("maSinhVien");
                    String tenSV = monHocRS.getString("tenSV");
                    float diemChuyenCan = monHocRS.getFloat("diemChuyenCan");
                    float diemGiuaKy = monHocRS.getFloat("diemGiuaKy");
                    float diemCuoiKy = monHocRS.getFloat("diemCuoiKy");
                    int lanThi = monHocRS.getInt("lanThi");
                    String tinhTrang = monHocRS.getString("tinhTrang");
                    String diemChu = monHocRS.getString("diemChu");
                    Object[] rowData = {maMonHoc, tenMon, maSV, tenSV, diemChuyenCan, diemGiuaKy, diemCuoiKy, lanThi, tinhTrang, diemChu};
                    tableModel.addRow(rowData);
                }
                tblBangDiem.repaint();
                monHocRS.close();

            } else {
                JOptionPane.showMessageDialog(rootPane, "Không có lớp dạy học nào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException E) {
            System.out.println(E.getMessage());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmbMonHoc = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panAction = new javax.swing.JPanel();
        btnNhapDiem = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangDiem = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panDiem = new javax.swing.JPanel();
        txtDiemChuyenCan = new javax.swing.JTextField();
        txtDiemGiuaKy = new javax.swing.JTextField();
        txtDiemCuoiKy = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin về môn học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));

        cmbMonHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Môn học");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cmbMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        btnNhapDiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/add.png"))); // NOI18N
        btnNhapDiem.setText("Nhập điểm");

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/all.png"))); // NOI18N
        btnLoad.setText("Load");

        javax.swing.GroupLayout panActionLayout = new javax.swing.GroupLayout(panAction);
        panAction.setLayout(panActionLayout);
        panActionLayout.setHorizontalGroup(
            panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panActionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNhapDiem))
                    .addComponent(btnLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panActionLayout.setVerticalGroup(
            panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panActionLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(btnNhapDiem)
                .addGap(18, 18, 18)
                .addComponent(btnLoad)
                .addContainerGap(192, Short.MAX_VALUE))
        );

        tblBangDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã môn", "Môn học", "Mã SV", "Sinh Viên", "Đ.Chuyên cần", "Đ.Giữa Kỳ", "Đ.Cuối kỳ", "Lần thi", "Tình trạng ", "Điểm chữ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBangDiem);
        if (tblBangDiem.getColumnModel().getColumnCount() > 0) {
            tblBangDiem.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblBangDiem.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblBangDiem.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblBangDiem.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblBangDiem.getColumnModel().getColumn(5).setPreferredWidth(50);
            tblBangDiem.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblBangDiem.getColumnModel().getColumn(7).setPreferredWidth(30);
        }

        jLabel7.setText("Tên sinh viên");

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 802, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(panAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btnTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setText("QUẢN LÝ ĐIỂM");

        panDiem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin điểm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel4.setText("Chuyên cần");

        jLabel5.setText("Giữa kỳ");

        jLabel6.setText("Cuối kỳ");

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/save.png"))); // NOI18N
        btnLuu.setText("Lưu");

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/reset.png"))); // NOI18N
        btnReset.setText("Reset");

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/cancel.png"))); // NOI18N
        btnHuy.setText("Hủy");

        javax.swing.GroupLayout panDiemLayout = new javax.swing.GroupLayout(panDiem);
        panDiem.setLayout(panDiemLayout);
        panDiemLayout.setHorizontalGroup(
            panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDiemLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDiemChuyenCan, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiemGiuaKy, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiemCuoiKy, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panDiemLayout.setVerticalGroup(
            panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDiemLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panDiemLayout.createSequentialGroup()
                        .addComponent(btnLuu)
                        .addGap(2, 2, 2)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHuy))
                    .addGroup(panDiemLayout.createSequentialGroup()
                        .addGroup(panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiemChuyenCan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiemGiuaKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiemCuoiKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/back.png"))); // NOI18N
        btnTroVe.setText("Trở về");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98)
                                .addComponent(panDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(235, 235, 235)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTroVe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panDiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhapDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhapDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhapDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhapDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhapDiem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnNhapDiem;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JComboBox<String> cmbMonHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panAction;
    private javax.swing.JPanel panDiem;
    private javax.swing.JTable tblBangDiem;
    private javax.swing.JTextField txtDiemChuyenCan;
    private javax.swing.JTextField txtDiemCuoiKy;
    private javax.swing.JTextField txtDiemGiuaKy;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

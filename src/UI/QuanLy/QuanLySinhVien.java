/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.QuanLy;

import Connection.JdbcHelper;
import DAO.LopDAO;
import DAO.SinhVienDAO;
import Model.Lop;
import Model.SinhVien;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author vankhanh
 */
public class QuanLySinhVien extends javax.swing.JFrame {

    private SinhVienDAO sinhVienDAO = null;
    private int[] columnWidths = {100, 150, 150, 100, 200, 200, 150};
    DefaultTableModel tableModel = null;
    private ArrayList<SinhVien> arrSinhVien;
    private String ACTION_STATUS = "INSERT";
    private int MaSVSelected;

    /**
     * Creates new form TraCuuDiem
     */
    public QuanLySinhVien() {
        initComponents();
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        initData();
        initAction();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modalThemSinhVien = new javax.swing.JDialog();
        lblModalName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenSinhVien = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        cmbLop = new javax.swing.JComboBox<>();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        dptNgaySinh = new com.toedter.calendar.JDateChooser();
        btnHuy = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnSaveSv = new javax.swing.JButton();
        btnGrGioiTinh = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachSinhVien = new javax.swing.JTable();
        txtChuoiTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        modalThemSinhVien.setTitle("Thêm mới sinh viên");

        lblModalName.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lblModalName.setText("THÊM MỚI SINH VIÊN");

        jLabel4.setText("Tên sinh viên");

        jLabel5.setText("Địa chỉ");

        jLabel7.setText("Tên đăng nhập");

        jLabel8.setText("Ngày sinh");

        jLabel9.setText("Giới tính");

        jLabel10.setText("Lớp");

        txtSoDienThoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoDienThoaiActionPerformed(evt);
            }
        });

        jLabel11.setText("Số điện thoại");

        jLabel12.setText("Email");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        cmbLop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnGrGioiTinh.add(rdoNam);
        rdoNam.setText("Nam");
        rdoNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamActionPerformed(evt);
            }
        });

        btnGrGioiTinh.add(rdoNu);
        rdoNu.setText("Nữ");

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/cancel.png"))); // NOI18N
        btnHuy.setText("Hủy");

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/reset.png"))); // NOI18N
        btnReset.setText("Reset");

        btnSaveSv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/save.png"))); // NOI18N
        btnSaveSv.setText("Lưu");

        javax.swing.GroupLayout modalThemSinhVienLayout = new javax.swing.GroupLayout(modalThemSinhVien.getContentPane());
        modalThemSinhVien.getContentPane().setLayout(modalThemSinhVienLayout);
        modalThemSinhVienLayout.setHorizontalGroup(
            modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modalThemSinhVienLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modalThemSinhVienLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(dptNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modalThemSinhVienLayout.createSequentialGroup()
                        .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modalThemSinhVienLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtTenSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modalThemSinhVienLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblModalName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modalThemSinhVienLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modalThemSinhVienLayout.createSequentialGroup()
                        .addComponent(cmbLop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(67, 67, 67)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modalThemSinhVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modalThemSinhVienLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(27, 27, 27)
                        .addComponent(rdoNam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoNu)
                        .addGap(118, 118, 118))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modalThemSinhVienLayout.createSequentialGroup()
                        .addComponent(btnSaveSv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReset)
                        .addGap(12, 12, 12)
                        .addComponent(btnHuy)
                        .addGap(21, 21, 21))))
        );
        modalThemSinhVienLayout.setVerticalGroup(
            modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modalThemSinhVienLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblModalName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(modalThemSinhVienLayout.createSequentialGroup()
                        .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(dptNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(modalThemSinhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveSv)
                    .addComponent(btnReset)
                    .addComponent(btnHuy))
                .addGap(14, 14, 14))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setText("DANH SÁCH SINH VIÊN");

        tblDanhSachSinhVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Tên Sinh viên", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDanhSachSinhVien);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 23, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel2.setText("Tên Sinh viên");

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/all.png"))); // NOI18N
        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/back.png"))); // NOI18N
        btnTroVe.setText("Trở về");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(306, 306, 306))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtChuoiTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTroVe))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(btnTimKiem)
                            .addComponent(txtChuoiTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoad)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtSoDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoDienThoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoDienThoaiActionPerformed

    private void rdoNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNamActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
        initData();
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        String txtChuoiTimKiem = this.txtChuoiTimKiem.getText();
        arrSinhVien = sinhVienDAO.findStudentByName(txtChuoiTimKiem);
        DefaultTableModel tableModel = (DefaultTableModel) tblDanhSachSinhVien.getModel();
        tableModel.setRowCount(0);
        for (SinhVien sv : arrSinhVien) {
            Object[] rowData = {sv.getMaSV(), sv.getTenSinhVien(), sv.getUsername(), sv.getNgaySinh(), sv.getEmail(), sv.getDiaChi(), sv.getSoDienThoai()};
            tableModel.addRow(rowData);
        }

    }

    private void resetForm() {
        txtTenSinhVien.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        txtSoDienThoai.setText("");
        dptNgaySinh.cleanup();

    }

    private void initAction() {
        SinhVienDAO svDAO = new SinhVienDAO();
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_STATUS = "INSERT";
                modalThemSinhVien.setSize(800, 500);
                modalThemSinhVien.setTitle("Thêm mới sinh viên");
                lblModalName.setText("Thêm sinh viên");
                modalThemSinhVien.setVisible(true);
                initModal();
                resetForm();
            }
        });
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_STATUS = "EDIT";
                modalThemSinhVien.setSize(800, 500);
                modalThemSinhVien.setTitle("Sửa sinh viên");
                lblModalName.setText("Sửa sinh viên");
                modalThemSinhVien.setVisible(true);

            }
        });
        tblDanhSachSinhVien.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Xử lý sự kiện khi người dùng chọn một dòng trong table
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblDanhSachSinhVien.getSelectedRow();
                    if (selectedRow != -1) {
                        ResultSet resultSet = null;
                        try {
                            MaSVSelected = Integer.parseInt(tblDanhSachSinhVien.getValueAt(selectedRow, 0).toString());
                            String sql = "SELECT * FROM SinhVien WHERE id= ?";
                            resultSet = JdbcHelper.executeQuery(sql, MaSVSelected);
                            if (resultSet.next()) {
                                txtTenSinhVien.setText(resultSet.getString("ten"));
                                txtDiaChi.setText(resultSet.getString("diaChi"));
                                txtEmail.setText(resultSet.getString("email"));
                                txtUsername.setText(resultSet.getString("username"));
                                txtSoDienThoai.setText(resultSet.getString("soDienThoai"));
                                dptNgaySinh.setDate(resultSet.getDate("ngaySinh"));

                                if (resultSet.getString("gioiTinh").equalsIgnoreCase("Nam")) {
                                    rdoNam.setSelected(true);
                                } else {
                                    rdoNu.setSelected(true);
                                }
                                // Đọc các giá trị khác từ ResultSet và thiết lập cho các text field khác
                            }
                            initModal();
                            String maLop = resultSet.getString("maLop");
                            for (int i = 0; i < cmbLop.getItemCount(); i++) {
                                String cmbItem = cmbLop.getItemAt(i).toString();
                                if (maLop.equalsIgnoreCase(cmbItem.substring(0, cmbItem.indexOf("-")).trim())) {
                                    cmbLop.setSelectedIndex(i);
                                    break;
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                if (resultSet != null) {
                                    resultSet.close();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

            }

        });
        btnSaveSv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maLop = cmbLop.getSelectedItem().toString().substring(0, cmbLop.getSelectedItem().toString().indexOf("-"));
                String gioiTinh = "";
                if (rdoNam.isSelected()) {
                    gioiTinh = "Nam";
                } else {
                    gioiTinh = "Nữ";
                }
                boolean insertSuccess = false;
                if (ACTION_STATUS.equalsIgnoreCase("INSERT")) {
                    insertSuccess = svDAO.insertStudent(txtTenSinhVien.getText(), dptNgaySinh.getDate(), "Nữ",
                            txtDiaChi.getText(), maLop, txtEmail.getText(), txtSoDienThoai.getText(), txtUsername.getText());
                }else{
                    ACTION_STATUS = "EDIT";
                    insertSuccess = svDAO.updateSinhVien(MaSVSelected,txtTenSinhVien.getText(), dptNgaySinh.getDate(), "Nữ",
                            txtDiaChi.getText(), maLop, txtEmail.getText(), txtSoDienThoai.getText(), txtUsername.getText());
                }

                if (insertSuccess) {
                    modalThemSinhVien.dispose();
                    initData();
                    JOptionPane.showMessageDialog(rootPane, ACTION_STATUS +" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(rootPane, ACTION_STATUS +" thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblDanhSachSinhVien.getSelectedRow();
                if (selectedRow != -1) {
                    int studentID = (int) tblDanhSachSinhVien.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn xóa sinh viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        svDAO.deleteStudentById(studentID);
                        initData();
                        JOptionPane.showMessageDialog(rootPane, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }
        });

    }

    private void initModal() {
        cmbLop.removeAllItems();
        LopDAO lopDAO = new LopDAO();
        ArrayList<Lop> lop = lopDAO.getAllLop();
        for (Lop item : lop) {
            cmbLop.addItem(item.getMalop() + " - " + item.getTenLop());
        }

    }

    private void initData() {

        String[] cols = new String[]{
            "Mã SV", "Tên Sinh viên", "Tên đăng nhập", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại"};
        tableModel = (DefaultTableModel) tblDanhSachSinhVien.getModel();
        tableModel = new DefaultTableModel(cols, 0);
        sinhVienDAO = new SinhVienDAO();
        try {
            arrSinhVien = sinhVienDAO.getAllSinhVien();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableModel.setRowCount(0);
        for (SinhVien sv : arrSinhVien) {
            Object[] rowData = {sv.getMaSV(), sv.getTenSinhVien(), sv.getUsername(), sv.getNgaySinh(), sv.getEmail(), sv.getDiaChi(), sv.getSoDienThoai()};
            tableModel.addRow(rowData);
        }
        tblDanhSachSinhVien.setModel(tableModel);
        for (int i = 0; i < tblDanhSachSinhVien.getColumnModel().getColumnCount(); i++) {
            TableColumn column = tblDanhSachSinhVien.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        };
    }

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
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGrGioiTinh;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSaveSv;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cmbLop;
    private com.toedter.calendar.JDateChooser dptNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblModalName;
    private javax.swing.JDialog modalThemSinhVien;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblDanhSachSinhVien;
    private javax.swing.JTextField txtChuoiTimKiem;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenSinhVien;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}

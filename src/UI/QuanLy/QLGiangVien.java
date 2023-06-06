/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.QuanLy;

import DAO.GiangVienDAO;
import DAO.KhoaDAO;
import DAO.SinhVienDAO;
import Model.Khoa;
import Model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vankhanh
 */
public class QLGiangVien extends javax.swing.JFrame {

    private int MaGVSelected;
    DefaultTableModel tableModel = null;
    private GiangVienDAO giangVienDAO = null;
    private KhoaDAO khoaDAO = null;
    private String ACTION_STATUS = "ALL";

    public QLGiangVien() {
        initComponents();
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        giangVienDAO = new GiangVienDAO();
        initData();
        initAction();
        checkAction();
    }

    public void checkAction() {
        if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
            panThongTinGV.setVisible(true);
            txtUsername.setEnabled(true);
            panAction.setVisible(false);
        } else if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
            panThongTinGV.setVisible(true);
            txtUsername.setEnabled(false);
            panAction.setVisible(false);
        } else {
            panThongTinGV.setVisible(false);
            panAction.setVisible(true);
        }
    }

    public void initAction() {

        //Nut lưu thông tin Môn
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
                    String ten = txtTenGV.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn Thêm giảng viên " + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String sdt = txtSoDienThoai.getText();
                        String khoa = cmbKhoa.getSelectedItem().toString().substring(0, cmbKhoa.getSelectedItem().toString().indexOf("_")).trim();
                        String email = txtEmail.getText();
                        String username = txtUsername.getText();
                        boolean status =  giangVienDAO.themGiangVien(ten, email, khoa, sdt, username);
                        if(!status){
                            JOptionPane.showMessageDialog(rootPane, "Thêm mới thất bại", "Đã xảy ra lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                        initData();
                    }
                }
                if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
                    String ten = txtTenGV.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn Thêm giảng viên " + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String sdt = txtSoDienThoai.getText();
                        String khoa = cmbKhoa.getSelectedItem().toString().substring(0, cmbKhoa.getSelectedItem().toString().indexOf("_")).trim();
                        String email = txtEmail.getText();
                        if (giangVienDAO.suaGV(MaGVSelected, ten, email, khoa, sdt)){
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                        initData();
                    }
                }
                ACTION_STATUS ="ALL";
                // Hiện thị lại các nút action
                checkAction();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ACTION_STATUS="ALL";
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
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_STATUS = "ADD";
                checkAction();
                resetForm();
            }
        });

        //Nut sua
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblGiangVien.getSelectedRow();
                if(selectedRow >=0){
                    ACTION_STATUS = "EDIT";
                    checkAction();
                }else {
                    JOptionPane.showMessageDialog(rootPane,"Chọn giảng viên cần sửa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleted = tblGiangVien.getSelectedRow();
                if(seleted>=0){
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn xóa giảng viên này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        giangVienDAO.xoaGV(MaGVSelected);
                        initData();
                    }
                }else {
                    JOptionPane.showConfirmDialog(rootPane, "Chọn giảng viên để xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnTIm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                ResultSet giangVienRS = giangVienDAO.findName(txtTimkiem.getText().trim());
                try {
                    while (giangVienRS.next()) {
                        int maGV = giangVienRS.getInt("id");
                        String ten = giangVienRS.getString("ten");
                        String email = giangVienRS.getString("email");
                        String khoa = giangVienRS.getString("tenKhoa");
                        String sdt = giangVienRS.getString("soDienThoai");
                        String username = giangVienRS.getString("username");
                        Object[] rowData = {maGV, ten, sdt, khoa, email, username};
                        tableModel.addRow(rowData);
                    }
                    giangVienRS.close();
                    tblGiangVien.repaint();

                } catch (SQLException E) {
                    System.out.println(E.getMessage());
                }
            }
        });
        tblGiangVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblGiangVien.getSelectedRow();
                if (selectedRow >= 0) {
                    MaGVSelected = (int) tblGiangVien.getValueAt(selectedRow, 0);
                    String ten = (String) tblGiangVien.getValueAt(selectedRow, 1);
                    String sdt = (String) tblGiangVien.getValueAt(selectedRow, 2);
                    String khoa = (String) tblGiangVien.getValueAt(selectedRow, 3);
                    String email = (String) tblGiangVien.getValueAt(selectedRow, 4);
                    String username = (String) tblGiangVien.getValueAt(selectedRow, 5);
                    txtTenGV.setText(ten);
                    txtSoDienThoai.setText(sdt);
                    txtEmail.setText(email);
                    txtUsername.setText(username);
                    for (int i = 0; i < cmbKhoa.getItemCount(); i++) {
                        String cmbItem = cmbKhoa.getItemAt(i).toString();
                        if (khoa.equalsIgnoreCase(cmbItem.substring(cmbItem.indexOf("_") + 1).trim())) {
                            cmbKhoa.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
    }
    private void resetForm(){
        txtTenGV.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        txtUsername.setText("");
    }
    private void initData() {
        // Đổ dữ liệu cho combo box Khoa
        cmbKhoa.removeAllItems();
        khoaDAO = new KhoaDAO();
        ResultSet khoaRS = khoaDAO.getAll();
        try {
            while (khoaRS.next()) {
                String khoa = khoaRS.getInt("id") + "_" + khoaRS.getString("ten");
                cmbKhoa.addItem(khoa);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                khoaRS.close();
            } catch (Exception E) {
                E.getMessage();
            }
        }


        tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) tblGiangVien.getModel();
        tableModel.setRowCount(0);
        giangVienDAO = new GiangVienDAO();
        ResultSet giangVienRS = giangVienDAO.getAll();
        try {
            while (giangVienRS.next()) {
                int maGV = giangVienRS.getInt("id");
                String ten = giangVienRS.getString("ten");
                String email = giangVienRS.getString("email");
                String khoa = giangVienRS.getString("tenKhoa");
                String sdt = giangVienRS.getString("soDienThoai");
                String username = giangVienRS.getString("username");
                Object[] rowData = {maGV, ten, sdt, khoa, email, username};
                tableModel.addRow(rowData);
            }
            giangVienRS.close();
            tblGiangVien.repaint();

        } catch (SQLException E) {
            System.out.println(E.getMessage());
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        panThongTinGV = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenGV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        cmbKhoa = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGiangVien = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtTimkiem = new javax.swing.JTextField();
        btnTIm = new javax.swing.JButton();
        panAction = new javax.swing.JPanel();
        btnLoad = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("QUẢN LÝ GIẢNG VIÊN");

        panThongTinGV.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin giảng viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        panThongTinGV.setToolTipText("Thông tin giảng viên");
        panThongTinGV.setName(""); // NOI18N

        jLabel1.setText("Tên giản viên");

        jLabel3.setText("Số điện thoại");

        jLabel4.setText("Email");

        cmbKhoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Khoa");

        jLabel7.setText("Username");

        btnLuu.setText("Lưu");

        btnReset.setText("Reset");

        btnHuy.setText("Hủy");

        javax.swing.GroupLayout panThongTinGVLayout = new javax.swing.GroupLayout(panThongTinGV);
        panThongTinGV.setLayout(panThongTinGVLayout);
        panThongTinGVLayout.setHorizontalGroup(
            panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongTinGVLayout.createSequentialGroup()
                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panThongTinGVLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panThongTinGVLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panThongTinGVLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtTenGV, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panThongTinGVLayout.createSequentialGroup()
                                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3))
                                .addGap(20, 20, 20)
                                .addComponent(txtSoDienThoai)))))
                .addGap(61, 61, 61)
                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panThongTinGVLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cmbKhoa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panThongTinGVLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuu, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnReset, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHuy, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        panThongTinGVLayout.setVerticalGroup(
            panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongTinGVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panThongTinGVLayout.createSequentialGroup()
                        .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panThongTinGVLayout.createSequentialGroup()
                                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtTenGV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panThongTinGVLayout.createSequentialGroup()
                                .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panThongTinGVLayout.createSequentialGroup()
                                        .addGap(96, 96, 96)
                                        .addComponent(btnHuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panThongTinGVLayout.createSequentialGroup()
                                        .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(19, 19, 19)
                                        .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(48, 48, 48)))
                                .addGap(3, 3, 3)))
                        .addContainerGap())
                    .addGroup(panThongTinGVLayout.createSequentialGroup()
                        .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(19, 19, 19)
                        .addGroup(panThongTinGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))))
        );

        tblGiangVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã GV", "Tên GV", "Số điện thoại", "Khoa công tác", "Email", "Username"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblGiangVien);

        jLabel6.setText("Tìm theo tên");

        btnTIm.setText("Tìm");

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/all.png"))); // NOI18N
        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panActionLayout = new javax.swing.GroupLayout(panAction);
        panAction.setLayout(panActionLayout);
        panActionLayout.setHorizontalGroup(
            panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        panActionLayout.setVerticalGroup(
            panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panActionLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoad)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        btnTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/back.png"))); // NOI18N
        btnTroVe.setText("Trở về");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panThongTinGV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTIm)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTroVe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panThongTinGV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTIm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
        initData();
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

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
            java.util.logging.Logger.getLogger(QLGiangVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLGiangVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLGiangVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLGiangVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLGiangVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTIm;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cmbKhoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panAction;
    private javax.swing.JPanel panThongTinGV;
    private javax.swing.JTable tblGiangVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenGV;
    private javax.swing.JTextField txtTimkiem;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}

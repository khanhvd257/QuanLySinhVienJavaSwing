/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.QuanLy;

import DAO.GiangVienDAO;
import DAO.KhoaDAO;
import DAO.MonDAO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vankhanh
 */
public class QLMon extends javax.swing.JFrame {

    private int maMonSelected;
    DefaultTableModel tableModel = null;
    private MonDAO monHocDAO = null;
    private String ACTION_STATUS = "ALL";

    public QLMon() {
        initComponents();
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        monHocDAO = new MonDAO();
        initData();
        initAction();
        checkAction();
    }

    public void initAction() {

        //Nut lưu thông tin Môn
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
                    String ten = txtTenmon.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn Thêm môn" + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String soTin = txtSoTin.getText();
                        monHocDAO.them(ten, soTin);
                        initData();
                    }
                }
                if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
                    String ten = txtTenmon.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn sửa " + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String soTin = txtSoTin.getText();
                        if (monHocDAO.sua(maMonSelected, ten, soTin)) {
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                        initData();
                    }
                }
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
                int selectedRow = tblMonHoc.getSelectedRow();
                if (selectedRow >= 0) {
                    ACTION_STATUS = "EDIT";
                    checkAction();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Chọn Dữ liệu cần sửa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleted = tblMonHoc.getSelectedRow();
                if (seleted >= 0) {
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn xóa dữ liệu này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        monHocDAO.xoa(maMonSelected);
                        initData();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Chọn dữ liệu để xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnTimkiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                ResultSet monHocRS = monHocDAO.findName(txtTimKiem.getText().trim());
                try {
                    while (monHocRS.next()) {
                        int maMon = monHocRS.getInt("id");
                        String ten = monHocRS.getString("ten");
                        String soTin = monHocRS.getString("soTinChi");
                        Object[] rowData = {maMon, ten, soTin};
                        tableModel.addRow(rowData);
                    }
                    monHocRS.close();
                    tblMonHoc.repaint();

                } catch (SQLException E) {
                    System.out.println(E.getMessage());
                }
            }
        });
        btnThemGiangVienDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalGiangVienDay.show();
                modalGiangVienDay.setSize(500, 300);
                modalGiangVienDay.setTitle("GIẢNG VIÊN DẠY MÔN HỌC");
                modalGiangVienDay.setLocationRelativeTo(null);
                initComboxGiangVien();
            }
        });
        btnSaveGV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maGV = "";
                if (cmbGiangVien.getSelectedIndex() != -1) {
                    maGV = cmbGiangVien.getSelectedItem().toString().substring(cmbGiangVien.getSelectedItem().toString().indexOf("_") + 1);
                }
                boolean status = monHocDAO.themGiangVienDayHoc(maGV, String.valueOf(maMonSelected));
                if (!status) {
                    JOptionPane.showMessageDialog(rootPane, "Lỗi khi gán giảng viên cho môn học", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
                initComboxGiangVien();
            }
        });
        tblMonHoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedR = tblMonHoc.getSelectedRow();
                if (selectedR >= 0) {
                    maMonSelected = (int) tblMonHoc.getValueAt(selectedR, 0);
                    String ten = (String) tblMonHoc.getValueAt(selectedR, 1);
                    String soTin = (String) tblMonHoc.getValueAt(selectedR, 2);
                    txtTenmon.setText(ten);
                    txtSoTin.setText(soTin);
                    lblTenMonHoc.setText(ten);
                    lblSoTinChi.setText(soTin);
                }
            }

        });
    }

    private void resetForm() {
        txtSoTin.setText("");
        txtTenmon.setText("");
    }

    public void checkAction() {
        if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
            panThongTin.setVisible(true);
            panAction.setVisible(false);
        } else if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
            panThongTin.setVisible(true);
            panAction.setVisible(false);
        } else {
            panThongTin.setVisible(false);
            panAction.setVisible(true);
        }
    }

    private void initComboxGiangVien() {
        cmbGiangVien.removeAllItems();
        ResultSet GvienRS = monHocDAO.layGiangVienChuaDayTheoMon(maMonSelected);
        try {
            while (GvienRS.next()) {
                String tenGV = GvienRS.getString("ten");
                int maGV = GvienRS.getInt("id");
                cmbGiangVien.addItem(tenGV + "_" + maGV);
            }
            GvienRS.close();
        } catch (SQLException E) {
            E.getMessage();
        }
    }

    private void initData() {

        tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) tblMonHoc.getModel();
        tableModel.setRowCount(0);
        ResultSet monHocRS = monHocDAO.getAll();
        try {
            while (monHocRS.next()) {
                int maMon = monHocRS.getInt("id");
                String ten = monHocRS.getString("ten");
                String soTin = monHocRS.getString("soTinChi");
                Object[] rowData = {maMon, ten, soTin};
                tableModel.addRow(rowData);
            }
            monHocRS.close();
            tblMonHoc.repaint();

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

        popMenuMon = new javax.swing.JPopupMenu();
        mnuAddGiangVien = new javax.swing.JMenuItem();
        modalGiangVienDay = new javax.swing.JDialog();
        lblModalName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbGiangVien = new javax.swing.JComboBox<>();
        btnHuygV = new javax.swing.JButton();
        btnSaveGV = new javax.swing.JButton();
        lblSoTinChi = new javax.swing.JLabel();
        lblTenMonHoc = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panThongTin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenmon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSoTin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMonHoc = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimkiem = new javax.swing.JButton();
        panAction = new javax.swing.JPanel();
        btnThemGiangVienDay = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        mnuAddGiangVien.setText("Thêm giảng viên dạy");
        mnuAddGiangVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddGiangVienActionPerformed(evt);
            }
        });
        popMenuMon.add(mnuAddGiangVien);

        modalGiangVienDay.setTitle("Thêm mới sinh viên");

        lblModalName.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lblModalName.setText("Đăng ký giảng viên dạy");

        jLabel5.setText("Tên môn");

        jLabel10.setText("Giảng viên dạy");

        jLabel12.setText("Số tín chỉ");

        cmbGiangVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnHuygV.setText("Hủy");
        btnHuygV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuygVActionPerformed(evt);
            }
        });

        btnSaveGV.setText("Lưu");

        lblSoTinChi.setText("lblSoTinChi");

        lblTenMonHoc.setText("lblTenMon");

        javax.swing.GroupLayout modalGiangVienDayLayout = new javax.swing.GroupLayout(modalGiangVienDay.getContentPane());
        modalGiangVienDay.getContentPane().setLayout(modalGiangVienDayLayout);
        modalGiangVienDayLayout.setHorizontalGroup(
            modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modalGiangVienDayLayout.createSequentialGroup()
                .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modalGiangVienDayLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10))
                        .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modalGiangVienDayLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(modalGiangVienDayLayout.createSequentialGroup()
                                        .addComponent(btnSaveGV)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHuygV))))
                            .addGroup(modalGiangVienDayLayout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenMonHoc)
                                    .addComponent(lblSoTinChi)))))
                    .addGroup(modalGiangVienDayLayout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(lblModalName, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        modalGiangVienDayLayout.setVerticalGroup(
            modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modalGiangVienDayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblModalName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTenMonHoc))
                .addGap(18, 18, 18)
                .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblSoTinChi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(modalGiangVienDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuygV)
                    .addComponent(btnSaveGV))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 514));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel2.setText("QUẢN LÝ MÔN");

        panThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin môn học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));

        jLabel1.setText("Tên Môn");

        jLabel3.setText("Số tín chỉ");

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/cancel.png"))); // NOI18N
        btnHuy.setText("Hủy");

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/reset.png"))); // NOI18N
        btnReset.setText("Reset");

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/save.png"))); // NOI18N
        btnLuu.setText("Lưu");

        javax.swing.GroupLayout panThongTinLayout = new javax.swing.GroupLayout(panThongTin);
        panThongTin.setLayout(panThongTinLayout);
        panThongTinLayout.setHorizontalGroup(
            panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongTinLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(panThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenmon, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoTin, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnLuu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset)
                .addGap(12, 12, 12)
                .addComponent(btnHuy))
        );
        panThongTinLayout.setVerticalGroup(
            panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongTinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTenmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLuu)
                        .addComponent(btnReset)
                        .addComponent(btnHuy)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        tblMonHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Môn", "Tên môn", "Số tín chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMonHoc);

        jLabel6.setText("Tìm theo tên");

        btnTimkiem.setText("Tìm");

        btnThemGiangVienDay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/user.png"))); // NOI18N
        btnThemGiangVienDay.setText("Thêm Giảng viên dạy");
        btnThemGiangVienDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemGiangVienDayActionPerformed(evt);
            }
        });

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
            .addGroup(panActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemGiangVienDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panActionLayout.setVerticalGroup(
            panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panActionLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnThemGiangVienDay)
                .addGap(18, 18, 18)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoad)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/back.png"))); // NOI18N
        btnTroVe.setText("Trở về");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimkiem)
                .addGap(133, 133, 133))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(205, 205, 205)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTroVe)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimkiem))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuygVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuygVActionPerformed
        // TODO add your handling code here:
        modalGiangVienDay.hide();
    }//GEN-LAST:event_btnHuygVActionPerformed

    private void mnuAddGiangVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddGiangVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuAddGiangVienActionPerformed

    private void btnThemGiangVienDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemGiangVienDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemGiangVienDayActionPerformed

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
            java.util.logging.Logger.getLogger(QLMon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLMon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLMon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLMon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLMon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnHuygV;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSaveGV;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemGiangVienDay;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cmbGiangVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblModalName;
    private javax.swing.JLabel lblSoTinChi;
    private javax.swing.JLabel lblTenMonHoc;
    private javax.swing.JMenuItem mnuAddGiangVien;
    private javax.swing.JDialog modalGiangVienDay;
    private javax.swing.JPanel panAction;
    private javax.swing.JPanel panThongTin;
    private javax.swing.JPopupMenu popMenuMon;
    private javax.swing.JTable tblMonHoc;
    private javax.swing.JTextField txtSoTin;
    private javax.swing.JTextField txtTenmon;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

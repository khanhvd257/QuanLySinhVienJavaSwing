/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.SinhVien;

import DAO.DangKyHocDAO;
import DAO.MonDAO;
import Model.AppData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vankhanh
 */
public class DanKyMonHoc extends javax.swing.JFrame {

    private int maMonSelected;
    DefaultTableModel tableModel = null;
    private DangKyHocDAO dangKyHocDAO  = null;

    private String ACTION_STATUS = "ALL";
    public DanKyMonHoc() {
        initComponents();
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        dangKyHocDAO = new DangKyHocDAO();
        initData();
        initAction();
        checkAction();
    }
    public void initAction() {

        //Nut lưu thông tin DangKy Môn
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
                    String ten = lblTenMonHoc.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn đăng ký môn " + ten, "Xác nhận đăng ký", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        int maSV = AppData.getSinhVien().getMaSV();
                        String maGV = cmbGiangVien.getSelectedItem().toString().substring(cmbGiangVien.getSelectedItem().toString().indexOf("_") + 1).trim();
                        dangKyHocDAO.dangKyHoc(maSV, maGV,maMonSelected);
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
        //nút load dữ liệu
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initData();
            }
        });

        // nút thêm
        btnDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_STATUS = "ADD";
                checkAction();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                int maSV = AppData.getSinhVien().getMaSV();
                ResultSet monHocRS = dangKyHocDAO.findName(maSV,txtTimkiem.getText());
                try {
                    while (monHocRS.next()) {
                        int maMon = monHocRS.getInt("id");
                        String ten = monHocRS.getString("ten");
                        String soTin = monHocRS.getString("soTinChi");
                        Object[] rowData = {maMon, ten, soTin};
                        tableModel.addRow(rowData);
                    }
                    monHocRS.close();
                    tblMonDangKy.repaint();

                } catch (SQLException E) {
                    System.out.println(E.getMessage());
                }
            }
        });
        tblMonDangKy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedR = tblMonDangKy.getSelectedRow();
                if(selectedR>=0){
                    maMonSelected = (int) tblMonDangKy.getValueAt(selectedR, 0);
                    String ten = (String) tblMonDangKy.getValueAt(selectedR, 1);
                    String soTin = (String) tblMonDangKy.getValueAt(selectedR, 2);
                    lblTenMonHoc.setText(ten);
                    lblSoTinChi.setText(soTin);
                    initGV();
                }
            }
        });
    }
    private void initGV(){
        cmbGiangVien.removeAllItems();
        ResultSet giangVienRS = dangKyHocDAO.giangVienDayMon(maMonSelected);
        try {
            if (giangVienRS != null && giangVienRS.next()) {
                do {
                    String gv = giangVienRS.getString("ten") + "_" + giangVienRS.getInt("id");
                    cmbGiangVien.addItem(gv);
                } while (giangVienRS.next());
            } else {
                cmbGiangVien.addItem("Không có giảng viên dạy học");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                giangVienRS.close();
            } catch (Exception E) {
                E.getMessage();
            }
        }
    }
    public void checkAction() {
        if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
            modalDKMonHoc.setVisible(true);
            modalDKMonHoc.setSize(500,300);
            modalDKMonHoc.setTitle("Đăng kí môn");
            modalDKMonHoc.setLocationRelativeTo(null);
        } else if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {

        } else {
           modalDKMonHoc.setVisible(false);
        }
    }
    private void initData() {

        tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) tblMonDangKy.getModel();
        tableModel.setRowCount(0);
        int maSV = AppData.getSinhVien().getMaSV();
        ResultSet monHocRS = dangKyHocDAO.layTatCaMonChuaHoc(maSV);
        try {
            while (monHocRS.next()) {
                int maMon = monHocRS.getInt("id");
                String ten = monHocRS.getString("ten");
                String soTin = monHocRS.getString("soTinChi");
                Object[] rowData = {maMon, ten, soTin};
                tableModel.addRow(rowData);
            }
            monHocRS.close();
            tblMonDangKy.repaint();

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

        modalDKMonHoc = new javax.swing.JDialog();
        lblModalName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbGiangVien = new javax.swing.JComboBox<>();
        btnHuy = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        lblSoTinChi = new javax.swing.JLabel();
        lblTenMonHoc = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMonDangKy = new javax.swing.JTable();
        txtTimkiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        btnDangKy = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        modalDKMonHoc.setTitle("Thêm mới sinh viên");

        lblModalName.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lblModalName.setText("PHIẾU ĐĂNG KÝ MÔN HỌC");

        jLabel4.setText("Tên môn");

        jLabel10.setText("Giảng viên dạy");

        jLabel12.setText("Số tín chỉ");

        cmbGiangVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnSave.setText("Lưu");

        lblSoTinChi.setText("lblSoTinChi");

        lblTenMonHoc.setText("lblTenMon");

        javax.swing.GroupLayout modalDKMonHocLayout = new javax.swing.GroupLayout(modalDKMonHoc.getContentPane());
        modalDKMonHoc.getContentPane().setLayout(modalDKMonHocLayout);
        modalDKMonHocLayout.setHorizontalGroup(
            modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modalDKMonHocLayout.createSequentialGroup()
                .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modalDKMonHocLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10))
                        .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modalDKMonHocLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(modalDKMonHocLayout.createSequentialGroup()
                                        .addComponent(btnSave)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHuy))))
                            .addGroup(modalDKMonHocLayout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenMonHoc)
                                    .addComponent(lblSoTinChi)))))
                    .addGroup(modalDKMonHocLayout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(lblModalName, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        modalDKMonHocLayout.setVerticalGroup(
            modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modalDKMonHocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblModalName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTenMonHoc))
                .addGap(18, 18, 18)
                .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblSoTinChi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(modalDKMonHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnSave))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setText("Đăng Ký Môn Học");

        tblMonDangKy.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblMonDangKy);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jLabel2.setText("Tên môn học");

        btnSearch.setText("Tìm");

        btnDangKy.setText("Đăng ký");
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDangKy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnTroVe)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnSearch))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnDangKy)
                        .addGap(18, 18, 18)
                        .addComponent(btnLoad)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        modalDKMonHoc.hide();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDangKyActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoadActionPerformed

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
            java.util.logging.Logger.getLogger(DanKyMonHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanKyMonHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanKyMonHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanKyMonHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanKyMonHoc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangKy;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JComboBox<String> cmbGiangVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblModalName;
    private javax.swing.JLabel lblSoTinChi;
    private javax.swing.JLabel lblTenMonHoc;
    private javax.swing.JDialog modalDKMonHoc;
    private javax.swing.JTable tblMonDangKy;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.QuanLy;

import DAO.KhoaDAO;
import DAO.MonDAO;

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
public class QLKhoa extends javax.swing.JFrame {

    private int maKhoaSelected;
    DefaultTableModel tableModel = null;
    private KhoaDAO khoaDAO = null;
    private String ACTION_STATUS = "ALL";
    public QLKhoa() {
        initComponents();
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        khoaDAO = new KhoaDAO();
        initData();
        initAction();
        checkAction();
    }
    public void initAction() {

        //Nut lưu thông tin Khoa
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ACTION_STATUS.equalsIgnoreCase("ADD")) {
                    String ten = txtTenKhoa.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn Thêm môn" + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        khoaDAO.them(ten);
                        initData();
                    }
                }
                if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
                    String ten = txtTenKhoa.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn sửa " + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        if (khoaDAO.sua(maKhoaSelected, ten)){
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
                int selectedRow = tblKhoa.getSelectedRow();
                if(selectedRow >=0){
                    ACTION_STATUS = "EDIT";
                    checkAction();
                }else {
                    JOptionPane.showMessageDialog(rootPane,"Chọn Dữ liệu cần sửa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleted = tblKhoa.getSelectedRow();
                if(seleted>=0){
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn xóa dữ liệu này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        khoaDAO.xoa(maKhoaSelected);
                        initData();
                    }
                }else {
                    JOptionPane.showMessageDialog(rootPane, "Chọn dữ liệu để xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                ResultSet khoaRS = khoaDAO.findName(txtTimkiem.getText().trim());
                try {
                    while (khoaRS.next()) {
                        int maKhoa = khoaRS.getInt("id");
                        String ten = khoaRS.getString("ten");
                        Object[] rowData = {maKhoa, ten};
                        tableModel.addRow(rowData);
                    }
                    khoaRS.close();
                    tblKhoa.repaint();

                } catch (SQLException E) {
                    System.out.println(E.getMessage());
                }
            }
        });
        tblKhoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedR = tblKhoa.getSelectedRow();
                if(selectedR>=0){
                    maKhoaSelected = (int) tblKhoa.getValueAt(selectedR, 0);
                    String ten = (String) tblKhoa.getValueAt(selectedR, 1);
                    txtTenKhoa.setText(ten);
                }
            }
        });
    }
    private void resetForm(){
        txtTenKhoa.setText("");
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
    private void initData() {

        tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) tblKhoa.getModel();
        tableModel.setRowCount(0);
        ResultSet khoaRS = khoaDAO.getAll();
        try {
            while (khoaRS.next()) {
                int maKhoa = khoaRS.getInt("id");
                String ten = khoaRS.getString("ten");
                Object[] rowData = {maKhoa, ten};
                tableModel.addRow(rowData);
            }
            khoaRS.close();
            tblKhoa.repaint();

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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhoa = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtTimkiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        panAction = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        panThongTin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenKhoa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("QUẢN LÝ KHOA");

        tblKhoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã Khoa", "Tên Khoa"
            }
        ));
        jScrollPane1.setViewportView(tblKhoa);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel6.setText("Tìm theo tên");

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm");

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

        javax.swing.GroupLayout panActionLayout = new javax.swing.GroupLayout(panAction);
        panAction.setLayout(panActionLayout);
        panActionLayout.setHorizontalGroup(
            panActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
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
                .addContainerGap(142, Short.MAX_VALUE))
        );

        panThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin môn học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel1.setText("Tên Khoa");

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/save.png"))); // NOI18N
        btnLuu.setText("Lưu");

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/reset.png"))); // NOI18N
        btnReset.setText("Reset");

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/cancel.png"))); // NOI18N
        btnHuy.setText("Hủy");

        javax.swing.GroupLayout panThongTinLayout = new javax.swing.GroupLayout(panThongTin);
        panThongTin.setLayout(panThongTinLayout);
        panThongTinLayout.setHorizontalGroup(
            panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongTinLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(panThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65)
                .addComponent(btnLuu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset)
                .addGap(12, 12, 12)
                .addComponent(btnHuy)
                .addGap(21, 21, 21))
        );
        panThongTinLayout.setVerticalGroup(
            panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongTinLayout.createSequentialGroup()
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panThongTinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTenKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panThongTinLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLuu)
                            .addComponent(btnReset)
                            .addComponent(btnHuy))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
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
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(panThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(159, 159, 159)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(btnTimKiem)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
        initData();
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
            java.util.logging.Logger.getLogger(QLKhoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLKhoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLKhoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLKhoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLKhoa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panAction;
    private javax.swing.JPanel panThongTin;
    private javax.swing.JTable tblKhoa;
    private javax.swing.JTextField txtTenKhoa;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}

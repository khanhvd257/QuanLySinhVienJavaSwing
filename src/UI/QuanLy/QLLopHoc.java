/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.QuanLy;

import DAO.KhoaDAO;
import DAO.LopDAO;
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
public class QLLopHoc extends javax.swing.JFrame {

    private int maLopSelected;
    DefaultTableModel tableModel = null;
    private LopDAO lopDAO = null;
    private KhoaDAO khoaDAO = null;
    private String ACTION_STATUS = "ALL";
    public QLLopHoc() {
        initComponents();
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        lopDAO = new LopDAO();
        khoaDAO = new KhoaDAO();
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
                    String ten = txtTenLop.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn Thêm lớp " + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String khoa = cmbKhoa.getSelectedItem().toString().substring(0, cmbKhoa.getSelectedItem().toString().indexOf("_")).trim();
                        lopDAO.them(ten, khoa);
                        initData();
                    }
                }
                if (ACTION_STATUS.equalsIgnoreCase("EDIT")) {
                    String ten = txtTenLop.getText();
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn sửa " + ten, "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String khoa = cmbKhoa.getSelectedItem().toString().substring(0, cmbKhoa.getSelectedItem().toString().indexOf("_")).trim();
                        if (lopDAO.sua(maLopSelected, ten, khoa)){
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
                int selectedRow = tblLopHoc.getSelectedRow();
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
                int seleted = tblLopHoc.getSelectedRow();
                if(seleted>=0){
                    int result = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc chắn muốn xóa dữ liệu này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        lopDAO.xoa(maLopSelected);
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
                ResultSet monHocRS = lopDAO.findName(txtTimKiem.getText().trim());
                try {
                    while (monHocRS.next()) {
                        int maMon = monHocRS.getInt("id");
                        String ten = monHocRS.getString("ten");
                        String tenKhoa = monHocRS.getString("tenKhoa");
                        Object[] rowData = {maMon, ten, tenKhoa};
                        tableModel.addRow(rowData);
                    }
                    monHocRS.close();
                    tblLopHoc.repaint();

                } catch (SQLException E) {
                    System.out.println(E.getMessage());
                }
            }
        });
        tblLopHoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedR = tblLopHoc.getSelectedRow();
                if(selectedR>=0){
                    maLopSelected = (int) tblLopHoc.getValueAt(selectedR, 0);
                    String ten = (String) tblLopHoc.getValueAt(selectedR, 1);
                    String khoa = (String) tblLopHoc.getValueAt(selectedR, 2);
                    for (int i = 0; i < cmbKhoa.getItemCount(); i++) {
                        String cmbItem = cmbKhoa.getItemAt(i).toString();
                        if (khoa.equalsIgnoreCase(cmbItem.substring(cmbItem.indexOf("_") + 1).trim())) {
                            cmbKhoa.setSelectedIndex(i);
                            break;
                        }
                    }
                    txtTenLop.setText(ten);
                }
            }
        });
    }
    private void resetForm(){
        txtTenLop.setText("");
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
        cmbKhoa.removeAllItems();
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
        tableModel = (DefaultTableModel) tblLopHoc.getModel();
        tableModel.setRowCount(0);
        ResultSet monHocRS = lopDAO.getAll();
        try {
            while (monHocRS.next()) {
                int maMon = monHocRS.getInt("id");
                String ten = monHocRS.getString("ten");
                String tenKhoa = monHocRS.getString("tenKhoa");
                Object[] rowData = {maMon, ten, tenKhoa};
                tableModel.addRow(rowData);
            }
            monHocRS.close();
            tblLopHoc.repaint();

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
        panThongTin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenLop = new javax.swing.JTextField();
        cmbKhoa = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLopHoc = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        panAction = new javax.swing.JPanel();
        btnLoad = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("QUẢN LÝ LỚP");

        panThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin lớp học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel1.setText("Tên Lớp");

        cmbKhoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Khoa");

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
                .addGap(15, 15, 15)
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenLop, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cmbKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                .addComponent(btnLuu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset)
                .addGap(12, 12, 12)
                .addComponent(btnHuy)
                .addGap(40, 40, 40))
        );
        panThongTinLayout.setVerticalGroup(
            panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongTinLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTenLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
            .addGroup(panThongTinLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuu)
                    .addComponent(btnReset)
                    .addComponent(btnHuy))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblLopHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Lớp", "Tên Lớp", "Tên Khoa"
            }
        ));
        jScrollPane1.setViewportView(tblLopHoc);

        jLabel6.setText("Tìm theo tên");

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm");

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
                .addContainerGap(132, Short.MAX_VALUE))
        );

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
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(panThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(214, 214, 214)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(QLLopHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLLopHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLLopHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLLopHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLLopHoc().setVisible(true);
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
    private javax.swing.JComboBox<String> cmbKhoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panAction;
    private javax.swing.JPanel panThongTin;
    private javax.swing.JTable tblLopHoc;
    private javax.swing.JTextField txtTenLop;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

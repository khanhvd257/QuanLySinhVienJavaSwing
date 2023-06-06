/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import DAO.LoginDAO;
import DAO.SinhVienDAO;
import DAO.UserDao;
import Model.*;
import UI.GiangVien.TrangChuGV;
import UI.QuanLy.TrangChuQuanLy;
import UI.SinhVien.TrangChuSinhVien;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author User
 */
public class Login extends javax.swing.JFrame {

    LoginDAO loginDAO = null;

    public Login() {
        initComponents();
        this.loadSavedCredentials();
        loginDAO = new LoginDAO();
        setLocationRelativeTo(null);
        txtPassword.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                saveLoginInfo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saveLoginInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saveLoginInfo();
            }
        });
        txtUsername.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                saveLoginInfo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saveLoginInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saveLoginInfo();
            }
        });
    }

    private void checkLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        ResultSet loginRS = loginDAO.login(username, password);
        try {
            if (loginRS != null && loginRS.next()) {
                do {
                    JOptionPane.showMessageDialog(rootPane, "Đăng nhập thành công", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
                    int vaiTro = loginRS.getInt("vaiTro");
                    if (vaiTro == 0) {
                        // sinh vien
                        layThongTinSV(username);
                        System.out.println(AppData.getSinhVien());
                        TrangChuSinhVien trangChuSinhVien = new TrangChuSinhVien();
                        trangChuSinhVien.setLocationRelativeTo(null);
                        trangChuSinhVien.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        trangChuSinhVien.setVisible(true);
                        dispose();
                        return;
                    }
                    if (vaiTro == 1) {
                        //giang viên
                        layThongTinGV(username);
                        TrangChuGV trangChuGV = new TrangChuGV();
                        trangChuGV.setLocationRelativeTo(null);
                        trangChuGV.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        trangChuGV.setVisible(true);
                        dispose();
                        return;
                    }
                    if (vaiTro == 2) {
                        QuanLy ql = new QuanLy(username);
                        AppData.setQuanLy(ql);
                        TrangChuQuanLy trangChuQuanLy = new TrangChuQuanLy();
                        trangChuQuanLy.setVisible(true);
                        trangChuQuanLy.setLocationRelativeTo(null);
                        trangChuQuanLy.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        dispose();
                        return;
                    }
                } while (loginRS.next());
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sai thông tin đăng nhâp", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void layThongTinGV(String username) {
        ResultSet giangVienRs = loginDAO.layThongTinGV(username);

        try {
            while (giangVienRs.next()) {
                int id = giangVienRs.getInt("id");
                String ten = giangVienRs.getString("ten");
                String email = giangVienRs.getString("email");
                int maKhoa = giangVienRs.getInt("maKhoa");
                String sdt = giangVienRs.getString("soDienThoai");
                String Username = giangVienRs.getString("username");
                GiangVien gv = new GiangVien(ten, email, sdt, id, maKhoa);
                AppData.setGiangVien(gv);
            };
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void layThongTinSV(String username) {
        ResultSet sinhVienRS = loginDAO.layThongTinSV(username);

        try {
            while (sinhVienRS.next()) {
                int id = sinhVienRS.getInt("id");
                String ten = sinhVienRS.getString("ten");
                Date ngaySinh = sinhVienRS.getDate("ngaySinh");
                String gioiTinh = sinhVienRS.getString("gioiTinh");
                String diaChi = sinhVienRS.getString("diaChi");
                int maLop = sinhVienRS.getInt("maLop");
                String email = sinhVienRS.getString("email");
                String sdt = sinhVienRS.getString("soDienThoai");
                String Username = sinhVienRS.getString("username");
                SinhVien sv = new SinhVien(ten, diaChi, email, sdt, username, gioiTinh, id, maLop, ngaySinh);
                AppData.setSinhVien(sv);
            };
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void saveLoginInfo() {
        if(chk_saveLogin.isSelected()){
            Preferences prefs = Preferences.userNodeForPackage(Login.class);
            prefs.put("username", txtUsername.getText());
            prefs.put("password", txtPassword.getText());
            prefs.putBoolean("isSave", chk_saveLogin.isSelected());
        }else {
            clearSavedCredentials();
        }
    }

    private void clearSavedCredentials() {
        Preferences prefs = Preferences.userNodeForPackage(Login.class);
        prefs.remove("username");
        prefs.remove("password");
        prefs.remove("isSave");
    }

    private void loadSavedCredentials() {
        Preferences prefs = Preferences.userNodeForPackage(Login.class);
        txtPassword.setText(prefs.get("password", ""));
        txtUsername.setText(prefs.get("username", ""));
        chk_saveLogin.setSelected(prefs.getBoolean("isSave", false));

    }

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        chk_saveLogin = new javax.swing.JCheckBox();
        btnExit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("HỆ THỐNG ĐĂNG NHẬP");

        jLabel2.setText("Tên đăng nhập");

        jLabel3.setText("Mật khẩu");

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/loginbtn.png"))); // NOI18N
        btnLogin.setText("Đăng nhập");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        chk_saveLogin.setText("Lưu mật khẩu đăng nhập?");
        chk_saveLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_saveLoginActionPerformed(evt);
            }
        });

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/thoat.png"))); // NOI18N
        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/login.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chk_saveLogin)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnLogin))
                                .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(69, 69, 69))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chk_saveLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        checkLogin();

    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void chk_saveLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_saveLoginActionPerformed
        saveLoginInfo();
    }//GEN-LAST:event_chk_saveLoginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox chk_saveLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

}

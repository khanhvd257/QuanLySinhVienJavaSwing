/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.SinhVien;

import Model.AppData;
import UI.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vankhanh
 */
public class TrangChuSinhVien extends javax.swing.JFrame {

    /**
     * Creates new form TrangChu
     */
    public TrangChuSinhVien() {
        initComponents();
        hienThi();
        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn thoát khỏi chương trình không?", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                if (confirmed == JOptionPane.YES_OPTION) {
                    Login login = new Login();
                    login.setLocationRelativeTo(null);
                    login.show();
                    dispose();
                }
            }
        });
    }
    private void hienThi() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                Date currentDate = new Date();
                String currentTime = sdf.format(currentDate);
                lblThoiGian.setText(currentTime);
            }
        });
        timer.start();
        String tenSV = AppData.getSinhVien().getTenSinhVien();
        lblUser.setText("Xin chào, " + tenSV);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblThoiGian = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        traGiangVien = new javax.swing.JButton();
        btnTraCuuDiem = new javax.swing.JButton();
        btnDangKiMon = new javax.swing.JButton();
        btnTraHocPho = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(238, 208, 227));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel1.setText("HỆ THỐNG QUẢN LÝ SINH VIÊN");

        lblThoiGian.setBackground(new java.awt.Color(102, 255, 51));
        lblThoiGian.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lblThoiGian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/clock.png"))); // NOI18N

        lblUser.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 0, 0));
        lblUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/user.png"))); // NOI18N
        lblUser.setText("Xin chào, user");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser)
                    .addComponent(lblThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(215, 215, 215))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Trang chủ sinh viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP));

        traGiangVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/banner/SVTraGiangVien.png"))); // NOI18N
        traGiangVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        traGiangVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traGiangVienActionPerformed(evt);
            }
        });

        btnTraCuuDiem.setBackground(new java.awt.Color(51, 255, 51));
        btnTraCuuDiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/banner/SVTraDiem.png"))); // NOI18N
        btnTraCuuDiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTraCuuDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuDiemActionPerformed(evt);
            }
        });

        btnDangKiMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/banner/SVDangKyHoc.png"))); // NOI18N
        btnDangKiMon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDangKiMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKiMonActionPerformed(evt);
            }
        });

        btnTraHocPho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/banner/SCTraCuuHocPhi.png"))); // NOI18N
        btnTraHocPho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTraHocPho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraHocPhoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTraHocPho, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTraCuuDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDangKiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(traGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTraCuuDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(traGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTraHocPho, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDangKiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/public/icon/exit.png"))); // NOI18N
        btnThoat.setText("Thoát");

        jMenu1.setText("Thông tin sinh viên");

        jMenuItem1.setText("Sửa thông tin ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Đăng xuất");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThoat))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 40, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThoat)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTraCuuDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuDiemActionPerformed
        TraCuuDiem traCuuDiem = new TraCuuDiem();
        traCuuDiem.setLocationRelativeTo(null);
        traCuuDiem.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        traCuuDiem.show();


    }//GEN-LAST:event_btnTraCuuDiemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnDangKiMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKiMonActionPerformed
        DanKyMonHoc danKyMonHoc = new DanKyMonHoc();
        danKyMonHoc.setLocationRelativeTo(null);
        danKyMonHoc.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        danKyMonHoc.setVisible(true);


    }//GEN-LAST:event_btnDangKiMonActionPerformed

    private void btnTraHocPhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraHocPhoActionPerformed
        TraCuuHocPhi hp = new TraCuuHocPhi();
        hp.setLocationRelativeTo(null);
        hp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        hp.show();
    }//GEN-LAST:event_btnTraHocPhoActionPerformed

    private void traGiangVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_traGiangVienActionPerformed

        TraCuuGiangVien gv = new TraCuuGiangVien();
        gv.setLocationRelativeTo(null);
        gv.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gv.show();
    }//GEN-LAST:event_traGiangVienActionPerformed

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
            java.util.logging.Logger.getLogger(TrangChuSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChuSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChuSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChuSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChuSinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangKiMon;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTraCuuDiem;
    private javax.swing.JButton btnTraHocPho;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblThoiGian;
    private javax.swing.JLabel lblUser;
    private javax.swing.JButton traGiangVien;
    // End of variables declaration//GEN-END:variables
}

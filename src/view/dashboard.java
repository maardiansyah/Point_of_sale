/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.util.HashMap;
import java.util.Map;
import laporan.lapoBarang;
import laporan.lapoPenjualan;
import laporan.lapoStok;

/**
 *
 * @author aldia
 */
public class dashboard extends javax.swing.JFrame {
    
    public static String levelUser;
    Map param = new HashMap();

    /**
     * Creates new form dashboard
     */
    public dashboard() {
        initComponents();
        loginGagal();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        mHome = new javax.swing.JMenu();
        smLogin = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        mBarang = new javax.swing.JMenu();
        katBar = new javax.swing.JMenuItem();
        datBar = new javax.swing.JMenuItem();
        sBar = new javax.swing.JMenuItem();
        mSuplier = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        mTransaksi = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        mLaporan = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        mUser = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Point Of Sale");
        setBackground(new java.awt.Color(102, 0, 102));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 779, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(102, 0, 102));

        mHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/login.png"))); // NOI18N
        mHome.setText("Home");

        smLogin.setText("Login");
        smLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smLoginActionPerformed(evt);
            }
        });
        mHome.add(smLogin);

        jMenuItem9.setText("Keluar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        mHome.add(jMenuItem9);

        jMenuBar1.add(mHome);

        mBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/barang.png"))); // NOI18N
        mBarang.setText("Barang");

        katBar.setText("Kategori Barang");
        katBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                katBarActionPerformed(evt);
            }
        });
        mBarang.add(katBar);

        datBar.setText("Data Barang");
        datBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datBarActionPerformed(evt);
            }
        });
        mBarang.add(datBar);

        sBar.setText("Stok Barang");
        sBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sBarActionPerformed(evt);
            }
        });
        mBarang.add(sBar);

        jMenuBar1.add(mBarang);

        mSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/suplier.png"))); // NOI18N
        mSuplier.setText("Supplier");

        jMenuItem5.setText("Data Supplier");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        mSuplier.add(jMenuItem5);

        jMenuBar1.add(mSuplier);

        mTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/transaksi.png"))); // NOI18N
        mTransaksi.setText("Transaksi");

        jMenuItem10.setText("Transaksi");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        mTransaksi.add(jMenuItem10);

        jMenuBar1.add(mTransaksi);

        mLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/laporan.png"))); // NOI18N
        mLaporan.setText("Laporan");
        mLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mLaporanActionPerformed(evt);
            }
        });

        jMenuItem6.setText("Laporan Penjualan");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        mLaporan.add(jMenuItem6);

        jMenuItem7.setText("Laporan Stok");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        mLaporan.add(jMenuItem7);

        jMenuItem8.setText("Laporan Barang");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        mLaporan.add(jMenuItem8);

        jMenuBar1.add(mLaporan);

        mUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/user.png"))); // NOI18N
        mUser.setText("User");

        jMenuItem11.setText("Kelola User");
        mUser.add(jMenuItem11);

        jMenuItem12.setText("Laporan User");
        mUser.add(jMenuItem12);

        jMenuBar1.add(mUser);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void katBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_katBarActionPerformed
        // TODO add your handling code here:
        katBarang katbar = new katBarang();
        katbar.setVisible(true);
    }//GEN-LAST:event_katBarActionPerformed

    private void sBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sBarActionPerformed
        // TODO add your handling code here:
        dataStok sbar = new dataStok();
        sbar.setVisible(true);
    }//GEN-LAST:event_sBarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        setExtendedState(dashboard.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowOpened

    private void smLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smLoginActionPerformed
        // TODO add your handling code here:
        if("Login".equals(dashboard.smLogin.getText())){
            login login = new login();
            login.setVisible(true);
        } else {
            dashboard.loginGagal();
        }
    }//GEN-LAST:event_smLoginActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void datBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datBarActionPerformed
        // TODO add your handling code here:
        dataBarang datbar = new dataBarang();
        datbar.setVisible(true);
    }//GEN-LAST:event_datBarActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        supplier sup = new supplier();
        sup.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        transaksi trans = new transaksi();
        trans.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void mLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mLaporanActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_mLaporanActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        lapoPenjualan lapJul = new lapoPenjualan();
        lapJul.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        lapoStok lStok = new lapoStok();
        lStok.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        lapoBarang lapBar = new lapoBarang();
        lapBar.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

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
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JMenuItem datBar;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private static javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private static javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private static javax.swing.JMenuItem katBar;
    private static javax.swing.JMenu mBarang;
    private javax.swing.JMenu mHome;
    private static javax.swing.JMenu mLaporan;
    private static javax.swing.JMenu mSuplier;
    private static javax.swing.JMenu mTransaksi;
    private static javax.swing.JMenu mUser;
    private static javax.swing.JMenuItem sBar;
    private static javax.swing.JMenuItem smLogin;
    // End of variables declaration//GEN-END:variables

    public static void loginGagal(){
        mBarang.setVisible(false);
        mLaporan.setVisible(false);
        mSuplier.setVisible(false);
        mTransaksi.setVisible(false);
        mUser.setVisible(false);
        smLogin.setText("Login");
        
    }
    
    public static void loginSukses(){
        mTransaksi.setVisible(true);
        mSuplier.setVisible(true);
        
        if(levelUser.equals("admin")){
            mLaporan.setVisible(true);
            mBarang.setVisible(true);
            datBar.setVisible(true);
            katBar.setVisible(true);
            
        }
        if(levelUser.equals("owner")){
            mUser.setVisible(true);
            mLaporan.setVisible(true);
            mBarang.setVisible(true);
            mTransaksi.setVisible(true);
            mSuplier.setVisible(true);
        } smLogin.setText("Logout");
    }
}
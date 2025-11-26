/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Manajemen;

import Main.JFrame_Login;
import Main.ThreadPoolManager;
import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class JFrame_Main_Manajemen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFrame_Main_Manajemen.class.getName());
    
    // Atribut
    private String namaLengkapManajemen = "Nama Default Manajemen"; // Default value
        
    // Icon
    private final ImageIcon clinicIcon = new ImageIcon(getClass().getResource("/gambarKlinik.png"));
    
    // Constructor Default
    public JFrame_Main_Manajemen() {
        initComponents(); 
        
        // Memusatkan dan memaksimalkan window
        this.setLocationRelativeTo(null); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Menjadwalkan update gambar setelah ukuran tombol tersedia
        SwingUtilities.invokeLater(() -> {
            // Sesuaikan ukuran gambar
            labelIconKlinik.setIcon(new ImageIcon(clinicIcon.getImage().getScaledInstance(labelIconKlinik.getWidth(), labelIconKlinik.getHeight(), Image.SCALE_SMOOTH)));
        });
        
        // Inisialisasi semua panel halaman
        JPanel_Dashboard_Manajemen panelDashboard = new JPanel_Dashboard_Manajemen(this.namaLengkapManajemen);
        JPanel_Laporan panelLaporan = new JPanel_Laporan();
        JPanel_Manajemen_User panelUser = new JPanel_Manajemen_User();
        JPanel_Manajemen_Dokter panelDokter = new JPanel_Manajemen_Dokter();

        // Tambahkan panel-panel tersebut ke panelContent dengan nama unik (key) untuk memanggil panel itu
        panelContent.add(panelDashboard, "cardDashboard");
        panelContent.add(panelLaporan, "cardLaporan");
        panelContent.add(panelUser, "cardUser");
        panelContent.add(panelDokter, "cardDokter");

        // Tampilkan panel pertama (dashboard) saat aplikasi dibuka
        CardLayout cl = (CardLayout) panelContent.getLayout();
        cl.show(panelContent, "cardDashboard");
    }
    
    // Constructor Login
    public JFrame_Main_Manajemen(int userId, String namaLengkap) {
        // Panggil constructor default (yang akan menjalankan initComponents dan membuat Panel Dashboard)
        this(); 
        
        // Simpan nama ke variabel global SEBELUM memanggil initComponents
        this.namaLengkapManajemen = namaLengkap;
        
        // 3. Update Label Sidebar (jika ada)
        // lblNamaManajemen.setText(namaLengkap); 
        
        // Cara Paling Aman (Re-add Dashboard):
        JPanel_Dashboard_Manajemen panelDashboardBaru = new JPanel_Dashboard_Manajemen(namaLengkap);
        // Menambahkan panel baru dengan key yang SAMA ("cardDashboard") akan menimpa panel lama
        panelContent.add(panelDashboardBaru, "cardDashboard");
        
        // Refresh tampilan
        CardLayout cl = (CardLayout) panelContent.getLayout();
        cl.show(panelContent, "cardDashboard");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelSidebar = new javax.swing.JPanel();
        labelJudulKlinik = new javax.swing.JLabel();
        labelIconKlinik = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnDashboard = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnManajemenUser = new javax.swing.JButton();
        btnManajemenDokter = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Dashboard - Sistem Informasi Klinik");

        jScrollPane1.setBorder(null);

        panelSidebar.setBackground(new java.awt.Color(34, 40, 49));
        panelSidebar.setPreferredSize(new java.awt.Dimension(250, 389));

        labelJudulKlinik.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        labelJudulKlinik.setForeground(new java.awt.Color(255, 255, 255));
        labelJudulKlinik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelJudulKlinik.setText("<html>KLINIK<br>SEHAT SELALU</html>");

        labelIconKlinik.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        labelIconKlinik.setForeground(new java.awt.Color(255, 255, 255));
        labelIconKlinik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelIconKlinik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarKlinik.png"))); // NOI18N

        btnDashboard.setBackground(new java.awt.Color(255, 255, 255));
        btnDashboard.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnDashboard.setForeground(java.awt.Color.white);
        btnDashboard.setText("🏠  Dashboard");
        btnDashboard.setContentAreaFilled(false);
        btnDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDashboardMouseExited(evt);
            }
        });
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        btnLaporan.setBackground(new java.awt.Color(255, 255, 255));
        btnLaporan.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnLaporan.setForeground(java.awt.Color.white);
        btnLaporan.setText("📊   Laporan");
        btnLaporan.setBorderPainted(false);
        btnLaporan.setContentAreaFilled(false);
        btnLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLaporan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLaporanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLaporanMouseExited(evt);
            }
        });
        btnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanActionPerformed(evt);
            }
        });

        btnManajemenUser.setBackground(new java.awt.Color(255, 255, 255));
        btnManajemenUser.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnManajemenUser.setForeground(java.awt.Color.white);
        btnManajemenUser.setText("👥  Manajemen User");
        btnManajemenUser.setBorderPainted(false);
        btnManajemenUser.setContentAreaFilled(false);
        btnManajemenUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManajemenUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnManajemenUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnManajemenUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnManajemenUserMouseExited(evt);
            }
        });
        btnManajemenUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManajemenUserActionPerformed(evt);
            }
        });

        btnManajemenDokter.setBackground(new java.awt.Color(255, 255, 255));
        btnManajemenDokter.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnManajemenDokter.setForeground(java.awt.Color.white);
        btnManajemenDokter.setText("️👨‍  Manajemen Dokter");
        btnManajemenDokter.setBorderPainted(false);
        btnManajemenDokter.setContentAreaFilled(false);
        btnManajemenDokter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManajemenDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnManajemenDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnManajemenDokterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnManajemenDokterMouseExited(evt);
            }
        });
        btnManajemenDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManajemenDokterActionPerformed(evt);
            }
        });

        btn_logout.setBackground(new java.awt.Color(220, 53, 69));
        btn_logout.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setText("[➜  Logout");
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSidebarLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(panelSidebarLayout);
        panelSidebarLayout.setHorizontalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSidebarLayout.createSequentialGroup()
                        .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSidebarLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelSidebarLayout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(btn_logout))
                                    .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnManajemenUser, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnManajemenDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelSidebarLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(labelIconKlinik, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelJudulKlinik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        panelSidebarLayout.setVerticalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelIconKlinik, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelJudulKlinik, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnManajemenUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnManajemenDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jScrollPane1.setViewportView(panelSidebar);

        jScrollPane2.setBorder(null);

        panelContent.setBackground(new java.awt.Color(255, 255, 255));
        panelContent.setLayout(new java.awt.CardLayout());
        jScrollPane2.setViewportView(panelContent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // Ambil CardLayout dari panelContent
        CardLayout cl = (CardLayout) panelContent.getLayout();
        // Tampilkan "kartu" dengan nama "cardDashboard"
        cl.show(panelContent, "cardDashboard");
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnManajemenDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManajemenDokterActionPerformed
        CardLayout cl = (CardLayout) panelContent.getLayout();
        cl.show(panelContent, "cardDokter");
    }//GEN-LAST:event_btnManajemenDokterActionPerformed

    private void btnManajemenUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManajemenUserActionPerformed
        CardLayout cl = (CardLayout) panelContent.getLayout();
        cl.show(panelContent, "cardUser");
    }//GEN-LAST:event_btnManajemenUserActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
        CardLayout cl = (CardLayout) panelContent.getLayout();
        cl.show(panelContent, "cardLaporan");
    }//GEN-LAST:event_btnLaporanActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        // Tampilkan konfirmasi
        int pilihan = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin logout?", 
                "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

        if (pilihan == JOptionPane.YES_OPTION) {
            ThreadPoolManager.getInstance().shutdown();
            new JFrame_Login().setVisible(true);
            this.dispose(); 
        }
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btnDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseEntered
        btnDashboard.setForeground(new java.awt.Color(50,120,220));
    }//GEN-LAST:event_btnDashboardMouseEntered

    private void btnDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseExited
        btnDashboard.setForeground(java.awt.Color.white);
    }//GEN-LAST:event_btnDashboardMouseExited

    private void btnLaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseEntered
        btnLaporan.setForeground(new java.awt.Color(50,120,220));
    }//GEN-LAST:event_btnLaporanMouseEntered

    private void btnLaporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseExited
        btnLaporan.setForeground(java.awt.Color.white);
    }//GEN-LAST:event_btnLaporanMouseExited

    private void btnManajemenUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManajemenUserMouseEntered
        btnManajemenUser.setForeground(new java.awt.Color(50,120,220));
    }//GEN-LAST:event_btnManajemenUserMouseEntered

    private void btnManajemenUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManajemenUserMouseExited
        btnManajemenUser.setForeground(java.awt.Color.white);
    }//GEN-LAST:event_btnManajemenUserMouseExited

    private void btnManajemenDokterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManajemenDokterMouseEntered
        btnManajemenDokter.setForeground(new java.awt.Color(50,120,220));
    }//GEN-LAST:event_btnManajemenDokterMouseEntered

    private void btnManajemenDokterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManajemenDokterMouseExited
        btnManajemenDokter.setForeground(java.awt.Color.white);
    }//GEN-LAST:event_btnManajemenDokterMouseExited
    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JFrame_Main_Manajemen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnManajemenDokter;
    private javax.swing.JButton btnManajemenUser;
    private javax.swing.JButton btn_logout;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelIconKlinik;
    private javax.swing.JLabel labelJudulKlinik;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelSidebar;
    // End of variables declaration//GEN-END:variables
}

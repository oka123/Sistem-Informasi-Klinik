/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Manajemen;

import Database.KoneksiDatabase;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class JPanel_Dashboard_Manajemen extends javax.swing.JPanel {
    
    // Deklarasitribut
    KoneksiDatabase koneksi;
    Connection conn;
    
    // Icon
    private final ImageIcon kunjunganIcon = new ImageIcon(getClass().getResource("/Kunjungan.png"));
    private final ImageIcon pendapatanIcon = new ImageIcon(getClass().getResource("/pendapatan.png"));
    private final ImageIcon patientIcon = new ImageIcon(getClass().getResource("/patient.png"));
    
    // Constructor
    public JPanel_Dashboard_Manajemen(String namaManajemen) { 
        initComponents();
        
        // Set Teks Selamat Datang dengan Nama
        if (namaManajemen != null && !namaManajemen.isEmpty()) {
            lblWelcome.setText("Selamat datang kembali, " + namaManajemen + "! Ini adalah ringkasan klinik Anda hari ini.");
        } else {
            lblWelcome.setText("Selamat datang kembali, Super Admin! Ini adalah ringkasan klinik Anda hari ini.");
        }
        
        // Kode Icon (Tetap sama)
        SwingUtilities.invokeLater(() -> {
            setResizedIcon(lblIconKunjungan, kunjunganIcon);
            setResizedIcon(lblIconPendapatan, pendapatanIcon);
            setResizedIcon(lblIconPasien, patientIcon);
        });
        
        loadDashboardData();
    }
          
    // Helper method untuk resize icon agar kode lebih rapi
    private void setResizedIcon(javax.swing.JLabel label, ImageIcon icon) {
        if (label.getWidth() > 0 && label.getHeight() > 0) {
            label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(
                label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH)));
        }
    }
    
    public void loadDashboardData() {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
        refreshButton.setEnabled(false);
        // Ubah teks label jadi indikator loading
        lblJumlahKunjungan.setText("...");
        lblTotalPendapatan.setText("...");
        lblJumlahPasien.setText("...");
        
        // 2. PROSES BACKGROUND (Thread Sederhana)
        // Kita pakai "new Thread" agar UI bisa update teks "..." tadi dulu
        new Thread(new Runnable() {
            @Override
            public void run() {
                // --- KODE DATABASE (Sama seperti sebelumnya) ---
                KoneksiDatabase koneksi = new KoneksiDatabase();
                try (Connection conn = koneksi.getConnection()) {
                    if (conn != null) {
                        // Variabel penampung hasil
                        String txtKunjungan = "0";
                        String txtPendapatan = "Rp 0";
                        String txtPasien = "0";

                        // A. Hitung Kunjungan
                        String sqlKunjungan = "SELECT COUNT(*) AS total FROM kunjungan WHERE DATE(tanggal_kunjungan) = CURDATE()";
                        try (PreparedStatement ps = conn.prepareStatement(sqlKunjungan); ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) txtKunjungan = String.valueOf(rs.getLong("total"));
                        }

                        // B. Hitung Pendapatan
                        String sqlPendapatan = "SELECT SUM(total_bayar) AS total FROM pembayaran WHERE DATE(tanggal_bayar) = CURDATE()";
                        try (PreparedStatement ps = conn.prepareStatement(sqlPendapatan); ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) txtPendapatan = formatRupiah(rs.getDouble("total"));
                        }

                        // C. Hitung Pasien
                        String sqlPasien = "SELECT COUNT(*) AS total FROM pasien";
                        try (PreparedStatement ps = conn.prepareStatement(sqlPasien); ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) txtPasien = String.valueOf(rs.getLong("total"));
                        }
                        
                        // --- 3. UPDATE UI (SELESAI) ---
                        // Karena kita di dalam Thread, kita harus pakai SwingUtilities untuk update GUI
                        String finalKunjungan = txtKunjungan;
                        String finalPendapatan = txtPendapatan;
                        String finalPasien = txtPasien;
                        
                        SwingUtilities.invokeLater(() -> {
                            lblJumlahKunjungan.setText(finalKunjungan);
                            lblTotalPendapatan.setText(finalPendapatan);
                            lblJumlahPasien.setText(finalPasien);
                            
                            // Balikin Kursor ke Normal
                            setCursor(java.awt.Cursor.getDefaultCursor());
                            refreshButton.setEnabled(true);
                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Jika error, balikin kursor juga
                    SwingUtilities.invokeLater(() -> { 
                        setCursor(java.awt.Cursor.getDefaultCursor());
                        JOptionPane.showMessageDialog(null, "Gagal memuat data dashboard: " + e.getMessage());
                    });
                }
            }
        }).start();
//        koneksi = new KoneksiDatabase();
//        
//        try {
//            conn = koneksi.getConnection();
//            if (conn != null) {
//                // Hitung Kunjungan Hari Ini
//                // Asumsi: tabel 'kunjungan', kolom 'tanggal_kunjungan'
//                String sqlKunjungan = "SELECT COUNT(*) AS total FROM kunjungan WHERE DATE(tanggal_kunjungan) = CURDATE()";
//                setLabelFromQuery(conn, sqlKunjungan, lblJumlahKunjungan, false);
//
//                // Hitung Total Pendapatan Hari Ini
//                // Asumsi: tabel 'pembayaran', kolom 'total_bayar', 'tanggal_bayar'
//                String sqlPendapatan = "SELECT SUM(total_bayar) AS total FROM pembayaran WHERE DATE(tanggal_bayar) = CURDATE()";
//                setLabelFromQuery(conn, sqlPendapatan, lblTotalPendapatan, true);
//
//                // Hitung Total Pasien Terdaftar
//                // Asumsi: tabel 'pasien'
//                String sqlPasien = "SELECT COUNT(*) AS total FROM pasien";
//                setLabelFromQuery(conn, sqlPasien, lblJumlahPasien, false);
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal memuat data dashboard: " + e.getMessage());
//            e.printStackTrace();
//        }
    }
    
    // Method bantuan untuk menjalankan query dan set text ke label
    private void setLabelFromQuery(Connection conn, String sql, javax.swing.JLabel label, boolean isCurrency) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                double nilai = rs.getDouble("total");
                
                if (isCurrency) {
                    // Format ke Rupiah (Rp 1.000.000)
                    label.setText(formatRupiah(nilai));
                } else {
                    // Format angka biasa (tanpa koma desimal untuk count)
                    label.setText(String.valueOf((int)nilai));
                }
            } else {
                label.setText("0");
            }
            
        } catch (SQLException e) {
            System.err.println("Error query: " + sql);
            label.setText("Error");
        }
    }
    
    // Format angka ke Rupiah Indonesia
    private String formatRupiah(double number) {
        Locale localeID = new Locale("id", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJudul = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cardTotalKunjungan = new javax.swing.JPanel();
        lblIconKunjungan = new javax.swing.JLabel();
        lblJumlahKunjungan = new javax.swing.JLabel();
        lblTitleKunjungan = new javax.swing.JLabel();
        cardTotalPendapatan = new javax.swing.JPanel();
        lblIconPendapatan = new javax.swing.JLabel();
        lblTotalPendapatan = new javax.swing.JLabel();
        lblTitlePendapatan = new javax.swing.JLabel();
        cardPasien = new javax.swing.JPanel();
        lblIconPasien = new javax.swing.JLabel();
        lblJumlahPasien = new javax.swing.JLabel();
        lblTitlePasien = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnAksesTambahPasien = new javax.swing.JButton();
        refreshButton = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(51, 51, 51));
        lblJudul.setText("Dashboard");
        lblJudul.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblWelcome.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(153, 153, 153));
        lblWelcome.setText("Selamat datang kembali, [Nama Pemilik Klinik]! Ini adalah ringkasan klinik Anda hari ini.");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        cardTotalKunjungan.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalKunjungan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(50, 120, 220)));
        cardTotalKunjungan.setPreferredSize(new java.awt.Dimension(250, 120));

        lblIconKunjungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Kunjungan.png"))); // NOI18N

        lblJumlahKunjungan.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblJumlahKunjungan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJumlahKunjungan.setText("2");
        lblJumlahKunjungan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblTitleKunjungan.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblTitleKunjungan.setForeground(new java.awt.Color(102, 102, 102));
        lblTitleKunjungan.setText("Total Kunjungan");

        javax.swing.GroupLayout cardTotalKunjunganLayout = new javax.swing.GroupLayout(cardTotalKunjungan);
        cardTotalKunjungan.setLayout(cardTotalKunjunganLayout);
        cardTotalKunjunganLayout.setHorizontalGroup(
            cardTotalKunjunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTotalKunjunganLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIconKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardTotalKunjunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJumlahKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        cardTotalKunjunganLayout.setVerticalGroup(
            cardTotalKunjunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTotalKunjunganLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(cardTotalKunjunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblIconKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cardTotalKunjunganLayout.createSequentialGroup()
                        .addComponent(lblJumlahKunjungan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitleKunjungan)))
                .addGap(23, 23, 23))
        );

        cardTotalPendapatan.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalPendapatan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(25, 135, 84)));
        cardTotalPendapatan.setPreferredSize(new java.awt.Dimension(250, 120));

        lblIconPendapatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pendapatan.png"))); // NOI18N

        lblTotalPendapatan.setFont(new java.awt.Font("sansserif", 1, 25)); // NOI18N
        lblTotalPendapatan.setText("Rp. 150.000.000");
        lblTotalPendapatan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblTitlePendapatan.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblTitlePendapatan.setForeground(new java.awt.Color(102, 102, 102));
        lblTitlePendapatan.setText("Total Pendapatan");

        javax.swing.GroupLayout cardTotalPendapatanLayout = new javax.swing.GroupLayout(cardTotalPendapatan);
        cardTotalPendapatan.setLayout(cardTotalPendapatanLayout);
        cardTotalPendapatanLayout.setHorizontalGroup(
            cardTotalPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTotalPendapatanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIconPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardTotalPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitlePendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPendapatan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardTotalPendapatanLayout.setVerticalGroup(
            cardTotalPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTotalPendapatanLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(cardTotalPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblIconPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cardTotalPendapatanLayout.createSequentialGroup()
                        .addComponent(lblTotalPendapatan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitlePendapatan)))
                .addGap(23, 23, 23))
        );

        cardPasien.setBackground(new java.awt.Color(255, 255, 255));
        cardPasien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(255, 193, 7)));
        cardPasien.setPreferredSize(new java.awt.Dimension(250, 120));

        lblIconPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/patient.png"))); // NOI18N

        lblJumlahPasien.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblJumlahPasien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJumlahPasien.setText("125");
        lblJumlahPasien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblTitlePasien.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblTitlePasien.setForeground(new java.awt.Color(102, 102, 102));
        lblTitlePasien.setText("Pasien Baru");

        javax.swing.GroupLayout cardPasienLayout = new javax.swing.GroupLayout(cardPasien);
        cardPasien.setLayout(cardPasienLayout);
        cardPasienLayout.setHorizontalGroup(
            cardPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPasienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIconPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitlePasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblJumlahPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        cardPasienLayout.setVerticalGroup(
            cardPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPasienLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(cardPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblIconPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cardPasienLayout.createSequentialGroup()
                        .addComponent(lblJumlahPasien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitlePasien)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setText("Akses Cepat");

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        btnAksesTambahPasien.setBackground(new java.awt.Color(50, 120, 220));
        btnAksesTambahPasien.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnAksesTambahPasien.setForeground(new java.awt.Color(255, 255, 255));
        btnAksesTambahPasien.setText("Lihat Semua Laporan");
        btnAksesTambahPasien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAksesTambahPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAksesTambahPasienActionPerformed(evt);
            }
        });

        refreshButton.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(0, 0, 0));
        refreshButton.setText("🔄");
        refreshButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblWelcome)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblJudul)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refreshButton))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnAksesTambahPasien)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cardTotalKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cardTotalPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cardPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 41, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblJudul)
                    .addComponent(refreshButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWelcome)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardTotalKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardTotalPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAksesTambahPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAksesTambahPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAksesTambahPasienActionPerformed
        Container parent = this.getParent();
        CardLayout cl = (CardLayout) parent.getLayout();
        cl.show(parent, "cardLaporan");
    }//GEN-LAST:event_btnAksesTambahPasienActionPerformed

    private void refreshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseClicked
        this.loadDashboardData();
    }//GEN-LAST:event_refreshButtonMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAksesTambahPasien;
    private javax.swing.JPanel cardPasien;
    private javax.swing.JPanel cardTotalKunjungan;
    private javax.swing.JPanel cardTotalPendapatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblIconKunjungan;
    private javax.swing.JLabel lblIconPasien;
    private javax.swing.JLabel lblIconPendapatan;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblJumlahKunjungan;
    private javax.swing.JLabel lblJumlahPasien;
    private javax.swing.JLabel lblTitleKunjungan;
    private javax.swing.JLabel lblTitlePasien;
    private javax.swing.JLabel lblTitlePendapatan;
    private javax.swing.JLabel lblTotalPendapatan;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel refreshButton;
    // End of variables declaration//GEN-END:variables
}

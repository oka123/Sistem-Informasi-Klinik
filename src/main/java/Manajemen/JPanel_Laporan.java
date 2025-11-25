/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Manajemen;

import Database.KoneksiDatabase;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JPanel_Laporan extends javax.swing.JPanel {

//    Deklarasi Atribut
    private final NumberFormat formatRupiah;
    private String tempTotalText = "";
    
    // Constructor
    public JPanel_Laporan() {
        initComponents();
        
        // Inisialisasi format rupiah
        Locale localeID = new Locale("id", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        
        initTampilanAwal();
    }
    
    private void initTampilanAwal() {
        // Atur model tabel default
        DefaultTableModel modelAwal = new DefaultTableModel(new Object[]{"Data Laporan Belum Dimuat..."}, 0);
        tblLaporan.setModel(modelAwal);
        
        // Reset Label Ringkasan
        lblTotalPendapatan.setText("Total: -");
        
        // Set default combo box
        comboJenisLaporan.setSelectedIndex(0); 
        
        // Kosongkan tanggal
         dcDariTanggal.setDate(null);
         dcSampaiTanggal.setDate(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_PilihLaporan = new javax.swing.JLabel();
        comboJenisLaporan = new javax.swing.JComboBox<>();
        dcDariTanggal = new com.toedter.calendar.JDateChooser();
        dcSampaiTanggal = new com.toedter.calendar.JDateChooser();
        lbl_dariTanggal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnTampilkan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        lblTotalPendapatan = new javax.swing.JLabel();
        btnExportData = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(952, 800));

        lbl_PilihLaporan.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lbl_PilihLaporan.setForeground(new java.awt.Color(0, 0, 0));
        lbl_PilihLaporan.setText("Pilih Laporan");

        comboJenisLaporan.setBackground(new java.awt.Color(50, 120, 220));
        comboJenisLaporan.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        comboJenisLaporan.setForeground(new java.awt.Color(255, 255, 255));
        comboJenisLaporan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laporan Pendapatan", "Laporan Kunjungan Pasien", "Laporan Penjualan Obat", " " }));
        comboJenisLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboJenisLaporan.setOpaque(true);
        comboJenisLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJenisLaporanActionPerformed(evt);
            }
        });

        dcDariTanggal.setBackground(new java.awt.Color(255, 255, 255));
        dcDariTanggal.setForeground(new java.awt.Color(0, 0, 0));

        dcSampaiTanggal.setBackground(new java.awt.Color(255, 255, 255));
        dcSampaiTanggal.setForeground(new java.awt.Color(0, 0, 0));

        lbl_dariTanggal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_dariTanggal.setForeground(new java.awt.Color(0, 0, 0));
        lbl_dariTanggal.setText("Dari Tanggal");

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Sampai Tanggal");

        btnTampilkan.setBackground(new java.awt.Color(50, 120, 220));
        btnTampilkan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnTampilkan.setForeground(new java.awt.Color(255, 255, 255));
        btnTampilkan.setText("Tampilkan Laporan");
        btnTampilkan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        tblLaporan.setAutoCreateRowSorter(true);
        tblLaporan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tblLaporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Pembayaran", "Tanggal Bayar", "Kasir", "Metode", "Biaya Jasa", "Biaya Obat", "Total Bayar"
            }
        ));
        tblLaporan.setGridColor(new java.awt.Color(224, 224, 224));
        tblLaporan.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblLaporan.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblLaporan.setShowGrid(true);
        jScrollPane1.setViewportView(tblLaporan);

        lblTotalPendapatan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lblTotalPendapatan.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalPendapatan.setText("Total Pendapatan: Rp. 120.000.000");

        btnExportData.setBackground(new java.awt.Color(25, 135, 84));
        btnExportData.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        btnExportData.setForeground(new java.awt.Color(255, 255, 255));
        btnExportData.setText("Export Data");
        btnExportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTotalPendapatan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportData))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_PilihLaporan)
                        .addGap(18, 18, 18)
                        .addComponent(comboJenisLaporan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcDariTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_dariTanggal)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dcSampaiTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                .addGap(19, 19, 19)
                                .addComponent(btnTampilkan))
                            .addComponent(jLabel1))))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_dariTanggal)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_PilihLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboJenisLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcDariTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcSampaiTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTampilkan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblTotalPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportData, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        tampilkanLaporan();
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void comboJenisLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboJenisLaporanActionPerformed
        // Reset tabel saat jenis laporan diganti agar user tidak bingung
        DefaultTableModel modelKosong = new DefaultTableModel();
        String jenis = (String) comboJenisLaporan.getSelectedItem();
        
        // Set header preview tabel saja
        if (jenis.equals("Laporan Pendapatan")) {
            modelKosong.setColumnIdentifiers(new Object[]{"ID Bayar", "Tgl Bayar", "Kasir", "Metode", "Jasa", "Obat", "Total"});
        } else if (jenis.equals("Laporan Kunjungan Pasien")) {
            modelKosong.setColumnIdentifiers(new Object[]{"Tgl Kunjungan", "No Antrean", "Pasien", "Dokter", "Status"});
        } else {
            modelKosong.setColumnIdentifiers(new Object[]{"Nama Obat", "Satuan", "Total Terjual", "Sisa Stok"});
        }
        
        tblLaporan.setModel(modelKosong);
        lblTotalPendapatan.setText("Total: [Silahkan Pilih Tanggal & Klik Tampilkan]");
    }//GEN-LAST:event_comboJenisLaporanActionPerformed

    private void btnExportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportDataActionPerformed
        exportKeExcel();
    }//GEN-LAST:event_btnExportDataActionPerformed
    
    private void tampilkanLaporan() {
        // 1. Validasi Input Tanggal (Tetap di UI Thread)
        if (dcDariTanggal.getDate() == null || dcSampaiTanggal.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Harap pilih rentang tanggal 'Dari' dan 'Sampai'!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Konversi Tanggal (Tetap di UI Thread)
        final java.sql.Date sqlDari = new java.sql.Date(dcDariTanggal.getDate().getTime());
        final java.sql.Date sqlSampai = new java.sql.Date(dcSampaiTanggal.getDate().getTime());
        final String jenisLaporan = (String) comboJenisLaporan.getSelectedItem();

        // 3. UBAH KURSOR JADI LOADING
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnTampilkan.setEnabled(false); // Nonaktifkan tombol agar user tidak spam klik

        // 4. PROSES BACKGROUND
        new Thread(() -> {
            DefaultTableModel resultModel = null;
            String errorMsg = null;

            try {
                // Panggil method load yang sudah kita modifikasi
                // Method ini akan mengisi 'this.tempTotalText' juga
                switch (jenisLaporan) {
                    case "Laporan Pendapatan" -> resultModel = loadLaporanPendapatans(sqlDari, sqlSampai);
                    case "Laporan Kunjungan Pasien" -> resultModel = loadLaporanKunjungans(sqlDari, sqlSampai);
                    // case Laporan Obat: Saya perhatikan di kode Anda tertulis "Laporan Penjualan Obat" di switch
                    // tapi "Laporan Penggunaan Obat" di string. Sesuaikan dengan string di JComboBox Anda.
                    // Di sini saya pakai "Laporan Penggunaan Obat" sesuai method loadLaporanObat sebelumnya.
                    case "Laporan Penggunaan Obat" -> resultModel = loadLaporanObats(sqlDari, sqlSampai);
                }
            } catch (Exception e) {
                errorMsg = e.getMessage();
                e.printStackTrace();
            }

            // 5. UPDATE UI (KEMBALI KE UI THREAD)
            final DefaultTableModel finalModel = resultModel;
            final String finalError = errorMsg;

            javax.swing.SwingUtilities.invokeLater(() -> {
                if (finalError != null) {
                    JOptionPane.showMessageDialog(this, "Gagal memuat laporan: " + finalError, "Error", JOptionPane.ERROR_MESSAGE);
                } else if (finalModel != null) {
                    // Set Model ke Tabel
                    tblLaporan.setModel(finalModel);
                    
                    // Set Text Total dari variabel sementara
                    lblTotalPendapatan.setText(this.tempTotalText);
                } else {
                    JOptionPane.showMessageDialog(this, "Jenis laporan tidak dikenali.");
                }

                // BALIKIN KURSOR & TOMBOL
                this.setCursor(Cursor.getDefaultCursor());
                btnTampilkan.setEnabled(true);
            });

        }).start();
//        
//        // Validasi Input Tanggal
//        if (dcDariTanggal.getDate() == null || dcSampaiTanggal.getDate() == null) {
//            JOptionPane.showMessageDialog(this, "Harap pilih rentang tanggal 'Dari' dan 'Sampai'!", "Peringatan", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Konversi Tanggal Java ke SQL Date
//        java.sql.Date sqlDari = new java.sql.Date(dcDariTanggal.getDate().getTime());
//        java.sql.Date sqlSampai = new java.sql.Date(dcSampaiTanggal.getDate().getTime());
//
//        String jenisLaporan = (String) comboJenisLaporan.getSelectedItem();
//
//        // Panggil method spesifik berdasarkan pilihan
//        switch (jenisLaporan) {
//            case "Laporan Pendapatan" -> loadLaporanPendapatan(sqlDari, sqlSampai);
//            case "Laporan Kunjungan Pasien" -> loadLaporanKunjungan(sqlDari, sqlSampai);
//            case "Laporan Penjualan Obat" -> loadLaporanObat(sqlDari, sqlSampai);
//            default -> JOptionPane.showMessageDialog(this, "Jenis laporan tidak valid.");
//        }
//        this.setCursor(Cursor.getDefaultCursor());
//    }
//    
//    private void loadLaporanPendapatan(java.sql.Date dari, java.sql.Date sampai) {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new Object[]{"ID Pembayaran", "Tanggal Bayar", "Kasir", "Metode", "Biaya Jasa", "Biaya Obat", "Total Bayar"});
//        
//        double grandTotal = 0;
//        
//        // Query JOIN: Pembayaran -> User (Kasir)
//        // Hitung total_bayar dinamis (jasa + obat) jika kolom total_bayar belum ada di DB
//        String sql = "SELECT p.pembayaran_id, p.tanggal_bayar, u.nama_lengkap, " +
//                     "p.metode_bayar, p.total_biaya_jasa, p.total_biaya_obat, p.total_bayar " +
//                     "FROM pembayaran p " +
//                     "LEFT JOIN user u ON p.user_kasir_id = u.user_id " +
//                     "WHERE DATE(p.tanggal_bayar) BETWEEN ? AND ?";
//
//        try (Connection conn = new KoneksiDatabase().getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            
//            pstmt.setDate(1, dari);
//            pstmt.setDate(2, sampai);
//            
//            ResultSet rs = pstmt.executeQuery();
//            
//            while (rs.next()) {
//                double jasa = rs.getDouble("total_biaya_jasa");
//                double obat = rs.getDouble("total_biaya_obat");
//                double total = rs.getDouble("total_bayar");
//                
//                grandTotal += total;
//                
//                model.addRow(new Object[]{
//                    rs.getInt("pembayaran_id"),
//                    rs.getTimestamp("tanggal_bayar"),
//                    rs.getString("nama_lengkap"), // Nama Kasir
//                    rs.getString("metode_bayar"),
//                    formatRupiah.format(jasa),
//                    formatRupiah.format(obat),
//                    formatRupiah.format(total)
//                });
//            }
//            
//            tblLaporan.setModel(model);
//            lblTotalPendapatan.setText("Total Pendapatan: " + formatRupiah.format(grandTotal));
//            
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal memuat laporan pendapatan: " + e.getMessage());
//            e.printStackTrace();
//        }
    }
    
    private void loadLaporanKunjungan(java.sql.Date dari, java.sql.Date sampai) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tanggal", "Pasien", "Dokter", "Status"});
        
        int totalKunjungan = 0;

        // Query JOIN: Kunjungan -> Pasien -> Dokter -> User (Untuk nama dokter)
        String sql = "SELECT k.tanggal_kunjungan, p.nama_pasien, u.nama_lengkap AS nama_dokter, k.status_kunjungan " +
                     "FROM kunjungan k " +
                     "JOIN pasien p ON k.pasien_id = p.pasien_id " +
                     "JOIN dokter d ON k.dokter_id = d.dokter_id " +
                     "JOIN user u ON d.user_id = u.user_id " + // Ambil nama dokter dari tabel user
                     "WHERE DATE(k.tanggal_kunjungan) BETWEEN ? AND ?";

        try (Connection conn = new KoneksiDatabase().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, dari);
            pstmt.setDate(2, sampai);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                totalKunjungan++;
                model.addRow(new Object[]{
                    rs.getTimestamp("tanggal_kunjungan"),
                    rs.getString("nama_pasien"),
                    rs.getString("nama_dokter"),
                    rs.getString("status_kunjungan")
                });
            }
            
            tblLaporan.setModel(model);
            lblTotalPendapatan.setText("Total Kunjungan: " + totalKunjungan + " Pasien");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat laporan kunjungan: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadLaporanObat(java.sql.Date dari, java.sql.Date sampai) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Obat", "Satuan", "Total Terjual (Qty)", "Sisa Stok"});
        
        int totalItemTerjual = 0;

        // Query Agregasi (SUM) dengan JOIN
        // Detail_Resep -> Resep (untuk filter tanggal) -> Obat (untuk nama & stok)
        String sql = "SELECT o.nama_obat, o.satuan, SUM(dr.jumlah) as total_qty, o.stok " +
                     "FROM detail_resep dr " +
                     "JOIN obat o ON dr.obat_id = o.obat_id " +
                     "JOIN resep r ON dr.resep_id = r.resep_id " +
                     "WHERE DATE(r.tanggal_resep) BETWEEN ? AND ? " +
                     "GROUP BY o.obat_id, o.nama_obat, o.satuan, o.stok " +
                     "ORDER BY total_qty DESC";

        try (Connection conn = new KoneksiDatabase().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, dari);
            pstmt.setDate(2, sampai);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int qty = rs.getInt("total_qty");
                totalItemTerjual += qty;
                
                model.addRow(new Object[]{
                    rs.getString("nama_obat"),
                    rs.getString("satuan"),
                    qty,
                    rs.getInt("stok")
                });
            }
            
            tblLaporan.setModel(model);
            lblTotalPendapatan.setText("Total Item Obat Terjual: " + totalItemTerjual);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat laporan obat: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Test
    
    // 1. Laporan Pendapatan
    private DefaultTableModel loadLaporanPendapatans(java.sql.Date dari, java.sql.Date sampai) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID Pembayaran", "Tanggal Bayar", "Kasir", "Metode", "Biaya Jasa", "Biaya Obat", "Total Bayar"});
        
        double grandTotal = 0;
        
        String sql = "SELECT p.pembayaran_id, p.tanggal_bayar, u.nama_lengkap, " +
                     "p.metode_bayar, p.total_biaya_jasa, p.total_biaya_obat, p.total_bayar " +
                     "FROM pembayaran p " +
                     "LEFT JOIN user u ON p.user_kasir_id = u.user_id " +
                     "WHERE DATE(p.tanggal_bayar) BETWEEN ? AND ?";

        try (Connection conn = new KoneksiDatabase().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, dari);
            pstmt.setDate(2, sampai);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                double total = rs.getDouble("total_bayar");
                double jasa = rs.getDouble("total_biaya_jasa");
                double obat = rs.getDouble("total_biaya_obat");
                grandTotal += total;
                
                model.addRow(new Object[]{
                    rs.getInt("pembayaran_id"),
                    rs.getTimestamp("tanggal_bayar"),
                    rs.getString("nama_lengkap"), 
                    rs.getString("metode_bayar"),
                    formatRupiah.format(jasa),
                    formatRupiah.format(obat),
                    formatRupiah.format(total)
                });
            }
        }
        // Simpan ringkasan ke variabel sementara
        this.tempTotalText = "Total Pendapatan: " + formatRupiah.format(grandTotal);
        return model;
    }

    // 2. Laporan Kunjungan
    private DefaultTableModel loadLaporanKunjungans(java.sql.Date dari, java.sql.Date sampai) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tanggal", "Pasien", "Dokter", "Status"});
        int totalKunjungan = 0;

        String sql = "SELECT k.tanggal_kunjungan, p.nama_pasien, u.nama_lengkap AS nama_dokter, k.status_kunjungan " +
                     "FROM kunjungan k " +
                     "JOIN pasien p ON k.pasien_id = p.pasien_id " +
                     "JOIN dokter d ON k.dokter_id = d.dokter_id " +
                     "JOIN user u ON d.user_id = u.user_id " + 
                     "WHERE DATE(k.tanggal_kunjungan) BETWEEN ? AND ?";

        try (Connection conn = new KoneksiDatabase().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, dari);
            pstmt.setDate(2, sampai);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                totalKunjungan++;
                model.addRow(new Object[]{
                    rs.getTimestamp("tanggal_kunjungan"),
                    rs.getString("nama_pasien"),
                    rs.getString("nama_dokter"),
                    rs.getString("status_kunjungan")
                });
            }
        }
        this.tempTotalText = "Total Kunjungan: " + totalKunjungan + " Pasien";
        return model;
    }

    // 3. Laporan Obat
    private DefaultTableModel loadLaporanObats(java.sql.Date dari, java.sql.Date sampai) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Obat", "Satuan", "Total Terjual (Qty)", "Sisa Stok"});
        int totalItemTerjual = 0;

        String sql = "SELECT o.nama_obat, o.satuan, SUM(dr.jumlah) as total_qty, o.stok " +
                     "FROM detail_resep dr " +
                     "JOIN obat o ON dr.obat_id = o.obat_id " +
                     "JOIN resep r ON dr.resep_id = r.resep_id " +
                     "WHERE DATE(r.tanggal_resep) BETWEEN ? AND ? " +
                     "GROUP BY o.obat_id, o.nama_obat, o.satuan, o.stok " +
                     "ORDER BY total_qty DESC";

        try (Connection conn = new KoneksiDatabase().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, dari);
            pstmt.setDate(2, sampai);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int qty = rs.getInt("total_qty");
                totalItemTerjual += qty;
                model.addRow(new Object[]{
                    rs.getString("nama_obat"),
                    rs.getString("satuan"),
                    qty,
                    rs.getInt("stok")
                });
            }
        }
        this.tempTotalText = "Total Item Obat Terjual: " + totalItemTerjual;
        return model;
    }
    
    // Akhir Test
    
    private void exportKeExcel() {
        // Cek apakah ada data di tabel
        if (tblLaporan.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diexport.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Pilih Lokasi Penyimpanan File
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan ke Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel File (*.xlsx)", "xlsx"));

        // Set nama file default (berdasarkan jenis laporan + timestamp sederhana)
        String jenis = comboJenisLaporan.getSelectedItem().toString().replace(" ", "_");
        String namaFileDefault = jenis + "_" + System.currentTimeMillis() + ".xlsx";
        fileChooser.setSelectedFile(new File(namaFileDefault));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Pastikan ekstensi .xlsx
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            // roses Penulisan Excel (Apache POI)
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Laporan");

                // --- HEADER STYLE ---
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderTop(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // --- MEMBUAT ROW HEADER ---
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < tblLaporan.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(tblLaporan.getColumnName(i));
                    cell.setCellStyle(headerStyle);
                }

                // --- MENGISI DATA BARIS ---
                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setBorderBottom(BorderStyle.THIN);
                dataStyle.setBorderRight(BorderStyle.THIN);
                dataStyle.setBorderLeft(BorderStyle.THIN);

                for (int r = 0; r < tblLaporan.getRowCount(); r++) {
                    Row row = sheet.createRow(r + 1); // +1 karena baris 0 dipakai header
                    for (int c = 0; c < tblLaporan.getColumnCount(); c++) {
                        Cell cell = row.createCell(c);
                        Object value = tblLaporan.getValueAt(r, c);

                        if (value != null) {
                            // Cek tipe data agar di Excel bisa dihitung (Sum/Avg)
                            String strValue = value.toString();

                            // Coba parsing angka (menghapus "Rp " dan "." agar jadi angka murni di Excel)
                            // Logika ini opsional, tergantung apakah Anda ingin format "Rp" tetap ada atau jadi angka
                            if (strValue.startsWith("Rp")) {
                                // Jika ingin disimpan sebagai teks apa adanya:
                                cell.setCellValue(strValue);
                            } else if (value instanceof Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                            } else {
                                cell.setCellValue(strValue);
                            }
                        } else {
                            cell.setCellValue("");
                        }
                        cell.setCellStyle(dataStyle);
                    }
                }

                // Auto Size Kolom (agar lebar kolom pas)
                for (int i = 0; i < tblLaporan.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                // Simpan ke File
                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                }

                JOptionPane.showMessageDialog(this, "Data berhasil diexport ke:\n" + fileToSave.getAbsolutePath());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal mengexport data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportData;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JComboBox<String> comboJenisLaporan;
    private com.toedter.calendar.JDateChooser dcDariTanggal;
    private com.toedter.calendar.JDateChooser dcSampaiTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalPendapatan;
    private javax.swing.JLabel lbl_PilihLaporan;
    private javax.swing.JLabel lbl_dariTanggal;
    private javax.swing.JTable tblLaporan;
    // End of variables declaration//GEN-END:variables

}


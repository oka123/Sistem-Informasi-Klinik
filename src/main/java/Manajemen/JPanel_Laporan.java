/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Manajemen;

import Database.KoneksiDatabase;
import Main.ThreadPoolManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.Cursor;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Date;
import javax.swing.SwingUtilities;

public class JPanel_Laporan extends javax.swing.JPanel implements Manajemen {
    
    // Atribut
    private final NumberFormat formatRupiah;
    private String tempTotalText = "";
           
    // Constructor
    public JPanel_Laporan() {
        initComponents();
        
        // Inisialisasi format rupiah
        Locale localeID = new Locale("id", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        
        initTampilanAwalLaporan();
    }
    
    // Konsep Enkapsulasi (Getter dan Setter)
    public String getTempTotalText() {
        return this.tempTotalText;
    }

    public void setTempTotalText(String tempTotalText) {
        this.tempTotalText = tempTotalText;
    }
    
    @Override
    public void initTampilanAwalLaporan() {
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
    
    @Override
    public void tampilkanLaporan() {
        // 1. Validasi Input Tanggal (Tetap di UI Thread)
        if (dcDariTanggal.getDate() == null || dcSampaiTanggal.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Harap pilih rentang tanggal 'Dari' dan 'Sampai'!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Konversi Tanggal (Tetap di UI Thread)
        final Date sqlDari = new Date(dcDariTanggal.getDate().getTime());
        final Date sqlSampai = new Date(dcSampaiTanggal.getDate().getTime());
        final String jenisLaporan = (String) comboJenisLaporan.getSelectedItem();

        // 3. UBAH KURSOR JADI LOADING
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnTampilkan.setEnabled(false); // Nonaktifkan tombol agar user tidak spam klik

        // 4. PROSES BACKGROUND
        ThreadPoolManager.getInstance().submit(() -> {
            DefaultTableModel resultModel = null;
            String errorMsg = null;

            try {
                // Panggil method load yang sudah kita modifikasi
                // Method ini akan mengisi 'this.tempTotalText' juga
                switch (jenisLaporan) {
                    case "Laporan Pendapatan" -> resultModel = loadLaporanPendapatan(sqlDari, sqlSampai);
                    case "Laporan Kunjungan Pasien" -> resultModel = loadLaporanKunjungan(sqlDari, sqlSampai);
                    case "Laporan Penjualan Obat" -> resultModel = loadLaporanObat(sqlDari, sqlSampai);
                    default -> JOptionPane.showMessageDialog(this, "Jenis laporan tidak valid.");
                }
            } catch (HeadlessException | SQLException e) {
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

        });
//        
//        // Validasi Input Tanggal
//        if (dcDariTanggal.getDate() == null || dcSampaiTanggal.getDate() == null) {
//            JOptionPane.showMessageDialog(this, "Harap pilih rentang tanggal 'Dari' dan 'Sampai'!", "Peringatan", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Konversi Tanggal Java ke SQL Date
//        Date sqlDari = new Date(dcDariTanggal.getDate().getTime());
//        Date sqlSampai = new Date(dcSampaiTanggal.getDate().getTime());
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

    }
    
//    private void loadLaporanPendapatan(Date dari, Date sampai) {
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
//    }
    
//    private void loadLaporanKunjungan(Date dari, Date sampai) {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new Object[]{"Tanggal", "Pasien", "Dokter", "Status"});
//        
//        int totalKunjungan = 0;
//
//        // Query JOIN: Kunjungan -> Pasien -> Dokter -> User (Untuk nama dokter)
//        String sql = "SELECT k.tanggal_kunjungan, p.nama_pasien, u.nama_lengkap AS nama_dokter, k.status_kunjungan " +
//                     "FROM kunjungan k " +
//                     "JOIN pasien p ON k.pasien_id = p.pasien_id " +
//                     "JOIN dokter d ON k.dokter_id = d.dokter_id " +
//                     "JOIN user u ON d.user_id = u.user_id " + // Ambil nama dokter dari tabel user
//                     "WHERE DATE(k.tanggal_kunjungan) BETWEEN ? AND ?";
//
//        try (Connection conn = new KoneksiDatabase().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            
//            pstmt.setDate(1, dari);
//            pstmt.setDate(2, sampai);
//            
//            ResultSet rs = pstmt.executeQuery();
//            
//            while (rs.next()) {
//                totalKunjungan++;
//                model.addRow(new Object[]{
//                    rs.getTimestamp("tanggal_kunjungan"),
//                    rs.getString("nama_pasien"),
//                    rs.getString("nama_dokter"),
//                    rs.getString("status_kunjungan")
//                });
//            }
//            
//            tblLaporan.setModel(model);
//            lblTotalPendapatan.setText("Total Kunjungan: " + totalKunjungan + " Pasien");
//            
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal memuat laporan kunjungan: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
    
//    private void loadLaporanObat(Date dari, Date sampai) {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new Object[]{"Nama Obat", "Satuan", "Total Terjual (Qty)", "Sisa Stok"});
//        
//        int totalItemTerjual = 0;
//
//        // Query Agregasi (SUM) dengan JOIN
//        // Detail_Resep -> Resep (untuk filter tanggal) -> Obat (untuk nama & stok)
//        String sql = "SELECT o.nama_obat, o.satuan, SUM(dr.jumlah) as total_qty, o.stok " +
//                     "FROM detail_resep dr " +
//                     "JOIN obat o ON dr.obat_id = o.obat_id " +
//                     "JOIN resep r ON dr.resep_id = r.resep_id " +
//                     "WHERE DATE(r.tanggal_resep) BETWEEN ? AND ? " +
//                     "GROUP BY o.obat_id, o.nama_obat, o.satuan, o.stok " +
//                     "ORDER BY total_qty DESC";
//
//        try (Connection conn = new KoneksiDatabase().getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            
//            pstmt.setDate(1, dari);
//            pstmt.setDate(2, sampai);
//            
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                int qty = rs.getInt("total_qty");
//                totalItemTerjual += qty;
//                
//                model.addRow(new Object[]{
//                    rs.getString("nama_obat"),
//                    rs.getString("satuan"),
//                    qty,
//                    rs.getInt("stok")
//                });
//            }
//            
//            tblLaporan.setModel(model);
//            lblTotalPendapatan.setText("Total Item Obat Terjual: " + totalItemTerjual);
//            
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal memuat laporan obat: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
    
    // Test
    
    // 1. Laporan Pendapatan
    @Override
    public DefaultTableModel loadLaporanPendapatan(Date dari, Date sampai) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID Pembayaran", "Tanggal Bayar", "Kasir", "Metode", "Biaya Jasa", "Biaya Obat", "Total Bayar"});
        
        double grandTotal = 0;
        
        String sql = "SELECT p.pembayaran_id, p.tanggal_bayar, u.nama_lengkap, " +
                     "p.metode_bayar, p.total_biaya_jasa, p.total_biaya_obat, p.total_bayar " +
                     "FROM pembayaran p " +
                     "LEFT JOIN user u ON p.user_kasir_id = u.user_id " +
                     "WHERE DATE(p.tanggal_bayar) BETWEEN ? AND ?";

        try {
            Connection conn = Database.KoneksiDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Simpan ringkasan ke variabel sementara
        this.tempTotalText = "Total Pendapatan: " + formatRupiah.format(grandTotal);
        return model;
    }

    // 2. Laporan Kunjungan
    @Override
    public DefaultTableModel loadLaporanKunjungan(Date dari, Date sampai) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tanggal", "Pasien", "Dokter", "Status"});
        int totalKunjungan = 0;

        String sql = "SELECT k.tanggal_kunjungan, p.nama_pasien, u.nama_lengkap AS nama_dokter, k.status_kunjungan " +
                     "FROM kunjungan k " +
                     "JOIN pasien p ON k.pasien_id = p.pasien_id " +
                     "JOIN dokter d ON k.dokter_id = d.dokter_id " +
                     "JOIN user u ON d.user_id = u.user_id " + 
                     "WHERE DATE(k.tanggal_kunjungan) BETWEEN ? AND ?";

        try {
            Connection conn = Database.KoneksiDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.tempTotalText = "Total Kunjungan: " + totalKunjungan + " Pasien";
        return model;
    }

    // 3. Laporan Obat
    @Override
    public DefaultTableModel loadLaporanObat(Date dari, Date sampai) throws SQLException {
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

        try {
            Connection conn = Database.KoneksiDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.tempTotalText = "Total Item Obat Terjual: " + totalItemTerjual;
        return model;
    }
    
    // Akhir Test
    
    @Override
    public void exportLaporanToExcel() {
        if (tblLaporan.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diexport.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan ke Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel File (*.xlsx)", "xlsx"));
        
        String jenis = comboJenisLaporan.getSelectedItem().toString().replace(" ", "_");
        String namaFileDefault = jenis + "_" + System.currentTimeMillis() + ".xlsx";
        fileChooser.setSelectedFile(new File(namaFileDefault));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // --- PERBAIKAN DI SINI ---
            
            // 1. Ambil file mentah dari chooser
            File rawFile = fileChooser.getSelectedFile();
            
            // 2. Cek ekstensi .xlsx SEBELUM masuk thread (di UI Thread)
            // Jika tidak ada .xlsx, kita buat file baru dengan nama yang benar
            if (!rawFile.getAbsolutePath().endsWith(".xlsx")) {
                rawFile = new File(rawFile.getAbsolutePath() + ".xlsx");
            }
            
            // 3. Buat variabel FINAL untuk diserahkan ke dalam Thread
            // Variabel ini tidak boleh diubah lagi nilainya di bawah sana
            final File finalFileToSave = rawFile;
            
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            btnExportData.setEnabled(false);
            
            // Gunakan Thread Pool untuk proses penulisan file (I/O operation)
            ThreadPoolManager.getInstance().submit(() -> {
                try {
                    File fileToSave = fileChooser.getSelectedFile();
                    if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
                    }

                    try (Workbook workbook = new XSSFWorkbook()) {
                        Sheet sheet = workbook.createSheet("Laporan");
                        
                        // ... (Styling code sama seperti sebelumnya) ...
                        CellStyle headerStyle = workbook.createCellStyle();
                        Font headerFont = workbook.createFont();
                        headerFont.setBold(true);
                        headerStyle.setFont(headerFont);
                        headerStyle.setBorderBottom(BorderStyle.THIN);
                        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                        CellStyle dataStyle = workbook.createCellStyle();
                        dataStyle.setBorderBottom(BorderStyle.THIN);
                        dataStyle.setBorderRight(BorderStyle.THIN);
                        dataStyle.setBorderLeft(BorderStyle.THIN);

                        // Write Header (UI Access harus hati-hati di thread lain, tapi baca JTable biasanya aman jika read-only)
                        // Untuk thread-safety yang 100% benar, data tabel harus dicopy dulu di UI thread.
                        // Namun untuk simplifikasi di sini kita akses langsung dengan asumsi user tidak mengubah tabel saat export.
                        
                        Row headerRow = sheet.createRow(0);
                        for (int i = 0; i < tblLaporan.getColumnCount(); i++) {
                            Cell cell = headerRow.createCell(i);
                            cell.setCellValue(tblLaporan.getColumnName(i));
                            cell.setCellStyle(headerStyle);
                        }

                        for (int r = 0; r < tblLaporan.getRowCount(); r++) {
                            Row row = sheet.createRow(r + 1);
                            for (int c = 0; c < tblLaporan.getColumnCount(); c++) {
                                Cell cell = row.createCell(c);
                                Object value = tblLaporan.getValueAt(r, c);
                                if (value != null) {
                                    String strValue = value.toString();
                                    if (strValue.startsWith("Rp")) cell.setCellValue(strValue);
                                    else if (value instanceof Number number) cell.setCellValue(number.doubleValue());
                                    else cell.setCellValue(strValue);
                                } else {
                                    cell.setCellValue("");
                                }
                                cell.setCellStyle(dataStyle);
                            }
                        }

                        for (int i = 0; i < tblLaporan.getColumnCount(); i++) sheet.autoSizeColumn(i);

                        try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                            workbook.write(out);
                        }
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Data berhasil diexport ke:\n" + finalFileToSave.getAbsolutePath());
                    });

                } catch (IOException e) {
                    SwingUtilities.invokeLater(() -> {
                        this.setCursor(Cursor.getDefaultCursor());
                        JOptionPane.showMessageDialog(this, "Gagal mengexport data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    });
                    e.printStackTrace();
                } finally {
                    this.setCursor(Cursor.getDefaultCursor());
                    btnExportData.setEnabled(true);
                }
            });
        }
//        // Cek apakah ada data di tabel
//        if (tblLaporan.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diexport.", "Info", JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
//
//        // Pilih Lokasi Penyimpanan File
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Simpan Laporan ke Excel");
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel File (*.xlsx)", "xlsx"));
//
//        // Set nama file default (berdasarkan jenis laporan + timestamp sederhana)
//        String jenis = comboJenisLaporan.getSelectedItem().toString().replace(" ", "_");
//        String namaFileDefault = jenis + "_" + System.currentTimeMillis() + ".xlsx";
//        fileChooser.setSelectedFile(new File(namaFileDefault));
//
//        int userSelection = fileChooser.showSaveDialog(this);
//
//        if (userSelection == JFileChooser.APPROVE_OPTION) {
//            File fileToSave = fileChooser.getSelectedFile();
//
//            // Pastikan ekstensi .xlsx
//            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
//                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
//            }
//
//            // roses Penulisan Excel (Apache POI)
//            try (Workbook workbook = new XSSFWorkbook()) {
//                Sheet sheet = workbook.createSheet("Laporan");
//
//                // --- HEADER STYLE ---
//                CellStyle headerStyle = workbook.createCellStyle();
//                Font headerFont = workbook.createFont();
//                headerFont.setBold(true);
//                headerStyle.setFont(headerFont);
//                headerStyle.setBorderBottom(BorderStyle.THIN);
//                headerStyle.setBorderTop(BorderStyle.THIN);
//                headerStyle.setBorderRight(BorderStyle.THIN);
//                headerStyle.setBorderLeft(BorderStyle.THIN);
//                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//                // --- MEMBUAT ROW HEADER ---
//                Row headerRow = sheet.createRow(0);
//                for (int i = 0; i < tblLaporan.getColumnCount(); i++) {
//                    Cell cell = headerRow.createCell(i);
//                    cell.setCellValue(tblLaporan.getColumnName(i));
//                    cell.setCellStyle(headerStyle);
//                }
//
//                // --- MENGISI DATA BARIS ---
//                CellStyle dataStyle = workbook.createCellStyle();
//                dataStyle.setBorderBottom(BorderStyle.THIN);
//                dataStyle.setBorderRight(BorderStyle.THIN);
//                dataStyle.setBorderLeft(BorderStyle.THIN);
//
//                for (int r = 0; r < tblLaporan.getRowCount(); r++) {
//                    Row row = sheet.createRow(r + 1); // +1 karena baris 0 dipakai header
//                    for (int c = 0; c < tblLaporan.getColumnCount(); c++) {
//                        Cell cell = row.createCell(c);
//                        Object value = tblLaporan.getValueAt(r, c);
//
//                        if (value != null) {
//                            // Cek tipe data agar di Excel bisa dihitung (Sum/Avg)
//                            String strValue = value.toString();
//
//                            // Coba parsing angka (menghapus "Rp " dan "." agar jadi angka murni di Excel)
//                            // Logika ini opsional, tergantung apakah Anda ingin format "Rp" tetap ada atau jadi angka
//                            if (strValue.startsWith("Rp")) {
//                                // Jika ingin disimpan sebagai teks apa adanya:
//                                cell.setCellValue(strValue);
//                            } else if (value instanceof Number) {
//                                cell.setCellValue(((Number) value).doubleValue());
//                            } else {
//                                cell.setCellValue(strValue);
//                            }
//                        } else {
//                            cell.setCellValue("");
//                        }
//                        cell.setCellStyle(dataStyle);
//                    }
//                }
//
//                // Auto Size Kolom (agar lebar kolom pas)
//                for (int i = 0; i < tblLaporan.getColumnCount(); i++) {
//                    sheet.autoSizeColumn(i);
//                }
//
//                // Simpan ke File
//                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
//                    workbook.write(out);
//                }
//
//                JOptionPane.showMessageDialog(this, "Data berhasil diexport ke:\n" + fileToSave.getAbsolutePath());
//
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(this, "Gagal mengexport data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                e.printStackTrace();
//            }
//        }

    }
    
    @Override
    public void tampilkanGrafikPendapatan() {
        if (dcDariTanggal.getDate() == null || dcSampaiTanggal.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Pilih rentang tanggal terlebih dahulu!");
            return;
        }

        final Date sqlDari = new Date(dcDariTanggal.getDate().getTime());
        final Date sqlSampai = new Date(dcSampaiTanggal.getDate().getTime());
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnGrafik.setEnabled(false);

        // Menggunakan Thread Pool juga untuk Grafik agar konsisten
        ThreadPoolManager.getInstance().submit(() -> {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            boolean success = false;
            String errorMsg = null;

            String sql = "SELECT DATE(tanggal_bayar) as tgl, SUM(total_bayar) as total " +
                         "FROM pembayaran " +
                         "WHERE DATE(tanggal_bayar) BETWEEN ? AND ? " +
                         "GROUP BY DATE(tanggal_bayar) " +
                         "ORDER BY tgl ASC";

            try {
                Connection conn = KoneksiDatabase.getConnection();  // Koneksi dibuka di sini

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {  // PreparedStatement ditangani oleh try-with-resources
                    pstmt.setDate(1, sqlDari);
                    pstmt.setDate(2, sqlSampai);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        String tanggal = rs.getString("tgl");
                        double total = rs.getDouble("total");
                        dataset.addValue(total, "Pendapatan", tanggal);
                    }

                    success = true;  // Set success jika data berhasil diproses
                }

            } catch (SQLException e) {
                errorMsg = e.getMessage();
            }

            // Update UI
            final boolean finalSuccess = success;
            final String finalError = errorMsg;
            
            SwingUtilities.invokeLater(() -> {
                this.setCursor(Cursor.getDefaultCursor());
                btnGrafik.setEnabled(true);
                
                if (finalSuccess) {
                    JFreeChart barChart = ChartFactory.createBarChart(
                            "Grafik Pendapatan Klinik", 
                            "Tanggal", 
                            "Total Pendapatan (Rp)", 
                            dataset, 
                            PlotOrientation.VERTICAL, 
                            true, true, false);

                    JDialog chartDialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Grafik Pendapatan", true);
                    chartDialog.setSize(900, 600);
                    chartDialog.setLocationRelativeTo(null);
                    ChartPanel chartPanel = new ChartPanel(barChart);
                    chartDialog.add(chartPanel, BorderLayout.CENTER);
                    chartDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengambil data grafik: " + finalError);
                }
            });
        });
//        // 1. Validasi Tanggal dulu
//        if (dcDariTanggal.getDate() == null || dcSampaiTanggal.getDate() == null) {
//            JOptionPane.showMessageDialog(this, "Pilih rentang tanggal terlebih dahulu!");
//            return;
//        }
//
//        Date sqlDari = new Date(dcDariTanggal.getDate().getTime());
//        Date sqlSampai = new Date(dcSampaiTanggal.getDate().getTime());
//
//        // 2. Siapkan Dataset (Wadah Data untuk Grafik)
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        // 3. Query Data (Group by Tanggal)
//        String sql = "SELECT DATE(tanggal_bayar) as tgl, SUM(total_bayar) as total " +
//                     "FROM pembayaran " +
//                     "WHERE DATE(tanggal_bayar) BETWEEN ? AND ? " +
//                     "GROUP BY DATE(tanggal_bayar) " +
//                     "ORDER BY tgl ASC";
//
//        try (Connection conn = new Database.KoneksiDatabase().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setDate(1, sqlDari);
//            pstmt.setDate(2, sqlSampai);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            // Masukkan hasil query ke Dataset JFreeChart
//            while (rs.next()) {
//                String tanggal = rs.getString("tgl"); // Sumbu X
//                double total = rs.getDouble("total"); // Sumbu Y
//                dataset.addValue(total, "Pendapatan", tanggal);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Gagal mengambil data grafik: " + e.getMessage());
//            return;
//        }
//
//        // 4. Buat Objek Chart
//        JFreeChart barChart = ChartFactory.createBarChart(
//                "Grafik Pendapatan Klinik",  // Judul Grafik
//                "Tanggal",                   // Label Sumbu X
//                "Total Pendapatan (Rp)",     // Label Sumbu Y
//                dataset,                     // Data
//                PlotOrientation.VERTICAL,    // Orientasi
//                true, true, false);
//
//        // 5. Tampilkan dalam Dialog Pop-up
//        JDialog chartDialog = new JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), "Grafik", true);
//        chartDialog.setSize(800, 600);
//        chartDialog.setLocationRelativeTo(null);
//
//        // Bungkus chart ke dalam Panel
//        ChartPanel chartPanel = new ChartPanel(barChart);
//        chartDialog.add(chartPanel, BorderLayout.CENTER);
//
//        chartDialog.setVisible(true);

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
        btnGrafik = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(952, 800));

        lbl_PilihLaporan.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lbl_PilihLaporan.setForeground(new java.awt.Color(0, 0, 0));
        lbl_PilihLaporan.setText("Pilih Laporan");

        comboJenisLaporan.setBackground(new java.awt.Color(0, 123, 255));
        comboJenisLaporan.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        comboJenisLaporan.setForeground(new java.awt.Color(255, 255, 255));
        comboJenisLaporan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laporan Pendapatan", "Laporan Kunjungan Pasien", "Laporan Penjualan Obat" }));
        comboJenisLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboJenisLaporan.setOpaque(true);
        comboJenisLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJenisLaporanActionPerformed(evt);
            }
        });

        dcDariTanggal.setBackground(new java.awt.Color(255, 255, 255));
        dcDariTanggal.setForeground(new java.awt.Color(0, 0, 0));
        dcDariTanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dcDariTanggalMouseClicked(evt);
            }
        });

        dcSampaiTanggal.setBackground(new java.awt.Color(255, 255, 255));
        dcSampaiTanggal.setForeground(new java.awt.Color(0, 0, 0));

        lbl_dariTanggal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_dariTanggal.setForeground(new java.awt.Color(0, 0, 0));
        lbl_dariTanggal.setText("Dari Tanggal");

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Sampai Tanggal");

        btnTampilkan.setBackground(new java.awt.Color(0, 123, 255));
        btnTampilkan.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
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
        tblLaporan.setSelectionBackground(new java.awt.Color(0, 123, 255));
        tblLaporan.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblLaporan.setShowGrid(true);
        jScrollPane1.setViewportView(tblLaporan);

        lblTotalPendapatan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lblTotalPendapatan.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalPendapatan.setText("Total Pendapatan: Rp. 120.000.000");

        btnExportData.setBackground(new java.awt.Color(40, 167, 69));
        btnExportData.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnExportData.setForeground(new java.awt.Color(255, 255, 255));
        btnExportData.setText("Export Data");
        btnExportData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportDataActionPerformed(evt);
            }
        });

        btnGrafik.setBackground(new java.awt.Color(0, 123, 255));
        btnGrafik.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnGrafik.setForeground(new java.awt.Color(255, 255, 255));
        btnGrafik.setText("Lihat Grafik");
        btnGrafik.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGrafik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrafikActionPerformed(evt);
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
                        .addComponent(btnGrafik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExportData))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_PilihLaporan)
                        .addGap(18, 18, 18)
                        .addComponent(comboJenisLaporan, 0, 253, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcDariTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_dariTanggal)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dcSampaiTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
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
                    .addComponent(btnExportData, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGrafik, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        switch (jenis) {
            case "Laporan Pendapatan" -> modelKosong.setColumnIdentifiers(new Object[]{"ID Bayar", "Tgl Bayar", "Kasir", "Metode", "Jasa", "Obat", "Total"});
            case "Laporan Kunjungan Pasien" -> modelKosong.setColumnIdentifiers(new Object[]{"Tgl Kunjungan", "No Antrean", "Pasien", "Dokter", "Status"});
            default -> modelKosong.setColumnIdentifiers(new Object[]{"Nama Obat", "Satuan", "Total Terjual", "Sisa Stok"});
        }
        
        tblLaporan.setModel(modelKosong);
        lblTotalPendapatan.setText("Total: [Silahkan Pilih Tanggal & Klik Tampilkan]");
    }//GEN-LAST:event_comboJenisLaporanActionPerformed

    private void btnExportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportDataActionPerformed
        exportLaporanToExcel();
    }//GEN-LAST:event_btnExportDataActionPerformed

    private void btnGrafikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrafikActionPerformed
        if (comboJenisLaporan.getSelectedItem().equals("Laporan Pendapatan")) {
        tampilkanGrafikPendapatan();
    } else {
        JOptionPane.showMessageDialog(this, "Fitur grafik saat ini hanya tersedia untuk Laporan Pendapatan.");
    }
    }//GEN-LAST:event_btnGrafikActionPerformed

    private void dcDariTanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dcDariTanggalMouseClicked
       dcDariTanggal.getCalendarButton().doClick();
    }//GEN-LAST:event_dcDariTanggalMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportData;
    private javax.swing.JButton btnGrafik;
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


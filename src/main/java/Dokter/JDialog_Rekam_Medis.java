// no-compact
package Dokter;

import Database.KoneksiDatabase;
import Resepsionis.JPanel_Informasi_Dokter;
import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JDialog_Rekam_Medis extends javax.swing.JDialog {

    // Variabel
    private String idKunjungan;

    private DefaultTableModel modelResep;

    // Constructor utama
    public JDialog_Rekam_Medis(java.awt.Frame parent, boolean modal,
                               String idKunjungan, String idPasien, String namaPasien) {
        super(parent, modal);
        initComponents();
        loadDataObat(); 

        this.idKunjungan = idKunjungan;

        lblNamaPasien.setText(namaPasien);
        lblIdPasien.setText(idPasien);
        lblIdKunjungan.setText(idKunjungan);

        initCustomComponents();
        loadDataObat();
    }

    // Constructor sekunder
    public JDialog_Rekam_Medis(java.awt.Frame parent, boolean modal,
                               JPanel_Informasi_Dokter panelManajemen, String idDokter) {
        super(parent, modal);
        initComponents();

    }

    private void initCustomComponents() {
        setLocationRelativeTo(null);

        // Spinner
        spinnerJumlah.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        // Tabel resep
        modelResep = (DefaultTableModel) tblResep.getModel();
        modelResep.setRowCount(0);  // hapus dummy row

        // Sembunyikan kolom ID obat
        tblResep.getColumnModel().getColumn(0).setMinWidth(0);
        tblResep.getColumnModel().getColumn(0).setMaxWidth(0);
        tblResep.getColumnModel().getColumn(0).setWidth(0);

        // Placeholder
        txtDosis.putClientProperty("JTextField.placeholderText", "Contoh: 3 x 1 hari sesudah makan");
    
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        scrollDialog = new javax.swing.JScrollPane();
        panelForm = new javax.swing.JPanel();
        panelInfoPasien = new javax.swing.JPanel();
        lblNamaPasien = new javax.swing.JLabel();
        lblIdPasien = new javax.swing.JLabel();
        lblIdKunjungan = new javax.swing.JLabel();
        lblIdKunjungan1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAnamnesa = new javax.swing.JTextArea();
        lblIdKunjungan2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiagnosa = new javax.swing.JTextArea();
        panelResep = new javax.swing.JPanel();
        lblNamaPasien1 = new javax.swing.JLabel();
        comboObat = new javax.swing.JComboBox<>();
        lblNamaPasien2 = new javax.swing.JLabel();
        spinnerJumlah = new javax.swing.JSpinner();
        lblNamaPasien3 = new javax.swing.JLabel();
        txtDosis = new javax.swing.JTextField();
        btnTambahObat = new javax.swing.JButton();
        scrollPaneTabel = new javax.swing.JScrollPane();
        tblResep = new javax.swing.JTable();
        btnHapusObat = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnSimpanRekamMedis = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Pemeriksaan Rekam Medis");
        setModal(true);

        panelForm.setBackground(new java.awt.Color(255, 255, 255));
        panelForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelInfoPasien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Pasien", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N

        lblNamaPasien.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNamaPasien.setText("Nama Pasien");

        lblIdPasien.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblIdPasien.setText("ID Pasien");

        lblIdKunjungan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblIdKunjungan.setText("ID Kunjungan");

        javax.swing.GroupLayout panelInfoPasienLayout = new javax.swing.GroupLayout(panelInfoPasien);
        panelInfoPasien.setLayout(panelInfoPasienLayout);
        panelInfoPasienLayout.setHorizontalGroup(
            panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPasienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNamaPasien)
                    .addComponent(lblIdPasien)
                    .addComponent(lblIdKunjungan))
                .addContainerGap(742, Short.MAX_VALUE))
        );
        panelInfoPasienLayout.setVerticalGroup(
            panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPasienLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblNamaPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblIdPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblIdKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblIdKunjungan1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblIdKunjungan1.setText("Anamnesa / Keluhan Pasien");

        txtAnamnesa.setColumns(20);
        txtAnamnesa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtAnamnesa.setLineWrap(true);
        txtAnamnesa.setRows(5);
        txtAnamnesa.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtAnamnesa);

        lblIdKunjungan2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblIdKunjungan2.setText("Diagnosa / Pemeriksaan Fisik");

        txtDiagnosa.setColumns(20);
        txtDiagnosa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDiagnosa.setLineWrap(true);
        txtDiagnosa.setRows(5);
        txtDiagnosa.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtDiagnosa);

        panelResep.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resep Obat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N

        lblNamaPasien1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNamaPasien1.setText("Obat");

        lblNamaPasien2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNamaPasien2.setText("Jumlah");

        lblNamaPasien3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNamaPasien3.setText("Dosis");

        txtDosis.setText("3 x 1 hari");

        btnTambahObat.setBackground(new java.awt.Color(50, 120, 220));
        btnTambahObat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnTambahObat.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahObat.setText("➕  Tambah ke Resep");
        btnTambahObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahObatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelResepLayout = new javax.swing.GroupLayout(panelResep);
        panelResep.setLayout(panelResepLayout);
        panelResepLayout.setHorizontalGroup(
            panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelResepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelResepLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTambahObat))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelResepLayout.createSequentialGroup()
                        .addComponent(lblNamaPasien1)
                        .addGap(33, 33, 33)
                        .addComponent(comboObat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelResepLayout.createSequentialGroup()
                        .addGroup(panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamaPasien2)
                            .addComponent(lblNamaPasien3))
                        .addGap(18, 18, 18)
                        .addGroup(panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerJumlah)
                            .addComponent(txtDosis))))
                .addGap(33, 33, 33))
        );
        panelResepLayout.setVerticalGroup(
            panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResepLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNamaPasien1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboObat, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNamaPasien2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNamaPasien3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnTambahObat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tblResep.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tblResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"D001", "Paracetamol", "3", "3 x 1 hari"},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Obat", "Nama Obat", "Jumlah", "Dosis"
            }
        ));
        tblResep.setGridColor(new java.awt.Color(224, 224, 224));
        tblResep.setRowHeight(30);
        tblResep.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblResep.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblResep.setShowGrid(true);
        scrollPaneTabel.setViewportView(tblResep);

        btnHapusObat.setBackground(new java.awt.Color(220, 53, 69));
        btnHapusObat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnHapusObat.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusObat.setText("❌  Hapus dari Resep");
        btnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusObatActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        btnSimpanRekamMedis.setBackground(new java.awt.Color(50, 120, 220));
        btnSimpanRekamMedis.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnSimpanRekamMedis.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanRekamMedis.setText("Simpan Rekam Medis & Selesaikan Kunjungan");
        btnSimpanRekamMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanRekamMedisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(scrollPaneTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addComponent(panelResep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(lblIdKunjungan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(lblIdKunjungan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpanRekamMedis))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addComponent(btnHapusObat)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelInfoPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblIdKunjungan1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblIdKunjungan2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelResep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrollPaneTabel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnHapusObat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpanRekamMedis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        scrollDialog.setViewportView(panelForm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusObatActionPerformed
        int row = tblResep.getSelectedRow();
        if (row != -1) {
            modelResep.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih obat yang ingin dihapus.");
        }       
    }//GEN-LAST:event_btnHapusObatActionPerformed

    private void btnSimpanRekamMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanRekamMedisActionPerformed
        String anamnesa = txtAnamnesa.getText();
        String diagnosa = txtDiagnosa.getText();

        if (anamnesa.trim().isEmpty() || diagnosa.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Anamnesa dan Diagnosa wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;

        try {
            // 1. Dapatkan koneksi dan Matikan autocommit
            conn = KoneksiDatabase.getConnection();
            conn.setAutoCommit(false);

            // -----------------------------------------------------------------
            // STEP A: Insert Rekam Medis (DENGAN PERBAIKAN KOLOM)
            // -----------------------------------------------------------------
            // Query: 4 placeholder + NOW()
            String sqlRM = "INSERT INTO rekam_medis (kunjungan_id, anamnesa, pemeriksaan_fisik, diagnosa, tindakan) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psRM = conn.prepareStatement(sqlRM, Statement.RETURN_GENERATED_KEYS)) {
                psRM.setString(1, this.idKunjungan);
                psRM.setString(2, anamnesa);
                psRM.setString(3, diagnosa); // Mengisi pemeriksaan_fisik dengan teks diagnosa agar tidak kosong
                psRM.setString(4, diagnosa);
                psRM.setString(5, "-");      // Mengisi tindakan dengan strip (-) agar tidak error
                psRM.executeUpdate();
                int idRekamMedis;
                try ( // Ambil ID Rekam Medis
                        ResultSet rsRM = psRM.getGeneratedKeys()) {
                    idRekamMedis = rsRM.next() ? rsRM.getInt(1) : 0;
                }
                // -----------------------------------------------------------------
                // STEP B: Insert Resep & Detail (Jika ada obat)
                // -----------------------------------------------------------------
                if (modelResep.getRowCount() > 0) {
                    int idResep;
                    try ( // 1. Buat Header Resep
                            PreparedStatement psResep = conn.prepareStatement(
                                    "INSERT INTO resep (rekam_medis_id, tanggal_resep, status_resep) VALUES (?, NOW(), ?)",
                                    Statement.RETURN_GENERATED_KEYS
                            )) {
                        psResep.setInt(1, idRekamMedis);
                        psResep.setString(2, "Belum Diambil"); // Status Resep
                        psResep.executeUpdate();
                        idResep = 0;
                        try (ResultSet rsResep = psResep.getGeneratedKeys()) {
                            if (rsResep.next()) {
                                idResep = rsResep.getInt(1);
                            }
                        }
                    }
                    
                    // 2. Siapkan Query Detail, Stok, dan Ambil Harga
                    String sqlDetail = "INSERT INTO detail_resep (resep_id, obat_id, jumlah, dosis, subtotal_harga) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement psStok;
                    PreparedStatement psGetHarga;
                    try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                        String sqlStok = "UPDATE obat SET stok = stok - ? WHERE obat_id = ?";
                        psStok = conn.prepareStatement(sqlStok);
                        // Query untuk mengambil harga jual obat
                        String sqlGetHarga = "SELECT harga_jual FROM obat WHERE obat_id = ?"; // PASTIKAN KOLOM HARGA ANDA BERNAMA 'harga_jual'
                        psGetHarga = conn.prepareStatement(sqlGetHarga);
                        // 3. Looping Data di Tabel
                        for (int i = 0; i < modelResep.getRowCount(); i++) {
                            String idObat = modelResep.getValueAt(i, 0).toString();
                            int jumlah = Integer.parseInt(modelResep.getValueAt(i, 2).toString());
                            String dosis = modelResep.getValueAt(i, 3).toString();
                            
                            // Perhitungan Harga (FIX: Deklarasi subtotalHarga)
                            double hargaSatuan = 0.0;
                            psGetHarga.setString(1, idObat);
                            try (ResultSet rsHarga = psGetHarga.executeQuery()) {
                                if (rsHarga.next()) {
                                    hargaSatuan = rsHarga.getDouble("harga_jual");
                                }
                            }
                            double subtotalHarga = hargaSatuan * jumlah; // Variabel dideklarasikan di sini
                            
                            // Batch Insert Detail
                            psDetail.setInt(1, idResep);
                            psDetail.setString(2, idObat);
                            psDetail.setInt(3, jumlah);
                            psDetail.setString(4, dosis);
                            psDetail.setDouble(5, subtotalHarga); // Kini subtotalHarga ditemukan
                            psDetail.addBatch();
                            
                            // Batch Update Stok
                            psStok.setInt(1, jumlah);
                            psStok.setString(2, idObat);
                            psStok.addBatch();
                        }   // Eksekusi Batch
                        psDetail.executeBatch();
                        psStok.executeBatch();
                        // Tutup PreparedStatement yang digunakan di loop
                    }
                    psStok.close();
                    psGetHarga.close();
                }
                // Tutup PreparedStatement Rekam Medis
            }
            
            // -----------------------------------------------------------------
            // STEP C: Update Status Kunjungan
            // -----------------------------------------------------------------
            String sqlUpdateKunjungan = "UPDATE kunjungan SET status_kunjungan = 'Selesai' WHERE kunjungan_id = ?";
            try (PreparedStatement psKunjungan = conn.prepareStatement(sqlUpdateKunjungan)) {
                psKunjungan.setString(1, this.idKunjungan);
                psKunjungan.executeUpdate();
            }

            // -----------------------------------------------------------------
            // STEP D: Commit Transaksi (Simpan Permanen)
            // -----------------------------------------------------------------
            conn.commit();
            
            JOptionPane.showMessageDialog(this, "Rekam medis berhasil disimpan!");
            this.dispose(); // Tutup form

        } catch (SQLException e) {
            // Jika ada error, ROLLBACK (Batalkan semua perubahan)
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
            JOptionPane.showMessageDialog(this, "GAGAL: Terjadi kesalahan database: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Cek console output untuk detail error
        } catch (HeadlessException | NumberFormatException e) {
             if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
            JOptionPane.showMessageDialog(this, "GAGAL: Terjadi kesalahan umum: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            // Kembalikan ke mode auto commit
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException ex) {}
                 try { conn.close(); } catch (SQLException ex) {} // Pastikan koneksi ditutup
            }
        }                                         
    }//GEN-LAST:event_btnSimpanRekamMedisActionPerformed

    private void btnTambahObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahObatActionPerformed
        ObatItem item = (ObatItem) comboObat.getSelectedItem();
    int jumlah = (Integer) spinnerJumlah.getValue();
    String dosis = txtDosis.getText();

    if (dosis.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Dosis tidak boleh kosong!");
        return;
    }

    modelResep.addRow(new Object[]{
        item.getId(),
        item.getNama(),
        jumlah,
        dosis
    });

    txtDosis.setText("");
    spinnerJumlah.setValue(1);
    }//GEN-LAST:event_btnTambahObatActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapusObat;
    private javax.swing.JButton btnSimpanRekamMedis;
    private javax.swing.JButton btnTambahObat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<ObatItem> comboObat;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblIdKunjungan;
    private javax.swing.JLabel lblIdKunjungan1;
    private javax.swing.JLabel lblIdKunjungan2;
    private javax.swing.JLabel lblIdPasien;
    private javax.swing.JLabel lblNamaPasien;
    private javax.swing.JLabel lblNamaPasien1;
    private javax.swing.JLabel lblNamaPasien2;
    private javax.swing.JLabel lblNamaPasien3;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelInfoPasien;
    private javax.swing.JPanel panelResep;
    private javax.swing.JScrollPane scrollDialog;
    private javax.swing.JScrollPane scrollPaneTabel;
    private javax.swing.JSpinner spinnerJumlah;
    private javax.swing.JTable tblResep;
    private javax.swing.JTextArea txtAnamnesa;
    private javax.swing.JTextArea txtDiagnosa;
    private javax.swing.JTextField txtDosis;
    // End of variables declaration//GEN-END:variables

    private void loadDataObat() {
    comboObat.removeAllItems();
    try {
        Connection conn = KoneksiDatabase.getConnection();
        String sql = "SELECT obat_id, nama_obat, harga_jual FROM obat WHERE stok > 0 ORDER BY nama_obat ASC";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            comboObat.addItem(new ObatItem(
                rs.getString("obat_id"),
                rs.getString("nama_obat"),
                rs.getInt("harga_jual")
            ));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Gagal memuat data obat: " + e.getMessage());
    }
}

}
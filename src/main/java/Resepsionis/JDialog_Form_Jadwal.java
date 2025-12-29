/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Resepsionis;

import Database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class JDialog_Form_Jadwal extends javax.swing.JDialog {
    
    
    class DokterItem {
        public String id;
        public String nama;

        public DokterItem(String id, String nama) {
            this.id = id;
            this.nama = nama;
        }

        // Ini adalah teks yang akan ditampilkan di JComboBox
        @Override
        public String toString() {
            return nama;
        }
    }
    
    // Atribut
    Connection conn = KoneksiDatabase.getConnection();
    
    /**
     * Creates new form JDialog_Form_Jadwal
     */
    private String idJadwalToEdit = null;
    // Tambahkan juga referensi ke panel tabel (opsional tapi berguna)
    
    public JDialog_Form_Jadwal(java.awt.Frame parent, boolean modal, JPanel_Manajemen_Jadwal panelManajemen) {
        super(parent, modal);
        initComponents();

        // Panggil method kustomisasi
        initCustomComponents();
        loadComboBoxDokter();
        
        txtIDJadwal.setText("auto");
        txtIDJadwal.setEnabled(false);
        
        javax.swing.JSpinner.DateEditor editorMulai = new javax.swing.JSpinner.DateEditor(spinnerJamMulai, "HH:mm");
        spinnerJamMulai.setEditor(editorMulai);
        spinnerJamMulai.setValue(new java.util.Date()); // Set jam sekarang

        // Setting Format Spinner Jam Selesai
        javax.swing.JSpinner.DateEditor editorSelesai = new javax.swing.JSpinner.DateEditor(spinnerJamSelesai, "HH:mm");
        spinnerJamSelesai.setEditor(editorSelesai);
        spinnerJamSelesai.setValue(new java.util.Date());
        
        txtIDJadwal.setText("auto");
        txtIDJadwal.setEnabled(false);
        
        javax.swing.JSpinner.DateEditor editorMulai = new javax.swing.JSpinner.DateEditor(spinnerJamMulai, "HH:mm");
        spinnerJamMulai.setEditor(editorMulai);
        spinnerJamMulai.setValue(new java.util.Date()); // Set jam sekarang

        // Setting Format Spinner Jam Selesai
        javax.swing.JSpinner.DateEditor editorSelesai = new javax.swing.JSpinner.DateEditor(spinnerJamSelesai, "HH:mm");
        spinnerJamSelesai.setEditor(editorSelesai);
        spinnerJamSelesai.setValue(new java.util.Date());
        
    }
    
    public JDialog_Form_Jadwal(java.awt.Frame parent, boolean modal, JPanel_Manajemen_Jadwal panelManajemen, String idJadwal) {
        this(parent, modal, panelManajemen); // Panggil constructor pertama

        this.idJadwalToEdit = idJadwal; // Simpan ID untuk diedit

        // Ubah tampilan form untuk mode Edit
        lblJudul.setText("Edit Jadwal Praktik");
        txtIDJadwal.setText(idJadwal);
        txtIDJadwal.setEnabled(false); // ID tidak bisa diubah

        // Panggil method untuk memuat data lama
        loadDataForEdit();
    }
    
    private void initCustomComponents() {
        setLocationRelativeTo(null);

        // --- KONFIGURASI JSpinner WAKTU ---
        java.util.Date date = new java.util.Date();
        javax.swing.SpinnerDateModel smMulai = new javax.swing.SpinnerDateModel(date, null, null, java.util.Calendar.HOUR_OF_DAY);
        spinnerJamMulai.setModel(smMulai);
        javax.swing.JSpinner.DateEditor deMulai = new javax.swing.JSpinner.DateEditor(spinnerJamMulai, "HH:mm");
        spinnerJamMulai.setEditor(deMulai);

        // Lakukan hal yang sama untuk spinnerJamSelesai
        javax.swing.SpinnerDateModel smSelesai = new javax.swing.SpinnerDateModel(date, null, null, java.util.Calendar.HOUR_OF_DAY);
        spinnerJamSelesai.setModel(smSelesai);
        javax.swing.JSpinner.DateEditor deSelesai = new javax.swing.JSpinner.DateEditor(spinnerJamSelesai, "HH:mm");
        spinnerJamSelesai.setEditor(deSelesai);
    }
    
    private void loadComboBoxDokter() {
        comboDokter.removeAllItems();

        // Query JOIN untuk ambil ID Dokter dan Nama User
        String sql = "SELECT d.dokter_id, u.nama_lengkap " +
                     "FROM dokter d JOIN user u ON d.user_id = u.user_id " +
                     "ORDER BY u.nama_lengkap ASC";
        
        try {
            try (Statement stmt = this.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql);) {

                while (rs.next()) {
                    String id = rs.getString("dokter_id");
                    String nama = rs.getString("nama_lengkap");
                    comboDokter.addItem(new DokterItem(id, nama));
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat dokter: " + e.getMessage());
        }
    }
    

    private void loadDataForEdit() {
        // Pastikan query menggunakan nama kolom yang benar (jadwal_id)
        String sql = "SELECT * FROM jadwal_praktik WHERE jadwal_id = ?";
        
        try {
            
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {

                stmt.setString(1, idJadwalToEdit); // Variabel ID yang dikirim dari tabel

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // 1. SET HARI (Mudah, tinggal string)
                        String hariDB = rs.getString("hari");
                        comboHari.setSelectedItem(hariDB);

                        // 2. SET JAM (Perlu parsing dari String "09:00:00" ke Date)
                        java.text.SimpleDateFormat formatJam = new java.text.SimpleDateFormat("HH:mm:ss");
                        try {
                            String jamMulaiStr = rs.getString("jam_mulai");
                            String jamSelesaiStr = rs.getString("jam_selesai");

                            spinnerJamMulai.setValue(formatJam.parse(jamMulaiStr));
                            spinnerJamSelesai.setValue(formatJam.parse(jamSelesaiStr));
                        } catch (SQLException | ParseException e) {
                            System.out.println("Error parsing jam: " + e.getMessage());
                        }

                        // 3. SET DOKTER (Agak Tricky)
                        // Kita harus mencari DokterItem di dalam ComboBox yang ID-nya cocok
                        String idDokterDB = rs.getString("dokter_id");

                        // Loop semua isi ComboBox untuk cari yang cocok
                        for (int i = 0; i < comboDokter.getItemCount(); i++) {
                            DokterItem item = (DokterItem) comboDokter.getItemAt(i);
                            if (item.id.equals(idDokterDB)) {
                                comboDokter.setSelectedIndex(i);
                                break; // Ketemu, berhenti looping
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data edit: " + e.getMessage());
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

        panelForm = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtIDJadwal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        comboDokter = new javax.swing.JComboBox();
        comboDokter = new javax.swing.JComboBox();
        comboHari = new javax.swing.JComboBox<>();
        spinnerJamMulai = new javax.swing.JSpinner();
        spinnerJamSelesai = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Data Pasien");
        setModal(true);
        setResizable(false);

        panelForm.setBackground(new java.awt.Color(255, 255, 255));
        panelForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelForm.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 20)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(0, 0, 0));
        lblJudul.setText("Form Jadwal Praktik");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID Jadwal");

        txtIDJadwal.setEditable(false);
        txtIDJadwal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtIDJadwal.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Dokter");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Hari");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Jam Mulai");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Jam Selesai");

        btnSimpan.setBackground(new java.awt.Color(50, 120, 220));
        btnSimpan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(220, 53, 69));
        btnBatal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Batal");
        btnBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        comboDokter.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        comboDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dokter1", "Dokter2", "Dokter3" }));
        comboDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dokter1", "Dokter2", "Dokter3" }));

        comboHari.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        comboHari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu" }));
        comboHari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu" }));

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addComponent(lblJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 229, Short.MAX_VALUE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(0, 218, Short.MAX_VALUE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel6))
                        .addGap(37, 37, 37)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinnerJamMulai)
                            .addComponent(comboHari, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboDokter, javax.swing.GroupLayout.Alignment.TRAILING, 0, 308, Short.MAX_VALUE)
                            .addComponent(txtIDJadwal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerJamSelesai))))
                .addContainerGap())
            .addGroup(panelFormLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addComponent(lblJudul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIDJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerJamMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerJamSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        DokterItem dokterDipilih = (DokterItem) comboDokter.getSelectedItem();
        String hari = comboHari.getSelectedItem().toString();
    
        // Ambil Jam dari JSpinner (Asumsi pakai SpinnerDateModel)
        // Jika pakai JTextField, cukup: txtJamMulai.getText()
        java.util.Date dateMulai = (java.util.Date) spinnerJamMulai.getValue();
        java.util.Date dateSelesai = (java.util.Date) spinnerJamSelesai.getValue();
    
        // Format ke String "HH:mm:ss" untuk MySQL
        java.text.SimpleDateFormat formatJam = new java.text.SimpleDateFormat("HH:mm:ss");
        String jamMulai = formatJam.format(dateMulai);
        String jamSelesai = formatJam.format(dateSelesai);
    
        // 2. Validasi Sederhana
        if (dokterDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih dokter terlebih dahulu!");
            return;
        }
    
        // Cek apakah jam selesai lebih awal dari jam mulai (Logika terbalik)
        if (dateSelesai.before(dateMulai)) {
             JOptionPane.showMessageDialog(this, "Jam Selesai tidak boleh lebih awal dari Jam Mulai!");
             return;
        }

        // 3. Proses Simpan Database
        String sql;
        
        if (idJadwalToEdit == null) {
        // --- MODE TAMBAH BARU ---
            sql = "INSERT INTO jadwal_praktik (dokter_id, hari, jam_mulai, jam_selesai) VALUES (?, ?, ?, ?)";
        } else {
            // --- MODE EDIT (UPDATE) ---
            // Gunakan jadwal_id sebagai kunci update
            sql = "UPDATE jadwal_praktik SET dokter_id=?, hari=?, jam_mulai=?, jam_selesai=? WHERE jadwal_id=?";
        }
        
        try {
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {

                stmt.setString(1, dokterDipilih.id);
                stmt.setString(2, hari);
                stmt.setString(3, jamMulai);
                stmt.setString(4, jamSelesai);

                if (idJadwalToEdit != null) {
                    stmt.setString(5, idJadwalToEdit); 
                }

                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Jadwal berhasil ditambahkan!");
                this.dispose(); // Tutup Form Dialog

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan jadwal: " + e.getMessage());
        }
      
      
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox comboDokter;
    private javax.swing.JComboBox comboDokter;
    private javax.swing.JComboBox<String> comboHari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JPanel panelForm;
    private javax.swing.JSpinner spinnerJamMulai;
    private javax.swing.JSpinner spinnerJamSelesai;
    private javax.swing.JTextField txtIDJadwal;
    // End of variables declaration//GEN-END:variables
}

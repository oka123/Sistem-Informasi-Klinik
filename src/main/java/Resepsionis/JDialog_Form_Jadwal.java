/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Resepsionis;

//import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class JDialog_Form_Jadwal extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JDialog_Form_Jadwal.class.getName());
    
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

    /**
     * Creates new form JDialog_Form_Jadwal
     */
    private String idJadwalToEdit = null;
    // Tambahkan juga referensi ke panel tabel (opsional tapi berguna)
    private JPanel_Manajemen_Jadwal panelManajemen;
    private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
    
    public JDialog_Form_Jadwal(java.awt.Frame parent, boolean modal, JPanel_Manajemen_Jadwal panelManajemen) {
        super(parent, modal);
        initComponents();
        this.panelManajemen = panelManajemen; // Simpan referensi panel

        // Panggil method kustomisasi
        initCustomComponents();
        loadComboBoxDokter();

        // Set ID Jadwal (misal: auto-generate)
        txtIDJadwal.setText(generateNewJadwalID());
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
    try {
        // SQL: "SELECT id_dokter, nama_dokter FROM dokter ORDER BY nama_dokter"
        // ... (Eksekusi query) ...

//        while (rs.next()) {
//            // Tambahkan objek DokterItem ke combo box
//            comboDokter.addItem(new DokterItem(rs.getString("id_dokter"), rs.getString("nama_dokter")));
//        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data dokter: " + e.getMessage());
    }
}
    
    private String generateNewJadwalID() {
        // Logika Anda, misal "J-001"
        return "J001"; // Placeholder
    }

    private void loadDataForEdit() {
        try {
            // SQL: "SELECT * FROM jadwal WHERE id_jadwal = ?" (gunakan idJadwalToEdit)
            // ... (Eksekusi query) ...

//            if (rs.next()) {
//                // Set Hari
//                comboHari.setSelectedItem(rs.getString("hari"));
//
//                // Set Waktu
//                spinnerJamMulai.setValue(sdf.parse(rs.getString("jam_mulai")));
//                spinnerJamSelesai.setValue(sdf.parse(rs.getString("jam_selesai")));
//
//                // Set Dokter (ini agak tricky)
//                String idDokterLama = rs.getString("id_dokter");
//                for (int i = 0; i < comboDokter.getItemCount(); i++) {
//                    DokterItem item = (DokterItem) comboDokter.getItemAt(i);
//                    if (item.id.equals(idDokterLama)) {
//                        comboDokter.setSelectedIndex(i);
//                        break;
//                    }
//                }
//            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data jadwal: " + e.getMessage());
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
        comboDokter = new javax.swing.JComboBox<>();
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
        comboDokter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dokter1", "Dokter2", "Dokter3" }));

        comboHari.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        comboHari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu" }));

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
        // TODO add your handling code here:
        try {
            // Ambil data dari form
//            String id = txtIDJadwal.getText();
//            String hari = comboHari.getSelectedItem().toString();

            // Ambil ID dari objek DokterItem
//            DokterItem dokterItem = (DokterItem) comboDokter.getSelectedItem();
//            String idDokter = dokterItem.id;

            // Format waktu dari JSpinner
//            String jamMulai = sdf.format(spinnerJamMulai.getValue());
//            String jamSelesai = sdf.format(spinnerJamSelesai.getValue());

//            if (idJadwalToEdit == null) {
                // Mode TAMBAH (INSERT)
                // SQL: "INSERT INTO jadwal (id_jadwal, id_dokter, hari, jam_mulai, jam_selesai) VALUES (?, ?, ?, ?, ?)"
//            } else {
                // Mode EDIT (UPDATE)
                // SQL: "UPDATE jadwal SET id_dokter = ?, hari = ?, jam_mulai = ?, jam_selesai = ? WHERE id_jadwal = ?"
//            }

            // ... (Eksekusi query) ...

            JOptionPane.showMessageDialog(this, "Jadwal berhasil disimpan!");
            panelManajemen.loadDataJadwal(); // Refresh tabel
            this.dispose(); // Tutup form

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
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
    private javax.swing.JComboBox<String> comboDokter;
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

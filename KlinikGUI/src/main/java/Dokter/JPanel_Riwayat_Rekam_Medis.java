/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Dokter;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Frame;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public final class JPanel_Riwayat_Rekam_Medis extends javax.swing.JPanel {
    
    private final ImageIcon searchIcon = new ImageIcon(getClass().getResource("/search.png"));
    
    /**
     * Creates new form JPanel_Manajemen_Pasien
     */
    public JPanel_Riwayat_Rekam_Medis() {
        initComponents();
        
        txtCariPasien.putClientProperty("JTextField.placeholderText", "Cari pasien...");
        
        // Menjadwalkan update gambar setelah ukuran tombol tersedia
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Sesuaikan ukuran gambar
                btnCariPasien.setIcon(new ImageIcon(searchIcon.getImage().getScaledInstance(btnCariPasien.getWidth(), btnCariPasien.getHeight(), Image.SCALE_SMOOTH)));
            }
        });
        
        tblRiwayat.getColumnModel().getColumn(0).setMaxWidth(0);

//        loadDataToTable(null);
    }
    
    class PasienItem {
        public String id;
        public String nama;
        public String tglLahir;

        public PasienItem(String id, String nama, String tglLahir) {
            this.id = id;
            this.nama = nama;
            this.tglLahir = tglLahir;
        }

        // Ini yang akan ditampilkan di dialog pilihan
        @Override
        public String toString() {
            return nama + " (ID: " + id + ", Lahir: " + tglLahir + ")";
        }
    }

    public void loadDataToTable() {
        
    }
    
    private void loadRiwayat(String idPasien, String namaPasien) {
        // 1. Set judul TitledBorder di panel kiri
        javax.swing.border.TitledBorder border = (javax.swing.border.TitledBorder) panelMaster.getBorder();
        border.setTitle("Riwayat Kunjungan: " + namaPasien);
        panelMaster.repaint(); // Refresh border

        // 2. Bersihkan detail panel (kanan)
        clearDetailPanel();

        // 3. Muat data ke tblRiwayat
        DefaultTableModel model = (DefaultTableModel) tblRiwayat.getModel();
        model.setRowCount(0);

        // SQL: Query JOIN kunjungan, rekam_medis, dan dokter
        // "SELECT r.id_rekam_medis, k.tanggal_kunjungan, d.nama_dokter, r.diagnosa " +
        // "FROM kunjungan k " +
        // "JOIN rekam_medis r ON k.id_kunjungan = r.id_kunjungan " +
        // "JOIN dokter d ON k.dokter_id = d.id_dokter " +
        // "WHERE k.pasien_id = ? ORDER BY k.tanggal_kunjungan DESC"

        // ... (Eksekusi query dengan idPasien) ...
        // while (rs.next()) {
        //    model.addRow(new Object[]{
        //        rs.getString("id_rekam_medis"),
        //        rs.getDate("tanggal_kunjungan"),
        //        rs.getString("nama_dokter"),
        //        rs.getString("diagnosa")
        //    });
        // }
    }
    
    private void loadDetail(String idRekamMedis) {
        try {
            // 1. Ambil data rekam medis
            // SQL: "SELECT anamnesa, diagnosa FROM rekam_medis WHERE id_rekam_medis = ?"
            // ... (Eksekusi query 1) ...
            // if (rs.next()) {
            //    txtDetailAnamnesa.setText(rs.getString("anamnesa"));
            //    txtDetailDiagnosa.setText(rs.getString("diagnosa"));
            // }

            // 2. Ambil data resep
            DefaultTableModel modelResep = (DefaultTableModel) tblDetailResep.getModel();
            modelResep.setRowCount(0);

            // SQL: Query JOIN resep, detail_resep, dan obat
            // "SELECT o.nama_obat, dr.jumlah, dr.dosis " +
            // "FROM resep r " +
            // "JOIN detail_resep dr ON r.resep_id = dr.resep_id " +
            // "JOIN obat o ON dr.obat_id = o.obat_id " +
            // "WHERE r.rekam_medis_id = ?"

            // ... (Eksekusi query 2 dengan idRekamMedis) ...
            // while (rs2.next()) {
            //    modelResep.addRow(new Object[]{
            //        rs2.getString("nama_obat"),
            //        rs2.getInt("jumlah"),
            //        rs2.getString("dosis")
            //    });
            // }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat detail rekam medis: " + e.getMessage());
        }
    }
    
    private void clearDetailPanel() {
        txtDetailAnamnesa.setText("");
        txtDetailDiagnosa.setText("");
        ((DefaultTableModel) tblDetailResep.getModel()).setRowCount(0);
        ((DefaultTableModel) tblRiwayat.getModel()).setRowCount(0);

        javax.swing.border.TitledBorder border = (javax.swing.border.TitledBorder) panelMaster.getBorder();
        border.setTitle("Riwayat Kunjungan Pasien");
        panelMaster.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJudul = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelPencarian = new javax.swing.JPanel();
        btnCariPasien = new javax.swing.JButton();
        txtCariPasien = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelMaster = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRiwayat = new javax.swing.JTable();
        panelDetail = new javax.swing.JPanel();
        lblNamaPasien1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDetailAnamnesa = new javax.swing.JTextArea();
        lblNamaPasien2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDetailDiagnosa = new javax.swing.JTextArea();
        lblNamaPasien3 = new javax.swing.JLabel();
        scrollPaneTabel1 = new javax.swing.JScrollPane();
        tblDetailResep = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(51, 51, 51));
        lblJudul.setText("Riwayat Rekam Medis Pasien");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        panelPencarian.setBackground(new java.awt.Color(255, 255, 255));

        btnCariPasien.setBackground(new java.awt.Color(75, 167, 87));
        btnCariPasien.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnCariPasien.setForeground(new java.awt.Color(255, 255, 255));
        btnCariPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnCariPasien.setContentAreaFilled(false);
        btnCariPasien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCariPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPasienActionPerformed(evt);
            }
        });

        jSplitPane1.setDividerLocation(450);

        panelMaster.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Riwayat Kunjungan Pasien", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        panelMaster.setLayout(new java.awt.BorderLayout());
        panelMaster.add(jScrollPane1, java.awt.BorderLayout.PAGE_START);

        tblRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"R001", "23-07-2021", "Abc", "Penyakit"},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Rekam Medis", "Tanggal", "Nama Dokter", "Diagnosa"
            }
        ));
        tblRiwayat.setRowHeight(30);
        tblRiwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRiwayatMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblRiwayat);

        panelMaster.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(panelMaster);

        lblNamaPasien1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNamaPasien1.setText("Anamnesa / Keluhan");

        txtDetailAnamnesa.setEditable(false);
        txtDetailAnamnesa.setColumns(20);
        txtDetailAnamnesa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDetailAnamnesa.setLineWrap(true);
        txtDetailAnamnesa.setRows(5);
        txtDetailAnamnesa.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtDetailAnamnesa);

        lblNamaPasien2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNamaPasien2.setText("Diagnosa / Temuan Dokter:");

        txtDetailDiagnosa.setEditable(false);
        txtDetailDiagnosa.setColumns(20);
        txtDetailDiagnosa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDetailDiagnosa.setLineWrap(true);
        txtDetailDiagnosa.setRows(5);
        txtDetailDiagnosa.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txtDetailDiagnosa);

        lblNamaPasien3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNamaPasien3.setText("Resep yang Diberikan:");

        tblDetailResep.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tblDetailResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"D001", "dr. ABCD", "THT"},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nama Obat", "Jumlah", "Dosis"
            }
        ));
        tblDetailResep.setGridColor(new java.awt.Color(224, 224, 224));
        tblDetailResep.setRowHeight(30);
        tblDetailResep.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblDetailResep.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblDetailResep.setShowGrid(true);
        scrollPaneTabel1.setViewportView(tblDetailResep);

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNamaPasien3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamaPasien2, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNamaPasien1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4)
                    .addComponent(scrollPaneTabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblNamaPasien1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblNamaPasien2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblNamaPasien3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneTabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(panelDetail);

        javax.swing.GroupLayout panelPencarianLayout = new javax.swing.GroupLayout(panelPencarian);
        panelPencarian.setLayout(panelPencarianLayout);
        panelPencarianLayout.setHorizontalGroup(
            panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPencarianLayout.createSequentialGroup()
                .addContainerGap(694, Short.MAX_VALUE)
                .addComponent(txtCariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(panelPencarianLayout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        panelPencarianLayout.setVerticalGroup(
            panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPencarianLayout.createSequentialGroup()
                .addGroup(panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnCariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblJudul)
                .addContainerGap(653, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPencarian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblJudul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPasienActionPerformed
        // TODO add your handling code here:
        String searchTerm = txtCariPasien.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan ID atau Nama Pasien untuk dicari.");
            return;
        }

        java.util.List<PasienItem> hasilPasien = new java.util.ArrayList<>();

        // SQL: "SELECT pasien_id, nama_pasien, tanggal_lahir FROM pasien WHERE nama_pasien LIKE ? OR pasien_id LIKE ?"
        // ... (Eksekusi query dengan searchTerm) ...
        // while (rs.next()) {
        //    hasilPasien.add(new PasienItem(rs.getString("pasien_id"), rs.getString("nama_pasien"), rs.getString("tanggal_lahir")));
        // }

        // --- (Simulasi Data untuk Tes) ---
        hasilPasien.add(new PasienItem("P001", "Budi Santoso", "1990-05-10"));
        hasilPasien.add(new PasienItem("P007", "Budi Hartono", "1985-11-20"));
        // --- (Hapus Simulasi) ---

        if (hasilPasien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pasien tidak ditemukan.");
            clearDetailPanel();
        } else if (hasilPasien.size() == 1) {
            // Jika hanya 1 hasil, langsung muat riwayatnya
            PasienItem pasien = hasilPasien.get(0);
            loadRiwayat(pasien.id, pasien.nama);
        } else {
            // Jika banyak hasil, tampilkan dialog pilihan
            PasienItem pilihan = (PasienItem) JOptionPane.showInputDialog(
                    this,
                    "Ditemukan beberapa pasien, pilih satu:",
                    "Hasil Pencarian",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    hasilPasien.toArray(), // Array pilihan
                    hasilPasien.get(0)     // Pilihan default
            );

            if (pilihan != null) { // Jika user memilih (tidak menekan 'cancel')
                loadRiwayat(pilihan.id, pilihan.nama);
            }
        }
    }//GEN-LAST:event_btnCariPasienActionPerformed

    private void tblRiwayatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRiwayatMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblRiwayat.getSelectedRow();
        if (selectedRow == -1) return;

        // Ambil ID Rekam Medis dari kolom tersembunyi (index 0)
        String idRekamMedis = tblRiwayat.getValueAt(selectedRow, 0).toString();

        // Panggil method untuk mengisi panel detail (kanan)
        loadDetail(idRekamMedis);
    }//GEN-LAST:event_tblRiwayatMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariPasien;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblNamaPasien1;
    private javax.swing.JLabel lblNamaPasien2;
    private javax.swing.JLabel lblNamaPasien3;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelMaster;
    private javax.swing.JPanel panelPencarian;
    private javax.swing.JScrollPane scrollPaneTabel1;
    private javax.swing.JTable tblDetailResep;
    private javax.swing.JTable tblRiwayat;
    private javax.swing.JTextField txtCariPasien;
    private javax.swing.JTextArea txtDetailAnamnesa;
    private javax.swing.JTextArea txtDetailDiagnosa;
    // End of variables declaration//GEN-END:variables
}

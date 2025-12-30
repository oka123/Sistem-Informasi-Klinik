/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Dokter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Image;
import javax.swing.ImageIcon;
import Database.KoneksiDatabase;

public final class JPanel_Riwayat_Rekam_Medis extends javax.swing.JPanel {

    private final ImageIcon searchIcon = new ImageIcon(getClass().getResource("/search.png"));

    public JPanel_Riwayat_Rekam_Medis() {
        initComponents();
        initCustomSettings();
    }

    private void initCustomSettings() {
        txtCariPasien.putClientProperty("JTextField.placeholderText", "Cari nama atau ID pasien...");
        
        // Membersihkan tabel dari data dummy bawaan GUI Builder
        ((DefaultTableModel) tblRiwayat.getModel()).setRowCount(0);
        ((DefaultTableModel) tblDetailResep.getModel()).setRowCount(0);

        SwingUtilities.invokeLater(() -> {
            if (btnCariPasien.getWidth() > 0) {
                btnCariPasien.setIcon(new ImageIcon(searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            }
        });

        // Sembunyikan kolom ID Rekam Medis (indeks 0) tapi tetap bisa diakses datanya
        tblRiwayat.getColumnModel().getColumn(0).setMinWidth(0);
        tblRiwayat.getColumnModel().getColumn(0).setMaxWidth(0);
        tblRiwayat.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    // Helper class untuk menampung hasil pencarian
    class PasienItem {
        String id, nama, tglLahir;
        public PasienItem(String id, String nama, String tglLahir) {
            this.id = id; this.nama = nama; this.tglLahir = tglLahir;
        }
        @Override
        public String toString() {
            return nama + " (ID: " + id + " | Lahir: " + tglLahir + ")";
        }
    }

    private void loadRiwayat(String idPasien, String namaPasien) {
    DefaultTableModel model = (DefaultTableModel) tblRiwayat.getModel();
    model.setRowCount(0); 
    clearDetailFields();

    // Update judul panel
    javax.swing.border.TitledBorder border = (javax.swing.border.TitledBorder) panelMaster.getBorder();
    border.setTitle("Riwayat Kunjungan: " + namaPasien);
    panelMaster.repaint();

    // Perbaikan Query: Menggunakan u.username (Sesuaikan jika nama kolom di tabel user berbeda)
    String sql = "SELECT r.rekam_medis_id, k.tanggal_kunjungan, u.username AS nama_dokter, r.diagnosa " +
                 "FROM rekam_medis r " +
                 "JOIN kunjungan k ON r.kunjungan_id = k.kunjungan_id " +
                 "JOIN dokter d ON k.dokter_id = d.dokter_id " +
                 "JOIN user u ON d.user_id = u.user_id " + 
                 "WHERE k.pasien_id = ? ORDER BY k.tanggal_kunjungan DESC";

    try (Connection conn = KoneksiDatabase.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setString(1, idPasien);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("rekam_medis_id"),
                rs.getString("tanggal_kunjungan"),
                rs.getString("nama_dokter"),
                rs.getString("diagnosa")
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat riwayat: " + e.getMessage());
    }
}

    private void loadDetail(String idRekamMedis) {
        String sql = "SELECT anamnesa, pemeriksaan_fisik, diagnosa, tindakan FROM rekam_medis WHERE rekam_medis_id = ?";
        
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idRekamMedis);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                txtDetailAnamnesa.setText(rs.getString("anamnesa"));
                
                String infoLengkap = "--- PEMERIKSAAN FISIK ---\n" + rs.getString("pemeriksaan_fisik") + 
                                     "\n\n--- DIAGNOSA ---\n" + rs.getString("diagnosa") + 
                                     "\n\n--- TINDAKAN ---\n" + rs.getString("tindakan");
                txtDetailDiagnosa.setText(infoLengkap);
                
                // Muat resep menggunakan koneksi yang sama
                loadTabelResep(idRekamMedis, conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTabelResep(String idRekamMedis, Connection conn) {
        DefaultTableModel modelResep = (DefaultTableModel) tblDetailResep.getModel();
        modelResep.setRowCount(0);

        String sqlResep = "SELECT o.nama_obat, dr.jumlah, dr.dosis " +
                          "FROM resep r " +
                          "JOIN detail_resep dr ON r.resep_id = dr.resep_id " +
                          "JOIN obat o ON dr.obat_id = o.obat_id " +
                          "WHERE r.rekam_medis_id = ?";

        try (PreparedStatement pstResep = conn.prepareStatement(sqlResep)) {
            pstResep.setString(1, idRekamMedis);
            try (ResultSet rsResep = pstResep.executeQuery()) {
                while (rsResep.next()) {
                    modelResep.addRow(new Object[]{
                        rsResep.getString("nama_obat"),
                        rsResep.getString("jumlah"),
                        rsResep.getString("dosis")
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Error resep: " + e.getMessage());
        }
    }

    private void clearDetailFields() {
        txtDetailAnamnesa.setText("");
        txtDetailDiagnosa.setText("");
        ((DefaultTableModel) tblDetailResep.getModel()).setRowCount(0);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        String keyword = txtCariPasien.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan nama atau ID pasien.");
            return;
        }

        java.util.List<PasienItem> list = new java.util.ArrayList<>();
        String sql = "SELECT pasien_id, nama_pasien, tanggal_lahir FROM pasien WHERE nama_pasien LIKE ? OR pasien_id = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, keyword);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                list.add(new PasienItem(rs.getString("pasien_id"), rs.getString("nama_pasien"), rs.getString("tanggal_lahir")));
            }

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pasien tidak ditemukan.");
            } else if (list.size() == 1) {
                loadRiwayat(list.get(0).id, list.get(0).nama);
            } else {
                PasienItem pilihan = (PasienItem) JOptionPane.showInputDialog(this, "Pilih Pasien:", "Hasil Pencarian",
                        JOptionPane.QUESTION_MESSAGE, null, list.toArray(), list.get(0));
                if (pilihan != null) loadRiwayat(pilihan.id, pilihan.nama);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCariPasienActionPerformed

    private void tblRiwayatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRiwayatMouseClicked
        int selectedRow = tblRiwayat.getSelectedRow();
        if (selectedRow != -1) {
            String idRM = tblRiwayat.getValueAt(selectedRow, 0).toString();
            loadDetail(idRM);
        }
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

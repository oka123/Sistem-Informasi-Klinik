/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Manajemen;

import Database.KoneksiDatabase;
import Main.ThreadPoolManager;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public final class JPanel_Manajemen_Dokter extends javax.swing.JPanel implements Manajemen {
    
    // Atribut
    
    // Constructor
    public JPanel_Manajemen_Dokter() {
        initComponents();
        loadDataDokter(""); // Muat data awal
    }
    
    @Override
    public void loadDataDokter(String key) {
        // 1. UBAH KURSOR JADI LOADING
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnCari.setEnabled(false);

        // 2. PROSES BACKGROUND
        ThreadPoolManager.getInstance().submit(() -> {
            List<Object[]> dataList = new ArrayList<>();
            String errorMsg = null;

            // Query JOIN: Mengambil data spesifik dokter dan data umum dari tabel user
            String sql = "SELECT d.dokter_id, u.nama_lengkap, u.username, d.spesialisasi, u.no_telepon, u.alamat " +
                         "FROM dokter AS d " +
                         "JOIN user AS u ON d.user_id = u.user_id";

            boolean isSearch = (key != null && !key.trim().isEmpty());
            
            // Logika Pencarian (Sekarang bisa cari by Username juga)
            if (isSearch) {
                sql += " WHERE u.nama_lengkap LIKE ? OR u.username LIKE ? OR d.spesialisasi LIKE ?";
            }
            
            sql += " ORDER BY u.nama_lengkap ASC"; // Urutkan abjad nama

            try {
                Connection conn = KoneksiDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

                if (isSearch) {
                    String pola = "%" + key + "%";
                    pstmt.setString(1, pola);
                    pstmt.setString(2, pola);
                    pstmt.setString(3, pola);
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        // Simpan ke list sementara (Thread Safe)
                        dataList.add(new Object[]{
                            rs.getInt("dokter_id"),
                            rs.getString("nama_lengkap"),
                            rs.getString("username"),
                            rs.getString("spesialisasi"),
                            rs.getString("no_telepon"),
                            rs.getString("alamat")
                        });
                    }
                }
            } catch (SQLException e) {
                errorMsg = e.getMessage();
                e.printStackTrace();
            }

            // 3. UPDATE UI (KEMBALI KE UI THREAD)
            final String finalError = errorMsg;
            
            SwingUtilities.invokeLater(() -> {
                if (finalError != null) {
                    JOptionPane.showMessageDialog(this, "Gagal memuat data dokter: " + finalError, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    DefaultTableModel model = (DefaultTableModel) tblDokter.getModel();
                    
                    // Set Header Kolom secara Programmatic
                    String[] judulKolom = {"ID Dokter", "Nama Lengkap", "Username", "Spesialisasi", "No. Telepon", "Alamat"};
                    model.setColumnIdentifiers(judulKolom);
                    
                    // Bersihkan & Isi Data
                    model.setRowCount(0);
                    for (Object[] row : dataList) {
                        model.addRow(row);
                    }
                }
                
                // BALIKIN KURSOR KE NORMAL
                this.setCursor(Cursor.getDefaultCursor());
                btnCari.setEnabled(true);
            });

        });
//        DefaultTableModel model = (DefaultTableModel) tblDokter.getModel();
//        model.setRowCount(0);
//        
//        // Set Header Kolom secara Programmatic (Agar sesuai urutan yang diminta)
//        String[] judulKolom = {"ID Dokter", "Nama Lengkap", "Username", "Spesialisasi", "No. Telepon", "Alamat"};
//        model.setColumnIdentifiers(judulKolom);
//
//        // Query JOIN: Mengambil data spesifik dokter dan data umum dari tabel user
//        String sql = "SELECT d.dokter_id, u.nama_lengkap, u.username, d.spesialisasi, u.no_telepon, u.alamat " +
//                     "FROM dokter d " +
//                     "JOIN user u ON d.user_id = u.user_id";
//
//        boolean isSearch = (key != null && !key.trim().isEmpty());
//        
//        // Logika Pencarian (Sekarang bisa cari by Username juga)
//        if (isSearch) {
//            sql += " WHERE u.nama_lengkap LIKE ? OR u.username LIKE ? OR d.spesialisasi LIKE ?";
//        }
//        
//        sql += " ORDER BY u.nama_lengkap ASC"; // Urutkan abjad nama
//
//        try (Connection conn = new KoneksiDatabase().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            if (isSearch) {
//                String pola = "%" + key + "%";
//                pstmt.setString(1, pola);
//                pstmt.setString(2, pola);
//                pstmt.setString(3, pola);
//            }
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    model.addRow(new Object[]{
//                        rs.getInt("dokter_id"),
//                        rs.getString("nama_lengkap"),
//                        rs.getString("username"),
//                        rs.getString("spesialisasi"),
//                        rs.getString("no_telepon"),
//                        rs.getString("alamat")
//                    });
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Gagal memuat data dokter: " + e.getMessage());
//            e.printStackTrace();
//        }
    }
    
    // Method Hapus dengan Transaksi (Hapus Dokter dulu, baru User)
    @Override
    public void hapusDokterDanUser(int idDokter) {
        Connection conn = null;
        try {
            conn = KoneksiDatabase.getConnection();
            conn.setAutoCommit(false); 

            // Ambil user_id (Query pakai int)
            String sqlGetUserId = "SELECT user_id FROM dokter WHERE dokter_id = ?";
            int userId = -1;
            try (PreparedStatement pstmtGet = conn.prepareStatement(sqlGetUserId)) {
                pstmtGet.setInt(1, idDokter); // SET INT
                ResultSet rs = pstmtGet.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }

            // Hapus dokter (Query pakai int)
            String sqlDelDokter = "DELETE FROM dokter WHERE dokter_id = ?";
            try (PreparedStatement pstmtDelD = conn.prepareStatement(sqlDelDokter)) {
                pstmtDelD.setInt(1, idDokter); // SET INT
                pstmtDelD.executeUpdate();
            }

            // Hapus user
            if (userId != -1) {
                String sqlDelUser = "DELETE FROM user WHERE user_id = ?";
                try (PreparedStatement pstmtDelU = conn.prepareStatement(sqlDelUser)) {
                    pstmtDelU.setInt(1, userId);
                    pstmtDelU.executeUpdate();
                }
            }

            conn.commit(); 
            JOptionPane.showMessageDialog(this, "Data dokter berhasil dihapus.");
            loadDataDokter("");

        } catch (HeadlessException | SQLException e) {
            try { if (conn != null) conn.rollback(); } catch(SQLException ex){} 
            JOptionPane.showMessageDialog(this, "Gagal menghapus: " + e.getMessage());
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); } } catch(SQLException ex){}
        }
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelKontrol = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        scrollPaneTabel = new javax.swing.JScrollPane();
        tblDokter = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        panelKontrol.setBackground(new java.awt.Color(255, 255, 255));

        btnTambah.setBackground(new java.awt.Color(0, 123, 255));
        btnTambah.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("➕ Tambah Data");
        btnTambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(255, 130, 0));
        btnEdit.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("📝 Edit Data");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(220, 53, 69));
        btnHapus.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setText("❌  Hapus Data");
        btnHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tblDokter.setAutoCreateRowSorter(true);
        tblDokter.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tblDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Dokter Klinik", "Dokter", "Anak", "08123456789", "Jl. Abcd"},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Dokter", "Nama Lengkap", "Username", "Spesialisasi", "No. Telepon", "Alamat"
            }
        ));
        tblDokter.setGridColor(new java.awt.Color(224, 224, 224));
        tblDokter.setRowHeight(30);
        tblDokter.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblDokter.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblDokter.setShowGrid(true);
        scrollPaneTabel.setViewportView(tblDokter);

        javax.swing.GroupLayout panelKontrolLayout = new javax.swing.GroupLayout(panelKontrol);
        panelKontrol.setLayout(panelKontrolLayout);
        panelKontrolLayout.setHorizontalGroup(
            panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKontrolLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
                    .addGroup(panelKontrolLayout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        panelKontrolLayout.setVerticalGroup(
            panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKontrolLayout.createSequentialGroup()
                .addGroup(panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKontrolLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(scrollPaneTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
                    .addGroup(panelKontrolLayout.createSequentialGroup()
                        .addGroup(panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cari Dokter");

        btnCari.setBackground(new java.awt.Color(255, 255, 255));
        btnCari.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnCari.setForeground(new java.awt.Color(0, 0, 0));
        btnCari.setText("🔍");
        btnCari.setBorder(null);
        btnCari.setBorderPainted(false);
        btnCari.setContentAreaFilled(false);
        btnCari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(0, 123, 255));
        btnRefresh.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("🔄 Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh))
                    .addComponent(panelKontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(panelKontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        loadDataDokter(txtCari.getText());
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        // Mode TAMBAH (id null)
        JDialog_Form_Dokter form = new JDialog_Form_Dokter(parentFrame, true, null);
        form.setTitle("Tambah Dokter Baru");
        form.setVisible(true);

        loadDataDokter(""); // Refresh setelah tambah
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int selectedRow = tblDokter.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Mohon pilih baris data dokter yang ingin diubah.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Konversi index jika tabel sedang di-sort dan ambil ID yang terpilih
        int modelRow = tblDokter.convertRowIndexToModel(selectedRow);
        int idDokter = (int) tblDokter.getModel().getValueAt(modelRow, 0);

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        // Kirim ID dalam bentuk Integer
        JDialog_Form_Dokter formDokter = new JDialog_Form_Dokter(parentFrame, true, idDokter);
        formDokter.setTitle("Edit Data Dokter");
        formDokter.setVisible(true);
        
        loadDataDokter("");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int selectedRow = tblDokter.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Mohon pilih baris data dokter yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

//        String idStr = tblDokter.getValueAt(selectedRow, 0).toString();
//        int idDokter = Integer.parseInt(idStr); // KONVERSI KE INT

        int modelRow = tblDokter.convertRowIndexToModel(selectedRow);
        int idDokter = (int) tblDokter.getModel().getValueAt(modelRow, 0);
        String namaDokter = (String) tblDokter.getModel().getValueAt(modelRow, 1);

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus Dokter: '" + namaDokter + "' dan menghapus akun User-nya juga.\nLanjutkan?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            hapusDokterDanUser(idDokter); // Kirim int
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadDataDokter("");
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JScrollPane scrollPaneTabel;
    private javax.swing.JTable tblDokter;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}

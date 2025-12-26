/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Manajemen;

import Database.KoneksiDatabase;
import Main.ThreadPoolManager;
import java.awt.Cursor;
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

public class JPanel_Manajemen_Layanan extends javax.swing.JPanel implements Manajemen {
    
    // Atribut

    // Constructor
    public JPanel_Manajemen_Layanan() {
        initComponents();
        // Aktifkan sorting otomatis
        loadDataLayanan("");
    }
    
    @Override
    public void loadDataLayanan(String key) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnRefresh.setEnabled(false);
        btnCariLayanan.setEnabled(false);
        
        ThreadPoolManager.getInstance().submit(() -> {
            List<Object[]> dataList = new ArrayList<>();
            String errorMsg = null;

            String sql = "SELECT * FROM layanan";
            if (key != null && !key.trim().isEmpty()) {
                sql += " WHERE nama_layanan LIKE ?";
            }
            sql += " ORDER BY nama_layanan ASC";

            try {
                Connection conn = KoneksiDatabase.getConnection();
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    if (key != null && !key.trim().isEmpty()) {
                        pstmt.setString(1, "%" + key + "%");
                    }

                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            dataList.add(new Object[]{
                                rs.getInt("layanan_id"),
                                rs.getString("nama_layanan"),
                                rs.getDouble("biaya")
                            });
                        }
                    }
                }
            } catch (SQLException e) {
                errorMsg = e.getMessage();
            }

            final String finalError = errorMsg;
            SwingUtilities.invokeLater(() -> {
                if (finalError != null) {
                    JOptionPane.showMessageDialog(this, "Gagal memuat data: " + finalError);
                } else {
                    DefaultTableModel model = (DefaultTableModel) tblLayanan.getModel();
                    
                    // Set Header Kolom secara Programmatic
                    String[] judulKolom = {"ID Layanan", "Nama Layanan", "Biaya"};
                    model.setColumnIdentifiers(judulKolom);
                    
                    model.setRowCount(0);
                    for (Object[] row : dataList) {
                        model.addRow(row);
                    }
                }
                this.setCursor(Cursor.getDefaultCursor());
                btnRefresh.setEnabled(true);
                btnCariLayanan.setEnabled(true);
            });
        });
    }
    
    @Override
    public void hapusLayanan (int idLayanan) {
        String sql = "DELETE FROM layanan WHERE layanan_id = ?";
        try {
            Connection conn = KoneksiDatabase.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, idLayanan);
                    pstmt.executeUpdate();
                    
                    JOptionPane.showMessageDialog(this, "Data layanan berhasil dihapus.");
                    loadDataLayanan("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_cariUser = new javax.swing.JLabel();
        txtCariLayanan = new javax.swing.JTextField();
        btnCariLayanan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLayanan = new javax.swing.JTable();
        btnTambahLayanan = new javax.swing.JButton();
        btnEditLayanan = new javax.swing.JButton();
        btnHapusLayanan = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        btn_cariUser.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btn_cariUser.setForeground(new java.awt.Color(0, 0, 0));
        btn_cariUser.setText("Cari Layanan");

        txtCariLayanan.setBackground(new java.awt.Color(255, 255, 255));
        txtCariLayanan.setForeground(new java.awt.Color(0, 0, 0));

        btnCariLayanan.setBackground(new java.awt.Color(255, 255, 255));
        btnCariLayanan.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnCariLayanan.setForeground(new java.awt.Color(0, 0, 0));
        btnCariLayanan.setText("🔍");
        btnCariLayanan.setBorder(null);
        btnCariLayanan.setBorderPainted(false);
        btnCariLayanan.setContentAreaFilled(false);
        btnCariLayanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCariLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariLayananActionPerformed(evt);
            }
        });

        tblLayanan.setAutoCreateRowSorter(true);
        tblLayanan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tblLayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
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
                "layanan_id", "nama_layanan", "biaya_layanan"
            }
        ));
        tblLayanan.setGridColor(new java.awt.Color(224, 224, 224));
        tblLayanan.setRowHeight(30);
        tblLayanan.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblLayanan.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblLayanan.setShowGrid(true);
        jScrollPane1.setViewportView(tblLayanan);

        btnTambahLayanan.setBackground(new java.awt.Color(0, 123, 255));
        btnTambahLayanan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnTambahLayanan.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahLayanan.setText("➕ Tambah Data");
        btnTambahLayanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahLayananActionPerformed(evt);
            }
        });

        btnEditLayanan.setBackground(new java.awt.Color(255, 130, 0));
        btnEditLayanan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnEditLayanan.setForeground(new java.awt.Color(255, 255, 255));
        btnEditLayanan.setText("📝 Edit Data");
        btnEditLayanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditLayananActionPerformed(evt);
            }
        });

        btnHapusLayanan.setBackground(new java.awt.Color(220, 53, 69));
        btnHapusLayanan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnHapusLayanan.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusLayanan.setText("❌  Hapus Data");
        btnHapusLayanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapusLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusLayananActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnTambahLayanan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditLayanan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapusLayanan))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_cariUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCariLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCariLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 352, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_cariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCariLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahLayananActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog_Form_Layanan formLayanan = new JDialog_Form_Layanan(parent, true, null);
        formLayanan.setTitle("Tambah Layanan Baru");
        formLayanan.setVisible(true);
        loadDataLayanan("");
    }//GEN-LAST:event_btnTambahLayananActionPerformed

    private void btnEditLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditLayananActionPerformed
        int selectedRow = tblLayanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Mohon pilih baris data layanan yang ingin diubah.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Konversi index jika tabel sedang di-sort dan ambil ID yang terpilih
        int modelRow = tblLayanan.convertRowIndexToModel(selectedRow);
        int idLayanan = (int) tblLayanan.getModel().getValueAt(modelRow, 0);

        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog_Form_Layanan formLayanan = new JDialog_Form_Layanan(parent, true, idLayanan);
        formLayanan.setTitle("Edit Data Layanan");
        formLayanan.setVisible(true);
        loadDataLayanan("");
    }//GEN-LAST:event_btnEditLayananActionPerformed

    private void btnHapusLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusLayananActionPerformed
        int selectedRow = tblLayanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Mohon pilih baris data layanan yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tblLayanan.convertRowIndexToModel(selectedRow);
        int idLayanan = (int) tblLayanan.getModel().getValueAt(modelRow, 0);
        String namaLayanan = (String) tblLayanan.getModel().getValueAt(modelRow, 1);

        int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus Layanan: '" + namaLayanan + "'?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            hapusLayanan(idLayanan);
        }
    }//GEN-LAST:event_btnHapusLayananActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadDataLayanan("");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCariLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariLayananActionPerformed
        // Ambil teks dari text field
        String kataKunci = txtCariLayanan.getText();
        // Panggil method load dengan kata kunci tersebut
        loadDataLayanan(kataKunci);
    }//GEN-LAST:event_btnCariLayananActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariLayanan;
    private javax.swing.JButton btnEditLayanan;
    private javax.swing.JButton btnHapusLayanan;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTambahLayanan;
    private javax.swing.JLabel btn_cariUser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLayanan;
    private javax.swing.JTextField txtCariLayanan;
    // End of variables declaration//GEN-END:variables

}

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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class JPanel_Manajemen_User extends javax.swing.JPanel implements Manajemen {
    
    // Atribut

    // Constructor
    public JPanel_Manajemen_User() {
        initComponents();
        loadDataUser("");
    }
    
    @Override
    public void loadDataUser(String key) {
        // 1. UBAH KURSOR JADI LOADING (WAIT)
        // Ini memberi sinyal visual ke user bahwa sistem sedang bekerja
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnRefresh.setEnabled(false);
        btnCariUser.setEnabled(false);

        // 2. JALANKAN PROSES DATABASE DI BACKGROUND THREAD
        ThreadPoolManager.getInstance().submit(() -> {
            // Siapkan penampung data sementara (List)
            // Kita tidak boleh mengubah JTable langsung dari thread ini
            List<Object[]> dataList = new ArrayList<>();
            String errorMsg = null;

            // --- LOGIKA DATABASE (Background) ---
            String sql = "SELECT user_id, username, nama_lengkap, role, no_telepon, alamat " +
                         "FROM user WHERE role != 'Dokter'";
            
            boolean isSearch = (key != null && !key.trim().isEmpty());
            
            if (isSearch) {
                sql += " AND (username LIKE ? OR nama_lengkap LIKE ?)";
            }

            try {
                Connection conn = Database.KoneksiDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

                if (isSearch) {
                    String pattern = "%" + key + "%";
                    pstmt.setString(1, pattern);
                    pstmt.setString(2, pattern);
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        // Simpan data ke list sementara dulu
                        dataList.add(new Object[]{
                            rs.getString("user_id"),
                            rs.getString("username"),
                            rs.getString("nama_lengkap"),
                            rs.getString("role"),
                            rs.getString("no_telepon"),
                            rs.getString("alamat")
                        });
                    }
                }
            } catch (Exception e) {
                errorMsg = e.getMessage();
                e.printStackTrace();
            }

            // --- 3. UPDATE TAMPILAN (Kembali ke UI Thread) ---
            // Kita harus melempar data yang sudah didapat kembali ke Swing Thread
            final String finalError = errorMsg;
            
            SwingUtilities.invokeLater(() -> {
                // Cek jika ada error
                if (finalError != null) {
                    JOptionPane.showMessageDialog(this, "Gagal memuat data user: " + finalError);
                } else {
                    // Update Tabel dengan data baru
                    DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
                    
                    // Atur ulang kolom (header)
                    String[] judulKolom = {"ID User", "Username", "Nama Lengkap", "Role", "No. Telepon", "Alamat"};
                    model.setColumnIdentifiers(judulKolom);
                    
                    // Bersihkan baris lama & Isi baris baru
                    model.setRowCount(0); 
                    for (Object[] rowData : dataList) {
                        model.addRow(rowData);
                    }
                }

                // PENTING: KEMBALIKAN KURSOR KE NORMAL (DEFAULT)
                this.setCursor(Cursor.getDefaultCursor());
                btnRefresh.setEnabled(true);
                btnCariUser.setEnabled(true);
            });

        });
//        DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
//        model.setRowCount(0); // Bersihkan tabel lama
//        
//        // Kolom tabel
//        String[] judulKolom = {"ID User", "Username", "Nama Lengkap", "Role", "No. Telepon", "Alamat"};
//        model.setColumnIdentifiers(judulKolom);
//        
//        // SQL ambil data dari tabel user kecuali role dokter
//        String sql = "SELECT user_id, username, nama_lengkap, role, no_telepon, alamat " +
//                     "FROM user WHERE role != 'Dokter'";
//        
//        // Jika ada kata kunci pencarian, tambahkan klausa WHERE
//        boolean isSearch = (key != null && !key.trim().isEmpty());
//        
//        if (isSearch) {
//            // Mencari berdasarkan Username ATAU Nama Lengkap
//            sql += " AND (username LIKE ? OR nama_lengkap LIKE ?)";
//        }
//
//        try (Connection conn = new KoneksiDatabase().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            if (isSearch) {
//                String pattern = "%" + key + "%";
//                pstmt.setString(1, pattern);
//                pstmt.setString(2, pattern);
//            }
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    model.addRow(new Object[]{
//                        rs.getString("user_id"),
//                        rs.getString("username"),
//                        rs.getString("nama_lengkap"),
//                        rs.getString("role"),
//                        rs.getString("no_telepon"), 
//                        rs.getString("alamat")     
//                    });
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Gagal memuat data user: " + e.getMessage());
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_cariUser = new javax.swing.JLabel();
        txtCariUser = new javax.swing.JTextField();
        btnCariUser = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnTambahUser = new javax.swing.JButton();
        btnEditUser = new javax.swing.JButton();
        btnHapusUser = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        btn_cariUser.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btn_cariUser.setForeground(new java.awt.Color(0, 0, 0));
        btn_cariUser.setText("Cari User");

        txtCariUser.setBackground(new java.awt.Color(255, 255, 255));
        txtCariUser.setForeground(new java.awt.Color(0, 0, 0));

        btnCariUser.setBackground(new java.awt.Color(255, 255, 255));
        btnCariUser.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnCariUser.setForeground(new java.awt.Color(0, 0, 0));
        btnCariUser.setText("🔍");
        btnCariUser.setBorder(null);
        btnCariUser.setBorderPainted(false);
        btnCariUser.setContentAreaFilled(false);
        btnCariUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCariUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariUserActionPerformed(evt);
            }
        });

        tblUser.setAutoCreateRowSorter(true);
        tblUser.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
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
                "User Id", "Username", "Nama Lengkap", "Role"
            }
        ));
        tblUser.setGridColor(new java.awt.Color(224, 224, 224));
        tblUser.setRowHeight(30);
        tblUser.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblUser.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblUser.setShowGrid(true);
        jScrollPane1.setViewportView(tblUser);

        btnTambahUser.setBackground(new java.awt.Color(0, 123, 255));
        btnTambahUser.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnTambahUser.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahUser.setText("➕ Tambah Data");
        btnTambahUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahUserActionPerformed(evt);
            }
        });

        btnEditUser.setBackground(new java.awt.Color(255, 130, 0));
        btnEditUser.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnEditUser.setForeground(new java.awt.Color(255, 255, 255));
        btnEditUser.setText("📝 Edit Data");
        btnEditUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUserActionPerformed(evt);
            }
        });

        btnHapusUser.setBackground(new java.awt.Color(220, 53, 69));
        btnHapusUser.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnHapusUser.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusUser.setText("❌  Hapus Data");
        btnHapusUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapusUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusUserActionPerformed(evt);
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
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambahUser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapusUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_cariUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 379, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_cariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahUserActionPerformed
        // Membuat instance JDialog_Form_User
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this); 

        // Buka Dialog dalam mode TAMBAH (parameter null)
        JDialog_Form_User form = new JDialog_Form_User(parentFrame, true, null);
        form.setTitle("Tambah User Baru");
        form.setVisible(true);

        // Refresh tabel setelah dialog ditutup
        loadDataUser("");
    }//GEN-LAST:event_btnTambahUserActionPerformed

    private void btnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserActionPerformed
        int selectedRow = tblUser.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Mohon pilih baris data user yang ingin diubah.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int modelRow = tblUser.convertRowIndexToModel(selectedRow);
        
        DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
        String userId = model.getValueAt(modelRow, 0).toString();

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this); 
        
        // Buka Dialog dalam mode EDIT (kirim userId)
        JDialog_Form_User form = new JDialog_Form_User(parentFrame, true, userId);
        form.setTitle("Edit User");
        form.setVisible(true);

        loadDataUser(""); // Refresh setelah edit
    }//GEN-LAST:event_btnEditUserActionPerformed

    private void btnHapusUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusUserActionPerformed
        int selectedRow = tblUser.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Mohon pilih baris data user yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = tblUser.convertRowIndexToModel(selectedRow);
        
        DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
        
        String userId = model.getValueAt(modelRow, 0).toString();
        String username = model.getValueAt(modelRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus User: " + username + "?", 
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // EKSEKUSI DELETE DATABASE
            String sql = "DELETE FROM user WHERE user_id = ?";
            
            try (Connection conn = new KoneksiDatabase().getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, userId);
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "User berhasil dihapus.");
                loadDataUser(""); // Refresh tabel
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnHapusUserActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadDataUser("");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCariUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariUserActionPerformed
        // Ambil teks dari text field
        String kataKunci = txtCariUser.getText();
        // Panggil method load dengan kata kunci tersebut
        loadDataUser(kataKunci);
    }//GEN-LAST:event_btnCariUserActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariUser;
    private javax.swing.JButton btnEditUser;
    private javax.swing.JButton btnHapusUser;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTambahUser;
    private javax.swing.JLabel btn_cariUser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField txtCariUser;
    // End of variables declaration//GEN-END:variables

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Resepsionis;
import Database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Frame;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;

/**
 *
 * @author USER
 */
public final class JPanel_Manajemen_Pasien extends javax.swing.JPanel {
    
    private final ImageIcon searchIcon = new ImageIcon(getClass().getResource("/search.png"));
    
    /**
     * Creates new form JPanel_Manajemen_Pasien
     */
    private KoneksiDatabase db;
    public JPanel_Manajemen_Pasien() {
        initComponents();
        this.db = new KoneksiDatabase();
        
        // Menjadwalkan update gambar setelah ukuran tombol tersedia
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Sesuaikan ukuran gambar
                btnCari.setIcon(new ImageIcon(searchIcon.getImage().getScaledInstance(btnCari.getWidth(), btnCari.getHeight(), Image.SCALE_SMOOTH)));
            }
        });
        
        txtCari.putClientProperty("JTextField.placeholderText", "Cari pasien...");
        
        tblPasien.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tblPasien.getTableHeader().setOpaque(false);
        tblPasien.getTableHeader().setBackground(new java.awt.Color(32, 136, 203)); // Warna biru
        tblPasien.getTableHeader().setForeground(new java.awt.Color(255,255,255)); // Teks putih

        loadDataToTable();
    }
    public void loadDataToTable() {
        loadDataToTable(null);  
    }
    public void loadDataToTable(String searchTerm) {
//        // 1. Dapatkan model tabel
//        DefaultTableModel model = (DefaultTableModel) tblPasien.getModel();
//
//        // 2. Kosongkan tabel (hapus baris lama)
//        model.setRowCount(0);
//
//        // 3. Buat query SQL dasar
//        String sql = "SELECT * FROM pasien";
//
//        // 4. Tambahkan kondisi WHERE jika ada searchTerm
//        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
//            sql += " WHERE nama_pasien LIKE ? OR pasien_id LIKE ?";
//        }
//
//        // 5. Gunakan try-with-resources untuk koneksi
//        try (Connection conn = Koneksi.getConnection(); // Asumsi Anda punya kelas Koneksi
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            // 6. Set parameter pencarian jika ada
//            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
//                String likeTerm = "%" + searchTerm + "%";
//                pstmt.setString(1, likeTerm);
//                pstmt.setString(2, likeTerm);
//            }
//
//            // 7. Eksekusi query
//            try (ResultSet rs = pstmt.executeQuery()) {
//                // 8. Looping hasil dan tambahkan ke model tabel
//                while (rs.next()) {
//                    model.addRow(new Object[]{
//                        rs.getString("pasien_id"),
//                        rs.getString("nama_pasien"),
//                        rs.getDate("tanggal_lahir"), // Asumsi tipe data DATE
//                        rs.getString("jenis_kelamin"),
//                        rs.getString("alamat"),
//                        rs.getString("no_telepon")
//                    });
//                }
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, 
//                    "Gagal memuat data pasien: " + e.getMessage(), 
//                    "Error Database", 
//                    JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace(); // Tampilkan error di console untuk debug
//        }

           javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblPasien.getModel();
            model.setRowCount(0); // Bersihkan tabel

            // 1. Buat Query yang dinamis
            String sql = "SELECT * FROM pasien";
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
             sql += " WHERE nama_pasien LIKE ? OR pasien_id LIKE ?";
           }
        sql += " ORDER BY pasien_id DESC"; // Tambahkan pengurutan

        try (Connection conn = db.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // 2. Jika ada kata kunci pencarian, masukkan ke parameter
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            String likeValue = "%" + searchTerm + "%";
            stmt.setString(1, likeValue);
            stmt.setString(2, likeValue);
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getString("pasien_id"),
                    rs.getString("nama_pasien"),
                    rs.getDate("tanggal_lahir"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("alamat"),
                    rs.getString("no_telepon")
                };
                model.addRow(row);
            }
            }
         } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat tabel: " + e.getMessage());
        e.printStackTrace();
        }



     }
    
    

    /**
     * 
     * 
     * 
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJudul = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelKontrol = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        scrollPaneTabel = new javax.swing.JScrollPane();
        tblPasien = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(51, 51, 51));
        lblJudul.setText("Manajemen Data Pasien");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        panelKontrol.setBackground(new java.awt.Color(255, 255, 255));

        btnTambah.setBackground(new java.awt.Color(50, 120, 220));
        btnTambah.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("➕  Tambah Pasien");
        btnTambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(255, 130, 0));
        btnEdit.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("📝  Edit Data");
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

        btnCari.setBackground(new java.awt.Color(75, 167, 87));
        btnCari.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnCari.setForeground(new java.awt.Color(255, 255, 255));
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnCari.setContentAreaFilled(false);
        btnCari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblPasien.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tblPasien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"P001", "ABCD", "20-12-2001", "Laki-laki", "Jl. Abc", "0813"},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Pasien", "Nama Lengkap", "Tanggal Lahir", "Jenis Kelamin", "Alamat", "No. Telepon"
            }
        ));
        tblPasien.setGridColor(new java.awt.Color(224, 224, 224));
        tblPasien.setRowHeight(30);
        tblPasien.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblPasien.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblPasien.setShowGrid(true);
        scrollPaneTabel.setViewportView(tblPasien);

        javax.swing.GroupLayout panelKontrolLayout = new javax.swing.GroupLayout(panelKontrol);
        panelKontrol.setLayout(panelKontrolLayout);
        panelKontrolLayout.setHorizontalGroup(
            panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKontrolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                    .addGroup(panelKontrolLayout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelKontrolLayout.setVerticalGroup(
            panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKontrolLayout.createSequentialGroup()
                .addGroup(panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelKontrolLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(scrollPaneTabel, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelKontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblJudul)
                        .addGap(0, 569, Short.MAX_VALUE)))
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
                .addComponent(panelKontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        JDialog_Form_Pasien formPasien = new JDialog_Form_Pasien(null, true, this); // null = parent, true = modal
        formPasien.setVisible(true);

        // Setelah form ditutup, muat ulang data di tabel
        loadDataToTable();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        // 1. Dapatkan baris yang dipilih
//        int selectedRow = tblPasien.getSelectedRow();
//
//        // 2. Cek apakah ada baris yang dipilih
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, 
//                    "Pilih data pasien yang ingin Anda edit terlebih dahulu.", 
//                    "Peringatan", 
//                    JOptionPane.WARNING_MESSAGE);
////            return; // Hentikan eksekusi
//        }
//
//        // 3. Ambil ID Pasien dari kolom pertama (index 0)
//        String idPasienToEdit = tblPasien.getValueAt(selectedRow, 0).toString();
//
//        // 4. Dapatkan frame induk (cara paling aman)
//        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
//
//        // 5. Buat dan tampilkan JDialog_Form_Pasien dalam mode EDIT
//        //    (Asumsi Anda punya constructor ini)
//        JDialog_Form_Pasien formEdit = new JDialog_Form_Pasien(parentFrame, true, this, idPasienToEdit);
//        formEdit.setVisible(true);
//
//        // 6. Setelah form ditutup, muat ulang data di tabel
//        //    Kita kirim txtCari.getText() agar pencarian tetap aktif jika ada
//        loadDataToTable(txtCari.getText());

            int baris = tblPasien.getSelectedRow();
    
            if (baris != -1) {
            // Ambil ID Pasien dari kolom 0 tabel
            String idPasien = tblPasien.getValueAt(baris, 0).toString();
        
            // Buka Dialog dengan constructor Edit (Constructor ke-2)
            // 'this' merujuk pada Panel, 'parent' biasanya diambil dari Frame utama
            java.awt.Frame parent = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            JDialog_Form_Pasien dialog = new JDialog_Form_Pasien(parent, true, this, idPasien);
            dialog.setVisible(true);
            } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih data pada tabel terlebih dahulu!");
            }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        // 1. Dapatkan baris yang dipilih
        int selectedRow = tblPasien.getSelectedRow();

        // 2. Cek apakah ada baris yang dipilih
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Pilih data pasien yang ingin Anda hapus.", 
                    "Peringatan", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Ambil ID dan Nama untuk konfirmasi
        String idPasien = tblPasien.getValueAt(selectedRow, 0).toString();
        String namaPasien = tblPasien.getValueAt(selectedRow, 1).toString();

        // 4. Tampilkan dialog konfirmasi
        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus data pasien:\n" + namaPasien + " (ID: " + idPasien + ")?",
                "Konfirmasi Hapus Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        
        
        // 4. Jika user klik YES, baru eksekusi hapus
    if (pilihan == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM pasien WHERE pasien_id = ?";
        
        try (Connection conn = Database.KoneksiDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, idPasien);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data pasien berhasil dihapus!");
                
                // 5. Refresh tabel agar data yang dihapus hilang dari tampilan
                loadDataToTable(); 
                
                // Opsional: Bersihkan field pencarian jika ada
                txtCari.setText("");
            }
            
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // ERROR KHUSUS: Jika pasien sudah punya riwayat kunjungan (Foreign Key)
            JOptionPane.showMessageDialog(this, 
                "Gagal menghapus data!\n" +
                "Pasien ini memiliki riwayat kunjungan/resep.\n" +
                "Hapus data kunjungan terkait terlebih dahulu.", 
                "Terproteksi Database", 
                JOptionPane.ERROR_MESSAGE);
                
        } catch (java.sql.SQLException e) {
            // Error database umum lainnya
            JOptionPane.showMessageDialog(this, 
                "Terjadi kesalahan database: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

        
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        // 1. Ambil kata kunci dari JTextField
        String searchTerm = txtCari.getText();

        // 2. Panggil method loadDataToTable dengan kata kunci tersebut
        loadDataToTable(searchTerm);
    }//GEN-LAST:event_btnCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JScrollPane scrollPaneTabel;
    private javax.swing.JTable tblPasien;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}

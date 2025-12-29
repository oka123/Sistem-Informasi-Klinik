/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Manajemen;

import Database.KoneksiDatabase;
import Utils.NumericFilter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.text.PlainDocument;

public class JDialog_Form_Dokter extends javax.swing.JDialog implements Manajemen {
    // Atribut
    private Integer idDokterToEdit = null;
    String pesanError = "";
    
    // Constructor
    public JDialog_Form_Dokter(java.awt.Frame parent, boolean modal, Integer idDokter) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        
        PlainDocument doc = (PlainDocument) txtNoTelepon.getDocument();
        doc.setDocumentFilter(new NumericFilter());
        
        this.idDokterToEdit = idDokter;
        txtIDDokter.setEnabled(false);
        
        if (idDokter != null) {
            lblJudul.setText("Edit Data Dokter");
            txtIDDokter.setText(String.valueOf(idDokter)); // Tampilkan sebagai String di GUI
            
            // Saat Edit, Username & Password biasanya tidak diubah di sini untuk keamanan/konsistensi
            // Kita disable atau sembunyikan logika insert user
            lblInfoPassword.setText("Biarkan kosong jika tidak ingin mengubah password");
            
            loadDataDokterForEdit();
        } else {
            lblJudul.setText("Tambah Dokter Baru");
            txtIDDokter.setText("Auto");
            txtUsername.setEnabled(true);
        }
    }
    
    private void loadDataDokterForEdit() {
        String sql = "SELECT d.dokter_id, d.spesialisasi, u.username, u.nama_lengkap, u.no_telepon, u.alamat " +
                     "FROM dokter d " +
                     "JOIN user u ON d.user_id = u.user_id " +
                     "WHERE d.dokter_id = ?";
                     
        try {
            Connection conn = KoneksiDatabase.getConnection();  // Koneksi dibuka di sini

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {  // PreparedStatement ditangani oleh try-with-resources
                pstmt.setInt(1, this.idDokterToEdit);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    txtNamaLengkap.setText(rs.getString("nama_lengkap"));
                    txtUsername.setText(rs.getString("username"));
                    txtPassword.setText("");
                    comboSpesialisasi.setSelectedItem(rs.getString("spesialisasi"));
                    txtAlamat.setText(rs.getString("alamat"));
                    txtNoTelepon.setText(rs.getString("no_telepon"));
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }
    
    // Metode untuk validasi input yang mengumpulkan pesan error
    private String validasiInput(String input, String pesanError) {
        if (input.isEmpty()) {
            return pesanError + "\n";
        }
        return "";
    }
    
    @Override
    public void tambahDokter(Connection conn, String nama, String username, char[] passwordChars, String spesialisasi, String telp, String alamat) throws Exception {
        
        if (passwordChars.length == 0) {
            throw new Exception("Password wajib diisi untuk dokter baru!");
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, passwordChars);

        // 1. Insert ke tabel User
        String sqlUser = "INSERT INTO user (username, password, nama_lengkap, role, no_telepon, alamat) " +
                         "VALUES (?, ?, ?, 'Dokter', ?, ?)";
        
        int userIdBaru = -1;
        try (PreparedStatement pstmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
            pstmtUser.setString(1, username);
            pstmtUser.setString(2, hashedPassword);
            pstmtUser.setString(3, nama);
            pstmtUser.setString(4, telp);
            pstmtUser.setString(5, alamat);
            pstmtUser.executeUpdate();

            ResultSet rs = pstmtUser.getGeneratedKeys();
            if (rs.next()) userIdBaru = rs.getInt(1);
        }

        if (userIdBaru == -1) throw new Exception("Gagal mendapatkan generated User ID.");

        // 2. Insert ke tabel Dokter
        String sqlDokter = "INSERT INTO dokter (user_id, spesialisasi, no_telepon, alamat) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmtDokter = conn.prepareStatement(sqlDokter)) {
            pstmtDokter.setInt(1, userIdBaru);
            pstmtDokter.setString(2, spesialisasi);
            pstmtDokter.setString(3, telp);
            pstmtDokter.setString(4, alamat);
            pstmtDokter.executeUpdate();
        }
        
        JOptionPane.showMessageDialog(this, "Dokter berhasil ditambahkan!");
    }
    
    @Override
    public void editDokter(Connection conn, String nama, String username, char[] passwordChars, String spesialisasi, String telp, String alamat) throws Exception {
        
        // 1. Cari user_id berdasarkan idDokterToEdit
        int userId = -1;
        String sqlGetId = "SELECT user_id FROM dokter WHERE dokter_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlGetId)) {
            ps.setInt(1, idDokterToEdit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) userId = rs.getInt("user_id");
        }

        if (userId == -1) throw new Exception("Data User tidak ditemukan untuk Dokter ini.");

        // 2. Update tabel User
        if (passwordChars.length > 0) {
            // Update dengan Password Baru
            String hashedPassword = BCrypt.withDefaults().hashToString(12, passwordChars);
            String sqlUser = "UPDATE user SET nama_lengkap=?, username=?, password=?, no_telepon=?, alamat=? WHERE user_id=?";
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                psUser.setString(1, nama);
                psUser.setString(2, username);
                psUser.setString(3, hashedPassword);
                psUser.setString(4, telp);
                psUser.setString(5, alamat);
                psUser.setInt(6, userId);
                psUser.executeUpdate();
            }
        } else {
            // Update Tanpa Password
            String sqlUser = "UPDATE user SET nama_lengkap=?, username=?, no_telepon=?, alamat=? WHERE user_id=?";
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                psUser.setString(1, nama);
                psUser.setString(2, username);
                psUser.setString(3, telp);
                psUser.setString(4, alamat);
                psUser.setInt(5, userId);
                psUser.executeUpdate();
            }
        }

        // 3. Update tabel Dokter
        String sqlDokter = "UPDATE dokter SET spesialisasi=? WHERE dokter_id=?";
        try (PreparedStatement psDokter = conn.prepareStatement(sqlDokter)) {
            psDokter.setString(1, spesialisasi);
            psDokter.setInt(2, idDokterToEdit);
            psDokter.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Data dokter berhasil diperbarui!");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelForm = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtIDDokter = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNamaLengkap = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        scrollAlamat = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtNoTelepon = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboSpesialisasi = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblInfoPassword = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Data Dokter");
        setModal(true);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(461, 569));

        panelForm.setBackground(new java.awt.Color(255, 255, 255));
        panelForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelForm.setPreferredSize(new java.awt.Dimension(461, 569));

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 20)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(0, 0, 0));
        lblJudul.setText("Form Data Dokter");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID Dokter");

        txtIDDokter.setEditable(false);
        txtIDDokter.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtIDDokter.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nama Dokter");

        txtNamaLengkap.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNamaLengkap.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(5);
        txtAlamat.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAlamat);

        scrollAlamat.setViewportView(jScrollPane1);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("No. Telepon");

        txtNoTelepon.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNoTelepon.setForeground(new java.awt.Color(0, 0, 0));

        btnSimpan.setBackground(new java.awt.Color(0, 123, 255));
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

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Spesialisasi");

        comboSpesialisasi.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        comboSpesialisasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Umum", "Gigi", "Anak", "Mata", "THT" }));
        comboSpesialisasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Username");

        txtUsername.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Password");

        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordFocusLost(evt);
            }
        });

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addComponent(lblJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollAlamat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                            .addComponent(txtNamaLengkap, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIDDokter, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNoTelepon)
                            .addComponent(comboSpesialisasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPassword)
                            .addComponent(lblInfoPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jSeparator1)
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addComponent(lblJudul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIDDokter, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNamaLengkap, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfoPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSpesialisasi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNoTelepon, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jScrollPane2.setViewportView(panelForm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        String nama = txtNamaLengkap.getText();
        String username = txtUsername.getText();
        char[] passwordChars = txtPassword.getPassword();
        String spesialisasi = (String) comboSpesialisasi.getSelectedItem();
        String alamat = txtAlamat.getText();
        String telp = txtNoTelepon.getText();

        // Validasi untuk masing-masing input
        pesanError += validasiInput(username, "Username, ");
        pesanError += validasiInput(nama, "Nama lengkap, ");
        pesanError += validasiInput(spesialisasi, "Spesialisasi, ");
        pesanError += validasiInput(telp, "Nomor Telepon, ");
        pesanError += validasiInput(alamat, "Alamat, ");

        // Jika ada pesan error, tampilkan dalam satu dialog
        if (!pesanError.isEmpty()) {
            JOptionPane.showMessageDialog(this, pesanError + "wajib diisi!");
            return;
        }

        Connection conn = null;
        try {
            conn = KoneksiDatabase.getConnection();
            conn.setAutoCommit(false); 

            if (idDokterToEdit == null) {
                // --- Tambah Data ---
                tambahDokter(conn, nama, username, passwordChars, spesialisasi, telp, alamat);
            } else {
                // --- Edit Data ---
                editDokter(conn, nama, username, passwordChars, spesialisasi, telp, alamat);
            }

            conn.commit(); 
            this.dispose();

        } catch (Exception e) {
            try { 
                if (conn != null) conn.rollback(); 
            } catch(SQLException ex){
                ex.printStackTrace();
            } 
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        } finally {
            try { 
                if (conn != null) { 
                    conn.setAutoCommit(true);
                } 
            } catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        txtPassword.setEchoChar((char) 0);
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusLost
        txtPassword.setEchoChar('*');
    }//GEN-LAST:event_txtPasswordFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboSpesialisasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblInfoPassword;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JPanel panelForm;
    private javax.swing.JScrollPane scrollAlamat;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtIDDokter;
    private javax.swing.JTextField txtNamaLengkap;
    private javax.swing.JTextField txtNoTelepon;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}

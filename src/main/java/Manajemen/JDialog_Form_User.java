/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Manajemen;

import Database.KoneksiDatabase;
import Utils.NumericFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.SQLException;
import javax.swing.text.PlainDocument;

public class JDialog_Form_User extends javax.swing.JDialog implements Manajemen {
    // Atribut
    private Integer idUserToEdit = null;

    // Constructor
    public JDialog_Form_User(java.awt.Frame parent, boolean modal, Integer idUser) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        PlainDocument doc = (PlainDocument) txtNoTelepon.getDocument();
        doc.setDocumentFilter(new NumericFilter());
        
        this.idUserToEdit = idUser;
        
        if (this.idUserToEdit != null) {
            // Mode EDIT: Muat data lama
            loadDataUserForEdit();
            txt_user_id.setText(String.valueOf(idUser));
            txt_user_id.setEnabled(false); // ID tidak boleh diubah
            lblInfoPassword.setText("Biarkan kosong jika tidak ingin mengubah password");
        } else {
            // Mode TAMBAH
            txt_user_id.setText("Auto");
            txt_user_id.setEnabled(false);
            lblInfoPassword.setText("");
        }
    }
    
    // Method untuk memuat data lama ke form (Mode Edit)
    private void loadDataUserForEdit() {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try {
            Connection conn = KoneksiDatabase.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, this.idUserToEdit);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    txt_username.setText(rs.getString("username"));
                    txt_nama_lengkap.setText(rs.getString("nama_lengkap"));
                    comboRole.setSelectedItem(rs.getString("role"));
                    txtNoTelepon.setText(rs.getString("no_telepon"));
                    txtAlamat.setText(rs.getString("alamat"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }
          
    @Override
    public void tambahUser(Connection conn, String username, char[] passwordChars, String namaLengkap, String role, String noTelepon, String alamat) throws Exception {
        
        // Validasi khusus: Password wajib diisi untuk user baru
        if (passwordChars.length == 0) {
            throw new Exception("Password wajib diisi untuk user baru!");
        }

        // Proses Hashing BCrypt
        String hashedPassword = BCrypt.withDefaults().hashToString(12, passwordChars);

        String sql = "INSERT INTO user (username, password, nama_lengkap, role, no_telepon, alamat) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, namaLengkap);
            pstmt.setString(4, role);
            pstmt.setString(5, noTelepon);
            pstmt.setString(6, alamat);
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "User baru berhasil ditambahkan!");
        }
    }
    
    @Override
    public void editUser(Connection conn, String username, char[] passwordChars, String namaLengkap, String role, String noTelepon, String alamat) throws Exception {
        
        if (passwordChars.length == 0) {
            // Skenario A: Update TANPA mengubah password
            String sql = "UPDATE user SET username=?, nama_lengkap=?, role=?, no_telepon=?, alamat=? WHERE user_id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, namaLengkap);
                pstmt.setString(3, role);
                pstmt.setString(4, noTelepon);
                pstmt.setString(5, alamat);
                pstmt.setInt(6, this.idUserToEdit);
                pstmt.executeUpdate();
            }
        } else {
            // Skenario B: Update DENGAN password baru (Hash ulang)
            String hashedPassword = BCrypt.withDefaults().hashToString(12, passwordChars);

            String sql = "UPDATE user SET username=?, password=?, nama_lengkap=?, role=?, no_telepon=?, alamat=? WHERE user_id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, hashedPassword);
                pstmt.setString(3, namaLengkap);
                pstmt.setString(4, role);
                pstmt.setString(5, noTelepon);
                pstmt.setString(6, alamat);
                pstmt.setInt(7, this.idUserToEdit);
                pstmt.executeUpdate();
            }
        }
        
        JOptionPane.showMessageDialog(this, "Data user berhasil diperbarui!");
    }
    
    // Metode untuk validasi input yang mengumpulkan pesan error
    private String validasiInput(String input, String pesanError) {
        if (input.isEmpty()) {
            return pesanError + "\n";
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_user_id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_nama_lengkap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comboRole = new javax.swing.JComboBox<>();
        btnSimpanUser = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        txt_password = new javax.swing.JPasswordField();
        lblInfoPassword = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNoTelepon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 538));

        jPanel1.setPreferredSize(new java.awt.Dimension(452, 538));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Form Data User");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("User ID");

        txt_user_id.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_user_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_user_idActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("Username");

        txt_username.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Password");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText("Nama Lengkap");

        txt_nama_lengkap.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("Role");

        comboRole.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        comboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Resepsionis", "Kasir", "Apoteker", "Manajemen" }));
        comboRole.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnSimpanUser.setBackground(new java.awt.Color(0, 123, 255));
        btnSimpanUser.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnSimpanUser.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanUser.setText("Simpan");
        btnSimpanUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpanUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanUserActionPerformed(evt);
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

        txt_password.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_passwordFocusLost(evt);
            }
        });

        lblInfoPassword.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setText("No. Telepon");

        txtNoTelepon.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnSimpanUser, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addComponent(jLabel6))
                                    .addGap(49, 49, 49)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                        .addComponent(txtNoTelepon, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_nama_lengkap, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_username, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_user_id, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_password, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(comboRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblInfoPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(27, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_user_id, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfoPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nama_lengkap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNoTelepon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusGained
        txt_password.setEchoChar((char) 0);
    }//GEN-LAST:event_txt_passwordFocusGained

    private void txt_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusLost
        txt_password.setEchoChar('*');
    }//GEN-LAST:event_txt_passwordFocusLost

    private void txt_user_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_user_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_user_idActionPerformed

    private void btnSimpanUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanUserActionPerformed
        // 1. Ambil data dari input field
        String username = txt_username.getText();
        char[] passwordChars = txt_password.getPassword();
        String namaLengkap = txt_nama_lengkap.getText();
        String role = (String) comboRole.getSelectedItem();
        String noTelepon = txtNoTelepon.getText();
        String alamat = txtAlamat.getText();

        // 2. Validasi Input
        String pesanError = "";
        pesanError += validasiInput(username, "Username, ");
        pesanError += validasiInput(namaLengkap, "Nama lengkap, ");
        pesanError += validasiInput(role, "Role, ");
        pesanError += validasiInput(noTelepon, "Nomor Telepon, ");
        pesanError += validasiInput(alamat, "Alamat, ");

        if (!pesanError.isEmpty()) {
            JOptionPane.showMessageDialog(this, pesanError + "wajib diisi!");
            return;
        }

        try {
            Connection conn = KoneksiDatabase.getConnection();
            
            // 3. Pilih Jalur (Tambah atau Edit)
            if (this.idUserToEdit == null) {
                tambahUser(conn, username, passwordChars, namaLengkap, role, noTelepon, alamat);
            } else {
                editUser(conn, username, passwordChars, namaLengkap, role, noTelepon, alamat);
            }

            this.dispose(); // Tutup dialog setelah sukses

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSimpanUserActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpanUser;
    private javax.swing.JComboBox<String> comboRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblInfoPassword;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtNoTelepon;
    private javax.swing.JTextField txt_nama_lengkap;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_user_id;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
    
}

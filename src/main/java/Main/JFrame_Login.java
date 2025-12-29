/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Main;

import Apoteker.JFrameMainApoteker;
import Database.KoneksiDatabase;
import Resepsionis.JFrame_Main_Resepsionis;
import Dokter.JFrame_Main_Dokter;
import Kasir.JFrame_Main_Kasir;
import Manajemen.JFrame_Main_Manajemen;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class JFrame_Login extends javax.swing.JFrame {
    
    // Atribut
    Connection conn = KoneksiDatabase.getConnection();
    
    private boolean isPasswordVisible = false;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFrame_Login.class.getName());
    
//    Statement stmt;
//    ResultSet rs;
    
//    Inisialisasi Icon
    private final ImageIcon clinicIcon = new ImageIcon(getClass().getResource("/gambarKlinik.png"));
    private final ImageIcon eyeOpenIcon = new ImageIcon(getClass().getResource("/eye.png"));
    private final ImageIcon eyeClosedIcon = new ImageIcon(getClass().getResource("/eyeslash.png")); 
    
//    Constructor
    public JFrame_Login() {
        initComponents();
        this.setLocationRelativeTo(null); 
        
        // Menambahkan placeholder text
        txt_username.putClientProperty("JTextField.placeholderText", "Masukkan username...");
        txt_password.putClientProperty("JTextField.placeholderText", "Masukkan password...");
        
        // Menjadwalkan update gambar setelah ukuran tombol tersedia
        SwingUtilities.invokeLater(() -> {
            // Sesuaikan ukuran icon (fit)
            labelIconKlinik.setIcon(new ImageIcon(clinicIcon.getImage().getScaledInstance(labelIconKlinik.getWidth(), labelIconKlinik.getHeight(), Image.SCALE_SMOOTH)));
            
            showHidePasswordButton.setIcon(new ImageIcon(eyeClosedIcon.getImage().getScaledInstance(showHidePasswordButton.getWidth(), showHidePasswordButton.getHeight(), Image.SCALE_SMOOTH)));
            
        });
    }
    
    private void notifikasiLoginGagal() {
        JOptionPane.showMessageDialog(this, 
                    "Username atau Password salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_kiri = new javax.swing.JPanel();
        labelIconKlinik = new javax.swing.JLabel();
        panel_kanan = new javax.swing.JPanel();
        label_title = new javax.swing.JLabel();
        label_username = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        label_password = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();
        showHidePasswordButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - Klinik Sehat Selalu");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        panel_kiri.setBackground(new java.awt.Color(180, 220, 240));

        labelIconKlinik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarKlinik.png"))); // NOI18N

        javax.swing.GroupLayout panel_kiriLayout = new javax.swing.GroupLayout(panel_kiri);
        panel_kiri.setLayout(panel_kiriLayout);
        panel_kiriLayout.setHorizontalGroup(
            panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_kiriLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(labelIconKlinik)
                .addGap(59, 59, 59))
        );
        panel_kiriLayout.setVerticalGroup(
            panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kiriLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(labelIconKlinik)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        panel_kanan.setBackground(new java.awt.Color(255, 255, 255));
        panel_kanan.setPreferredSize(new java.awt.Dimension(200, 75));

        label_title.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        label_title.setForeground(java.awt.Color.black);
        label_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_title.setText("Klinik Sehat Selalu");

        label_username.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        label_username.setForeground(java.awt.Color.black);
        label_username.setText("Username");

        txt_username.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txt_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_usernameKeyPressed(evt);
            }
        });

        label_password.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        label_password.setForeground(java.awt.Color.black);
        label_password.setText("Password");

        txt_password.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passwordKeyPressed(evt);
            }
        });

        btn_login.setBackground(new java.awt.Color(50, 120, 220));
        btn_login.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btn_login.setForeground(java.awt.Color.white);
        btn_login.setText("➜]  Login");
        btn_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        showHidePasswordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeslash.png"))); // NOI18N
        showHidePasswordButton.setContentAreaFilled(false);
        showHidePasswordButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showHidePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHidePasswordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_kananLayout = new javax.swing.GroupLayout(panel_kanan);
        panel_kanan.setLayout(panel_kananLayout);
        panel_kananLayout.setHorizontalGroup(
            panel_kananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_kananLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(panel_kananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_password)
                    .addComponent(label_username)
                    .addComponent(txt_password)
                    .addComponent(txt_username)
                    .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showHidePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        panel_kananLayout.setVerticalGroup(
            panel_kananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kananLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(label_title)
                .addGap(40, 40, 40)
                .addComponent(label_username)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(label_password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_kananLayout.createSequentialGroup()
                        .addComponent(showHidePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addGap(48, 48, 48)
                .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_kiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panel_kanan, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_kiri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_kanan, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        String username = txt_username.getText();
        String password = new String(txt_password.getPassword());

        // Query: hanya cari berdasarkan username
        String sql = "SELECT user_id, username, password, nama_lengkap, role FROM user WHERE username=?";

        try {
            // Prepare dan eksekusi query ke DB
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                // Ambil hash password dari DB
                String hashedPassword = result.getString("password");
                // Verifikasi password (bcrypt Favre)
                BCrypt.Result verify = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
                if (verify.verified) {
                    // Ambil nilai dari result
                    int userId = result.getInt("user_id");
                    String namaLengkap = result.getString("nama_lengkap");
                    String role = result.getString("role");
                    
                    JOptionPane.showMessageDialog(this, "Login Berhasil sebagai " + role);
                    dispose();  // Tutup form login

                    //  Pengecekan role serta navigasinya
                    switch (role.toLowerCase()) {
                        case "resepsionis" -> {
                            JFrame_Main_Resepsionis tampil = new JFrame_Main_Resepsionis(userId, namaLengkap);
                          tampil.setVisible(true);
                        }
                        case "dokter" -> {
                            JFrame_Main_Dokter tampil1 = new JFrame_Main_Dokter();
                            tampil1.setVisible(true);
                        }
                        case "apoteker" -> {
                            JFrameMainApoteker tampil2 = new JFrameMainApoteker();
                            tampil2.setVisible(true);
                        }
                        case "kasir" -> {
                            new JFrame_Main_Kasir().setVisible(true);
                        }
                        case "manajemen" -> {
                            JFrame_Main_Manajemen tampil3 = new JFrame_Main_Manajemen(userId, namaLengkap);
                            tampil3.setVisible(true);
                        }
                        default -> {
                            JOptionPane.showMessageDialog(this, 
                                "Role tidak dikenal: " + role, 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    this.notifikasiLoginGagal();
                }
            } else {
                this.notifikasiLoginGagal();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_loginActionPerformed
    
    private void showHidePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHidePasswordButtonActionPerformed
        // Switch icon button show/hide password
        Image eyeOpenIconResized = eyeOpenIcon.getImage().getScaledInstance(showHidePasswordButton.getWidth(), showHidePasswordButton.getHeight(), Image.SCALE_SMOOTH);
        Image eyeClosedIconResized = eyeClosedIcon.getImage().getScaledInstance(showHidePasswordButton.getWidth(), showHidePasswordButton.getHeight(), Image.SCALE_SMOOTH);
        if (isPasswordVisible) {
            // Jika password sedang ditampilkan, sembunyikan password
            txt_password.setEchoChar('*');  // Sembunyikan password dengan karakter '*'
            showHidePasswordButton.setIcon(new ImageIcon(eyeClosedIconResized)); // Ganti ikon ke mata tertutup
        } else {
            // Jika password tidak ditampilkan, tampilkan password
            txt_password.setEchoChar((char) 0);  // Tampilkan password dengan echoChar 0
            showHidePasswordButton.setIcon(new ImageIcon(eyeOpenIconResized)); // Ganti ikon ke mata terbuka
        }

        isPasswordVisible = !isPasswordVisible;
    }//GEN-LAST:event_showHidePasswordButtonActionPerformed

    private void txt_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passwordKeyPressed
        // Cek apakah tombol yang ditekan adalah ENTER (VK_ENTER)
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btn_loginActionPerformed(null); 
        }
    }//GEN-LAST:event_txt_passwordKeyPressed

    private void txt_usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usernameKeyPressed
        // Cek apakah tombol yang ditekan adalah ENTER (VK_ENTER)
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btn_loginActionPerformed(null); 
        }
    }//GEN-LAST:event_txt_usernameKeyPressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JFrame_Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JLabel labelIconKlinik;
    private javax.swing.JLabel label_password;
    private javax.swing.JLabel label_title;
    private javax.swing.JLabel label_username;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JPanel panel_kiri;
    private javax.swing.JButton showHidePasswordButton;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}

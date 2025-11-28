/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Main;

import java.sql.Connection;
import java.sql.SQLException;
import Database.KoneksiDatabase;

import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Kelompok 5A
 */
public class Main {

    public static void main(String[] args) {
        /* Set the FlatLaf look and feel */
        // Ini adalah kode untuk mengaktifkan tema modern (Light Theme)
        try {
            javax.swing.UIManager.setLookAndFeel( new com.formdev.flatlaf.FlatLightLaf() );
        } catch( javax.swing.UnsupportedLookAndFeelException ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        // Jika kamu ingin Dark Mode, gunakan ini:
        // javax.swing.UIManager.setLookAndFeel( new com.formdev.flatlaf.FlatDarkLaf() );
        
        // Set global font untuk semua komponen Swing
        Font globalFont = new Font("Arial", Font.PLAIN, 14); // Ganti dengan font yang diinginkan
        UIManager.put("Button.font", globalFont);    // Untuk tombol
        UIManager.put("Label.font", globalFont);     // Untuk label
        UIManager.put("TextField.font", globalFont); // Untuk text field
        UIManager.put("TextArea.font", globalFont);  // Untuk text area
        UIManager.put("ComboBox.font", globalFont);  // Untuk combo box
        UIManager.put("Table.font", globalFont);     // Untuk tabel
        
//      Uji Coba Koneksi ke DB
        try {
            Connection conn = Database.KoneksiDatabase.getConnection();
            if (conn != null) {
                JOptionPane.showMessageDialog(null, "Koneksi ke Database Berhasil, Silahkan Login!");
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat koneksi: " + e.getMessage());
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new JFrame_Login().setVisible(true);
        });
        
    }
}

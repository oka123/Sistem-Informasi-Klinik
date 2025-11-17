
package Database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
 
public class KoneksiDatabase {
    
    // Atribut
    private static Connection koneksi;
    // Load file .env
    private static final Dotenv dotenv = Dotenv.load();
    
    public static Connection getConnection() {
        try {
            // Memastikan koneksi baru hanya dibuat jika belum ada atau sudah tertutup
            if (koneksi == null || koneksi.isClosed()) {
                String hostname = dotenv.get("DB_HOST");
                String port = dotenv.get("DB_PORT");
                String database = dotenv.get("DB_DATABASE");
                String username = dotenv.get("DB_USERNAME");
                String password = dotenv.get("DB_PASSWORD");
                
                try {
                    // Load Driver (Opsional untuk Java terbaru, tapi bagus untuk kompatibilitas)
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // Buat URL koneksi dengan atribut
                    String url = String.format("jdbc:mysql://%s:%s/%s?useCompression=true", hostname, port, database);
                    // Buat Koneksi Baru
                    koneksi = DriverManager.getConnection(url, username, password);
                    
                    // Sinkronisasi zona waktu (Asia/Singapore sesuai WITA)
                    // Menggunakan try-with-resources agar 'stmt' otomatis ditutup
                    try (Statement stmt = koneksi.createStatement()) {
                        stmt.executeUpdate("SET time_zone = 'Asia/Singapore'");
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Gagal Koneksi Database: " + e.getMessage());
                    // Return null atau lempar exception agar program tahu koneksi gagal
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Kembalikan koneksi yang SUDAH ADA (Reuse)
        return koneksi;
    }
    
    public static void closeConnection() {
        try {
            if (koneksi != null && !koneksi.isClosed()) {
                koneksi.close();
            }
        } catch (SQLException e) {
            System.err.println("Gagal menutup koneksi database: " + e.getMessage());
        } finally {
            // Penting: Set ke null agar saat login lagi, getConnection() membuat objek baru
            koneksi = null;
        }
    }
}

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
 
public class KoneksiDatabase {
    
    // Atribut
    private static Connection koneksi;
    private static final String hostname = "klinik-klinik.c.aivencloud.com";
    private static final String database = "klinik";
    private static final String port= "12241";
    private static final String username = "avnadmin";
    private static final String password = "AVNS_5e6NcoUQA0Q-vk_YdAs";
    
    public static Connection getConnection() {
        // Cek apakah koneksi belum ada ATAU sudah terputus/closed
        try {
            if (koneksi == null || koneksi.isClosed()) {
                try {
                    // Load Driver (Opsional untuk Java terbaru, tapi bagus untuk kompatibilitas)
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // Buat URL koneksi dengan menggunakan atribut
                    String url = String.format("jdbc:mysql://%s:%s/%s?useCompression=true", hostname, port, database);
                    // Buat Koneksi Baru
                    koneksi = DriverManager.getConnection(url, username, password);
                    // System.out.println("Koneksi Berhasil Dibuat"); // Untuk debug
                    
                    String sqlWaktu = "SET time_zone = 'Asia/Singapore'";
                    Statement stmt = koneksi.createStatement();
                    stmt.executeUpdate(sqlWaktu);
                    
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
    
//    public KoneksiDatabase(){
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            koneksi = DriverManager.getConnection("jdbc:mysql://klinik-klinik.c.aivencloud.com:12241/klinik?useCompression=true", "avnadmin", "AVNS_5e6NcoUQA0Q-vk_YdAs");
//        }
//        catch(ClassNotFoundException | SQLException e){
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
    // Atribut untuk koneksi database
    //private final String hostname;
//    private final String database;
//    private final String port;
//    private final String username;
//    private final String password;
    
//    public KoneksiDatabase () {
//        this.hostname = "ywhq0t.h.filess.io";
//        this.database = "klinik_uncleroll";
//        this.port = "61002";
//        this.username = "klinik_uncleroll";
//        this.password = "31f424b42139b5f830f0f408f0892aaf30d7f095";
//    }
    
    // Metode untuk membuat koneksi ke database
//    public Connection connect() throws SQLException {
//        // Menyusun URL koneksi dengan menggunakan atribut
//        String url = String.format("jdbc:mysql://%s:%s/%s?useCompression=true", hostname, port, database);
//        // Mengembalikan koneksi
//        return DriverManager.getConnection(url, username, password);
//    }
    
//    public Connection getConnection() throws SQLException{
//        return koneksi;
//    }
    
}

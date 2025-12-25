package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class KoneksiDatabase {

    // Simpan kredensial sebagai variabel agar mudah diubah
    private static final String URL = "jdbc:mysql://klinik-klinik.c.aivencloud.com:12241/klinik?useCompression=true";
    private static final String USER = "avnadmin";
    private static final String PASS = "AVNS_5e6NcoUQA0Q-vk_YdAs";

    public KoneksiDatabase() {
        // Constructor hanya untuk load driver (opsional, tapi bagus untuk memastikan driver ada)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver Database tidak ditemukan: " + e.getMessage());
        }
    }

    // PERUBAHAN UTAMA DI SINI:
    // Method ini harus membuka koneksi BARU setiap kali dipanggil
    public static Connection getConnection() throws SQLException {
        try {
            // Membuka koneksi baru ke Aiven setiap kali dipanggil
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            // Tampilkan pesan jika gagal connect (misal internet mati)
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke Database Remote (Aiven):\n" + e.getMessage());
            throw e;
        }
    }
}
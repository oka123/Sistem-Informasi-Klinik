
package Manajemen;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public interface Manajemen {
    default public void loadDashboardData() {}
    
    default public void initTampilanAwalLaporan() {}
    default public void tampilkanLaporan() {}
    default public DefaultTableModel loadLaporanPendapatan(java.sql.Date dari, java.sql.Date sampai) throws SQLException {
        return null;
    }
    default public DefaultTableModel loadLaporanKunjungan(java.sql.Date dari, java.sql.Date sampai) throws SQLException {
        return null;
    }
    default public DefaultTableModel loadLaporanObat(java.sql.Date dari, java.sql.Date sampai) throws SQLException {
        return null;
    }
    default public void tampilkanGrafikPendapatan() {}
    default public void exportLaporanToExcel() {}
    
    default public void loadDataDokter(String key) {}
    default public void tambahDokter(Connection conn, String nama, String username, char[] passwordChars, String spesialisasi, String telp, String alamat) throws Exception {}
    default public void editDokter(Connection conn, String nama, String username, char[] passwordChars, String spesialisasi, String telp, String alamat) throws Exception {}
    default public void hapusDokterDanUser(int idDokter) {}
    
    default public void loadDataUser(String key) {}
    default public void tambahUser(Connection conn, String username, char[] passwordChars, String namaLengkap, String role, String noTelepon, String alamat) throws Exception {}
    default public void editUser(Connection conn, String username, char[] passwordChars, String namaLengkap, String role, String noTelepon, String alamat) throws Exception {}
    default public void hapusDataUser(int idUser) {}
    
    default public void loadDataLayanan(String key) {}
    default public void tambahLayanan(Connection conn, String nama, double biaya) throws SQLException {}
    default public void editLayanan(Connection conn, String nama, double biaya) throws SQLException {}
    default public void hapusLayanan (int idLayanan) {}
}

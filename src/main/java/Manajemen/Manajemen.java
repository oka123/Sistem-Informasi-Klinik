/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Manajemen;

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
    default public void exportLaporanToExcel() {}
    default public void tampilkanGrafikPendapatan() {}
    default public void loadDataDokter(String key) {}
    default public void hapusDokterDanUser(int idDokter) {}
    default public void loadDataUser(String key) {}
}

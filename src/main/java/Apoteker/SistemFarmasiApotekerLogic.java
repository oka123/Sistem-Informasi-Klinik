package Apoteker;

import javax.swing.table.DefaultTableModel;
import java.util.*;

// Import kelas model yang sudah dipisah
// NetBeans akan mengenali kelas di package yang sama secara otomatis, tapi import eksplisit lebih baik

public class SistemFarmasiApotekerLogic {
    // Simulasi Database: Kumpulan Obat dan Antrean Resep
    private Map<String, Obat> inventarisObat = new HashMap<>();
    private List<String[]> antreanResepData = new ArrayList<>();
    
    public SistemFarmasiApotekerLogic() {
        // Data Awal Obat
        inventarisObat.put("OBT001", new Obat("OBT001", "Paracetamol 500mg", "Tablet", 2000.0, 150));
        inventarisObat.put("OBT002", new Obat("OBT002", "Amoxicillin 250mg", "Kapsul", 5000.0, 50));
        inventarisObat.put("OBT003", new Obat("OBT003", "Vitamin C", "Tablet", 1000.0, 5)); // Stok Rendah
        
        // Data Awal Antrean Resep (ID Resep, Waktu, Pasien, Dokter)
        antreanResepData.add(new String[]{"RSP001", "09:00", "Budi Santoso", "Dr. Aulia"});
        antreanResepData.add(new String[]{"RSP002", "10:30", "Citra Dewi", "Dr. Bima"});
    }
    
    // Logika Database: loadData() Antrean Resep
    public DefaultTableModel loadAntreanResep() {
        String[] kolom = {"ID Resep", "Waktu", "Nama Pasien", "Nama Dokter"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        
        // SELECT ... WHERE r.status_resep = 'Belum Diambil'
        for(String[] row : antreanResepData) {
            model.addRow(row);
        }
        return model;
    }
    
    // Logika Database: loadData() Detail Resep
    public List<DetailResep> loadDetailResep(String resepId) {
        // Simulasi query SELECT o.nama_obat, dr.jumlah, dr.dosis, o.stok FROM Detail_Resep...
        List<DetailResep> details = new ArrayList<>();
        if (resepId.equals("RSP001")) {
            details.add(new DetailResep("OBT001", "Paracetamol 500mg", 10, "1x sehari", inventarisObat.get("OBT001").stok));
            details.add(new DetailResep("OBT002", "Amoxicillin 250mg", 5, "2x sehari", inventarisObat.get("OBT002").stok));
        } else if (resepId.equals("RSP002")) {
            details.add(new DetailResep("OBT003", "Vitamin C", 10, "1x sehari", inventarisObat.get("OBT003").stok)); // Stok Rendah (5)
        }
        return details;
    }
    
    // Logika Database: TRANSAKSI Selesai & Serahkan Obat
    public void serahkanObat(String resepId, List<DetailResep> details) {
        // 1. Looping tblDetailResep: UPDATE Obat SET stok = stok - ? WHERE obat_id = ?.
        for(DetailResep dr : details) {
            Obat obat = inventarisObat.get(dr.obatId);
            if (obat != null) {
                obat.stok -= dr.jumlahDiminta;
            }
        }
        
        // Simulasi: Hapus dari Antrean Resep
        antreanResepData.removeIf(row -> row[0].equals(resepId));
        
        System.out.println("LOG: Resep " + resepId + " Selesai. Stok Diperbarui.");
    }
    
    // Logika Database: CRUD Obat
    public DefaultTableModel loadObat() {
        String[] kolom = {"ID Obat", "Nama Obat", "Satuan", "Harga Jual (Rp)", "Stok"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        
        for(Obat obat : inventarisObat.values()) {
            model.addRow(new Object[]{
                obat.id, obat.nama, obat.satuan, obat.hargaJual, obat.stok
            });
        }
        return model;
    }

    public void simpanObat(Obat obat) {
        inventarisObat.put(obat.id, obat);
    }
    
    public Obat getObat(String id) {
        return inventarisObat.get(id);
    }
    
    public void hapusObat(String id) {
        inventarisObat.remove(id);
    }
}
package Apoteker;

public class Obat { // Kelas ini harus Public dan dipisah
    public String id, nama, satuan;
    public int stok;
    public double hargaJual;
    
    public Obat(String id, String nama, String satuan, double hargaJual, int stok) {
        this.id = id; 
        this.nama = nama; 
        this.satuan = satuan; 
        this.hargaJual = hargaJual; 
        this.stok = stok;
    }
}
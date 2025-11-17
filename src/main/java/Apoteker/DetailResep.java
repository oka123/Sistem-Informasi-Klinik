package Apoteker;

public class DetailResep { // Kelas ini harus Public dan dipisah
    public String obatId, namaObat, dosis;
    public int jumlahDiminta, stokSaatIni;
    
    public DetailResep(String obatId, String namaObat, int jumlahDiminta, String dosis, int stokSaatIni) {
        this.obatId = obatId; 
        this.namaObat = namaObat; 
        this.jumlahDiminta = jumlahDiminta; 
        this.dosis = dosis; 
        this.stokSaatIni = stokSaatIni;
    }
}
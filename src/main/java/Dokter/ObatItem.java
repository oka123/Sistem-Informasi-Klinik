package Dokter;

public class ObatItem {
    private String id;
    private String nama;
    private int harga;

    // Constructor 3 parameter
    public ObatItem(String id, String nama, int harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    // Untuk debugging (optional)
    @Override
    public String toString() {
        return nama;
    }
}

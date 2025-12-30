package Kasir;

import java.util.ArrayList;

public class Dummy_Data {
    private static final ArrayList<String[]> antrean = new ArrayList<>();

    static {
        antrean.add(new String[]{"1", "Adit", "Checkup", "50000", "Belum Bayar"});
        antrean.add(new String[]{"2", "Budi", "Rontgen", "150000", "Belum Bayar"});
        antrean.add(new String[]{"3", "Cici", "Laboratorium", "120000", "Belum Bayar"});
        antrean.add(new String[]{"4", "Dedi", "Obat", "70000", "Belum Bayar"});
    }

    public static ArrayList<String[]> getAntrean() {
        return antrean;
    }

    public static void updateStatus(String id) {
        for (String[] row : antrean) {
            if (row[0].equals(id)) {
                row[4] = "Selesai";
                break;
            }
        }
    }
}



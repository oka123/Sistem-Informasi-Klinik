package Apoteker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
// Import kelas yang digunakan

public class JDialogProsesResep extends JDialog {
    private JTable tblDetailResep;
    private JLabel lblInfoPasien, lblPeringatanStok;
    private JButton btnSelesai;
    private SistemFarmasiApotekerLogic logic;
    private String resepId, namaPasien;
    private List<DetailResep> detailResepList;

    public JDialogProsesResep(Frame owner, boolean modal, SistemFarmasiApotekerLogic logic, String resepId, String namaPasien) {
        super(owner, "Proses Resep: " + resepId, modal);
        this.logic = logic;
        this.resepId = resepId;
        this.namaPasien = namaPasien;
        
        setSize(600, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        
        // 1. Top Panel (Info Pasien & Peringatan)
        JPanel topPanel = new JPanel(new BorderLayout());
        lblInfoPasien = new JLabel("Pasien: " + namaPasien + " | ID Resep: " + resepId, JLabel.LEFT);
        lblInfoPasien.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblPeringatanStok = new JLabel("STOK TIDAK CUKUP! Mohon lakukan pengadaan/ganti obat.", JLabel.CENTER);
        lblPeringatanStok.setForeground(Color.RED);
        lblPeringatanStok.setFont(new Font("Arial", Font.BOLD, 14));
        lblPeringatanStok.setVisible(false);
        
        topPanel.add(lblInfoPasien, BorderLayout.NORTH);
        topPanel.add(lblPeringatanStok, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // 2. Tabel Detail Resep
        tblDetailResep = new JTable();
        loadData(); // Memuat data dan mengecek stok
        add(new JScrollPane(tblDetailResep), BorderLayout.CENTER);

        // 3. Bottom Panel (Tombol)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSelesai = new JButton("Selesai & Serahkan Obat");
        btnSelesai.addActionListener(e -> btnSelesaiClicked());
        buttonPanel.add(btnSelesai);
        add(buttonPanel, BorderLayout.SOUTH);
        
        checkStokAndEnableButton(); 
    }
    
    private void loadData() {
        detailResepList = logic.loadDetailResep(resepId);
        String[] kolom = {"Nama Obat", "Jumlah Diminta", "Dosis", "Stok Saat Ini"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        for (DetailResep dr : detailResepList) {
            model.addRow(new Object[]{dr.namaObat, dr.jumlahDiminta, dr.dosis, dr.stokSaatIni});
        }
        tblDetailResep.setModel(model);
    }
    
    private void checkStokAndEnableButton() {
        boolean stokCukup = true;
        for (DetailResep dr : detailResepList) {
            if (dr.stokSaatIni < dr.jumlahDiminta) {
                stokCukup = false;
                break;
            }
        }
        
        if (!stokCukup) {
            lblPeringatanStok.setVisible(true);
            btnSelesai.setEnabled(false);
        } else {
            btnSelesai.setEnabled(true);
        }
    }

    private void btnSelesaiClicked() {
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin resep sudah disiapkan dan siap diserahkan?", "Konfirmasi Serah Terima", JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            logic.serahkanObat(resepId, detailResepList);
            
            JOptionPane.showMessageDialog(this, "Serah terima obat berhasil. Stok telah diperbarui dan Resep ditutup.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            dispose(); 
        }
    }
}
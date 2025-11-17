package Apoteker;

import Database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class JDialog_Proses_Resep extends javax.swing.JDialog {

    private static final Logger logger = java.util.logging.Logger.getLogger(JDialog_Proses_Resep.class.getName());
    private int kunjunganId;

    public JDialog_Proses_Resep(java.awt.Frame parent, boolean modal, int kunjunganId) {
        super(parent, modal);
        this.kunjunganId = kunjunganId;
        initComponents();
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lblPeringatanStok.setVisible(false);
        lblInfoPasien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); 
        btnSelesai.addActionListener(e -> prosesPenyerahanObat());
        btnTutup.addActionListener(e -> dispose());
        loadDataResep();
    }

    public JDialog_Proses_Resep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void loadDataResep() {
        // Query untuk detail resep melalui join rekam_medis dan resep
        String sqlPasien = """
            SELECT p.nama_pasien, k.tanggal_kunjungan
            FROM kunjungan k
            JOIN pasien p ON k.pasien_id = p.pasien_id
            WHERE k.kunjungan_id = ?
        """;

        String sqlDetail = """
            SELECT o.nama_obat, dr.jumlah, dr.dosis, o.stok, o.obat_id 
            FROM detail_resep dr
            JOIN resep r ON dr.resep_id = r.resep_id
            JOIN rekam_medis rm ON r.rekam_medis_id = rm.rekam_medis_id
            JOIN obat o ON dr.obat_id = o.obat_id
            WHERE rm.kunjungan_id = ?
        """;

        try (Connection conn = KoneksiDatabase.getConnection()) {
            // 1. Muat Info Pasien
            try (PreparedStatement ps = conn.prepareStatement(sqlPasien)) {
                ps.setInt(1, kunjunganId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    lblInfoPasien.setText(String.format("Pasien: %s | Tgl: %s", 
                            rs.getString("nama_pasien"), rs.getString("tanggal_kunjungan")));
                }
            }

            // 2. Muat Detail Tabel
            DefaultTableModel model = (DefaultTableModel) tblDetailResep.getModel();
            model.setRowCount(0);
            boolean stokKurang = false;

            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setInt(1, kunjunganId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int qtyDibutuhkan = rs.getInt("jumlah");
                    int stokTersedia = rs.getInt("stok");
                    String status = (stokTersedia < qtyDibutuhkan) ? "Stok Kurang" : "OK";
                    
                    if (stokTersedia < qtyDibutuhkan) stokKurang = true;

                    model.addRow(new Object[]{
                        rs.getString("nama_obat"),
                        qtyDibutuhkan,
                        rs.getString("dosis"),
                        stokTersedia,
                        status
                    });
                }
            }

            lblPeringatanStok.setVisible(stokKurang);
            btnSelesai.setEnabled(!stokKurang);
            this.setTitle("Proses Resep Kunjungan #" + kunjunganId);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saat memuat resep", e);
            JOptionPane.showMessageDialog(this, "Gagal memuat data resep: " + e.getMessage());
        }
    }

    private void prosesPenyerahanObat() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Selesaikan resep ini? Stok obat akan otomatis dikurangi.", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;

        Connection conn = null;
        try {
            conn = KoneksiDatabase.getConnection();
            conn.setAutoCommit(false); // MEMULAI TRANSAKSI

            // 1. Ambil data obat yang harus dikurangi (Join ke rekam_medis lewat resep)
            String sqlGetItems = """
                SELECT dr.obat_id, dr.jumlah 
                FROM detail_resep dr
                JOIN resep r ON dr.resep_id = r.resep_id
                JOIN rekam_medis rm ON r.rekam_medis_id = rm.rekam_medis_id
                WHERE rm.kunjungan_id = ?
            """;

            String sqlUpdateStok = "UPDATE obat SET stok = stok - ? WHERE obat_id = ?";
            
            try (PreparedStatement psGet = conn.prepareStatement(sqlGetItems);
                 PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateStok)) {
                
                psGet.setInt(1, kunjunganId);
                ResultSet rs = psGet.executeQuery();
                
                boolean adaData = false;
                while (rs.next()) {
                    adaData = true;
                    psUpdate.setInt(1, rs.getInt("jumlah"));
                    psUpdate.setInt(2, rs.getInt("obat_id"));
                    psUpdate.addBatch();
                }
                
                if (adaData) psUpdate.executeBatch();
            }

            // 2. Update Status Kunjungan
            String sqlUpdateStatus = "UPDATE kunjungan SET status_kunjungan = 'Menunggu Pembayaran' WHERE kunjungan_id = ?";
            try (PreparedStatement psStatus = conn.prepareStatement(sqlUpdateStatus)) {
                psStatus.setInt(1, kunjunganId);
                psStatus.executeUpdate();
            }

            conn.commit(); // SIMPAN SEMUA PERUBAHAN
            JOptionPane.showMessageDialog(this, "Resep berhasil diproses. Pasien diarahkan ke Kasir.");
            dispose();

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { logger.log(Level.SEVERE, null, ex); }
            }
            logger.log(Level.SEVERE, "Gagal proses resep", e);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan database: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { /* ignore */ }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblInfoPasien = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetailResep = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblPeringatanStok = new javax.swing.JLabel();
        btnTutup = new javax.swing.JButton();
        btnSelesai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Detail Resep");

        lblInfoPasien.setText("Info pasien");
        jPanel1.add(lblInfoPasien);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tblDetailResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Obat", "Jumlah", "Dosis", "Stok Saat Ini"
            }
        ));
        tblDetailResep.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblDetailResep);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel2.setLayout(flowLayout1);

        lblPeringatanStok.setForeground(new java.awt.Color(255, 0, 0));
        lblPeringatanStok.setText("PERINGATAN: Stok obat tidak cukup. Transaksi dibatalkan");
        jPanel2.add(lblPeringatanStok);

        btnTutup.setText("Tutup");
        jPanel2.add(btnTutup);

        btnSelesai.setText("Selesai");
        jPanel2.add(btnSelesai);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            JDialog_Proses_Resep dialog = new JDialog_Proses_Resep(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelesai;
    private javax.swing.JButton btnTutup;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblInfoPasien;
    private javax.swing.JLabel lblPeringatanStok;
    private javax.swing.JTable tblDetailResep;
    // End of variables declaration//GEN-END:variables
}

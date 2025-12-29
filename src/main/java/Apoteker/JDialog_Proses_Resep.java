package Apoteker;

import Database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class JDialog_Proses_Resep extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JDialog_Proses_Resep.class.getName());
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
<<<<<<< HEAD

private void loadDataResep() {
        try (Connection conn = new KoneksiDatabase().getConnection()) {
            
            String sqlPasien = """
                SELECT p.nama_pasien, k.tanggal_kunjungan
                FROM kunjungan k
                JOIN pasien p ON k.pasien_id = p.pasien_id
                WHERE k.kunjungan_id = ?
            """;

            try (PreparedStatement ps = conn.prepareStatement(sqlPasien)) {
                ps.setInt(1, kunjunganId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    lblInfoPasien.setText(
                        "Resep Kunjungan ID: " + kunjunganId + 
                        " | Pasien: " + rs.getString("nama_pasien") +
                        " | Tgl: " + rs.getString("tanggal_kunjungan")
                    );
                }
            }

                    String sqlDetail = """
             SELECT o.nama_obat, r.jumlah, r.dosis, o.stok, o.obat_id 
             FROM detail_resep r
             JOIN obat o ON r.obat_id = o.obat_id
             WHERE r.kunjungan_id_ = ?  -- Gunakan nama kolom dengan garis bawah
         """;

            DefaultTableModel model = (DefaultTableModel) tblDetailResep.getModel();
            model.setRowCount(0);

            boolean stokKurang = false;

            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setInt(1, kunjunganId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int jumlah = rs.getInt("jumlah");
                    int stok = rs.getInt("stok");

                    String status = (stok < jumlah) ? "Stok Kurang" : "OK";
                    if (stok < jumlah) stokKurang = true;

                    model.addRow(new Object[]{
                        rs.getString("nama_obat"),
                        jumlah,
                        rs.getString("dosis"),
                        stok,
                        status
                    });
                }
            }

            lblPeringatanStok.setVisible(stokKurang);
            
            if (stokKurang) {
                btnSelesai.setVisible(false);
            } else {
                btnSelesai.setVisible(true);
                btnSelesai.setEnabled(true);
            }
            
            btnTutup.setVisible(true);
            
            this.setTitle("Proses Resep Kunjungan ID: " + kunjunganId);

        } catch (SQLException e) {
            logger.log(java.util.logging.Level.SEVERE, "Error saat memuat resep", e);
            JOptionPane.showMessageDialog(this, "Gagal memuat data resep\n" + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }

private void prosesPenyerahanObat() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Anda yakin ingin menyelesaikan resep ini dan mengurangi stok obat?", 
                "Konfirmasi Penyerahan", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = new KoneksiDatabase().getConnection()) {
            conn.setAutoCommit(false); 

            String sqlDetail = "SELECT obat_id, jumlah FROM detail_resep WHERE resep_id = ?";
            try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                psDetail.setInt(1, kunjunganId);
                ResultSet rs = psDetail.executeQuery();
                
                String sqlUpdate = "UPDATE obat SET stok = stok - ? WHERE obat_id = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    while (rs.next()) {
                        psUpdate.setInt(1, rs.getInt("jumlah"));
                        psUpdate.setString(2, rs.getString("obat_id"));
                        psUpdate.addBatch();
                    }
                    psUpdate.executeBatch();
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE kunjungan SET status_kunjungan = 'Menunggu Pembayaran' WHERE kunjungan_id = ?"
            )) {
                ps.setInt(1, kunjunganId);
                ps.executeUpdate();
            }

            conn.commit(); 
            JOptionPane.showMessageDialog(this, "Obat berhasil diserahkan dan status kunjungan diperbarui.");
            dispose();

        } catch (SQLException e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal memproses penyerahan obat", e);
            JOptionPane.showMessageDialog(this, "Gagal memproses penyerahan obat\n" + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
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
<<<<<<< HEAD
        tblDetailResep.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblDetailResep);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel2.setLayout(flowLayout1);
=======
        jScrollPane1.setViewportView(tblDetailResep);
=======

private void loadDataResep() {
        try (Connection conn = new KoneksiDatabase().getConnection()) {
            
            String sqlPasien = """
                SELECT p.nama_pasien, k.tanggal_kunjungan
                FROM kunjungan k
                JOIN pasien p ON k.pasien_id = p.pasien_id
                WHERE k.kunjungan_id = ?
            """;

            try (PreparedStatement ps = conn.prepareStatement(sqlPasien)) {
                ps.setInt(1, kunjunganId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    lblInfoPasien.setText(
                        "Resep Kunjungan ID: " + kunjunganId + 
                        " | Pasien: " + rs.getString("nama_pasien") +
                        " | Tgl: " + rs.getString("tanggal_kunjungan")
                    );
                }
            }
>>>>>>> 907995d (final (kayaknya))

            String sqlDetail = """
                SELECT o.nama_obat, r.jumlah, r.dosis, o.stok, o.obat_id 
                FROM detail_resep r
                JOIN obat o ON r.obat_id = o.obat_id
                WHERE r.kunjungan_id = ?
            """;

            DefaultTableModel model = (DefaultTableModel) tblDetailResep.getModel();
            model.setRowCount(0);

            boolean stokKurang = false;

            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setInt(1, kunjunganId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int jumlah = rs.getInt("jumlah");
                    int stok = rs.getInt("stok");

                    String status = (stok < jumlah) ? "Stok Kurang" : "OK";
                    if (stok < jumlah) stokKurang = true;

                    model.addRow(new Object[]{
                        rs.getString("nama_obat"),
                        jumlah,
                        rs.getString("dosis"),
                        stok,
                        status
                    });
                }
            }

            lblPeringatanStok.setVisible(stokKurang);
            
            if (stokKurang) {
                btnSelesai.setVisible(false);
            } else {
                btnSelesai.setVisible(true);
                btnSelesai.setEnabled(true);
            }
            
            btnTutup.setVisible(true);
            
            this.setTitle("Proses Resep Kunjungan ID: " + kunjunganId);

        } catch (SQLException e) {
            logger.log(java.util.logging.Level.SEVERE, "Error saat memuat resep", e);
            JOptionPane.showMessageDialog(this, "Gagal memuat data resep\n" + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }

private void prosesPenyerahanObat() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Anda yakin ingin menyelesaikan resep ini dan mengurangi stok obat?", 
                "Konfirmasi Penyerahan", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = new KoneksiDatabase().getConnection()) {
            conn.setAutoCommit(false); 

            String sqlDetail = "SELECT obat_id, jumlah FROM detail_resep WHERE kunjungan_id = ?";
            try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                psDetail.setInt(1, kunjunganId);
                ResultSet rs = psDetail.executeQuery();
                
                String sqlUpdate = "UPDATE obat SET stok = stok - ? WHERE obat_id = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    while (rs.next()) {
                        psUpdate.setInt(1, rs.getInt("jumlah"));
                        psUpdate.setString(2, rs.getString("obat_id"));
                        psUpdate.addBatch();
                    }
                    psUpdate.executeBatch();
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE kunjungan SET status_kunjungan = 'Menunggu Pembayaran' WHERE kunjungan_id = ?"
            )) {
                ps.setInt(1, kunjunganId);
                ps.executeUpdate();
            }

            conn.commit(); 
            JOptionPane.showMessageDialog(this, "Obat berhasil diserahkan dan status kunjungan diperbarui.");
            dispose();

        } catch (SQLException e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal memproses penyerahan obat", e);
            JOptionPane.showMessageDialog(this, "Gagal memproses penyerahan obat\n" + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
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

<<<<<<< HEAD
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
>>>>>>> 0a4dd43 ()
=======
        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel2.setLayout(flowLayout1);
>>>>>>> 907995d (final (kayaknya))

        lblPeringatanStok.setForeground(new java.awt.Color(255, 0, 0));
        lblPeringatanStok.setText("PERINGATAN: Stok obat tidak cukup. Transaksi dibatalkan");
        jPanel2.add(lblPeringatanStok);

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 907995d (final (kayaknya))
        btnTutup.setText("Tutup");
        jPanel2.add(btnTutup);

        btnSelesai.setText("Selesai");
        jPanel2.add(btnSelesai);
<<<<<<< HEAD
=======
        btnSelesaiSerahkan.setText("Selesai & Serahkan Obat");
        jPanel2.add(btnSelesaiSerahkan);
>>>>>>> 0a4dd43 ()
=======
>>>>>>> 907995d (final (kayaknya))

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

<<<<<<< HEAD
<<<<<<< HEAD
=======
    


>>>>>>> 0a4dd43 ()
=======
>>>>>>> 907995d (final (kayaknya))
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDialog_Proses_Resep dialog = new JDialog_Proses_Resep(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
<<<<<<< HEAD
<<<<<<< HEAD
    private javax.swing.JButton btnSelesai;
    private javax.swing.JButton btnTutup;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
=======
    private javax.swing.JButton btnSelesaiSerahkan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
>>>>>>> 0a4dd43 ()
=======
    private javax.swing.JButton btnSelesai;
    private javax.swing.JButton btnTutup;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
>>>>>>> 907995d (final (kayaknya))
    private javax.swing.JLabel lblInfoPasien;
    private javax.swing.JLabel lblPeringatanStok;
    private javax.swing.JTable tblDetailResep;
    // End of variables declaration//GEN-END:variables
}

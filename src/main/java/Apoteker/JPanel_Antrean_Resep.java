package Apoteker;

import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Database.KoneksiDatabase; 
import java.sql.*;

public class JPanel_Antrean_Resep extends javax.swing.JPanel {
    private final Frame parentFrame;
    private DefaultTableModel tableModel;
    public JPanel_Antrean_Resep(Frame parent) {
        initComponents();
        this.parentFrame = parent;
        initializeTableModel();
        loadDataAntrean(); 
        setupListeners();
    }

    

    private void initializeTableModel() {
    tableModel = new DefaultTableModel(
        new Object [][] {},
        new String [] {"ID Kunjungan", "Waktu Masuk", "Nama Pasien", "Dokter"} // Tambah kolom
    );
    jTable1.setModel(tableModel);
}

    public void loadDataAntrean() {
    tableModel.setRowCount(0);



    String sql = "SELECT k.kunjungan_id, k.tanggal_kunjungan, p.nama_pasien, " +
             "u.nama_lengkap AS nama_dokter, d.spesialisasi " +
             "FROM klinik.kunjungan k " +
             "JOIN klinik.pasien p ON k.pasien_id = p.pasien_id " +
             "JOIN klinik.dokter d ON k.dokter_id = d.dokter_id " + // Join ke tabel dokter
             "JOIN klinik.user u ON d.user_id = u.user_id " +      // Join ke tabel user untuk ambil nama
             "WHERE k.status_kunjungan = 'Menunggu Obat' " +
             "ORDER BY k.tanggal_kunjungan ASC";



    KoneksiDatabase db = new KoneksiDatabase();
    try (Connection conn = db.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

   while (rs.next()) {
    // Menggabungkan Nama dan Spesialisasi
    String infoDokter = rs.getString("nama_dokter") + " (" + rs.getString("spesialisasi") + ")";
    
        tableModel.addRow(new Object[]{
            rs.getString("kunjungan_id"),
            rs.getString("tanggal_kunjungan"),
            rs.getString("nama_pasien"),
            infoDokter // Ini yang akan muncul di kolom Dokter
        });
     }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat antrean resep: " + e.getMessage(), "Error DB", JOptionPane.ERROR_MESSAGE);
    }
}

    

    private void setupListeners() {
        
    btnProsesResep.addActionListener(e -> {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this, 
                "Mohon pilih resep yang ingin diproses.", 
                "Peringatan Pemilihan Resep", 
                JOptionPane.WARNING_MESSAGE
            );
            return; 
        }

        String kunjunganIdStr = tableModel.getValueAt(selectedRow, 0).toString();
        int kunjunganId;

        try {

            kunjunganId = Integer.parseInt(kunjunganIdStr);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID Kunjungan tidak valid.", "Error Data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog_Proses_Resep dialog = new JDialog_Proses_Resep(parentFrame, true, kunjunganId);

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {

                loadDataAntrean(); 
            }
        });
        dialog.setVisible(true);
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

<<<<<<< HEAD
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tblAntreanResep = new javax.swing.JLabel();
=======
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAntreanResep = new javax.swing.JTable();
>>>>>>> 0a4dd43 ()
        jPanel1 = new javax.swing.JPanel();
        btnProsesResep = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

<<<<<<< HEAD
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
=======
        jLabel1.setText("Antrean Resep Belum Diambil");
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        tblAntreanResep.setModel(new javax.swing.table.DefaultTableModel(
>>>>>>> 0a4dd43 ()
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
<<<<<<< HEAD
                "ID Pasien", "Waktu", "Nama Pasien", "Dokter"
            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tblAntreanResep.setBackground(new java.awt.Color(255, 255, 255));
        tblAntreanResep.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        tblAntreanResep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tblAntreanResep.setText("Antrean Resep");
        add(tblAntreanResep, java.awt.BorderLayout.PAGE_START);
=======
                "ID Resep", "Waktu", "Nama Pasien", "Nama Dokter"
            }
        ));
        jScrollPane1.setViewportView(tblAntreanResep);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
>>>>>>> 0a4dd43 ()

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnProsesResep.setText("Proses Resep Terpilih");
        jPanel1.add(btnProsesResep);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProsesResep;
<<<<<<< HEAD
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel tblAntreanResep;
=======
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAntreanResep;
>>>>>>> 0a4dd43 ()
    // End of variables declaration//GEN-END:variables
}

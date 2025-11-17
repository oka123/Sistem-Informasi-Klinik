
package Dokter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import Database.KoneksiDatabase;

/**
 *
 * @author USER
 */
public final class JPanel_Antrean_Pasien extends javax.swing.JPanel {

    
    private String loggedInDoctorId;
    
    public JPanel_Antrean_Pasien(String idDokter) {
    initComponents();
    this.loggedInDoctorId = idDokter;
    
    System.out.println("=== DOCTOR LOGIN INFO ===");
    System.out.println("Logged in Doctor ID: " + this.loggedInDoctorId);
    System.out.println("=========================");
    
    tblAntrean.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    tblAntrean.getTableHeader().setOpaque(false);
    tblAntrean.getTableHeader().setBackground(new java.awt.Color(32, 136, 203));
    tblAntrean.getTableHeader().setForeground(new java.awt.Color(255,255,255));
    
    // Hapus resetTableModel() dari sini
    // resetTableModel(); // HAPUS BARIS INI
    
    initPanel();
}
    private void initPanel() {
    // 1. Set tanggal hari ini
    java.time.LocalDate today = java.time.LocalDate.now();
    java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy");
    lblTanggal.setText("Tanggal: " + today.format(formatter));

    // 2. Siapkan tabel dan muat data
    resetTableModel();
    loadDataAntrean();
    
    // 3. Pasang Listener untuk filter status (ComboBox)
    comboFilterStatus.addActionListener(e -> loadDataAntrean());

    // 4. Matikan tombol periksa di awal (sebelum ada baris yang dipilih)
    btnPeriksa.setEnabled(false);

    // --- DI SINI TEMPATNYA ---
    // Listener seleksi tabel untuk mengaktifkan/menonaktifkan tombol Periksa
    tblAntrean.getSelectionModel().addListSelectionListener(e -> {
        // Cek apakah user sedang mengklik baris (bukan sedang drag) dan ada baris yang terpilih
        if (!e.getValueIsAdjusting() && tblAntrean.getSelectedRow() != -1) {
            // Ambil data status dari baris yang diklik (Kolom Index 4)
            String status = tblAntrean.getValueAt(tblAntrean.getSelectedRow(), 4).toString();

            // Tombol hanya menyala (True) jika statusnya adalah "Menunggu"
            btnPeriksa.setEnabled(status.equalsIgnoreCase("Menunggu"));
        }
    });
    // -------------------------
}

    
    public void loadDataAntrean() {
    System.out.println("=== START loadDataAntrean ===");
    
    DefaultTableModel model = (DefaultTableModel) tblAntrean.getModel();
    model.setRowCount(0);

    String statusFilter = comboFilterStatus.getSelectedItem().toString();

String sql = """
    SELECT 
        k.kunjungan_id, 
        p.pasien_id, 
        p.nama_pasien, 
        k.status_kunjungan
    FROM kunjungan k
    JOIN pasien p ON k.pasien_id = p.pasien_id
    WHERE k.dokter_id = ? 
    
"""; // CURDATE() memastikan hanya pasien hari ini yang muncul

// Tambahkan filter status jika bukan "Semua"
if (statusFilter.equals("Menunggu")) {
    sql += " AND k.status_kunjungan = 'Menunggu'";
} else if (statusFilter.equals("Selesai")) {
    sql += " AND k.status_kunjungan = 'Selesai'";
}

sql += " ORDER BY k.kunjungan_id ASC";

    System.out.println("4. SQL Query: " + sql);

    try (
        Connection conn = KoneksiDatabase.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        System.out.println("5. Connection OK: " + (conn != null));
        
        ps.setString(1, loggedInDoctorId);
        System.out.println("6. Parameter doctor_id: " + loggedInDoctorId);
        
        try (ResultSet rs = ps.executeQuery()) {
            int rowCount = 0;
            
            // Metadata check
            java.sql.ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            System.out.println("7. Number of columns: " + columnCount);
            
            while (rs.next()) {
                rowCount++;
                model.addRow(new Object[]{
                    rs.getString("kunjungan_id"), 
                    rowCount,                     
                    rs.getString("pasien_id"),    
                    rs.getString("nama_pasien"),  
                    rs.getString("status_kunjungan")
                });
            } // Penutup while

            System.out.println("9. Total rows loaded: " + rowCount);
            
            if (rowCount == 0) {
                System.out.println("10. WARNING: No data found!");
            }
        } // Penutup ResultSet (rs) otomatis tertutup di sini

    } catch (Exception e) {
        System.out.println("=== EXCEPTION DETAILS ===");
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
    
    System.out.println("=== END loadDataAntrean ===\n");
} // <-- INI TUTUP METHOD loadDataAntrean()

// BARU SETELAH INI METHOD resetTableModel()
private void resetTableModel() {
    DefaultTableModel model = new DefaultTableModel(
        new Object[][]{}, 
        new String[]{
            "ID Kunjungan", "No. Antrean", "ID Pasien", "Nama Pasien", "Status"
        }
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    tblAntrean.setModel(model);
    hideIdColumn(); // Tambahkan ini agar kolom ID langsung hilang setelah model di-set
}

    private void hideIdColumn() {
    tblAntrean.getColumnModel().getColumn(0).setMinWidth(0);
    tblAntrean.getColumnModel().getColumn(0).setMaxWidth(0);
    tblAntrean.getColumnModel().getColumn(0).setWidth(0);
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRefresh2 = new javax.swing.JButton();
        lblJudul = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelFilter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboFilterStatus = new javax.swing.JComboBox<>();
        scrollPaneTabel = new javax.swing.JScrollPane();
        tblAntrean = new javax.swing.JTable();
        panelAksi = new javax.swing.JPanel();
        btnPeriksa = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        btnRefresh2.setBackground(new java.awt.Color(0, 123, 255));
        btnRefresh2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnRefresh2.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh2.setText("🔄 Refresh");
        btnRefresh2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefresh2ActionPerformed(evt);
            }
        });

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(51, 51, 51));
        lblJudul.setText("Antrean Pasien Hari Ini");

        lblTanggal.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(102, 102, 102));
        lblTanggal.setText("Tanggal: [placeholder]");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        panelFilter.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Tampilkan Status:");

        comboFilterStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menunggu", "Selesai", "Semua" }));

        tblAntrean.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tblAntrean.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"K001", "123", "P001", "Abc", "Menunggu"},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Kunjungan", "No. Antrean", "ID Pasien", "Nama Pasien", "Status"
            }
        ));
        tblAntrean.setGridColor(new java.awt.Color(224, 224, 224));
        tblAntrean.setRowHeight(30);
        tblAntrean.setSelectionBackground(new java.awt.Color(50, 120, 220));
        tblAntrean.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblAntrean.setShowGrid(true);
        scrollPaneTabel.setViewportView(tblAntrean);

        panelAksi.setBackground(new java.awt.Color(255, 255, 255));

        btnPeriksa.setBackground(new java.awt.Color(50, 120, 220));
        btnPeriksa.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnPeriksa.setForeground(new java.awt.Color(255, 255, 255));
        btnPeriksa.setText("🔍  Periksa Pasien");
        btnPeriksa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriksaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAksiLayout = new javax.swing.GroupLayout(panelAksi);
        panelAksi.setLayout(panelAksiLayout);
        panelAksiLayout.setHorizontalGroup(
            panelAksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAksiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPeriksa)
                .addContainerGap())
        );
        panelAksiLayout.setVerticalGroup(
            panelAksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPeriksa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneTabel)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboFilterStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboFilterStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollPaneTabel, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelAksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTanggal)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblJudul)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(466, 466, 466)
                        .addComponent(btnRefresh2)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJudul)
                    .addComponent(btnRefresh2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTanggal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriksaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblAntrean.getSelectedRow();

        // Pastikan ada baris terpilih
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pasien dari tabel terlebih dahulu.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil data penting dari tabel
        String idKunjungan = tblAntrean.getValueAt(selectedRow, 0).toString();
        String idPasien = tblAntrean.getValueAt(selectedRow, 2).toString();
        String namaPasien = tblAntrean.getValueAt(selectedRow, 3).toString();

        // Tampilkan JDialog Rekam Medis (yang akan kita buat selanjutnya)
        // Kita kirim data ini ke form rekam medis
        JDialog_Rekam_Medis formRekamMedis = new JDialog_Rekam_Medis(
            null, // Parent frame
            true, // Modal
            idKunjungan,
            idPasien,
            namaPasien
        );

        formRekamMedis.setVisible(true);

        // Setelah form ditutup (data disimpan/dibatalkan),
        // muat ulang data antrean
        loadDataAntrean();
        btnPeriksa.setEnabled(false); // Nonaktifkan kembali tombol
    }//GEN-LAST:event_btnPeriksaActionPerformed

    private void btnRefresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh2ActionPerformed
        loadDataAntrean();
    }//GEN-LAST:event_btnRefresh2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPeriksa;
    private javax.swing.JButton btnRefresh2;
    private javax.swing.JComboBox<String> comboFilterStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JPanel panelAksi;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JScrollPane scrollPaneTabel;
    private javax.swing.JTable tblAntrean;
    // End of variables declaration//GEN-END:variables
}

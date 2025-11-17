
package Manajemen;

import Database.KoneksiDatabase;
import Utils.NumericFilter;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.text.PlainDocument;

public class JDialog_Form_Layanan extends javax.swing.JDialog implements Manajemen {
    // Atribut
    Connection conn = KoneksiDatabase.getConnection();
    private Integer idLayanan = null;
    
    // Constructor
    public JDialog_Form_Layanan(java.awt.Frame parent, boolean modal, Integer idLayanan) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        
        PlainDocument doc = (PlainDocument) txtBiaya.getDocument();
        doc.setDocumentFilter(new NumericFilter());
        
        this.idLayanan = idLayanan;
        txtIDLayanan.setEnabled(false);
        
        if (idLayanan != null) {
            lblTitle.setText("Edit Layanan / Jasa");
            txtIDLayanan.setText(String.valueOf(idLayanan)); // Tampilkan sebagai String di GUI
            loadDataLayananForEdit();
        } else {
            lblTitle.setText("Tambah Layanan Baru");
            txtIDLayanan.setText("Auto");
        }
    }
    
    private void loadDataLayananForEdit() {
        String sql = "SELECT * FROM layanan WHERE layanan_id = ?";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {  // PreparedStatement ditangani oleh try-with-resources
            pstmt.setInt(1, idLayanan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    txtNamaLayanan.setText(rs.getString("nama_layanan"));
                    PlainDocument doc = (PlainDocument) txtBiaya.getDocument();
                    doc.setDocumentFilter(null);
                    txtBiaya.setText(formatRupiah(rs.getDouble("biaya")));
                    doc.setDocumentFilter(new NumericFilter());
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }
    
    @Override
    public void tambahLayanan(Connection conn, String nama, double biaya) throws SQLException {
        String sql = "INSERT INTO layanan (nama_layanan, biaya) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setDouble(2, biaya);
            pstmt.executeUpdate();
        }
    }
    
    @Override
    public void editLayanan(Connection conn, String nama, double biaya) throws SQLException {
        String sql = "UPDATE layanan SET nama_layanan = ?, biaya = ? WHERE layanan_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setDouble(2, biaya);
            pstmt.setInt(3, idLayanan);
            pstmt.executeUpdate();
        }
    }
    
    // Format angka ke Rupiah Indonesia
    private String formatRupiah(double number) {
        Locale localeID = new Locale("id", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelForm = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtIDLayanan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNamaLayanan = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtBiaya = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Data Dokter");
        setModal(true);

        panelForm.setBackground(new java.awt.Color(255, 255, 255));
        panelForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelForm.setPreferredSize(new java.awt.Dimension(474, 294));

        lblTitle.setFont(new java.awt.Font("sansserif", 1, 20)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblTitle.setText("Form Data Layanan");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID Layanan");

        txtIDLayanan.setEditable(false);
        txtIDLayanan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtIDLayanan.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nama Layanan");

        txtNamaLayanan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNamaLayanan.setForeground(new java.awt.Color(0, 0, 0));

        btnSimpan.setBackground(new java.awt.Color(0, 123, 255));
        btnSimpan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(220, 53, 69));
        btnBatal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Batal");
        btnBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Biaya");

        txtBiaya.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtBiaya.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormLayout.createSequentialGroup()
                        .addGap(0, 226, Short.MAX_VALUE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNamaLayanan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIDLayanan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBiaya, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addComponent(jSeparator1)
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIDLayanan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNamaLayanan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBiaya, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(panelForm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // 1. Ambil Data dari UI
        String nama = txtNamaLayanan.getText().trim();
        String biayaStr = txtBiaya.getText().trim();

        // 2. Validasi Dasar
        if (nama.isEmpty() || biayaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!");
            return;
        }

        try {
            // 3. Validasi & Parsing Angka
            double biaya = Double.parseDouble(biayaStr);
                        
            // 5. Pilih Jalur (Tambah atau Edit)
            if (idLayanan == null) {
                tambahLayanan(this.conn, nama, biaya);
            } else {
                editLayanan(this.conn, nama, biaya);
            }

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Biaya harus berupa angka!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelForm;
    private javax.swing.JTextField txtBiaya;
    private javax.swing.JTextField txtIDLayanan;
    private javax.swing.JTextField txtNamaLayanan;
    // End of variables declaration//GEN-END:variables
}

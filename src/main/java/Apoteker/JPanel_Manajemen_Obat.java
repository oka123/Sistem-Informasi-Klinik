package Apoteker;

import java.awt.Frame;

public class JPanel_Manajemen_Obat extends javax.swing.JPanel {

    private final Frame parentFrame; // Menyimpan reference Frame utama
    
    public JPanel_Manajemen_Obat(Frame parent) {
        initComponents();
        this.parentFrame = parent;
        setupListeners();
    }
    
private void setupListeners() {
        // Logika untuk tombol "Tambah Obat"
        btnTambahObat.addActionListener(e -> {
            // Tampilkan JDialog_Form_Obat
            // Perhatikan: Pastikan JDialog_Form_Obat ada dan diimpor
            JDialog_Form_Obat dialog = new JDialog_Form_Obat(parentFrame, true); // true: modal
            dialog.setTitle("Tambah Obat Baru"); // Beri judul yang relevan
            dialog.setVisible(true);
        });

        btnEditObat.addActionListener(e -> {
            // *** Tambahkan Logika Validasi Pilihan Baris ***
            int selectedRow = tblDataObat.getSelectedRow();
            if (selectedRow != -1) {
                // Logika untuk mendapatkan data obat terpilih dari tabel
                // Object obatData = getObatDataFromTable(selectedRow); // Asumsi ada method ini
                
                // Tampilkan JDialog_Form_Obat
                JDialog_Form_Obat dialog = new JDialog_Form_Obat(parentFrame, true);
                // dialog.loadObatData(obatData); // Asumsi ada method untuk mengisi form
                dialog.setTitle("Edit Data Obat");
                dialog.setVisible(true);
            } else {
                // Tampilkan pesan error jika tidak ada baris yang dipilih
                javax.swing.JOptionPane.showMessageDialog(this, 
                        "Pilih baris obat yang ingin diedit terlebih dahulu.", 
                        "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnHapusObat.addActionListener(e -> {
             int selectedRow = tblDataObat.getSelectedRow();
             if (selectedRow != -1) {
                 // Tambahkan konfirmasi sebelum menghapus
                 int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                         "Anda yakin ingin menghapus data obat ini?", "Konfirmasi Hapus",
                         javax.swing.JOptionPane.YES_NO_OPTION);
                 if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                     // Logika hapus dari database/model
                     // ...
                 }
             } else {
                javax.swing.JOptionPane.showMessageDialog(this, 
                        "Pilih baris obat yang ingin dihapus terlebih dahulu.", 
                        "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
             }
        });
        
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnTambahObat = new javax.swing.JButton();
        btnEditObat = new javax.swing.JButton();
        btnHapusObat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataObat = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Cari Obat");
        jPanel1.add(jLabel1);
        jPanel1.add(jTextField1);

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        jPanel1.add(btnCari);

        btnTambahObat.setBackground(new java.awt.Color(0, 255, 0));
        btnTambahObat.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahObat.setText("Tambah Obat");
        jPanel1.add(btnTambahObat);

        btnEditObat.setBackground(new java.awt.Color(0, 0, 255));
        btnEditObat.setForeground(new java.awt.Color(255, 255, 255));
        btnEditObat.setText("Edit Obat");
        jPanel1.add(btnEditObat);

        btnHapusObat.setBackground(new java.awt.Color(255, 0, 0));
        btnHapusObat.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusObat.setText("Hapus Obat");
        jPanel1.add(btnHapusObat);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tblDataObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Obat", "Nama Obat", "Satuan", "Harga", "Stok"
            }
        ));
        tblDataObat.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDataObat);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEditObat;
    private javax.swing.JButton btnHapusObat;
    private javax.swing.JButton btnTambahObat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblDataObat;
    // End of variables declaration//GEN-END:variables

}
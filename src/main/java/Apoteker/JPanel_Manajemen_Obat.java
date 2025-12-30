package Apoteker;

import java.awt.Frame;
import javax.swing.table.DefaultTableModel;
import Database.KoneksiDatabase; 
import java.sql.*;

public class JPanel_Manajemen_Obat extends javax.swing.JPanel implements ObatUpdateListener {

    private final Frame parentFrame;
    private DefaultTableModel tableModel;

    public JPanel_Manajemen_Obat(Frame parent) {
        initComponents();
        this.parentFrame = parent;
        initializeTableModel();
        setupListeners();
        loadDataObat("");
    }
    
    @Override
    public void onObatUpdated() {
        loadDataObat("");
    }
    
    private void initializeTableModel() {
        String[] columnNames = {"ID Obat", "Nama Obat", "Satuan", "Harga", "Stok"};
        tableModel = new DefaultTableModel(null, columnNames);
        tblDataObat.setModel(tableModel);
    }

    public void loadDataObat(String keyword) {
        tableModel.setRowCount(0);
        String sql;
        if (keyword.isEmpty()) {
            sql = "SELECT obat_id, nama_obat, satuan, harga_jual, stok FROM obat";
        } else {
            sql = "SELECT obat_id, nama_obat, satuan, harga_jual, stok FROM obat "
                 + "WHERE nama_obat LIKE ? OR obat_id LIKE ?";
        }

        try {
            KoneksiDatabase db = new KoneksiDatabase();
            Connection conn = db.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            if (!keyword.isEmpty()) {
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("obat_id"),
                    rs.getString("nama_obat"),
                    rs.getString("satuan"),
                    rs.getDouble("harga_jual"),
                    rs.getInt("stok")
                });
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Gagal memuat data obat: " + e.getMessage(),
                    "Kesalahan",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteObat(String obatID) {
        try {
            KoneksiDatabase db = new KoneksiDatabase();
            Connection conn = db.getConnection();

            String sql = "DELETE FROM obat WHERE obat_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, obatID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                        "Data obat berhasil dihapus.", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loadDataObat(""); 
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, 
                        "Gagal menghapus data obat.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Gagal menghapus data: " + e.getMessage(), "Kesalahan Database", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEditDialog(String obatID) {
        try {
            KoneksiDatabase db = new KoneksiDatabase();
            Connection conn = db.getConnection();

            String sql = "SELECT obat_id, nama_obat, satuan, harga_jual, stok FROM obat WHERE obat_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, obatID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama_obat");
                String satuan = rs.getString("satuan");
                double harga = rs.getDouble("harga_jual");
                int stok = rs.getInt("stok");

                JDialog_Form_Obat dialog = new JDialog_Form_Obat(parentFrame, true, this); 
                dialog.setTitle("Edit Data Obat: " + obatID);
                dialog.setFormModeEdit(obatID, nama, satuan, harga, stok); 
                dialog.setVisible(true);

            } else {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Data obat tidak ditemukan.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Gagal memuat data untuk diedit: " + e.getMessage(), "Kesalahan Database", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

private void setupListeners() {
    btnTambahObat.addActionListener(e -> {
        JDialog_Form_Obat dialog = new JDialog_Form_Obat(parentFrame, true, this); 
        dialog.setTitle("Tambah Obat Baru");
        dialog.setVisible(true);
    });

    btnEditObat.addActionListener(e -> {
        int selectedRow = tblDataObat.getSelectedRow();
        if (selectedRow != -1) {
            String selectedObatID = tableModel.getValueAt(selectedRow, 0).toString();
            openEditDialog(selectedObatID);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Pilih baris obat yang ingin diedit terlebih dahulu.", 
                    "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    });

    btnHapusObat.addActionListener(e -> {
        int selectedRow = tblDataObat.getSelectedRow();
        if (selectedRow != -1) {
            String obatID = tableModel.getValueAt(selectedRow, 0).toString();
            String namaObat = tableModel.getValueAt(selectedRow, 1).toString();

            int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                    "Anda yakin ingin menghapus obat: " + namaObat + " (ID: " + obatID + ")?", 
                    "Konfirmasi Hapus", javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                deleteObat(obatID);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Pilih baris obat yang ingin dihapus terlebih dahulu.", 
                    "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    });
    
    txtCariObat.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                btnCariActionPerformed(null); 
            }
        }
        });

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCariObat = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnTambahObat = new javax.swing.JButton();
        btnEditObat = new javax.swing.JButton();
        btnHapusObat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataObat = new javax.swing.JTable();
        jScrollBar1 = new javax.swing.JScrollBar();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Cari Obat");
        jPanel1.add(jLabel1);

        txtCariObat.setPreferredSize(new java.awt.Dimension(140, 22));
        jPanel1.add(txtCariObat);

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        jPanel1.add(btnCari);

        jSeparator1.setForeground(new java.awt.Color(242, 242, 242));
        jSeparator1.setEnabled(false);
        jSeparator1.setPreferredSize(new java.awt.Dimension(70, 10));
        jPanel1.add(jSeparator1);

        btnTambahObat.setText("Tambah Obat");
        jPanel1.add(btnTambahObat);

        btnEditObat.setText("Edit Obat");
        jPanel1.add(btnEditObat);

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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDataObat.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDataObat);
        if (tblDataObat.getColumnModel().getColumnCount() > 0) {
            tblDataObat.getColumnModel().getColumn(0).setResizable(false);
            tblDataObat.getColumnModel().getColumn(1).setResizable(false);
            tblDataObat.getColumnModel().getColumn(2).setResizable(false);
            tblDataObat.getColumnModel().getColumn(3).setResizable(false);
            tblDataObat.getColumnModel().getColumn(4).setResizable(false);
        }

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
        add(jScrollBar1, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        String keyword = txtCariObat.getText().trim();

    loadDataObat(keyword);
      
    if (tableModel.getRowCount() == 0 && !keyword.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Tidak ditemukan obat dengan kata kunci: " + keyword, 
                "Hasil Pencarian", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEditObat;
    private javax.swing.JButton btnHapusObat;
    private javax.swing.JButton btnTambahObat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblDataObat;
    private javax.swing.JTextField txtCariObat;
    // End of variables declaration//GEN-END:variables

}
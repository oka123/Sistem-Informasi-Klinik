/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
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
        btnTambahObat.addActionListener(e -> {
            JDialog_Form_Obat dialog = new JDialog_Form_Obat(parentFrame, true);
            dialog.setVisible(true);
        });
        
        btnEditObat.addActionListener(e -> {
            JDialog_Form_Obat dialog = new JDialog_Form_Obat(parentFrame, true);
            dialog.setVisible(true);
        });
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnTambahObat = new javax.swing.JButton();
        btnEditObat = new javax.swing.JButton();
        btnHapusObat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataObat = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Cari Obat");
        jPanel1.add(jLabel1);
        jPanel1.add(txtCari);

        btnCari.setText("Cari");
        jPanel1.add(btnCari);

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
                "ID Obat", "Nama Obat", "Satuan", "Harga Jual", "Stok"
            }
        ));
        jScrollPane1.setViewportView(tblDataObat);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEditObat;
    private javax.swing.JButton btnHapusObat;
    private javax.swing.JButton btnTambahObat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDataObat;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}

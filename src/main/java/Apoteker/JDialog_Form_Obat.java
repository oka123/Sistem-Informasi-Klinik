package Apoteker;

import com.formdev.flatlaf.FlatLightLaf;
import Database.KoneksiDatabase;
import java.sql.*;
import javax.swing.*;

interface ObatUpdateListener {
    void onObatUpdated();
}

public class JDialog_Form_Obat extends javax.swing.JDialog {

    private boolean isEditMode = false;
    private String selectedObatID = null;

    private ObatUpdateListener updateListener; 

    public JDialog_Form_Obat(java.awt.Frame parent, boolean modal, ObatUpdateListener listener) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        
        this.updateListener = listener;
        jSpinner1.setModel(new SpinnerNumberModel(0.0, 0.0, 999999.0, 1.0)); 
        jSpinner2.setModel(new SpinnerNumberModel(0, 0, 99999, 1));

        setupListeners();
    }

    public JDialog_Form_Obat(java.awt.Frame parent, boolean modal) {
        this(parent, modal, null); 
    }

    public void setFormModeEdit(String obatID, String nama, String satuan, double harga, int stok) {
        isEditMode = true;
        selectedObatID = obatID;

        jTextField1.setText(obatID);
        jTextField1.setEnabled(false);

        jTextField2.setText(nama);
        jTextField3.setText(satuan);
        jSpinner1.setValue(harga);
        jSpinner2.setValue(stok);
        
        this.setTitle("Edit Data Obat: " + obatID);
    }

    private void setupListeners() {
        btnSimpan.addActionListener(e -> simpanData());
        btnBatal.addActionListener(e -> this.dispose());
    }

    private void simpanData() {
        String id = jTextField1.getText().trim();
        String nama = jTextField2.getText().trim();
        String satuan = jTextField3.getText().trim();
        
        double harga = 0.0;
        int stok = 0;
        try {
             harga = Double.parseDouble(jSpinner1.getValue().toString());
             stok = Integer.parseInt(jSpinner2.getValue().toString());
        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "Format Harga atau Stok tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }


        if (id.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID dan Nama Obat wajib diisi.");
            return;
        }

        try {
            KoneksiDatabase db = new KoneksiDatabase();
            Connection conn = db.getConnection();
            PreparedStatement ps;

            if (isEditMode) {
                ps = conn.prepareStatement(
                    "UPDATE obat SET nama_obat=?, satuan=?, harga_jual=?, stok=? WHERE obat_id=?"
                );
                ps.setString(1, nama);
                ps.setString(2, satuan);
                ps.setDouble(3, harga);
                ps.setInt(4, stok);
                ps.setString(5, selectedObatID);
            } else {
                ps = conn.prepareStatement(
                    "INSERT INTO obat (obat_id, nama_obat, satuan, harga_jual, stok) VALUES (?,?,?,?,?)"
                );
                ps.setString(1, id);
                ps.setString(2, nama);
                ps.setString(3, satuan);
                ps.setDouble(4, harga);
                ps.setInt(5, stok);
            }

            ps.executeUpdate();
            ps.close();
            conn.close();

            JOptionPane.showMessageDialog(this,
                isEditMode ? "Data obat berhasil diupdate!" : "Data obat berhasil ditambahkan!"
            );

            if (updateListener != null) {
                updateListener.onObatUpdated();
            }

            this.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();

            String message = ex.getMessage();
            if (message != null && message.contains("Duplicate entry") && !isEditMode) {
                JOptionPane.showMessageDialog(this, 
                    "Error: ID Obat sudah ada. Masukkan ID Obat yang berbeda.", 
                    "Error Duplikasi", JOptionPane.ERROR_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Error: Gagal menyimpan data: " + message, "Error Database", JOptionPane.ERROR_MESSAGE);
            }
           
        }
    }




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detail Obat");

        btnSimpan.setText("Simpan");
        jPanel1.add(btnSimpan);

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btnBatal);

        jLabel1.setText("ID Obat");

        jLabel2.setText("Nama Obat");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Satuan");

        jLabel4.setText("Harga Jual");

        spinnerHargaJual.setToolTipText("");
        spinnerHargaJual.setName(""); // NOI18N

        jLabel5.setText("Stok");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jSpinner1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinner2)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(246, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

public static void main(String args[]) {
    try {
        FlatLightLaf.setup();
    } catch (Exception e) {}

    java.awt.EventQueue.invokeLater(() -> {
        new JDialog_Form_Obat(null, true).setVisible(true);
    });
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

package Apoteker;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
// Import kelas yang digunakan

public class JDialogFormObat extends JDialog {
    private JTextField txtIdObat, txtNamaObat, txtSatuan;
    private JSpinner spinnerHargaJual, spinnerStok;
    private JButton btnSimpan;
    private SistemFarmasiApotekerLogic logic;
    private String obatId;
    private boolean isEdit;

    public JDialogFormObat(Frame owner, boolean modal, SistemFarmasiApotekerLogic logic, String obatId) {
        super(owner, obatId == null ? "Tambah Data Obat Baru" : "Edit Data Obat", modal);
        this.logic = logic;
        this.obatId = obatId;
        this.isEdit = obatId != null;
        
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Komponen GUI
        txtIdObat = new JTextField(isEdit ? obatId : "");
        txtIdObat.setEditable(!isEdit); 
        txtNamaObat = new JTextField();
        txtSatuan = new JTextField();
        
        SpinnerNumberModel hargaModel = new SpinnerNumberModel(isEdit ? 0.0 : 1000.0, 0.0, 100000.0, 500.0);
        spinnerHargaJual = new JSpinner(hargaModel);
        spinnerHargaJual.setEditor(new JSpinner.NumberEditor(spinnerHargaJual, "Rp#,##0.00"));
        
        SpinnerNumberModel stokModel = new SpinnerNumberModel(isEdit ? 0 : 1, 0, 10000, 1);
        spinnerStok = new JSpinner(stokModel);
        
        // Tambahkan ke Panel
        formPanel.add(new JLabel("ID Obat:")); formPanel.add(txtIdObat);
        formPanel.add(new JLabel("Nama Obat:")); formPanel.add(txtNamaObat);
        formPanel.add(new JLabel("Satuan:")); formPanel.add(txtSatuan);
        formPanel.add(new JLabel("Harga Jual:")); formPanel.add(spinnerHargaJual);
        formPanel.add(new JLabel("Stok:")); formPanel.add(spinnerStok);
        
        add(formPanel, BorderLayout.CENTER);

        // Tombol Simpan
        btnSimpan = new JButton("Simpan Data");
        btnSimpan.addActionListener(e -> btnSimpanClicked());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSimpan);
        add(buttonPanel, BorderLayout.SOUTH);
        
        if (isEdit) {
            loadDataForEdit();
        }
    }
    
    private void loadDataForEdit() {
        Obat obat = logic.getObat(obatId);
        if (obat != null) {
            txtNamaObat.setText(obat.nama);
            txtSatuan.setText(obat.satuan);
            spinnerHargaJual.setValue(obat.hargaJual);
            spinnerStok.setValue(obat.stok);
        }
    }

    private void btnSimpanClicked() {
        try {
            String id = txtIdObat.getText().trim().toUpperCase();
            String nama = txtNamaObat.getText().trim();
            String satuan = txtSatuan.getText().trim();
            double harga = (Double) spinnerHargaJual.getValue();
            int stok = (Integer) spinnerStok.getValue();
            
            if (id.isEmpty() || nama.isEmpty() || satuan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Obat obatBaru = new Obat(id, nama, satuan, harga, stok);
            logic.simpanObat(obatBaru);
            
            JOptionPane.showMessageDialog(this, "Data Obat berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
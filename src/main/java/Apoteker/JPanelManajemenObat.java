package Apoteker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
// Import kelas yang digunakan

public class JPanelManajemenObat extends JPanel {
    private JTable tblObat;
    private JButton btnTambah, btnEdit, btnHapus, btnCari;
    private JTextField txtCari;
    private JFrame parentFrame;
    private SistemFarmasiApotekerLogic logic;

    public JPanelManajemenObat(JFrame parent, SistemFarmasiApotekerLogic logic) {
        this.parentFrame = parent;
        this.logic = logic;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(createSearchPanel(), BorderLayout.NORTH);
        topPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        tblObat = new JTable();
        loadData();
        tblObat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tblObat), BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Cari Obat:"));
        txtCari = new JTextField(20);
        btnCari = new JButton("Cari");
        panel.add(txtCari);
        panel.add(btnCari);
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTambah = new JButton("➕ Tambah Obat");
        btnEdit = new JButton("✎ Edit Obat");
        btnHapus = new JButton("❌ Hapus Obat");
        
        btnTambah.addActionListener(e -> openForm(false));
        btnEdit.addActionListener(e -> openForm(true));
        btnHapus.addActionListener(e -> hapusObat());
        
        panel.add(btnTambah);
        panel.add(btnEdit);
        panel.add(btnHapus);
        return panel;
    }
    
    public void loadData() {
        tblObat.setModel(logic.loadObat());
    }

    private void openForm(boolean isEdit) {
        String obatId = null;
        if (isEdit) {
            int selectedRow = tblObat.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris untuk di edit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            obatId = (String) tblObat.getValueAt(selectedRow, 0);
        }
        
        JDialogFormObat dialog = new JDialogFormObat(parentFrame, true, logic, obatId);
        dialog.setVisible(true);
        loadData(); // Refresh setelah dialog ditutup
    }
    
    private void hapusObat() {
        int selectedRow = tblObat.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String obatId = (String) tblObat.getValueAt(selectedRow, 0);
        
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus obat ID: " + obatId + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            logic.hapusObat(obatId);
            JOptionPane.showMessageDialog(this, "Obat berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        }
    }
}
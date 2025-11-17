package Apoteker;

import javax.swing.*;
import java.awt.*;
// Import kelas yang digunakan

public class JPanelAntreanResep extends JPanel {
    private JTable tblAntreanResep;
    private JButton btnProsesResep;
    private JFrame parentFrame;
    private SistemFarmasiApotekerLogic logic;

    public JPanelAntreanResep(JFrame parent, SistemFarmasiApotekerLogic logic) {
        this.parentFrame = parent;
        this.logic = logic;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Antrean Resep Belum Diambil", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        tblAntreanResep = new JTable();
        loadData(); 
        tblAntreanResep.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tblAntreanResep), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnProsesResep = new JButton("Proses Resep Terpilih");
        btnProsesResep.addActionListener(e -> btnProsesResepClicked());
        buttonPanel.add(btnProsesResep);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void loadData() {
        tblAntreanResep.setModel(logic.loadAntreanResep());
    }

    private void btnProsesResepClicked() {
        int selectedRow = tblAntreanResep.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih resep yang akan diproses.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String resepId = (String) tblAntreanResep.getValueAt(selectedRow, 0);
        String namaPasien = (String) tblAntreanResep.getValueAt(selectedRow, 2);

        JDialogProsesResep dialog = new JDialogProsesResep(parentFrame, true, logic, resepId, namaPasien);
        dialog.setVisible(true);
        
        loadData(); // Refresh tabel setelah dialog ditutup
    }
}
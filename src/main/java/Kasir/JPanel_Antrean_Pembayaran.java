package Kasir;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class JPanel_Antrean_Pembayaran extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtCari;
    private JButton btnProses, btnRefresh;

    public JPanel_Antrean_Pembayaran() {
        setLayout(new BorderLayout());

        String[] kolom = {"ID", "Nama Pasien", "Layanan", "Biaya", "Status"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        JPanel panelAtas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCari = new JTextField(20);
        btnRefresh = new JButton("Refresh");
        panelAtas.add(new JLabel("Cari Nama:"));
        panelAtas.add(txtCari);
        panelAtas.add(btnRefresh);

        btnProses = new JButton("Proses Pembayaran");

        add(panelAtas, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnProses, BorderLayout.SOUTH);

        loadData();
        setupListeners();
    }

    private void loadData() {
        model.setRowCount(0);
        for (String[] row : Dummy_Data.getAntrean()) {
            model.addRow(row);
        }
    }

    private void setupListeners() {
        btnRefresh.addActionListener(e -> loadData());

        txtCari.addCaretListener(e -> {
            String keyword = txtCari.getText().toLowerCase();
            model.setRowCount(0);
            for (String[] row : Dummy_Data.getAntrean()) {
                if (row[1].toLowerCase().contains(keyword)) {
                    model.addRow(row);
                }
            }
        });

        btnProses.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Pilih data dulu!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = model.getValueAt(selectedRow, 0).toString();
            String nama = model.getValueAt(selectedRow, 1).toString();
            String biaya = model.getValueAt(selectedRow, 3).toString();

            new Dialog_Pembayaran(null, true, id, nama, biaya).setVisible(true);
            loadData();
        });
    }
}


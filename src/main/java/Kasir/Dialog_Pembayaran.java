package Kasir;

import javax.swing.*;
import java.awt.*;

public class Dialog_Pembayaran extends JDialog {

    private JLabel lblNama, lblBiaya, lblKembalian;
    private JTextField txtJumlah;
    private JButton btnBayar, btnCancel;
    
    private String idPasien, namaPasien, biayaStr;

    public Dialog_Pembayaran(Frame parent, boolean modal, String id, String nama, String biaya) {
        super(parent, modal);
        this.idPasien = id;
        this.namaPasien = nama;
        this.biayaStr = biaya;
        initComponents();
    }

    private void initComponents() {
        setTitle("Pembayaran Pasien");
        setSize(350, 250);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(null);

        lblNama = new JLabel(namaPasien);
        lblBiaya = new JLabel(biayaStr);
        txtJumlah = new JTextField();
        lblKembalian = new JLabel("-");
        btnBayar = new JButton("Bayar");
        btnCancel = new JButton("Batal");

        add(new JLabel("Nama:"));
        add(lblNama);
        add(new JLabel("Biaya:"));
        add(lblBiaya);
        add(new JLabel("Jumlah Bayar:"));
        add(txtJumlah);
        add(new JLabel("Kembalian:"));
        add(lblKembalian);
        add(btnBayar);
        add(btnCancel);

        btnBayar.addActionListener(e -> prosesPembayaran());
        btnCancel.addActionListener(e -> dispose());
    }

    private void prosesPembayaran() {
        try {
            int biaya = Integer.parseInt(biayaStr);
            int bayar = Integer.parseInt(txtJumlah.getText());

            if (bayar < biaya) {
                JOptionPane.showMessageDialog(this, "Uang kurang!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int kembalian = bayar - biaya;
            lblKembalian.setText("Rp " + kembalian);

            Dummy_Data.updateStatus(idPasien);
            JOptionPane.showMessageDialog(this, "Pembayaran berhasil!");

            dispose();
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package Kasir;

import Main.JFrame_Login;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class JFrame_Main_Kasir extends JFrame {

    private JPanel panelSidebar;
    private JPanel panelContent;
    private JButton btnAntrean;
    private JButton btnLogout; // Tambahkan deklarasi tombol logout

    public JFrame_Main_Kasir() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kasir - Klinik Sehat Selalu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Sidebar
        panelSidebar = new JPanel();
        panelSidebar.setPreferredSize(new Dimension(200, getHeight()));
        panelSidebar.setLayout(new GridLayout(0, 1)); // Menggunakan GridLayout untuk fleksibilitas penempatan

        btnAntrean = new JButton("Antrean Pembayaran");
        panelSidebar.add(btnAntrean);

        // Membuat panel baru untuk tombol logout agar lebih fleksibel dalam penempatan
        JPanel panelLogout = new JPanel();
        panelLogout.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // FlowLayout dengan margin 10px di kiri dan vertikal
        btnLogout = new JButton("➜]  Logout");
        btnLogout.setBackground(new java.awt.Color(220, 53, 69));
        btnLogout.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        panelLogout.add(btnLogout); // Menambahkan tombol logout ke panel

        panelSidebar.add(panelLogout); // Menambahkan panel logout ke sidebar

        add(panelSidebar, BorderLayout.WEST);

        // Content
        panelContent = new JPanel(new CardLayout());
        JPanel_Antrean_Pembayaran panelAntrean = new JPanel_Antrean_Pembayaran();
        panelContent.add(panelAntrean, "PANEL_ANTREAN");
        add(panelContent, BorderLayout.CENTER);

        // Switch panel
        btnAntrean.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelContent.getLayout();
            cl.show(panelContent, "PANEL_ANTREAN");
        });
    }

    // Action listener untuk tombol logout
    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Konfirmasi logout
        int pilihan = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin logout?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (pilihan == JOptionPane.YES_OPTION) {
            new JFrame_Login().setVisible(true); // Buka login
            this.dispose(); // Tutup dashboard ini
        }
    }    

    public static void main(String[] args) {
        new JFrame_Main_Kasir().setVisible(true);
    }
}

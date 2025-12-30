<<<<<<< HEAD
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
=======
package Kasir;
import com.formdev.flatlaf.FlatLightLaf;
/**
 *
 * @author karuna
 */
public class JFrame_Main_Kasir extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFrame_Main_Kasir.class.getName());
    
    // Deklarasi panel konten baru
    private JPanel_Antrean_Pembayaran panelAntreanPembayaran; 
    private JPanel_Riwayat_Transaksi panelRiwayatTransaksi;
    /**
     * Creates new form JFrame_MainKasir
     */
    public JFrame_Main_Kasir() {
        initComponents();
        
        // Inisialisasi panel konten
        panelAntreanPembayaran = new JPanel_Antrean_Pembayaran(); 
        panelRiwayatTransaksi = new JPanel_Riwayat_Transaksi();
        // Tambahkan panel ke PanelKonten dengan nama tertentu untuk CardLayout
        PanelKonten.add(panelAntreanPembayaran, "ANTREAN_PEMBAYARAN"); 
        PanelKonten.add(panelRiwayatTransaksi, "RIWAYAT_TRANSAKSI");
        // Panggil method untuk mengatur aksi tombol
        setupListeners();
        
        // Tampilkan panel antrean pembayaran sebagai panel default saat aplikasi dimulai
        showPanel("ANTREAN_PEMBAYARAN");
    }
    
    private void setupListeners() {
        btnAntreanPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntreanPembayaranActionPerformed(evt);
            }
        });
        
        // Listener untuk tombol Riwayat Transaksi (jika Anda memiliki panelnya)
        btnRiwayatTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatTransaksiActionPerformed(evt);
            }
        });
    }

    // Method baru untuk menampilkan panel berdasarkan nama CardLayout
    private void showPanel(String panelName) {
        java.awt.CardLayout cl = (java.awt.CardLayout)(PanelKonten.getLayout());
        cl.show(PanelKonten, panelName);
    }
    
    // ... (rest of the class)
    
    // Tambahkan method action listener yang baru
    private void btnAntreanPembayaranActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        panelAntreanPembayaran.tampilkanAntrean(); // <--- TAMBAHAN: Memanggil method refresh data
        showPanel("ANTREAN_PEMBAYARAN"); // Tampilkan panel antrean pembayaran
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSidebar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAntreanPembayaran = new javax.swing.JButton();
        btnRiwayatTransaksi = new javax.swing.JButton();
        PanelKonten = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Menu Kasir");

        btnAntreanPembayaran.setText("Antrean Pembayaran");

        btnRiwayatTransaksi.setText("Riwayat Transaksi");
        btnRiwayatTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatTransaksiActionPerformed(evt);
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
>>>>>>> 8cae046 (final (kayaknya)2)

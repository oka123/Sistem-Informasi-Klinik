package Apoteker;

import Main.JFrame_Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Import semua komponen GUI yang digunakan

public class JFrameMainApoteker extends JFrame {
    private JTabbedPane mainTabs;

    public JFrameMainApoteker() {
        setTitle("💊 Apoteker - Klinik Sehat Selalu");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        mainTabs = new JTabbedPane();
        
        add(createSidebar(), BorderLayout.WEST);
        add(mainTabs, BorderLayout.CENTER);

        addContentTabs();
        
        setVisible(true);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(11, 1, 5, 5));  // Menambahkan satu baris lagi untuk tombol Logout
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        
        // HANYA SIDEBAR YANG DIUBAH MENJADI BIRU MUDA
        sidebar.setBackground(new Color(173, 216, 230)); // Light blue untuk sidebar saja
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Menu Apoteker");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setOpaque(true);
        title.setBackground(new Color(173, 216, 230)); // Background judul sidebar
        sidebar.add(title);
        
        JSeparator separator = new JSeparator();
        separator.setBackground(new Color(100, 149, 237));
        sidebar.add(separator);

        JButton btnAntrean = new JButton("1. Antrean Resep");
        JButton btnManajemen = new JButton("2. Manajemen Obat");
        JButton btnLogout = new JButton("Logout"); // Tombol Logout
        
        // Button dalam sidebar juga disesuaikan
        btnAntrean.setBackground(new Color(240, 248, 255));
        btnManajemen.setBackground(new Color(240, 248, 255));
        btnLogout.setBackground(new Color(220,53,69)); // Warna tombol Logout (Tomato)
        btnLogout.setForeground(new Color(255,255,255));
        
        // Menambahkan aksi tombol Antrean dan Manajemen
        btnAntrean.addActionListener(e -> mainTabs.setSelectedIndex(0));
        btnManajemen.addActionListener(e -> mainTabs.setSelectedIndex(1));

        // Menambahkan aksi tombol Logout
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menampilkan konfirmasi sebelum logout
                int response = JOptionPane.showConfirmDialog(
                        JFrameMainApoteker.this,
                        "Apakah Anda yakin ingin logout?",
                        "Konfirmasi Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                
                if (response == JOptionPane.YES_OPTION) {
                    // Menutup jendela aplikasi dan kembali ke layar login
                    JFrame_Login loginFrame = new JFrame_Login(); // Misalnya, JFrameLogin adalah frame login kamu
                    loginFrame.setVisible(true);
                    JFrameMainApoteker.this.dispose(); // Menutup frame Apoteker
                }
            }
        });
        
        sidebar.add(btnAntrean);
        sidebar.add(btnManajemen);
        sidebar.add(btnLogout); // Menambahkan tombol Logout ke sidebar
        
        return sidebar;
    }
    
    private void addContentTabs() {
        SistemFarmasiApotekerLogic logic = new SistemFarmasiApotekerLogic(); 
        
        JPanelAntreanResep antreanPanel = new JPanelAntreanResep(this, logic);
        mainTabs.addTab("Antrean Resep", antreanPanel);
        
        JPanelManajemenObat obatPanel = new JPanelManajemenObat(this, logic);
        mainTabs.addTab("Manajemen Obat", obatPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JFrameMainApoteker());
    }
}

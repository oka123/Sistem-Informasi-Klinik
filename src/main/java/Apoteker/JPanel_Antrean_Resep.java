package Apoteker;

import java.awt.Frame;
import javax.swing.JOptionPane;

public class JPanel_Antrean_Resep extends javax.swing.JPanel {

    private final Frame parentFrame;
    
    public JPanel_Antrean_Resep(Frame parent) {
        initComponents();
        this.parentFrame = parent;
        setupListeners();
    }
    
    private void setupListeners() {
        btnProsesResep.addActionListener(e -> {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Mohon pilih resep yang ingin diproses.", 
                    "Peringatan Pemilihan Resep", 
                    JOptionPane.WARNING_MESSAGE
                );
                return; 
            }
            JDialog_Proses_Resep dialog = new JDialog_Proses_Resep(parentFrame, true);
            dialog.setVisible(true);
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tblAntreanResep = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnProsesResep = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Pasien", "Waktu", "Nama Pasien", "Dokter"
            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tblAntreanResep.setBackground(new java.awt.Color(255, 255, 255));
        tblAntreanResep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tblAntreanResep.setText("Antrean Resep Belum Diambil");
        add(tblAntreanResep, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnProsesResep.setText("Proses Resep Terpilih");
        jPanel1.add(btnProsesResep);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProsesResep;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel tblAntreanResep;
    // End of variables declaration//GEN-END:variables
}

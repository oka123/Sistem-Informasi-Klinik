/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Resepsionis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

/**
 *
 * @author USER
 */
public class JPanel_Registrasi_Kunjungan extends javax.swing.JPanel {
    
    private String loggedInResepsionisId;
    private String selectedPasienId = null; // Menyimpan ID pasien yg ditemukan
    
    /**
     * Creates new form JPanel_Registrasi_Kunjungan
     */
    // Kelas helper untuk JComboBox
    class DokterItem {
        String id;
        String nama;

        public DokterItem(String id, String nama) {
            this.id = id;
            this.nama = nama;
        }

        @Override
        public String toString() {
            return nama; // Ini yang akan tampil di layar
        }
    }
    
    // Class Khusus untuk Mewarnai Baris Tabel
    class StatusRenderer extends javax.swing.table.DefaultTableCellRenderer {

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {

            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // 1. Ambil Status dari Kolom ke-4 (Index 4 adalah kolom Status di query kita sebelumnya)
            // Pastikan index kolom status benar. Di method loadDataAntrean, urutannya:
            // 0: No, 1: Nama, 2: Poli, 3: Dokter, 4: Status
            Object statusObj = table.getValueAt(row, 4); 
            String status = (statusObj != null) ? statusObj.toString() : "";

            // 2. Tentukan Warna Berdasarkan Status
            if ("Selesai".equalsIgnoreCase(status)) {
                c.setBackground(new java.awt.Color(200, 255, 200)); // Hijau Muda
                c.setForeground(java.awt.Color.BLACK);
            } else if ("Diperiksa".equalsIgnoreCase(status)) {
                c.setBackground(new java.awt.Color(255, 255, 200)); // Kuning Muda
                c.setForeground(java.awt.Color.BLACK);
            } else if ("Batal".equalsIgnoreCase(status)) {
                c.setBackground(new java.awt.Color(255, 200, 200)); // Merah Muda
                c.setForeground(java.awt.Color.BLACK);
            } else {
                // Status "Menunggu" atau default
                c.setBackground(java.awt.Color.WHITE);
                c.setForeground(java.awt.Color.BLACK);
            }

            // 3. Jaga agar warna seleksi (saat diklik) tetap biru
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }

            return c;
        }
    }
    
    
    public JPanel_Registrasi_Kunjungan() {
        initComponents();
        
        loadDataAntrean();
        loadDataPoli();
        tampilkanEstimasiAntrean();
        
        //Memastikan label estimasi anrean di tengah
        label_antrean.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // --- PASANG PEWARNA TABEL ---
        // Kita pasang renderer ini ke SEMUA kolom (0 sampai 4)
        StatusRenderer renderer = new StatusRenderer();
        for (int i = 0; i < tblAntrean.getColumnCount(); i++) {
            tblAntrean.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        txtCariPasien.putClientProperty("JTextField.placeholderText", "Ketik ID atau Nama...");
        
        // --- TAMBAHKAN BARIS INI UNTUK MENYEMBUNYIKANNYA ---
        panelInfoPasien.setVisible(false); 
        // ---

        initForm();
        
        // --- FITUR KLIK KANAN PADA TABEL ---
        javax.swing.JPopupMenu popupMenu = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem itemDiperiksa = new javax.swing.JMenuItem("Set Status: Diperiksa");
        javax.swing.JMenuItem itemSelesai = new javax.swing.JMenuItem("Set Status: Selesai");
        javax.swing.JMenuItem itemBatal = new javax.swing.JMenuItem("Batalkan Antrean/Hapus Pasien");

        popupMenu.add(itemDiperiksa);
        popupMenu.add(itemSelesai);
        popupMenu.addSeparator(); // Garis pemisah
        popupMenu.add(itemBatal);

        // 1. Aksi Menu "Diperiksa"
        itemDiperiksa.addActionListener(e -> {
            int row = tblAntrean.getSelectedRow();
            if (row != -1) {
                String id = tblAntrean.getValueAt(row, 0).toString(); // Ambil No. Antrean
                updateStatusKunjungan(id, "Diperiksa");
            }
        });

        // 2. Aksi Menu "Selesai"
        itemSelesai.addActionListener(e -> {
            int row = tblAntrean.getSelectedRow();
            if (row != -1) {
                String id = tblAntrean.getValueAt(row, 0).toString();
                updateStatusKunjungan(id, "Selesai");
            }
        });

        // 3. Aksi Menu "Batal" (Hapus data atau set batal)
        itemBatal.addActionListener(e -> {
            int row = tblAntrean.getSelectedRow();
            if (row != -1) {
                String id = tblAntrean.getValueAt(row, 0).toString();
                int jawab = javax.swing.JOptionPane.showConfirmDialog(this, "Yakin batalkan antrean " + id + "?");
                if (jawab == javax.swing.JOptionPane.YES_OPTION) {
                    // Kita gunakan method updateStatusKunjungan tapi kita ubah querynya nanti kalau mau DELETE
                    // Untuk sekarang kita set status jadi "Batal" (Pastikan ENUM di DB mendukung 'Batal')
                    // Jika tidak, hapus saja:
                     hapusAntrean(id); // Kita buat method hapus di bawah
                }
            }
        });

        // Pasang Mouse Listener ke Tabel
        tblAntrean.setComponentPopupMenu(popupMenu);
        tblAntrean.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // Agar baris otomatis terpilih saat klik kanan
                if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
                    int row = tblAntrean.rowAtPoint(e.getPoint());
                    tblAntrean.setRowSelectionInterval(row, row);
                }
            }
        });
        
    }
    
    
    
    public JPanel_Registrasi_Kunjungan(String resepsionisId) {
        initComponents();
        this.loggedInResepsionisId = resepsionisId;

        initForm(); // Panggil method kustom
    }
    
    private void initForm() {
        // 1. Sembunyikan/Nonaktifkan komponen
        panelInfoPasien.setVisible(false);
        panelStep2.setEnabled(false);
        btnDaftarkan.setEnabled(false);

        // 2. Format JSpinner
//        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerBiayaJasa, "#,##0");
//        spinnerBiayaJasa.setEditor(editor);

        // 3. Muat data dokter ke JComboBox
        loadDokterComboBox();

        // 4. Tambahkan listener ke comboDokter
        cbDokter.addActionListener(e -> {
            DokterItem item = (DokterItem) cbDokter.getSelectedItem();
            if (item != null) {
//                label_dokter.setText(item.spesialisasi);
            }
        });
    }

    private void loadDokterComboBox() {
        // SQL: "SELECT id_dokter, nama_dokter, spesialisasi FROM dokter"
        // ... (Eksekusi query) ...
        // while (rs.next()) {
        //    comboDokter.addItem(new DokterItem(rs.getString("id_dokter"), rs.getString("nama_dokter"), rs.getString("spesialisasi")));
        // }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSeparator1 = new javax.swing.JSeparator();
        lblJudul = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelStep1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCariPasien = new javax.swing.JTextField();
        btnCariPasien = new javax.swing.JButton();
        panelInfoPasien = new javax.swing.JPanel();
        labelID = new javax.swing.JLabel();
        label_id = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        label_nama = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        label_tgl_lahir = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        label_alamat = new javax.swing.JLabel();
        panelStep2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbDokter = new javax.swing.JComboBox();
        cbPoli = new javax.swing.JComboBox<>();
        PanelEstimasi = new javax.swing.JPanel();
        label_judul = new javax.swing.JLabel();
        label_antrean = new javax.swing.JLabel();
        btnDaftarkan = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAntrean = new javax.swing.JTable();

        setBackground(java.awt.Color.white);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(51, 51, 51));
        lblJudul.setText("Registrasi Kunjungan Pasien");

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Daftarkan pasien yang berkunjung hari ini ke antrean dokter.");

        panelStep1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari Pasien", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("Cari (ID atau Nama Pasien):");

        txtCariPasien.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        btnCariPasien.setBackground(new java.awt.Color(50, 120, 220));
        btnCariPasien.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnCariPasien.setForeground(java.awt.Color.white);
        btnCariPasien.setText("🔍  Cari");
        btnCariPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPasienActionPerformed(evt);
            }
        });

        panelInfoPasien.setBackground(new java.awt.Color(245, 245, 245));
        panelInfoPasien.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail Data Pasien"));
        panelInfoPasien.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        labelID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelID.setForeground(java.awt.Color.black);
        labelID.setText("ID Pasien:");

        label_id.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        label_id.setForeground(java.awt.Color.black);
        label_id.setText("-");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.black);
        jLabel5.setText("Nama:");

        label_nama.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        label_nama.setForeground(java.awt.Color.black);
        label_nama.setText("-");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.black);
        jLabel6.setText("Tgl. Lahir:");

        label_tgl_lahir.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        label_tgl_lahir.setForeground(java.awt.Color.black);
        label_tgl_lahir.setText("-");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.black);
        jLabel8.setText("Alamat");

        label_alamat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        label_alamat.setForeground(java.awt.Color.black);
        label_alamat.setText("-");

        javax.swing.GroupLayout panelInfoPasienLayout = new javax.swing.GroupLayout(panelInfoPasien);
        panelInfoPasien.setLayout(panelInfoPasienLayout);
        panelInfoPasienLayout.setHorizontalGroup(
            panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPasienLayout.createSequentialGroup()
                .addGroup(panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelID)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_nama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_tgl_lahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_alamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelInfoPasienLayout.setVerticalGroup(
            panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPasienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_tgl_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoPasienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelStep1Layout = new javax.swing.GroupLayout(panelStep1);
        panelStep1.setLayout(panelStep1Layout);
        panelStep1Layout.setHorizontalGroup(
            panelStep1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStep1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStep1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInfoPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelStep1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCariPasien))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStep1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelStep1Layout.setVerticalGroup(
            panelStep1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStep1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelStep1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelInfoPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelStep2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        panelStep2.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Pilih Poli");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setText("Dokter");

        cbDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dokter Kliniks", "Wahyu Triadi", "Amelia Devi" }));

        cbPoli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Poli Umum", "Poli Gigi", "Poli Anak", "Poli Mata", "Poli THT" }));
        cbPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPoliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelStep2Layout = new javax.swing.GroupLayout(panelStep2);
        panelStep2.setLayout(panelStep2Layout);
        panelStep2Layout.setHorizontalGroup(
            panelStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStep2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPoli, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        panelStep2Layout.setVerticalGroup(
            panelStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStep2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(panelStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPoli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        PanelEstimasi.setLayout(new java.awt.GridBagLayout());

        label_judul.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        label_judul.setForeground(new java.awt.Color(255, 255, 255));
        label_judul.setText("Estimasi Nomor Antrean Berikutnya");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        PanelEstimasi.add(label_judul, gridBagConstraints);

        label_antrean.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        label_antrean.setForeground(new java.awt.Color(51, 255, 51));
        label_antrean.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_antrean.setText("-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        PanelEstimasi.add(label_antrean, gridBagConstraints);

        btnDaftarkan.setBackground(new java.awt.Color(25, 135, 84));
        btnDaftarkan.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        btnDaftarkan.setForeground(java.awt.Color.white);
        btnDaftarkan.setText("✚  Daftarkan Pasien ke Antrean");
        btnDaftarkan.setEnabled(false);
        btnDaftarkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarkanActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Daftar Antrean Pasien Hari Ini"));

        tblAntrean.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No Antrean", "Nama Pasien", "Poli", "Dokter Tujuan", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblAntrean);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(panelStep1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelStep2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDaftarkan))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(lblJudul))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addComponent(PanelEstimasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblJudul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelStep1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelStep2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(PanelEstimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDaftarkan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPasienActionPerformed
        // TODO add your handling code here:
        cariPasien();
       
        // }
    }//GEN-LAST:event_btnCariPasienActionPerformed

    private void btnDaftarkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarkanActionPerformed
       // 1. Validasi: Pastikan pasien sudah dipilih
    if (this.selectedPasienId == null || this.selectedPasienId.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Kesalahan: Harap pilih pasien terlebih dahulu dari hasil pencarian.");
        return;
    }

    // 2. Ambil Data dari Form
    String pasienId = this.selectedPasienId;
    
    // PENTING: Pastikan Anda punya variabel ini. Jika belum login, ganti sementara dengan "1" (ID Admin default)
    // String resepsionisId = this.loggedInResepsionisId; 
    String resepsionisId = "1"; // <-- GANTI INI NANTI dengan ID user yang login
    
    // Ambil Dokter (Gunakan class DokterPilihan yang baru kita buat)
    if (cbDokter.getSelectedItem() == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih dokter tujuan!");
        return;
    }
    
    // Casting ke DokterPilihan (sesuai perbaikan sebelumnya)
    DokterItem dokter = (DokterItem) cbDokter.getSelectedItem();
    String dokterId = dokter.id;

    // 3. Konfirmasi (Opsional, biar aman)
    int pilihan = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Daftarkan pasien " + label_nama.getText() + "\nKe " + dokter.nama + "?",
            "Konfirmasi Pendaftaran", 
            javax.swing.JOptionPane.YES_NO_OPTION);

    if (pilihan != javax.swing.JOptionPane.YES_OPTION) {
        return; // Batal jika user klik No
    }

    // 4. Eksekusi INSERT ke Database
    // Query sesuai tabel 'kunjungan' di gambar image_554e49.jpg
    String sql = "INSERT INTO kunjungan (pasien_id, dokter_id, user_resepsionis_id, tanggal_kunjungan, status_kunjungan) " +
                 "VALUES (?, ?, ?, NOW(), 'Menunggu')";

    try (java.sql.Connection conn = Database.KoneksiDatabase.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Set Parameter Tanda Tanya (?)
        stmt.setString(1, pasienId);      // Pasien ID
        stmt.setString(2, dokterId);      // Dokter ID
        stmt.setString(3, resepsionisId); // Resepsionis ID
        
        // Eksekusi
        int berhasil = stmt.executeUpdate();

        if (berhasil > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Pasien berhasil didaftarkan ke antrean!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // --- PENTING: Refresh Tabel Bawah ---
            loadDataAntrean(); 
            // ------------------------------------
            //refresh angka estimasi
            tampilkanEstimasiAntrean();
            
            // Reset Form biar bersih
            txtCariPasien.setText("");
            // Kosongkan label detail pasien jika perlu
            // labelNama.setText("-"); 
            selectedPasienId = null; // Reset ID terpilih
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan ke database: " + e.getMessage(), "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Cek error di output bawah NetBeans
    }
    }//GEN-LAST:event_btnDaftarkanActionPerformed

    private void cbPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPoliActionPerformed
        // TODO add your handling code here:
        // Cek apakah ada item yang dipilih (menghindari error saat loading awal)
        if (cbPoli.getSelectedItem() != null) {
            String poli = cbPoli.getSelectedItem().toString();
            loadDokterByPoli(poli);
        }
       
    }//GEN-LAST:event_cbPoliActionPerformed
    
    private void resetForm() {
        txtCariPasien.setText("");
        panelInfoPasien.setVisible(false);
        panelStep2.setEnabled(false);
        btnDaftarkan.setEnabled(false);
        cbDokter.setSelectedIndex(0);
//        spinnerBiayaJasa.setValue(50000);
        this.selectedPasienId = null;
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelEstimasi;
    private javax.swing.JButton btnCariPasien;
    private javax.swing.JButton btnDaftarkan;
    private javax.swing.JComboBox cbDokter;
    private javax.swing.JComboBox<String> cbPoli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelID;
    private javax.swing.JLabel label_alamat;
    private javax.swing.JLabel label_antrean;
    private javax.swing.JLabel label_id;
    private javax.swing.JLabel label_judul;
    private javax.swing.JLabel label_nama;
    private javax.swing.JLabel label_tgl_lahir;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JPanel panelInfoPasien;
    private javax.swing.JPanel panelStep1;
    private javax.swing.JPanel panelStep2;
    private javax.swing.JTable tblAntrean;
    private javax.swing.JTextField txtCariPasien;
    // End of variables declaration//GEN-END:variables


    private void cariPasien() {
        String keyword = txtCariPasien.getText().trim();

        // Validasi jika kosong
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mohon isi ID atau Nama Pasien terlebih dahulu!");
            return;
        }

        // Query: Cari berdasarkan ID ATAU Nama yang mirip
        String sql = "SELECT * FROM pasien WHERE pasien_id = ? OR nama_pasien LIKE ?";

        try (Connection conn = Database.KoneksiDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

             // Parameter 1: Cari ID Pasien (Persis)
             stmt.setString(1, keyword);
             // Parameter 2: Cari Nama (Mirip/Mengandung kata)
             stmt.setString(2, "%" + keyword + "%");

             ResultSet rs = stmt.executeQuery();
        
            if (rs.next()) {
                // --- JIKA DATA DITEMUKAN ---

                // 1. Simpan ID ke variabel global (untuk dipakai saat tombol Daftar diklik nanti)
                selectedPasienId = rs.getString("pasien_id");

                // 2. Tampilkan data ke Label GUI
                label_id.setText(rs.getString("pasien_id"));
                label_nama.setText(rs.getString("nama_pasien"));
                label_tgl_lahir.setText(rs.getString("tanggal_lahir"));
                label_alamat.setText(rs.getString("alamat"));

                // 3. Hitung Usia (Opsional tapi bagus)
                java.sql.Date tglLahir = rs.getDate("tanggal_lahir");
                if (tglLahir != null) {
                    // Konversi tanggal ke string + hitung tahun (logika sederhana)
                    String tglStr = new java.text.SimpleDateFormat("dd MMMM yyyy").format(tglLahir);
                    label_tgl_lahir.setText(tglStr);
                } else {
                    label_tgl_lahir.setText("-");
                }

                // Opsional: Beri warna hijau pada teks agar terlihat 'Found'
                label_nama.setForeground(new java.awt.Color(0, 153, 51));
                
                
                panelInfoPasien.setVisible(true); // Munculkan detail pasien
                panelStep2.setEnabled(true);      // Aktifkan panel dokter
                cbDokter.setEnabled(true);     // Aktifkan combo box
//                spinnerBiayaJasa.setEnabled(true);
                btnDaftarkan.setEnabled(true);    // Aktifkan tombol daftar
            // ==========================================

            } else {
                // --- JIKA DATA TIDAK DITEMUKAN ---
                resetFormPasien();
                JOptionPane.showMessageDialog(this, "Data pasien tidak ditemukan!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Database: " + e.getMessage());
        }
    }

// Method helper untuk membersihkan label jika tidak ketemu
    private void resetFormPasien() {
        selectedPasienId = null; // Reset ID
        label_id.setText("-");
        label_nama.setText("-");
        label_tgl_lahir.setText("-");
        label_alamat.setText("-");
        label_nama.setForeground(java.awt.Color.BLACK); // Kembalikan warna hitam
    }
   
  private void loadDataAntrean() {
    // 1. Siapkan Model Tabel
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblAntrean.getModel();
    model.setRowCount(0); // Bersihkan data lama

    // 2. Query SQL Kompleks (JOIN 4 Tabel)
    // Alur: Kunjungan -> Pasien (ambil nama pasien) -> Dokter -> User (ambil nama dokter)
    String sql = "SELECT k.kunjungan_id, p.nama_pasien, d.spesialisasi, u.nama_lengkap AS nama_dokter, k.status_kunjungan " +
                 "FROM kunjungan k " +
                 "JOIN pasien p ON k.pasien_id = p.pasien_id " +
                 "JOIN dokter d ON k.dokter_id = d.dokter_id " +
                 "JOIN user u ON d.user_id = u.user_id " +
                 "WHERE DATE(k.tanggal_kunjungan) = CURDATE() " + // Filter HANYA HARI INI
                 "ORDER BY k.kunjungan_id DESC"; // Yang baru daftar ada di atas

    // 3. Eksekusi Query
    try (java.sql.Connection conn = Database.KoneksiDatabase.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
         java.sql.ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            // Kita format ID Kunjungan jadi format Antrean, misal "ANT-001"
            String idRaw = rs.getString("kunjungan_id");
            String noAntrean = "ANT-" + idRaw; 
            
            Object[] row = {
                noAntrean,
                rs.getString("nama_pasien"),
                rs.getString("spesialisasi"),
                rs.getString("nama_dokter"), // Ini alias dari query di atas
                rs.getString("status_kunjungan")
            };
            model.addRow(row);
        }
        
    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat antrean: " + e.getMessage());
    }
}  
   
    private void loadDataPoli() {
      cbPoli.removeAllItems();

      // Ambil data spesialisasi yang unik (DISTINCT)
      String sql = "SELECT DISTINCT spesialisasi FROM dokter ORDER BY spesialisasi";

      try (java.sql.Connection conn = Database.KoneksiDatabase.getConnection();
           java.sql.Statement stmt = conn.createStatement();
           java.sql.ResultSet rs = stmt.executeQuery(sql)) {

          while (rs.next()) {
              String spes = rs.getString("spesialisasi");
              if (spes != null && !spes.isEmpty()) {
                  cbPoli.addItem(spes);
              }
          }

      } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat poli: " + e.getMessage());
      }
  }
    
    private void loadDokterByPoli(String poliDipilih) {
     cbDokter.removeAllItems();

     // Query: Ambil Dokter yang spesialisasinya SESUAI pilihan
     String sql = "SELECT d.dokter_id, u.nama_lengkap " +
                  "FROM dokter d " +
                  "JOIN user u ON d.user_id = u.user_id " +
                  "WHERE d.spesialisasi = ? " +
                  "ORDER BY u.nama_lengkap ASC";

     try (java.sql.Connection conn = Database.KoneksiDatabase.getConnection();
          java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

         stmt.setString(1, poliDipilih);

         try (java.sql.ResultSet rs = stmt.executeQuery()) {
             while (rs.next()) {
                 String id = rs.getString("dokter_id");
                 String nama = rs.getString("nama_lengkap");

                 // Masukkan ke ComboBox sebagai Objek
                 cbDokter.addItem(new DokterItem(id, nama));
             }
         }

     } catch (Exception e) {
         javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat dokter: " + e.getMessage());
     }
     
   }
    
  
  private void tampilkanEstimasiAntrean() {
    // Logic: Hitung jumlah kunjungan HARI INI
    String sql = "SELECT COUNT(*) AS total FROM kunjungan WHERE DATE(tanggal_kunjungan) = CURDATE()";
    
    try (java.sql.Connection conn = Database.KoneksiDatabase.getConnection();
         java.sql.Statement stmt = conn.createStatement();
         java.sql.ResultSet rs = stmt.executeQuery(sql)) {
        
        if (rs.next()) {
            int totalHariIni = rs.getInt("total");
            int antreanBerikutnya = totalHariIni + 1;
            
            // Tampilkan di Panel Estimasi (Saya asumsikan Anda punya JLabel di sana)
            // Jika belum ada Label, buat JLabel baru di panel bawah bernama 'lblEstimasi'
            // Format contoh: "Antrean Berikutnya: Nomor 5"
            
            // Update Label Judul di panel bawah (lblEstimasi atau sejenisnya)
            // Pastikan Anda sudah memberi nama variabel pada JLabel di panel hitam bawah tersebut
            // Contoh nama variabel: lblAngkaAntrean
            
            //label_antrean.setText("Nomor Antrean Berikutnya: " + antreanBerikutnya);
            // Set teks HANYA angkanya saja
            label_antrean.setText(String.valueOf(antreanBerikutnya));
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
  private void updateStatusKunjungan(String idKunjunganRaw, String statusBaru) {
    // idKunjunganRaw formatnya misal "A-005", kita harus ambil angkanya saja "5"
    // Tapi tunggu, id di database integer. Kita perlu parsing.
    
    // 1. Bersihkan ID (Hapus prefix "A-" atau "ANT-")
    String idBersih = idKunjunganRaw.replaceAll("[^0-9]", ""); // Hanya ambil angka
    
    String sql = "UPDATE kunjungan SET status_kunjungan = ? WHERE kunjungan_id = ?";
    
    try (java.sql.Connection conn = Database.KoneksiDatabase.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, statusBaru);
        stmt.setString(2, idBersih);
        
        stmt.executeUpdate();
        
        // Refresh tabel agar status berubah
        loadDataAntrean(); 
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal update status: " + e.getMessage());
    }
}
  
  private void hapusAntrean(String idKunjunganRaw) {
    String idBersih = idKunjunganRaw.replaceAll("[^0-9]", "");
    String sql = "DELETE FROM kunjungan WHERE kunjungan_id = ?";
    
    try (java.sql.Connection conn = Database.KoneksiDatabase.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, idBersih);
        stmt.executeUpdate();
        loadDataAntrean(); // Refresh
        tampilkanEstimasiAntrean(); // Refresh angka estimasi juga
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
    }
}
  
  
  
  
  
  
}

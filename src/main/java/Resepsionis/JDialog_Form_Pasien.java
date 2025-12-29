/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
    package Resepsionis;
    package Resepsionis;

    import javax.swing.JOptionPane;
    import Database.KoneksiDatabase;
    import java.sql.Connection;
    import java.sql.Date;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    /**
     *
     * @author USER
     */
    public class JDialog_Form_Pasien extends javax.swing.JDialog {

        
        // Atribut
        Connection conn = KoneksiDatabase.getConnection();


        /**
         * Creates new form JDialog_Form_Pasien
         */
        private String idPasienToEdit = null;
        // Tambahkan juga referensi ke panel tabel (opsional tapi berguna)
        private JPanel_Manajemen_Pasien panelManajemen;

        public JDialog_Form_Pasien(java.awt.Frame parent, boolean modal, JPanel_Manajemen_Pasien panelManajemen) {
            super(parent, modal);
            initComponents();
            this.panelManajemen = panelManajemen; // Simpan referensi panel
            // Panggil method kustomisasi
            
            txtIDPasien.setText("auto");
            txtIDPasien.setEnabled(false);
            
            initCustomComponents();

            // Set ID Pasien (misal: auto-generate)

        }

        public JDialog_Form_Pasien(java.awt.Frame parent, boolean modal, JPanel_Manajemen_Pasien panelManajemen, String idPasien) {
            this(parent, modal, panelManajemen); // Panggil constructor pertama
            // Set ID Pasien (misal: auto-generate)

        }

        public JDialog_Form_Pasien(java.awt.Frame parent, boolean modal, JPanel_Manajemen_Pasien panelManajemen, String idPasien) {
            this(parent, modal, panelManajemen); // Panggil constructor pertama

            this.idPasienToEdit = idPasien; // Simpan ID untuk diedit
            this.idPasienToEdit = idPasien; // Simpan ID untuk diedit

            if (this.idPasienToEdit != null) {
                lblJudul.setText("Edit Data Pasien");
                txtIDPasien.setText(idPasien); // Menindih tulisan "auto"
                txtIDPasien.setEnabled(false);
                loadDataForEdit(); 
            }


        }

        private void initCustomComponents() {
            // Memusatkan dialog
            setLocationRelativeTo(null);
            if (this.idPasienToEdit != null) {
                lblJudul.setText("Edit Data Pasien");
                txtIDPasien.setText(idPasien); // Menindih tulisan "auto"
                txtIDPasien.setEnabled(false);
                loadDataForEdit(); 
            }


        }

        private void initCustomComponents() {
            // Memusatkan dialog
            setLocationRelativeTo(null);

            // Set placeholder (seperti yang kita pelajari)
            txtNamaPasien.putClientProperty("JTextField.placeholderText", "Masukkan nama lengkap pasien...");
            txtNoTelepon.putClientProperty("JTextField.placeholderText", "Contoh: 08123456789...");
        }
            // Set placeholder (seperti yang kita pelajari)
            txtNamaPasien.putClientProperty("JTextField.placeholderText", "Masukkan nama lengkap pasien...");
            txtNoTelepon.putClientProperty("JTextField.placeholderText", "Contoh: 08123456789...");
        }

    
    

    private void loadDataForEdit() {
        
    String sql = "SELECT * FROM pasien WHERE pasien_id = ?";
    
    try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
        stmt.setString(1, idPasienToEdit);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Set setiap komponen sesuai kolom di database
            txtNamaPasien.setText(rs.getString("nama_pasien"));
            txtAlamat.setText(rs.getString("alamat"));
            txtNoTelepon.setText(rs.getString("no_telepon"));
            txtgol_darah.setSelectedItem(rs.getString("golongan_darah"));
            txtpekerjaan.setText(rs.getString("pekerjaan"));
            txtnama_kerabat.setText(rs.getString("nama_kerabat"));
            txtno_kerabat.setText(rs.getString("no_telp_kerabat"));

            // Set Tanggal Lahir (JDateChooser)
            dcTanggalLahir.setDate(rs.getDate("tanggal_lahir"));

            // Set Jenis Kelamin (RadioButton)
            String jk = rs.getString("jenis_kelamin");
            if ("Laki-laki".equals(jk)) {
                rbLakiLaki.setSelected(true);
            } else {
                rbPerempuan.setSelected(true);
            }

            String statusDiDB = rs.getString("status_pernikahan");
            if (statusDiDB != null) {
                cbStatus.setSelectedItem(statusDiDB);
            }




        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data lama: " + e.getMessage());
    }
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelForm = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtIDPasien = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNamaPasien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dcTanggalLahir = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        rbLakiLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        scrollAlamat = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtNoTelepon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtpekerjaan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtpekerjaan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtnama_kerabat = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtno_kerabat = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        txtgol_darah = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Data Pasien");
        setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        setModal(true);

        panelForm.setBackground(new java.awt.Color(255, 255, 255));
        panelForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelForm.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        lblJudul.setFont(new java.awt.Font("sansserif", 1, 20)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(0, 0, 0));
        lblJudul.setText("Tambah Data Pasien");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID Pasien");

        txtIDPasien.setEditable(false);
        txtIDPasien.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtIDPasien.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nama Lengkap");

        txtNamaPasien.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNamaPasien.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tanggal Lahir");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Jenis Kelamin");

        buttonGroup1.add(rbLakiLaki);
        rbLakiLaki.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        rbLakiLaki.setText("Laki-laki");
        rbLakiLaki.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        buttonGroup1.add(rbPerempuan);
        rbPerempuan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        rbPerempuan.setText("Perempuan");
        rbPerempuan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPerempuanActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(5);
        txtAlamat.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAlamat);

        scrollAlamat.setViewportView(jScrollPane1);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("No. Telepon");

        txtNoTelepon.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNoTelepon.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Golongan Darah");

        txtpekerjaan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpekerjaan.setForeground(new java.awt.Color(0, 0, 0));
        txtpekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpekerjaanActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Pekerjaan");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Golongan Darah");

        txtpekerjaan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpekerjaan.setForeground(new java.awt.Color(0, 0, 0));
        txtpekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpekerjaanActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Pekerjaan");

        btnSimpan.setBackground(new java.awt.Color(50, 120, 220));
        btnSimpan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(220, 53, 69));
        btnBatal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Batal");
        btnBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Nama Kerabat");

        txtnama_kerabat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnama_kerabat.setForeground(new java.awt.Color(0, 0, 0));
        txtnama_kerabat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnama_kerabatActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("No. Telp Kerabat");

        txtno_kerabat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtno_kerabat.setForeground(new java.awt.Color(0, 0, 0));
        txtno_kerabat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtno_kerabatActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Status");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Belum Menikah", "Menikah", "Cerai Hidup", "Cerai Mati" }));

        txtgol_darah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-", " " }));
        txtgol_darah.setSelectedIndex(8);

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFormLayout.createSequentialGroup()
                                .addComponent(rbLakiLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbLakiLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbPerempuan)
                                .addGap(0, 136, Short.MAX_VALUE))
                            .addComponent(dcTanggalLahir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNamaPasien, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIDPasien, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addComponent(txtNamaPasien, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIDPasien, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addGroup(panelFormLayout.createSequentialGroup()
                .addComponent(lblJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtpekerjaan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNoTelepon)
                    .addComponent(txtnama_kerabat, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtno_kerabat, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollAlamat)
                    .addComponent(txtgol_darah, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addComponent(lblJudul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIDPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNamaPasien, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcTanggalLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbPerempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbLakiLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtNoTelepon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgol_darah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnama_kerabat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtno_kerabat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane2.setViewportView(panelForm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        //this.dispose();
        
        //this.dispose();
        
        // 1. Ambil semua data dari form
            //String id = txtIDPasien.getText();
            String nama = txtNamaPasien.getText();
            String alamat = txtAlamat.getText();
            String telp = txtNoTelepon.getText();
            String goldar = txtgol_darah.getSelectedItem().toString();
            String pekerjaan = txtpekerjaan.getText();
            String namaKerabat = txtnama_kerabat.getText(); 
            String telpKerabat = txtno_kerabat.getText(); // Sesuaikan nama variabel GUI Anda
            String statusNikah = cbStatus.getSelectedItem().toString(); // Ambil dari ComboBox
        
        // Ambil Gender dari Radio Button
        String gender = rbLakiLaki.isSelected() ? "Laki-laki" : "Perempuan";
           // Ambil Tanggal dari JDateChooser
        Date tglLahirSql = null;
        if (dcTanggalLahir.getDate() != null) {
        tglLahirSql = new Date(dcTanggalLahir.getDate().getTime());
        }

        // 2. Validasi Input
        if (nama.trim().isEmpty() || tglLahirSql == null) {
        JOptionPane.showMessageDialog(this, "Nama dan Tanggal Lahir wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
        }
        

        //3. Eksekusi Database
        
        PreparedStatement stmt = null;
        try {
            String sql;
            
            // 3. Cek apakah ini mode TAMBAH atau EDIT            
            if (idPasienToEdit == null) {
                // Mode TAMBAH: Jalankan query INSERT
                // MODE TAMBAH (INSERT)
                // Sesuai struktur tabel: nama_pasien, tanggal_lahir, jenis_kelamin, alamat, no_telepon
                sql = "INSERT INTO pasien (nama_pasien, tanggal_lahir, jenis_kelamin, alamat, no_telepon, golongan_darah, pekerjaan, nama_kerabat, no_telp_kerabat, status_pernikahan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = this.conn.prepareStatement(sql);
                
                
                stmt.setString(1, nama);
                stmt.setDate(2, tglLahirSql);
                stmt.setString(3, gender);
                stmt.setString(4, alamat);
                stmt.setString(5, telp);
                
                stmt.setString(6, goldar);
                stmt.setString(7, pekerjaan);
                stmt.setString(8, namaKerabat);
                stmt.setString(9, telpKerabat);
                stmt.setString(10, statusNikah);
            
            } else {
                // Mode EDIT: Jalankan query UPDATE
                
                sql = "UPDATE pasien SET nama_pasien=?, tanggal_lahir=?, jenis_kelamin=?, alamat=?, no_telepon=?, golongan_darah=?, pekerjaan=?, nama_kerabat=?, no_telp_kerabat=?, status_pernikahan=? WHERE pasien_id=?";
                stmt = this.conn.prepareStatement(sql);
                
                stmt.setString(1, nama);
                stmt.setDate(2, tglLahirSql);
                stmt.setString(3, gender);
                stmt.setString(4, alamat);
                stmt.setString(5, telp);
                
                stmt.setString(6, goldar);
                stmt.setString(7, pekerjaan);
                stmt.setString(8, namaKerabat);
                stmt.setString(9, telpKerabat);
                stmt.setString(10, statusNikah);
                
                stmt.setString(11, idPasienToEdit); // Primary Key dari variabel penanda
                
                
            }
            
            int success = stmt.executeUpdate();
            if (success > 0) {
            JOptionPane.showMessageDialog(this, idPasienToEdit == null ? "Data Pasien Berhasil Ditambah" : "Data Pasien Berhasil Diperbarui");
            
            // 4. Refresh Tabel di Panel Utama
                
                sql = "UPDATE pasien SET nama_pasien=?, tanggal_lahir=?, jenis_kelamin=?, alamat=?, no_telepon=?, golongan_darah=?, pekerjaan=?, nama_kerabat=?, no_telp_kerabat=?, status_pernikahan=? WHERE pasien_id=?";
                stmt = this.conn.prepareStatement(sql);
                
                stmt.setString(1, nama);
                stmt.setDate(2, tglLahirSql);
                stmt.setString(3, gender);
                stmt.setString(4, alamat);
                stmt.setString(5, telp);
                
                stmt.setString(6, goldar);
                stmt.setString(7, pekerjaan);
                stmt.setString(8, namaKerabat);
                stmt.setString(9, telpKerabat);
                stmt.setString(10, statusNikah);
                
                stmt.setString(11, idPasienToEdit); // Primary Key dari variabel penanda
                
                
            }
            
            int success = stmt.executeUpdate();
            if (success > 0) {
            JOptionPane.showMessageDialog(this, idPasienToEdit == null ? "Data Pasien Berhasil Ditambah" : "Data Pasien Berhasil Diperbarui");
            
            // 4. Refresh Tabel di Panel Utama
            if (panelManajemen != null) {
                panelManajemen.loadDataToTable();
            }
            this.dispose(); // Tutup Dialog
                panelManajemen.loadDataToTable();
            }
            this.dispose(); // Tutup Dialog
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                // Menangani kesalahan saat menutup statement atau koneksi
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void rbPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPerempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbPerempuanActionPerformed

    private void txtpekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpekerjaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpekerjaanActionPerformed

    private void txtnama_kerabatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnama_kerabatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama_kerabatActionPerformed

    private void txtno_kerabatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtno_kerabatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtno_kerabatActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> cbStatus;
    private com.toedter.calendar.JDateChooser dcTanggalLahir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JPanel panelForm;
    private javax.swing.JRadioButton rbLakiLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JScrollPane scrollAlamat;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtIDPasien;
    private javax.swing.JTextField txtNamaPasien;
    private javax.swing.JTextField txtNoTelepon;
    private javax.swing.JComboBox<String> txtgol_darah;
    private javax.swing.JTextField txtnama_kerabat;
    private javax.swing.JTextField txtno_kerabat;
    private javax.swing.JTextField txtpekerjaan;
    // End of variables declaration//GEN-END:variables
}

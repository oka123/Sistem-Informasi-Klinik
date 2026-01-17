# 🏥 Sistem Informasi Klinik

**Sistem Informasi Klinik** adalah proyek aplikasi desktop berbasis **Java GUI** yang merupakan *final project* mata kuliah **Pemrograman Berorientasi Objek (PBO)**. Aplikasi ini dirancang untuk membantu pengelolaan kegiatan operasional klinik secara efisien, seperti data pasien, jadwal dokter, rekam medis, dan stok obat dengan basis data **MySQL**.

---

## 📌 Daftar Isi

1. [Latar Belakang & Tujuan](#-latar-belakang--tujuan)
2. [Fitur Utama](#-fitur-utama)
3. [Teknologi yang Digunakan](#️-teknologi-yang-digunakan)
4. [Struktur Direktori Proyek](#-struktur-direktori-proyek)
5. [Prasyarat Instalasi](#-prasyarat-instalasi)
6. [Langkah Instalasi & Konfigurasi](#️-langkah-instalasi--konfigurasi)
7. [Cara Menjalankan Aplikasi](#️-cara-menjalankan-aplikasi)
8. [Contoh Akun / Role Pengguna](#-contoh-akun--role-pengguna)
9. [Screenshot / Diagram](#-screenshot)
10. [Lisensi](#-lisensi)
11. [Developer](#-developer)

---

## 🧠 Latar Belakang & Tujuan

Dalam praktik klinik, banyak proses administratif, seperti pendaftaran pasien, pengaturan jadwal dokter, dan pengelolaan stok obat masih dilakukan manual atau tersebar di berbagai media. Hal ini berpotensi menyebabkan kesalahan data, pengulangan kerja, dan efisiensi operasional yang rendah.

📌 **Tujuan sistem ini adalah**:

* Menyediakan aplikasi desktop terintegrasi untuk mengelola data klinik secara komprehensif.
* Meningkatkan efisiensi layanan administrasi klinik.
* Mengurangi kesalahan manual dalam pencatatan informasi klinik.

---

## 🚀 Fitur Utama

Aplikasi **Sistem Informasi Klinik** memiliki fungsi-fungsi berikut:

* 📋 **Manajemen Data Pasien** – Tambah, edit, hapus, dan pencarian data pasien.
* 🩺 **Penjadwalan Jadwal Dokter** – Input dan atur jadwal dokter praktik.
* 📑 **Rekam Medis** – Penyimpanan serta pencarian catatan medis pasien.
* 💊 **Pengelolaan Stok Obat** – Tambah/kurangi stok serta lihat daftar obat.
* 📊 **Laporan Administrasi Klinis** - Export Laporan Pendapatan, Kunjungan Pasien, dan Penjualan Obat.

---

## 🛠️ Teknologi yang Digunakan

| Komponen                | Teknologi                                                 |
| ----------------------- | --------------------------------------------------------- |
| **Bahasa Pemrograman**  | Java                                                      |
| **User Interface**      | GUI (Java Swing) |
| **Database**            | MySQL                                                     |
| **Build System**        | Maven (`pom.xml`)                                         |
| **IDE yang Disarankan** | Apache NetBeans                                           |
| **Resource Tambahan**   | `clinic.ico`, objek library untuk layout                  |

---

## 📁 Struktur Direktori Proyek

```
Sistem-Informasi-Klinik/
├── .env.example                  # Template konfigurasi koneksi ke database (MySQL)
├── DBKlinik.sql                  # Dump database MySQL
├── pom.xml                       # Konfigurasi Maven
├── src/main/...                  # Kode sumber Java
├── lib/...                       # Library/ dependency internal
├── target/                       # Output build Maven
├── clinic.ico                    # Ikon aplikasi
├── README.md                     # Dokumentasi ini
├── LICENSE                       # Lisensi MIT
└── .gitignore                    # File dan folder yang diabaikan Git
```
---

## 📌 Prasyarat Instalasi

Sebelum menjalankan aplikasi, pastikan sistem Anda memenuhi kebutuhan berikut:

### 🔧 Software

* Java JDK 24 atau lebih tinggi.
* MySQL Server (versi 5.7+ direkomendasikan).
* Apache NetBeans (atau IDE lain yang mendukung Java Maven).
* MySQL Workbench / phpMyAdmin untuk manajemen database.

### 📁 Database

* File database: `DBKlinik.sql` (termasuk struktur dan data tabel).

---

## ⚙️ Langkah Instalasi & Konfigurasi

Ikuti langkah berikut secara berurutan:

### 1. **Clone Repository**

```bash
git clone https://github.com/oka123/Sistem-Informasi-Klinik.git
cd Sistem-Informasi-Klinik
```

### 2. **Setup Database MySQL**

1. Buka MySQL Workbench / phpMyAdmin.
2. Buat database baru, misalnya: `db_klinik`.
3. **Import** file `DBKlinik.sql`.

### 3. **Konfigurasi Aplikasi**

Jika aplikasi menggunakan file konfigurasi (seperti `.env.example`):

1. Salin dan paste file `.env.example` → `.env`.
2. Buka file `.env` dan isi parameter koneksi database:

```
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=db_klinik
DB_USERNAME=root
DB_PASSWORD=your_password
```

### 4. **Import ke IDE**

* Buka IDE (NetBeans/IntelliJ/Eclipse).
* **Import sebagai proyek Maven**.
* Pastikan dependensi Maven terunduh otomatis.

### 5. **Build Proyek**

* Run build melalui IDE atau:

```bash
mvn clean install
```

---

## ▶️ Cara Menjalankan Aplikasi

### 💻 Development

Jalankan proyek dari IDE:

* Klik kanan pada `MainClass` → **Run** / **Debug**.

### 🏭 Production / Distribusi

Jika ada *executable JAR*:

```bash
java -jar target/Sistem-Informasi-Klinik.jar
```

---

## 👥 Contoh Akun / Role Pengguna

📌 Contoh akun (username / password):

| Role        | Username      | Password         |
| ----------- | ------------- | ---------------- |
| Resepsionis | `Resepsionis` | `Resepsionis123` |
| Dokter      | `Dokter`      | `Dokter123`      |
| Kasir       | `Kasir`       | `Kasir123`       |
| Apoteker    | `Apoteker`    | `Apoteker123`    |
| Manajemen   | `Manajemen`   | `Manajemen123`   |

> 🔎 Jika role ini belum tersedia di `DBKlinik.sql`, maka buat secara manual di tabel user. *(Role/credential ini adalah contoh umum untuk klinik.)*

---

## 📷 Screenshot
Login:

<img width="815" height="540" alt="image" src="https://github.com/user-attachments/assets/163c94a8-3d76-4c41-9cbc-4ca8dab92283" />

Resepsionis:

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/12095767-c7b5-4f1b-aa8c-0bb5c3790b76" />

Dokter:

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/50fd2428-87c9-49c8-8da6-92d56ec06d6b" />

Kasir:

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/b24147b9-6f8e-468b-a93d-b5682c6d7655" />

Apoteker:

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/34eab146-02ff-440d-a221-afb98c28bb75" />

Manajemen:

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/b6dc1c24-1199-4dda-a931-a79fa713cb26" />

---

## 📄 Lisensi

Proyek ini menggunakan lisensi **MIT License** — lihat file `LICENSE` untuk detail lengkap.

---

## 👨‍💻 Developer

**Kelompok 5A - Pemrograman Berorientasi Objek**

Informatika 24 - FMIPA - Universitas Udayana

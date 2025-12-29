# Sistem-Informasi-Klinik
<<<<<<< HEAD
Proyek ini adalah final project mata kuliah Pemrograman Berorientasi Objek (PBO). Proyek ini berupa Sistem Informasi Klinik berbasis GUI menggunakan bahasa Java dan Database MySQL. Sistem ini membantu pengelolaan data pasien, jadwal dokter, rekam medis, serta stok obat di klinik secara efisien.
<<<<<<< HEAD
=======
<<<<<<< HEAD

## Penggunaan
1. Pastikan Anda sudah menginstal **Java Development Kit (JDK)** dan **NetBeans IDE** di komputer Anda.
2. Pastikan Anda sudah menginstal **MySQL** dan membuat database untuk sistem ini. Anda dapat membuat database dengan nama `klinik` menggunakan perintah SQL berikut:
   ```sql
   CREATE DATABASE klinik;
   ```
3. Clone repository ini ke dalam folder di komputer Anda.
   ```bash
   git clone https://github.com/username/repository-name.git
   ```
4. Buka proyek ini di NetBeans IDE.
5. Jalankan aplikasi dengan mengikuti langkah-langkah berikut:
   - Lakukan **Clean and Build** atau **Build** pada proyek untuk memastikan semua file terkompilasi dengan benar.
   - Setelah itu, pilih **Run Project** untuk menjalankan aplikasi.

## Konfigurasi Koneksi Database
Koneksi database pada sistem ini dapat dikonfigurasi di kelas `KoneksiDatabase`, yang terletak pada package `Database`.

### Langkah-langkah konfigurasi:
1. Buka file `KoneksiDatabase.java` di dalam package `Database`.
2. Cari bagian kode yang berisi konfigurasi koneksi database, seperti berikut:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/klinik";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "";
   ```
3. Ubah nilai `URL`, `USERNAME`, dan `PASSWORD` sesuai dengan pengaturan database Anda:
   - **URL**: Ganti dengan alamat server MySQL Anda. Jika menggunakan database lokal, URL-nya biasanya berupa `jdbc:mysql://localhost:3306/klinik`.
   - **USERNAME**: Username untuk login ke MySQL, default-nya adalah `root`.
   - **PASSWORD**: Password untuk login ke MySQL, jika Anda tidak mengatur password pada instalasi MySQL, biarkan kosong.

4. Pastikan koneksi berhasil dengan cara melakukan pengujian pada aplikasi.

## Menggunakan Aplikasi
Setelah aplikasi berjalan, Anda dapat mengelola berbagai fitur klinik seperti:
- Menambah, mengedit, atau menghapus data pasien.
- Mengatur jadwal dokter.
- Mengelola rekam medis pasien.
- Memantau stok obat yang tersedia.

### Fitur Utama:
- **Manajemen Pasien**: Menambah, mengedit, dan menghapus data pasien.
- **Manajemen Dokter**: Menjadwalkan waktu praktek dan mengatur jadwal pertemuan dengan pasien.
- **Rekam Medis**: Mencatat rekam medis setiap pasien dan tindak lanjutnya.
- **Stok Obat**: Memantau ketersediaan stok obat dan melakukan pemesanan ulang jika diperlukan.

---

Jika ada pertanyaan atau masalah dalam penggunaan aplikasi, Anda dapat menghubungi pengembang melalui Issues pada GitHub repository ini.
=======
>>>>>>> 765c466 (Initial commit)
=======
Proyek ini adalah final project mata kuliah Pemrograman Berorientasi Objek (PBO). Proyek ini berupa Sistem Informasi Klinik berbasis GUI menggunakan bahasa Java dan Database MySQL. 
Sistem ini membantu pengelolaan data pasien, jadwal dokter, rekam medis, serta stok obat di klinik secara efisien.
## Fitur Utama
- Pendaftaran dan pengelolaan data pasien
- Pengelolaan jadwal dokter
- Penyimpanan dan pencarian rekam medis
- Pengelolaan stok obat
<<<<<<< HEAD
<<<<<<< HEAD
- Laporan dan analisis data klinik
>>>>>>> 10ddf57 (updated readme)
<<<<<<< HEAD
>>>>>>> a2ca410 (updated readme)
=======
=======
- Laporan dan analisis data klinik.
>>>>>>> 3446eec (test)
<<<<<<< HEAD
>>>>>>> 74a1288 (test)
=======
=======
- Laporan dan analisis data klinik.
Proyek ini adalah final project mata kuliah Pemrograman Berorientasi Objek (PBO). Proyek ini berupa Sistem Informasi Klinik berbasis GUI menggunakan bahasa Java dan Database MySQL. Sistem ini membantu pengelolaan data pasien, jadwal dokter, rekam medis, serta stok obat di klinik secara efisien.

## Penggunaan
1. Pastikan Anda sudah menginstal **Java Development Kit (JDK)** dan **NetBeans IDE** di komputer Anda.
2. Pastikan Anda sudah menginstal **MySQL** dan membuat database untuk sistem ini. Anda dapat membuat database dengan nama `klinik` menggunakan perintah SQL berikut:
   ```sql
   CREATE DATABASE klinik;
   ```
3. Clone repository ini ke dalam folder di komputer Anda.
   ```bash
   git clone https://github.com/username/repository-name.git
   ```
4. Buka proyek ini di NetBeans IDE.
5. Jalankan aplikasi dengan mengikuti langkah-langkah berikut:
   - Lakukan **Clean and Build** atau **Build** pada proyek untuk memastikan semua file terkompilasi dengan benar.
   - Setelah itu, pilih **Run Project** untuk menjalankan aplikasi.

## Konfigurasi Koneksi Database
Koneksi database pada sistem ini dapat dikonfigurasi di kelas `KoneksiDatabase`, yang terletak pada package `Database`.

### Langkah-langkah konfigurasi:
1. Buka file `KoneksiDatabase.java` di dalam package `Database`.
2. Cari bagian kode yang berisi konfigurasi koneksi database, seperti berikut:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/klinik";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "";
   ```
3. Ubah nilai `URL`, `USERNAME`, dan `PASSWORD` sesuai dengan pengaturan database Anda:
   - **URL**: Ganti dengan alamat server MySQL Anda. Jika menggunakan database lokal, URL-nya biasanya berupa `jdbc:mysql://localhost:3306/klinik`.
   - **USERNAME**: Username untuk login ke MySQL, default-nya adalah `root`.
   - **PASSWORD**: Password untuk login ke MySQL, jika Anda tidak mengatur password pada instalasi MySQL, biarkan kosong.

4. Pastikan koneksi berhasil dengan cara melakukan pengujian pada aplikasi.

## Menggunakan Aplikasi
Setelah aplikasi berjalan, Anda dapat mengelola berbagai fitur klinik seperti:
- Menambah, mengedit, atau menghapus data pasien.
- Mengatur jadwal dokter.
- Mengelola rekam medis pasien.
- Memantau stok obat yang tersedia.

### Fitur Utama:
- **Manajemen Pasien**: Menambah, mengedit, dan menghapus data pasien.
- **Manajemen Dokter**: Menjadwalkan waktu praktek dan mengatur jadwal pertemuan dengan pasien.
- **Rekam Medis**: Mencatat rekam medis setiap pasien dan tindak lanjutnya.
- **Stok Obat**: Memantau ketersediaan stok obat dan melakukan pemesanan ulang jika diperlukan.

---

Jika ada pertanyaan atau masalah dalam penggunaan aplikasi, Anda dapat menghubungi pengembang melalui Issues pada GitHub repository ini.
>>>>>>> 3d7d480 (Rev1 Resepsionis)
>>>>>>> 0f9a17a (Rev1 Resepsioniss)

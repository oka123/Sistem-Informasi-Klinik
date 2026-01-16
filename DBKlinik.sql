-- --------------------------------------------------------
-- Host:                         klinik-klinik.c.aivencloud.com
-- Server version:               8.0.35 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             12.13.0.7147
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for klinik
CREATE DATABASE IF NOT EXISTS `klinik` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `klinik`;

-- Dumping structure for table klinik.detail_layanan
CREATE TABLE IF NOT EXISTS `detail_layanan` (
  `detail_layanan_id` int NOT NULL AUTO_INCREMENT,
  `kunjungan_id` int NOT NULL,
  `layanan_id` smallint NOT NULL,
  `biaya_saat_ini` decimal(10,0) NOT NULL,
  PRIMARY KEY (`detail_layanan_id`),
  KEY `fk_detail_layanan_kunjungan` (`kunjungan_id`),
  KEY `fk_detail_layanan_master` (`layanan_id`),
  CONSTRAINT `fk_detail_layanan_kunjungan` FOREIGN KEY (`kunjungan_id`) REFERENCES `kunjungan` (`kunjungan_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_detail_layanan_master` FOREIGN KEY (`layanan_id`) REFERENCES `layanan` (`layanan_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.detail_layanan: ~23 rows (approximately)
REPLACE INTO `detail_layanan` (`detail_layanan_id`, `kunjungan_id`, `layanan_id`, `biaya_saat_ini`) VALUES
	(3, 97, 3, 150000),
	(21, 33, 21, 75000),
	(27, 27, 3, 150000),
	(28, 500, 500, 75000),
	(29, 501, 501, 100000),
	(30, 502, 502, 200000),
	(31, 503, 503, 150000),
	(32, 504, 504, 100000),
	(33, 505, 505, 150000),
	(34, 506, 506, 250000),
	(35, 507, 507, 60000),
	(36, 508, 508, 150000),
	(37, 509, 509, 85000),
	(38, 510, 510, 200000),
	(39, 511, 511, 150000),
	(40, 512, 512, 800000),
	(41, 513, 513, 50000),
	(42, 514, 514, 30000),
	(43, 515, 515, 150000),
	(44, 516, 516, 75000),
	(45, 517, 517, 40000),
	(46, 518, 518, 80000),
	(47, 519, 519, 350000);

-- Dumping structure for table klinik.detail_resep
CREATE TABLE IF NOT EXISTS `detail_resep` (
  `detail_resep_id` int NOT NULL AUTO_INCREMENT,
  `resep_id` mediumint NOT NULL,
  `obat_id` smallint NOT NULL,
  `jumlah` smallint NOT NULL,
  `dosis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `subtotal_harga` decimal(10,0) NOT NULL,
  PRIMARY KEY (`detail_resep_id`),
  KEY `fk_obat_id` (`obat_id`),
  KEY `fk_resep_id` (`resep_id`),
  CONSTRAINT `fk_obat_id` FOREIGN KEY (`obat_id`) REFERENCES `obat` (`obat_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_resep_id` FOREIGN KEY (`resep_id`) REFERENCES `resep` (`resep_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.detail_resep: ~29 rows (approximately)
REPLACE INTO `detail_resep` (`detail_resep_id`, `resep_id`, `obat_id`, `jumlah`, `dosis`, `subtotal_harga`) VALUES
	(27, 2, 1, 10, '10x sehari stm', 50000000),
	(37, 1, 1, 2, '2x sehari setelah makan', 50000),
	(38, 1, 2, 1, '3x sehari selama 5 hari', 150000),
	(39, 2, 3, 3, '1x sehari sebelum tidur', 75000),
	(40, 2, 4, 2, '2x sehari setelah makan', 100000),
	(41, 3, 5, 1, '1x sehari setelah makan', 35000),
	(42, 3, 6, 1, '1x sehari setelah makan', 20000),
	(43, 1, 1, 3, '3x Sehari Sebelum Makan', 70000),
	(45, 5, 4, 1, '3 x 1 hari', 49062),
	(48, 500, 500, 10, '3x1 Sesudah Makan', 50000),
	(49, 501, 501, 15, '3x1 Habiskan', 150000),
	(50, 502, 502, 5, '1x1 Pagi', 10000),
	(51, 503, 503, 10, '3x1 Jika Nyeri', 80000),
	(52, 504, 504, 1, '3x1 Sendok Teh', 15000),
	(53, 505, 505, 1, 'Oles 2x Sehari', 25000),
	(54, 506, 506, 2, 'Sesuai Kebutuhan', 24000),
	(55, 507, 507, 1, 'Sesuai Kebutuhan', 20000),
	(56, 508, 508, 1, '1 Pcs', 35000),
	(57, 509, 509, 1, 'Sesuai Kebutuhan', 18000),
	(58, 510, 510, 1, 'Sesuai Kebutuhan', 50000),
	(59, 511, 511, 1, 'Sesuai Kebutuhan', 5000),
	(60, 512, 512, 1, 'Oles Dada', 12000),
	(61, 513, 513, 1, 'Oles Tipis', 15000),
	(62, 514, 514, 1, '2 Tetes Kanan Kiri', 10000),
	(63, 515, 515, 10, '1x1 Pagi', 70000),
	(64, 516, 516, 5, '1 Sachet Jika Perlu', 20000),
	(65, 517, 517, 10, '2x1 Sebelum Makan', 120000),
	(66, 518, 518, 10, '1x1 Malam', 150000),
	(67, 519, 519, 30, '3x1 Sesudah Makan', 300000);

-- Dumping structure for table klinik.dokter
CREATE TABLE IF NOT EXISTS `dokter` (
  `dokter_id` smallint NOT NULL AUTO_INCREMENT,
  `user_id` smallint NOT NULL,
  `spesialisasi` enum('Umum','Gigi','Anak','Mata','THT') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `no_telepon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`dokter_id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=521 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.dokter: ~26 rows (approximately)
REPLACE INTO `dokter` (`dokter_id`, `user_id`, `spesialisasi`, `no_telepon`, `alamat`) VALUES
	(1, 7, 'Umum', '081313727250', 'Jl. Bima No. 100'),
	(2, 2, 'Anak', '081212131340', 'Jl. Pemogan No. 100'),
	(3, 11, 'THT', '089595951012', 'Jl. Asri No. 19'),
	(4, 14, 'Mata', '089512312355', 'Jl. Raya Kerobokan No. 123'),
	(6, 18, 'Gigi', '089898676712', 'Jl. Raya Tanah Lot'),
	(500, 500, 'Umum', '0811111111', 'Jl. Dummy No. 1'),
	(501, 501, 'Gigi', '0811111112', 'Jl. Dummy No. 2'),
	(502, 502, 'Anak', '0811111113', 'Jl. Dummy No. 3'),
	(503, 503, 'Mata', '0811111114', 'Jl. Dummy No. 4'),
	(504, 504, 'THT', '0811111115', 'Jl. Dummy No. 5'),
	(505, 511, 'Umum', '0811111116', 'Jl. Dummy No. 6'),
	(506, 512, 'Gigi', '0811111117', 'Jl. Dummy No. 7'),
	(507, 513, 'Anak', '0811111118', 'Jl. Dummy No. 8'),
	(508, 514, 'Mata', '0811111119', 'Jl. Dummy No. 9'),
	(509, 515, 'THT', '0811111120', 'Jl. Dummy No. 10'),
	(510, 509, 'Umum', '0844444441', 'Jl. Farmasi No. 1'),
	(511, 510, 'Gigi', '0855555551', 'Jl. Kantor No. 1'),
	(512, 516, 'Anak', '0822222223', 'Jl. Admin No. 3'),
	(513, 517, 'Mata', '0833333333', 'Jl. Keuangan No. 3'),
	(514, 518, 'THT', '0844444442', 'Jl. Farmasi No. 2'),
	(515, 519, 'Umum', '0899999991', 'Jl. Extra No. 1'),
	(516, 520, 'Gigi', '0899999992', 'Jl. Extra No. 2'),
	(517, 505, 'Anak', '0822222221', 'Jl. Admin No. 1'),
	(518, 506, 'Mata', '0822222222', 'Jl. Admin No. 2'),
	(519, 507, 'THT', '0833333331', 'Jl. Keuangan No. 1');

-- Dumping structure for table klinik.jadwal_praktik
CREATE TABLE IF NOT EXISTS `jadwal_praktik` (
  `jadwal_id` smallint NOT NULL AUTO_INCREMENT,
  `dokter_id` smallint NOT NULL,
  `hari` enum('Senin','Selasa','Rabu','Kamis','Jumat','Sabtu','Minggu') COLLATE utf8mb4_general_ci NOT NULL,
  `jam_mulai` time NOT NULL,
  `jam_selesai` time NOT NULL,
  PRIMARY KEY (`jadwal_id`) USING BTREE,
  KEY `fk_jadwal_dokter` (`dokter_id`),
  CONSTRAINT `fk_jadwal_dokter` FOREIGN KEY (`dokter_id`) REFERENCES `dokter` (`dokter_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.jadwal_praktik: ~24 rows (approximately)
REPLACE INTO `jadwal_praktik` (`jadwal_id`, `dokter_id`, `hari`, `jam_mulai`, `jam_selesai`) VALUES
	(2, 2, 'Senin', '14:00:00', '21:00:00'),
	(3, 3, 'Senin', '09:00:00', '16:00:00'),
	(4, 1, 'Rabu', '10:00:00', '17:00:00'),
	(5, 1, 'Selasa', '07:00:00', '15:00:00'),
	(6, 500, 'Senin', '08:00:00', '12:00:00'),
	(7, 500, 'Rabu', '08:00:00', '12:00:00'),
	(8, 501, 'Selasa', '09:00:00', '13:00:00'),
	(9, 501, 'Kamis', '09:00:00', '13:00:00'),
	(10, 502, 'Jumat', '07:00:00', '11:00:00'),
	(11, 502, 'Sabtu', '07:00:00', '11:00:00'),
	(12, 503, 'Senin', '13:00:00', '17:00:00'),
	(13, 503, 'Rabu', '13:00:00', '17:00:00'),
	(14, 504, 'Selasa', '14:00:00', '18:00:00'),
	(15, 504, 'Kamis', '14:00:00', '18:00:00'),
	(16, 505, 'Jumat', '15:00:00', '19:00:00'),
	(17, 505, 'Sabtu', '15:00:00', '19:00:00'),
	(18, 506, 'Selasa', '18:00:00', '21:00:00'),
	(20, 507, 'Selasa', '19:00:00', '22:00:00'),
	(21, 507, 'Kamis', '19:00:00', '22:00:00'),
	(22, 508, 'Jumat', '16:00:00', '20:00:00'),
	(23, 508, 'Minggu', '09:00:00', '12:00:00'),
	(24, 509, 'Sabtu', '17:00:00', '21:00:00'),
	(25, 509, 'Minggu', '13:00:00', '16:00:00'),
	(26, 509, 'Sabtu', '07:00:00', '16:00:00');

-- Dumping structure for table klinik.kunjungan
CREATE TABLE IF NOT EXISTS `kunjungan` (
  `kunjungan_id` int NOT NULL AUTO_INCREMENT,
  `pasien_id` mediumint NOT NULL,
  `dokter_id` smallint NOT NULL,
  `user_resepsionis_id` smallint NOT NULL,
  `tanggal_kunjungan` datetime NOT NULL,
  `status_kunjungan` enum('Menunggu','Diperiksa','Menunggu Obat','Menunggu Pembayaran','Selesai') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`kunjungan_id`),
  KEY `fk_dokter_id` (`dokter_id`),
  KEY `fk_pasien_id` (`pasien_id`),
  KEY `fk_user_resepsionis_id` (`user_resepsionis_id`),
  CONSTRAINT `fk_dokter_id` FOREIGN KEY (`dokter_id`) REFERENCES `dokter` (`dokter_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_pasien_id` FOREIGN KEY (`pasien_id`) REFERENCES `pasien` (`pasien_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_resepsionis_id` FOREIGN KEY (`user_resepsionis_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=525 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.kunjungan: ~37 rows (approximately)
REPLACE INTO `kunjungan` (`kunjungan_id`, `pasien_id`, `dokter_id`, `user_resepsionis_id`, `tanggal_kunjungan`, `status_kunjungan`) VALUES
	(27, 27, 3, 1, '2025-11-24 08:14:34', 'Menunggu Pembayaran'),
	(33, 100, 3, 13, '2025-12-21 00:11:34', 'Selesai'),
	(50, 50, 3, 1, '2025-11-24 08:14:34', 'Diperiksa'),
	(68, 68, 3, 6, '2025-11-25 00:14:34', 'Menunggu'),
	(73, 73, 1, 6, '2025-08-27 08:14:34', 'Selesai'),
	(97, 97, 2, 6, '2025-11-24 08:14:34', 'Menunggu Pembayaran'),
	(98, 100, 1, 1, '2025-12-27 14:02:12', 'Selesai'),
	(100, 101, 1, 1, '2025-12-27 14:37:41', 'Diperiksa'),
	(101, 103, 1, 1, '2025-12-27 15:05:40', 'Selesai'),
	(102, 102, 3, 1, '2025-12-27 15:09:52', 'Selesai'),
	(103, 100, 1, 1, '2025-12-27 15:10:22', 'Selesai'),
	(104, 103, 1, 1, '2025-12-29 19:54:23', 'Selesai'),
	(500, 500, 500, 505, '2025-12-31 08:00:00', 'Selesai'),
	(501, 501, 500, 505, '2025-10-01 08:30:00', 'Selesai'),
	(502, 502, 501, 506, '2025-10-01 09:00:00', 'Selesai'),
	(503, 503, 501, 506, '2025-10-01 09:30:00', 'Selesai'),
	(504, 504, 502, 505, '2025-10-02 08:00:00', 'Selesai'),
	(505, 505, 502, 505, '2025-10-02 08:30:00', 'Selesai'),
	(506, 506, 503, 506, '2025-10-02 09:00:00', 'Selesai'),
	(507, 507, 503, 506, '2025-10-02 09:30:00', 'Selesai'),
	(508, 508, 504, 505, '2025-10-03 10:00:00', 'Selesai'),
	(509, 509, 504, 505, '2025-10-03 10:30:00', 'Selesai'),
	(510, 510, 505, 506, '2025-10-03 11:00:00', 'Menunggu Pembayaran'),
	(511, 511, 505, 506, '2025-10-03 11:30:00', 'Menunggu Pembayaran'),
	(512, 512, 506, 505, '2025-10-04 08:00:00', 'Menunggu Obat'),
	(513, 513, 506, 505, '2025-10-04 08:30:00', 'Menunggu Pembayaran'),
	(514, 514, 507, 506, '2025-10-04 09:00:00', 'Diperiksa'),
	(515, 515, 507, 506, '2025-10-04 09:30:00', 'Diperiksa'),
	(516, 516, 508, 505, '2025-10-05 10:00:00', 'Menunggu'),
	(517, 517, 508, 505, '2025-10-05 10:30:00', 'Menunggu'),
	(518, 518, 509, 506, '2025-10-05 11:00:00', 'Menunggu'),
	(519, 519, 509, 506, '2025-10-05 11:30:00', 'Menunggu'),
	(520, 515, 518, 1, '2025-12-31 13:32:39', 'Menunggu'),
	(521, 511, 509, 1, '2025-12-31 13:34:53', 'Menunggu'),
	(522, 510, 502, 1, '2025-12-31 13:35:15', 'Selesai'),
	(523, 509, 505, 1, '2025-12-31 13:35:51', 'Diperiksa'),
	(524, 507, 507, 1, '2025-12-31 13:47:29', 'Selesai');

-- Dumping structure for table klinik.layanan
CREATE TABLE IF NOT EXISTS `layanan` (
  `layanan_id` smallint NOT NULL AUTO_INCREMENT,
  `nama_layanan` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `biaya` decimal(10,0) NOT NULL,
  PRIMARY KEY (`layanan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=521 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.layanan: ~42 rows (approximately)
REPLACE INTO `layanan` (`layanan_id`, `nama_layanan`, `biaya`) VALUES
	(1, 'Tidak Ada', 0),
	(2, 'Konsultasi Dokter Umum', 50000),
	(3, 'Konsultasi Dokter Spesialis', 150000),
	(4, 'Administrasi Pasien Baru', 20000),
	(5, 'Administrasi Pasien Lama', 10000),
	(6, 'Surat Keterangan Sehat', 35000),
	(7, 'Surat Keterangan Sakit', 25000),
	(8, 'Cek Gula Darah Sewaktu', 25000),
	(9, 'Cek Kolesterol Total', 45000),
	(10, 'Cek Asam Urat', 30000),
	(11, 'Nebulizer (Per Sesi)', 75000),
	(12, 'Rawat Luka Ringan (Dressing)', 50000),
	(13, 'Rawat Luka Berat / Infeksi', 120000),
	(14, 'Jahit Luka (Hecting) 1-3 Jahitan', 150000),
	(15, 'Jahit Luka (Hecting) > 3 Jahitan', 250000),
	(16, 'Angkat Jahitan (Up Hecting)', 60000),
	(17, 'Suntik Vitamin C (Immune Booster)', 100000),
	(18, 'Suntik KB (3 Bulan)', 45000),
	(19, 'Rekam Jantung (EKG)', 125000),
	(20, 'Pemeriksaan Fisik Lengkap', 100000),
	(21, 'Tindik Telinga (Bayi/Dewasa)', 75000),
	(500, 'Konsultasi Gizi', 75000),
	(501, 'Fisioterapi Ringan', 100000),
	(502, 'Pembersihan Karang Gigi', 200000),
	(503, 'Tambal Gigi', 150000),
	(504, 'Cabut Gigi Anak', 100000),
	(505, 'Cabut Gigi Dewasa', 150000),
	(506, 'USG Kehamilan', 250000),
	(507, 'Tes Urin Lengkap', 60000),
	(508, 'Tes Darah Lengkap', 150000),
	(509, 'Rapid Test Antigen', 85000),
	(510, 'Vaksin Influenza', 200000),
	(511, 'Vaksin Hepatitis B', 150000),
	(512, 'Sunat / Sirkumsisi', 800000),
	(513, 'Pemasangan Infus', 50000),
	(514, 'Oksigenasi (Per Jam)', 30000),
	(515, 'Konsultasi Psikolog', 150000),
	(516, 'Pemeriksaan Mata', 75000),
	(517, 'Tes Buta Warna', 40000),
	(518, 'Pemeriksaan Pendengaran', 80000),
	(519, 'Medical Checkup Basic', 350000);

-- Dumping structure for table klinik.obat
CREATE TABLE IF NOT EXISTS `obat` (
  `obat_id` smallint NOT NULL AUTO_INCREMENT,
  `nama_obat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `satuan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `harga_jual` decimal(10,0) NOT NULL,
  `stok` smallint unsigned NOT NULL,
  PRIMARY KEY (`obat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.obat: ~27 rows (approximately)
REPLACE INTO `obat` (`obat_id`, `nama_obat`, `satuan`, `harga_jual`, `stok`) VALUES
	(1, 'Diapet', 'Kapsul', 5000, 100),
	(2, 'Obat 399', 'Sirup', 69382, 367),
	(3, 'Obat 629', 'Tablet', 79387, 57),
	(4, 'Obat 239', 'Tablet', 49062, 440),
	(5, 'Obat 984', 'Sirup', 2462, 497),
	(6, 'Obat 919', 'Tablet', 33696, 393),
	(10, 'Vitamin', 'pcs', 100000, 110),
	(500, 'Paracetamol 500mg', 'Tablet', 5000, 100),
	(501, 'Amoxicillin 500mg', 'Kapsul', 10000, 50),
	(502, 'Vitamin C 500mg', 'Tablet', 2000, 200),
	(503, 'Ibuprofen 400mg', 'Tablet', 8000, 80),
	(504, 'Cough Syrup Anak', 'Botol', 15000, 30),
	(505, 'Betadine Antiseptik', 'Botol', 25000, 20),
	(506, 'Kasa Steril', 'Box', 12000, 40),
	(507, 'Alkohol 70%', 'Botol', 20000, 25),
	(508, 'Masker Medis', 'Box', 35000, 50),
	(509, 'Hand Sanitizer', 'Botol', 18000, 60),
	(510, 'Termometer Digital', 'Pcs', 50000, 10),
	(511, 'Plester Luka', 'Box', 5000, 100),
	(512, 'Minyak Kayu Putih', 'Botol', 12000, 44),
	(513, 'Salep Kulit', 'Tube', 15000, 33),
	(514, 'Obat Tetes Mata', 'Botol', 10000, 40),
	(515, 'Vitamin B Complex', 'Tablet', 7000, 90),
	(516, 'Antangin Cair', 'Sachet', 4000, 150),
	(517, 'Omeprazole', 'Kapsul', 12000, 55),
	(518, 'Simvastatin', 'Tablet', 15000, 65),
	(519, 'Metformin', 'Tablet', 10000, 75);

-- Dumping structure for table klinik.pasien
CREATE TABLE IF NOT EXISTS `pasien` (
  `pasien_id` mediumint NOT NULL AUTO_INCREMENT,
  `nama_pasien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `jenis_kelamin` enum('Laki-laki','Perempuan') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `no_telepon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pekerjaan` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `golongan_darah` enum('A+','A-','B+','B-','AB+','AB-','O+','O-','') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status_pernikahan` enum('Menikah','Belum Menikah','Cerai Hidup','Cerai Mati') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nama_kerabat` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `no_telp_kerabat` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`pasien_id`)
) ENGINE=InnoDB AUTO_INCREMENT=521 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.pasien: ~29 rows (approximately)
REPLACE INTO `pasien` (`pasien_id`, `nama_pasien`, `tanggal_lahir`, `jenis_kelamin`, `alamat`, `no_telepon`, `pekerjaan`, `golongan_darah`, `status_pernikahan`, `nama_kerabat`, `no_telp_kerabat`) VALUES
	(27, 'Pasien 71', '1992-10-09', 'Laki-laki', 'Alamat 89 Jalan No. 85', '+621938146420', '-', 'A+', 'Belum Menikah', '-', '-'),
	(50, 'Pasien 795', '1984-09-21', 'Laki-laki', 'Alamat 990 Jalan No. 8', '+621383933490', '-', 'A+', 'Menikah', '-', '-'),
	(68, 'Pasien 369', '1986-09-16', 'Perempuan', 'Alamat 183 Jalan No. 66', '+621727954256', '-', 'A+', 'Belum Menikah', '-', '-'),
	(73, 'Pasien 580', '2021-11-16', 'Laki-laki', 'Alamat 145 Jalan No. 74', '+621220879540', '-', 'A+', 'Menikah', '-', '-'),
	(97, 'Pasien 404', '2024-06-02', 'Laki-laki', 'Alamat 813 Jalan No. 79', '+621491943130', '-', 'A+', 'Belum Menikah', '-', '-'),
	(100, 'wawan', '2025-12-17', 'Perempuan', 'jalan raya ubung kaja (UK)', '0812389123545', '-', 'A+', 'Menikah', '-', '-'),
	(101, 'wahyu', '2025-12-02', 'Laki-laki', 'jalan raya denpasar', '0897867453412', '-', 'A+', 'Belum Menikah', '-', '-'),
	(102, 'Satrio', '2025-06-02', 'Laki-laki', 'Jalan Merdeka no 10', '08123890564783', '-', 'A+', 'Menikah', '-', '-'),
	(103, 'Ari', '2025-06-17', 'Laki-laki', 'Jalan Dewi Sri No 17A', '03616891234234', 'Mahasiswa', 'AB+', 'Belum Menikah', 'Luis Alberto', '0812260312356'),
	(500, 'Pasien A', '1990-01-01', 'Laki-laki', 'Jl. Pasien 1', '0812000001', 'Swasta', 'A+', 'Menikah', 'Istri A', '0812999901'),
	(501, 'Pasien B', '1991-02-02', 'Perempuan', 'Jl. Pasien 2', '0812000002', 'PNS', 'B+', 'Belum Menikah', 'Ibu B', '0812999902'),
	(502, 'Pasien C', '1992-03-03', 'Laki-laki', 'Jl. Pasien 3', '0812000003', 'Wiraswasta', 'O+', 'Menikah', 'Suami C', '0812999903'),
	(503, 'Pasien D', '1993-04-04', 'Perempuan', 'Jl. Pasien 4', '0812000004', 'IRT', 'AB+', 'Cerai Hidup', 'Anak D', '0812999904'),
	(504, 'Pasien E', '1994-05-05', 'Laki-laki', 'Jl. Pasien 5', '0812000005', 'Mahasiswa', 'A-', 'Belum Menikah', 'Ayah E', '0812999905'),
	(505, 'Pasien F', '1995-06-06', 'Perempuan', 'Jl. Pasien 6', '0812000006', 'Guru', 'B-', 'Menikah', 'Suami F', '0812999906'),
	(506, 'Pasien G', '1996-07-07', 'Laki-laki', 'Jl. Pasien 7', '0812000007', 'Polisi', 'O-', 'Belum Menikah', 'Ibu G', '0812999907'),
	(507, 'Pasien H', '1997-08-08', 'Perempuan', 'Jl. Pasien 8', '0812000008', 'Perawat', 'AB-', 'Menikah', 'Suami H', '0812999908'),
	(508, 'Pasien I', '1998-09-09', 'Laki-laki', 'Jl. Pasien 9', '0812000009', 'TNI', 'A+', 'Cerai Mati', 'Anak I', '0812999909'),
	(509, 'Pasien J', '1999-10-10', 'Perempuan', 'Jl. Pasien 10', '0812000010', 'Dosen', 'B+', 'Menikah', 'Suami J', '0812999910'),
	(510, 'Pasien K', '2000-11-11', 'Laki-laki', 'Jl. Pasien 11', '0812000011', 'Pelajar', 'O+', 'Belum Menikah', 'Ayah K', '0812999911'),
	(511, 'Pasien L', '2001-12-12', 'Perempuan', 'Jl. Pasien 12', '0812000012', 'Seniman', 'AB+', 'Menikah', 'Suami L', '0812999912'),
	(512, 'Pasien M', '1985-01-13', 'Laki-laki', 'Jl. Pasien 13', '0812000013', 'Supir', 'A-', 'Cerai Hidup', 'Ibu M', '0812999913'),
	(513, 'Pasien N', '1986-02-14', 'Perempuan', 'Jl. Pasien 14', '0812000014', 'Pedagang', 'B-', 'Menikah', 'Suami N', '0812999914'),
	(514, 'Pasien O', '1987-03-15', 'Laki-laki', 'Jl. Pasien 15', '0812000015', 'Buruh', 'O-', 'Belum Menikah', 'Ayah O', '0812999915'),
	(515, 'Pasien P', '1988-04-16', 'Perempuan', 'Jl. Pasien 16', '0812000016', 'Karyawan', 'AB-', 'Menikah', 'Suami P', '0812999916'),
	(516, 'Pasien Q', '1989-05-17', 'Laki-laki', 'Jl. Pasien 17', '0812000017', 'Petani', 'A+', 'Cerai Mati', 'Anak Q', '0812999917'),
	(517, 'Pasien R', '1990-06-18', 'Perempuan', 'Jl. Pasien 18', '0812000018', 'Penjahit', 'B+', 'Menikah', 'Suami R', '0812999918'),
	(518, 'Pasien S', '1991-07-19', 'Perempuan', 'Jl. Pasien 19', '0812000019', 'Nelayan', 'O+', 'Belum Menikah', 'Ayah S', '0812999919'),
	(519, 'Pasien T', '1992-08-20', 'Perempuan', 'Jl. Pasien 20', '0812000020', 'Koki', 'AB+', 'Menikah', 'Suami T', '0812999920');

-- Dumping structure for table klinik.pembayaran
CREATE TABLE IF NOT EXISTS `pembayaran` (
  `pembayaran_id` int NOT NULL AUTO_INCREMENT,
  `kunjungan_id` int NOT NULL,
  `user_kasir_id` smallint NOT NULL,
  `tanggal_bayar` datetime NOT NULL,
  `total_biaya_jasa` decimal(10,0) NOT NULL,
  `total_biaya_obat` decimal(10,0) NOT NULL,
  `total_bayar` decimal(10,0) NOT NULL,
  `metode_bayar` enum('Tunai','Debit','Asuransi') COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`pembayaran_id`),
  UNIQUE KEY `kunjungan_id` (`kunjungan_id`),
  KEY `fk_user_kasir_id` (`user_kasir_id`),
  CONSTRAINT `fk_kunjungan_id` FOREIGN KEY (`kunjungan_id`) REFERENCES `kunjungan` (`kunjungan_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_kasir_id` FOREIGN KEY (`user_kasir_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.pembayaran: ~27 rows (approximately)
REPLACE INTO `pembayaran` (`pembayaran_id`, `kunjungan_id`, `user_kasir_id`, `tanggal_bayar`, `total_biaya_jasa`, `total_biaya_obat`, `total_bayar`, `metode_bayar`) VALUES
	(1, 104, 5, '2025-11-30 00:00:17', 50000, 55000, 105000, 'Tunai'),
	(3, 73, 5, '2025-11-25 00:00:17', 200000, 55000, 255000, 'Tunai'),
	(10, 101, 6, '2025-12-28 16:26:51', 50000, 25000, 75000, 'Tunai'),
	(13, 102, 1, '2025-12-27 09:37:41', 75000, 0, 75000, 'Debit'),
	(17, 103, 5, '2025-11-26 00:00:17', 30000, 20000, 50000, 'Tunai'),
	(20, 33, 1, '2025-12-31 12:33:36', 75000, 0, 75000, 'Debit'),
	(21, 500, 507, '2025-10-01 09:00:00', 75000, 50000, 125000, 'Tunai'),
	(22, 501, 507, '2025-10-01 09:30:00', 100000, 150000, 250000, 'Debit'),
	(23, 502, 508, '2025-10-01 10:00:00', 200000, 10000, 210000, 'Tunai'),
	(24, 503, 508, '2025-10-01 10:30:00', 150000, 80000, 230000, 'Asuransi'),
	(25, 504, 507, '2025-10-02 09:00:00', 100000, 15000, 115000, 'Tunai'),
	(26, 505, 507, '2025-10-02 09:30:00', 150000, 25000, 175000, 'Debit'),
	(27, 506, 508, '2025-10-02 10:00:00', 250000, 24000, 274000, 'Tunai'),
	(28, 507, 508, '2025-10-02 10:30:00', 60000, 20000, 80000, 'Asuransi'),
	(29, 508, 507, '2025-10-03 11:00:00', 150000, 35000, 185000, 'Tunai'),
	(30, 509, 507, '2025-10-03 11:30:00', 85000, 18000, 103000, 'Debit'),
	(31, 510, 508, '2025-10-03 12:00:00', 200000, 50000, 250000, 'Tunai'),
	(32, 511, 508, '2025-10-03 12:30:00', 150000, 5000, 155000, 'Asuransi'),
	(33, 512, 507, '2025-10-04 09:00:00', 800000, 12000, 812000, 'Debit'),
	(34, 513, 507, '2025-10-04 09:30:00', 50000, 15000, 65000, 'Tunai'),
	(35, 514, 508, '2025-10-04 10:00:00', 30000, 10000, 40000, 'Tunai'),
	(36, 515, 508, '2025-10-04 10:30:00', 150000, 70000, 220000, 'Debit'),
	(37, 516, 507, '2025-10-05 11:00:00', 75000, 20000, 95000, 'Tunai'),
	(38, 517, 507, '2025-10-05 11:30:00', 40000, 120000, 160000, 'Asuransi'),
	(39, 518, 508, '2025-10-05 12:00:00', 80000, 150000, 230000, 'Tunai'),
	(40, 519, 508, '2025-10-05 12:30:00', 350000, 300000, 650000, 'Debit');

-- Dumping structure for table klinik.rekam_medis
CREATE TABLE IF NOT EXISTS `rekam_medis` (
  `rekam_medis_id` int NOT NULL AUTO_INCREMENT,
  `kunjungan_id` int NOT NULL,
  `anamnesa` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pemeriksaan_fisik` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `diagnosa` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tindakan` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`rekam_medis_id`),
  UNIQUE KEY `kunjungan_id` (`kunjungan_id`),
  CONSTRAINT `fk_rm_kunjungan_id` FOREIGN KEY (`kunjungan_id`) REFERENCES `kunjungan` (`kunjungan_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.rekam_medis: ~24 rows (approximately)
REPLACE INTO `rekam_medis` (`rekam_medis_id`, `kunjungan_id`, `anamnesa`, `pemeriksaan_fisik`, `diagnosa`, `tindakan`) VALUES
	(1, 27, 'Pasien datang dengan keluhan sakit tenggorokan dan telinga berdenging. Pasien merasa sakit saat menelan dan ada rasa penuh di telinga.', 'Pemeriksaan menunjukkan adanya iritasi pada tenggorokan dan penurunan pendengaran pada telinga kiri. Tidak ada pembengkakan pada kelenjar getah bening.', 'Diagnosa: Faringitis akut dengan kemungkinan otitis media. Penyebab kemungkinan infeksi virus atau bakteri.', 'Memberikan antibiotik dan obat antiinflamasi. Menyarankan pasien untuk banyak minum air hangat dan istirahat. Jika keluhan berlanjut, rujukan ke spesialis THT.'),
	(2, 97, 'Pasien anak berusia 5 tahun datang dengan keluhan batuk berdahak, demam ringan, dan pilek. Tidak ada riwayat alergi atau penyakit berat.', 'Pemeriksaan fisik menunjukkan adanya hidung tersumbat dan batuk produktif. Suhu tubuh 38°C.', 'Diagnosa: Infeksi saluran pernapasan atas (ISPA), kemungkinan akibat virus. Tidak ada tanda infeksi paru-paru.', 'Memberikan obat batuk dan penurun demam. Menyarankan banyak minum air, istirahat, dan menjaga kelembapan udara di rumah.'),
	(3, 73, 'Pasien dewasa datang dengan keluhan nyeri pada perut bagian bawah disertai dengan rasa mual dan kembung. Nyeri mulai dirasakan sejak kemarin.', 'Pemeriksaan fisik menunjukkan perut kembung dan nyeri tekan pada daerah perut bawah, tidak ada pembengkakan atau tanda perdarahan.', 'Diagnosa: Gastritis akut. Kemungkinan penyebab: pola makan yang tidak teratur dan stres.', 'Memberikan obat antasid dan penghilang rasa sakit. Menyarankan pasien untuk makan teratur, menghindari makanan pedas, dan mengurangi stres.'),
	(5, 102, 'Demam', 'Suhu 39C', 'Demam Berdarah', 'Opname'),
	(500, 500, 'Demam tinggi 3 hari', 'Suhu 39C', 'Demam Berdarah', 'Opname'),
	(501, 501, 'Sakit gigi geraham', 'Gigi berlubang', 'Karies Gigi', 'Tambal'),
	(502, 502, 'Batuk pilek', 'Tenggorokan merah', 'ISPA', 'Obat jalan'),
	(503, 503, 'Mata merah', 'Konjungtiva merah', 'Konjungtivitis', 'Tetes mata'),
	(504, 504, 'Telinga berdenging', 'Kotoran telinga menumpuk', 'Serumen Prop', 'Irigasi telinga'),
	(505, 505, 'Sakit perut', 'Nyeri tekan ulu hati', 'Maag', 'Obat lambung'),
	(506, 506, 'Gatal-gatal kulit', 'Ruam merah', 'Alergi', 'Salep'),
	(507, 507, 'Luka sobek di tangan', 'Luka terbuka 3cm', 'Vulnus Laceratum', 'Jahit 3 simpul'),
	(508, 508, 'Pusing berputar', 'Tekanan darah normal', 'Vertigo', 'Obat vertigo'),
	(509, 509, 'Nyeri sendi lutut', 'Bengkak pada lutut', 'Arthritis', 'Obat nyeri'),
	(510, 510, 'Sesak nafas', 'Wheezing positif', 'Asma', 'Nebulizer'),
	(511, 511, 'Diare 5 kali sehari', 'Dehidrasi ringan', 'Gastroenteritis', 'Oralit & Zinc'),
	(512, 512, 'Sakit kepala sebelah', 'Normal', 'Migrain', 'Obat sakit kepala'),
	(513, 513, 'Nyeri saat berkemih', 'Nyeri tekan suprapubik', 'ISK', 'Antibiotik'),
	(514, 514, 'Bisul di punggung', 'Benjolan bernanah', 'Abses', 'Insisi drainase'),
	(515, 515, 'Kuku cantengan', 'Bengkak ibu jari kaki', 'Unguis Incarnatus', 'Ekstraksi kuku'),
	(516, 516, 'Badan lemas', 'Tensi 90/60', 'Hipotensi', 'Vitamin & Istirahat'),
	(517, 517, 'Kolesterol tinggi', 'Cek lab kolesterol 250', 'Hiperkolesterolemia', 'Simvastatin'),
	(518, 518, 'Gula darah tinggi', 'GDS 300', 'Diabetes Melitus', 'Metformin'),
	(519, 519, 'Asam urat tinggi', 'Bengkak jempol kaki', 'Gout Arthritis', 'Allopurinol');

-- Dumping structure for table klinik.resep
CREATE TABLE IF NOT EXISTS `resep` (
  `resep_id` mediumint NOT NULL AUTO_INCREMENT,
  `rekam_medis_id` int NOT NULL,
  `tanggal_resep` datetime NOT NULL,
  `status_resep` enum('Belum Diambil','Sudah Diambil') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`resep_id`),
  UNIQUE KEY `rekam_medis_id` (`rekam_medis_id`),
  CONSTRAINT `fk_rekam_medis_id` FOREIGN KEY (`rekam_medis_id`) REFERENCES `rekam_medis` (`rekam_medis_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.resep: ~24 rows (approximately)
REPLACE INTO `resep` (`resep_id`, `rekam_medis_id`, `tanggal_resep`, `status_resep`) VALUES
	(1, 3, '2025-11-24 16:37:40', 'Sudah Diambil'),
	(2, 1, '2025-11-24 16:37:40', 'Sudah Diambil'),
	(3, 2, '2025-11-24 16:37:40', 'Sudah Diambil'),
	(5, 5, '2025-12-29 17:49:59', 'Belum Diambil'),
	(500, 500, '2025-10-01 08:10:00', 'Sudah Diambil'),
	(501, 501, '2025-10-01 08:40:00', 'Sudah Diambil'),
	(502, 502, '2025-10-01 09:10:00', 'Sudah Diambil'),
	(503, 503, '2025-10-01 09:40:00', 'Sudah Diambil'),
	(504, 504, '2025-10-02 08:10:00', 'Sudah Diambil'),
	(505, 505, '2025-10-02 08:40:00', 'Sudah Diambil'),
	(506, 506, '2025-10-02 09:10:00', 'Sudah Diambil'),
	(507, 507, '2025-10-02 09:40:00', 'Sudah Diambil'),
	(508, 508, '2025-10-03 10:10:00', 'Sudah Diambil'),
	(509, 509, '2025-10-03 10:40:00', 'Sudah Diambil'),
	(510, 510, '2025-10-03 11:10:00', 'Belum Diambil'),
	(511, 511, '2025-10-03 11:40:00', 'Belum Diambil'),
	(512, 512, '2025-10-04 08:10:00', 'Belum Diambil'),
	(513, 513, '2025-10-04 08:40:00', 'Belum Diambil'),
	(514, 514, '2025-10-04 09:10:00', 'Belum Diambil'),
	(515, 515, '2025-10-04 09:40:00', 'Belum Diambil'),
	(516, 516, '2025-10-05 10:10:00', 'Belum Diambil'),
	(517, 517, '2025-10-05 10:40:00', 'Belum Diambil'),
	(518, 518, '2025-10-05 11:10:00', 'Belum Diambil'),
	(519, 519, '2025-10-05 11:40:00', 'Belum Diambil');

-- Dumping structure for table klinik.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` smallint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Password yang disimpan sudah melalui hashing bcrypt',
  `nama_lengkap` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` enum('Resepsionis','Dokter','Kasir','Apoteker','Manajemen') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `no_telepon` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=527 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table klinik.user: ~36 rows (approximately)
REPLACE INTO `user` (`user_id`, `username`, `password`, `nama_lengkap`, `role`, `no_telepon`, `alamat`) VALUES
	(1, 'Wawan', '$2a$12$Ia60AdbfOGGu58pK8wGp1ufHWOONZLckfDsvlkTBRV5DZrg9pp/HC', 'Wawan Krisnawan', 'Resepsionis', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(2, 'Wahyu', '$2a$12$NC88VHOEiKRZoWwsQtjgKuYOcDGjMpr5Tnt0YZ3SnGhLNdRtoRxrC', 'Wahyu Triadi', 'Dokter', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(3, 'Bayu', '$2a$12$/amNIOgxO2AVUl./kON6UuX6V55nX3ESM5J5JQpr.SkZj79SkVs8K', 'Bayu Krisnawan', 'Apoteker', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(4, 'Ardi', '$2a$12$fNljNQljFMuEoJNTan/xcuoaRE0dbX2R9gkO4sgTyuhTz0e/52.fe', 'Ardi Cakra', 'Manajemen', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(5, 'Thomas', '$2a$12$CmXohW4g4gsYOPPHMnvMW.Uhp0viIi4OKh1ZVYoUZsg/Wcd87yH4e', 'Thomas Alva Edisound', 'Kasir', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(6, 'Resepsionis', '$2a$12$NNrHOTeL2AEIJU7GkuO1zOwIxjgEw/0hQz5mnBL3SklxHeNHKicxK', 'Resepsionis Klinik', 'Resepsionis', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(7, 'Dokter', '$2a$12$JuJ5GHZe1irhPg578ijy6.AV8ecCz22KrjfW98Ytuge2VkcgjOW6.', 'Dokter Kliniks', 'Dokter', '081234567891', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(8, 'Apoteker', '$2a$12$c8CDTSkPqPctbh9lLL.//eM1J5gw.vcTNIsdmrc.84.tgdEJp6bb.', 'Apoteker Klinik', 'Apoteker', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(9, 'Manajemen', '$2a$12$93KBvZEoy.d/dytVZlXcgOAr/u2jq3OP12Ven03cnkJOJNOoNni0S', 'Manajemen Klinik', 'Manajemen', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(10, 'Kasir', '$2a$12$f7Jl3pMuDFGFJcWlKpnMDuPm1GCuZKK6pbLmwr/0MEshurzqu359W', 'Kasir Klinik', 'Kasir', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(11, 'Amelia', '$2a$12$0apiUxobObt3HYn8h7HFDuR8e94fDkyIlw5I1sYPj8A1fv4XNBAgW', 'Amelia Devi', 'Dokter', '08123456789', 'Jl. Abc, Jimbaran, Badung, Bali'),
	(12, 'okaadyuta', '$2a$12$VKa8TAaAGvW4FGIXF0vGzuLfq1wF/AKasZ45b58L.rDO8dZLLLpPq', 'I Putu Gede Oka Adyuta', 'Manajemen', '085805945279', 'Jimbaran, Kec. Kuta Sel., Kabupaten Badung, Bali'),
	(13, 'test1234', '$2a$12$LL9cUZxYcWlCSNKOrbTzMuT89rWsydUMUXee3gCgf9p1QtgA8jpdi', 'test1234', 'Resepsionis', '11111234567890', 'test1234'),
	(14, 'ayurisa', '$2a$12$tP.X1fwzxqYVfPCW6sspleaZ7mYzsMQGiQPjleTgt1E1sgI/.gyji', 'Ayu Risa', 'Dokter', '089512312355', 'Jl. Raya Kerobokan No. 123'),
	(18, 'aguseka', '$2a$12$PefufMAEcs7jMK6Xkw7cS.BdhAwXo0.7clwYg/zGnQ.7ehxqMk2Fu', 'Agus Eka', 'Dokter', '089898676712', 'Jl. Raya Tanah Lot'),
	(500, 'dokter_dummy1', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Budi Santoso', 'Dokter', '0811111111', 'Jl. Dummy No. 1'),
	(501, 'dokter_dummy2', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Siti Aminah', 'Dokter', '0811111112', 'Jl. Dummy No. 2'),
	(502, 'dokter_dummy3', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Andi Wijaya', 'Dokter', '0811111113', 'Jl. Dummy No. 3'),
	(503, 'dokter_dummy4', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Rina Kartika', 'Dokter', '0811111114', 'Jl. Dummy No. 4'),
	(504, 'dokter_dummy5', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Joko Susilo', 'Dokter', '0811111115', 'Jl. Dummy No. 5'),
	(505, 'resepsionis_d1', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Rini Resepsionis', 'Resepsionis', '0822222221', 'Jl. Admin No. 1'),
	(506, 'resepsionis_d2', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Rudi Resepsionis', 'Resepsionis', '0822222222', 'Jl. Admin No. 2'),
	(507, 'kasir_d1', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Kiki Kasir', 'Kasir', '0833333331', 'Jl. Keuangan No. 1'),
	(508, 'kasir_d2', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Koko Kasir', 'Kasir', '0833333332', 'Jl. Keuangan No. 2'),
	(509, 'apoteker_d1', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Ani Apoteker', 'Apoteker', '0844444441', 'Jl. Farmasi No. 1'),
	(510, 'manajemen_d1', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Maman Manajer', 'Manajemen', '0855555551', 'Jl. Kantor No. 1'),
	(511, 'dokter_dummy6', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Farhan', 'Dokter', '0811111116', 'Jl. Dummy No. 6'),
	(512, 'dokter_dummy7', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Galuh', 'Dokter', '0811111117', 'Jl. Dummy No. 7'),
	(513, 'dokter_dummy8', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Hana', 'Dokter', '0811111118', 'Jl. Dummy No. 8'),
	(514, 'dokter_dummy9', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Iwan', 'Dokter', '0811111119', 'Jl. Dummy No. 9'),
	(515, 'dokter_dummy10', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Dr. Jenny', 'Dokter', '0811111120', 'Jl. Dummy No. 10'),
	(516, 'resepsionis_d3', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Sari Resepsionis', 'Resepsionis', '0822222223', 'Jl. Admin No. 3'),
	(517, 'kasir_d3', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Lulu Kasir', 'Kasir', '0833333333', 'Jl. Keuangan No. 3'),
	(518, 'apoteker_d2', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'Budi Apoteker', 'Apoteker', '0844444442', 'Jl. Farmasi No. 2'),
	(519, 'user_tambahan1', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'User Extra 1', 'Resepsionis', '0899999991', 'Jl. Extra No. 1'),
	(520, 'user_tambahan2', '$2a$12$EdIYj2.sqKghiFJKTk25le/PRw5/DVLfqc2AT8KQTVCRi7l1wXDcW', 'User Extra 2', 'Kasir', '0899999992', 'Jl. Extra No. 2');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

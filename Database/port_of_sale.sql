-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2022 at 03:59 AM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `port_of_sale`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `barang_id` int(11) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `kategori_id` int(11) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`barang_id`, `nama_barang`, `kategori_id`, `harga`) VALUES
(1, 'mie sedap kari ayam', 1, 2000),
(4, 'Indomie Goreng ', 1, 2300),
(5, 'mie soto ayam', 1, 2300),
(13, 'Rojo Lele 5Kg', 19, 74000),
(19, 'ABC Kecap Manis', 22, 5500),
(15, 'ABC sambal', 20, 7000),
(14, 'Bimoli 2L', 2, 32000),
(18, 'Advan S6+', 6, 500000);

-- --------------------------------------------------------

--
-- Table structure for table `kategori_barang`
--

CREATE TABLE `kategori_barang` (
  `kategori_id` int(11) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kategori_barang`
--

INSERT INTO `kategori_barang` (`kategori_id`, `nama_kategori`) VALUES
(1, 'mie instan'),
(2, 'Minyak'),
(6, 'handphone'),
(8, 'sepatu'),
(20, 'Saus'),
(18, 'Permen'),
(19, 'Beras'),
(22, 'Kecap');

-- --------------------------------------------------------

--
-- Table structure for table `kota`
--

CREATE TABLE `kota` (
  `id_kota` int(11) NOT NULL,
  `Kd_Kota` int(5) NOT NULL,
  `Kota` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kota`
--

INSERT INTO `kota` (`id_kota`, `Kd_Kota`, `Kota`) VALUES
(1, 1, 'Tangerang');

-- --------------------------------------------------------

--
-- Table structure for table `operator`
--

CREATE TABLE `operator` (
  `nama_lengkap` varchar(50) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `last_login` date DEFAULT NULL,
  `level` enum('admin',' kasir','owner') NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `operator`
--

INSERT INTO `operator` (`nama_lengkap`, `username`, `password`, `last_login`, `level`) VALUES
('kasir', 'kasir', 'kasir', '0000-00-00', ' kasir'),
('aldiansyah', 'owner', 'owner', '0000-00-00', 'owner'),
('aldi', 'admin', 'admin', '0000-00-00', 'admin'),
('Azizah', 'aziza', '123', NULL, 'owner');

-- --------------------------------------------------------

--
-- Table structure for table `stok`
--

CREATE TABLE `stok` (
  `barang_id` int(11) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stok`
--

INSERT INTO `stok` (`barang_id`, `stok`) VALUES
(1, 12),
(4, 24),
(5, 13),
(7, 26),
(9, 2),
(13, 7),
(14, 8),
(15, 30),
(17, 2),
(18, 4),
(19, 30);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL,
  `Kd_Supplier` varchar(9) NOT NULL,
  `Nama_Supplier` varchar(25) NOT NULL,
  `Alamat` text,
  `kategori_id` int(11) NOT NULL,
  `barang_id` int(11) NOT NULL,
  `Telp` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `Kd_Supplier`, `Nama_Supplier`, `Alamat`, `kategori_id`, `barang_id`, `Telp`) VALUES
(2, 'S-D001', 'PT. Distributor', 'Pergudangan Industri Tangerang', 1, 4, '0123'),
(3, 'S-D002', 'CV. Kecap Manis', 'Jl. Kedelai Hitam 5', 2, 14, '123'),
(4, 'S-D001', 'PT.Distributor', 'Pergudangan Industri Tangerang', 1, 5, '0123'),
(5, 'S-D003', 'PT. Adisty', 'Jl. Kencan', 19, 13, '0321'),
(6, 'S-D004', 'PT. Tech Sejahtera', 'Jl. Teknologi Pusat', 6, 18, '213'),
(7, 'S-D005', 'PT. Maju Mundur', 'Jl. Ramai Jaya', 20, 15, '02143');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `transaksi_id` int(11) NOT NULL,
  `tanggal_transaksi` date NOT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `pemasukan` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`transaksi_id`, `tanggal_transaksi`, `operator_id`, `pemasukan`) VALUES
(7, '2014-07-18', 1, 0),
(6, '2014-07-17', 2, 0),
(5, '2014-07-17', 2, 0),
(8, '2016-05-23', 5, 0),
(9, '2022-01-22', NULL, 600300),
(10, '2022-01-22', NULL, 600300),
(11, '2022-01-22', NULL, 600300),
(12, '2022-01-22', NULL, 600300),
(13, '2022-01-22', NULL, 600300),
(14, '2022-01-22', NULL, 600300),
(15, '2022-01-22', NULL, 600300),
(16, '2022-01-22', NULL, 600300),
(17, '2022-01-22', NULL, 600300),
(18, '2022-01-22', NULL, 600300),
(19, '2022-01-22', NULL, 600300),
(20, '2022-01-22', NULL, 478000),
(21, '2022-01-22', NULL, 478000),
(22, '2022-01-22', NULL, 478000),
(23, '2022-01-22', NULL, 478000),
(24, '2022-01-22', NULL, 478000),
(25, '2022-01-22', NULL, 478000),
(26, '2022-01-22', NULL, 478000),
(27, '2022-01-22', NULL, 562000),
(28, '2022-01-22', NULL, 562000),
(29, '2022-01-22', NULL, 562000),
(30, '2022-01-22', NULL, 562000),
(31, '2022-01-22', NULL, 49000),
(32, '2022-01-22', NULL, 49000),
(33, '2022-01-22', NULL, 49000),
(34, '2022-01-22', NULL, 49000),
(35, '2022-01-22', NULL, 14000),
(36, '2022-01-22', NULL, 56000),
(37, '2022-01-22', NULL, 56000),
(38, '2022-01-22', NULL, 21000),
(39, '2022-01-22', NULL, 56000),
(40, '2022-01-22', NULL, 70000),
(41, '2022-01-22', NULL, 70000),
(42, '2022-01-22', NULL, 7000),
(43, '2022-01-22', NULL, 63000),
(44, '2022-01-22', NULL, 0),
(45, '2022-01-22', NULL, 535000);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_detail`
--

CREATE TABLE `transaksi_detail` (
  `t_detail_id` int(11) NOT NULL,
  `barang_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `transaksi_id` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `status` enum('0','1') NOT NULL COMMENT '1= sudah diproses , 0 belum diproses',
  `sub_total` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi_detail`
--

INSERT INTO `transaksi_detail` (`t_detail_id`, `barang_id`, `qty`, `transaksi_id`, `harga`, `status`, `sub_total`) VALUES
(10, 1, 6, 30, 2000, '1', 0),
(9, 6, 3, 30, 3000, '1', 0),
(8, 1, 4, 30, 2000, '1', 0),
(11, 5, 4, 30, 2300, '1', 0),
(12, 4, 4, 30, 1500, '1', 0),
(13, 1, 3, 30, 2000, '1', 0),
(14, 6, 2, 30, 3000, '1', 0),
(15, 4, 4, 30, 1500, '1', 0),
(22, 5, 7, 30, 2300, '1', 0),
(21, 5, 6, 30, 2300, '1', 0),
(23, 4, 2, 38, 1500, '1', 3000),
(24, 1, 3, 38, 2000, '1', 6000),
(25, 5, 1, 38, 2300, '1', 2300),
(41, 14, 2, 38, 32000, '1', 64000),
(27, 17, 2, 38, 18000, '1', 36000),
(40, 15, 79, 38, 7000, '1', 553000),
(42, 17, 23, 38, 18000, '1', 414000),
(43, 15, 12, 38, 7000, '1', 84000),
(44, 15, 7, 38, 7000, '1', 49000),
(45, 15, 2, 38, 7000, '1', 14000),
(46, 15, 8, 38, 7000, '1', 56000),
(47, 15, 3, 38, 7000, '1', 21000),
(48, 15, 8, 39, 7000, '1', 56000),
(49, 15, 10, 40, 7000, '1', 70000),
(50, 15, 1, 42, 7000, '1', 7000),
(51, 15, 9, 43, 7000, '1', 63000),
(52, 14, 2, 44, 32000, '1', 64000),
(53, 15, 5, 44, 7000, '1', 35000),
(56, 15, 5, 45, 7000, '1', 35000),
(57, 18, 1, 45, 500000, '1', 500000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`barang_id`);

--
-- Indexes for table `kategori_barang`
--
ALTER TABLE `kategori_barang`
  ADD PRIMARY KEY (`kategori_id`);

--
-- Indexes for table `kota`
--
ALTER TABLE `kota`
  ADD PRIMARY KEY (`id_kota`);

--
-- Indexes for table `operator`
--
ALTER TABLE `operator`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `stok`
--
ALTER TABLE `stok`
  ADD PRIMARY KEY (`barang_id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`transaksi_id`);

--
-- Indexes for table `transaksi_detail`
--
ALTER TABLE `transaksi_detail`
  ADD PRIMARY KEY (`t_detail_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `barang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `kategori_barang`
--
ALTER TABLE `kategori_barang`
  MODIFY `kategori_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `kota`
--
ALTER TABLE `kota`
  MODIFY `id_kota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `transaksi_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
--
-- AUTO_INCREMENT for table `transaksi_detail`
--
ALTER TABLE `transaksi_detail`
  MODIFY `t_detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 06, 2023 at 05:44 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `QuanLySinhVien`
--

-- --------------------------------------------------------

--
-- Table structure for table `DiemThi`
--

CREATE TABLE `DiemThi` (
  `id` int(11) NOT NULL,
  `maSinhVien` int(11) DEFAULT NULL,
  `maGiangVien` int(11) NOT NULL,
  `maMonHoc` int(11) DEFAULT NULL,
  `diemCuoiKy` float DEFAULT NULL,
  `diemChuyenCan` float DEFAULT NULL,
  `diemGiuaKy` float DEFAULT NULL,
  `lanThi` int(11) NOT NULL DEFAULT 1,
  `deleteFlag` int(11) NOT NULL DEFAULT 0,
  `tinhTrang` varchar(50) NOT NULL DEFAULT 'Đang học',
  `diemChu` varchar(50) NOT NULL DEFAULT 'Chưa tính'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `DiemThi`
--

INSERT INTO `DiemThi` (`id`, `maSinhVien`, `maGiangVien`, `maMonHoc`, `diemCuoiKy`, `diemChuyenCan`, `diemGiuaKy`, `lanThi`, `deleteFlag`, `tinhTrang`, `diemChu`) VALUES
(1, 1, 2, 4, 9, 10, 7, 1, 0, 'Đạt', 'A'),
(2, 1, 2, 7, NULL, NULL, NULL, 1, 0, 'Đang học', 'Chưa tính'),
(3, 1, 3, 3, 9, 8, 8, 1, 0, 'Đạt', 'A'),
(4, 6, 2, 3, 9, 8, 8, 1, 0, 'Đạt', 'A'),
(5, 9, 2, 9, NULL, NULL, NULL, 1, 0, 'Đang học', 'Chưa tính'),
(6, 8, 3, 9, 6, 10, 9, 1, 0, 'Đạt', 'C'),
(7, 8, 6, 4, 8, 10, 9, 1, 0, 'Đạt', 'B'),
(8, 8, 8, 1, 10, 10, 10, 1, 0, 'Đạt', 'A'),
(9, 8, 6, 6, 8, 10, 8, 1, 0, 'Đạt', 'B'),
(10, 19, 6, 6, 10, 9, 9, 1, 0, 'Đạt', 'A'),
(11, 20, 13, 5, 1, 10, 10, 1, 0, 'Không đạt', 'F'),
(12, 9, 13, 5, NULL, NULL, NULL, 1, 0, 'Đang học', 'Chưa tính'),
(13, 6, 13, 5, NULL, NULL, NULL, 1, 0, 'Đang học', 'Chưa tính'),
(14, 20, 13, 9, NULL, NULL, NULL, 1, 0, 'Đang học', 'Chưa tính'),
(15, 20, 13, 2, NULL, NULL, NULL, 1, 0, 'Đang học', 'Chưa tính');

--
-- Triggers `DiemThi`
--
DELIMITER $$
CREATE TRIGGER `ThemBangHocPhi` AFTER INSERT ON `DiemThi` FOR EACH ROW BEGIN
SET @soTinChi = (SELECT mh.soTinChi FROM MonHoc mh WHERE id = NEW.maMonHoc);
    INSERT INTO HocPhi VALUES (NULL, NEW.maSinhVien, NEW.maMonhoc, @soTinChi * 390000, 0);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_tinhTrang_column` BEFORE UPDATE ON `DiemThi` FOR EACH ROW BEGIN
    DECLARE diemTB DOUBLE;
    SET diemTB = (NEW.diemChuyenCan * 0.1 + NEW.diemGiuaKy * 0.2 + NEW.diemCuoiKy * 0.7);

    IF diemTB >= 4 THEN
        SET NEW.tinhTrang = 'Đạt';
    ELSE
        SET NEW.tinhTrang = 'Không đạt';
    END IF;
    IF diemTB >= 8.5 THEN
        SET NEW.diemChu = 'A';
    ELSEIF diemTB >= 7.0 THEN
        SET NEW.diemChu = 'B';
    ELSEIF diemTB >= 5.5 THEN
        SET NEW.diemChu = 'C';
    ELSEIF diemTB >= 4.0 THEN
        SET NEW.diemChu = 'D';
    ELSE
        SET NEW.diemChu = 'F';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `GiangVien`
--

CREATE TABLE `GiangVien` (
  `id` int(11) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `maKhoa` int(11) NOT NULL,
  `soDienThoai` varchar(20) NOT NULL,
  `username` varchar(500) NOT NULL,
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `GiangVien`
--

INSERT INTO `GiangVien` (`id`, `ten`, `email`, `maKhoa`, `soDienThoai`, `username`, `deleteFlag`) VALUES
(1, 'Trần Thị Dậu', 'duong@gmail.com', 1, '093831313', 'daudv', 0),
(2, 'Dương Văn Lâm', 'lam@gmail.com', 2, '093831313', 'trangdd', 0),
(3, 'Dương Văn Thành', 'thanh@gmail.com', 1, '093831311', 'thanhdv', 1),
(4, 'Nguyễn Văn Trung', 'trung@gmail.com', 1, '093435611', 'trungnv', 0),
(5, 'Nguyễn Văn Thắng', 'Nguyễn Văn Thắng', 2, '093435612', 'thangvv', 1),
(6, 'Văn Đình Khánh', 'Văn Đình Khánh', 2, '093435123', 'khanhvd', 0),
(7, 'Đặng Văn Kiên', 'kien123@gmail.com', 1, '093831211', 'kienDV', 0),
(8, 'Trần Ngọc Tân', 'tan1234@gmail.com', 2, '0987631313', 'tantn', 0),
(9, 'Trâm Anh', 'tram@gmail.com', 1, '09222922', 'tramkk', 0),
(13, 'Giảng Văn Viên', 'giangviencute@gmail.com', 1, '038713122', 'giangvien', 0),
(14, 'Giảng Thị Nụ', 'giangnuxinh@gmail.com', 1, '0921847141', 'giangnu', 0);

--
-- Triggers `GiangVien`
--
DELIMITER $$
CREATE TRIGGER `themGV_trigger` AFTER INSERT ON `GiangVien` FOR EACH ROW INSERT INTO TaiKhoan 
VALUES (NULL,NEW.username, '123456a@', "1" ,0)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `GiangVienDangKy`
--

CREATE TABLE `GiangVienDangKy` (
  `maGiangVien` int(11) NOT NULL,
  `maMonHoc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `GiangVienDangKy`
--

INSERT INTO `GiangVienDangKy` (`maGiangVien`, `maMonHoc`) VALUES
(1, 1),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 9),
(2, 1),
(2, 3),
(2, 4),
(2, 5),
(2, 9),
(3, 1),
(3, 3),
(3, 4),
(3, 5),
(3, 9),
(4, 1),
(4, 3),
(4, 4),
(4, 5),
(4, 9),
(5, 1),
(5, 3),
(5, 4),
(5, 5),
(5, 9),
(6, 1),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(6, 9),
(7, 1),
(7, 3),
(7, 4),
(7, 5),
(7, 9),
(8, 1),
(8, 3),
(8, 4),
(8, 5),
(8, 6),
(8, 9),
(9, 1),
(9, 3),
(9, 4),
(9, 5),
(9, 9),
(13, 0),
(13, 2),
(13, 5),
(13, 9),
(14, 5);

-- --------------------------------------------------------

--
-- Table structure for table `HocPhi`
--

CREATE TABLE `HocPhi` (
  `id` int(11) NOT NULL,
  `maSinhVien` int(11) DEFAULT NULL,
  `maMonHoc` int(11) DEFAULT NULL,
  `HocPhi` double DEFAULT NULL,
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `HocPhi`
--

INSERT INTO `HocPhi` (`id`, `maSinhVien`, `maMonHoc`, `HocPhi`, `deleteFlag`) VALUES
(1, 8, 9, 1170000, 0),
(2, 8, 4, 1170000, 0),
(3, 8, 1, 1170000, 0),
(4, 8, 6, 1560000, 0),
(5, 19, 6, 1560000, 0),
(6, 20, 5, 1170000, 0),
(7, 9, 5, 1170000, 0),
(8, 6, 5, 1170000, 0),
(9, 20, 9, 1170000, 0),
(10, 20, 2, 1170000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Khoa`
--

CREATE TABLE `Khoa` (
  `id` int(11) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Khoa`
--

INSERT INTO `Khoa` (`id`, `ten`, `deleteFlag`) VALUES
(1, 'Khoa Công Trình', 0),
(2, 'Khoa Cơ Khí', 0),
(3, 'Khoa Công nghệ thông tin', 0),
(4, 'Khoa Kinh tế vận tải', 0),
(5, 'Khoa Mạng máy tính', 0);

-- --------------------------------------------------------

--
-- Table structure for table `LopHoc`
--

CREATE TABLE `LopHoc` (
  `id` int(11) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `maKhoa` int(11) NOT NULL,
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `LopHoc`
--

INSERT INTO `LopHoc` (`id`, `ten`, `maKhoa`, `deleteFlag`) VALUES
(1, '71DCCK22', 1, 0),
(2, '71DCCK23', 1, 0),
(3, '70DCMT22', 5, 0),
(4, '72DCMT21', 5, 0),
(5, '71DCVT22', 4, 0),
(6, '69DCCK20', 2, 0),
(7, '70DCTT23', 3, 0),
(8, '71DCTT23', 3, 0),
(9, '73DCVT24', 4, 0),
(10, '69DCCT22', 1, 0),
(11, '72DCTT23', 3, 0),
(12, '72DCVT21', 1, 0),
(13, '73DCVT24', 4, 0),
(14, '73DCVT24', 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `MonHoc`
--

CREATE TABLE `MonHoc` (
  `id` int(11) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `soTinChi` int(11) NOT NULL,
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `MonHoc`
--

INSERT INTO `MonHoc` (`id`, `ten`, `soTinChi`, `deleteFlag`) VALUES
(1, 'Nhập môn cơ sở dữ liệu', 3, 0),
(2, 'Trí tuệ nhân tạo', 3, 0),
(3, 'Tư tưởng Hồ Chí Minh', 2, 0),
(4, 'Hệ quản trị cơ sở dữ liệu', 3, 0),
(5, 'Nhập môn xử lý ảnh', 3, 0),
(6, 'Toán rời rạc', 4, 0),
(7, 'Linh kiện điện tử', 3, 0),
(8, 'Nhập môn Tin học', 3, 0),
(9, 'Công nghệ phần mềm', 3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `SinhVien`
--

CREATE TABLE `SinhVien` (
  `id` int(11) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `ngaySinh` date NOT NULL,
  `gioiTinh` enum('Nam','Nữ') NOT NULL,
  `diaChi` varchar(255) NOT NULL,
  `maLop` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `soDienThoai` varchar(15) NOT NULL,
  `username` varchar(500) NOT NULL,
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `SinhVien`
--

INSERT INTO `SinhVien` (`id`, `ten`, `ngaySinh`, `gioiTinh`, `diaChi`, `maLop`, `email`, `soDienThoai`, `username`, `deleteFlag`) VALUES
(1, 'Văn Đình Khánh', '2004-05-03', 'Nữ', 'Thanh Hóa', 1, 'VDK@gmail.com', '098773819', 'khanhvan', 0),
(3, 'Phượng Đào Thị', '2002-05-06', 'Nữ', 'Hà Nội', 2, 'phuongdt@gmail.com', '098713131', 'phuongdt', 0),
(6, 'Đỗ Ngọc Cao', '2002-05-10', 'Nam', 'Nam Định', 1, 'Caodn@gmail.com', '029873133', 'caodn', 0),
(7, 'Đỗ Quang Thị Duy', '2002-05-17', 'Nữ', 'Hà Nam', 1, 'duy@gmail.com', '098371313', 'duydn', 0),
(8, 'Hoàng Ngọc Báo', '2022-07-14', 'Nữ', 'Thanh Hóa', 2, 'ngoc@gmail.com', '093813322', 'ngocht', 0),
(9, 'Lương Sơn', '2002-05-11', 'Nữ', 'Hà Nam', 1, 'Sơn@gmail.com', '018371313', 'sonlv', 0),
(12, 'Quỳnh Nguyễn Thị', '2023-05-06', 'Nữ', 'Thái Bình', 12, 'quynh@gmail.com', '028131132', 'quynhnt', 0),
(19, 'Trần Sỹ Quang', '2002-06-08', 'Nữ', 'Nam Định', 4, 'quangts@gmail.com', '098313133', 'quangts', 0),
(20, 'Sinh Thị Viên', '2002-06-15', 'Nữ', 'Thanh Hóa', 12, 'sinhvien123@edu.com', '0223456789', 'sinhvien', 0);

--
-- Triggers `SinhVien`
--
DELIMITER $$
CREATE TRIGGER `themSinhVien_trigger` AFTER INSERT ON `SinhVien` FOR EACH ROW INSERT INTO TaiKhoan VALUES (NULL,NEW.username,'123456','0','0')
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `TaiKhoan`
--

CREATE TABLE `TaiKhoan` (
  `id` int(11) NOT NULL,
  `username` varchar(500) NOT NULL,
  `password` varchar(500) NOT NULL DEFAULT '123456',
  `vaiTro` enum('0','1','2') NOT NULL DEFAULT '0',
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `TaiKhoan`
--

INSERT INTO `TaiKhoan` (`id`, `username`, `password`, `vaiTro`, `deleteFlag`) VALUES
(1, 'khanhvd', '123456', '1', 0),
(2, 'kienDV', '123456', '1', 0),
(3, 'daudv', '123456', '1', 0),
(4, 'admin', 'admin', '2', 0),
(5, 'tramkk', '123456', '1', 0),
(6, 'trangdd', '123456', '1', 0),
(9, 'ngocht', '123456', '0', 0),
(10, 'tantn', '123456', '1', 0),
(11, 'khanhvan', '123456', '0', 0),
(12, 'caodn', '123456', '0', 0),
(13, 'quangts', '123456', '0', 0),
(14, 'thanhdv', '123456', '1', 0),
(15, 'trungnv', '123456', '1', 0),
(16, 'thangvv', '123456', '1', 0),
(17, 'phuongdt', '123456', '0', 0),
(18, 'duydn', '123456', '0', 0),
(19, 'sonlv', '123456', '0', 0),
(20, 'quynhnt', '123456', '0', 0),
(21, 'sinhvien', '123456', '0', 0),
(22, 'giangvien', '123456a@', '1', 0),
(23, 'giangnu', '123456a@', '1', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `DiemThi`
--
ALTER TABLE `DiemThi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `maSinhVien` (`maSinhVien`),
  ADD KEY `maMonHoc` (`maMonHoc`);

--
-- Indexes for table `GiangVien`
--
ALTER TABLE `GiangVien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `maKhoa` (`maKhoa`);

--
-- Indexes for table `GiangVienDangKy`
--
ALTER TABLE `GiangVienDangKy`
  ADD PRIMARY KEY (`maGiangVien`,`maMonHoc`);

--
-- Indexes for table `HocPhi`
--
ALTER TABLE `HocPhi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `maSinhVien` (`maSinhVien`),
  ADD KEY `maMonHoc` (`maMonHoc`);

--
-- Indexes for table `Khoa`
--
ALTER TABLE `Khoa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `LopHoc`
--
ALTER TABLE `LopHoc`
  ADD PRIMARY KEY (`id`),
  ADD KEY `maKhoa` (`maKhoa`);

--
-- Indexes for table `MonHoc`
--
ALTER TABLE `MonHoc`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `SinhVien`
--
ALTER TABLE `SinhVien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `maLop` (`maLop`);

--
-- Indexes for table `TaiKhoan`
--
ALTER TABLE `TaiKhoan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `DiemThi`
--
ALTER TABLE `DiemThi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `GiangVien`
--
ALTER TABLE `GiangVien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `HocPhi`
--
ALTER TABLE `HocPhi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Khoa`
--
ALTER TABLE `Khoa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `LopHoc`
--
ALTER TABLE `LopHoc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `MonHoc`
--
ALTER TABLE `MonHoc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `SinhVien`
--
ALTER TABLE `SinhVien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `TaiKhoan`
--
ALTER TABLE `TaiKhoan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `DiemThi`
--
ALTER TABLE `DiemThi`
  ADD CONSTRAINT `diemthi_ibfk_1` FOREIGN KEY (`maSinhVien`) REFERENCES `SinhVien` (`id`),
  ADD CONSTRAINT `diemthi_ibfk_2` FOREIGN KEY (`maMonHoc`) REFERENCES `MonHoc` (`id`);

--
-- Constraints for table `GiangVien`
--
ALTER TABLE `GiangVien`
  ADD CONSTRAINT `giangvien_ibfk_1` FOREIGN KEY (`maKhoa`) REFERENCES `Khoa` (`id`);

--
-- Constraints for table `HocPhi`
--
ALTER TABLE `HocPhi`
  ADD CONSTRAINT `hocphi_ibfk_1` FOREIGN KEY (`maSinhVien`) REFERENCES `SinhVien` (`id`),
  ADD CONSTRAINT `hocphi_ibfk_2` FOREIGN KEY (`maMonHoc`) REFERENCES `MonHoc` (`id`);

--
-- Constraints for table `LopHoc`
--
ALTER TABLE `LopHoc`
  ADD CONSTRAINT `lophoc_ibfk_1` FOREIGN KEY (`maKhoa`) REFERENCES `Khoa` (`id`);

--
-- Constraints for table `SinhVien`
--
ALTER TABLE `SinhVien`
  ADD CONSTRAINT `sinhvien_ibfk_1` FOREIGN KEY (`maLop`) REFERENCES `LopHoc` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

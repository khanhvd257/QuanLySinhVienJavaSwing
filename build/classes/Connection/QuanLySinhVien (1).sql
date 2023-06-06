-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 04, 2023 at 04:44 AM
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
(1, 1, 2, 4, 2, 10, 7, 1, 0, 'Không đạt', 'F'),
(2, 1, 2, 7, NULL, NULL, NULL, 1, 0, 'Đang học', 'Chưa tính');

--
-- Triggers `DiemThi`
--
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
(1, 'Dương Văn Dậu', 'duong@gmail.com', 1, '0938313131313', 'duongddd', 0),
(2, 'Dương Văn Lâm', 'lam@gmail.com', 2, '0938313132', 'tragddd', 0),
(3, 'Dương Văn Thành', 'thanh@gmail.com', 1, '0938313111', 'thanhdv', 1),
(4, 'Nguyễn Văn Trung', 'trung@gmail.com', 1, '0934356111', 'trungnv', 0),
(5, 'Nguyễn Văn Thắng', 'Nguyễn Văn Thắng', 2, '093435612', 'Nguyễn Văn Thắng', 1),
(6, 'Văn Đình Khánh', 'Văn Đình Khánh', 2, '093435123', 'khanhvd', 0),
(7, 'Đặng Văn Kiên', 'kien123@gmail.com', 2, '093831234', 'kienDV', 0),
(8, 'Trần Ngọc Tân', 'tan1234@gmail.com', 2, '0914812414', 'tantt', 0),
(9, 'Trâm Anh', 'tram@gmail.com', 1, '09222922', 'tramkk', 0);

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
(1, 'Văn Đình Khánh', '2004-05-03', 'Nữ', 'Thanh Hóa', 1, 'VDK@gmail.com', '098773819', 'khanhvd', 0),
(3, 'Phượng Đào Thị', '2002-05-06', 'Nữ', 'Hà Nội', 2, 'phuongdt@gmail.com', '098713131', 'phuongdt', 0),
(6, 'Đỗ Ngọc Cao', '2002-05-10', 'Nam', 'Nam Định', 1, 'Caodn@gmail.com', '029873133', 'caodn', 0),
(7, 'Đỗ Quang Thị Duy', '2002-05-17', 'Nữ', 'Hà Nam', 1, 'duy@gmail.com', '098371313', 'duydn', 0),
(8, 'Hoàng Ngọc Báo', '2022-07-14', 'Nữ', 'Thanh Hóa', 2, 'ngoc@gmail.com', '093813322', 'ngocht', 0),
(9, 'Lương Sơn', '2002-05-11', 'Nữ', 'Hà Nam', 1, 'Sơn@gmail.com', '018371313', 'sonlv', 0),
(12, 'Quỳnh Nguyễn', '2023-05-06', 'Nữ', 'Thái Bình', 1, 'quynh@gmail.com', '028131132', 'quynhnt', 0);

-- --------------------------------------------------------

--
-- Table structure for table `TaiKhoan`
--

CREATE TABLE `TaiKhoan` (
  `id` int(11) NOT NULL,
  `username` varchar(500) NOT NULL,
  `password` varchar(500) NOT NULL,
  `vaiTro` enum('0','1','2') NOT NULL DEFAULT '0',
  `deleteFlag` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `TaiKhoan`
--

INSERT INTO `TaiKhoan` (`id`, `username`, `password`, `vaiTro`, `deleteFlag`) VALUES
(1, 'khanhvd', '123456', '0', 0),
(2, 'kienDV', '123456', '0', 0),
(3, 'tantt', '123456', '0', 0),
(4, 'tramkk', '123346', '0', 0);

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
  ADD KEY `maKhoa` (`maKhoa`);

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
  ADD KEY `maLop` (`maLop`);

--
-- Indexes for table `TaiKhoan`
--
ALTER TABLE `TaiKhoan`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `DiemThi`
--
ALTER TABLE `DiemThi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `GiangVien`
--
ALTER TABLE `GiangVien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `HocPhi`
--
ALTER TABLE `HocPhi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `TaiKhoan`
--
ALTER TABLE `TaiKhoan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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

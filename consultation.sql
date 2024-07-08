-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 08, 2024 at 08:43 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `consultation`
--

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `id` int(11) NOT NULL,
  `doctor_username` varchar(50) DEFAULT NULL,
  `user_username` varchar(50) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chat`
--

INSERT INTO `chat` (`id`, `doctor_username`, `user_username`, `message`, `timestamp`) VALUES
(72, 'user1', 'Robby', 'asd', '2024-06-16 07:48:39'),
(73, 'user1', NULL, 'asd', '2024-06-16 07:48:41'),
(74, 'user1', NULL, 'a', '2024-06-16 07:54:40'),
(75, 'user1', 'Robby', 'asd', '2024-06-16 07:54:59'),
(76, 'user1', NULL, 'asd', '2024-06-16 07:55:02'),
(77, 'user1', 'Robby', 'tes', '2024-06-16 07:55:05'),
(78, 'user1', NULL, 'a', '2024-06-16 07:57:00'),
(79, 'user1', NULL, 'asd', '2024-06-16 07:57:27'),
(80, 'user1', NULL, 'asd', '2024-06-16 07:57:30'),
(81, 'user1', NULL, 'a', '2024-06-16 08:13:04'),
(82, 'user1', NULL, 'a', '2024-06-16 08:14:06');

-- --------------------------------------------------------

--
-- Table structure for table `chats`
--

CREATE TABLE `chats` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `sender` varchar(10) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chats`
--

INSERT INTO `chats` (`id`, `user_id`, `doctor_id`, `message`, `sender`, `timestamp`) VALUES
(82, 1, 1, 'Halo dok', 'user', '2024-07-06 12:08:45'),
(83, 1, 1, 'Iya halo, ada keluhan apa?', 'doctor', '2024-07-06 12:08:56'),
(84, 1, 1, 'Saya merasa pusing dok, untuk obatnya kira kira apa ya dok?', 'user', '2024-07-06 12:09:18'),
(85, 1, 1, 'Untuk obatnya paracetamol saja bisa, saya kasih resep 10 buah paracetamol ya', 'doctor', '2024-07-06 12:09:48'),
(86, 1, 1, 'Untuk harganya 10.000 aja ya', 'doctor', '2024-07-06 12:10:07'),
(87, 1, 1, 'Siap dok', 'user', '2024-07-06 12:10:11');

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `specialization` varchar(255) NOT NULL,
  `contact_info` varchar(255) NOT NULL,
  `saldo` varchar(255) NOT NULL,
  `Pengalaman` int(100) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`id`, `name`, `specialization`, `contact_info`, `saldo`, `Pengalaman`, `username`, `password`, `status`) VALUES
(1, 'Dr. Ahmad', 'Dokter gigi', 'Ahmad@peter.petra.id', '30000', 10, 'user1', 'password1', 1),
(2, 'Dr. Budi', 'Dokter Mental', 'Budi@peter.petra.id', '', 5, 'user2', 'password2', 0),
(3, 'Dr. Chandra', 'Dokter Bidan', 'Chandra@peter.petra.id', '', 3, 'user3', 'password3', 0),
(4, 'Dr. Dewi', 'Dokter Saraf', 'Dewi@peter.petra.id', '', 7, 'user4', 'password4', 0),
(5, 'Dr. Eka', 'Dokter Bedah', 'Eka@peter.petra.id', '', 20, 'user5', 'password5', 0),
(6, 'Dr. Fajar', 'Dokter Umum', 'Fajar@peter.petra.id', '', 6, 'user6', 'password6', 0);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `doctor_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `payment_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userId` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `qty` int(11) DEFAULT 0,
  `medicine` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `username`, `password`, `qty`, `medicine`) VALUES
(1, 'robby', 'january14', 0, NULL),
(2, 'exampleUser1', 'password1', 0, ''),
(3, 'exampleUser2', 'password2', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `user_id` int(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `qty` int(11) DEFAULT 0,
  `medicine` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user_id`, `username`, `password`, `qty`, `medicine`) VALUES
(1, 1, 'Robby', 'password', NULL, 'halo'),
(18, 0, 'Robby', 'password', 0, '');

--
-- Triggers `users`
--
DELIMITER $$
CREATE TRIGGER `before_insert_users_trigger` BEFORE INSERT ON `users` FOR EACH ROW BEGIN
    SET NEW.id = NEW.user_id;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chats`
--
ALTER TABLE `chats`
  ADD PRIMARY KEY (`id`),
  ADD KEY `doctor_id` (`doctor_id`),
  ADD KEY `chats_ibfk_1` (`user_id`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `doctor_id` (`doctor_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- AUTO_INCREMENT for table `chats`
--
ALTER TABLE `chats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chats`
--
ALTER TABLE `chats`
  ADD CONSTRAINT `chats_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `chats_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

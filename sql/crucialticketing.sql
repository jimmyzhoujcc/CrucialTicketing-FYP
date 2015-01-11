-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 11, 2015 at 10:44 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `crucialticketing`
--

-- --------------------------------------------------------

--
-- Table structure for table `queue`
--

CREATE TABLE IF NOT EXISTS `queue` (
  `queue_id` int(11) NOT NULL AUTO_INCREMENT,
  `queue_name` varchar(25) NOT NULL,
  PRIMARY KEY (`queue_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `queue`
--

INSERT INTO `queue` (`queue_id`, `queue_name`) VALUES
(1, 'Default Queue');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(25) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role_name`) VALUES
(1, 'END_USER');

-- --------------------------------------------------------

--
-- Table structure for table `system`
--

CREATE TABLE IF NOT EXISTS `system` (
  `system_id` int(11) NOT NULL AUTO_INCREMENT,
  `system_name` varchar(25) NOT NULL,
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `system`
--

INSERT INTO `system` (`system_id`, `system_name`) VALUES
(1, 'Crucial Manager');

-- --------------------------------------------------------

--
-- Table structure for table `system_control`
--

CREATE TABLE IF NOT EXISTS `system_control` (
  `system_control_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_type_id` int(11) NOT NULL,
  `system_id` int(11) NOT NULL,
  `workflow_template_id` int(11) NOT NULL,
  PRIMARY KEY (`system_control_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `ticket_id` int(11) NOT NULL AUTO_INCREMENT,
  `short_description` varchar(50) NOT NULL,
  `system_control_id` int(11) NOT NULL,
  `message_processor_id` int(11) NOT NULL,
  `created_by_id` int(11) NOT NULL,
  `reported_by_id` int(11) NOT NULL,
  `current_status_id` int(11) NOT NULL,
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`ticket_id`, `short_description`, `system_control_id`, `message_processor_id`, `created_by_id`, `reported_by_id`, `current_status_id`) VALUES
(1, 'the', 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ticket_change`
--

CREATE TABLE IF NOT EXISTS `ticket_change` (
  `ticket_change_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `change_time` int(11) NOT NULL,
  PRIMARY KEY (`ticket_change_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ticket_type`
--

CREATE TABLE IF NOT EXISTS `ticket_type` (
  `ticket_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_type_name` varchar(25) NOT NULL,
  PRIMARY KEY (`ticket_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `ticket_type`
--

INSERT INTO `ticket_type` (`ticket_type_id`, `ticket_type_name`) VALUES
(1, 'Incident');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`) VALUES
(1, 'test', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `workflow_status`
--

CREATE TABLE IF NOT EXISTS `workflow_status` (
  `workflow_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(25) NOT NULL,
  PRIMARY KEY (`workflow_status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `workflow_status`
--

INSERT INTO `workflow_status` (`workflow_status_id`, `status_name`) VALUES
(1, 'New'),
(2, 'Work In Progress'),
(3, 'Complete');

-- --------------------------------------------------------

--
-- Table structure for table `workflow_structure`
--

CREATE TABLE IF NOT EXISTS `workflow_structure` (
  `workflow_structure_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_template_id` int(11) NOT NULL,
  `workflow_status_from_id` int(11) NOT NULL,
  `workflow_status_to_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `queue_id` int(11) NOT NULL,
  PRIMARY KEY (`workflow_structure_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `workflow_structure`
--

INSERT INTO `workflow_structure` (`workflow_structure_id`, `workflow_template_id`, `workflow_status_from_id`, `workflow_status_to_id`, `role_id`, `queue_id`) VALUES
(1, 1, 1, 2, 1, 1),
(2, 1, 2, 3, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `workflow_template`
--

CREATE TABLE IF NOT EXISTS `workflow_template` (
  `workflow_template_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_template_name` varchar(25) NOT NULL,
  PRIMARY KEY (`workflow_template_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `workflow_template`
--

INSERT INTO `workflow_template` (`workflow_template_id`, `workflow_template_name`) VALUES
(1, 'Incident default');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

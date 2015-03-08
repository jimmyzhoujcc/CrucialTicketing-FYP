-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2015 at 03:29 AM
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
-- Table structure for table `application`
--

CREATE TABLE IF NOT EXISTS `application` (
  `application_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_name` varchar(25) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`application_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `application`
--

INSERT INTO `application` (`application_id`, `application_name`, `active_flag`) VALUES
(1, 'Crucial Manager', 0);

-- --------------------------------------------------------

--
-- Table structure for table `application_change_log`
--

CREATE TABLE IF NOT EXISTS `application_change_log` (
  `application_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `application_name` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`application_change_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `application_control`
--

CREATE TABLE IF NOT EXISTS `application_control` (
  `application_control_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_type_id` int(11) NOT NULL,
  `application_id` int(11) NOT NULL,
  `workflow_template_id` int(11) NOT NULL,
  `severity_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `sla_clock` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`application_control_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `application_control`
--

INSERT INTO `application_control` (`application_control_id`, `ticket_type_id`, `application_id`, `workflow_template_id`, `severity_id`, `role_id`, `sla_clock`, `active_flag`) VALUES
(1, 1, 1, 1, 2, 0, 800, 0),
(3, 1, 1, 1, 1, 0, 200, 0);

-- --------------------------------------------------------

--
-- Table structure for table `application_control_change_log`
--

CREATE TABLE IF NOT EXISTS `application_control_change_log` (
  `application_control_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_control_id` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`application_control_change_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `change_log`
--

CREATE TABLE IF NOT EXISTS `change_log` (
  `change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `application_control_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `workflow_status_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  PRIMARY KEY (`change_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `change_log`
--

INSERT INTO `change_log` (`change_log_id`, `ticket_id`, `application_control_id`, `user_id`, `workflow_status_id`, `stamp`) VALUES
(1, 1, 3, 1, 1, 1424289520),
(2, 1, 3, 1, 2, 1424289520),
(3, 2, 3, 1, 1, 1424291233),
(4, 3, 3, 1, 1, 1424291305),
(5, 4, 3, 1, 1, 1424291768);

-- --------------------------------------------------------

--
-- Table structure for table `file_upload`
--

CREATE TABLE IF NOT EXISTS `file_upload` (
  `file_upload_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `file_name` varchar(250) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(250) NOT NULL,
  `stamp` int(11) NOT NULL,
  PRIMARY KEY (`file_upload_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `file_upload`
--

INSERT INTO `file_upload` (`file_upload_id`, `ticket_id`, `user_id`, `file_name`, `name`, `description`, `stamp`) VALUES
(1, 4, 1, 'DANIEL JAMES FOLEY CV.pdf', '', '', 1424291875);

-- --------------------------------------------------------

--
-- Table structure for table `queue`
--

CREATE TABLE IF NOT EXISTS `queue` (
  `queue_id` int(11) NOT NULL AUTO_INCREMENT,
  `queue_name` varchar(25) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`queue_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `queue`
--

INSERT INTO `queue` (`queue_id`, `queue_name`, `active_flag`) VALUES
(1, 'hello', -1);

-- --------------------------------------------------------

--
-- Table structure for table `queue_change_log`
--

CREATE TABLE IF NOT EXISTS `queue_change_log` (
  `queue_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `queue_id` int(11) NOT NULL,
  `queue_name` varchar(25) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`queue_change_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `queue_change_log`
--

INSERT INTO `queue_change_log` (`queue_change_log_id`, `queue_id`, `queue_name`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(1, 1, 'hello', 99999, 1, 1425401378, -1);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(25) NOT NULL,
  `role_description` varchar(150) NOT NULL,
  `protected` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role_name`, `role_description`, `protected`, `active_flag`) VALUES
(1, 'END_USER', '', 0, 1),
(2, 'MAINT_1_TICKET_1', '', 0, 1),
(3, 'SUPER_USER', '', 0, 1),
(4, 'MAINT_USER_CREATION', '', 0, 1),
(5, 'MAINT_ROLE_CREATION', '', 0, 1),
(6, 'MAINT_QUEUE_CREATION', 'Role required to maintain queues', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `role_change_log`
--

CREATE TABLE IF NOT EXISTS `role_change_log` (
  `role_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `role_name` varchar(25) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`role_change_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `role_change_log`
--

INSERT INTO `role_change_log` (`role_change_log_id`, `role_id`, `role_name`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(1, 15, '', 191919, 1, 1424649048, -1);

-- --------------------------------------------------------

--
-- Table structure for table `severity`
--

CREATE TABLE IF NOT EXISTS `severity` (
  `severity_id` int(11) NOT NULL AUTO_INCREMENT,
  `severity_level` int(11) NOT NULL,
  `severity_name` varchar(25) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`severity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `severity`
--

INSERT INTO `severity` (`severity_id`, `severity_level`, `severity_name`, `active_flag`) VALUES
(1, 1, 'Very High', 0),
(2, 2, 'High', 0),
(3, 3, 'Medium', 0),
(4, 4, 'Low', 0),
(5, 5, 'Very Low', 0);

-- --------------------------------------------------------

--
-- Table structure for table `severity_change_log`
--

CREATE TABLE IF NOT EXISTS `severity_change_log` (
  `severity_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `severity_id` int(11) NOT NULL,
  `severity_level` int(11) NOT NULL,
  `severity_name` varchar(25) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`severity_change_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `ticket_id` int(11) NOT NULL AUTO_INCREMENT,
  `short_description` varchar(50) NOT NULL,
  `application_control_id` int(11) NOT NULL,
  `created_by_id` int(11) NOT NULL,
  `reported_by_id` int(11) NOT NULL,
  `current_status_id` int(11) NOT NULL,
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`ticket_id`, `short_description`, `application_control_id`, `created_by_id`, `reported_by_id`, `current_status_id`) VALUES
(1, 'test ticket', 3, 1, 1, 2),
(2, 'huhiu', 3, 1, 1, 1),
(3, 'iojoi', 3, 1, 1, 1),
(4, 'jjjj', 3, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ticket_lock_request`
--

CREATE TABLE IF NOT EXISTS `ticket_lock_request` (
  `lock_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`lock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ticket_log`
--

CREATE TABLE IF NOT EXISTS `ticket_log` (
  `ticket_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `ticket_log_entry` varchar(1000) NOT NULL,
  `stamp` int(11) NOT NULL,
  PRIMARY KEY (`ticket_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `ticket_log`
--

INSERT INTO `ticket_log` (`ticket_log_id`, `ticket_id`, `user_id`, `ticket_log_entry`, `stamp`) VALUES
(1, 1, 1, 'Test', 1424289520),
(2, 2, 1, 'njkn', 1424291233),
(3, 3, 1, 'hio', 1424291305),
(4, 4, 1, 'jjjj', 1424291768);

-- --------------------------------------------------------

--
-- Table structure for table `ticket_type`
--

CREATE TABLE IF NOT EXISTS `ticket_type` (
  `ticket_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_type_name` varchar(25) NOT NULL,
  PRIMARY KEY (`ticket_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `ticket_type`
--

INSERT INTO `ticket_type` (`ticket_type_id`, `ticket_type_name`) VALUES
(1, 'Incident'),
(2, 'Problem'),
(3, 'Change'),
(4, 'Service Request');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `hash` varchar(250) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  `contact` varchar(15) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=44 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `hash`, `first_name`, `last_name`, `email_address`, `contact`, `active_flag`) VALUES
(1, 'dan', '1000:94c3d8666ce83e05054ea294d69819db9991b2ae452e66f1:c5d222c0133afd3dea35949063011f0a3656a1f6b5fb6f9c', 'Dan', 'Foley', 'Dan', 'Dan', 1),
(43, 'danielFoley', '', 'daniel', 'foley', 'djf1991@hotmail.co.uk', '07803326264', -2);

-- --------------------------------------------------------

--
-- Table structure for table `user_alert`
--

CREATE TABLE IF NOT EXISTS `user_alert` (
  `user_alert_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `message` varchar(250) NOT NULL,
  `read` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  PRIMARY KEY (`user_alert_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user_alert`
--

INSERT INTO `user_alert` (`user_alert_id`, `user_id`, `message`, `read`, `stamp`) VALUES
(1, 1, 'A user account has been setup with the following information: Username (null) Password (CS7CJ5qx)', 1, 1424642634),
(2, 1, 'A role has been setup with the following information: role Name (hello) Role Description (null)', 1, 1424649111);

-- --------------------------------------------------------

--
-- Table structure for table `user_change_log`
--

CREATE TABLE IF NOT EXISTS `user_change_log` (
  `user_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `hash` varchar(100) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  `contact` varchar(15) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_change_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user_change_log`
--

INSERT INTO `user_change_log` (`user_change_log_id`, `user_id`, `hash`, `email_address`, `contact`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(2, 42, '', 'djf1991@hotmail.co.uk', '07803326264', 1, 1, 1425515519, -2),
(3, 43, '', 'djf1991@hotmail.co.uk', '07803326264', 1, 1, 1425515555, -2);

-- --------------------------------------------------------

--
-- Table structure for table `user_queue_con`
--

CREATE TABLE IF NOT EXISTS `user_queue_con` (
  `user_queue_con_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `queue_id` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  `new_user_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_queue_con_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `user_queue_con`
--

INSERT INTO `user_queue_con` (`user_queue_con_id`, `user_id`, `queue_id`, `active_flag`, `new_user_flag`) VALUES
(1, 29, 6, 1, 1),
(2, 30, 6, 1, 1),
(3, 29, 7, 1, 1),
(4, 30, 7, 1, 1),
(5, 1, 9, -1, 1),
(6, 38, 9, -1, 1),
(7, 1, 10, -1, 1),
(8, 38, 10, -1, 1),
(9, 1, 11, -1, 1),
(10, 38, 11, -1, 1),
(11, 1, 12, -1, 1),
(12, 38, 12, -1, 1),
(13, 1, 13, -1, 1),
(14, 1, 14, -1, 1),
(15, 1, 1, -1, 1),
(16, 38, 1, -1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_role_con`
--

CREATE TABLE IF NOT EXISTS `user_role_con` (
  `user_role_con_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `valid_from` int(11) NOT NULL,
  `valid_to` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  `new_user_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_role_con_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `user_role_con`
--

INSERT INTO `user_role_con` (`user_role_con_id`, `user_id`, `role_id`, `valid_from`, `valid_to`, `active_flag`, `new_user_flag`) VALUES
(1, 1, 1, 0, 999999999, 1, 0),
(2, 1, 4, 0, 999999999, 1, 0),
(3, 1, 2, 0, 999999999, 1, 0),
(4, 1, 5, 0, 999999999, 1, 0),
(13, 1, 12, 0, 999999999, 1, 0),
(25, 38, 1, 662688000, 663465600, 1, 1),
(26, 38, 2, 662688000, 687049200, 1, 1),
(27, 1, 6, 0, 0, 1, 0),
(28, 42, 1, 662688000, 663465600, -2, 1),
(29, 43, 1, 662688000, 663465600, -2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_role_con_change_log`
--

CREATE TABLE IF NOT EXISTS `user_role_con_change_log` (
  `user_role_con_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `valid_from` int(11) NOT NULL,
  `valid_to` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_role_con_change_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user_role_con_change_log`
--

INSERT INTO `user_role_con_change_log` (`user_role_con_change_log_id`, `user_id`, `role_id`, `valid_from`, `valid_to`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(1, 42, 1, 662688000, 663465600, 1, 1, 1425515536, -1),
(2, 43, 1, 662688000, 663465600, 1, 1, 1425515564, -1);

-- --------------------------------------------------------

--
-- Table structure for table `workflow_status`
--

CREATE TABLE IF NOT EXISTS `workflow_status` (
  `workflow_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_status_name` varchar(25) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`workflow_status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `workflow_status`
--

INSERT INTO `workflow_status` (`workflow_status_id`, `workflow_status_name`, `active_flag`) VALUES
(1, 'New', 0),
(2, 'Assigned', 0),
(3, 'Work In Progress', 0),
(4, 'Complete', 0);

-- --------------------------------------------------------

--
-- Table structure for table `workflow_status_change_log`
--

CREATE TABLE IF NOT EXISTS `workflow_status_change_log` (
  `workflow_status_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_status_id` int(11) NOT NULL,
  `workflow_status_name` varchar(25) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`workflow_status_change_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `workflow_structure`
--

CREATE TABLE IF NOT EXISTS `workflow_structure` (
  `workflow_structure_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_template_id` int(11) NOT NULL,
  `from_workflow_status_id` int(11) NOT NULL,
  `to_workflow_status_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `queue_id` int(11) NOT NULL,
  `clock_active` int(11) NOT NULL,
  PRIMARY KEY (`workflow_structure_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `workflow_structure`
--

INSERT INTO `workflow_structure` (`workflow_structure_id`, `workflow_template_id`, `from_workflow_status_id`, `to_workflow_status_id`, `role_id`, `queue_id`, `clock_active`) VALUES
(1, 1, 1, 2, 1, 1, 1),
(2, 1, 2, 3, 1, 1, 1),
(3, 1, 3, 4, 1, 1, 1),
(4, 1, 1, 4, 1, 1, 1);

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

--
-- Constraints for dumped tables
--

--
-- Constraints for table `workflow_template`
--
ALTER TABLE `workflow_template`
  ADD CONSTRAINT `workflow_template_ibfk_1` FOREIGN KEY (`workflow_template_id`) REFERENCES `workflow_structure` (`workflow_structure_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

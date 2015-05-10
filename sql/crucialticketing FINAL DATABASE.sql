-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 16, 2015 at 10:51 AM
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
  `application_name` varchar(50) NOT NULL,
  `protected` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`application_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `application`
--

INSERT INTO `application` (`application_id`, `application_name`, `protected`, `active_flag`) VALUES
(1, 'Crucial Manager', 1, 1),
(8, 'Printing Network', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `application_change_log`
--

CREATE TABLE IF NOT EXISTS `application_change_log` (
  `application_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `application_name` varchar(25) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`application_change_log_id`),
  KEY `application_id` (`application_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `application_control`
--

CREATE TABLE IF NOT EXISTS `application_control` (
  `application_control_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_type_id` int(11) NOT NULL,
  `application_id` int(11) NOT NULL,
  `workflow_id` int(11) NOT NULL,
  `severity_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `sla_clock` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`application_control_id`),
  KEY `ticket_type_id` (`ticket_type_id`),
  KEY `application_id` (`application_id`),
  KEY `workflow_id` (`workflow_id`),
  KEY `severity_id` (`severity_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `application_control`
--

INSERT INTO `application_control` (`application_control_id`, `ticket_type_id`, `application_id`, `workflow_id`, `severity_id`, `role_id`, `sla_clock`, `active_flag`) VALUES
(3, 1, 1, 3, 1, 2, 1000, 1),
(4, 2, 1, 3, 1, 2, 1000, 1),
(5, 1, 1, 3, 2, 2, 1000, 1);

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
  PRIMARY KEY (`application_control_change_log_id`),
  KEY `application_control_id` (`application_control_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Table structure for table `application_control_lock_request`
--

CREATE TABLE IF NOT EXISTS `application_control_lock_request` (
  `application_control_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_control_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`application_control_lock_request_id`),
  KEY `application_control_id` (`application_control_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `application_lock_request`
--

CREATE TABLE IF NOT EXISTS `application_lock_request` (
  `application_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`application_lock_request_id`),
  KEY `application_id` (`application_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  PRIMARY KEY (`file_upload_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Table structure for table `queue`
--

CREATE TABLE IF NOT EXISTS `queue` (
  `queue_id` int(11) NOT NULL AUTO_INCREMENT,
  `queue_name` varchar(50) NOT NULL,
  `protected` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`queue_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `queue`
--

INSERT INTO `queue` (`queue_id`, `queue_name`, `protected`, `active_flag`) VALUES
(1, 'Reporting User', 1, 1);

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
  PRIMARY KEY (`queue_change_log_id`),
  KEY `queue_id` (`queue_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `queue_lock_request`
--

CREATE TABLE IF NOT EXISTS `queue_lock_request` (
  `queue_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `queue_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`queue_lock_request_id`),
  KEY `queue_id` (`queue_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `role_description` varchar(150) NOT NULL,
  `protected` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role_name`, `role_description`, `protected`, `active_flag`) VALUES
(1, 'END_USER', 'Default role to access the system', 1, 1),
(2, 'MAINT_TICKET_CREATION', 'Role required to create tickets', 1, 1),
(3, 'MAINT_USER_CREATION', 'Role required to create user requests', 1, 1),
(4, 'MAINT_ROLE_CREATION', 'Role required to create role requests', 1, 1),
(5, 'MAINT_QUEUE_CREATION', 'Role required to create queue requests', 1, 1),
(6, 'MAINT_APPLICATION_CREATION', 'Role required to create application requests', 1, 1),
(7, 'MAINT_SEVERITY_CREATION', 'Role required to create severity requests', 1, 1),
(8, 'MAINT_CONFIGURATION_CREATION', 'Role required to create configuration requests', 1, 1),
(9, 'MAINT_WORKFLOW_CREATION', 'Role required to create workflow requests', 1, 1),
(10, 'MAINT_WORKFLOWSTATUS_CREATION', 'Role required to create workflow status requests', 1, 1),
(11, 'MAINT_TICKET_VIEW', 'Role required to view existing tickets', 1, 1),
(12, 'MAINT_USER_VIEW', 'Role required to view existing users', 1, 1),
(13, 'MAINT_ROLE_VIEW', 'Role required to view existing roles', 1, 1),
(14, 'MAINT_QUEUE_VIEW', 'Role required to view existing queues', 1, 1),
(15, 'MAINT_APPLICATION_VIEW', 'Role required to view existing applications', 1, 1),
(16, 'MAINT_SEVERITY_VIEW', 'Role required to view existing severities', 1, 1),
(17, 'MAINT_CONFIGURATION_VIEW', 'Role required to view existing configurations', 1, 1),
(18, 'MAINT_WORKFLOW_VIEW', 'Role required to view existing workflows', 1, 1),
(19, 'MAINT_WORKFLOWSTATUS_VIEW', 'Role required to view existing workflow statuses', 1, 1),
(20, 'MAINT_TICKET_UPDATE', 'Role required to update tickets', 1, 1),
(21, 'MAINT_USER_UPDATE', 'Role required to update users', 1, 1),
(22, 'MAINT_ROLE_UPDATE', 'Role required to update roles', 1, 1),
(23, 'MAINT_QUEUE_UPDATE', 'Role required to update queues', 1, 1),
(24, 'MAINT_APPLICATION_UPDATE', 'Role required to update applications', 1, 1),
(25, 'MAINT_SEVERITY_UPDATE', 'Role required to update severities', 1, 1),
(26, 'MAINT_CONFIGURATION_UPDATE', 'Role required to update configurations', 1, 1),
(27, 'MAINT_WORKFLOW_UPDATE', 'Role required to update workflows', 1, 1),
(28, 'MAINT_WORKFLOWSTATUS_UPDATE', 'Role required to update workflow statuses', 1, 1),
(29, 'MAINT_REPORTING', 'Role required to execute reports', 1, 1);

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
  PRIMARY KEY (`role_change_log_id`),
  KEY `role_id` (`role_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `role_lock_request`
--

CREATE TABLE IF NOT EXISTS `role_lock_request` (
  `role_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`role_lock_request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
(1, 1, 'Very High', 1),
(2, 2, 'High', 1),
(3, 3, 'Medium', 1),
(4, 4, 'Low', 1),
(5, 5, 'Very Low', 1);

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
  PRIMARY KEY (`severity_change_log_id`),
  KEY `severity_id` (`severity_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `severity_lock_request`
--

CREATE TABLE IF NOT EXISTS `severity_lock_request` (
  `severity_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `severity_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`severity_lock_request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `ticket_id` int(11) NOT NULL AUTO_INCREMENT,
  `short_description` varchar(50) NOT NULL,
  `reported_by_user_id` int(11) NOT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `reported_by_id` (`reported_by_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

--
-- Table structure for table `ticket_change_log`
--

CREATE TABLE IF NOT EXISTS `ticket_change_log` (
  `ticket_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `application_control_id` int(11) NOT NULL,
  `workflow_status_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  PRIMARY KEY (`ticket_change_log_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `application_control_id` (`application_control_id`),
  KEY `workflow_status_id` (`workflow_status_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_complete_earliest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_complete_earliest_view` (
`ticket_id` int(11)
,`short_description` varchar(50)
,`reported_by_user_id` int(11)
,`ticket_type_id` int(11)
,`application_id` int(11)
,`severity_id` int(11)
,`workflow_id` int(11)
,`workflow_status_id` int(11)
,`queue_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_complete_latest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_complete_latest_view` (
`ticket_id` int(11)
,`short_description` varchar(50)
,`reported_by_user_id` int(11)
,`ticket_type_id` int(11)
,`application_id` int(11)
,`severity_id` int(11)
,`workflow_id` int(11)
,`workflow_status_id` int(11)
,`queue_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_control_earliest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_control_earliest_view` (
`ticket_id` int(11)
,`ticket_type_id` int(11)
,`application_id` int(11)
,`severity_id` int(11)
,`workflow_id` int(11)
,`workflow_status_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_control_latest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_control_latest_view` (
`ticket_id` int(11)
,`ticket_type_id` int(11)
,`application_id` int(11)
,`severity_id` int(11)
,`workflow_id` int(11)
,`workflow_status_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_earliest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_earliest_view` (
`ticket_id` int(11)
,`short_description` varchar(50)
,`reported_by_user_id` int(11)
,`ticket_type_id` int(11)
,`application_id` int(11)
,`severity_id` int(11)
,`workflow_id` int(11)
,`workflow_status_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_history_earliest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_history_earliest_view` (
`ticket_change_log_id` int(11)
,`ticket_id` int(11)
,`application_control_id` int(11)
,`workflow_status_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_history_latest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_history_latest_view` (
`ticket_change_log_id` int(11)
,`ticket_id` int(11)
,`application_control_id` int(11)
,`workflow_status_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `ticket_latest_view`
--
CREATE TABLE IF NOT EXISTS `ticket_latest_view` (
`ticket_id` int(11)
,`short_description` varchar(50)
,`reported_by_user_id` int(11)
,`ticket_type_id` int(11)
,`application_id` int(11)
,`severity_id` int(11)
,`workflow_id` int(11)
,`workflow_status_id` int(11)
,`requestor_user_id` int(11)
,`stamp` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `ticket_link`
--

CREATE TABLE IF NOT EXISTS `ticket_link` (
  `ticket_link_id` int(11) NOT NULL AUTO_INCREMENT,
  `from_ticket_id` int(11) NOT NULL,
  `to_ticket_id` int(11) NOT NULL,
  PRIMARY KEY (`ticket_link_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `ticket_link`
--

INSERT INTO `ticket_link` (`ticket_link_id`, `from_ticket_id`, `to_ticket_id`) VALUES
(1, 4, 5),
(2, 4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `ticket_lock_request`
--

CREATE TABLE IF NOT EXISTS `ticket_lock_request` (
  `ticket_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`ticket_lock_request_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

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
  PRIMARY KEY (`ticket_log_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=25 ;

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
  `username` varchar(25) NOT NULL,
  `hash` varchar(250) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  `contact` varchar(15) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `hash`, `first_name`, `last_name`, `email_address`, `contact`, `active_flag`) VALUES
(1, 'Default', 'DISABLED', 'System', 'User', 'Default', 'Default', 1),
(2, 'dan', '1000:16bc408a3ff4be1705880247ef5ba1dc67e8024614023a74:f1f18c3829e20aef9b92d34a37ca9056667912420aba1fca', 'Super', 'User', 'Super User', 'Super User', 1);

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  PRIMARY KEY (`user_change_log_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `user_change_log`
--

INSERT INTO `user_change_log` (`user_change_log_id`, `user_id`, `hash`, `email_address`, `contact`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(1, 2, '', 'djf1991@hotmail.co.uk', '07803326264', 1, 2, 1426901489, 1),
(3, 2, '', 'Super User', 'Super User', 1, 2, 1426963509, 0),
(4, 2, '', 'Super User', 'Super User', 1, 2, 1426963587, 1),
(5, 2, '', 'Super User', 'Super User', 1, 2, 1426963741, 0),
(8, 2, '', 'Super User', 'Super User', 1, 2, 1427470285, 0),
(9, 2, '', 'Super User', 'Super User', 1, 2, 1427470393, 0),
(10, 2, '', 'Super User', 'Super User', 1, 2, 1427470646, 0),
(11, 2, '', 'Super User', 'Super User', 1, 2, 1427470794, 0),
(12, 2, '', 'Super User', 'Super User', 1, 2, 1427470935, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_lock_request`
--

CREATE TABLE IF NOT EXISTS `user_lock_request` (
  `user_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`user_lock_request_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_queue_con`
--

CREATE TABLE IF NOT EXISTS `user_queue_con` (
  `user_queue_con_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `queue_id` int(11) NOT NULL,
  `new_user_flag` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_queue_con_id`),
  KEY `user_id` (`user_id`),
  KEY `queue_id` (`queue_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `user_queue_con`
--

INSERT INTO `user_queue_con` (`user_queue_con_id`, `user_id`, `queue_id`, `new_user_flag`, `active_flag`) VALUES
(10, 2, 1, 0, -1);

-- --------------------------------------------------------

--
-- Table structure for table `user_queue_con_change_log`
--

CREATE TABLE IF NOT EXISTS `user_queue_con_change_log` (
  `user_queue_con_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_queue_con_id` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_queue_con_change_log_id`),
  KEY `user_queue_con_id` (`user_queue_con_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `requestor_user_id` (`requestor_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

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
  `new_user_flag` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_role_con_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

--
-- Dumping data for table `user_role_con`
--

INSERT INTO `user_role_con` (`user_role_con_id`, `user_id`, `role_id`, `valid_from`, `valid_to`, `new_user_flag`, `active_flag`) VALUES
(1, 2, 1, 1429170047, 1433116800, 0, 1),
(2, 2, 2, 0, 1433116800, 0, 1),
(3, 2, 3, 0, 1433116800, 0, 1),
(4, 2, 4, 0, 1433116800, 0, 1),
(5, 2, 5, 0, 1433116800, 0, 1),
(6, 2, 6, 0, 1433116800, 0, 1),
(7, 2, 7, 0, 1433116800, 0, 1),
(8, 2, 8, 0, 1433116800, 0, 1),
(9, 2, 9, 0, 1433116800, 0, 1),
(10, 2, 10, 0, 1433116800, 0, 1),
(11, 2, 11, 0, 1433116800, 0, 1),
(12, 2, 12, 0, 1433116800, 0, 1),
(13, 2, 13, 0, 1433116800, 0, 1),
(14, 2, 14, 0, 1433116800, 0, 1),
(15, 2, 15, 0, 1433116800, 0, 1),
(16, 2, 16, 0, 1433116800, 0, 1),
(17, 2, 17, 0, 1433116800, 0, 1),
(18, 2, 18, 0, 1433116800, 0, 1),
(19, 2, 19, 0, 1433116800, 0, 1),
(20, 2, 20, 0, 1433116800, 0, 1),
(21, 2, 21, 0, 1433116800, 0, 1),
(22, 2, 22, 0, 1433116800, 0, 1),
(23, 2, 23, 0, 1433116800, 0, 1),
(24, 2, 24, 0, 1433116800, 0, 1),
(25, 2, 25, 0, 1433116800, 0, 1),
(26, 2, 26, 0, 1433116800, 0, 1),
(27, 2, 27, 0, 1433116800, 0, 1),
(28, 2, 28, 0, 1433116800, 0, 1),
(29, 2, 29, 0, 1433116800, 0, 1),
(30, 1, 1, 0, 0, 0, -1),
(31, 1, 1, 1427673600, 1427760000, 0, -1);

-- --------------------------------------------------------

--
-- Table structure for table `user_role_con_change_log`
--

CREATE TABLE IF NOT EXISTS `user_role_con_change_log` (
  `user_role_con_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_role_con_id` int(11) NOT NULL,
  `valid_from` int(11) NOT NULL,
  `valid_to` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`user_role_con_change_log_id`),
  KEY `user_role_con_id` (`user_role_con_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `user_role_con_change_log`
--

INSERT INTO `user_role_con_change_log` (`user_role_con_change_log_id`, `user_role_con_id`, `valid_from`, `valid_to`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(1, 30, 0, 0, 4, 2, 1427746597, -2),
(2, 30, 0, 0, 4, 2, 1427746600, -1),
(3, 31, 1427673600, 1427760000, 4, 2, 1427746889, -2),
(4, 31, 1427673600, 1427760000, 4, 2, 1427746899, -1);

-- --------------------------------------------------------

--
-- Table structure for table `workflow`
--

CREATE TABLE IF NOT EXISTS `workflow` (
  `workflow_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_name` varchar(50) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`workflow_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `workflow`
--

INSERT INTO `workflow` (`workflow_id`, `workflow_name`, `active_flag`) VALUES
(3, 'Default Workflow', 1);

-- --------------------------------------------------------

--
-- Table structure for table `workflow_change_log`
--

CREATE TABLE IF NOT EXISTS `workflow_change_log` (
  `workflow_change_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_id` int(11) NOT NULL,
  `workflow_name` varchar(100) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `stamp` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`workflow_change_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `workflow_change_log`
--

INSERT INTO `workflow_change_log` (`workflow_change_log_id`, `workflow_id`, `workflow_name`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(1, 2, 'New workflow Name', 1, 2, 1426191081, -1),
(2, 2, 'New workflow Name', 1, 2, 1426191082, 1),
(3, 2, 'Test', 1, 2, 1427052085, 0),
(4, 2, 'Test', 1, 2, 1427052186, 1),
(5, 2, 'Test', 1, 2, 1427559319, 0),
(6, 3, 'Default Workflow', 1, 2, 1427559487, -1),
(7, 3, 'Default Workflow', 0, 2, 1427559840, 1);

-- --------------------------------------------------------

--
-- Table structure for table `workflow_lock_request`
--

CREATE TABLE IF NOT EXISTS `workflow_lock_request` (
  `workflow_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`workflow_lock_request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `workflow_status`
--

CREATE TABLE IF NOT EXISTS `workflow_status` (
  `workflow_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_status_name` varchar(25) NOT NULL,
  `workflow_status_type` int(11) NOT NULL,
  `active_flag` int(11) NOT NULL,
  PRIMARY KEY (`workflow_status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `workflow_status`
--

INSERT INTO `workflow_status` (`workflow_status_id`, `workflow_status_name`, `workflow_status_type`, `active_flag`) VALUES
(1, 'New', 1, 1),
(2, 'Assigned', 0, 1),
(3, 'Work In Progress', 0, 1),
(4, 'Complete', 2, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `workflow_status_change_log`
--

INSERT INTO `workflow_status_change_log` (`workflow_status_change_log_id`, `workflow_status_id`, `workflow_status_name`, `ticket_id`, `requestor_user_id`, `stamp`, `active_flag`) VALUES
(1, 1, 'New', 1, 2, 1427068029, 0),
(2, 1, 'New', 1, 2, 1427068061, 1),
(3, 1, 'New', 1, 2, 1427559339, 0),
(4, 1, 'New', 1, 2, 1427559366, 1);

-- --------------------------------------------------------

--
-- Table structure for table `workflow_status_lock_request`
--

CREATE TABLE IF NOT EXISTS `workflow_status_lock_request` (
  `workflow_status_lock_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_status_id` int(11) NOT NULL,
  `requestor_user_id` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  `request_pass_time` int(11) NOT NULL,
  PRIMARY KEY (`workflow_status_lock_request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `workflow_structure`
--

CREATE TABLE IF NOT EXISTS `workflow_structure` (
  `workflow_structure_id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_id` int(11) NOT NULL,
  `from_workflow_status_id` int(11) NOT NULL,
  `to_workflow_status_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `queue_id` int(11) NOT NULL,
  `clock_active` int(11) NOT NULL,
  PRIMARY KEY (`workflow_structure_id`),
  KEY `workflow_id` (`workflow_id`),
  KEY `from_workflow_status_id` (`from_workflow_status_id`),
  KEY `to_workflow_status_id` (`to_workflow_status_id`),
  KEY `role_id` (`role_id`),
  KEY `queue_id` (`queue_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `workflow_structure`
--

INSERT INTO `workflow_structure` (`workflow_structure_id`, `workflow_id`, `from_workflow_status_id`, `to_workflow_status_id`, `role_id`, `queue_id`, `clock_active`) VALUES
(5, 3, 1, 2, 1, 1, 0),
(6, 3, 2, 3, 1, 1, 1),
(7, 3, 3, 4, 1, 1, 1);

-- --------------------------------------------------------

--
-- Structure for view `ticket_complete_earliest_view`
--
DROP TABLE IF EXISTS `ticket_complete_earliest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_complete_earliest_view` AS select `ticket_earliest_view`.`ticket_id` AS `ticket_id`,`ticket_earliest_view`.`short_description` AS `short_description`,`ticket_earliest_view`.`reported_by_user_id` AS `reported_by_user_id`,`ticket_earliest_view`.`ticket_type_id` AS `ticket_type_id`,`ticket_earliest_view`.`application_id` AS `application_id`,`ticket_earliest_view`.`severity_id` AS `severity_id`,`ticket_earliest_view`.`workflow_id` AS `workflow_id`,`ticket_earliest_view`.`workflow_status_id` AS `workflow_status_id`,`workflow_structure`.`queue_id` AS `queue_id`,`ticket_earliest_view`.`requestor_user_id` AS `requestor_user_id`,`ticket_earliest_view`.`stamp` AS `stamp` from (`workflow_structure` join `ticket_earliest_view` on(((`workflow_structure`.`workflow_id` = `ticket_earliest_view`.`workflow_id`) and (`workflow_structure`.`from_workflow_status_id` = `ticket_earliest_view`.`workflow_status_id`))));

-- --------------------------------------------------------

--
-- Structure for view `ticket_complete_latest_view`
--
DROP TABLE IF EXISTS `ticket_complete_latest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_complete_latest_view` AS select `ticket_latest_view`.`ticket_id` AS `ticket_id`,`ticket_latest_view`.`short_description` AS `short_description`,`ticket_latest_view`.`reported_by_user_id` AS `reported_by_user_id`,`ticket_latest_view`.`ticket_type_id` AS `ticket_type_id`,`ticket_latest_view`.`application_id` AS `application_id`,`ticket_latest_view`.`severity_id` AS `severity_id`,`ticket_latest_view`.`workflow_id` AS `workflow_id`,`ticket_latest_view`.`workflow_status_id` AS `workflow_status_id`,`workflow_structure`.`queue_id` AS `queue_id`,`ticket_latest_view`.`requestor_user_id` AS `requestor_user_id`,`ticket_latest_view`.`stamp` AS `stamp` from (`workflow_structure` join `ticket_latest_view` on(((`workflow_structure`.`workflow_id` = `ticket_latest_view`.`workflow_id`) and (`workflow_structure`.`from_workflow_status_id` = `ticket_latest_view`.`workflow_status_id`))));

-- --------------------------------------------------------

--
-- Structure for view `ticket_control_earliest_view`
--
DROP TABLE IF EXISTS `ticket_control_earliest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_control_earliest_view` AS select `ticket_history_earliest_view`.`ticket_id` AS `ticket_id`,`application_control`.`ticket_type_id` AS `ticket_type_id`,`application_control`.`application_id` AS `application_id`,`application_control`.`severity_id` AS `severity_id`,`application_control`.`workflow_id` AS `workflow_id`,`ticket_history_earliest_view`.`workflow_status_id` AS `workflow_status_id`,`ticket_history_earliest_view`.`requestor_user_id` AS `requestor_user_id`,`ticket_history_earliest_view`.`stamp` AS `stamp` from (`application_control` join `ticket_history_earliest_view` on((`application_control`.`application_control_id` = `ticket_history_earliest_view`.`application_control_id`)));

-- --------------------------------------------------------

--
-- Structure for view `ticket_control_latest_view`
--
DROP TABLE IF EXISTS `ticket_control_latest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_control_latest_view` AS select `ticket_history_latest_view`.`ticket_id` AS `ticket_id`,`application_control`.`ticket_type_id` AS `ticket_type_id`,`application_control`.`application_id` AS `application_id`,`application_control`.`severity_id` AS `severity_id`,`application_control`.`workflow_id` AS `workflow_id`,`ticket_history_latest_view`.`workflow_status_id` AS `workflow_status_id`,`ticket_history_latest_view`.`requestor_user_id` AS `requestor_user_id`,`ticket_history_latest_view`.`stamp` AS `stamp` from (`application_control` join `ticket_history_latest_view` on((`application_control`.`application_control_id` = `ticket_history_latest_view`.`application_control_id`)));

-- --------------------------------------------------------

--
-- Structure for view `ticket_earliest_view`
--
DROP TABLE IF EXISTS `ticket_earliest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_earliest_view` AS select `ticket`.`ticket_id` AS `ticket_id`,`ticket`.`short_description` AS `short_description`,`ticket`.`reported_by_user_id` AS `reported_by_user_id`,`ticket_control_earliest_view`.`ticket_type_id` AS `ticket_type_id`,`ticket_control_earliest_view`.`application_id` AS `application_id`,`ticket_control_earliest_view`.`severity_id` AS `severity_id`,`ticket_control_earliest_view`.`workflow_id` AS `workflow_id`,`ticket_control_earliest_view`.`workflow_status_id` AS `workflow_status_id`,`ticket_control_earliest_view`.`requestor_user_id` AS `requestor_user_id`,`ticket_control_earliest_view`.`stamp` AS `stamp` from (`ticket` join `ticket_control_earliest_view` on((`ticket`.`ticket_id` = `ticket_control_earliest_view`.`ticket_id`)));

-- --------------------------------------------------------

--
-- Structure for view `ticket_history_earliest_view`
--
DROP TABLE IF EXISTS `ticket_history_earliest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_history_earliest_view` AS select `ticket_change_log`.`ticket_change_log_id` AS `ticket_change_log_id`,`ticket_change_log`.`ticket_id` AS `ticket_id`,`ticket_change_log`.`application_control_id` AS `application_control_id`,`ticket_change_log`.`workflow_status_id` AS `workflow_status_id`,`ticket_change_log`.`requestor_user_id` AS `requestor_user_id`,`ticket_change_log`.`stamp` AS `stamp` from `ticket_change_log` where (`ticket_change_log`.`ticket_id`,`ticket_change_log`.`stamp`) in (select `ticket_change_log`.`ticket_id`,min(`ticket_change_log`.`stamp`) from `ticket_change_log` group by `ticket_change_log`.`ticket_id`) order by `ticket_change_log`.`ticket_id`,`ticket_change_log`.`stamp`;

-- --------------------------------------------------------

--
-- Structure for view `ticket_history_latest_view`
--
DROP TABLE IF EXISTS `ticket_history_latest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_history_latest_view` AS select `ticket_change_log`.`ticket_change_log_id` AS `ticket_change_log_id`,`ticket_change_log`.`ticket_id` AS `ticket_id`,`ticket_change_log`.`application_control_id` AS `application_control_id`,`ticket_change_log`.`workflow_status_id` AS `workflow_status_id`,`ticket_change_log`.`requestor_user_id` AS `requestor_user_id`,`ticket_change_log`.`stamp` AS `stamp` from `ticket_change_log` where (`ticket_change_log`.`ticket_id`,`ticket_change_log`.`stamp`) in (select `ticket_change_log`.`ticket_id`,max(`ticket_change_log`.`stamp`) from `ticket_change_log` group by `ticket_change_log`.`ticket_id`) order by `ticket_change_log`.`ticket_id`,`ticket_change_log`.`stamp`;

-- --------------------------------------------------------

--
-- Structure for view `ticket_latest_view`
--
DROP TABLE IF EXISTS `ticket_latest_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ticket_latest_view` AS select `ticket`.`ticket_id` AS `ticket_id`,`ticket`.`short_description` AS `short_description`,`ticket`.`reported_by_user_id` AS `reported_by_user_id`,`ticket_control_latest_view`.`ticket_type_id` AS `ticket_type_id`,`ticket_control_latest_view`.`application_id` AS `application_id`,`ticket_control_latest_view`.`severity_id` AS `severity_id`,`ticket_control_latest_view`.`workflow_id` AS `workflow_id`,`ticket_control_latest_view`.`workflow_status_id` AS `workflow_status_id`,`ticket_control_latest_view`.`requestor_user_id` AS `requestor_user_id`,`ticket_control_latest_view`.`stamp` AS `stamp` from (`ticket` join `ticket_control_latest_view` on((`ticket`.`ticket_id` = `ticket_control_latest_view`.`ticket_id`)));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `application_change_log`
--
ALTER TABLE `application_change_log`
  ADD CONSTRAINT `appchglog_app_fk` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appchglog_ticket_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appchglog_user_fk` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `application_control`
--
ALTER TABLE `application_control`
  ADD CONSTRAINT `appcontrol_app_fk` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appcontrol_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appcontrol_sev_fk` FOREIGN KEY (`severity_id`) REFERENCES `severity` (`severity_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appcontrol_tickettype_fk` FOREIGN KEY (`ticket_type_id`) REFERENCES `ticket_type` (`ticket_type_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appcontrol_wkflow_fk` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`workflow_id`) ON DELETE CASCADE;

--
-- Constraints for table `application_control_change_log`
--
ALTER TABLE `application_control_change_log`
  ADD CONSTRAINT `appcontrolchglog_appcontrol_fk` FOREIGN KEY (`application_control_id`) REFERENCES `application_control` (`application_control_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appcontrolchglog_ticket_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appcontrolchglog_user_fk` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `application_control_lock_request`
--
ALTER TABLE `application_control_lock_request`
  ADD CONSTRAINT `appctrlock_appctr_appctrid` FOREIGN KEY (`application_control_id`) REFERENCES `application_control` (`application_control_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appctrlock_user_userid` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `application_lock_request`
--
ALTER TABLE `application_lock_request`
  ADD CONSTRAINT `applock_app_appid` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `applock_user_userid` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `file_upload`
--
ALTER TABLE `file_upload`
  ADD CONSTRAINT `fileupload_ticket_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fileupload_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `queue_change_log`
--
ALTER TABLE `queue_change_log`
  ADD CONSTRAINT `queuechglog_queue_fk` FOREIGN KEY (`queue_id`) REFERENCES `queue` (`queue_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `queuechglog_ticket_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `queuechglog_user_fk` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `queue_lock_request`
--
ALTER TABLE `queue_lock_request`
  ADD CONSTRAINT `queuelock_queue_queueid` FOREIGN KEY (`queue_id`) REFERENCES `queue` (`queue_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `queuelock_user_userid` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `role_change_log`
--
ALTER TABLE `role_change_log`
  ADD CONSTRAINT `rolechglog_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rolechglog_ticket_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rolechglog_user_fk` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `severity_change_log`
--
ALTER TABLE `severity_change_log`
  ADD CONSTRAINT `sevchglog_sev_fk` FOREIGN KEY (`severity_id`) REFERENCES `severity` (`severity_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `sevchglog_ticket_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `sevchglog_user_fk` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `ticket_change_log`
--
ALTER TABLE `ticket_change_log`
  ADD CONSTRAINT `ticketchangelog_ac_fk` FOREIGN KEY (`application_control_id`) REFERENCES `application_control` (`application_control_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ticketchangelog_t_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ticketchangelog_u_fk` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ticketchangelog_wsta_fk` FOREIGN KEY (`workflow_status_id`) REFERENCES `workflow_status` (`workflow_status_id`) ON DELETE CASCADE;

--
-- Constraints for table `ticket_lock_request`
--
ALTER TABLE `ticket_lock_request`
  ADD CONSTRAINT `ticketlock_ticket_ticketid` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ticketlock_user_userid` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `ticket_log`
--
ALTER TABLE `ticket_log`
  ADD CONSTRAINT `ticketlog_ticketid_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ticketlog_user_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `user_change_log`
--
ALTER TABLE `user_change_log`
  ADD CONSTRAINT `ucl_user_foreign_key` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `user_queue_con`
--
ALTER TABLE `user_queue_con`
  ADD CONSTRAINT `uqc_queue_queueid` FOREIGN KEY (`queue_id`) REFERENCES `queue` (`queue_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uqc_user_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `user_queue_con_change_log`
--
ALTER TABLE `user_queue_con_change_log`
  ADD CONSTRAINT `uqccl_t_fk` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uqccl_uqc_fk` FOREIGN KEY (`user_queue_con_id`) REFERENCES `user_queue_con` (`user_queue_con_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uqccl_u_fk` FOREIGN KEY (`requestor_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `user_role_con`
--
ALTER TABLE `user_role_con`
  ADD CONSTRAINT `role_foreign_key` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_foreign_key` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `user_role_con_change_log`
--
ALTER TABLE `user_role_con_change_log`
  ADD CONSTRAINT `user_role_con_foreign_key` FOREIGN KEY (`user_role_con_id`) REFERENCES `user_role_con` (`user_role_con_id`) ON DELETE CASCADE;

--
-- Constraints for table `workflow_structure`
--
ALTER TABLE `workflow_structure`
  ADD CONSTRAINT `wstr_q_fk` FOREIGN KEY (`queue_id`) REFERENCES `queue` (`queue_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `wstr_r_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `wstr_ws1_fk` FOREIGN KEY (`from_workflow_status_id`) REFERENCES `workflow_status` (`workflow_status_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `wstr_ws2_fk` FOREIGN KEY (`to_workflow_status_id`) REFERENCES `workflow_status` (`workflow_status_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `wstr_w_fk` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`workflow_id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

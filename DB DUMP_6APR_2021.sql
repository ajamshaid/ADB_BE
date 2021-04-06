-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.32-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for adc
DROP DATABASE IF EXISTS `adc`;
CREATE DATABASE IF NOT EXISTS `adc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `adc`;

-- Dumping structure for table adc.account_detail
DROP TABLE IF EXISTS `account_detail`;
CREATE TABLE IF NOT EXISTS `account_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NULL DEFAULT NULL,
  `acct_number` varchar(255) DEFAULT NULL,
  `acct_status` varchar(255) DEFAULT NULL,
  `acct_title` varchar(255) DEFAULT NULL,
  `acct_type` varchar(255) DEFAULT NULL,
  `cnic` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `iban` varchar(255) NOT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `ntn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_jcnbygge8ecbc0ka7v5c0xwvh` (`iban`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table adc.account_detail: ~3 rows (approximately)
/*!40000 ALTER TABLE `account_detail` DISABLE KEYS */;
REPLACE INTO `account_detail` (`id`, `created_on`, `updated_on`, `acct_number`, `acct_status`, `acct_title`, `acct_type`, `cnic`, `email`, `iban`, `mobile_number`, `ntn`) VALUES
	(1, '2021-03-01 11:16:04', '2021-03-01 11:16:04', '1020499502135', '600', 'Dawood Hercules', '701', '4210134567890', 'abc@psw.gov.pk', 'PK36SCBL0010001123456005', '03451234567', '0425425'),
	(2, '2021-03-01 11:16:04', '2021-03-01 11:16:04', 'act 123', '600', 'JS Bank', '701', 'cnic 1234', '123@psw.pk', 'PK123', '0300 123', 'ntn 123'),
	(3, '2021-03-01 11:16:04', '2021-03-01 11:16:04', 'act 333', '600', 'Slik Bank', '701', 'cnic 333', '333@psw.pk', 'PK333', '0300 333', 'ntn 333'),
	(4, '2021-03-01 11:16:04', '2021-03-01 11:16:04', 'act 444', '600', 'Sonari Bank', '701', 'cnic 444', '444@psw.pk', 'PK444', '0300 444', 'ntn 444');
/*!40000 ALTER TABLE `account_detail` ENABLE KEYS */;

-- Dumping structure for table adc.authorized_payment_modes
DROP TABLE IF EXISTS `authorized_payment_modes`;
CREATE TABLE IF NOT EXISTS `authorized_payment_modes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2rj5o7jkyogn8beno2c1vy5x5` (`account_id`),
  CONSTRAINT `FK2rj5o7jkyogn8beno2c1vy5x5` FOREIGN KEY (`account_id`) REFERENCES `account_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table adc.authorized_payment_modes: ~9 rows (approximately)
/*!40000 ALTER TABLE `authorized_payment_modes` DISABLE KEYS */;
REPLACE INTO `authorized_payment_modes` (`id`, `code`, `type`, `account_id`) VALUES
	(1, '305', 'EXPORT', 1),
	(2, '306', 'EXPORT', 1),
	(3, '307', 'EXPORT', 1),
	(4, '301', 'IMPORT', 1),
	(5, '302', 'IMPORT', 1),
	(6, '303', 'IMPORT', 1),
	(7, '301', 'IMPORT', 2),
	(8, '302', 'IMPORT', 2),
	(9, '305', 'EXPORT', 2);
/*!40000 ALTER TABLE `authorized_payment_modes` ENABLE KEYS */;

-- Dumping structure for table adc.change_bank
DROP TABLE IF EXISTS `change_bank`;
CREATE TABLE IF NOT EXISTS `change_bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NULL DEFAULT NULL,
  `exporter_payment_mode` varchar(255) DEFAULT NULL,
  `exporter_ntn` varchar(255) DEFAULT NULL,
  `exporter_name` varchar(255) DEFAULT NULL,
  `payment_identification_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_o1g231dltg1i70ks97twfm13a` (`exporter_ntn`) USING BTREE,
  UNIQUE KEY `UK_jrckn9rjkbuow2ap76q7ho8dq` (`payment_identification_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table adc.change_bank: ~0 rows (approximately)
/*!40000 ALTER TABLE `change_bank` DISABLE KEYS */;
REPLACE INTO `change_bank` (`id`, `created_on`, `updated_on`, `exporter_payment_mode`, `exporter_ntn`, `exporter_name`, `payment_identification_no`) VALUES
	(1, '2021-03-08 15:42:42', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `change_bank` ENABLE KEYS */;

-- Dumping structure for table adc.country
DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table adc.country: ~8 rows (approximately)
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
REPLACE INTO `country` (`id`, `code`, `name`) VALUES
	(1, 'AUS', 'Australia'),
	(2, 'COL', 'Colombia'),
	(3, 'EGY', 'Egypt'),
	(4, 'IND', 'India'),
	(5, 'IRL', 'Ireland'),
	(6, 'ISR', 'Israel'),
	(7, 'MEX', 'Mexico'),
	(8, 'NPL', 'Nepal');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;

-- Dumping structure for table adc.gd_financial
DROP TABLE IF EXISTS `gd_financial`;
CREATE TABLE IF NOT EXISTS `gd_financial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NULL DEFAULT NULL,
  `gd_number` varchar(255) DEFAULT NULL,
  `gd_type` varchar(255) DEFAULT NULL,
  `gd_object` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_g2uoe04th02i8crxtagr0d2sc` (`gd_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table adc.gd_financial: ~0 rows (approximately)
/*!40000 ALTER TABLE `gd_financial` DISABLE KEYS */;
REPLACE INTO `gd_financial` (`id`, `created_on`, `updated_on`, `gd_number`, `gd_type`, `gd_object`) VALUES
	(20, '2021-03-09 10:26:36', NULL, 'IMPORT-HC-86-03-09-2020', 'IMPORT', '{\r\n  "gdNumber" : "IMPORT-HC-86-03-09-2020",\r\n  "blawbDate" : "20201012",\r\n  "consignerConsigneeInfo" : {\r\n    "ntnFtn" : "1425425",\r\n    "strn" : "1700003019489",\r\n    "consigneeName" : "PSW",\r\n    "consigneeAddress" : "PECHS",\r\n    "consignorName" : "M/S. International Jute Traders",\r\n    "consignorAddress" : "95, MOTIJHEEL COMMERCIAL AREA (2ND FLOOR) BANGLADESH."\r\n  },\r\n  "virairNumber" : "KEWB-0005-010112020",\r\n  "consignmentCategory" : "Commercial",\r\n  "financialInfo" : {\r\n    "beneficiaryName" : "Ahmed",\r\n    "beneficiaryAddress" : "122 M Amir trade center, Allama Iqbal Road، Karachi.",\r\n    "beneficiaryCountry" : "PAK",\r\n    "beneficiaryIban" : "PK36BAHL0010001123456005",\r\n    "exporterName" : "Alex George",\r\n    "exporterAddress" : "Jabel Omar, Building No. 244, 26th Floor, Abu Dhabi, UAE.",\r\n    "exporterCountry" : "DXB",\r\n    "modeOfPayment" : "Contract/Collection",\r\n    "contractData" : {\r\n      "advPayPercentage" : 50,\r\n      "docAgainstPayPercentage" : 25,\r\n      "docAgainstAcceptancePercentage" : 25,\r\n      "days" : 10,\r\n      "totalPercentage" : 100\r\n    },\r\n    "intendedPayDate" : "20200924",\r\n    "transportDocDate" : "20201012",\r\n    "invoiceCurrency" : "USD",\r\n    "invoiceNumber" : "AS1234567",\r\n    "invoiceDate" : "20200223",\r\n    "invoiceValue" : 10000,\r\n    "deliveryTerm" : "CFR",\r\n    "paymentTerms" : "Collection",\r\n    "fobValue" : 100,\r\n    "freight" : 1,\r\n    "cfrValue" : 1,\r\n    "insurance" : 0,\r\n    "landingCharges" : 100,\r\n    "assessedValue" : 50,\r\n    "OtherCharges" : 0,\r\n    "exchangeRate" : 158\r\n  },\r\n  "blawbNumber" : "BL-24923231",\r\n  "itemInformation" : {\r\n    "hsCode" : "8446.1000",\r\n    "quantity" : 6,\r\n    "unitPrice" : 20,\r\n    "totalValue" : 120,\r\n    "importValue" : 120,\r\n    "importValueInvoice" : 1000\r\n  },\r\n  "gdType" : "Home Consumption",\r\n  "generalInformation" : {\r\n    "numberOfPackages" : "100.0000",\r\n    "packageType" : "Packages",\r\n    "netWeight" : "1.89400 MT",\r\n    "grossWeight" : "1.11400 MT",\r\n    "containerOrTruckNumber" : "ASF9999991",\r\n    "sealNumber" : "SL2674",\r\n    "containerType" : "20FT",\r\n    "portOfShipment" : "CHN",\r\n    "portOfDelivery" : "PKBQM",\r\n    "portOfDischarge" : "AEDXB",\r\n    "terminalLocation" : "Qasim International Container Terminal"\r\n  },\r\n  "collectorate" : "Qasim International Container Terminal"\r\n}');
/*!40000 ALTER TABLE `gd_financial` ENABLE KEYS */;

-- Dumping structure for table adc.log_request
DROP TABLE IF EXISTS `log_request`;
CREATE TABLE IF NOT EXISTS `log_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NULL DEFAULT NULL,
  `msg_identifier` varchar(255) NOT NULL,
  `receiver_id` varchar(255) NOT NULL,
  `request_method` varchar(255) NOT NULL,
  `request_payload` text,
  `request_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `response_code` int(11) NOT NULL,
  `response_message` varchar(255) NOT NULL,
  `response_payload` text,
  `response_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `sender_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table adc.log_request: ~22 rows (approximately)
/*!40000 ALTER TABLE `log_request` DISABLE KEYS */;
REPLACE INTO `log_request` (`id`, `created_on`, `updated_on`, `msg_identifier`, `receiver_id`, `request_method`, `request_payload`, `request_time`, `response_code`, `response_message`, `response_payload`, `response_time`, `sender_id`) VALUES
	(1, '2021-04-03 11:55:13', NULL, 'Sharing of Update Information and Payment Mode By AD', 'PSW', 'POST', '{\r\n  "messageId" : "940b2c1c-92b6-11eb-a8b3-0242ac130003",\r\n  "timestamp" : 1617432912016,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1512",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK 123213123",\r\n    "accountTitle" : null,\r\n    "accountNumber" : null,\r\n    "accountStatus" : null,\r\n    "accountType" : "701",\r\n    "ntn" : null,\r\n    "cnic" : null,\r\n    "authorizedPaymentModesForImport" : [ ],\r\n    "authorizedPaymentModesForExports" : [ ]\r\n  }\r\n}', '2021-04-03 11:55:12', 200, 'Updated authorized payment modes', '{\r\n  "messageId" : "940b2c1c-92b6-11eb-a8b3-0242ac130003",\r\n  "timestamp" : 1617432912016,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated authorized payment modes"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1512"\r\n}', '2021-04-03 11:55:13', 'SLKB'),
	(2, '2021-04-03 12:19:05', NULL, 'Verify Trader Profile From AD', 'SCBL', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "PSW",\r\n  "receiverId" : "SCBL",\r\n  "processingCode" : "03",\r\n  "methodId" : null,\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "data" : {\r\n    "ntn" : "ntn 123",\r\n    "iban" : "PK123",\r\n    "email" : "123@psw.pk",\r\n    "mobileNumber" : "0300 123",\r\n    "gdNumber" : null\r\n  }\r\n}', '2021-04-03 12:19:05', 207, 'IBAN verification successful.', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "responseCode" : "207",\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "message" : {\r\n    "code" : "207",\r\n    "description" : "IBAN verification successful."\r\n  },\r\n  "data" : ""\r\n}', '2021-04-03 12:19:05', 'PSW'),
	(3, '2021-04-03 12:19:08', NULL, 'Sharing of Account Details & Authorized Payment Modes', 'SCBL', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "PSW",\r\n  "receiverId" : "SCBL",\r\n  "processingCode" : "03",\r\n  "methodId" : null,\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "data" : {\r\n    "ntn" : null,\r\n    "iban" : "PK123",\r\n    "email" : null,\r\n    "mobileNumber" : null,\r\n    "gdNumber" : null\r\n  }\r\n}', '2021-04-03 12:19:08', 200, 'Account details and authorized payment modes shared.', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "responseCode" : "200",\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Account details and authorized payment modes shared."\r\n  },\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "accountTitle" : "JS Bank",\r\n    "accountNumber" : "act 123",\r\n    "accountStatus" : "600",\r\n    "accountType" : "701",\r\n    "ntn" : "ntn 123",\r\n    "cnic" : "cnic 1234",\r\n    "authorizedPaymentModesForImport" : [ "301", "302" ],\r\n    "authorizedPaymentModesForExports" : [ "305" ]\r\n  }\r\n}', '2021-04-03 12:19:08', 'PSW'),
	(4, '2021-04-03 12:19:10', NULL, 'Sharing Negative List of Countries', 'SCBL', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "PSW",\r\n  "receiverId" : "SCBL",\r\n  "processingCode" : "03",\r\n  "methodId" : null,\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "data" : {\r\n    "ntn" : null,\r\n    "iban" : "PK123",\r\n    "email" : null,\r\n    "mobileNumber" : null,\r\n    "gdNumber" : null\r\n  }\r\n}', '2021-04-03 12:19:10', 200, 'Negative list of countries shared.', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "responseCode" : "200",\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Negative list of countries shared."\r\n  },\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "restrictedCountriesForImport" : [ ],\r\n    "restrictedCountriesForExport" : [ "IND" ]\r\n  }\r\n}', '2021-04-03 12:19:10', 'PSW'),
	(5, '2021-04-03 12:19:13', NULL, 'Sharing Negative List of Countries', 'SCBL', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "PSW",\r\n  "receiverId" : "SCBL",\r\n  "processingCode" : "03",\r\n  "methodId" : null,\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "data" : {\r\n    "ntn" : null,\r\n    "iban" : "PK123",\r\n    "email" : null,\r\n    "mobileNumber" : null,\r\n    "gdNumber" : null\r\n  }\r\n}', '2021-04-03 12:19:13', 200, 'Negative list of commodities shared.', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "responseCode" : "200",\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Negative list of commodities shared."\r\n  },\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "restrictedCommoditiesForImport" : [ "4814.2000", "6006.3110", "7406.1000" ],\r\n    "restrictedCommoditiesForExport" : [ "8305.1000", "9021.3100" ]\r\n  }\r\n}', '2021-04-03 12:19:13', 'PSW'),
	(6, '2021-04-03 12:19:15', NULL, 'Sharing Negative List of Countries', 'SCBL', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "PSW",\r\n  "receiverId" : "SCBL",\r\n  "processingCode" : "03",\r\n  "methodId" : null,\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "data" : {\r\n    "ntn" : null,\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "email" : null,\r\n    "mobileNumber" : null,\r\n    "gdNumber" : null\r\n  }\r\n}', '2021-04-03 12:19:15', 200, 'Negative list of suppliers shared.', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "responseCode" : "200",\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Negative list of suppliers shared."\r\n  },\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedSuppliersForImport" : [ "Magnas Group of Companies" ],\r\n    "restrictedSuppliersForExport" : [ "Ali Corporations Ltd" ]\r\n  }\r\n}', '2021-04-03 12:19:15', 'PSW'),
	(7, '2021-04-03 13:10:16', NULL, 'Sharing of Update Information and Payment Mode By AD', 'PSW', 'POST', '{\r\n  "messageId" : "940b2c1c-92b6-11eb-a8b3-0242ac130003",\r\n  "timestamp" : 1617437120475,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1512",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK 123213123",\r\n    "accountTitle" : null,\r\n    "accountNumber" : null,\r\n    "accountStatus" : null,\r\n    "accountType" : "701",\r\n    "ntn" : null,\r\n    "cnic" : null,\r\n    "authorizedPaymentModesForImport" : [ ],\r\n    "authorizedPaymentModesForExports" : [ ]\r\n  }\r\n}', '2021-04-03 13:05:46', 200, 'Updated authorized payment modes', '{\r\n  "messageId" : "940b2c1c-92b6-11eb-a8b3-0242ac130003",\r\n  "timestamp" : 1617437120475,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated authorized payment modes"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1512"\r\n}', '2021-04-03 13:10:16', 'SLKB'),
	(8, '2021-04-03 13:12:59', NULL, 'Sharing of Update Information and Payment Mode By AD', 'PSW', 'POST', '{\r\n  "messageId" : "940b2c1c-92b6-11eb-a8b3-0242ac130003",\r\n  "timestamp" : 1617437578655,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1512",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK 123213123",\r\n    "accountTitle" : null,\r\n    "accountNumber" : null,\r\n    "accountStatus" : "600",\r\n    "accountType" : "701",\r\n    "ntn" : null,\r\n    "cnic" : null,\r\n    "authorizedPaymentModesForImport" : [ "305", "306" ],\r\n    "authorizedPaymentModesForExports" : [ "301", "307" ]\r\n  }\r\n}', '2021-04-03 13:12:59', 200, 'Updated authorized payment modes', '{\r\n  "messageId" : "940b2c1c-92b6-11eb-a8b3-0242ac130003",\r\n  "timestamp" : 1617437578655,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated authorized payment modes"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1512"\r\n}', '2021-04-03 13:12:59', 'SLKB'),
	(9, '2021-04-03 13:22:31', NULL, 'Sharing of Update Information and Payment Mode By AD', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617438150462,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1513",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedCountriesForImport" : [ "COL", "EGY", "AUS" ],\r\n    "restrictedCountriesForExport" : [ "AUS" ]\r\n  }\r\n}', '2021-04-03 13:22:31', 200, 'Updated negative list of countries', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617438150462,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of countries"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1513"\r\n}', '2021-04-03 13:22:31', 'SLKB'),
	(10, '2021-04-03 13:24:38', NULL, 'Sharing Updated Negative List of Countries with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617438277359,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1513",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedCountriesForImport" : [ "COL", "EGY", "AUS" ],\r\n    "restrictedCountriesForExport" : [ "AUS" ]\r\n  }\r\n}', '2021-04-03 13:24:38', 200, 'Updated negative list of countries', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617438277359,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of countries"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1513"\r\n}', '2021-04-03 13:24:38', 'SLKB'),
	(11, '2021-04-04 12:25:40', NULL, 'Sharing Updated Negative List of Commodities with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617521123404,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1514",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedCommoditiesForImport" : [ ],\r\n    "restrictedCommoditiesForExport" : [ ]\r\n  }\r\n}', '2021-04-04 12:25:32', 200, 'Updated negative list of commodities', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617521123404,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of commodities"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1514"\r\n}', '2021-04-04 12:25:40', 'SLKB'),
	(12, '2021-04-04 12:26:46', NULL, 'Sharing Updated Negative List of Suppliers with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617521205802,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1515",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedSuppliersForImport" : [ "Magnas Group of Companies" ],\r\n    "restrictedSuppliersForExport" : [ "Ali Corporations Ltd" ]\r\n  }\r\n}', '2021-04-04 12:26:46', 200, 'Updated negative list of suppliers', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617521205802,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of suppliers"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1515"\r\n}', '2021-04-04 12:26:46', 'SLKB'),
	(13, '2021-04-04 12:50:07', NULL, 'Sharing Updated Negative List of Countries with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617522605707,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1513",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : null,\r\n    "restrictedCountriesForImport" : null,\r\n    "restrictedCountriesForExport" : null\r\n  }\r\n}', '2021-04-04 12:50:06', 200, 'Updated negative list of countries', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617522605707,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of countries"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1513"\r\n}', '2021-04-04 12:50:07', 'SLKB'),
	(14, '2021-04-04 12:55:14', NULL, 'Sharing Updated Negative List of Countries with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617522911772,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1513",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "restrictedCountriesForImport" : [ ],\r\n    "restrictedCountriesForExport" : [ "IND" ]\r\n  }\r\n}', '2021-04-04 12:55:14', 200, 'Updated negative list of countries', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617522911772,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of countries"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1513"\r\n}', '2021-04-04 12:55:14', 'SLKB'),
	(15, '2021-04-04 12:58:48', NULL, 'Sharing Updated Negative List of Countries with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523127676,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1513",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "restrictedCountriesForImport" : [ ],\r\n    "restrictedCountriesForExport" : [ "IND" ]\r\n  }\r\n}', '2021-04-04 12:58:48', 200, 'Updated negative list of countries', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523127676,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of countries"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1513"\r\n}', '2021-04-04 12:58:48', 'SLKB'),
	(16, '2021-04-04 12:59:01', NULL, 'Sharing Updated Negative List of Commodities with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523140883,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1514",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "restrictedCommoditiesForImport" : [ "4814.2000", "6006.3110", "7406.1000" ],\r\n    "restrictedCommoditiesForExport" : [ "8305.1000", "9021.3100" ]\r\n  }\r\n}', '2021-04-04 12:59:01', 200, 'Updated negative list of commodities', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523140883,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of commodities"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1514"\r\n}', '2021-04-04 12:59:01', 'SLKB'),
	(17, '2021-04-04 12:59:08', NULL, 'Sharing Updated Negative List of Suppliers with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523147482,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1515",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "restrictedSuppliersForImport" : [ "Jonas limited liability corporation (LLC)", "Ali Corporations Ltd" ],\r\n    "restrictedSuppliersForExport" : [ "Magnas Group of Companies", "Global Pharma Pvt. Ltd." ]\r\n  }\r\n}', '2021-04-04 12:59:08', 200, 'Updated negative list of suppliers', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523147482,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of suppliers"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1515"\r\n}', '2021-04-04 12:59:08', 'SLKB'),
	(18, '2021-04-04 12:59:50', NULL, 'Sharing Updated Negative List of Suppliers with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523190278,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1515",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedSuppliersForImport" : [ "Magnas Group of Companies" ],\r\n    "restrictedSuppliersForExport" : [ "Ali Corporations Ltd" ]\r\n  }\r\n}', '2021-04-04 12:59:50', 200, 'Updated negative list of suppliers', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523190278,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of suppliers"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1515"\r\n}', '2021-04-04 12:59:50', 'SLKB'),
	(19, '2021-04-04 13:01:00', NULL, 'Sharing Updated Negative List of Countries with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523260150,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1513",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedCountriesForImport" : [ "COL", "EGY", "AUS" ],\r\n    "restrictedCountriesForExport" : [ "AUS" ]\r\n  }\r\n}', '2021-04-04 13:01:00', 200, 'Updated negative list of countries', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617523260150,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of countries"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1513"\r\n}', '2021-04-04 13:01:00', 'SLKB'),
	(20, '2021-04-04 14:23:07', NULL, 'Trader Profile Active/Inactive Message by AD to PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617528186338,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1516",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "accountStatus" : "600"\r\n  }\r\n}', '2021-04-04 14:23:07', 200, 'ALL Good ', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617528186338,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "ALL Good "\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1516"\r\n}', '2021-04-04 14:23:07', 'SLKB'),
	(21, '2021-04-04 14:46:08', NULL, 'Update in Trader’s Email and Mobile Number Message by AD to PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617529566879,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1534",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "ntn" : "0425425",\r\n    "traderName" : "Schlumberger Seaco Inc.",\r\n    "iban" : "PK123",\r\n    "email" : "hibaowais32@gmail.com",\r\n    "mobileNumber" : "00923427765432"\r\n  }\r\n}', '2021-04-04 14:46:08', 200, 'Updated Trader Email and MObile number', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617529566879,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated Trader Email and MObile number"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1534"\r\n}', '2021-04-04 14:46:08', 'SLKB'),
	(22, '2021-04-04 14:47:25', NULL, 'Trader Profile Active/Inactive Message by AD to PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617529644749,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1516",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "accountStatus" : "600"\r\n  }\r\n}', '2021-04-04 14:47:25', 200, 'Updated Account Status', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617529644749,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated Account Status"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1516"\r\n}', '2021-04-04 14:47:25', 'SLKB'),
	(23, '2021-04-06 09:58:08', NULL, 'Verify Trader Profile From AD', 'SCBL', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "PSW",\r\n  "receiverId" : "SCBL",\r\n  "processingCode" : "03",\r\n  "methodId" : null,\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "data" : {\r\n    "ntn" : "ntn 123",\r\n    "iban" : "PK123",\r\n    "email" : "123@psw.pk",\r\n    "mobileNumber" : "0300 123",\r\n    "gdNumber" : null\r\n  }\r\n}', '2021-04-06 09:58:08', 207, 'IBAN verification successful.', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-989521cd1ca8",\r\n  "timestamp" : 20200925183412,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "responseCode" : "207",\r\n  "signature" : "82045ede93efbbcbea55da67c6655e9b",\r\n  "message" : {\r\n    "code" : "207",\r\n    "description" : "IBAN verification successful."\r\n  },\r\n  "data" : ""\r\n}', '2021-04-06 09:58:08', 'PSW'),
	(24, '2021-04-06 09:58:12', NULL, 'Sharing Updated Negative List of Countries with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617685091855,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1513",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK36SCBL0010001123456005",\r\n    "restrictedCountriesForImport" : [ "COL", "EGY", "AUS" ],\r\n    "restrictedCountriesForExport" : [ "AUS" ]\r\n  }\r\n}', '2021-04-06 09:58:12', 200, 'Updated negative list of countries', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617685091855,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of countries"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1513"\r\n}', '2021-04-06 09:58:12', 'SLKB'),
	(25, '2021-04-06 09:58:15', NULL, 'Sharing Updated Negative List of Commodities with PSW', 'PSW', 'POST', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617685095014,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "PSW",\r\n  "processingCode" : "03",\r\n  "methodId" : "1514",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "data" : {\r\n    "iban" : "PK123",\r\n    "restrictedCommoditiesForImport" : [ "4814.2000", "6006.3110", "7406.1000" ],\r\n    "restrictedCommoditiesForExport" : [ "8305.1000", "9021.3100" ]\r\n  }\r\n}', '2021-04-06 09:58:15', 200, 'Updated negative list of commodities', '{\r\n  "messageId" : "a1374655-5eb8-4a0e-9eb5-0989521cd1ca",\r\n  "timestamp" : 1617685095014,\r\n  "senderId" : "SLKB",\r\n  "receiverId" : "SLKB",\r\n  "responseCode" : "200",\r\n  "signature" : "SLKB_SIGNATURE_ABCBC",\r\n  "message" : {\r\n    "code" : "200",\r\n    "description" : "Updated negative list of commodities"\r\n  },\r\n  "data" : "",\r\n  "methodId" : "1514"\r\n}', '2021-04-06 09:58:15', 'SLKB');
/*!40000 ALTER TABLE `log_request` ENABLE KEYS */;

-- Dumping structure for table adc.pay_mode
DROP TABLE IF EXISTS `pay_mode`;
CREATE TABLE IF NOT EXISTS `pay_mode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table adc.pay_mode: ~0 rows (approximately)
/*!40000 ALTER TABLE `pay_mode` DISABLE KEYS */;
/*!40000 ALTER TABLE `pay_mode` ENABLE KEYS */;

-- Dumping structure for table adc.restricted_commodities
DROP TABLE IF EXISTS `restricted_commodities`;
CREATE TABLE IF NOT EXISTS `restricted_commodities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(14) NOT NULL,
  `type` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKje9r5efu8cbb2p2mw7fpfp1qp` (`account_id`),
  CONSTRAINT `FKje9r5efu8cbb2p2mw7fpfp1qp` FOREIGN KEY (`account_id`) REFERENCES `account_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table adc.restricted_commodities: ~5 rows (approximately)
/*!40000 ALTER TABLE `restricted_commodities` DISABLE KEYS */;
REPLACE INTO `restricted_commodities` (`id`, `code`, `type`, `account_id`) VALUES
	(1, '4814.2000', 'IMPORT', 2),
	(2, '6006.3110', 'IMPORT', 2),
	(3, '7406.1000', 'IMPORT', 2),
	(4, '8305.1000', 'EXPORT', 2),
	(5, '9021.3100', 'EXPORT', 2);
/*!40000 ALTER TABLE `restricted_commodities` ENABLE KEYS */;

-- Dumping structure for table adc.restricted_countries
DROP TABLE IF EXISTS `restricted_countries`;
CREATE TABLE IF NOT EXISTS `restricted_countries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `country_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpjhbf22kix3bwk2bs8832t31o` (`account_id`),
  KEY `FK1amxwry0747kgo2jio2dvb5ut` (`country_id`),
  CONSTRAINT `FK1amxwry0747kgo2jio2dvb5ut` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FKpjhbf22kix3bwk2bs8832t31o` FOREIGN KEY (`account_id`) REFERENCES `account_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table adc.restricted_countries: ~4 rows (approximately)
/*!40000 ALTER TABLE `restricted_countries` DISABLE KEYS */;
REPLACE INTO `restricted_countries` (`id`, `type`, `account_id`, `country_id`) VALUES
	(1, 'EXPORT', 1, 1),
	(2, 'IMPORT', 1, 1),
	(3, 'IMPORT', 1, 2),
	(4, 'IMPORT', 1, 3),
	(5, 'EXPORT', 2, 4);
/*!40000 ALTER TABLE `restricted_countries` ENABLE KEYS */;

-- Dumping structure for table adc.restricted_suppliers
DROP TABLE IF EXISTS `restricted_suppliers`;
CREATE TABLE IF NOT EXISTS `restricted_suppliers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn7oh0ic1qajna1dkbsxa4128r` (`account_id`),
  CONSTRAINT `FKn7oh0ic1qajna1dkbsxa4128r` FOREIGN KEY (`account_id`) REFERENCES `account_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table adc.restricted_suppliers: ~6 rows (approximately)
/*!40000 ALTER TABLE `restricted_suppliers` DISABLE KEYS */;
REPLACE INTO `restricted_suppliers` (`id`, `name`, `type`, `account_id`) VALUES
	(1, 'Jonas limited liability corporation (LLC)', 'IMPORT', 2),
	(2, 'Ali Corporations Ltd', 'IMPORT', 2),
	(3, 'Ali Corporations Ltd', 'EXPORT', 1),
	(4, 'Magnas Group of Companies', 'EXPORT', 2),
	(5, 'Global Pharma Pvt. Ltd.', 'EXPORT', 2),
	(6, 'Magnas Group of Companies', 'IMPORT', 1);
/*!40000 ALTER TABLE `restricted_suppliers` ENABLE KEYS */;

-- Dumping structure for table adc.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table adc.user: ~0 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `is_active`, `password`, `username`) VALUES
	(1, b'1', '$2a$10$1sjIBJJ9d2zltVnM.8nU6eXU3RNvAGSJ91nls.xhdAAsCURqVP/aS', 'info@ad.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

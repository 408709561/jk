
CREATE DATABASE ag_bus_test DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
Use ag_bus_test;
/*
 Navicat Premium Data Transfer

 Source Server         : ag-admin
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : ag_bus_test

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 02/11/2018 15:58:23 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `depart_data_test`
-- ----------------------------
DROP TABLE IF EXISTS `depart_data_test`;
CREATE TABLE `depart_data_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `depart_id` varchar(36) DEFAULT NULL,
  `crt_user_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_user_id` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `upd_user_name` varchar(255) DEFAULT NULL COMMENT '最后更新人',
  `upd_user_id` varchar(36) DEFAULT NULL COMMENT '最后更新人ID',
  `upd_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `depart_data_test`
-- ----------------------------
BEGIN;
INSERT INTO `depart_data_test` VALUES ('1', '只有授权了公司数据权限的人才可以看到', '2c3df2292bc34ed0a312d2bc46171883', null, null, null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('2', '只有授权了集团数据权限的人才可以看到', 'root', null, null, null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('3', '这是admin用户创建的数据', '2c3df2292bc34ed0a312d2bc46171883', null, '1', null, null, null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

CREATE DATABASE ag_app DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
Use ag_app;
/*
 Navicat Premium Data Transfer

 Source Server         : ag-admin
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : ag_app

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 05/27/2018 11:04:40 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `app_user`
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `tenant_id` varchar(255) DEFAULT NULL,
  `crt_user_id` varchar(255) DEFAULT NULL,
  `is_deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_mobile` (`mobile`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `app_user`
-- ----------------------------
BEGIN;
INSERT INTO `app_user` VALUES ('1', '13888888888', '张三', 'jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=', 'ac88ceb386aa4231b09bf472cb937c24', null, '0'), ('2', '13888888889', '李四', 'jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=', 'ac88ceb386aa4231b09bf472cb937c24', null, '0'), ('4', '13888888880', 'xxx_13888888880', 'jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=', '1ec08564dcc344018d6aaa910068f0f0', null, '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

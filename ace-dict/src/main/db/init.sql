CREATE DATABASE ag_dict DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
Use ag_dict;
/*
 Navicat Premium Data Transfer

 Source Server         : ag-admin
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : ag_dict

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 02/04/2018 10:05:50 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `type` varchar(1) DEFAULT NULL COMMENT '类型',
  `name` varchar(255) DEFAULT NULL COMMENT '目录名',
  `parent_id` varchar(36) DEFAULT NULL,
  `crt_user_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_user_id` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `upd_user_name` varchar(255) DEFAULT NULL COMMENT '最后更新人',
  `upd_user_id` varchar(36) DEFAULT NULL COMMENT '最后更新人ID',
  `upd_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `dict_type`
-- ----------------------------
BEGIN;
INSERT INTO `dict_type` VALUES ('2e7a6f2dab184e3c891040d7b9b7e7ec', 'authority_element', null, '资源类型', '8ab51d3c861d476eb10600b2ed0f1f0d', '', null, '2018-02-01 12:23:39', '', null, '2018-02-01 12:24:04', null, null, null, null), ('53b7b71f63273f53aa2c7590b446fb33', 'comm', '0', '公共字典', '64fce2915a213504be2b08ad12746af2', null, null, null, null, null, null, null, null, null, null), ('64fce2915a213504be2b08ad12746af2', 'root', '0', '根节点', '-1', null, null, null, null, null, null, null, null, null, null), ('67374d6aebeb4cc28484c7f303ec85f5', 'authority_menu', null, '菜单类型', '8ab51d3c861d476eb10600b2ed0f1f0d', '', null, '2018-02-01 12:23:26', '', null, '2018-02-01 12:23:37', null, null, null, null), ('8ab51d3c861d476eb10600b2ed0f1f0d', 'authority', null, '权限字典', '64fce2915a213504be2b08ad12746af2', '', null, '2018-02-01 12:22:58', '', null, '2018-02-01 12:23:22', null, null, null, null), ('d62b242fde833653bce1498787ea6c31', 'comm_sex', '1', '性别', '53b7b71f63273f53aa2c7590b446fb33', '', null, '2018-01-31 17:36:38', '', null, '2018-01-31 17:36:49', null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `dict_value`
-- ----------------------------
DROP TABLE IF EXISTS `dict_value`;
CREATE TABLE `dict_value` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `value` varchar(255) DEFAULT NULL COMMENT '值',
  `label_default` varchar(255) DEFAULT NULL COMMENT '默认显示',
  `label_en_US` varchar(255) DEFAULT NULL COMMENT '英文显示',
  `label_zh_CH` varchar(255) DEFAULT NULL COMMENT '中文显示',
  `type_id` varchar(36) DEFAULT NULL,
  `label_attr1` varchar(255) DEFAULT NULL COMMENT '值',
  `label_attr2` varchar(255) DEFAULT NULL COMMENT '值',
  `label_attr3` varchar(255) DEFAULT NULL COMMENT '值',
  `crt_user_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_user_id` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `upd_user_name` varchar(255) DEFAULT NULL COMMENT '最后更新人',
  `upd_user_id` varchar(36) DEFAULT NULL COMMENT '最后更新人ID',
  `upd_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `parent_id` varchar(36) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `dict_value`
-- ----------------------------
BEGIN;
INSERT INTO `dict_value` VALUES ('276fc4a924b241d6820209c42e1b2ccc', 'authority_element_button', 'button', '按钮', 'button', 'button', '2e7a6f2dab184e3c891040d7b9b7e7ec', null, null, null, '', null, '2018-02-01 12:26:38', 'Mr.AG', '1', '2018-02-02 19:43:43', null, null, null, null, null), ('4b1112f4d71c492796690062aeedeb25', 'authority_element_uri', 'uri', '资源', 'uri', 'uri', '2e7a6f2dab184e3c891040d7b9b7e7ec', null, null, null, '', null, '2018-02-01 12:26:01', 'Mr.AG', '1', '2018-02-02 19:43:54', null, null, null, null, null), ('657ae6bd192a36729d39446174bcf64c', 'comm_sex_woman', 'woman', '女', 'Woman', '女', 'd62b242fde833653bce1498787ea6c31', null, null, null, null, null, null, '', null, '2018-02-01 12:51:52', null, null, null, null, null), ('8571cabddd083a5ab732b1df81bdf392', 'comm_sex_man', 'man', '男', 'Man', '男', 'd62b242fde833653bce1498787ea6c31', null, null, null, null, null, null, '', null, '2018-02-01 12:51:55', null, null, null, null, null), ('c5158e555e164e648b149dafe2e89e0a', 'comm_sex_unknown', 'unknown', '未知', 'Unknown', '未知', 'd62b242fde833653bce1498787ea6c31', null, null, null, '', null, '2018-01-31 19:27:27', '', null, '2018-02-01 12:51:58', null, null, null, null, null), ('d55d6ed5a0024779b959f43288857089', 'authority_menu_dirt', 'dirt', '目录', 'Dirt', '目录', '67374d6aebeb4cc28484c7f303ec85f5', null, null, null, '', null, '2018-02-01 12:25:09', '', null, '2018-02-01 12:52:49', null, null, null, null, null), ('ee4920be6f424be0bfba54d7b1202068', 'authority_menu_menu', 'menu', '菜单', 'Menu', '菜单', '67374d6aebeb4cc28484c7f303ec85f5', null, null, null, '', null, '2018-02-01 12:24:23', '', null, '2018-02-01 12:52:51', null, null, null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

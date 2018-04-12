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

 Date: 04/12/2018 20:14:06 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
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
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `dict_type`
-- ----------------------------
BEGIN;
INSERT INTO `dict_type` VALUES ('2e7a6f2dab184e3c891040d7b9b7e7ec', 'authority_element', null, '资源类型', '8ab51d3c861d476eb10600b2ed0f1f0d', '', null, '2018-02-01 12:23:39', '', null, '2018-02-01 12:24:04', null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('4de6efea2456471ca3a4efa9f757d7cc', 'org', null, '组织部门', '64fce2915a213504be2b08ad12746af2', 'Mr.AG', '1', '2018-02-04 23:04:11', 'Mr.AG', '1', '2018-02-04 23:04:28', null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('53b7b71f63273f53aa2c7590b446fb33', 'comm', '0', '公共字典', '64fce2915a213504be2b08ad12746af2', null, null, null, null, null, null, null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('64fce2915a213504be2b08ad12746af2', 'root', '0', '根节点', '-1', null, null, null, null, null, null, null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('67374d6aebeb4cc28484c7f303ec85f5', 'authority_menu', null, '菜单类型', '8ab51d3c861d476eb10600b2ed0f1f0d', '', null, '2018-02-01 12:23:26', '', null, '2018-02-01 12:23:37', null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('6db3bb1a0bcb4cfca75a1e9d87996ff0', 'org_position', null, '岗位类型', '4de6efea2456471ca3a4efa9f757d7cc', 'Mr.AG', '1', '2018-04-04 18:56:30', 'Mr.AG', '1', '2018-04-04 19:06:30', null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('8ab51d3c861d476eb10600b2ed0f1f0d', 'authority', null, '权限字典', '64fce2915a213504be2b08ad12746af2', '', null, '2018-02-01 12:22:58', '', null, '2018-02-01 12:23:22', null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('cd9c72b4554a4061a0aa50755bd5419a', 'org_depart', null, '部门类型', '4de6efea2456471ca3a4efa9f757d7cc', 'Mr.AG', '1', '2018-02-04 23:04:31', 'Mr.AG', '1', '2018-02-04 23:11:43', null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24'), ('d62b242fde833653bce1498787ea6c31', 'comm_sex', '1', '性别', '53b7b71f63273f53aa2c7590b446fb33', '', null, '2018-01-31 17:36:38', '', null, '2018-01-31 17:36:49', null, null, null, null, 'ac88ceb386aa4231b09bf472cb937c24');
COMMIT;

-- ----------------------------
--  Table structure for `dict_value`
-- ----------------------------
DROP TABLE IF EXISTS `dict_value`;
CREATE TABLE `dict_value` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户ID',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
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
  `order_num` int(11) DEFAULT '1' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `dict_value`
-- ----------------------------
BEGIN;
INSERT INTO `dict_value` VALUES ('063ac8469d2b48398cc8263b8b786356', 'ac88ceb386aa4231b09bf472cb937c24', 'org_depart_group', 'group', '集团', 'Group', '集团', 'cd9c72b4554a4061a0aa50755bd5419a', null, null, null, 'Mr.AG', '1', '2018-02-04 23:08:46', 'Mr.AG', '1', '2018-02-05 12:40:41', null, null, null, null, null, '1'), ('276fc4a924b241d6820209c42e1b2ccc', 'ac88ceb386aa4231b09bf472cb937c24', 'authority_element_button', 'button', '按钮', 'button', 'button', '2e7a6f2dab184e3c891040d7b9b7e7ec', null, null, null, '', null, '2018-02-01 12:26:38', 'Mr.AG', '1', '2018-02-02 19:43:43', null, null, null, null, null, '1'), ('49000e5b738544df9f864489142e518c', 'ac88ceb386aa4231b09bf472cb937c24', 'org_depart_ou', 'ou', '公司', 'OU', '公司', 'cd9c72b4554a4061a0aa50755bd5419a', null, null, null, 'Mr.AG', '1', '2018-02-04 23:10:17', 'Mr.AG', '1', '2018-02-05 12:40:46', null, null, null, null, null, '2'), ('4b1112f4d71c492796690062aeedeb25', 'ac88ceb386aa4231b09bf472cb937c24', 'authority_element_uri', 'uri', '资源', 'uri', 'uri', '2e7a6f2dab184e3c891040d7b9b7e7ec', null, null, null, '', null, '2018-02-01 12:26:01', 'Mr.AG', '1', '2018-02-02 19:43:54', null, null, null, null, null, '1'), ('648eba56aba04fdbb5be553c27234ab9', 'ac88ceb386aa4231b09bf472cb937c24', 'org_position_role', 'role', '权限岗位', '权限岗位', '权限岗位', '6db3bb1a0bcb4cfca75a1e9d87996ff0', null, null, null, 'Mr.AG', '1', '2018-04-04 18:57:38', 'Mr.AG', '1', '2018-04-04 18:57:38', null, null, null, null, null, '2'), ('657ae6bd192a36729d39446174bcf64c', 'ac88ceb386aa4231b09bf472cb937c24', 'comm_sex_woman', 'woman', '女', 'Woman', '女', 'd62b242fde833653bce1498787ea6c31', null, null, null, null, null, null, '', null, '2018-02-01 12:51:52', null, null, null, null, null, '1'), ('687a9d26f11049d29ea5e33eecd6a4fd', 'ac88ceb386aa4231b09bf472cb937c24', 'org_depart_dept', 'dept', '部门', 'Depart', '部门', 'cd9c72b4554a4061a0aa50755bd5419a', null, null, null, 'Mr.AG', '1', '2018-02-04 23:11:02', 'Mr.AG', '1', '2018-02-05 12:40:52', null, null, null, null, null, '4'), ('75a832a6d3684b4a9aa62e04e84213d0', 'ac88ceb386aa4231b09bf472cb937c24', 'org_depart_bu', 'bu', '事业部', 'BU', '事业部', 'cd9c72b4554a4061a0aa50755bd5419a', null, null, null, 'Mr.AG', '1', '2018-02-04 23:10:45', 'Mr.AG', '1', '2018-02-05 12:40:49', null, null, null, null, null, '3'), ('8571cabddd083a5ab732b1df81bdf392', 'ac88ceb386aa4231b09bf472cb937c24', 'comm_sex_man', 'man', '男', 'Man', '男', 'd62b242fde833653bce1498787ea6c31', null, null, null, null, null, null, '', null, '2018-02-01 12:51:55', null, null, null, null, null, '1'), ('adc4459f7af64bc5975da46fb8b430b3', 'ac88ceb386aa4231b09bf472cb937c24', 'org_position_flow', 'flow', '流程岗位', '流程岗位', '流程岗位', '6db3bb1a0bcb4cfca75a1e9d87996ff0', null, null, null, 'Mr.AG', '1', '2018-04-04 18:57:26', 'Mr.AG', '1', '2018-04-04 18:57:26', null, null, null, null, null, '1'), ('c5158e555e164e648b149dafe2e89e0a', 'ac88ceb386aa4231b09bf472cb937c24', 'comm_sex_unknown', 'unknown', '未知', 'Unknown', '未知', 'd62b242fde833653bce1498787ea6c31', null, null, null, '', null, '2018-01-31 19:27:27', '', null, '2018-02-01 12:51:58', null, null, null, null, null, '1'), ('d55d6ed5a0024779b959f43288857089', 'ac88ceb386aa4231b09bf472cb937c24', 'authority_menu_dirt', 'dirt', '目录', 'Dirt', '目录', '67374d6aebeb4cc28484c7f303ec85f5', null, null, null, '', null, '2018-02-01 12:25:09', '', null, '2018-02-01 12:52:49', null, null, null, null, null, '1'), ('ee4920be6f424be0bfba54d7b1202068', 'ac88ceb386aa4231b09bf472cb937c24', 'authority_menu_menu', 'menu', '菜单', 'Menu', '菜单', '67374d6aebeb4cc28484c7f303ec85f5', null, null, null, '', null, '2018-02-01 12:24:23', '', null, '2018-02-01 12:52:51', null, null, null, null, null, '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

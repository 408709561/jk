CREATE DATABASE ag_auth DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
Use ag_auth;
/*
 Navicat Premium Data Transfer

 Source Server         : ag-admin
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : ag_auth

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 04/12/2018 20:14:15 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `auth_client`
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '服务编码',
  `secret` varchar(255) DEFAULT NULL COMMENT '服务密钥',
  `name` varchar(255) DEFAULT NULL COMMENT '服务名',
  `locked` char(1) DEFAULT NULL COMMENT '是否锁定',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `crt_host` varchar(255) DEFAULT NULL COMMENT '创建主机',
  `upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `upd_user` varchar(255) DEFAULT NULL COMMENT '更新人',
  `upd_name` varchar(255) DEFAULT NULL COMMENT '更新姓名',
  `upd_host` varchar(255) DEFAULT NULL COMMENT '更新主机',
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `auth_client`
-- ----------------------------
BEGIN;
INSERT INTO `auth_client` VALUES ('1', 'ace-gate', '123456', 'ace-gate', '0', '服务网关', null, '', '', '', '2017-07-07 21:51:32', '1', '管理员', '0:0:0:0:0:0:0:1', '', '', '', '', '', '', '', ''), ('18', 'ace-transaction', '123456', 'ace-transaction', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('20', 'ace-dict', '123566', 'ace-dict', '0', '数据字典服务', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('21', 'ace-demo-depart-data', '123456', 'ace-demo-depart-data', '0', '测试服务', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('22', 'ace-workflow', '123456', 'ace-workflow', '0', '工作流服务', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('3', 'ace-admin', '123456', 'ace-admin', '0', '', null, null, null, null, '2017-07-06 21:42:17', '1', '管理员', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null), ('6', 'ace-auth', '123456', 'ace-auth', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('7', 'ace-tool', '123456', 'ace-tool', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `auth_client_service`
-- ----------------------------
DROP TABLE IF EXISTS `auth_client_service`;
CREATE TABLE `auth_client_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `auth_client_service`
-- ----------------------------
BEGIN;
INSERT INTO `auth_client_service` VALUES ('21', '4', '5', null, null, null, null, null, null, null, null, null, null, null, null, null), ('43', '3', '16', null, null, null, null, null, null, null, null, null, null, null, null, null), ('45', '12', '16', null, null, null, null, null, null, null, null, null, null, null, null, null), ('46', '18', '18', null, null, null, null, null, null, null, null, null, null, null, null, null), ('53', '3', '6', null, null, null, null, null, null, null, null, null, null, null, null, null), ('61', '3', '1', null, null, null, null, null, null, null, null, null, null, null, null, null), ('62', '6', '1', null, null, null, null, null, null, null, null, null, null, null, null, null), ('63', '20', '1', null, null, null, null, null, null, null, null, null, null, null, null, null), ('65', '3', '21', null, null, null, null, null, null, null, null, null, null, null, null, null), ('66', '3', '22', null, null, null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `gateway_route`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route`;
CREATE TABLE `gateway_route` (
  `id` varchar(50) NOT NULL,
  `path` varchar(255) NOT NULL COMMENT '映射路劲',
  `service_id` varchar(50) DEFAULT NULL COMMENT '映射服务',
  `url` varchar(255) DEFAULT NULL COMMENT '映射外连接',
  `retryable` tinyint(1) DEFAULT NULL COMMENT '是否重试',
  `enabled` tinyint(1) NOT NULL COMMENT '是否启用',
  `strip_prefix` tinyint(1) DEFAULT NULL COMMENT '是否忽略前缀',
  `crt_user_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_user_id` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `upd_user_name` varchar(255) DEFAULT NULL COMMENT '最后更新人',
  `upd_user_id` varchar(36) DEFAULT NULL COMMENT '最后更新人ID',
  `upd_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `gateway_route`
-- ----------------------------
BEGIN;
INSERT INTO `gateway_route` VALUES ('admin', '/admin/**', 'ace-admin', null, '0', '1', '1', 'Mr.AG', '1', '2018-02-25 14:33:30', 'Mr.AG', '1', '2018-02-25 14:38:31'), ('auth', '/auth/**', 'ace-auth', null, '0', '1', '1', null, null, null, 'Mr.AG', '1', '2018-02-25 14:29:51'), ('center', '/center/**', 'ace-center', null, '0', '1', '1', 'Mr.AG', '1', '2018-02-26 12:50:51', 'Mr.AG', '1', '2018-02-26 12:50:51'), ('dict', '/dict/**', 'ace-dict', null, '0', '1', '1', null, null, null, 'Mr.AG', '1', '2018-02-25 14:41:07'), ('tool', '/tool/**', 'ace-tool', null, '0', '1', '1', null, null, '2018-04-02 21:04:47', null, null, '2018-04-02 21:04:52'), ('workflow', '/wf/**', 'ace-workflow', null, '0', '1', '1', null, null, '2018-04-05 13:58:08', null, null, '2018-04-05 13:58:14');
COMMIT;

-- ----------------------------
--  Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(100) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `crt_user_name` varchar(255) DEFAULT NULL,
  `crt_user_id` varchar(36) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `upd_user_name` varchar(255) DEFAULT NULL,
  `upd_user_id` varchar(36) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `is_deleted` char(1) DEFAULT NULL,
  `is_disabled` char(1) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `oauth_client_details`
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('client', '', 'client', 'read', 'password,refresh_token,authorization_code', 'http://localhost:4040/sso/login', null, '3600', '2592000', '{}', 'true', null, null, null, null, null, null, null, null, null), ('vue', null, 'vue', 'read', 'password,refresh_token', 'http://localhost:9527/#/', null, '14400', '2592000', '{}', 'true', '', null, null, null, 'Mr.AG', '1', '2018-03-28 20:43:14', null, null);
COMMIT;


SET FOREIGN_KEY_CHECKS = 1;


CREATE DATABASE ag_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
Use ag_admin;
/*
 Navicat Premium Data Transfer

 Source Server         : ag-admin
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : ag_admin

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 02/10/2018 16:53:21 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `base_depart`
-- ----------------------------
DROP TABLE IF EXISTS `base_depart`;
CREATE TABLE `base_depart` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '组织名称',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '上级节点',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `path` varchar(4000) DEFAULT NULL COMMENT '路劲',
  `type` varchar(36) DEFAULT NULL COMMENT '部门类型',
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
  `tenant_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_depart`
-- ----------------------------
BEGIN;
INSERT INTO `base_depart` VALUES ('2c3df2292bc34ed0a312d2bc46171883', '公司', 'root', 'root_ou', null, 'ou', null, null, null, 'Mr.AG', '1', '2018-02-05 13:04:35', null, null, null, null, null), ('root', '某集团', '-1', 'root', '/', 'group', null, null, null, 'Mr.AG', '1', '2018-02-05 12:58:31', null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_depart_user`
-- ----------------------------
DROP TABLE IF EXISTS `base_depart_user`;
CREATE TABLE `base_depart_user` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `depart_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_depart_user`
-- ----------------------------
BEGIN;
INSERT INTO `base_depart_user` VALUES ('92981c75b6e740be8579ed8099b1f7c9', '69a3e83e9e0540d9ac85e4fc3cbc45ec', '2c3df2292bc34ed0a312d2bc46171883'), ('9ab75a6145d240fcb8dbee373468a468', 'a7cc7babdb7b4ddb99406b6a086e424b', '2c3df2292bc34ed0a312d2bc46171883'), ('test', '1', 'root');
COMMIT;

-- ----------------------------
--  Table structure for `base_element`
-- ----------------------------
DROP TABLE IF EXISTS `base_element`;
CREATE TABLE `base_element` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '资源编码',
  `type` varchar(255) DEFAULT NULL COMMENT '资源类型',
  `name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `uri` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `menu_id` varchar(255) DEFAULT NULL COMMENT '资源关联菜单',
  `parent_id` varchar(255) DEFAULT NULL,
  `path` varchar(2000) DEFAULT NULL COMMENT '资源树状检索路径',
  `method` varchar(10) DEFAULT NULL COMMENT '资源请求类型',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
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
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_element`
-- ----------------------------
BEGIN;
INSERT INTO `base_element` VALUES ('10', 'menuManager:btn_add', 'button', '新增', '/admin/menu', '6', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('11', 'menuManager:btn_edit', 'button', '编辑', '/admin/menu', '6', '', '', 'PUT', '', '2017-06-24 00:00:00', '', '', '', '', '', '', '', '', '', '', '', null), ('12', 'menuManager:btn_del', 'button', '删除', '/admin/menu', '6', '', '', 'DELETE', '', '2017-06-24 00:00:00', '', '', '', '', '', '', '', '', '', '', '', null), ('13', 'menuManager:btn_element_add', 'button', '新增元素', '/admin/element', '6', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('14', 'menuManager:btn_element_edit', 'button', '编辑元素', '/admin/element', '6', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('15', 'menuManager:btn_element_del', 'button', '删除元素', '/admin/element', '6', null, null, 'DELETE', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('16', 'groupManager:btn_add', 'button', '新增', '/admin/group', '7', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('17', 'groupManager:btn_edit', 'button', '编辑', '/admin/group', '7', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('18', 'groupManager:btn_del', 'button', '删除', '/admin/group', '7', null, null, 'DELETE', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('19', 'groupManager:btn_userManager', 'button', '分配用户', '/admin/group/{*}/user', '7', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('20', 'groupManager:btn_resourceManager', 'button', '分配权限', '/admin/group/{*}/authority', '7', null, null, 'GET', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('21', 'groupManager:menu', 'uri', '分配菜单', '/admin/group/{*}/authority/menu', '7', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('22', 'groupManager:element', 'uri', '分配资源', '/admin/group/{*}/authority/element', '7', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('23', 'userManager:view', 'uri', '查看', '/admin/user', '1', '', '', 'GET', '', '2017-06-26 00:00:00', '', '', '', '', '', '', '', '', '', '', '', null), ('24', 'menuManager:view', 'uri', '查看', '/admin/menu', '6', '', '', 'GET', '', '2017-06-26 00:00:00', '', '', '', '', '', '', '', '', '', '', '', null), ('27', 'menuManager:element_view', 'uri', '查看', '/admin/element', '6', null, null, 'GET', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('28', 'groupManager:view', 'uri', '查看', '/admin/group', '7', null, null, 'GET', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('2b7b250b2b7346759938a05da57d2fcb', 'tenantManager:view', 'uri', '查看租户', '/admin/tenant', '7574b969c9fa4e5895d6cc9c2b8a9a62', null, null, 'GET', null, '2018-02-09 12:26:27', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('3', 'userManager:btn_add', 'button', '新增', '/admin/user', '1', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('32', 'groupTypeManager:view', 'uri', '查看', '/admin/groupType', '8', null, null, 'GET', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('33', 'groupTypeManager:btn_add', 'button', '新增', '/admin/groupType', '8', null, null, 'POST', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('34', 'groupTypeManager:btn_edit', 'button', '编辑', '/admin/groupType', '8', null, null, 'PUT', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('35', 'groupTypeManager:btn_del', 'button', '删除', '/admin/groupType', '8', null, null, 'DELETE', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('39925b8263664c9e923f9c34f9bc2a37', 'positionManager:btn_user', 'button', '分配岗位人员', '/admin/position/{*}/user', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'PUT', null, '2018-02-10 16:49:57', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('4', 'userManager:btn_edit', 'button', '编辑', '/admin/user', '1', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('41', 'gateLogManager:view', 'button', '查看', '/admin/gateLog', '27', null, null, 'GET', '', '2017-07-01 00:00:00', '1', 'admin', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null, null), ('42', 'serviceManager:view', 'uri', '查看', '/auth/service', '30', null, null, 'GET', null, '2017-12-26 20:17:42', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('43', 'serviceManager:btn_add', 'button', '新增', '/auth/service', '30', null, null, 'POST', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('44', 'serviceManager:btn_edit', 'button', '编辑', '/auth/service', '30', null, null, 'PUT', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('45', 'serviceManager:btn_del', 'button', '删除', '/auth/service', '30', null, null, 'DELETE', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('46', 'serviceManager:btn_clientManager', 'button', '授权服务', '/auth/service/{*}/client', '30', null, null, 'POST', null, '2017-12-30 16:32:48', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('47', 'serviceManager:btn_buidlProject', 'button', '构建工程', '/auth/generator/build', '30', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('48', 'dictTypeManager:view', 'uri', '查看字典目录', '/dict/dictType', '21', null, null, 'GET', null, '2017-12-26 20:17:42', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('49', 'dictTypeManager:btn_add', 'button', '新增字典目录', '/dict/dictType', '21', null, null, 'POST', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('5', 'userManager:btn_del', 'button', '删除', '/admin/user', '1', null, null, 'DELETE', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('50', 'dictTypeManager:btn_edit', 'button', '编辑字典目录', '/dict/dictType', '21', null, null, 'PUT', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('51', 'dictTypeManager:btn_del', 'button', '删除字典目录', '/dict/dictType', '21', null, null, 'DELETE', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('52', 'dictValueManager:view', 'uri', '查看字典值', '/dict/dictValue', '21', null, null, 'GET', null, '2017-12-26 20:17:42', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('53', 'dictValueManager:btn_add', 'button', '新增字典值', '/dict/dictValue', '21', null, null, 'POST', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('54', 'dictValueManager:btn_edit', 'button', '编辑字典值', '/dict/dictValue', '21', null, null, 'PUT', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('55', 'dictValueManager:btn_del', 'button', '删除字典值', '/dict/dictValue', '21', null, null, 'DELETE', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('56', 'departManager:view', 'uri', '查看部门值', '/admin/depart', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'GET', null, '2017-12-26 20:17:42', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('57', 'departManager:btn_add', 'button', '新增部门值', '/admin/depart', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'POST', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('58', 'departManager:btn_edit', 'button', '编辑部门值', '/admin/depart', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'PUT', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('59', 'departManager:btn_del', 'button', '删除部门值', '/admin/depart', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'DELETE', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('6d7ea83bb71d476dbfbda2bcbc1a01bd', 'positionManager:btn_del', 'button', '删除岗位', '/admin/position', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'DELETE', null, '2018-02-10 16:47:45', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('7a16202657c3478c8b4ece27a792e026', 'positionManager:btn_group', 'button', '分配岗位角色', '/admin/position/{*}/group', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'PUT', null, '2018-02-10 16:51:31', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('8b5ff030c76341059a335e0653fef5ea', 'departManager:btn_user_del', 'button', '删除部门人员', '/admin/depart/user', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'DELETE', null, '2018-02-10 16:45:10', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('9', 'menuManager:element', 'uri', '按钮页面', '/admin/element', '6', null, null, 'GET', '', null, null, null, null, null, null, null, null, null, null, null, null, null), ('a03cac538f794958bc7fa6458f85b8ae', 'tenantManager:btn_del', 'uri', '删除租户', '/admin/tenant', '7574b969c9fa4e5895d6cc9c2b8a9a62', null, null, 'DELETE', null, '2018-02-09 12:28:42', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('c6469be83d8e4d5ca404b46b347f7d4c', 'positionManager:btn_add', 'button', '新增岗位', '/admin/position', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'POST', null, '2018-02-10 16:47:23', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('cac49646909c4750b23f223f53eafc5b', 'positionManager:btn_edit', 'button', '编辑岗位', '/admin/posision', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'PUT', null, '2018-02-10 16:48:24', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('d2773b3568c6438c8f9cc21b06b660ee', 'departManager:btn_user_add', 'button', '新增部门人员', '/admin/depart/user', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'POST', null, '2018-02-10 16:44:07', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('d6b714eff47a42d0a654e55c39304dd5', 'tenantManager:btn_edit', 'button', '编辑租户', '/admin/tenant', '7574b969c9fa4e5895d6cc9c2b8a9a62', null, null, 'PUT', null, '2018-02-09 12:29:26', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('eddad1f3d2d54ba0ac50e8c4781764cb', 'tenantManager:btn_add', 'button', '新增租户', '/admin/tenant', '7574b969c9fa4e5895d6cc9c2b8a9a62', null, null, 'POST', null, '2018-02-09 12:27:11', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('ef73cc9bf70d448caf72e3f45015700a', 'positionManager:view', 'uri', '查看岗位', '/admin/depart', 'b5211cc69d234b28a97f27e63edc9a58', null, null, 'GET', null, '2018-02-10 16:48:49', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_group`
-- ----------------------------
DROP TABLE IF EXISTS `base_group`;
CREATE TABLE `base_group` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '角色编码',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `parent_id` varchar(36) NOT NULL COMMENT '上级节点',
  `path` varchar(2000) DEFAULT NULL COMMENT '树状关系',
  `type` char(1) DEFAULT NULL COMMENT '类型',
  `group_type` varchar(36) NOT NULL COMMENT '角色组类型',
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_group`
-- ----------------------------
BEGIN;
INSERT INTO `base_group` VALUES ('1', 'adminRole', '管理员', '-1', '/adminRole', null, 'role', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('3', 'testGroup', '体验组', '-1', '/testGroup', null, 'role', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('4', 'visitorRole', '游客', '3', '/testGroup/visitorRole', null, 'role', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('6', 'company', '董事长', '-1', '/company', null, 'org', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('7', 'financeDepart', '部长', '6', '/company/financeDepart', null, 'org', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('8', 'hrDepart', '处长', '6', '/company/hrDepart', null, 'org', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('9', 'blogAdmin', '博客管理员', '-1', '/blogAdmin', null, 'role', '', '2017-07-16 16:59:30', '1', 'Mr.AG', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_group_leader`
-- ----------------------------
DROP TABLE IF EXISTS `base_group_leader`;
CREATE TABLE `base_group_leader` (
  `id` varchar(36) NOT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_group_leader`
-- ----------------------------
BEGIN;
INSERT INTO `base_group_leader` VALUES ('6', '9', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('cda2223ece3a4ed9b8fe2341e9856642', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_group_member`
-- ----------------------------
DROP TABLE IF EXISTS `base_group_member`;
CREATE TABLE `base_group_member` (
  `id` varchar(36) NOT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_group_member`
-- ----------------------------
BEGIN;
INSERT INTO `base_group_member` VALUES ('2', '4', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('9', '9', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_group_type`
-- ----------------------------
DROP TABLE IF EXISTS `base_group_type`;
CREATE TABLE `base_group_type` (
  `id` varchar(32) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(255) DEFAULT NULL COMMENT '创建人ID',
  `crt_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_host` varchar(255) DEFAULT NULL COMMENT '创建主机',
  `upd_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `upd_user` varchar(255) DEFAULT NULL COMMENT '最后更新人ID',
  `upd_name` varchar(255) DEFAULT NULL COMMENT '最后更新人',
  `upd_host` varchar(255) DEFAULT NULL COMMENT '最后更新主机',
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_group_type`
-- ----------------------------
BEGIN;
INSERT INTO `base_group_type` VALUES ('free', 'free', '自定义类型', 'sad', null, null, null, null, '2018-01-22 12:59:12', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('org', 'org', '岗位组（不可删）', null, null, null, null, null, '2017-08-25 17:52:43', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('role', 'role', '角色组（不可删）', 'role', null, null, null, null, '2017-08-25 17:52:37', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_menu`
-- ----------------------------
DROP TABLE IF EXISTS `base_menu`;
CREATE TABLE `base_menu` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '路径编码',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `parent_id` varchar(36) NOT NULL COMMENT '父级节点',
  `href` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `type` char(10) DEFAULT NULL,
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `path` varchar(500) DEFAULT NULL COMMENT '菜单上下级关系',
  `enabled` char(1) DEFAULT NULL COMMENT '启用禁用',
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_menu`
-- ----------------------------
BEGIN;
INSERT INTO `base_menu` VALUES ('1', 'userManager', '用户管理', '5', '/admin/user', 'user', 'menu', '1', '', '/adminSys/baseManager/userManager', null, null, null, null, null, '2018-01-23 15:35:50', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/user/index\')', null, null, null, null, null, null, null, null), ('13', 'adminSys', '权限管理系统', '-1', '/base', 'setting', 'dirt', '0', '', '/adminSys', null, null, null, null, null, '2018-02-02 19:48:22', '1', 'Mr.AG', '127.0.0.1', 'Layout', null, null, null, null, null, null, null, null), ('21', 'dictManager', '数据字典', '5', '', 'documentation', 'menu', '5', '', '/adminSys/baseManager/dictManager', null, null, null, null, null, '2018-02-04 14:48:47', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('27', 'gateLogManager', '操作日志', '5', '/admin/gateLog', 'log', 'menu', '6', '', '/adminSys/baseManager/gateLogManager', null, '2017-07-01 00:00:00', '1', 'admin', '0:0:0:0:0:0:0:1', '2017-09-05 22:32:55', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/gateLog/index\')', null, null, null, null, null, null, null, null), ('29', 'authManager', '服务管理', '13', '/auth', 'service', 'dirt', '3', '服务权限管理', '/adminSys/authManager', null, '2017-12-26 19:54:45', '1', 'Mr.AG', '127.0.0.1', '2018-02-02 19:48:42', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('30', 'serviceManager', '服务权限管理', '29', '/auth/service', 'client', 'menu', '1', '服务管理', '/adminSys/authManager/serviceManager', null, '2017-12-26 19:56:06', '1', 'Mr.AG', '127.0.0.1', '2018-02-02 19:48:46', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('31', 'monitorManager', '监控运维管理', '13', null, 'setting', 'dirt', '3', null, '/adminSys/monitorManager', null, '2018-01-07 11:35:19', '1', 'Mr.AG', '127.0.0.1', '2018-02-04 22:33:41', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('33', 'serviceMonitorManager', '服务监控管理', '31', null, 'client', 'menu', '2', null, '/adminSys/monitorManager/serviceMonitorManager', null, '2018-01-07 11:38:13', '1', 'Mr.AG', '127.0.0.1', '2018-01-07 11:38:29', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('34', 'serviceEurekaManager', '服务注册管理', '31', null, 'client', 'menu', '1', null, '/adminSys/monitorManager/serviceEurekaManager', null, '2018-01-07 12:20:31', '1', 'Mr.AG', '127.0.0.1', '2018-01-07 12:20:31', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('35', 'serviceZipkinManager', '服务链路监控', '31', null, 'client', 'menu', '3', null, '/adminSys/monitorManager/serviceZipkinManager', null, '2018-01-07 17:58:14', '1', 'Mr.AG', '127.0.0.1', '2018-01-07 17:58:14', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null), ('5', 'baseManager', '基础配置管理', '13', '/admin', 'setting', 'dirt', '0', '', '/adminSys/baseManager', null, null, null, null, null, '2018-02-02 19:48:27', '1', 'Mr.AG', '127.0.0.1', 'Layout', null, null, null, null, null, null, null, null), ('6', 'menuManager', '菜单管理', '5', '/admin/menu', 'category', 'menu', '2', '', '/adminSys/baseManager/menuManager', null, null, null, null, null, '2017-09-05 21:10:25', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/menu/index\')', null, null, null, null, null, null, null, null), ('7', 'groupManager', '角色权限管理', '5', '/admin/group', 'group_fill', 'menu', '3', '', '/adminSys/baseManager/groupManager', null, null, null, null, null, '2017-09-05 21:11:34', '1', 'Mr.AG', '127.0.0.1', 'import(\'admin/group/index\')', null, null, null, null, null, null, null, null), ('7494902cc9e948668e51f4596042b084', 'apiManager', '服务接口文档', '29', '/auth/api', 'documentation', 'menu', '2', null, '/adminSys/authManager/apiManager', null, '2018-02-07 13:28:05', '1', 'Mr.AG', null, '2018-02-07 13:30:42', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('7574b969c9fa4e5895d6cc9c2b8a9a62', 'tenantManager', '租户管理', '5', '/admin/tenantManager', 'tenant', 'menu', '7', null, '/adminSys/baseManager/tenantManager', null, '2018-02-09 08:56:43', '1', 'Mr.AG', null, '2018-02-09 08:56:43', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('7a0a75752c7d422abd2e30b7aad744d7', 'orgManager', '组织部门管理', '13', '/org', 'org', 'dirt', '2', null, '/adminSys/orgManager', null, '2018-02-04 22:33:33', '1', 'Mr.AG', null, '2018-02-04 22:35:57', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null), ('8', 'groupTypeManager', '角色类型管理', '5', '/admin/groupType', 'group', 'menu', '4', '', '/adminSys/baseManager/groupTypeManager', null, null, null, null, null, '2017-09-05 21:12:28', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/groupType/index\')', null, null, null, null, null, null, null, null), ('b5211cc69d234b28a97f27e63edc9a58', 'departManager', '部门管理', '7a0a75752c7d422abd2e30b7aad744d7', '/org/depart', 'depart', 'menu', '1', null, '/adminSys/orgManager/departManager', null, '2018-02-04 22:40:01', '1', 'Mr.AG', null, '2018-02-04 22:40:01', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_position`
-- ----------------------------
DROP TABLE IF EXISTS `base_position`;
CREATE TABLE `base_position` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '职位',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `depart_id` varchar(36) DEFAULT NULL COMMENT '部门ID',
  `type` varchar(36) DEFAULT NULL COMMENT '类型',
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
  `tenant_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_position`
-- ----------------------------
BEGIN;
INSERT INTO `base_position` VALUES ('b12b7a790c79461eba76e67b602b0f25', '部长', 'leader', '2c3df2292bc34ed0a312d2bc46171883', null, 'Mr.AG', '1', '2018-02-08 13:35:06', 'Mr.AG', '1', '2018-02-08 13:35:06', null, null, null, null, null), ('c6cc6658126d47fc812b941c073e47d6', 'CEO', 'CEO', 'root', null, '', null, '2018-02-06 20:10:01', 'Mr.AG', '1', '2018-02-07 22:13:53', null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_position_group`
-- ----------------------------
DROP TABLE IF EXISTS `base_position_group`;
CREATE TABLE `base_position_group` (
  `id` varchar(36) NOT NULL,
  `position_id` varchar(36) DEFAULT NULL,
  `group_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_position_group`
-- ----------------------------
BEGIN;
INSERT INTO `base_position_group` VALUES ('06a23833c64a4af8b67d9f607d2b9f27', 'c6cc6658126d47fc812b941c073e47d6', '8'), ('75d53b70fb7a43e9abb5ca3c0bf1e436', 'b12b7a790c79461eba76e67b602b0f25', '7'), ('efe59b832b394ff4a6f274ede921802e', 'c6cc6658126d47fc812b941c073e47d6', '7');
COMMIT;

-- ----------------------------
--  Table structure for `base_position_user`
-- ----------------------------
DROP TABLE IF EXISTS `base_position_user`;
CREATE TABLE `base_position_user` (
  `id` varchar(36) NOT NULL,
  `position_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_position_user`
-- ----------------------------
BEGIN;
INSERT INTO `base_position_user` VALUES ('2189a2c499004df6bb347707388952d5', 'b12b7a790c79461eba76e67b602b0f25', '69a3e83e9e0540d9ac85e4fc3cbc45ec'), ('3940315dd7104f72881eba7fba8906ff', 'c6cc6658126d47fc812b941c073e47d6', '1');
COMMIT;

-- ----------------------------
--  Table structure for `base_resource_authority`
-- ----------------------------
DROP TABLE IF EXISTS `base_resource_authority`;
CREATE TABLE `base_resource_authority` (
  `id` varchar(36) NOT NULL,
  `authority_id` varchar(255) DEFAULT NULL COMMENT '角色ID',
  `authority_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `resource_id` varchar(255) DEFAULT NULL COMMENT '资源ID',
  `resource_type` varchar(255) DEFAULT NULL COMMENT '资源类型',
  `parent_id` varchar(255) DEFAULT NULL,
  `path` varchar(2000) DEFAULT NULL,
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
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_resource_authority`
-- ----------------------------
BEGIN;
INSERT INTO `base_resource_authority` VALUES ('0c4ebea9ed7944598cbcfc7778750a33', '1', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('1021', '9', 'group', '3', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('1026', '9', 'group', '23', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('1031', '9', 'group', '4', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('1032', '9', 'group', '1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('1033', '9', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('1034', '9', 'group', '-1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('1035', '9', 'group', '5', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('15cefec9b2794f7eb9e21c8ac28eb21e', '1', 'group', '51', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('17587cda98cb4d81940b65760833953e', '1', 'group', '50', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('227c208d1cf4463aad525a67f3329d0f', '7', 'group', '-1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('349', '4', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('396', '4', 'group', '23', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('397', '4', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('398', '4', 'group', '27', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('399', '4', 'group', '24', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('400', '4', 'group', '28', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('401', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('402', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('403', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('421', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('422', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('423', '4', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('424', '4', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('440', '4', 'group', '32', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('464', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('465', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('466', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('467', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('468', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('469', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('470', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('471', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('472', '1', 'group', '40', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('492', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('493', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('494', '1', 'group', '40', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('4b10cfa656a849cf9dbf03485525a5a9', '7', 'group', '5', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('4e34372b08fe412094c6d0bb4ee55302', '7', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('516', '4', 'group', '41', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('517', '4', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('518', '4', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('519', '4', 'group', '40', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('611', '4', 'group', '42', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('612', '4', 'group', '36', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('628', '4', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('629', '4', 'group', '5', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('630', '4', 'group', '1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('631', '4', 'group', '6', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('632', '4', 'group', '7', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('633', '4', 'group', '8', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('634', '4', 'group', '27', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('635', '4', 'group', '9', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('636', '4', 'group', '24', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('637', '4', 'group', '22', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('638', '4', 'group', '23', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('639', '4', 'group', '25', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('640', '4', 'group', '26', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('641', '4', 'group', '28', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('710', '9', 'group', '42', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('711', '9', 'group', '43', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('712', '9', 'group', '44', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('713', '9', 'group', '45', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('718', '9', 'group', '42', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('719', '9', 'group', '44', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('720', '9', 'group', '45', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('721', '9', 'group', '43', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('749', '10', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('750', '10', 'group', '14', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('751', '10', 'group', '-1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('752', '10', 'group', '5', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('753', '10', 'group', '6', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('754', '10', 'group', '17', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('755', '10', 'group', '20', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('777f09a221864684b7ea115750e6e126', '1', 'group', '52', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('8492be816fe64a8fa7317eeac44e0ff4', '7', 'group', '23', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('8ff6f9e083b34a61b8a81aa45c5b3bca', '1', 'group', '55', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('95af878c876d42589ffa60ec54cc0f32', '7', 'group', '1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('9c132c924fe84905b68c4a58349fe76c', '1', 'group', '-1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('9edb9015d70147b4a43fc37496bd22d8', '1', 'group', '41', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('a6739586574044748bb6f75cdc62df1b', '1', 'group', '49', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('a6bb78e5d97e4995bbdde988c2e68489', '1', 'group', '54', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('c0ddcc06b7854f5bbe9634af41963941', '1', 'group', '53', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('e00741edcc894d1cbabbc75e0ef2c6eb', '1', 'group', '48', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `base_tenant`
-- ----------------------------
DROP TABLE IF EXISTS `base_tenant`;
CREATE TABLE `base_tenant` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '最后更新时间',
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
  `description` varchar(500) DEFAULT NULL,
  `is_super_tenant` varchar(1) DEFAULT NULL COMMENT '是否超级租户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- ----------------------------
--  Records of `base_tenant`
-- ----------------------------
BEGIN;
INSERT INTO `base_tenant` VALUES ('ac88ceb386aa4231b09bf472cb937c24', 'superTenant', '超级租户', 'Mr.AG', '1', '2018-02-09 16:31:20', 'Mr.AG', '1', '2018-02-09 16:31:20', null, null, null, null, '超级租户', '1');
COMMIT;

-- ----------------------------
--  Table structure for `base_user`
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user` (
  `id` varchar(36) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mobile_phone` varchar(255) DEFAULT NULL,
  `tel_phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sex` varchar(16) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  `is_deleted` char(1) DEFAULT NULL COMMENT '是否删除',
  `is_disabled` char(1) DEFAULT NULL COMMENT '是否作废',
  `depart_id` varchar(36) DEFAULT NULL COMMENT '默认部门',
  `is_super_admin` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_usernane` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `base_user`
-- ----------------------------
BEGIN;
INSERT INTO `base_user` VALUES ('00e35a639b8141dc8426dd0cd9521fdc', 'wanghaodda', '$2a$15$474Ly5vj6wzhF2ASZj0VDuJX09Wb7fV0FOjiLrJAiJcdyxS7kijF6', 'wanghaodda', null, null, null, null, null, '男', null, null, null, '2018-02-08 09:24:49', '1', 'Mr.AG', null, '2018-02-08 13:31:14', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null, '1', '0', 'root', '0'), ('1', 'admin', '$2a$12$S/yLlj9kzi5Dgsz97H4rAekxrPlk/10eXp1lUJcAVAx.2M9tOpWie', 'Mr.AG', '', null, '', null, '', 'man', null, null, '', null, null, null, null, '2018-02-07 19:14:10', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null, '0', '0', 'root', '1'), ('2', 'test', '$2a$12$zWe6knO6rGp15UVfdWTTxu.Ykt.k3QnD5FPoj6a1cnL63csHY2A1S', '测试账户', '', null, '', null, '', 'man', null, null, '', null, null, null, null, '2018-02-07 21:15:06', '1', 'Mr.AG', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null, null, '0', '0', 'root', '0'), ('4', 'blog', '$2a$12$S/yLlj9kzi5Dgsz97H4rAekxrPlk/10eXp1lUJcAVAx.2M9tOpWie', 'Mr.AG(博主)', '', null, '', null, '', '男', null, null, '12', null, null, null, null, '2018-02-07 21:45:46', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null, null, '0', '0', 'root', '0'), ('5', 'xxxxx', '$2a$15$NjKPSChX7tjnGCc/V5w2B.xP/Z8wlIWIOeEsY3hZRQgjYX7KmCCmG', 'xxxxx', null, null, null, null, null, 'woman', null, null, null, null, null, null, null, '2018-02-01 12:53:24', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null, '1', '0', 'root', '0'), ('69a3e83e9e0540d9ac85e4fc3cbc45ec', 'OrgTest', '$2a$15$JHcRJ31DkocCZnmMnV7gAuHSzYoXiX9zkmH4Vkj5H4YcNLAEQONOG', '组织权限测试', null, null, null, null, null, '男', null, null, null, '2018-02-08 13:33:26', '1', 'Mr.AG', null, '2018-02-08 13:33:26', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null, '0', '0', '2c3df2292bc34ed0a312d2bc46171883', '0'), ('a7cc7babdb7b4ddb99406b6a086e424b', 'dsd', '$2a$15$fWfhd/dy1NIHN2D2DiXNuOeHMsoU2osAiqFJn7fmLU5S5uiWQo9MW', 'dsd', null, null, null, null, null, '男', null, null, null, '2018-02-08 09:45:52', '1', 'Mr.AG', null, '2018-02-08 13:31:23', '1', 'Mr.AG', null, null, null, null, null, null, null, null, null, null, '1', '0', '2c3df2292bc34ed0a312d2bc46171883', '0');
COMMIT;

-- ----------------------------
--  Table structure for `gate_log`
-- ----------------------------
DROP TABLE IF EXISTS `gate_log`;
CREATE TABLE `gate_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  `menu` varchar(255) DEFAULT NULL COMMENT '菜单',
  `opt` varchar(255) DEFAULT NULL COMMENT '操作',
  `uri` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `crt_time` datetime DEFAULT NULL COMMENT '操作时间',
  `crt_user` varchar(255) DEFAULT NULL COMMENT '操作人ID',
  `crt_name` varchar(255) DEFAULT NULL COMMENT '操作人',
  `crt_host` varchar(255) DEFAULT NULL COMMENT '操作主机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;

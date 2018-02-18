-- 增加租户ID
ALTER TABLE `ag_admin`.`gate_log` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_menu` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_resource_authority` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_group_type` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_group_member` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_group_leader` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_group` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_element` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;
ALTER TABLE `ag_admin`.`base_user` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户Id' AFTER `id`;

ALTER TABLE `ag_dict`.`dict_type` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户ID' AFTER `id`;
ALTER TABLE `ag_dict`.`dict_value` ADD COLUMN `tenant_id` varchar(36) COMMENT '租户ID' AFTER `id`;
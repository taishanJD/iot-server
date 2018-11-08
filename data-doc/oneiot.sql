/*
 Navicat MySQL Data Transfer

 Source Server         : OneShare test 192.168.204.26
 Source Server Type    : MySQL
 Source Server Version : 50621
 Source Host           : 192.168.204.26
 Source Database       : oneiot

 Target Server Type    : MySQL
 Target Server Version : 50621
 File Encoding         : utf-8

 Date: 01/17/2018 14:28:23 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `data_point`
-- ----------------------------
DROP TABLE IF EXISTS `data_point`;
CREATE TABLE `data_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据点ID',
  `product_id` bigint(20) NOT NULL COMMENT '产品ID',
  `model_id` bigint(20) NOT NULL COMMENT '设备模型ID',
  `name` varchar(100) NOT NULL COMMENT '数据点名称',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `unit_name` varchar(32) DEFAULT NULL COMMENT '单位名称',
  `unit_sign` varchar(10) DEFAULT NULL COMMENT '单位符号',
  `frequency` int(10) NOT NULL DEFAULT '60' COMMENT '上报频率，单位s',
  `type` tinyint(4) NOT NULL COMMENT '数据类型(0:float,1:double,2:short,3:int,4:long,5:boolean,6:string)',
  `length` int(10) DEFAULT NULL COMMENT '数据长度(只对string有效)',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `model_id` (`model_id`,`name`),
  UNIQUE KEY `product_name` (`product_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据点';

-- ----------------------------
--  Table structure for `data_point_extra`
-- ----------------------------
DROP TABLE IF EXISTS `data_point_extra`;
CREATE TABLE `data_point_extra` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据点扩展ID',
  `data_point_id` bigint(20) NOT NULL COMMENT '数据点ID',
  `name` varchar(100) NOT NULL COMMENT 'key name',
  `value` varchar(100) DEFAULT NULL COMMENT 'key value',
  `description` varchar(100) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`),
  UNIQUE KEY `data_point_id` (`data_point_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据点扩展';

-- ----------------------------
--  Table structure for `device`
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_id` bigint(20) NOT NULL COMMENT '设备模型ID',
  `name` varchar(100) NOT NULL COMMENT '设备名称',
  `api_key` varchar(256) DEFAULT NULL COMMENT 'apihub key',
  `sn` varchar(100) DEFAULT NULL COMMENT '设备唯一标识',
  `mac` varchar(64) DEFAULT NULL COMMENT 'mac地址',
  `imei` varchar(64) DEFAULT NULL COMMENT 'imei',
  `description` varchar(256) DEFAULT NULL COMMENT '说明',
  `device_status` tinyint(4) DEFAULT '1' COMMENT '在线状态(0:在线,1:下线)',
  `last_online_date` datetime DEFAULT NULL COMMENT '最后一次上线时间',
  `last_offline_date` datetime DEFAULT NULL COMMENT '最后一次下线时间',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`product_id`),
  UNIQUE KEY `sn` (`sn`,`product_id`),
  UNIQUE KEY `mac` (`mac`,`product_id`),
  KEY `product_id` (`product_id`,`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备';

-- ----------------------------
--  Table structure for `device_model`
-- ----------------------------
DROP TABLE IF EXISTS `device_model`;
CREATE TABLE `device_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备模型ID',
  `product_id` bigint(20) NOT NULL COMMENT '产品ID',
  `longitude` varchar(100) DEFAULT NULL COMMENT '经度数据点名称',
  `laitude` varchar(100) DEFAULT NULL COMMENT '纬度数据点名称',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备模型';

-- ----------------------------
--  Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_id` bigint(20) NOT NULL COMMENT '产品ID',
  `name` varchar(100) NOT NULL COMMENT '设备组名称',
  `description` varchar(256) DEFAULT NULL COMMENT '说明',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`product_id`),
  KEY `product_id` (`product_id`,`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备组';

-- ----------------------------
--  Table structure for `group_device`
-- ----------------------------
DROP TABLE IF EXISTS `group_device`;
CREATE TABLE `group_device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` bigint(20) NOT NULL COMMENT '设备组ID',
  `device_id` bigint(20) NOT NULL COMMENT '设备ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_device` (`group_id`,`device_id`),
  KEY `device_id` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备组与设备映射关系';

-- ----------------------------
--  Table structure for `mqtt_acl`
-- ----------------------------
DROP TABLE IF EXISTS `mqtt_acl`;
CREATE TABLE `mqtt_acl` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `allow` int(1) DEFAULT NULL COMMENT '0: deny, 1: allow',
  `ipaddr` varchar(60) DEFAULT NULL COMMENT 'IpAddress',
  `username` varchar(100) DEFAULT NULL COMMENT 'Username',
  `clientid` varchar(100) DEFAULT NULL COMMENT 'ClientId',
  `access` int(2) NOT NULL COMMENT '1: subscribe, 2: publish, 3: pubsub',
  `topic` varchar(100) NOT NULL DEFAULT '' COMMENT 'Topic Filter',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mqtt_acl`
-- ----------------------------
BEGIN;
INSERT INTO `mqtt_acl` VALUES ('1', '1', null, '$all', null, '2', '#'), ('2', '0', null, '$all', null, '1', '$SYS/#'), ('3', '0', null, '$all', null, '1', 'eq #'), ('5', '1', '127.0.0.1', null, null, '2', '$SYS/#'), ('6', '1', '127.0.0.1', null, null, '2', '#'), ('7', '1', null, 'dashboard', null, '1', '$SYS/#'), ('8', '0', null, 'root', null, '3', '#');
COMMIT;

-- ----------------------------
--  Table structure for `mqtt_user`
-- ----------------------------
DROP TABLE IF EXISTS `mqtt_user`;
CREATE TABLE `mqtt_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(20) DEFAULT NULL,
  `is_superuser` tinyint(1) DEFAULT '0',
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mqtt_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mqtt_user`
-- ----------------------------
BEGIN;
INSERT INTO `mqtt_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '', '1', '2018-01-10 18:11:46'), ('2', 'root', 'e10adc3949ba59abbe56e057f20f883e', '', '1', '2018-01-10 18:11:46');
COMMIT;

-- ----------------------------
--  Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `name` varchar(100) NOT NULL COMMENT '产品名称',
  `industry` varchar(100) NOT NULL COMMENT '所属行业',
  `description` varchar(200) NOT NULL COMMENT '产品介绍',
  `connect_type` varchar(64) NOT NULL COMMENT '接入方式：public：公开协议，private：私有协议',
  `connect_protocal` varchar(64) NOT NULL COMMENT '接入协议：mqtt,https',
  `auth_method` varchar(64) NOT NULL COMMENT 'APIKey：apihub key,Cert:证书',
  `del_flag` char(1) NOT NULL COMMENT '删除标记(0:正常;1:已删除)',
  `status` char(1) NOT NULL COMMENT '产品状态(0:开发中;1:已发布)',
  `register_code` varchar(256) DEFAULT NULL COMMENT '产品注册码,注册设备时使用',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tenant` (`tenant_id`,`create_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品';

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_code` varchar(64) NOT NULL COMMENT '角色标识',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`role_code`),
  UNIQUE KEY `role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('system_admin', '系统管理员', '系统管理员管理所有租户及其系统资源'), ('tenant_admin', '租户管理员', '租户管理员管理租户下资源'), ('tenant_dev', '租户开发者', '租户开发者');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记(0:正常;1:已删除)',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
--  Table structure for `tenant`
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '租户ID',
  `del_flag` char(1) NOT NULL COMMENT '删除标记(0:正常;1:已删除)',
  `tenant_name` varchar(50) NOT NULL COMMENT '租户名称',
  `industry` varchar(100) DEFAULT NULL COMMENT '所属行业',
  `contact_people` varchar(100) DEFAULT NULL COMMENT '联系人',
  `contact_mobile` varchar(100) DEFAULT NULL COMMENT '联系电话',
  `description` varchar(200) DEFAULT NULL COMMENT '租户介绍',
  `secret_name` varchar(100) NOT NULL COMMENT '租户秘钥name',
  `secret_key` varchar(100) NOT NULL COMMENT '租户秘钥key',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_name` (`tenant_name`) USING BTREE,
  UNIQUE KEY `secret_name` (`secret_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='租户表';

-- ----------------------------
--  Table structure for `trigger`
-- ----------------------------
DROP TABLE IF EXISTS `trigger`;
CREATE TABLE `trigger` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_id` bigint(20) NOT NULL COMMENT '产品ID',
  `name` varchar(100) NOT NULL COMMENT '触发器名称',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_name` (`product_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='触发器';

-- ----------------------------
--  Table structure for `trigger_condition`
-- ----------------------------
DROP TABLE IF EXISTS `trigger_condition`;
CREATE TABLE `trigger_condition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `trigger_id` bigint(20) NOT NULL COMMENT '触发器ID',
  `left_operand` varchar(256) NOT NULL COMMENT '左操作数表达式，例如${data_point1}*2000',
  `operator` tinyint(4) NOT NULL COMMENT '操作符(0:＝,1:>,2:<,3:>=,4:<=,5:<>)',
  `right_operand` varchar(256) NOT NULL COMMENT '右操作数表达式，例如${data_point3}+2000',
  `connect_type` tinyint(4) DEFAULT '0' COMMENT '连接方式(0:与,1:或)',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `trigger_id` (`trigger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='触发器触发条件';

-- ----------------------------
--  Table structure for `trigger_object`
-- ----------------------------
DROP TABLE IF EXISTS `trigger_object`;
CREATE TABLE `trigger_object` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `trigger_id` bigint(20) NOT NULL COMMENT '触发器ID',
  `object_type` tinyint(4) DEFAULT '0' COMMENT '类型(0:设备,1:设备组)',
  `object_id` bigint(20) NOT NULL COMMENT '设备ID或者设备组id，当类型是设备组，0代表所有设备',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `trigger_id` (`trigger_id`,`object_type`,`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='触发器作用对象';

-- ----------------------------
--  Table structure for `trigger_result`
-- ----------------------------
DROP TABLE IF EXISTS `trigger_result`;
CREATE TABLE `trigger_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `trigger_id` bigint(20) NOT NULL COMMENT '触发器ID',
  `result_type` tinyint(4) DEFAULT '0' COMMENT '结果类型(0:邮件通知,1:url)',
  `email` varchar(256) DEFAULT NULL COMMENT '邮箱地址',
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `trigger_id` (`trigger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='触发器触发结果';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `del_flag` char(1) NOT NULL COMMENT '删除标记(0:正常;1:已删除)',
  `name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `status` char(1) NOT NULL COMMENT '用户状态(0:未激活;1:正常;2:冻结)',
  `user_type` char(1) NOT NULL COMMENT '用户类型(0:系统管理用户;1:租户用户)',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
--  Table structure for `user_system_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_system_role`;
CREATE TABLE `user_system_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户表ID',
  `role_code` varchar(64) NOT NULL COMMENT '系统角色标识',
  PRIMARY KEY (`user_id`,`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统管理员角色权限表';

-- ----------------------------
--  Table structure for `user_teant`
-- ----------------------------
DROP TABLE IF EXISTS `user_teant`;
CREATE TABLE `user_teant` (
  `user_id` bigint(20) NOT NULL COMMENT '用户表ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户表ID',
  PRIMARY KEY (`user_id`,`tenant_id`),
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户租户表';

-- ----------------------------
--  Table structure for `user_tenant_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_tenant_role`;
CREATE TABLE `user_tenant_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户表ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `role_code` varchar(64) NOT NULL COMMENT '角色标识',
  PRIMARY KEY (`user_id`,`tenant_id`,`role_code`),
  KEY `tenant_id` (`tenant_id`) USING BTREE,
  KEY `user_tenant_id` (`user_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户在租户中角色权限表';

SET FOREIGN_KEY_CHECKS = 1;

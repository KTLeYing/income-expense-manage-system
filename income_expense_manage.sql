/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : income_expense_manage

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2021-12-27 10:08:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员自增id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `avatar_path` varchar(255) DEFAULT NULL COMMENT '头像路径',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for budget
-- ----------------------------
DROP TABLE IF EXISTS `budget`;
CREATE TABLE `budget` (
  `budget_id` int NOT NULL AUTO_INCREMENT COMMENT '预算自增id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `num` double DEFAULT NULL COMMENT '预算数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`budget_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预算表';

-- ----------------------------
-- Records of budget
-- ----------------------------

-- ----------------------------
-- Table structure for i_e_category
-- ----------------------------
DROP TABLE IF EXISTS `i_e_category`;
CREATE TABLE `i_e_category` (
  `i_e_category_id` int NOT NULL AUTO_INCREMENT COMMENT '收支类型自增id',
  `parent_category` varchar(255) DEFAULT NULL COMMENT '父收支类型',
  `son_category` varchar(255) DEFAULT NULL COMMENT '子收支类型',
  PRIMARY KEY (`i_e_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收支类型';

-- ----------------------------
-- Records of i_e_category
-- ----------------------------

-- ----------------------------
-- Table structure for i_e_record
-- ----------------------------
DROP TABLE IF EXISTS `i_e_record`;
CREATE TABLE `i_e_record` (
  `i_e_record_id` int NOT NULL AUTO_INCREMENT COMMENT '收支记录自增id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `i_c_category_id` int DEFAULT NULL COMMENT '收支类型id',
  `num` int DEFAULT NULL COMMENT '收支数量(钱数)',
  `note` varchar(255) DEFAULT NULL COMMENT '收支备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`i_e_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收支记录表';

-- ----------------------------
-- Records of i_e_record
-- ----------------------------

-- ----------------------------
-- Table structure for memorandum
-- ----------------------------
DROP TABLE IF EXISTS `memorandum`;
CREATE TABLE `memorandum` (
  `memorandum_id` int NOT NULL AUTO_INCREMENT COMMENT '备忘录自增id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `save_path` varchar(255) DEFAULT NULL COMMENT '文件保存路径',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间/创建时间',
  PRIMARY KEY (`memorandum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='备忘录表';

-- ----------------------------
-- Records of memorandum
-- ----------------------------

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `news_id` int NOT NULL AUTO_INCREMENT COMMENT '新闻自增id',
  `title` varchar(255) DEFAULT NULL COMMENT '新闻标题',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键词',
  `visit_count` bigint DEFAULT NULL COMMENT '访问数',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻表';

-- ----------------------------
-- Records of news
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户自增id',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `sex` tinyint DEFAULT NULL COMMENT '性别，1:男 2:女',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `avatar_path` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `banned` tinyint DEFAULT '1' COMMENT '是否已经被禁用，1:未被禁用   0: 已被禁用',
  `deleted` tinyint DEFAULT '1' COMMENT '是否已经删除，1：未删除  0: 已删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'mzl', '43f115574c1bc6457e215d9ca5a08910c477868778a8e71e', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 19:35:13', '2021-12-24 19:36:31');
INSERT INTO `user` VALUES ('2', 'sky', '43f115574c1bc6457e215d9ca5a08910c477868778a8e71e', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 19:35:13', '2021-12-24 12:47:06');
INSERT INTO `user` VALUES ('3', 'lisi', '43f115574c1bc6457e215d9ca5a08910c477868778a8e71e', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 19:35:13', '2021-12-24 19:36:31');
INSERT INTO `user` VALUES ('4', 'wangwu', 'f6535896342371b582d4408f22f745d8af30a4e77fb35005', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 20:33:31', '2021-12-24 20:33:31');
INSERT INTO `user` VALUES ('5', 'lili', '358448c4393ac4b389c69185773565d45c89320803d25571', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 20:48:53', '2021-12-24 20:48:53');
INSERT INTO `user` VALUES ('6', 'yier', 'd3f869652206c7737f69918763bf4fb0fb19b16b0c54e758', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 20:57:07', '2021-12-24 12:57:52');
INSERT INTO `user` VALUES ('7', 'yisan', '06a73da8c64036e94e70e30aa99465f1801d44ff8ab9c41c', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:08:24', '2021-12-24 21:08:24');
INSERT INTO `user` VALUES ('8', 'wuwu', '754206e8bc95d5ef66e5253a65196727e26b76774ad0df09', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:10:49', '2021-12-24 21:11:57');
INSERT INTO `user` VALUES ('9', 'erer', 'f82208e91d40209d0f241c3cc50798770744a51692636b9c', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:44:16', '2021-12-24 13:58:16');
INSERT INTO `user` VALUES ('10', 'www', '28ea86e9e10920212880338553e72dc5b445b76a4873452a', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:58:47', '2021-12-24 13:59:46');

-- ----------------------------
-- Table structure for wish_list
-- ----------------------------
DROP TABLE IF EXISTS `wish_list`;
CREATE TABLE `wish_list` (
  `wish_list_id` int NOT NULL AUTO_INCREMENT COMMENT '许愿单自增id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) DEFAULT NULL COMMENT '心愿单名',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '心愿单内容',
  `num` double DEFAULT NULL COMMENT '心愿的钱数',
  `state` tinyint DEFAULT NULL COMMENT '状态，0：未完成  1: 完成',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`wish_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='心愿单表';

-- ----------------------------
-- Records of wish_list
-- ----------------------------

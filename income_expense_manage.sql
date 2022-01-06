/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : income_expense_manage

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2022-01-05 11:10:37
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
  `type` tinyint DEFAULT '1' COMMENT '1：月预算   2: 年预算',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`budget_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='预算表';

-- ----------------------------
-- Records of budget
-- ----------------------------

-- ----------------------------
-- Table structure for i_e_category
-- ----------------------------
DROP TABLE IF EXISTS `i_e_category`;
CREATE TABLE `i_e_category` (
  `i_e_category_id` int NOT NULL AUTO_INCREMENT COMMENT '收支类型自增id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `parent_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父收支类型，只有 收入  和  支出两种',
  `son_category` varchar(255) DEFAULT NULL COMMENT '子收支类型',
  PRIMARY KEY (`i_e_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='收支类型';

-- ----------------------------
-- Records of i_e_category
-- ----------------------------
INSERT INTO `i_e_category` VALUES ('1', '1', '收入', '餐补');
INSERT INTO `i_e_category` VALUES ('2', '1', '收入', '工资');
INSERT INTO `i_e_category` VALUES ('3', '1', '支出', '伙食费');
INSERT INTO `i_e_category` VALUES ('4', '1', '支出', '淘宝');
INSERT INTO `i_e_category` VALUES ('5', '1', '支出', '京东');
INSERT INTO `i_e_category` VALUES ('6', '1', '收入', '兼职');
INSERT INTO `i_e_category` VALUES ('7', '1', '收入', '红包');
INSERT INTO `i_e_category` VALUES ('8', '1', '收入', '学校奖学金');
INSERT INTO `i_e_category` VALUES ('9', '1', '收入', '国家奖学金');
INSERT INTO `i_e_category` VALUES ('10', '1', '收入', '国家励志奖学金');
INSERT INTO `i_e_category` VALUES ('11', '1', '支出', '房租');
INSERT INTO `i_e_category` VALUES ('12', '1', '支出', '水费');
INSERT INTO `i_e_category` VALUES ('13', '1', '支出', '电费');
INSERT INTO `i_e_category` VALUES ('15', '1', '支出', '其他');
INSERT INTO `i_e_category` VALUES ('18', '1', '收入', '其他');

-- ----------------------------
-- Table structure for i_e_record
-- ----------------------------
DROP TABLE IF EXISTS `i_e_record`;
CREATE TABLE `i_e_record` (
  `i_e_record_id` int NOT NULL AUTO_INCREMENT COMMENT '收支记录自增id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `i_e_category_id` int DEFAULT NULL COMMENT '收支类型id',
  `num` double DEFAULT NULL COMMENT '收支数量(钱数)',
  `note` varchar(255) DEFAULT NULL COMMENT '收支备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`i_e_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='收支记录表';

-- ----------------------------
-- Records of i_e_record
-- ----------------------------
INSERT INTO `i_e_record` VALUES ('1', '1', '1', '1000', '2021.12的餐补', '2021-10-30 07:09:52');
INSERT INTO `i_e_record` VALUES ('2', '1', '1', '1000', '2021.11的餐补', '2021-12-23 07:15:58');
INSERT INTO `i_e_record` VALUES ('3', '1', '2', '7000', '2021.11的工资', '2021-12-30 07:16:42');
INSERT INTO `i_e_record` VALUES ('4', '1', '2', '18000', '2021.10的工资', '2020-12-30 07:16:47');
INSERT INTO `i_e_record` VALUES ('5', '1', '3', '1500', '2021.10的伙食费', '2021-12-22 07:17:31');
INSERT INTO `i_e_record` VALUES ('6', '1', '3', '1200', '2021.12的伙食费', '2020-12-26 07:17:40');
INSERT INTO `i_e_record` VALUES ('7', '1', '4', '500', '买鞋', '2020-10-28 07:17:54');
INSERT INTO `i_e_record` VALUES ('8', '1', '4', '200', '买香水', '2021-12-30 07:18:08');
INSERT INTO `i_e_record` VALUES ('9', '1', '4', '20', '买手机套', '2021-11-30 07:18:20');
INSERT INTO `i_e_record` VALUES ('10', '1', '5', '4500', '买手机', '2021-12-30 07:18:54');
INSERT INTO `i_e_record` VALUES ('11', '1', '5', '8000', '买相机', '2021-12-30 07:19:24');
INSERT INTO `i_e_record` VALUES ('12', '1', '4', '88', '买枕头', '2021-12-21 07:19:40');
INSERT INTO `i_e_record` VALUES ('14', '1', '10', '5000', '大四国家励志奖学金', '2011-11-30 07:24:50');
INSERT INTO `i_e_record` VALUES ('15', '1', '12', '36.7', '2021.11水费', '2021-12-28 07:25:41');
INSERT INTO `i_e_record` VALUES ('16', '1', '12', '40.58', '2021.12水费', '2021-12-30 07:27:21');
INSERT INTO `i_e_record` VALUES ('20', '1', '12', '40.58', '2021.12水费', '2021-12-31 08:41:18');
INSERT INTO `i_e_record` VALUES ('21', '1', '12', '31', '2021.12水费', '2021-12-31 08:41:31');
INSERT INTO `i_e_record` VALUES ('22', '1', '2', '7000', '2021.12工资', '2021-12-31 08:42:00');
INSERT INTO `i_e_record` VALUES ('23', '1', '2', '9000', '2021.12餐补', '2021-12-31 08:42:12');
INSERT INTO `i_e_record` VALUES ('24', '1', '2', '1000', '2021.08餐补', '2021-08-09 06:17:18');
INSERT INTO `i_e_record` VALUES ('25', '1', '2', '9000', '2021.12餐补', '2022-01-04 02:34:12');
INSERT INTO `i_e_record` VALUES ('26', '1', '2', '500', '2021.08餐补', '2021-08-09 06:17:18');
INSERT INTO `i_e_record` VALUES ('27', '1', '3', '3000', '2021.08伙食费', '2021-08-09 06:17:18');
INSERT INTO `i_e_record` VALUES ('28', '1', '3', '3000', '2021.07伙食费', '2021-07-09 06:17:18');
INSERT INTO `i_e_record` VALUES ('29', '1', '3', '1000', '2021.07伙食费', '2021-07-09 06:17:18');
INSERT INTO `i_e_record` VALUES ('30', '1', '1', '3000', '2021.07餐补', '2021-07-09 06:17:18');
INSERT INTO `i_e_record` VALUES ('31', '1', '2', '10000', '2021.07工资', '2021-07-20 06:17:18');
INSERT INTO `i_e_record` VALUES ('32', '1', '9', '8000', '2021的国家奖学金', '2021-07-25 06:17:18');
INSERT INTO `i_e_record` VALUES ('33', '1', '18', '3000', '2021.07的其他', '2021-07-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('34', '1', '18', '3000', '2021.07的其他', '2012-07-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('35', '1', '18', '9000', '2012.07的其他', '2012-07-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('36', '1', '1', '1000', '2012.08的餐补', '2012-07-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('37', '1', '1', '3000', '2011.07的餐补', '2011-07-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('38', '1', '18', '3000', '2011.06的其他', '2011-06-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('39', '1', '10', '5000', '2011.06的国家励志奖学金', '2011-06-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('40', '1', '9', '10000', '2012.12的国家奖学金', '2012-12-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('41', '1', '3', '2000', '2011.11的伙食费', '2011-11-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('42', '1', '3', '5000', '2012.11的伙食费', '2012-11-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('43', '1', '4', '800', '2012.10的护肤品', '2012-10-16 06:17:18');
INSERT INTO `i_e_record` VALUES ('44', '1', '4', '300', '2011.10的护肤品', '2011-10-16 06:17:18');

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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'mzl', 'f3e89b784335e8a75839e29d367949a97970280899f7dc0a', '1', '2198902814@qq.com', '13652707142', 'http://localhost:8080/ddgh/ttthhh.jpg', '1', '1', '2021-12-24 19:35:13', '2022-01-04 01:58:02');
INSERT INTO `user` VALUES ('2', 'sky', '43f115574c1bc6457e215d9ca5a08910c477868778a8e71e', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 19:35:13', '2021-12-24 12:47:06');
INSERT INTO `user` VALUES ('3', 'lisi', '43f115574c1bc6457e215d9ca5a08910c477868778a8e71e', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 19:35:13', '2021-12-24 19:36:31');
INSERT INTO `user` VALUES ('4', 'wangwu', 'f6535896342371b582d4408f22f745d8af30a4e77fb35005', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 20:33:31', '2021-12-24 20:33:31');
INSERT INTO `user` VALUES ('5', 'lili', '358448c4393ac4b389c69185773565d45c89320803d25571', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 20:48:53', '2021-12-24 20:48:53');
INSERT INTO `user` VALUES ('6', 'yier', 'd3f869652206c7737f69918763bf4fb0fb19b16b0c54e758', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 20:57:07', '2021-12-24 12:57:52');
INSERT INTO `user` VALUES ('7', 'yisan', '06a73da8c64036e94e70e30aa99465f1801d44ff8ab9c41c', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:08:24', '2021-12-24 21:08:24');
INSERT INTO `user` VALUES ('8', 'wuwu', '754206e8bc95d5ef66e5253a65196727e26b76774ad0df09', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:10:49', '2021-12-24 21:11:57');
INSERT INTO `user` VALUES ('9', 'erer', 'f82208e91d40209d0f241c3cc50798770744a51692636b9c', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:44:16', '2021-12-24 13:58:16');
INSERT INTO `user` VALUES ('10', 'www', '28ea86e9e10920212880338553e72dc5b445b76a4873452a', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-24 21:58:47', '2021-12-24 13:59:46');
INSERT INTO `user` VALUES ('11', 'yili', '31ff1cc86c3706b22cb1ec9d28547ca27759016d91d0bc08', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 02:26:08', '2021-12-27 02:32:02');
INSERT INTO `user` VALUES ('12', 'tutu', 'a8143e544a31a8a49f84a79824e80982f05ea6418ef00401', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 10:32:55', '2021-12-27 02:35:53');
INSERT INTO `user` VALUES ('13', 'rr', '69653d825e03191739172843942e4c362e24e16a5a135066', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 10:41:38', '2021-12-27 02:42:34');
INSERT INTO `user` VALUES ('14', 'qq', '968d11f3e191f0440a80b984f5b87ba9812bd3cf9591cf01', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 10:45:29', '2021-12-27 02:46:36');
INSERT INTO `user` VALUES ('15', 'yy', 'c85d8348b88848534e71247975ad25f5343016b79bb0fb15', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 02:48:54', '2021-12-27 02:48:54');
INSERT INTO `user` VALUES ('16', 'w1', 'c85d8348b88848534e71247975ad25f5343016b79bb0fb15', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 10:51:03', '2021-12-27 10:51:03');
INSERT INTO `user` VALUES ('17', 'ee', '216a7400e13784c134c25744110027405f1be9be9655ff65', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 11:15:59', '2021-12-27 03:23:01');
INSERT INTO `user` VALUES ('18', 'e1', 'c91300e4c40b31482e337f84f1059325019252a44109d147', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 03:20:00', '2021-12-27 03:23:42');
INSERT INTO `user` VALUES ('19', 'e2', 'c95d8f81202bd81764e8663d30e75bb62f33c4dd5c76d908', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 03:21:55', '2021-12-27 03:22:16');
INSERT INTO `user` VALUES ('20', 'e3', '22543c43555f696263f15c51a1ff6bc9d07178b305782b02', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 04:27:09', '2021-12-27 04:27:39');
INSERT INTO `user` VALUES ('21', 'e4', '746670d7889228b045b0345ae4088ea9c110539f9047be0f', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 04:56:23', '2021-12-27 05:02:43');
INSERT INTO `user` VALUES ('22', 'e5', 'd3e243094b84b0d218677c6105e48da26b11387d8930d99d', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 05:14:56', '2021-12-27 05:15:24');
INSERT INTO `user` VALUES ('23', 'e6', '49ca6e42380fc0ad1d468f63644b8bf2d14b53578b08f073', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 05:42:24', '2021-12-27 05:43:26');
INSERT INTO `user` VALUES ('24', 'e8', '87df4019f029031422a1494056019ef63a78d2533ce61002', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 06:22:51', '2021-12-27 06:24:14');
INSERT INTO `user` VALUES ('25', 'e9', '18e994f7ae9c01982f59017d419575f9f239c46352928b5c', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 14:40:49', '2021-12-27 06:41:53');
INSERT INTO `user` VALUES ('26', 'e10', 'd46f4fa5280624d94903638c85c511e9323bf28f9385ee7d', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 14:45:59', '2021-12-27 06:46:24');
INSERT INTO `user` VALUES ('27', 'e11', '98dd8db4f611f25470d19293284f5197ec77a0ac6552d644', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 07:10:29', '2021-12-27 07:10:29');
INSERT INTO `user` VALUES ('28', 'e12', '43c20752eb40a91673f86e7872312da27c3d963511332602', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 07:11:43', '2021-12-27 07:12:24');
INSERT INTO `user` VALUES ('29', 'e13', '66590933a13e33929015ad8f817d2c82ce23098d5d85e19c', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 07:14:18', '2021-12-27 07:14:47');
INSERT INTO `user` VALUES ('30', 'e14', '695f0e89e17279ac38924a5d563d46a6141286620da4d003', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 15:15:55', '2021-12-27 07:17:22');
INSERT INTO `user` VALUES ('31', 'e15', 'b8897ad5532c607906c28c9dd63c22b3617965a78078d745', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 07:29:21', '2021-12-27 07:29:54');
INSERT INTO `user` VALUES ('32', 'mazl', 'b1b25d54ec4bb19336c9aa0c73889d914a3158f72568873a', '1', '2198902813@qq.com', '13652707141', 'http://localhost:8080/ddgh/gg.jpg', '1', '1', '2021-12-27 12:56:58', '2021-12-27 12:56:58');
INSERT INTO `user` VALUES ('33', 'lolo', 'a5bc2ae62425d66094701d20695c27b46788a32a2110d720', '1', '2198902813@qq.com', '13652707141', 'http://localhostr:8080/fgfg.png', '1', '1', '2021-12-27 13:34:17', '2021-12-27 13:42:34');
INSERT INTO `user` VALUES ('34', 'lol', '22a65702546681f719b1df9ce4183c96749a470268b6cc95', '1', '2198902815@qq.com', '13652707144', 'http://localhostr:8080/fgfg.png', '1', '1', '2021-12-27 14:59:29', '2021-12-27 14:59:29');
INSERT INTO `user` VALUES ('35', 'loli', '56332018478a23f097460a51387a8e69f740c4747935b79d', '1', '2198902816@qq.com', '13652707143', 'http://localhostr:8080/fgfg.png', '1', '1', '2021-12-27 15:00:29', '2021-12-27 15:00:29');
INSERT INTO `user` VALUES ('36', 'lolu', '98b435e23801e1a784398d20b5d907e70a6733b99011337a', '1', '2198902817@qq.com', '13652707147', 'http://localhostr:8080/fgfg.png', '1', '1', '2021-12-27 15:00:45', '2021-12-27 15:00:45');
INSERT INTO `user` VALUES ('37', 'go', 'b3974d21f59776cd6b79f32406a98ff65423f90114509746', '1', '2198902819@qq.com', '13652707149', 'http://localhostr:8080/fgfg.png', '1', '1', '2021-12-29 05:40:43', '2021-12-29 05:42:17');

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

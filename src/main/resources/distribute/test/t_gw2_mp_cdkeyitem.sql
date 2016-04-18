/*
Navicat MySQL Data Transfer

Source Server         : 172.19.20.2--admin--JMMKbFcCDH
Source Server Version : 50523
Source Host           : 172.19.20.2:3309
Source Database       : gw2_dianquan

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2016-04-18 09:52:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gw2_mp_cdkeyitem
-- ----------------------------
DROP TABLE IF EXISTS `t_gw2_mp_cdkeyitem`;
CREATE TABLE `t_gw2_mp_cdkeyitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `price` int(11) DEFAULT NULL COMMENT ' 商品价格',
  `pics` varchar(500) DEFAULT NULL COMMENT '商品图片url地址',
  `msg` varchar(500) DEFAULT NULL COMMENT '商品描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*
Navicat MySQL Data Transfer

Source Server         : 172.19.20.2--admin--JMMKbFcCDH
Source Server Version : 50523
Source Host           : 172.19.20.2:3309
Source Database       : gw2_dianquan

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2016-04-18 09:53:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gw2_mp_winning
-- ----------------------------
DROP TABLE IF EXISTS `t_gw2_mp_winning`;
CREATE TABLE `t_gw2_mp_winning` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `termid` int(11) DEFAULT NULL COMMENT '某一个商品第几期的ID',
  `itemname` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip地址',
  `cdkey` varchar(255) DEFAULT NULL COMMENT '获奖之后拿到的cdkey',
  `orderid` varchar(50) DEFAULT NULL COMMENT '订单号',
  `createdatetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_itemid` (`termid`) USING BTREE,
  KEY `index_account` (`account`) USING BTREE,
  KEY `index_itemname` (`itemname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13581 DEFAULT CHARSET=utf8;

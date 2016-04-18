/*
Navicat MySQL Data Transfer

Source Server         : 172.19.20.2--admin--JMMKbFcCDH
Source Server Version : 50523
Source Host           : 172.19.20.2:3309
Source Database       : gw2_dianquan

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2016-04-18 09:52:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gw2_mp_term
-- ----------------------------
DROP TABLE IF EXISTS `t_gw2_mp_term`;
CREATE TABLE `t_gw2_mp_term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `servings` int(11) DEFAULT NULL COMMENT '商品的份数，例如98份',
  `auctionservings` int(11) DEFAULT NULL COMMENT '被拍的份数',
  `price` int(11) DEFAULT NULL COMMENT '商品的价格',
  `itemid` int(11) DEFAULT NULL COMMENT '商品字典表ID',
  `number` int(11) DEFAULT NULL COMMENT '第几期',
  `st` tinyint(4) DEFAULT NULL,
  `createdatetime` datetime DEFAULT NULL COMMENT '创建的时间',
  PRIMARY KEY (`id`),
  KEY `index_id` (`id`) USING BTREE,
  KEY `index_item_id` (`itemid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13589 DEFAULT CHARSET=utf8;

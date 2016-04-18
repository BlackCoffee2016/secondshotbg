/*
Navicat MySQL Data Transfer

Source Server         : 172.19.20.2--admin--JMMKbFcCDH
Source Server Version : 50523
Source Host           : 172.19.20.2:3309
Source Database       : gw2_dianquan

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2016-04-18 09:52:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gw2_mp_term_detailed
-- ----------------------------
DROP TABLE IF EXISTS `t_gw2_mp_term_detailed`;
CREATE TABLE `t_gw2_mp_term_detailed` (
  `termid` int(11) NOT NULL COMMENT '某一个商品的期数ID',
  `keynumber` int(11) NOT NULL COMMENT '如果是98份，那么key就是从1到98之间的值',
  `username` varchar(255) DEFAULT NULL COMMENT '账号',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `orderid` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `ip` varchar(255) DEFAULT NULL COMMENT 'IP地址',
  `createdatetime` datetime DEFAULT NULL COMMENT '创建时间',
  `itemid` int(11) DEFAULT NULL,
  PRIMARY KEY (`termid`,`keynumber`),
  KEY `index_termid` (`termid`) USING BTREE,
  KEY `index_orderid` (`orderid`) USING BTREE,
  KEY `index_username` (`username`) USING BTREE,
  KEY `index_keynumber` (`keynumber`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

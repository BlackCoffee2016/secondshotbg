/*
Navicat MySQL Data Transfer

Source Server         : 172.19.20.2--admin--JMMKbFcCDH
Source Server Version : 50523
Source Host           : 172.19.20.2:3309
Source Database       : gw2_dianquan

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2016-04-18 09:52:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gw2_mp_history
-- ----------------------------
DROP TABLE IF EXISTS `t_gw2_mp_history`;
CREATE TABLE `t_gw2_mp_history` (
  `orderid` varchar(50) NOT NULL COMMENT '订单编号',
  `termid` int(11) NOT NULL COMMENT '某一个商品第几期的ID',
  `itemname` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `count` int(11) DEFAULT NULL COMMENT '秒拍的数量',
  `price` int(11) DEFAULT NULL COMMENT '价格',
  `orderdate` datetime DEFAULT NULL COMMENT '订单创建的时间',
  `termdate` datetime DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  PRIMARY KEY (`orderid`,`termid`),
  KEY `index_account` (`account`) USING BTREE,
  KEY `index_termid` (`termid`) USING BTREE,
  KEY `index_termdate` (`termdate`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

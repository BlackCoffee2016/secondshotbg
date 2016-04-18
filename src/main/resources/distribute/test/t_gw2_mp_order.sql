/*
Navicat MySQL Data Transfer

Source Server         : 172.19.20.2--admin--JMMKbFcCDH
Source Server Version : 50523
Source Host           : 172.19.20.2:3309
Source Database       : gw2_dianquan

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2016-04-18 09:52:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gw2_mp_order
-- ----------------------------
DROP TABLE IF EXISTS `t_gw2_mp_order`;
CREATE TABLE `t_gw2_mp_order` (
  `orderid` varchar(50) NOT NULL COMMENT '订单编号',
  `account` varchar(50) DEFAULT NULL COMMENT '空中网账号',
  `createdate` datetime DEFAULT NULL COMMENT '创建日期',
  `price` int(11) DEFAULT '1' COMMENT '订单的价格，默认为1元',
  `termid` int(11) DEFAULT NULL COMMENT '某一个商品对应第几期的编号',
  `count` int(11) DEFAULT NULL COMMENT '秒拍的数量',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态 0 订单初始状态 1 支付成功 2秒杀成功',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip地址',
  `lastupdatetime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `cdkeytype` tinyint(4) DEFAULT NULL COMMENT '秒杀的cdkey类型 1基础版98  2数字豪华版198 3数字典藏版498  与商品字典表中的ID一致',
  `paytype` bigint(255) DEFAULT NULL COMMENT '支付类型',
  PRIMARY KEY (`orderid`),
  KEY `index_orderid` (`orderid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

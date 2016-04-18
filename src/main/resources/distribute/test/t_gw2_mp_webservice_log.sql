/*
Navicat MySQL Data Transfer

Source Server         : 172.19.20.2--admin--JMMKbFcCDH
Source Server Version : 50523
Source Host           : 172.19.20.2:3309
Source Database       : gw2_dianquan

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2016-04-18 09:53:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_gw2_mp_webservice_log
-- ----------------------------
DROP TABLE IF EXISTS `t_gw2_mp_webservice_log`;
CREATE TABLE `t_gw2_mp_webservice_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logtime` datetime DEFAULT NULL COMMENT '日志时间',
  `username` varchar(255) DEFAULT NULL COMMENT '空中网账号',
  `method` varchar(50) DEFAULT NULL COMMENT '调用接口的方法',
  `params` text COMMENT '参数',
  `result` text COMMENT '接口调用返回接口',
  `orderid` varchar(50) DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163761 DEFAULT CHARSET=utf8;

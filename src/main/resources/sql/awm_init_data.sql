/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : awm

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 02/10/2018 17:47:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_base_code
-- ----------------------------
DROP TABLE IF EXISTS `t_base_code`;
CREATE TABLE `t_base_code`  (
  `uuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_base_code
-- ----------------------------
INSERT INTO `t_base_code` VALUES ('1', '1', '上架', 'GoodsStatus');
INSERT INTO `t_base_code` VALUES ('2', '2', '折扣', 'GoodsStatus');
INSERT INTO `t_base_code` VALUES ('3', '3', '缺货', 'GoodsStatus');
INSERT INTO `t_base_code` VALUES ('4', '0', '下架', 'GoodsStatus');

-- ----------------------------
-- Table structure for t_catalog
-- ----------------------------
DROP TABLE IF EXISTS `t_catalog`;
CREATE TABLE `t_catalog`  (
  `uuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `level` int(3) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_catalog
-- ----------------------------
INSERT INTO `t_catalog` VALUES ('10', '10', '婴幼儿奶粉', 1);
INSERT INTO `t_catalog` VALUES ('20', '20', '保健品', 1);
INSERT INTO `t_catalog` VALUES ('30', '30', 'UGG', 1);
INSERT INTO `t_catalog` VALUES ('32', '50', '32', 1);
INSERT INTO `t_catalog` VALUES ('33', '60', '说是', 1);
INSERT INTO `t_catalog` VALUES ('43', '70', '说是', 1);
INSERT INTO `t_catalog` VALUES ('54', '80', '12', 1);
INSERT INTO `t_catalog` VALUES ('e615565ceb274e9f8fbfa6cd4283cb38', '90', '5666', 1);

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file`  (
  `uuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `suffix` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_file
-- ----------------------------
INSERT INTO `t_file` VALUES ('0009104e-a7a5-4f38-8cf3-871edca4cbb7', 'C:\\development\\upfiles', '下载', 'jpg');
INSERT INTO `t_file` VALUES ('0b2f610a-574d-4d61-90e9-232a4ab4c76d', 'C:\\development\\upfiles', 'sfz2', 'jpg');
INSERT INTO `t_file` VALUES ('27a81579-5280-44b1-8eab-4310ffb9a825', 'C:\\development\\upfiles', 'sfz2', 'jpg');
INSERT INTO `t_file` VALUES ('2da58fb2-d76f-42a2-8ae7-894dc069b9eb', 'C:\\development\\upfiles', 'sfz1', 'jpg');
INSERT INTO `t_file` VALUES ('3a55a5a4-ebe2-4d26-b893-957a4c5197d1', 'C:\\development\\upfiles', 'sfz2', 'jpg');
INSERT INTO `t_file` VALUES ('43d45d73-6bad-45d1-ae37-a50e89f12c9c', 'C:\\development\\upfiles', '下载', 'jpg');
INSERT INTO `t_file` VALUES ('5fbbb58d-d8e0-459f-8077-aa62d777e056', 'C:\\development\\upfiles', 'sfz2', 'jpg');
INSERT INTO `t_file` VALUES ('614d19f3-b0de-4908-9ec9-e4091e41db87', 'C:\\development\\upfiles', '下载', 'jpg');
INSERT INTO `t_file` VALUES ('73b7263e-3295-422d-b0ac-acf8d2b64367', 'C:\\development\\upfiles', 'sfz1', 'jpg');
INSERT INTO `t_file` VALUES ('73dcbb6d-7fac-4e19-8ecb-df41ac662678', 'C:\\development\\upfiles', 'sfz2', 'jpg');
INSERT INTO `t_file` VALUES ('949468ad-9e83-47fb-8345-f7993b46df2f', 'C:\\development\\upfiles', 'yyzz', 'jpg');
INSERT INTO `t_file` VALUES ('9597cc1f-2e64-452b-8d50-d6b7c2b8c4cd', 'C:\\development\\upfiles', 'yyzz', 'jpg');
INSERT INTO `t_file` VALUES ('a866e70d-5adb-4c17-b9fa-e8ffb9842620', 'C:\\development\\upfiles', '下载', 'jpg');
INSERT INTO `t_file` VALUES ('ab6971e2-ac81-4834-ad5a-8d0f894a2430', 'C:\\development\\upfiles', 'sfz2', 'jpg');
INSERT INTO `t_file` VALUES ('be532bc9-fb24-4204-bba4-f6d35ea9cdd1', 'C:\\development\\upfiles', 'sfz1', 'jpg');
INSERT INTO `t_file` VALUES ('be5a969f-f51d-47d0-bae7-817e0671b410', 'C:\\development\\upfiles', '下载', 'jpg');
INSERT INTO `t_file` VALUES ('ec00d234-239c-4c30-bacf-1bf92192fcdb', 'C:\\development\\upfiles', '5', 'jpg');
INSERT INTO `t_file` VALUES ('fe60241b-6f12-4b59-952d-235b70381a11', 'C:\\development\\upfiles', '下载', 'jpg');

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `uuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `main_pic_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主图ID',
  `post_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递说明',
  `specification` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品状态GoodsStatus，0下架，1上架，2折扣，3缺货，',
  `retail_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '零售价格',
  `wholesale_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '代理价格',
  `update_time` datetime(0) NULL DEFAULT NULL,
  `catalog_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品分类ID',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('60e7829f96c4414a9a7813a9871710a6', '测试2', 'ec00d234-239c-4c30-bacf-1bf92192fcdb', '444', '23', '2', 3.00, 33.00, '2018-10-02 13:28:00', '10');
INSERT INTO `t_goods` VALUES ('6bccba53721a49b9b5b9e0bbbadee251', '爱他美1段', '3a55a5a4-ebe2-4d26-b893-957a4c5197d1', '22', '三生三世', '1', 12.00, 12.08, NULL, '10');
INSERT INTO `t_goods` VALUES ('90a9410f440941c2aea57cbc701aed3c', '123', '2da58fb2-d76f-42a2-8ae7-894dc069b9eb', '123', '123', '3', 123.00, 321.00, '2018-09-28 17:07:35', '10');
INSERT INTO `t_goods` VALUES ('c63500902d2042229892e98db39a2c77', '123', '73dcbb6d-7fac-4e19-8ecb-df41ac662678', '123', '123', '2', 123.00, 123.00, '2018-09-28 17:07:53', '10');
INSERT INTO `t_goods` VALUES ('d792cd70f4ed4c499026e8691be8113b', '123', '0b2f610a-574d-4d61-90e9-232a4ab4c76d', '123', '123', '3', 123.00, 123.00, '2018-09-28 17:07:46', '20');
INSERT INTO `t_goods` VALUES ('e29eea64ca6449a3aec6e3aa10e15036', '对对对', 'be532bc9-fb24-4204-bba4-f6d35ea9cdd1', NULL, '232', '2', 1222.00, 12.02, NULL, '20');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(3) NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

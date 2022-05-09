/*
 Navicat Premium Data Transfer

 Source Server         : 5.7
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : adopt_cat

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 25/04/2021 16:09:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章的id',
  `user_id` bigint(11) NOT NULL COMMENT '发表人的id（外键）',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `article_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `article_comment_count` int(11) NOT NULL COMMENT '评论数',
  `article_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '发表日期',
  `is_check` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员是否审核',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `article_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = sjis COLLATE = sjis_japanese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment_parent
-- ----------------------------
DROP TABLE IF EXISTS `comment_parent`;
CREATE TABLE `comment_parent`  (
  `comment_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论的id',
  `comment_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '评论发表时间',
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '哪篇文章下的评论（外键）',
  `user_id` bigint(11) NOT NULL COMMENT '谁发表的评论（外键）',
  `like_num` int(255) NOT NULL COMMENT '点赞数',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  CONSTRAINT `comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment_son
-- ----------------------------
DROP TABLE IF EXISTS `comment_son`;
CREATE TABLE `comment_son`  (
  `comment_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子评论的id',
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父评论的id',
  `from_id` bigint(255) NOT NULL COMMENT '发布评论的id',
  `to_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '被评论人的id',
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论的内容',
  `comment_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '评论时间',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `son_parent`(`parent_id`) USING BTREE,
  INDEX `sonn_user`(`from_id`) USING BTREE,
  CONSTRAINT `son_parent` FOREIGN KEY (`parent_id`) REFERENCES `comment_parent` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sonn_user` FOREIGN KEY (`from_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for find_cat
-- ----------------------------
DROP TABLE IF EXISTS `find_cat`;
CREATE TABLE `find_cat`  (
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章的id',
  `user_id` bigint(11) NOT NULL COMMENT '发表人的id（外键）',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章的标题',
  `article_comment_count` int(11) NOT NULL COMMENT '文章下面评论的总数',
  `article_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '文章发表日期',
  `cat_sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '猫的性别',
  `cat_age` int(11) NOT NULL COMMENT '猫的年龄',
  `lost_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '丢失的位置',
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `cat_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '猫咪描述',
  `cat_lost_date` date NOT NULL COMMENT '猫咪丢失时间',
  `cat_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '猫咪的照片',
  `province` int(255) NOT NULL COMMENT '所在省份',
  `is_check` int(11) NOT NULL COMMENT '管理员是否审核',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `province`(`province`) USING BTREE,
  CONSTRAINT `cat_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `find_cat_ibfk_1` FOREIGN KEY (`province`) REFERENCES `province` (`province_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = sjis COLLATE = sjis_japanese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for find_people
-- ----------------------------
DROP TABLE IF EXISTS `find_people`;
CREATE TABLE `find_people`  (
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章的id',
  `user_id` bigint(11) NOT NULL COMMENT '发布人的id（外键）',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章的标题',
  `article_comment_count` int(11) NOT NULL COMMENT '文章下面的评论数（不包括回复评论的）',
  `article_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '文章的发布日期',
  `cat_sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '猫的性别',
  `cat_age` int(11) NOT NULL COMMENT '猫的年龄',
  `province` int(11) NOT NULL COMMENT '所在省（外键）',
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `cat_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '猫的简介',
  `claim` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '领养要求',
  `cat_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '猫咪的照片',
  `is_check` int(255) NOT NULL COMMENT '管理员是否审核',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `province`(`province`) USING BTREE,
  CONSTRAINT `find_people_ibfk_1` FOREIGN KEY (`province`) REFERENCES `province` (`province_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `people_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`  (
  `province_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '省份的编号',
  `province_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份的名称',
  `parent_province_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  PRIMARY KEY (`province_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(11) NOT NULL COMMENT 'id',
  `user_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `user_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `user_birthday` date NOT NULL COMMENT '生日',
  `user_mode` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `user_role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user' COMMENT '权限',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

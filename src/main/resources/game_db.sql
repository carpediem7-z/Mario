/*
 Navicat Premium Dump SQL

 Source Server         : ym
 Source Server Type    : MySQL
 Source Server Version : 50719 (5.7.19)
 Source Host           : localhost:3306
 Source Schema         : game_db

 Target Server Type    : MySQL
 Target Server Version : 50719 (5.7.19)
 File Encoding         : 65001

 Date: 30/12/2025 11:08:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for game_saves
-- ----------------------------
DROP TABLE IF EXISTS `game_saves`;
CREATE TABLE `game_saves`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `save_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '默认存档',
  `save_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `save_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `game_saves_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of game_saves
-- ----------------------------
INSERT INTO `game_saves` VALUES (5, 1, '进度1', '{\"level\":1, \"score\":100}', '2025-11-02 15:36:05');
INSERT INTO `game_saves` VALUES (6, 2, '进度2', '{\"level\":2, \"score\":200}', '2025-11-02 15:36:05');
INSERT INTO `game_saves` VALUES (7, 3, '进度3', '{\"level\":3, \"score\":300}', '2025-11-02 15:36:05');

-- ----------------------------
-- Table structure for rank_list
-- ----------------------------
DROP TABLE IF EXISTS `rank_list`;
CREATE TABLE `rank_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL DEFAULT 0,
  `play_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `rank_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rank_list
-- ----------------------------
INSERT INTO `rank_list` VALUES (8, 1, 1000, '2025-11-02 15:36:05');
INSERT INTO `rank_list` VALUES (9, 2, 2000, '2025-11-02 15:36:05');
INSERT INTO `rank_list` VALUES (10, 3, 3000, '2025-11-02 15:36:05');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'test123', '$2b$10$JG4svNUJteDYIr0wIkw2ueJafRgGnJeAq3I0JIqDC5sM7dzz.GSj.', '2025-11-02 14:19:32');
INSERT INTO `users` VALUES (2, 'mario', '$2b$10$EixZaYbB.rK4fl8x2q4E5OQ6D6Zv5fF7G8H9J0K1L2M3N4O5P6Q7R8S9T0U', '2025-10-20 09:00:00');
INSERT INTO `users` VALUES (3, 'luigi', '$2b$10$EixZaYbB.rK4fl8x2q4E5OQ6D6Zv5fF7G8H9J0K1L2M3N4O5P6Q7R8S9T0U', '2025-10-20 10:00:00');
INSERT INTO `users` VALUES (4, 'peach', '$2b$10$EixZaYbB.rK4fl8x2q4E5OQ6D6Zv5fF7G8H9J0K1L2M3N4O5P6Q7R8S9T0U', '2025-10-20 11:00:00');
INSERT INTO `users` VALUES (5, 'user1', '$2b$10$EixZaYbB.rK4fl8x2q4E5OQ6D6Zv5fF7G8H9J0K1L2M3N4O5P6Q7R8S9T0U', '2025-11-02 15:36:05');
INSERT INTO `users` VALUES (6, 'user2', '$2b$10$EixZaYbB.rK4fl8x2q4E5OQ6D6Zv5fF7G8H9J0K1L2M3N4O5P6Q7R8S9T0U', '2025-11-02 15:36:05');
INSERT INTO `users` VALUES (7, 'user3', '$2b$10$EixZaYbB.rK4fl8x2q4E5OQ6D6Zv5fF7G8H9J0K1L2M3N4O5P6Q7R8S9T0U', '2025-11-02 15:36:05');

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for academy
-- ----------------------------
DROP TABLE IF EXISTS `academy`;
CREATE TABLE `academy` (
  `a_id` char(11) NOT NULL,
  `a_name` char(20) NOT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of academy
-- ----------------------------
BEGIN;
INSERT INTO `academy` VALUES ('a_1001', '计算机与软件学院');
INSERT INTO `academy` VALUES ('a_1002', '信息与商务管理学院');
COMMIT;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `c_id` char(11) NOT NULL,
  `c_name` char(20) NOT NULL,
  `c_num` int DEFAULT '0',
  `d_id` char(11) DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `department` (`d_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of class
-- ----------------------------
BEGIN;
INSERT INTO `class` VALUES ('c_1001', '计算机1班', 30, 'd_1001');
INSERT INTO `class` VALUES ('c_1002', '计算机2班', 30, 'd_1001');
INSERT INTO `class` VALUES ('c_1003', '软件1班', 30, 'd_1002');
INSERT INTO `class` VALUES ('c_1004', '电子商务1班', 30, 'd_1003');
COMMIT;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` char(11) NOT NULL,
  `course_name` char(20) NOT NULL,
  `course_type` char(4) DEFAULT NULL,
  `course_credit` int DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  CONSTRAINT `course_chk_1` CHECK (((`course_type` = _utf8mb4'选修') or (`course_type` = _utf8mb4'必修')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of course
-- ----------------------------
BEGIN;
INSERT INTO `course` VALUES ('cour_1001', '面向对象程序设计（Java）', '必修', 4);
INSERT INTO `course` VALUES ('cour_1002', '数据结构', '必修', 4);
INSERT INTO `course` VALUES ('cour_1003', '数据库原理', '必修', 4);
INSERT INTO `course` VALUES ('cour_1004', '3D动画', '选修', 2);
INSERT INTO `course` VALUES ('cour_1005', 'PS基础', '选修', 2);
COMMIT;

-- ----------------------------
-- Table structure for course_plan
-- ----------------------------
DROP TABLE IF EXISTS `course_plan`;
CREATE TABLE `course_plan` (
  `cp_year` char(20) NOT NULL,
  `cp_class` char(10) NOT NULL,
  `course_id` char(11) NOT NULL,
  `cp_week` char(11) DEFAULT NULL,
  PRIMARY KEY (`cp_year`,`cp_class`,`course_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_plan_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of course_plan
-- ----------------------------
BEGIN;
INSERT INTO `course_plan` VALUES ('20-21学年第一学期', '19', 'cour_1001', '18');
INSERT INTO `course_plan` VALUES ('20-21学年第一学期', '19', 'cour_1002', '18');
INSERT INTO `course_plan` VALUES ('20-21学年第一学期', '19', 'cour_1003', '18');
INSERT INTO `course_plan` VALUES ('20-21学年第一学期', '19', 'cour_1004', '18');
INSERT INTO `course_plan` VALUES ('20-21学年第一学期', '19', 'cour_1005', '16');
INSERT INTO `course_plan` VALUES ('20-21学年第二学期', '19', 'cour_1004', '16');
INSERT INTO `course_plan` VALUES ('20-21学年第二学期', '19', 'cour_1005', '18');
COMMIT;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `d_id` char(11) NOT NULL,
  `d_name` char(20) NOT NULL,
  `a_id` char(11) DEFAULT NULL,
  PRIMARY KEY (`d_id`),
  KEY `a_id` (`a_id`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`a_id`) REFERENCES `academy` (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO `department` VALUES ('d_1001', '计算机科学与技术', 'a_1001');
INSERT INTO `department` VALUES ('d_1002', '软件工程', 'a_1001');
INSERT INTO `department` VALUES ('d_1003', '电子商务', 'a_1002');
COMMIT;

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `tc_id` char(11) NOT NULL,
  `s_id` char(11) NOT NULL,
  `g_ordinary` float DEFAULT NULL,
  `g_final` float DEFAULT NULL,
  `g_sum` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`tc_id`,`s_id`),
  KEY `s_id` (`s_id`),
  CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `student` (`s_id`),
  CONSTRAINT `grade_ibfk_2` FOREIGN KEY (`tc_id`) REFERENCES `teaching_class` (`tc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of grade
-- ----------------------------
BEGIN;
INSERT INTO `grade` VALUES ('tc_1001', '1901010101', 40, 80, 88.00);
INSERT INTO `grade` VALUES ('tc_1001', '1901010102', 35, 90, 89.00);
INSERT INTO `grade` VALUES ('tc_1002', '1901010101', 0, 0, 0.00);
INSERT INTO `grade` VALUES ('tc_1003', '1901010101', NULL, NULL, NULL);
INSERT INTO `grade` VALUES ('tc_1004', '1901010101', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `u_id` char(11) NOT NULL,
  `u_password` char(20) NOT NULL,
  `u_status` char(4) DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  CONSTRAINT `s_user_chk_1` CHECK (((`u_status` = _utf8mb4'学生') or (`u_status` = _utf8mb4'教师')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of s_user
-- ----------------------------
BEGIN;
INSERT INTO `s_user` VALUES ('1901010101', '147258', '学生');
INSERT INTO `s_user` VALUES ('1901010102', '123456', '学生');
INSERT INTO `s_user` VALUES ('1901020101', '123456', '学生');
INSERT INTO `s_user` VALUES ('1901020102', '123456', '学生');
INSERT INTO `s_user` VALUES ('1901030101', '123456', '学生');
INSERT INTO `s_user` VALUES ('t_1001', '123456', '教师');
INSERT INTO `s_user` VALUES ('t_1002', '123456', '教师');
INSERT INTO `s_user` VALUES ('t_1003', '123456', '教师');
INSERT INTO `s_user` VALUES ('t_1004', '123456', '教师');
INSERT INTO `s_user` VALUES ('t_1005', '123456', '教师');
INSERT INTO `s_user` VALUES ('t_1006', '123456', '教师');
COMMIT;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `s_id` char(11) NOT NULL,
  `s_name` char(8) NOT NULL,
  `s_sex` char(2) DEFAULT NULL,
  `s_date` date DEFAULT NULL,
  `c_id` char(11) DEFAULT NULL,
  PRIMARY KEY (`s_id`),
  KEY `c_id` (`c_id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`c_id`) REFERENCES `class` (`c_id`),
  CONSTRAINT `student_chk_1` CHECK (((`s_sex` = _utf8mb4'男') or (`s_sex` = _utf8mb4'女')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO `student` VALUES ('1901010101', '令狐冲', '男', '2001-06-05', 'c_1001');
INSERT INTO `student` VALUES ('1901010102', '任盈盈', '女', '2001-05-04', 'c_1001');
INSERT INTO `student` VALUES ('1901020101', '孙尚香', '女', '2001-05-04', 'c_1003');
INSERT INTO `student` VALUES ('1901020102', '乔峰', '男', '1999-01-04', 'c_1003');
INSERT INTO `student` VALUES ('1901030101', '岳不群', '男', '1999-01-23', 'c_1004');
COMMIT;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `t_id` char(11) NOT NULL,
  `t_name` char(8) NOT NULL,
  `t_sex` char(2) DEFAULT NULL,
  `t_date` date DEFAULT NULL,
  PRIMARY KEY (`t_id`),
  CONSTRAINT `teacher_chk_1` CHECK (((`t_sex` = _utf8mb4'男') or (`t_sex` = _utf8mb4'女')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of teacher
-- ----------------------------
BEGIN;
INSERT INTO `teacher` VALUES ('t_1001', '风清扬', '男', '1990-05-04');
INSERT INTO `teacher` VALUES ('t_1002', '周芷若', '女', '1989-01-05');
INSERT INTO `teacher` VALUES ('t_1003', '灭绝师太', '女', '1969-01-04');
INSERT INTO `teacher` VALUES ('t_1004', '张无忌', '男', '1980-03-09');
INSERT INTO `teacher` VALUES ('t_1005', '虚竹', '男', '1985-04-15');
INSERT INTO `teacher` VALUES ('t_1006', '张三丰', '男', '1990-01-25');
COMMIT;

-- ----------------------------
-- Table structure for teacher_teaching_class
-- ----------------------------
DROP TABLE IF EXISTS `teacher_teaching_class`;
CREATE TABLE `teacher_teaching_class` (
  `t_id` char(11) NOT NULL,
  `tc_id` char(11) NOT NULL,
  PRIMARY KEY (`t_id`,`tc_id`),
  KEY `tc_id` (`tc_id`),
  CONSTRAINT `teacher_teaching_class_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`t_id`),
  CONSTRAINT `teacher_teaching_class_ibfk_2` FOREIGN KEY (`tc_id`) REFERENCES `teaching_class` (`tc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of teacher_teaching_class
-- ----------------------------
BEGIN;
INSERT INTO `teacher_teaching_class` VALUES ('t_1001', 'tc_1001');
INSERT INTO `teacher_teaching_class` VALUES ('t_1001', 'tc_1002');
INSERT INTO `teacher_teaching_class` VALUES ('t_1003', 'tc_1003');
INSERT INTO `teacher_teaching_class` VALUES ('t_1004', 'tc_1004');
INSERT INTO `teacher_teaching_class` VALUES ('t_1004', 'tc_1005');
INSERT INTO `teacher_teaching_class` VALUES ('t_1005', 'tc_1006');
INSERT INTO `teacher_teaching_class` VALUES ('t_1003', 'tc_1007');
INSERT INTO `teacher_teaching_class` VALUES ('t_1006', 'tc_1008');
COMMIT;

-- ----------------------------
-- Table structure for teaching_class
-- ----------------------------
DROP TABLE IF EXISTS `teaching_class`;
CREATE TABLE `teaching_class` (
  `tc_id` char(11) NOT NULL,
  `tc_name` char(20) NOT NULL,
  `tc_num` int DEFAULT '0',
  `tc_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tc_point` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of teaching_class
-- ----------------------------
BEGIN;
INSERT INTO `teaching_class` VALUES ('tc_1001', 'Java 1班', 30, '1_2 2_3', 'A7-108');
INSERT INTO `teaching_class` VALUES ('tc_1002', '数据库 1班', 29, '3_4', 'A5-210');
INSERT INTO `teaching_class` VALUES ('tc_1003', '数据结构 1班', 30, '4_2 1_4', 'A3-206');
INSERT INTO `teaching_class` VALUES ('tc_1004', '3D动画 1班', 30, '3_1 4_1', 'A5-210');
INSERT INTO `teaching_class` VALUES ('tc_1005', 'PS基础 1班', 30, '1_2 5_5', 'A5-106');
INSERT INTO `teaching_class` VALUES ('tc_1006', 'PS基础 2班', 30, '2_2 3_3', 'A1-107');
INSERT INTO `teaching_class` VALUES ('tc_1007', '3D动画2 班', 30, '2_5 5_1', 'A5-107');
INSERT INTO `teaching_class` VALUES ('tc_1008', 'PS基础 3班', 30, '4_2 4_3', 'A2-206');
COMMIT;

-- ----------------------------
-- Table structure for teaching_plan
-- ----------------------------
DROP TABLE IF EXISTS `teaching_plan`;
CREATE TABLE `teaching_plan` (
  `cp_year` char(20) NOT NULL,
  `cp_class` char(10) NOT NULL,
  `tc_id` char(11) NOT NULL,
  `course_id` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`cp_year`,`cp_class`,`tc_id`),
  KEY `cp_year` (`cp_year`,`cp_class`,`course_id`),
  KEY `tc_id` (`tc_id`),
  CONSTRAINT `teaching_plan_ibfk_1` FOREIGN KEY (`cp_year`, `cp_class`, `course_id`) REFERENCES `course_plan` (`cp_year`, `cp_class`, `course_id`),
  CONSTRAINT `teaching_plan_ibfk_2` FOREIGN KEY (`tc_id`) REFERENCES `teaching_class` (`tc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of teaching_plan
-- ----------------------------
BEGIN;
INSERT INTO `teaching_plan` VALUES ('20-21学年第一学期', '19', 'tc_1001', 'cour_1001');
INSERT INTO `teaching_plan` VALUES ('20-21学年第一学期', '19', 'tc_1003', 'cour_1002');
INSERT INTO `teaching_plan` VALUES ('20-21学年第一学期', '19', 'tc_1002', 'cour_1003');
INSERT INTO `teaching_plan` VALUES ('20-21学年第一学期', '19', 'tc_1004', 'cour_1004');
INSERT INTO `teaching_plan` VALUES ('20-21学年第一学期', '19', 'tc_1005', 'cour_1005');
INSERT INTO `teaching_plan` VALUES ('20-21学年第一学期', '19', 'tc_1006', 'cour_1005');
INSERT INTO `teaching_plan` VALUES ('20-21学年第二学期', '19', 'tc_1007', 'cour_1004');
INSERT INTO `teaching_plan` VALUES ('20-21学年第二学期', '19', 'tc_1008', 'cour_1005');
COMMIT;

-- ----------------------------
-- View structure for scg
-- ----------------------------
DROP VIEW IF EXISTS `scg`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `scg` AS select `grade`.`s_id` AS `s_id`,`student`.`s_name` AS `s_name`,`course`.`course_name` AS `course_name`,`grade`.`g_ordinary` AS `g_ordinary`,`grade`.`g_final` AS `g_final` from (((`grade` join `teaching_plan`) join `student`) join `course`) where ((`grade`.`s_id` = `student`.`s_id`) and (`grade`.`tc_id` = `teaching_plan`.`tc_id`) and (`course`.`course_id` = `teaching_plan`.`course_id`));

-- ----------------------------
-- View structure for student_grade_year
-- ----------------------------
DROP VIEW IF EXISTS `student_grade_year`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `student_grade_year` AS select `grade`.`s_id` AS `s_id`,`student`.`s_name` AS `s_name`,`course`.`course_name` AS `course_name`,`grade`.`g_ordinary` AS `g_ordinary`,`grade`.`g_final` AS `g_final`,`grade`.`g_sum` AS `g_sum`,`teaching_plan`.`cp_year` AS `cp_year` from (((`grade` join `teaching_plan`) join `student`) join `course`) where ((`grade`.`s_id` = `student`.`s_id`) and (`grade`.`tc_id` = `teaching_plan`.`tc_id`) and (`course`.`course_id` = `teaching_plan`.`course_id`));

-- ----------------------------
-- View structure for tsg
-- ----------------------------
DROP VIEW IF EXISTS `tsg`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `tsg` AS select `teacher`.`t_name` AS `t_name`,`grade`.`tc_id` AS `tc_id`,`grade`.`s_id` AS `s_id`,`grade`.`g_ordinary` AS `g_ordinary`,`grade`.`g_final` AS `g_final` from ((`teacher` join `teacher_teaching_class`) join `grade`) where ((`teacher`.`t_id` = `teacher_teaching_class`.`t_id`) and (`teacher_teaching_class`.`tc_id` = `grade`.`tc_id`)) group by `teacher`.`t_name`,`grade`.`g_final`,`grade`.`g_ordinary`,`grade`.`s_id`,`grade`.`tc_id`;

-- ----------------------------
-- View structure for yc
-- ----------------------------
DROP VIEW IF EXISTS `yc`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `yc` AS select `teaching_plan`.`cp_year` AS `cp_year`,`teaching_plan`.`cp_class` AS `cp_class`,`course`.`course_name` AS `course_name`,`teaching_plan`.`tc_id` AS `tc_id`,`teaching_class`.`tc_name` AS `tc_name`,`course`.`course_credit` AS `course_credit`,`teacher`.`t_name` AS `t_name`,`teaching_class`.`tc_point` AS `tc_point`,`teaching_class`.`tc_time` AS `tc_time` from ((((`teaching_plan` join `teaching_class`) join `course`) join `teacher_teaching_class`) join `teacher`) where ((`teaching_plan`.`tc_id` = `teaching_class`.`tc_id`) and (`teaching_plan`.`course_id` = `course`.`course_id`) and (`teaching_plan`.`tc_id` = `teacher_teaching_class`.`tc_id`) and (`teacher_teaching_class`.`t_id` = `teacher`.`t_id`) and (`course`.`course_type` = '选修'));

SET FOREIGN_KEY_CHECKS = 1;

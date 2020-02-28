/*
 Navicat Premium Data Transfer

 Source Server         : wolfcode_crm_test
 Source Server Type    : MySQL
 Source Server Version : 50645
 Source Host           : 192.168.113.66:3307
 Source Schema         : crm

 Target Server Type    : MySQL
 Target Server Version : 50645
 File Encoding         : 65001

 Date: 08/01/2020 11:15:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_systemmenu
-- ----------------------------
DROP TABLE IF EXISTS `t_systemmenu`;
CREATE TABLE `t_systemmenu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `url` varchar(250) DEFAULT NULL,
  `types` varchar(100) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `appclass` varchar(255) DEFAULT NULL,
  `otherscripts` varchar(255) DEFAULT NULL,
  `pack` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `sn` varchar(32) DEFAULT NULL,
  `vrtype` varchar(100) DEFAULT NULL,
  `therole` varchar(255) DEFAULT NULL,
  `issystem` bit(1) DEFAULT NULL,
  `actionclass` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`),
  KEY `FKE3BA1CB98FDC454F` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=426012 DEFAULT CHARSET=utf8 COMMENT='系统 菜单栏';;

-- ----------------------------
-- Records of t_systemmenu
-- ----------------------------
BEGIN;
INSERT INTO `t_systemmenu` VALUES (1, '学员管理(售后)', '', NULL, 2, '', '', '', '', 0, '', NULL, 'crgl', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (2, '基础数据维护', '', NULL, 24, '', '', '', '', 0, '', NULL, 'jcsjwh', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (3, '系统管理', '', NULL, 25, '', '', '', '', 0, '', NULL, 'xtgl', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (4, '日常工作', 'base/WorkLogPanel.js', NULL, 19, 'WorkLogPanel', '', '', '', 0, '', NULL, 'ccgz', NULL, NULL, b'0', '', 327687);
INSERT INTO `t_systemmenu` VALUES (5, '潜在学员管理', 'market/ClientPanel.js', NULL, 2, 'ClientPanel', '', '', '', 0, '', NULL, 'qzxygl', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (6, '学员跟踪', 'market/ClientVisitPanel.js', NULL, 3, 'ClientVisitPanel', '', '', '', 0, '', NULL, 'xygz', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (7, '正式学员管理', 'base/StudentPanel.js', NULL, 1, 'StudentPanel', '', '', '', 0, '', NULL, 'zsxygl', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (8, '学员升班留级', 'base/StudentUpLowerGradePanel.js', NULL, 4, 'StudentUpLowerGradePanel', '', '', '', 0, '', NULL, 'xysblj', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (9, '学员流失', 'base/StudentLostPanel.js', NULL, 11, 'StudentLostPanel', '', '', '', 0, '', NULL, 'xyls', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (10, '学员就业信息', 'job/StudentGraduatePanel.js', NULL, 1, 'StudentGraduatePanel', '', '', '', 0, '', NULL, 'xyjy', NULL, NULL, b'0', '', 65536);
INSERT INTO `t_systemmenu` VALUES (11, '学员移交', 'market/ClientTransferPanel.js', NULL, 8, 'ClientTransferPanel', '', '', '', 0, '', NULL, 'xyyj', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (12, '网络营销和推广', 'market/NetSpreadPanel.js', NULL, 11, 'NetSpreadPanel', '', '', '', -1, '', NULL, 'wlyxhtg', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (13, '学员移交历史查询', 'market/ClientTransferLogPanel.js', NULL, 10, 'ClientTransferLogPanel', '', '', '', 0, '', NULL, 'xyyjlscx', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (14, '开班信息管理', 'teaching/classinfo/list', NULL, 1, 'ClassInfoPanel', '', '', '', 0, '', NULL, 'kbxxgl', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (15, '培训产品维护', 'base/TrainProductPanel.js', NULL, 3, 'TrainProductPanel', '', '', '', 0, '', NULL, 'pxcpwh', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (16, '数据字典', 'systemManage/SystemDictionaryManagePanel.js', NULL, 1, 'SystemDictionaryManagePanel', '', '', '', 0, '', NULL, 'sjzd', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (17, '地域信息维护', 'systemManage/SystemRegionPanel.js', NULL, 2, 'SystemRegionPanel', '', '', '', 0, '', NULL, 'ddxywh', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (18, '部门管理', 'base/DepartmentPanel.js', NULL, 3, 'DepartmentPanel', '', '', '', 0, '', NULL, 'bmgl', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (19, '员工管理', 'base/EmployeeManage.js', NULL, 4, 'EmployeePanel', '', '', '', 0, '', NULL, 'yggl', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (20, '系统权限设置', '', NULL, 6, '', '', '', '', 0, '', NULL, 'xxqxsz', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (21, '系统角色', 'systemManage/RoleManagePanel.js', NULL, 1, 'RoleManagePanel', '', '', '', 0, '', NULL, 'xtjs', NULL, NULL, b'0', '', 20);
INSERT INTO `t_systemmenu` VALUES (22, '系统权限', 'systemManage/PermissionPanel.js', NULL, 2, 'PermissionPanelManage', '', '', '', 0, '', NULL, 'xxqx', NULL, NULL, b'0', '', 20);
INSERT INTO `t_systemmenu` VALUES (23, '系统资源', 'systemManage/SystemResourcePanel.js', NULL, 3, 'SystemResourcePanel', '', '', '', 0, '', NULL, 'xxzy', NULL, NULL, b'0', '', 20);
INSERT INTO `t_systemmenu` VALUES (24, '系统菜单', 'systemManage/SystemMenuManagePanel.js', NULL, 4, 'SystemMenuManagePanel', '', '', '', 0, '', NULL, 'xxcd', NULL, NULL, b'0', '', 20);
INSERT INTO `t_systemmenu` VALUES (25, '日志查看', 'systemManage/SystemLogPanel.js', NULL, 8, 'SystemLogPanel', '', '', '', 0, '', NULL, 'rzck', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (26, '讲师学科归属', 'teaching/teacherClass/list', NULL, 21, NULL, NULL, 'tech', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 327684);
INSERT INTO `t_systemmenu` VALUES (27, '评分模板', 'teaching/estimateTemplate/list', NULL, 22, NULL, NULL, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 327684);
INSERT INTO `t_systemmenu` VALUES (32768, '收款管理', 'base/PaymentPanel.js', NULL, 5, 'PaymentPanel', '', '', '', 0, '', NULL, 'xyfk', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (32769, '催款', 'base/ChargementPanel.js', NULL, 6, 'ChargementPanel', '', '', '', -1, '', NULL, 'xyck', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (32770, '0付款项目', '', NULL, 13, '', '', '', '', -1, '', NULL, 'hncxy', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (32771, '自我介绍', 'market/ClientSelfIntroductionPanel.js', NULL, 1, 'ClientSelfIntroductionPanel', '', '', '', 0, '', NULL, 'zwjs', NULL, NULL, b'0', '', 32770);
INSERT INTO `t_systemmenu` VALUES (32772, '入学测试', 'market/ClientEnrolExaminationPanel.js', NULL, 2, 'ClientEnrolExaminationPanel', '', '', '', 0, '', NULL, 'rxcs', NULL, NULL, b'0', '', 32770);
INSERT INTO `t_systemmenu` VALUES (65536, '就业面试', '', NULL, 5, '', '', '', '', -1, '', NULL, 'jyms', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (65537, '客户服务(售前)', '', NULL, 1, '', '', '', '', 0, '', NULL, 'khfw', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (65538, '支出管理', 'base/PayoutPanel.js', NULL, 12, 'PayoutPanel', '', '', '', 0, '', NULL, 'rczc', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (65539, '行业', 'job/TradePanel.js', NULL, 2, 'TradePanel', '', '', '', 0, '', NULL, 'hy', NULL, NULL, b'0', '', 327685);
INSERT INTO `t_systemmenu` VALUES (65540, '招聘企业', 'job/EnterprisePanel.js', NULL, 3, 'EnterprisePanel', '', '', '', 0, '', NULL, 'zpqy', NULL, NULL, b'0', '', 327685);
INSERT INTO `t_systemmenu` VALUES (65541, '企业点评', 'job/EnterpriseCommentPanel.js', NULL, 4, 'EnterpriseCommentPanel', '', '', '', 0, '', NULL, 'qydp', NULL, NULL, b'0', '', 327685);
INSERT INTO `t_systemmenu` VALUES (65542, '招聘经理', 'job/HrManagerPanel.js', NULL, 5, 'HrManagerPanel', '', '', '', 0, '', NULL, 'zpjl', NULL, NULL, b'0', '', 327685);
INSERT INTO `t_systemmenu` VALUES (65543, '招聘职位', 'job/PublishPostPanel.js', NULL, 6, 'PublishPostPanel', '', '', '', 0, '', NULL, 'zpzw', NULL, NULL, b'0', '', 327685);
INSERT INTO `t_systemmenu` VALUES (65544, '简历', 'job/ResumePanel.js', NULL, 7, 'ResumePanel', '', '', '', 0, '', NULL, 'jl', NULL, NULL, b'0', '', 327686);
INSERT INTO `t_systemmenu` VALUES (65545, '面试通知', 'job/InterviewNoticePanel.js', NULL, 8, 'InterviewNoticePanel', '', '', '', 0, '', NULL, 'mstz', NULL, NULL, b'0', '', 327686);
INSERT INTO `t_systemmenu` VALUES (65546, '面试结果', 'job/InterviewResultPanel.js', NULL, 9, 'InterviewResultPanel', '', '', '', 0, '', NULL, 'msjg', NULL, NULL, b'0', '', 327686);
INSERT INTO `t_systemmenu` VALUES (98304, '报表中心', '', NULL, 6, '', '', '', '', 0, '', NULL, 'reportCenter', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (98305, '学费收缴详情', 'report/StudentFeePaymentPanel.js', NULL, 3, 'StudentFeePaymentPanel', '', '', '', 0, '', NULL, 'StudentFeePayment', NULL, NULL, b'0', '', 98304);
INSERT INTO `t_systemmenu` VALUES (98306, '综合财务报表', 'report/FinancePanel.js', NULL, 2, 'FinancePanel', '', '', '', 0, '', NULL, 'Finance', NULL, NULL, b'0', '', 98304);
INSERT INTO `t_systemmenu` VALUES (98307, '大客户(学校)', 'market/SchoolPanel.js', NULL, 14, 'SchoolPanel', NULL, NULL, NULL, 0, NULL, NULL, 'School', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (98308, '学校联系人', 'market/SchoolLinkmanPanel.js', NULL, 16, 'SchoolLinkmanPanel', NULL, NULL, NULL, 0, NULL, NULL, 'SchoolLinkman', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (131072, '短信平台', 'base/SmsMessagePanel.js', NULL, 18, 'SmsMessagePanel', '', '', '', -1, '', NULL, 'SmsMessage', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (131073, '短信模板管理', 'base/SmsMessageTemplatePanel.js', NULL, 19, 'SmsMessageTemplatePanel', '', '', '', -1, '', NULL, 'SmsMessageTemplatePanel', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (131074, '潜在客户池', 'ClientPoolPanel.js', NULL, 20, 'ClientPoolPanel', '', 'market', '', 0, '', NULL, 'pool', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (131075, '考试管理', 'ClientExamPanel.js', NULL, 21, 'ClientExamPanel', '', '/market', '', 0, '', NULL, 'ClientExam', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (131076, '课程安排查询', 'CourseSearchPanel.js', NULL, 14, 'CourseSearchPanel', NULL, 'teach', NULL, 0, NULL, NULL, 'CourseSearchPanel', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (131077, '借款学员缴费详情', 'DebtStudentPaymentPanel.js', NULL, 15, 'DebtStudentPaymentPanel', '', 'report', '', -1, '', NULL, 'debtStudentPayment', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (131078, '营销日报表', 'DayReportPanel.js', NULL, 16, 'DayReportPanel', '', 'report', '', -1, '', NULL, 'dayReport', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (131080, '学工部日常安排', 'TeachDailyWorkPanel.js', NULL, 20, 'TeachDailyWorkPanel', '', 'teach', '', -1, '', NULL, 'TeachDailyWorkPanel', NULL, NULL, b'0', '', 327684);
INSERT INTO `t_systemmenu` VALUES (131081, '学科经营月报管理', 'SubjectMonthReportDataPanel.js', NULL, 5, 'SubjectMonthReportDataPanel', NULL, 'base', NULL, 0, NULL, NULL, 'SubjectMonthReportData', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (163840, '系统配置参数设置', 'SystemConfigPanel.js', NULL, 7, 'SystemConfigPanel', '', 'systemManage', '', 0, '', NULL, 'SystemConfigPanel', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (196608, '招生任务设置', 'RecruitTaskInfoPanel.js', NULL, 2, 'RecruitTaskInfoPanel', '', 'base', '', 0, '', NULL, 'RecruitTaskInfoPanel', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (229376, '借款申请', 'LoanFormPanel.js', NULL, 14, 'LoanFormPanel', '', 'base', '', 0, '', NULL, 'loan', NULL, NULL, b'0', '', 327687);
INSERT INTO `t_systemmenu` VALUES (229377, '实训管理', 'TrainingPlanPanel.js', NULL, 15, 'TrainingPanel', '', 'market', '', -1, '', NULL, 'TrainingPlanPanel', NULL, NULL, b'0', '', 65537);
INSERT INTO `t_systemmenu` VALUES (229378, '工资管理', NULL, NULL, 5, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'SalaryManage', NULL, NULL, b'0', '', 3);
INSERT INTO `t_systemmenu` VALUES (229379, '工资模板设置', 'SalaryTemplatePanel.js', NULL, 1, 'SalaryTemplatePanel', NULL, 'base', NULL, 0, NULL, NULL, 'SalaryTemplatePanel', NULL, NULL, b'0', '', 229378);
INSERT INTO `t_systemmenu` VALUES (229380, '工资管理', 'SalaryPanel.js', NULL, 2, 'SalaryPanel', '', 'base', '', 0, '', NULL, 'SalaryPanel', NULL, NULL, b'0', '', 425989);
INSERT INTO `t_systemmenu` VALUES (294912, '教师评分', 'teaching/estimate/list', NULL, 16, 'TeacherScorePanel', '', 'teach', '', -1, '', NULL, 'teacherscore', NULL, NULL, b'0', '', 327684);
INSERT INTO `t_systemmenu` VALUES (294913, '课程表', 'CoursePanel.js', NULL, 17, 'CoursePanel', '', 'teach', '', 0, '', NULL, 'courseplan', NULL, NULL, b'0', '', 327684);
INSERT INTO `t_systemmenu` VALUES (294914, '教室设置', 'ClassroomPanel.js', NULL, 18, 'ClassroomPanel', '', 'teach', '', 0, '', NULL, 'classroom', NULL, NULL, b'0', '', 327684);
INSERT INTO `t_systemmenu` VALUES (327680, '学员信息统计', 'StudentPanel.js', NULL, 1, 'StudentStatisticsList', NULL, 'base', NULL, 0, NULL, NULL, 'StudentStatisticsList', NULL, NULL, b'0', '', 98304);
INSERT INTO `t_systemmenu` VALUES (327681, '潜在学员报表', 'ClientPanel.js', NULL, 4, 'ClientDayChartPanel', NULL, 'market', NULL, 0, NULL, NULL, 'ClientDayChartPanel', NULL, NULL, b'0', '', 98304);
INSERT INTO `t_systemmenu` VALUES (327682, '支出明细', 'PayoutSearchPanel.js', NULL, 15, 'PayoutSearchPanel', '', 'base', '', 0, '', NULL, 'payoutsearch', NULL, NULL, b'0', '', 327687);
INSERT INTO `t_systemmenu` VALUES (327683, '学科管理', 'TrainSubjectPanel.js', NULL, 4, 'TrainSubjectPanel', NULL, 'base', NULL, 0, NULL, NULL, 'TrainSubject', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (327684, '教务管理', '', NULL, 4, '', '', '', '', 0, '', NULL, 'teach', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (327685, '企业', '', NULL, 10, '', '', '', '', -1, '', NULL, 'enterprise', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (327686, '简历及面试情况 ', '', NULL, 11, '', '', '', '', -1, '', NULL, 'resumeandreview', NULL, NULL, b'0', '', 2);
INSERT INTO `t_systemmenu` VALUES (327687, '日常办公', '', NULL, 3, '', '', '', '', 0, '', NULL, 'oa', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (360448, '课程模板', 'CoursesTemplatePanel.js', NULL, 19, 'CoursesTemplatePanel', NULL, 'teach', NULL, 0, NULL, NULL, 'coursesTemplatePanel', NULL, NULL, b'0', '', 327684);
INSERT INTO `t_systemmenu` VALUES (393216, '工资查询', 'SalarySearchPanel.js', NULL, 20, 'SalarySearchPanel', NULL, 'base', NULL, 0, NULL, NULL, 'salarySearchPanel', NULL, NULL, b'0', '', 327687);
INSERT INTO `t_systemmenu` VALUES (425956, '收款分摊', 'report/StudentPaymentStatisticsPanel.js', NULL, 5, 'StudentPaymentStatisticsPanel', '', '', '', 0, '', NULL, 'paymentm', NULL, NULL, b'0', NULL, 98304);
INSERT INTO `t_systemmenu` VALUES (425984, '学员档案管理', 'StudentArchivesPanel.js', NULL, 13, 'StudentArchivesPanel', '', 'base', '', 0, '', NULL, 'studentarchives', NULL, NULL, b'0', '', 1);
INSERT INTO `t_systemmenu` VALUES (425985, '查询客户信息日志', 'QueryLogPanel.js', NULL, 26, 'QueryLogPanel', '', 'market', '', 0, '', NULL, 'cqlog', NULL, NULL, NULL, NULL, 2);
INSERT INTO `t_systemmenu` VALUES (425989, '工资管理', '', NULL, 16, '', '', '', '', 0, '', NULL, 'gzgl', NULL, NULL, b'0', NULL, NULL);
INSERT INTO `t_systemmenu` VALUES (425990, '学员管理(售后)', '', 'EDUMS', 2, '', '', '', '', -1, '', NULL, 'edums_crgl', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425991, '基础数据维护', '', 'EDUMS', 24, '', '', '', '', -1, '', NULL, 'edums_jcsjwh', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425992, '系统管理', '', 'EDUMS', 25, '', '', '', '', -1, '', NULL, 'edums_xtgl', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425993, '客户服务(售前)', '', 'EDUMS', 1, '', '', '', '', -1, '', NULL, 'edums_khfw', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425994, '日常办公', '', 'EDUMS', 3, '', '', '', '', -1, '', NULL, 'edums_oa', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425995, '教务管理', '', 'EDUMS', 4, '', '', '', '', -1, '', NULL, 'edums_teach', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425996, '就业面试', '', 'EDUMS', 5, '', '', '', '', -1, '', NULL, 'edums_jyms', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425997, '报表中心', '', 'EDUMS', 6, '', '', '', '', -1, '', NULL, 'edums_reportCenter', NULL, NULL, b'0', '', NULL);
INSERT INTO `t_systemmenu` VALUES (425998, '系统权限设置', '', 'EDUMS', 6, '', '', '', '', -1, '', NULL, 'edums_xxqxsz', NULL, NULL, b'0', '', 425992);
INSERT INTO `t_systemmenu` VALUES (425999, '开班信息管理', 'teaching/classinfo/list', 'EDUMS', 1, 'ClassInfoPanel', '', '', '', -1, '', NULL, 'edums_kbxxgl', NULL, NULL, b'0', '', 425991);
INSERT INTO `t_systemmenu` VALUES (426000, '培训产品维护', 'teaching/trainproduct/list', 'EDUMS', 3, 'TrainProductPanel', '', '', '', -1, '', NULL, 'edums_pxcpwh', NULL, NULL, b'0', '', 425991);
INSERT INTO `t_systemmenu` VALUES (426001, '学科管理', 'teaching/trainsubject/list', 'EDUMS', 4, 'TrainSubjectPanel', NULL, 'base', NULL, -1, NULL, NULL, 'edums_TrainSubject', NULL, NULL, b'0', '', 425991);
INSERT INTO `t_systemmenu` VALUES (426002, '数据字典', 'system/dictionary/list', 'EDUMS', 1, 'SystemDictionaryManagePanel', '', '', '', -1, '', NULL, 'edums_sjzd', NULL, NULL, b'0', '', 425992);
INSERT INTO `t_systemmenu` VALUES (426003, '部门管理', 'system/department/list', 'EDUMS', 3, 'DepartmentPanel', '', '', '', -1, '', NULL, 'edums_bmgl', NULL, NULL, b'0', '', 425992);
INSERT INTO `t_systemmenu` VALUES (426004, '员工管理', 'system/employee/list', 'EDUMS', 4, 'EmployeePanel', '', '', '', -1, '', NULL, 'edums_yggl', NULL, NULL, b'0', '', 425992);
INSERT INTO `t_systemmenu` VALUES (426005, '系统角色', 'system/role/list', 'EDUMS', 1, 'RoleManagePanel', '', '', '', -1, '', NULL, 'edums_xtjs', NULL, NULL, b'0', '', 425998);
INSERT INTO `t_systemmenu` VALUES (426006, '系统权限', 'system/permission/list', 'EDUMS', 2, 'PermissionPanelManage', '', '', '', -1, '', NULL, 'edums_xxqx', NULL, NULL, b'0', '', 425998);
INSERT INTO `t_systemmenu` VALUES (426007, '系统菜单', 'system/systemmenu/list', 'EDUMS', 4, 'SystemMenuManagePanel', '', '', '', -1, '', NULL, 'edums_xxcd', NULL, NULL, b'0', '', 425998);
INSERT INTO `t_systemmenu` VALUES (426008, '日志查看', 'system/systemlog/list', 'EDUMS', 8, 'SystemLogPanel', '', '', '', -1, '', NULL, 'edums_rzck', NULL, NULL, b'0', '', 425992);
INSERT INTO `t_systemmenu` VALUES (426009, '教室设置', 'teaching/classroom/list', 'EDUMS', 18, 'ClassroomPanel', '', 'teach', '', -1, '', NULL, 'edums_classroom', NULL, NULL, b'0', '', 425995);
INSERT INTO `t_systemmenu` VALUES (426010, '课程表', 'teaching/course/list', 'EDUMS', 17, 'CoursePanel', '', 'teach', '', -1, '', NULL, 'edums_courseplan', NULL, NULL, b'0', '', 425995);
INSERT INTO `t_systemmenu` VALUES (426011, '教师评分', 'teaching/estimate/list', 'EDUMS', 16, 'TeacherScorePanel', '', 'teach', '', -1, '', NULL, 'edums_teacherscore', NULL, NULL, b'0', '', 425995);
INSERT INTO `t_systemmenu` VALUES (426012, '学员流向维度统计', 'teaching/charts/student/transfer', 'EDUMS', 1, 'StudentTransferCharts', NULL, 'teach', NULL, -1, NULL, NULL, 'edums_charts_student_transfer', NULL, NULL, b'0', NULL, 425997);
INSERT INTO `t_systemmenu` VALUES (426013, '学员自身详情统计', 'teaching/charts/student/details', 'EDUMS', 3, 'StudentDetailsCharts', NULL, 'teach', NULL, -1, NULL, NULL, 'edums_charts_student_details', NULL, NULL, b'0', NULL, 425997);
INSERT INTO `t_systemmenu` VALUES (426014, '学员口碑挖掘统计', 'teaching/charts/student/evaluation', 'EDUMS', 2, 'StudentEvaluationCharts', NULL, 'teach', NULL, -1, NULL, NULL, 'edums_charts_student_evaluation', NULL, NULL, b'0', NULL, 425997);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

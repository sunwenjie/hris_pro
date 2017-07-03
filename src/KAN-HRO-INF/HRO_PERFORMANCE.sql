# 绩效管理 - 预算设定 - 主表
CREATE TABLE hro_pm_budget_setting_header (
	headerId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,
	corpId int(11) NOT NULL,
	year int(4) NOT NULL, 								# 年份
	startDate datetime DEFAULT NULL,					# 员工自评时间（开始）
	endDate datetime DEFAULT NULL,						# 员工自评时间（结束）
	startDate_bl datetime DEFAULT NULL,					# 同事反馈时间（开始）
	endDate_bl datetime DEFAULT NULL,					# 同事反馈时间（结束）
	startDate_pm datetime DEFAULT NULL,					# 主管评分时间（开始）
	endDate_pm datetime DEFAULT NULL,					# 主管评分时间（结束）
	startDate_final datetime DEFAULT NULL,				# 主管最终修改评价时间（开始）
	endDate_final datetime DEFAULT NULL,				# 主管最终修改评价时间（结束）
	startDate_bu datetime DEFAULT NULL,					# BU Head上传更改时间（开始）
	endDate_bu datetime DEFAULT NULL,					# BU Head上传更改时间（结束）
	maxInvitation tinyint(4) DEFAULT 0,					# 最大邀请评估人数
	noticeLetterTemplate int(11) NOT NULL, 				# 通知信模板
	description varchar(2000) DEFAULT NULL,
	deleted tinyint(4) DEFAULT NULL,  	
	status tinyint(4) DEFAULT NULL,
    remark1 varchar(1000) DEFAULT NULL,
    remark2 varchar(1000) DEFAULT NULL,
    createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
   	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
	PRIMARY KEY (`headerId`)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

# 绩效管理 - 预算设定 - 从表
CREATE TABLE hro_pm_budget_setting_detail (
	detailId int(11) NOT NULL AUTO_INCREMENT,
	headerId int(11) NOT NULL,
	parentBranchId int(11) NOT NULL,					# 按BU/Function分配预算
	ttc	double NOT NULL,								# TTC预算
	bonus double NOT NULL,								# 年终奖预算
	deleted tinyint(4) DEFAULT NULL,
	status tinyint(4) DEFAULT NULL,
    remark1 varchar(1000) DEFAULT NULL,
    remark2 varchar(1000) DEFAULT NULL,
    createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
   	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
	PRIMARY KEY (`detailId`)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

# 绩效管理 - 工资涨幅规则
CREATE TABLE hro_pm_yerr_rule (
	ruleId int(11) NOT NULL AUTO_INCREMENT,				# 规则ID
	accountId int(11) NOT NULL,
	corpId int(11) NOT NULL,
	rating double(4,2) NOT NULL,						# 绩效评分{1:2:2.5:3:3.5:4:4.5:5}
	distribution double(4,2) NOT NULL, 					# 分配比例
	meritRateRMB double(4,3) DEFAULT 0,					# 业绩涨幅（人民币）
	meritRateHKD double(4,3) DEFAULT 0,					# 业绩涨幅（港币）
	meritRateSGD double(4,3) DEFAULT 0,					# 业绩涨幅（新加坡元）
	meritRateRemark1 double(4,3) DEFAULT 0,				# 业绩涨幅（待定）
	meritRateRemark2 double(4,3) DEFAULT 0,				# 业绩涨幅（待定）
	meritAndPromotionRateRMB double(4,3) DEFAULT 0,		# 业绩+晋升涨幅（人民币）
	meritAndPromotionRateHKD double(4,3) DEFAULT 0,		# 业绩+晋升涨幅（港币）
	meritAndPromotionRateSGD double(4,3) DEFAULT 0,		# 业绩+晋升涨幅（新加坡元）
	meritAndPromotionRateRemark1 double(4,3) DEFAULT 0,	# 业绩+晋升涨幅（待定）
	meritAndPromotionRateRemark2 double(4,3) DEFAULT 0,	# 业绩+晋升涨幅（待定）
	bounsRate double(4,3) DEFAULT 0,					# 奖金涨幅
	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
 	status tinyint(4) DEFAULT NULL,
    remark1 varchar(1000) DEFAULT NULL,
    remark2 varchar(1000) DEFAULT NULL,
    createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
   	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (`ruleId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

# 绩效管理 - 自评邀请表
CREATE TABLE hro_pm_invite_assessment (
	inviteId int(11) NOT NULL AUTO_INCREMENT,
	assessmentId int(11) NOT NULL,						# 邀请ID
	inviteEmployeeId int(11) NOT NULL,					# 邀请对象employeeId
	`randomKey` VARCHAR(32) DEFAULT NULL,
	answer1 varchar(2000) DEFAULT NULL,					# 评价1
	answer2 varchar(2000) DEFAULT NULL,					# 评价2
	answer3 varchar(2000) DEFAULT NULL,					# 评价3
	answer4 varchar(2000) DEFAULT NULL,					# 评价4
	deleted tinyint(4) DEFAULT NULL,
 	status tinyint(4) DEFAULT NULL,
    remark1 varchar(1000) DEFAULT NULL,
    remark2 varchar(1000) DEFAULT NULL,
    createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
   	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (`inviteId`)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

# 绩效管理 - 员工自评表
CREATE TABLE hro_pm_self_assessment (
	assessmentId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,
	corpId int(11) NOT NULL,
	year int(4) NOT NULL,
	parentBranchId int(11) NOT NULL,					# BU/Function
	selfPositionId int(11) DEFAULT NULL,				# 职位ID
	parentPositionId int(11) DEFAULT NULL,				# People Manager职位ID
	bizLeaderPositionId int(11) DEFAULT NULL,			# Biz Lead职位ID
	buLeaderPositionId int(11) DEFAULT NULL,			# BU Leader职位ID
	hrOwnerPositionId int(11) DEFAULT NULL,				# HR对接人职位ID
	employeeId int(11) NOT NULL,						# 员工ID
	employeeNameZH varchar(200) NOT NULL,				# 员工姓名（中文）
	employeeNameEN varchar(200) NOT NULL,				# 员工姓名（英文）
	directLeaderNameZH varchar(200) NOT NULL,			# 直属主管姓名（中文）
	directLeaderNameEN varchar(200) NOT NULL,			# 直属主管姓名（英文）
	accomplishments text(2000) DEFAULT NULL, 			# 自评 - 工作成果
	areasOfStrengths text(2000) DEFAULT NULL,			# 自评 - 优势长处
	areasOfImprovement text(2000) DEFAULT NULL,			# 自评 - 待改善领域
	nextYearGoals text(2000) DEFAULT NULL,				# 自评 - 明年目标
	nextYearPlans text(2000) DEFAULT NULL,				# 自评 - 明年计划
	successors varchar(100) DEFAULT NULL,				# 自评 - 接班人
	otherComments text(2000) DEFAULT NULL,				# 自评 - 其他意见
	accomplishments_bm text(2000) DEFAULT NULL, 		# 业务汇报人评 - 工作成果 bm = business manager
	areasOfStrengths_bm text(2000) DEFAULT NULL,		# 业务汇报人评 - 优势长处
	areasOfImprovement_bm text(2000) DEFAULT NULL,		# 业务汇报人评 - 待改善领域
	nextYearGoalsAndPlans_bm text(2000) DEFAULT NULL,	# 业务汇报人评 - 明年目标/计划
	isPromotion_bm tinyint(4) DEFAULT 3,				# 业务汇报人评 - 是否晋升{1:是；2:否；3:没有意见}
	promotionReason_bm text(2000) DEFAULT NULL,			# 业务汇报人评 - 晋升原因
	successors_bm varchar(100) DEFAULT NULL,			# 业务汇报人评 - 接班人
	accomplishments_pm text(2000) DEFAULT NULL, 		# 主管评 - 工作成果 pm = people manager
	areasOfStrengths_pm text(2000) DEFAULT NULL,		# 主管评 - 优势长处
	areasOfImprovement_pm text(2000) DEFAULT NULL,		# 主管评 - 待改善领域
	nextYearGoalsAndPlans_pm text(2000) DEFAULT NULL,	# 主管评 - 明年目标/计划
	isPromotion_pm tinyint(4) DEFAULT 3,				# 主管评 - 是否晋升{1:是；2:否；3:没有意见}
	promotionReason_pm text(2000) DEFAULT NULL,			# 主管评 - 晋升原因
	successors_pm varchar(100) DEFAULT NULL,			# 主管评 - 接班人
	rating_pm varchar(25) DEFAULT NULL,					# 主管评 - 评分
	accomplishments_bu text(2000) DEFAULT NULL, 		# BU评 - 工作成果 BU = BU Head
	areasOfStrengths_bu text(2000) DEFAULT NULL,		# BU评 - 优势长处
	areasOfImprovement_bu text(2000) DEFAULT NULL,		# BU评 - 待改善领域
	nextYearGoalsAndPlans_bu text(2000) DEFAULT NULL,	# BU评 - 明年目标/计划
	isPromotion_bu tinyint(4) DEFAULT 3,				# BU评 - 是否晋升{1:是；2:否；3:没有意见}
	promotionReason_bu text(2000) DEFAULT NULL,			# BU评 - 晋升原因
	successors_bu varchar(100) DEFAULT NULL,			# BU评 - 接班人
	rating_bu varchar(25) DEFAULT NULL,					# BU评 - 评分
	rating_final varchar(25) DEFAULT NULL,				# 最终 - 评分
 	deleted tinyint(4) DEFAULT NULL,			
 	status tinyint(4) DEFAULT NULL,						# 0:请选择##1:新建##2:待评估##3:已评估##4:已同步##5:已调整##6:完成
 	status_bm tinyint(4) DEFAULT 1,						# 业务汇报人评 - 状态（默认1，已提交2）
 	status_bu tinyint(4) DEFAULT 1,						# BU评 - 状态 - 状态（默认1，已提交2）
 	pm1PositionId varchar(11) DEFAULT NULL,				# 除去直系主管，其他的主管1职位ID
 	pm2PositionId varchar(11) DEFAULT NULL,				# 除去直系主管，其他的主管2职位ID
 	pm3PositionId varchar(11) DEFAULT NULL,				# 除去直系主管，其他的主管3职位ID
 	pm4PositionId varchar(11) DEFAULT NULL,				# 除去直系主管，其他的主管4职位ID
 	pm1Content text DEFAULT NULL,						# 除去直系主管，其他的主管1
 	pm2Content text DEFAULT NULL,						# 除去直系主管，其他的主管2
 	pm3Content text DEFAULT NULL,						# 除去直系主管，其他的主管3
 	pm4Content text DEFAULT NULL,						# 除去直系主管，其他的主管4
 	status_pm1 tinyint(4) DEFAULT 1,					# 除去直系主管，其他的主管1 - 状态（默认1，已提交2）
 	status_pm2 tinyint(4) DEFAULT 1,					# 除去直系主管，其他的主管2 - 状态（默认1，已提交2）
 	status_pm3 tinyint(4) DEFAULT 1,					# 除去直系主管，其他的主管3 - 状态（默认1，已提交2）
 	status_pm4 tinyint(4) DEFAULT 1,					# 除去直系主管，其他的主管4 - 状态（默认1，已提交2）
    remark1 varchar(1000) DEFAULT NULL,
    remark2 varchar(1000) DEFAULT NULL,
    createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
   	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (`assessmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

alter table hro_performance
add column `currentYearEndBonus` DOUBLE DEFAULT NULL AFTER `ttcUSD`;

INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(850, 'menu_performance_Modules', 1, '绩效', 'Performance', '绩效', 'Performance', 2, 'N/A', 19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'menu_performance_Modules', NULL, NULL, 10, '{2}', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2016-03-07 00:00:00', '1', '2016-03-07 00:00:00'),
	(890, 'menu_performance_Configuration', 1, '设置', 'Configuration', '设置', 'Configuration', 0, 'N/A', 19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 850, 'menu_performance_Modules', 'menu_performance_Configuration', NULL, 50, '{2}', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2016-04-06 00:00:00', '1', '2016-04-06 00:00:00'),
	(1177, 'menu_performance_SelfAssessment', 1, '员工自评', 'Employee Self Assessment', '员工自评', 'Employee Self Assessment', 2, 'N/A', 19, 'HRO_PM_SELF_ASSESSMENT', NULL, 'selfAssessmentAction.do?proc=list_object', 'selfAssessmentAction.do?proc=list_object', '', '', '', '', '', '', 850, 'menu_performance_Modules', 'menu_performance_SelfAssessment', '', 20, '{1:2:3:4:5:6:55}', '', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2016-03-07 00:00:00', '1', '2016-03-17 12:08:00'),
	(1178, 'menu_performance_TTC', 1, '工资涨幅规则', 'TTC', '工资涨幅规则', 'TTC', 2, 'N/A', 19, 'HRO_PM_YERR_RULE', NULL, 'yerrRuleAction.do?proc=list_object', 'yerrRuleAction.do?proc=list_object', '', '', '', '', '', '', 890, 'menu_performance_Modules', 'menu_performance_Configuration', 'menu_performance_TTC', 51, '{1:2:3:4:5}', '', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2016-04-07 00:00:00', '1', '2016-04-06 15:44:27'),
	(1179, 'menu_performance_YERR', 1, 'YERR', 'YERR', 'YERR', 'YERR', 2, 'N/A', 19, 'HRO_PM_YERR', NULL, 'performance.do?proc=pm_list_object', 'performance.do?proc=pm_list_object', '', '', '', '', '', '', 850, 'menu_performance_Modules', 'menu_performance_YERR', '', 30, '{2:3:4:5:6}', '', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2016-04-07 00:00:00', '1', '2016-04-06 16:16:48'),
	(1180, 'menu_performance_GoalSetting', 1, '目标设定', 'Goal Setting', '目标设定', 'Goal Setting', 2, 'N/A', 19, 'HRO_PM_GOAL_SETTING', NULL, 'selfAssessmentAction.do?proc=list_goal', 'selfAssessmentAction.do?proc=list_goal', NULL, NULL, NULL, NULL, NULL, NULL, 850, 'menu_performance_Modules', 'menu_performance_GoalSetting', '', 25, '{1:2:3:4:5:6:55}', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2016-04-07 00:00:00', '1', '2016-04-07 00:00:00'),
	(1181, 'menu_performance_BudgetSetting', 1, '年度自评设定', 'Annual self assessment Setting', '年度自评设定', 'Annual self assessment Setting', 2, 'N/A', 19, 'HRO_PM_BUDGET_SETTING', NULL, 'budgetSettingHeaderAction.do?proc=list_object', 'budgetSettingHeaderAction.do?proc=list_object', NULL, NULL, NULL, NULL, NULL, NULL, 890, 'menu_performance_Modules', 'menu_performance_Configuration', 'menu_performance_BudgetSetting', 52, '{1:2:3:4:5}', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2016-04-07 00:00:00', '1', '2016-04-07 00:00:00');

alter table `hro_performance`
add column yesGPTarget smallint(1) DEFAULT 2 AFTER `description`,
add column yesNewGPTarget smallint(1) DEFAULT 2 AFTER `yesGPTarget`;
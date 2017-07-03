use HRO;

DROP TABLE IF EXISTS hro_mgt_options;
DROP TABLE IF EXISTS hro_mgt_email_configuration;
DROP TABLE IF EXISTS hro_mgt_share_folder_configuration;
DROP TABLE IF EXISTS hro_mgt_skill;
DROP TABLE IF EXISTS hro_mgt_education;
DROP TABLE IF EXISTS hro_mgt_language;
DROP TABLE IF EXISTS hro_mgt_contract_type;
DROP TABLE IF EXISTS hro_mgt_position;
DROP TABLE IF EXISTS hro_mgt_position_grade;
DROP TABLE IF EXISTS hro_mgt_position_grade_currency;
DROP TABLE IF EXISTS hro_mgt_employee_status;
DROP TABLE IF EXISTS hro_mgt_item;									# 科目
DROP TABLE IF EXISTS hro_mgt_item_group;							# 科目分组
DROP TABLE IF EXISTS hro_mgt_item_group_relation;		     		# 科目、科目分组的关系
DROP TABLE IF EXISTS hro_mgt_item_mapping;							# 科目映射
DROP TABLE IF EXISTS hro_mgt_tax;									# 税率
DROP TABLE IF EXISTS hro_mgt_social_benefit_solution_header;		# 社保方案主表，按照城市和户籍定义不同的社保方案	
DROP TABLE IF EXISTS hro_mgt_social_benefit_solution_detail;		# 社保方案从表，按照城市选择不同的险种；比例须确定下来
DROP TABLE IF EXISTS hro_mgt_commercial_benefit_solution_header;	# 商保方案主表	
DROP TABLE IF EXISTS hro_mgt_commercial_benefit_solution_detail;	# 商保方案从表，一个商保方案可以有多个商保明细
DROP TABLE IF EXISTS hro_mgt_certification;							# 证书 - 奖项
DROP TABLE IF EXISTS hro_mgt_membership;							# 社会活动
DROP TABLE IF EXISTS hro_mgt_business_contract_template;			# 商务合同模板
DROP TABLE IF EXISTS hro_mgt_labor_contract_template;				# 劳动合同模板
DROP TABLE IF EXISTS hro_mgt_industry_type;							# 行业类型
DROP TABLE IF EXISTS hro_mgt_calendar_header;						# 日历主表
DROP TABLE IF EXISTS hro_mgt_calendar_detail;						# 日历从表
DROP TABLE IF EXISTS hro_mgt_shift_header;							# 排班主表
DROP TABLE IF EXISTS hro_mgt_shift_detail;							# 排班从表
DROP TABLE IF EXISTS hro_mgt_shift_exception;						# 排班例外
DROP TABLE IF EXISTS hro_mgt_bank;									# 银行
DROP TABLE IF EXISTS hro_mgt_setting;								# 个人中心
DROP TABLE IF EXISTS hro_mgt_sick_leave_salary_header;				# 病假工资 - 主表
DROP TABLE IF EXISTS hro_mgt_sick_leave_salary_detail;				# 病假工资 - 从表
DROP TABLE IF EXISTS hro_mgt_resign_template;						# 退工单模板
DROP TABLE IF EXISTS hro_mgt_annual_leave_rule_header;				# 年假规则 - 主表
DROP TABLE IF EXISTS hro_mgt_annual_leave_rule_detail;				# 年假规则 - 从表
DROP TABLE IF EXISTS hro_mgt_exchange_rate							# 汇率（local兑换美元）

CREATE TABLE hro_mgt_exchange_rate (
	exchangeRateId int(11) NOT NULL AUTO_INCREMENT,					# 
	accountId int(11) NOT NULL,										#	
	corpId int(11) NOT NULL,										# 客户ID
	currencyNameZH varchar(100) NOT NULL,							#
	currencyNameEN varchar(100) DEFAULT NULL,						# 汇率
	currencyCode varchar(10) NOT NULL,								# 货币代号
	fromUSD double NOT NULL,										# 美元
	toLocal double NOT NULL,										# 本地
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (exchangeRateId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


CREATE TABLE hro_mgt_options (
  	optionsId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	branchPrefer tinyint(4) DEFAULT NULL,							# 组织显示方式  1：架构图方式 2：列表显示
  	isSumSubBranchHC tinyint(4)DEFAULT NULL,						# 是否统计子部门人数(0:不统计，1：统计)
  	positionPrefer tinyint(4) DEFAULT NULL,							# 职位显示方式  1：纵向显示2：横向显示
  	language tinyint(4) DEFAULT NULL,
  	useBrowserLanguage tinyint(4) DEFAULT NULL,
  	dateFormat tinyint(4) DEFAULT NULL,
  	timeFormat tinyint(4) DEFAULT NULL,
  	pageStyle tinyint(4) DEFAULT NULL,
  	smsGetway int(11) DEFAULT NULL,
  	orderBindContract tinyint(4) DEFAULT NULL,						# 订单绑定合同（是/否），订单是否必须绑定合同
  	logoFile varchar(1000) DEFAULT NULL,							# 公司商标存放路径	
  	logoFileSize INTEGER DEFAULT NULL,								# 公司商标文件大小
  	sbGenerateCondition tinyint(4) DEFAULT NULL,					# 社保申报条件（1:订单批准，2:订单生效），订单
  	cbGenerateCondition tinyint(4) DEFAULT NULL,					# 商保申购条件（1:订单批准，2:订单生效），订单
  	settlementCondition tinyint(4) DEFAULT NULL,					# 结算处理条件（1:订单批准，2:订单生效），订单
  	sbGenerateConditionSC tinyint(4) DEFAULT NULL,					# 社保申报条件（1:服务协议批准，2:服务协议盖章，3:服务协议归档），服务协议
  	cbGenerateConditionSC tinyint(4) DEFAULT NULL,					# 商保申购条件（1:服务协议批准，2:服务协议盖章，3:服务协议归档），服务协议
  	settlementConditionSC tinyint(4) DEFAULT NULL,					# 结算处理条件（1:服务协议批准，2:服务协议盖章，3:服务协议归档），服务协议
  	accuracy tinyint(4) DEFAULT NULL,								# 精度（取整，保留一位，保留二位）
	round tinyint(4) DEFAULT NULL,									# 取舍（四舍五入，截取，向上进位）
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (optionsId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
	
CREATE TABLE hro_mgt_email_configuration (
  	configurationId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	showName varchar(100) DEFAULT NULL,
  	mailType tinyint(4) DEFAULT NULL,
  	sentAs varchar(100) DEFAULT NULL,
  	accountName varchar(50) DEFAULT NULL,
  	smtpHost varchar(100) DEFAULT NULL,
  	smtpPort varchar(25) DEFAULT NULL,
  	username varchar(100) DEFAULT NULL,
  	password varchar(100) DEFAULT NULL,
  	smtpAuthType tinyint(4) DEFAULT NULL,
  	smtpSecurityType tinyint(4) DEFAULT NULL,
  	pop3Host varchar(100) DEFAULT NULL,
  	pop3Port varchar(25) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (configurationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
	
CREATE TABLE hro_mgt_share_folder_configuration (
  	configurationId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	host varchar(100) DEFAULT NULL,
  	port varchar(25) DEFAULT NULL,
  	username varchar(100) DEFAULT NULL,
  	password varchar(100) DEFAULT NULL,
  	directory varchar(100) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (configurationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_skill (
  	skillId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	corpId int(11) NOT NULL,										# 客户ID
  	skillName varchar(50) NOT NULL,
  	description varchar(2000) DEFAULT NULL,
  	parentSkillId int(11) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (skillId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_education (
  	educationId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	corpId int(11) NOT NULL,										# 客户ID
  	nameZH varchar(50) NOT NULL,
  	nameEN varchar(50) NOT NULL,
  	description varchar(2000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (educationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_language (
  	languageId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	corpId int(11) NOT NULL,										# 客户ID
  	nameZH varchar(100) NOT NULL,
  	nameEN varchar(100) NOT NULL,
  	description varchar(2000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (languageId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_contract_type (
	typeId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,
  	nameZH varchar(200) DEFAULT NULL,
  	nameEN varchar(200) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (typeId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_position (
  	positionId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	corpId int(11) NOT NULL,										# 客户ID
  	positionGradeId int(11) NOT NULL,								# 职级
  	title varchar(200) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
  	skill varchar(1000) DEFAULT NULL,
  	note varchar(1000) DEFAULT NULL,
  	attachment varchar(1000) DEFAULT NULL,
  	parentPositionId int(11) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (positionId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_position_grade (
  	positionGradeId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,
  	corpId int(11) NOT NULL,										# 客户ID
  	gradeNameZH varchar(200) DEFAULT NULL,
  	gradeNameEN varchar(200) DEFAULT NULL,
  	weight smallint(8) DEFAULT NULL,								# 权重
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (positionGradeId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_position_grade_currency (
  	positionGradeId int(11) NOT NULL,
  	currencyId varchar(25) NOT NULL,
  	minSalary double DEFAULT NULL,
  	maxSalary double DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (positionGradeId, currencyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_employee_status (
	employeeStatusId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,
	corpId int(11) default null,																				
	nameZH varchar(200) DEFAULT NULL,
	nameEN varchar(200) DEFAULT NULL,
	setDefault tinyint(4) DEFAULT NULL,
	description varchar(1000) DEFAULT NULL,
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (employeeStatusId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_item (
	itemId int(11) NOT NULL AUTO_INCREMENT,							# 科目ID
	accountId int(11) NOT NULL,										# 账户ID。AccountId为“1”，是系统创建的。
	corpId int(11) default null,									# 企业ID，用于Inhouse使用
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	itemCap double DEFAULT NULL,									# 科目上限（防止科目输入过大金额）
	itemFloor double DEFAULT NULL,									# 科目下限（防止科目输入过小金额）
	sequence smallint(8) DEFAULT NULL,								# 科目顺序
	personalTax tinyint(4) DEFAULT NULL,							# 计算个税（是/否，例如，报销等不需要计算个税）
	companyTax tinyint(4) DEFAULT NULL,								# 计算营业税（是/否，例如，代收代付科目企业不需要缴纳营业税）
	calculateType tinyint(4) DEFAULT NULL,							# 计算类型（直接计算 - 在系统中会进行运算、导入计算、导出 - 不等）
	itemType tinyint(4) DEFAULT NULL,								# 科目类型（工资、补助、奖金、加班、报销、休假、社保、商保、服务费、风险、第三方成本、其他）
	billRatePersonal double DEFAULT NULL,							# 比率（个人收入）
	billRateCompany double DEFAULT NULL,							# 比率（公司营收）
	costRatePersonal double DEFAULT NULL,							# 比率（个人支出）
	costRateCompany double DEFAULT NULL,							# 比率（公司成本）
	billFixPersonal double DEFAULT NULL,							# 固定金（个人收入）
	billFixCompany double DEFAULT NULL,								# 固定金（公司营收）
	costFixPersonal double DEFAULT NULL,							# 固定金（个人支出）
	costFixCompany double DEFAULT NULL,								# 固定金（公司成本）
	personalTaxAgent tinyint(4) DEFAULT NULL,						# 代扣个税（是/否，个人），个人Bill“100%”、个人Cost“0%”
	description varchar(1000) DEFAULT NULL,							# 科目描述
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (itemId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_item_group (
	itemGroupId int(11) NOT NULL AUTO_INCREMENT,					# 科目分组ID
	accountId int(11) NOT NULL,										# 账户ID。AccountId为“1”，是系统创建的。
	corpId int(100) default null,									# 企业ID，用于Inhouse使用
	nameZH varchar(200) DEFAULT NULL,								# 科目分组名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目分组名称（英文）
	itemGroupType tinyint(4) DEFAULT NULL,							# 科目分组类型（1:休假扣款；2:薪资结算分组；5:其他）
	bindItemId int(11) DEFAULT NULL,								# 绑定科目ID
	listMerge tinyint(4) DEFAULT NULL,								# 是否需要按照科目分组合并（列表）
	reportMerge tinyint(4) DEFAULT NULL,							# 是否需要按照科目分组合并（报表）
	invoiceMerge tinyint(4) DEFAULT NULL,							# 是否需要按照科目分组合并（发票）
	description varchar(1000) DEFAULT NULL,
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (itemGroupId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_item_group_relation (
	relationId int(11) NOT NULL AUTO_INCREMENT,						# 科目 - 分组关联ID
	itemGroupId int(11) NOT NULL,									# 科目分组ID
	itemId int(11) NOT NULL,										# 科目ID
	description varchar(1000) DEFAULT NULL,
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_item_mapping (
  	mappingId int(11) NOT NULL AUTO_INCREMENT,						# 科目映射ID
  	accountId int(11) NOT NULL,										# 账户ID
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	itemId int(11) NOT NULL,										# 科目ID
	entityId int(11) NOT NULL,										# 法务实体ID
	businessTypeId int(11) NOT NULL,								# 业务类型ID
  	saleDebit varchar(200) DEFAULT NULL,							# 销售 - 借
  	saleDebitBranch varchar(50) DEFAULT NULL,						# 销售 - 借（按部门），是/否
  	saleCredit varchar(200) DEFAULT NULL,							# 销售 - 贷
  	saleCreditBranch varchar(50) DEFAULT NULL,						# 销售 - 贷（按部门），是/否
  	costDebit varchar(200) DEFAULT NULL,							# 成本 - 借
  	costDebitBranch varchar(50) DEFAULT NULL,						# 成本 - 借（按部门），是/否
  	costCredit varchar(200) DEFAULT NULL,							# 成本 - 贷
  	costCreditBranch varchar(50) DEFAULT NULL,						# 成本 - 贷（按部门），是/否
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (mappingId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_tax (
  	taxId int(11) NOT NULL AUTO_INCREMENT,							# 税率ID
  	accountId int(11) NOT NULL,										# 账户ID
	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	entityId int(11) NOT NULL,										# 法务实体ID
	businessTypeId int(11) NOT NULL,								# 业务类型ID
	nameZH varchar(200) NOT NULL,									# 税率名称（中文）
  	nameEN varchar(200) NOT NULL,									# 税率名称（英文）	
  	saleTax double DEFAULT NULL,									# 销售税率
  	costTax double DEFAULT NULL,									# 成本税率
  	actualTax double DEFAULT NULL,									# 实缴税率
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (taxId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_social_benefit_solution_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 社保方案主表ID，按照城市和户籍定义不同的社保方案
  	accountId int(11) NOT NULL,										# 账户ID
  	corpId int(11) NOT NULL,										# 客户ID
  	sysHeaderId int(11) NOT NULL,									# 社保ID，Super账户创建
  	nameZH varchar(200) NOT NULL,									# 社保方案名称（中文）
  	nameEN varchar(200) NOT NULL,									# 社保方案名称（英文）	
  	sbType tinyint(4) DEFAULT 2,									# 社保方案类型 0:请选择##1:社保##2:公积金##3:综合##4:其他
  	attribute tinyint(4) DEFAULT NULL,								# 社保所属月（当月，次月，上月；如果主表不选择，则按照从表筛选）
  	effective tinyint(4) DEFAULT NULL,								# 社保发生月（当月；如果主表不选择，则按照从表筛选）
  	startDateLimit varchar(25) DEFAULT NULL,						# 申报开始时间（默认为Super创建的社保申报开始时间，不同账户可以往前修改日期，但不能跨月）
  	endDateLimit varchar(25) DEFAULT NULL,							# 申报截止时间（默认为Super创建的社保申报截止时间，不同账户可以往前修改日期，但不能跨月）
  	startRule tinyint(4) DEFAULT NULL,								# 开始月社保缴纳规则（按日期 - 15号以后入职需缴社保，按工作日天数 - 上1天班缴纳社保，按自然月天数 - 超过5天需缴纳社保；如果主表不选择，则按照从表筛选）
  	startRuleRemark varchar(25) DEFAULT NULL,						# 开始月社保缴纳规则备注；如果主表不选择，则按照从表筛选
  	endRule tinyint(4) DEFAULT NULL,								# 结束月社保缴纳规则；如果主表不选择，则按照从表筛选
  	endRuleRemark varchar(25) DEFAULT NULL,							# 结束月社保缴纳规则备注；如果主表不选择，则按照从表筛选
  	attachment varchar(1000) DEFAULT NULL,							# 社保方案附件
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_social_benefit_solution_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 社保方案从表ID，按照城市选择不同的险种；比例须确定下来
  	headerId int(11) NOT NULL,										# 社保方案主表ID
  	sysDetailId int(11) NOT NULL,									# 社保从表ID，Super账户创建
  	itemId int(11) NOT NULL,										# 科目ID，只能从Super账户创建的明细中选择
  	companyPercent double DEFAULT NULL,								# 公司比例 - 默认系统定义数值最低值，可再次更改
  	personalPercent double DEFAULT NULL,							# 个人比例 - 默认系统定义数值最低值，可再次更改
  	companyFloor double DEFAULT NULL,								# 公司基数（低） - 默认系统定义数值，可再次更改
  	companyCap double DEFAULT NULL,									# 公司基数（高） - 默认系统定义数值，可再次更改
  	personalFloor double DEFAULT NULL,								# 个人基数（低） - 默认系统定义数值，可再次更改
  	personalCap double DEFAULT NULL,								# 个人基数（高） - 默认系统定义数值，可再次更改
  	companyFixAmount double DEFAULT NULL,							# 公司固定金 - 默认系统定义数值，可再次更改
  	personalFixAmount double DEFAULT NULL,							# 个人固定金 - 默认系统定义数值，可再次更改
  	attribute tinyint(4) DEFAULT NULL,								# 社保所属月（当月，次月，上月；如果主表不选择，则按照从表筛选）
  	effective tinyint(4) DEFAULT NULL,								# 社保发生月（当月；如果主表不选择，则按照从表筛选）
  	startDateLimit varchar(25) DEFAULT NULL,						# 申报开始时间（默认为Super创建的社保申报开始时间，不同账户可以往前修改日期，但不能跨月；填写一个月中的几号；从表不选则继承主表设置）
  	endDateLimit varchar(25) DEFAULT NULL,							# 申报截止时间（默认为Super创建的社保申报截止时间，不同账户可以往前修改日期，但不能跨月；填写一个月中的几号；从表不选则继承主表设置）
  	startRule tinyint(4) DEFAULT NULL,								# 开始月社保缴纳规则（按日期 - 15号以后入职需缴社保，按工作日天数 - 上1天班缴纳社保，按自然月天数 - 超过5天需缴纳社保；如果主表不选择，则按照从表筛选）
  	startRuleRemark varchar(25) DEFAULT NULL,						# 开始月社保缴纳规则备注；如果主表不选择，则按照从表筛选
  	endRule tinyint(4) DEFAULT NULL,								# 结束月社保缴纳规则；如果主表不选择，则按照从表筛选
  	endRuleRemark varchar(25) DEFAULT NULL,							# 结束月社保缴纳规则备注；如果主表不选择，则按照从表筛选
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_commercial_benefit_solution_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 商保方案主表ID
  	accountId int(11) NOT NULL,										# 账户ID
  	corpId int(11) NOT NULL,										# 客户ID
  	nameZH varchar(200) NOT NULL,									# 商保方案名称（中文）
  	nameEN varchar(200) NOT NULL,									# 商保方案名称（英文）	
  	validFrom datetime DEFAULT NULL,								# 有效开始时间
  	validEnd datetime DEFAULT NULL,									# 有效截止时间
  	attachment varchar(1000) DEFAULT NULL,							# 商保方案附件
  	calculateType tinyint(4) NOT NULL,								# 费用方式（按月，按天）
  	accuracy tinyint(4) DEFAULT NULL,								# 空为使用默认，保留小数位（取整，保留一位，保留二位；如果主表不选择，则按照从表筛选）
  	round tinyint(4) DEFAULT NULL,									# 空为使用默认，小数位保留方式（四舍五入，截取，向上进位；如果主表不选择，则按照从表筛选）
  	freeShortOfMonth tinyint(4) DEFAULT NULL,						# 不全月免费（是/否） - 针对入职、离职不满月情况，与chargeFullMonth不能同时选“是”
	chargeFullMonth	tinyint(4) DEFAULT NULL,						# 按全月计费（是/否） - 针对入职、离职不满月情况，与freeShortOfMonth不能同时选“是”
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_commercial_benefit_solution_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 商保方案从表ID，一个商保方案可以有多个商保科目
  	headerId int(11) NOT NULL,										# 商保方案主表ID
  	itemId int(11) NOT NULL,										# 科目ID，从类型为商保的科目中选择（Account为“1的”以及自己的）
  	purchaseCost double DEFAULT NULL,								# 采购成本，是指商保从保险公司购得的最终价格
  	salesCost double DEFAULT NULL,									# 销售成本，是指采购成本加上公司内部的管理成本
  	salesPrice double DEFAULT NULL,									# 销售价格
  	calculateType tinyint(4) NOT NULL,								# 费用方式（按月，按天）
  	accuracy tinyint(4) DEFAULT NULL,								# 空为使用默认，保留小数位（取整，保留一位，保留二位；如果主表不选择，则按照从表筛选）
  	round tinyint(4) DEFAULT NULL,									# 空为使用默认，小数位保留方式（四舍五入，截取，向上进位；如果主表不选择，则按照从表筛选）
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_certification (
	certificationId int(11) NOT NULL AUTO_INCREMENT,				# 证书 - 奖项Id
	accountId int(11) NOT NULL,										# 账户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
  	nameZH varchar(200) DEFAULT NULL,								# 证书名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 证书名称（英文）
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (certificationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_membership (
	membershipId int(11) NOT NULL AUTO_INCREMENT,					# 社会活动Id
	accountId int(11) NOT NULL,										# 账户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
  	nameZH varchar(200) DEFAULT NULL,								# 社会活动名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 社会活动名称（英文）
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (membershipId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_business_contract_template (
  	templateId int(11) NOT NULL AUTO_INCREMENT,						# 商务合同模板Id
  	accountId int(11) NOT NULL,										# 账户Id
  	corpId int(11) DEFAULT NULL,									# 客户账户Id
  	nameZH varchar(200) DEFAULT NULL,								# 模板中文名
  	nameEN varchar(200) DEFAULT NULL,								# 模板英文名
  	entityIds varchar(1000) DEFAULT NULL,							# 法务实体，可多选（Jason方式存入）
  	businessTypeIds varchar(1000) DEFAULT NULL,						# 业务类型，可多选（Jason方式存入）
  	content text DEFAULT NULL,										# 模板内容，传入参数类似${value}
  	contentType tinyint(4) DEFAULT NULL,							# 模板内容类型，1:Text，2:HTML（默认为Text）
  	description varchar(1000) DEFAULT NULL,							# 模板描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (templateId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_labor_contract_template (
  	templateId int(11) NOT NULL AUTO_INCREMENT,						# 劳动合同模板Id
  	accountId int(11) NOT NULL,										# 账户Id
  	corpId int(11) DEFAULT NULL,									# 客户账户Id
  	contractTypeId int(11) NOT NULL,								# 合同类型（绑定设置 - 工作 - 合同类型）
  	nameZH varchar(200) DEFAULT NULL,								# 模板中文名
  	nameEN varchar(200) DEFAULT NULL,								# 模板英文名
  	entityIds varchar(1000) DEFAULT NULL,							# 法务实体，可多选（Jason方式存入）
  	businessTypeIds varchar(1000) DEFAULT NULL,						# 业务类型，可多选（Jason方式存入）
  	clientIds varchar(1000) DEFAULT NULL,						    # 客户，可多选（Jason方式存入）
  	content text DEFAULT NULL,										# 模板内容，传入参数类似${value}
  	contentType tinyint(4) DEFAULT NULL,							# 模板内容类型，1:Text，2:HTML（默认为Text）
  	description varchar(1000) DEFAULT NULL,							# 模板描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (templateId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_industry_type (
	typeId int(11) NOT NULL AUTO_INCREMENT,							# 行业类别Id
	accountId int(11) NOT NULL,										# 账户Id
  	corpId int(11)	default null,									# 企业ID，用于Inhouse使用
	nameZH varchar(200) DEFAULT NULL,								# 行业类别名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 行业类别名称（英文）
  	description varchar(1000) DEFAULT NULL,							# 描述信息
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (typeId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_calendar_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 日历主表Id
  	accountId int(11) NOT NULL,										# 账户Id
  	corpId int(11) NOT NULL,										# 客户Id
  	nameZH varchar(200) NOT NULL,									# 日历名称（中文）
  	nameEN varchar(200) NOT NULL,									# 日历名称（英文）
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_calendar_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 日历从表Id
  	headerId int(11) NOT NULL,										# 日历主表Id
  	nameZH varchar(200) NOT NULL,									# 日期名称（中文）
  	nameEN varchar(200) NOT NULL,									# 日期名称（英文）
  	day datetime NOT NULL,											# 日期
  	dayType tinyint(4) NOT NULL,									# 日期类型（1:工作日，2:休息日，3:节假日）
  	changeDay datetime DEFAULT NULL,								# 调换日期，当日期类型选者“工作日”情况显示
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_shift_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 排班主表Id
  	accountId int(11) NOT NULL,										# 账户Id
  	corpId int(11) NOT NULL,										# 客户Id
  	nameZH varchar(200) NOT NULL,									# 排班名称（中文）
  	nameEN varchar(200) NOT NULL,									# 排班名称（英文）
  	shiftType tinyint(4) NOT NULL,									# 排班类型（1:按周，2:按天，3:自定义），按天和自定义之外的休息日和节假日参考日历
  	shiftIndex tinyint(4) NOT NULL,									# 排班频率（例如：每多少周或每多少天）	
  	startDate datetime DEFAULT NULL,								# 排班开始时间（按天会用到）
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_shift_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 排班从表Id
  	headerId int(11) NOT NULL,										# 排班主表Id
  	nameZH varchar(200) DEFAULT NULL,								# 日期名称（中文），按周和按天自动生成，自定义不使用
  	nameEN varchar(200) DEFAULT NULL,								# 日期名称（英文）
  	dayIndex tinyint(4) DEFAULT NULL,								# 日期序列（从1开始排）
  	shiftDay datetime DEFAULT NULL,									# 排班日期，自定义使用，其他不使用
  	shiftPeriod varchar(500) DEFAULT NULL,							# 工作时间段（1:00:00 - 00:30，2:00:30 - 01:00，...），按周默认显示上午7点至晚上7点的区间，其他全部展开，Jason方式存储
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_shift_exception (
  	exceptionId int(11) NOT NULL AUTO_INCREMENT,					# 排班例外Id
  	headerId int(11) NOT NULL,										# 排班主表Id
  	nameZH varchar(200) DEFAULT NULL,								# 例外名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 例外名称（英文）
  	dayType	tinyint(4) DEFAULT NULL,								# 例外日期类型（1:工作日，2:休息日，3:节假日）
  	exceptionDay datetime DEFAULT NULL,								# 例外日期，自定义使用（精确到某一天）
  	exceptionPeriod varchar(500) DEFAULT NULL,						# 例外时间段（1:00:00 - 00:30，2:00:30 - 01:00，...）
  	exceptionType tinyint(4) DEFAULT NULL,						    # 例外类型（0：请选择；1：请假；2：加班）
  	itemId int(11) DEFAULT NULL,									# 例外科目
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (exceptionId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_bank (
	bankId int(11) NOT NULL AUTO_INCREMENT,							# 银行Id
	accountId int(11) NOT NULL,										# 账户Id
	corpId int(11) NOT NULL,										# 客户Id
	cityId int(11) DEFAULT NULL,									# 城市ID
  	nameZH varchar(200) DEFAULT NULL,								# 银行名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 银行名称（英文）
  	addressZH varchar(200) DEFAULT NULL,							# 中文地址
	addressEN varchar(200) DEFAULT NULL,							# 英文地址
	telephone varchar(50) DEFAULT NULL,								# 电话
	fax varchar(50) DEFAULT NULL,									# 传真
	postcode varchar(50) DEFAULT NULL,								# 邮编
	email varchar(200) DEFAULT NULL,								# 邮件地址
	website varchar(200) DEFAULT NULL,								# 网址
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (bankId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_setting (	
	settingId int(11) NOT NULL AUTO_INCREMENT,						# 设置ID
	accountId int(11) NOT NULL,										# 账户ID
	corpId int(11) DEFAULT NULL,									# 客户ID
	userId int(11) DEFAULT NULL,									# 用户ID
	baseInfo tinyint(4) DEFAULT NULL,								# 基本信息
	baseInfoRank tinyint(4) DEFAULT NULL,							# 基本信息序号
	message tinyint(4) DEFAULT NULL,								# 通知
	messageRank tinyint(4) DEFAULT NULL,							# 通知序号
	dataView tinyint(4) DEFAULT NULL,								# 数据概览
	dataViewRank tinyint(4) DEFAULT NULL,							# 数据概览序号
	clientContract tinyint(4) DEFAULT NULL,							# 商务合同
	clientContractRank tinyint(4) DEFAULT NULL,						# 商务合同序号
	orders tinyint(4) DEFAULT NULL,									# 订单
	ordersRank tinyint(4) DEFAULT NULL,								# 订单序号
	contractService tinyint(4) DEFAULT NULL,						# 派送信息
	contractServiceRank tinyint(4) DEFAULT NULL,					# 派送信息序号
	attendance tinyint(4) DEFAULT NULL,								# 考勤
	attendanceRank tinyint(4) DEFAULT NULL,							# 考勤序号
	sb tinyint(4) DEFAULT NULL,										# 社保
	sbRank tinyint(4) DEFAULT NULL,									# 社保序号	
	cb tinyint(4) DEFAULT NULL,										# 商报
	cbRank tinyint(4) DEFAULT NULL,									# 商报序号	
	settlement tinyint(4) DEFAULT NULL,								# 结算
	settlementRank tinyint(4) DEFAULT NULL,							# 结算序号
	payment tinyint(4) DEFAULT NULL,								# 薪酬
	paymentRank tinyint(4) DEFAULT NULL,							# 薪酬序号
	income tinyint(4) DEFAULT NULL,									# 收款
	incomeRank tinyint(4) DEFAULT NULL,								# 收款序号
	employeeChange tinyint(4) DEFAULT NULL,							# 人员变更
	employeeChangeRank tinyint(4) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (settingId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_annual_leave_rule_header (
	headerId int(11) NOT NULL AUTO_INCREMENT,						# 年假规则主表ID
	accountId int(11) NOT NULL,										# 账户ID
	corpId int(11) DEFAULT NULL,									# 客户ID
	nameZH varchar(200) DEFAULT NULL,								# 名称（中文），必填
  	nameEN varchar(200) DEFAULT NULL,								# 名称（英文）
  	divideType tinyint(4) DEFAULT NULL,								# 折算方式
  	baseOn tinyint(4) DEFAULT NULL,									# 基于，必填（1:入司日期；2:开始工作日期）
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_annual_leave_rule_detail (
	detailId int(11) NOT NULL AUTO_INCREMENT,						# 年假规则从表ID
  	headerId int(11) NOT NULL,										# 年假规则主表ID
  	seniority int(4) DEFAULT NULL,									# 工龄满X年
  	positionGradeId int(11)  DEFAULT NULL,							# 对应职级ID
  	legalHours double(8,1) DEFAULT NULL,							# 法定小时数
  	benefitHours double(8,1)DEFAULT NULL,							# 福利小时数
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_sick_leave_salary_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 病假工资主表ID
  	accountId int(11) NOT NULL,										# 账户ID
	corpId int(11) DEFAULT NULL,									# 客户ID
  	nameZH varchar(200) DEFAULT NULL,								# 名称（中文），必填
  	nameEN varchar(200) DEFAULT NULL,								# 名称（英文）
  	itemId int(11) NOT NULL,										# 科目ID，必填，同一Client不能重复（如果Client为空，同一Account不能重复）
  	baseOn tinyint(4) DEFAULT NULL,									# 基于，必填（1:入司日期；2:开始工作日期）
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_mgt_sick_leave_salary_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 病假工资从表ID
  	headerId int(11) NOT NULL,										# 病假工资主表ID
  	rangeFrom double DEFAULT NULL,									# 工作月数（开始），下拉选项（0-120）
  	rangeTo double DEFAULT NULL,									# 工作月数（结束），下拉选项（0-120），必须大于或等于开始
  	percentage double DEFAULT NULL,									# 带薪比例，百分比（介于0-100）
  	fix double DEFAULT NULL,										# 固定金
  	deduct double DEFAULT NULL,										# 固定扣款
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE  hro_mgt_resign_template (
  	templateId int(11) NOT NULL AUTO_INCREMENT,						# 退工单模板Id
  	accountId int(11) NOT NULL,										# 账户Id
  	corpId int(11) DEFAULT NULL,									# corpId
  	templateType tinyint(4) DEFAULT NULL,                           # (1:退工单 2:收入证明 3:在职证明 4:离职证明 5:担保证明)
  	nameZH varchar(200) DEFAULT NULL,								# 模板中文名
  	nameEN varchar(200) DEFAULT NULL,								# 模板英文名
  	content text DEFAULT NULL,										# 模板内容，传入参数类似${value}
  	contentType tinyint(4) DEFAULT NULL,							# 模板内容类型，1:Text，2:HTML（默认为Text）
  	description varchar(1000) DEFAULT NULL,							# 模板描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (templateId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
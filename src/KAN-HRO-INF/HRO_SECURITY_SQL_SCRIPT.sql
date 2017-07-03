use HRO;

DROP TABLE IF EXISTS hro_sec_user;									# 系统用户，与公司员工对应，但公司员工不一定都是系统用户
DROP TABLE IF EXISTS hro_sec_staff;									# 公司内部职员信息（基本表）
DROP TABLE IF EXISTS hro_sec_branch;								# 部门，只有Position跟部门有关系
DROP TABLE IF EXISTS hro_sec_location;								# 办公地点、分支机构（包含通讯信息）
DROP TABLE IF EXISTS hro_sec_entity;	                            # 法务实体
DROP TABLE IF EXISTS hro_sec_business_type                          # 业务类型
DROP TABLE IF EXISTS hro_sec_module_right_relation;					# 全局数据访问权限 - Right
DROP TABLE IF EXISTS hro_sec_module_rule_relation;					# 全局数据访问权限 - Rule
DROP TABLE IF EXISTS hro_sec_position;								# 职位表（公司内部，非雇员）
DROP TABLE IF EXISTS hro_sec_position_staff_relation;				# 公司内部员工跟职位的关系，一个员工可以对应多个职位
DROP TABLE IF EXISTS hro_sec_position_grade;						# 职级，审批可作为节点
DROP TABLE IF EXISTS hro_sec_position_grade_currency;				# 职级对应的工资参考水平
DROP TABLE IF EXISTS hro_sec_position_group_relation;				# 职位跟群组的关系，一个职位可以对应多个群组
DROP TABLE IF EXISTS hro_sec_position_module_right_relation;		# 一个职位对应不同模块的不同功能权限
DROP TABLE IF EXISTS hro_sec_position_column_right_relation;		# 一个职位对应不同字段的不同功能权限
DROP TABLE IF EXISTS hro_sec_position_module_rule_relation;			# 职位数据访问权限
DROP TABLE IF EXISTS hro_sec_group;									# 职位群组
DROP TABLE IF EXISTS hro_sec_group_module_right_relation;			# 一个群组对应不同模块的不同功能权限
DROP TABLE IF EXISTS hro_sec_group_column_right_relation;			# 一个群组对应不同字段的不同功能权限
DROP TABLE IF EXISTS hro_sec_group_module_rule_relation;			# 职位群组数据访问权限
DROP TABLE IF EXISTS hro_sec_org_shoot								# 组织机构图历史快照

CREATE TABLE hro_sec_user (
  	userId int(11) NOT NULL AUTO_INCREMENT,							# 用户ID
  	accountId int(11) NOT NULL,										# 账户ID
  	corpId int(11)  DEFAULT NULL,									# InHouse账户ID
  	userIds varchar(1000) DEFAULT NULL,								# 关联InHouse账户ID
  	staffId int(11) NOT NULL,										# 员工ID
  	username varchar(50) DEFAULT NULL,								# 用户名
  	password varchar(50) DEFAULT NULL,								# 密码
  	bindIP varchar(50) NULL,										# 绑定IP地址，如果绑定只能指定IP地址访问
  	lastLogin datetime DEFAULT NULL,								# 最后登录时间
  	lastLoginIP varchar(50) NULL,									# 最后登录IP地址
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
  	PRIMARY KEY (userId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_staff (
	staffId int(11) NOT NULL AUTO_INCREMENT,						# 员工ID
	accountId int(11) NOT NULL,										# 账户ID
	corpId int(11) default null,									# 企业ID，用于Inhouse使用
	staffNo varchar(50) DEFAULT NULL,								# 员工编号
	salutation tinyint(4) DEFAULT NULL,								# 称呼，即用作性别
  	nameZH varchar(200) DEFAULT NULL,								# 中文名
  	nameEN varchar(200) DEFAULT NULL,								# 英文名
  	birthday datetime DEFAULT NULL,									# 出生年月
  	bizPhone varchar(50) DEFAULT NULL,								# 工作电话
  	bizExt varchar(25) DEFAULT NULL,								# 工作电话分机号（暂时不用）
  	personalPhone varchar(50) DEFAULT NULL,							# 家庭电话
  	bizMobile varchar(50) DEFAULT NULL,								# 公司手机
  	personalMobile varchar(50) DEFAULT NULL,						# 私人手机
  	otherPhone varchar(50) DEFAULT NULL,							# 其他电话
  	fax varchar(50) DEFAULT NULL,									# 传真
  	bizEmail varchar(200) DEFAULT NULL,								# 公司邮箱
  	personalEmail varchar(200) DEFAULT NULL,						# 私人邮箱
	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
  	maritalStatus tinyint(4) DEFAULT NULL,							# 婚姻状况
  	registrationHometown varchar(50) DEFAULT NULL,					# 户籍城市（籍贯）
  	registrationAddress varchar(200) DEFAULT NULL,					# 户籍地址
  	personalAddress varchar(200) DEFAULT NULL,						# 家庭地址
  	personalPostcode varchar(50) DEFAULT NULL,						# 家庭地址邮编
  	description varchar(1000) DEFAULT NULL,							# 个人描述
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 员工状态，1:在职，2:招募，3:离职，4:候选
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (staffId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_branch (
	branchId int(11) NOT NULL AUTO_INCREMENT,						# 部门ID
	accountId int(11) NOT NULL,										# 账户ID
	corpId  int(11) DEFAULT NULL,
	parentBranchId int(11) DEFAULT NULL,							
	entityId int(11) DEFAULT NULL,									#法务实体
	businessTypeId int(11) DEFAULT NULL,							#业务类型
	branchCode varchar(12) NOT NULL,								# 部门编号，0-6位字符
	nameZH varchar(200) DEFAULT NULL,								# 部门中文名称（简称）
	nameEN varchar(200) DEFAULT NULL,								# 部门英文名称（简称）
	description varchar(1000) DEFAULT NULL,							# 部门描述
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
  	PRIMARY KEY (branchId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_location (
	locationId int(11) NOT NULL AUTO_INCREMENT,						# 地址ID（通常是指一个办公地点），Position与Location关联
	accountId int(11) NOT NULL,										# 账户ID
	corpId int(11) default null,									# 企业ID，用于Inhouse使用
	cityId int(11) NOT NULL,										# 城市ID
	nameZH varchar(200) DEFAULT NULL,								# 中文名称
	nameEN varchar(200) DEFAULT NULL,								# 英文名称
	addressZH varchar(200) DEFAULT NULL,							# 中文地址
	addressEN varchar(200) DEFAULT NULL,							# 英文地址
	telephone varchar(50) DEFAULT NULL,								# 电话
	fax varchar(50) DEFAULT NULL,									# 传真
	postcode varchar(50) DEFAULT NULL,								# 邮编
	email varchar(200) DEFAULT NULL,								# 邮件地址
	website varchar(200) DEFAULT NULL,								# 网址
	description varchar(1000) DEFAULT NULL,							# 描述
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	lastLogin datetime DEFAULT NULL,
  	lastLoginIP varchar(50) NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (locationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_entity (
	entityId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,
	corpId int(11) default null,										
	locationId int(11) NOT NULL,
	title varchar(200) DEFAULT NULL,
  	nameZH varchar(200) DEFAULT NULL,
  	nameEN varchar(200) DEFAULT NULL,
  	bizType varchar(200) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (entityId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_business_type (
	businessTypeId int(11) NOT NULL AUTO_INCREMENT,					# 业务类型ID
	accountId int(11) NOT NULL,										# 账户ID，“0”表示账户创建
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
	nameZH varchar(200) DEFAULT NULL,								# 业务类型名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 业务类型名称（英文）
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (businessTypeId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_module_right_relation (	
	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，各模块的功能访问权限，账户全局设置
	accountId int(11) NOT NULL,										# 账户ID
  	moduleId int(11) NOT NULL,										# 模块ID
  	rightId int(11) NOT NULL,										# 功能规则ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_module_rule_relation (	
	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，各模块的数据访问权限，账户全局设置
	accountId int(11) NOT NULL,										# 账户ID
  	moduleId int(11) NOT NULL,										# 模块ID
  	ruleId int(11) NOT NULL,										# 数据权限ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_position (
  	positionId int(11) NOT NULL AUTO_INCREMENT,						# 职位ID
  	accountId int(11) NOT NULL,										# 账户ID
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	locationId int(11) NOT NULL,									# 地址ID
  	branchId int(11) NOT NULL,										# 部门ID
  	positionGradeId int(11) NOT NULL,								# 职级
  	titleZH varchar(200) DEFAULT NULL,								# 职位名称（中文）
  	titleEN varchar(200) DEFAULT NULL,								# 职位名称（英文）
  	description varchar(1000) DEFAULT NULL,							# 职位描述（JD）
  	skill varchar(1000) DEFAULT NULL,								# 所需技能
  	note varchar(1000) DEFAULT NULL,								# 备忘录
  	attachment varchar(5000) DEFAULT NULL,							# 附件
  	parentPositionId int(11) DEFAULT NULL,							# 上级职位
  	isVacant tinyint(4) DEFAULT NULL,								# 是否是空缺职位
	isIndependentDisplay tinyint(4) DEFAULT NULL,					# 是否独立显示
  	needPublish tinyint(4) DEFAULT NULL,							# 是否需要发布
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
  	PRIMARY KEY (positionId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_position_staff_relation (
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID
  	positionId int(11) NOT NULL,									# 职位对应的ID
  	staffId int(11) NOT NULL,										# 所属人或代理人ID
  	staffType tinyint(4) NOT NULL,									# 所属人（1）或代理人（2），如果是代理人，则代理开始和结束时间不能为空
  	agentStart datetime DEFAULT NULL,								# 代理期间开始
  	agentEnd datetime DEFAULT NULL,									# 代理期间结束
  	description varchar(1000) DEFAULT NULL,							# 描述或说明
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_position_grade (
  	positionGradeId int(11) NOT NULL AUTO_INCREMENT,				# 职级ID
  	accountId int(11) NOT NULL,										# 账户ID
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	gradeNameZH varchar(200) DEFAULT NULL,							# 职级中文名称
  	gradeNameEN varchar(200) DEFAULT NULL,							# 职级英文名称
  	weight smallint(8) DEFAULT NULL,								# 权重
  	description varchar(1000) DEFAULT NULL,							# 职级描述
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

CREATE TABLE hro_sec_position_grade_currency (
  	positionGradeId int(11) NOT NULL,								# 职级ID，对应不同币种的上下限
  	currencyId varchar(25) NOT NULL,								# 货币ID
  	minSalary double DEFAULT NULL,									# 薪资上限
  	maxSalary double DEFAULT NULL,									# 薪资下限
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

CREATE TABLE hro_sec_position_group_relation (
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，一个Position可以属于多个群组
  	positionId int(11) NOT NULL,									# 职位ID
  	groupId int(11) NOT NULL,										# 群组ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_position_module_right_relation (						 
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，一个职位能访问哪些模块，并对这些模块有什么功能权限
	positionId int(11) NOT NULL,									# 职位ID 
  	moduleId int(11) NOT NULL,										# 模块ID
  	rightId int(11) NOT NULL,										# 访问权限ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_position_column_right_relation (						 
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，一个职位能访问哪些字段，并对这些字段有什么功能权限
	positionId int(11) NOT NULL,									# 职位ID
  	columnId int(11) NOT NULL,										# 字段ID
  	rightId int(11) NOT NULL,										# 访问权限ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_position_module_rule_relation (	
	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，一个职位能访问到哪些模块，并对这些模块有什么数据权限
	positionId int(11) NOT NULL,									# 职位ID
  	moduleId int(11) NOT NULL,										# 模块ID
  	ruleId int(11) NOT NULL,										# 数据权限ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_group (
  	groupId int(11) NOT NULL AUTO_INCREMENT,						# 职位组ID
  	accountId int(11) NOT NULL,										# 账户ID，如果是“1”，说明是公共群组
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	nameZH varchar(200) DEFAULT NULL,								# 职位组中文名
  	nameEN varchar(200) DEFAULT NULL,								# 职位组英文名
  	hrFunction tinyint(4) DEFAULT NULL,								# HR职能（只有人事部才该有）		
  	description varchar(1000) DEFAULT NULL,							# 职位组描述
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
  	PRIMARY KEY (groupId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_group_column_right_relation (						 
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，一个群组能访问哪些字段，并对这些群组有什么功能权限
	groupId int(11) NOT NULL,										# 群组ID
  	columnId int(11) NOT NULL,										# 字段ID
  	rightId int(11) NOT NULL,										# 访问权限ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE _module_right_relation (						 
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，一个群组能访问哪些模块，并对这些模块有什么功能权限
	groupId int(11) NOT NULL,										# 群组ID
  	moduleId int(11) NOT NULL,										# 模块ID
  	rightId int(11) NOT NULL,										# 访问权限ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_group_module_rule_relation (	
	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，一个群组能访问到哪些模块，并对这些群组有什么数据权限
	groupId int(11) NOT NULL,										# 群组ID
  	moduleId int(11) NOT NULL,										# 模块ID
  	ruleId int(11) NOT NULL,										# 数据权限ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sec_org_shoot(
	shootId int(11) NOT NULL AUTO_INCREMENT,						# 快照ID
	accountId int(11) NOT NULL,										# 账户ID，如果是“1”，说明是公共群组
	corpId int(11) DEFAULT NULL,										# 授权ID
	nameZH varchar(200)DEFAULT NULL,								# 中文名		
	nameEN varchar(200)DEFAULT NULL,								# 英文名
	shootType tinyint(4) DEFAULT NULL,								# 快照类型 1：部门 2:职位
	shootData mediumblob DEFAULT NULL,								# 快照二进制数据
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (shootId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
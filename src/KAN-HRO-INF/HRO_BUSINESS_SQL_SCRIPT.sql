use HRO;

DROP TABLE IF EXISTS hro_biz_employee;								# 雇员
DROP TABLE IF EXISTS hro_biz_employee_emergency						# 雇员 - 紧急联系人
DROP TABLE IF EXISTS hro_biz_employee_education						# 雇员 - 学习经历
DROP TABLE IF EXISTS hro_biz_employee_work							# 雇员 - 工作经历
DROP TABLE IF EXISTS hro_biz_employee_skill							# 雇员 - 技能
DROP TABLE IF EXISTS hro_biz_employee_language						# 雇员 - 语言技能
DROP TABLE IF EXISTS hro_biz_employee_certification					# 雇员 - 证书，奖项，培训
DROP TABLE IF EXISTS hro_biz_employee_membership					# 雇员 - 社会活动
DROP TABLE IF EXISTS hro_biz_employee_log;							# 雇员 - 日志跟踪
DROP TABLE IF EXISTS hro_biz_employee_contract;						# 雇员 - 合同
DROP TABLE IF EXISTS hro_biz_employee_contract_property;			# 雇员 - 合同参数
DROP TABLE IF EXISTS hro_biz_employee_contract_sb					# 雇员 - 合同 - 社保方案
DROP TABLE IF EXISTS hro_biz_employee_contract_sb_detail			# 雇员 - 合同 - 社保方案明细
DROP TABLE IF EXISTS hro_biz_employee_contract_cb					# 雇员 - 合同 - 商保方案
DROP TABLE IF EXISTS hro_biz_employee_contract_salary				# 雇员 - 合同 - 薪酬方案
DROP TABLE IF EXISTS hro_biz_employee_contract_performance			# 雇员 - 合同 - 绩效设置
DROP TABLE IF EXISTS hro_biz_employee_contract_ot					# 雇员 - 合同 - 加班设置
DROP TABLE IF EXISTS hro_biz_employee_contract_leave				# 雇员 - 合同 - 请假设置
DROP TABLE IF EXISTS hro_biz_employee_contract_other				# 雇员 - 合同 - 其他设置
DROP TABLE IF EXISTS hro_biz_employee_contract_settlement			# 雇员 - 合同 - 成本/营收
DROP TABLE IF EXISTS hro_biz_employee_contract_temp;				# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_employee_contract_salary_temp;			# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_employee_contract_sb_temp;				# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_employee_contract_cb_temp;				# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_employee_contract_leave_temp;			# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_employee_contract_ot_temp;				# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_employee_contract_other_temp;			# 临时表 - 用于导入

DROP TABLE IF EXISTS hro_biz_employee_positionchange;				# 异动、调薪
DROP TABLE IF EXISTS hro_biz_client;								# 客户
DROP TABLE IF EXISTS hro_biz_client_contact;						# 客户 - 联系人
DROP TABLE IF EXISTS hro_biz_client_user;							# 客户 - 用户
DROP TABLE IF EXISTS hro_biz_client_group;							# 客户 - 集团
DROP TABLE IF EXISTS hro_biz_client_invoice;						# 客户 - 账单地址
DROP TABLE IF EXISTS hro_biz_client_contract;						# 客户 - 商务合同
DROP TABLE IF EXISTS hro_biz_client_contract_property;				# 客户 - 商务合同参数
DROP TABLE IF EXISTS hro_biz_client_order_header					# 客户 - 订单主表
DROP TABLE IF EXISTS hro_biz_client_order_header_rule				# 客户 - 订单主表规则
DROP TABLE IF EXISTS hro_biz_client_order_detail					# 客户 - 订单从表
DROP TABLE IF EXISTS hro_biz_client_order_detail_rule				# 客户 - 订单从表规则
DROP TABLE IF EXISTS hro_biz_client_order_detail_sb_rule			# 客户 - 订单补缴规则
DROP TABLE IF EXISTS hro_biz_client_order_sb						# 客户 - 订单社保（定义社保选取范围）
DROP TABLE IF EXISTS hro_biz_client_order_cb						# 客户 - 订单商保（定义商保选取范围）
DROP TABLE IF EXISTS hro_biz_client_order_performance				# 客户 - 订单绩效设置
DROP TABLE IF EXISTS hro_biz_client_order_ot						# 客户 - 订单加班设置
DROP TABLE IF EXISTS hro_biz_client_order_leave						# 客户 - 订单休假设置
DROP TABLE IF EXISTS hro_biz_client_order_other						# 客户 - 订单其他设置
DROP TABLE IF EXISTS hro_biz_vendor;								# 供应商
DROP TABLE IF EXISTS hro_biz_vendor_contact;						# 供应商 - 联系人
DROP TABLE IF EXISTS hro_biz_vendor_user;							# 供应商 - 用户
DROP TABLE IF EXISTS hro_biz_vendor_service;						# 供应商 - 服务
DROP TABLE IF EXISTS hro_biz_attendance_leave_header;				# 考勤 - 请假主表
DROP TABLE IF EXISTS hro_biz_attendance_leave_detail;				# 考勤 - 请假从表
DROP TABLE IF EXISTS hro_biz_attendance_ot_header;					# 考勤 - 加班主表
DROP TABLE IF EXISTS hro_biz_attendance_ot_detail;					# 考勤 - 加班从表
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_batch;			# 考勤 - 批次
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_header;			# 考勤 - 主表
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_detail;			# 考勤 - 从表
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_allowance;		# 考勤 - 奖金、补助、报销
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_allowance_temp;	# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_header_temp;		# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_attendance_leave_detail_temp;			# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_attendance_leave_header_temp;			# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_attendance_ot_detail_temp;				# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_attendance_ot_header_temp;				# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_attendance_import_header;				# 临时表 - 用于导入
DROP TABLE IF EXISTS hro_biz_attendance_import_detail;				# 临时表 - 用于导入

CREATE TABLE hro_biz_employee (
	employeeId int(11) NOT NULL AUTO_INCREMENT,						# 雇员Id
	accountId int(11) NOT NULL,										# 账户Id
	corpId int(11) NOT NULL,										# 客户账户Id
	employeeNo varchar(50) DEFAULT NULL,							# 雇员编号
	nameZH varchar(200) DEFAULT NULL,								# 中文名
  	nameEN varchar(200) DEFAULT NULL,								# 英文名
	salutation tinyint(4) DEFAULT NULL,								# 称呼，即用作性别
  	birthday datetime DEFAULT NULL,									# 出生年月
  	maritalStatus tinyint(4) DEFAULT NULL,							# 婚姻状况
  	nationNality smallint(8) DEFAULT NULL,							# 国籍
  	birthdayPlace varchar(100) DEFAULT NULL,						# 出生地
  	residencyCityId int(11) DEFAULT NULL,							# 籍贯
  	residencyCity varchar(200) DEFAULT NULL,						# 户籍城市
  	residencyAddress varchar(200) DEFAULT NULL,						# 户籍地址
  	personalAddress varchar(200) DEFAULT NULL,						# 当前住址
  	personalPostcode varchar(50) DEFAULT NULL,						# 住址邮编
  	highestEducation tinyint(4) DEFAULT NULL,						# 最高学历
  	recordNo varchar(50) DEFAULT NULL,								# 档案编号
  	recordAddress varchar(200) DEFAULT NULL,						# 档案所在地
  	residencyType tinyint(4) DEFAULT NULL,							# 户籍性质，对应社保模块中的Residency
  	graduationDate datetime DEFAULT NULL,							# 毕业日期
  	onboardDate datetime DEFAULT NULL,								# 进入公司的时间
	startWorkDate datetime DEFAULT NULL,							# 首次参与工作时间
  	hasForeignerWorkLicence tinyint(4) DEFAULT NULL,				# 外国人就业许可证
  	foreignerWorkLicenceNo varchar(50) DEFAULT NULL,				# 外就证编号
  	foreignerWorkLicenceEndDate datetime DEFAULT NULL,				# 外就证失效日期
   	hasResidenceLicence tinyint(4) DEFAULT NULL,					# 居住证
   	residenceNo varchar(50) DEFAULT NULL,							# 居住证编号
   	residenceStartDate datetime DEFAULT NULL,						# 居住证生效日期				
   	residenceEndDate datetime DEFAULT NULL,							# 居住证失效日期
   	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
  	certificateStartDate datetime DEFAULT NULL,						# 证件生效日期		
  	certificateEndDate datetime DEFAULT NULL,						# 证件失效日期		
  	certificateAwardFrom varchar(200) DEFAULT NULL,					# 证件颁发机构
  	bankId int(11) DEFAULT NULL,									# 银行
  	bankBranch varchar(100) DEFAULT NULL,							# 开户网点
  	bankAccount varchar(50) DEFAULT NULL,							# 银行账户
	phone1 varchar(50) DEFAULT NULL,								# 电话
  	mobile1 varchar(50) DEFAULT NULL,								# 手机
  	email1 varchar(200) DEFAULT NULL,								# 邮箱
  	website1 varchar(200) DEFAULT NULL,								# 个人主页
  	phone2 varchar(50) DEFAULT NULL,								# 电话（备用）
  	mobile2 varchar(50) DEFAULT NULL,								# 手机（备用）
  	email2 varchar(200) DEFAULT NULL,								# 邮箱（备用）
  	website2 varchar(200) DEFAULT NULL,								# 个人主页（备用）
	im1Type tinyint(4) DEFAULT NULL,								# 即时通讯 - 1
  	im1 varchar(200) DEFAULT NULL,									# 即时通讯号码	
  	im2Type tinyint(4) DEFAULT NULL,								# 即时通讯 - 2
  	im2 varchar(200) DEFAULT NULL,									# 即时通讯号码
  	im3Type tinyint(4) DEFAULT NULL,								# 即时通讯 - 3
  	im3 varchar(200) DEFAULT NULL,									# 即时通讯号码
  	im4Type tinyint(4) DEFAULT NULL,								# 即时通讯 - 4
  	im4 varchar(200) DEFAULT NULL,									# 即时通讯号码
  	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	photo varchar(1000) DEFAULT NULL,								# 形象照片
  	attachment varchar(5000) DEFAULT NULL,							# 附件
  	resumeZH text DEFAULT NULL,										# 简历（中文）
  	resumeEN text DEFAULT NULL,										# 简历（英文）
  	publicCode varchar(100) DEFAULT NULL,							# 雇员/员工个人密钥（公钥 - HRM使用）
  	_tempPositionIds varchar(100) DEFAULT NULL,						# 冗余字段存放职位
  	_tempBranchIds varchar(50) DEFAULT NULL,						# 冗余字段存放部门
  	_tempParentBranchIds varchar(50) DEFAULT NULL,					# 冗余字段存放上级部门
  	_tempParentPositionIds varchar(50) DEFAULT NULL,				# 冗余字段存放上级职位
  	_tempParentPositionOwners varchar(50) DEFAULT NULL,				# 冗余字段存放上级职位所属人
  	_tempParentPositionBranchIds varchar(50) DEFAULT NULL,			# 冗余字段存放上级职位部门 
  	_tempPositionLocationIds varchar(50) DEFAULT NULL,				# 冗余字段存放职位工作地址 
  	_tempUserName varchar(50) DEFAULT NULL,							# 冗余字段存放用户名
  	description varchar(1000) DEFAULT NULL,							# 备注
	deleted tinyint(4) DEFAULT NULL,								# 是否已被删除
  	status tinyint(4) DEFAULT NULL,									# 雇员状态，1:在职，2:招募，3:离职，4:候选
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (employeeId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_emergency (
	employeeEmergencyId int(11) NOT NULL AUTO_INCREMENT,			# 紧急联系方式Id
	employeeId int(11) NOT NULL,									# 员工Id
	relationshipId tinyint(4) DEFAULT NULL,							# 与紧急联系人关系（丈夫，妻子，父亲，母亲，子女，祖父母，亲戚，同事，同学，朋友，其他）
	name varchar(200) DEFAULT NULL,									# 姓名
  	phone varchar(50) DEFAULT NULL,									# 电话号码
  	mobile varchar(50) DEFAULT NULL,								# 移动电话
  	address varchar(200) DEFAULT NULL,								# 联系地址
  	postcode varchar(50) DEFAULT NULL,								# 邮编
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (employeeEmergencyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_education (
	employeeEducationId int(11) NOT NULL AUTO_INCREMENT,			# 学习经历Id
	employeeId int(11) NOT NULL,									# 员工Id
	schoolName varchar(200) DEFAULT NULL,							# 学校名称
	startDate datetime DEFAULT NULL,								# 开始学习时间
	endDate datetime DEFAULT NULL,									# 结束学习时间
	major varchar(200) DEFAULT NULL,								# 专业
  	educationId varchar(50) DEFAULT NULL,							# 教育程度（绑定设置 - 资质 - 教育程度）
  	schoolingLength tinyint(4) DEFAULT NULL,						# 学制
  	studyType tinyint(4) DEFAULT NULL,								# 学习形式
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (employeeEducationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_work (
	employeeWorkId int(11) NOT NULL AUTO_INCREMENT,					# 工作经历Id
	employeeId int(11) NOT NULL,									# 员工Id
	companyName varchar(200) DEFAULT NULL,							# 公司名称
	startDate datetime DEFAULT NULL,								# 开始工作时间
	endDate datetime DEFAULT NULL,									# 结束工作时间（当前在职无需填写）
	department varchar(100) DEFAULT NULL,							# 任职部门
	positionId int(11) DEFAULT NULL,								# 任职职位（绑定设置 - 工作 - 外部职位）
	positionName varchar(200) DEFAULT NULL,	                        # 职位名称
	industryId int(11) DEFAULT NULL,								# 所属行业
  	companyType tinyint(4) DEFAULT NULL,							# 企业性质
  	size tinyint(4) DEFAULT NULL,						    		# 公司规模
	contact varchar(200) DEFAULT NULL,								# 联系人
  	phone varchar(50) DEFAULT NULL,									# 联系人电话
  	mobile varchar(50) DEFAULT NULL,								# 联系人手机
  	description varchar(1000) DEFAULT NULL,							# 工作内容					
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（在职，离职）									
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (employeeWorkId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_skill (
	employeeSkillId int(11) NOT NULL AUTO_INCREMENT,				# 雇员技能Id
	employeeId int(11) NOT NULL,									# 员工Id
  	skillId int(11) NOT NULL,										# 技能Id（绑定设置 - 资质 - 技能）
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (employeeSkillId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_language (
	employeeLanguageId int(11) NOT NULL AUTO_INCREMENT,				# 雇员技能Id
	employeeId int(11) NOT NULL,									# 员工Id
  	languageId int(11) NOT NULL,									# 语言Id（绑定设置 - 资质 - 语言）
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (employeeLanguageId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_certification (
	employeeCertificationId int(11) NOT NULL AUTO_INCREMENT,		# 雇员证书 - 奖项Id
	employeeId int(11) NOT NULL,									# 员工Id
  	certificationId int(11) NOT NULL,								# 证书 - 奖项Id（绑定设置 - 资质 - 证书 - 奖项）
  	awardFrom varchar(200) DEFAULT NULL,							# 颁发机构
  	awardDate datetime DEFAULT NULL,								# 颁发日期
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (employeeCertificationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_membership (
	employeeMembershipId int(11) NOT NULL AUTO_INCREMENT,			# 雇员社会活动Id
	employeeId int(11) NOT NULL,									# 员工Id
  	membershipId int(11) NOT NULL,									# 社会活动Id（绑定设置 - 资质 - 社会活动）
  	activeFrom datetime DEFAULT NULL,								# 开始时间
  	activeTo datetime DEFAULT NULL,									# 结束时间（不填表示当前进行中）
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (employeeMembershipId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE `hro_biz_employee_log` (
  `employeeLogId` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) DEFAULT NULL,								# 雇员ID
  `type` tinyint(4) DEFAULT NULL,									# 数据来源（1：手动插入；2：系统生成）
  `content` varchar(1000) DEFAULT NULL,								# 日志内容
  `description` varchar(1000) DEFAULT NULL,							
  `deleted` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `remark1` varchar(5000) DEFAULT NULL,
  `remark2` varchar(1000) DEFAULT NULL,
  `remark3` varchar(1000) DEFAULT NULL,
  `remark4` varchar(1000) DEFAULT NULL,
  `remark5` varchar(1000) DEFAULT NULL,
  `createBy` varchar(25) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyBy` varchar(25) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`employeeLogId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract (
	contractId int(11) NOT NULL AUTO_INCREMENT,						# 合同Id，主键（合同在雇员界面分为2个Tab，一个为劳动合同，另一个为服务）
	accountId int(11) NOT NULL,										# 账户Id 
	employeeId int(11) NOT NULL,									# 雇员Id
	clientId int(11) DEFAULT NULL,									# 客户Id（如果/*是外包业务并且**/是劳动合同，可以不选择客户）
	corpId int(11) DEFAULT NULL,									# 客户Id（如果/*是外包业务并且**/是劳动合同，可以不选择客户）
	orderId int(11) DEFAULT NULL,									# 订单Id（如果/*是外包业务并且**/是劳动合同，可以不选择订单）
	department varchar(200) DEFAULT NULL,							# 工作部门
  	positionId int(11) NOT NULL,									# 职位Id（绑定设置 - 工作 - 外部职位）
  	additionalPosition varchar(50) DEFAULT NULL,					# 备用职位
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	templateId int(11) NOT NULL,									# 合同模板Id
	lineManagerId int(11) DEFAULT NULL,								# 直线经理Id（绑定客户联系人）
	approveType tinyint(4) DEFAULT NULL,							# 审核方式（1:职位设定；2:按直线经理）
	contractNo varchar(50) DEFAULT NULL,							# 合同编号（归档使用）
	masterContractId int(11) DEFAULT NULL,							# 主合同号（归档编号），在一个雇员劳动合同下可创建多个时间不重叠的服务协议
  	nameZH varchar(200) DEFAULT NULL,								# 合同名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 合同名称（英文）
  	content text DEFAULT NULL,										# 合同内容，参数${column}使用系统常量填充，参数${blank_input}，${blank_textarea}创建合同时填写
  	startDate datetime DEFAULT NULL,								# 合同开始时间
  	endDate datetime DEFAULT NULL,									# 合同结束时间
  	period smallint(8) DEFAULT NULL,								# 合同时限（月数）
  	applyOTFirst tinyint(4) DEFAULT NULL,							# 加班需要申请（1:是，2:否）（仅限服务协议使用）
  	otLimitByDay smallint(8) DEFAULT NULL,							# 每天加班上限（小时）（JS验证小于10）（仅限服务协议使用）
  	otLimitByMonth smallint(8) DEFAULT NULL,						# 每月加班上限（小时）（JS验证小于200）（仅限服务协议使用）
  	workdayOTItemId int(11) DEFAULT NULL,							# 工作日加班科目 - 下拉科目类型为加班（仅限服务协议使用）
  	weekendOTItemId int(11) DEFAULT NULL,							# 休息日加班科目 - 下拉科目类型为加班（仅限服务协议使用）
  	holidayOTItemId int(11) DEFAULT NULL,							# 节假日加班科目 - 下拉科目类型为加班（仅限服务协议使用）
  	attendanceCheckType tinyint(4) DEFAULT NULL,					# 考勤方式（即时审批，汇总审批，双重审批）（仅限服务协议使用）
  	resignDate datetime DEFAULT NULL,								# 离职日期
  	employStatus tinyint(4) DEFAULT NULL,							# 雇佣状态（1:在职，2:正常结束，3:被动离职，4:主动离职）（仅限服务协议使用）
  	calendarId int(11) DEFAULT NULL,								# 日历Id
  	shiftId int(11) DEFAULT NULL,									# 排班Id
  	sickLeaveSalaryId int(11) DEFAULT NULL,							# 病假工资Id
  	flag tinyint(4) DEFAULT NULL,									# 标明是劳动合同还是服务协议
  	medicalNumber varchar(50) DEFAULT NULL,							# 医保卡帐号
  	sbNumber varchar(50) DEFAULT NULL,								# 社保卡帐号
	fundNumber varchar(50) DEFAULT NULL,							# 公积金帐号
	hsNumber  varchar(50)zDEFAULT NULL,								# 房贴号
  	attachment varchar(5000) DEFAULT NULL,							# 附件
  	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT '0',									# 所属人（Position Id）
  	settlementBranch varchar(25) DEFAULT NULL,						# 成本部门/营收部门
  	locked tinyint(4) DEFAULT NULL,									# 锁定（是/否）
  	description varchar(1000) DEFAULT NULL,							# 描述
  	salaryVendorId int(11) DEFAULT NULL,							# 薪酬供应商
  	incomeTaxBaseId int(11) DEFAULT NULL,							# 个税起征
  	incomeTaxRangeHeaderId int(11) DEFAULT NULL,					# 个税税率
  	probationMonth tinyint(4) DEFAULT NULL,							# 试用期月数（0-6个月）
  	probationEndDate datetime DEFAULT NULL,							# 试用期结束时间
  	isRetained tinyint(4) DEFAULT NULL,								# 是否被延期（1:是，2:否），在被延期的合同上打上标识
  	isContinued tinyint(4) DEFAULT NULL,							# 是否被续签（1:是，2:否），在被续签的合同上打上标识
  	continueNeeded tinyint(4) DEFAULT NULL,							# 是否需要续签（1:是，2:否），提前在需要被续签的合同上打上标识
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 合同状态（1:新建，2:待审核，3:批准，4:退回，5:已盖章，6:归档，7:结束）
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (contractId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_property (
	propertyId int(11) NOT NULL AUTO_INCREMENT,						# 参数Id，主键
	contractId int(11) NOT NULL,									# 商务合同Id
	propertyName varchar(200) NOT NULL,								# 参数名称
	propertyValue varchar(5000) DEFAULT NULL,						# 参数值	
	valueType tinyint(4) DEFAULT NULL,								# 字段值的类型（使用自定义字段中的值类型）
  	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (propertyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_sb (
	employeeSBId int(11) NOT NULL AUTO_INCREMENT,					# 雇员社保方案Id，主键
	contractId int(11) NOT NULL,									# 劳动合同Id
	sbSolutionId int(11) NOT NULL,									# 社保方案Id（绑定设置 - 业务 - 社保方案）
	vendorId int(11) DEFAULT NULL,									# 供应商Id，如果只有一个供应商，默认选中
	vendorServiceId int(11) DEFAULT NULL,							# 供应商服务Id，如果只有一个供应商服务，默认选中
	personalSBBurden tinyint(4) DEFAULT NULL,						# 社保个人部分公司承担（是，否）
	startDate datetime DEFAULT NULL,								# 加保日期
	endDate datetime DEFAULT NULL,									# 退保日期
	needMedicalCard tinyint(4) DEFAULT NULL,						# 需要办理医保卡						
	needSBCard tinyint(4) DEFAULT NULL,								# 需要办理社保卡
	medicalNumber varchar(50) DEFAULT NULL,							# 医保卡帐号
	sbNumber varchar(50) DEFAULT NULL,								# 社保卡帐号
	fundNumber varchar(50) DEFAULT NULL,							# 公积金帐号
	sbBase double DEFAULT NULL,										# 社保基数
	flag tinyint(4) DEFAULT NULL,									# 社保实际缴纳标识（1:正常，2:未缴纳）
  	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（0:无社保，1:加保提交，2:待申报加保，3:正常缴纳，4:退保提交，5:待申报退保，6:已退保，7:补缴）
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (employeeSBId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_sb_detail (
	employeeSBDetailId int(11) NOT NULL AUTO_INCREMENT,				# 雇员社保方案明细Id，主键（用于设置雇员基数）
	employeeSBId int(11) NOT NULL,									# 雇员社保方案Id
	solutionDetailId int(11) NOT NULL,								# 社保方案明细Id（绑定设置 - 业务 - 社保方案）
	baseCompany double DEFAULT NULL,								# 公司基数
	basePersonal double DEFAULT NULL,								# 个人基数
  	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (employeeSBDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_cb (
	employeeCBId int(11) NOT NULL AUTO_INCREMENT,					# 雇员商保方案Id，主键
	contractId int(11) NOT NULL,									# 劳动合同Id
	solutionId int(11) NOT NULL,									# 商保方案Id（绑定设置 - 业务 - 商保方案）
	startDate datetime DEFAULT NULL,								# 加保日期
	endDate datetime DEFAULT NULL,									# 退保日期
	freeShortOfMonth tinyint(4) DEFAULT NULL,						# 不全月免费（是/否） - 针对入职、离职不满月情况，与chargeFullMonth不能同时选“是”
	chargeFullMonth	tinyint(4) DEFAULT NULL,						# 按全月计费（是/否） - 针对入职、离职不满月情况，与freeShortOfMonth不能同时选“是”
  	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（0:无商保，1:申购提交，2:待申购，3:正常购买，4:退购提交，5:待退购，6:已退购）
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (employeeCBId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_salary (
	employeeSalaryId int(11) NOT NULL AUTO_INCREMENT,				# 雇员薪酬方案Id，主键（工资性科目，工资、奖金、津贴等）
	contractId int(11) NOT NULL,									# 劳动合同Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目）
	salaryType tinyint(4) NOT NULL,									# 计薪方式（按月，按小时）
	divideType tinyint(4) NOT NULL,									# 折算方式（不折算，按月工作天数，按月平均计薪天数），计薪方式为“按月”的情况显示
	divideTypeIncomplete tinyint(4) DEFAULT NULL,					# 折算方式 - 不满月（不折算，按月工作天数，按月平均计薪天数），计薪方式为“按月”的情况显示
	excludeDivideItemIds varchar(1000) DEFAULT NULL,				# 不折算休假科目
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（当使用基数来源时出现）
	fix double DEFAULT NULL,										# 固定金（当使用基数来源时出现）
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不用）
	discount double DEFAULT NULL,									# 折扣
	multiple double DEFAULT NULL,									# 倍率
	cycle tinyint(4) DEFAULT NULL,									# 发放周期（月数，一次性）
	startDate datetime DEFAULT NULL,								# 发放起始时间
	endDate datetime DEFAULT NULL,									# 发放截至时间，一次性情况不显示
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（默认 - /21.75×出勤天数/工作日天数 ：> 21.75反算，自定义）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
	showToTS tinyint(4) DEFAULT NULL,								# 显示至考勤表（是/否）
	probationUsing tinyint(4) DEFAULT NULL,							# 试用期是否可使用 
	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:批准，4:退回）
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (employeeSalaryId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_performance (
	employeePerformanceId int(11) NOT NULL AUTO_INCREMENT,			# 员工绩效设置Id，主键
	contractId int(11) NOT NULL,									# 劳动合同Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目，工资、补助、奖金）
	performanceSolutionId int(11) NOT NULL,							# 绩效方案Id（绑定设置 - 业务 - 商保方案）
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（暂时不用）
	fix double DEFAULT NULL,										# 固定金（暂时不用）
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不用）
	discount double DEFAULT NULL,									# 折扣（暂时不用）
	multiple double DEFAULT NULL,									# 倍率（暂时不用）
	cycle tinyint(4) DEFAULT NULL,									# 发生周期（月数，一次性）
	startDate datetime DEFAULT NULL,								# 生效时间
	endDate datetime DEFAULT NULL,									# 结束时间
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（暂时不用）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (employeePerformanceId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_ot (
	employeeOTId int(11) NOT NULL AUTO_INCREMENT,					# 雇员加班设置Id，主键
	contractId int(11) NOT NULL,									# 劳动合同Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目）
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（当使用基数来源时出现）
	fix double DEFAULT NULL,										# 固定金（当使用基数来源时出现）
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不计算）
	discount double DEFAULT NULL,									# 折扣
	multiple double DEFAULT NULL,									# 倍率
	cycle tinyint(4) DEFAULT NULL,									# 发放周期（月数，一次性）
	startDate datetime DEFAULT NULL,								# 生效日期
	endDate datetime DEFAULT NULL,									# 结束日期
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（默认 - 加班小时数×倍率×折扣（基数 or 基数来源），自定义）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (employeeOTId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_leave (
	employeeLeaveId int(11) NOT NULL AUTO_INCREMENT,				# 雇员休假设置Id，主键
	contractId int(11) NOT NULL,									# 劳动合同Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目）
	legalQuantity smallint(4) DEFAULT NULL,							# 法定数量（小时）
	benefitQuantity smallint(4) DEFAULT NULL,						# 福利数量（小时）
	cycle tinyint(4) DEFAULT NULL,									# 使用周期（合同期，日历年，合同年）
	probationUsing tinyint(4) DEFAULT NULL,							# 试用期是否可使用
	delayUsing tinyint(4) DEFAULT NULL,								# 可以延迟使用
	legalQuantityDelayMonth tinyint(4) DEFAULT NULL,				# 法定假未使用完可延期月数（0表示不可延期）		
	benefitQuantityDelayMonth tinyint(4) DEFAULT NULL,				# 福利假未使用完可延期月数（0表示不可延期）
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (employeeLeaveId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_other (
	employeeOtherId int(11) NOT NULL AUTO_INCREMENT,				# 雇员其他设置Id，主键
	contractId int(11) NOT NULL,									# 劳动合同Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目，除工资、社保、商保、休假、加班、服务费外的所有科目）
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（当使用基数来源时出现）
	fix double DEFAULT NULL,										# 固定金（当使用基数来源时出现）
	quantity smallint(4) DEFAULT NULL,								# 数量
	discount double DEFAULT NULL,									# 折扣
	multiple double DEFAULT NULL,									# 倍率
	cycle tinyint(4) DEFAULT NULL,									# 发生周期（月数，一次性）
	startDate datetime DEFAULT NULL,								# 生效时间
	endDate datetime DEFAULT NULL,									# 结束时间
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（默认 - 加班小时数×倍率×折扣（基数 or 基数来源），自定义）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
	showToTS tinyint(4) DEFAULT NULL,								# 显示至考勤表（是/否）
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (employeeOtherId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_temp (
  	contractId int(11) NOT NULL AUTO_INCREMENT,
  	batchId int(11) NOT NULL,
  	accountId int(11) NOT NULL,
  	employeeId int(11) NOT NULL,
  	clientId int(11) DEFAULT NULL,
  	corpId int(11) DEFAULT NULL,
  	orderId int(11) DEFAULT NULL,
  	department varchar(200) DEFAULT NULL,
  	positionId int(11) DEFAULT NULL,
  	entityId int(11) NOT NULL,
  	businessTypeId int(11) NOT NULL,
  	templateId int(11) NOT NULL,
  	lineManagerId int(11) DEFAULT NULL,
  	approveType tinyint(4) DEFAULT NULL,
  	contractNo varchar(50) DEFAULT NULL,
  	masterContractId int(11) DEFAULT NULL,
  	nameZH varchar(200) DEFAULT NULL,
  	nameEN varchar(200) DEFAULT NULL,
  	content text,
  	startDate datetime DEFAULT NULL,
  	endDate datetime DEFAULT NULL,
  	period smallint(8) DEFAULT NULL,
  	applyOTFirst tinyint(4) DEFAULT NULL,
  	otLimitByDay smallint(8) DEFAULT NULL,
  	otLimitByMonth smallint(8) DEFAULT NULL,
  	workdayOTItemId int(11) DEFAULT NULL,
  	weekendOTItemId int(11) DEFAULT NULL,
  	holidayOTItemId int(11) DEFAULT NULL,
  	attendanceCheckType tinyint(4) DEFAULT NULL,
  	calendarId int(11) DEFAULT NULL,
  	shiftId int(11) DEFAULT NULL,
  	sickLeaveSalaryId int(11) DEFAULT NULL,
  	flag tinyint(4) DEFAULT NULL,
  	attachment varchar(5000) DEFAULT NULL,
  	branch varchar(25) DEFAULT NULL,
  	owner varchar(25) DEFAULT NULL,
  	resignDate datetime DEFAULT NULL,
  	employStatus tinyint(4) DEFAULT NULL,
  	locked tinyint(4) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
  	isRetained tinyint(4) DEFAULT NULL,									
  	isContinued tinyint(4) DEFAULT NULL,	
  	continueNeeded tinyint(4) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	tempStatus tinyint(4) DEFAULT NULL,
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (contractId)
) ENGINE=InnoDB AUTO_INCREMENT=200000482 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_salary_temp (
  	employeeSalaryId int(11) NOT NULL AUTO_INCREMENT,
  	contractId int(11) NOT NULL,
  	itemId int(11) NOT NULL,
  	salaryType tinyint(4) NOT NULL,
  	divideType tinyint(4) DEFAULT NULL,
  	divideTypeIncomplete tinyint(4) DEFAULT NULL,						
  	excludeDivideItemIds varchar(1000) DEFAULT NULL,
  	base double DEFAULT NULL,
  	baseFrom int(11) DEFAULT NULL,
  	percentage double DEFAULT NULL,
  	fix double DEFAULT NULL,
  	quantity smallint(4) DEFAULT NULL,
  	discount double DEFAULT NULL,
  	multiple double DEFAULT NULL,
  	cycle tinyint(4) DEFAULT NULL,
  	startDate datetime DEFAULT NULL,
  	endDate datetime DEFAULT NULL,
  	resultCap double DEFAULT NULL,
  	resultFloor double DEFAULT NULL,
  	formularType tinyint(4) DEFAULT NULL,
  	formular varchar(1000) DEFAULT NULL,
  	showToTS tinyint(4) DEFAULT NULL,
  	probationUsing tinyint(4) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (employeeSalaryId)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_sb_temp (
  	employeeSBId int(11) NOT NULL AUTO_INCREMENT,
  	contractId int(11) NOT NULL,
  	sbSolutionId int(11) NOT NULL,
  	description varchar(1000) DEFAULT NULL,
  	vendorId int(11) DEFAULT NULL,
  	vendorServiceId int(11) DEFAULT NULL,
  	startDate datetime DEFAULT NULL,
  	endDate datetime DEFAULT NULL,
  	needMedicalCard tinyint(4) DEFAULT NULL,
  	needSBCard tinyint(4) DEFAULT NULL,
  	medicalNumber varchar(50) DEFAULT NULL,
  	sbNumber varchar(50) DEFAULT NULL,
  	fundNumber varchar(50) DEFAULT NULL,
  	sbBase double DEFAULT NULL,
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
  	PRIMARY KEY (employeeSBId)
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_cb_temp (
  	employeeCBId int(11) NOT NULL AUTO_INCREMENT,
  	contractId int(11) NOT NULL,
  	solutionId int(11) NOT NULL,
  	startDate datetime DEFAULT NULL,
  	endDate datetime DEFAULT NULL,
  	freeShortOfMonth tinyint(4) DEFAULT NULL,
  	chargeFullMonth tinyint(4) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (employeeCBId)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_leave_temp (
  	employeeLeaveId int(11) NOT NULL AUTO_INCREMENT,
  	contractId int(11) NOT NULL,
  	itemId int(11) NOT NULL,
  	legalQuantity smallint(4) DEFAULT NULL,
  	benefitQuantity smallint(4) DEFAULT NULL,
  	cycle tinyint(4) DEFAULT NULL,
  	probationUsing tinyint(4) DEFAULT NULL,
  	delayUsing tinyint(4) DEFAULT NULL,
  	legalQuantityDelayMonth tinyint(4) DEFAULT NULL,
  	benefitQuantityDelayMonth tinyint(4) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (employeeLeaveId)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_ot_temp (
  	employeeOTId int(11) NOT NULL AUTO_INCREMENT,
  	contractId int(11) NOT NULL,
  	itemId int(11) NOT NULL,
  	base double DEFAULT NULL,
  	baseFrom int(11) DEFAULT NULL,
  	percentage double DEFAULT NULL,
  	fix double DEFAULT NULL,
  	quantity smallint(4) DEFAULT NULL,
  	discount double DEFAULT NULL,
  	multiple double DEFAULT NULL,
  	cycle tinyint(4) DEFAULT NULL,
  	startDate datetime DEFAULT NULL,
  	endDate datetime DEFAULT NULL,
  	resultCap double DEFAULT NULL,
  	resultFloor double DEFAULT NULL,
  	formularType tinyint(4) DEFAULT NULL,
  	formular varchar(1000) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (employeeOTId)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_other_temp (
  	employeeOtherId int(11) NOT NULL AUTO_INCREMENT,
  	contractId int(11) NOT NULL,
  	itemId int(11) NOT NULL,
  	base double DEFAULT NULL,
  	baseFrom int(11) DEFAULT NULL,
  	percentage double DEFAULT NULL,
  	fix double DEFAULT NULL,
  	quantity smallint(4) DEFAULT NULL,
  	discount double DEFAULT NULL,
  	multiple double DEFAULT NULL,
  	cycle tinyint(4) DEFAULT NULL,
  	startDate datetime DEFAULT NULL,
  	endDate datetime DEFAULT NULL,
  	resultCap double DEFAULT NULL,
  	resultFloor double DEFAULT NULL,
  	formularType tinyint(4) DEFAULT NULL,
  	formular varchar(1000) DEFAULT NULL,
  	showToTS tinyint(4) DEFAULT NULL,
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (employeeOtherId)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;

CREATE TABLE `hro_biz_employee_positionchange` (
  `positionChangeId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `corpId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `staffId` int(11) NOT NULL,
  `oldBranchId` int(11) DEFAULT NULL,
  `oldStaffPositionRelationId` int(11) DEFAULT NULL,
  `oldPositionId` int(11) DEFAULT NULL,
  `oldStartDate` date DEFAULT NULL,
  `oldEndDate` date DEFAULT NULL,
  `newBranchId` int(11) DEFAULT NULL,
  `newPositionId` int(11) DEFAULT NULL,
  `newStartDate` date DEFAULT NULL,
  `newEndDate` date DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `isImmediatelyEffective` tinyint(4) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `positionStatus` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `submitFlag` tinyint(4) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `remark1` varchar(1000) DEFAULT NULL,
  `remark2` varchar(1000) DEFAULT NULL,
  `remark3` varchar(1000) DEFAULT NULL,
  `remark4` varchar(1000) DEFAULT NULL,
  `remark5` varchar(1000) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyBy` varchar(255) DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  PRIMARY KEY (`positionChangeId`)
) ENGINE=InnoDB AUTO_INCREMENT=467 DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client (
  	clientId int(11) NOT NULL AUTO_INCREMENT,						# 客户，主键
  	groupId int(11) DEFAULT NULL,									# 集团ID
  	accountId int(11) NOT NULL,										# 账户ID
  	corpId int(11) DEFAULT NULL,									# 企业ID，用于Inhouse使用
  	number varchar(50) DEFAULT NULL,								# 客户编号
  	nameZH varchar(200) DEFAULT NULL,								# 客户中文名称
  	nameEN varchar(200) DEFAULT NULL,								# 客户英文名称
  	titleZH varchar(200) DEFAULT NULL,								# 客户简称中文名称
  	titleEN varchar(200) DEFAULT NULL,								# 客户简称英文名称
  	cityId int(11) DEFAULT NULL,									# 城市
  	address varchar(200) DEFAULT NULL,								# 联系地址
  	postcode varchar(50) DEFAULT NULL,								# 邮编
  	mainContact varchar(50) DEFAULT NULL,							# 主要联系人
  	phone varchar(50) DEFAULT NULL,									# 电话号码
  	mobile varchar(50) DEFAULT NULL,								# 移动电话
  	fax varchar(50) DEFAULT NULL,									# 传真号码
  	email varchar(200) DEFAULT NULL,								# 邮箱地址
  	im1Type tinyint(4) DEFAULT NULL,								# IM1类型
  	im1 varchar(200) DEFAULT NULL,									# IM1号码	
  	im2Type tinyint(4) DEFAULT NULL,								# IM2类型
  	im2 varchar(200) DEFAULT NULL,									# IM2号码
  	website varchar(500) DEFAULT NULL,								# 网站地址
  	invoiceDate varchar(50) DEFAULT NULL,							# 发票日期
  	paymentTerms varchar(50) DEFAULT NULL,							# 支付条款
  	industryId int(11) DEFAULT NULL,								# 所属行业
  	type tinyint(4) DEFAULT NULL,									# 企业性质
  	size tinyint(4) DEFAULT NULL,						    		# 公司规模
  	description varchar(5000) DEFAULT NULL,                			# 公司简介
  	recommendPerson varchar(100) DEFAULT NULL,            	  		# 推荐人
  	recommendBranch varchar(100) DEFAULT NULL,            			# 推荐人部门
  	recommendPosition varchar(100) DEFAULT NULL,					# 推荐人职位
  	legalEntity int(11) DEFAULT NULL,								# 法务实体
  	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	orderBindContract tinyint(4) DEFAULT NULL,						# 订单绑定合同（是/否），（如果选择“是”，创建订单时一定需要选择商务合同）
  	logoFile varchar(1000) DEFAULT NULL,							# 公司商标存放路径	
  	logoFileSize INTEGER DEFAULT NULL,								# 公司商标文件大小
  	imageFile varchar(1000) DEFAULT NULL,							# 公司形象照片
  	sbGenerateCondition tinyint(4) DEFAULT NULL,					# 社保申报条件（1:订单批准，2:订单生效），订单
  	cbGenerateCondition tinyint(4) DEFAULT NULL,					# 商保申购条件（1:订单批准，2:订单生效），订单
  	settlementCondition tinyint(4) DEFAULT NULL,					# 结算处理条件（1:订单批准，2:订单生效），订单
  	sbGenerateConditionSC tinyint(4) DEFAULT NULL,					# 社保申报条件（1:服务协议批准，2:服务协议盖章，3:服务协议归档），服务协议
  	cbGenerateConditionSC tinyint(4) DEFAULT NULL,					# 商保申购条件（1:服务协议批准，2:服务协议盖章，3:服务协议归档），服务协议
  	settlementConditionSC tinyint(4) DEFAULT NULL,					# 结算处理条件（1:服务协议批准，2:服务协议盖章，3:服务协议归档），服务协议
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:批准，4:退回，5:锁定）
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (clientId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_contact (
  	contactId int(11) NOT NULL AUTO_INCREMENT,						# 联系人Id，主键
  	clientId int(11) NOT NULL,										# 客户Id
  	accountId int(11) NOT NULL,										# 账户Id
  	corpId int(11) DEFAULT NULL,									# 企业ID，用于Inhouse使用
  	salutation tinyint(4) DEFAULT NULL,								# 称呼
  	nameZH varchar(200) DEFAULT NULL,								# 中文姓名
  	nameEN varchar(200) DEFAULT NULL,								# 英文姓名
  	title varchar(200) DEFAULT NULL,								# 头衔
  	department varchar(100) DEFAULT NULL,							# 所属部门
  	bizPhone varchar(50) DEFAULT NULL,								# 公司电话
  	personalPhone varchar(50) DEFAULT NULL,							# 私人电话
  	bizMobile varchar(50) DEFAULT NULL,								# 公司手机号码
  	personalMobile varchar(50) DEFAULT NULL,						# 个人手机号码
  	otherPhone varchar(50) DEFAULT NULL,							# 其他号码
  	fax varchar(50) DEFAULT NULL,									# 传真号码
  	bizEmail varchar(200) DEFAULT NULL,								# 公司邮箱
  	personalEmail varchar(200) DEFAULT NULL,						# 个人邮箱
  	im1Type tinyint(4) DEFAULT NULL,								# IM1类型
  	im1 varchar(200) DEFAULT NULL,									# IM1号码	
  	im2Type tinyint(4) DEFAULT NULL,								# IM2类型
  	im2 varchar(200) DEFAULT NULL,									# IM2号码
  	comment varchar(1000) DEFAULT NULL,								# 备注
  	canAdvBizEmail tinyint(4) DEFAULT NULL,							# 是否可以发送推送消息到企业邮箱
  	canAdvPersonalEmail tinyint(4) DEFAULT NULL,					# 是否可以发送推送消息到个人邮箱
  	canAdvBizSMS tinyint(4) DEFAULT NULL,							# 是否可以发送推送消息到企业短消息
  	canAdvPersonalSMS tinyint(4) DEFAULT NULL,						# 是否可以发送推送消息到个人短消息
  	cityId int(11) DEFAULT NULL,									# 城市
  	address varchar(200) DEFAULT NULL,								# 联系地址
  	postcode varchar(50) DEFAULT NULL,								# 邮编
  	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
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
  	PRIMARY KEY (contactId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;  	

CREATE TABLE hro_biz_client_user (
  	clientUserId int(11) NOT NULL AUTO_INCREMENT,					# 用户ID
  	accountId int(11) DEFAULT NULL,									# 账户ID
  	corpId int(11) DEFAULT NULL,									# 企业ID，用于Inhouse使用
  	clientId int(11) NOT NULL,										# 客户ID
  	clientContactId int(11) NOT NULL,								# 客户联系人ID
  	username varchar(50) DEFAULT NULL,								# 用户名
  	password varchar(50) DEFAULT NULL,								# 密码
  	bindIP varchar(50) NULL,										# 绑定IP地址，如果绑定只能指定IP地址访问
  	lastLogin datetime DEFAULT NULL,								# 最后登录时间
  	lastLoginIP varchar(50) NULL,									# 最后登录IP地址
  	superUserId int(11) DEFAULT NULL,								# 主账号ID （同一个客户可能被分配了多个账号，此时可以用主账号将分账号串联起来）
  	validatedSuperUser tinyint(4) DEFAULT NULL,						# 客户界面 (0:设置,1:不设置)
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

CREATE TABLE hro_biz_client_group (
	groupId int(11) NOT NULL AUTO_INCREMENT,						# 集团Id，主键
	accountId int(11) NOT NULL,										# 账户Id
	corpId int(11) DEFAULT NULL,									# 企业ID，用于Inhouse使用
	number varchar(50) DEFAULT NULL,								# 集团编号
  	nameZH varchar(200) DEFAULT NULL,								# 集团中文名
  	nameEN varchar(200) DEFAULT NULL,								# 集团英文名
  	description varchar(1000) DEFAULT NULL,							# 描述信息
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (groupId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_invoice (
	invoiceId int(11) NOT NULL AUTO_INCREMENT,						# 发票地址Id，主键
	clientId int(11) NOT NULL,										# 客户Id
	accountId int(11) NOT NULL,										# 账户Id 
	corpId int(11) DEFAULT NULL,									# 企业ID，用于Inhouse使用
	title varchar(200) DEFAULT NULL,								# 发票台头
	salutation tinyint(4) DEFAULT NULL,								# 称呼
  	nameZH varchar(200) DEFAULT NULL,								# 中文名（收件人）
  	nameEN varchar(200) DEFAULT NULL,								# 英文名（收件人）
  	position varchar(200) DEFAULT NULL,								# 职位
  	department varchar(100) DEFAULT NULL,							# 部门
  	cityId int(11) DEFAULT NULL,									# 城市
  	address varchar(200) DEFAULT NULL,								# 地址
  	postcode varchar(50) DEFAULT NULL,								# 邮编
  	email varchar(200) DEFAULT NULL,								# 邮箱
  	phone varchar(50) DEFAULT NULL,									# 电话
  	mobile varchar(50) DEFAULT NULL,								# 手机
  	invoiceDate varchar(50) DEFAULT NULL,							# 开票日期
  	paymentTerms varchar(50) DEFAULT NULL,							# 付款周期
  	legalEntity int(11) DEFAULT NULL,								# 法务实体
  	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (invoiceId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_contract (
	contractId int(11) NOT NULL AUTO_INCREMENT,						# 商务合同Id，主键
	clientId int(11) NOT NULL,										# 客户Id
	accountId int(11) NOT NULL,										# 账户Id 
	corpId int(11) DEFAULT NULL,									# 企业ID，用于Inhouse使用
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	templateId int(11) NOT NULL,									# 合同模板Id
	contractNo varchar(50) DEFAULT NULL,							# 合同编号（归档使用）
	versionType tinyint(4) DEFAULT NULL								# 版本类型（系统版，客户版）
	masterContractId int(11) DEFAULT NULL,							# 主合同号（归档编号）
	invoiceAddressId int(11) DEFAULT NULL,							# 发票地址（关联Client Invoice Address） 
  	nameZH varchar(200) DEFAULT NULL,								# 合同名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 合同名称（英文）
  	content text DEFAULT NULL,										# 合同内容，参数${column}使用系统常量填充，参数${blank_input}，${blank_textarea}创建合同时填写
  	startDate datetime DEFAULT NULL,								# 合同开始时间
  	endDate datetime DEFAULT NULL,									# 合同结束时间
  	period smallint(8) DEFAULT NULL,								# 合同时限（月数）
  	attachment varchar(5000) DEFAULT NULL,							# 附件
  	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 合同状态（新建，待审核，批准，退回，已盖章，归档）
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (contractId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_contract_property (
	propertyId int(11) NOT NULL AUTO_INCREMENT,						# 参数Id，主键
	contractId int(11) NOT NULL,									# 商务合同Id
	constantId int(11) NOT NULL,									# 参数Id
	propertyName varchar(200) NOT NULL,								# 参数名称
	propertyValue varchar(5000) DEFAULT NULL,						# 参数值	
	valueType tinyint(4) DEFAULT NULL,								# 字段值的类型（使用自定义字段中的值类型）
  	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (propertyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_header (
	orderHeaderId int(11) NOT NULL AUTO_INCREMENT,					# 订单主表Id，主键
	clientId int(11) NOT NULL,										# 客户Id
	accountId int(11) NOT NULL,										# 账户Id 
	corpId int(11) DEFAULT NULL,									# 企业ID，用于Inhouse使用
	contractId int(11) DEFAULT NULL,								# 商务合同Id，本地化中可以设置订单结算是否必需有已归档的商务合同
	entityId int(11) NOT NULL,										# 法务实体Id，默认选择商务合同带出，也可以更换
	businessTypeId int(11) NOT NULL,								# 业务类型Id，默认选择商务合同带出，也可以更换
	invoiceAddressId int(11) NOT NULL,								# 发票地址Id，默认选择商务合同带出，也可以更换
	nameZH varchar(200) DEFAULT NULL,								# 订单名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 订单名称（英文）
  	startDate datetime DEFAULT NULL,								# 订单开始时间，默认选择商务合同带出，也可以更换
  	endDate datetime DEFAULT NULL,									# 订单结束时间，默认选择商务合同带出，也可以更换
  	circleStartDay tinyint(4) DEFAULT NULL,							# 计薪周期 - 开始日
  	circleEndDay tinyint(4) DEFAULT NULL,							# 计薪周期 - 结束日（结束日所在月作为所属月）
  	salaryVendorId int(11) DEFAULT NULL,							# 薪酬供应商
  	payrollDay tinyint(4) DEFAULT NULL,								# 发薪日期
  	salaryMonth tinyint(4) DEFAULT NULL,							# 工资月份（1:当前月，2:上一月，3:上二月，4:下一月），适用于结算
  	sbMonth tinyint(4) DEFAULT NULL,								# 社保月份（1:当前月，2:上一月，3:上二月，4:下一月），适用于结算
  	cbMonth tinyint(4) DEFAULT NULL,								# 商保月份（1:当前月，2:上一月，3:上二月，4:下一月），适用于结算
  	fundMonth tinyint(4) DEFAULT NULL,								# 公积金月份（1:当前月，2:上一月，3:上二月，4:下一月），适用于结算
  	salesType tinyint(4) DEFAULT NULL,								# 销售方式（1:固定金，2:加价，3:打包），“固定金”方式服务费无需从BaseFrom计算，“加价”服务费从BaseFrom计算，“打包”方式服务费是逆算的
  	invoiceType tinyint(4) DEFAULT NULL,							# 开票方式（1:按订单开票，2:按客户开票，3:按商务合同开票，4:按雇员开票）
  	settlementType tinyint(4) DEFAULT NULL,							# 结算方式（1:工资、社保和服务费都分开，2:工资、社保和服务费都合并，3:工资和社保合并，服务费分开，4:工资和服务费合并，社保分开，5:社保和服务费合并，工资分开） 
  	probationMonth tinyint(4) DEFAULT NULL,							# 试用期月数（0-6个月）
  	serviceScope tinyint(4) DEFAULT NULL,							# 服务内容（1:只缴社保，2:只发工资，3:缴社保+发工资，4:代发工资，5:缴社保+代发工资）
  	personalSBBurden tinyint(4) DEFAULT NULL,						# 社保个人部分公司承担（是，否）
  	applyOTFirst tinyint(4) DEFAULT NULL,							# 加班需要申请（1:是，2:否）
  	otLimitByDay smallint(8) DEFAULT NULL,							# 每天加班小时数上限（JS验证小于10）
  	otLimitByMonth smallint(8) DEFAULT NULL,						# 每月加班小时数上限（JS验证小于200）
  	workdayOTItemId int(11) DEFAULT NULL,							# 工作日加班科目 - 下拉科目类型为加班
  	weekendOTItemId int(11) DEFAULT NULL,							# 休息日加班科目 - 下拉科目类型为加班
  	holidayOTItemId int(11) DEFAULT NULL,							# 节假日加班科目 - 下拉科目类型为加班
  	attendanceCheckType tinyint(4) DEFAULT NULL,					# 考勤审核（1:即时审批，2:汇总审批，3:双重审批）
  	attendanceGenerate tinyint(4) DEFAULT NULL,						# 考勤生成（1:月初生成，2:月末生成）
  	approveType tinyint(4) DEFAULT NULL,							# 审核方式（1:职位设定；2:按直线经理）
  	calendarId int(11) NOT NULL,									# 日历Id
  	shiftId int(11) NOT NULL,										# 排班Id
  	sickLeaveSalaryId int(11) NOT NULL,								# 病假工资Id
  	taxId int(11) NOT NULL,											# 税率Id - 下拉框税率
  	attachment varchar(5000) DEFAULT NULL,							# 附件
  	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	locked tinyint(4) DEFAULT NULL,									# 锁定（是/否）
  	noticeExpire varchar(1000) DEFAULT NULL,						# 到期通知(订单合同到期通知 1:三个月提醒 2:一个月提醒 3:15天提醒 4:一个星期每天提醒)
  	noticeProbationExpire varchar(1000) DEFAULT NULL,				# 试用期到期通知(订单合同到期通知 1:三个月提醒 2:一个月提醒 3:15天提醒 4:一个星期每天提醒)
  	noticeRetire varchar(1000) DEFAULT NULL,						# 退休到期通知(订单合同到期通知 1:三个月提醒 2:一个月提醒 3:15天提醒 4:一个星期每天提醒)
  	currency varchar(25) DEFAULT '0'								# 货币类型
  	contractPeriod tinyint(4) DEFAULT NULL							# 合同期限
  	salaryType tinyint(4) NOT NULL,									# 计薪方式（按月，按小时）
	divideType tinyint(4) NOT NULL,									# 折算方式（不折算，按月工作天数，按月平均计薪天数），计薪方式为“按月”的情况显示
	divideTypeIncomplete tinyint(4) DEFAULT NULL,					# 折算方式 - 不满月（不折算，按月工作天数，按月平均计薪天数），计薪方式为“按月”的情况显示
  	incomeTaxBaseId int(11) DEFAULT NULL;							# 个税起征ID
   	incomeTaxRangeHeaderId int(11) DEFAULT NULL;					# 个税税率ID
  	excludeDivideItemIds varchar(1000) DEFAULT NULL,				# 不折算休假科目
	description varchar(1000) DEFAULT NULL,							# 描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 合同状态（1:新建，2:待审核，3:批准，4:退回，5:生效，6:结束，7:终止，8:取消），商务合同已盖章则订单生效，已盖章级以上状态的商务合同创建订单（批准后立即生效）
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (orderHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_header_rule (
	orderHeaderRuleId int(11) NOT NULL AUTO_INCREMENT,				# 订单主表规则Id，主键（规则的原则是：设置后费用下降）
	orderHeaderId int(11) NOT NULL,									# 订单主表Id
	ruleType tinyint(4) NOT NULL,									# 规则类型（1:订单人数 - 大于（含等于），按比例打折，2:订单金额 - 大于（含等于），按比例打折） 
	ruleValue double DEFAULT NULL,									# 规则单位（人数或金额）
	ruleResult double DEFAULT NULL,									# 规则结果（百分比）
  	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderHeaderRuleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_detail (
	orderDetailId int(11) NOT NULL AUTO_INCREMENT,					# 订单从表Id，主键
	orderHeaderId int(11) NOT NULL,									# 订单主表Id
	itemId int(11) NOT NULL,										# 科目Id 
	calculateType tinyint(4) NOT NULL,								# 计算方式（1:按缴社保人数，2:按购商保人数，3:按发工资人数，4:按结算人数）
	packageType tinyint(4) DEFAULT NULL,							# 费用方式（1:按人头，2:按订单）		- 打包情况使用	
	divideType tinyint(4) NOT NULL,									# 折算方式（不折算，按工作天数，按日历天数），费用方式为“按人头”的情况显示
	cycle tinyint(4) DEFAULT NULL,									# 计算周期（月数，一次性），跟雇员服务协议中的一致
	startDate datetime DEFAULT NULL,								# 生效日期
	endDate datetime DEFAULT NULL,									# 结束日期
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）  	- 加价情况使用
	percentage double DEFAULT NULL,									# 比例（当使用基数来源时出现）		- 加价情况使用
	fix double DEFAULT NULL,										# 固定金（当使用基数来源时出现）		- 加价情况使用
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不计算）
	discount double DEFAULT NULL,									# 折扣（暂时不计算）
	multiple double DEFAULT NULL,									# 倍率（暂时不计算）
	resultCap double DEFAULT NULL,									# 计算结果上限						- 加价情况使用	
	resultFloor double DEFAULT NULL,								# 计算结果下限						- 加价情况使用
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（1:按工作日折算 - Base*(出勤天数+带薪休假)/工作日，2:按日历日折算 - Base*(涵盖天数-不带薪休假)/日历日，3:自定义）
	formular varchar(1000) DEFAULT NULL,							# 计算公式，服务费用折算使用公式
	onboardNoCharge tinyint(4) DEFAULT NULL,						# 入职不收服务费
	offDutyNoCharge tinyint(4) DEFAULT NULL,						# 离职不收服务费
	resignNoCharge tinyint(4) DEFAULT NULL,							# 自动离职不收服务费
	probationNoCharge tinyint(4) DEFAULT NULL,						# 试用期不收服务费
	onboardWithout tinyint(4) DEFAULT NULL,							# 入职当天不计费
	offDutyWidthout tinyint(4) DEFAULT NULL,						# 离职当天不计费
  	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_detail_rule (
	orderDetailRuleId int(11) NOT NULL AUTO_INCREMENT,				# 订单从表规则Id，主键（规则的原则是：设置后费用下降）
	orderDetailId int(11) NOT NULL,									# 订单从表Id
	ruleType tinyint(4) NOT NULL,									# 规则类型（1:费用人数 - 大于（含等于），2:入职日期 - 晚于（含等于），3:离职日期 - 早于（含等于），4:在职天数（工作日） - 小于（含等于），5:在职天数（日历日） - 小于（含等于），6:科目金额 - 大于（含等于），按比例打折） 
	chargeType tinyint(4) NOT NULL,									# 计算方式（1:金额，2:百分比）
	ruleValue double DEFAULT NULL,									# 规则单位
	ruleResult double DEFAULT NULL,									# 规则结果
  	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderDetailRuleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_detail_sb_rule (
	sbRuleId int(11) NOT NULL AUTO_INCREMENT,						# 补缴ID
	orderDetailId int(11) NOT NULL,									# 订单从表ID
	sbSolutionId int(11) NOT NULL,									# 社保方案ID
	sbRuleType tinyint(4) DEFAULT NULL,								# 收费规则（1：不收费；2：按人次收费；3：按月份收费）
	amount double DEFAULT NULL,										# 金额
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (sbRuleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_sb (
	orderSbId int(11) NOT NULL AUTO_INCREMENT,						# 订单社保Id，主键
	orderHeaderId int(11) NOT NULL,									# 订单主表Id
	sbSolutionId int(11) NOT NULL,									# 社保方案Id 
	vendorId int(11) DEFAULT NULL,									# 供应商Id，如果只有一个供应商，默认选中
	vendorServiceId int(11) DEFAULT NULL,							# 供应商服务Id，如果只有一个供应商服务，默认选中
	personalSBBurden tinyint(4) DEFAULT NULL,						# 社保个人部分公司承担（是，否）
  	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderSbId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_cb (
	orderCbId int(11) NOT NULL AUTO_INCREMENT,						# 订单商保Id，主键
	orderHeaderId int(11) NOT NULL,									# 订单主表Id
	cbSolutionId int(11) NOT NULL,									# 商保方案Id 
	freeShortOfMonth tinyint(4) DEFAULT NULL,						# 不全月免费（是/否） - 针对入职、离职不满月情况，与chargeFullMonth不能同时选“是”
	chargeFullMonth	tinyint(4) DEFAULT NULL,						# 按全月计费（是/否） - 针对入职、离职不满月情况，与freeShortOfMonth不能同时选“是”
  	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderCbId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_performance (
	orderPerformanceId int(11) NOT NULL AUTO_INCREMENT,				# 订单绩效设置Id，主键
	orderHeaderId int(11) NOT NULL,									# 劳动合同Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目，工资、补助、奖金）
	performanceSolutionId int(11) NOT NULL,							# 绩效方案Id（绑定设置 - 业务 - 商保方案）
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（暂时不用）
	fix double DEFAULT NULL,										# 固定金（暂时不用）
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不用）
	discount double DEFAULT NULL,									# 折扣（暂时不用）
	multiple double DEFAULT NULL,									# 倍率（暂时不用）
	cycle tinyint(4) DEFAULT NULL,									# 发生周期（月数，一次性）
	startDate datetime DEFAULT NULL,								# 生效时间
	endDate datetime DEFAULT NULL,									# 结束时间
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（暂时不用）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderPerformanceId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_ot (
	orderOTId int(11) NOT NULL AUTO_INCREMENT,						# 订单加班设置Id，主键
	orderHeaderId int(11) NOT NULL,									# 订单主表Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目）
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（当使用基数来源时出现）
	fix double DEFAULT NULL,										# 固定金（当使用基数来源时出现）
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不计算）
	discount double DEFAULT NULL,									# 折扣（暂时不计算）
	multiple double DEFAULT NULL,									# 倍率（1, 1.5, 2, 3）
	cycle tinyint(4) DEFAULT NULL,									# 发放周期（月数，一次性）
	startDate datetime DEFAULT NULL,								# 生效日期
	endDate datetime DEFAULT NULL,									# 结束日期
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（默认 - 加班小时数×倍率×折扣（基数 or 基数来源），自定义）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderOTId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_leave (
	orderLeaveId int(11) NOT NULL AUTO_INCREMENT,					# 订单休假设置Id，主键
	orderHeaderId int(11) NOT NULL,									# 订单主表Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目）
	annualLeaveRuleId int(11) DEFAULT NULL,							# 年假规则（仅年假使用）
	legalQuantity smallint(4) DEFAULT NULL,							# 法定数量（小时），只有年假才使用
	benefitQuantity smallint(4) DEFAULT NULL,						# 福利数量（小时）
	cycle tinyint(4) DEFAULT NULL,									# 使用周期（服务期，日历年，服务年 - 服务器内的一周年）
	probationUsing tinyint(4) DEFAULT NULL,							# 试用期是否可使用
	delayUsing tinyint(4) DEFAULT NULL,								# 可以延迟使用
	legalQuantityDelayMonth tinyint(4) DEFAULT NULL,				# 法定假未使用完可延期月数（0表示不可延期）		
	benefitQuantityDelayMonth tinyint(4) DEFAULT NULL,				# 福利假未使用完可延期月数（0表示不可延期）
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderLeaveId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_client_order_other (
	orderOtherId int(11) NOT NULL AUTO_INCREMENT,					# 订单其他设置Id，主键
	orderHeaderId int(11) NOT NULL,									# 订单主表Id
	itemId int(11) NOT NULL,										# 科目Id（绑定设置 - 财务 - 科目，除社保、商保、休假、加班、服务费外的所有科目）
	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（当使用基数来源时出现）
	fix double DEFAULT NULL,										# 固定金（当使用基数来源时出现）
	quantity smallint(4) DEFAULT NULL,								# 数量
	discount double DEFAULT NULL,									# 折扣
	multiple double DEFAULT NULL,									# 倍率
	cycle tinyint(4) DEFAULT NULL,									# 发生周期（月数，一次性）
	startDate datetime DEFAULT NULL,								# 生效日期
	endDate datetime DEFAULT NULL,									# 结束日期
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（默认 - 加班小时数×倍率×折扣（基数 or 基数来源），自定义）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
	showToTS tinyint(4) DEFAULT NULL,								# 显示至考勤表（是/否）
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (orderOtherId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_vendor (										
	vendorId int(11) NOT NULL AUTO_INCREMENT,						# 供应商Id，主键
	accountId int(11) NOT NULL,										# 账户Id	
  	corpId	int(11) default null,									# 区分Inhouse 、hrService
	nameZH varchar(200) DEFAULT NULL,								# 供应商中文名称
  	nameEN varchar(200) DEFAULT NULL,								# 供应商英文名称
  	cityId int(11) DEFAULT NULL,									# 供应商所在城市ID
  	contractStartDate datetime DEFAULT NULL,						# 合同开始日期	
  	contractEndDate datetime DEFAULT NULL,							# 合同结束日期
  	charterNumber varchar(100) DEFAULT NULL,						# 供应商执照号
  	bankId int(11) DEFAULT NULL,									# 供应商银行ID
  	bankName varchar(200) DEFAULT NULL,								# 供应商银行名称
  	bankAccount varchar(100) DEFAULT NULL,							# 供应商银行账号
  	bankAccountName varchar(200) DEFAULT NULL,						# 供应商银行账号名
  	type tinyint(4) DEFAULT NULL,									# 供应商类型 （外部内部）
  	attachment varchar(1000) DEFAULT NULL,							# 供应商附件存放路径
  	description varchar(1000) DEFAULT NULL,							# 描述
  	legalEntity int(11) DEFAULT NULL,								# 法务实体（外键）参照表 Hro_Mgr_Entity
  	branch varchar(25) DEFAULT NULL,								# 部门（存ID）
  	owner varchar(25) DEFAULT NULL,						  			# 所属人（存放Position Id）
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:批准，4:退回，5:锁定）
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (vendorId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_vendor_contact (								
  	vendorContactId int(11) NOT NULL AUTO_INCREMENT,				# 供应商联系人Id，主键
  	vendorId int(11) NOT NULL,										# 供应商Id
  	accountId int(11) NOT NULL,										# 账户Id 
  	corpId int(11) DEFAULT NULL,									# 账户Id 
  	salutation tinyint(4) DEFAULT NULL,								# 称呼，男士/女士
  	nameZH varchar(200) DEFAULT NULL,								# 联系人名（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 联系人名（英文）		
  	title varchar(200) DEFAULT NULL,								# 职位
  	department varchar(100) DEFAULT NULL,							# 部门
  	bizPhone varchar(50) DEFAULT NULL,								# 工作电话号
  	personalPhone varchar(50) DEFAULT NULL,							# 私人电话号
  	bizMobile varchar(50) DEFAULT NULL,								# 工作手机号
  	personalMobile varchar(50) DEFAULT NULL,						# 私人手机号
  	otherPhone varchar(50) DEFAULT NULL,							# 其他联系方式
  	fax varchar(50) DEFAULT NULL,									# 传真
  	bizEmail varchar(200) DEFAULT NULL,								# 工作电子邮箱
  	personalEmail varchar(200) DEFAULT NULL,						# 私人电子邮箱
  	comment varchar(1000) DEFAULT NULL,								# 说明
  	canAdvBizEmail tinyint(4) DEFAULT NULL,							# 工作邮箱推送
  	canAdvPersonalEmail tinyint(4) DEFAULT NULL,					# 私人邮箱推送	
  	canAdvBizSMS tinyint(4) DEFAULT NULL,							# 工作短信推送
  	canAdvPersonalSMS tinyint(4) DEFAULT NULL,						# 私人短信推送
  	cityId int(11) DEFAULT NULL,									# 联系人所在城市ID
  	address varchar(200) DEFAULT NULL,								# 地址
  	postcode varchar(50) DEFAULT NULL,								# 邮编
  	branch varchar(25) DEFAULT NULL,								# 部门（存ID）
  	owner varchar(25) DEFAULT NULL,						  			# 所属人（存放Position Id ）
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
  	PRIMARY KEY (vendorContactId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;  

CREATE TABLE hro_biz_vendor_user (
	venderUserId int(11) NOT NULL AUTO_INCREMENT,					# 用户ID
  	accountId int(11) NOT NULL,										# 账户ID
  	vendorId int(11) NOT NULL,										# 供应商ID
  	vendorContactId int(11) NOT NULL,								# 供应商联系人ID
  	username varchar(50) DEFAULT NULL,								# 用户名
  	password varchar(50) DEFAULT NULL,								# 密码
  	bindIP varchar(50) DEFAULT NULL,								# 绑定IP地址，如果绑定只能指定IP地址访问
  	lastLogin datetime DEFAULT NULL,								# 最后登录时间
  	lastLoginIP varchar(50) DEFAULT NULL,							# 最后登录IP地址
  	superUserId int(11) DEFAULT NULL,								# 供应商界面不设置
  	validatedSuperUser tinyint(4) DEFAULT NULL,						# 供应商界面不设置（Yes，No）
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
  	PRIMARY KEY (venderUserId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;  

CREATE TABLE hro_biz_vendor_service (						
	serviceId int(11) NOT NULL AUTO_INCREMENT,						# 供应商服务Id
	vendorId int(11) NOT NULL,										# 供应商Id
	cityId int(11) NOT NULL,										# 省份 - 城市
	sbHeaderId int(11) NOT NULL,									# 社保方案
	serviceIds varchar(1000) NOT NULL,								# 服务，{0:1:2:3:4}
	serviceFee double NOT NULL,										# 服务费
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
  	PRIMARY KEY (serviceId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_leave_header (						# 提醒：工作排班中需要确定请假是否可以补请历史假
	leaveHeaderId int(11) NOT NULL AUTO_INCREMENT,					# 请假Id
	accountId int(11) NOT NULL,										# 账户Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	employeeId int(11) NOT NULL,									# 雇员Id
	contractId int(11) NOT NULL,									# 服务协议Id
	itemId int(11) NOT NULL,										# 科目Id，仅选择Leave类别的科目	
	retrieveId int(11) DEFAULT NULL,								# 使用销假（当雇员存在销假时，下次请假使用）
	estimateStartDate datetime DEFAULT NULL,						# 预计请假开始时间（例如，2013-08-01 14:30，时间段为每半小时），是否有剩余假期可请后续将动态计算
	estimateEndDate datetime DEFAULT NULL,							# 预计请假结束时间
	actualStartDate datetime DEFAULT NULL,							# 实际请假开始时间
	actualEndDate datetime DEFAULT NULL,							# 实际请假结束时间
	estimateLegalHours double(8,1) DEFAULT NULL,					# 预计法定假小时数（只有年假会使用此项），不允许手工输入
	estimateBenefitHours double(8,1) DEFAULT NULL,					# 预计福利假小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
	actualLegalHours double(8,1) DEFAULT NULL,						# 实际法定假小时数（只有年假会使用此项），不允许手工输入
	actualBenefitHours double(8,1) DEFAULT NULL,					# 实际福利假小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
	attachment varchar(1000) DEFAULT NULL,							# 附件
	retrieveStatus tinyint(4) DEFAULT NULL,							# 销假状态（1:正常，2:提交 - 待销假，3:销假，4:拒绝销假），销假（未Order Go当月处理，已Order Go下月处理）
	unread tinyint(4) DEFAULT NULL,									# 是否未读(仅用于手机模块统计，1 未读，2 已读);
	dataFrom tinyint(4) DEFAULT NULL,								# 数据来源（1：系统录入；2：导入生成）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 请假状态（1:新建，2:提交 - 待批准，3:批准，4:拒绝请假，5:已结算），状态为批准的Leave参与计算，只有销假才使用Actual字段
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (leaveHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_leave_detail (						
	leaveDetailId int(11) NOT NULL AUTO_INCREMENT,					# 请假从表Id
	leaveHeaderId int(11) NOT NULL,									# 请假主表Id
	timesheetId int(11) DEFAULT NULL,								# 表勤表Id
	estimateStartDate datetime DEFAULT NULL,						# 预计请假开始时间（例如，2013-08-01 14:30，时间段为每半小时），是否有剩余假期可请后续将动态计算
	estimateEndDate datetime DEFAULT NULL,							# 预计请假结束时间
	actualStartDate datetime DEFAULT NULL,							# 实际请假开始时间
	actualEndDate datetime DEFAULT NULL,							# 实际请假结束时间
	estimateLegalHours double(8,1) DEFAULT NULL,					# 预计法定假小时数（只有年假会使用此项），不允许手工输入
	estimateBenefitHours double(8,1) DEFAULT NULL,					# 预计福利假小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
	actualLegalHours double(8,1) DEFAULT NULL,						# 实际法定假小时数（只有年假会使用此项），不允许手工输入
	actualBenefitHours double(8,1) DEFAULT NULL,					# 实际福利假小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
	attachment varchar(1000) DEFAULT NULL,							# 附件
	retrieveStatus tinyint(4) DEFAULT NULL,							# 销假状态（1:正常，2:提交 - 待销假，3:销假，4:拒绝销假），销假（未Order Go当月处理，已Order Go下月处理）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 请假状态（1:新建，2:提交 - 待批准，3:批准，4:拒绝请假，5:已结算），状态为批准的Leave参与计算，只有销假才使用Actual字段
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (leaveDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_ot_header (							# 提醒：工作排班或劳动合同中需要设定加班限制（例如，每周或每月加班不能超多少小时），考虑加班是否需要预申请
	otHeaderId int(11) NOT NULL AUTO_INCREMENT,						# 加班Id
	accountId int(11) NOT NULL,										# 账户Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	employeeId int(11) NOT NULL,									# 雇员Id
	contractId int(11) NOT NULL,									# 服务协议Id
	itemId int(11) NOT NULL,										# 科目Id，仅选择OT类别的科目	
	estimateStartDate datetime DEFAULT NULL,						# 预计加班开始时间（例如，2013-08-01 14:30，时间段为每半小时），是否有剩余假期可请后续将动态计算
	estimateEndDate datetime DEFAULT NULL,							# 预计加班结束时间
	actualStartDate datetime DEFAULT NULL,							# 实际加班开始时间
	actualEndDate datetime DEFAULT NULL,							# 实际加班结束时间
	estimateHours double(8,1) DEFAULT NULL,							# 预计加班小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
	actualHours double(8,1) DEFAULT NULL,							# 实际加班小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
	unread tinyint(4) DEFAULT NULL,									# 是否未读(仅用于手机模块统计，1 未读，2 已读);
	dataFrom tinyint(4) DEFAULT NULL,								# 数据来源（1：系统录入；2：导入生成）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 加班状态（1:新建，2:提交 - 待批准，3:批准，4:提交 - 待确认，5:确认，6:拒绝，7：已结算）只有状态为确认的OT参与计算
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (otHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_ot_detail (							
	otDetailId int(11) NOT NULL AUTO_INCREMENT,						# 加班主表Id
	otHeaderId int(11) NOT NULL,									# 加班从表Id
	timesheetId int(11) DEFAULT NULL,								# 表勤表Id
	estimateStartDate datetime DEFAULT NULL,						# 预计加班开始时间（例如，2013-08-01 14:30，时间段为每半小时），是否有剩余假期可请后续将动态计算
	estimateEndDate datetime DEFAULT NULL,							# 预计加班结束时间
	actualStartDate datetime DEFAULT NULL,							# 实际加班开始时间
	actualEndDate datetime DEFAULT NULL,							# 实际加班结束时间
	estimateHours double(8,1) DEFAULT NULL,							# 预计加班小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
	actualHours double(8,1) DEFAULT NULL,							# 实际加班小时数（小时数计算将按照员工所选时间区间及工作排班），不允许手工输入
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 加班状态（1:新建，2:提交 - 待批准，3:批准，4:提交 - 待确认，5:确认，6:拒绝，7：已结算）只有状态为确认的OT参与计算
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (otDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_timesheet_batch (									
	batchId int(11) NOT NULL AUTO_INCREMENT,						# 批次Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) DEFAULT NULL,									# 法务实体Id
	businessTypeId int(11) DEFAULT NULL,							# 业务类型Id
	clientId int(11) DEFAULT NULL,									# 客户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
	orderId int(11) DEFAULT NULL,									# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	employeeId int(11) DEFAULT NULL,								# 雇员Id
	monthly varchar(25) DEFAULT NULL,								# 考勤月份（例如2013/9）
  	weekly varchar(25) DEFAULT NULL,								# 考勤周次（例如2013/35）
	startDate datetime DEFAULT NULL,								# 开始时间 - 指Batch运行的时间
	endDate datetime DEFAULT NULL,		 							# 结束时间 - 指Batch运行的时间
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:批准，4:退回，5:已结算，6:取消），需要跟Timesheet Header有联动关系								
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (batchId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_timesheet_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 考勤主表Id
  	accountId int(11) NOT NULL,										# 账户Id
  	employeeId int(11) NOT NULL,									# 雇员Id
  	contractId int(11) NOT NULL,									# 服务协议Id
  	clientId int(11) NOT NULL,										# 客户Id
  	corpId int(11) NOT NULL,										# 客户Id
  	orderId int(11) NOT NULL,										# 订单Id
  	batchId int(11) DEFAULT 0,										# 批次Id
  	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
  	weekly varchar(25) DEFAULT NULL,								# 周次（例如2013/35）
  	startDate datetime NOT NULL,									# 开始日期
  	endDate datetime NOT NULL, 										# 结束日期
  	totalWorkHours double NOT NULL,									# 工作小时数（考勤周期合计）
  	totalWorkDays double NOT NULL, 									# 工作天数（考勤周期合计）
  	totalFullHours double NOT NULL,									# 全勤小时数（考勤周期合计）
  	totalFullDays double NOT NULL, 									# 全勤天数（考勤周期合计）
  	needAudit tinyint(4) NOT NULL,									# 需要审批（是/否）		
  	isNormal tinyint(4) NOT NULL,									# 正常出勤（是/否）
  	attachment varchar(1000) DEFAULT NULL, 							# 附件
  	description varchar(1000) DEFAULT NULL, 						# 可填写退回原因
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:批准，4:退回，5:已结算，6:取消）
  	remark1 varchar(5000) DEFAULT NULL,
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

CREATE TABLE hro_biz_attendance_timesheet_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 考勤从表Id
  	headerId int(11) NOT NULL,										# 考勤主表Id
  	day datetime NOT NULL,											# 日期
  	dayType tinyint(4) NOT NULL,									# 日期类型（参考日历中的日期类型）
  	workHours double NOT NULL,										# 工作小时数（实际）
  	fullHours double NOT NULL,										# 当天全勤小时数
  	signIn datetime DEFAULT NULL,									# 打卡开始时间
  	signOut datetime DEFAULT NULL,									# 打卡结束时间
  	description varchar(1000) DEFAULT NULL, 
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
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_timesheet_allowance (
  	allowanceId int(11) NOT NULL AUTO_INCREMENT,					# 考勤津贴Id
  	headerId int(11) NOT NULL,										# 考勤主表Id
  	itemId int(11) NOT NULL,										# 科目Id
  	base double DEFAULT NULL,										# 基数
	baseFrom int(11) DEFAULT NULL,									# 基数来源，多个科目（即ItemGroup）
	percentage double DEFAULT NULL,									# 比例（当使用基数来源时出现）
	fix double DEFAULT NULL,										# 固定金（当使用基数来源时出现）
	quantity smallint(4) DEFAULT NULL,								# 数量
	discount double DEFAULT NULL,									# 折扣
	multiple double DEFAULT NULL,									# 倍率
	resultCap double DEFAULT NULL,									# 计算结果上限		
	resultFloor double DEFAULT NULL,								# 计算结果下限
	formularType tinyint(4) DEFAULT NULL,							# 计算公式类型（默认 - 数量×倍率×折扣（基数 or 基数来源×比例+固定金），自定义）
	formular varchar(1000) DEFAULT NULL,							# 计算公式
  	description varchar(1000) DEFAULT NULL, 
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
  	PRIMARY KEY (allowanceId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_timesheet_allowance_temp (
	allowanceId int(11) NOT NULL AUTO_INCREMENT ,
	headerId int(11) NOT NULL ,
	itemId int(11) NOT NULL ,
	base double NULL DEFAULT NULL ,
	baseFrom int(11) NULL DEFAULT NULL ,
	percentage double NULL DEFAULT NULL ,
	fix double NULL DEFAULT NULL ,
	quantity smallint(4) NULL DEFAULT NULL ,
	discount double NULL DEFAULT NULL ,
	multiple double NULL DEFAULT NULL ,
	resultCap double NULL DEFAULT NULL ,
	resultFloor double NULL DEFAULT NULL ,
	formularType tinyint(4) NULL DEFAULT NULL ,
	formular varchar(1000) DEFAULT NULL ,
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (allowanceId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;;

CREATE TABLE hro_biz_attendance_timesheet_header_temp(
	headerId int(11) NOT NULL AUTO_INCREMENT ,
	accountId int(11) NOT NULL ,
	employeeId int(11) NOT NULL ,
	contractId int(11) NOT NULL ,
	clientId int(11) NOT NULL ,
	corpId int(11) DEFAULT NULL,			
	orderId int(11) NULL DEFAULT NULL ,
	batchId int(11) NULL DEFAULT 0 ,
	monthly varchar(25) DEFAULT NULL ,
	weekly varchar(25) DEFAULT NULL ,
	startDate datetime NOT NULL ,
	endDate datetime NOT NULL ,
	totalWorkHours double NOT NULL ,
	totalWorkDays double NOT NULL ,
	totalFullHours double NOT NULL ,
	totalFullDays double NOT NULL ,
	needAudit tinyint(4) NOT NULL ,
	isNormal tinyint(4) NOT NULL ,
	attachment varchar(1000) DEFAULT NULL ,
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (headerId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;;

CREATE TABLE hro_biz_attendance_leave_detail_temp(
	leaveDetailId int(11) NOT NULL AUTO_INCREMENT ,
	leaveHeaderId int(11) NOT NULL ,
	timesheetId int(11) NULL DEFAULT NULL ,
	itemId int(11) NOT NULL ,
	estimateStartDate datetime NULL DEFAULT NULL ,
	estimateEndDate datetime NULL DEFAULT NULL ,
	actualStartDate datetime NULL DEFAULT NULL ,
	actualEndDate datetime NULL DEFAULT NULL ,
	estimateLegalHours double(8,1) NULL DEFAULT NULL ,
	estimateBenefitHours double(8,1) NULL DEFAULT NULL ,
	actualLegalHours double(8,1) NULL DEFAULT NULL ,
	actualBenefitHours double(8,1) NULL DEFAULT NULL ,
	attachment varchar(1000) DEFAULT NULL ,
	retrieveStatus tinyint(4) NULL DEFAULT NULL ,
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (leaveDetailId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_leave_header_temp(
	leaveHeaderId int(11) NOT NULL AUTO_INCREMENT ,
	batchId int(11)  NULL ,
	accountId int(11) NOT NULL ,
	clientId int(11) NOT NULL ,
	corpId int(11) NULL DEFAULT NULL ,
	employeeId int(11) NOT NULL ,
	contractId int(11) NOT NULL ,
	itemId int(11) NOT NULL ,
	retrieveId int(11) NULL DEFAULT NULL ,
	estimateStartDate datetime NULL DEFAULT NULL ,
	estimateEndDate datetime NULL DEFAULT NULL ,
	actualStartDate datetime NULL DEFAULT NULL ,
	actualEndDate datetime NULL DEFAULT NULL ,
	estimateLegalHours double(8,1) NULL DEFAULT NULL ,
	estimateBenefitHours double(8,1) NULL DEFAULT NULL ,
	actualLegalHours double(8,1) NULL DEFAULT NULL ,
	actualBenefitHours double(8,1) NULL DEFAULT NULL ,
	attachment varchar(1000) DEFAULT NULL ,
	description varchar(1000) DEFAULT NULL ,
	retrieveStatus tinyint(4) NULL DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (leaveHeaderId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;
;

CREATE TABLE hro_biz_attendance_ot_detail_temp(
	otDetailId int(11) NOT NULL AUTO_INCREMENT ,
	otHeaderId int(11) NOT NULL ,
	timesheetId int(11) NULL DEFAULT NULL ,
	itemId int(11) NOT NULL ,
	estimateStartDate datetime NULL DEFAULT NULL ,
	estimateEndDate datetime NULL DEFAULT NULL ,
	actualStartDate datetime NULL DEFAULT NULL ,
	actualEndDate datetime NULL DEFAULT NULL ,
	estimateHours double(8,1) NULL DEFAULT NULL ,
	actualHours double(8,1) NULL DEFAULT NULL ,
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (otDetailId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_ot_header_temp (
	otHeaderId int(11) NOT NULL AUTO_INCREMENT ,
	batchId int(11) NULL ,
	accountId int(11) NOT NULL ,
	clientId int(11) NOT NULL ,
	corpId int(11) NULL DEFAULT NULL ,
	employeeId int(11) NOT NULL ,
	contractId int(11) NOT NULL ,
	itemId int(11) NOT NULL ,
	estimateStartDate datetime NULL DEFAULT NULL ,
	estimateEndDate datetime NULL DEFAULT NULL ,
	actualStartDate datetime NULL DEFAULT NULL ,
	actualEndDate datetime NULL DEFAULT NULL ,
	estimateHours double(8,1) NULL DEFAULT NULL ,
	actualHours double(8,1) NULL DEFAULT NULL ,
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (otHeaderId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_employee_contract_settlement (
	employeeSettlementId int(11) NOT NULL AUTO_INCREMENT,			# 雇员成本/营收Id，主键
	contractId int(11) NOT NULL,									# 劳动合同Id
	itemId int(11) DEFAULT NULL,									# 元素Id
	baseFrom int(11) DEFAULT NULL,									# 元素组（即ItemGroup） 
	divideType int(11) DEFAULT NULL,								# 拆分方式（1：按照法务实体2：按照业务类型3：按照部门）
	percentage double DEFAULT NULL,									# 比例（0-100小数）
	fix double DEFAULT NULL,										# 固定金（0-100000）
	startDate datetime DEFAULT NULL,								# 生效时间（预留字段）
	endDate datetime DEFAULT NULL,								    # 结束时间（预留字段）
	resultCap double DEFAULT NULL,									# 计算结果上限（预留字段）
	resultFloor double DEFAULT NULL,								# 计算结果下限（预留字段）
	description varchar(1000) DEFAULT NULL,							# 描述
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
  	PRIMARY KEY (employeeSettlementId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_record (
 	recordId int(11) NOT NULL AUTO_INCREMENT,
 	serialId int(11) NOT NULL ,										# 标识，判断记录是否重复 
 	accountId int(11) NOT NULL ,
 	corpId int(11) DEFAULT NULL , 
 	employeeId int(11) NOT NULL,         							# 员工ID
 	employeeNo varchar(50) DEFAULT NULL,       						# 员工编号 
 	employeeNameZH varchar(200) DEFAULT NULL,       				# 员工姓名（中文）
 	employeeNameEN varchar(200) DEFAULT NULL,       				# 员工姓名（英文）
 	signDate date DEFAULT NULL,        								# 打卡日期（2014-06-04）
 	signTime time DEFAULT NULL,        								# 打卡时间（14:27）
 	signType varchar(50) DEFAULT NULL,       						# 签到方式 
 	machineNo varchar(50) DEFAULT NULL,       						# 设备编号 
   	description varchar(1000) DEFAULT NULL, 
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
   	PRIMARY KEY (recordId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_import_header(
	headerId int(11) NOT NULL AUTO_INCREMENT ,						# 导入考勤主表ID
	batchId int(11) NOT NULL ,										# 批次ID
	accountId int(11) DEFAULT NULL ,
	clientId int(11) DEFAULT NULL ,
	corpId int(11) NULL DEFAULT NULL ,
	contractId int(11) DEFAULT NULL ,								# 劳动合同ID
	monthly varchar(25) DEFAULT NULL ,								# 考勤表月份
	totalWorkHours double NOT NULL,									# 工作小时数（考勤周期合计）
  	totalWorkDays double NOT NULL, 									# 工作天数（考勤周期合计）
  	totalFullHours double NOT NULL,									# 全勤小时数（考勤周期合计）
  	totalFullDays double NOT NULL, 									# 全勤天数（考勤周期合计）
	attachment varchar(1000) DEFAULT NULL ,							# 附件
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (headerId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_biz_attendance_import_detail(
	detailId int(11) NOT NULL AUTO_INCREMENT ,						# 导入考勤从表ID
	headerId int(11) NOT NULL ,										
	itemId int(11) DEFAULT NULL ,										# 科目ID
	hours double DEFAULT NULL ,											# 考勤小时数
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (detailId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;
use HRO;

DROP TABLE IF EXISTS hro_sb_batch;									# 社保批次
DROP TABLE IF EXISTS hro_sb_header;									# 社保主表 - 提交状态可参与结算
DROP TABLE IF EXISTS hro_sb_detail;									# 社保从表 - 提交状态可参与结算
DROP TABLE IF EXISTS hro_sb_header_temp;							# 社保主表临时表 - 供应商上传社保方案用
DROP TABLE IF EXISTS hro_sb_detail_temp;							# 社保从表临时表 - 供应商上传社保方案用
DROP TABLE IF EXISTS hro_sb_adjustment_header;						# 社保调整主表 - 提交状态可参与结算
DROP TABLE IF EXISTS hro_sb_adjustment_detail;						# 社保调整从表

CREATE TABLE hro_sb_batch (											
	batchId int(11) NOT NULL AUTO_INCREMENT,						# 批次Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) DEFAULT NULL,									# 法务实体Id
	businessTypeId int(11) DEFAULT NULL,							# 业务类型Id
	cityId int(11) DEFAULT NULL,									# 社保城市Id，多数情况按照社保城市操作
	clientId int(11) DEFAULT NULL,									# 客户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
	orderId int(11) DEFAULT NULL,									# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	monthly varchar(25) DEFAULT NULL,								# 账单月份（例如2013/9），（不作为入账参考，所属月、操作月、费用月之外的月份，社保比较特殊）
	startDate datetime DEFAULT NULL,								# 开始时间 - 指Batch运行的时间
	endDate datetime DEFAULT NULL,									# 结束时间 - 指Batch运行的时间
  	description varchar(1000) DEFAULT NULL,							
  	sbType tinyint(4) DEFAULT 2,									# 社保方案类型 0:请选择##1:社保##2:公积金##3:综合##4:其他
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:批准，3:确认，4:提交，5:已结算）									
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

CREATE TABLE hro_sb_header (							
	headerId int(11) NOT NULL AUTO_INCREMENT,						# 社保主表Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	batchId int(11) NOT NULL,										# 批次Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	clientNo varchar(50) DEFAULT NULL,								# 客户编号
	clientNameZH varchar(200) DEFAULT NULL,							# 客户名称（英文）
	clientNameEN varchar(200) DEFAULT NULL,							# 客户名称（中文）
	orderId int(11) NOT NULL,										# 订单Id
	contractId int(11) NOT NULL,									# 服务协议Id
	contractStartDate datetime DEFAULT NULL,						# 服务协议开始日期
	contractEndDate datetime DEFAULT NULL,							# 服务协议结束日期
	contractBranch varchar(25) DEFAULT NULL,						# 服务协议所属部门（Branch Id）
  	contractOwner varchar(25) DEFAULT NULL,							# 服务协议所属人（Position Id）
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员中文名
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员英文名
	employeeSBId int(11) NOT NULL,									# 雇员社保方案Id
	employeeSBNameZH varchar(200) DEFAULT NULL,						# 雇员社保方案名称（中文）
	employeeSBNameEN varchar(200) DEFAULT NULL,						# 雇员社保方案名称（英文）
	cityId int(11) DEFAULT NULL,									# 社保城市Id
	vendorId int(11) DEFAULT NULL,									# 供应商Id，“0”或者空表示不使用供应商
	vendorNameZH varchar(200) DEFAULT NULL,							# 供应商名称（中文）
	vendorNameEN varchar(200) DEFAULT NULL,							# 供应商名称（英文）
	vendorServiceIds varchar(1000) DEFAULT NULL,					# 供应商服务内容{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# 供应商服务费
	workPlace varchar(100) DEFAULT NULL,							# 雇员工作地点
	gender tinyint(4) DEFAULT NULL,									# 性别（男/女），从称呼转译
	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
	needMedicalCard tinyint(4) DEFAULT NULL,						# 需要办理医保卡						
	needSBCard tinyint(4) DEFAULT NULL,								# 需要办理社保卡
	medicalNumber varchar(50) DEFAULT NULL,							# 医保卡帐号
	sbNumber varchar(50) DEFAULT NULL,								# 社保卡帐号
	fundNumber varchar(50) DEFAULT NULL,							# 公积金帐号
	personalSBBurden tinyint(4) DEFAULT NULL,						# 社保个人部分公司承担（是，否）
	residencyType tinyint(4) DEFAULT NULL,							# 户籍性质
	residencyCityId  int(11) DEFAULT NULL,							# 户籍城市
	residencyAddress varchar(200) DEFAULT NULL,						# 户籍地址
	highestEducation int(4) DEFAULT NULL,							# 最高学历
	maritalStatus tinyint(4) DEFAULT NULL,							# 婚姻状况
	employStatus tinyint(4) DEFAULT NULL,							# 雇员状态
	sbStatus tinyint(4) DEFAULT NULL,								# 社保状态
	startDate datetime DEFAULT NULL,								# 加保日期（起缴年月）
	endDate datetime DEFAULT NULL,									# 退保日期
	onboardDate datetime DEFAULT NULL,								# 入职日期（跟服务协议开始时间一致）
	resignDate datetime DEFAULT NULL,								# 离职日期
	monthly varchar(25) DEFAULT NULL,								# 账单月份（例如2013/9）
	flag tinyint(4) DEFAULT NULL,									# 社保实际缴纳标识。0 请选择 ,1正常，2未缴纳
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:批准，3:确认，4:提交，5:已结算）			
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

CREATE TABLE hro_sb_header_temp (							
	headerId int(11) NOT NULL AUTO_INCREMENT,						# 社保主表Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	batchId int(11) NOT NULL,										# 批次Id
	clientId int(11) DEFAULT NULL,									# 客户Id
	corpId int(11) default null,									# 企业ID，用于Inhouse使用
	clientNo varchar(50) DEFAULT NULL,								# 客户编号
	clientNameZH varchar(200) DEFAULT NULL,							# 客户名称（英文）
	clientNameEN varchar(200) DEFAULT NULL,							# 客户名称（中文）
	orderId int(11) NOT NULL,										# 订单Id
	contractId int(11) NOT NULL,									# 服务协议Id
	contractStartDate datetime DEFAULT NULL,						# 服务协议开始日期
	contractEndDate datetime DEFAULT NULL,							# 服务协议结束日期
	contractBranch varchar(25) DEFAULT NULL,						# 服务协议所属部门（Branch Id）
  	contractOwner varchar(25) DEFAULT NULL,							# 服务协议所属人（Position Id）
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员中文名
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员英文名
	employeeSBId int(11) NOT NULL,									# 雇员社保方案Id
	employeeSBNameZH varchar(200) DEFAULT NULL,						# 雇员社保方案名称（中文）
	employeeSBNameEN varchar(200) DEFAULT NULL,						# 雇员社保方案名称（英文）
	cityId int(11) DEFAULT NULL,									# 社保城市Id
	vendorId int(11) DEFAULT NULL,									# 供应商Id，“0”或者空表示不使用供应商
	vendorNameZH varchar(200) DEFAULT NULL,							# 供应商名称（中文）
	vendorNameEN varchar(200) DEFAULT NULL,							# 供应商名称（英文）
	vendorServiceIds varchar(1000) DEFAULT NULL,					# 供应商服务内容{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# 供应商服务费
	workPlace varchar(100) DEFAULT NULL,							# 雇员工作地点
	gender tinyint(4) DEFAULT NULL,									# 性别（男/女），从称呼转译
	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
	needMedicalCard tinyint(4) DEFAULT NULL,						# 需要办理医保卡						
	needSBCard tinyint(4) DEFAULT NULL,								# 需要办理社保卡
	medicalNumber varchar(50) DEFAULT NULL,							# 医保卡帐号
	sbNumber varchar(50) DEFAULT NULL,								# 社保卡帐号
	fundNumber varchar(50) DEFAULT NULL,							# 公积金帐号
	personalSBBurden tinyint(4) DEFAULT NULL,						# 社保个人部分公司承担（是，否）
	residencyType tinyint(4) DEFAULT NULL,							# 户籍性质
	residencyCityId  int(11) DEFAULT NULL,							# 户籍城市
	residencyAddress varchar(200) DEFAULT NULL,						# 户籍地址
	highestEducation tinyint(4) DEFAULT NULL,						# 最高学历
	maritalStatus tinyint(4) DEFAULT NULL,							# 婚姻状况
	employStatus tinyint(4) DEFAULT NULL,							# 雇员状态
	sbStatus tinyint(4) DEFAULT NULL,								# 社保状态
	startDate datetime DEFAULT NULL,								# 加保日期（起缴年月）
	endDate datetime DEFAULT NULL,									# 退保日期
	onboardDate datetime DEFAULT NULL,								# 入职日期（跟服务协议开始时间一致）
	resignDate datetime DEFAULT NULL,								# 离职日期
	monthly varchar(25) DEFAULT NULL,								# 账单月份（例如2013/9）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:批准，3:确认，4:提交，5:已结算）			
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

CREATE TABLE hro_sb_detail (			
	detailId int(11) NOT NULL AUTO_INCREMENT,						# 社保从表Id
	headerId int(11) NOT NULL,										# 社保主表Id
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	basePersonal double DEFAULT NULL,								# 基数（个人）
	baseCompany double DEFAULT NULL,								# 基数（公司）
	ratePersonal double DEFAULT NULL,								# 比率（个人）
	rateCompany double DEFAULT NULL,								# 比率（公司）
	fixPersonal double DEFAULT NULL,								# 固定金（个人）
	fixCompany double DEFAULT NULL,									# 固定金（公司）
	amountPersonal double DEFAULT NULL,								# 合计（个人）
	amountCompany double DEFAULT NULL,								# 合计（公司）
	monthly varchar(25) DEFAULT NULL,								# 账单月份（例如2013/9）
	accountMonthly varchar(25) DEFAULT NULL,						# 所属月份（例如2013/9）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:批准，3:确认，4:提交，5:已结算）				
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

CREATE TABLE hro_sb_detail_temp (			
	detailId int(11) NOT NULL AUTO_INCREMENT,						# 社保从表Id
	headerId int(11) NOT NULL,										# 社保主表Id
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	basePersonal double DEFAULT NULL,								# 基数（个人）
	baseCompany double DEFAULT NULL,								# 基数（公司）
	ratePersonal double DEFAULT NULL,								# 比率（个人）
	rateCompany double DEFAULT NULL,								# 比率（公司）
	fixPersonal double DEFAULT NULL,								# 固定金（个人）
	fixCompany double DEFAULT NULL,									# 固定金（公司）
	amountPersonal double DEFAULT NULL,								# 合计（个人）
	amountCompany double DEFAULT NULL,								# 合计（公司）
	monthly varchar(25) DEFAULT NULL,								# 账单月份（例如2013/9）
	accountMonthly varchar(25) DEFAULT NULL,						# 所属月份（例如2013/9）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:批准，3:确认，4:提交，5:已结算）				
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

CREATE TABLE hro_sb_adjustment_header (								 
	adjustmentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# 调整主表Id，调整以服务协议为参照
	accountId int(11) NOT NULL,										# 账户Id
	vendorId int(11) DEFAULT NULL,									# 供应商Id，“0”或者空表示不使用供应商
	vendorNameZH varchar(200) DEFAULT NULL,							# 供应商名称（中文）
	vendorNameEN varchar(200) DEFAULT NULL,							# 供应商名称（英文）
	orderId int(11) NOT NULL,										# 订单Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	clientId int(11) NOT NULL,										# 客户Id
	clientNo varchar(50) DEFAULT NULL,								# 客户编号
	clientNameZH varchar(200) DEFAULT NULL,							# 客户名称（中文）
	clientNameEN varchar(200) DEFAULT NULL,							# 客户名称（英文）
	corpId int(11) NOT NULL,										# 客户Id
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员中文名
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员英文名
	employeeSBId int(11) NOT NULL,									# 雇员社保方案Id
	contractId int(11) NOT NULL,									# 服务协议Id
	personalSBBurden tinyint(4) DEFAULT NULL,						# 社保个人部分公司承担（是，否）
	amountPersonal double DEFAULT NULL,								# 合计（个人）
	amountCompany double DEFAULT NULL,								# 合计（公司）
	monthly varchar(25) DEFAULT NULL,								# 账单月份
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:提交，4:退回，5:已结算）			
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (adjustmentHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sb_adjustment_detail (								 
	adjustmentDetailId int(11) NOT NULL AUTO_INCREMENT,				# 调整从表Id
	adjustmentHeaderId int(11) NOT NULL,							# 调整主表Id
	itemId int(11) NOT NULL,										# 科目Id
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	amountPersonal double DEFAULT NULL,								# 合计（个人）
	amountCompany double DEFAULT NULL,								# 合计（公司）
	monthly varchar(25) DEFAULT NULL,								# 账单月份
	accountMonthly varchar(25) DEFAULT NULL,						# 所属月份（例如2013/9）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:启用，2:停用）				
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (adjustmentDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
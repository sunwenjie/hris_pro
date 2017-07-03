use HRO;

DROP TABLE IF EXISTS hro_cb_batch;									# 商保批次
DROP TABLE IF EXISTS hro_cb_header;									# 商保主表 - 提交状态可参与结算
DROP TABLE IF EXISTS hro_cb_detail;									# 商保从表 - 提交状态可参与结算

CREATE TABLE hro_cb_batch (											
	batchId int(11) NOT NULL AUTO_INCREMENT,						# 批次Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) DEFAULT NULL,									# 法务实体Id
	businessTypeId int(11) DEFAULT NULL,							# 业务类型Id
	cbId int(11) DEFAULT NULL,										# 商保方案Id
	clientId int(11) DEFAULT NULL,									# 客户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
	orderId int(11) DEFAULT NULL,									# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	monthly varchar(25) DEFAULT NULL,								# 账单月份（例如2013/9），（商保账单月份跟所属月份一致）
	startDate datetime DEFAULT NULL,								# 开始时间 - 指Batch运行的时间
	endDate datetime DEFAULT NULL,									# 结束时间 - 指Batch运行的时间
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
  	PRIMARY KEY (batchId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_cb_header (							
	headerId int(11) NOT NULL AUTO_INCREMENT,						# 商保主表Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	batchId int(11) NOT NULL,										# 批次Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	orderId int(11) NOT NULL,										# 订单Id
	contractId int(11) NOT NULL,									# 服务协议Id
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员中文名
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员英文名
	employeeCBId int(11) NOT NULL,									# 雇员商保方案Id
	workPlace varchar(100) DEFAULT NULL,							# 雇员工作地点
	gender tinyint(4) DEFAULT NULL,									# 性别（男/女），从称呼转译
	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
	residencyType tinyint(4) DEFAULT NULL,							# 户籍性质
	residencyCityId  int(11) DEFAULT NULL,							# 户籍城市
	residencyAddress varchar(200) DEFAULT NULL,						# 户籍地址
	highestEducation tinyint(4) DEFAULT NULL,						# 最高学历
	maritalStatus tinyint(4) DEFAULT NULL,							# 婚姻状况
	employStatus tinyint(4) DEFAULT NULL,							# 雇员状态
	cbStatus tinyint(4) DEFAULT NULL,								# 商保状态
	startDate datetime DEFAULT NULL,								# 起购日期
	endDate datetime DEFAULT NULL,									# 退购日期
	onboardDate datetime DEFAULT NULL,								# 入职日期（跟服务协议开始时间一致）
	resignDate datetime DEFAULT NULL,								# 离职日期
	monthly varchar(25) DEFAULT NULL,								# 所属月份
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

CREATE TABLE hro_cb_detail (			
	detailId int(11) NOT NULL AUTO_INCREMENT,						# 商保从表Id
	headerId int(11) NOT NULL,										# 商保主表Id
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	amountPurchaseCost double DEFAULT NULL,							# 合计（采购成本）
	amountSalesCost double DEFAULT NULL,							# 合计（销售成本）
	amountSalesPrice double DEFAULT NULL,							# 合计（销售价格）
	monthly varchar(25) DEFAULT NULL,								# 所属月份
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
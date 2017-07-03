use HRO;

DROP TABLE IF EXISTS hro_settle_order_batch_temp;					# 订单批次 - 预算
DROP TABLE IF EXISTS hro_settle_order_header_temp;					# 订单主表 - 预算
DROP TABLE IF EXISTS hro_settle_order_contract_temp;				# 订单服务协议 - 预算
DROP TABLE IF EXISTS hro_settle_order_detail_temp;					# 订单从表 - 预算
DROP TABLE IF EXISTS hro_settle_order_batch;						# 订单批次
DROP TABLE IF EXISTS hro_settle_order_header;						# 订单主表
DROP TABLE IF EXISTS hro_settle_order_contract;						# 订单服务协议
DROP TABLE IF EXISTS hro_settle_order_detail;						# 订单从表
DROP TABLE IF EXISTS hro_settle_adjustment_header;					# 调整主表
DROP TABLE IF EXISTS hro_settle_adjustment_detail;					# 调整从表

CREATE TABLE hro_settle_order_batch_temp (							# 存在Batch Temp中的所有订单锁定，锁定订单不能再被结算
	batchId int(11) NOT NULL AUTO_INCREMENT,						# 批次Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) DEFAULT NULL,									# 法务实体Id
	businessTypeId int(11) DEFAULT NULL,							# 业务类型Id
	clientId int(11) DEFAULT NULL,									# 客户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
	orderId int(11) DEFAULT NULL,									# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
  	weekly varchar(25) DEFAULT NULL,								# 周次（例如2013/35）
	containSalary tinyint(4) DEFAULT NULL,							# 工资（是/否） - 批次包含工资
	containSB tinyint(4) DEFAULT NULL,								# 社保（是/否） - 批次包含社保
	containCB tinyint(4) DEFAULT NULL,								# 商保（是/否） - 批次包含商保
	containServiceFee tinyint(4) DEFAULT NULL,						# 服务费（是/否） - 批次包含服务费
	containOther tinyint(4) DEFAULT NULL,							# 其他（是/否）-  - 批次包含其他
	accountPeriod datetime DEFAULT NULL,							# 会计期
	startDate datetime DEFAULT NULL,								# 开始时间 - 指Batch运行的时间
	endDate datetime DEFAULT NULL,									# 结束时间 - 指Batch运行的时间
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
  	PRIMARY KEY (batchId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_header_temp (							
	orderHeaderId int(11) NOT NULL AUTO_INCREMENT,					# Order Go主表Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	batchId int(11) NOT NULL,										# 批次Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	orderId int(11) NOT NULL,										# 订单Id
	startDate datetime DEFAULT NULL,								# 订单开始日期
	endDate datetime DEFAULT NULL,									# 订单结束日期
	taxId int(11) DEFAULT NULL,										# 税率Id
	taxNameZH varchar(200) DEFAULT NULL,							# 税率名称（中文）
	taxNameEN varchar(200) DEFAULT NULL,							# 税率名称（英文）
	taxRemark varchar(200) DEFAULT NULL,							# 税率备注
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	orderAmount double DEFAULT NULL,								# 订单总金额
	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
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
  	PRIMARY KEY (orderHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;	

CREATE TABLE hro_settle_order_contract_temp (							
	contractId int(11) NOT NULL AUTO_INCREMENT,						# 服务协议Id
	orderHeaderId int(11) NOT NULL ,								# 订单主表Id（结算订单表）
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	orderId int(11) NOT NULL,										# 订单Id（或帐套Id）
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeContractId int(11) NOT NULL,							# 雇员服务协议Id
	timesheetId int(11) DEFAULT NULL,								# 考勤表Id
	startDate datetime DEFAULT NULL,								# 服务协议开始日期
	endDate datetime DEFAULT NULL,									# 服务协议结束日期
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/09）
  	salaryMonth varchar(25) DEFAULT NULL,							# 工资月份（例如2013/09）
  	sbMonth varchar(25) DEFAULT NULL,								# 社保月份（例如2013/09）
  	cbMonth varchar(25) DEFAULT NULL,								# 商保月份（例如2013/09）
  	fundMonth varchar(25) DEFAULT NULL,								# 公积金月份（例如2013/09）
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
  	PRIMARY KEY (contractId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_detail_temp (			
	orderDetailId int(11) NOT NULL AUTO_INCREMENT,					# 订单从表Id
	contractId int(11) NOT NULL,									# 服务协议Id
	headerId varchar(200) DEFAULT NULL,								# 主表Id，与明细类型搭配使用
	detailId varchar(200) DEFAULT NULL,								# 明细Id，与明细类型搭配使用
	detailType tinyint(4) DEFAULT NULL,								# 明细类型（1:社保明细；2:商保明细；3:社保调整明细；4:工资导入主表；5:工资导入明细）
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	base double DEFAULT NULL,										# 直接输入的值或Base From的结果值
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不计算）
	discount double DEFAULT NULL,									# 折扣（暂时不计算）
	multiple double DEFAULT NULL,									# 倍率（暂时不计算）
	sbBaseCompany double DEFAULT NULL,								# 社保基数（公司）
	sbBasePersonal double DEFAULT NULL,								# 社保基数（个人）
	billRatePersonal double NOT NULL,								# 比率（个人收入）
	billRateCompany double NOT NULL,								# 比率（公司营收）
	costRatePersonal double NOT NULL,								# 比率（个人支出）
	costRateCompany double NOT NULL,								# 比率（公司成本）
	billFixPersonal double NOT NULL,								# 固定金（个人收入）
	billFixCompany double NOT NULL,									# 固定金（公司营收）
	costFixPersonal double NOT NULL,								# 固定金（个人支出）
	costFixCompany double NOT NULL,									# 固定金（公司成本）
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	taxAmountActual double NOT NULL,								# 税收（实缴） 
	taxAmountCost double NOT NULL,									# 税收（成本）
	taxAmountSales double NOT NULL,									# 税收（销售）
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
  	PRIMARY KEY (orderDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_batch (								
	batchId int(11) NOT NULL AUTO_INCREMENT,			
	batchTempId int(11) NOT NULL,									# 临时表主键
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) DEFAULT NULL,									# 法务实体Id
	businessTypeId int(11) DEFAULT NULL,							# 业务类型Id
	clientId int(11) DEFAULT NULL,									# 客户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
	orderId int(11) DEFAULT NULL,									# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
  	weekly varchar(25) DEFAULT NULL,								# 周次（例如2013/35）
	containSalary tinyint(4) DEFAULT NULL,							# 工资（是/否） - 批次包含工资
	containSB tinyint(4) DEFAULT NULL,								# 社保（是/否） - 批次包含社保
	containCB tinyint(4) DEFAULT NULL,								# 商保（是/否） - 批次包含商保
	containServiceFee tinyint(4) DEFAULT NULL,						# 服务费（是/否） - 批次包含服务费
	containOther tinyint(4) DEFAULT NULL,							# 其他（是/否）-  - 批次包含其他
	accountPeriod datetime DEFAULT NULL,							# 会计期
	startDate datetime DEFAULT NULL,								# 开始时间 - 指Batch运行的时间
	endDate datetime DEFAULT NULL,									# 结束时间 - 指Batch运行的时间
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
  	PRIMARY KEY (batchId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_header (								 
	orderHeaderId int(11) NOT NULL AUTO_INCREMENT,					# Order主表Id
	orderHeaderTempId int(11) NOT NULL,								# 临时表主键
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	batchId int(11) NOT NULL,										# 批次Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	orderId int(11) NOT NULL,										# 订单Id
	startDate datetime DEFAULT NULL,								# 订单开始日期
	endDate datetime DEFAULT NULL,									# 订单结束日期
	taxId int(11) DEFAULT NULL,										# 税率Id
	taxNameZH varchar(200) DEFAULT NULL,							# 税率名称（中文）
	taxNameEN varchar(200) DEFAULT NULL,							# 税率名称（英文）
	taxRemark varchar(200) DEFAULT NULL,							# 税率备注
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	orderAmount double DEFAULT NULL,								# 订单总金额
	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
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
  	PRIMARY KEY (orderHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_contract (							
	contractId int(11) NOT NULL AUTO_INCREMENT,						# 服务协议Id
	contractTempId int(11) NOT NULL,								# 临时表主键
	orderHeaderId int(11) NOT NULL,									# 订单主表Id（结算订单表）
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	orderId int(11) NOT NULL,										# 订单Id（或帐套Id）
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeContractId int(11) NOT NULL,							# 雇员服务协议Id
	timesheetId int(11) DEFAULT NULL,								# 考勤表Id
	startDate datetime DEFAULT NULL,								# 服务协议开始日期
	endDate datetime DEFAULT NULL,									# 服务协议结束日期
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
  	salaryMonth varchar(25) DEFAULT NULL,							# 工资月份（例如2013/09）
  	sbMonth varchar(25) DEFAULT NULL,								# 社保月份（例如2013/09）
  	cbMonth varchar(25) DEFAULT NULL,								# 商保月份（例如2013/09）
  	fundMonth varchar(25) DEFAULT NULL,								# 公积金月份（例如2013/09）
  	paymentFlag tinyint(4) DEFAULT NULL,							# 是否已算薪酬（1:是，2:否）
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
  	PRIMARY KEY (contractId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_detail (			
	orderDetailId int(11) NOT NULL AUTO_INCREMENT,					# 订单从表Id
	contractId int(11) NOT NULL,									# 服务协议Id
	headerId varchar(200) DEFAULT NULL,								# 主表Id，与明细类型搭配使用
	detailId varchar(200) DEFAULT NULL,								# 明细Id，与明细类型搭配使用
	detailType tinyint(4) DEFAULT NULL,								# 明细类型（1:社保明细；2:商保明细；3:社保调整明细）
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	base double DEFAULT NULL,										# 直接输入的值或Base From的结果值
	quantity smallint(4) DEFAULT NULL,								# 数量（暂时不计算）
	discount double DEFAULT NULL,									# 折扣（暂时不计算）
	multiple double DEFAULT NULL,									# 倍率（暂时不计算）
	sbBaseCompany double DEFAULT NULL,								# 社保基数（公司）
	sbBasePersonal double DEFAULT NULL,								# 社保基数（个人）
	billRateCompany double NOT NULL,								# 比率（公司营收）
	billRatePersonal double NOT NULL,								# 比率（个人收入）
	costRateCompany double NOT NULL,								# 比率（公司成本）
	costRatePersonal double NOT NULL,								# 比率（个人支出）
	billFixCompany double NOT NULL,									# 固定金（公司营收）
	billFixPersonal double NOT NULL,								# 固定金（个人收入）
	costFixCompany double NOT NULL,									# 固定金（公司成本）
	costFixPersonal double NOT NULL,								# 固定金（个人支出）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	taxAmountActual double NOT NULL,								# 税收（实缴） 
	taxAmountCost double NOT NULL,									# 税收（成本）
	taxAmountSales double NOT NULL,									# 税收（销售）
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
  	PRIMARY KEY (orderDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_adjustment_header (								 
	adjustmentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# 调整主表Id，调整以服务协议为参照
	accountId int(11) NOT NULL,										# 账户Id
	orderId int(11) NOT NULL,										# 订单Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员中文名
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员英文名
	contractId int(11) NOT NULL,									# 服务协议Id
	taxId int(11) NOT NULL,											# 税率Id
	taxNameZH varchar(200) DEFAULT NULL,							# 税率名称（中文）
	taxNameEN varchar(200) DEFAULT NULL,							# 税率名称（英文）
	adjustmentDate datetime DEFAULT NULL,							# 调整日期（费用日期），默认当天，只能往后，不能往前
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
  	paymentFlag tinyint(4) DEFAULT NULL,							# 是否已算薪酬（1:是，2:否）
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (adjustmentHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_adjustment_detail (								 
	adjustmentDetailId int(11) NOT NULL AUTO_INCREMENT,				# 调整从表Id
	adjustmentHeaderId int(11) NOT NULL,							# 调整主表Id
	itemId int(11) NOT NULL,										# 科目Id
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	base double DEFAULT NULL,										# X直接输入的值或Base From的结果值
	quantity smallint(4) DEFAULT NULL,								# X数量（暂时不计算）
	discount double DEFAULT NULL,									# X折扣（暂时不计算）
	multiple double DEFAULT NULL,									# X倍率（暂时不计算）
	billRatePersonal double DEFAULT NULL,							# X比率（个人收入）
	billRateCompany double DEFAULT NULL,							# X比率（公司营收）
	costRatePersonal double DEFAULT NULL,							# X比率（个人支出）
	costRateCompany double DEFAULT NULL,							# X比率（公司成本）
	billFixPersonal double DEFAULT NULL,							# X固定金（个人收入）
	billFixCompany double DEFAULT NULL,								# X固定金（公司营收）
	costFixPersonal double DEFAULT NULL,							# X固定金（个人支出）
	costFixCompany double DEFAULT NULL,								# X固定金（公司成本）
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	billAmountCompany double NOT NULL,								# 合计（公司营收）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	taxAmountActual double DEFAULT NULL,							# X税收（实缴） 
	taxAmountCost double DEFAULT NULL,								# X税收（成本）
	taxAmountSales double DEFAULT NULL,								# X税收（销售）
	monthly varchar(25) DEFAULT NULL,								# 月份（例如2013/9）
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
  	PRIMARY KEY (adjustmentDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
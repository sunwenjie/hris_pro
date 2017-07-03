use HRO;

DROP TABLE IF EXISTS hro_payment_batch;								# 薪酬批次
DROP TABLE IF EXISTS hro_payment_header;							# 薪酬主表
DROP TABLE IF EXISTS hro_payment_detail;							# 薪酬从表
DROP TABLE IF EXISTS hro_payment_adjustment_header;					# 薪酬调整主表
DROP TABLE IF EXISTS hro_payment_adjustment_detail;					# 薪酬调整从表
DROP TABLE IF EXISTS hro_salary_header;								# 工资主表 - 用于客户计算好的工资数据导入
DROP TABLE IF EXISTS hro_salary_detail;								# 工资从表
DROP TABLE IF EXISTS hro_common_batch;								# 通用批次

CREATE TABLE hro_payment_batch (									# 结算明细中含有标记“是否已支付”
	batchId int(11) NOT NULL AUTO_INCREMENT,						# 批次Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) DEFAULT NULL,									# 法务实体Id
	businessTypeId int(11) DEFAULT NULL,							# 业务类型Id
	clientId int(11) DEFAULT NULL,									# 客户Id
	corpId int(11) DEFAULT NULL,									
	orderId int(11) DEFAULT NULL,									# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	employeeId int(11) DEFAULT NULL,								# 雇员Id
	monthly varchar(25) DEFAULT NULL,								# 薪酬月份（例如2013/9）
  	weekly varchar(25) DEFAULT NULL,								# 薪酬周次（例如2013/35）
	startDate datetime DEFAULT NULL,								# 开始时间 - 指Batch运行的时间
	endDate datetime DEFAULT NULL,		 							# 结束时间 - 指Batch运行的时间
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:提交，3:发放）								
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

CREATE TABLE hro_payment_header (							
	paymentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# 薪酬主表Id，提交、发放暂且先到Header层次
	orderContractId int(11) NOT NULL,								# 结算服务协议Id，跟雇员服务协议Id不一样（调整过来的数据存放AdjustmentHeaderId）
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	batchId int(11) NOT NULL,										# 批次Id
	corpId int(11) NOT NULL,										# 客户Id
	orderId int(11) NOT NULL,										# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	employeeId int(11) DEFAULT NULL,								# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员姓名（中文）
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员姓名（英文）
	itemGroupId int(11) DEFAULT NULL,								# 工资计算分组
	startDate datetime DEFAULT NULL,								# 薪酬开始日期
	endDate datetime DEFAULT NULL,									# 薪酬结束日期
	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
  	bankId int(11) DEFAULT NULL,									# 银行Id	
  	bankNameZH varchar(200) DEFAULT NULL,							# 银行名称（中文）
  	bankNameEN varchar(200) DEFAULT NULL,							# 银行名称（英文）
  	bankAccount varchar(50) DEFAULT NULL,							# 银行账户
  	billAmountCompany double NOT NULL,								# 合计（公司营收）
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	costAmountCompany double NOT NULL,								# 合计（公司成本）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	taxAmountPersonal double NOT NULL,								# 合计（个税）
	addtionalBillAmountPersonal double NOT NULL,					# 附加合计（个人收入），用于税前加的金额
	taxAgentAmountPersonal double DEFAULT NULL,						# 代扣税工资
  	monthly varchar(25) DEFAULT NULL,								# 薪酬月份（例如2013/9）
  	taxFlag tinyint(4) DEFAULT NULL,								# 个税标记（1:已报税，2:未报税）	
  	vendorId int(11) DEFAULT NULL,									# 供应商Id，“0”或者空表示不使用供应商
	vendorNameZH varchar(200) DEFAULT NULL,							# 供应商名称（中文）
	vendorNameEN varchar(200) DEFAULT NULL,							# 供应商名称（英文）
	vendorServiceIds varchar(1000) DEFAULT NULL,					# 供应商服务内容{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# 供应商服务费
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:提交，3:发放）			
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (paymentHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;	

CREATE TABLE hro_payment_detail (			
	paymentDetailId int(11) NOT NULL AUTO_INCREMENT,				# 薪酬从表Id
	paymentHeaderId int(11) NOT NULL,								# 薪酬主表Id
	orderDetailId int(11) DEFAULT NULL,								# 结算明细Id（调整过来的数据存放AdjustmentDetailId）
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	baseCompany double DEFAULT NULL,								# 基数（公司）
	basePersonal double DEFAULT NULL,								# 基数（个人）
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
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:提交，3:发放）					
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (paymentDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_payment_adjustment_header (								 
	adjustmentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# 调整主表Id，最小单位服务协议，薪酬模块调整只调个税
	accountId int(11) NOT NULL,										# 账户Id
	orderId int(11) NOT NULL,										# 订单Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	contractId int(11) NOT NULL,									# 服务协议Id
	employeeId int(11) NOT NULL,									# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员中文名
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员英文名
	itemGroupId int(11) DEFAULT NULL,								# 工资计算分组
	contractId int(11) NOT NULL,									# 服务协议Id
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	taxAmountPersonal double NOT NULL,								# 合计（个税）
	addtionalBillAmountPersonal double NOT NULL,					# 附加合计（个人收入）
	taxAgentAmountPersonal double DEFAULT NULL,						# 代扣税工资
	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	monthly varchar(25) DEFAULT NULL,								# 调整月份（例如2013/9）
  	taxFlag tinyint(4) DEFAULT NULL,								# 个税标记（1:已报税，2:未报税）	
  	vendorId int(11) DEFAULT NULL,									# 供应商Id，“0”或者空表示不使用供应商
	vendorNameZH varchar(200) DEFAULT NULL,							# 供应商名称（中文）
	vendorNameEN varchar(200) DEFAULT NULL,							# 供应商名称（英文）
	vendorServiceIds varchar(1000) DEFAULT NULL,					# 供应商服务内容{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# 供应商服务费
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:批准，4:退回，5:发放）			
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (ajustmentHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_payment_adjustment_detail (								 
	adjustmentDetailId int(11) NOT NULL AUTO_INCREMENT,				# 调整从表Id
	adjustmentHeaderId int(11) NOT NULL,							# 调整主表Id
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	billAmountPersonal double NOT NULL,								# 合计（个人收入）
	costAmountPersonal double NOT NULL,								# 合计（个人支出）
	taxAmountPersonal double NOT NULL,								# 合计（个税）
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
  	PRIMARY KEY (ajustmentDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_salary_header (							
	salaryHeaderId int(11) NOT NULL AUTO_INCREMENT,					# 工资主表Id
	accountId int(11) NOT NULL,										# 账户Id
	entityId int(11) NOT NULL,										# 法务实体Id
	businessTypeId int(11) NOT NULL,								# 业务类型Id
	batchId int(11) DEFAULT NULL,									# 批次Id
	clientId int(11) NOT NULL,										# 客户Id
	corpId int(11) NOT NULL,										# 客户Id
	clientNameZH varchar(200) DEFAULT NULL,							# 客户名称（中文）
	clientNameEN varchar(200) DEFAULT NULL,							# 客户名称（英文）
	orderId int(11) NOT NULL,										# 订单Id
	contractId int(11) DEFAULT NULL,								# 服务协议Id
	employeeId int(11) DEFAULT NULL,								# 雇员Id
	employeeNameZH varchar(200) DEFAULT NULL,						# 雇员姓名（中文）
	employeeNameEN varchar(200) DEFAULT NULL,						# 雇员姓名（英文）
	startDate datetime DEFAULT NULL,								# 薪酬开始日期
	endDate datetime DEFAULT NULL,									# 薪酬结束日期
	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
  	bankId int(11) DEFAULT NULL,									# 银行Id	
  	bankNameZH varchar(200) DEFAULT NULL,							# 银行名称（中文）
  	bankNameEN varchar(200) DEFAULT NULL,							# 银行名称（英文）
  	bankAccount varchar(50) DEFAULT NULL,							# 银行账户
  	billAmountCompany double DEFAULT 0,								# 合计（公司营收）
	billAmountPersonal double DEFAULT 0,							# 合计（个人收入）
	costAmountCompany double DEFAULT 0,								# 合计（公司成本）
	costAmountPersonal double DEFAULT 0,							# 合计（个人支出）
	taxAmountPersonal double DEFAULT 0,								# 合计（个税）
	addtionalBillAmountPersonal double DEFAULT 0,					# 附加合计（个人收入），用于税前加的金额
	estimateSalary double DEFAULT 0,								# 税前工资
	actualSalary double DEFAULT 0,									# 税后收入，用于倒算税的导入 
  	monthly varchar(25) DEFAULT NULL,								# 薪酬月份（例如2013/9）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:提交，3:发放）			
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (salaryHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;	

CREATE TABLE hro_salary_detail (			
	salaryDetailId int(11) NOT NULL AUTO_INCREMENT,					# 工资从表Id
	salaryHeaderId int(11) NOT NULL,								# 工资主表Id
	itemId int(11) NOT NULL,										# 科目Id
	itemNo varchar(50) DEFAULT NULL,								# 科目编号
	nameZH varchar(200) DEFAULT NULL,								# 科目名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 科目名称（英文）
	baseCompany double DEFAULT 0,									# 基数（公司）
	basePersonal double DEFAULT 0,									# 基数（个人）
	billRateCompany double DEFAULT 0,								# 比率（公司营收）
	billRatePersonal double DEFAULT 0,								# 比率（个人收入）
	costRateCompany double DEFAULT 0,								# 比率（公司成本）
	costRatePersonal double DEFAULT 0,								# 比率（个人支出）
	billFixCompany double DEFAULT 0,								# 固定金（公司营收）
	billFixPersonal double DEFAULT 0,								# 固定金（个人收入）
	costFixCompany double DEFAULT 0,								# 固定金（公司成本）
	costFixPersonal double DEFAULT 0,								# 固定金（个人支出）
	billAmountCompany double DEFAULT 0,								# 合计（公司营收）
	billAmountPersonal double DEFAULT 0,							# 合计（个人收入）
	costAmountCompany double DEFAULT 0,								# 合计（公司成本）
	costAmountPersonal double DEFAULT 0,							# 合计（个人支出）
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:提交，3:发放）					
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (salaryDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_common_batch (										# 结算明细中含有标记“是否已支付”
	batchId int(11) NOT NULL AUTO_INCREMENT,						# 批次Id
	accountId int(11) NOT NULL,										# 账户Id
	corpId int(11) DEFAULT NULL,									# 客户Id
	vendorId int(11) DEFAULT NULL,									# 供应商Id
	accessAction varchar(200) DEFAULT NULL,							# 链接
	importExcelName varchar(100) DEFAULT NULL,						# 导入文件名
  	description varchar(1000) DEFAULT NULL,	
  	owner  varchar(25)  DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（1:新建，2:待审核，3:生效）		工资导入：1新建 2提交 3已结算						
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

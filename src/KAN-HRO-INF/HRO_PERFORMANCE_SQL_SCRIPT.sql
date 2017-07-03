CREATE TABLE hro_performance(
	performanceId int(11) NOT NULL AUTO_INCREMENT,			#主键
	accountId int(11) NOT NULL,
	corpId int(11) NOT NULL,
	yearly int(11) NOT NULL,								#绩效年份
	fullName varchar(50) DEFAULT NULL,
	shortName varchar(50) DEFAULT NULL,
	chineseName varchar(50) DEFAULT NULL,
	employmentEntityEN varchar(200) DEFAULT NULL,			#法务名称(英文)
	employmentEntityZH varchar(200) DEFAULT NULL,			#法务名称(中文)
	companyInitial varchar(200) DEFAULT NULL,				#公司名称
	buFunctionEN varchar(200) DEFAULT NULL,					#上级部门(英文)
	buFunctionZH varchar(200) DEFAULT NULL,
	departmentEN varchar(200) DEFAULT NULL,					#部门
	departmentZH varchar(200) DEFAULT NULL,
	costCenter varchar(200) DEFAULT NULL,
	functionCode varchar(200) DEFAULT NULL,					#业务类型
	location varchar(200) DEFAULT NULL,
	jobRole varchar(200) DEFAULT NULL,
	positionEN varchar(200) DEFAULT NULL,					#职位
	positionZH varchar(200) DEFAULT NULL,
	jobGrade varchar(50) DEFAULT NULL,
	internalTitle varchar(200) DEFAULT NULL,				#内部职称
	lineBizManager varchar(200) DEFAULT NULL,				#业务直线汇报人
	lineHRManager varchar(200) DEFAULT NULL,				#HR直线汇报人
	seniorityDate datetime DEFAULT NULL,					#首次工作日期
	employmentDate datetime DEFAULT NULL,					#入司日期
	shareOptions varchar(200) DEFAULT NULL,
	lastYearPerformanceRating varchar(50) DEFAULT NULL,		#上年绩效等级
	lastYearPerformancePromotion varchar(50) DEFAULT NULL,	#上年晋升
	midYearPromotion varchar(50) DEFAULT NULL,				#本年中晋升
	midYearSalaryIncrease varchar(50) DEFAULT NULL,			#本年中薪资增长
	currencyCode varchar(50) DEFAULT NULL,					#货币
	baseSalaryLocal double DEFAULT NULL,					#月基本工资(本地)
	baseSalaryUSD	double DEFAULT NULL,					#月基本工资(美元)
	annualBaseSalaryLocal double DEFAULT NULL,				#年薪(本地)
	annualBaseSalaryUSD double DEFAULT NULL,
	housingAllowanceLocal double DEFAULT NULL,				#年住房补贴
	childrenEduAllowanceLocal double DEFAULT NULL,			#年儿童教育补贴
	guaranteedCashLocal double DEFAULT NULL,				#年保险费
	guaranteedCashUSD double DEFAULT NULL,					#年保险费(美元)
	monthlyTarget double DEFAULT NULL,						#月奖金
	quarterlyTarget double DEFAULT NULL,					#季度奖金	
	gpTarget double DEFAULT NULL,							
	targetValueLocal double DEFAULT NULL,					#奖金总值
	targetValueUSD double DEFAULT NULL,						
	ttcLocal double DEFAULT NULL,	
	ttcUSD double DEFAULT NULL,
	yearPerformanceRating varchar(50) DEFAULT NULL,			#年绩效评分
	yearPerformancePromotion varchar(50) DEFAULT NULL,		#年绩效晋升
	recommendTTCIncrease varchar(50) DEFAULT NULL,			#推荐TTC增长
	ttcIncrease varchar(50) DEFAULT NULL,					#TTC增长
	newTTCLocal double DEFAULT NULL,
	newTTCUSD double DEFAULT NULL,
	newBaseSalaryLocal double DEFAULT NULL,					#新月薪
	newBaseSalaryUSD double DEFAULT NULL,	
	newAnnualSalaryLocal double DEFAULT NULL,				#新年薪
	newAnnualSalaryUSD double DEFAULT NULL,	
	newAnnualHousingAllowance double DEFAULT NULL,
	newAnnualChildrenEduAllowance double DEFAULT NULL,	
	newAnnualGuaranteedAllowanceLocal double DEFAULT NULL,
	newAnnualGuatanteedAllowanceUSD double DEFAULT NULL,
	newMonthlyTarget double DEFAULT NULL,
	newQuarterlyTarget double DEFAULT NULL,	
	newGPTarget double DEFAULT NULL,	
	newTargetValueLocal double DEFAULT NULL,
	newTargetValueUSD double DEFAULT NULL,
	newJobGrade varchar(50) DEFAULT NULL,
	newInternalTitle varchar(200) DEFAULT NULL,
	newPositionEN varchar(200) DEFAULT NULL,
	newPositionZH varchar(200) DEFAULT NULL,
	newShareOptions varchar(200) DEFAULT NULL,
	targetBonus double DEFAULT NULL,							#分红
	proposedBonus double DEFAULT NULL,							#预留分红
	proposedPayoutLocal double DEFAULT NULL,					#预留支出
	proposedPayoutUSD double DEFAULT NULL,										
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
	PRIMARY KEY (performanceId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;
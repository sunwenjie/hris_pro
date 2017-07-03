CREATE TABLE hro_performance(
	performanceId int(11) NOT NULL AUTO_INCREMENT,			#����
	accountId int(11) NOT NULL,
	corpId int(11) NOT NULL,
	yearly int(11) NOT NULL,								#��Ч���
	fullName varchar(50) DEFAULT NULL,
	shortName varchar(50) DEFAULT NULL,
	chineseName varchar(50) DEFAULT NULL,
	employmentEntityEN varchar(200) DEFAULT NULL,			#��������(Ӣ��)
	employmentEntityZH varchar(200) DEFAULT NULL,			#��������(����)
	companyInitial varchar(200) DEFAULT NULL,				#��˾����
	buFunctionEN varchar(200) DEFAULT NULL,					#�ϼ�����(Ӣ��)
	buFunctionZH varchar(200) DEFAULT NULL,
	departmentEN varchar(200) DEFAULT NULL,					#����
	departmentZH varchar(200) DEFAULT NULL,
	costCenter varchar(200) DEFAULT NULL,
	functionCode varchar(200) DEFAULT NULL,					#ҵ������
	location varchar(200) DEFAULT NULL,
	jobRole varchar(200) DEFAULT NULL,
	positionEN varchar(200) DEFAULT NULL,					#ְλ
	positionZH varchar(200) DEFAULT NULL,
	jobGrade varchar(50) DEFAULT NULL,
	internalTitle varchar(200) DEFAULT NULL,				#�ڲ�ְ��
	lineBizManager varchar(200) DEFAULT NULL,				#ҵ��ֱ�߻㱨��
	lineHRManager varchar(200) DEFAULT NULL,				#HRֱ�߻㱨��
	seniorityDate datetime DEFAULT NULL,					#�״ι�������
	employmentDate datetime DEFAULT NULL,					#��˾����
	shareOptions varchar(200) DEFAULT NULL,
	lastYearPerformanceRating varchar(50) DEFAULT NULL,		#���꼨Ч�ȼ�
	lastYearPerformancePromotion varchar(50) DEFAULT NULL,	#�������
	midYearPromotion varchar(50) DEFAULT NULL,				#�����н���
	midYearSalaryIncrease varchar(50) DEFAULT NULL,			#������н������
	currencyCode varchar(50) DEFAULT NULL,					#����
	baseSalaryLocal double DEFAULT NULL,					#�»�������(����)
	baseSalaryUSD	double DEFAULT NULL,					#�»�������(��Ԫ)
	annualBaseSalaryLocal double DEFAULT NULL,				#��н(����)
	annualBaseSalaryUSD double DEFAULT NULL,
	housingAllowanceLocal double DEFAULT NULL,				#��ס������
	childrenEduAllowanceLocal double DEFAULT NULL,			#���ͯ��������
	guaranteedCashLocal double DEFAULT NULL,				#�걣�շ�
	guaranteedCashUSD double DEFAULT NULL,					#�걣�շ�(��Ԫ)
	monthlyTarget double DEFAULT NULL,						#�½���
	quarterlyTarget double DEFAULT NULL,					#���Ƚ���	
	gpTarget double DEFAULT NULL,							
	targetValueLocal double DEFAULT NULL,					#������ֵ
	targetValueUSD double DEFAULT NULL,						
	ttcLocal double DEFAULT NULL,	
	ttcUSD double DEFAULT NULL,
	yearPerformanceRating varchar(50) DEFAULT NULL,			#�꼨Ч����
	yearPerformancePromotion varchar(50) DEFAULT NULL,		#�꼨Ч����
	recommendTTCIncrease varchar(50) DEFAULT NULL,			#�Ƽ�TTC����
	ttcIncrease varchar(50) DEFAULT NULL,					#TTC����
	newTTCLocal double DEFAULT NULL,
	newTTCUSD double DEFAULT NULL,
	newBaseSalaryLocal double DEFAULT NULL,					#����н
	newBaseSalaryUSD double DEFAULT NULL,	
	newAnnualSalaryLocal double DEFAULT NULL,				#����н
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
	targetBonus double DEFAULT NULL,							#�ֺ�
	proposedBonus double DEFAULT NULL,							#Ԥ���ֺ�
	proposedPayoutLocal double DEFAULT NULL,					#Ԥ��֧��
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
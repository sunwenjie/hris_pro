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
DROP TABLE IF EXISTS hro_mgt_item;									# ��Ŀ
DROP TABLE IF EXISTS hro_mgt_item_group;							# ��Ŀ����
DROP TABLE IF EXISTS hro_mgt_item_group_relation;		     		# ��Ŀ����Ŀ����Ĺ�ϵ
DROP TABLE IF EXISTS hro_mgt_item_mapping;							# ��Ŀӳ��
DROP TABLE IF EXISTS hro_mgt_tax;									# ˰��
DROP TABLE IF EXISTS hro_mgt_social_benefit_solution_header;		# �籣�����������ճ��кͻ������岻ͬ���籣����	
DROP TABLE IF EXISTS hro_mgt_social_benefit_solution_detail;		# �籣�����ӱ����ճ���ѡ��ͬ�����֣�������ȷ������
DROP TABLE IF EXISTS hro_mgt_commercial_benefit_solution_header;	# �̱���������	
DROP TABLE IF EXISTS hro_mgt_commercial_benefit_solution_detail;	# �̱������ӱ�һ���̱����������ж���̱���ϸ
DROP TABLE IF EXISTS hro_mgt_certification;							# ֤�� - ����
DROP TABLE IF EXISTS hro_mgt_membership;							# ���
DROP TABLE IF EXISTS hro_mgt_business_contract_template;			# �����ͬģ��
DROP TABLE IF EXISTS hro_mgt_labor_contract_template;				# �Ͷ���ͬģ��
DROP TABLE IF EXISTS hro_mgt_industry_type;							# ��ҵ����
DROP TABLE IF EXISTS hro_mgt_calendar_header;						# ��������
DROP TABLE IF EXISTS hro_mgt_calendar_detail;						# �����ӱ�
DROP TABLE IF EXISTS hro_mgt_shift_header;							# �Ű�����
DROP TABLE IF EXISTS hro_mgt_shift_detail;							# �Ű�ӱ�
DROP TABLE IF EXISTS hro_mgt_shift_exception;						# �Ű�����
DROP TABLE IF EXISTS hro_mgt_bank;									# ����
DROP TABLE IF EXISTS hro_mgt_setting;								# ��������
DROP TABLE IF EXISTS hro_mgt_sick_leave_salary_header;				# ���ٹ��� - ����
DROP TABLE IF EXISTS hro_mgt_sick_leave_salary_detail;				# ���ٹ��� - �ӱ�
DROP TABLE IF EXISTS hro_mgt_resign_template;						# �˹���ģ��
DROP TABLE IF EXISTS hro_mgt_annual_leave_rule_header;				# ��ٹ��� - ����
DROP TABLE IF EXISTS hro_mgt_annual_leave_rule_detail;				# ��ٹ��� - �ӱ�
DROP TABLE IF EXISTS hro_mgt_exchange_rate							# ���ʣ�local�һ���Ԫ��

CREATE TABLE hro_mgt_exchange_rate (
	exchangeRateId int(11) NOT NULL AUTO_INCREMENT,					# 
	accountId int(11) NOT NULL,										#	
	corpId int(11) NOT NULL,										# �ͻ�ID
	currencyNameZH varchar(100) NOT NULL,							#
	currencyNameEN varchar(100) DEFAULT NULL,						# ����
	currencyCode varchar(10) NOT NULL,								# ���Ҵ���
	fromUSD double NOT NULL,										# ��Ԫ
	toLocal double NOT NULL,										# ����
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
  	branchPrefer tinyint(4) DEFAULT NULL,							# ��֯��ʾ��ʽ  1���ܹ�ͼ��ʽ 2���б���ʾ
  	isSumSubBranchHC tinyint(4)DEFAULT NULL,						# �Ƿ�ͳ���Ӳ�������(0:��ͳ�ƣ�1��ͳ��)
  	positionPrefer tinyint(4) DEFAULT NULL,							# ְλ��ʾ��ʽ  1��������ʾ2��������ʾ
  	language tinyint(4) DEFAULT NULL,
  	useBrowserLanguage tinyint(4) DEFAULT NULL,
  	dateFormat tinyint(4) DEFAULT NULL,
  	timeFormat tinyint(4) DEFAULT NULL,
  	pageStyle tinyint(4) DEFAULT NULL,
  	smsGetway int(11) DEFAULT NULL,
  	orderBindContract tinyint(4) DEFAULT NULL,						# �����󶨺�ͬ����/�񣩣������Ƿ����󶨺�ͬ
  	logoFile varchar(1000) DEFAULT NULL,							# ��˾�̱���·��	
  	logoFileSize INTEGER DEFAULT NULL,								# ��˾�̱��ļ���С
  	sbGenerateCondition tinyint(4) DEFAULT NULL,					# �籣�걨������1:������׼��2:������Ч��������
  	cbGenerateCondition tinyint(4) DEFAULT NULL,					# �̱��깺������1:������׼��2:������Ч��������
  	settlementCondition tinyint(4) DEFAULT NULL,					# ���㴦��������1:������׼��2:������Ч��������
  	sbGenerateConditionSC tinyint(4) DEFAULT NULL,					# �籣�걨������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
  	cbGenerateConditionSC tinyint(4) DEFAULT NULL,					# �̱��깺������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
  	settlementConditionSC tinyint(4) DEFAULT NULL,					# ���㴦��������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
  	accuracy tinyint(4) DEFAULT NULL,								# ���ȣ�ȡ��������һλ��������λ��
	round tinyint(4) DEFAULT NULL,									# ȡ�ᣨ�������룬��ȡ�����Ͻ�λ��
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
  	corpId int(11) NOT NULL,										# �ͻ�ID
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
  	corpId int(11) NOT NULL,										# �ͻ�ID
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
  	corpId int(11) NOT NULL,										# �ͻ�ID
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
  	corpId int(11) NOT NULL,										# �ͻ�ID
  	positionGradeId int(11) NOT NULL,								# ְ��
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
  	corpId int(11) NOT NULL,										# �ͻ�ID
  	gradeNameZH varchar(200) DEFAULT NULL,
  	gradeNameEN varchar(200) DEFAULT NULL,
  	weight smallint(8) DEFAULT NULL,								# Ȩ��
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
	itemId int(11) NOT NULL AUTO_INCREMENT,							# ��ĿID
	accountId int(11) NOT NULL,										# �˻�ID��AccountIdΪ��1������ϵͳ�����ġ�
	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	itemCap double DEFAULT NULL,									# ��Ŀ���ޣ���ֹ��Ŀ��������
	itemFloor double DEFAULT NULL,									# ��Ŀ���ޣ���ֹ��Ŀ�����С��
	sequence smallint(8) DEFAULT NULL,								# ��Ŀ˳��
	personalTax tinyint(4) DEFAULT NULL,							# �����˰����/�����磬�����Ȳ���Ҫ�����˰��
	companyTax tinyint(4) DEFAULT NULL,								# ����Ӫҵ˰����/�����磬���մ�����Ŀ��ҵ����Ҫ����Ӫҵ˰��
	calculateType tinyint(4) DEFAULT NULL,							# �������ͣ�ֱ�Ӽ��� - ��ϵͳ�л�������㡢������㡢���� - ���ȣ�
	itemType tinyint(4) DEFAULT NULL,								# ��Ŀ���ͣ����ʡ����������𡢼Ӱࡢ�������ݼ١��籣���̱�������ѡ����ա��������ɱ���������
	billRatePersonal double DEFAULT NULL,							# ���ʣ��������룩
	billRateCompany double DEFAULT NULL,							# ���ʣ���˾Ӫ�գ�
	costRatePersonal double DEFAULT NULL,							# ���ʣ�����֧����
	costRateCompany double DEFAULT NULL,							# ���ʣ���˾�ɱ���
	billFixPersonal double DEFAULT NULL,							# �̶��𣨸������룩
	billFixCompany double DEFAULT NULL,								# �̶��𣨹�˾Ӫ�գ�
	costFixPersonal double DEFAULT NULL,							# �̶��𣨸���֧����
	costFixCompany double DEFAULT NULL,								# �̶��𣨹�˾�ɱ���
	personalTaxAgent tinyint(4) DEFAULT NULL,						# ���۸�˰����/�񣬸��ˣ�������Bill��100%��������Cost��0%��
	description varchar(1000) DEFAULT NULL,							# ��Ŀ����
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
	itemGroupId int(11) NOT NULL AUTO_INCREMENT,					# ��Ŀ����ID
	accountId int(11) NOT NULL,										# �˻�ID��AccountIdΪ��1������ϵͳ�����ġ�
	corpId int(100) default null,									# ��ҵID������Inhouseʹ��
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ�������ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ�������ƣ�Ӣ�ģ�
	itemGroupType tinyint(4) DEFAULT NULL,							# ��Ŀ�������ͣ�1:�ݼٿۿ2:н�ʽ�����飻5:������
	bindItemId int(11) DEFAULT NULL,								# �󶨿�ĿID
	listMerge tinyint(4) DEFAULT NULL,								# �Ƿ���Ҫ���տ�Ŀ����ϲ����б�
	reportMerge tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ���տ�Ŀ����ϲ�������
	invoiceMerge tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ���տ�Ŀ����ϲ�����Ʊ��
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
	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��Ŀ - �������ID
	itemGroupId int(11) NOT NULL,									# ��Ŀ����ID
	itemId int(11) NOT NULL,										# ��ĿID
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
  	mappingId int(11) NOT NULL AUTO_INCREMENT,						# ��Ŀӳ��ID
  	accountId int(11) NOT NULL,										# �˻�ID
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	itemId int(11) NOT NULL,										# ��ĿID
	entityId int(11) NOT NULL,										# ����ʵ��ID
	businessTypeId int(11) NOT NULL,								# ҵ������ID
  	saleDebit varchar(200) DEFAULT NULL,							# ���� - ��
  	saleDebitBranch varchar(50) DEFAULT NULL,						# ���� - �裨�����ţ�����/��
  	saleCredit varchar(200) DEFAULT NULL,							# ���� - ��
  	saleCreditBranch varchar(50) DEFAULT NULL,						# ���� - ���������ţ�����/��
  	costDebit varchar(200) DEFAULT NULL,							# �ɱ� - ��
  	costDebitBranch varchar(50) DEFAULT NULL,						# �ɱ� - �裨�����ţ�����/��
  	costCredit varchar(200) DEFAULT NULL,							# �ɱ� - ��
  	costCreditBranch varchar(50) DEFAULT NULL,						# �ɱ� - ���������ţ�����/��
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
  	taxId int(11) NOT NULL AUTO_INCREMENT,							# ˰��ID
  	accountId int(11) NOT NULL,										# �˻�ID
	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	entityId int(11) NOT NULL,										# ����ʵ��ID
	businessTypeId int(11) NOT NULL,								# ҵ������ID
	nameZH varchar(200) NOT NULL,									# ˰�����ƣ����ģ�
  	nameEN varchar(200) NOT NULL,									# ˰�����ƣ�Ӣ�ģ�	
  	saleTax double DEFAULT NULL,									# ����˰��
  	costTax double DEFAULT NULL,									# �ɱ�˰��
  	actualTax double DEFAULT NULL,									# ʵ��˰��
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
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# �籣��������ID�����ճ��кͻ������岻ͬ���籣����
  	accountId int(11) NOT NULL,										# �˻�ID
  	corpId int(11) NOT NULL,										# �ͻ�ID
  	sysHeaderId int(11) NOT NULL,									# �籣ID��Super�˻�����
  	nameZH varchar(200) NOT NULL,									# �籣�������ƣ����ģ�
  	nameEN varchar(200) NOT NULL,									# �籣�������ƣ�Ӣ�ģ�	
  	sbType tinyint(4) DEFAULT 2,									# �籣�������� 0:��ѡ��##1:�籣##2:������##3:�ۺ�##4:����
  	attribute tinyint(4) DEFAULT NULL,								# �籣�����£����£����£����£��������ѡ�����մӱ�ɸѡ��
  	effective tinyint(4) DEFAULT NULL,								# �籣�����£����£��������ѡ�����մӱ�ɸѡ��
  	startDateLimit varchar(25) DEFAULT NULL,						# �걨��ʼʱ�䣨Ĭ��ΪSuper�������籣�걨��ʼʱ�䣬��ͬ�˻�������ǰ�޸����ڣ������ܿ��£�
  	endDateLimit varchar(25) DEFAULT NULL,							# �걨��ֹʱ�䣨Ĭ��ΪSuper�������籣�걨��ֹʱ�䣬��ͬ�˻�������ǰ�޸����ڣ������ܿ��£�
  	startRule tinyint(4) DEFAULT NULL,								# ��ʼ���籣���ɹ��򣨰����� - 15���Ժ���ְ����籣�������������� - ��1�������籣������Ȼ������ - ����5��������籣���������ѡ�����մӱ�ɸѡ��
  	startRuleRemark varchar(25) DEFAULT NULL,						# ��ʼ���籣���ɹ���ע���������ѡ�����մӱ�ɸѡ
  	endRule tinyint(4) DEFAULT NULL,								# �������籣���ɹ����������ѡ�����մӱ�ɸѡ
  	endRuleRemark varchar(25) DEFAULT NULL,							# �������籣���ɹ���ע���������ѡ�����մӱ�ɸѡ
  	attachment varchar(1000) DEFAULT NULL,							# �籣��������
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
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# �籣�����ӱ�ID�����ճ���ѡ��ͬ�����֣�������ȷ������
  	headerId int(11) NOT NULL,										# �籣��������ID
  	sysDetailId int(11) NOT NULL,									# �籣�ӱ�ID��Super�˻�����
  	itemId int(11) NOT NULL,										# ��ĿID��ֻ�ܴ�Super�˻���������ϸ��ѡ��
  	companyPercent double DEFAULT NULL,								# ��˾���� - Ĭ��ϵͳ������ֵ���ֵ�����ٴθ���
  	personalPercent double DEFAULT NULL,							# ���˱��� - Ĭ��ϵͳ������ֵ���ֵ�����ٴθ���
  	companyFloor double DEFAULT NULL,								# ��˾�������ͣ� - Ĭ��ϵͳ������ֵ�����ٴθ���
  	companyCap double DEFAULT NULL,									# ��˾�������ߣ� - Ĭ��ϵͳ������ֵ�����ٴθ���
  	personalFloor double DEFAULT NULL,								# ���˻������ͣ� - Ĭ��ϵͳ������ֵ�����ٴθ���
  	personalCap double DEFAULT NULL,								# ���˻������ߣ� - Ĭ��ϵͳ������ֵ�����ٴθ���
  	companyFixAmount double DEFAULT NULL,							# ��˾�̶��� - Ĭ��ϵͳ������ֵ�����ٴθ���
  	personalFixAmount double DEFAULT NULL,							# ���˹̶��� - Ĭ��ϵͳ������ֵ�����ٴθ���
  	attribute tinyint(4) DEFAULT NULL,								# �籣�����£����£����£����£��������ѡ�����մӱ�ɸѡ��
  	effective tinyint(4) DEFAULT NULL,								# �籣�����£����£��������ѡ�����մӱ�ɸѡ��
  	startDateLimit varchar(25) DEFAULT NULL,						# �걨��ʼʱ�䣨Ĭ��ΪSuper�������籣�걨��ʼʱ�䣬��ͬ�˻�������ǰ�޸����ڣ������ܿ��£���дһ�����еļ��ţ��ӱ�ѡ��̳��������ã�
  	endDateLimit varchar(25) DEFAULT NULL,							# �걨��ֹʱ�䣨Ĭ��ΪSuper�������籣�걨��ֹʱ�䣬��ͬ�˻�������ǰ�޸����ڣ������ܿ��£���дһ�����еļ��ţ��ӱ�ѡ��̳��������ã�
  	startRule tinyint(4) DEFAULT NULL,								# ��ʼ���籣���ɹ��򣨰����� - 15���Ժ���ְ����籣�������������� - ��1�������籣������Ȼ������ - ����5��������籣���������ѡ�����մӱ�ɸѡ��
  	startRuleRemark varchar(25) DEFAULT NULL,						# ��ʼ���籣���ɹ���ע���������ѡ�����մӱ�ɸѡ
  	endRule tinyint(4) DEFAULT NULL,								# �������籣���ɹ����������ѡ�����մӱ�ɸѡ
  	endRuleRemark varchar(25) DEFAULT NULL,							# �������籣���ɹ���ע���������ѡ�����մӱ�ɸѡ
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
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# �̱���������ID
  	accountId int(11) NOT NULL,										# �˻�ID
  	corpId int(11) NOT NULL,										# �ͻ�ID
  	nameZH varchar(200) NOT NULL,									# �̱��������ƣ����ģ�
  	nameEN varchar(200) NOT NULL,									# �̱��������ƣ�Ӣ�ģ�	
  	validFrom datetime DEFAULT NULL,								# ��Ч��ʼʱ��
  	validEnd datetime DEFAULT NULL,									# ��Ч��ֹʱ��
  	attachment varchar(1000) DEFAULT NULL,							# �̱���������
  	calculateType tinyint(4) NOT NULL,								# ���÷�ʽ�����£����죩
  	accuracy tinyint(4) DEFAULT NULL,								# ��Ϊʹ��Ĭ�ϣ�����С��λ��ȡ��������һλ��������λ���������ѡ�����մӱ�ɸѡ��
  	round tinyint(4) DEFAULT NULL,									# ��Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ���������ѡ�����մӱ�ɸѡ��
  	freeShortOfMonth tinyint(4) DEFAULT NULL,						# ��ȫ����ѣ���/�� - �����ְ����ְ�������������chargeFullMonth����ͬʱѡ���ǡ�
	chargeFullMonth	tinyint(4) DEFAULT NULL,						# ��ȫ�¼Ʒѣ���/�� - �����ְ����ְ�������������freeShortOfMonth����ͬʱѡ���ǡ�
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
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# �̱������ӱ�ID��һ���̱����������ж���̱���Ŀ
  	headerId int(11) NOT NULL,										# �̱���������ID
  	itemId int(11) NOT NULL,										# ��ĿID��������Ϊ�̱��Ŀ�Ŀ��ѡ��AccountΪ��1�ġ��Լ��Լ��ģ�
  	purchaseCost double DEFAULT NULL,								# �ɹ��ɱ�����ָ�̱��ӱ��չ�˾���õ����ռ۸�
  	salesCost double DEFAULT NULL,									# ���۳ɱ�����ָ�ɹ��ɱ����Ϲ�˾�ڲ��Ĺ���ɱ�
  	salesPrice double DEFAULT NULL,									# ���ۼ۸�
  	calculateType tinyint(4) NOT NULL,								# ���÷�ʽ�����£����죩
  	accuracy tinyint(4) DEFAULT NULL,								# ��Ϊʹ��Ĭ�ϣ�����С��λ��ȡ��������һλ��������λ���������ѡ�����մӱ�ɸѡ��
  	round tinyint(4) DEFAULT NULL,									# ��Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ���������ѡ�����մӱ�ɸѡ��
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
	certificationId int(11) NOT NULL AUTO_INCREMENT,				# ֤�� - ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
  	nameZH varchar(200) DEFAULT NULL,								# ֤�����ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ֤�����ƣ�Ӣ�ģ�
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
	membershipId int(11) NOT NULL AUTO_INCREMENT,					# ���Id
	accountId int(11) NOT NULL,										# �˻�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
  	nameZH varchar(200) DEFAULT NULL,								# ������ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ������ƣ�Ӣ�ģ�
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
  	templateId int(11) NOT NULL AUTO_INCREMENT,						# �����ͬģ��Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	corpId int(11) DEFAULT NULL,									# �ͻ��˻�Id
  	nameZH varchar(200) DEFAULT NULL,								# ģ��������
  	nameEN varchar(200) DEFAULT NULL,								# ģ��Ӣ����
  	entityIds varchar(1000) DEFAULT NULL,							# ����ʵ�壬�ɶ�ѡ��Jason��ʽ���룩
  	businessTypeIds varchar(1000) DEFAULT NULL,						# ҵ�����ͣ��ɶ�ѡ��Jason��ʽ���룩
  	content text DEFAULT NULL,										# ģ�����ݣ������������${value}
  	contentType tinyint(4) DEFAULT NULL,							# ģ���������ͣ�1:Text��2:HTML��Ĭ��ΪText��
  	description varchar(1000) DEFAULT NULL,							# ģ������
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
  	templateId int(11) NOT NULL AUTO_INCREMENT,						# �Ͷ���ͬģ��Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	corpId int(11) DEFAULT NULL,									# �ͻ��˻�Id
  	contractTypeId int(11) NOT NULL,								# ��ͬ���ͣ������� - ���� - ��ͬ���ͣ�
  	nameZH varchar(200) DEFAULT NULL,								# ģ��������
  	nameEN varchar(200) DEFAULT NULL,								# ģ��Ӣ����
  	entityIds varchar(1000) DEFAULT NULL,							# ����ʵ�壬�ɶ�ѡ��Jason��ʽ���룩
  	businessTypeIds varchar(1000) DEFAULT NULL,						# ҵ�����ͣ��ɶ�ѡ��Jason��ʽ���룩
  	clientIds varchar(1000) DEFAULT NULL,						    # �ͻ����ɶ�ѡ��Jason��ʽ���룩
  	content text DEFAULT NULL,										# ģ�����ݣ������������${value}
  	contentType tinyint(4) DEFAULT NULL,							# ģ���������ͣ�1:Text��2:HTML��Ĭ��ΪText��
  	description varchar(1000) DEFAULT NULL,							# ģ������
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
	typeId int(11) NOT NULL AUTO_INCREMENT,							# ��ҵ���Id
	accountId int(11) NOT NULL,										# �˻�Id
  	corpId int(11)	default null,									# ��ҵID������Inhouseʹ��
	nameZH varchar(200) DEFAULT NULL,								# ��ҵ������ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ��ҵ������ƣ�Ӣ�ģ�
  	description varchar(1000) DEFAULT NULL,							# ������Ϣ
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
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# ��������Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	corpId int(11) NOT NULL,										# �ͻ�Id
  	nameZH varchar(200) NOT NULL,									# �������ƣ����ģ�
  	nameEN varchar(200) NOT NULL,									# �������ƣ�Ӣ�ģ�
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
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# �����ӱ�Id
  	headerId int(11) NOT NULL,										# ��������Id
  	nameZH varchar(200) NOT NULL,									# �������ƣ����ģ�
  	nameEN varchar(200) NOT NULL,									# �������ƣ�Ӣ�ģ�
  	day datetime NOT NULL,											# ����
  	dayType tinyint(4) NOT NULL,									# �������ͣ�1:�����գ�2:��Ϣ�գ�3:�ڼ��գ�
  	changeDay datetime DEFAULT NULL,								# �������ڣ�����������ѡ�ߡ������ա������ʾ
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
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# �Ű�����Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	corpId int(11) NOT NULL,										# �ͻ�Id
  	nameZH varchar(200) NOT NULL,									# �Ű����ƣ����ģ�
  	nameEN varchar(200) NOT NULL,									# �Ű����ƣ�Ӣ�ģ�
  	shiftType tinyint(4) NOT NULL,									# �Ű����ͣ�1:���ܣ�2:���죬3:�Զ��壩��������Զ���֮�����Ϣ�պͽڼ��ղο�����
  	shiftIndex tinyint(4) NOT NULL,									# �Ű�Ƶ�ʣ����磺ÿ�����ܻ�ÿ�����죩	
  	startDate datetime DEFAULT NULL,								# �Ű࿪ʼʱ�䣨������õ���
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
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# �Ű�ӱ�Id
  	headerId int(11) NOT NULL,										# �Ű�����Id
  	nameZH varchar(200) DEFAULT NULL,								# �������ƣ����ģ������ܺͰ����Զ����ɣ��Զ��岻ʹ��
  	nameEN varchar(200) DEFAULT NULL,								# �������ƣ�Ӣ�ģ�
  	dayIndex tinyint(4) DEFAULT NULL,								# �������У���1��ʼ�ţ�
  	shiftDay datetime DEFAULT NULL,									# �Ű����ڣ��Զ���ʹ�ã�������ʹ��
  	shiftPeriod varchar(500) DEFAULT NULL,							# ����ʱ��Σ�1:00:00 - 00:30��2:00:30 - 01:00��...��������Ĭ����ʾ����7��������7������䣬����ȫ��չ����Jason��ʽ�洢
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
  	exceptionId int(11) NOT NULL AUTO_INCREMENT,					# �Ű�����Id
  	headerId int(11) NOT NULL,										# �Ű�����Id
  	nameZH varchar(200) DEFAULT NULL,								# �������ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# �������ƣ�Ӣ�ģ�
  	dayType	tinyint(4) DEFAULT NULL,								# �����������ͣ�1:�����գ�2:��Ϣ�գ�3:�ڼ��գ�
  	exceptionDay datetime DEFAULT NULL,								# �������ڣ��Զ���ʹ�ã���ȷ��ĳһ�죩
  	exceptionPeriod varchar(500) DEFAULT NULL,						# ����ʱ��Σ�1:00:00 - 00:30��2:00:30 - 01:00��...��
  	exceptionType tinyint(4) DEFAULT NULL,						    # �������ͣ�0����ѡ��1����٣�2���Ӱࣩ
  	itemId int(11) DEFAULT NULL,									# �����Ŀ
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
	bankId int(11) NOT NULL AUTO_INCREMENT,							# ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	cityId int(11) DEFAULT NULL,									# ����ID
  	nameZH varchar(200) DEFAULT NULL,								# �������ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# �������ƣ�Ӣ�ģ�
  	addressZH varchar(200) DEFAULT NULL,							# ���ĵ�ַ
	addressEN varchar(200) DEFAULT NULL,							# Ӣ�ĵ�ַ
	telephone varchar(50) DEFAULT NULL,								# �绰
	fax varchar(50) DEFAULT NULL,									# ����
	postcode varchar(50) DEFAULT NULL,								# �ʱ�
	email varchar(200) DEFAULT NULL,								# �ʼ���ַ
	website varchar(200) DEFAULT NULL,								# ��ַ
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
	settingId int(11) NOT NULL AUTO_INCREMENT,						# ����ID
	accountId int(11) NOT NULL,										# �˻�ID
	corpId int(11) DEFAULT NULL,									# �ͻ�ID
	userId int(11) DEFAULT NULL,									# �û�ID
	baseInfo tinyint(4) DEFAULT NULL,								# ������Ϣ
	baseInfoRank tinyint(4) DEFAULT NULL,							# ������Ϣ���
	message tinyint(4) DEFAULT NULL,								# ֪ͨ
	messageRank tinyint(4) DEFAULT NULL,							# ֪ͨ���
	dataView tinyint(4) DEFAULT NULL,								# ���ݸ���
	dataViewRank tinyint(4) DEFAULT NULL,							# ���ݸ������
	clientContract tinyint(4) DEFAULT NULL,							# �����ͬ
	clientContractRank tinyint(4) DEFAULT NULL,						# �����ͬ���
	orders tinyint(4) DEFAULT NULL,									# ����
	ordersRank tinyint(4) DEFAULT NULL,								# �������
	contractService tinyint(4) DEFAULT NULL,						# ������Ϣ
	contractServiceRank tinyint(4) DEFAULT NULL,					# ������Ϣ���
	attendance tinyint(4) DEFAULT NULL,								# ����
	attendanceRank tinyint(4) DEFAULT NULL,							# �������
	sb tinyint(4) DEFAULT NULL,										# �籣
	sbRank tinyint(4) DEFAULT NULL,									# �籣���	
	cb tinyint(4) DEFAULT NULL,										# �̱�
	cbRank tinyint(4) DEFAULT NULL,									# �̱����	
	settlement tinyint(4) DEFAULT NULL,								# ����
	settlementRank tinyint(4) DEFAULT NULL,							# �������
	payment tinyint(4) DEFAULT NULL,								# н��
	paymentRank tinyint(4) DEFAULT NULL,							# н�����
	income tinyint(4) DEFAULT NULL,									# �տ�
	incomeRank tinyint(4) DEFAULT NULL,								# �տ����
	employeeChange tinyint(4) DEFAULT NULL,							# ��Ա���
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
	headerId int(11) NOT NULL AUTO_INCREMENT,						# ��ٹ�������ID
	accountId int(11) NOT NULL,										# �˻�ID
	corpId int(11) DEFAULT NULL,									# �ͻ�ID
	nameZH varchar(200) DEFAULT NULL,								# ���ƣ����ģ�������
  	nameEN varchar(200) DEFAULT NULL,								# ���ƣ�Ӣ�ģ�
  	divideType tinyint(4) DEFAULT NULL,								# ���㷽ʽ
  	baseOn tinyint(4) DEFAULT NULL,									# ���ڣ����1:��˾���ڣ�2:��ʼ�������ڣ�
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
	detailId int(11) NOT NULL AUTO_INCREMENT,						# ��ٹ���ӱ�ID
  	headerId int(11) NOT NULL,										# ��ٹ�������ID
  	seniority int(4) DEFAULT NULL,									# ������X��
  	positionGradeId int(11)  DEFAULT NULL,							# ��Ӧְ��ID
  	legalHours double(8,1) DEFAULT NULL,							# ����Сʱ��
  	benefitHours double(8,1)DEFAULT NULL,							# ����Сʱ��
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
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# ���ٹ�������ID
  	accountId int(11) NOT NULL,										# �˻�ID
	corpId int(11) DEFAULT NULL,									# �ͻ�ID
  	nameZH varchar(200) DEFAULT NULL,								# ���ƣ����ģ�������
  	nameEN varchar(200) DEFAULT NULL,								# ���ƣ�Ӣ�ģ�
  	itemId int(11) NOT NULL,										# ��ĿID�����ͬһClient�����ظ������ClientΪ�գ�ͬһAccount�����ظ���
  	baseOn tinyint(4) DEFAULT NULL,									# ���ڣ����1:��˾���ڣ�2:��ʼ�������ڣ�
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
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# ���ٹ��ʴӱ�ID
  	headerId int(11) NOT NULL,										# ���ٹ�������ID
  	rangeFrom double DEFAULT NULL,									# ������������ʼ��������ѡ�0-120��
  	rangeTo double DEFAULT NULL,									# ����������������������ѡ�0-120����������ڻ���ڿ�ʼ
  	percentage double DEFAULT NULL,									# ��н�������ٷֱȣ�����0-100��
  	fix double DEFAULT NULL,										# �̶���
  	deduct double DEFAULT NULL,										# �̶��ۿ�
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
  	templateId int(11) NOT NULL AUTO_INCREMENT,						# �˹���ģ��Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	corpId int(11) DEFAULT NULL,									# corpId
  	templateType tinyint(4) DEFAULT NULL,                           # (1:�˹��� 2:����֤�� 3:��ְ֤�� 4:��ְ֤�� 5:����֤��)
  	nameZH varchar(200) DEFAULT NULL,								# ģ��������
  	nameEN varchar(200) DEFAULT NULL,								# ģ��Ӣ����
  	content text DEFAULT NULL,										# ģ�����ݣ������������${value}
  	contentType tinyint(4) DEFAULT NULL,							# ģ���������ͣ�1:Text��2:HTML��Ĭ��ΪText��
  	description varchar(1000) DEFAULT NULL,							# ģ������
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
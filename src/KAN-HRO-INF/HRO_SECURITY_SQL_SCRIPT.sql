use HRO;

DROP TABLE IF EXISTS hro_sec_user;									# ϵͳ�û����빫˾Ա����Ӧ������˾Ա����һ������ϵͳ�û�
DROP TABLE IF EXISTS hro_sec_staff;									# ��˾�ڲ�ְԱ��Ϣ��������
DROP TABLE IF EXISTS hro_sec_branch;								# ���ţ�ֻ��Position�������й�ϵ
DROP TABLE IF EXISTS hro_sec_location;								# �칫�ص㡢��֧����������ͨѶ��Ϣ��
DROP TABLE IF EXISTS hro_sec_entity;	                            # ����ʵ��
DROP TABLE IF EXISTS hro_sec_business_type                          # ҵ������
DROP TABLE IF EXISTS hro_sec_module_right_relation;					# ȫ�����ݷ���Ȩ�� - Right
DROP TABLE IF EXISTS hro_sec_module_rule_relation;					# ȫ�����ݷ���Ȩ�� - Rule
DROP TABLE IF EXISTS hro_sec_position;								# ְλ����˾�ڲ����ǹ�Ա��
DROP TABLE IF EXISTS hro_sec_position_staff_relation;				# ��˾�ڲ�Ա����ְλ�Ĺ�ϵ��һ��Ա�����Զ�Ӧ���ְλ
DROP TABLE IF EXISTS hro_sec_position_grade;						# ְ������������Ϊ�ڵ�
DROP TABLE IF EXISTS hro_sec_position_grade_currency;				# ְ����Ӧ�Ĺ��ʲο�ˮƽ
DROP TABLE IF EXISTS hro_sec_position_group_relation;				# ְλ��Ⱥ��Ĺ�ϵ��һ��ְλ���Զ�Ӧ���Ⱥ��
DROP TABLE IF EXISTS hro_sec_position_module_right_relation;		# һ��ְλ��Ӧ��ͬģ��Ĳ�ͬ����Ȩ��
DROP TABLE IF EXISTS hro_sec_position_column_right_relation;		# һ��ְλ��Ӧ��ͬ�ֶεĲ�ͬ����Ȩ��
DROP TABLE IF EXISTS hro_sec_position_module_rule_relation;			# ְλ���ݷ���Ȩ��
DROP TABLE IF EXISTS hro_sec_group;									# ְλȺ��
DROP TABLE IF EXISTS hro_sec_group_module_right_relation;			# һ��Ⱥ���Ӧ��ͬģ��Ĳ�ͬ����Ȩ��
DROP TABLE IF EXISTS hro_sec_group_column_right_relation;			# һ��Ⱥ���Ӧ��ͬ�ֶεĲ�ͬ����Ȩ��
DROP TABLE IF EXISTS hro_sec_group_module_rule_relation;			# ְλȺ�����ݷ���Ȩ��
DROP TABLE IF EXISTS hro_sec_org_shoot								# ��֯����ͼ��ʷ����

CREATE TABLE hro_sec_user (
  	userId int(11) NOT NULL AUTO_INCREMENT,							# �û�ID
  	accountId int(11) NOT NULL,										# �˻�ID
  	corpId int(11)  DEFAULT NULL,									# InHouse�˻�ID
  	userIds varchar(1000) DEFAULT NULL,								# ����InHouse�˻�ID
  	staffId int(11) NOT NULL,										# Ա��ID
  	username varchar(50) DEFAULT NULL,								# �û���
  	password varchar(50) DEFAULT NULL,								# ����
  	bindIP varchar(50) NULL,										# ��IP��ַ�������ֻ��ָ��IP��ַ����
  	lastLogin datetime DEFAULT NULL,								# ����¼ʱ��
  	lastLoginIP varchar(50) NULL,									# ����¼IP��ַ
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
	staffId int(11) NOT NULL AUTO_INCREMENT,						# Ա��ID
	accountId int(11) NOT NULL,										# �˻�ID
	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
	staffNo varchar(50) DEFAULT NULL,								# Ա�����
	salutation tinyint(4) DEFAULT NULL,								# �ƺ����������Ա�
  	nameZH varchar(200) DEFAULT NULL,								# ������
  	nameEN varchar(200) DEFAULT NULL,								# Ӣ����
  	birthday datetime DEFAULT NULL,									# ��������
  	bizPhone varchar(50) DEFAULT NULL,								# �����绰
  	bizExt varchar(25) DEFAULT NULL,								# �����绰�ֻ��ţ���ʱ���ã�
  	personalPhone varchar(50) DEFAULT NULL,							# ��ͥ�绰
  	bizMobile varchar(50) DEFAULT NULL,								# ��˾�ֻ�
  	personalMobile varchar(50) DEFAULT NULL,						# ˽���ֻ�
  	otherPhone varchar(50) DEFAULT NULL,							# �����绰
  	fax varchar(50) DEFAULT NULL,									# ����
  	bizEmail varchar(200) DEFAULT NULL,								# ��˾����
  	personalEmail varchar(200) DEFAULT NULL,						# ˽������
	certificateType tinyint(4) DEFAULT NULL,						# ֤������
  	certificateNumber varchar(50) DEFAULT NULL,						# ֤������
  	maritalStatus tinyint(4) DEFAULT NULL,							# ����״��
  	registrationHometown varchar(50) DEFAULT NULL,					# �������У����ᣩ
  	registrationAddress varchar(200) DEFAULT NULL,					# ������ַ
  	personalAddress varchar(200) DEFAULT NULL,						# ��ͥ��ַ
  	personalPostcode varchar(50) DEFAULT NULL,						# ��ͥ��ַ�ʱ�
  	description varchar(1000) DEFAULT NULL,							# ��������
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# Ա��״̬��1:��ְ��2:��ļ��3:��ְ��4:��ѡ
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
	branchId int(11) NOT NULL AUTO_INCREMENT,						# ����ID
	accountId int(11) NOT NULL,										# �˻�ID
	corpId  int(11) DEFAULT NULL,
	parentBranchId int(11) DEFAULT NULL,							
	entityId int(11) DEFAULT NULL,									#����ʵ��
	businessTypeId int(11) DEFAULT NULL,							#ҵ������
	branchCode varchar(12) NOT NULL,								# ���ű�ţ�0-6λ�ַ�
	nameZH varchar(200) DEFAULT NULL,								# �����������ƣ���ƣ�
	nameEN varchar(200) DEFAULT NULL,								# ����Ӣ�����ƣ���ƣ�
	description varchar(1000) DEFAULT NULL,							# ��������
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
	locationId int(11) NOT NULL AUTO_INCREMENT,						# ��ַID��ͨ����ָһ���칫�ص㣩��Position��Location����
	accountId int(11) NOT NULL,										# �˻�ID
	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
	cityId int(11) NOT NULL,										# ����ID
	nameZH varchar(200) DEFAULT NULL,								# ��������
	nameEN varchar(200) DEFAULT NULL,								# Ӣ������
	addressZH varchar(200) DEFAULT NULL,							# ���ĵ�ַ
	addressEN varchar(200) DEFAULT NULL,							# Ӣ�ĵ�ַ
	telephone varchar(50) DEFAULT NULL,								# �绰
	fax varchar(50) DEFAULT NULL,									# ����
	postcode varchar(50) DEFAULT NULL,								# �ʱ�
	email varchar(200) DEFAULT NULL,								# �ʼ���ַ
	website varchar(200) DEFAULT NULL,								# ��ַ
	description varchar(1000) DEFAULT NULL,							# ����
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
	businessTypeId int(11) NOT NULL AUTO_INCREMENT,					# ҵ������ID
	accountId int(11) NOT NULL,										# �˻�ID����0����ʾ�˻�����
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
	nameZH varchar(200) DEFAULT NULL,								# ҵ���������ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ҵ���������ƣ�Ӣ�ģ�
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
	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID����ģ��Ĺ��ܷ���Ȩ�ޣ��˻�ȫ������
	accountId int(11) NOT NULL,										# �˻�ID
  	moduleId int(11) NOT NULL,										# ģ��ID
  	rightId int(11) NOT NULL,										# ���ܹ���ID
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
	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID����ģ������ݷ���Ȩ�ޣ��˻�ȫ������
	accountId int(11) NOT NULL,										# �˻�ID
  	moduleId int(11) NOT NULL,										# ģ��ID
  	ruleId int(11) NOT NULL,										# ����Ȩ��ID
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
  	positionId int(11) NOT NULL AUTO_INCREMENT,						# ְλID
  	accountId int(11) NOT NULL,										# �˻�ID
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	locationId int(11) NOT NULL,									# ��ַID
  	branchId int(11) NOT NULL,										# ����ID
  	positionGradeId int(11) NOT NULL,								# ְ��
  	titleZH varchar(200) DEFAULT NULL,								# ְλ���ƣ����ģ�
  	titleEN varchar(200) DEFAULT NULL,								# ְλ���ƣ�Ӣ�ģ�
  	description varchar(1000) DEFAULT NULL,							# ְλ������JD��
  	skill varchar(1000) DEFAULT NULL,								# ���輼��
  	note varchar(1000) DEFAULT NULL,								# ����¼
  	attachment varchar(5000) DEFAULT NULL,							# ����
  	parentPositionId int(11) DEFAULT NULL,							# �ϼ�ְλ
  	isVacant tinyint(4) DEFAULT NULL,								# �Ƿ��ǿ�ȱְλ
	isIndependentDisplay tinyint(4) DEFAULT NULL,					# �Ƿ������ʾ
  	needPublish tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����
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
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID
  	positionId int(11) NOT NULL,									# ְλ��Ӧ��ID
  	staffId int(11) NOT NULL,										# �����˻������ID
  	staffType tinyint(4) NOT NULL,									# �����ˣ�1��������ˣ�2��������Ǵ����ˣ������ʼ�ͽ���ʱ�䲻��Ϊ��
  	agentStart datetime DEFAULT NULL,								# �����ڼ俪ʼ
  	agentEnd datetime DEFAULT NULL,									# �����ڼ����
  	description varchar(1000) DEFAULT NULL,							# ������˵��
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
  	positionGradeId int(11) NOT NULL AUTO_INCREMENT,				# ְ��ID
  	accountId int(11) NOT NULL,										# �˻�ID
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	gradeNameZH varchar(200) DEFAULT NULL,							# ְ����������
  	gradeNameEN varchar(200) DEFAULT NULL,							# ְ��Ӣ������
  	weight smallint(8) DEFAULT NULL,								# Ȩ��
  	description varchar(1000) DEFAULT NULL,							# ְ������
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
  	positionGradeId int(11) NOT NULL,								# ְ��ID����Ӧ��ͬ���ֵ�������
  	currencyId varchar(25) NOT NULL,								# ����ID
  	minSalary double DEFAULT NULL,									# н������
  	maxSalary double DEFAULT NULL,									# н������
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
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��һ��Position�������ڶ��Ⱥ��
  	positionId int(11) NOT NULL,									# ְλID
  	groupId int(11) NOT NULL,										# Ⱥ��ID
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
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��һ��ְλ�ܷ�����Щģ�飬������Щģ����ʲô����Ȩ��
	positionId int(11) NOT NULL,									# ְλID 
  	moduleId int(11) NOT NULL,										# ģ��ID
  	rightId int(11) NOT NULL,										# ����Ȩ��ID
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
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��һ��ְλ�ܷ�����Щ�ֶΣ�������Щ�ֶ���ʲô����Ȩ��
	positionId int(11) NOT NULL,									# ְλID
  	columnId int(11) NOT NULL,										# �ֶ�ID
  	rightId int(11) NOT NULL,										# ����Ȩ��ID
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
	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��һ��ְλ�ܷ��ʵ���Щģ�飬������Щģ����ʲô����Ȩ��
	positionId int(11) NOT NULL,									# ְλID
  	moduleId int(11) NOT NULL,										# ģ��ID
  	ruleId int(11) NOT NULL,										# ����Ȩ��ID
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
  	groupId int(11) NOT NULL AUTO_INCREMENT,						# ְλ��ID
  	accountId int(11) NOT NULL,										# �˻�ID������ǡ�1����˵���ǹ���Ⱥ��
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	nameZH varchar(200) DEFAULT NULL,								# ְλ��������
  	nameEN varchar(200) DEFAULT NULL,								# ְλ��Ӣ����
  	hrFunction tinyint(4) DEFAULT NULL,								# HRְ�ܣ�ֻ�����²��Ÿ��У�		
  	description varchar(1000) DEFAULT NULL,							# ְλ������
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
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��һ��Ⱥ���ܷ�����Щ�ֶΣ�������ЩȺ����ʲô����Ȩ��
	groupId int(11) NOT NULL,										# Ⱥ��ID
  	columnId int(11) NOT NULL,										# �ֶ�ID
  	rightId int(11) NOT NULL,										# ����Ȩ��ID
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
  	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��һ��Ⱥ���ܷ�����Щģ�飬������Щģ����ʲô����Ȩ��
	groupId int(11) NOT NULL,										# Ⱥ��ID
  	moduleId int(11) NOT NULL,										# ģ��ID
  	rightId int(11) NOT NULL,										# ����Ȩ��ID
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
	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��һ��Ⱥ���ܷ��ʵ���Щģ�飬������ЩȺ����ʲô����Ȩ��
	groupId int(11) NOT NULL,										# Ⱥ��ID
  	moduleId int(11) NOT NULL,										# ģ��ID
  	ruleId int(11) NOT NULL,										# ����Ȩ��ID
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
	shootId int(11) NOT NULL AUTO_INCREMENT,						# ����ID
	accountId int(11) NOT NULL,										# �˻�ID������ǡ�1����˵���ǹ���Ⱥ��
	corpId int(11) DEFAULT NULL,										# ��ȨID
	nameZH varchar(200)DEFAULT NULL,								# ������		
	nameEN varchar(200)DEFAULT NULL,								# Ӣ����
	shootType tinyint(4) DEFAULT NULL,								# �������� 1������ 2:ְλ
	shootData mediumblob DEFAULT NULL,								# ���ն���������
	description varchar(1000) DEFAULT NULL,							# ����
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
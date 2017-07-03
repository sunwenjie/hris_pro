use HRO;

DROP TABLE IF EXISTS hro_sys_account;								# �˻���Ϣ�������Ϊ����
DROP TABLE IF EXISTS hro_sys_module;								# �������Ϊ�˵�����
DROP TABLE IF EXISTS hro_sys_account_module_relation;				# ��ͬAccount�ܷ��ʵ�ģ��
DROP TABLE IF EXISTS hro_sys_right;									# ���ܷ���Ȩ�ޣ����Ȩ��
DROP TABLE IF EXISTS hro_sys_rule;									# ���ݷ���Ȩ�ޣ���ƹ���
DROP TABLE IF EXISTS hro_sys_workflow;								# ������ģ�飬��ǰϵͳ��֧�ֹ�������ģ��
DROP TABLE IF EXISTS hro_sys_country;								# ���Ҽ�¼���û���������
DROP TABLE IF EXISTS hro_sys_province;								# ʡ�ݼ�¼���û���������
DROP TABLE IF EXISTS hro_sys_city;									# ���м�¼���û���������
DROP TABLE IF EXISTS hro_sys_smsconfig;								# �������ã�����Ӷ�����Ź�Ӧ�̣�ѡ��2-3��ֻ���ͣ����������һ�ҹ�Ӧ�̷��Ͳ��ɹ���ѡ��һ��֧�ֶ��Ž��գ�
DROP TABLE IF EXISTS hro_sys_social_benefit_header;					# �籣�������ճ��д��	
DROP TABLE IF EXISTS hro_sys_social_benefit_detail;					# �籣�ӱ����ճ��еĲ�ͬ���ִ��
DROP TABLE IF EXISTS hro_sys_constant;								# ��̬�������ã�ӳ�䣩
DROP TABLE IF EXISTS hro_sys_income_tax_base;						# ��˰����
DROP TABLE IF EXISTS hro_sys_income_tax_range_header;				# ��˰˰������
DROP TABLE IF EXISTS hro_sys_income_tax_range_detail;				# ��˰˰�ʴӱ�

CREATE TABLE hro_sys_account (
  	accountId int(11) NOT NULL AUTO_INCREMENT,						# �˻���Ϣ����1��Ϊ�����˻�
  	nameCN varchar(50) DEFAULT NULL,								# �˻����ļ�д
  	nameEN varchar(50) DEFAULT NULL,								# �˻�Ӣ�ļ�д
  	entityName varchar(200) DEFAULT NULL,							# �˻���Ӧ��˾���ƣ�������ע�����ƣ�
  	accountType tinyint(4) default null								# �˻�����
  	linkman varchar(100) DEFAULT NULL,								# �˻���ϵ��
  	salutation tinyint(4) DEFAULT NULL,								# �ƺ������Ա�
  	title varchar(200) DEFAULT NULL,								# �˻���ϵ��ְλ
  	department varchar(100) DEFAULT NULL,							# �˻���ϵ�����ڲ���
  	bizPhone varchar(50) DEFAULT NULL,								# �����绰
  	personalPhone varchar(50) DEFAULT NULL,							# ��ͥ�绰
  	bizMobile varchar(50) DEFAULT NULL,								# �����ֻ�
  	personalMobile varchar(50) DEFAULT NULL,						# ˽���ֻ�
  	otherPhone varchar(50) DEFAULT NULL,							# �����绰
  	fax varchar(50) DEFAULT NULL,									# ����
  	bizEmail varchar(200) DEFAULT NULL,								# ��������
  	personalEmail varchar(200) DEFAULT NULL,						# ˽������
  	website varchar(200) DEFAULT NULL,								# ��˾��ַ
  	canAdvBizEmail tinyint(4) DEFAULT NULL,							# ��˾�����ܷ��͹����Ϣ
  	canAdvPersonalEmail tinyint(4) DEFAULT NULL,					# ˽�������ܷ��͹����Ϣ
  	canAdvBizSMS tinyint(4) DEFAULT NULL,							# ��˾�ֻ��ܷ��͹����Ϣ
  	canAdvPersonalSMS tinyint(4) DEFAULT NULL,						# ˽���ֻ��ܷ��͹����Ϣ
  	cityId int(11) DEFAULT NULL,									# �˻���ϵ�����ڳ���
  	address varchar(200) DEFAULT NULL,								# �˻���ϵ�˵�ַ
  	postcode varchar(50) DEFAULT NULL,								# �˻���ϵ���ʱ�
  	bindIP varchar(1000) DEFAULT NULL,								# �˻���IP��ַ
  	imageFile varchar(1000) DEFAULT NULL,							# ��˾������Ƭ���·��
  	description varchar(5000) DEFAULT NULL,							# ��˾����������ҵ����
  	comment varchar(1000) DEFAULT NULL,								# ���ۣ�ƽ̨����ʹ��
  	initialized tinyint(4) DEFAULT NULL,							# �Ƿ��Ѿ���ʼ��
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
  	PRIMARY KEY (accountId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_module (
  	moduleId int(11) NOT NULL AUTO_INCREMENT,						# ģ��ID�����˵�ID��ϵͳ��ʼ��������ԱInsert��ID��̶���
  	moduleName varchar(100) DEFAULT NULL,							# ģ�����ƣ�����CSS��ʶ
  	moduleFlag int(11) DEFAULT NULL,							    # ģ�����ͣ���1���˵� 2:tab��
  	nameZH varchar(200) DEFAULT NULL,								# ģ�����ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ģ�����ƣ�Ӣ�ģ�
  	titleZH varchar(200) DEFAULT NULL,								# ģ���ʶ�����ģ�
  	titleEN varchar(200) DEFAULT NULL,								# ģ���ʶ��Ӣ�ģ�
  	role tinyint(4) DEFAULT NULL,									# ��ɫ�����ֹ���(0)��HR_Service(1) �� In_House(2)
  	property varchar(200) DEFAULT NULL,								# ��Դ�ļ�����
  	moduleType tinyint(4) DEFAULT NULL,								# ģ����𣨿ͻ�����Ӧ�̣���Ա�ȣ�
  	accessAction varchar(200) DEFAULT NULL,							# ģ���Ӧ��Action
  	defaultAction varchar(200) DEFAULT NULL,						# Ĭ��Action
  	listAction varchar(200) DEFAULT NULL,							# �б���Action
  	newAction varchar(200) DEFAULT NULL,							# �½�����Action
  	toNewAction varchar(200) DEFAULT NULL,							# ��ת���½�����Action
  	modifyAction varchar(200) DEFAULT NULL,							# �޸Ĺ���Action
  	toModifyAction varchar(200) DEFAULT NULL,						# ��ת���޸Ĺ���Action
  	deleteAction varchar(200) DEFAULT NULL,							# ɾ������Action						
  	deletesAction varchar(200) DEFAULT NULL,						# ɾ���б���Action
  	parentModuleId int(11) DEFAULT NULL,							# �����ܽڵ�ID
	levelOneModuleName varchar(100) DEFAULT NULL,					# һ������ģ�����ƣ�����CSS��ʶ
	levelTwoModuleName varchar(100) DEFAULT NULL, 					# ��������ģ�����ƣ�����CSS��ʶ
	levelThreeModuleName varchar(100) DEFAULT NULL, 				# ��������ģ�����ƣ�����CSS��ʶ
	moduleIndex	smallint(8) DEFAULT NULL,							# ģ��˳��
	rightIds varchar(200) DEFAULT NULL,								# ģ��Ȩ�� - ��ģ������Щ����Ȩ�ޣ�Right Table�ж�ѡ��Jason��ʽ�洢��
	ruleIds varchar(200) DEFAULT NULL,								# ģ����� - ��ģ������Щ��������Right Table�ж�ѡ��Jason��ʽ�洢��
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
  	PRIMARY KEY (moduleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_account_module_relation (	
	relationId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵID��Super���ã���ͬ�˻��ܷ��ʵ�ģ��
	accountId int(11) NOT NULL,										# �˻�ID
  	moduleId int(11) NOT NULL,										# ģ��ID
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

CREATE TABLE hro_sys_right (						
	/* 
	 * �½���New�� - ģ����ֶ�ʹ�ã�
	 * �鿴��View�� - ģ����ֶ�ʹ�ã�
	 * �޸ģ�Modify�� - ģ����ֶ�ʹ�ã�
	 * ɾ����Delete�� - ģ��ʹ�ã�
	 * �б�List�� - ģ��ʹ�ã�
	 * �ύ��Submit�� - ģ��ʹ��
	 */ 
  	rightId int(11) NOT NULL AUTO_INCREMENT,						# ����Ȩ��ID��ϵͳ��ʼ��������ԱInsert��ID��̶���
  	rightType tinyint(4) DEFAULT NULL,								# ����Ȩ�����ͣ���1��ģ�飬��2���ֶ�
  	nameZH varchar(200) DEFAULT NULL,								# ����Ȩ��������
  	nameEN varchar(200) DEFAULT NULL,								# ����Ȩ��Ӣ����
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
  	PRIMARY KEY (rightId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_rule (						
	/* 
	 * ������ֻ����
	 * ��������ȡ������/�༭��
	 * ��������ȡ������/�༭��ɾ����
	 * ���ţ����ɼ���
	 * ���ţ�ֻ����
	 * ���ţ���ȡ������/�༭��
	 * ���ţ���ȡ������/�༭��ɾ����
	 * ֱ���ϼ���ֻ����
	 * ֱ���ϼ�����ȡ������/�༭��
	 * ֱ���ϼ�����ȡ������/�༭��ɾ����
	 * ���������ɼ���
	 * ������ֻ����
	 * ��������ȡ������/�༭��
	 * ��������ȡ������/�༭��ɾ����
	 * ˽�У���ȡ������/�༭��
	 * ˽�У���ȡ������/�༭��ɾ����
	 */ 
  	ruleId int(11) NOT NULL AUTO_INCREMENT,							# ���ݹ���ID��ϵͳ��ʼ��������ԱInsert��ID��̶���
	ruleType tinyint(4) NOT NULL,									# ���ݹ������ͣ���1����������2�����ţ���3��ֱ���ϼ�����4����������5��˽��
  	nameZH varchar(200) DEFAULT NULL,								# ���ݹ���������
  	nameEN varchar(200) DEFAULT NULL,								# ���ݹ���Ӣ����
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
  	PRIMARY KEY (ruleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_workflow (
  	workflowModuleId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,									# ϵͳId��HRO��HRM��
  	moduleId int(11) NOT NULL,										# ϵͳģ��id
  	scopeType tinyint(4) NOT NULL,									# ���÷�Χ��1:HrService,2:InHourse��
  	nameZH varchar(200) DEFAULT NULL,								# ������
  	nameEN varchar(200) DEFAULT NULL,								# Ӣ����
  	rightIds varchar(200) DEFAULT NULL,								# ��������������Ȩ��Ids��Json��ʽ�洢{1,3,4,6}
  	includeViewObjJsp varchar(1000) DEFAULT NULL,					# ������չʾ��˶����jspҳ��
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
  	PRIMARY KEY (workflowModuleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_country (
  	countryId int(11) NOT NULL AUTO_INCREMENT,						# ����ID
  	countryNumber varchar(10) DEFAULT NULL,							# ���ұ�ţ����ʣ���156��840��826
  	countryCode varchar(10) DEFAULT NULL,							# ���ұ��루���ʣ���CN��US��UK
  	countryNameZH varchar(200) DEFAULT NULL,						# ����������
  	countryNameEN varchar(200) DEFAULT NULL,						# ����Ӣ����
  	countryISO3 varchar(5) DEFAULT NULL,							# ���ұ��루ISO����CHN��USA��GBR
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
  	PRIMARY KEY (countryId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_province (
  	provinceId int(11) NOT NULL AUTO_INCREMENT,						# ʡID
  	countryId int(11) NOT NULL,										# ����ID
  	provinceNameZH varchar(200) DEFAULT NULL,						# ʡ������
  	provinceNameEN varchar(200) DEFAULT NULL,						# ʡӢ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (provinceId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_city (
  	cityId int(11) NOT NULL AUTO_INCREMENT,							# ����ID
  	provinceId int(11) NOT NULL,									# ʡID
  	cityNameZH varchar(200) NOT NULL,								# ����������
  	cityNameEN varchar(200) NOT NULL,								# ����Ӣ����
  	cityCode varchar(10) NOT NULL,									# ���б�ţ����ţ�010��021						
  	cityISO3 varchar(5) NOT NULL,									# ���б��루ISO��PEK��SHA
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (cityId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_smsconfig (
  	configId int(11) NOT NULL AUTO_INCREMENT,						# ��������ID��һ��ID����һ�ҹ�Ӧ��
  	nameZH varchar(200) NOT NULL,									# ��������������
  	nameEN varchar(200) NOT NULL,									# ��������Ӣ����
  	serverHost varchar(200) NOT NULL,								# ���ŷ�������ַ						
  	serverPort varchar(5) NOT NULL,									# ���ŷ������˿�
  	username varchar(50) NOT NULL,									# �û���
  	password varchar(50) NOT NULL,									# ���룬���ܴ洢
  	price double DEFAULT NULL,										# ��ͻ��շѵļ۸�
  	sendTime varchar(25) DEFAULT NULL,								# �����ӳ٣�ͨ����0����ʾ�������ͣ��ӹ�Ӧ���ṩ�ӿڶ���
  	sendType varchar(5) DEFAULT NULL,								# �������ͣ���ͨ����Ĭ�Ϸ��͡��ӳٷ��ͻ�ʱ���ͣ����ӹ�Ӧ���ṩ�ӿڶ���
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
  	PRIMARY KEY (configId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_social_benefit_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# �籣����ID��һ��ID����һ�����е�һ�ֻ�������
  	nameZH varchar(200) NOT NULL,									# �籣���ƣ����ģ�
  	nameEN varchar(200) NOT NULL,									# �籣���ƣ�Ӣ�ģ�
  	cityId int(11) NOT NULL,										# �籣����		
  	termMonth varchar(50) DEFAULT NULL,								# �������ڣ�ÿ�½��ɣ����Ƚ��ɻ�����ɣ��洢�·ݡ�1,2,3,4,5,6,7,8,9,10,11,12����
  	residency varchar(50) DEFAULT NULL,								# ������ȫ���ã����س�����س��򣬱���ũ�壬���ũ�壬�۰�̨����壻�������ѡ�����մӱ�ɸѡ��
  	adjustMonth varchar(25) DEFAULT NULL,							# ÿ������·ݣ��������ѡ�����մӱ�ɸѡ
  	attribute tinyint(4) DEFAULT NULL,								# �籣�����£����£����£����£��������ѡ�����մӱ�ɸѡ��
  	effective tinyint(4) DEFAULT NULL,								# �籣�����£����£��������ѡ�����մӱ�ɸѡ��
  	startDateLimit varchar(25) DEFAULT NULL,						# �걨��ʼʱ�䣨��дһ�����еļ��ţ��������ѡ�����մӱ�ɸѡ��
  	endDateLimit varchar(25) DEFAULT NULL,							# �걨��ֹʱ�䣨��дһ�����еļ��ţ��������ѡ�����մӱ�ɸѡ��
  	startRule tinyint(4) DEFAULT NULL,								# ��ʼ���籣���ɹ��򣨰����� - 15���Ժ���ְ����籣�������������� - ��1�������籣������Ȼ������ - ����5��������籣���������ѡ�����մӱ�ɸѡ��
  	startRuleRemark varchar(25) DEFAULT NULL,						# ��ʼ���籣���ɹ���ע���������ѡ�����մӱ�ɸѡ
  	endRule tinyint(4) DEFAULT NULL,								# �������籣���ɹ����������ѡ�����մӱ�ɸѡ
  	endRuleRemark varchar(25) DEFAULT NULL,							# �������籣���ɹ���ע���������ѡ�����մӱ�ɸѡ
  	makeup tinyint(4) DEFAULT NULL,									# �Ƿ���Բ��ɣ��������ѡ�����մӱ�ɸѡ
  	makeupMonth varchar(25) DEFAULT NULL,							# �ɲ�����������0�������Բ��ɣ����ֱ�ʾ�ɲ����������հױ�ʾ���⣻�������ѡ�����մӱ�ɸѡ��
  	makeupCrossYear tinyint(4) DEFAULT NULL,						# �ܷ���겹�ɣ��������ѡ�����մӱ�ɸѡ
  	accuracy tinyint(4) DEFAULT NULL,								# ��Ϊʹ��Ĭ�ϣ�����С��λ��ȡ��������һλ��������λ���������ѡ�����մӱ�ɸѡ��
  	round tinyint(4) DEFAULT NULL,									# ��Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ���������ѡ�����մӱ�ɸѡ��
  	attachment varchar(1000) DEFAULT NULL,							# �籣����
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

CREATE TABLE hro_sys_social_benefit_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# �籣�ӱ�ID��һ��ID����һ���籣����
  	headerId int(11) NOT NULL,										# �籣����ID
  	itemId int(11) NOT NULL,										# ��ĿID
  	companyPercentLow varchar(50) DEFAULT NULL,						# ��˾�������ͣ�
  	companyPercentHight double DEFAULT NULL,						# ��˾�������ߣ�
  	personalPercentLow varchar(50) DEFAULT NULL,					# ���˱������ͣ�
  	personalPercentHight double DEFAULT NULL,						# ���˱������ߣ�
  	companyFloor double DEFAULT NULL,								# ��˾�������ͣ�
  	companyCap double DEFAULT NULL,									# ��˾�������ߣ�
  	personalFloor double DEFAULT NULL,								# ���˻������ͣ�
  	personalCap double DEFAULT NULL,								# ���˻������ߣ�
  	companyFixAmount double DEFAULT NULL,							# ��˾�̶���
  	personalFixAmount double DEFAULT NULL,							# ���˹̶���
  	termMonth varchar(50) DEFAULT NULL,								# �������ڣ�ÿ�½��ɣ����Ƚ��ɻ�����ɣ��洢�·ݡ�1,2,3,4,5,6,7,8,9,10,11,12�����ӱ�ѡ��̳��������ã�
  	residency varchar(50) DEFAULT NULL,								# ������ȫ���ã����س�����س��򣬱���ũ�壬���ũ�壬�۰�̨����壻�ӱ�ѡ��̳��������ã�
  	adjustMonth varchar(25) DEFAULT NULL,							# ÿ������·ݣ��ӱ�ѡ��̳���������
  	attribute tinyint(4) DEFAULT NULL,								# �籣�����£����£����£����£��ӱ�ѡ��̳��������ã�
  	effective tinyint(4) DEFAULT NULL,								# �籣�����£����£��ӱ�ѡ��̳��������ã�
  	startDateLimit varchar(25) DEFAULT NULL,						# �걨��ʼʱ�䣨��дһ�����еļ��ţ��ӱ�ѡ��̳��������ã�
  	endDateLimit varchar(25) DEFAULT NULL,							# �걨��ֹʱ�䣨��дһ�����еļ��ţ��ӱ�ѡ��̳��������ã�
  	startRule tinyint(4) DEFAULT NULL,								# ��ʼ���籣���ɹ��򣨰����� - 15���Ժ���ְ����籣�������������� - ��1�������籣������Ȼ������ - ����5��������籣���ӱ�ѡ��̳��������ã�
  	startRuleRemark varchar(25) DEFAULT NULL,						# ��ʼ���籣���ɹ���ע���ӱ�ѡ��̳���������
  	endRule tinyint(4) DEFAULT NULL,								# �������籣���ɹ��򣻴ӱ�ѡ��̳���������
  	endRuleRemark varchar(25) DEFAULT NULL,							# �������籣���ɹ���ע���ӱ�ѡ��̳���������
  	makeup tinyint(4) DEFAULT NULL,									# �Ƿ���Բ��ɣ��ӱ�ѡ��̳���������
  	makeupMonth varchar(25) DEFAULT NULL,							# �ɲ�����������0�������Բ��ɣ����ֱ�ʾ�ɲ����������հױ�ʾ���⣻�ӱ�ѡ��̳��������ã�
  	makeupCrossYear tinyint(4) DEFAULT NULL,						# �ܷ���겹�ɣ��ӱ�ѡ��̳���������
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

CREATE TABLE hro_sys_constant (
  	constantId int(11) NOT NULL AUTO_INCREMENT,						# ����Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	nameZH varchar(200) NOT NULL,									# ����������
  	nameEN varchar(200) NOT NULL,									# ����Ӣ����
  	scopeType tinyint(4) NOT NULL,									# ���÷�Χ��1:��Ϣ��2:�����ͬ��3:�Ͷ���ͬ��4:���㣬5:�˹�����
  	propertyName varchar(200) NOT NULL,								# �ֶ�����ӳ�䶯̬������	
  	valueType tinyint(4) DEFAULT NULL,								# �������ͣ�ʹ���Զ����ֶ��е�ֵ���ͣ�
  	characterType tinyint(4) NOT NULL,								# �������ʣ�ϵͳ�������˻���������̬������
  	content varchar(1000) DEFAULT NULL, 							# �������ݣ���CONT����ͷ����Ҫת�ƣ����磺��CONT:now��, ��CONT:nowdate��, ��CONT:nowtime���ȣ�
  	lengthType tinyint(4) NOT NULL,									# �ؼ���С����С���еȣ��ϴ�
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (constantId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_income_tax_base (
  	baseId int(11) NOT NULL AUTO_INCREMENT,							# ��˰����ID
  	nameZH varchar(200) DEFAULT NULL,								# ���ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ���ƣ�Ӣ�ģ�
  	startDate datetime DEFAULT NULL,								# ��Ч����
  	endDate datetime DEFAULT NULL,									# ʧЧ����
  	base double DEFAULT NULL,										# ��˰������
  	baseForeigner double DEFAULT NULL,								# ��˰�����㣨�⼮��
  	isDefault tinyint(4) DEFAULT NULL,								# �Ƿ�Ĭ��
  	accuracy tinyint(4) DEFAULT NULL,								# ��Ϊʹ��Ĭ�ϣ�����С��λ��ȡ��������һλ��������λ��
  	round tinyint(4) DEFAULT NULL,									# ��Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ��
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
  	PRIMARY KEY (baseId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_income_tax_range_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# ˰������ID
  	nameZH varchar(200) DEFAULT NULL,								# ���ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ���ƣ�Ӣ�ģ�
  	startDate datetime DEFAULT NULL,								# ��Ч����
  	endDate datetime DEFAULT NULL,									# ʧЧ����
  	isDefault tinyint(4) DEFAULT NULL,								# �Ƿ�Ĭ��
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

CREATE TABLE hro_sys_income_tax_range_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# ˰�ʴӱ�ID
  	headerId int(11) NOT NULL,										# ˰������ID
  	rangeFrom double DEFAULT NULL,									# �����������ʼ��
  	rangeTo double DEFAULT NULL,									# ���������������
  	percentage double DEFAULT NULL,									# ˰��
  	deduct double DEFAULT NULL,										# �۳����
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
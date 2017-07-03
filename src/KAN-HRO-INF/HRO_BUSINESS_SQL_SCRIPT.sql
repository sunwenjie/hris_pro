use HRO;

DROP TABLE IF EXISTS hro_biz_employee;								# ��Ա
DROP TABLE IF EXISTS hro_biz_employee_emergency						# ��Ա - ������ϵ��
DROP TABLE IF EXISTS hro_biz_employee_education						# ��Ա - ѧϰ����
DROP TABLE IF EXISTS hro_biz_employee_work							# ��Ա - ��������
DROP TABLE IF EXISTS hro_biz_employee_skill							# ��Ա - ����
DROP TABLE IF EXISTS hro_biz_employee_language						# ��Ա - ���Լ���
DROP TABLE IF EXISTS hro_biz_employee_certification					# ��Ա - ֤�飬�����ѵ
DROP TABLE IF EXISTS hro_biz_employee_membership					# ��Ա - ���
DROP TABLE IF EXISTS hro_biz_employee_log;							# ��Ա - ��־����
DROP TABLE IF EXISTS hro_biz_employee_contract;						# ��Ա - ��ͬ
DROP TABLE IF EXISTS hro_biz_employee_contract_property;			# ��Ա - ��ͬ����
DROP TABLE IF EXISTS hro_biz_employee_contract_sb					# ��Ա - ��ͬ - �籣����
DROP TABLE IF EXISTS hro_biz_employee_contract_sb_detail			# ��Ա - ��ͬ - �籣������ϸ
DROP TABLE IF EXISTS hro_biz_employee_contract_cb					# ��Ա - ��ͬ - �̱�����
DROP TABLE IF EXISTS hro_biz_employee_contract_salary				# ��Ա - ��ͬ - н�귽��
DROP TABLE IF EXISTS hro_biz_employee_contract_performance			# ��Ա - ��ͬ - ��Ч����
DROP TABLE IF EXISTS hro_biz_employee_contract_ot					# ��Ա - ��ͬ - �Ӱ�����
DROP TABLE IF EXISTS hro_biz_employee_contract_leave				# ��Ա - ��ͬ - �������
DROP TABLE IF EXISTS hro_biz_employee_contract_other				# ��Ա - ��ͬ - ��������
DROP TABLE IF EXISTS hro_biz_employee_contract_settlement			# ��Ա - ��ͬ - �ɱ�/Ӫ��
DROP TABLE IF EXISTS hro_biz_employee_contract_temp;				# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_employee_contract_salary_temp;			# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_employee_contract_sb_temp;				# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_employee_contract_cb_temp;				# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_employee_contract_leave_temp;			# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_employee_contract_ot_temp;				# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_employee_contract_other_temp;			# ��ʱ�� - ���ڵ���

DROP TABLE IF EXISTS hro_biz_employee_positionchange;				# �춯����н
DROP TABLE IF EXISTS hro_biz_client;								# �ͻ�
DROP TABLE IF EXISTS hro_biz_client_contact;						# �ͻ� - ��ϵ��
DROP TABLE IF EXISTS hro_biz_client_user;							# �ͻ� - �û�
DROP TABLE IF EXISTS hro_biz_client_group;							# �ͻ� - ����
DROP TABLE IF EXISTS hro_biz_client_invoice;						# �ͻ� - �˵���ַ
DROP TABLE IF EXISTS hro_biz_client_contract;						# �ͻ� - �����ͬ
DROP TABLE IF EXISTS hro_biz_client_contract_property;				# �ͻ� - �����ͬ����
DROP TABLE IF EXISTS hro_biz_client_order_header					# �ͻ� - ��������
DROP TABLE IF EXISTS hro_biz_client_order_header_rule				# �ͻ� - �����������
DROP TABLE IF EXISTS hro_biz_client_order_detail					# �ͻ� - �����ӱ�
DROP TABLE IF EXISTS hro_biz_client_order_detail_rule				# �ͻ� - �����ӱ����
DROP TABLE IF EXISTS hro_biz_client_order_detail_sb_rule			# �ͻ� - �������ɹ���
DROP TABLE IF EXISTS hro_biz_client_order_sb						# �ͻ� - �����籣�������籣ѡȡ��Χ��
DROP TABLE IF EXISTS hro_biz_client_order_cb						# �ͻ� - �����̱��������̱�ѡȡ��Χ��
DROP TABLE IF EXISTS hro_biz_client_order_performance				# �ͻ� - ������Ч����
DROP TABLE IF EXISTS hro_biz_client_order_ot						# �ͻ� - �����Ӱ�����
DROP TABLE IF EXISTS hro_biz_client_order_leave						# �ͻ� - �����ݼ�����
DROP TABLE IF EXISTS hro_biz_client_order_other						# �ͻ� - ������������
DROP TABLE IF EXISTS hro_biz_vendor;								# ��Ӧ��
DROP TABLE IF EXISTS hro_biz_vendor_contact;						# ��Ӧ�� - ��ϵ��
DROP TABLE IF EXISTS hro_biz_vendor_user;							# ��Ӧ�� - �û�
DROP TABLE IF EXISTS hro_biz_vendor_service;						# ��Ӧ�� - ����
DROP TABLE IF EXISTS hro_biz_attendance_leave_header;				# ���� - �������
DROP TABLE IF EXISTS hro_biz_attendance_leave_detail;				# ���� - ��ٴӱ�
DROP TABLE IF EXISTS hro_biz_attendance_ot_header;					# ���� - �Ӱ�����
DROP TABLE IF EXISTS hro_biz_attendance_ot_detail;					# ���� - �Ӱ�ӱ�
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_batch;			# ���� - ����
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_header;			# ���� - ����
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_detail;			# ���� - �ӱ�
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_allowance;		# ���� - ���𡢲���������
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_allowance_temp;	# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_attendance_timesheet_header_temp;		# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_attendance_leave_detail_temp;			# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_attendance_leave_header_temp;			# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_attendance_ot_detail_temp;				# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_attendance_ot_header_temp;				# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_attendance_import_header;				# ��ʱ�� - ���ڵ���
DROP TABLE IF EXISTS hro_biz_attendance_import_detail;				# ��ʱ�� - ���ڵ���

CREATE TABLE hro_biz_employee (
	employeeId int(11) NOT NULL AUTO_INCREMENT,						# ��ԱId
	accountId int(11) NOT NULL,										# �˻�Id
	corpId int(11) NOT NULL,										# �ͻ��˻�Id
	employeeNo varchar(50) DEFAULT NULL,							# ��Ա���
	nameZH varchar(200) DEFAULT NULL,								# ������
  	nameEN varchar(200) DEFAULT NULL,								# Ӣ����
	salutation tinyint(4) DEFAULT NULL,								# �ƺ����������Ա�
  	birthday datetime DEFAULT NULL,									# ��������
  	maritalStatus tinyint(4) DEFAULT NULL,							# ����״��
  	nationNality smallint(8) DEFAULT NULL,							# ����
  	birthdayPlace varchar(100) DEFAULT NULL,						# ������
  	residencyCityId int(11) DEFAULT NULL,							# ����
  	residencyCity varchar(200) DEFAULT NULL,						# ��������
  	residencyAddress varchar(200) DEFAULT NULL,						# ������ַ
  	personalAddress varchar(200) DEFAULT NULL,						# ��ǰסַ
  	personalPostcode varchar(50) DEFAULT NULL,						# סַ�ʱ�
  	highestEducation tinyint(4) DEFAULT NULL,						# ���ѧ��
  	recordNo varchar(50) DEFAULT NULL,								# �������
  	recordAddress varchar(200) DEFAULT NULL,						# �������ڵ�
  	residencyType tinyint(4) DEFAULT NULL,							# �������ʣ���Ӧ�籣ģ���е�Residency
  	graduationDate datetime DEFAULT NULL,							# ��ҵ����
  	onboardDate datetime DEFAULT NULL,								# ���빫˾��ʱ��
	startWorkDate datetime DEFAULT NULL,							# �״β��빤��ʱ��
  	hasForeignerWorkLicence tinyint(4) DEFAULT NULL,				# ����˾�ҵ���֤
  	foreignerWorkLicenceNo varchar(50) DEFAULT NULL,				# ���֤���
  	foreignerWorkLicenceEndDate datetime DEFAULT NULL,				# ���֤ʧЧ����
   	hasResidenceLicence tinyint(4) DEFAULT NULL,					# ��ס֤
   	residenceNo varchar(50) DEFAULT NULL,							# ��ס֤���
   	residenceStartDate datetime DEFAULT NULL,						# ��ס֤��Ч����				
   	residenceEndDate datetime DEFAULT NULL,							# ��ס֤ʧЧ����
   	certificateType tinyint(4) DEFAULT NULL,						# ֤������
  	certificateNumber varchar(50) DEFAULT NULL,						# ֤������
  	certificateStartDate datetime DEFAULT NULL,						# ֤����Ч����		
  	certificateEndDate datetime DEFAULT NULL,						# ֤��ʧЧ����		
  	certificateAwardFrom varchar(200) DEFAULT NULL,					# ֤���䷢����
  	bankId int(11) DEFAULT NULL,									# ����
  	bankBranch varchar(100) DEFAULT NULL,							# ��������
  	bankAccount varchar(50) DEFAULT NULL,							# �����˻�
	phone1 varchar(50) DEFAULT NULL,								# �绰
  	mobile1 varchar(50) DEFAULT NULL,								# �ֻ�
  	email1 varchar(200) DEFAULT NULL,								# ����
  	website1 varchar(200) DEFAULT NULL,								# ������ҳ
  	phone2 varchar(50) DEFAULT NULL,								# �绰�����ã�
  	mobile2 varchar(50) DEFAULT NULL,								# �ֻ������ã�
  	email2 varchar(200) DEFAULT NULL,								# ���䣨���ã�
  	website2 varchar(200) DEFAULT NULL,								# ������ҳ�����ã�
	im1Type tinyint(4) DEFAULT NULL,								# ��ʱͨѶ - 1
  	im1 varchar(200) DEFAULT NULL,									# ��ʱͨѶ����	
  	im2Type tinyint(4) DEFAULT NULL,								# ��ʱͨѶ - 2
  	im2 varchar(200) DEFAULT NULL,									# ��ʱͨѶ����
  	im3Type tinyint(4) DEFAULT NULL,								# ��ʱͨѶ - 3
  	im3 varchar(200) DEFAULT NULL,									# ��ʱͨѶ����
  	im4Type tinyint(4) DEFAULT NULL,								# ��ʱͨѶ - 4
  	im4 varchar(200) DEFAULT NULL,									# ��ʱͨѶ����
  	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	photo varchar(1000) DEFAULT NULL,								# ������Ƭ
  	attachment varchar(5000) DEFAULT NULL,							# ����
  	resumeZH text DEFAULT NULL,										# ���������ģ�
  	resumeEN text DEFAULT NULL,										# ������Ӣ�ģ�
  	publicCode varchar(100) DEFAULT NULL,							# ��Ա/Ա��������Կ����Կ - HRMʹ�ã�
  	_tempPositionIds varchar(100) DEFAULT NULL,						# �����ֶδ��ְλ
  	_tempBranchIds varchar(50) DEFAULT NULL,						# �����ֶδ�Ų���
  	_tempParentBranchIds varchar(50) DEFAULT NULL,					# �����ֶδ���ϼ�����
  	_tempParentPositionIds varchar(50) DEFAULT NULL,				# �����ֶδ���ϼ�ְλ
  	_tempParentPositionOwners varchar(50) DEFAULT NULL,				# �����ֶδ���ϼ�ְλ������
  	_tempParentPositionBranchIds varchar(50) DEFAULT NULL,			# �����ֶδ���ϼ�ְλ���� 
  	_tempPositionLocationIds varchar(50) DEFAULT NULL,				# �����ֶδ��ְλ������ַ 
  	_tempUserName varchar(50) DEFAULT NULL,							# �����ֶδ���û���
  	description varchar(1000) DEFAULT NULL,							# ��ע
	deleted tinyint(4) DEFAULT NULL,								# �Ƿ��ѱ�ɾ��
  	status tinyint(4) DEFAULT NULL,									# ��Ա״̬��1:��ְ��2:��ļ��3:��ְ��4:��ѡ
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
	employeeEmergencyId int(11) NOT NULL AUTO_INCREMENT,			# ������ϵ��ʽId
	employeeId int(11) NOT NULL,									# Ա��Id
	relationshipId tinyint(4) DEFAULT NULL,							# �������ϵ�˹�ϵ���ɷ����ӣ����ף�ĸ�ף���Ů���游ĸ�����ݣ�ͬ�£�ͬѧ�����ѣ�������
	name varchar(200) DEFAULT NULL,									# ����
  	phone varchar(50) DEFAULT NULL,									# �绰����
  	mobile varchar(50) DEFAULT NULL,								# �ƶ��绰
  	address varchar(200) DEFAULT NULL,								# ��ϵ��ַ
  	postcode varchar(50) DEFAULT NULL,								# �ʱ�
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
	employeeEducationId int(11) NOT NULL AUTO_INCREMENT,			# ѧϰ����Id
	employeeId int(11) NOT NULL,									# Ա��Id
	schoolName varchar(200) DEFAULT NULL,							# ѧУ����
	startDate datetime DEFAULT NULL,								# ��ʼѧϰʱ��
	endDate datetime DEFAULT NULL,									# ����ѧϰʱ��
	major varchar(200) DEFAULT NULL,								# רҵ
  	educationId varchar(50) DEFAULT NULL,							# �����̶ȣ������� - ���� - �����̶ȣ�
  	schoolingLength tinyint(4) DEFAULT NULL,						# ѧ��
  	studyType tinyint(4) DEFAULT NULL,								# ѧϰ��ʽ
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
	employeeWorkId int(11) NOT NULL AUTO_INCREMENT,					# ��������Id
	employeeId int(11) NOT NULL,									# Ա��Id
	companyName varchar(200) DEFAULT NULL,							# ��˾����
	startDate datetime DEFAULT NULL,								# ��ʼ����ʱ��
	endDate datetime DEFAULT NULL,									# ��������ʱ�䣨��ǰ��ְ������д��
	department varchar(100) DEFAULT NULL,							# ��ְ����
	positionId int(11) DEFAULT NULL,								# ��ְְλ�������� - ���� - �ⲿְλ��
	positionName varchar(200) DEFAULT NULL,	                        # ְλ����
	industryId int(11) DEFAULT NULL,								# ������ҵ
  	companyType tinyint(4) DEFAULT NULL,							# ��ҵ����
  	size tinyint(4) DEFAULT NULL,						    		# ��˾��ģ
	contact varchar(200) DEFAULT NULL,								# ��ϵ��
  	phone varchar(50) DEFAULT NULL,									# ��ϵ�˵绰
  	mobile varchar(50) DEFAULT NULL,								# ��ϵ���ֻ�
  	description varchar(1000) DEFAULT NULL,							# ��������					
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬����ְ����ְ��									
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
	employeeSkillId int(11) NOT NULL AUTO_INCREMENT,				# ��Ա����Id
	employeeId int(11) NOT NULL,									# Ա��Id
  	skillId int(11) NOT NULL,										# ����Id�������� - ���� - ���ܣ�
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
	employeeLanguageId int(11) NOT NULL AUTO_INCREMENT,				# ��Ա����Id
	employeeId int(11) NOT NULL,									# Ա��Id
  	languageId int(11) NOT NULL,									# ����Id�������� - ���� - ���ԣ�
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
	employeeCertificationId int(11) NOT NULL AUTO_INCREMENT,		# ��Ա֤�� - ����Id
	employeeId int(11) NOT NULL,									# Ա��Id
  	certificationId int(11) NOT NULL,								# ֤�� - ����Id�������� - ���� - ֤�� - ���
  	awardFrom varchar(200) DEFAULT NULL,							# �䷢����
  	awardDate datetime DEFAULT NULL,								# �䷢����
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
	employeeMembershipId int(11) NOT NULL AUTO_INCREMENT,			# ��Ա���Id
	employeeId int(11) NOT NULL,									# Ա��Id
  	membershipId int(11) NOT NULL,									# ���Id�������� - ���� - �����
  	activeFrom datetime DEFAULT NULL,								# ��ʼʱ��
  	activeTo datetime DEFAULT NULL,									# ����ʱ�䣨�����ʾ��ǰ�����У�
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
  `employeeId` int(11) DEFAULT NULL,								# ��ԱID
  `type` tinyint(4) DEFAULT NULL,									# ������Դ��1���ֶ����룻2��ϵͳ���ɣ�
  `content` varchar(1000) DEFAULT NULL,								# ��־����
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
	contractId int(11) NOT NULL AUTO_INCREMENT,						# ��ͬId����������ͬ�ڹ�Ա�����Ϊ2��Tab��һ��Ϊ�Ͷ���ͬ����һ��Ϊ����
	accountId int(11) NOT NULL,										# �˻�Id 
	employeeId int(11) NOT NULL,									# ��ԱId
	clientId int(11) DEFAULT NULL,									# �ͻ�Id�����/*�����ҵ����**/���Ͷ���ͬ�����Բ�ѡ��ͻ���
	corpId int(11) DEFAULT NULL,									# �ͻ�Id�����/*�����ҵ����**/���Ͷ���ͬ�����Բ�ѡ��ͻ���
	orderId int(11) DEFAULT NULL,									# ����Id�����/*�����ҵ����**/���Ͷ���ͬ�����Բ�ѡ�񶩵���
	department varchar(200) DEFAULT NULL,							# ��������
  	positionId int(11) NOT NULL,									# ְλId�������� - ���� - �ⲿְλ��
  	additionalPosition varchar(50) DEFAULT NULL,					# ����ְλ
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	templateId int(11) NOT NULL,									# ��ͬģ��Id
	lineManagerId int(11) DEFAULT NULL,								# ֱ�߾���Id���󶨿ͻ���ϵ�ˣ�
	approveType tinyint(4) DEFAULT NULL,							# ��˷�ʽ��1:ְλ�趨��2:��ֱ�߾���
	contractNo varchar(50) DEFAULT NULL,							# ��ͬ��ţ��鵵ʹ�ã�
	masterContractId int(11) DEFAULT NULL,							# ����ͬ�ţ��鵵��ţ�����һ����Ա�Ͷ���ͬ�¿ɴ������ʱ�䲻�ص��ķ���Э��
  	nameZH varchar(200) DEFAULT NULL,								# ��ͬ���ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ��ͬ���ƣ�Ӣ�ģ�
  	content text DEFAULT NULL,										# ��ͬ���ݣ�����${column}ʹ��ϵͳ������䣬����${blank_input}��${blank_textarea}������ͬʱ��д
  	startDate datetime DEFAULT NULL,								# ��ͬ��ʼʱ��
  	endDate datetime DEFAULT NULL,									# ��ͬ����ʱ��
  	period smallint(8) DEFAULT NULL,								# ��ͬʱ�ޣ�������
  	applyOTFirst tinyint(4) DEFAULT NULL,							# �Ӱ���Ҫ���루1:�ǣ�2:�񣩣����޷���Э��ʹ�ã�
  	otLimitByDay smallint(8) DEFAULT NULL,							# ÿ��Ӱ����ޣ�Сʱ����JS��֤С��10�������޷���Э��ʹ�ã�
  	otLimitByMonth smallint(8) DEFAULT NULL,						# ÿ�¼Ӱ����ޣ�Сʱ����JS��֤С��200�������޷���Э��ʹ�ã�
  	workdayOTItemId int(11) DEFAULT NULL,							# �����ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱࣨ���޷���Э��ʹ�ã�
  	weekendOTItemId int(11) DEFAULT NULL,							# ��Ϣ�ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱࣨ���޷���Э��ʹ�ã�
  	holidayOTItemId int(11) DEFAULT NULL,							# �ڼ��ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱࣨ���޷���Э��ʹ�ã�
  	attendanceCheckType tinyint(4) DEFAULT NULL,					# ���ڷ�ʽ����ʱ����������������˫�������������޷���Э��ʹ�ã�
  	resignDate datetime DEFAULT NULL,								# ��ְ����
  	employStatus tinyint(4) DEFAULT NULL,							# ��Ӷ״̬��1:��ְ��2:����������3:������ְ��4:������ְ�������޷���Э��ʹ�ã�
  	calendarId int(11) DEFAULT NULL,								# ����Id
  	shiftId int(11) DEFAULT NULL,									# �Ű�Id
  	sickLeaveSalaryId int(11) DEFAULT NULL,							# ���ٹ���Id
  	flag tinyint(4) DEFAULT NULL,									# �������Ͷ���ͬ���Ƿ���Э��
  	medicalNumber varchar(50) DEFAULT NULL,							# ҽ�����ʺ�
  	sbNumber varchar(50) DEFAULT NULL,								# �籣���ʺ�
	fundNumber varchar(50) DEFAULT NULL,							# �������ʺ�
	hsNumber  varchar(50)zDEFAULT NULL,								# ������
  	attachment varchar(5000) DEFAULT NULL,							# ����
  	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT '0',									# �����ˣ�Position Id��
  	settlementBranch varchar(25) DEFAULT NULL,						# �ɱ�����/Ӫ�ղ���
  	locked tinyint(4) DEFAULT NULL,									# ��������/��
  	description varchar(1000) DEFAULT NULL,							# ����
  	salaryVendorId int(11) DEFAULT NULL,							# н�깩Ӧ��
  	incomeTaxBaseId int(11) DEFAULT NULL,							# ��˰����
  	incomeTaxRangeHeaderId int(11) DEFAULT NULL,					# ��˰˰��
  	probationMonth tinyint(4) DEFAULT NULL,							# ������������0-6���£�
  	probationEndDate datetime DEFAULT NULL,							# �����ڽ���ʱ��
  	isRetained tinyint(4) DEFAULT NULL,								# �Ƿ����ڣ�1:�ǣ�2:�񣩣��ڱ����ڵĺ�ͬ�ϴ��ϱ�ʶ
  	isContinued tinyint(4) DEFAULT NULL,							# �Ƿ���ǩ��1:�ǣ�2:�񣩣��ڱ���ǩ�ĺ�ͬ�ϴ��ϱ�ʶ
  	continueNeeded tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ��ǩ��1:�ǣ�2:�񣩣���ǰ����Ҫ����ǩ�ĺ�ͬ�ϴ��ϱ�ʶ
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ��ͬ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:�Ѹ��£�6:�鵵��7:������
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
	propertyId int(11) NOT NULL AUTO_INCREMENT,						# ����Id������
	contractId int(11) NOT NULL,									# �����ͬId
	propertyName varchar(200) NOT NULL,								# ��������
	propertyValue varchar(5000) DEFAULT NULL,						# ����ֵ	
	valueType tinyint(4) DEFAULT NULL,								# �ֶ�ֵ�����ͣ�ʹ���Զ����ֶ��е�ֵ���ͣ�
  	description varchar(1000) DEFAULT NULL,							# ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬
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
	employeeSBId int(11) NOT NULL AUTO_INCREMENT,					# ��Ա�籣����Id������
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	sbSolutionId int(11) NOT NULL,									# �籣����Id�������� - ҵ�� - �籣������
	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id�����ֻ��һ����Ӧ�̣�Ĭ��ѡ��
	vendorServiceId int(11) DEFAULT NULL,							# ��Ӧ�̷���Id�����ֻ��һ����Ӧ�̷���Ĭ��ѡ��
	personalSBBurden tinyint(4) DEFAULT NULL,						# �籣���˲��ֹ�˾�е����ǣ���
	startDate datetime DEFAULT NULL,								# �ӱ�����
	endDate datetime DEFAULT NULL,									# �˱�����
	needMedicalCard tinyint(4) DEFAULT NULL,						# ��Ҫ����ҽ����						
	needSBCard tinyint(4) DEFAULT NULL,								# ��Ҫ�����籣��
	medicalNumber varchar(50) DEFAULT NULL,							# ҽ�����ʺ�
	sbNumber varchar(50) DEFAULT NULL,								# �籣���ʺ�
	fundNumber varchar(50) DEFAULT NULL,							# �������ʺ�
	sbBase double DEFAULT NULL,										# �籣����
	flag tinyint(4) DEFAULT NULL,									# �籣ʵ�ʽ��ɱ�ʶ��1:������2:δ���ɣ�
  	description varchar(1000) DEFAULT NULL,							# ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��0:���籣��1:�ӱ��ύ��2:���걨�ӱ���3:�������ɣ�4:�˱��ύ��5:���걨�˱���6:���˱���7:���ɣ�
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
	employeeSBDetailId int(11) NOT NULL AUTO_INCREMENT,				# ��Ա�籣������ϸId���������������ù�Ա������
	employeeSBId int(11) NOT NULL,									# ��Ա�籣����Id
	solutionDetailId int(11) NOT NULL,								# �籣������ϸId�������� - ҵ�� - �籣������
	baseCompany double DEFAULT NULL,								# ��˾����
	basePersonal double DEFAULT NULL,								# ���˻���
  	description varchar(1000) DEFAULT NULL,							# ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬
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
	employeeCBId int(11) NOT NULL AUTO_INCREMENT,					# ��Ա�̱�����Id������
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	solutionId int(11) NOT NULL,									# �̱�����Id�������� - ҵ�� - �̱�������
	startDate datetime DEFAULT NULL,								# �ӱ�����
	endDate datetime DEFAULT NULL,									# �˱�����
	freeShortOfMonth tinyint(4) DEFAULT NULL,						# ��ȫ����ѣ���/�� - �����ְ����ְ�������������chargeFullMonth����ͬʱѡ���ǡ�
	chargeFullMonth	tinyint(4) DEFAULT NULL,						# ��ȫ�¼Ʒѣ���/�� - �����ְ����ְ�������������freeShortOfMonth����ͬʱѡ���ǡ�
  	description varchar(1000) DEFAULT NULL,							# ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��0:���̱���1:�깺�ύ��2:���깺��3:��������4:�˹��ύ��5:���˹���6:���˹���
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
	employeeSalaryId int(11) NOT NULL AUTO_INCREMENT,				# ��Աн�귽��Id�������������Կ�Ŀ�����ʡ����𡢽����ȣ�
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ��
	salaryType tinyint(4) NOT NULL,									# ��н��ʽ�����£���Сʱ��
	divideType tinyint(4) NOT NULL,									# ���㷽ʽ�������㣬���¹�������������ƽ����н����������н��ʽΪ�����¡��������ʾ
	divideTypeIncomplete tinyint(4) DEFAULT NULL,					# ���㷽ʽ - �����£������㣬���¹�������������ƽ����н����������н��ʽΪ�����¡��������ʾ
	excludeDivideItemIds varchar(1000) DEFAULT NULL,				# �������ݼٿ�Ŀ
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʹ�û�����Դʱ���֣�
	fix double DEFAULT NULL,										# �̶��𣨵�ʹ�û�����Դʱ���֣�
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ���ã�
	discount double DEFAULT NULL,									# �ۿ�
	multiple double DEFAULT NULL,									# ����
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�
	startDate datetime DEFAULT NULL,								# ������ʼʱ��
	endDate datetime DEFAULT NULL,									# ���Ž���ʱ�䣬һ�����������ʾ
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ�Ĭ�� - /21.75����������/���������� ��> 21.75���㣬�Զ��壩
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
	showToTS tinyint(4) DEFAULT NULL,								# ��ʾ�����ڱ���/��
	probationUsing tinyint(4) DEFAULT NULL,							# �������Ƿ��ʹ�� 
	description varchar(1000) DEFAULT NULL,							# ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�
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
	employeePerformanceId int(11) NOT NULL AUTO_INCREMENT,			# Ա����Ч����Id������
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ�����ʡ�����������
	performanceSolutionId int(11) NOT NULL,							# ��Ч����Id�������� - ҵ�� - �̱�������
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʱ���ã�
	fix double DEFAULT NULL,										# �̶�����ʱ���ã�
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ���ã�
	discount double DEFAULT NULL,									# �ۿۣ���ʱ���ã�
	multiple double DEFAULT NULL,									# ���ʣ���ʱ���ã�
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�
	startDate datetime DEFAULT NULL,								# ��Чʱ��
	endDate datetime DEFAULT NULL,									# ����ʱ��
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ���ʱ���ã�
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
	description varchar(1000) DEFAULT NULL,							# ����
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
	employeeOTId int(11) NOT NULL AUTO_INCREMENT,					# ��Ա�Ӱ�����Id������
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ��
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʹ�û�����Դʱ���֣�
	fix double DEFAULT NULL,										# �̶��𣨵�ʹ�û�����Դʱ���֣�
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ�����㣩
	discount double DEFAULT NULL,									# �ۿ�
	multiple double DEFAULT NULL,									# ����
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�
	startDate datetime DEFAULT NULL,								# ��Ч����
	endDate datetime DEFAULT NULL,									# ��������
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ�Ĭ�� - �Ӱ�Сʱ�������ʡ��ۿۣ����� or ������Դ�����Զ��壩
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
	description varchar(1000) DEFAULT NULL,							# ����
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
	employeeLeaveId int(11) NOT NULL AUTO_INCREMENT,				# ��Ա�ݼ�����Id������
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ��
	legalQuantity smallint(4) DEFAULT NULL,							# ����������Сʱ��
	benefitQuantity smallint(4) DEFAULT NULL,						# ����������Сʱ��
	cycle tinyint(4) DEFAULT NULL,									# ʹ�����ڣ���ͬ�ڣ������꣬��ͬ�꣩
	probationUsing tinyint(4) DEFAULT NULL,							# �������Ƿ��ʹ��
	delayUsing tinyint(4) DEFAULT NULL,								# �����ӳ�ʹ��
	legalQuantityDelayMonth tinyint(4) DEFAULT NULL,				# ������δʹ���������������0��ʾ�������ڣ�		
	benefitQuantityDelayMonth tinyint(4) DEFAULT NULL,				# ������δʹ���������������0��ʾ�������ڣ�
	description varchar(1000) DEFAULT NULL,							# ����
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
	employeeOtherId int(11) NOT NULL AUTO_INCREMENT,				# ��Ա��������Id������
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ�������ʡ��籣���̱����ݼ١��Ӱࡢ�����������п�Ŀ��
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʹ�û�����Դʱ���֣�
	fix double DEFAULT NULL,										# �̶��𣨵�ʹ�û�����Դʱ���֣�
	quantity smallint(4) DEFAULT NULL,								# ����
	discount double DEFAULT NULL,									# �ۿ�
	multiple double DEFAULT NULL,									# ����
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�
	startDate datetime DEFAULT NULL,								# ��Чʱ��
	endDate datetime DEFAULT NULL,									# ����ʱ��
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ�Ĭ�� - �Ӱ�Сʱ�������ʡ��ۿۣ����� or ������Դ�����Զ��壩
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
	showToTS tinyint(4) DEFAULT NULL,								# ��ʾ�����ڱ���/��
	description varchar(1000) DEFAULT NULL,							# ����
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
  	clientId int(11) NOT NULL AUTO_INCREMENT,						# �ͻ�������
  	groupId int(11) DEFAULT NULL,									# ����ID
  	accountId int(11) NOT NULL,										# �˻�ID
  	corpId int(11) DEFAULT NULL,									# ��ҵID������Inhouseʹ��
  	number varchar(50) DEFAULT NULL,								# �ͻ����
  	nameZH varchar(200) DEFAULT NULL,								# �ͻ���������
  	nameEN varchar(200) DEFAULT NULL,								# �ͻ�Ӣ������
  	titleZH varchar(200) DEFAULT NULL,								# �ͻ������������
  	titleEN varchar(200) DEFAULT NULL,								# �ͻ����Ӣ������
  	cityId int(11) DEFAULT NULL,									# ����
  	address varchar(200) DEFAULT NULL,								# ��ϵ��ַ
  	postcode varchar(50) DEFAULT NULL,								# �ʱ�
  	mainContact varchar(50) DEFAULT NULL,							# ��Ҫ��ϵ��
  	phone varchar(50) DEFAULT NULL,									# �绰����
  	mobile varchar(50) DEFAULT NULL,								# �ƶ��绰
  	fax varchar(50) DEFAULT NULL,									# �������
  	email varchar(200) DEFAULT NULL,								# �����ַ
  	im1Type tinyint(4) DEFAULT NULL,								# IM1����
  	im1 varchar(200) DEFAULT NULL,									# IM1����	
  	im2Type tinyint(4) DEFAULT NULL,								# IM2����
  	im2 varchar(200) DEFAULT NULL,									# IM2����
  	website varchar(500) DEFAULT NULL,								# ��վ��ַ
  	invoiceDate varchar(50) DEFAULT NULL,							# ��Ʊ����
  	paymentTerms varchar(50) DEFAULT NULL,							# ֧������
  	industryId int(11) DEFAULT NULL,								# ������ҵ
  	type tinyint(4) DEFAULT NULL,									# ��ҵ����
  	size tinyint(4) DEFAULT NULL,						    		# ��˾��ģ
  	description varchar(5000) DEFAULT NULL,                			# ��˾���
  	recommendPerson varchar(100) DEFAULT NULL,            	  		# �Ƽ���
  	recommendBranch varchar(100) DEFAULT NULL,            			# �Ƽ��˲���
  	recommendPosition varchar(100) DEFAULT NULL,					# �Ƽ���ְλ
  	legalEntity int(11) DEFAULT NULL,								# ����ʵ��
  	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	orderBindContract tinyint(4) DEFAULT NULL,						# �����󶨺�ͬ����/�񣩣������ѡ���ǡ�����������ʱһ����Ҫѡ�������ͬ��
  	logoFile varchar(1000) DEFAULT NULL,							# ��˾�̱���·��	
  	logoFileSize INTEGER DEFAULT NULL,								# ��˾�̱��ļ���С
  	imageFile varchar(1000) DEFAULT NULL,							# ��˾������Ƭ
  	sbGenerateCondition tinyint(4) DEFAULT NULL,					# �籣�걨������1:������׼��2:������Ч��������
  	cbGenerateCondition tinyint(4) DEFAULT NULL,					# �̱��깺������1:������׼��2:������Ч��������
  	settlementCondition tinyint(4) DEFAULT NULL,					# ���㴦��������1:������׼��2:������Ч��������
  	sbGenerateConditionSC tinyint(4) DEFAULT NULL,					# �籣�걨������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
  	cbGenerateConditionSC tinyint(4) DEFAULT NULL,					# �̱��깺������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
  	settlementConditionSC tinyint(4) DEFAULT NULL,					# ���㴦��������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:������
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
  	contactId int(11) NOT NULL AUTO_INCREMENT,						# ��ϵ��Id������
  	clientId int(11) NOT NULL,										# �ͻ�Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	corpId int(11) DEFAULT NULL,									# ��ҵID������Inhouseʹ��
  	salutation tinyint(4) DEFAULT NULL,								# �ƺ�
  	nameZH varchar(200) DEFAULT NULL,								# ��������
  	nameEN varchar(200) DEFAULT NULL,								# Ӣ������
  	title varchar(200) DEFAULT NULL,								# ͷ��
  	department varchar(100) DEFAULT NULL,							# ��������
  	bizPhone varchar(50) DEFAULT NULL,								# ��˾�绰
  	personalPhone varchar(50) DEFAULT NULL,							# ˽�˵绰
  	bizMobile varchar(50) DEFAULT NULL,								# ��˾�ֻ�����
  	personalMobile varchar(50) DEFAULT NULL,						# �����ֻ�����
  	otherPhone varchar(50) DEFAULT NULL,							# ��������
  	fax varchar(50) DEFAULT NULL,									# �������
  	bizEmail varchar(200) DEFAULT NULL,								# ��˾����
  	personalEmail varchar(200) DEFAULT NULL,						# ��������
  	im1Type tinyint(4) DEFAULT NULL,								# IM1����
  	im1 varchar(200) DEFAULT NULL,									# IM1����	
  	im2Type tinyint(4) DEFAULT NULL,								# IM2����
  	im2 varchar(200) DEFAULT NULL,									# IM2����
  	comment varchar(1000) DEFAULT NULL,								# ��ע
  	canAdvBizEmail tinyint(4) DEFAULT NULL,							# �Ƿ���Է���������Ϣ����ҵ����
  	canAdvPersonalEmail tinyint(4) DEFAULT NULL,					# �Ƿ���Է���������Ϣ����������
  	canAdvBizSMS tinyint(4) DEFAULT NULL,							# �Ƿ���Է���������Ϣ����ҵ����Ϣ
  	canAdvPersonalSMS tinyint(4) DEFAULT NULL,						# �Ƿ���Է���������Ϣ�����˶���Ϣ
  	cityId int(11) DEFAULT NULL,									# ����
  	address varchar(200) DEFAULT NULL,								# ��ϵ��ַ
  	postcode varchar(50) DEFAULT NULL,								# �ʱ�
  	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
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
  	PRIMARY KEY (contactId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;  	

CREATE TABLE hro_biz_client_user (
  	clientUserId int(11) NOT NULL AUTO_INCREMENT,					# �û�ID
  	accountId int(11) DEFAULT NULL,									# �˻�ID
  	corpId int(11) DEFAULT NULL,									# ��ҵID������Inhouseʹ��
  	clientId int(11) NOT NULL,										# �ͻ�ID
  	clientContactId int(11) NOT NULL,								# �ͻ���ϵ��ID
  	username varchar(50) DEFAULT NULL,								# �û���
  	password varchar(50) DEFAULT NULL,								# ����
  	bindIP varchar(50) NULL,										# ��IP��ַ�������ֻ��ָ��IP��ַ����
  	lastLogin datetime DEFAULT NULL,								# ����¼ʱ��
  	lastLoginIP varchar(50) NULL,									# ����¼IP��ַ
  	superUserId int(11) DEFAULT NULL,								# ���˺�ID ��ͬһ���ͻ����ܱ������˶���˺ţ���ʱ���������˺Ž����˺Ŵ���������
  	validatedSuperUser tinyint(4) DEFAULT NULL,						# �ͻ����� (0:����,1:������)
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
	groupId int(11) NOT NULL AUTO_INCREMENT,						# ����Id������
	accountId int(11) NOT NULL,										# �˻�Id
	corpId int(11) DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	number varchar(50) DEFAULT NULL,								# ���ű��
  	nameZH varchar(200) DEFAULT NULL,								# ����������
  	nameEN varchar(200) DEFAULT NULL,								# ����Ӣ����
  	description varchar(1000) DEFAULT NULL,							# ������Ϣ
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
	invoiceId int(11) NOT NULL AUTO_INCREMENT,						# ��Ʊ��ַId������
	clientId int(11) NOT NULL,										# �ͻ�Id
	accountId int(11) NOT NULL,										# �˻�Id 
	corpId int(11) DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	title varchar(200) DEFAULT NULL,								# ��Ʊ̨ͷ
	salutation tinyint(4) DEFAULT NULL,								# �ƺ�
  	nameZH varchar(200) DEFAULT NULL,								# ���������ռ��ˣ�
  	nameEN varchar(200) DEFAULT NULL,								# Ӣ�������ռ��ˣ�
  	position varchar(200) DEFAULT NULL,								# ְλ
  	department varchar(100) DEFAULT NULL,							# ����
  	cityId int(11) DEFAULT NULL,									# ����
  	address varchar(200) DEFAULT NULL,								# ��ַ
  	postcode varchar(50) DEFAULT NULL,								# �ʱ�
  	email varchar(200) DEFAULT NULL,								# ����
  	phone varchar(50) DEFAULT NULL,									# �绰
  	mobile varchar(50) DEFAULT NULL,								# �ֻ�
  	invoiceDate varchar(50) DEFAULT NULL,							# ��Ʊ����
  	paymentTerms varchar(50) DEFAULT NULL,							# ��������
  	legalEntity int(11) DEFAULT NULL,								# ����ʵ��
  	description varchar(1000) DEFAULT NULL,							# ����
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
	contractId int(11) NOT NULL AUTO_INCREMENT,						# �����ͬId������
	clientId int(11) NOT NULL,										# �ͻ�Id
	accountId int(11) NOT NULL,										# �˻�Id 
	corpId int(11) DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	templateId int(11) NOT NULL,									# ��ͬģ��Id
	contractNo varchar(50) DEFAULT NULL,							# ��ͬ��ţ��鵵ʹ�ã�
	versionType tinyint(4) DEFAULT NULL								# �汾���ͣ�ϵͳ�棬�ͻ��棩
	masterContractId int(11) DEFAULT NULL,							# ����ͬ�ţ��鵵��ţ�
	invoiceAddressId int(11) DEFAULT NULL,							# ��Ʊ��ַ������Client Invoice Address�� 
  	nameZH varchar(200) DEFAULT NULL,								# ��ͬ���ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ��ͬ���ƣ�Ӣ�ģ�
  	content text DEFAULT NULL,										# ��ͬ���ݣ�����${column}ʹ��ϵͳ������䣬����${blank_input}��${blank_textarea}������ͬʱ��д
  	startDate datetime DEFAULT NULL,								# ��ͬ��ʼʱ��
  	endDate datetime DEFAULT NULL,									# ��ͬ����ʱ��
  	period smallint(8) DEFAULT NULL,								# ��ͬʱ�ޣ�������
  	attachment varchar(5000) DEFAULT NULL,							# ����
  	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	description varchar(1000) DEFAULT NULL,							# ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ��ͬ״̬���½�������ˣ���׼���˻أ��Ѹ��£��鵵��
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
	propertyId int(11) NOT NULL AUTO_INCREMENT,						# ����Id������
	contractId int(11) NOT NULL,									# �����ͬId
	constantId int(11) NOT NULL,									# ����Id
	propertyName varchar(200) NOT NULL,								# ��������
	propertyValue varchar(5000) DEFAULT NULL,						# ����ֵ	
	valueType tinyint(4) DEFAULT NULL,								# �ֶ�ֵ�����ͣ�ʹ���Զ����ֶ��е�ֵ���ͣ�
  	description varchar(1000) DEFAULT NULL,							# ����
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
	orderHeaderId int(11) NOT NULL AUTO_INCREMENT,					# ��������Id������
	clientId int(11) NOT NULL,										# �ͻ�Id
	accountId int(11) NOT NULL,										# �˻�Id 
	corpId int(11) DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	contractId int(11) DEFAULT NULL,								# �����ͬId�����ػ��п������ö��������Ƿ�������ѹ鵵�������ͬ
	entityId int(11) NOT NULL,										# ����ʵ��Id��Ĭ��ѡ�������ͬ������Ҳ���Ը���
	businessTypeId int(11) NOT NULL,								# ҵ������Id��Ĭ��ѡ�������ͬ������Ҳ���Ը���
	invoiceAddressId int(11) NOT NULL,								# ��Ʊ��ַId��Ĭ��ѡ�������ͬ������Ҳ���Ը���
	nameZH varchar(200) DEFAULT NULL,								# �������ƣ����ģ�
  	nameEN varchar(200) DEFAULT NULL,								# �������ƣ�Ӣ�ģ�
  	startDate datetime DEFAULT NULL,								# ������ʼʱ�䣬Ĭ��ѡ�������ͬ������Ҳ���Ը���
  	endDate datetime DEFAULT NULL,									# ��������ʱ�䣬Ĭ��ѡ�������ͬ������Ҳ���Ը���
  	circleStartDay tinyint(4) DEFAULT NULL,							# ��н���� - ��ʼ��
  	circleEndDay tinyint(4) DEFAULT NULL,							# ��н���� - �����գ���������������Ϊ�����£�
  	salaryVendorId int(11) DEFAULT NULL,							# н�깩Ӧ��
  	payrollDay tinyint(4) DEFAULT NULL,								# ��н����
  	salaryMonth tinyint(4) DEFAULT NULL,							# �����·ݣ�1:��ǰ�£�2:��һ�£�3:�϶��£�4:��һ�£��������ڽ���
  	sbMonth tinyint(4) DEFAULT NULL,								# �籣�·ݣ�1:��ǰ�£�2:��һ�£�3:�϶��£�4:��һ�£��������ڽ���
  	cbMonth tinyint(4) DEFAULT NULL,								# �̱��·ݣ�1:��ǰ�£�2:��һ�£�3:�϶��£�4:��һ�£��������ڽ���
  	fundMonth tinyint(4) DEFAULT NULL,								# �������·ݣ�1:��ǰ�£�2:��һ�£�3:�϶��£�4:��һ�£��������ڽ���
  	salesType tinyint(4) DEFAULT NULL,								# ���۷�ʽ��1:�̶���2:�Ӽۣ�3:����������̶��𡱷�ʽ����������BaseFrom���㣬���Ӽۡ�����Ѵ�BaseFrom���㣬���������ʽ������������
  	invoiceType tinyint(4) DEFAULT NULL,							# ��Ʊ��ʽ��1:��������Ʊ��2:���ͻ���Ʊ��3:�������ͬ��Ʊ��4:����Ա��Ʊ��
  	settlementType tinyint(4) DEFAULT NULL,							# ���㷽ʽ��1:���ʡ��籣�ͷ���Ѷ��ֿ���2:���ʡ��籣�ͷ���Ѷ��ϲ���3:���ʺ��籣�ϲ�������ѷֿ���4:���ʺͷ���Ѻϲ����籣�ֿ���5:�籣�ͷ���Ѻϲ������ʷֿ��� 
  	probationMonth tinyint(4) DEFAULT NULL,							# ������������0-6���£�
  	serviceScope tinyint(4) DEFAULT NULL,							# �������ݣ�1:ֻ���籣��2:ֻ�����ʣ�3:���籣+�����ʣ�4:�������ʣ�5:���籣+�������ʣ�
  	personalSBBurden tinyint(4) DEFAULT NULL,						# �籣���˲��ֹ�˾�е����ǣ���
  	applyOTFirst tinyint(4) DEFAULT NULL,							# �Ӱ���Ҫ���루1:�ǣ�2:��
  	otLimitByDay smallint(8) DEFAULT NULL,							# ÿ��Ӱ�Сʱ�����ޣ�JS��֤С��10��
  	otLimitByMonth smallint(8) DEFAULT NULL,						# ÿ�¼Ӱ�Сʱ�����ޣ�JS��֤С��200��
  	workdayOTItemId int(11) DEFAULT NULL,							# �����ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱ�
  	weekendOTItemId int(11) DEFAULT NULL,							# ��Ϣ�ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱ�
  	holidayOTItemId int(11) DEFAULT NULL,							# �ڼ��ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱ�
  	attendanceCheckType tinyint(4) DEFAULT NULL,					# ������ˣ�1:��ʱ������2:����������3:˫��������
  	attendanceGenerate tinyint(4) DEFAULT NULL,						# �������ɣ�1:�³����ɣ�2:��ĩ���ɣ�
  	approveType tinyint(4) DEFAULT NULL,							# ��˷�ʽ��1:ְλ�趨��2:��ֱ�߾���
  	calendarId int(11) NOT NULL,									# ����Id
  	shiftId int(11) NOT NULL,										# �Ű�Id
  	sickLeaveSalaryId int(11) NOT NULL,								# ���ٹ���Id
  	taxId int(11) NOT NULL,											# ˰��Id - ������˰��
  	attachment varchar(5000) DEFAULT NULL,							# ����
  	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	locked tinyint(4) DEFAULT NULL,									# ��������/��
  	noticeExpire varchar(1000) DEFAULT NULL,						# ����֪ͨ(������ͬ����֪ͨ 1:���������� 2:һ�������� 3:15������ 4:һ������ÿ������)
  	noticeProbationExpire varchar(1000) DEFAULT NULL,				# �����ڵ���֪ͨ(������ͬ����֪ͨ 1:���������� 2:һ�������� 3:15������ 4:һ������ÿ������)
  	noticeRetire varchar(1000) DEFAULT NULL,						# ���ݵ���֪ͨ(������ͬ����֪ͨ 1:���������� 2:һ�������� 3:15������ 4:һ������ÿ������)
  	currency varchar(25) DEFAULT '0'								# ��������
  	contractPeriod tinyint(4) DEFAULT NULL							# ��ͬ����
  	salaryType tinyint(4) NOT NULL,									# ��н��ʽ�����£���Сʱ��
	divideType tinyint(4) NOT NULL,									# ���㷽ʽ�������㣬���¹�������������ƽ����н����������н��ʽΪ�����¡��������ʾ
	divideTypeIncomplete tinyint(4) DEFAULT NULL,					# ���㷽ʽ - �����£������㣬���¹�������������ƽ����н����������н��ʽΪ�����¡��������ʾ
  	incomeTaxBaseId int(11) DEFAULT NULL;							# ��˰����ID
   	incomeTaxRangeHeaderId int(11) DEFAULT NULL;					# ��˰˰��ID
  	excludeDivideItemIds varchar(1000) DEFAULT NULL,				# �������ݼٿ�Ŀ
	description varchar(1000) DEFAULT NULL,							# ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ��ͬ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:��Ч��6:������7:��ֹ��8:ȡ�����������ͬ�Ѹ����򶩵���Ч���Ѹ��¼�����״̬�������ͬ������������׼��������Ч��
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
	orderHeaderRuleId int(11) NOT NULL AUTO_INCREMENT,				# �����������Id�������������ԭ���ǣ����ú�����½���
	orderHeaderId int(11) NOT NULL,									# ��������Id
	ruleType tinyint(4) NOT NULL,									# �������ͣ�1:�������� - ���ڣ������ڣ������������ۣ�2:������� - ���ڣ������ڣ������������ۣ� 
	ruleValue double DEFAULT NULL,									# ����λ���������
	ruleResult double DEFAULT NULL,									# ���������ٷֱȣ�
  	description varchar(1000) DEFAULT NULL,							# ����
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
	orderDetailId int(11) NOT NULL AUTO_INCREMENT,					# �����ӱ�Id������
	orderHeaderId int(11) NOT NULL,									# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId 
	calculateType tinyint(4) NOT NULL,								# ���㷽ʽ��1:�����籣������2:�����̱�������3:��������������4:������������
	packageType tinyint(4) DEFAULT NULL,							# ���÷�ʽ��1:����ͷ��2:��������		- ������ʹ��	
	divideType tinyint(4) NOT NULL,									# ���㷽ʽ�������㣬�����������������������������÷�ʽΪ������ͷ���������ʾ
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�������Ա����Э���е�һ��
	startDate datetime DEFAULT NULL,								# ��Ч����
	endDate datetime DEFAULT NULL,									# ��������
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��  	- �Ӽ����ʹ��
	percentage double DEFAULT NULL,									# ��������ʹ�û�����Դʱ���֣�		- �Ӽ����ʹ��
	fix double DEFAULT NULL,										# �̶��𣨵�ʹ�û�����Դʱ���֣�		- �Ӽ����ʹ��
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ�����㣩
	discount double DEFAULT NULL,									# �ۿۣ���ʱ�����㣩
	multiple double DEFAULT NULL,									# ���ʣ���ʱ�����㣩
	resultCap double DEFAULT NULL,									# ����������						- �Ӽ����ʹ��	
	resultFloor double DEFAULT NULL,								# ����������						- �Ӽ����ʹ��
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ�1:������������ - Base*(��������+��н�ݼ�)/�����գ�2:������������ - Base*(��������-����н�ݼ�)/�����գ�3:�Զ��壩
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ�������������ʹ�ù�ʽ
	onboardNoCharge tinyint(4) DEFAULT NULL,						# ��ְ���շ����
	offDutyNoCharge tinyint(4) DEFAULT NULL,						# ��ְ���շ����
	resignNoCharge tinyint(4) DEFAULT NULL,							# �Զ���ְ���շ����
	probationNoCharge tinyint(4) DEFAULT NULL,						# �����ڲ��շ����
	onboardWithout tinyint(4) DEFAULT NULL,							# ��ְ���첻�Ʒ�
	offDutyWidthout tinyint(4) DEFAULT NULL,						# ��ְ���첻�Ʒ�
  	description varchar(1000) DEFAULT NULL,							# ����
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
	orderDetailRuleId int(11) NOT NULL AUTO_INCREMENT,				# �����ӱ����Id�������������ԭ���ǣ����ú�����½���
	orderDetailId int(11) NOT NULL,									# �����ӱ�Id
	ruleType tinyint(4) NOT NULL,									# �������ͣ�1:�������� - ���ڣ������ڣ���2:��ְ���� - ���ڣ������ڣ���3:��ְ���� - ���ڣ������ڣ���4:��ְ�����������գ� - С�ڣ������ڣ���5:��ְ�����������գ� - С�ڣ������ڣ���6:��Ŀ��� - ���ڣ������ڣ������������ۣ� 
	chargeType tinyint(4) NOT NULL,									# ���㷽ʽ��1:��2:�ٷֱȣ�
	ruleValue double DEFAULT NULL,									# ����λ
	ruleResult double DEFAULT NULL,									# ������
  	description varchar(1000) DEFAULT NULL,							# ����
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
	sbRuleId int(11) NOT NULL AUTO_INCREMENT,						# ����ID
	orderDetailId int(11) NOT NULL,									# �����ӱ�ID
	sbSolutionId int(11) NOT NULL,									# �籣����ID
	sbRuleType tinyint(4) DEFAULT NULL,								# �շѹ���1�����շѣ�2�����˴��շѣ�3�����·��շѣ�
	amount double DEFAULT NULL,										# ���
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
	orderSbId int(11) NOT NULL AUTO_INCREMENT,						# �����籣Id������
	orderHeaderId int(11) NOT NULL,									# ��������Id
	sbSolutionId int(11) NOT NULL,									# �籣����Id 
	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id�����ֻ��һ����Ӧ�̣�Ĭ��ѡ��
	vendorServiceId int(11) DEFAULT NULL,							# ��Ӧ�̷���Id�����ֻ��һ����Ӧ�̷���Ĭ��ѡ��
	personalSBBurden tinyint(4) DEFAULT NULL,						# �籣���˲��ֹ�˾�е����ǣ���
  	description varchar(1000) DEFAULT NULL,							# ����
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
	orderCbId int(11) NOT NULL AUTO_INCREMENT,						# �����̱�Id������
	orderHeaderId int(11) NOT NULL,									# ��������Id
	cbSolutionId int(11) NOT NULL,									# �̱�����Id 
	freeShortOfMonth tinyint(4) DEFAULT NULL,						# ��ȫ����ѣ���/�� - �����ְ����ְ�������������chargeFullMonth����ͬʱѡ���ǡ�
	chargeFullMonth	tinyint(4) DEFAULT NULL,						# ��ȫ�¼Ʒѣ���/�� - �����ְ����ְ�������������freeShortOfMonth����ͬʱѡ���ǡ�
  	description varchar(1000) DEFAULT NULL,							# ����
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
	orderPerformanceId int(11) NOT NULL AUTO_INCREMENT,				# ������Ч����Id������
	orderHeaderId int(11) NOT NULL,									# �Ͷ���ͬId
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ�����ʡ�����������
	performanceSolutionId int(11) NOT NULL,							# ��Ч����Id�������� - ҵ�� - �̱�������
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʱ���ã�
	fix double DEFAULT NULL,										# �̶�����ʱ���ã�
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ���ã�
	discount double DEFAULT NULL,									# �ۿۣ���ʱ���ã�
	multiple double DEFAULT NULL,									# ���ʣ���ʱ���ã�
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�
	startDate datetime DEFAULT NULL,								# ��Чʱ��
	endDate datetime DEFAULT NULL,									# ����ʱ��
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ���ʱ���ã�
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
	description varchar(1000) DEFAULT NULL,							# ����
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
	orderOTId int(11) NOT NULL AUTO_INCREMENT,						# �����Ӱ�����Id������
	orderHeaderId int(11) NOT NULL,									# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ��
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʹ�û�����Դʱ���֣�
	fix double DEFAULT NULL,										# �̶��𣨵�ʹ�û�����Դʱ���֣�
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ�����㣩
	discount double DEFAULT NULL,									# �ۿۣ���ʱ�����㣩
	multiple double DEFAULT NULL,									# ���ʣ�1, 1.5, 2, 3��
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�
	startDate datetime DEFAULT NULL,								# ��Ч����
	endDate datetime DEFAULT NULL,									# ��������
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ�Ĭ�� - �Ӱ�Сʱ�������ʡ��ۿۣ����� or ������Դ�����Զ��壩
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
	description varchar(1000) DEFAULT NULL,							# ����
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
	orderLeaveId int(11) NOT NULL AUTO_INCREMENT,					# �����ݼ�����Id������
	orderHeaderId int(11) NOT NULL,									# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ��
	annualLeaveRuleId int(11) DEFAULT NULL,							# ��ٹ��򣨽����ʹ�ã�
	legalQuantity smallint(4) DEFAULT NULL,							# ����������Сʱ����ֻ����ٲ�ʹ��
	benefitQuantity smallint(4) DEFAULT NULL,						# ����������Сʱ��
	cycle tinyint(4) DEFAULT NULL,									# ʹ�����ڣ������ڣ������꣬������ - �������ڵ�һ���꣩
	probationUsing tinyint(4) DEFAULT NULL,							# �������Ƿ��ʹ��
	delayUsing tinyint(4) DEFAULT NULL,								# �����ӳ�ʹ��
	legalQuantityDelayMonth tinyint(4) DEFAULT NULL,				# ������δʹ���������������0��ʾ�������ڣ�		
	benefitQuantityDelayMonth tinyint(4) DEFAULT NULL,				# ������δʹ���������������0��ʾ�������ڣ�
	description varchar(1000) DEFAULT NULL,							# ����
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
	orderOtherId int(11) NOT NULL AUTO_INCREMENT,					# ������������Id������
	orderHeaderId int(11) NOT NULL,									# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId�������� - ���� - ��Ŀ�����籣���̱����ݼ١��Ӱࡢ�����������п�Ŀ��
	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʹ�û�����Դʱ���֣�
	fix double DEFAULT NULL,										# �̶��𣨵�ʹ�û�����Դʱ���֣�
	quantity smallint(4) DEFAULT NULL,								# ����
	discount double DEFAULT NULL,									# �ۿ�
	multiple double DEFAULT NULL,									# ����
	cycle tinyint(4) DEFAULT NULL,									# �������ڣ�������һ���ԣ�
	startDate datetime DEFAULT NULL,								# ��Ч����
	endDate datetime DEFAULT NULL,									# ��������
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ�Ĭ�� - �Ӱ�Сʱ�������ʡ��ۿۣ����� or ������Դ�����Զ��壩
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
	showToTS tinyint(4) DEFAULT NULL,								# ��ʾ�����ڱ���/��
	description varchar(1000) DEFAULT NULL,							# ����
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
	vendorId int(11) NOT NULL AUTO_INCREMENT,						# ��Ӧ��Id������
	accountId int(11) NOT NULL,										# �˻�Id	
  	corpId	int(11) default null,									# ����Inhouse ��hrService
	nameZH varchar(200) DEFAULT NULL,								# ��Ӧ����������
  	nameEN varchar(200) DEFAULT NULL,								# ��Ӧ��Ӣ������
  	cityId int(11) DEFAULT NULL,									# ��Ӧ�����ڳ���ID
  	contractStartDate datetime DEFAULT NULL,						# ��ͬ��ʼ����	
  	contractEndDate datetime DEFAULT NULL,							# ��ͬ��������
  	charterNumber varchar(100) DEFAULT NULL,						# ��Ӧ��ִ�պ�
  	bankId int(11) DEFAULT NULL,									# ��Ӧ������ID
  	bankName varchar(200) DEFAULT NULL,								# ��Ӧ����������
  	bankAccount varchar(100) DEFAULT NULL,							# ��Ӧ�������˺�
  	bankAccountName varchar(200) DEFAULT NULL,						# ��Ӧ�������˺���
  	type tinyint(4) DEFAULT NULL,									# ��Ӧ������ ���ⲿ�ڲ���
  	attachment varchar(1000) DEFAULT NULL,							# ��Ӧ�̸������·��
  	description varchar(1000) DEFAULT NULL,							# ����
  	legalEntity int(11) DEFAULT NULL,								# ����ʵ�壨��������ձ� Hro_Mgr_Entity
  	branch varchar(25) DEFAULT NULL,								# ���ţ���ID��
  	owner varchar(25) DEFAULT NULL,						  			# �����ˣ����Position Id��
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:������
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
  	vendorContactId int(11) NOT NULL AUTO_INCREMENT,				# ��Ӧ����ϵ��Id������
  	vendorId int(11) NOT NULL,										# ��Ӧ��Id
  	accountId int(11) NOT NULL,										# �˻�Id 
  	corpId int(11) DEFAULT NULL,									# �˻�Id 
  	salutation tinyint(4) DEFAULT NULL,								# �ƺ�����ʿ/Ůʿ
  	nameZH varchar(200) DEFAULT NULL,								# ��ϵ���������ģ�
  	nameEN varchar(200) DEFAULT NULL,								# ��ϵ������Ӣ�ģ�		
  	title varchar(200) DEFAULT NULL,								# ְλ
  	department varchar(100) DEFAULT NULL,							# ����
  	bizPhone varchar(50) DEFAULT NULL,								# �����绰��
  	personalPhone varchar(50) DEFAULT NULL,							# ˽�˵绰��
  	bizMobile varchar(50) DEFAULT NULL,								# �����ֻ���
  	personalMobile varchar(50) DEFAULT NULL,						# ˽���ֻ���
  	otherPhone varchar(50) DEFAULT NULL,							# ������ϵ��ʽ
  	fax varchar(50) DEFAULT NULL,									# ����
  	bizEmail varchar(200) DEFAULT NULL,								# ������������
  	personalEmail varchar(200) DEFAULT NULL,						# ˽�˵�������
  	comment varchar(1000) DEFAULT NULL,								# ˵��
  	canAdvBizEmail tinyint(4) DEFAULT NULL,							# ������������
  	canAdvPersonalEmail tinyint(4) DEFAULT NULL,					# ˽����������	
  	canAdvBizSMS tinyint(4) DEFAULT NULL,							# ������������
  	canAdvPersonalSMS tinyint(4) DEFAULT NULL,						# ˽�˶�������
  	cityId int(11) DEFAULT NULL,									# ��ϵ�����ڳ���ID
  	address varchar(200) DEFAULT NULL,								# ��ַ
  	postcode varchar(50) DEFAULT NULL,								# �ʱ�
  	branch varchar(25) DEFAULT NULL,								# ���ţ���ID��
  	owner varchar(25) DEFAULT NULL,						  			# �����ˣ����Position Id ��
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
	venderUserId int(11) NOT NULL AUTO_INCREMENT,					# �û�ID
  	accountId int(11) NOT NULL,										# �˻�ID
  	vendorId int(11) NOT NULL,										# ��Ӧ��ID
  	vendorContactId int(11) NOT NULL,								# ��Ӧ����ϵ��ID
  	username varchar(50) DEFAULT NULL,								# �û���
  	password varchar(50) DEFAULT NULL,								# ����
  	bindIP varchar(50) DEFAULT NULL,								# ��IP��ַ�������ֻ��ָ��IP��ַ����
  	lastLogin datetime DEFAULT NULL,								# ����¼ʱ��
  	lastLoginIP varchar(50) DEFAULT NULL,							# ����¼IP��ַ
  	superUserId int(11) DEFAULT NULL,								# ��Ӧ�̽��治����
  	validatedSuperUser tinyint(4) DEFAULT NULL,						# ��Ӧ�̽��治���ã�Yes��No��
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
	serviceId int(11) NOT NULL AUTO_INCREMENT,						# ��Ӧ�̷���Id
	vendorId int(11) NOT NULL,										# ��Ӧ��Id
	cityId int(11) NOT NULL,										# ʡ�� - ����
	sbHeaderId int(11) NOT NULL,									# �籣����
	serviceIds varchar(1000) NOT NULL,								# ����{0:1:2:3:4}
	serviceFee double NOT NULL,										# �����
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

CREATE TABLE hro_biz_attendance_leave_header (						# ���ѣ������Ű�����Ҫȷ������Ƿ���Բ�����ʷ��
	leaveHeaderId int(11) NOT NULL AUTO_INCREMENT,					# ���Id
	accountId int(11) NOT NULL,										# �˻�Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	employeeId int(11) NOT NULL,									# ��ԱId
	contractId int(11) NOT NULL,									# ����Э��Id
	itemId int(11) NOT NULL,										# ��ĿId����ѡ��Leave���Ŀ�Ŀ	
	retrieveId int(11) DEFAULT NULL,								# ʹ�����٣�����Ա��������ʱ���´����ʹ�ã�
	estimateStartDate datetime DEFAULT NULL,						# Ԥ����ٿ�ʼʱ�䣨���磬2013-08-01 14:30��ʱ���Ϊÿ��Сʱ�����Ƿ���ʣ����ڿ����������̬����
	estimateEndDate datetime DEFAULT NULL,							# Ԥ����ٽ���ʱ��
	actualStartDate datetime DEFAULT NULL,							# ʵ����ٿ�ʼʱ��
	actualEndDate datetime DEFAULT NULL,							# ʵ����ٽ���ʱ��
	estimateLegalHours double(8,1) DEFAULT NULL,					# Ԥ�Ʒ�����Сʱ����ֻ����ٻ�ʹ�ô�����������ֹ�����
	estimateBenefitHours double(8,1) DEFAULT NULL,					# Ԥ�Ƹ�����Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
	actualLegalHours double(8,1) DEFAULT NULL,						# ʵ�ʷ�����Сʱ����ֻ����ٻ�ʹ�ô�����������ֹ�����
	actualBenefitHours double(8,1) DEFAULT NULL,					# ʵ�ʸ�����Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
	attachment varchar(1000) DEFAULT NULL,							# ����
	retrieveStatus tinyint(4) DEFAULT NULL,							# ����״̬��1:������2:�ύ - �����٣�3:���٣�4:�ܾ����٣������٣�δOrder Go���´�����Order Go���´���
	unread tinyint(4) DEFAULT NULL,									# �Ƿ�δ��(�������ֻ�ģ��ͳ�ƣ�1 δ����2 �Ѷ�);
	dataFrom tinyint(4) DEFAULT NULL,								# ������Դ��1��ϵͳ¼�룻2���������ɣ�
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ���״̬��1:�½���2:�ύ - ����׼��3:��׼��4:�ܾ���٣�5:�ѽ��㣩��״̬Ϊ��׼��Leave������㣬ֻ�����ٲ�ʹ��Actual�ֶ�
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
	leaveDetailId int(11) NOT NULL AUTO_INCREMENT,					# ��ٴӱ�Id
	leaveHeaderId int(11) NOT NULL,									# �������Id
	timesheetId int(11) DEFAULT NULL,								# ���ڱ�Id
	estimateStartDate datetime DEFAULT NULL,						# Ԥ����ٿ�ʼʱ�䣨���磬2013-08-01 14:30��ʱ���Ϊÿ��Сʱ�����Ƿ���ʣ����ڿ����������̬����
	estimateEndDate datetime DEFAULT NULL,							# Ԥ����ٽ���ʱ��
	actualStartDate datetime DEFAULT NULL,							# ʵ����ٿ�ʼʱ��
	actualEndDate datetime DEFAULT NULL,							# ʵ����ٽ���ʱ��
	estimateLegalHours double(8,1) DEFAULT NULL,					# Ԥ�Ʒ�����Сʱ����ֻ����ٻ�ʹ�ô�����������ֹ�����
	estimateBenefitHours double(8,1) DEFAULT NULL,					# Ԥ�Ƹ�����Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
	actualLegalHours double(8,1) DEFAULT NULL,						# ʵ�ʷ�����Сʱ����ֻ����ٻ�ʹ�ô�����������ֹ�����
	actualBenefitHours double(8,1) DEFAULT NULL,					# ʵ�ʸ�����Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
	attachment varchar(1000) DEFAULT NULL,							# ����
	retrieveStatus tinyint(4) DEFAULT NULL,							# ����״̬��1:������2:�ύ - �����٣�3:���٣�4:�ܾ����٣������٣�δOrder Go���´�����Order Go���´���
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ���״̬��1:�½���2:�ύ - ����׼��3:��׼��4:�ܾ���٣�5:�ѽ��㣩��״̬Ϊ��׼��Leave������㣬ֻ�����ٲ�ʹ��Actual�ֶ�
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

CREATE TABLE hro_biz_attendance_ot_header (							# ���ѣ������Ű���Ͷ���ͬ����Ҫ�趨�Ӱ����ƣ����磬ÿ�ܻ�ÿ�¼Ӱ಻�ܳ�����Сʱ�������ǼӰ��Ƿ���ҪԤ����
	otHeaderId int(11) NOT NULL AUTO_INCREMENT,						# �Ӱ�Id
	accountId int(11) NOT NULL,										# �˻�Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	employeeId int(11) NOT NULL,									# ��ԱId
	contractId int(11) NOT NULL,									# ����Э��Id
	itemId int(11) NOT NULL,										# ��ĿId����ѡ��OT���Ŀ�Ŀ	
	estimateStartDate datetime DEFAULT NULL,						# Ԥ�ƼӰ࿪ʼʱ�䣨���磬2013-08-01 14:30��ʱ���Ϊÿ��Сʱ�����Ƿ���ʣ����ڿ����������̬����
	estimateEndDate datetime DEFAULT NULL,							# Ԥ�ƼӰ����ʱ��
	actualStartDate datetime DEFAULT NULL,							# ʵ�ʼӰ࿪ʼʱ��
	actualEndDate datetime DEFAULT NULL,							# ʵ�ʼӰ����ʱ��
	estimateHours double(8,1) DEFAULT NULL,							# Ԥ�ƼӰ�Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
	actualHours double(8,1) DEFAULT NULL,							# ʵ�ʼӰ�Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
	unread tinyint(4) DEFAULT NULL,									# �Ƿ�δ��(�������ֻ�ģ��ͳ�ƣ�1 δ����2 �Ѷ�);
	dataFrom tinyint(4) DEFAULT NULL,								# ������Դ��1��ϵͳ¼�룻2���������ɣ�
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# �Ӱ�״̬��1:�½���2:�ύ - ����׼��3:��׼��4:�ύ - ��ȷ�ϣ�5:ȷ�ϣ�6:�ܾ���7���ѽ��㣩ֻ��״̬Ϊȷ�ϵ�OT�������
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
	otDetailId int(11) NOT NULL AUTO_INCREMENT,						# �Ӱ�����Id
	otHeaderId int(11) NOT NULL,									# �Ӱ�ӱ�Id
	timesheetId int(11) DEFAULT NULL,								# ���ڱ�Id
	estimateStartDate datetime DEFAULT NULL,						# Ԥ�ƼӰ࿪ʼʱ�䣨���磬2013-08-01 14:30��ʱ���Ϊÿ��Сʱ�����Ƿ���ʣ����ڿ����������̬����
	estimateEndDate datetime DEFAULT NULL,							# Ԥ�ƼӰ����ʱ��
	actualStartDate datetime DEFAULT NULL,							# ʵ�ʼӰ࿪ʼʱ��
	actualEndDate datetime DEFAULT NULL,							# ʵ�ʼӰ����ʱ��
	estimateHours double(8,1) DEFAULT NULL,							# Ԥ�ƼӰ�Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
	actualHours double(8,1) DEFAULT NULL,							# ʵ�ʼӰ�Сʱ����Сʱ�����㽫����Ա����ѡʱ�����估�����Űࣩ���������ֹ�����
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# �Ӱ�״̬��1:�½���2:�ύ - ����׼��3:��׼��4:�ύ - ��ȷ�ϣ�5:ȷ�ϣ�6:�ܾ���7���ѽ��㣩ֻ��״̬Ϊȷ�ϵ�OT�������
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
	batchId int(11) NOT NULL AUTO_INCREMENT,						# ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) DEFAULT NULL,									# ����ʵ��Id
	businessTypeId int(11) DEFAULT NULL,							# ҵ������Id
	clientId int(11) DEFAULT NULL,									# �ͻ�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
	orderId int(11) DEFAULT NULL,									# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	employeeId int(11) DEFAULT NULL,								# ��ԱId
	monthly varchar(25) DEFAULT NULL,								# �����·ݣ�����2013/9��
  	weekly varchar(25) DEFAULT NULL,								# �����ܴΣ�����2013/35��
	startDate datetime DEFAULT NULL,								# ��ʼʱ�� - ָBatch���е�ʱ��
	endDate datetime DEFAULT NULL,		 							# ����ʱ�� - ָBatch���е�ʱ��
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:�ѽ��㣬6:ȡ��������Ҫ��Timesheet Header��������ϵ								
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
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# ��������Id
  	accountId int(11) NOT NULL,										# �˻�Id
  	employeeId int(11) NOT NULL,									# ��ԱId
  	contractId int(11) NOT NULL,									# ����Э��Id
  	clientId int(11) NOT NULL,										# �ͻ�Id
  	corpId int(11) NOT NULL,										# �ͻ�Id
  	orderId int(11) NOT NULL,										# ����Id
  	batchId int(11) DEFAULT 0,										# ����Id
  	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
  	weekly varchar(25) DEFAULT NULL,								# �ܴΣ�����2013/35��
  	startDate datetime NOT NULL,									# ��ʼ����
  	endDate datetime NOT NULL, 										# ��������
  	totalWorkHours double NOT NULL,									# ����Сʱ�����������ںϼƣ�
  	totalWorkDays double NOT NULL, 									# �����������������ںϼƣ�
  	totalFullHours double NOT NULL,									# ȫ��Сʱ�����������ںϼƣ�
  	totalFullDays double NOT NULL, 									# ȫ���������������ںϼƣ�
  	needAudit tinyint(4) NOT NULL,									# ��Ҫ��������/��		
  	isNormal tinyint(4) NOT NULL,									# �������ڣ���/��
  	attachment varchar(1000) DEFAULT NULL, 							# ����
  	description varchar(1000) DEFAULT NULL, 						# ����д�˻�ԭ��
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:�ѽ��㣬6:ȡ����
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
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# ���ڴӱ�Id
  	headerId int(11) NOT NULL,										# ��������Id
  	day datetime NOT NULL,											# ����
  	dayType tinyint(4) NOT NULL,									# �������ͣ��ο������е��������ͣ�
  	workHours double NOT NULL,										# ����Сʱ����ʵ�ʣ�
  	fullHours double NOT NULL,										# ����ȫ��Сʱ��
  	signIn datetime DEFAULT NULL,									# �򿨿�ʼʱ��
  	signOut datetime DEFAULT NULL,									# �򿨽���ʱ��
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
  	allowanceId int(11) NOT NULL AUTO_INCREMENT,					# ���ڽ���Id
  	headerId int(11) NOT NULL,										# ��������Id
  	itemId int(11) NOT NULL,										# ��ĿId
  	base double DEFAULT NULL,										# ����
	baseFrom int(11) DEFAULT NULL,									# ������Դ�������Ŀ����ItemGroup��
	percentage double DEFAULT NULL,									# ��������ʹ�û�����Դʱ���֣�
	fix double DEFAULT NULL,										# �̶��𣨵�ʹ�û�����Դʱ���֣�
	quantity smallint(4) DEFAULT NULL,								# ����
	discount double DEFAULT NULL,									# �ۿ�
	multiple double DEFAULT NULL,									# ����
	resultCap double DEFAULT NULL,									# ����������		
	resultFloor double DEFAULT NULL,								# ����������
	formularType tinyint(4) DEFAULT NULL,							# ���㹫ʽ���ͣ�Ĭ�� - ���������ʡ��ۿۣ����� or ������Դ������+�̶��𣩣��Զ��壩
	formular varchar(1000) DEFAULT NULL,							# ���㹫ʽ
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
	employeeSettlementId int(11) NOT NULL AUTO_INCREMENT,			# ��Ա�ɱ�/Ӫ��Id������
	contractId int(11) NOT NULL,									# �Ͷ���ͬId
	itemId int(11) DEFAULT NULL,									# Ԫ��Id
	baseFrom int(11) DEFAULT NULL,									# Ԫ���飨��ItemGroup�� 
	divideType int(11) DEFAULT NULL,								# ��ַ�ʽ��1�����շ���ʵ��2������ҵ������3�����ղ��ţ�
	percentage double DEFAULT NULL,									# ������0-100С����
	fix double DEFAULT NULL,										# �̶���0-100000��
	startDate datetime DEFAULT NULL,								# ��Чʱ�䣨Ԥ���ֶΣ�
	endDate datetime DEFAULT NULL,								    # ����ʱ�䣨Ԥ���ֶΣ�
	resultCap double DEFAULT NULL,									# ���������ޣ�Ԥ���ֶΣ�
	resultFloor double DEFAULT NULL,								# ���������ޣ�Ԥ���ֶΣ�
	description varchar(1000) DEFAULT NULL,							# ����
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
 	serialId int(11) NOT NULL ,										# ��ʶ���жϼ�¼�Ƿ��ظ� 
 	accountId int(11) NOT NULL ,
 	corpId int(11) DEFAULT NULL , 
 	employeeId int(11) NOT NULL,         							# Ա��ID
 	employeeNo varchar(50) DEFAULT NULL,       						# Ա����� 
 	employeeNameZH varchar(200) DEFAULT NULL,       				# Ա�����������ģ�
 	employeeNameEN varchar(200) DEFAULT NULL,       				# Ա��������Ӣ�ģ�
 	signDate date DEFAULT NULL,        								# �����ڣ�2014-06-04��
 	signTime time DEFAULT NULL,        								# ��ʱ�䣨14:27��
 	signType varchar(50) DEFAULT NULL,       						# ǩ����ʽ 
 	machineNo varchar(50) DEFAULT NULL,       						# �豸��� 
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
	headerId int(11) NOT NULL AUTO_INCREMENT ,						# ���뿼������ID
	batchId int(11) NOT NULL ,										# ����ID
	accountId int(11) DEFAULT NULL ,
	clientId int(11) DEFAULT NULL ,
	corpId int(11) NULL DEFAULT NULL ,
	contractId int(11) DEFAULT NULL ,								# �Ͷ���ͬID
	monthly varchar(25) DEFAULT NULL ,								# ���ڱ��·�
	totalWorkHours double NOT NULL,									# ����Сʱ�����������ںϼƣ�
  	totalWorkDays double NOT NULL, 									# �����������������ںϼƣ�
  	totalFullHours double NOT NULL,									# ȫ��Сʱ�����������ںϼƣ�
  	totalFullDays double NOT NULL, 									# ȫ���������������ںϼƣ�
	attachment varchar(1000) DEFAULT NULL ,							# ����
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
	detailId int(11) NOT NULL AUTO_INCREMENT ,						# ���뿼�ڴӱ�ID
	headerId int(11) NOT NULL ,										
	itemId int(11) DEFAULT NULL ,										# ��ĿID
	hours double DEFAULT NULL ,											# ����Сʱ��
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
use HRO;

DROP TABLE IF EXISTS hro_history_user;								# �û���ʷ��¼

CREATE TABLE hro_history_user (
	historyId bigint(13) NOT NULL AUTO_INCREMENT,	 				
	workflowId int(11) DEFAULT NULL,								# ������Id
  	userId int(11) NOT NULL,										
  	accountId int(11) NOT NULL,										
  	staffId int(11) NOT NULL,										
  	username varchar(50) DEFAULT NULL,								
  	password varchar(50) DEFAULT NULL,								
  	bindIP varchar(50) NULL,										
  	lastLogin datetime DEFAULT NULL,								
  	lastLoginIP varchar(50) NULL,	
  	userIds varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	hisTitle varchar(200) not NULL,									# �������ƣ�2013-01-01 15:08:31 Kevin Jin �޸ģ�
  	hisDescription varchar(5000) DEFAULT NULL,						# ������ͨ���ǲ��������µ�����
  	modifyBy varchar(25) DEFAULT NULL,								# ��������
  	modifyDate datetime DEFAULT NULL,								# ������ʱ��
  	PRIMARY KEY (historyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_history_sec_staff (
	historyId bigint(13) NOT NULL AUTO_INCREMENT,	 				
	workflowId int(11) DEFAULT NULL,								# ������Id
	staffId int(11) ,												# Ա��ID
	accountId int(11) NOT NULL,										# �˻�ID
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
  	hisTitle varchar(200) not NULL,									# �������ƣ�2013-01-01 15:08:31 Kevin Jin �޸ģ�
  	hisDescription varchar(5000) DEFAULT NULL,						# ������ͨ���ǲ��������µ�����
  	modifyBy varchar(25) DEFAULT NULL,								# ��������
  	modifyDate datetime DEFAULT NULL,								# ������ʱ��
  	PRIMARY KEY (historyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_history (
	historyId bigint(13) NOT NULL AUTO_INCREMENT,					# ��ʷ��¼�� 				
	accountId int(11) NOT NULL,										# �˻�ID
	workflowId int(11) DEFAULT NULL,								# ������Id
	objectId int(11) NOT NULL,										# ����ID
	objectType tinyint(4) NOT NULL,									# ����Type��1:��ʷ��2:��������
	accessAction varchar(200) DEFAULT NULL,							# ģ���еĲ�������
	objectClass varchar(200) DEFAULT NULL,							# �������������磬com.kan.base.domain.BaseVO��
	serviceBean varchar(200) DEFAULT NULL,							# Service Name�����磬Spring�����Bean��
	serviceMethod varchar(200) DEFAULT NULL,						# Service Method�����磬insertStaff��
	hisTitle varchar(200) NOT NULL,									# �������ƣ�2013-01-01 15:08:31 Kevin Jin �޸ģ�
  	hisDescription varchar(5000) DEFAULT NULL,						# ����������ͨ���ǲ��������µ�����
  	passObject  text DEFAULT NULL,									# �������ͨ��Ŀ������Jason�ַ���
  	failObject  text DEFAULT NULL,									# �������ʧ��Ŀ������Jason�ַ���
	tempStatus tinyint(4) DEFAULT NULL, 							# ������ʶ���������˵������1��ͬ�⣻2��ͬ�⣩
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ��ʷ��¼״̬��1:ʹ���У�2:�ѹ���
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,								# Ŀ�������󴴽���
  	createDate datetime DEFAULT NULL,								# Ŀ�������󴴽�ʱ��
  	modifyBy varchar(25) DEFAULT NULL,								# Ŀ�������������
  	modifyDate datetime DEFAULT NULL,								# Ŀ�����������ʱ��
  	PRIMARY KEY (historyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



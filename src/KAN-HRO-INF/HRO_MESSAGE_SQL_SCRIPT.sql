use HRO;

DROP TABLE IF EXISTS hro_message_mail;								# �ʼ����У����з����ʼ��˴��ݴ�
DROP TABLE IF EXISTS hro_message_sms;								# ���Ŷ��У����з��Ͷ��Ŵ˴��ݴ�
DROP TABLE IF EXISTS hro_message_info;								# ��Ϣ���У�����վ�ڽ�����Ϣ�˴��ݴ�
DROP TABLE IF EXISTS hro_message_template;							# ��Ϣģ�壬�ʼ������ź�վ����Ϣ����ʹ�ã���Ϲ�������
DROP TABLE IF EXISTS hro_message_regular;							# ��Ϣ���͹���,������Ϣ���մ˹�����

CREATE TABLE hro_message_mail (
  	mailId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,									# ϵͳId��HRO��HRM��
  	accountId int(11) NOT NULL,										# Account Id
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	regularId int(11) DEFAULT NULL,									# ѭ������ID
  	title varchar(200) NOT NULL,									# �ʼ�����
  	content varchar(5000) DEFAULT NULL,								# �ʼ�����
  	contentType tinyint(4) DEFAULT NULL,							# �ʼ��������ͣ�Text��HTML��##1:���ı�##2:����ʽ
  	reception varchar(200) DEFAULT NULL,							# �ռ��ˣ�ͬһ�ʼ����͸�1λ������
  	templateId varchar(200) DEFAULT NULL,							# �ʼ�ģ��ID
  	showName varchar(100) DEFAULT NULL,								# ��Ҫ��ʾ�ı���
  	sentAs varchar(100) DEFAULT NULL,								# ������
  	smtpHost varchar(100) DEFAULT NULL,								# SMTP������
  	smtpPort varchar(25) DEFAULT NULL,								# SMTP�˿�
  	username varchar(100) DEFAULT NULL,								# SMTP�û���
  	password varchar(100) DEFAULT NULL,								# SMTP���루����ܱ��棩
  	smtpAuthType tinyint(4) DEFAULT NULL,							# �ʼ������Ƿ���Ҫ��֤
  	lastSendingTime datetime DEFAULT NULL,							# ����Է���ʱ��
  	sendingTimes tinyint(4) DEFAULT NULL,							# �ѳ��Է��ʹ���
  	sentTime datetime DEFAULT NULL,									# ���շ���ʱ��
  	sendType tinyint(4) DEFAULT NULL,								# ��������
  	toSendTime datetime DEFAULT NULL,								# ������ʱ��
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 1:�����ͣ�2:�����У�3:�ѷ��ͣ�4:��ͣ
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (mailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
	
CREATE TABLE hro_message_sms (
  	smsId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,									# ϵͳId��HRO��HRM��
  	accountId int(11) NOT NULL,										# Account Id
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	content varchar(500) DEFAULT NULL,								# ��������
  	reception varchar(50) DEFAULT NULL,								# �����ռ��ˣ�ͬһ���ŷ��͸�1λ������
  	lastSendingTime datetime DEFAULT NULL,							# ����Է���ʱ��
  	sendingTimes tinyint(4) DEFAULT NULL,							# �ѳ��Է��ʹ���
  	sentTime datetime DEFAULT NULL,									# ���շ���ʱ��
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 1:�����ͣ�2:�����У�3:�ѷ��ͣ�4:��ͣ
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (smsId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
	
CREATE TABLE hro_message_info (
  	infoId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,									# ϵͳId��HRO��HRM��
  	accountId int(11) NOT NULL,										# Account Id
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	title varchar(200) NOT NULL,									# վ����Ϣ����
  	content varchar(500) DEFAULT NULL,								# վ����Ϣ����
  	reception varchar(25) DEFAULT NULL,								# ��Ϣ�����ߣ�ϵͳUserId��
  	expiredTime datetime DEFAULT NULL,								# ��Ϣ����ʱ�䣨ͨ��90�죩
  	readTime datetime DEFAULT NULL,									# �Ķ�ʱ��
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 1:�½���2:������3:�Ѷ� #[��Է�����]
  	receptionStatus  tinyint(4) DEFAULT NULL,						# 2:δ����3:�Ѷ� #[��Խ�����]
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (infoId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_message_template (
  	templateId int(11) NOT NULL AUTO_INCREMENT,						# Message Template ID
  	accountId int(11) NOT NULL,										# �˻���Ϣ����1��Ϊ����ģ��
  	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
  	nameZH varchar(200) DEFAULT NULL,								# ��Ϣģ��������
  	nameEN varchar(200) DEFAULT NULL,								# ��Ϣģ��Ӣ����
  	templateType tinyint(4) DEFAULT NULL,							# ��Ϣģ�����ͣ�1:�ʼ���2:���ţ�3:վ����Ϣ
  	content varchar(5000) DEFAULT NULL,								# ��Ϣģ�����ݣ������������${value}
  	contentType tinyint(4) DEFAULT NULL,							# ��Ϣģ���������ͣ�1:Text��2:HTML
  	description varchar(1000) DEFAULT NULL,							# ��Ϣģ������
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

CREATE TABLE hro_message_regular (
  	regularId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,									# ϵͳId��HRO��HRM��
  	accountId int(11) NOT NULL,										# Account Id
  	startDateTime datetime DEFAULT NULL,                            # ��ʱʱ��
  	endDateTime datetime DEFAULT NULL,								# ʧЧʱ��
  	repeatType tinyint(4) DEFAULT NULL,								# �ظ�ģʽ�����գ����ܣ����£�
  	period int(11) DEFAULT NULL,									# ���
  	additionalPeriod int(11) DEFAULT NULL, 							# ���Ӽ��ʱ��
  	weekPeriod varchar(1000) DEFAULT NULL,  						# ���ڼ��
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
  	PRIMARY KEY (regularId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

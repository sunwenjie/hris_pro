use HRO;

DROP TABLE IF EXISTS hro_message_mail;								# 邮件队列，所有发送邮件此处暂存
DROP TABLE IF EXISTS hro_message_sms;								# 短信队列，所有发送短信此处暂存
DROP TABLE IF EXISTS hro_message_info;								# 消息队列，所有站内交互信息此处暂存
DROP TABLE IF EXISTS hro_message_template;							# 消息模板，邮件、短信和站内信息都会使用（结合工作流）
DROP TABLE IF EXISTS hro_message_regular;							# 消息发送规则,所有消息参照此规则发送

CREATE TABLE hro_message_mail (
  	mailId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,									# 系统Id，HRO、HRM等
  	accountId int(11) NOT NULL,										# Account Id
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	regularId int(11) DEFAULT NULL,									# 循环周期ID
  	title varchar(200) NOT NULL,									# 邮件主题
  	content varchar(5000) DEFAULT NULL,								# 邮件内容
  	contentType tinyint(4) DEFAULT NULL,							# 邮件内容类型（Text或HTML）##1:纯文本##2:含样式
  	reception varchar(200) DEFAULT NULL,							# 收件人，同一邮件发送给1位接收者
  	templateId varchar(200) DEFAULT NULL,							# 邮件模板ID
  	showName varchar(100) DEFAULT NULL,								# 需要显示的别名
  	sentAs varchar(100) DEFAULT NULL,								# 发送人
  	smtpHost varchar(100) DEFAULT NULL,								# SMTP服务器
  	smtpPort varchar(25) DEFAULT NULL,								# SMTP端口
  	username varchar(100) DEFAULT NULL,								# SMTP用户名
  	password varchar(100) DEFAULT NULL,								# SMTP密码（需加密保存）
  	smtpAuthType tinyint(4) DEFAULT NULL,							# 邮件发送是否需要验证
  	lastSendingTime datetime DEFAULT NULL,							# 最后尝试发送时间
  	sendingTimes tinyint(4) DEFAULT NULL,							# 已尝试发送次数
  	sentTime datetime DEFAULT NULL,									# 最终发送时间
  	sendType tinyint(4) DEFAULT NULL,								# 发送类型
  	toSendTime datetime DEFAULT NULL,								# 待发送时间
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 1:待发送，2:发送中，3:已发送，4:暂停
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
  	systemId tinyint(4) NOT NULL,									# 系统Id，HRO、HRM等
  	accountId int(11) NOT NULL,										# Account Id
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	content varchar(500) DEFAULT NULL,								# 短信内容
  	reception varchar(50) DEFAULT NULL,								# 短信收件人，同一短信发送给1位接收者
  	lastSendingTime datetime DEFAULT NULL,							# 最后尝试发送时间
  	sendingTimes tinyint(4) DEFAULT NULL,							# 已尝试发送次数
  	sentTime datetime DEFAULT NULL,									# 最终发送时间
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 1:待发送，2:发送中，3:已发送，4:暂停
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
  	systemId tinyint(4) NOT NULL,									# 系统Id，HRO、HRM等
  	accountId int(11) NOT NULL,										# Account Id
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	title varchar(200) NOT NULL,									# 站内信息标题
  	content varchar(500) DEFAULT NULL,								# 站内信息内容
  	reception varchar(25) DEFAULT NULL,								# 信息接收者（系统UserId）
  	expiredTime datetime DEFAULT NULL,								# 信息过期时间（通常90天）
  	readTime datetime DEFAULT NULL,									# 阅读时间
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 1:新建，2:发布，3:已读 #[针对发送者]
  	receptionStatus  tinyint(4) DEFAULT NULL,						# 2:未读，3:已读 #[针对接收者]
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
  	accountId int(11) NOT NULL,										# 账户信息，“1”为公共模板
  	corpId int(11) default null,									# 企业ID，用于Inhouse使用
  	nameZH varchar(200) DEFAULT NULL,								# 消息模板中文名
  	nameEN varchar(200) DEFAULT NULL,								# 消息模板英文名
  	templateType tinyint(4) DEFAULT NULL,							# 消息模板类型，1:邮件，2:短信，3:站内信息
  	content varchar(5000) DEFAULT NULL,								# 消息模板内容，传入参数类似${value}
  	contentType tinyint(4) DEFAULT NULL,							# 消息模板内容类型，1:Text，2:HTML
  	description varchar(1000) DEFAULT NULL,							# 消息模板描述
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
  	systemId tinyint(4) NOT NULL,									# 系统Id，HRO、HRM等
  	accountId int(11) NOT NULL,										# Account Id
  	startDateTime datetime DEFAULT NULL,                            # 定时时间
  	endDateTime datetime DEFAULT NULL,								# 失效时间
  	repeatType tinyint(4) DEFAULT NULL,								# 重复模式（按日，按周，按月）
  	period int(11) DEFAULT NULL,									# 间隔
  	additionalPeriod int(11) DEFAULT NULL, 							# 附加间隔时间
  	weekPeriod varchar(1000) DEFAULT NULL,  						# 星期间隔
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

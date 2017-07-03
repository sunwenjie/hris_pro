use HRO;

DROP TABLE IF EXISTS hro_history_user;								# 用户历史记录

CREATE TABLE hro_history_user (
	historyId bigint(13) NOT NULL AUTO_INCREMENT,	 				
	workflowId int(11) DEFAULT NULL,								# 工作流Id
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
  	hisTitle varchar(200) not NULL,									# 备份名称（2013-01-01 15:08:31 Kevin Jin 修改）
  	hisDescription varchar(5000) DEFAULT NULL,						# 描述，通常是操作者留下的评论
  	modifyBy varchar(25) DEFAULT NULL,								# 最后更改人
  	modifyDate datetime DEFAULT NULL,								# 最后更改时间
  	PRIMARY KEY (historyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_history_sec_staff (
	historyId bigint(13) NOT NULL AUTO_INCREMENT,	 				
	workflowId int(11) DEFAULT NULL,								# 工作流Id
	staffId int(11) ,												# 员工ID
	accountId int(11) NOT NULL,										# 账户ID
	staffNo varchar(50) DEFAULT NULL,								# 员工编号
	salutation tinyint(4) DEFAULT NULL,								# 称呼，即用作性别
  	nameZH varchar(200) DEFAULT NULL,								# 中文名
  	nameEN varchar(200) DEFAULT NULL,								# 英文名
  	birthday datetime DEFAULT NULL,									# 出生年月
  	bizPhone varchar(50) DEFAULT NULL,								# 工作电话
  	bizExt varchar(25) DEFAULT NULL,								# 工作电话分机号（暂时不用）
  	personalPhone varchar(50) DEFAULT NULL,							# 家庭电话
  	bizMobile varchar(50) DEFAULT NULL,								# 公司手机
  	personalMobile varchar(50) DEFAULT NULL,						# 私人手机
  	otherPhone varchar(50) DEFAULT NULL,							# 其他电话
  	fax varchar(50) DEFAULT NULL,									# 传真
  	bizEmail varchar(200) DEFAULT NULL,								# 公司邮箱
  	personalEmail varchar(200) DEFAULT NULL,						# 私人邮箱
	certificateType tinyint(4) DEFAULT NULL,						# 证件类型
  	certificateNumber varchar(50) DEFAULT NULL,						# 证件号码
  	maritalStatus tinyint(4) DEFAULT NULL,							# 婚姻状况
  	registrationHometown varchar(50) DEFAULT NULL,					# 户籍城市（籍贯）
  	registrationAddress varchar(200) DEFAULT NULL,					# 户籍地址
  	personalAddress varchar(200) DEFAULT NULL,						# 家庭地址
  	personalPostcode varchar(50) DEFAULT NULL,						# 家庭地址邮编
  	description varchar(1000) DEFAULT NULL,							# 个人描述
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 员工状态，1:在职，2:招募，3:离职，4:候选
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	hisTitle varchar(200) not NULL,									# 备份名称（2013-01-01 15:08:31 Kevin Jin 修改）
  	hisDescription varchar(5000) DEFAULT NULL,						# 描述，通常是操作者留下的评论
  	modifyBy varchar(25) DEFAULT NULL,								# 最后更改人
  	modifyDate datetime DEFAULT NULL,								# 最后更改时间
  	PRIMARY KEY (historyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_history (
	historyId bigint(13) NOT NULL AUTO_INCREMENT,					# 历史记录表 				
	accountId int(11) NOT NULL,										# 账户ID
	workflowId int(11) DEFAULT NULL,								# 工作流Id
	objectId int(11) NOT NULL,										# 对象ID
	objectType tinyint(4) NOT NULL,									# 对象Type（1:历史，2:工作流）
	accessAction varchar(200) DEFAULT NULL,							# 模块中的操作链接
	objectClass varchar(200) DEFAULT NULL,							# 对象类名（例如，com.kan.base.domain.BaseVO）
	serviceBean varchar(200) DEFAULT NULL,							# Service Name（例如，Spring定义的Bean）
	serviceMethod varchar(200) DEFAULT NULL,						# Service Method（例如，insertStaff）
	hisTitle varchar(200) NOT NULL,									# 备份名称（2013-01-01 15:08:31 Kevin Jin 修改）
  	hisDescription varchar(5000) DEFAULT NULL,						# 备份描述，通常是操作者留下的评论
  	passObject  text DEFAULT NULL,									# 存入审核通过目标对象的Jason字符串
  	failObject  text DEFAULT NULL,									# 存入审核失败目标对象的Jason字符串
	tempStatus tinyint(4) DEFAULT NULL, 							# 用来标识最新审批人的意见（1：同意；2：同意）
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 历史记录状态，1:使用中，2:已过期
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,								# 目标对象最后创建人
  	createDate datetime DEFAULT NULL,								# 目标对象最后创建时间
  	modifyBy varchar(25) DEFAULT NULL,								# 目标对象最后更改人
  	modifyDate datetime DEFAULT NULL,								# 目标对象最后更改时间
  	PRIMARY KEY (historyId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



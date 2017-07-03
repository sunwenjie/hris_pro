use HRO;

DROP TABLE IF EXISTS hro_workflow_define;						# 工作流定义，各账户管理员定义本账户的工作流
DROP TABLE IF EXISTS hro_workflow_define_requirements;			# 工作流自定义条件
DROP TABLE IF EXISTS hro_workflow_define_steps;					# 工作流定义（步骤），各账户管理员定义不同模块操作的步骤
DROP TABLE IF EXISTS hro_workflow_actual;						# 工作流，系统中当前触发的工作流
DROP TABLE IF EXISTS hro_workflow_actual_steps;					# 工作流（步骤），系统中已触发的工作流的步骤及步骤执行情况

CREATE TABLE hro_workflow_define (
  	defineId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,								# 系统Id，HRO、HRM等
  	accountId int(11) NOT NULL,									# Account Id
  	corpId int(11) default null,								# 企业ID，用于Inhouse使用
  	nameZH varchar(200) DEFAULT NULL,							# 中文名
  	nameEN varchar(200) DEFAULT NULL,							# 英文名
  	scope tinyint(4) NOT NULL,									# 作用范围，1:内部，2:外部（是指非组织架构人员发起）
  	workflowModuleId int(11) NOT NULL,							# 工作流触发模块Id
  	rightIds varchar(200) DEFAULT NULL,							# 工作流触发操作权限Ids，Json方式存储{1,3,4,6}
  	approvalType tinyint(4) DEFAULT NULL,						# 流程审批类型，1:自定义，2:基于组织架构-从操作人开始，3:基于组织架构-从所属人开始，4:基于组织架构-从影响人开始
  	topPositionGrade int(11) NOT NULL,							# 最高审批职级
  	steps tinyint(4) DEFAULT NULL,								# 审批步骤数，通常是指审批级数，不包含必须步骤
  	sendEmail tinyint(4) DEFAULT NULL,							# 是否需要发送邮件
  	sendSMS tinyint(4) DEFAULT NULL,							# 是否需要发送短信
  	sendInfo tinyint(4) DEFAULT NULL,							# 是否需要发送系统信息
  	emailTemplateType int(11) DEFAULT NULL,						# 邮件模板ID
  	smsTemplateType int(11) DEFAULT NULL,						# 短信模板ID
  	infoTemplateType int(11) DEFAULT NULL,						# 系统消息模板
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
  	PRIMARY KEY (defineId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
	
CREATE TABLE hro_workflow_define_requirements (					# 自定义必要条件
  	requirementId int(11) NOT NULL AUTO_INCREMENT,
  	defineId int(11) NOT NULL,
  	nameZH varchar(200) DEFAULT NULL,							# 中文名称
  	nameEN varchar(200) DEFAULT NULL,							# 英文名称
  	requirementType tinyint(4) DEFAULT NULL,					# 条件类型(暂时不用)，1:字段比较(默认项)，2:sql脚本，3:EL表达式
  	columnId  int(11) DEFAULT NULL,								# 字段ID
  	columnNameDb varchar(200) DEFAULT NULL,						# 字段名称
  	compareType int(11) DEFAULT NULL							# 比较类型  1:等于[默认],2:大于,3:小于,4:大于等于,5:小于等于
  	compareValue varchar(1000) DEFAULT NULL,					# 比较值
	columnIndex  tinyint(4) DEFAULT NULL,						# 条件顺序
  	expression  varchar(1000) DEFAULT NULL,						# 表达式(暂时不用)  sql脚本表达式或者EL表达式
  	combineType tinyint(4) DEFAULT NULL,						# 组合的条件（and，or）
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
  	PRIMARY KEY (requirementId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


CREATE TABLE hro_workflow_define_steps (
  	stepId int(11) NOT NULL AUTO_INCREMENT,
  	defineId int(11) NOT NULL,
	auditType tinyint(11) DEFAULT NULL,							# 审批类型 1:内部职位ID, 4:内部员工Id，
  	stepType tinyint(4) DEFAULT NULL,							# 步骤类型，1:必须步骤（工作流中必须含有），2:屏蔽步骤（工作流中含有则进行覆盖），3:参考步骤（只发通知不参与审批）
	positionId int(11) DEFAULT NULL,							# 工作流职位
	staffId	   int(11) DEFAULT NULL,							# 指定审批人staffId
  	joinType tinyint(4) DEFAULT NULL,							# 参与方式  1:按职位，2:按职级 0: 不选择则默认为按权重
	referPositionId int(11) DEFAULT NULL,						# 参考职位
	referPositionGrade int(11) DEFAULT NULL,					# 参考职级
	joinOrderType tinyint(4) DEFAULT NULL,						# 参与顺序  1:并行，2:审核前，3:审核后 0:默认为按权重时，按照权重相等则并行，权重不等则串行
  	stepIndex tinyint(4) DEFAULT NULL,							# 工作流顺序
  	sendEmail tinyint(4) DEFAULT NULL,							# 是否需要发送邮件
  	sendSMS tinyint(4) DEFAULT NULL,							# 是否需要发送短信
  	sendInfo tinyint(4) DEFAULT NULL,							# 是否需要发送系统信息
  	emailTemplateId int(11) DEFAULT NULL,						# 邮件模板ID
  	smsTemplateId int(11) DEFAULT NULL,							# 短信模板ID
  	infoTemplateId int(11) DEFAULT NULL,						# 系统消息模板
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
  	PRIMARY KEY (stepId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
	
CREATE TABLE hro_workflow_actual (
  	workflowId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,								# 系统Id，HRO、HRM等
  	accountId int(11) NOT NULL,									# Account Id
  	corpId int(11) default null,								# 企业ID，用于Inhouse使用
  	defineId int(11) NOT NULL,									# Define Id
  	workflowModuleId int(11) NOT NULL,							# 工作流触发模块Id
  	rightId varchar(50) NOT NULL,							    # 工作流触发操作权限1:新建；3:修改；4:删除；6:提交
  	objectId int(11) NOT NULL,									# 数据对象Id
  	positionId int(11) NOT NULL,								# Position Id（触发工作流的Position Id）
  	nameZH varchar(200) DEFAULT NULL,							# 中文名
  	nameEN varchar(200) DEFAULT NULL,							# 英文名
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,								# 1:启动，2:停止，3:完成							
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (workflowId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_workflow_actual_steps (
  	stepId int(11) NOT NULL AUTO_INCREMENT,
  	workflowId int(11) NOT NULL,
  	stepType tinyint(4) DEFAULT NULL,							# 步骤类型，1:必须步骤（工作流中必须含有），2:屏蔽步骤（工作流中含有则进行覆盖），3:参考步骤（只发通知不参与审批）
  	auditType int(11) DEFAULT NULL,								# 审批类型 1:内部职位ID,2:外部职位ID,3:客户联系人ID，4:内部员工Id，
  	auditTargetId int(11) NOT NULL,								# 审核ID (审核职位ID/客户联系人ID，staffID)
  	stepIndex tinyint(4) NOT NULL,								# 工作流顺序（顺序相同是指不同职位同时操作），由低到高
  	sendEmail tinyint(4) DEFAULT NULL,							# 是否需要发送邮件
  	sendSMS tinyint(4) DEFAULT NULL,							# 是否需要发送短信
  	sendInfo tinyint(4) DEFAULT NULL,							# 是否需要发送系统信息
  	emailTemplateType int(11) DEFAULT NULL,						# 邮件模板ID
  	smsTemplateType int(11) DEFAULT NULL,						# 短信模板ID
  	infoTemplateType int(11) DEFAULT NULL,						# 系统消息模板
  	randomKey varchar(32) DEFAULT NULL,							# 验证邮件审核随机码
  	handleDate datetime DEFAULT NULL,							# 处理时间
  	description varchar(1000) DEFAULT NULL,						# 操作时的描述
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,								# 1:未开始,2:待操作,3:同意,4:拒绝	，5:已通知，6:未通知						
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (stepId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
	
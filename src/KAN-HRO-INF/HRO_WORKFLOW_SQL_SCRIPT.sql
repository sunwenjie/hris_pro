use HRO;

DROP TABLE IF EXISTS hro_workflow_define;						# ���������壬���˻�����Ա���屾�˻��Ĺ�����
DROP TABLE IF EXISTS hro_workflow_define_requirements;			# �������Զ�������
DROP TABLE IF EXISTS hro_workflow_define_steps;					# ���������壨���裩�����˻�����Ա���岻ͬģ������Ĳ���
DROP TABLE IF EXISTS hro_workflow_actual;						# ��������ϵͳ�е�ǰ�����Ĺ�����
DROP TABLE IF EXISTS hro_workflow_actual_steps;					# �����������裩��ϵͳ���Ѵ����Ĺ������Ĳ��輰����ִ�����

CREATE TABLE hro_workflow_define (
  	defineId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,								# ϵͳId��HRO��HRM��
  	accountId int(11) NOT NULL,									# Account Id
  	corpId int(11) default null,								# ��ҵID������Inhouseʹ��
  	nameZH varchar(200) DEFAULT NULL,							# ������
  	nameEN varchar(200) DEFAULT NULL,							# Ӣ����
  	scope tinyint(4) NOT NULL,									# ���÷�Χ��1:�ڲ���2:�ⲿ����ָ����֯�ܹ���Ա����
  	workflowModuleId int(11) NOT NULL,							# ����������ģ��Id
  	rightIds varchar(200) DEFAULT NULL,							# ��������������Ȩ��Ids��Json��ʽ�洢{1,3,4,6}
  	approvalType tinyint(4) DEFAULT NULL,						# �����������ͣ�1:�Զ��壬2:������֯�ܹ�-�Ӳ����˿�ʼ��3:������֯�ܹ�-�������˿�ʼ��4:������֯�ܹ�-��Ӱ���˿�ʼ
  	topPositionGrade int(11) NOT NULL,							# �������ְ��
  	steps tinyint(4) DEFAULT NULL,								# ������������ͨ����ָ�������������������벽��
  	sendEmail tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ�����ʼ�
  	sendSMS tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ���Ͷ���
  	sendInfo tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����ϵͳ��Ϣ
  	emailTemplateType int(11) DEFAULT NULL,						# �ʼ�ģ��ID
  	smsTemplateType int(11) DEFAULT NULL,						# ����ģ��ID
  	infoTemplateType int(11) DEFAULT NULL,						# ϵͳ��Ϣģ��
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
	
CREATE TABLE hro_workflow_define_requirements (					# �Զ����Ҫ����
  	requirementId int(11) NOT NULL AUTO_INCREMENT,
  	defineId int(11) NOT NULL,
  	nameZH varchar(200) DEFAULT NULL,							# ��������
  	nameEN varchar(200) DEFAULT NULL,							# Ӣ������
  	requirementType tinyint(4) DEFAULT NULL,					# ��������(��ʱ����)��1:�ֶαȽ�(Ĭ����)��2:sql�ű���3:EL���ʽ
  	columnId  int(11) DEFAULT NULL,								# �ֶ�ID
  	columnNameDb varchar(200) DEFAULT NULL,						# �ֶ�����
  	compareType int(11) DEFAULT NULL							# �Ƚ�����  1:����[Ĭ��],2:����,3:С��,4:���ڵ���,5:С�ڵ���
  	compareValue varchar(1000) DEFAULT NULL,					# �Ƚ�ֵ
	columnIndex  tinyint(4) DEFAULT NULL,						# ����˳��
  	expression  varchar(1000) DEFAULT NULL,						# ���ʽ(��ʱ����)  sql�ű����ʽ����EL���ʽ
  	combineType tinyint(4) DEFAULT NULL,						# ��ϵ�������and��or��
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
	auditType tinyint(11) DEFAULT NULL,							# �������� 1:�ڲ�ְλID, 4:�ڲ�Ա��Id��
  	stepType tinyint(4) DEFAULT NULL,							# �������ͣ�1:���벽�裨�������б��뺬�У���2:���β��裨�������к�������и��ǣ���3:�ο����裨ֻ��֪ͨ������������
	positionId int(11) DEFAULT NULL,							# ������ְλ
	staffId	   int(11) DEFAULT NULL,							# ָ��������staffId
  	joinType tinyint(4) DEFAULT NULL,							# ���뷽ʽ  1:��ְλ��2:��ְ�� 0: ��ѡ����Ĭ��Ϊ��Ȩ��
	referPositionId int(11) DEFAULT NULL,						# �ο�ְλ
	referPositionGrade int(11) DEFAULT NULL,					# �ο�ְ��
	joinOrderType tinyint(4) DEFAULT NULL,						# ����˳��  1:���У�2:���ǰ��3:��˺� 0:Ĭ��Ϊ��Ȩ��ʱ������Ȩ��������У�Ȩ�ز�������
  	stepIndex tinyint(4) DEFAULT NULL,							# ������˳��
  	sendEmail tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ�����ʼ�
  	sendSMS tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ���Ͷ���
  	sendInfo tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����ϵͳ��Ϣ
  	emailTemplateId int(11) DEFAULT NULL,						# �ʼ�ģ��ID
  	smsTemplateId int(11) DEFAULT NULL,							# ����ģ��ID
  	infoTemplateId int(11) DEFAULT NULL,						# ϵͳ��Ϣģ��
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
  	systemId tinyint(4) NOT NULL,								# ϵͳId��HRO��HRM��
  	accountId int(11) NOT NULL,									# Account Id
  	corpId int(11) default null,								# ��ҵID������Inhouseʹ��
  	defineId int(11) NOT NULL,									# Define Id
  	workflowModuleId int(11) NOT NULL,							# ����������ģ��Id
  	rightId varchar(50) NOT NULL,							    # ��������������Ȩ��1:�½���3:�޸ģ�4:ɾ����6:�ύ
  	objectId int(11) NOT NULL,									# ���ݶ���Id
  	positionId int(11) NOT NULL,								# Position Id��������������Position Id��
  	nameZH varchar(200) DEFAULT NULL,							# ������
  	nameEN varchar(200) DEFAULT NULL,							# Ӣ����
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,								# 1:������2:ֹͣ��3:���							
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
  	stepType tinyint(4) DEFAULT NULL,							# �������ͣ�1:���벽�裨�������б��뺬�У���2:���β��裨�������к�������и��ǣ���3:�ο����裨ֻ��֪ͨ������������
  	auditType int(11) DEFAULT NULL,								# �������� 1:�ڲ�ְλID,2:�ⲿְλID,3:�ͻ���ϵ��ID��4:�ڲ�Ա��Id��
  	auditTargetId int(11) NOT NULL,								# ���ID (���ְλID/�ͻ���ϵ��ID��staffID)
  	stepIndex tinyint(4) NOT NULL,								# ������˳��˳����ͬ��ָ��ְͬλͬʱ���������ɵ͵���
  	sendEmail tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ�����ʼ�
  	sendSMS tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ���Ͷ���
  	sendInfo tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����ϵͳ��Ϣ
  	emailTemplateType int(11) DEFAULT NULL,						# �ʼ�ģ��ID
  	smsTemplateType int(11) DEFAULT NULL,						# ����ģ��ID
  	infoTemplateType int(11) DEFAULT NULL,						# ϵͳ��Ϣģ��
  	randomKey varchar(32) DEFAULT NULL,							# ��֤�ʼ���������
  	handleDate datetime DEFAULT NULL,							# ����ʱ��
  	description varchar(1000) DEFAULT NULL,						# ����ʱ������
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,								# 1:δ��ʼ,2:������,3:ͬ��,4:�ܾ�	��5:��֪ͨ��6:δ֪ͨ						
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
	
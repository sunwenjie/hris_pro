use HRO;

DROP TABLE IF EXISTS hro_settle_order_batch_temp;					# �������� - Ԥ��
DROP TABLE IF EXISTS hro_settle_order_header_temp;					# �������� - Ԥ��
DROP TABLE IF EXISTS hro_settle_order_contract_temp;				# ��������Э�� - Ԥ��
DROP TABLE IF EXISTS hro_settle_order_detail_temp;					# �����ӱ� - Ԥ��
DROP TABLE IF EXISTS hro_settle_order_batch;						# ��������
DROP TABLE IF EXISTS hro_settle_order_header;						# ��������
DROP TABLE IF EXISTS hro_settle_order_contract;						# ��������Э��
DROP TABLE IF EXISTS hro_settle_order_detail;						# �����ӱ�
DROP TABLE IF EXISTS hro_settle_adjustment_header;					# ��������
DROP TABLE IF EXISTS hro_settle_adjustment_detail;					# �����ӱ�

CREATE TABLE hro_settle_order_batch_temp (							# ����Batch Temp�е����ж����������������������ٱ�����
	batchId int(11) NOT NULL AUTO_INCREMENT,						# ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) DEFAULT NULL,									# ����ʵ��Id
	businessTypeId int(11) DEFAULT NULL,							# ҵ������Id
	clientId int(11) DEFAULT NULL,									# �ͻ�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
	orderId int(11) DEFAULT NULL,									# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
  	weekly varchar(25) DEFAULT NULL,								# �ܴΣ�����2013/35��
	containSalary tinyint(4) DEFAULT NULL,							# ���ʣ���/�� - ���ΰ�������
	containSB tinyint(4) DEFAULT NULL,								# �籣����/�� - ���ΰ����籣
	containCB tinyint(4) DEFAULT NULL,								# �̱�����/�� - ���ΰ����̱�
	containServiceFee tinyint(4) DEFAULT NULL,						# ����ѣ���/�� - ���ΰ��������
	containOther tinyint(4) DEFAULT NULL,							# ��������/��-  - ���ΰ�������
	accountPeriod datetime DEFAULT NULL,							# �����
	startDate datetime DEFAULT NULL,								# ��ʼʱ�� - ָBatch���е�ʱ��
	endDate datetime DEFAULT NULL,									# ����ʱ�� - ָBatch���е�ʱ��
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
  	PRIMARY KEY (batchId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_header_temp (							
	orderHeaderId int(11) NOT NULL AUTO_INCREMENT,					# Order Go����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	batchId int(11) NOT NULL,										# ����Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	orderId int(11) NOT NULL,										# ����Id
	startDate datetime DEFAULT NULL,								# ������ʼ����
	endDate datetime DEFAULT NULL,									# ������������
	taxId int(11) DEFAULT NULL,										# ˰��Id
	taxNameZH varchar(200) DEFAULT NULL,							# ˰�����ƣ����ģ�
	taxNameEN varchar(200) DEFAULT NULL,							# ˰�����ƣ�Ӣ�ģ�
	taxRemark varchar(200) DEFAULT NULL,							# ˰�ʱ�ע
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	orderAmount double DEFAULT NULL,								# �����ܽ��
	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
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
  	PRIMARY KEY (orderHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;	

CREATE TABLE hro_settle_order_contract_temp (							
	contractId int(11) NOT NULL AUTO_INCREMENT,						# ����Э��Id
	orderHeaderId int(11) NOT NULL ,								# ��������Id�����㶩����
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	orderId int(11) NOT NULL,										# ����Id��������Id��
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeContractId int(11) NOT NULL,							# ��Ա����Э��Id
	timesheetId int(11) DEFAULT NULL,								# ���ڱ�Id
	startDate datetime DEFAULT NULL,								# ����Э�鿪ʼ����
	endDate datetime DEFAULT NULL,									# ����Э���������
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/09��
  	salaryMonth varchar(25) DEFAULT NULL,							# �����·ݣ�����2013/09��
  	sbMonth varchar(25) DEFAULT NULL,								# �籣�·ݣ�����2013/09��
  	cbMonth varchar(25) DEFAULT NULL,								# �̱��·ݣ�����2013/09��
  	fundMonth varchar(25) DEFAULT NULL,								# �������·ݣ�����2013/09��
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
  	PRIMARY KEY (contractId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_detail_temp (			
	orderDetailId int(11) NOT NULL AUTO_INCREMENT,					# �����ӱ�Id
	contractId int(11) NOT NULL,									# ����Э��Id
	headerId varchar(200) DEFAULT NULL,								# ����Id������ϸ���ʹ���ʹ��
	detailId varchar(200) DEFAULT NULL,								# ��ϸId������ϸ���ʹ���ʹ��
	detailType tinyint(4) DEFAULT NULL,								# ��ϸ���ͣ�1:�籣��ϸ��2:�̱���ϸ��3:�籣������ϸ��4:���ʵ�������5:���ʵ�����ϸ��
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	base double DEFAULT NULL,										# ֱ�������ֵ��Base From�Ľ��ֵ
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ�����㣩
	discount double DEFAULT NULL,									# �ۿۣ���ʱ�����㣩
	multiple double DEFAULT NULL,									# ���ʣ���ʱ�����㣩
	sbBaseCompany double DEFAULT NULL,								# �籣��������˾��
	sbBasePersonal double DEFAULT NULL,								# �籣���������ˣ�
	billRatePersonal double NOT NULL,								# ���ʣ��������룩
	billRateCompany double NOT NULL,								# ���ʣ���˾Ӫ�գ�
	costRatePersonal double NOT NULL,								# ���ʣ�����֧����
	costRateCompany double NOT NULL,								# ���ʣ���˾�ɱ���
	billFixPersonal double NOT NULL,								# �̶��𣨸������룩
	billFixCompany double NOT NULL,									# �̶��𣨹�˾Ӫ�գ�
	costFixPersonal double NOT NULL,								# �̶��𣨸���֧����
	costFixCompany double NOT NULL,									# �̶��𣨹�˾�ɱ���
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	taxAmountActual double NOT NULL,								# ˰�գ�ʵ�ɣ� 
	taxAmountCost double NOT NULL,									# ˰�գ��ɱ���
	taxAmountSales double NOT NULL,									# ˰�գ����ۣ�
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
  	PRIMARY KEY (orderDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_batch (								
	batchId int(11) NOT NULL AUTO_INCREMENT,			
	batchTempId int(11) NOT NULL,									# ��ʱ������
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) DEFAULT NULL,									# ����ʵ��Id
	businessTypeId int(11) DEFAULT NULL,							# ҵ������Id
	clientId int(11) DEFAULT NULL,									# �ͻ�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
	orderId int(11) DEFAULT NULL,									# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
  	weekly varchar(25) DEFAULT NULL,								# �ܴΣ�����2013/35��
	containSalary tinyint(4) DEFAULT NULL,							# ���ʣ���/�� - ���ΰ�������
	containSB tinyint(4) DEFAULT NULL,								# �籣����/�� - ���ΰ����籣
	containCB tinyint(4) DEFAULT NULL,								# �̱�����/�� - ���ΰ����̱�
	containServiceFee tinyint(4) DEFAULT NULL,						# ����ѣ���/�� - ���ΰ��������
	containOther tinyint(4) DEFAULT NULL,							# ��������/��-  - ���ΰ�������
	accountPeriod datetime DEFAULT NULL,							# �����
	startDate datetime DEFAULT NULL,								# ��ʼʱ�� - ָBatch���е�ʱ��
	endDate datetime DEFAULT NULL,									# ����ʱ�� - ָBatch���е�ʱ��
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
  	PRIMARY KEY (batchId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_header (								 
	orderHeaderId int(11) NOT NULL AUTO_INCREMENT,					# Order����Id
	orderHeaderTempId int(11) NOT NULL,								# ��ʱ������
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	batchId int(11) NOT NULL,										# ����Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	orderId int(11) NOT NULL,										# ����Id
	startDate datetime DEFAULT NULL,								# ������ʼ����
	endDate datetime DEFAULT NULL,									# ������������
	taxId int(11) DEFAULT NULL,										# ˰��Id
	taxNameZH varchar(200) DEFAULT NULL,							# ˰�����ƣ����ģ�
	taxNameEN varchar(200) DEFAULT NULL,							# ˰�����ƣ�Ӣ�ģ�
	taxRemark varchar(200) DEFAULT NULL,							# ˰�ʱ�ע
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	orderAmount double DEFAULT NULL,								# �����ܽ��
	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
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
  	PRIMARY KEY (orderHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_contract (							
	contractId int(11) NOT NULL AUTO_INCREMENT,						# ����Э��Id
	contractTempId int(11) NOT NULL,								# ��ʱ������
	orderHeaderId int(11) NOT NULL,									# ��������Id�����㶩����
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	orderId int(11) NOT NULL,										# ����Id��������Id��
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeContractId int(11) NOT NULL,							# ��Ա����Э��Id
	timesheetId int(11) DEFAULT NULL,								# ���ڱ�Id
	startDate datetime DEFAULT NULL,								# ����Э�鿪ʼ����
	endDate datetime DEFAULT NULL,									# ����Э���������
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
  	salaryMonth varchar(25) DEFAULT NULL,							# �����·ݣ�����2013/09��
  	sbMonth varchar(25) DEFAULT NULL,								# �籣�·ݣ�����2013/09��
  	cbMonth varchar(25) DEFAULT NULL,								# �̱��·ݣ�����2013/09��
  	fundMonth varchar(25) DEFAULT NULL,								# �������·ݣ�����2013/09��
  	paymentFlag tinyint(4) DEFAULT NULL,							# �Ƿ�����н�꣨1:�ǣ�2:��
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
  	PRIMARY KEY (contractId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_order_detail (			
	orderDetailId int(11) NOT NULL AUTO_INCREMENT,					# �����ӱ�Id
	contractId int(11) NOT NULL,									# ����Э��Id
	headerId varchar(200) DEFAULT NULL,								# ����Id������ϸ���ʹ���ʹ��
	detailId varchar(200) DEFAULT NULL,								# ��ϸId������ϸ���ʹ���ʹ��
	detailType tinyint(4) DEFAULT NULL,								# ��ϸ���ͣ�1:�籣��ϸ��2:�̱���ϸ��3:�籣������ϸ��
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	base double DEFAULT NULL,										# ֱ�������ֵ��Base From�Ľ��ֵ
	quantity smallint(4) DEFAULT NULL,								# ��������ʱ�����㣩
	discount double DEFAULT NULL,									# �ۿۣ���ʱ�����㣩
	multiple double DEFAULT NULL,									# ���ʣ���ʱ�����㣩
	sbBaseCompany double DEFAULT NULL,								# �籣��������˾��
	sbBasePersonal double DEFAULT NULL,								# �籣���������ˣ�
	billRateCompany double NOT NULL,								# ���ʣ���˾Ӫ�գ�
	billRatePersonal double NOT NULL,								# ���ʣ��������룩
	costRateCompany double NOT NULL,								# ���ʣ���˾�ɱ���
	costRatePersonal double NOT NULL,								# ���ʣ�����֧����
	billFixCompany double NOT NULL,									# �̶��𣨹�˾Ӫ�գ�
	billFixPersonal double NOT NULL,								# �̶��𣨸������룩
	costFixCompany double NOT NULL,									# �̶��𣨹�˾�ɱ���
	costFixPersonal double NOT NULL,								# �̶��𣨸���֧����
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	taxAmountActual double NOT NULL,								# ˰�գ�ʵ�ɣ� 
	taxAmountCost double NOT NULL,									# ˰�գ��ɱ���
	taxAmountSales double NOT NULL,									# ˰�գ����ۣ�
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
  	PRIMARY KEY (orderDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_adjustment_header (								 
	adjustmentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# ��������Id�������Է���Э��Ϊ����
	accountId int(11) NOT NULL,										# �˻�Id
	orderId int(11) NOT NULL,										# ����Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա������
	employeeNameEN varchar(200) DEFAULT NULL,						# ��ԱӢ����
	contractId int(11) NOT NULL,									# ����Э��Id
	taxId int(11) NOT NULL,											# ˰��Id
	taxNameZH varchar(200) DEFAULT NULL,							# ˰�����ƣ����ģ�
	taxNameEN varchar(200) DEFAULT NULL,							# ˰�����ƣ�Ӣ�ģ�
	adjustmentDate datetime DEFAULT NULL,							# �������ڣ��������ڣ���Ĭ�ϵ��죬ֻ�����󣬲�����ǰ
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
  	paymentFlag tinyint(4) DEFAULT NULL,							# �Ƿ�����н�꣨1:�ǣ�2:��
  	description varchar(1000) DEFAULT NULL,							
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
  	PRIMARY KEY (adjustmentHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_settle_adjustment_detail (								 
	adjustmentDetailId int(11) NOT NULL AUTO_INCREMENT,				# �����ӱ�Id
	adjustmentHeaderId int(11) NOT NULL,							# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	base double DEFAULT NULL,										# Xֱ�������ֵ��Base From�Ľ��ֵ
	quantity smallint(4) DEFAULT NULL,								# X��������ʱ�����㣩
	discount double DEFAULT NULL,									# X�ۿۣ���ʱ�����㣩
	multiple double DEFAULT NULL,									# X���ʣ���ʱ�����㣩
	billRatePersonal double DEFAULT NULL,							# X���ʣ��������룩
	billRateCompany double DEFAULT NULL,							# X���ʣ���˾Ӫ�գ�
	costRatePersonal double DEFAULT NULL,							# X���ʣ�����֧����
	costRateCompany double DEFAULT NULL,							# X���ʣ���˾�ɱ���
	billFixPersonal double DEFAULT NULL,							# X�̶��𣨸������룩
	billFixCompany double DEFAULT NULL,								# X�̶��𣨹�˾Ӫ�գ�
	costFixPersonal double DEFAULT NULL,							# X�̶��𣨸���֧����
	costFixCompany double DEFAULT NULL,								# X�̶��𣨹�˾�ɱ���
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	taxAmountActual double DEFAULT NULL,							# X˰�գ�ʵ�ɣ� 
	taxAmountCost double DEFAULT NULL,								# X˰�գ��ɱ���
	taxAmountSales double DEFAULT NULL,								# X˰�գ����ۣ�
	monthly varchar(25) DEFAULT NULL,								# �·ݣ�����2013/9��
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
  	PRIMARY KEY (adjustmentDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
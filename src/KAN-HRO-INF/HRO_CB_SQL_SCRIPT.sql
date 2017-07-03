use HRO;

DROP TABLE IF EXISTS hro_cb_batch;									# �̱�����
DROP TABLE IF EXISTS hro_cb_header;									# �̱����� - �ύ״̬�ɲ������
DROP TABLE IF EXISTS hro_cb_detail;									# �̱��ӱ� - �ύ״̬�ɲ������

CREATE TABLE hro_cb_batch (											
	batchId int(11) NOT NULL AUTO_INCREMENT,						# ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) DEFAULT NULL,									# ����ʵ��Id
	businessTypeId int(11) DEFAULT NULL,							# ҵ������Id
	cbId int(11) DEFAULT NULL,										# �̱�����Id
	clientId int(11) DEFAULT NULL,									# �ͻ�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
	orderId int(11) DEFAULT NULL,									# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	monthly varchar(25) DEFAULT NULL,								# �˵��·ݣ�����2013/9�������̱��˵��·ݸ������·�һ�£�
	startDate datetime DEFAULT NULL,								# ��ʼʱ�� - ָBatch���е�ʱ��
	endDate datetime DEFAULT NULL,									# ����ʱ�� - ָBatch���е�ʱ��
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:��׼��3:ȷ�ϣ�4:�ύ��5:�ѽ��㣩									
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

CREATE TABLE hro_cb_header (							
	headerId int(11) NOT NULL AUTO_INCREMENT,						# �̱�����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	batchId int(11) NOT NULL,										# ����Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	orderId int(11) NOT NULL,										# ����Id
	contractId int(11) NOT NULL,									# ����Э��Id
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա������
	employeeNameEN varchar(200) DEFAULT NULL,						# ��ԱӢ����
	employeeCBId int(11) NOT NULL,									# ��Ա�̱�����Id
	workPlace varchar(100) DEFAULT NULL,							# ��Ա�����ص�
	gender tinyint(4) DEFAULT NULL,									# �Ա���/Ů�����ӳƺ�ת��
	certificateType tinyint(4) DEFAULT NULL,						# ֤������
  	certificateNumber varchar(50) DEFAULT NULL,						# ֤������
	residencyType tinyint(4) DEFAULT NULL,							# ��������
	residencyCityId  int(11) DEFAULT NULL,							# ��������
	residencyAddress varchar(200) DEFAULT NULL,						# ������ַ
	highestEducation tinyint(4) DEFAULT NULL,						# ���ѧ��
	maritalStatus tinyint(4) DEFAULT NULL,							# ����״��
	employStatus tinyint(4) DEFAULT NULL,							# ��Ա״̬
	cbStatus tinyint(4) DEFAULT NULL,								# �̱�״̬
	startDate datetime DEFAULT NULL,								# ������
	endDate datetime DEFAULT NULL,									# �˹�����
	onboardDate datetime DEFAULT NULL,								# ��ְ���ڣ�������Э�鿪ʼʱ��һ�£�
	resignDate datetime DEFAULT NULL,								# ��ְ����
	monthly varchar(25) DEFAULT NULL,								# �����·�
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:��׼��3:ȷ�ϣ�4:�ύ��5:�ѽ��㣩			
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

CREATE TABLE hro_cb_detail (			
	detailId int(11) NOT NULL AUTO_INCREMENT,						# �̱��ӱ�Id
	headerId int(11) NOT NULL,										# �̱�����Id
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	amountPurchaseCost double DEFAULT NULL,							# �ϼƣ��ɹ��ɱ���
	amountSalesCost double DEFAULT NULL,							# �ϼƣ����۳ɱ���
	amountSalesPrice double DEFAULT NULL,							# �ϼƣ����ۼ۸�
	monthly varchar(25) DEFAULT NULL,								# �����·�
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:��׼��3:ȷ�ϣ�4:�ύ��5:�ѽ��㣩				
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
use HRO;

DROP TABLE IF EXISTS hro_sb_batch;									# �籣����
DROP TABLE IF EXISTS hro_sb_header;									# �籣���� - �ύ״̬�ɲ������
DROP TABLE IF EXISTS hro_sb_detail;									# �籣�ӱ� - �ύ״̬�ɲ������
DROP TABLE IF EXISTS hro_sb_header_temp;							# �籣������ʱ�� - ��Ӧ���ϴ��籣������
DROP TABLE IF EXISTS hro_sb_detail_temp;							# �籣�ӱ���ʱ�� - ��Ӧ���ϴ��籣������
DROP TABLE IF EXISTS hro_sb_adjustment_header;						# �籣�������� - �ύ״̬�ɲ������
DROP TABLE IF EXISTS hro_sb_adjustment_detail;						# �籣�����ӱ�

CREATE TABLE hro_sb_batch (											
	batchId int(11) NOT NULL AUTO_INCREMENT,						# ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) DEFAULT NULL,									# ����ʵ��Id
	businessTypeId int(11) DEFAULT NULL,							# ҵ������Id
	cityId int(11) DEFAULT NULL,									# �籣����Id��������������籣���в���
	clientId int(11) DEFAULT NULL,									# �ͻ�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
	orderId int(11) DEFAULT NULL,									# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	monthly varchar(25) DEFAULT NULL,								# �˵��·ݣ�����2013/9����������Ϊ���˲ο��������¡������¡�������֮����·ݣ��籣�Ƚ����⣩
	startDate datetime DEFAULT NULL,								# ��ʼʱ�� - ָBatch���е�ʱ��
	endDate datetime DEFAULT NULL,									# ����ʱ�� - ָBatch���е�ʱ��
  	description varchar(1000) DEFAULT NULL,							
  	sbType tinyint(4) DEFAULT 2,									# �籣�������� 0:��ѡ��##1:�籣##2:������##3:�ۺ�##4:����
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

CREATE TABLE hro_sb_header (							
	headerId int(11) NOT NULL AUTO_INCREMENT,						# �籣����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	batchId int(11) NOT NULL,										# ����Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	clientNo varchar(50) DEFAULT NULL,								# �ͻ����
	clientNameZH varchar(200) DEFAULT NULL,							# �ͻ����ƣ�Ӣ�ģ�
	clientNameEN varchar(200) DEFAULT NULL,							# �ͻ����ƣ����ģ�
	orderId int(11) NOT NULL,										# ����Id
	contractId int(11) NOT NULL,									# ����Э��Id
	contractStartDate datetime DEFAULT NULL,						# ����Э�鿪ʼ����
	contractEndDate datetime DEFAULT NULL,							# ����Э���������
	contractBranch varchar(25) DEFAULT NULL,						# ����Э���������ţ�Branch Id��
  	contractOwner varchar(25) DEFAULT NULL,							# ����Э�������ˣ�Position Id��
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա������
	employeeNameEN varchar(200) DEFAULT NULL,						# ��ԱӢ����
	employeeSBId int(11) NOT NULL,									# ��Ա�籣����Id
	employeeSBNameZH varchar(200) DEFAULT NULL,						# ��Ա�籣�������ƣ����ģ�
	employeeSBNameEN varchar(200) DEFAULT NULL,						# ��Ա�籣�������ƣ�Ӣ�ģ�
	cityId int(11) DEFAULT NULL,									# �籣����Id
	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id����0�����߿ձ�ʾ��ʹ�ù�Ӧ��
	vendorNameZH varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ����ģ�
	vendorNameEN varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ�Ӣ�ģ�
	vendorServiceIds varchar(1000) DEFAULT NULL,					# ��Ӧ�̷�������{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# ��Ӧ�̷����
	workPlace varchar(100) DEFAULT NULL,							# ��Ա�����ص�
	gender tinyint(4) DEFAULT NULL,									# �Ա���/Ů�����ӳƺ�ת��
	certificateType tinyint(4) DEFAULT NULL,						# ֤������
  	certificateNumber varchar(50) DEFAULT NULL,						# ֤������
	needMedicalCard tinyint(4) DEFAULT NULL,						# ��Ҫ����ҽ����						
	needSBCard tinyint(4) DEFAULT NULL,								# ��Ҫ�����籣��
	medicalNumber varchar(50) DEFAULT NULL,							# ҽ�����ʺ�
	sbNumber varchar(50) DEFAULT NULL,								# �籣���ʺ�
	fundNumber varchar(50) DEFAULT NULL,							# �������ʺ�
	personalSBBurden tinyint(4) DEFAULT NULL,						# �籣���˲��ֹ�˾�е����ǣ���
	residencyType tinyint(4) DEFAULT NULL,							# ��������
	residencyCityId  int(11) DEFAULT NULL,							# ��������
	residencyAddress varchar(200) DEFAULT NULL,						# ������ַ
	highestEducation int(4) DEFAULT NULL,							# ���ѧ��
	maritalStatus tinyint(4) DEFAULT NULL,							# ����״��
	employStatus tinyint(4) DEFAULT NULL,							# ��Ա״̬
	sbStatus tinyint(4) DEFAULT NULL,								# �籣״̬
	startDate datetime DEFAULT NULL,								# �ӱ����ڣ�������£�
	endDate datetime DEFAULT NULL,									# �˱�����
	onboardDate datetime DEFAULT NULL,								# ��ְ���ڣ�������Э�鿪ʼʱ��һ�£�
	resignDate datetime DEFAULT NULL,								# ��ְ����
	monthly varchar(25) DEFAULT NULL,								# �˵��·ݣ�����2013/9��
	flag tinyint(4) DEFAULT NULL,									# �籣ʵ�ʽ��ɱ�ʶ��0 ��ѡ�� ,1������2δ����
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

CREATE TABLE hro_sb_header_temp (							
	headerId int(11) NOT NULL AUTO_INCREMENT,						# �籣����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	batchId int(11) NOT NULL,										# ����Id
	clientId int(11) DEFAULT NULL,									# �ͻ�Id
	corpId int(11) default null,									# ��ҵID������Inhouseʹ��
	clientNo varchar(50) DEFAULT NULL,								# �ͻ����
	clientNameZH varchar(200) DEFAULT NULL,							# �ͻ����ƣ�Ӣ�ģ�
	clientNameEN varchar(200) DEFAULT NULL,							# �ͻ����ƣ����ģ�
	orderId int(11) NOT NULL,										# ����Id
	contractId int(11) NOT NULL,									# ����Э��Id
	contractStartDate datetime DEFAULT NULL,						# ����Э�鿪ʼ����
	contractEndDate datetime DEFAULT NULL,							# ����Э���������
	contractBranch varchar(25) DEFAULT NULL,						# ����Э���������ţ�Branch Id��
  	contractOwner varchar(25) DEFAULT NULL,							# ����Э�������ˣ�Position Id��
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա������
	employeeNameEN varchar(200) DEFAULT NULL,						# ��ԱӢ����
	employeeSBId int(11) NOT NULL,									# ��Ա�籣����Id
	employeeSBNameZH varchar(200) DEFAULT NULL,						# ��Ա�籣�������ƣ����ģ�
	employeeSBNameEN varchar(200) DEFAULT NULL,						# ��Ա�籣�������ƣ�Ӣ�ģ�
	cityId int(11) DEFAULT NULL,									# �籣����Id
	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id����0�����߿ձ�ʾ��ʹ�ù�Ӧ��
	vendorNameZH varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ����ģ�
	vendorNameEN varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ�Ӣ�ģ�
	vendorServiceIds varchar(1000) DEFAULT NULL,					# ��Ӧ�̷�������{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# ��Ӧ�̷����
	workPlace varchar(100) DEFAULT NULL,							# ��Ա�����ص�
	gender tinyint(4) DEFAULT NULL,									# �Ա���/Ů�����ӳƺ�ת��
	certificateType tinyint(4) DEFAULT NULL,						# ֤������
  	certificateNumber varchar(50) DEFAULT NULL,						# ֤������
	needMedicalCard tinyint(4) DEFAULT NULL,						# ��Ҫ����ҽ����						
	needSBCard tinyint(4) DEFAULT NULL,								# ��Ҫ�����籣��
	medicalNumber varchar(50) DEFAULT NULL,							# ҽ�����ʺ�
	sbNumber varchar(50) DEFAULT NULL,								# �籣���ʺ�
	fundNumber varchar(50) DEFAULT NULL,							# �������ʺ�
	personalSBBurden tinyint(4) DEFAULT NULL,						# �籣���˲��ֹ�˾�е����ǣ���
	residencyType tinyint(4) DEFAULT NULL,							# ��������
	residencyCityId  int(11) DEFAULT NULL,							# ��������
	residencyAddress varchar(200) DEFAULT NULL,						# ������ַ
	highestEducation tinyint(4) DEFAULT NULL,						# ���ѧ��
	maritalStatus tinyint(4) DEFAULT NULL,							# ����״��
	employStatus tinyint(4) DEFAULT NULL,							# ��Ա״̬
	sbStatus tinyint(4) DEFAULT NULL,								# �籣״̬
	startDate datetime DEFAULT NULL,								# �ӱ����ڣ�������£�
	endDate datetime DEFAULT NULL,									# �˱�����
	onboardDate datetime DEFAULT NULL,								# ��ְ���ڣ�������Э�鿪ʼʱ��һ�£�
	resignDate datetime DEFAULT NULL,								# ��ְ����
	monthly varchar(25) DEFAULT NULL,								# �˵��·ݣ�����2013/9��
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

CREATE TABLE hro_sb_detail (			
	detailId int(11) NOT NULL AUTO_INCREMENT,						# �籣�ӱ�Id
	headerId int(11) NOT NULL,										# �籣����Id
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	basePersonal double DEFAULT NULL,								# ���������ˣ�
	baseCompany double DEFAULT NULL,								# ��������˾��
	ratePersonal double DEFAULT NULL,								# ���ʣ����ˣ�
	rateCompany double DEFAULT NULL,								# ���ʣ���˾��
	fixPersonal double DEFAULT NULL,								# �̶��𣨸��ˣ�
	fixCompany double DEFAULT NULL,									# �̶��𣨹�˾��
	amountPersonal double DEFAULT NULL,								# �ϼƣ����ˣ�
	amountCompany double DEFAULT NULL,								# �ϼƣ���˾��
	monthly varchar(25) DEFAULT NULL,								# �˵��·ݣ�����2013/9��
	accountMonthly varchar(25) DEFAULT NULL,						# �����·ݣ�����2013/9��
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

CREATE TABLE hro_sb_detail_temp (			
	detailId int(11) NOT NULL AUTO_INCREMENT,						# �籣�ӱ�Id
	headerId int(11) NOT NULL,										# �籣����Id
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	basePersonal double DEFAULT NULL,								# ���������ˣ�
	baseCompany double DEFAULT NULL,								# ��������˾��
	ratePersonal double DEFAULT NULL,								# ���ʣ����ˣ�
	rateCompany double DEFAULT NULL,								# ���ʣ���˾��
	fixPersonal double DEFAULT NULL,								# �̶��𣨸��ˣ�
	fixCompany double DEFAULT NULL,									# �̶��𣨹�˾��
	amountPersonal double DEFAULT NULL,								# �ϼƣ����ˣ�
	amountCompany double DEFAULT NULL,								# �ϼƣ���˾��
	monthly varchar(25) DEFAULT NULL,								# �˵��·ݣ�����2013/9��
	accountMonthly varchar(25) DEFAULT NULL,						# �����·ݣ�����2013/9��
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

CREATE TABLE hro_sb_adjustment_header (								 
	adjustmentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# ��������Id�������Է���Э��Ϊ����
	accountId int(11) NOT NULL,										# �˻�Id
	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id����0�����߿ձ�ʾ��ʹ�ù�Ӧ��
	vendorNameZH varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ����ģ�
	vendorNameEN varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ�Ӣ�ģ�
	orderId int(11) NOT NULL,										# ����Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	clientNo varchar(50) DEFAULT NULL,								# �ͻ����
	clientNameZH varchar(200) DEFAULT NULL,							# �ͻ����ƣ����ģ�
	clientNameEN varchar(200) DEFAULT NULL,							# �ͻ����ƣ�Ӣ�ģ�
	corpId int(11) NOT NULL,										# �ͻ�Id
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա������
	employeeNameEN varchar(200) DEFAULT NULL,						# ��ԱӢ����
	employeeSBId int(11) NOT NULL,									# ��Ա�籣����Id
	contractId int(11) NOT NULL,									# ����Э��Id
	personalSBBurden tinyint(4) DEFAULT NULL,						# �籣���˲��ֹ�˾�е����ǣ���
	amountPersonal double DEFAULT NULL,								# �ϼƣ����ˣ�
	amountCompany double DEFAULT NULL,								# �ϼƣ���˾��
	monthly varchar(25) DEFAULT NULL,								# �˵��·�
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:�ύ��4:�˻أ�5:�ѽ��㣩			
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

CREATE TABLE hro_sb_adjustment_detail (								 
	adjustmentDetailId int(11) NOT NULL AUTO_INCREMENT,				# �����ӱ�Id
	adjustmentHeaderId int(11) NOT NULL,							# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	amountPersonal double DEFAULT NULL,								# �ϼƣ����ˣ�
	amountCompany double DEFAULT NULL,								# �ϼƣ���˾��
	monthly varchar(25) DEFAULT NULL,								# �˵��·�
	accountMonthly varchar(25) DEFAULT NULL,						# �����·ݣ�����2013/9��
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:���ã�2:ͣ�ã�				
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
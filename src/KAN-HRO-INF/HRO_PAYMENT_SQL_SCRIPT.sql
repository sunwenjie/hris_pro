use HRO;

DROP TABLE IF EXISTS hro_payment_batch;								# н������
DROP TABLE IF EXISTS hro_payment_header;							# н������
DROP TABLE IF EXISTS hro_payment_detail;							# н��ӱ�
DROP TABLE IF EXISTS hro_payment_adjustment_header;					# н���������
DROP TABLE IF EXISTS hro_payment_adjustment_detail;					# н������ӱ�
DROP TABLE IF EXISTS hro_salary_header;								# �������� - ���ڿͻ�����õĹ������ݵ���
DROP TABLE IF EXISTS hro_salary_detail;								# ���ʴӱ�
DROP TABLE IF EXISTS hro_common_batch;								# ͨ������

CREATE TABLE hro_payment_batch (									# ������ϸ�к��б�ǡ��Ƿ���֧����
	batchId int(11) NOT NULL AUTO_INCREMENT,						# ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) DEFAULT NULL,									# ����ʵ��Id
	businessTypeId int(11) DEFAULT NULL,							# ҵ������Id
	clientId int(11) DEFAULT NULL,									# �ͻ�Id
	corpId int(11) DEFAULT NULL,									
	orderId int(11) DEFAULT NULL,									# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	employeeId int(11) DEFAULT NULL,								# ��ԱId
	monthly varchar(25) DEFAULT NULL,								# н���·ݣ�����2013/9��
  	weekly varchar(25) DEFAULT NULL,								# н���ܴΣ�����2013/35��
	startDate datetime DEFAULT NULL,								# ��ʼʱ�� - ָBatch���е�ʱ��
	endDate datetime DEFAULT NULL,		 							# ����ʱ�� - ָBatch���е�ʱ��
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:�ύ��3:���ţ�								
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

CREATE TABLE hro_payment_header (							
	paymentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# н������Id���ύ�����������ȵ�Header���
	orderContractId int(11) NOT NULL,								# �������Э��Id������Ա����Э��Id��һ�����������������ݴ��AdjustmentHeaderId��
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	batchId int(11) NOT NULL,										# ����Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	orderId int(11) NOT NULL,										# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	employeeId int(11) DEFAULT NULL,								# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա���������ģ�
	employeeNameEN varchar(200) DEFAULT NULL,						# ��Ա������Ӣ�ģ�
	itemGroupId int(11) DEFAULT NULL,								# ���ʼ������
	startDate datetime DEFAULT NULL,								# н�꿪ʼ����
	endDate datetime DEFAULT NULL,									# н���������
	certificateType tinyint(4) DEFAULT NULL,						# ֤������
  	certificateNumber varchar(50) DEFAULT NULL,						# ֤������
  	bankId int(11) DEFAULT NULL,									# ����Id	
  	bankNameZH varchar(200) DEFAULT NULL,							# �������ƣ����ģ�
  	bankNameEN varchar(200) DEFAULT NULL,							# �������ƣ�Ӣ�ģ�
  	bankAccount varchar(50) DEFAULT NULL,							# �����˻�
  	billAmountCompany double NOT NULL,								# �ϼƣ���˾Ӫ�գ�
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	costAmountCompany double NOT NULL,								# �ϼƣ���˾�ɱ���
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	taxAmountPersonal double NOT NULL,								# �ϼƣ���˰��
	addtionalBillAmountPersonal double NOT NULL,					# ���Ӻϼƣ��������룩������˰ǰ�ӵĽ��
	taxAgentAmountPersonal double DEFAULT NULL,						# ����˰����
  	monthly varchar(25) DEFAULT NULL,								# н���·ݣ�����2013/9��
  	taxFlag tinyint(4) DEFAULT NULL,								# ��˰��ǣ�1:�ѱ�˰��2:δ��˰��	
  	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id����0�����߿ձ�ʾ��ʹ�ù�Ӧ��
	vendorNameZH varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ����ģ�
	vendorNameEN varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ�Ӣ�ģ�
	vendorServiceIds varchar(1000) DEFAULT NULL,					# ��Ӧ�̷�������{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# ��Ӧ�̷����
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:�ύ��3:���ţ�			
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (paymentHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;	

CREATE TABLE hro_payment_detail (			
	paymentDetailId int(11) NOT NULL AUTO_INCREMENT,				# н��ӱ�Id
	paymentHeaderId int(11) NOT NULL,								# н������Id
	orderDetailId int(11) DEFAULT NULL,								# ������ϸId���������������ݴ��AdjustmentDetailId��
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	baseCompany double DEFAULT NULL,								# ��������˾��
	basePersonal double DEFAULT NULL,								# ���������ˣ�
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
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:�ύ��3:���ţ�					
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (paymentDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_payment_adjustment_header (								 
	adjustmentHeaderId int(11) NOT NULL AUTO_INCREMENT,				# ��������Id����С��λ����Э�飬н��ģ�����ֻ����˰
	accountId int(11) NOT NULL,										# �˻�Id
	orderId int(11) NOT NULL,										# ����Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	contractId int(11) NOT NULL,									# ����Э��Id
	employeeId int(11) NOT NULL,									# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա������
	employeeNameEN varchar(200) DEFAULT NULL,						# ��ԱӢ����
	itemGroupId int(11) DEFAULT NULL,								# ���ʼ������
	contractId int(11) NOT NULL,									# ����Э��Id
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	taxAmountPersonal double NOT NULL,								# �ϼƣ���˰��
	addtionalBillAmountPersonal double NOT NULL,					# ���Ӻϼƣ��������룩
	taxAgentAmountPersonal double DEFAULT NULL,						# ����˰����
	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	monthly varchar(25) DEFAULT NULL,								# �����·ݣ�����2013/9��
  	taxFlag tinyint(4) DEFAULT NULL,								# ��˰��ǣ�1:�ѱ�˰��2:δ��˰��	
  	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id����0�����߿ձ�ʾ��ʹ�ù�Ӧ��
	vendorNameZH varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ����ģ�
	vendorNameEN varchar(200) DEFAULT NULL,							# ��Ӧ�����ƣ�Ӣ�ģ�
	vendorServiceIds varchar(1000) DEFAULT NULL,					# ��Ӧ�̷�������{0:1:2:3:4}						
	vendorServiceFee double DEFAULT NULL,							# ��Ӧ�̷����
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:���ţ�			
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (ajustmentHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_payment_adjustment_detail (								 
	adjustmentDetailId int(11) NOT NULL AUTO_INCREMENT,				# �����ӱ�Id
	adjustmentHeaderId int(11) NOT NULL,							# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	billAmountPersonal double NOT NULL,								# �ϼƣ��������룩
	costAmountPersonal double NOT NULL,								# �ϼƣ�����֧����
	taxAmountPersonal double NOT NULL,								# �ϼƣ���˰��
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
  	PRIMARY KEY (ajustmentDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_salary_header (							
	salaryHeaderId int(11) NOT NULL AUTO_INCREMENT,					# ��������Id
	accountId int(11) NOT NULL,										# �˻�Id
	entityId int(11) NOT NULL,										# ����ʵ��Id
	businessTypeId int(11) NOT NULL,								# ҵ������Id
	batchId int(11) DEFAULT NULL,									# ����Id
	clientId int(11) NOT NULL,										# �ͻ�Id
	corpId int(11) NOT NULL,										# �ͻ�Id
	clientNameZH varchar(200) DEFAULT NULL,							# �ͻ����ƣ����ģ�
	clientNameEN varchar(200) DEFAULT NULL,							# �ͻ����ƣ�Ӣ�ģ�
	orderId int(11) NOT NULL,										# ����Id
	contractId int(11) DEFAULT NULL,								# ����Э��Id
	employeeId int(11) DEFAULT NULL,								# ��ԱId
	employeeNameZH varchar(200) DEFAULT NULL,						# ��Ա���������ģ�
	employeeNameEN varchar(200) DEFAULT NULL,						# ��Ա������Ӣ�ģ�
	startDate datetime DEFAULT NULL,								# н�꿪ʼ����
	endDate datetime DEFAULT NULL,									# н���������
	certificateType tinyint(4) DEFAULT NULL,						# ֤������
  	certificateNumber varchar(50) DEFAULT NULL,						# ֤������
  	bankId int(11) DEFAULT NULL,									# ����Id	
  	bankNameZH varchar(200) DEFAULT NULL,							# �������ƣ����ģ�
  	bankNameEN varchar(200) DEFAULT NULL,							# �������ƣ�Ӣ�ģ�
  	bankAccount varchar(50) DEFAULT NULL,							# �����˻�
  	billAmountCompany double DEFAULT 0,								# �ϼƣ���˾Ӫ�գ�
	billAmountPersonal double DEFAULT 0,							# �ϼƣ��������룩
	costAmountCompany double DEFAULT 0,								# �ϼƣ���˾�ɱ���
	costAmountPersonal double DEFAULT 0,							# �ϼƣ�����֧����
	taxAmountPersonal double DEFAULT 0,								# �ϼƣ���˰��
	addtionalBillAmountPersonal double DEFAULT 0,					# ���Ӻϼƣ��������룩������˰ǰ�ӵĽ��
	estimateSalary double DEFAULT 0,								# ˰ǰ����
	actualSalary double DEFAULT 0,									# ˰�����룬���ڵ���˰�ĵ��� 
  	monthly varchar(25) DEFAULT NULL,								# н���·ݣ�����2013/9��
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:�ύ��3:���ţ�			
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (salaryHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;	

CREATE TABLE hro_salary_detail (			
	salaryDetailId int(11) NOT NULL AUTO_INCREMENT,					# ���ʴӱ�Id
	salaryHeaderId int(11) NOT NULL,								# ��������Id
	itemId int(11) NOT NULL,										# ��ĿId
	itemNo varchar(50) DEFAULT NULL,								# ��Ŀ���
	nameZH varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ��Ŀ���ƣ�Ӣ�ģ�
	baseCompany double DEFAULT 0,									# ��������˾��
	basePersonal double DEFAULT 0,									# ���������ˣ�
	billRateCompany double DEFAULT 0,								# ���ʣ���˾Ӫ�գ�
	billRatePersonal double DEFAULT 0,								# ���ʣ��������룩
	costRateCompany double DEFAULT 0,								# ���ʣ���˾�ɱ���
	costRatePersonal double DEFAULT 0,								# ���ʣ�����֧����
	billFixCompany double DEFAULT 0,								# �̶��𣨹�˾Ӫ�գ�
	billFixPersonal double DEFAULT 0,								# �̶��𣨸������룩
	costFixCompany double DEFAULT 0,								# �̶��𣨹�˾�ɱ���
	costFixPersonal double DEFAULT 0,								# �̶��𣨸���֧����
	billAmountCompany double DEFAULT 0,								# �ϼƣ���˾Ӫ�գ�
	billAmountPersonal double DEFAULT 0,							# �ϼƣ��������룩
	costAmountCompany double DEFAULT 0,								# �ϼƣ���˾�ɱ���
	costAmountPersonal double DEFAULT 0,							# �ϼƣ�����֧����
  	description varchar(1000) DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:�ύ��3:���ţ�					
  	remark1 varchar(5000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (salaryDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_common_batch (										# ������ϸ�к��б�ǡ��Ƿ���֧����
	batchId int(11) NOT NULL AUTO_INCREMENT,						# ����Id
	accountId int(11) NOT NULL,										# �˻�Id
	corpId int(11) DEFAULT NULL,									# �ͻ�Id
	vendorId int(11) DEFAULT NULL,									# ��Ӧ��Id
	accessAction varchar(200) DEFAULT NULL,							# ����
	importExcelName varchar(100) DEFAULT NULL,						# �����ļ���
  	description varchar(1000) DEFAULT NULL,	
  	owner  varchar(25)  DEFAULT NULL,							
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬��1:�½���2:����ˣ�3:��Ч��		���ʵ��룺1�½� 2�ύ 3�ѽ���						
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

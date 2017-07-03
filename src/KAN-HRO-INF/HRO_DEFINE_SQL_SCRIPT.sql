use HRO;

DROP TABLE IF EXISTS hro_def_table;									# ���ݱ����ͼ
DROP TABLE IF EXISTS hro_def_column;								# ���ݱ����ͼ���ֶ�
DROP TABLE IF EXISTS hro_def_column_group;							# �ֶη���
DROP TABLE IF EXISTS hro_def_option_header;							# �Զ���ѡ��
DROP TABLE IF EXISTS hro_def_option_detail;							# �Զ���ѡ�� - ��Ӧѡ����
DROP TABLE IF EXISTS hro_def_manager_header;						# �Զ���ҳ��
DROP TABLE IF EXISTS hro_def_manager_detail;						# �Զ���ҳ���ֶ�
DROP TABLE IF EXISTS hro_def_list_header;							# �Զ����б�
DROP TABLE IF EXISTS hro_def_list_detail;							# �Զ����б��ֶ�
DROP TABLE IF EXISTS hro_def_search_header;							# �Զ�������
DROP TABLE IF EXISTS hro_def_search_detail;							# �Զ��������ֶ�
DROP TABLE IF EXISTS hro_def_report_header;							# �Զ��屨��			
DROP TABLE IF EXISTS hro_def_report_detail;							# �Զ��屨���ֶ�
DROP TABLE IF EXISTS hro_def_report_search_detail;					# �Զ��屨�������ֶ�
DROP TABLE IF EXISTS hro_def_import_header;							# �Զ��嵼��
DROP TABLE IF EXISTS hro_def_import_detail;							# �Զ��嵼���ֶ�
DROP TABLE IF EXISTS hro_def_mapping_header;						# �Զ���ƥ��
DROP TABLE IF EXISTS hro_def_mapping_detail;						# �Զ���ƥ���ֶ�
DROP TABLE IF EXISTS hro_def_bank_template_header;					# �Զ������й���ģ��
DROP TABLE IF EXISTS hro_def_bank_template_detail;					# �Զ������й���ģ���ֶ�
DROP TABLE IF EXISTS hro_def_tax_template_header;					# �Զ����˰ģ��
DROP TABLE IF EXISTS hro_def_tax_template_detail;					# �Զ����˰ģ���ֶ�

CREATE TABLE hro_def_table (
	tableId int(11) NOT NULL AUTO_INCREMENT,						# ���ڴ�ű����ͼ
	accountId int(11) NOT NULL,										# ����ǡ�1����˵���ǹ����ı����ͼ
	corpId int(11) DEFAULT NULL,
	nameZH varchar(200) DEFAULT NULL,								# �����ͼ������
	nameEN varchar(200) DEFAULT NULL,								# �����ͼӢ����
	tableType tinyint(4) DEFAULT NULL,								# �����ݿ������ͼ
	tableIndex int(11) DEFAULT NULL,								# �����ݿ������ͼ
	moduleType int(11) DEFAULT NULL,								# �����ͼģ������
	accessAction varchar(200) DEFAULT NULL,							# �����ͼ����˳��
	accessName varchar(200) DEFAULT NULL,							# �����ͼ��Ӧ�����ƣ����ݿ⣩
	accessTempName varchar(200) DEFAULT NULL,						# ��ͼ��Ӧ�ı��Ӧ�����ƣ����ݿ⣩
	linkedTableIds varchar(200) DEFAULT NULL,						# ��ͼ�����ı�
	role tinyint(4) DEFAULT NULL,									# ���ʽ�ɫ��1:HR�����̣�2:In House HR��
	canManager tinyint(4) DEFAULT NULL,								# �Ƿ�֧��ҳ�����
	canList tinyint(4) DEFAULT NULL,								# �Ƿ�֧���б����
	canSearch tinyint(4) DEFAULT NULL,								# �Ƿ�֧����������
	canReport tinyint(4) DEFAULT NULL,								# �Ƿ�֧�ֱ������
	canImport tinyint(4) DEFAULT NULL,								# �Ƿ�֧�ֵ������
	className varchar(200) DEFAULT NULL,							# ��Ӧʵ����
  	description varchar(1000) DEFAULT NULL,							# ����
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
  	PRIMARY KEY (tableId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_column (
  	columnId int(11) NOT NULL AUTO_INCREMENT,
  	accountId int(11) NOT NULL,										# ����ǡ�1����˵���ǹ����ֶ���
  	corpId int(11)  DEFAULT NULL,									# ��ҵID������Inhouseʹ��
  	tableId int(11) NOT NULL,										# ���ݿ�����ͼ
  	nameDB varchar(200) DEFAULT NULL,								# ���ݿ���е�����
  	nameSys varchar(200) DEFAULT NULL,								# ��ϵͳ�е���ʾ����
  	nameCusZH varchar(200) DEFAULT NULL,							# �û��Զ�����ʾ���ƣ����ģ�
  	nameCusEH varchar(200) DEFAULT NULL,							# �û��Զ�����ʾ���ƣ�Ӣ�ģ�
  	groupId int(11) NOT NULL,										# �ֶ��ڽ����ϰ�������ʾ
  	isPrimaryKey tinyint(4) DEFAULT NULL,							# �Ƿ�������	
	isForeignKey tinyint(4) DEFAULT NULL,							# �Ƿ������
	isDBColumn tinyint(4) DEFAULT NULL,								# �Ƿ����ݿ��ֶ�
  	isRequired tinyint(4) DEFAULT NULL,								# �Ƿ�ϵͳ�����											
  	isDisplayed tinyint(4) DEFAULT NULL,							# �Ƿ���ʾ
  	columnIndex smallint(8) DEFAULT NULL,							# ��ʾ˳��
  	inputType tinyint(4) DEFAULT NULL,								# ����HTML�ؼ�������
  	valueType tinyint(4) DEFAULT NULL,								# �ֶ�ֵ������
  	optionType tinyint(4) DEFAULT NULL,								# ������ѡ�����Դ��ϵͳ���С��û��Զ��塢ֱ��ֵ��
  	optionValue	varchar(1000) DEFAULT NULL,							# ������ѡ���ֵ	{1:��;2:Ů}									
  	cssStyle varchar(1000) DEFAULT NULL,							# ��CSS���б�Ԫ�أ���li��ʼ���ã�
  	jsEvent varchar(1000) DEFAULT NULL,								# ��JS������ؼ�
  	validateType varchar(50) DEFAULT NULL,							# ��֤���ͣ�select, email, passwrod, passwrodconfirm, numeric, currency, character, common, phone, mobile, idcard, website, ip��
  	validateRequired varchar(25) DEFAULT NULL,						# ��֤�Ƿ���true or false��
  	validateLength varchar(200) DEFAULT NULL,						# ��֤���볤�ȣ�MIN��MAX���»��߷ָ
  	validateRange varchar(200) DEFAULT NULL,						# ��֤���뷶Χ��MIN��MAX���»��߷ָ
  	editable tinyint(4) DEFAULT NULL,								# �Ƿ���Ա༭
  	useThinking tinyint(4) DEFAULT NULL,							# �Ƿ�ʹ�����빦��
  	thinkingId varchar(200) DEFAULT NULL, 							# ���빦�ܹ�����ColumnId
	thinkingAction varchar(200) DEFAULT NULL, 						# ���빦�ܷ���Action
	useTitle tinyint(4) DEFAULT NULL,								# �Ƿ�ʹ����ʾ
	titleZH varchar(1000) DEFAULT NULL,								# ��ǩ��ʾ�����ģ�
	titleEN varchar(1000) DEFAULT NULL,								# ��ǩ��ʾ��Ӣ�ģ�
	canImport tinyint(4) DEFAULT NULL,								# �Ƿ�֧�ֵ������
  	description varchar(1000) DEFAULT NULL,							# ����
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
  	PRIMARY KEY (columnId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_column_group (
	groupId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										# ����ǡ�1����˵���ǹ����ֶ���
	corpId int(11)  DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	nameZH varchar(200) DEFAULT NULL,								# �ֶ�Ⱥ��������
	nameEN varchar(200) DEFAULT NULL,								# �ֶ�Ⱥ��Ӣ����
	useName tinyint(4) DEFAULT NULL,								# �Ƿ���ʾ����
	useBorder tinyint(4) DEFAULT NULL,								# �Ƿ���ʾ�߿�
	usePadding tinyint(4) DEFAULT NULL,								# �Ƿ�ʹ���ڲ�����
	useMargin tinyint(4) DEFAULT NULL,								# �Ƿ�ʹ���ⲿ��չ
	isDisplayed tinyint(4) DEFAULT NULL,							# �Ƿ���ʾ
	isFlexable tinyint(4) DEFAULT NULL,								# �Ƿ�����չ��������
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (groupId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_option_header (
	optionHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										# ����ǡ�1����˵���ǹ����б�
	corpId int(11)  DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	nameZH varchar(200) DEFAULT NULL,								# �����б�������
	nameEN varchar(200) DEFAULT NULL,								# �����б�Ӣ����
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (optionHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_option_detail (
	optionDetailId int(11) NOT NULL AUTO_INCREMENT,
	optionHeaderId int(11) NOT NULL,
	optionId varchar(100) DEFAULT NULL,								# �����б���ֵ
	optionIndex	smallint(8) DEFAULT NULL,							# �����б�������˳��
	optionNameZH varchar(200) DEFAULT NULL,							# �����б���������
	optionNameEN varchar(200) DEFAULT NULL,							# �����б���Ӣ����
	optionValue varchar(200) DEFAULT NULL,							# �����б����ֵ
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (optionDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_manager_header (
	managerHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										
	corpId int(11)  DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	tableId int(11) NOT NULL, 										# ���ݿ�����ͼ
	nameZH varchar(200) DEFAULT NULL,								# ҳ�����ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# ҳ�����ƣ�Ӣ�ģ�
	comments varchar(1000) DEFAULT NULL,							# ҳ�汸ע��ͨ����ʾ��ҳ���Ϸ��������ֶκ��棩						
  	description varchar(1000) DEFAULT NULL,							# ����
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
  	PRIMARY KEY (managerHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_manager_detail (
	managerDetailId int(11) NOT NULL AUTO_INCREMENT,
	managerHeaderId int(11) NOT NULL,
	columnId int(11) NOT NULL,
	nameZH varchar(200) DEFAULT NULL,								# �ֶ����ƣ����ģ�
	nameEN varchar(200) DEFAULT NULL,								# �ֶ����ƣ�Ӣ�ģ�
  	groupId int(11) NOT NULL,										# �ֶ���		
  	isRequired tinyint(4) DEFAULT NULL,								# �Ƿ����		
  	display tinyint(4) DEFAULT NULL,								# ��ʾ��ʽ
  	columnIndex smallint(8) DEFAULT NULL,							# ��ʾ˳��								
	useTitle tinyint(4) DEFAULT NULL,								# ������ʾ
	titleZH varchar(1000) DEFAULT NULL,								# ��ǩ��ʾ�����ģ�
	titleEN varchar(1000) DEFAULT NULL,								# ��ǩ��ʾ��Ӣ�ģ�
	align varchar(25) DEFAULT NULL,									# �ֶζ��루���󣬿��ң�
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (managerDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_list_header (
	listHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										# ����ǡ�1����˵���ǹ����ֶ���
	corpId int(11)  DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	tableId int(11) NOT NULL, 										# ���ݿ�����ͼ
	parentId int(11) NOT NULL,										# ��������ID
	useJavaObject tinyint(4) DEFAULT NULL,							# ʹ��JAVA����
	javaObjectName varchar(200) DEFAULT NULL,						# JAVA�����޶��������磺com.kan.base.domain.BaseVO��
	searchId int(11) NOT NULL,										# �б����������������
	nameZH varchar(200) DEFAULT NULL,								# �б�������
	nameEN varchar(200) DEFAULT NULL,								# �б�Ӣ����
	pageSize tinyint(4) DEFAULT NULL,								# �б���ÿ��Page��ʾ�ļ�¼��
	loadPages tinyint(4) DEFAULT NULL,								# ���ݿ�Ԥ������ҳ����0�����У���1��ΪĬ��
	usePagination tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ��ҳ
	isSearchFirst tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����������
	exportExcel tinyint(4) DEFAULT NULL,							# �б��Ƿ�֧��Excel��������
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (listHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_list_detail (
	listDetailId int(11) NOT NULL AUTO_INCREMENT,
	listHeaderId int(11) NOT NULL,
	columnId int(11) DEFAULT NULL,
	propertyName varchar(200) DEFAULT NULL,							# ������
	nameZH varchar(200) DEFAULT NULL,								# �б��ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �б��ֶ�Ӣ����
	columnWidth varchar(25) DEFAULT NULL,							# �б��ֶο��
	columnWidthType tinyint(4) DEFAULT NULL,						# �б��ֶο�����ͣ��ٷֱ� - %���̶�ֵ - px��
	columnIndex tinyint(4) DEFAULT NULL,							# �б��ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �б��ֶ������С
	isDecoded tinyint(4) DEFAULT NULL,								# �б��ֶ��Ƿ���Ҫת��
	isLinked tinyint(4) DEFAULT NULL, 								# �Ƿ�����������鿴ҳ��
	linkedAction varchar(200) DEFAULT NULL,	                        # ��������ҳ���Action
	linkedTartget tinyint(4) DEFAULT NULL,	                        # ��������ҳ�����ʾ��ʽ��_self, _blank��
	appendContent varchar(1000) DEFAULT NULL,	                	# ��������
	properties varchar(200) DEFAULT NULL,	                		# ��̬������������linkedAction��appendContent�������䷽ʽ��ȡVO
	datetimeFormat varchar(50) DEFAULT NULL,						# ���ڸ�ʽ����Ϊʹ��Ĭ��
	accuracy tinyint(4) DEFAULT NULL,								# ����С��λ��ȡ��������һλ��������λ��������λ��������λ����Ϊʹ��Ĭ�ϣ�
	round tinyint(4) DEFAULT NULL,									# ��ȡ��ʽ����Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ��
	align varchar(25) DEFAULT NULL,									# �ֶζ��루Left��Center��Right��
	sort tinyint(4) DEFAULT NULL,									# �Ƿ���Ҫ����
	display tinyint(4) DEFAULT NULL,								# ��ǰ�б��ֶ��Ƿ���Ҫ��ʾ
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (listDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_search_header (
	searchHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										# ����ǡ�1����˵���ǹ����ֶ���
	corpId int(11)  DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	tableId int(11) NOT NULL, 										# ���ݿ�����ͼ
	useJavaObject tinyint(4) DEFAULT NULL,							# ʹ��JAVA����
	javaObjectName varchar(200) DEFAULT NULL,						# JAVA�����޶��������磺com.kan.base.domain.BaseVO��
	nameZH varchar(200) DEFAULT NULL,								# ����������
	nameEN varchar(200) DEFAULT NULL,								# ����Ӣ����
	isSearchFirst tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����������
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (searchHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_search_detail (
	searchDetailId int(11) NOT NULL AUTO_INCREMENT,
	searchHeaderId int(11) NOT NULL,
	columnId int(11) DEFAULT NULL,									# �ֶ�
	propertyName varchar(200) DEFAULT NULL,							# ������
	nameZH varchar(200) DEFAULT NULL,								# �����ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �����ֶ�Ӣ����
	columnIndex	tinyint(4) DEFAULT NULL,							# �����ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �����ֶ������С
	useThinking tinyint(4) DEFAULT NULL,							# �Ƿ�ʹ�����빦��
	thinkingAction varchar(200) DEFAULT NULL, 						# ���빦�ܷ���Action
	contentType tinyint(4) DEFAULT NULL,							# �ֶε�ֵ���ͣ�1:ֱ��ֵ��2:����ֵ��
	content varchar(1000) DEFAULT NULL,								# �ֶε�ֵ�����ڱ�������
	range varchar(200) DEFAULT NULL,								# �ֶε�ֵ - Range��Range��MIN��MAX���»��߷ָ�
	display tinyint(4) DEFAULT NULL,								# ��ǰ���������Ƿ���Ҫ��ʾ
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (searchDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_report_header (
	reportHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										# ����ǡ�1����˵���ǹ�������
	corpId int(11)  DEFAULT NULL,									# ��ҵID������Inhouseʹ��
	tableId int(11) NOT NULL, 										# ���ݿ�����ͼ
	nameZH varchar(200) DEFAULT NULL,								# ����������
	nameEN varchar(200) DEFAULT NULL,								# ����Ӣ����
	clicks int(11) DEFAULT NULL,									# �������������
	usePagination tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ��ҳ
	pageSize tinyint(4) DEFAULT NULL,								# ������ÿ��Page��ʾ�ļ�¼��
	loadPages tinyint(4) DEFAULT NULL,								# ���ݿ�Ԥ������ҳ����0�����У���1��ΪĬ��
	isSearchFirst tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����������
	sortColumns varchar(1000) DEFAULT NULL, 						# �����ֶΣ�{1:asc;2:desc}
	groupColumns varchar(1000) DEFAULT NULL,  						# �����ֶΣ�{1:2:3:4}
	statisticsColumns varchar(1000) DEFAULT NULL,  					# ͳ���ֶΣ�{1:sum;2:avg}
	exportExcelType tinyint(4) DEFAULT NULL,						# ����Excel��ʽ {��������/ֱ�ӵ���/������}
	isExportPDF tinyint(4) DEFAULT NULL,	 						# �Ƿ���Ե���PDF
	moduleType tinyint(4) DEFAULT NULL,								# ������ָ��ģ��
	isPublic tinyint(4) DEFAULT NULL,								# �Ƿ񹫹���{1:public;2:private}
	positionIds varchar(5000) DEFAULT NULL,							# ����ְλ����positionId���ϣ�{1:2:3:4}
	positionGradeIds varchar(5000) DEFAULT NULL, 					# ����ְ��
	positionGroupIds varchar(5000) DEFAULT NULL, 					# ����ְλ��
	branch varchar(25) DEFAULT NULL,								# �������ţ�Branch Id��
  	owner varchar(25) DEFAULT NULL,									# �����ˣ�Position Id��
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# ״̬�����á�������ͣ�ã�
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	remark3 varchar(1000) DEFAULT NULL,
  	remark4 varchar(1000) DEFAULT NULL,
  	remark5 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (reportHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_report_detail (
	reportDetailId int(11) NOT NULL AUTO_INCREMENT,
	reportHeaderId int(11) NOT NULL,
	columnId int(11) NOT NULL,
	nameZH varchar(200) DEFAULT NULL,								# �����ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �����ֶ�Ӣ����
	columnWidth varchar(25) DEFAULT NULL,							# �����ֶο�ȣ��ٷֱȻ�̶�ֵ��
	columnWidthType tinyint(4) DEFAULT NULL,					 	# �б��ֶο�����ͣ��ٷֱ� - %���̶�ֵ - px����new��
	columnIndex	tinyint(4) DEFAULT NULL,							# �����ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �����ֶ������С
	isDecoded tinyint(4) DEFAULT NULL,								# �����ֶ��Ƿ���Ҫת��
	isLinked tinyint(4) DEFAULT NULL, 								# �Ƿ�����������鿴ҳ��
	linkedAction varchar(200) DEFAULT NULL,	                     	# ��������ҳ���Action new
	linkedTartget tinyint(4) DEFAULT NULL,	                        # ��������ҳ�����ʾ��ʽ��_self, _blank�� ��new��
	datetimeFormat varchar(50) DEFAULT NULL,						# ���ڸ�ʽ����Ϊʹ��Ĭ��
	accuracy tinyint(4) DEFAULT NULL,								# ����С��λ��ȡ��������һλ��������λ��������λ��������λ����Ϊʹ��Ĭ�ϣ���new��
	round tinyint(4) DEFAULT NULL,									# ��ȡ��ʽ����Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ����new��
	align varchar(25) DEFAULT NULL,									# �ֶζ��루Left��Center��Right����new��
	sort tinyint(4) DEFAULT NULL,									# �Ƿ���Ҫ����new��
	display tinyint(4) DEFAULT NULL,								# ��ǰ�б��ֶ��Ƿ���Ҫ��ʾ
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (reportDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_report_search_detail (
	reportSearchDetailId int(11) NOT NULL AUTO_INCREMENT,
	reportHeaderId int(11) NOT NULL,
	columnId int(11) NOT NULL,										# �ֶ�
	nameZH varchar(200) DEFAULT NULL,								# �����ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �����ֶ�Ӣ����
	columnIndex	tinyint(4) DEFAULT NULL,							# �����ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �����ֶ������С
	useThinking tinyint(4) DEFAULT NULL,							# �Ƿ�ʹ�����빦��
	thinkingAction varchar(200) DEFAULT NULL, 						# ���빦�ܷ���Action
	combineType tinyint(4) DEFAULT NULL,							# ��ϵ�������and��or��
	condition tinyint(4) DEFAULT NULL,								# �ֶε�������=��>��>=��<��<=��like��in��between��
	content varchar(1000) DEFAULT NULL,								# �ֶε�ֵ�����ڱ���������betweenʹ���»��߷ָ
	range varchar(200) DEFAULT NULL,								# �ֶε�ֵ - Range��Range��MIN��MAX���»��߷ָ�
	display tinyint(4) DEFAULT NULL,								# ��ǰ���������Ƿ���Ҫ��ʾ
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (reportSearchDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_import_header (
	importHeaderId int(11) NOT NULL AUTO_INCREMENT,
	parentId int(11) DEFAULT NULL,									# ����ID���������ӹ�ϵ��
	accountId int(11) NOT NULL,										
	corpId int(11) NOT NULL,										
	tableId int(11) NOT NULL, 										# ���ݿ�����ͼ
	nameZH varchar(200) DEFAULT NULL,								# �����б�������
	nameEN varchar(200) DEFAULT NULL,								# �����б�Ӣ����
	positionIds varchar(5000) DEFAULT NULL,							# ����ְλ����PositionId���ϣ�{1:2:3:4}
	positionGradeIds varchar(5000) DEFAULT NULL, 					# ����ְ��
	positionGroupIds varchar(5000) DEFAULT NULL, 					# ����ְλ��
	needBatchId tinyint(4) DEFAULT NULL,							# �Ƿ���Ҫ����
	`matchConfig` varchar(100) DEFAULT NULL,						# ��ͷƥ������
	handlerBeanId varchar(200) DEFAULT NULL,						# ����ʱִ���Զ�ִ�е�IExcelImportHandler��ӦBeanId
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (importHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_import_detail (
	importDetailId int(11) NOT NULL AUTO_INCREMENT,
	importHeaderId int(11) NOT NULL,
	columnId int(11) NOT NULL,
	nameZH varchar(200) DEFAULT NULL,								# �����б��ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �����б��ֶ�Ӣ����
	primaryKey tinyint(4) DEFAULT NULL,								# ��������/��
	isForeignKey tinyint(4) DEFAULT NULL,							# �������/��
	columnWidth varchar(25) DEFAULT NULL,							# �����б��ֶο��
	columnIndex tinyint(4) DEFAULT NULL,							# �����б��ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �����б��ֶ������С
	specialField tinyint(4) DEFAULT NULL,							# �����ֶΣ�1:���У�2:�����ˣ�
	tempValue varchar(1000) DEFAULT NULL,							# ʾ��ֵ
	isDecoded tinyint(4) DEFAULT NULL,								# �б��ֶ��Ƿ���Ҫת��
	isIgnoreDefaultValidate  tinyint(4) DEFAULT NULL,				# �Ƿ����Ĭ����֤����1��������֤������handler����2��
	datetimeFormat varchar(50) DEFAULT NULL,						# ���ڸ�ʽ����Ϊʹ��Ĭ��
	accuracy tinyint(4) DEFAULT NULL,								# ����С��λ��ȡ��������һλ��������λ��������λ��������λ����Ϊʹ��Ĭ�ϣ�
	round tinyint(4) DEFAULT NULL,									# ��ȡ��ʽ����Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ��
	align varchar(25) DEFAULT NULL,									# �ֶζ��루Left��Center��Right������ǰ��������ģ��
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (importDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_mapping_header (
	mappingHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										
	corpId int(11) NOT NULL,										
	reportId int(11) DEFAULT NULL, 									# ����ID�����ڱ�����ʾ�򵼳�Excel��
	importId int(11) DEFAULT NULL, 									# ����ID��reportId��importId��listId����һ���ֶβ���Ϊ��
	listId int(11) DEFAULT NULL, 									# �б�ID��reportId��importId��listId����һ���ֶβ���Ϊ��
	nameZH varchar(200) DEFAULT NULL,								# ƥ��������
	nameEN varchar(200) DEFAULT NULL,								# ƥ��Ӣ����
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (mappingHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_mapping_detail (
	mappingDetailId int(11) NOT NULL AUTO_INCREMENT,
	mappingHeaderId int(11) NOT NULL,
	columnId int(11) DEFAULT NULL,
	propertyName varchar(200) DEFAULT NULL,							# ������
	nameZH varchar(200) DEFAULT NULL,								# �ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �ֶ�Ӣ����
	columnWidth varchar(25) DEFAULT NULL,							# �ֶο��
	columnIndex tinyint(4) DEFAULT NULL,							# �ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �ֶ������С
	isDecoded tinyint(4) DEFAULT NULL,								# �ֶ��Ƿ���Ҫת��
	datetimeFormat varchar(50) DEFAULT NULL,						# ���ڸ�ʽ����Ϊʹ��Ĭ��
	accuracy tinyint(4) DEFAULT NULL,								# ����С��λ��ȡ��������һλ��������λ��������λ��������λ����Ϊʹ��Ĭ�ϣ�
	round tinyint(4) DEFAULT NULL,									# ��ȡ��ʽ����Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ��
	align varchar(25) DEFAULT NULL,									# �ֶζ��루Left��Center��Right��
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (mappingDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_bank_template_header (
	templateHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										
	corpId int(11) DEFAULT NULL,			
	bankId int(11) NOT NULL,
	nameZH varchar(200) DEFAULT NULL,								# ����ģ��������
	nameEN varchar(200) DEFAULT NULL,								# ����ģ��Ӣ����
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (templateHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_bank_template_detail (
	templateDetailId int(11) NOT NULL AUTO_INCREMENT,
	templateHeaderId int(11) NOT NULL,
	propertyName varchar(200) NOT NULL,
	nameZH varchar(200) DEFAULT NULL,								# �ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �ֶ�Ӣ����
	inputType tinyint(4) DEFAULT NULL,								# ����HTML�ؼ������ͣ��ı����Ͳ���ʾ���ڸ�ʽ������С��λ����ȡ��ʽ���������Ͳ���ʾ���ڸ�ʽ��Ĭ�Ͽ��ң����ڸ�ʽ����ʾ����С��λ����ȡ��ʽ��
	columnWidth varchar(25) DEFAULT NULL,							# �ֶο��
	columnIndex tinyint(4) DEFAULT NULL,							# �ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �ֶ������С
	isDecoded tinyint(4) DEFAULT NULL,								# �ֶ��Ƿ���Ҫת��
	datetimeFormat varchar(50) DEFAULT NULL,						# ���ڸ�ʽ����Ϊʹ��Ĭ��
	accuracy tinyint(4) DEFAULT NULL,								# ����С��λ��ȡ��������һλ��������λ��������λ��������λ����Ϊʹ��Ĭ�ϣ�
	round tinyint(4) DEFAULT NULL,									# ��ȡ��ʽ����Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ��
	align varchar(25) DEFAULT NULL,									# �ֶζ��루Left��Right��
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (templateDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_tax_template_header (
	templateHeaderId int(11) NOT NULL AUTO_INCREMENT,
	accountId int(11) NOT NULL,										
	corpId int(11) DEFAULT NULL,			
	cityId int(11) NOT NULL,
	nameZH varchar(200) DEFAULT NULL,								# ��˰ģ��������
	nameEN varchar(200) DEFAULT NULL,								# ��˰ģ��Ӣ����
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (templateHeaderId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_def_tax_template_detail (
	templateDetailId int(11) NOT NULL AUTO_INCREMENT,
	templateHeaderId int(11) NOT NULL,
	propertyName varchar(200) NOT NULL,
	nameZH varchar(200) DEFAULT NULL,								# �ֶ�������
	nameEN varchar(200) DEFAULT NULL,								# �ֶ�Ӣ����
	valueType tinyint(4) DEFAULT NULL,								# �ֶ�ֵ�����ͣ��ı����Ͳ���ʾ���ڸ�ʽ������С��λ����ȡ��ʽ���������Ͳ���ʾ���ڸ�ʽ��Ĭ�Ͽ��ң����ڸ�ʽ����ʾ����С��λ����ȡ��ʽ��
	columnWidth varchar(25) DEFAULT NULL,							# �ֶο��
	columnIndex tinyint(4) DEFAULT NULL,							# �ֶ�����˳��
	fontSize tinyint(4) DEFAULT NULL,								# �ֶ������С
	isDecoded tinyint(4) DEFAULT NULL,								# �ֶ��Ƿ���Ҫת��
	datetimeFormat varchar(50) DEFAULT NULL,						# ���ڸ�ʽ����Ϊʹ��Ĭ��
	accuracy tinyint(4) DEFAULT NULL,								# ����С��λ��ȡ��������һλ��������λ��������λ��������λ����Ϊʹ��Ĭ�ϣ�
	round tinyint(4) DEFAULT NULL,									# ��ȡ��ʽ����Ϊʹ��Ĭ�ϣ�С��λ������ʽ���������룬��ȡ�����Ͻ�λ��
	align varchar(25) DEFAULT NULL,									# �ֶζ��루Left��Right��
  	description varchar(1000) DEFAULT NULL,
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
  	PRIMARY KEY (templateDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
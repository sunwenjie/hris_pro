use HRO;

DROP TABLE IF EXISTS hro_def_table;									# 数据表或视图
DROP TABLE IF EXISTS hro_def_column;								# 数据表或视图的字段
DROP TABLE IF EXISTS hro_def_column_group;							# 字段分组
DROP TABLE IF EXISTS hro_def_option_header;							# 自定义选项
DROP TABLE IF EXISTS hro_def_option_detail;							# 自定义选项 - 对应选择项
DROP TABLE IF EXISTS hro_def_manager_header;						# 自定义页面
DROP TABLE IF EXISTS hro_def_manager_detail;						# 自定义页面字段
DROP TABLE IF EXISTS hro_def_list_header;							# 自定义列表
DROP TABLE IF EXISTS hro_def_list_detail;							# 自定义列表字段
DROP TABLE IF EXISTS hro_def_search_header;							# 自定义搜索
DROP TABLE IF EXISTS hro_def_search_detail;							# 自定义搜索字段
DROP TABLE IF EXISTS hro_def_report_header;							# 自定义报表			
DROP TABLE IF EXISTS hro_def_report_detail;							# 自定义报表字段
DROP TABLE IF EXISTS hro_def_report_search_detail;					# 自定义报表搜索字段
DROP TABLE IF EXISTS hro_def_import_header;							# 自定义导入
DROP TABLE IF EXISTS hro_def_import_detail;							# 自定义导入字段
DROP TABLE IF EXISTS hro_def_mapping_header;						# 自定义匹配
DROP TABLE IF EXISTS hro_def_mapping_detail;						# 自定义匹配字段
DROP TABLE IF EXISTS hro_def_bank_template_header;					# 自定义银行工资模板
DROP TABLE IF EXISTS hro_def_bank_template_detail;					# 自定义银行工资模板字段
DROP TABLE IF EXISTS hro_def_tax_template_header;					# 自定义个税模板
DROP TABLE IF EXISTS hro_def_tax_template_detail;					# 自定义个税模板字段

CREATE TABLE hro_def_table (
	tableId int(11) NOT NULL AUTO_INCREMENT,						# 用于存放表或视图
	accountId int(11) NOT NULL,										# 如果是“1”，说明是公共的表或视图
	corpId int(11) DEFAULT NULL,
	nameZH varchar(200) DEFAULT NULL,								# 表或视图中文名
	nameEN varchar(200) DEFAULT NULL,								# 表或视图英文名
	tableType tinyint(4) DEFAULT NULL,								# 是数据库表还是视图
	tableIndex int(11) DEFAULT NULL,								# 是数据库表还是视图
	moduleType int(11) DEFAULT NULL,								# 表或视图模块类型
	accessAction varchar(200) DEFAULT NULL,							# 表或视图排列顺序
	accessName varchar(200) DEFAULT NULL,							# 表或视图对应的名称（数据库）
	accessTempName varchar(200) DEFAULT NULL,						# 视图对应的表对应的名称（数据库）
	linkedTableIds varchar(200) DEFAULT NULL,						# 视图关联的表
	role tinyint(4) DEFAULT NULL,									# 访问角色（1:HR服务商，2:In House HR）
	canManager tinyint(4) DEFAULT NULL,								# 是否支持页面管理
	canList tinyint(4) DEFAULT NULL,								# 是否支持列表管理
	canSearch tinyint(4) DEFAULT NULL,								# 是否支持搜索管理
	canReport tinyint(4) DEFAULT NULL,								# 是否支持报表管理
	canImport tinyint(4) DEFAULT NULL,								# 是否支持导入管理
	className varchar(200) DEFAULT NULL,							# 对应实体类
  	description varchar(1000) DEFAULT NULL,							# 描述
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
  	accountId int(11) NOT NULL,										# 如果是“1”，说明是公共字段组
  	corpId int(11)  DEFAULT NULL,									# 企业ID，用于Inhouse使用
  	tableId int(11) NOT NULL,										# 数据库表或视图
  	nameDB varchar(200) DEFAULT NULL,								# 数据库表中的名称
  	nameSys varchar(200) DEFAULT NULL,								# 在系统中的显示名称
  	nameCusZH varchar(200) DEFAULT NULL,							# 用户自定义显示名称（中文）
  	nameCusEH varchar(200) DEFAULT NULL,							# 用户自定义显示名称（英文）
  	groupId int(11) NOT NULL,										# 字段在界面上按区域显示
  	isPrimaryKey tinyint(4) DEFAULT NULL,							# 是否是主键	
	isForeignKey tinyint(4) DEFAULT NULL,							# 是否是外键
	isDBColumn tinyint(4) DEFAULT NULL,								# 是否数据库字段
  	isRequired tinyint(4) DEFAULT NULL,								# 是否系统必须的											
  	isDisplayed tinyint(4) DEFAULT NULL,							# 是否显示
  	columnIndex smallint(8) DEFAULT NULL,							# 显示顺序
  	inputType tinyint(4) DEFAULT NULL,								# 界面HTML控件的类型
  	valueType tinyint(4) DEFAULT NULL,								# 字段值的类型
  	optionType tinyint(4) DEFAULT NULL,								# 下拉框选项的来源（系统固有、用户自定义、直接值）
  	optionValue	varchar(1000) DEFAULT NULL,							# 下拉框选项的值	{1:男;2:女}									
  	cssStyle varchar(1000) DEFAULT NULL,							# 绑定CSS到列表元素（从li开始设置）
  	jsEvent varchar(1000) DEFAULT NULL,								# 绑定JS到界面控件
  	validateType varchar(50) DEFAULT NULL,							# 验证类型（select, email, passwrod, passwrodconfirm, numeric, currency, character, common, phone, mobile, idcard, website, ip）
  	validateRequired varchar(25) DEFAULT NULL,						# 验证是否必填（true or false）
  	validateLength varchar(200) DEFAULT NULL,						# 验证输入长度（MIN和MAX用下划线分割）
  	validateRange varchar(200) DEFAULT NULL,						# 验证输入范围（MIN和MAX用下划线分割）
  	editable tinyint(4) DEFAULT NULL,								# 是否可以编辑
  	useThinking tinyint(4) DEFAULT NULL,							# 是否使用联想功能
  	thinkingId varchar(200) DEFAULT NULL, 							# 联想功能关联的ColumnId
	thinkingAction varchar(200) DEFAULT NULL, 						# 联想功能访问Action
	useTitle tinyint(4) DEFAULT NULL,								# 是否使用提示
	titleZH varchar(1000) DEFAULT NULL,								# 标签提示（中文）
	titleEN varchar(1000) DEFAULT NULL,								# 标签提示（英文）
	canImport tinyint(4) DEFAULT NULL,								# 是否支持导入管理
  	description varchar(1000) DEFAULT NULL,							# 描述
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
	accountId int(11) NOT NULL,										# 如果是“1”，说明是公共字段组
	corpId int(11)  DEFAULT NULL,									# 企业ID，用于Inhouse使用
	nameZH varchar(200) DEFAULT NULL,								# 字段群组中文名
	nameEN varchar(200) DEFAULT NULL,								# 字段群组英文名
	useName tinyint(4) DEFAULT NULL,								# 是否显示组名
	useBorder tinyint(4) DEFAULT NULL,								# 是否显示边框
	usePadding tinyint(4) DEFAULT NULL,								# 是否使用内部缩进
	useMargin tinyint(4) DEFAULT NULL,								# 是否使用外部扩展
	isDisplayed tinyint(4) DEFAULT NULL,							# 是否显示
	isFlexable tinyint(4) DEFAULT NULL,								# 是否可灵活展开或收缩
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
	accountId int(11) NOT NULL,										# 如果是“1”，说明是公共列表
	corpId int(11)  DEFAULT NULL,									# 企业ID，用于Inhouse使用
	nameZH varchar(200) DEFAULT NULL,								# 下拉列表中文名
	nameEN varchar(200) DEFAULT NULL,								# 下拉列表英文名
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
	optionId varchar(100) DEFAULT NULL,								# 下拉列表项值
	optionIndex	smallint(8) DEFAULT NULL,							# 下拉列表项排列顺序
	optionNameZH varchar(200) DEFAULT NULL,							# 下拉列表项中文名
	optionNameEN varchar(200) DEFAULT NULL,							# 下拉列表项英文名
	optionValue varchar(200) DEFAULT NULL,							# 下拉列表项备用值
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
	corpId int(11)  DEFAULT NULL,									# 企业ID，用于Inhouse使用
	tableId int(11) NOT NULL, 										# 数据库表或视图
	nameZH varchar(200) DEFAULT NULL,								# 页面名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 页面名称（英文）
	comments varchar(1000) DEFAULT NULL,							# 页面备注（通常显示在页面上方，必填字段后面）						
  	description varchar(1000) DEFAULT NULL,							# 描述
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
	nameZH varchar(200) DEFAULT NULL,								# 字段名称（中文）
	nameEN varchar(200) DEFAULT NULL,								# 字段名称（英文）
  	groupId int(11) NOT NULL,										# 字段组		
  	isRequired tinyint(4) DEFAULT NULL,								# 是否必填		
  	display tinyint(4) DEFAULT NULL,								# 显示方式
  	columnIndex smallint(8) DEFAULT NULL,							# 显示顺序								
	useTitle tinyint(4) DEFAULT NULL,								# 启用提示
	titleZH varchar(1000) DEFAULT NULL,								# 标签提示（中文）
	titleEN varchar(1000) DEFAULT NULL,								# 标签提示（英文）
	align varchar(25) DEFAULT NULL,									# 字段对齐（靠左，靠右）
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
	accountId int(11) NOT NULL,										# 如果是“1”，说明是公共字段组
	corpId int(11)  DEFAULT NULL,									# 企业ID，用于Inhouse使用
	tableId int(11) NOT NULL, 										# 数据库表或视图
	parentId int(11) NOT NULL,										# 关联主表ID
	useJavaObject tinyint(4) DEFAULT NULL,							# 使用JAVA对象？
	javaObjectName varchar(200) DEFAULT NULL,						# JAVA对象限定名（例如：com.kan.base.domain.BaseVO）
	searchId int(11) NOT NULL,										# 列表相关联的搜索条件
	nameZH varchar(200) DEFAULT NULL,								# 列表中文名
	nameEN varchar(200) DEFAULT NULL,								# 列表英文名
	pageSize tinyint(4) DEFAULT NULL,								# 列表中每个Page显示的记录数
	loadPages tinyint(4) DEFAULT NULL,								# 数据库预读多少页，“0”所有，“1”为默认
	usePagination tinyint(4) DEFAULT NULL,							# 是否需要分页
	isSearchFirst tinyint(4) DEFAULT NULL,							# 是否需要先输入条件
	exportExcel tinyint(4) DEFAULT NULL,							# 列表是否支持Excel导出功能
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
	propertyName varchar(200) DEFAULT NULL,							# 参数名
	nameZH varchar(200) DEFAULT NULL,								# 列表字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 列表字段英文名
	columnWidth varchar(25) DEFAULT NULL,							# 列表字段宽度
	columnWidthType tinyint(4) DEFAULT NULL,						# 列表字段宽度类型（百分比 - %，固定值 - px）
	columnIndex tinyint(4) DEFAULT NULL,							# 列表字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 列表字段字体大小
	isDecoded tinyint(4) DEFAULT NULL,								# 列表字段是否需要转译
	isLinked tinyint(4) DEFAULT NULL, 								# 是否连接至详情查看页面
	linkedAction varchar(200) DEFAULT NULL,	                        # 链接详情页面的Action
	linkedTartget tinyint(4) DEFAULT NULL,	                        # 链接详情页面的显示方式（_self, _blank）
	appendContent varchar(1000) DEFAULT NULL,	                	# 附加内容
	properties varchar(200) DEFAULT NULL,	                		# 动态参数（作用于linkedAction和appendContent），反射方式获取VO
	datetimeFormat varchar(50) DEFAULT NULL,						# 日期格式，空为使用默认
	accuracy tinyint(4) DEFAULT NULL,								# 保留小数位（取整，保留一位，保留二位，保留三位，保留四位）空为使用默认，
	round tinyint(4) DEFAULT NULL,									# 截取方式，空为使用默认，小数位保留方式（四舍五入，截取，向上进位）
	align varchar(25) DEFAULT NULL,									# 字段对齐（Left，Center，Right）
	sort tinyint(4) DEFAULT NULL,									# 是否需要排序
	display tinyint(4) DEFAULT NULL,								# 当前列表字段是否需要显示
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
	accountId int(11) NOT NULL,										# 如果是“1”，说明是公共字段组
	corpId int(11)  DEFAULT NULL,									# 企业ID，用于Inhouse使用
	tableId int(11) NOT NULL, 										# 数据库表或视图
	useJavaObject tinyint(4) DEFAULT NULL,							# 使用JAVA对象？
	javaObjectName varchar(200) DEFAULT NULL,						# JAVA对象限定名（例如：com.kan.base.domain.BaseVO）
	nameZH varchar(200) DEFAULT NULL,								# 搜索中文名
	nameEN varchar(200) DEFAULT NULL,								# 搜索英文名
	isSearchFirst tinyint(4) DEFAULT NULL,							# 是否需要先输入条件
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
	columnId int(11) DEFAULT NULL,									# 字段
	propertyName varchar(200) DEFAULT NULL,							# 参数名
	nameZH varchar(200) DEFAULT NULL,								# 搜索字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 搜索字段英文名
	columnIndex	tinyint(4) DEFAULT NULL,							# 搜索字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 搜索字段字体大小
	useThinking tinyint(4) DEFAULT NULL,							# 是否使用联想功能
	thinkingAction varchar(200) DEFAULT NULL, 						# 联想功能访问Action
	contentType tinyint(4) DEFAULT NULL,							# 字段的值类型（1:直接值，2:区间值）
	content varchar(1000) DEFAULT NULL,								# 字段的值，用于报表搜索
	range varchar(200) DEFAULT NULL,								# 字段的值 - Range，Range的MIN和MAX用下划线分割
	display tinyint(4) DEFAULT NULL,								# 当前搜索条件是否需要显示
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
	accountId int(11) NOT NULL,										# 如果是“1”，说明是公共报表
	corpId int(11)  DEFAULT NULL,									# 企业ID，用于Inhouse使用
	tableId int(11) NOT NULL, 										# 数据库表或视图
	nameZH varchar(200) DEFAULT NULL,								# 报表中文名
	nameEN varchar(200) DEFAULT NULL,								# 报表英文名
	clicks int(11) DEFAULT NULL,									# 点击率用于排序
	usePagination tinyint(4) DEFAULT NULL,							# 是否需要分页
	pageSize tinyint(4) DEFAULT NULL,								# 报表中每个Page显示的记录数
	loadPages tinyint(4) DEFAULT NULL,								# 数据库预读多少页，“0”所有，“1”为默认
	isSearchFirst tinyint(4) DEFAULT NULL,							# 是否需要先输入条件
	sortColumns varchar(1000) DEFAULT NULL, 						# 排序字段，{1:asc;2:desc}
	groupColumns varchar(1000) DEFAULT NULL,  						# 分组字段，{1:2:3:4}
	statisticsColumns varchar(1000) DEFAULT NULL,  					# 统计字段，{1:sum;2:avg}
	exportExcelType tinyint(4) DEFAULT NULL,						# 导出Excel方式 {搜索导出/直接导出/不导出}
	isExportPDF tinyint(4) DEFAULT NULL,	 						# 是否可以导出PDF
	moduleType tinyint(4) DEFAULT NULL,								# 发布到指定模块
	isPublic tinyint(4) DEFAULT NULL,								# 是否公共，{1:public;2:private}
	positionIds varchar(5000) DEFAULT NULL,							# 开放职位（存positionId集合）{1:2:3:4}
	positionGradeIds varchar(5000) DEFAULT NULL, 					# 开放职级
	positionGroupIds varchar(5000) DEFAULT NULL, 					# 开放职位组
	branch varchar(25) DEFAULT NULL,								# 所属部门（Branch Id）
  	owner varchar(25) DEFAULT NULL,									# 所属人（Position Id）
  	description varchar(1000) DEFAULT NULL,
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,									# 状态（启用、发布、停用）
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
	nameZH varchar(200) DEFAULT NULL,								# 报表字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 报表字段英文名
	columnWidth varchar(25) DEFAULT NULL,							# 报表字段宽度（百分比或固定值）
	columnWidthType tinyint(4) DEFAULT NULL,					 	# 列表字段宽度类型（百分比 - %，固定值 - px）（new）
	columnIndex	tinyint(4) DEFAULT NULL,							# 报表字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 报表字段字体大小
	isDecoded tinyint(4) DEFAULT NULL,								# 报表字段是否需要转译
	isLinked tinyint(4) DEFAULT NULL, 								# 是否连接至详情查看页面
	linkedAction varchar(200) DEFAULT NULL,	                     	# 链接详情页面的Action new
	linkedTartget tinyint(4) DEFAULT NULL,	                        # 链接详情页面的显示方式（_self, _blank） （new）
	datetimeFormat varchar(50) DEFAULT NULL,						# 日期格式，空为使用默认
	accuracy tinyint(4) DEFAULT NULL,								# 保留小数位（取整，保留一位，保留二位，保留三位，保留四位）空为使用默认，（new）
	round tinyint(4) DEFAULT NULL,									# 截取方式，空为使用默认，小数位保留方式（四舍五入，截取，向上进位）（new）
	align varchar(25) DEFAULT NULL,									# 字段对齐（Left，Center，Right）（new）
	sort tinyint(4) DEFAULT NULL,									# 是否需要排序（new）
	display tinyint(4) DEFAULT NULL,								# 当前列表字段是否需要显示
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
	columnId int(11) NOT NULL,										# 字段
	nameZH varchar(200) DEFAULT NULL,								# 搜索字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 搜索字段英文名
	columnIndex	tinyint(4) DEFAULT NULL,							# 搜索字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 搜索字段字体大小
	useThinking tinyint(4) DEFAULT NULL,							# 是否使用联想功能
	thinkingAction varchar(200) DEFAULT NULL, 						# 联想功能访问Action
	combineType tinyint(4) DEFAULT NULL,							# 组合的条件（and，or）
	condition tinyint(4) DEFAULT NULL,								# 字段的条件（=，>，>=，<，<=，like，in，between）
	content varchar(1000) DEFAULT NULL,								# 字段的值，用于报表搜索（between使用下划线分割）
	range varchar(200) DEFAULT NULL,								# 字段的值 - Range，Range的MIN和MAX用下划线分割
	display tinyint(4) DEFAULT NULL,								# 当前搜索条件是否需要显示
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
	parentId int(11) DEFAULT NULL,									# 主表ID，用于主从关系表
	accountId int(11) NOT NULL,										
	corpId int(11) NOT NULL,										
	tableId int(11) NOT NULL, 										# 数据库表或视图
	nameZH varchar(200) DEFAULT NULL,								# 导入列表中文名
	nameEN varchar(200) DEFAULT NULL,								# 导入列表英文名
	positionIds varchar(5000) DEFAULT NULL,							# 开放职位（存PositionId集合）{1:2:3:4}
	positionGradeIds varchar(5000) DEFAULT NULL, 					# 开放职级
	positionGroupIds varchar(5000) DEFAULT NULL, 					# 开放职位组
	needBatchId tinyint(4) DEFAULT NULL,							# 是否需要批次
	`matchConfig` varchar(100) DEFAULT NULL,						# 表头匹配配置
	handlerBeanId varchar(200) DEFAULT NULL,						# 导入时执行自动执行的IExcelImportHandler对应BeanId
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
	nameZH varchar(200) DEFAULT NULL,								# 导入列表字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 导入列表字段英文名
	primaryKey tinyint(4) DEFAULT NULL,								# 主键（是/否）
	isForeignKey tinyint(4) DEFAULT NULL,							# 外键（是/否）
	columnWidth varchar(25) DEFAULT NULL,							# 导入列表字段宽度
	columnIndex tinyint(4) DEFAULT NULL,							# 导入列表字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 导入列表字段字体大小
	specialField tinyint(4) DEFAULT NULL,							# 特殊字段（1:城市，2:所属人）
	tempValue varchar(1000) DEFAULT NULL,							# 示例值
	isDecoded tinyint(4) DEFAULT NULL,								# 列表字段是否需要转译
	isIgnoreDefaultValidate  tinyint(4) DEFAULT NULL,				# 是否忽略默认验证，（1：忽略验证，交给handler处理，2）
	datetimeFormat varchar(50) DEFAULT NULL,						# 日期格式，空为使用默认
	accuracy tinyint(4) DEFAULT NULL,								# 保留小数位（取整，保留一位，保留二位，保留三位，保留四位）空为使用默认，
	round tinyint(4) DEFAULT NULL,									# 截取方式，空为使用默认，小数位保留方式（四舍五入，截取，向上进位）
	align varchar(25) DEFAULT NULL,									# 字段对齐（Left，Center，Right），当前用于生成模板
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
	reportId int(11) DEFAULT NULL, 									# 报表ID（用于报表显示或导出Excel）
	importId int(11) DEFAULT NULL, 									# 导入ID，reportId、importId、listId至少一个字段不能为空
	listId int(11) DEFAULT NULL, 									# 列表ID，reportId、importId、listId至少一个字段不能为空
	nameZH varchar(200) DEFAULT NULL,								# 匹配中文名
	nameEN varchar(200) DEFAULT NULL,								# 匹配英文名
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
	propertyName varchar(200) DEFAULT NULL,							# 参数名
	nameZH varchar(200) DEFAULT NULL,								# 字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 字段英文名
	columnWidth varchar(25) DEFAULT NULL,							# 字段宽度
	columnIndex tinyint(4) DEFAULT NULL,							# 字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 字段字体大小
	isDecoded tinyint(4) DEFAULT NULL,								# 字段是否需要转译
	datetimeFormat varchar(50) DEFAULT NULL,						# 日期格式，空为使用默认
	accuracy tinyint(4) DEFAULT NULL,								# 保留小数位（取整，保留一位，保留二位，保留三位，保留四位）空为使用默认，
	round tinyint(4) DEFAULT NULL,									# 截取方式，空为使用默认，小数位保留方式（四舍五入，截取，向上进位）
	align varchar(25) DEFAULT NULL,									# 字段对齐（Left，Center，Right）
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
	nameZH varchar(200) DEFAULT NULL,								# 工资模板中文名
	nameEN varchar(200) DEFAULT NULL,								# 工资模板英文名
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
	nameZH varchar(200) DEFAULT NULL,								# 字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 字段英文名
	inputType tinyint(4) DEFAULT NULL,								# 界面HTML控件的类型（文本类型不显示日期格式、保留小数位、截取方式；数字类型不显示日期格式，默认靠右；日期格式不显示保留小数位、截取方式）
	columnWidth varchar(25) DEFAULT NULL,							# 字段宽度
	columnIndex tinyint(4) DEFAULT NULL,							# 字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 字段字体大小
	isDecoded tinyint(4) DEFAULT NULL,								# 字段是否需要转译
	datetimeFormat varchar(50) DEFAULT NULL,						# 日期格式，空为使用默认
	accuracy tinyint(4) DEFAULT NULL,								# 保留小数位（取整，保留一位，保留二位，保留三位，保留四位）空为使用默认，
	round tinyint(4) DEFAULT NULL,									# 截取方式，空为使用默认，小数位保留方式（四舍五入，截取，向上进位）
	align varchar(25) DEFAULT NULL,									# 字段对齐（Left，Right）
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
	nameZH varchar(200) DEFAULT NULL,								# 个税模板中文名
	nameEN varchar(200) DEFAULT NULL,								# 个税模板英文名
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
	nameZH varchar(200) DEFAULT NULL,								# 字段中文名
	nameEN varchar(200) DEFAULT NULL,								# 字段英文名
	valueType tinyint(4) DEFAULT NULL,								# 字段值的类型（文本类型不显示日期格式、保留小数位、截取方式；数字类型不显示日期格式，默认靠右；日期格式不显示保留小数位、截取方式）
	columnWidth varchar(25) DEFAULT NULL,							# 字段宽度
	columnIndex tinyint(4) DEFAULT NULL,							# 字段排列顺序
	fontSize tinyint(4) DEFAULT NULL,								# 字段字体大小
	isDecoded tinyint(4) DEFAULT NULL,								# 字段是否需要转译
	datetimeFormat varchar(50) DEFAULT NULL,						# 日期格式，空为使用默认
	accuracy tinyint(4) DEFAULT NULL,								# 保留小数位（取整，保留一位，保留二位，保留三位，保留四位）空为使用默认，
	round tinyint(4) DEFAULT NULL,									# 截取方式，空为使用默认，小数位保留方式（四舍五入，截取，向上进位）
	align varchar(25) DEFAULT NULL,									# 字段对齐（Left，Right）
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
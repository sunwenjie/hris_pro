use HRO;

DROP TABLE IF EXISTS hro_sys_account;								# 账户信息，可理解为帐套
DROP TABLE IF EXISTS hro_sys_module;								# 可以理解为菜单管理
DROP TABLE IF EXISTS hro_sys_account_module_relation;				# 不同Account能访问的模块
DROP TABLE IF EXISTS hro_sys_right;									# 功能访问权限，简称权限
DROP TABLE IF EXISTS hro_sys_rule;									# 数据访问权限，简称规则
DROP TABLE IF EXISTS hro_sys_workflow;								# 工作流模块，当前系统中支持工作流的模块
DROP TABLE IF EXISTS hro_sys_country;								# 国家记录，用户不可增加
DROP TABLE IF EXISTS hro_sys_province;								# 省份记录，用户不可增加
DROP TABLE IF EXISTS hro_sys_city;									# 城市记录，用户不可增加
DROP TABLE IF EXISTS hro_sys_smsconfig;								# 短信配置，可添加多个短信供应商（选择2-3家只发送，避免短信在一家供应商发送不成功；选择一家支持短信接收）
DROP TABLE IF EXISTS hro_sys_social_benefit_header;					# 社保主表，按照城市存放	
DROP TABLE IF EXISTS hro_sys_social_benefit_detail;					# 社保从表，按照城市的不同险种存放
DROP TABLE IF EXISTS hro_sys_constant;								# 动态常量配置（映射）
DROP TABLE IF EXISTS hro_sys_income_tax_base;						# 个税起征
DROP TABLE IF EXISTS hro_sys_income_tax_range_header;				# 个税税率主表
DROP TABLE IF EXISTS hro_sys_income_tax_range_detail;				# 个税税率从表

CREATE TABLE hro_sys_account (
  	accountId int(11) NOT NULL AUTO_INCREMENT,						# 账户信息，“1”为超级账户
  	nameCN varchar(50) DEFAULT NULL,								# 账户中文简写
  	nameEN varchar(50) DEFAULT NULL,								# 账户英文简写
  	entityName varchar(200) DEFAULT NULL,							# 账户对应公司名称（法律上注册名称）
  	accountType tinyint(4) default null								# 账户类型
  	linkman varchar(100) DEFAULT NULL,								# 账户联系人
  	salutation tinyint(4) DEFAULT NULL,								# 称呼，即性别
  	title varchar(200) DEFAULT NULL,								# 账户联系人职位
  	department varchar(100) DEFAULT NULL,							# 账户联系人所在部门
  	bizPhone varchar(50) DEFAULT NULL,								# 工作电话
  	personalPhone varchar(50) DEFAULT NULL,							# 家庭电话
  	bizMobile varchar(50) DEFAULT NULL,								# 工作手机
  	personalMobile varchar(50) DEFAULT NULL,						# 私人手机
  	otherPhone varchar(50) DEFAULT NULL,							# 其他电话
  	fax varchar(50) DEFAULT NULL,									# 传真
  	bizEmail varchar(200) DEFAULT NULL,								# 工作邮箱
  	personalEmail varchar(200) DEFAULT NULL,						# 私人邮箱
  	website varchar(200) DEFAULT NULL,								# 公司网址
  	canAdvBizEmail tinyint(4) DEFAULT NULL,							# 公司邮箱能否发送广告信息
  	canAdvPersonalEmail tinyint(4) DEFAULT NULL,					# 私人邮箱能否发送广告信息
  	canAdvBizSMS tinyint(4) DEFAULT NULL,							# 公司手机能否发送广告信息
  	canAdvPersonalSMS tinyint(4) DEFAULT NULL,						# 私人手机能否发送广告信息
  	cityId int(11) DEFAULT NULL,									# 账户联系人所在城市
  	address varchar(200) DEFAULT NULL,								# 账户联系人地址
  	postcode varchar(50) DEFAULT NULL,								# 账户联系人邮编
  	bindIP varchar(1000) DEFAULT NULL,								# 账户绑定IP地址
  	imageFile varchar(1000) DEFAULT NULL,							# 公司形象照片存放路径
  	description varchar(5000) DEFAULT NULL,							# 公司描述，即企业介绍
  	comment varchar(1000) DEFAULT NULL,								# 评论，平台管理使用
  	initialized tinyint(4) DEFAULT NULL,							# 是否已经初始化
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
  	PRIMARY KEY (accountId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_module (
  	moduleId int(11) NOT NULL AUTO_INCREMENT,						# 模块ID，即菜单ID，系统初始化开发人员Insert（ID需固定）
  	moduleName varchar(100) DEFAULT NULL,							# 模块名称，用于CSS标识
  	moduleFlag int(11) DEFAULT NULL,							    # 模块类型，（1：菜单 2:tab）
  	nameZH varchar(200) DEFAULT NULL,								# 模块名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 模块名称（英文）
  	titleZH varchar(200) DEFAULT NULL,								# 模块标识（中文）
  	titleEN varchar(200) DEFAULT NULL,								# 模块标识（英文）
  	role tinyint(4) DEFAULT NULL,									# 角色，区分公共(0)，HR_Service(1) 和 In_House(2)
  	property varchar(200) DEFAULT NULL,								# 资源文件名称
  	moduleType tinyint(4) DEFAULT NULL,								# 模块类别（客户，供应商，雇员等）
  	accessAction varchar(200) DEFAULT NULL,							# 模块对应的Action
  	defaultAction varchar(200) DEFAULT NULL,						# 默认Action
  	listAction varchar(200) DEFAULT NULL,							# 列表功能Action
  	newAction varchar(200) DEFAULT NULL,							# 新建功能Action
  	toNewAction varchar(200) DEFAULT NULL,							# 跳转至新建功能Action
  	modifyAction varchar(200) DEFAULT NULL,							# 修改功能Action
  	toModifyAction varchar(200) DEFAULT NULL,						# 跳转至修改功能Action
  	deleteAction varchar(200) DEFAULT NULL,							# 删除功能Action						
  	deletesAction varchar(200) DEFAULT NULL,						# 删除列表功能Action
  	parentModuleId int(11) DEFAULT NULL,							# 父功能节点ID
	levelOneModuleName varchar(100) DEFAULT NULL,					# 一级功能模块名称，用于CSS标识
	levelTwoModuleName varchar(100) DEFAULT NULL, 					# 二级功能模块名称，用于CSS标识
	levelThreeModuleName varchar(100) DEFAULT NULL, 				# 三级功能模块名称，用于CSS标识
	moduleIndex	smallint(8) DEFAULT NULL,							# 模块顺序
	rightIds varchar(200) DEFAULT NULL,								# 模块权限 - 即模块有哪些操作权限（Right Table中多选，Jason方式存储）
	ruleIds varchar(200) DEFAULT NULL,								# 模块规则 - 即模块有哪些操作规则（Right Table中多选，Jason方式存储）
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
  	PRIMARY KEY (moduleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_account_module_relation (	
	relationId int(11) NOT NULL AUTO_INCREMENT,						# 关系ID，Super设置，不同账户能访问的模块
	accountId int(11) NOT NULL,										# 账户ID
  	moduleId int(11) NOT NULL,										# 模块ID
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
  	PRIMARY KEY (relationId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_right (						
	/* 
	 * 新建（New） - 模块和字段使用；
	 * 查看（View） - 模块和字段使用；
	 * 修改（Modify） - 模块和字段使用；
	 * 删除（Delete） - 模块使用；
	 * 列表（List） - 模块使用；
	 * 提交（Submit） - 模块使用
	 */ 
  	rightId int(11) NOT NULL AUTO_INCREMENT,						# 功能权限ID，系统初始化开发人员Insert（ID需固定）
  	rightType tinyint(4) DEFAULT NULL,								# 功能权限类型，“1”模块，“2”字段
  	nameZH varchar(200) DEFAULT NULL,								# 功能权限中文名
  	nameEN varchar(200) DEFAULT NULL,								# 功能权限英文名
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
  	PRIMARY KEY (rightId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_rule (						
	/* 
	 * 公开：只读；
	 * 公开：读取、创建/编辑；
	 * 公开：读取、创建/编辑、删除；
	 * 部门：不可见；
	 * 部门：只读；
	 * 部门：读取、创建/编辑；
	 * 部门：读取、创建/编辑、删除；
	 * 直线上级：只读；
	 * 直线上级：读取、创建/编辑；
	 * 直线上级：读取、创建/编辑、删除；
	 * 下属：不可见；
	 * 下属：只读；
	 * 下属：读取、创建/编辑；
	 * 下属：读取、创建/编辑、删除；
	 * 私有：读取、创建/编辑；
	 * 私有：读取、创建/编辑、删除；
	 */ 
  	ruleId int(11) NOT NULL AUTO_INCREMENT,							# 数据规则ID，系统初始化开发人员Insert（ID需固定）
	ruleType tinyint(4) NOT NULL,									# 数据规则类型，“1”公开，“2”部门，“3”直线上级，“4”下属，“5”私有
  	nameZH varchar(200) DEFAULT NULL,								# 数据规则中文名
  	nameEN varchar(200) DEFAULT NULL,								# 数据规则英文名
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
  	PRIMARY KEY (ruleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_workflow (
  	workflowModuleId int(11) NOT NULL AUTO_INCREMENT,
  	systemId tinyint(4) NOT NULL,									# 系统Id，HRO、HRM等
  	moduleId int(11) NOT NULL,										# 系统模块id
  	scopeType tinyint(4) NOT NULL,									# 作用范围（1:HrService,2:InHourse）
  	nameZH varchar(200) DEFAULT NULL,								# 中文名
  	nameEN varchar(200) DEFAULT NULL,								# 英文名
  	rightIds varchar(200) DEFAULT NULL,								# 工作流触发操作权限Ids，Json方式存储{1,3,4,6}
  	includeViewObjJsp varchar(1000) DEFAULT NULL,					# 包含的展示审核对象的jsp页面
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
  	PRIMARY KEY (workflowModuleId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_country (
  	countryId int(11) NOT NULL AUTO_INCREMENT,						# 国家ID
  	countryNumber varchar(10) DEFAULT NULL,							# 国家编号（国际），156，840，826
  	countryCode varchar(10) DEFAULT NULL,							# 国家编码（国际），CN，US，UK
  	countryNameZH varchar(200) DEFAULT NULL,						# 国家中文名
  	countryNameEN varchar(200) DEFAULT NULL,						# 国家英文名
  	countryISO3 varchar(5) DEFAULT NULL,							# 国家编码（ISO），CHN，USA，GBR
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
  	PRIMARY KEY (countryId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_province (
  	provinceId int(11) NOT NULL AUTO_INCREMENT,						# 省ID
  	countryId int(11) NOT NULL,										# 国家ID
  	provinceNameZH varchar(200) DEFAULT NULL,						# 省中文名
  	provinceNameEN varchar(200) DEFAULT NULL,						# 省英文名
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (provinceId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_city (
  	cityId int(11) NOT NULL AUTO_INCREMENT,							# 城市ID
  	provinceId int(11) NOT NULL,									# 省ID
  	cityNameZH varchar(200) NOT NULL,								# 城市中文名
  	cityNameEN varchar(200) NOT NULL,								# 城市英文名
  	cityCode varchar(10) NOT NULL,									# 城市编号（区号）010，021						
  	cityISO3 varchar(5) NOT NULL,									# 城市编码（ISO）PEK，SHA
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (cityId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_smsconfig (
  	configId int(11) NOT NULL AUTO_INCREMENT,						# 短信配置ID，一个ID代表一家供应商
  	nameZH varchar(200) NOT NULL,									# 短信配置中文名
  	nameEN varchar(200) NOT NULL,									# 短信配置英文名
  	serverHost varchar(200) NOT NULL,								# 短信服务器地址						
  	serverPort varchar(5) NOT NULL,									# 短信服务器端口
  	username varchar(50) NOT NULL,									# 用户名
  	password varchar(50) NOT NULL,									# 密码，加密存储
  	price double DEFAULT NULL,										# 向客户收费的价格
  	sendTime varchar(25) DEFAULT NULL,								# 发送延迟，通常“0”表示立即发送，视供应商提供接口而定
  	sendType varchar(5) DEFAULT NULL,								# 发送类型，（通常，默认发送、延迟发送或即时发送），视供应商提供接口而定
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
  	PRIMARY KEY (configId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_social_benefit_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 社保主表ID，一个ID代表一个城市的一种户籍类型
  	nameZH varchar(200) NOT NULL,									# 社保名称（中文）
  	nameEN varchar(200) NOT NULL,									# 社保名称（英文）
  	cityId int(11) NOT NULL,										# 社保城市		
  	termMonth varchar(50) DEFAULT NULL,								# 缴纳周期（每月缴纳，季度缴纳还是年缴；存储月份“1,2,3,4,5,6,7,8,9,10,11,12”）
  	residency varchar(50) DEFAULT NULL,								# 户籍（全适用，本地城镇，外地城镇，本地农村，外地农村，港澳台，外藉；如果主表不选择，则按照从表筛选）
  	adjustMonth varchar(25) DEFAULT NULL,							# 每年调整月份；如果主表不选择，则按照从表筛选
  	attribute tinyint(4) DEFAULT NULL,								# 社保所属月（当月，次月，上月；如果主表不选择，则按照从表筛选）
  	effective tinyint(4) DEFAULT NULL,								# 社保发生月（当月；如果主表不选择，则按照从表筛选）
  	startDateLimit varchar(25) DEFAULT NULL,						# 申报开始时间（填写一个月中的几号；如果主表不选择，则按照从表筛选）
  	endDateLimit varchar(25) DEFAULT NULL,							# 申报截止时间（填写一个月中的几号；如果主表不选择，则按照从表筛选）
  	startRule tinyint(4) DEFAULT NULL,								# 开始月社保缴纳规则（按日期 - 15号以后入职需缴社保，按工作日天数 - 上1天班缴纳社保，按自然月天数 - 超过5天需缴纳社保；如果主表不选择，则按照从表筛选）
  	startRuleRemark varchar(25) DEFAULT NULL,						# 开始月社保缴纳规则备注；如果主表不选择，则按照从表筛选
  	endRule tinyint(4) DEFAULT NULL,								# 结束月社保缴纳规则；如果主表不选择，则按照从表筛选
  	endRuleRemark varchar(25) DEFAULT NULL,							# 结束月社保缴纳规则备注；如果主表不选择，则按照从表筛选
  	makeup tinyint(4) DEFAULT NULL,									# 是否可以补缴；如果主表不选择，则按照从表筛选
  	makeupMonth varchar(25) DEFAULT NULL,							# 可补缴月数（“0”不可以补缴，数字表示可补缴月数，空白表示任意；如果主表不选择，则按照从表筛选）
  	makeupCrossYear tinyint(4) DEFAULT NULL,						# 能否跨年补缴；如果主表不选择，则按照从表筛选
  	accuracy tinyint(4) DEFAULT NULL,								# 空为使用默认，保留小数位（取整，保留一位，保留二位；如果主表不选择，则按照从表筛选）
  	round tinyint(4) DEFAULT NULL,									# 空为使用默认，小数位保留方式（四舍五入，截取，向上进位；如果主表不选择，则按照从表筛选）
  	attachment varchar(1000) DEFAULT NULL,							# 社保附件
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
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_social_benefit_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 社保从表ID，一个ID代表一个社保险种
  	headerId int(11) NOT NULL,										# 社保主表ID
  	itemId int(11) NOT NULL,										# 科目ID
  	companyPercentLow varchar(50) DEFAULT NULL,						# 公司比例（低）
  	companyPercentHight double DEFAULT NULL,						# 公司比例（高）
  	personalPercentLow varchar(50) DEFAULT NULL,					# 个人比例（低）
  	personalPercentHight double DEFAULT NULL,						# 个人比例（高）
  	companyFloor double DEFAULT NULL,								# 公司基数（低）
  	companyCap double DEFAULT NULL,									# 公司基数（高）
  	personalFloor double DEFAULT NULL,								# 个人基数（低）
  	personalCap double DEFAULT NULL,								# 个人基数（高）
  	companyFixAmount double DEFAULT NULL,							# 公司固定金
  	personalFixAmount double DEFAULT NULL,							# 个人固定金
  	termMonth varchar(50) DEFAULT NULL,								# 缴纳周期（每月缴纳，季度缴纳还是年缴；存储月份“1,2,3,4,5,6,7,8,9,10,11,12”；从表不选则继承主表设置）
  	residency varchar(50) DEFAULT NULL,								# 户籍（全适用，本地城镇，外地城镇，本地农村，外地农村，港澳台，外藉；从表不选则继承主表设置）
  	adjustMonth varchar(25) DEFAULT NULL,							# 每年调整月份；从表不选则继承主表设置
  	attribute tinyint(4) DEFAULT NULL,								# 社保所属月（当月，次月，上月；从表不选则继承主表设置）
  	effective tinyint(4) DEFAULT NULL,								# 社保发生月（当月；从表不选则继承主表设置）
  	startDateLimit varchar(25) DEFAULT NULL,						# 申报开始时间（填写一个月中的几号；从表不选则继承主表设置）
  	endDateLimit varchar(25) DEFAULT NULL,							# 申报截止时间（填写一个月中的几号；从表不选则继承主表设置）
  	startRule tinyint(4) DEFAULT NULL,								# 开始月社保缴纳规则（按日期 - 15号以后入职需缴社保，按工作日天数 - 上1天班缴纳社保，按自然月天数 - 超过5天需缴纳社保；从表不选则继承主表设置）
  	startRuleRemark varchar(25) DEFAULT NULL,						# 开始月社保缴纳规则备注；从表不选则继承主表设置
  	endRule tinyint(4) DEFAULT NULL,								# 结束月社保缴纳规则；从表不选则继承主表设置
  	endRuleRemark varchar(25) DEFAULT NULL,							# 结束月社保缴纳规则备注；从表不选则继承主表设置
  	makeup tinyint(4) DEFAULT NULL,									# 是否可以补缴；从表不选则继承主表设置
  	makeupMonth varchar(25) DEFAULT NULL,							# 可补缴月数（“0”不可以补缴，数字表示可补缴月数，空白表示任意；从表不选则继承主表设置）
  	makeupCrossYear tinyint(4) DEFAULT NULL,						# 能否跨年补缴；从表不选则继承主表设置
  	accuracy tinyint(4) DEFAULT NULL,								# 空为使用默认，保留小数位（取整，保留一位，保留二位；如果主表不选择，则按照从表筛选）
  	round tinyint(4) DEFAULT NULL,									# 空为使用默认，小数位保留方式（四舍五入，截取，向上进位；如果主表不选择，则按照从表筛选）
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
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_constant (
  	constantId int(11) NOT NULL AUTO_INCREMENT,						# 参数Id
  	accountId int(11) NOT NULL,										# 账户Id
  	nameZH varchar(200) NOT NULL,									# 参数中文名
  	nameEN varchar(200) NOT NULL,									# 参数英文名
  	scopeType tinyint(4) NOT NULL,									# 适用范围（1:信息，2:商务合同，3:劳动合同，4:结算，5:退工单）
  	propertyName varchar(200) NOT NULL,								# 字段名（映射动态常量）	
  	valueType tinyint(4) DEFAULT NULL,								# 内容类型（使用自定义字段中的值类型）
  	characterType tinyint(4) NOT NULL,								# 参数性质（系统常量，账户常量，动态变量）
  	content varchar(1000) DEFAULT NULL, 							# 参数内容（“CONT”开头则需要转移，例如：“CONT:now”, “CONT:nowdate”, “CONT:nowtime”等）
  	lengthType tinyint(4) NOT NULL,									# 控件大小（较小，中等，较大）
  	description varchar(1000) DEFAULT NULL, 
  	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
  	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
  	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (constantId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_income_tax_base (
  	baseId int(11) NOT NULL AUTO_INCREMENT,							# 个税起征ID
  	nameZH varchar(200) DEFAULT NULL,								# 名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 名称（英文）
  	startDate datetime DEFAULT NULL,								# 生效日期
  	endDate datetime DEFAULT NULL,									# 失效日期
  	base double DEFAULT NULL,										# 个税起征点
  	baseForeigner double DEFAULT NULL,								# 个税起征点（外籍）
  	isDefault tinyint(4) DEFAULT NULL,								# 是否默认
  	accuracy tinyint(4) DEFAULT NULL,								# 空为使用默认，保留小数位（取整，保留一位，保留二位）
  	round tinyint(4) DEFAULT NULL,									# 空为使用默认，小数位保留方式（四舍五入，截取，向上进位）
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
  	PRIMARY KEY (baseId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_income_tax_range_header (
  	headerId int(11) NOT NULL AUTO_INCREMENT,						# 税率主表ID
  	nameZH varchar(200) DEFAULT NULL,								# 名称（中文）
  	nameEN varchar(200) DEFAULT NULL,								# 名称（英文）
  	startDate datetime DEFAULT NULL,								# 生效日期
  	endDate datetime DEFAULT NULL,									# 失效日期
  	isDefault tinyint(4) DEFAULT NULL,								# 是否默认
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
  	PRIMARY KEY (headerId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_sys_income_tax_range_detail (
  	detailId int(11) NOT NULL AUTO_INCREMENT,						# 税率从表ID
  	headerId int(11) NOT NULL,										# 税率主表ID
  	rangeFrom double DEFAULT NULL,									# 收入基数（开始）
  	rangeTo double DEFAULT NULL,									# 收入基数（结束）
  	percentage double DEFAULT NULL,									# 税率
  	deduct double DEFAULT NULL,										# 扣除金额
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
  	PRIMARY KEY (detailId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
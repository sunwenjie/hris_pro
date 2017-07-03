#########add by iori @6.19 16:03
CREATE TABLE hro_performance(
	performanceId int(11) NOT NULL AUTO_INCREMENT,			#����
	accountId int(11) NOT NULL,
	corpId int(11) NOT NULL,
	employeeId int(11) NOT NULL,
	yearly int(11) NOT NULL,								#��Ч���
	fullName varchar(50) DEFAULT NULL,
	shortName varchar(50) DEFAULT NULL,
	chineseName varchar(50) DEFAULT NULL,
	employmentEntityEN varchar(200) DEFAULT NULL,			#��������(Ӣ��)
	employmentEntityZH varchar(200) DEFAULT NULL,			#��������(����)
	companyInitial varchar(200) DEFAULT NULL,				#��˾����
	buFunctionEN varchar(200) DEFAULT NULL,					#�ϼ�����(Ӣ��)
	buFunctionZH varchar(200) DEFAULT NULL,
	departmentEN varchar(200) DEFAULT NULL,					#����
	departmentZH varchar(200) DEFAULT NULL,
	costCenter varchar(200) DEFAULT NULL,
	functionCode varchar(200) DEFAULT NULL,					#ҵ������
	location varchar(200) DEFAULT NULL,
	jobRole varchar(200) DEFAULT NULL,
	positionEN varchar(200) DEFAULT NULL,					#ְλ
	positionZH varchar(200) DEFAULT NULL,
	jobGrade varchar(50) DEFAULT NULL,
	internalTitle varchar(200) DEFAULT NULL,				#�ڲ�ְ��
	lineBizManager varchar(200) DEFAULT NULL,				#ҵ��ֱ�߻㱨��
	lineHRManager varchar(200) DEFAULT NULL,				#HRֱ�߻㱨��
	seniorityDate datetime DEFAULT NULL,					#�״ι�������
	employmentDate datetime DEFAULT NULL,					#��˾����
	shareOptions varchar(200) DEFAULT NULL,
	lastYearPerformanceRating varchar(50) DEFAULT NULL,		#���꼨Ч�ȼ�
	lastYearPerformancePromotion varchar(50) DEFAULT NULL,	#�������
	midYearPromotion varchar(50) DEFAULT NULL,				#�����н���
	midYearSalaryIncrease varchar(50) DEFAULT NULL,			#������н������
	currencyCode varchar(50) DEFAULT NULL,					#����
	baseSalaryLocal double DEFAULT NULL,					#�»�������(����)
	baseSalaryUSD	double DEFAULT NULL,					#�»�������(��Ԫ)
	annualBaseSalaryLocal double DEFAULT NULL,				#��н(����)
	annualBaseSalaryUSD double DEFAULT NULL,
	housingAllowanceLocal double DEFAULT NULL,				#��ס������
	childrenEduAllowanceLocal double DEFAULT NULL,			#���ͯ��������
	guaranteedCashLocal double DEFAULT NULL,				#�걣�շ�
	guaranteedCashUSD double DEFAULT NULL,					#�걣�շ�(��Ԫ)
	monthlyTarget double DEFAULT NULL,						#�½���
	quarterlyTarget double DEFAULT NULL,					#���Ƚ���	
	gpTarget double DEFAULT NULL,							
	targetValueLocal double DEFAULT NULL,					#������ֵ
	targetValueUSD double DEFAULT NULL,						
	ttcLocal double DEFAULT NULL,	
	ttcUSD double DEFAULT NULL,
	yearPerformanceRating varchar(50) DEFAULT NULL,			#�꼨Ч����
	yearPerformancePromotion varchar(50) DEFAULT NULL,		#�꼨Ч����
	recommendTTCIncrease varchar(50) DEFAULT NULL,			#�Ƽ�TTC����
	ttcIncrease varchar(50) DEFAULT NULL,					#TTC����
	newTTCLocal double DEFAULT NULL,
	newTTCUSD double DEFAULT NULL,
	newBaseSalaryLocal double DEFAULT NULL,					#����н
	newBaseSalaryUSD double DEFAULT NULL,	
	newAnnualSalaryLocal double DEFAULT NULL,				#����н
	newAnnualSalaryUSD double DEFAULT NULL,	
	newAnnualHousingAllowance double DEFAULT NULL,
	newAnnualChildrenEduAllowance double DEFAULT NULL,	
	newAnnualGuaranteedAllowanceLocal double DEFAULT NULL,
	newAnnualGuatanteedAllowanceUSD double DEFAULT NULL,
	newMonthlyTarget double DEFAULT NULL,
	newQuarterlyTarget double DEFAULT NULL,	
	newGPTarget double DEFAULT NULL,	
	newTargetValueLocal double DEFAULT NULL,
	newTargetValueUSD double DEFAULT NULL,
	newJobGrade varchar(50) DEFAULT NULL,
	newInternalTitle varchar(200) DEFAULT NULL,
	newPositionEN varchar(200) DEFAULT NULL,
	newPositionZH varchar(200) DEFAULT NULL,
	newShareOptions varchar(200) DEFAULT NULL,
	targetBonus varchar(200) DEFAULT NULL,							#�ֺ�
	proposedBonus varchar(200) DEFAULT NULL,							#Ԥ���ֺ�
	proposedPayoutLocal varchar(200) DEFAULT NULL,					#Ԥ��֧��
	proposedPayoutUSD varchar(200) DEFAULT NULL,										
	description varchar(1000) DEFAULT NULL ,
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(5000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	remark3 varchar(1000) DEFAULT NULL ,
	remark4 varchar(1000) DEFAULT NULL ,
	remark5 varchar(1000) DEFAULT NULL ,
	createBy varchar(25) DEFAULT NULL ,
	createDate datetime NULL DEFAULT NULL ,
	modifyBy varchar(25) DEFAULT NULL ,
	modifyDate datetime NULL DEFAULT NULL ,
	PRIMARY KEY (performanceId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

------------------------ 2015��06��25��18:15:42
UPDATE hro_sys_module set defaultAction = 'performanceAction.do?proc=list_object' , listAction = 'performanceAction.do?proc=list_object' WHERE moduleid = 1151;

CREATE TABLE hro_mgt_exchange_rate (
	exchangeRateId int(11) NOT NULL AUTO_INCREMENT,					# 
	accountId int(11) NOT NULL,										#	
	corpId int(11) NOT NULL,										# �ͻ�ID
	currencyNameZH varchar(100) NOT NULL,							#
	currencyNameEN varchar(100) DEFAULT NULL,						# ����
	currencyCode varchar(10) NOT NULL,								# ���Ҵ���
	fromUSD double NOT NULL,										# ��Ԫ
	toLocal double NOT NULL,										# ����
	deleted tinyint(4) DEFAULT NULL,
  	status tinyint(4) DEFAULT NULL,
	remark1 varchar(1000) DEFAULT NULL,
  	remark2 varchar(1000) DEFAULT NULL,
	createBy varchar(25) DEFAULT NULL,
  	createDate datetime DEFAULT NULL,
  	modifyBy varchar(25) DEFAULT NULL,
  	modifyDate datetime DEFAULT NULL,
  	PRIMARY KEY (exchangeRateId)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

INSERT INTO `hro_mgt_exchange_rate` (`exchangeRateId`, `accountId`, `corpId`, `currencyNameZH`, `currencyNameEN`, `currencyCode`, `fromUSD`, `toLocal`, `status`, `deleted`, `remark1`, `remark2`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(1, 100017, 300115, '�����', 'RMB', 'RMB', 1.0000, 6.0000, 1, 1, NULL, NULL, '1', NULL, '1', NULL),
	(2, 100017, 300115, '�۱�', 'HKD', 'HKD', 1.0000, 7.7600, 1, 1, NULL, NULL, '1', NULL, '1', NULL),
	(3, 100017, 300115, '�¼���Ԫ', 'SGD', 'SGD', 1.0000, 1.2300, 1, 1, NULL, NULL, '1', NULL, '1', NULL),
	(4, 100017, 300115, '��̨��', 'NTD', 'NTD', 1.0000, 30.0000, 1, 1, NULL, NULL, '1', NULL, '1', NULL);


INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(796, 'menu_finance_ExchangeRate', 1, '����', 'Exchange Rate', '����', 'Exchange Rate', 0, 'N/A', 15, 'HRO_MGT_EXCHANGE_RATE', NULL, 'exchangeRateAction.do?proc=list_object', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 790, 'menu_finance_Modules', 'menu_finance_Configuration', 'menu_finance_ExchangeRate', 796, '{1:2:3:4:5}', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2013-11-27 11:19:48', '1', '2013-11-27 11:19:48');

# ���ɰ�ť

INSERT INTO `hro_sys_right` (`rightId`, `rightType`, `nameZH`, `nameEN`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(54, 1, '����', 'Generate', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2015-03-05 17:17:23', '1', '2015-03-05 17:17:23');

	# 2015��06��30��18:15:40
	INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(13370, 1, NULL, 71, 'employeeShortName', 'Ա����������ƣ�', 'Ա����������ƣ�', 'Short Name', 0, 2, 2, 2, 2, 2, 100, 1, 2, 0, NULL, '', '', '0', NULL, '0_0', '0_0', 1, 2, '', '', 0, '', '', '', 2, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2015-06-30 17:56:12', '1', '2015-06-30 17:56:12');





CREATE TABLE `hro_sys_log` (
  `id` BIGINT(22) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` TINYINT(4) NOT NULL COMMENT '1,add;2,update;3,delete;4,submit',
  `module` VARCHAR(90) NULL,
  `content` TEXT NULL,
  `ip`VARCHAR(45) NULL,
  `operateTime` DATETIME NULL,
  `operateBy` VARCHAR(45) NULL,
  `pKey` VARCHAR(45) NULL,
  `remark` VARCHAR(2000) NULL,
  PRIMARY KEY (`id`))ENGINE=InnoDB DEFAULT CHARSET=gbk;

# 2015��07��09��11:01:15
ALTER TABLE `hro_biz_employee_positionchange`
ADD COLUMN `remark1`  varchar(1000) NULL DEFAULT NULL AFTER `description`,
ADD COLUMN `remark2`  varchar(1000) NULL DEFAULT NULL AFTER `remark1`,
ADD COLUMN `remark3`  varchar(1000) NULL DEFAULT NULL AFTER `remark2`,
ADD COLUMN `remark4`  varchar(1000) NULL DEFAULT NULL AFTER `remark3`,
ADD COLUMN `remark5`  varchar(1000) NULL DEFAULT NULL AFTER `remark4`;

ALTER TABLE `hro_biz_employee_salary_adjustment`
ADD COLUMN `remark1`  varchar(1000) NULL DEFAULT NULL AFTER `description`,
ADD COLUMN `remark2`  varchar(1000) NULL DEFAULT NULL AFTER `remark1`,
ADD COLUMN `remark3`  varchar(1000) NULL DEFAULT NULL AFTER `remark2`,
ADD COLUMN `remark4`  varchar(1000) NULL DEFAULT NULL AFTER `remark3`,
ADD COLUMN `remark5`  varchar(1000) NULL DEFAULT NULL AFTER `remark4`;

## add by iori @ 7-10
ALTER TABLE hro_biz_employee_salary_adjustment  MODIFY COLUMN oldBase Double DEFAULT NULL;
ALTER TABLE hro_biz_employee_salary_adjustment  MODIFY COLUMN newBase Double DEFAULT NULL;



###################################executed##########################################




DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `getCharIndex`(codeChar char) RETURNS tinyint(4)
BEGIN
DECLARE temp_index TINYINT(4);

	IF ASCII(codeChar) = ASCII('A') THEN
		SET temp_index = 1;
	ELSEIF ASCII(codeChar) = ASCII('B') THEN
		SET temp_index = 2;
	ELSEIF ASCII(codeChar) = ASCII('C') THEN
		SET temp_index = 3;
	ELSEIF ASCII(codeChar) = ASCII('D') THEN
		SET temp_index = 4;
	ELSEIF ASCII(codeChar) = ASCII('E') THEN
		SET temp_index = 5;
	ELSEIF ASCII(codeChar) = ASCII('F') THEN
		SET temp_index = 6;
	ELSEIF ASCII(codeChar) = ASCII('G') THEN
		SET temp_index = 7;
	ELSEIF ASCII(codeChar) = ASCII('H') THEN
		SET temp_index = 8;
	ELSEIF ASCII(codeChar) = ASCII('I') THEN
		SET temp_index = 9;
	ELSEIF ASCII(codeChar) = ASCII('J') THEN
		SET temp_index = 10;
	ELSEIF ASCII(codeChar) = ASCII('K') THEN
		SET temp_index = 11;
	ELSEIF ASCII(codeChar) = ASCII('L') THEN
		SET temp_index = 12;
	ELSEIF ASCII(codeChar) = ASCII('M') THEN
		SET temp_index = 1;
	ELSEIF ASCII(codeChar) = ASCII('N') THEN
		SET temp_index = 2;
	ELSEIF ASCII(codeChar) = ASCII('O') THEN
		SET temp_index = 3;
	ELSEIF ASCII(codeChar) = ASCII('P') THEN
		SET temp_index = 4;
	ELSEIF ASCII(codeChar) = ASCII('Q') THEN
		SET temp_index = 5;
	ELSEIF ASCII(codeChar) = ASCII('R') THEN
		SET temp_index = 6;
	ELSEIF ASCII(codeChar) = ASCII('S') THEN
		SET temp_index = 7;
	ELSEIF ASCII(codeChar) = ASCII('T') THEN
		SET temp_index = 8;
	ELSEIF ASCII(codeChar) = ASCII('U') THEN
		SET temp_index = 9;
	ELSEIF ASCII(codeChar) = ASCII('V') THEN
		SET temp_index = 10;
	ELSEIF ASCII(codeChar) = ASCII('W') THEN
		SET temp_index = 11;
	ELSEIF ASCII(codeChar) = ASCII('X') THEN
		SET temp_index = 12;
	ELSEIF ASCII(codeChar) = ASCII('Y') THEN
		SET temp_index = 1;
	ELSEIF ASCII(codeChar) = ASCII('Z') THEN
		SET temp_index = 2;
	ELSEIF ASCII(codeChar) = ASCII('a') THEN
		SET temp_index = 3;
	ELSEIF ASCII(codeChar) = ASCII('b') THEN
		SET temp_index = 4;
	ELSEIF ASCII(codeChar) = ASCII('c') THEN
		SET temp_index = 5;
	ELSEIF ASCII(codeChar) = ASCII('d') THEN
		SET temp_index = 6;
	ELSEIF ASCII(codeChar) = ASCII('e') THEN
		SET temp_index = 7;
	ELSEIF ASCII(codeChar) = ASCII('f') THEN
		SET temp_index = 8;
	ELSEIF ASCII(codeChar) = ASCII('g') THEN
		SET temp_index = 9;
	ELSEIF ASCII(codeChar) = ASCII('h') THEN
		SET temp_index = 10;
	ELSEIF ASCII(codeChar) = ASCII('i') THEN
		SET temp_index = 11;
	ELSEIF ASCII(codeChar) = ASCII('j') THEN
		SET temp_index = 12;
	ELSEIF ASCII(codeChar) = ASCII('k') THEN
		SET temp_index = 1;
	ELSEIF ASCII(codeChar) = ASCII('l') THEN
		SET temp_index = 2;
	ELSEIF ASCII(codeChar) = ASCII('m') THEN
		SET temp_index = 3;
	ELSEIF ASCII(codeChar) = ASCII('n') THEN
		SET temp_index = 4;
	ELSEIF ASCII(codeChar) = ASCII('o') THEN
		SET temp_index = 5;
	ELSEIF ASCII(codeChar) = ASCII('p') THEN
		SET temp_index = 6;
	ELSEIF ASCII(codeChar) = ASCII('q') THEN
		SET temp_index = 7;
	ELSEIF ASCII(codeChar) = ASCII('r') THEN
		SET temp_index = 8;
	ELSEIF ASCII(codeChar) = ASCII('s') THEN
		SET temp_index = 9;
	ELSEIF ASCII(codeChar) = ASCII('t') THEN
		SET temp_index = 10;
	ELSEIF ASCII(codeChar) = ASCII('u') THEN
		SET temp_index = 11;
	ELSEIF ASCII(codeChar) = ASCII('v') THEN
		SET temp_index = 12;
	ELSEIF ASCII(codeChar) = ASCII('w') THEN
		SET temp_index = 1;
	ELSEIF ASCII(codeChar) = ASCII('x') THEN
		SET temp_index = 2;
	ELSEIF ASCII(codeChar) = ASCII('y') THEN
		SET temp_index = 3;
	ELSEIF ASCII(codeChar) = ASCII('z') THEN
		SET temp_index = 4;
	ELSEIF ASCII(codeChar) = ASCII('0') THEN
		SET temp_index = 5;
	ELSEIF ASCII(codeChar) = ASCII('1') THEN
		SET temp_index = 6;
	ELSEIF ASCII(codeChar) = ASCII('2') THEN
		SET temp_index = 7;
	ELSEIF ASCII(codeChar) = ASCII('3') THEN
		SET temp_index = 8;
	ELSEIF ASCII(codeChar) = ASCII('4') THEN
		SET temp_index = 9;
	ELSEIF ASCII(codeChar) = ASCII('5') THEN
		SET temp_index = 10;
	ELSEIF ASCII(codeChar) = ASCII('6') THEN
		SET temp_index = 11;
	ELSEIF ASCII(codeChar) = ASCII('7') THEN
		SET temp_index = 12;
	ELSEIF ASCII(codeChar) = ASCII('8') THEN
		SET temp_index = 1;
	ELSEIF ASCII(codeChar) = ASCII('9') THEN
		SET temp_index = 2;
	ELSE
		SET temp_index = 0;
	END IF;

	RETURN temp_index;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` FUNCTION `getIncrement`(publicCode		varchar(100),   
	privateCode		varchar(12)) RETURNS double
BEGIN
	DECLARE temp_increment VARCHAR(12);

	SET temp_increment = '';
		
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 1, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 2, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 3, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 4, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 5, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 6, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 7, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 8, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 9, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 10, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 11, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 12, 1)), 1)) INTO temp_increment;

	RETURN temp_increment;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `getPublicCode`(employeeId varchar(11)) RETURNS bigint(20)
BEGIN
    DECLARE temp_employeeId varchar(11);
    DECLARE temp_value bigint(20);
    
    SET temp_employeeId = '';
    SET temp_value = 0;
    
	SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 9, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 8, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 7, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 6, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 5, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 4, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 3, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 2, 1) ) INTO temp_employeeId;
    SELECT CONCAT(temp_employeeId,SUBSTRING(employeeId, 1, 1) ) INTO temp_employeeId;
    
    SELECT CONVERT(temp_employeeId,SIGNED)*CONVERT(temp_employeeId,SIGNED) INTO temp_value;
	IF temp_value<100000000000 
    THEN set temp_value=temp_value+100000000000;
    END IF;

RETURN temp_value;
END$$
DELIMITER ;
	
	
# 2015��07��21��15:47:52
ALTER TABLE `hro_sys_log`
ADD COLUMN `changeType`  varchar(5) NULL DEFAULT NULL AFTER `remark`,
ADD COLUMN `employeeNameZH`  varchar(100) NULL DEFAULT NULL AFTER `changeType`,
ADD COLUMN `employeeNameEN`  varchar(100) NULL DEFAULT NULL AFTER `employeeNameZH`;
	
## 7.23
INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(133, 'menu_security_Log', 1, 'ϵͳ��־', 'System Log', 'ϵͳ��־', 'System Log', 0, 'menu.system.log', 2, 'HRO_SYS_LOG', NULL, 'logAction.do?proc=list_object', '', '', '', '', '', '', '', 100, 'menu_security_Modules', 'menu_security_Log', '', 133, '{2:5}', '', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2013-06-20 19:31:26', '1', '2015-07-20 14:35:24');
# 2015��07��21��15:47:52
ALTER TABLE `hro_sys_log`
ADD COLUMN `employeeId` varchar(11) NULL DEFAULT NULL AFTER `remark`;
## 2015��07��24��19:20:22
INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(1171, 'menu_biz_Employee_Change_Report', 1, '��Ա�춯����', 'Employee Change Report', 'Ա���춯����', 'Employee Change Report', 2, 'N/A', 9, 'HRO_BIZ_EMPLOYEE_CHANGE_REPORT', NULL, 'employeeChangeReportAction.do?proc=list_object', 'employeeChangeReportAction.do?proc=list_object', NULL, NULL, NULL, NULL, NULL, NULL, 499, 'menu_employee_Modules', 'menu_employee_Reports', 'menu_biz_Employee_Change_Report', 1171, '{2:5:28}', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2013-06-25 18:24:08', '1', NULL);


INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(1170, 'menu_public_contacts', 1, 'ͨѶ¼', 'Contacts', 'ͨѶ¼', 'Contacts', 0, 'N/A', 9, 'HRO_PUBLIC_CONTACTS', NULL, 'employeeReportAction.do?proc=getContacts', 'employeeReportAction.do?proc=getContacts', '', '', '', '', '', '', 450, 'menu_employee_Modules', 'menu_public_contacts', '', 486, '{2}', '', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2013-06-25 18:24:08', '1', '2013-06-25 18:24:08');


## 2015��07��27��13:41:09
INSERT INTO `hro_sys_workflow` (`workflowModuleId`, `systemId`, `moduleId`, `scopeType`, `nameZH`, `nameEN`, `rightIds`, `includeViewObjJsp`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(64, 1, 1010, 2, 'Ա��н�귽����������', 'Employee Salary solution approval process', '{6}', 'employeeContractSalaryAction.do?proc=list_object_workflow_ajax', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2015-07-27 10:41:33', '1', '2015-07-27 10:47:18');
INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(7171, 1, NULL, 71, 'remark3', '�춯ԭ��', '�춯ԭ��', 'Movement Category', 9, 2, 2, 2, 1, 1, 100, 2, 2, 1, '169', '', '', '12', NULL, '0_0', '0_0', 1, 2, '', '', 0, '', '', '', 2, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2015-07-27 17:44:38', '1', '2015-07-27 17:44:38');
INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(6969, 1, NULL, 69, 'remark3', '�춯ԭ��', '�춯ԭ��', 'Movement Category', 11, 2, 2, 1, 1, 1, 100, 2, 2, 1, '169', '', '', '12', NULL, '0_0', '0_0', 1, 2, '', '', 0, '', '', '', 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2015-07-27 17:41:10', '1', '2015-07-27 17:41:10');
ALTER TABLE `hro_sys_log`
ADD COLUMN `changeReason` VARCHAR(5) NULL DEFAULT NULL AFTER `changeType`;


#
# н�귽���ӹ����� 2015-07-28 11:04:39
#
INSERT INTO `hro_sys_constant` (`constantId`, `accountId`, `nameZH`, `nameEN`, `scopeType`, `propertyName`, `valueType`, `characterType`, `content`, `lengthType`, `description`, `deleted`, `status`, `remark1`, `remark2`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(721, 1, '[н�귽��]н�ʿ�Ŀ�������ģ�', '[Salary Solution] Salary Name (Chinese)', 1, 'itemNameZH', 2, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-07-28 10:56:30', '1', '2015-07-28 10:56:30'),
	(722, 1, '[н�귽��]н�ʿ�Ŀ����Ӣ�ģ�', '[Salary Solution] Salary Name (English)', 1, 'itemNameEN', 2, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-07-28 10:57:17', '1', '2015-07-28 10:57:17'),
	(723, 1, '[н�귽��]Ա�����������ģ�', '[Salary Solution] Chinese Name', 1, 'employeeNameZH', 2, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-07-28 10:58:46', '1', '2015-07-28 10:59:02'),
	(724, 1, '[н�귽��]Ա��������Ӣ�ģ�', '[Salary Solution] Full Name', 1, 'employeeNameEN', 2, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-07-28 10:59:55', '1', '2015-07-28 10:59:55'),
	(725, 1, '[н�귽��]���', '[Salary Solution] Money', 1, 'base', 1, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-07-28 11:03:38', '1', '2015-07-28 11:03:38');

	
######################################################################
##############################executed################################	


alter table hro_biz_employee_positionchange add column oldBranchNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldBranchNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldPositionNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldPositionNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column newBranchNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column newBranchNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column newPositionNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column newPositionNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldParentBranchId INT(11) default null;
alter table hro_biz_employee_positionchange add column oldParentBranchNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldParentBranchNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldParentPositionId INT(11) default null;
alter table hro_biz_employee_positionchange add column oldParentPositionNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldParentPositionNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldPositionGradeId INT(11) default null;
alter table hro_biz_employee_positionchange add column oldPositionGradeNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldPositionGradeNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldParentPositionOwnersZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column oldParentPositionOwnersEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column newParentBranchId INT(11) default null;
alter table hro_biz_employee_positionchange add column newParentBranchNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column newParentBranchNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column newParentPositionId INT(11) default null;
alter table hro_biz_employee_positionchange add column newParentPositionNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column newParentPositionNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column newPositionGradeId INT(11) default null;
alter table hro_biz_employee_positionchange add column newPositionGradeNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column newPositionGradeNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column newParentPositionOwnersZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column newParentPositionOwnersEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column employeeNo varchar(50) default null;
alter table hro_biz_employee_positionchange add column employeeNameZH varchar(100) default null;
alter table hro_biz_employee_positionchange add column employeeNameEN varchar(100) default null;
alter table hro_biz_employee_positionchange add column employeeCertificateNumber varchar(100) default null;


###########
update hro_biz_employee_positionchange ec left join hro_biz_employee e on ec.employeeId=e.employeeId set ec.`employeeCertificateNumber`=e.`certificateNumber`,
ec.employeeNameZH = e.nameZH,
ec.employeeNameEN = e.nameEN,
ec.employeeNo = e.employeeNO;

update hro_biz_employee_positionchange ec left join hro_sec_position p on p.positionId = ec.`oldPositionId` set 
ec.`oldPositionNameZH` = p.`titleZH`,
ec.`oldPositionNameEN` = p.`titleEN`;

update hro_biz_employee_positionchange ec left join hro_sec_position p on p.positionId = ec.`oldPositionId` set 
ec.`oldParentPositionId` = p.`parentPositionId`,
ec.`oldPositionGradeId` = p.positionGradeId;


update hro_biz_employee_positionchange ec left join hro_sec_position p on p.positionId = ec.`oldParentPositionId` set 
ec.`oldParentPositionNameZH` = p.`titleZH`,
ec.`oldParentPositionNameEN` = p.`titleEN`;

update hro_biz_employee_positionchange ec left join hro_sec_branch b on b.branchId = ec.`oldBranchId` set
ec.`oldBranchNameZH` = b.nameZH,
ec.`oldBranchNameEN` = b.nameEN,
ec.`oldParentBranchId` = b.`parentBranchId`;

update hro_biz_employee_positionchange ec left join hro_sec_branch b on b.branchId = ec.oldParentBranchId set
ec.`oldParentBranchNameZH` = b.nameZH,
ec.`oldParentBranchNameEN` = b.nameEN;

update hro_biz_employee_positionchange ec left join hro_sec_position_grade g on g.positiongradeid = ec.`oldPositionGradeId` set 
ec.`oldPositionGradeNameZH` = g.gradeNameZH,
ec.`oldPositionGradeNameEN` = g.gradeNameEN;
#############################

update hro_biz_employee_positionchange ec left join hro_sec_position p on p.positionId = ec.newPositionId set 
ec.`newPositionNameZH` = p.`titleZH`,
ec.`newPositionNameEN` = p.`titleEN`;

update hro_biz_employee_positionchange ec left join hro_sec_position p on p.positionId = ec.newPositionId set 
ec.`newParentPositionId` = p.`parentPositionId`,
ec.`newPositionGradeId` = p.positionGradeId;


update hro_biz_employee_positionchange ec left join hro_sec_position p on p.positionId = ec.newParentPositionId set 
ec.`newParentPositionNameZH` = p.`titleZH`,
ec.`newParentPositionNameEN` = p.`titleEN`;

update hro_biz_employee_positionchange ec left join hro_sec_branch b on b.branchId = ec.`newBranchId` set
ec.`newBranchNameZH` = b.nameZH,
ec.`newBranchNameEN` = b.nameEN,
ec.`newParentBranchId` = b.`parentBranchId`;

update hro_biz_employee_positionchange ec left join hro_sec_branch b on b.branchId = ec.oldParentBranchId set
ec.`newParentBranchNameZH` = b.nameZH,
ec.`newParentBranchNameEN` = b.nameEN;

update hro_biz_employee_positionchange ec left join hro_sec_position_grade g on g.positiongradeid = ec.`oldPositionGradeId` set 
ec.`newPositionGradeNameZH` = g.gradeNameZH,
ec.`newPositionGradeNameEN` = g.gradeNameEN;

###########
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Doris Zhou' , oldParentPositionOwnersEN = 'Doris Zhou'  ,newParentPositionOwnersZH='Maggie Dai' , newParentPositionOwnersEN='Maggie Dai' where positionchangeid=519;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Darren Lewis Jacobs' , oldParentPositionOwnersEN = 'Darren Lewis Jacobs'  ,newParentPositionOwnersZH='Darren Lewis Jacobs' , newParentPositionOwnersEN='Darren Lewis Jacobs' where positionchangeid=518;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Chit Mei' , oldParentPositionOwnersEN = 'Ho Chit Mei'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=517;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tse Mei Ha' , oldParentPositionOwnersEN = 'Tse Mei Ha'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=516;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tse Mei Ha' , oldParentPositionOwnersEN = 'Tse Mei Ha'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=515;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ku Huichen' , oldParentPositionOwnersEN = 'Ku Huichen'  ,newParentPositionOwnersZH='Ku Huichen' , newParentPositionOwnersEN='Ku Huichen' where positionchangeid=514;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=513;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Murphy Cara Eileen' , oldParentPositionOwnersEN = 'Murphy Cara Eileen'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=512;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='Leung Kelly' , newParentPositionOwnersEN='Leung Kelly' where positionchangeid=511;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tan Jun Chee' , oldParentPositionOwnersEN = 'Tan Jun Chee'  ,newParentPositionOwnersZH='Tan Jun Chee' , newParentPositionOwnersEN='Tan Jun Chee' where positionchangeid=510;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Marrla Yu' , oldParentPositionOwnersEN = 'Marrla Yu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=509;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tan Jun Chee' , oldParentPositionOwnersEN = 'Tan Jun Chee'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=508;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Liz Lu' , oldParentPositionOwnersEN = 'Liz Lu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=507;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Marrla Yu' , oldParentPositionOwnersEN = 'Marrla Yu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=506;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=505;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Teoh Tham Kim' , oldParentPositionOwnersEN = 'Teoh Tham Kim'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=504;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=503;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='Leung Kelly' , newParentPositionOwnersEN='Leung Kelly' where positionchangeid=502;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='Leung Kelly' , newParentPositionOwnersEN='Leung Kelly' where positionchangeid=501;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Hsieh Wing Hong Sammy' , oldParentPositionOwnersEN = 'Hsieh Wing Hong Sammy'  ,newParentPositionOwnersZH='Hsieh Wing Hong Sammy' , newParentPositionOwnersEN='Hsieh Wing Hong Sammy' where positionchangeid=500;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Marrla Yu' , oldParentPositionOwnersEN = 'Marrla Yu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=499;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=498;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='Yim Tsz Ling' , newParentPositionOwnersEN='Yim Tsz Ling' where positionchangeid=497;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Larine Wang' , oldParentPositionOwnersEN = 'Larine Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=496;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=495;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=494;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=493;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='Kevin Hou' , newParentPositionOwnersEN='Kevin Hou' where positionchangeid=492;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Celia Tong' , oldParentPositionOwnersEN = 'Celia Tong'  ,newParentPositionOwnersZH='Leo Lu' , newParentPositionOwnersEN='Leo Lu' where positionchangeid=491;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Terry Li' , oldParentPositionOwnersEN = 'Terry Li'  ,newParentPositionOwnersZH='James Zhu' , newParentPositionOwnersEN='James Zhu' where positionchangeid=490;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Terry Li' , oldParentPositionOwnersEN = 'Terry Li'  ,newParentPositionOwnersZH='James Zhu' , newParentPositionOwnersEN='James Zhu' where positionchangeid=489;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Lydia Li' , newParentPositionOwnersEN='Lydia Li' where positionchangeid=488;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Chit Mei' , oldParentPositionOwnersEN = 'Ho Chit Mei'  ,newParentPositionOwnersZH='Ho Chit Mei' , newParentPositionOwnersEN='Ho Chit Mei' where positionchangeid=486;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Forest Wang' , oldParentPositionOwnersEN = 'Forest Wang'  ,newParentPositionOwnersZH='Forest Wang' , newParentPositionOwnersEN='Forest Wang' where positionchangeid=485;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Steven Yang' , oldParentPositionOwnersEN = 'Steven Yang'  ,newParentPositionOwnersZH='Steven Yang' , newParentPositionOwnersEN='Steven Yang' where positionchangeid=484;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Mai Jing Shi ' , oldParentPositionOwnersEN = 'Mai Jing Shi '  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=483;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Elena Liu' , oldParentPositionOwnersEN = 'Elena Liu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=482;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Celia Tong' , oldParentPositionOwnersEN = 'Celia Tong'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=481;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ganna Gan' , oldParentPositionOwnersEN = 'Ganna Gan'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=480;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Larine Wang' , oldParentPositionOwnersEN = 'Larine Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=479;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wong Pi Yan' , oldParentPositionOwnersEN = 'Wong Pi Yan'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=478;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=477;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=476;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chen Hong ' , oldParentPositionOwnersEN = 'Chen Hong '  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=475;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=474;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=473;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Lydia Li' , newParentPositionOwnersEN='Lydia Li' where positionchangeid=471;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Lydia Li' , newParentPositionOwnersEN='Lydia Li' where positionchangeid=470;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wong Pi Yan' , oldParentPositionOwnersEN = 'Wong Pi Yan'  ,newParentPositionOwnersZH='Hanson Liu' , newParentPositionOwnersEN='Hanson Liu' where positionchangeid=468;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wong Pi Yan' , oldParentPositionOwnersEN = 'Wong Pi Yan'  ,newParentPositionOwnersZH='Hanson Liu' , newParentPositionOwnersEN='Hanson Liu' where positionchangeid=467;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wong Pi Yan' , oldParentPositionOwnersEN = 'Wong Pi Yan'  ,newParentPositionOwnersZH='Hanson Liu' , newParentPositionOwnersEN='Hanson Liu' where positionchangeid=466;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Fok Kam Fai' , oldParentPositionOwnersEN = 'Fok Kam Fai'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=464;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=463;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=462;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Xin Xing' , oldParentPositionOwnersEN = 'Xin Xing'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=461;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=460;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=459;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ko Chih Ting' , oldParentPositionOwnersEN = 'Ko Chih Ting'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=458;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan King Ho' , oldParentPositionOwnersEN = 'Chan King Ho'  ,newParentPositionOwnersZH='Chan King Ho' , newParentPositionOwnersEN='Chan King Ho' where positionchangeid=457;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=456;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Celia Tong' , oldParentPositionOwnersEN = 'Celia Tong'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=455;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Chun Hing' , oldParentPositionOwnersEN = 'Chan Chun Hing'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=454;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=453;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kai Li' , oldParentPositionOwnersEN = 'Kai Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=452;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=451;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=450;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Frank Cai' , oldParentPositionOwnersEN = 'Frank Cai'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=449;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Danielle Ren' , oldParentPositionOwnersEN = 'Danielle Ren'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=448;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=447;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=446;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=445;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=444;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=443;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Forest Wang' , oldParentPositionOwnersEN = 'Forest Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=442;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Feeling Yang' , oldParentPositionOwnersEN = 'Feeling Yang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=441;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Forest Wang' , oldParentPositionOwnersEN = 'Forest Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=440;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Marrla Yu' , oldParentPositionOwnersEN = 'Marrla Yu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=439;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lo Wai Kwok' , oldParentPositionOwnersEN = 'Lo Wai Kwok'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=437;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=436;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Choi Chi Kin' , oldParentPositionOwnersEN = 'Choi Chi Kin'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=435;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Elena Liu' , oldParentPositionOwnersEN = 'Elena Liu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=434;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Elena Liu' , oldParentPositionOwnersEN = 'Elena Liu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=433;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Gordon An' , oldParentPositionOwnersEN = 'Gordon An'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=432;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Steven Yang' , oldParentPositionOwnersEN = 'Steven Yang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=431;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=430;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=429;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Larine Wang' , oldParentPositionOwnersEN = 'Larine Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=428;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Leo Lu' , oldParentPositionOwnersEN = 'Leo Lu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=427;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Fok Kam Fai' , oldParentPositionOwnersEN = 'Fok Kam Fai'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=426;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Fok Kam Fai' , oldParentPositionOwnersEN = 'Fok Kam Fai'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=425;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=424;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=423;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=422;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Doris Zhou' , oldParentPositionOwnersEN = 'Doris Zhou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=421;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tan Jun Chee' , oldParentPositionOwnersEN = 'Tan Jun Chee'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=420;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=419;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tan Jun Chee' , oldParentPositionOwnersEN = 'Tan Jun Chee'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=418;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=417;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tan Jun Chee' , oldParentPositionOwnersEN = 'Tan Jun Chee'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=416;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=415;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Forest Wang' , oldParentPositionOwnersEN = 'Forest Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=414;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Aaron Yu' , oldParentPositionOwnersEN = 'Aaron Yu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=413;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Elena Liu' , oldParentPositionOwnersEN = 'Elena Liu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=412;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=411;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ray Zhou' , oldParentPositionOwnersEN = 'Ray Zhou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=410;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=409;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Larine Wang' , oldParentPositionOwnersEN = 'Larine Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=408;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=407;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Nono Kong' , oldParentPositionOwnersEN = 'Nono Kong'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=406;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan King Ho' , oldParentPositionOwnersEN = 'Chan King Ho'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=403;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Mak Kim Wai' , oldParentPositionOwnersEN = 'Mak Kim Wai'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=402;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=363;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan King Ho' , oldParentPositionOwnersEN = 'Chan King Ho'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=362;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=361;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=360;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Hsieh Wing Hong Sammy' , oldParentPositionOwnersEN = 'Hsieh Wing Hong Sammy'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=359;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=358;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Xiao Mu' , oldParentPositionOwnersEN = 'Xiao Mu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=357;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Mak Kim Wai' , oldParentPositionOwnersEN = 'Mak Kim Wai'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=356;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=355;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=354;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=353;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=352;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Li Wai Chung' , oldParentPositionOwnersEN = 'Li Wai Chung'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=351;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=350;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=349;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=348;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Hsieh Wing Hong Sammy' , oldParentPositionOwnersEN = 'Hsieh Wing Hong Sammy'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=340;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yan Lee' , oldParentPositionOwnersEN = 'Yan Lee'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=339;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=338;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='Ho Kin Pong Frankie' , newParentPositionOwnersEN='Ho Kin Pong Frankie' where positionchangeid=336;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Leo Lu' , oldParentPositionOwnersEN = 'Leo Lu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=335;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=331;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chen Hong ' , oldParentPositionOwnersEN = 'Chen Hong '  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=330;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Forest Wang' , oldParentPositionOwnersEN = 'Forest Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=329;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Hanson Liu' , oldParentPositionOwnersEN = 'Hanson Liu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=328;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Forest Wang' , oldParentPositionOwnersEN = 'Forest Wang'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=327;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=326;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lau Wan Kei Sandy ' , oldParentPositionOwnersEN = 'Lau Wan Kei Sandy '  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=323;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='Yuki Dong' , newParentPositionOwnersEN='Yuki Dong' where positionchangeid=267;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=266;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=265;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=264;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=263;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=262;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=261;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=260;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan Ming Fat' , oldParentPositionOwnersEN = 'Chan Ming Fat'  ,newParentPositionOwnersZH='Chan Ming Fat' , newParentPositionOwnersEN='Chan Ming Fat' where positionchangeid=259;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lau Wan Kei Sandy ' , oldParentPositionOwnersEN = 'Lau Wan Kei Sandy '  ,newParentPositionOwnersZH='Wong Yim Fai' , newParentPositionOwnersEN='Wong Yim Fai' where positionchangeid=258;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='Chan King Ho' , newParentPositionOwnersEN='Chan King Ho' where positionchangeid=257;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='Chan King Ho' , newParentPositionOwnersEN='Chan King Ho' where positionchangeid=256;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='Chan King Ho' , newParentPositionOwnersEN='Chan King Ho' where positionchangeid=255;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan King Ho' , oldParentPositionOwnersEN = 'Chan King Ho'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=254;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan King Ho' , oldParentPositionOwnersEN = 'Chan King Ho'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=253;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chan King Ho' , oldParentPositionOwnersEN = 'Chan King Ho'  ,newParentPositionOwnersZH='Ho Kin Pong Frankie' , newParentPositionOwnersEN='Ho Kin Pong Frankie' where positionchangeid=252;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='Yip Herman' , newParentPositionOwnersEN='Yip Herman' where positionchangeid=251;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='Yip Herman' , newParentPositionOwnersEN='Yip Herman' where positionchangeid=250;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='Yip Herman' , newParentPositionOwnersEN='Yip Herman' where positionchangeid=249;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='Yip Herman' , newParentPositionOwnersEN='Yip Herman' where positionchangeid=248;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='Yim Tsz Ling' , newParentPositionOwnersEN='Yim Tsz Ling' where positionchangeid=247;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Teoh Tham Kim' , oldParentPositionOwnersEN = 'Teoh Tham Kim'  ,newParentPositionOwnersZH='Teoh Tham Kim' , newParentPositionOwnersEN='Teoh Tham Kim' where positionchangeid=246;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='Tan Jun Chee' , newParentPositionOwnersEN='Tan Jun Chee' where positionchangeid=245;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='Ho Kin Pong Frankie' , newParentPositionOwnersEN='Ho Kin Pong Frankie' where positionchangeid=244;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ho Kin Pong Frankie' , oldParentPositionOwnersEN = 'Ho Kin Pong Frankie'  ,newParentPositionOwnersZH='Ho Kin Pong Frankie' , newParentPositionOwnersEN='Ho Kin Pong Frankie' where positionchangeid=243;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='Wang Hui Ming , Qiu Xiao Wen' , newParentPositionOwnersEN='Wang Hui Ming , Qiu Xiao Wen' where positionchangeid=242;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='Wang Hui Ming , Qiu Xiao Wen' , newParentPositionOwnersEN='Wang Hui Ming , Qiu Xiao Wen' where positionchangeid=241;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='Wang Hui Ming , Qiu Xiao Wen' , newParentPositionOwnersEN='Wang Hui Ming , Qiu Xiao Wen' where positionchangeid=240;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='Wang Hui Ming , Qiu Xiao Wen' , newParentPositionOwnersEN='Wang Hui Ming , Qiu Xiao Wen' where positionchangeid=239;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Wang Hui Ming , Qiu Xiao Wen' , oldParentPositionOwnersEN = 'Wang Hui Ming , Qiu Xiao Wen'  ,newParentPositionOwnersZH='Wang Hui Ming , Qiu Xiao Wen' , newParentPositionOwnersEN='Wang Hui Ming , Qiu Xiao Wen' where positionchangeid=238;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='Yim Tsz Ling' , newParentPositionOwnersEN='Yim Tsz Ling' where positionchangeid=237;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Ku Huichen' , oldParentPositionOwnersEN = 'Ku Huichen'  ,newParentPositionOwnersZH='Ku Huichen' , newParentPositionOwnersEN='Ku Huichen' where positionchangeid=236;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Fong Chi' , oldParentPositionOwnersEN = 'Fong Chi'  ,newParentPositionOwnersZH='Fong Chi' , newParentPositionOwnersEN='Fong Chi' where positionchangeid=235;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tse Mei Ha' , oldParentPositionOwnersEN = 'Tse Mei Ha'  ,newParentPositionOwnersZH='Tse Mei Ha' , newParentPositionOwnersEN='Tse Mei Ha' where positionchangeid=234;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Tse Mei Ha' , oldParentPositionOwnersEN = 'Tse Mei Ha'  ,newParentPositionOwnersZH='Tse Mei Ha' , newParentPositionOwnersEN='Tse Mei Ha' where positionchangeid=233;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Li Wai Chung' , oldParentPositionOwnersEN = 'Li Wai Chung'  ,newParentPositionOwnersZH='Tse Mei Ha' , newParentPositionOwnersEN='Tse Mei Ha' where positionchangeid=232;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chen Hong ' , oldParentPositionOwnersEN = 'Chen Hong '  ,newParentPositionOwnersZH='Chen Hong ' , newParentPositionOwnersEN='Chen Hong ' where positionchangeid=231;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yip Herman' , oldParentPositionOwnersEN = 'Yip Herman'  ,newParentPositionOwnersZH='Yip Herman' , newParentPositionOwnersEN='Yip Herman' where positionchangeid=230;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Nana Chen' , oldParentPositionOwnersEN = 'Nana Chen'  ,newParentPositionOwnersZH='Nana Chen' , newParentPositionOwnersEN='Nana Chen' where positionchangeid=229;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Aaron Yu' , oldParentPositionOwnersEN = 'Aaron Yu'  ,newParentPositionOwnersZH='Aaron Yu' , newParentPositionOwnersEN='Aaron Yu' where positionchangeid=228;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Forest Wang' , newParentPositionOwnersEN='Forest Wang' where positionchangeid=227;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = '' , oldParentPositionOwnersEN = ''  ,newParentPositionOwnersZH='Maggie Dai' , newParentPositionOwnersEN='Maggie Dai' where positionchangeid=226;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Frank Cai' , newParentPositionOwnersEN='Frank Cai' where positionchangeid=225;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Doris Zhou' , oldParentPositionOwnersEN = 'Doris Zhou'  ,newParentPositionOwnersZH='Doris Zhou' , newParentPositionOwnersEN='Doris Zhou' where positionchangeid=224;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Lydia Li' , newParentPositionOwnersEN='Lydia Li' where positionchangeid=223;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Lydia Li' , newParentPositionOwnersEN='Lydia Li' where positionchangeid=222;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Lydia Li' , oldParentPositionOwnersEN = 'Lydia Li'  ,newParentPositionOwnersZH='Lydia Li' , newParentPositionOwnersEN='Lydia Li' where positionchangeid=221;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Yim Tsz Ling' , oldParentPositionOwnersEN = 'Yim Tsz Ling'  ,newParentPositionOwnersZH='Yim Tsz Ling' , newParentPositionOwnersEN='Yim Tsz Ling' where positionchangeid=220;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Kevin Hou' , oldParentPositionOwnersEN = 'Kevin Hou'  ,newParentPositionOwnersZH='Kevin Hou' , newParentPositionOwnersEN='Kevin Hou' where positionchangeid=219;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Frank Cai' , oldParentPositionOwnersEN = 'Frank Cai'  ,newParentPositionOwnersZH='Frank Cai' , newParentPositionOwnersEN='Frank Cai' where positionchangeid=218;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Xiao Mu' , oldParentPositionOwnersEN = 'Xiao Mu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=217;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chen Hong ' , oldParentPositionOwnersEN = 'Chen Hong '  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=216;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chen Hong ' , oldParentPositionOwnersEN = 'Chen Hong '  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=215;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Chen Hong ' , oldParentPositionOwnersEN = 'Chen Hong '  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=214;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Leo Lu' , oldParentPositionOwnersEN = 'Leo Lu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=213;
UPDATE hro_biz_employee_positionchange SET oldParentPositionOwnersZH = 'Megan Lu' , oldParentPositionOwnersEN = 'Megan Lu'  ,newParentPositionOwnersZH='' , newParentPositionOwnersEN='' where positionchangeid=212;

#########
INSERT INTO `hro_biz_employee_positionchange` (`positionChangeId`, `accountId`, `corpId`, `employeeId`, `staffId`, `oldBranchId`, `oldStaffPositionRelationId`, `oldPositionId`, `oldStartDate`, `oldEndDate`, `newBranchId`, `newPositionId`, `newStartDate`, `newEndDate`, `effectiveDate`, `isImmediatelyEffective`, `deleted`, `positionStatus`, `status`, `submitFlag`, `description`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`, `employeeCertificateNumber`, `oldBranchNameZH`, `oldBranchNameEN`, `oldPositionNameZH`, `oldPositionNameEN`, `newBranchNameZH`, `newBranchNameEN`, `newPositionNameZH`, `newPositionNameEN`, `oldParentBranchId`, `oldParentBranchNameZH`, `oldParentBranchNameEN`, `oldParentPositionId`, `oldParentPositionNameZH`, `oldParentPositionNameEN`, `oldPositionGradeId`, `oldPositionGradeNameZH`, `oldPositionGradeNameEN`, `oldParentPositionOwnersZH`, `oldParentPositionOwnersEN`, `newParentBranchId`, `newParentBranchNameZH`, `newParentBranchNameEN`, `newParentPositionId`, `newParentPositionNameZH`, `newParentPositionNameEN`, `newPositionGradeId`, `newPositionGradeNameZH`, `newPositionGradeNameEN`, `newParentPositionOwnersZH`, `newParentPositionOwnersEN`, `employeeNameZH`, `employeeNameEN`, `employeeNO`)
VALUES
	(550, 100017, 300115, 100015587, 111625, 583, 23728, 2479, '2015-08-03', '2015-08-01', 583, 2479, '2015-08-01', NULL, '2015-08-01', 1, 1, 3, 1, 1, '', NULL, NULL, '5', NULL, NULL, '251', '2015-08-07', '251', '2015-08-07', '310115198912240912', '��Ʒ������', ' Solution Development', '�����Ʒ����', 'Associate Product Manager', NULL, NULL, '�����Ʒ����', 'Associate Product Manager', 1570, 'Technology', 'Technology', 1181, 'Chief Technical Officer', 'Chief Technical Officer', 201, 'IC2 Sr.Specialist', 'IC2 Sr.Specialist', '������', 'Wendy Wang ', 1570, 'Technology', 'Technology', 1181, 'Chief Technical Officer', 'Chief Technical Officer', 201, 'IC2 Sr.Specialist', 'IC2 Sr.Specialist', '������', 'Yim Tsz Ling', '���Ѷ�', 'Tony Wang', ''),
	(551, 100017, 300115, 100017682, 112043, 583, 23726, 3496, '2015-08-03', '2015-08-01', 583, 3496, '2015-08-01', NULL, '2015-08-01', 1, 1, 3, 1, 1, '', NULL, NULL, '5', NULL, NULL, '251', '2015-08-07', '251', '2015-08-07', '450103199205202028', '��Ʒ������', ' Solution Development', '��ƷרԱ', 'Product Specialist', NULL, NULL, '��ƷרԱ', 'Product Specialist', 1570, 'Technology', 'Technology', 1181, 'Chief Technical Officer', 'Chief Technical Officer', 203, 'IC1 Specialist', 'IC1 Specialist', '������', 'Wendy Wang ', 1570, 'Technology', 'Technology', 1181, 'Chief Technical Officer', 'Chief Technical Officer', 203, 'IC1 Specialist', 'IC1 Specialist', '������', 'Yim Tsz Ling', '�ε�', 'Red He', '');

INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(467, 'menu_employee_BatchUpdateContract', 1, '��������Э��', 'Labor Contract', '�����Ͷ���ͬ', 'Update Labor Contract', 0, 'N/A', 9, 'HRO_BIZ_EMPLOYEE_CONTRACT_BATCH_IMPORT', '', 'employeeContractBatchAction.do?proc=list_batch', 'employeeContractBatchAction.do?proc=list_batch', '', '', '', '', 'employeeContractBatchAction.do?proc=delete_object', 'employeeContractBatchAction.do?proc=delete_objectList', 465, 'menu_employee_Modules', 'menu_employee_Import', 'menu_employee_BatchUpdateContract', 467, '{2:5:6:14:26:28}', '', '', 1, 1, '', '', '', '', '', '1', '2014-04-20 17:20:00', '1', '2014-05-15 15:01:21');


ALTER TABLE `hro_payment_header`
ADD COLUMN `annualBonus`  DOUBLE  DEFAULT NULL AFTER `taxAmountPersonal`;
	###########2015��08��15��16:08:04###############
INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(6970, 1, NULL, 69, 'remark4', 'ְλ�б�', 'ְλ�б�', 'Position List', 0, 2, 2, 1, 2, 2, 100, 2, 2, 2, '99', '', '', '0', NULL, '0_0', '0_0', 1, 2, '', '', 0, '', '', '', 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2015-08-14 15:28:34', '1', '2015-08-14 15:28:34');
UPDATE hro_sys_log A, HRO_BIZ_EMPLOYEE_CONTRACT B 
SET A.`content` = CONCAT('��ͬ��Ӷʱ�䣺',B.`startDate`)
WHERE A.`pKey` = B.CONTRACTID AND A.MODULE = 'com.kan.hro.domain.biz.employee.EmployeeContractVO' and  A.content like'{%'	 AND A.TYPE = 1;


#####2015��08��21��14:46:40

ALTER TABLE `hro_biz_employee_positionchange`
ADD COLUMN `isChildChange`  TINYINT(4) DEFAULT 2 AFTER `positionStatus`;

####2015-08-31 09:53:47
CREATE TABLE `hro_biz_employee_salary_adjustment_temp` (
  `salaryAdjustmentId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `corpId` int(11) NOT NULL,
  `batchId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `contractId` int(11) NOT NULL,
  `employeeSalaryId` int(11) DEFAULT NULL,
  `oldBase` double DEFAULT NULL,
  `oldStartDate` date DEFAULT NULL,
  `oldEndDate` date DEFAULT NULL,
  `newBase` double DEFAULT NULL,
  `newStartDate` date DEFAULT NULL,
  `newEndDate` date DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `submitFlag` tinyint(4) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `remark1` varchar(1000) DEFAULT NULL,
  `remark2` varchar(1000) DEFAULT NULL,
  `remark3` varchar(1000) DEFAULT NULL,
  `remark4` varchar(1000) DEFAULT NULL,
  `remark5` varchar(1000) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyBy` varchar(255) DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  PRIMARY KEY (`salaryAdjustmentId`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=gbk;

INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(1172, 'menu_employee_batch_salary_adjustment', 1, '������н', 'Batch Salary Adjustment', '������н', 'Batch Salary Adjustment', 0, 'N/A', 9, '{HRO_EMPLOYEE_BATCH_SALARY_ADJUSTMENT}', NULL, 'employeeSalaryAdjustmentBatchAction.do?proc=list_object', 'employeeSalaryAdjustmentBatchAction.do?proc=list_object', '', '', '', '', '', '', 465, 'menu_employee_Modules', 'menu_employee_Import', 'menu_employee_batch_salary_adjustment', 1172, '{2:5:6:14:26}', '', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2013-06-25 18:24:08', '1', '2015-08-28 11:32:24');

###############2015��09��16��10:12:11
alter table `hro_sec_branch`  
add column `settlementBranch` int(11) default 0 AFTER `businessTypeId`;



UPDATE `hro_sec_branch` set settlementBranch = 	549	, businessTypeId = 	95	WHERE branchId = 	551	;
UPDATE `hro_sec_branch` set settlementBranch = 	549	, businessTypeId = 	71	WHERE branchId = 	553	;
UPDATE `hro_sec_branch` set settlementBranch = 	549	, businessTypeId = 	97	WHERE branchId = 	1759	;
UPDATE `hro_sec_branch` set settlementBranch = 	549	, businessTypeId = 	93	WHERE branchId = 	1273	;
UPDATE `hro_sec_branch` set settlementBranch = 	549	, businessTypeId = 	95	WHERE branchId = 	1289	;
UPDATE `hro_sec_branch` set settlementBranch = 	549	, businessTypeId = 	95	WHERE branchId = 	1291	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	71	WHERE branchId = 	723	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	120	WHERE branchId = 	725	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	97	WHERE branchId = 	729	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	91	WHERE branchId = 	1301	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	91	WHERE branchId = 	1551	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	119	WHERE branchId = 	1573	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	95	WHERE branchId = 	1574	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	71	WHERE branchId = 	1758	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	95	WHERE branchId = 	721	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	95	WHERE branchId = 	719	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	71	WHERE branchId = 	727	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	81	WHERE branchId = 	1279	;
UPDATE `hro_sec_branch` set settlementBranch = 	717	, businessTypeId = 	67	WHERE branchId = 	1099	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	81	WHERE branchId = 	1283	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	81	WHERE branchId = 	1281	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	81	WHERE branchId = 	1804	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	87	WHERE branchId = 	795	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	87	WHERE branchId = 	797	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	111	WHERE branchId = 	1554	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	111	WHERE branchId = 	1553	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	107	WHERE branchId = 	649	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	107	WHERE branchId = 	651	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	134	WHERE branchId = 	595	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	83	WHERE branchId = 	593	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	83	WHERE branchId = 	591	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	575	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	577	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	579	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	581	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	1555	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	583	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	585	;
UPDATE `hro_sec_branch` set settlementBranch = 	1591	, businessTypeId = 	89	WHERE branchId = 	587	;
UPDATE `hro_sec_branch` set settlementBranch = 	1765	, businessTypeId = 	95	WHERE branchId = 	1779	;
UPDATE `hro_sec_branch` set settlementBranch = 	1765	, businessTypeId = 	97	WHERE branchId = 	1772	;
UPDATE `hro_sec_branch` set settlementBranch = 	1765	, businessTypeId = 	97	WHERE branchId = 	1771	;
UPDATE `hro_sec_branch` set settlementBranch = 	1765	, businessTypeId = 	95	WHERE branchId = 	1770	;
UPDATE `hro_sec_branch` set settlementBranch = 	1765	, businessTypeId = 	95	WHERE branchId = 	1767	;
UPDATE `hro_sec_branch` set settlementBranch = 	1765	, businessTypeId = 	71	WHERE branchId = 	1768	;
UPDATE `hro_sec_branch` set settlementBranch = 	1765	, businessTypeId = 	97	WHERE branchId = 	1769	;
UPDATE `hro_sec_branch` set settlementBranch = 	1773	, businessTypeId = 	95	WHERE branchId = 	1806	;
UPDATE `hro_sec_branch` set settlementBranch = 	1773	, businessTypeId = 	71	WHERE branchId = 	1775	;
UPDATE `hro_sec_branch` set settlementBranch = 	1773	, businessTypeId = 	95	WHERE branchId = 	1774	;
UPDATE `hro_sec_branch` set settlementBranch = 	1773	, businessTypeId = 	95	WHERE branchId = 	1777	;
UPDATE `hro_sec_branch` set settlementBranch = 	1773	, businessTypeId = 	97	WHERE branchId = 	1776	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	95	WHERE branchId = 	1805	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	71	WHERE branchId = 	1786	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	136	WHERE branchId = 	1790	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	136	WHERE branchId = 	1789	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	93	WHERE branchId = 	1788	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	91	WHERE branchId = 	1787	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	71	WHERE branchId = 	1785	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	95	WHERE branchId = 	1782	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	95	WHERE branchId = 	1783	;
UPDATE `hro_sec_branch` set settlementBranch = 	1781	, businessTypeId = 	95	WHERE branchId = 	1784	;
UPDATE `hro_sec_branch` set settlementBranch = 	1791	, businessTypeId = 	93	WHERE branchId = 	1796	;
UPDATE `hro_sec_branch` set settlementBranch = 	1791	, businessTypeId = 	111	WHERE branchId = 	1795	;
UPDATE `hro_sec_branch` set settlementBranch = 	1791	, businessTypeId = 	97	WHERE branchId = 	1792	;
UPDATE `hro_sec_branch` set settlementBranch = 	1791	, businessTypeId = 	97	WHERE branchId = 	1794	;
UPDATE `hro_sec_branch` set settlementBranch = 	1791	, businessTypeId = 	97	WHERE branchId = 	1793	;
UPDATE `hro_sec_branch` set settlementBranch = 	1797	, businessTypeId = 	95	WHERE branchId = 	1803	;
UPDATE `hro_sec_branch` set settlementBranch = 	1797	, businessTypeId = 	71	WHERE branchId = 	1802	;
UPDATE `hro_sec_branch` set settlementBranch = 	1797	, businessTypeId = 	95	WHERE branchId = 	1801	;
UPDATE `hro_sec_branch` set settlementBranch = 	1797	, businessTypeId = 	95	WHERE branchId = 	1800	;
UPDATE `hro_sec_branch` set settlementBranch = 	1797	, businessTypeId = 	95	WHERE branchId = 	1798	;

# 2015��09��22��16:55:15
INSERT INTO `hro_sys_constant` (`constantId`, `accountId`, `nameZH`, `nameEN`, `scopeType`, `propertyName`, `valueType`, `characterType`, `content`, `lengthType`, `description`, `deleted`, `status`, `remark1`, `remark2`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(514, 1, '[���]��ٿ�ʼʱ�䣨���٣�', '[Leave]Start Date (Sick-leave)', 1, 'actualStartDate', 0, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-03-25 13:29:39', '1', '2015-03-25 13:29:53'),
	(515, 1, '[���]��ٽ���ʱ�䣨���٣�', '[Leave]End Date (Sick-leave)', 1, 'actualEndDate', 0, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-03-25 13:29:39', '1', '2015-03-25 13:29:53'),
	(516, 1, '[���]���Сʱ�������٣�', '[Leave]Actual Hours', 1, 'actualHours', 0, 3, '', 2, '', 1, 1, NULL, NULL, '1', '2015-03-25 13:29:39', '1', '2015-03-25 13:29:53');
UPDATE `hro_sys_constant` SET `propertyName` = estimateHours WHERE `constantId` = 513;

################executed#################

#########add by siuvan 2015��10��27��17:06:28
CREATE TABLE hro_question_header(						#�ʾ��������
	headerId int(11) NOT NULL AUTO_INCREMENT,			#����ID
	titleZH varchar(1000) DEFAULT NULL,					#��������
	titleEN varchar(1000) DEFAULT NULL,					#����Ӣ��
	isSingle tinyint(4) NOT NULL, 						#�Ƿ�ѡ
	expirationDate datetime NOT NULL,					#��ֹ����
	answer varchar(50) NOT NULL,						#��ȷ��
	luckyNumber	tinyint(4) NOT NULL,					#�����û�����
	luckyType tinyint(4) NOT NULL,						#��ȡ�����û���ʽ{1:�����ȡ��2:��ʱ�䣻3:λ��Ϊ8}
	tipsZH varchar(1000) DEFAULT NULL,					#������ʾ����
	tipsEN varchar(1000) DEFAULT NULL,					#������ʾӢ��
	description varchar(1000) DEFAULT NULL,				
	deleted tinyint(4) NULL DEFAULT NULL ,
	status tinyint(4) NULL DEFAULT NULL ,
	remark1 varchar(1000) DEFAULT NULL ,
	remark2 varchar(1000) DEFAULT NULL ,
	PRIMARY KEY (headerId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_question_detail(						#�ʾ���дӱ�
	detailId int(11) NOT NULL AUTO_INCREMENT, 			#����ID
	headerId int(11) NOT NULL,							#���ID
	optionIndex tinyint(4) NULL DEFAULT NULL,			#ѡ��˳��
	nameZH varchar(1000) NOT NULL,						#ѡ������
	nameEN varchar(1000) NOT NULL,						#ѡ��Ӣ��
	deleted tinyint(4) NULL DEFAULT NULL,
	status tinyint(4) NULL DEFAULT NULL,
	remark1 varchar(1000) DEFAULT NULL,
	remark2 varchar(1000) DEFAULT NULL,
	PRIMARY KEY (detailId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE hro_answer(								#�ʾ���
	answerId int(11) NOT NULL AUTO_INCREMENT,			#����ID
	headerId int(11) NOT NULL,							#���ID
	weChatId varchar(50) NOT NULL,						#΢��ID
	answer varchar(50) NOT NULL,						#��
	submitDate datetime NOT NULL,						#�ύʱ��
	deleted tinyint(4) NULL DEFAULT NULL,
	status tinyint(4) NULL DEFAULT NULL,
	remark1 varchar(1000) DEFAULT NULL,
	remark2 varchar(1000) DEFAULT NULL,
	PRIMARY KEY (answerId)
)ENGINE=InnoDB DEFAULT CHARSET=gbk;



INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`)
VALUES
	(1173, 'menu_employee_batch_position_change', 1, '�����춯', 'Batch Position Change', '�����춯', 'Batch Position Change', 0, 'N/A', 9, '{HRO_EMPLOYEE_BATCH_POSITION_CHANGE}', NULL, 'employeePositionChangeBatchAction.do?proc=list_object', 'employeePositionChangeBatchAction.do?proc=list_object', '', '', '', '', '', '', 465, 'menu_employee_Modules', 'menu_employee_Import', 'menu_employee_batch_position_change', 1173, '{2:5:6:14:26}', '', '', 1, 1, NULL, NULL, NULL, NULL, NULL, '1', '2013-06-25 18:24:08', '1', '2015-08-28 11:32:24');

	
	CREATE TABLE `hro_biz_employee_positionchange_temp` (
  `positionChangeId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `corpId` int(11) NOT NULL,
  `batchId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `staffId` int(11) NOT NULL,
  `oldBranchId` int(11) DEFAULT NULL,
  `oldStaffPositionRelationId` int(11) DEFAULT NULL,
  `oldPositionId` int(11) DEFAULT NULL,
  `oldStartDate` date DEFAULT NULL,
  `oldEndDate` date DEFAULT NULL,
  `newBranchId` int(11) DEFAULT NULL,
  `newPositionId` int(11) DEFAULT NULL,
  `newStartDate` date DEFAULT NULL,
  `newEndDate` date DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `isImmediatelyEffective` tinyint(4) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `positionStatus` tinyint(4) DEFAULT NULL,
  `isChildChange` tinyint(4) DEFAULT '2',
  `status` tinyint(4) DEFAULT NULL,
  `submitFlag` tinyint(4) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `remark1` varchar(1000) DEFAULT NULL,
  `remark2` varchar(1000) DEFAULT NULL,
  `remark3` varchar(1000) DEFAULT NULL,
  `remark4` varchar(1000) DEFAULT NULL,
  `remark5` varchar(1000) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyBy` varchar(255) DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  `oldBranchNameZH` varchar(100) DEFAULT NULL,
  `oldBranchNameEN` varchar(100) DEFAULT NULL,
  `oldPositionNameZH` varchar(100) DEFAULT NULL,
  `oldPositionNameEN` varchar(100) DEFAULT NULL,
  `newBranchNameZH` varchar(100) DEFAULT NULL,
  `newBranchNameEN` varchar(100) DEFAULT NULL,
  `newPositionNameZH` varchar(100) DEFAULT NULL,
  `newPositionNameEN` varchar(100) DEFAULT NULL,
  `oldParentBranchId` int(11) DEFAULT NULL,
  `oldParentBranchNameZH` varchar(100) DEFAULT NULL,
  `oldParentBranchNameEN` varchar(100) DEFAULT NULL,
  `oldParentPositionId` int(11) DEFAULT NULL,
  `oldParentPositionNameZH` varchar(100) DEFAULT NULL,
  `oldParentPositionNameEN` varchar(100) DEFAULT NULL,
  `oldPositionGradeId` int(11) DEFAULT NULL,
  `oldPositionGradeNameZH` varchar(100) DEFAULT NULL,
  `oldPositionGradeNameEN` varchar(100) DEFAULT NULL,
  `oldParentPositionOwnersZH` varchar(100) DEFAULT NULL,
  `oldParentPositionOwnersEN` varchar(100) DEFAULT NULL,
  `newParentBranchId` int(11) DEFAULT NULL,
  `newParentBranchNameZH` varchar(100) DEFAULT NULL,
  `newParentBranchNameEN` varchar(100) DEFAULT NULL,
  `newParentPositionId` int(11) DEFAULT NULL,
  `newParentPositionNameZH` varchar(100) DEFAULT NULL,
  `newParentPositionNameEN` varchar(100) DEFAULT NULL,
  `newPositionGradeId` int(11) DEFAULT NULL,
  `newPositionGradeNameZH` varchar(100) DEFAULT NULL,
  `newPositionGradeNameEN` varchar(100) DEFAULT NULL,
  `employeeNo` varchar(50) DEFAULT NULL,
  `employeeNameZH` varchar(100) DEFAULT NULL,
  `employeeNameEN` varchar(100) DEFAULT NULL,
  `employeeCertificateNumber` varchar(100) DEFAULT NULL,
  `newParentPositionOwnersZH` varchar(100) DEFAULT NULL,
  `newParentPositionOwnersEN` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`positionChangeId`)
) ENGINE=InnoDB AUTO_INCREMENT=884 DEFAULT CHARSET=gbk;
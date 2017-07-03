select columnid from hro_def_column where tableId = 78 and nameZH like '%ҵ������%';
-- �޸��Ͷ���ͬ��  ҵ������ Ϊɾ��״̬���Ͷ���ͬ��ȥ���ˣ�columnid����Ϊ����Ĳ�ѯ���
UPDATE hro_def_column set deleted = 2 where columnid =7808;


-- ����ҳ��˵��� ҵ������Ϊ ��Ŀ
UPDATE `hro_sys_module` SET `moduleId`='104', `moduleName`='menu_seciruty_BusinessType', `ModuleFlag`='1', `nameZH`='��Ŀ����', `nameEN`='Business Type', `titleZH`='��Ŀ����', `titleEN`='Business Type', `role`='0', `property`='N/A', `moduleType`='2', `accessAction`='HRO_SYS_BUSINESSTYPE', `accessName`=NULL, `defaultAction`='businessTypeAction.do?proc=list_object', `listAction`='', `newAction`='', `toNewAction`='', `modifyAction`='', `toModifyAction`='', `deleteAction`='', `deletesAction`='', `parentModuleId`='100', `levelOneModuleName`='menu_security_Modules', `levelTwoModuleName`='menu_seciruty_BusinessType', `levelThreeModuleName`='', `moduleIndex`='104', `rightIds`='{1:2:3:4:5}', `ruleIds`='', `description`='', `deleted`='1', `status`='1', `remark1`=NULL, `remark2`=NULL, `remark3`=NULL, `remark4`=NULL, `remark5`=NULL, `createBy`='1', `createDate`='2013-07-17 17:47:56', `modifyBy`='1', `modifyDate`='2013-11-14 13:25:21' WHERE (`moduleId`='104');

-- Ա��ҳ����� ��Ŀ�ֶ�
ALTER TABLE `hro_biz_employee` ADD COLUMN `businessTypeId` int(11) null AFTER `corpId`;


-- Ҫ��ˢ��������������Ҳ���canImport����
ALTER TABLE `hro_def_column`
ADD COLUMN `canImport`  tinyint(4) NULL AFTER `description`;

INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('11252', '1', NULL, '69', 'businessTypeId', '��Ŀ', '��Ŀ', 'Project', '0', '2', '2', '1', '2', '1', '300', '2', '2', '2', '31', '', '', '0', NULL, '0_0', '0_0', '1', '2', '', '', '2', '', '', '', '1', '1', '1', NULL, NULL, NULL, NULL, NULL, '1', '2014-11-18 15:44:14', '1', '2014-11-18 15:44:14');


-- ְλ��������ݽ�ɫ�ֶ�
ALTER TABLE `hro_sec_group` ADD COLUMN `datarole` tinyint(4) null AFTER `hrFunction`;


select DISTINCT groupid from hro_sec_group_module_rule_relation ;
-- in�������������Ĳ�ѯ���
update hro_sec_group set datarole = 1 where hrFunction = 1 and  groupid in (24,
22,
1,
102,
104,
105,
106,
107,
112,
137,
139,
153,
159,
161,
169,
167,
175,
171);

update hro_sec_group set datarole = 0 where datarole is null;


-- ɾ��Դ���ݿ��в�Ϊ˽�е�����Ȩ�ޣ��������ݣ�
delete from hro_sec_group_module_rule_relation where ruleid <> 16;

-- ��ԭ���ݿ�������Ϊ˽�е����� ��Ӧ���µ�����Ȩ���ϣ�ԭ����˽�� 16��˽�У� ��Ӧ ���ڵ� 2������Ա������
alter TABLE hro_sec_group_module_rule_relation MODIFY `moduleId`   int(11) null;
update hro_sec_group_module_rule_relation set moduleId = null , ruleid = 2 where ruleid = 16;

-- �˴���ѯ��� ��������delete
select min(relationId) from hro_sec_group_module_rule_relation GROUP BY groupId
-- in�������������sql�Ĳ�ѯ���
DELETE from hro_sec_group_module_rule_relation 
where relationId not in(26,
29,
60,
91,
122,
159,
167,
213,
269,
277,
323,
383,
381,
455,
411)

-- ��״̬ ɾ��businessTypeId ����
UPDATE hro_def_import_detail set deleted=2 where  columnId in (
SELECT columnId from hro_def_column where nameDB= 'businessTypeId') and deleted =1;


-- ��ѯ����Ա������ ��Ϊ�����insert��׼����
SELECT * from hro_def_import_header where nameZH ='Ա������';

-- Ա��������� ��Ŀ   ��headerId��������Ĳ�ѯimportHeaderId��columnId���Ӧ��17�����ɵ�columnId��
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '9', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '55', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '71', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '91', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '125', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');

INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '157', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '189', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '221', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
INSERT INTO `hro_def_import_detail` (`importDetailId`, `importHeaderId`, `columnId`, `nameZH`, `nameEN`, `isPrimaryKey`, `isForeignKey`, `columnWidth`, `columnIndex`, `fontSize`, `specialField`, `tempValue`, `isDecoded`, `isIgnoreDefaultValidate`, `datetimeFormat`, `accuracy`, `round`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, '253', '11252', '��Ŀ', 'Project', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2014-11-20 17:06:20', '3', '2014-11-20 17:06:20');
-- �洢���� 
use hrm_p;
DROP PROCEDURE if EXISTS proc_modify_businessTypeId;
CREATE PROCEDURE proc_modify_businessTypeId()
BEGIN
	
-- ����һ����־done�� �����ж��α��Ƿ�������
	DECLARE done INT DEFAULT 0;

	-- ����һ��������������Ŵ��α�����ȡ������
	-- �ر�ע����������ֲ��������α���ʹ�õ�������ͬ������õ������ݶ���NULL
	DECLARE tname varchar(50) DEFAULT NULL;
	DECLARE tpass varchar(50) DEFAULT NULL;

	-- �����α��Ӧ�� SQL ���
	DECLARE cur CURSOR FOR
		SELECT a.table_name,a.column_name FROM information_schema.columns a LEFT JOIN information_schema.VIEWS b on a.TABLE_NAME = b.TABLE_NAME 
		WHERE a.column_name = 'businessTypeId' and a.IS_NULLABLE = 'NO' and b.check_option is null and a.TABLE_SCHEMA = 'hrm_p';

	-- ���α�ѭ�������Ὣ done ����Ϊ 1
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	
	-- ִ�в�ѯ
	open cur;
	-- �����α�ÿһ��
	REPEAT
		-- ��һ�е���Ϣ����ڶ�Ӧ�ı�����
		FETCH cur INTO tname, tpass;
		if not done then
			-- ����Ϳ���ʹ�� tname�� tpass ��Ӧ����Ϣ��
			-- select tname, tpass;

			SET @sql= CONCAT('','ALTER TABLE ',tname,' MODIFY `businessTypeId` int(11) NULL');
			PREPARE s1 FROM @sql;
			EXECUTE s1;
			DEALLOCATE PREPARE s1;
			
			-- ALTER TABLE tname MODIFY `businessTypeId` int(11) NULL;
		end if;
 	UNTIL done END REPEAT;
	CLOSE cur;

END;
CALL proc_modify_businessTypeId();


---------���Ͻű���ȫ��ˢ��hrm_p

## add by Ian @ 2015��1��14��  

ALTER TABLE `hro_biz_client_order_header`
ADD COLUMN `currency`  varchar(25) NULL DEFAULT 0 AFTER `noticeHKSB`;

INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) 
VALUES ('13324', '1', NULL, '78', 'currency', '��������', '��������', 'currency', '7', NULL, NULL, '0', '2', '1', '15', '2', '2', '1', '168', '', '', '12', '', '0_0', '0_0', '1', '2', '', '', NULL, '', '', '', NULL, '1', '1', '', '', '', '', '', '1', '2014-03-26 10:22:00', '1', '2014-03-26 10:22:00');

## add by Ian @ 2015��1��16�� 
INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) 
VALUES ('13325', '1', NULL, '40', 'isDeduct', '�Ƿ�ֱ�ӿ۳�', '�Ƿ�ֱ�ӿ۳�', 'isDeduct', '2', NULL, NULL, '0', '2', '1', '5', '2', '2', '1', '6', '', '', '0', '', '0_0', '0_0', '1', '2', '', '', '1', '��ѡ���ǡ�����ȫ�ڵ������ȫ���۳�', 'If \"yes\", then Job dissatisfaction with the whole month excluding the bonuses', '', NULL, '1', '1', '', '', '', '', '', '1', '2014-03-26 10:22:00', '1', '2014-03-26 10:22:00');

ALTER TABLE `hro_biz_employee_contract_salary`
ADD COLUMN `isDeduct`  tinyint(4) NULL DEFAULT 0 AFTER `probationUsing`;

ALTER TABLE `hro_mgt_options`
ADD COLUMN `monthAvg`  varchar(25) NULL DEFAULT 21.75 AFTER `positionPrefer`;

## add by Fox @ 2015��1��16�� 
ALTER TABLE `hro_biz_employee_contract`
ADD COLUMN `hireAgain`  tinyint(4) NULL AFTER `continueNeeded`,
ADD COLUMN `payment`  tinyint(4) NULL AFTER `hireAgain`;

ALTER TABLE `hro_biz_employee`
ADD COLUMN `hireAgain`  tinyint(4) NULL AFTER `healthCardStartDate`;
## add by steven @ 2015��1��16�� 
ALTER TABLE `hro_sys_social_benefit_detail` ADD COLUMN `companyAccuracy`  tinyint(4) NULL DEFAULT NULL AFTER `makeupCrossYear`;
ALTER TABLE `hro_sys_social_benefit_detail` ADD COLUMN `personalAccuracy`  tinyint(4) NULL DEFAULT NULL AFTER `companyAccuracy`;
update hro_sys_social_benefit_detail set companyAccuracy = accuracy;
update hro_sys_social_benefit_detail set personalAccuracy = accuracy;
ALTER TABLE `hro_sys_social_benefit_detail` DROP COLUMN `accuracy`;
ALTER TABLE `hro_sys_social_benefit_header` ADD COLUMN `companyAccuracy`  tinyint(4) NULL DEFAULT NULL AFTER `makeupCrossYear`;
ALTER TABLE `hro_sys_social_benefit_header` ADD COLUMN `personalAccuracy`  tinyint(4) NULL DEFAULT NULL AFTER `companyAccuracy`;
update hro_sys_social_benefit_header set companyAccuracy = accuracy;
update hro_sys_social_benefit_header set personalAccuracy = accuracy;
ALTER TABLE `hro_sys_social_benefit_header` DROP COLUMN `accuracy`;


## add by Ian @2015-1-21 18��57
ALTER TABLE `hro_biz_employee_contract`
ADD COLUMN `currency`  varchar(25) NULL DEFAULT 'CN' AFTER `description`;

UPDATE hro_def_column set deleted=2 where tableId=78 and nameDB='currency';

INSERT INTO hro_sec_staff (
	`accountId`,
	`corpId`,
	`staffNo`,
	`employeeId`,
	`salutation`,
	`nameZH`,
	`nameEN`,
	`birthday`,
	`bizPhone`,
	`personalPhone`,
	`bizMobile`,
	`personalMobile`,
	`bizEmail`,
	`personalEmail`,
	`certificateType`,
	`certificateNumber`,
	`maritalStatus`,
	`deleted`,
	`status`,
	`remark1`,
	`remark2`,
	`remark3`,
	`remark4`,
	`remark5`,
	`createBy`,
	`createDate`,
	`modifyBy`,
	`modifyDate`
) SELECT
	`accountId`,
	`corpId`,
	`employeeNo`,
	`employeeId`,
	`salutation`,
	`nameZH`,
	`nameEN`,
	`birthday`,
	`phone1`,
	`phone2`,
	`mobile1`,
	`mobile2`,
	`email1`,
	`email2`,
	`certificateType`,
	`certificateNumber`,
	`maritalStatus`,
	1,
	`status`,
	`remark1`,
	`remark2`,
	`remark3`,
	`remark4`,
	`remark5`,
	`createBy`,
	now(),
	`modifyBy`,
	now()
FROM
	hro_biz_employee
WHERE
	employeeId NOT IN (
		SELECT
			a.employeeId
		FROM
			hro_biz_employee a
		INNER JOIN hro_sec_staff b ON a.employeeId = b.employeeId
	)
AND deleted = 1;


## add by jason.ji @ 2015��1��22�� 
INSERT INTO `hro_def_column` (`columnId`, `accountId`, `corpId`, `tableId`, `nameDB`, `nameSys`, `nameZH`, `nameEN`, `groupId`, `isPrimaryKey`, `isForeignKey`, `isDBColumn`, `isRequired`, `displayType`, `columnIndex`, `inputType`, `valueType`, `optionType`, `optionValue`, `cssStyle`, `jsEvent`, `validateType`, `validateRequired`, `validateLength`, `validateRange`, `editable`, `useThinking`, `thinkingId`, `thinkingAction`, `useTitle`, `titleZH`, `titleEN`, `description`, `canImport`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('13338', '1', NULL, '69', '_tempPositionGradeIds', 'ְ��', 'ְ��', 'Position Grade', '0', '0', '0', '1', '2', '2', '100', '1', '2', '0', NULL, '', '', '0', NULL, '0_0', '0_0', '1', '2', '', '', '0', '', '', '', '2', '1', '1', NULL, NULL, NULL, NULL, NULL, '1', '2014-11-12 15:05:56', '1', '2014-11-12 15:05:56');


INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('528', '1', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('530', '2', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('532', '5', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('534', '7', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('536', '9', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('538', '11', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('540', '17', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('542', '19', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('544', '23', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');
INSERT INTO `hro_def_manager_detail` (`managerDetailId`, `managerHeaderId`, `columnId`, `nameZH`, `nameEN`, `groupId`, `isRequired`, `display`, `columnIndex`, `useTitle`, `titleZH`, `titleEN`, `align`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('546', '31', '13338', 'ְ��', '', '0', '0', '1', '998', '2', '', '', '1', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '3', '2015-01-22 19:17:42', '3', '2015-01-22 19:17:42');


## add by fox @ 2015��1��22�� 
INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES (NULL, 'menu_biz_Employee_Salary_Report', '1', 'Ա��н�ʱ���', 'Employee Salary Report', 'Ա��н�ʱ���', 'Employee Salary Report', '2', 'N/A', '9', 'HRO_BIZ_EMPLOYEE_SALARY_REPORT', NULL, 'employeeReportAction.do?proc=list_employee_salary_object', 'employeeReportAction.do?proc=list_employee_salary_object', NULL, NULL, NULL, NULL, NULL, NULL, '499', 'menu_employee_Modules', 'menu_employee_Reports', 'menu_biz_Employee_Salary_Report', '1135', '{2:5:28}', NULL, NULL, '1', '1', NULL, NULL, NULL, NULL, NULL, '1', '2014-11-21 10:54:01', '1', '2014-11-21 10:54:01');


## add by Ian @2015-01-23
INSERT INTO `hro_mgt_item` (`itemId`, `accountId`, `corpId`, `itemNo`, `nameZH`, `nameEN`, `itemCap`, `itemFloor`, `sequence`, `personalTax`, `companyTax`, `calculateType`, `itemType`, `billRateCompany`, `billRatePersonal`, `costRateCompany`, `costRatePersonal`, `billFixCompany`, `billFixPersonal`, `costFixCompany`, `costFixPersonal`, `personalTaxAgent`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('10253', '1', NULL, '11', '����', 'Sick leave', NULL, NULL, '11', '2', '2', '1', '6', '0', '0', '0', '0', '0', '0', '0', '0', '2', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '331', '2015-01-20 10:02:57', '331', '2015-01-20 10:02:57');
## add by steven @2015-01-27
update  hro_sys_module set defaultAction ='workflowActualAction.do?proc=list_object_unfinished&actualStepStatus=0' where moduleName = 'menu_message_Pending';

## add by Ian @2015-02-04
ALTER TABLE `hro_biz_client_order_header`
ADD COLUMN `contractPeriod`  tinyint(4) NULL COMMENT '��ͬ����' AFTER `currency`;

## add by jason.ji  �춯  ��н
INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('1146', 'menu_employee_Position_Changes', '1', '�춯/��Ǩ', 'Position Changes', '�춯/��Ǩ', 'Position Changes', '0', 'N/A', '9', '{HRO_BIZ_EMPLOYEE_POSITION_CHANGES}', NULL, 'employeePositionChangeAction.do?proc=list_object', 'employeePositionChangeAction.do?proc=list_object', '', '', '', '', '', '', '450', 'menu_employee_Modules', 'menu_employee_Position_Changes', '', '455', '{1:2:3:4:5:6}', '', '', '1', '1', NULL, NULL, '', NULL, NULL, '1', '2013-09-05 17:57:06', '1', '2014-05-15 10:49:06');
INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('1148', 'menu_employee_salary_adjustment', '1', '��н', 'Salary Adjustment', '��н', 'Salary Adjustment', '0', 'N/A', '9', '{HRO_EMPLOYEE_SALARY_ADJUSTMENT}', NULL, 'salaryAdjustment.do?proc=list_object', 'salaryAdjustment.do?proc=list_object', '', '', '', '', '', '', '450', 'menu_employee_Modules', 'menu_employee_salary_adjustment', '', '456', '{1:2:3:4:5:6}', '', '', '1', '1', NULL, NULL, '', NULL, NULL, '1', '2013-09-05 17:57:06', '1', '2014-05-15 10:49:06');

DROP TABLE IF EXISTS `hro_biz_employee_salary_adjustment`;
CREATE TABLE `hro_biz_employee_salary_adjustment` (
  `salaryAdjustmentId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `corpId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `contractId` int(11) NOT NULL,
  `employeeSalaryId` int(11) DEFAULT NULL,
  `oldBase` int(11) DEFAULT NULL,
  `oldStartDate` date DEFAULT NULL,
  `oldEndDate` date DEFAULT NULL,
  `newBase` int(11) DEFAULT NULL,
  `newStartDate` date DEFAULT NULL,
  `newEndDate` date DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `submitFlag` tinyint(4) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyBy` varchar(255) DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  PRIMARY KEY (`salaryAdjustmentId`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `hro_biz_employee_positionchange`;
CREATE TABLE `hro_biz_employee_positionchange` (
  `positionChangeId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `corpId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `staffId` int(11) NOT NULL,
  `oldBranchId` int(11) DEFAULT NULL,
  `oldPositionId` int(11) DEFAULT NULL,
  `oldStartDate` date DEFAULT NULL,
  `oldEndDate` date DEFAULT NULL,
  `newBranchId` int(11) DEFAULT NULL,
  `newPositionId` int(11) DEFAULT NULL,
  `newStartDate` date DEFAULT NULL,
  `newEndDate` date DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `isImmediatelyEffective` tinyint(4) NOT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `positionStatus` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `submitFlag` tinyint(4) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyBy` varchar(255) DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  PRIMARY KEY (`positionChangeId`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=gbk;



##add by fox @2015-02-12 ���� 

CREATE TABLE `hro_biz_travel` (
  `travelId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) DEFAULT NULL,
  `corpId` int(11) DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `startDate` varchar(200) DEFAULT NULL,
  `endDate` varchar(200) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `remark1` varchar(5000) DEFAULT NULL,
  `remark2` varchar(1000) DEFAULT NULL,
  `remark3` varchar(1000) DEFAULT NULL,
  `remark4` varchar(1000) DEFAULT NULL,
  `remark5` varchar(1000) DEFAULT NULL,
  `createBy` varchar(25) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyBy` varchar(25) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`travelId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=gbk;

INSERT INTO `hro_sys_module` (`moduleId`, `moduleName`, `ModuleFlag`, `nameZH`, `nameEN`, `titleZH`, `titleEN`, `role`, `property`, `moduleType`, `accessAction`, `accessName`, `defaultAction`, `listAction`, `newAction`, `toNewAction`, `modifyAction`, `toModifyAction`, `deleteAction`, `deletesAction`, `parentModuleId`, `levelOneModuleName`, `levelTwoModuleName`, `levelThreeModuleName`, `moduleIndex`, `rightIds`, `ruleIds`, `description`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('1154', 'menu_attendance_Travel', '1', '����', 'Travel', '����', 'Travel', '0', 'N/A', '10', '{HRO_BIZ_TRAVEL}', NULL, 'travelAction.do?proc=list_object', 'travelAction.do?proc=list_object', 'travelAction.do?proc=add_object', 'travelAction.do?proc=to_objectNew', 'travelAction.do?proc=modify_object', 'travelAction.do?proc=to_objectModify', 'travelAction.do?proc=delete_object', 'travelAction.do?proc=delete_objectList', '500', 'menu_attendance_Modules', 'menu_attendance_Travel', '', '503', '{1:2:3:4:5:6:28}', '{16}', '', '1', '1', NULL, NULL, NULL, NULL, NULL, '1', '2013-07-28 17:17:25', '1', '2014-10-22 12:47:17');

##add by steven @2015-02-13 �������� 
ALTER TABLE `hro_biz_employee_contract` ADD INDEX index_employeeId ( `employeeId` ) 


##add by fox @2015-03-02 ��������Ӱ��ֶ� 
ALTER TABLE `hro_biz_attendance_ot_header`
ADD COLUMN `specialOT`  tinyint(4) NULL AFTER `dataFrom`;


##add by ian 2015-03-03
ALTER TABLE `hro_payment_header`
ADD COLUMN `annualBonusTax`  double NULL DEFAULT 0 AFTER `taxAmountPersonal`;

##add by jason.ji 2015-03-04
UPDATE hro_biz_employee INNER JOIN hro_biz_employee_contract ON hro_biz_employee.employeeId=hro_biz_employee_contract.employeeId
SET hro_biz_employee.businessTypeId=hro_biz_employee_contract.businessTypeId
where hro_biz_employee.businessTypeId is null or hro_biz_employee.businessTypeId =0

##add by jason.ji 2015-03-05
UPDATE `hro_def_column` SET `jsEvent`='onFocus=\"WdatePicker()\"' WHERE (`columnId`='2202');
UPDATE `hro_def_column` SET `jsEvent`='onFocus=\"WdatePicker({minDate:\'#F{$dp.$D(\\\'startDate\\\',{d:1})}\'})\"' WHERE (`columnId`='2203');

INSERT INTO `hro_sys_right` (`rightId`, `rightType`, `nameZH`, `nameEN`, `deleted`, `status`, `remark1`, `remark2`, `remark3`, `remark4`, `remark5`, `createBy`, `createDate`, `modifyBy`, `modifyDate`) VALUES ('52', '1', '�б�����', 'Sort list', '1', '1', NULL, NULL, NULL, NULL, NULL, '1', '2015-03-05 17:17:23', '1', '2015-03-05 17:17:23');
UPDATE `hro_sys_module` SET `rightIds`='{1:2:3:4:5:26:28:50:52}' WHERE (`moduleId`='451');

ALTER TABLE hro_biz_employee MODIFY COLUMN highestEducation int(4);


DROP TABLE IF EXISTS `hro_biz_employee_positionchange`;
CREATE TABLE `hro_biz_employee_positionchange` (
  `positionChangeId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `corpId` int(11) NOT NULL,
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
  `status` tinyint(4) DEFAULT NULL,
  `submitFlag` tinyint(4) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyBy` varchar(255) DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  PRIMARY KEY (`positionChangeId`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=gbk;
##add by steven 2015-03-13 �޶��Ͷ���ͬ�����ڽ������ڵ�Ĭ��ֵ
update hro_biz_employee_contract c set c.probationMonth = (select o.probationMonth from HRO_BIZ_Client_Order_Header o where o.orderHeaderId=c.orderId)
where c.probationMonth =0  or c.probationMonth is null;

update hro_biz_employee_contract c set c.probationEndDate	= date_add(
		c.startDate,
		INTERVAL c.probationMonth MONTH
	)
where c.probationEndDate is null;

##add by Fox 2015-03-24 ��Ӷ״̬�޸�


-- �ǰ�����޸�
 UPDATE hro_biz_employee_contract
SET leaveReasons = (
	CASE employStatus
	WHEN 2 THEN
		'��������'
	WHEN 3 THEN
		'��λ���'
	WHEN 4 THEN
		'���˴�ְ'
	WHEN 5 THEN
		'��λ���������Υ�ͣ�'
	WHEN 6 THEN
		'��λ����������ڲ��ϸ�'
	WHEN 7 THEN
		'��λ�����������'
	WHEN 8 THEN
		'Э�̽��'
	WHEN 9 THEN
		'���ݣ��籣�ɷ����޲��㣩'
	WHEN 10 THEN
		'����'
	WHEN 11 THEN
		'����'
	WHEN 12 THEN
		'�̵�'
	WHEN 13 THEN
		'�ξ�'
	ELSE
		leaveReasons
	END
)
WHERE
	employStatus != '1'
AND (
	leaveReasons IS NULL
	OR leaveReasons = ''
)
AND accountId NOT IN (100017) 

;
-- ������޸�
UPDATE hro_biz_employee_contract
SET leaveReasons = (
	CASE employStatus
	WHEN 2 THEN
		'ǩ֤����'
	WHEN 3 THEN
		'����ְλ��ƥ��'
	WHEN 4 THEN
		'Υ����˾�涨'
	WHEN 5 THEN
		'����/����ְλ����'
	WHEN 6 THEN
		'��Ч����'
	WHEN 7 THEN
		'��������'
	WHEN 8 THEN
		'�Ϻõ�н��'
	WHEN 9 THEN
		'ְҵ���'
	WHEN 10 THEN
		'ְҵ��չ/��Ǩ����'
	WHEN 11 THEN
		'��˾��Ǩ/ͨ�ڿ���'
	WHEN 12 THEN
		'�Թ�˾����ͬ/û����'
	WHEN 13 THEN
		'�����ܲ�����'
	WHEN 14 THEN
		'��ѧ'
	WHEN 15 THEN
		'��ͥ����'
	WHEN 16 THEN
		'��������'
	WHEN 17 THEN
		'Э����ְ'
	WHEN 18 THEN
		'��ͬ����'
	WHEN 19 THEN
		'����'
	WHEN 20 THEN
		'��������Ӧ/������'
	WHEN 21 THEN
		'��Ч����'
	WHEN 22 THEN
		'����/�����ƽ��'

ELSE leaveReasons END ) where employStatus !='1 'and  (leaveReasons is NULL or leaveReasons = '') and accountId  in (100017)  


UPDATE hro_biz_employee_contract set  employStatus='3' where employStatus !='1'

UPDATE  hro_def_column set optionValue ='72'   where nameDB='employStatus' and  ( optionValue ='67' or optionValue='167')


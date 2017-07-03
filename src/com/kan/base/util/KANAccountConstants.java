package com.kan.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.BankTemplateDTO;
import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.MappingDTO;
import com.kan.base.domain.define.OptionDTO;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.SearchDTO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableVO;
import com.kan.base.domain.define.TaxTemplateDTO;
import com.kan.base.domain.management.AnnualLeaveRuleDTO;
import com.kan.base.domain.management.BankVO;
import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.domain.management.CalendarDTO;
import com.kan.base.domain.management.CertificationVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.ContractTypeVO;
import com.kan.base.domain.management.EducationVO;
import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.domain.management.EmployeeStatusVO;
import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.domain.management.IndustryTypeVO;
import com.kan.base.domain.management.ItemGroupDTO;
import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.domain.management.ItemMappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.domain.management.LanguageVO;
import com.kan.base.domain.management.MembershipVO;
import com.kan.base.domain.management.OptionsVO;
import com.kan.base.domain.management.ResignTemplateVO;
import com.kan.base.domain.management.ShareFolderConfigurationVO;
import com.kan.base.domain.management.ShiftDTO;
import com.kan.base.domain.management.SickLeaveSalaryDTO;
import com.kan.base.domain.management.SkillBaseView;
import com.kan.base.domain.management.SkillDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.TaxVO;
import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.GroupBaseView;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleDTO;
import com.kan.base.domain.security.GroupModuleRightRelationVO;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.domain.security.LocationVO;
import com.kan.base.domain.security.PositionBaseView;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionModuleDTO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.domain.system.RightVO;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.domain.workflow.WorkflowDefineDTO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.service.inf.define.BankTemplateHeaderService;
import com.kan.base.service.inf.define.ColumnGroupService;
import com.kan.base.service.inf.define.ImportHeaderService;
import com.kan.base.service.inf.define.ListHeaderService;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.service.inf.define.MappingHeaderService;
import com.kan.base.service.inf.define.OptionHeaderService;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.service.inf.define.SearchHeaderService;
import com.kan.base.service.inf.define.TableService;
import com.kan.base.service.inf.define.TaxTemplateHeaderService;
import com.kan.base.service.inf.management.AnnualLeaveRuleHeaderService;
import com.kan.base.service.inf.management.BankService;
import com.kan.base.service.inf.management.BusinessContractTemplateService;
import com.kan.base.service.inf.management.CalendarHeaderService;
import com.kan.base.service.inf.management.CertificationService;
import com.kan.base.service.inf.management.CommercialBenefitSolutionHeaderService;
import com.kan.base.service.inf.management.ContractTypeService;
import com.kan.base.service.inf.management.EducationService;
import com.kan.base.service.inf.management.EmailConfigurationService;
import com.kan.base.service.inf.management.EmployeeStatusService;
import com.kan.base.service.inf.management.ExchangeRateService;
import com.kan.base.service.inf.management.IndustryTypeService;
import com.kan.base.service.inf.management.ItemGroupService;
import com.kan.base.service.inf.management.ItemMappingService;
import com.kan.base.service.inf.management.ItemService;
import com.kan.base.service.inf.management.LaborContractTemplateService;
import com.kan.base.service.inf.management.LanguageService;
import com.kan.base.service.inf.management.MembershipService;
import com.kan.base.service.inf.management.OptionsService;
import com.kan.base.service.inf.management.ResignTemplateService;
import com.kan.base.service.inf.management.ShareFolderConfigurationService;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.service.inf.management.SickLeaveSalaryHeaderService;
import com.kan.base.service.inf.management.SkillService;
import com.kan.base.service.inf.management.SocialBenefitSolutionHeaderService;
import com.kan.base.service.inf.management.TaxService;
import com.kan.base.service.inf.message.MessageTemplateService;
import com.kan.base.service.inf.security.BranchService;
import com.kan.base.service.inf.security.BusinessTypeService;
import com.kan.base.service.inf.security.EntityService;
import com.kan.base.service.inf.security.GroupService;
import com.kan.base.service.inf.security.LocationService;
import com.kan.base.service.inf.security.PositionGradeService;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.system.ConstantService;
import com.kan.base.service.inf.system.LogService;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.service.inf.system.WorkflowModuleService;
import com.kan.base.web.actions.util.DownloadFileAction;

/**
 * @author Kevin Jin
 */
public class KANAccountConstants
{
   // 添加Logger功能
   protected Log logger = LogFactory.getLog( getClass() );

   /* Options Constants Start */

   public String ACCOUNT_ID;

   public String OPTIONS_LOGO_FILE = "images/logo/kanlogo_blue_en_s_s_new.png";

   public String OPTIONS_MOBILE_RIGHTS;

   public List< MappingVO > OPTIONS_CLIENT_LOGO_FILE = new ArrayList< MappingVO >();

   public List< MappingVO > OPTIONS_CLIENT_MOBILE_RIGHTS = new ArrayList< MappingVO >();

   // 1:架构图；2:列表
   public String OPTIONS_BRANCH_PREFER = "1";
   // 1:架构图；2:树状
   public String OPTIONS_POSITION_PREFER = "1";
   // 0:不统计子部门或以下部门人数##1:统计子部门及以下部门人数
   public String OPTIONS_ISSUMSUBBRANCHHC = "0";

   public String OPTIONS_LANGUAGE = "zh_CN";

   public boolean OPTIONS_USE_BS_LANGUAGE = true;

   public String OPTIONS_DATE_FORMAT = "yyyy-MM-dd";

   public String OPTIONS_TIME_FORMAT = "hh:mm:ss";

   public String OPTIONS_PAGE_STYLE;

   public String OPTIONS_SMS_CONFIG;

   public String OPTIONS_MAIL_SHOW_NAME;

   public boolean OPTIONS_ORDER_BIND_CONTRACT = true;

   public String OPTIONS_SB_GENERATE_CONDITION;

   public String OPTIONS_CB_GENERATE_CONDITION;

   public String OPTIONS_SETTLEMENT_GENERATE_CONDITION;

   public String OPTIONS_SB_GENERATE_CONDITION_SC;

   public String OPTIONS_CB_GENERATE_CONDITION_SC;

   public String OPTIONS_SETTLEMENT_GENERATE_CONDITION_SC;

   public String OPTIONS_ACCURACY;

   public String OPTIONS_ROUND;

   public String OPTIONS_INDEPENDENCE_TAX;

   public String OPTIONS_OT_MIN_UNIT;

   /* Email Constants Start */

   // 1:Kangroup(KAN); 2:Individual(IND)
   public String MAIL_TYPE = "KAN";

   public String MAIL_SENT_AS;

   public String MAIL_ACCOUNT_NAME;

   public String MAIL_SMTP_HOST;

   public String MAIL_SMTP_PORT = "25";

   public String MAIL_USERNAME;

   public String MAIL_PASSWORD;

   // 1:True; 2:False
   public boolean MAIL_SMTP_AUTH_TYPE = true;

   // 0:NULL; 1:SSL; 2:TLS
   public String MAIL_SMTP_SECURITY_TYPE = "NULL";

   public String MAIL_POP3_HOST;

   public String MAIL_POP3_PORT = "110";

   /* ShareFolder Constants Start */

   public String SHAREFOLDER_HOST;

   public String SHAREFOLDER_PORT;

   public String SHAREFOLDER_USERNAME;

   public String SHAREFOLDER_PASSWORD;

   public String SHAREFOLDER_DIRECTORY;

   // 月平均计薪日
   public double AVG_SALARY_DAYS_PER_MONTH;

   /**
    * Define
    */
   // 系统定义的表或视图，包含系统（可自主设定）及自定义的字段 - DTO
   public List< TableDTO > TABLE_DTO = new ArrayList< TableDTO >();

   // 账户自定义的字段分组 - VO
   public List< ColumnGroupVO > COLUMN_GROUP_VO = new ArrayList< ColumnGroupVO >();

   // 账户自定义选项 - DTO
   public List< OptionDTO > COLUMN_OPTION_DTO = new ArrayList< OptionDTO >();

   // 账户自定义搜索 - DTO
   public List< SearchDTO > SEARCH_DTO = new ArrayList< SearchDTO >();

   // 账户自定义列表 - DTO
   public List< ListDTO > LIST_DTO = new ArrayList< ListDTO >();

   // 账户自定义报表 - DTO（状态：已发布）
   public List< ReportDTO > REPORT_DTO = new ArrayList< ReportDTO >();

   // 账户自定义导入 - DTO
   public List< ImportDTO > IMPORT_DTO = new ArrayList< ImportDTO >();

   // 账户自定义页面 - DTO
   public List< ManagerDTO > MANAGER_DTO = new ArrayList< ManagerDTO >();

   // 账户客户导入导出匹配 - DTO
   public List< MappingDTO > MAPPING_DTO = new ArrayList< MappingDTO >();

   // 账户自定义工资模板 - DTO
   public List< BankTemplateDTO > BANK_TEMPLATE_DTO = new ArrayList< BankTemplateDTO >();

   // 账户自定义个税模板 - DTO
   public List< TaxTemplateDTO > TAX_TEMPLATE_DTO = new ArrayList< TaxTemplateDTO >();

   /**
    * Security & System
    */
   // 账户有效员工 - DTO
   public List< StaffDTO > STAFF_DTO = new ArrayList< StaffDTO >();

   // 账户有效员工 - Base View
   public List< StaffBaseView > STAFF_BASEVIEW = new ArrayList< StaffBaseView >();

   // 账户有效职位 - DTO
   public List< PositionDTO > POSITION_DTO = new ArrayList< PositionDTO >();

   // 账户有效职位 - Base View
   public List< PositionBaseView > POSITION_BASEVIEW = new ArrayList< PositionBaseView >();

   // 账户有效职位 - VO
   public List< PositionVO > POSITION_VO = new ArrayList< PositionVO >();

   // 账户有效职位组 - DTO
   public List< GroupDTO > POSITION_GROUP_DTO = new ArrayList< GroupDTO >();

   // 账户有效职位组 - Base View
   public List< GroupBaseView > POSITION_GROUP_BASEVIEW = new ArrayList< GroupBaseView >();

   // 账户有效职位组 - VO
   public List< GroupVO > POSITION_GROUP_VO = new ArrayList< GroupVO >();

   // 账户有效技能 - DTO
   public List< SkillBaseView > SKILL_BASEVIEW = new ArrayList< SkillBaseView >();

   // 账户有效地点 - VO
   public List< LocationVO > LOCATION_VO = new ArrayList< LocationVO >();

   // 系统日志模板
   public List< LogVO > SYS_LOG_VO = new ArrayList< LogVO >();

   public List< MappingVO > SYS_LOG_OPER_TYPE = new ArrayList< MappingVO >();

   // 账户有效部门 - VO
   public List< BranchVO > BRANCH_VO = new ArrayList< BranchVO >();

   // 账户有效部门 - DTO
   public List< BranchDTO > BRANCH_DTO = new ArrayList< BranchDTO >();

   // 账户有效职位等级 - VO
   public List< PositionGradeVO > POSITION_GRADE_VO = new ArrayList< PositionGradeVO >();

   // 账户全局设定的模块 - DTO
   public List< AccountModuleDTO > MODULE_DTO = new ArrayList< AccountModuleDTO >();

   // 账户全局设定的模块 - VO
   public List< ModuleVO > MODULE_VO = new ArrayList< ModuleVO >();

   // 账户全局设定的模块 - VO
   public List< ModuleVO > CLIENT_SELECT_MODULE_VO = new ArrayList< ModuleVO >();

   // 账户参数（含系统参数）
   public List< ConstantVO > CONSTANT_VO = new ArrayList< ConstantVO >();

   /**
    * Message
    */

   public List< MessageTemplateVO > MESSAGE_TEMPLATE_VO = new ArrayList< MessageTemplateVO >();

   /**
    * Management
    */
   // 账户有效法务实体 - VO
   public List< EntityVO > ENTITY_VO = new ArrayList< EntityVO >();

   // 账户有效税率 - VO
   public List< TaxVO > TAX_VO = new ArrayList< TaxVO >();

   // 账户有效劳动合同类型 - VO
   public List< ContractTypeVO > CONTRACT_TYPE_VO = new ArrayList< ContractTypeVO >();

   // 账户有效雇佣状态 - VO
   public List< EmployeeStatusVO > EMPLOYEE_STATUS_VO = new ArrayList< EmployeeStatusVO >();

   // 账户有效教育程度 - VO
   public List< EducationVO > EDUCATION_VO = new ArrayList< EducationVO >();

   // 账户有效语言能力 - VO
   public List< LanguageVO > LANGUAGE_VO = new ArrayList< LanguageVO >();

   // 账户有汇率 - VO
   public List< ExchangeRateVO > EXCHANGE_RATE_VO = new ArrayList< ExchangeRateVO >();

   // 账户有效技能 - DTO
   public List< SkillDTO > SKILL_DTO = new ArrayList< SkillDTO >();

   // 雇员有效职位 - DTO
   public List< com.kan.base.domain.management.PositionDTO > EMPLOYEE_POSITION_DTO = new ArrayList< com.kan.base.domain.management.PositionDTO >();

   // 账户有效职位等级 - VO
   public List< com.kan.base.domain.management.PositionGradeVO > EMPLOYEE_POSITION_GRADE_VO = new ArrayList< com.kan.base.domain.management.PositionGradeVO >();

   // 账户有效的科目 - VO
   public List< ItemVO > ITEM_VO = new ArrayList< ItemVO >();

   // 账户有效的银行 - VO
   public List< BankVO > BANK_VO = new ArrayList< BankVO >();

   // 账户有效的科目分组 - DTO
   public List< ItemGroupDTO > ITEM_GROUP_DTO = new ArrayList< ItemGroupDTO >();

   // 账户有效的业务类型 - VO
   public List< BusinessTypeVO > BUSINESS_TYPE_VO = new ArrayList< BusinessTypeVO >();

   // 账户有效的行业类型 - VO
   public List< IndustryTypeVO > INDUSTRY_TYPE_VO = new ArrayList< IndustryTypeVO >();

   // 账户有效科目映射 - VO
   public List< ItemMappingVO > ITEM_MAPPING_VO = new ArrayList< ItemMappingVO >();

   // 账户有效商务合同 - VO
   public List< BusinessContractTemplateVO > BUSINESS_CONTRACT_TEMPLATE_VO = new ArrayList< BusinessContractTemplateVO >();

   // 账户有效劳务合同 - VO
   public List< LaborContractTemplateVO > LABOR_CONTRACT_TEMPLATE_VO = new ArrayList< LaborContractTemplateVO >();

   //账户有效的退账单 VO
   public List< ResignTemplateVO > RESIGN_TEMPLATE_VO = new ArrayList< ResignTemplateVO >();

   // 账户有效社团活动 - VO
   public List< MembershipVO > MEMBERSHIP_VO = new ArrayList< MembershipVO >();

   //  账户有效证书 - 奖项 -VO
   public List< CertificationVO > CERTIFICATION_VO = new ArrayList< CertificationVO >();

   // 账户有效社保方案 - DTO
   public List< SocialBenefitSolutionDTO > SOCIAL_BENEFIT_SOLUTION_DTO = new ArrayList< SocialBenefitSolutionDTO >();

   // 账户有效商保方案 - DTO
   public List< CommercialBenefitSolutionDTO > COMMERCIAL_BENEFIT_SOLUTION_DTO = new ArrayList< CommercialBenefitSolutionDTO >();

   // 账户有效日历 - DTO
   public List< CalendarDTO > CALENDAR_DTO = new ArrayList< CalendarDTO >();

   // 账户有效排班 - DTO
   public List< ShiftDTO > SHIFT_DTO = new ArrayList< ShiftDTO >();

   // 账户有效病假方案 - DTO
   public List< SickLeaveSalaryDTO > SICK_LEAVE_SALARY_DTO = new ArrayList< SickLeaveSalaryDTO >();

   // 账户有效年假规则 - DTO
   public List< AnnualLeaveRuleDTO > ANNUAL_LEAVE_RULE_DTO = new ArrayList< AnnualLeaveRuleDTO >();

   /**
    * Workflow
    */
   // 账户设定的工作流 - DTO
   public List< WorkflowModuleDTO > WORKFLOW_MODULE_DTO = new ArrayList< WorkflowModuleDTO >();

   /**
    * Business
    */

   // 构造
   public KANAccountConstants( final String accountId )
   {
      ACCOUNT_ID = accountId;
   }

   // 初始化Options常量
   public void initOptions( final OptionsService optionsService ) throws KANException
   {
      try
      {
         final OptionsVO optionsVO = optionsService.getOptionsVOByAccountId( ACCOUNT_ID );

         if ( optionsVO != null )
         {
            // 获取Date Format格式
            List< MappingVO > mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.language" );

            if ( mappingVOs != null )
            {
               for ( MappingVO mappingVO : mappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( optionsVO.getLanguage() ) )
                  {
                     OPTIONS_LANGUAGE = mappingVO.getMappingTemp();
                     break;
                  }
               }
            }

            // 获取BRANCH架构展现样式
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.branchPrefer" );
            if ( mappingVOs != null )
            {
               for ( MappingVO mappingVO : mappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( optionsVO.getBranchPrefer() ) )
                  {
                     OPTIONS_BRANCH_PREFER = mappingVO.getMappingId();
                     break;
                  }
               }
            }
            if ( "0".equals( OPTIONS_BRANCH_PREFER ) )
            {
               OPTIONS_BRANCH_PREFER = "1";
            }
            // 获取POSITION架构展现样式
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.positionPrefer" );
            if ( mappingVOs != null )
            {
               for ( MappingVO mappingVO : mappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( optionsVO.getPositionPrefer() ) )
                  {
                     OPTIONS_POSITION_PREFER = mappingVO.getMappingId();
                     break;
                  }
               }
            }
            if ( "0".equals( OPTIONS_POSITION_PREFER ) )
            {
               OPTIONS_POSITION_PREFER = "1";
            }
            // 部门是否统计子部门人数
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.isSumSubBranchHC" );
            if ( mappingVOs != null )
            {
               for ( MappingVO mappingVO : mappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( optionsVO.getIsSumSubBranchHC() ) )
                  {
                     OPTIONS_ISSUMSUBBRANCHHC = mappingVO.getMappingId();
                     break;
                  }
               }
            }
            if ( !"1".equals( OPTIONS_ISSUMSUBBRANCHHC ) )
            {
               //不是统计，默认改为不统计
               OPTIONS_ISSUMSUBBRANCHHC = "0";
            }

            // 初始化UseBrowserLanguage参数
            if ( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equalsIgnoreCase( OptionsVO.TRUE ) )
            {
               OPTIONS_USE_BS_LANGUAGE = true;
            }
            else if ( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equalsIgnoreCase( OptionsVO.FALSE ) )
            {
               OPTIONS_USE_BS_LANGUAGE = false;
            }

            // 获取Date Format格式
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.dateformat" );
            // 初始化DateFormat参数
            if ( mappingVOs != null )
            {
               for ( MappingVO mappingVO : mappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( optionsVO.getDateFormat() ) )
                  {
                     OPTIONS_DATE_FORMAT = mappingVO.getMappingTemp();
                     break;
                  }
               }
            }

            // 获取Time Format格式
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.timeformat" );
            // 初始化TimeFormat参数
            if ( mappingVOs != null )
            {
               for ( MappingVO mappingVO : mappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( optionsVO.getTimeFormat() ) )
                  {
                     OPTIONS_TIME_FORMAT = mappingVO.getMappingTemp();
                     break;
                  }
               }
            }

            // 获取Page Style格式
            OPTIONS_PAGE_STYLE = optionsVO.getPageStyle();

            // 获取短信发送网关
            OPTIONS_SMS_CONFIG = optionsVO.getSmsGetway();

            // 初始化订单绑定合同参数
            if ( optionsVO.getOrderBindContract() != null && optionsVO.getOrderBindContract().equalsIgnoreCase( OptionsVO.TRUE ) )
            {
               OPTIONS_ORDER_BIND_CONTRACT = true;
            }
            else if ( optionsVO.getOrderBindContract() != null && optionsVO.getOrderBindContract().equalsIgnoreCase( OptionsVO.FALSE ) )
            {
               OPTIONS_ORDER_BIND_CONTRACT = false;
            }

            // 获取社保申报条件 - 订单
            OPTIONS_SB_GENERATE_CONDITION = optionsVO.getSbGenerateCondition();

            // 获取商保申购条件 - 订单
            OPTIONS_CB_GENERATE_CONDITION = optionsVO.getCbGenerateCondition();

            // 获取结算处理条件 - 订单
            OPTIONS_SETTLEMENT_GENERATE_CONDITION = optionsVO.getSettlementCondition();

            // 获取社保申报条件 - 服务协议
            OPTIONS_SB_GENERATE_CONDITION_SC = optionsVO.getSbGenerateConditionSC();

            // 获取商保申购条件 - 服务协议
            OPTIONS_CB_GENERATE_CONDITION_SC = optionsVO.getCbGenerateConditionSC();

            // 获取结算处理条件 - 服务协议
            OPTIONS_SETTLEMENT_GENERATE_CONDITION_SC = optionsVO.getSettlementConditionSC();

            // 获取精度
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "def.list.detail.accuracy" );
            // 初始化Accuracy参数
            if ( mappingVOs != null )
            {
               for ( MappingVO mappingVO : mappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( optionsVO.getAccuracy() ) )
                  {
                     OPTIONS_ACCURACY = mappingVO.getMappingTemp();
                     break;
                  }
               }
            }

            //月平均天数
            AVG_SALARY_DAYS_PER_MONTH = Double.valueOf( optionsVO.getMonthAvg() == null || optionsVO.getMonthAvg().equals( "" ) ? "21.75" : optionsVO.getMonthAvg() );

            // 获取取舍
            OPTIONS_ROUND = optionsVO.getRound();

            // 初始化logoFile
            initLogoFile( optionsVO.getLogoFile(), optionsVO.getLogoFileSize() );

            // 获取HRSERVICE手机模块权限
            OPTIONS_MOBILE_RIGHTS = optionsVO.getMobileModuleRightIds();

            // 添加客户照片
            OPTIONS_CLIENT_LOGO_FILE.clear();
            OPTIONS_CLIENT_LOGO_FILE.addAll( optionsVO.getClientLogoFiles() );

            // 获取INHOUSE 手机模块权限
            OPTIONS_CLIENT_MOBILE_RIGHTS.clear();
            OPTIONS_CLIENT_MOBILE_RIGHTS.addAll( optionsVO.getClientMobileModuleRightIds() );

            // 获取独立报税
            OPTIONS_INDEPENDENCE_TAX = optionsVO.getIndependenceTax();

            // 加班计算最小单位
            OPTIONS_OT_MIN_UNIT = optionsVO.getOtMinUnit();

            logger.info( "Loading Account - " + ACCOUNT_ID + " Options Confuguration: Load successfully." );
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         throw new KANException( e );
      }
   }

   public String getClientLogoFileByCorpId( final String corpId )
   {
      for ( MappingVO mappingVO : OPTIONS_CLIENT_LOGO_FILE )
      {
         if ( mappingVO.getMappingId().equals( corpId ) )
         {
            return initClientLogoFile( mappingVO.getMappingValue(), mappingVO.getMappingTemp(), corpId );
         }
      }
      // 如果没找到就返回默认
      return OPTIONS_LOGO_FILE;
   }

   public String getClientMobileModuleRightsByCorpId( final String corpId )
   {
      for ( MappingVO mappingVO : OPTIONS_CLIENT_MOBILE_RIGHTS )
      {
         if ( mappingVO.getMappingId().equals( corpId ) )
         {
            return mappingVO.getMappingValue();
         }
      }
      return "";
   }

   public String initClientLogoFile( final String logoFile, final String fileSizeStr, final String corpId )
   {
      String localLogoFileName = null;
      if ( KANUtil.filterEmpty( logoFile ) != null && KANUtil.filterEmpty( fileSizeStr ) != null )
      {
         String subName = "images/logo/";
         String fileName = null;
         int lastIndex = logoFile.lastIndexOf( "/" );
         if ( lastIndex >= 0 )
         {
            //1 获取本地服务器文件名
            fileName = logoFile.substring( lastIndex + 1, logoFile.length() );
            try
            {
               fileName = KANUtil.encodeString( fileName ).replace( "%", "" );
            }
            catch ( KANException e1 )
            {
               e1.printStackTrace();
            }
            localLogoFileName = subName + ACCOUNT_ID + "/" + corpId + "/" + fileName;
            boolean flag = false;
            //2 比较远程服务器文件与本地服务器文件
            String localLogoPath = KANUtil.basePath + "/" + localLogoFileName;
            File localLogoFile = new File( localLogoPath );

            File localLogoFileDir = new File( KANUtil.basePath + "/" + subName + ACCOUNT_ID + "/" + corpId + "/" );
            if ( !localLogoFileDir.exists() )
            {
               localLogoFileDir.mkdirs();
            }
            //2.1 本地存在同名文件名
            if ( localLogoFile.isFile() )
            {
               // 对比文件大小
               long fileSize = 0;
               if ( fileSizeStr != null )
               {
                  fileSize = Long.parseLong( fileSizeStr );
               }
               if ( fileSize != localLogoFile.length() )
               {
                  flag = true;
               }
            }
            else
            {
               flag = true;
            }
            if ( flag )
            {
               try
               {
                  FileOutputStream os = new FileOutputStream( localLogoPath );
                  DownloadFileAction.download( os, logoFile, ACCOUNT_ID );
               }
               catch ( Exception e )
               {
                  logger.info( "文件下载出错！", e );
               }
            }
         }
      }
      return localLogoFileName;
   }

   // 初始化Options常量
   private void initLogoFile( final String logoFile, final String fileSizeStr )
   {
      if ( KANUtil.filterEmpty( logoFile ) != null && KANUtil.filterEmpty( fileSizeStr ) != null )
      {
         String localLogoFileName = null;
         String subName = "images/logo/";
         String fileName = null;
         int lastIndex = logoFile.lastIndexOf( "/" );
         if ( lastIndex >= 0 )
         {
            //1 获取本地服务器文件名
            fileName = logoFile.substring( lastIndex + 1, logoFile.length() );
            try
            {
               fileName = KANUtil.encodeString( fileName ).replace( "%", "" );
            }
            catch ( KANException e1 )
            {
               e1.printStackTrace();
            }
            localLogoFileName = subName + ACCOUNT_ID + "/" + fileName;
            boolean flag = false;
            //2 比较远程服务器文件与本地服务器文件
            String localLogoPath = KANUtil.basePath + "/" + localLogoFileName;
            File localLogoFile = new File( localLogoPath );

            File localLogoFileDir = new File( KANUtil.basePath + "/" + subName + ACCOUNT_ID + "/" );
            if ( !localLogoFileDir.exists() )
            {
               localLogoFileDir.mkdirs();
            }
            //2.1 本地存在同名文件名
            if ( localLogoFile.isFile() )
            {
               // 对比文件大小
               long fileSize = 0;
               if ( fileSizeStr != null )
               {
                  fileSize = Long.parseLong( fileSizeStr );
               }
               if ( fileSize != localLogoFile.length() )
               {
                  flag = true;
               }
            }
            else
            {
               flag = true;
            }
            if ( flag )
            {
               try
               {
                  FileOutputStream os = new FileOutputStream( localLogoPath );
                  DownloadFileAction.download( os, logoFile, ACCOUNT_ID );
               }
               catch ( Exception e )
               {
                  logger.info( "文件下载出错！", e );
                  return;
               }
            }

            //logoFile赋值
            OPTIONS_LOGO_FILE = localLogoFileName;
         }
      }
   }

   // 初始化Email Configuration常量
   public void initEmailConfiguration( final EmailConfigurationService emailConfigurationService ) throws KANException
   {
      try
      {
         final EmailConfigurationVO emailConfigurationVO = emailConfigurationService.getEmailConfigurationVOByAccountId( ACCOUNT_ID );

         if ( emailConfigurationVO != null )
         {
            // 获取邮件相关配置信息
            OPTIONS_MAIL_SHOW_NAME = emailConfigurationVO.getShowName();

            // Decode邮件发送类型
            if ( emailConfigurationVO.getMailType() != null && emailConfigurationVO.getMailType().equals( "1" ) )
            {
               MAIL_TYPE = "KAN";
            }
            else if ( emailConfigurationVO.getMailType() != null && emailConfigurationVO.getMailType().equals( "2" ) )
            {
               MAIL_TYPE = "IND";
            }

            MAIL_SENT_AS = emailConfigurationVO.getSentAs();

            MAIL_ACCOUNT_NAME = emailConfigurationVO.getAccountName();

            MAIL_SMTP_HOST = emailConfigurationVO.getSmtpHost();

            // 端口不为空并且是数字则设置端口
            if ( emailConfigurationVO.getSmtpPort() != null && emailConfigurationVO.getSmtpPort().matches( ( "[0-9]*" ) ) )
            {
               MAIL_SMTP_PORT = emailConfigurationVO.getSmtpPort();
            }

            MAIL_USERNAME = emailConfigurationVO.getUsername();

            MAIL_PASSWORD = emailConfigurationVO.getPassword();

            // Decode邮件邮件验证类型
            if ( emailConfigurationVO.getSmtpAuthType() != null && emailConfigurationVO.getSmtpAuthType().equals( "1" ) )
            {
               MAIL_SMTP_AUTH_TYPE = true;
            }
            else if ( emailConfigurationVO.getSmtpAuthType() != null && emailConfigurationVO.getSmtpAuthType().equals( "2" ) )
            {
               MAIL_SMTP_AUTH_TYPE = false;
            }

            // Decode邮件邮件安全类型
            if ( emailConfigurationVO.getSmtpSecurityType() != null && emailConfigurationVO.getSmtpSecurityType().equals( "0" ) )
            {
               MAIL_SMTP_SECURITY_TYPE = "NULL";
            }
            else if ( emailConfigurationVO.getSmtpSecurityType() != null && emailConfigurationVO.getSmtpSecurityType().equals( "1" ) )
            {
               MAIL_SMTP_SECURITY_TYPE = "SSL";
            }
            else if ( emailConfigurationVO.getSmtpSecurityType() != null && emailConfigurationVO.getSmtpSecurityType().equals( "2" ) )
            {
               MAIL_SMTP_SECURITY_TYPE = "TLS";
            }

            MAIL_POP3_HOST = emailConfigurationVO.getPop3Host();

            // 端口不为空并且是数字则设置端口
            if ( emailConfigurationVO.getPop3Port() != null && emailConfigurationVO.getPop3Port().matches( ( "[0-9]*" ) ) )
            {
               MAIL_POP3_PORT = emailConfigurationVO.getPop3Port();
            }

            logger.info( "Loading Account - " + ACCOUNT_ID + " Email Confuguration: Load successfully." );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化ShareFolder Configuration常量
   public void initShareFolderConfiguration( final ShareFolderConfigurationService shareFolderConfigurationService ) throws KANException
   {
      try
      {
         final ShareFolderConfigurationVO shareFolderConfigurationVO = shareFolderConfigurationService.getShareFolderConfigurationVOByAccountId( ACCOUNT_ID );

         if ( shareFolderConfigurationVO != null )
         {
            SHAREFOLDER_HOST = shareFolderConfigurationVO.getHost();

            // 端口不为空并且是数字则设置端口
            if ( shareFolderConfigurationVO.getPort() != null && shareFolderConfigurationVO.getPort().matches( ( "[0-9]*" ) ) )
            {
               SHAREFOLDER_PORT = shareFolderConfigurationVO.getPort();
            }

            SHAREFOLDER_USERNAME = shareFolderConfigurationVO.getUsername();

            SHAREFOLDER_PASSWORD = shareFolderConfigurationVO.getPassword();

            // 标准化ShareFolder目录字符串
            if ( shareFolderConfigurationVO.getDirectory() != null )
            {
               SHAREFOLDER_DIRECTORY = shareFolderConfigurationVO.getDirectory();
               if ( !shareFolderConfigurationVO.getDirectory().startsWith( "/" ) )
               {
                  SHAREFOLDER_DIRECTORY = "/" + SHAREFOLDER_DIRECTORY;
               }
               if ( !shareFolderConfigurationVO.getDirectory().endsWith( "/" ) )
               {
                  SHAREFOLDER_DIRECTORY = SHAREFOLDER_DIRECTORY + "/";
               }
            }

            logger.info( "Loading Account - " + ACCOUNT_ID + " Share Folder Confuguration: Load successfully." );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Tables常量
   public void initTable( final TableService tableService ) throws KANException
   {
      try
      {
         // Clear First
         TABLE_DTO.clear();

         // 初始化Table DTO
         TABLE_DTO = tableService.getTableDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Table: " + TABLE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Tables常量
   public void initTableReport( final TableService tableService, final String tableId ) throws KANException
   {
      try
      {

         if ( TABLE_DTO != null && TABLE_DTO.size() > 0 && StringUtils.isNotBlank( tableId ) )
         {
            for ( TableDTO tableDTO : TABLE_DTO )
            {
               if ( tableDTO.getTableVO().getTableId().equals( tableId ) )
               {
                  tableDTO.setReportDTOs( tableService.getReportDTOByTableId( ACCOUNT_ID, tableId ) );
               }
            }
         }
         // 初始化Table DTO

         logger.info( "Loading Account - " + ACCOUNT_ID + " Table: " + TABLE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化ColumnGroups常量
   public void initColumnGroup( final ColumnGroupService columnGroupService ) throws KANException
   {
      try
      {
         // Clear First
         COLUMN_GROUP_VO.clear();

         final List< Object > columnGroupVOs = columnGroupService.getColumnGroupVOsByAccountId( ACCOUNT_ID );

         if ( columnGroupVOs != null )
         {
            if ( columnGroupVOs != null && columnGroupVOs.size() > 0 )
            {
               for ( Object object : columnGroupVOs )
               {
                  COLUMN_GROUP_VO.add( ( ColumnGroupVO ) object );
               }
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Column Group: " + COLUMN_GROUP_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Column Options常量
   public void initColumnOptions( final OptionHeaderService optionHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         COLUMN_OPTION_DTO.clear();

         // 装载用户自定义的选项
         COLUMN_OPTION_DTO = optionHeaderService.getOptionDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Column Options: " + COLUMN_OPTION_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // 初始化SearchHeader常量
   public void initSearchHeader( final SearchHeaderService searchHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         SEARCH_DTO.clear();

         // 装载用户自定义的搜索
         SEARCH_DTO = searchHeaderService.getSearchDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Search: " + SEARCH_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // 初始化ListHeader常量
   public void initListHeader( final ListHeaderService listHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         LIST_DTO.clear();

         // 装载用户自定义的搜索
         LIST_DTO = listHeaderService.getListDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " List: " + LIST_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // 初始化MappingHeader常量
   public void initMappingHeader( MappingHeaderService mappingHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         MAPPING_DTO.clear();

         // 装载客户匹配
         MAPPING_DTO = mappingHeaderService.getMappingDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Mapping: " + MAPPING_DTO.size() + " counts" );
      }
      catch ( KANException e )
      {
         throw new KANException( e );
      }
   }

   // 初始化ReportHeader常量
   public void initReportHeader( final ReportHeaderService reportHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         REPORT_DTO.clear();

         // 装载用户自定义的报表
         REPORT_DTO = reportHeaderService.getReportDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Report: " + REPORT_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // 初始化ImportHeader常量
   public void initImportHeader( final ImportHeaderService importHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         IMPORT_DTO.clear();

         // 装载用户自定义的导入
         IMPORT_DTO = importHeaderService.getImportDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Import: " + IMPORT_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // 初始化ManagerHeader常量
   public void initManagerHeader( final ManagerHeaderService managerHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         MANAGER_DTO.clear();

         // 装载用户自定义的页面
         MANAGER_DTO = managerHeaderService.getManagerDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Manager: " + MANAGER_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // 初始化BankTemplateHeader常量
   public void initBankTemplateHeader( final BankTemplateHeaderService bankTemplateHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         BANK_TEMPLATE_DTO.clear();

         // 装载用户自定义的页面
         BANK_TEMPLATE_DTO = bankTemplateHeaderService.getBankTemplateDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " BankTemplate: " + BANK_TEMPLATE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化TaxTemplateHeader常量
   public void initTaxTemplateHeader( final TaxTemplateHeaderService taxTemplateHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         TAX_TEMPLATE_DTO.clear();

         // 装载用户自定义的页面
         TAX_TEMPLATE_DTO = taxTemplateHeaderService.getTaxTemplateDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " TaxTemplate: " + TAX_TEMPLATE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Staff常量
   public void initStaff( final StaffService staffService ) throws KANException
   {
      try
      {
         // Clear First
         STAFF_DTO.clear();
         STAFF_BASEVIEW.clear();

         // 初始化Staff DTO
         STAFF_DTO = staffService.getStaffDTOsByAccountId( ACCOUNT_ID );

         // 初始化 StaffBaseView
         final List< Object > staffBaseViews = staffService.getStaffBaseViewsByAccountId( ACCOUNT_ID );
         if ( staffBaseViews != null )
         {
            // 遍历StaffBaseView List
            StaffBaseView staffBaseView;
            for ( Object staffBaseViewObject : staffBaseViews )
            {
               staffBaseView = ( StaffBaseView ) staffBaseViewObject;
               STAFF_BASEVIEW.add( staffBaseView );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Staff: " + STAFF_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Staff 菜单常量
   public void initStaffMenu( final StaffService staffService, final String reportHeaderId ) throws KANException
   {
      try
      {

         logger.info( "Loading Account - " + ACCOUNT_ID + " Staff menu start time: " + KANUtil.getDay( new Date() ) );

         //初始化staff上下级and同部门的 position
         for ( StaffDTO staffDTO : STAFF_DTO )
         {
            staffService.updateStaffModuleDTO( ACCOUNT_ID, staffDTO.getStaffVO().getStaffId(), reportHeaderId, staffDTO.getModuleDTOs() );
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Staff menu end time:" + KANUtil.getDay( new Date() ) );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   //   /**
   //    * 设置当前staff的所有下级position(包括了代理)
   //    * @param staffDTO
   //    * @param currentPositionId
   //    * @param positionDTOs
   //    * @param isChild
   //    * @author jack.sun
   //    * @date 2014-4-23
   //    
   //   private void initChildPosition( StaffDTO staffDTO, String currentPositionId, List< PositionDTO > positionDTOs, boolean isChild )
   //   {
   //      if ( StringUtils.isBlank( currentPositionId ) || positionDTOs == null )
   //         return;
   //
   //      //是目标节点的子节点
   //      if ( isChild )
   //      {
   //         for ( PositionDTO positionDTO : positionDTOs )
   //         {
   //            staffDTO.getChildPositions().add( positionDTO.getPositionVO().getPositionId() );
   //            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
   //            {
   //               initChildPosition( staffDTO, positionDTO.getPositionVO().getPositionId(), positionDTO.getPositionDTOs(), true );
   //            }
   //         }
   //
   //      }
   //      else
   //      {
   //         //不是目标节点的子节点
   //         for ( PositionDTO positionDTO : positionDTOs )
   //         {
   //            if ( positionDTO.getPositionVO().getPositionId().equals( currentPositionId ) )
   //            {
   //               for ( PositionDTO childPositionDTO : positionDTO.getPositionDTOs() )
   //               {
   //                  staffDTO.getChildPositions().add( childPositionDTO.getPositionVO().getPositionId() );
   //                  if ( childPositionDTO.getPositionDTOs() != null && childPositionDTO.getPositionDTOs().size() > 0 )
   //                  {
   //                     initChildPosition( staffDTO, childPositionDTO.getPositionVO().getPositionId(), childPositionDTO.getPositionDTOs(), true );
   //                  }
   //               }
   //            }
   //            else
   //            {
   //               //存在子节点
   //               if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
   //               {
   //                  initChildPosition( staffDTO, currentPositionId, positionDTO.getPositionDTOs(), false );
   //               }
   //            }
   //         }
   //      }
   //   }
   //
   //   /**
   //    * 设置当前staff的所有上级position（应该包括了代理position）
   //    * @param staffDTO
   //    * @param currentPositionId
   //    * @param positionDTOs
   //    * @param isChild
   //    * @author jack.sun
   //    * @date 2014-4-23
   //    */
   //   private void initParentPosition( StaffDTO staffDTO, String currentPositionId, List< PositionBaseView > positions )
   //   {
   //      if ( positions != null || StringUtils.isNotBlank( currentPositionId ) )
   //      {
   //
   //         //最上面的节点不需要找上级了。
   //         if ( "0".equals( currentPositionId ) )
   //         {
   //            return;
   //         }
   //
   //         for ( PositionBaseView positionBaseView : positions )
   //         {
   //            if ( currentPositionId.equals( positionBaseView.getId() ) )
   //            {
   //               staffDTO.getParentPositions().add( positionBaseView.getParentId() );
   //               initParentPosition( staffDTO, positionBaseView.getParentId(), positions );
   //               break;
   //            }
   //         }
   //      }
   //
   //   }
   //
   //   /**
   //    * @param staffDTO
   //    * @param positionId
   //    * @param branchId
   //    * @param pOSITION_DTO2
   //    * @author jack.sun
   //    * @date 2014-4-23
   //    */
   //   private void initBranchPosition( StaffDTO staffDTO, String branchId, List< PositionDTO > positionDTOs )
   //   {
   //      if ( positionDTOs != null && StringUtils.isNotBlank( branchId ) )
   //      {
   //         for ( PositionDTO positionDTO : positionDTOs )
   //         {
   //            if ( branchId.equals( positionDTO.getPositionVO().getBranchId() ) )
   //            {
   //               staffDTO.getBranchPositions().add( positionDTO.getPositionVO().getPositionId() );
   //            }
   //            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
   //            {
   //               initBranchPosition( staffDTO, branchId, positionDTO.getPositionDTOs() );
   //            }
   //         }
   //      }
   //   }
   //   

   // 初始化Staff常量 - 单个
   public void initStaff( final StaffService staffService, final String staffId ) throws KANException
   {
      try
      {
         if ( STAFF_DTO != null && STAFF_DTO.size() > 0 )
         {
            for ( int i = 0; i < STAFF_DTO.size(); i++ )
            {
               final StaffDTO staffDTO = STAFF_DTO.get( i );

               if ( staffDTO.getStaffVO().getStaffId().trim().equals( staffId ) )
               {
                  StaffDTO tempstaffDTO = staffService.getStaffDTOByStaffId( staffId );

                  STAFF_DTO.set( i, tempstaffDTO );

                  logger.info( "Loading Account - " + ACCOUNT_ID + " Staff Id: " + staffId );

                  return;
               }
            }

            STAFF_DTO.add( staffService.getStaffDTOByStaffId( staffId ) );

            logger.info( "Loading Account - " + ACCOUNT_ID + " Staff Id: " + staffId );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Staff常量删除 - 单个
   public void initStaffForDelete( final String staffId ) throws KANException
   {
      try
      {
         if ( STAFF_DTO != null && STAFF_DTO.size() > 0 )
         {
            for ( int i = 0; i < STAFF_DTO.size(); i++ )
            {
               final StaffDTO staffDTO = STAFF_DTO.get( i );

               if ( staffDTO.getStaffVO().getStaffId().trim().equals( staffId ) )
               {
                  STAFF_DTO.remove( staffDTO );
                  logger.info( "Loading Account - " + ACCOUNT_ID + " Staff Id: " + staffId );
                  return;
               }
            }

            logger.info( "Loading Account - " + ACCOUNT_ID + " Remove Staff Id: " + staffId );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化StaffBaseView常量 - 单个
   public void initStaffBaseView( final StaffService staffService, final String staffId ) throws KANException
   {
      try
      {
         if ( STAFF_BASEVIEW != null && STAFF_BASEVIEW.size() > 0 )
         {
            for ( int i = 0; i < STAFF_BASEVIEW.size(); i++ )
            {
               final StaffBaseView baseView = STAFF_BASEVIEW.get( i );

               if ( baseView.getId().trim().equals( staffId ) )
               {
                  STAFF_BASEVIEW.set( i, staffService.getStaffBaseViewByStaffId( staffId ) );

                  logger.info( "Loading Account - " + ACCOUNT_ID + " Staff Id: " + staffId );

                  return;
               }
            }

            STAFF_BASEVIEW.add( staffService.getStaffBaseViewByStaffId( staffId ) );

            logger.info( "Loading Account - " + ACCOUNT_ID + " Staff Id: " + staffId );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Location常量
   public void initLocation( final LocationService locationService ) throws KANException
   {
      try
      {
         // Clear First
         LOCATION_VO.clear();

         final List< Object > locationVOs = locationService.getLocationVOsByAccountId( ACCOUNT_ID );

         if ( locationVOs != null )
         {
            // 遍历LocationVO List
            LocationVO locationVO;
            for ( Object locationVOObject : locationVOs )
            {
               locationVO = ( LocationVO ) locationVOObject;
               LOCATION_VO.add( locationVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Location: " + LOCATION_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化log module常量
   public void initSystemLogModule( final LogService logService ) throws KANException
   {
      try
      {
         // Clear First
         SYS_LOG_VO.clear();

         final List< Object > logVOs = logService.getLogModules();

         if ( logVOs != null )
         {
            // 遍历LocationVO List
            for ( Object logObject : logVOs )
            {
               SYS_LOG_VO.add( ( LogVO ) logObject );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " log Module: " + SYS_LOG_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化log oper type常量
   public void initSystemLogOperType() throws KANException
   {
      try
      {
         // Clear First
         SYS_LOG_OPER_TYPE.clear();

         Operate[] operates = Operate.values();
         if ( operates != null && operates.length > 0 )
         {
            for ( Operate operate : operates )
            {
               Operate tmpOperate = Operate.decodeIndex( operate.getIndex() );
               final MappingVO mappingVO = new MappingVO( tmpOperate.getIndex() + "", tmpOperate.getName() );
               mappingVO.setMappingTemp( tmpOperate.getNameEN() );
               SYS_LOG_OPER_TYPE.add( mappingVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " log oper type: " + SYS_LOG_OPER_TYPE.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Branch常量
   public void initBranch( final BranchService branchService ) throws KANException
   {
      try
      {
         // Clear First
         BRANCH_VO.clear();

         final List< Object > branchVOs = branchService.getBranchVOsByAccountId( ACCOUNT_ID );

         if ( branchVOs != null )
         {
            // 遍历BranchVO List
            BranchVO branchVO;
            for ( Object branchVOObject : branchVOs )
            {
               branchVO = ( BranchVO ) branchVOObject;
               branchVO.setStaffIdsInBranch( getStaffIdsInBranch( branchVO.getBranchId() ) );
               BRANCH_VO.add( branchVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Branch: " + BRANCH_VO.size() + " counts" );

         BRANCH_DTO.clear();

         BRANCH_DTO = branchService.getBranchDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Branch: " + BRANCH_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 部门员工ID
   public List< String > getStaffIdsInBranch( final String branchId )
   {
      final List< StaffVO > staffVOsInBranch = new ArrayList< StaffVO >();
      // 部门下的职位
      final List< PositionVO > positionVOs = getPositionVOsByBranchId( branchId );
      // 职位里面的staff
      for ( PositionVO positionVO : positionVOs )
      {
         final List< StaffDTO > staffDTOs = getStaffDTOsByPositionId( positionVO.getPositionId() );
         for ( StaffDTO staffDTO : staffDTOs )
         {
            // 这个人所有的关系
            final List< PositionStaffRelationVO > positionStaffRelations = staffDTO.getPositionStaffRelationVOs();
            if ( positionStaffRelations != null && positionStaffRelations.size() > 0 )
            {
               for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelations )
               {
                  // 找到 这个人在这个职位上的关系
                  if ( positionStaffRelationVO.getPositionId().equals( positionVO.getPositionId() ) )
                  {
                     if ( !"2".equals( positionStaffRelationVO.getStaffType() ) )
                     {
                        staffVOsInBranch.add( staffDTO.getStaffVO() );
                     }
                  }
               }
            }
         }
      }
      final List< String > staffIds = new ArrayList< String >();
      for ( StaffVO staffVO : staffVOsInBranch )
      {
         staffIds.add( staffVO.getStaffId() );
      }
      return KANUtil.getDistinctList( staffIds );
   }

   // 初始化Skill常量
   public void initSkill( final SkillService skillService ) throws KANException
   {
      try
      {
         // Clear First
         SKILL_DTO.clear();
         SKILL_BASEVIEW.clear();

         // 初始化Skill DTO
         SKILL_DTO = skillService.getSkillDTOsByAccountId( ACCOUNT_ID );
         // 初始化SkillBaseview
         SKILL_BASEVIEW = skillService.getSkillBaseViewsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Skill: " + SKILL_BASEVIEW.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化EMPLOYEE_POSITION
   public void initEmployeePosition( final com.kan.base.service.inf.management.PositionService positionService ) throws KANException
   {
      try
      {
         // Clear First
         EMPLOYEE_POSITION_DTO.clear();

         // 初始化Position DTO
         EMPLOYEE_POSITION_DTO = positionService.getPositionDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Employee Position: " + EMPLOYEE_POSITION_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Position Grade常量
   public void initEmployeePositionGrade( final com.kan.base.service.inf.management.PositionGradeService positionGradeService ) throws KANException
   {
      try
      {
         // Clear First
         EMPLOYEE_POSITION_GRADE_VO.clear();

         final List< Object > positionGradeVOs = positionGradeService.getPositionGradeVOsByAccountId( ACCOUNT_ID );

         if ( positionGradeVOs != null )
         {
            // 遍历PositionGradeVO List
            com.kan.base.domain.management.PositionGradeVO positionGradeVO;
            for ( Object positionGradeVOObject : positionGradeVOs )
            {
               positionGradeVO = ( com.kan.base.domain.management.PositionGradeVO ) positionGradeVOObject;
               EMPLOYEE_POSITION_GRADE_VO.add( positionGradeVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Position Grade: " + EMPLOYEE_POSITION_GRADE_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Position常量
   public void initPosition( final PositionService positionService ) throws KANException
   {
      try
      {
         List< PositionDTO > POSITION_DTO_TEMP = new ArrayList< PositionDTO >();
         List< PositionBaseView > POSITION_BASEVIEW_TEMP = new ArrayList< PositionBaseView >();
         List< PositionVO > POSITION_VO_TEMP = new ArrayList< PositionVO >();

         // 初始化Position DTO
         POSITION_DTO_TEMP = positionService.getPositionDTOsByAccountId( ACCOUNT_ID );

         // 初始化Position View
         final List< Object > positionBaseViews = positionService.getPositionBaseViewsByAccountId( ACCOUNT_ID );

         if ( positionBaseViews != null )
         {
            // 遍历PositionBaseView List
            PositionBaseView positionBaseView;
            for ( Object positionBaseViewObject : positionBaseViews )
            {
               positionBaseView = ( PositionBaseView ) positionBaseViewObject;
               POSITION_BASEVIEW_TEMP.add( positionBaseView );
            }
         }

         // 初始化Position VO
         final List< Object > positionVOs = positionService.getPositionVOsByAccountId( ACCOUNT_ID );

         if ( positionVOs != null )
         {
            // 遍历PositionVO List
            PositionVO positionVO;
            for ( Object positionVOObject : positionVOs )
            {
               positionVO = ( PositionVO ) positionVOObject;
               POSITION_VO_TEMP.add( positionVO );
            }
         }

         this.POSITION_DTO.clear();
         this.POSITION_BASEVIEW.clear();
         this.POSITION_VO.clear();
         this.POSITION_DTO = POSITION_DTO_TEMP;
         this.POSITION_BASEVIEW = POSITION_BASEVIEW_TEMP;
         this.POSITION_VO = POSITION_VO_TEMP;

         logger.info( "Loading Account - " + ACCOUNT_ID + " Position: " + POSITION_VO_TEMP.size() + " counts" );

         POSITION_DTO_TEMP = null;
         POSITION_BASEVIEW_TEMP = null;
         POSITION_VO_TEMP = null;

      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 添加职位不完全刷新用.导入完成后需要刷新所有职位内存或者隔天生效
    * 导入刷新专用.避免导入过慢.
    * 其他不要调用
    * @param positionService
    * @param positionId
    * @throws KANException
    */
   public void initPosition( final PositionService positionService, final String positionId ) throws KANException
   {
      try
      {
         final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
         if ( positionVO != null )
         {
            this.POSITION_VO.add( positionVO );
            final PositionDTO positionDTO = new PositionDTO();
            final PositionDTO parentPositionDTO = getPositionDTOByPositionId( positionVO.getParentPositionId() );
            parentPositionDTO.getPositionDTOs().add( positionDTO );
            positionDTO.setPositionVO( positionVO );
         }

      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Group常量
   public void initPositionGroup( final GroupService groupService ) throws KANException
   {
      try
      {
         // Clear First
         POSITION_GROUP_DTO.clear();
         POSITION_GROUP_BASEVIEW.clear();
         POSITION_GROUP_VO.clear();

         // 初始化Position Group DTO
         POSITION_GROUP_DTO = groupService.getGroupDTOsByAccountId( ACCOUNT_ID );

         // 初始化Position Group View
         final List< Object > groupBaseViews = groupService.getGroupBaseViewsByAccountId( ACCOUNT_ID );

         if ( groupBaseViews != null )
         {
            // 遍历GroupBaseView List
            GroupBaseView groupBaseView;
            for ( Object groupBaseViewObject : groupBaseViews )
            {
               groupBaseView = ( GroupBaseView ) groupBaseViewObject;
               POSITION_GROUP_BASEVIEW.add( groupBaseView );
            }
         }

         // 初始化Position Group VO
         final List< Object > groupVOs = groupService.getGroupVOsByAccountId( ACCOUNT_ID );

         if ( groupVOs != null )
         {
            // 遍历GroupVO List
            GroupVO groupVO;
            for ( Object groupVOObject : groupVOs )
            {
               groupVO = ( GroupVO ) groupVOObject;
               POSITION_GROUP_VO.add( groupVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Position Group: " + POSITION_GROUP_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Position Grade常量
   public void initPositionGrade( final PositionGradeService positionGradeService ) throws KANException
   {
      try
      {
         // Clear First
         POSITION_GRADE_VO.clear();

         final List< Object > positionGradeVOs = positionGradeService.getPositionGradeVOsByAccountId( ACCOUNT_ID );

         if ( positionGradeVOs != null )
         {
            // 遍历PositionGradeVO List
            PositionGradeVO positionGradeVO;
            for ( Object positionGradeVOObject : positionGradeVOs )
            {
               positionGradeVO = ( PositionGradeVO ) positionGradeVOObject;
               POSITION_GRADE_VO.add( positionGradeVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Position Grade: " + POSITION_GRADE_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Module常量
   public void initModule( final ModuleService moduleService ) throws KANException
   {
      try
      {
         // Clear First
         MODULE_DTO.clear();
         MODULE_VO.clear();

         // 初始化Module DTO
         MODULE_DTO = moduleService.getAccountModuleDTOsByAccountId( ACCOUNT_ID );

         // 初始化Module VO
         final List< Object > moduleVOs = moduleService.getAccountModuleVOsByAccountId( ACCOUNT_ID );

         if ( moduleVOs != null )
         {
            // 遍历ModuleVO List
            ModuleVO moduleVO;
            for ( Object moduleVOObject : moduleVOs )
            {
               moduleVO = ( ModuleVO ) moduleVOObject;
               MODULE_VO.add( moduleVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Module: " + MODULE_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Constant，常量
   public void initConstant( final ConstantService constantService ) throws KANException
   {
      try
      {
         // 先清空CONSTANT_VO对象
         CONSTANT_VO.clear();

         // 初始化ConstantVO
         final List< Object > constantVOs = constantService.getConstantVOsByAccountId( ACCOUNT_ID );
         // 遍历ConstantVO
         if ( constantVOs != null )
         {
            for ( Object object : constantVOs )
            {
               CONSTANT_VO.add( ( ConstantVO ) object );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Constant: " + CONSTANT_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // 初始化WorkFlwoModuleDTO常量
   public void initWorkflow( final WorkflowModuleService workflowModuleService ) throws KANException
   {
      try
      {
         // Clear First
         this.WORKFLOW_MODULE_DTO.clear();

         // 初始化Module DTO
         WORKFLOW_MODULE_DTO = workflowModuleService.getAccountWorkflowDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Workflow Module: " + WORKFLOW_MODULE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Entity常量
   public void initEntity( final EntityService entityService ) throws KANException
   {
      try
      {
         // Clear First
         ENTITY_VO.clear();

         // 初始化EntityVO
         final List< Object > entityVOs = entityService.getEntityVOsByAccountId( ACCOUNT_ID );

         if ( entityVOs != null )
         {
            // 遍历EntityVO List
            for ( Object entityVOObject : entityVOs )
            {
               final EntityVO entityVO = ( EntityVO ) entityVOObject;
               ENTITY_VO.add( entityVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Entity: " + ENTITY_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Contract Type常量
   public void initContractType( final ContractTypeService contractTypeService ) throws KANException
   {
      try
      {
         // Clear First
         CONTRACT_TYPE_VO.clear();

         // 初始化ContractTypeVO
         final List< Object > contractTypeVOs = contractTypeService.getContractTypeVOsByAccountId( ACCOUNT_ID );

         if ( contractTypeVOs != null )
         {
            // 遍历ContractTypeVO List
            for ( Object contractTypeVOObject : contractTypeVOs )
            {
               final ContractTypeVO contractTypeVO = ( ContractTypeVO ) contractTypeVOObject;
               CONTRACT_TYPE_VO.add( contractTypeVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Contract Type: " + CONTRACT_TYPE_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Employee Status常量
   public void initEmployeeStatus( final EmployeeStatusService employeeStatusService ) throws KANException
   {
      try
      {
         // Clear First
         EMPLOYEE_STATUS_VO.clear();

         // 初始化EmployeeStatusVO
         final List< Object > employeeStatusVOs = employeeStatusService.getEmployeeStatusVOsByAccountId( ACCOUNT_ID );

         if ( employeeStatusVOs != null )
         {
            // 遍历EmployeeStatusVO List
            for ( Object employeeStatusVOObject : employeeStatusVOs )
            {
               final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) employeeStatusVOObject;
               EMPLOYEE_STATUS_VO.add( employeeStatusVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Employee Status: " + EMPLOYEE_STATUS_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Education常量
   public void initEducation( final EducationService educationService ) throws KANException
   {
      try
      {
         // Clear First
         EDUCATION_VO.clear();

         // 初始化EducationVO
         final List< Object > educationVOs = educationService.getEducationVOsByAccountId( ACCOUNT_ID );

         if ( educationVOs != null )
         {
            // 遍历EducationVO List
            for ( Object educationVOObject : educationVOs )
            {
               final EducationVO educationVO = ( EducationVO ) educationVOObject;
               EDUCATION_VO.add( educationVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Education: " + EDUCATION_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Language常量
   public void initLanguage( final LanguageService languageService ) throws KANException
   {
      try
      {

         // Clear First
         LANGUAGE_VO.clear();

         // 初始化LanguageVO
         final List< Object > languageVOs = languageService.getLanguageVOsByAccountId( ACCOUNT_ID );

         if ( languageVOs != null )
         {
            // 遍历LanguageVO List
            for ( Object languageVOObject : languageVOs )
            {
               final LanguageVO languageVO = ( LanguageVO ) languageVOObject;
               LANGUAGE_VO.add( languageVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Language: " + LANGUAGE_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化ExchangeRate常量
   public void initExchangeRate( final ExchangeRateService exchangeRateService ) throws KANException
   {
      try
      {

         // Clear First
         EXCHANGE_RATE_VO.clear();

         // 初始化ExchangeRateVOs
         final List< Object > exchangeRateVOs = exchangeRateService.getExchangeRateVOsByAccountId( ACCOUNT_ID );

         if ( exchangeRateVOs != null )
         {
            // 遍历LanguageVO List
            for ( Object exchangeRateVOObject : exchangeRateVOs )
            {
               final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) exchangeRateVOObject;
               EXCHANGE_RATE_VO.add( exchangeRateVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Exchange Rate: " + EXCHANGE_RATE_VO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Item常量
   public void initItem( final ItemService itemService ) throws KANException
   {
      try
      {
         // Clear First
         ITEM_VO.clear();
         // 初始化ItemVO List
         final List< Object > itemVOs = new ArrayList< Object >();

         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            itemVOs.addAll( itemService.getItemVOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }

         itemVOs.addAll( itemService.getItemVOsByAccountId( ACCOUNT_ID ) );

         if ( itemVOs != null )
         {
            // 遍历ItemVO List
            for ( Object itemVOObject : itemVOs )
            {
               final ItemVO itemVO = ( ItemVO ) itemVOObject;
               ITEM_VO.add( itemVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Item: " + ITEM_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Bank常量
   public void initBank( final BankService bankService ) throws KANException
   {
      try
      {
         // Clear First
         BANK_VO.clear();
         // 初始化ItemVO List
         final List< Object > bankVOs = new ArrayList< Object >();

         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            bankVOs.addAll( bankService.getBankVOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }
         bankVOs.addAll( bankService.getBankVOsByAccountId( ACCOUNT_ID ) );

         if ( bankVOs != null )
         {
            // 遍历BankVO List
            for ( Object bankVOObject : bankVOs )
            {
               final BankVO bankVO = ( BankVO ) bankVOObject;
               BANK_VO.add( bankVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Bank: " + BANK_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Tax常量
   public void initTax( final TaxService taxService ) throws KANException
   {
      try
      {
         // Clear First
         TAX_VO.clear();
         // 初始化TaxVO List
         final List< Object > taxVOs = taxService.getTaxVOsByAccountId( ACCOUNT_ID );

         if ( taxVOs != null )
         {
            // 遍历TaxVO List
            for ( Object taxVOObject : taxVOs )
            {
               final TaxVO taxVO = ( TaxVO ) taxVOObject;
               TAX_VO.add( taxVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Tax: " + TAX_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化ItemGroup常量
   public void initItemGroup( final ItemGroupService itemGroupService ) throws KANException
   {
      try
      {
         // Clear First
         ITEM_GROUP_DTO.clear();

         // 初始化ItemGroup DTO
         ITEM_GROUP_DTO = itemGroupService.getItemGroupDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Item Group: " + ITEM_GROUP_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化BusinessType常量
   public void initBusinessType( final BusinessTypeService businessTypeService ) throws KANException
   {
      try
      {
         // Clear First
         BUSINESS_TYPE_VO.clear();

         // 初始化BusinessTypeVO List
         final List< Object > businessTypeVOs = businessTypeService.getBusinessTypeVOsByAccountId( ACCOUNT_ID );

         if ( businessTypeVOs != null )
         {
            // 遍历BusinessTypeVO List
            for ( Object businessTypeVOObject : businessTypeVOs )
            {
               final BusinessTypeVO businessTypeVO = ( BusinessTypeVO ) businessTypeVOObject;
               BUSINESS_TYPE_VO.add( businessTypeVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Business Type: " + BUSINESS_TYPE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化IndustryType常量
   public void initIndustryType( final IndustryTypeService industryTypeService ) throws KANException
   {
      try
      {
         // Clear First
         INDUSTRY_TYPE_VO.clear();
         // 初始化IndustryTypeVO List
         final List< Object > industryTypeVOs = industryTypeService.getIndustryTypeVOsByAccountId( ACCOUNT_ID );

         if ( industryTypeVOs != null )
         {
            // 遍历IndustryTypeVO List
            for ( Object industryTypeVOObject : industryTypeVOs )
            {
               final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) industryTypeVOObject;
               INDUSTRY_TYPE_VO.add( industryTypeVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Industry Type: " + INDUSTRY_TYPE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化BusinessContractTemplate常量
   public void initBusinessContractTemplate( final BusinessContractTemplateService businessContractTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         BUSINESS_CONTRACT_TEMPLATE_VO.clear();

         // 初始化BusinessContractTemplateVO List
         final List< Object > businessContractTemplateVOs = businessContractTemplateService.getBusinessContractTemplateVOsByAccountId( ACCOUNT_ID );

         if ( businessContractTemplateVOs != null )
         {
            // 遍历BusinessContractTemplateVO List
            for ( Object businessContractTemplateVOObject : businessContractTemplateVOs )
            {
               final BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) businessContractTemplateVOObject;
               BUSINESS_CONTRACT_TEMPLATE_VO.add( businessContractTemplateVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Business Contract Template: " + BUSINESS_CONTRACT_TEMPLATE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化LaborContractTemplate常量
   public void initLaborContractTemplate( final LaborContractTemplateService laborContractTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         LABOR_CONTRACT_TEMPLATE_VO.clear();

         // 初始化LaborContractTemplateVO List
         final List< Object > laborContractTemplateVOs = laborContractTemplateService.getLaborContractTemplateVOsByAccountId( ACCOUNT_ID );

         if ( laborContractTemplateVOs != null )
         {
            // 遍历LaborContractTemplateVO List
            for ( Object laborContractTemplateVOObject : laborContractTemplateVOs )
            {
               final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) laborContractTemplateVOObject;
               LABOR_CONTRACT_TEMPLATE_VO.add( laborContractTemplateVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Labor Contract Template: " + LABOR_CONTRACT_TEMPLATE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化LaborContractTemplate常量
   public void initResignTemplate( final ResignTemplateService resignTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         RESIGN_TEMPLATE_VO.clear();

         // 初始化resignTemplateVO List
         final List< Object > resignTemplateVOs = resignTemplateService.getResignTemplateVOsByAccountId( ACCOUNT_ID );

         if ( resignTemplateVOs != null )
         {
            // 遍历resignTemplateVO List
            for ( Object resignTemplateVOObject : resignTemplateVOs )
            {
               final ResignTemplateVO resignTemplateVO = ( ResignTemplateVO ) resignTemplateVOObject;
               RESIGN_TEMPLATE_VO.add( resignTemplateVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Resign Template: " + RESIGN_TEMPLATE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Membership常量
   public void initMembership( final MembershipService membershipService ) throws KANException
   {
      try
      {
         // Clear First
         MEMBERSHIP_VO.clear();
         // 初始化MembershipVO List
         final List< Object > membershipVOs = membershipService.getMembershipVOsByAccountId( ACCOUNT_ID );

         if ( membershipVOs != null && membershipVOs.size() > 0 )
         {
            // 遍历MembershipVO List
            for ( Object membershipVOObject : membershipVOs )
            {
               final MembershipVO membershipVO = ( MembershipVO ) membershipVOObject;
               MEMBERSHIP_VO.add( membershipVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Membership: " + MEMBERSHIP_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化certification常量
   public void initCertification( final CertificationService certificationService ) throws KANException
   {
      try
      {
         // Clear First
         CERTIFICATION_VO.clear();
         // 初始化CertificationVO List

         final List< Object > certificationVOs = certificationService.getCertificationVOsByAccountId( ACCOUNT_ID );

         if ( certificationVOs != null && certificationVOs.size() > 0 )
         {
            // 遍历MembershipVO List
            for ( Object certificationVOObject : certificationVOs )
            {
               final CertificationVO certificationVO = ( CertificationVO ) certificationVOObject;
               CERTIFICATION_VO.add( certificationVO );

            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Certification: " + CERTIFICATION_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化社保方案
   public void initSocialBenefitSolution( final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         SOCIAL_BENEFIT_SOLUTION_DTO.clear();
         // 初始化SOCIAL_BENEFIT_SOLUTION_DTO
         final List< SocialBenefitSolutionDTO > socialBenefitSolutionDTOs = socialBenefitSolutionHeaderService.getSocialBenefitSolutionDTOsByAccountId( ACCOUNT_ID );

         if ( socialBenefitSolutionDTOs != null && socialBenefitSolutionDTOs.size() > 0 )
         {
            SOCIAL_BENEFIT_SOLUTION_DTO.addAll( socialBenefitSolutionDTOs );
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Social Benefit Solution: " + SOCIAL_BENEFIT_SOLUTION_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化商保方案
   public void initCommercialBenefitSolution( final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         COMMERCIAL_BENEFIT_SOLUTION_DTO.clear();

         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            COMMERCIAL_BENEFIT_SOLUTION_DTO.addAll( commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionDTOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }

         COMMERCIAL_BENEFIT_SOLUTION_DTO.addAll( commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionDTOsByAccountId( ACCOUNT_ID ) );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Commercial Benefit Solution: " + COMMERCIAL_BENEFIT_SOLUTION_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化病假工资
   public void initSickLeaveSalary( final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         SICK_LEAVE_SALARY_DTO.clear();

         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            SICK_LEAVE_SALARY_DTO.addAll( sickLeaveSalaryHeaderService.getSickLeaveSalaryDTOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }

         SICK_LEAVE_SALARY_DTO.addAll( sickLeaveSalaryHeaderService.getSickLeaveSalaryDTOsByAccountId( ACCOUNT_ID ) );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Sick Leave Salary: " + SICK_LEAVE_SALARY_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化年假规则
   public void initAnnualLeaveRule( final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         ANNUAL_LEAVE_RULE_DTO.clear();

         ANNUAL_LEAVE_RULE_DTO.addAll( annualLeaveRuleHeaderService.getAnnualLeaveRuleDTOsByAccountId( ACCOUNT_ID ) );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Annual Leave Rule: " + ANNUAL_LEAVE_RULE_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化日历
   public void initCalendar( final CalendarHeaderService calendarHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         CALENDAR_DTO.clear();

         // 初始化CALENDAR_DTO
         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            CALENDAR_DTO.addAll( calendarHeaderService.getCalendarDTOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }

         CALENDAR_DTO.addAll( calendarHeaderService.getCalendarDTOsByAccountId( ACCOUNT_ID ) );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Calendar: " + CALENDAR_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化排班
   public void initShift( final ShiftHeaderService shiftHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         SHIFT_DTO.clear();

         // 初始化SHIFT_DTO
         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            SHIFT_DTO.addAll( shiftHeaderService.getShiftDTOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }

         SHIFT_DTO.addAll( shiftHeaderService.getShiftDTOsByAccountId( ACCOUNT_ID ) );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Shift: " + SHIFT_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化ItemMapping常量
   public void initItemMapping( final ItemMappingService itemMappingService ) throws KANException
   {
      try
      {
         // Clear First
         ITEM_MAPPING_VO.clear();

         // 初始化ItemMappingVO List
         final List< Object > itemMappingVOs = itemMappingService.getItemMappingVOsByAccountId( ACCOUNT_ID );

         if ( itemMappingVOs != null )
         {
            // 遍历ItemMappingVO List
            for ( Object itemMappingVOObject : itemMappingVOs )
            {
               final ItemMappingVO itemMappingVO = ( ItemMappingVO ) itemMappingVOObject;
               ITEM_MAPPING_VO.add( itemMappingVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Item Mapping: " + ITEM_MAPPING_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化消息模板
   public void initMessageTemplate( final MessageTemplateService messageTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         MESSAGE_TEMPLATE_VO.clear();
         // 初始化ItemMappingVO List
         final List< Object > messageTemplateVOs = messageTemplateService.getMessageTemplateVOByAccountId( ACCOUNT_ID );
         if ( messageTemplateVOs != null )
         {
            // 遍历ItemMappingVO List
            for ( Object messageTemplateVOObject : messageTemplateVOs )
            {
               final MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) messageTemplateVOObject;
               MESSAGE_TEMPLATE_VO.add( messageTemplateVO );
            }
         }

         logger.info( "Loading Account - " + ACCOUNT_ID + " Message Template: " + MESSAGE_TEMPLATE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 按照PositionId获取PositionName
   public String getPositionNameByPositionId( final String positionId )
   {
      if ( positionId == null || positionId.trim().equals( "" ) || POSITION_BASEVIEW == null || POSITION_BASEVIEW.size() <= 0 )
      {
         return "";
      }

      for ( PositionBaseView positionBaseView : POSITION_BASEVIEW )
      {
         if ( positionBaseView.getId() != null && positionBaseView.getId().trim().equals( positionId ) )
         {
            return positionBaseView.getName();
         }
      }

      return "";
   }

   // 按照StaffId获取StaffBaseView
   public StaffBaseView getStaffBaseViewByStaffId( final String staffId )
   {
      // 遍历StaffBaseView List
      for ( StaffBaseView staffBaseView : STAFF_BASEVIEW )
      {
         if ( staffBaseView != null && staffBaseView.getId() != null && staffBaseView.getId().trim().equals( staffId ) )
         {
            return staffBaseView;
         }
      }

      return null;
   }

   // 按照StaffId获取StaffName
   public String getStaffNameByStaffIdAndLanguage( final String staffId, final String language )
   {
      if ( staffId == null || staffId.trim().equals( "" ) || STAFF_BASEVIEW == null || STAFF_BASEVIEW.size() <= 0 )
      {
         return "";
      }

      for ( StaffBaseView staffBaseView : STAFF_BASEVIEW )
      {
         if ( staffBaseView != null && staffBaseView.getId() != null && staffBaseView.getId().trim().equals( staffId ) )
         {
            return "zh".equalsIgnoreCase( language ) ? staffBaseView.getNameZH() : staffBaseView.getNameEN();
         }
      }

      return "";
   }

   // 按照StaffId获取StaffName
   public String getStaffNameByStaffId( final String staffId )
   {
      return getStaffNameByStaffId( staffId, false );
   }

   // 按照StaffId获取StaffName
   // iClick要取自定义字段“jiancheng”
   public String getStaffNameByStaffId( final String staffId, final boolean jiancheng )
   {
      if ( staffId == null || staffId.trim().equals( "" ) || STAFF_BASEVIEW == null || STAFF_BASEVIEW.size() <= 0 )
      {
         return "";
      }

      for ( StaffBaseView staffBaseView : STAFF_BASEVIEW )
      {
         if ( staffBaseView != null && staffBaseView.getId() != null && staffBaseView.getId().trim().equals( staffId ) )
         {
            if ( jiancheng )
            {
               if ( KANUtil.filterEmpty( staffBaseView.getRemark1() ) != null )
                  return JSONObject.fromObject( staffBaseView.getRemark1() ).getString( "jiancheng" );
               else
                  return "";

            }
            else
            {
               return staffBaseView.getName();
            }
         }
      }

      return "";
   }

   // 按照UserId获取StaffName
   public String getStaffNameByUserId( final String userId )
   {
      if ( userId == null || userId.trim().equals( "" ) || STAFF_BASEVIEW == null || STAFF_BASEVIEW.size() <= 0 )
      {
         return "";
      }

      for ( StaffBaseView staffBaseView : STAFF_BASEVIEW )
      {
         if ( staffBaseView.getUserId() != null && staffBaseView.getUserId().trim().equals( userId ) )
         {
            return staffBaseView.getName();
         }
      }

      return "";
   }

   // 获取Country 国家
   public List< MappingVO > getCountries( final String localeLanguage )
   {
      return getCountries( localeLanguage, null );
   }

   // 获取Country 国家
   public List< MappingVO > getCountries( final String localeLanguage, final String corpId )
   {
      return KANConstants.LOCATION_DTO.getCountries( localeLanguage );
   }

   // 获取Province
   public List< MappingVO > getProvinces( final String localeLanguage )
   {
      return getProvinces( localeLanguage, null );
   }

   // 获取Province
   public List< MappingVO > getProvinces( final String localeLanguage, final String corpId )
   {
      return KANConstants.LOCATION_DTO.getProvinces( localeLanguage );
   }

   // 获取LocationVO
   public LocationVO getLocationVOByLocationId( final String locationId )
   {
      // 遍历Location List
      for ( LocationVO locationVO : LOCATION_VO )
      {
         if ( locationId != null && locationVO != null && locationVO.getLocationId() != null && locationId.equals( locationVO.getLocationId() ) )
         {
            return locationVO;
         }
      }

      return null;
   }

   // 获取Location
   public List< MappingVO > getLocations( final String localeLanguage )
   {
      return getLocations( localeLanguage, null );
   }

   // 获取Location
   public List< MappingVO > getLocations( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      List< MappingVO > locations = new ArrayList< MappingVO >();

      // 遍历Location List
      for ( LocationVO locationVO : LOCATION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( locationVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( locationVO.getCorpId() ) != null && locationVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( locationVO.getLocationId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( locationVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( locationVO.getNameEN() );
            }

            locations.add( mappingVO );
         }
      }

      return locations;
   }

   public LocationVO getLocationByLocationId( final String locationId )
   {

      // 遍历Location List
      for ( LocationVO locationVO : LOCATION_VO )
      {
         if ( locationId.equals( locationVO.getLocationId() ) )
         {
            return locationVO;
         }
      }

      return null;
   }

   public List< MappingVO > getSystemLogModule( final String localeLanguage )
   {
      return getSystemLogModule( null, null );
   }

   // 获取Location
   public List< MappingVO > getSystemLogModule( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      List< MappingVO > logVOs = new ArrayList< MappingVO >();

      // 遍历Location List
      for ( LogVO vo : SYS_LOG_VO )
      {
         // 初始化MappingVO对象
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( vo.getModule() );
         final String[] strs = vo.getModule().split( "\\." );
         mappingVO.setMappingValue( strs[ strs.length - 1 ] );
         logVOs.add( mappingVO );
      }

      return logVOs;
   }

   public List< MappingVO > getSystemLogOperType( final String localeLanguage )
   {
      return getSystemLogOperType( localeLanguage, null );
   }

   // 获取Location
   public List< MappingVO > getSystemLogOperType( final String localeLanguage, final String corpId )
   {
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      for ( MappingVO mappingVO : SYS_LOG_OPER_TYPE )
      {
         MappingVO tmpMappingVO = new MappingVO();
         tmpMappingVO.setMappingId( mappingVO.getMappingId() );
         if ( "zh".equalsIgnoreCase( localeLanguage ) )
         {
            tmpMappingVO.setMappingValue( mappingVO.getMappingValue() );
         }
         else
         {
            tmpMappingVO.setMappingValue( mappingVO.getMappingTemp() );
         }
         mappingVOs.add( tmpMappingVO );
      }
      return mappingVOs;
   }

   // 获取Branch - By parentId
   public List< MappingVO > getBranchsByParentBranchId( final String parentBranchId )
   {
      final List< MappingVO > branchs = new ArrayList< MappingVO >();
      return getBranchsByParentBranchId( branchs, BRANCH_DTO, parentBranchId );
   }

   // 递归获取Branch - By parentId
   private List< MappingVO > getBranchsByParentBranchId( final List< MappingVO > branchs, final List< BranchDTO > branchDTOs, final String parentBranchId )
   {
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         for ( BranchDTO tempBranchDTO : branchDTOs )
         {
            if ( tempBranchDTO != null && tempBranchDTO.getBranchVO() != null && tempBranchDTO.getBranchVO().getParentBranchId().equals( parentBranchId ) )
            {
               branchs.add( new MappingVO( tempBranchDTO.getBranchVO().getBranchId(), tempBranchDTO.getBranchVO().getNameZH(), tempBranchDTO.getBranchVO().getNameEN() ) );
            }

            if ( tempBranchDTO.getBranchDTOs() != null && tempBranchDTO.getBranchDTOs().size() > 0 )
            {
               getBranchsByParentBranchId( branchs, tempBranchDTO.getBranchDTOs(), parentBranchId );
            }
         }
      }

      return branchs;
   }

   // 获取Branch
   public List< MappingVO > getBranchs( final String localeLanguage )
   {
      return getBranchs( localeLanguage, null );
   }

   // 获取Branch
   public List< MappingVO > getBranchs( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      List< MappingVO > branchs = new ArrayList< MappingVO >();

      /*// 遍历Branch List
      for ( BranchVO branchVO : BRANCH_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchVO.getCorpId() ) != null && branchVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( branchVO.getBranchId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( branchVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( branchVO.getNameEN() );
            }

            branchs.add( mappingVO );
         }

      }*/
      refreshBranchDTO( BRANCH_DTO, 0, corpId, branchs, localeLanguage );
      return branchs;
   }

   private void refreshBranchDTO( final List< BranchDTO > branchDTOs, final int level, final String corpId, final List< MappingVO > branchs, final String localeLanguage )
   {
      int index = 0;
      for ( BranchDTO branchDTO : branchDTOs )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) != null && branchDTO.getBranchVO().getCorpId().equals( corpId ) ) )
         {
            String singleOption = "";
            for ( int i = 0; i <= level; i++ )
            {
               if ( level == 0 )
               {
                  singleOption += "├─";
               }
               else
               {
                  if ( i == 0 )
                  {
                     singleOption += "&nbsp;│";
                  }
                  else if ( i == 1 )
                  {
                     singleOption += "├─";
                  }
                  else if ( i < level )
                  {
                     singleOption += "──";
                  }
                  else if ( i == level )
                  {
                     singleOption += ( index + 1 == branchDTOs.size() ? "└─" : "├─" );
                  }
               }
            }
            // 是否被选中
            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               branchs.add( new MappingVO( branchDTO.getBranchVO().getBranchId(), singleOption + branchDTO.getBranchVO().getNameZH(), branchDTO.getBranchVO().getNameZH() ) );
            }
            else
            {
               branchs.add( new MappingVO( branchDTO.getBranchVO().getBranchId(), singleOption + branchDTO.getBranchVO().getNameEN(), branchDTO.getBranchVO().getNameEN() ) );
            }
            if ( branchDTO.getBranchDTOs() != null && branchDTO.getBranchDTOs().size() > 0 )
            {
               refreshBranchDTO( branchDTO.getBranchDTOs(), level + 1, corpId, branchs, localeLanguage );
            }
         }
         index++;
      }
   }

   // 根据branchId获取BranchVO
   public List< MappingVO > getBUFunction()
   {
      // 初始化返回值对象
      List< MappingVO > branchs = new ArrayList< MappingVO >();

      if ( BRANCH_VO != null && BRANCH_VO.size() > 0 )
      {
         for ( BranchVO branchVO : BRANCH_VO )
         {
            if ( KANUtil.filterEmpty( branchVO.getBranchId() ) != null && branchVO.getParentBranchId().trim().equals( "0" ) )
            {
               branchs.add( new MappingVO( branchVO.getBranchId(), branchVO.getNameEN() ) );
            }
         }
      }

      return branchs;
   }

   // 根据name找到BUFunction的ID
   public String getBUFunctionIdByBranchName( final String name )
   {
      List< MappingVO > buFunctions = getBUFunction();
      if ( buFunctions != null && buFunctions.size() > 0 )
      {
         for ( MappingVO bu : buFunctions )
         {
            if ( KANUtil.filterEmpty( name ) != null && name.trim().equals( bu.getMappingValue() ) )
            {
               return bu.getMappingId();
            }
         }
      }

      return null;
   }

   // 根据branchId获取BranchVO
   public BranchVO getBranchVOByBranchId( final String branchId )
   {
      if ( BRANCH_VO != null && BRANCH_VO.size() > 0 )
      {
         for ( BranchVO branchVO : BRANCH_VO )
         {
            if ( KANUtil.filterEmpty( branchId ) != null && KANUtil.filterEmpty( branchVO.getBranchId() ) != null && branchVO.getBranchId().trim().equals( branchId ) )
            {
               return branchVO;
            }
         }
      }

      return null;
   }

   public BranchDTO getBranchDTOByBranchId( final String branchId )
   {
      return getBranchDTOByBranchId( BRANCH_DTO, branchId );
   }

   public BranchDTO getBranchDTOByBranchId( final List< BranchDTO > branchDTOs, final String branchId )
   {
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         for ( BranchDTO branchDTO : branchDTOs )
         {
            if ( KANUtil.filterEmpty( branchId ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getBranchId() ) != null
                  && branchDTO.getBranchVO().getBranchId().trim().equals( branchId ) )
            {
               return branchDTO;
            }
            if ( branchDTO != null && branchDTO.getBranchDTOs().size() > 0 )
            {
               branchDTO = getBranchDTOByBranchId( branchDTO.getBranchDTOs(), branchId );
               if ( branchDTO != null )
               {
                  return branchDTO;
               }
            }
         }
      }
      return null;
   }

   public List< BranchDTO > getBranchDTOsByCorpId( final String corpId )
   {
      final List< BranchDTO > branchDTOs = new ArrayList< BranchDTO >();
      for ( BranchDTO branchDTO : BRANCH_DTO )
      {
         // 如果是INhouse
         if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) == null )
         {
            continue;
         }
         // 如果是HRService
         if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) != null )
         {
            continue;
         }
         branchDTOs.add( branchDTO );
      }
      return branchDTOs;
   }

   // 根据职位，获取部门，父部门信息
   public List< BranchVO > getParentBranchVOsByBranchId( final String branchId )
   {
      final List< BranchVO > branchVOs = new ArrayList< BranchVO >();
      final BranchVO branchVO = getBranchVOByBranchId( branchId );
      if ( branchVO != null )
      {
         branchVOs.add( branchVO );
      }
      if ( branchVO != null && KANUtil.filterEmpty( branchVO.getParentBranchId() ) != null )
      {
         branchVOs.addAll( getParentBranchVOsByBranchId( branchVO.getParentBranchId() ) );
      }
      return branchVOs;
   }

   // 根据职位获取父职位
   public List< PositionVO > getParentPositionVOByPositionId( final String positionId )
   {
      final List< PositionVO > positionVOs = new ArrayList< PositionVO >();
      final PositionVO positionVO = getPositionVOByPositionId( positionId );
      if ( positionVO != null )
      {
         positionVOs.add( positionVO );
      }
      if ( positionVO != null && KANUtil.filterEmpty( positionVO.getParentPositionId() ) != null )
      {
         positionVOs.addAll( getParentPositionVOByPositionId( positionVO.getParentPositionId() ) );
      }
      return positionVOs;

   }

   // 获取Position Group - MappingVO
   public List< MappingVO > getPositionGroups( final String localeLanguage )
   {
      // 初始化返回值对象
      List< MappingVO > positionGroups = new ArrayList< MappingVO >();

      // 遍历Group List
      for ( GroupVO groupVO : POSITION_GROUP_VO )
      {
         // 初始化MappingVO对象
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( groupVO.getGroupId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( groupVO.getNameZH() );
         }
         else
         {
            mappingVO.setMappingValue( groupVO.getNameEN() );
         }

         positionGroups.add( mappingVO );
      }

      return positionGroups;
   }

   // 获取Position Group - MappingVO
   public List< MappingVO > getPositionGroups( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      List< MappingVO > positionGroups = new ArrayList< MappingVO >();

      // 遍历Group List
      for ( GroupVO groupVO : POSITION_GROUP_VO )
      {
         if ( KANUtil.filterEmpty( corpId ) == null || ( KANUtil.filterEmpty( corpId ) != null && groupVO.getCorpId() != null && groupVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( groupVO.getGroupId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( groupVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( groupVO.getNameEN() );
            }

            positionGroups.add( mappingVO );
         }
      }

      return positionGroups;
   }

   // 获取Position Group
   public List< GroupVO > getPositionGroupVOs()
   {
      return getPositionGroupVOs( null );
   }

   // 获取Position Group
   public List< GroupVO > getPositionGroupVOs( final String corpId )
   {
      List< GroupVO > groupVOs = new ArrayList< GroupVO >();
      for ( GroupVO groupVO : POSITION_GROUP_VO )
      {
         if ( KANUtil.filterEmpty( corpId ) != null )
         {
            if ( corpId.equals( groupVO.getCorpId() ) )
            {
               groupVOs.add( groupVO );
            }
         }
         else
         {
            if ( KANUtil.filterEmpty( groupVO.getCorpId() ) == null )
            {
               groupVOs.add( groupVO );
            }
         }

      }
      return groupVOs;
   }

   // 按照PositionGradeId获取PositionGradeVO
   public PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGradeId )
   {
      // 遍历PositionGradeVO List
      for ( PositionGradeVO positionGradeVO : POSITION_GRADE_VO )
      {
         if ( positionGradeVO.getPositionGradeId() != null && positionGradeVO.getPositionGradeId().trim().equals( positionGradeId ) )
         {
            return positionGradeVO;
         }
      }

      return null;
   }

   // 获取Position Grade
   public List< MappingVO > getPositionGrades( final String localeLanguage )
   {
      return getPositionGrades( localeLanguage, null );
   }

   // 获取Position Grade
   public List< MappingVO > getPositionGrades( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > positionGrades = new ArrayList< MappingVO >();

      // 遍历Position Grade List
      for ( PositionGradeVO positionGradeVO : POSITION_GRADE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ).equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( positionGradeVO.getPositionGradeId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( positionGradeVO.getGradeNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( positionGradeVO.getGradeNameEN() );
            }
            mappingVO.setMappingTemp( positionGradeVO.getWeight() );
            positionGrades.add( mappingVO );
         }
      }

      return positionGrades;
   }

   public PositionGradeVO getPositionGradeVOByName( final String gradeName, final String corpId )
   {
      PositionGradeVO targetVO = null;
      for ( PositionGradeVO positionGradeVO : POSITION_GRADE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ).equals( corpId ) ) )
         {
            if ( gradeName.equalsIgnoreCase( positionGradeVO.getGradeNameZH() ) || gradeName.equalsIgnoreCase( positionGradeVO.getGradeNameEN() ) )
            {
               targetVO = positionGradeVO;
               break;
            }
         }
      }
      return targetVO;
   }

   /**
    * 用于导入.
    * @param gradeName
    * @param corpId
    * @return
    */
   public PositionGradeVO getPositionGradeVOByNameSplit( final String gradeName, final String corpId )
   {
      PositionGradeVO targetVO = null;
      if ( KANUtil.filterEmpty( gradeName ) != null )
      {
         for ( PositionGradeVO positionGradeVO : POSITION_GRADE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ).equals( corpId ) ) )
            {
               String gradeNameZH = positionGradeVO.getGradeNameZH();
               String gradeNameEN = positionGradeVO.getGradeNameEN();
               String nameZH = "";
               String nameEN = "";
               if ( KANUtil.filterEmpty( gradeNameZH ) != null )
               {
                  nameZH = gradeNameZH.split( " " )[ 0 ];
               }
               if ( KANUtil.filterEmpty( gradeNameEN ) != null )
               {
                  nameEN = gradeNameEN.split( " " )[ 0 ];
               }
               if ( gradeName.equalsIgnoreCase( nameZH ) || gradeName.equalsIgnoreCase( nameEN ) )
               {
                  targetVO = positionGradeVO;
                  break;
               }
            }
         }
      }
      return targetVO;
   }

   // 按照PositionGradeId获取PositionGradeVO
   public com.kan.base.domain.management.PositionGradeVO getEmployeePositionGradeVOByPositionGradeId( final String positionGradeId )
   {
      // 遍历PositionGradeVO List
      for ( com.kan.base.domain.management.PositionGradeVO positionGradeVO : EMPLOYEE_POSITION_GRADE_VO )
      {
         if ( positionGradeVO.getPositionGradeId() != null && positionGradeVO.getPositionGradeId().trim().equals( positionGradeId ) )
         {
            return positionGradeVO;
         }
      }

      return null;
   }

   // 获取Position Grade
   public List< MappingVO > getEmployeePositionGrades( final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > positionGrades = new ArrayList< MappingVO >();

      // 遍历Position Grade List
      for ( com.kan.base.domain.management.PositionGradeVO positionGradeVO : EMPLOYEE_POSITION_GRADE_VO )
      {
         // 初始化MappingVO对象
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( positionGradeVO.getPositionGradeId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( positionGradeVO.getGradeNameZH() );
         }
         else
         {
            mappingVO.setMappingValue( positionGradeVO.getGradeNameEN() );
         }

         positionGrades.add( mappingVO );
      }

      return positionGrades;
   }

   // 获取Entity
   public List< MappingVO > getEntities( final String localeLanguage )
   {
      return getEntities( localeLanguage, null );
   }

   // 获取Entity
   public List< MappingVO > getEntities( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > entities = new ArrayList< MappingVO >();

      // 遍历EntityVO List
      for ( EntityVO entityVO : ENTITY_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( entityVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( entityVO.getCorpId() ) != null && entityVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( entityVO.getEntityId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( entityVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( entityVO.getNameEN() );
            }

            entities.add( mappingVO );
         }
      }

      return entities;
   }

   // 获取主ImportHeaders
   public List< MappingVO > getImportHeaders( final String localeLanguage )
   {
      return getImportHeaders( localeLanguage, null );
   }

   // 获取主ImportHeaders
   public List< MappingVO > getImportHeaders( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > importHeaders = new ArrayList< MappingVO >();

      // 遍历EntityVO List
      for ( ImportDTO importDTO : IMPORT_DTO )
      {
         ImportHeaderVO importHeaderVO = importDTO.getImportHeaderVO();
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( importHeaderVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( importHeaderVO.getCorpId() ) != null && importHeaderVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( importHeaderVO.getImportHeaderId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( importHeaderVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( importHeaderVO.getNameEN() );
            }

            importHeaders.add( mappingVO );
         }
      }

      return importHeaders;
   }

   public List< ImportHeaderVO > getImportHeaderVOs( final String localeLanguage )
   {
      return getImportHeaderVOs( localeLanguage, null );
   }

   // 获取主ImportHeaders
   public List< ImportHeaderVO > getImportHeaderVOs( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< ImportHeaderVO > importHeaders = new ArrayList< ImportHeaderVO >();

      // 遍历EntityVO List
      for ( ImportDTO importDTO : IMPORT_DTO )
      {
         ImportHeaderVO importHeaderVO = importDTO.getImportHeaderVO();
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( importHeaderVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( importHeaderVO.getCorpId() ) != null && importHeaderVO.getCorpId().equals( corpId ) ) )
         {
            importHeaders.add( importHeaderVO );
         }
      }

      return importHeaders;
   }

   public List< MappingVO > getReportHeaders( final String localeLanguage )
   {
      return getReportHeaders( localeLanguage, null );
   }

   // 获取主ReportHeaders
   public List< MappingVO > getReportHeaders( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > reportHeaders = new ArrayList< MappingVO >();

      // 遍历
      for ( ReportDTO reportDTO : REPORT_DTO )
      {
         if ( reportDTO != null && reportDTO.getReportHeaderVO() != null && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getReportHeaderId() ) != null )
         {
            // 如果是hr service
            if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getCorpId() ) != null )
            {
               continue;
            }
            // 如果是inhouse
            if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getCorpId() ) != null )
            {
               continue;
            }
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( reportDTO.getReportHeaderVO().getReportHeaderId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( reportDTO.getReportHeaderVO().getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( reportDTO.getReportHeaderVO().getNameEN() );
            }

            reportHeaders.add( mappingVO );
         }
      }

      return reportHeaders;
   }

   // 获取EntityVO
   public EntityVO getEntityVOByEntityId( final String entityId )
   {
      // 遍历EntityVO List
      for ( EntityVO entityVO : ENTITY_VO )
      {
         if ( entityVO.getEntityId() != null && entityVO.getEntityId().trim().equals( entityId ) )
         {
            return entityVO;
         }
      }

      return null;
   }

   // 获取EntityVO
   public EntityVO getEntityVOByEntityName( final String entityName, final String corpId )
   {
      // 遍历EntityVO List
      for ( EntityVO entityVO : ENTITY_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( entityVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( entityVO.getCorpId() ) != null && KANUtil.filterEmpty( entityVO.getCorpId() ).equals( corpId ) ) )
         {
            if ( ( KANUtil.filterEmpty( entityVO.getNameZH() ) != null && KANUtil.filterEmpty( entityVO.getNameZH() ).trim().equals( entityName ) )
                  || ( KANUtil.filterEmpty( entityVO.getNameEN() ) != null && KANUtil.filterEmpty( entityVO.getNameEN() ).trim().equals( entityName ) ) )
            {
               return entityVO;
            }
         }
      }

      return null;
   }

   // 获取ContractType
   public List< MappingVO > getContractTypes( final String localeLanguage )
   {
      return getContractTypes( localeLanguage, null );
   }

   // 获取ContractType
   public List< MappingVO > getContractTypes( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > contractTypes = new ArrayList< MappingVO >();

      // 遍历ContractTypeVO List
      for ( ContractTypeVO contractTypeVO : CONTRACT_TYPE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( contractTypeVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( contractTypeVO.getCorpId() ) != null && contractTypeVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( contractTypeVO.getTypeId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( contractTypeVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( contractTypeVO.getNameEN() );
            }

            contractTypes.add( mappingVO );
         }
      }

      return contractTypes;
   }

   // 获取EmployeeStatus
   public List< MappingVO > getEmployeeStatuses( final String localeLanguage )
   {
      return getEmployeeStatuses( localeLanguage, null );
   }

   // 获取EmployeeStatus
   public List< MappingVO > getEmployeeStatuses( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > employeeStatuses = new ArrayList< MappingVO >();

      // 遍历EmployeeStatusVO List
      for ( EmployeeStatusVO employeeStatusVO : EMPLOYEE_STATUS_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( employeeStatusVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( employeeStatusVO.getCorpId() ) != null && employeeStatusVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( employeeStatusVO.getEmployeeStatusId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( employeeStatusVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( employeeStatusVO.getNameEN() );
            }

            employeeStatuses.add( mappingVO );
         }
      }

      return employeeStatuses;
   }

   // 获取Education
   public List< MappingVO > getEducations( final String localeLanguage )
   {
      return getEducations( localeLanguage, null );
   }

   // 获取Education
   public List< MappingVO > getEducations( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > educations = new ArrayList< MappingVO >();

      // 遍历EducationVO List
      for ( EducationVO educationVO : EDUCATION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( educationVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( educationVO.getCorpId() ) != null && educationVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( educationVO.getEducationId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( educationVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( educationVO.getNameEN() );
            }

            educations.add( mappingVO );
         }
      }

      return educations;
   }

   // 获取Language
   public List< MappingVO > getLanguages( final String localeLanguage )
   {
      return getLanguages( localeLanguage, null );
   }

   // 获取Language
   public List< MappingVO > getLanguages( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > languages = new ArrayList< MappingVO >();

      // 遍历LanguageVO List
      for ( LanguageVO languageVO : LANGUAGE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( languageVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( languageVO.getCorpId() ) != null && languageVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( languageVO.getLanguageId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( languageVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( languageVO.getNameEN() );
            }

            languages.add( mappingVO );
         }
      }

      return languages;
   }

   // 获取ExchangeRate
   public ExchangeRateVO getExchangeRateVOByCurrencyCode( final String corpId, final String currencyCode )
   {
      // 遍历ExchangeRateVO List
      for ( ExchangeRateVO exchangeRateVO : EXCHANGE_RATE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( exchangeRateVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( exchangeRateVO.getCorpId() ) != null && exchangeRateVO.getCorpId().equals( corpId ) ) )
         {
            if ( exchangeRateVO.getCurrencyCode().equalsIgnoreCase( currencyCode ) )
               return exchangeRateVO;
         }
      }

      return null;
   }

   // 获得所有Items
   public List< MappingVO > getItems( final String localeLanguage )
   {
      return getItems( localeLanguage, null );
   }

   // 获得所有Items
   public List< MappingVO > getItems( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > items = new ArrayList< MappingVO >();

      // 遍历Item Map
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( itemVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && ( ( KANUtil.filterEmpty( itemVO.getCorpId() ) != null && itemVO.getCorpId().equals( corpId ) ) || itemVO.getAccountId().equals( "1" ) ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( itemVO.getItemId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( itemVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( itemVO.getNameEN() );
               }

               items.add( mappingVO );
            }
         }
      }
      return items;
   }

   // 获得所有Items
   public List< ItemVO > getItemVOsByCorpId( final String corpId )
   {
      // 初始化返回值对象
      final List< ItemVO > items = new ArrayList< ItemVO >();

      // 遍历Item Map
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( itemVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && ( ( KANUtil.filterEmpty( itemVO.getCorpId() ) != null && itemVO.getCorpId().equals( corpId ) ) || itemVO.getAccountId().equals( "1" ) ) ) )
            {
               items.add( itemVO );
            }
         }
      }
      return items;
   }

   // 获得ItemBaseView（type=0所有科目；type=1不包含社保，结算调整使用）
   public List< ItemVO > getItemVOsByType( final String type )
   {
      // 初始化返回值对象
      final List< ItemVO > itemVOs = new ArrayList< ItemVO >();

      // 遍历Item Map
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( KANUtil.filterEmpty( type ) != null && KANUtil.filterEmpty( type ).equals( "0" ) )
            {
               itemVOs.add( itemVO );
            }
            else if ( KANUtil.filterEmpty( type ) != null && KANUtil.filterEmpty( type ).equals( "1" ) && !itemVO.getItemType().equals( "7" ) )
            {
               itemVOs.add( itemVO );
            }
         }
      }

      return itemVOs;
   }

   // 获得所有Taxes
   public List< MappingVO > getTaxes( final String localeLanguage )
   {
      return getTaxes( localeLanguage, null );
   }

   // 获得所有Taxes
   public List< MappingVO > getTaxes( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > taxs = new ArrayList< MappingVO >();

      // 遍历Tax Map
      if ( TAX_VO != null && TAX_VO.size() > 0 )
      {
         for ( TaxVO taxVO : TAX_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( taxVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( taxVO.getCorpId() ) != null && taxVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( taxVO.getTaxId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( taxVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( taxVO.getNameEN() );
               }

               taxs.add( mappingVO );
            }
         }
      }
      return taxs;
   }

   // 根据itemId获取Item MappingVO形式
   public MappingVO getItemByItemId( final String itemId, final String localeLanguage )
   {
      // 初始化返回值对象
      final MappingVO mappingVO = new MappingVO();

      // 遍历Item Map
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( KANUtil.filterEmpty( itemVO.getItemId() ) != null && itemVO.getItemId().equals( itemId ) )
            {
               mappingVO.setMappingId( itemVO.getItemId() );
               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( itemVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( itemVO.getNameEN() );
               }

               break;
            }
         }
      }

      return mappingVO;
   }

   // 根据类型获得科目列表 （工资、补助、奖金、加班、报销、休假、社保、商保、服务费、风险、第三方成本、其他）
   public List< MappingVO > getItemsByType( final String type, final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > items = new ArrayList< MappingVO >();
      // 遍历Item Map
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {

            // 如果是“服务费”添加“营业税”
            if ( "141".equals( itemVO.getItemId() ) && "9".equals( type ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( itemVO.getItemId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( itemVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( itemVO.getNameEN() );
               }
               items.add( mappingVO );
            }

            if ( itemVO.getItemType() != null && itemVO.getItemType().trim().equals( type ) )
            {
               if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( itemVO.getCorpId() ) == null )
                     || ( KANUtil.filterEmpty( corpId ) != null && ( ( KANUtil.filterEmpty( itemVO.getCorpId() ) != null && itemVO.getCorpId().equals( corpId ) ) || itemVO.getAccountId().equals( "1" ) ) ) )
               {
                  // 初始化MappingVO对象
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( itemVO.getItemId() );

                  if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( itemVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( itemVO.getNameEN() );
                  }
                  items.add( mappingVO );
               }
            }
         }
      }

      return items;
   }

   // 获得工资的Items
   public List< MappingVO > getSalaryItems( final String localeLanguage )
   {
      return getSalaryItems( localeLanguage, null );
   }

   // 获得工资的Items
   public List< MappingVO > getSalaryItems( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > items = new ArrayList< MappingVO >();
      items.addAll( getItemsByType( "1", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "2", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "3", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "4", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "5", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "13", localeLanguage, corpId ) );
      return items;
   }

   // 获得加班的Items
   public List< MappingVO > getOtItems( final String localeLanguage )
   {
      return getOtItems( localeLanguage, null );
   }

   // 获得加班的Items
   public List< MappingVO > getOtItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "4", localeLanguage, corpId );
   }

   // 获得请假的Items
   public List< MappingVO > getLeaveItems( final String localeLanguage )
   {
      return getLeaveItems( localeLanguage, null );
   }

   // 获得请假的Items
   public List< MappingVO > getLeaveItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "6", localeLanguage, corpId );
   }

   // 获得考勤（包括请假、加班）的Items
   public List< MappingVO > getAttendanceItems( final String localeLanguage )
   {
      return getAttendanceItems( localeLanguage, null );
   }

   // 获得考勤（包括请假、加班）的Items
   public List< MappingVO > getAttendanceItems( final String localeLanguage, final String corpId )
   {
      List< MappingVO > returnList = new ArrayList< MappingVO >();
      returnList.addAll( getItemsByType( "4", localeLanguage, corpId ) );
      returnList.addAll( getItemsByType( "6", localeLanguage, corpId ) );
      return returnList;
   }

   // 获得社保的Items
   public List< MappingVO > getSbItems( final String localeLanguage )
   {
      return getSbItems( localeLanguage, null );
   }

   // 获得社保的Items
   public List< MappingVO > getSbItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "7", localeLanguage, corpId );
   }

   // 获得商保的Items
   public List< MappingVO > getCbItems( final String localeLanguage )
   {
      return getCbItems( localeLanguage, null );
   }

   // 获得商保的Items
   public List< MappingVO > getCbItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "8", localeLanguage, corpId );
   }

   // 获得服务费的Items
   public List< MappingVO > getServiceFeeItems( final String localeLanguage )
   {
      return getServiceFeeItems( localeLanguage, null );
   }

   // 获得服务费的Items
   public List< MappingVO > getServiceFeeItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "9", localeLanguage, corpId );
   }

   // 获得其他的Items
   public List< MappingVO > getOtherItems( final String localeLanguage )
   {
      return getOtherItems( localeLanguage, null );
   }

   // 获得其他的Items
   public List< MappingVO > getOtherItems( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > items = new ArrayList< MappingVO >();
      items.addAll( getItemsByType( "10", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "11", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "12", localeLanguage, corpId ) );

      return items;
   }

   // 获得ItemGroup
   public List< MappingVO > getItemGroups( final String localeLanguage )
   {
      return getItemGroups( localeLanguage, null );
   }

   // 获得ItemGroup
   public List< MappingVO > getItemGroups( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > itemGroups = new ArrayList< MappingVO >();

      // 遍历Item Map
      if ( ITEM_GROUP_DTO != null && ITEM_GROUP_DTO.size() > 0 )
      {
         for ( ItemGroupDTO itemGroupDTO : ITEM_GROUP_DTO )
         {
            // 初始化ItemGroupVO
            final ItemGroupVO itemGroupVO = itemGroupDTO.getItemGroupVO();

            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( itemGroupVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( itemGroupVO.getCorpId() ) != null && itemGroupVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( itemGroupVO.getItemGroupId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingValue( itemGroupVO.getNameEN() );
               }
               else
               {
                  mappingVO.setMappingValue( itemGroupVO.getNameZH() );
               }

               itemGroups.add( mappingVO );
            }
         }
      }
      return itemGroups;
   }

   // 获得Bank
   public List< MappingVO > getBanks( final String localeLanguage )
   {
      return getBanks( localeLanguage, null );
   }

   // 获得Bank
   public List< MappingVO > getBanks( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > banks = new ArrayList< MappingVO >();

      // 遍历BANK_VO
      if ( BANK_VO != null && BANK_VO.size() > 0 )
      {
         for ( BankVO bankVO : BANK_VO )
         {
            if ( ( KANUtil.filterEmpty( bankVO.getAccountId() ) != null && bankVO.getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( bankVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( bankVO.getCorpId() ) != null && bankVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( bankVO.getBankId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( bankVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( bankVO.getNameEN() );
               }

               banks.add( mappingVO );
            }
         }
      }

      return banks;
   }

   // 获得BusinessType
   public List< MappingVO > getBusinessTypes( final String localeLanguage )
   {
      return getBusinessTypes( localeLanguage, null );
   }

   // 获得BusinessType
   public List< MappingVO > getBusinessTypes( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > businessTypes = new ArrayList< MappingVO >();

      // 遍历BusinessType Map
      if ( BUSINESS_TYPE_VO != null && BUSINESS_TYPE_VO.size() > 0 )
      {
         for ( BusinessTypeVO businessTypeVO : BUSINESS_TYPE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( businessTypeVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( businessTypeVO.getCorpId() ) != null && businessTypeVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( businessTypeVO.getBusinessTypeId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( businessTypeVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( businessTypeVO.getNameEN() );
               }

               businessTypes.add( mappingVO );
            }
         }
      }
      return businessTypes;
   }

   // 获得ItemMapping
   public List< MappingVO > getItemMappings( final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > itemMappings = new ArrayList< MappingVO >();

      // 遍历BusinessType Map
      if ( ITEM_MAPPING_VO != null && ITEM_MAPPING_VO.size() > 0 )
      {
         for ( ItemMappingVO itemMappingVO : ITEM_MAPPING_VO )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( itemMappingVO.getMappingId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( itemMappingVO.getItemId() );
            }
            else
            {
               mappingVO.setMappingValue( itemMappingVO.getItemId() );
            }
            itemMappings.add( mappingVO );
         }
      }
      return itemMappings;
   }

   // 获得IndustryType
   public List< MappingVO > getIndustryTypes( final String localeLanguage )
   {
      return getIndustryTypes( localeLanguage, null );
   }

   // 获得IndustryType
   public List< MappingVO > getIndustryTypes( final String localeLanguage, String corpId )
   {
      // 行业类型无需判断是否为INHOUSE  OR  HRSERVICE
      corpId = null;
      // 初始化返回值对象
      final List< MappingVO > industryTypes = new ArrayList< MappingVO >();

      // 遍历IndustryType Map
      if ( INDUSTRY_TYPE_VO != null && INDUSTRY_TYPE_VO.size() > 0 )
      {
         for ( IndustryTypeVO industryTypeVO : INDUSTRY_TYPE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( industryTypeVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( industryTypeVO.getCorpId() ) != null && industryTypeVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( industryTypeVO.getTypeId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( industryTypeVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( industryTypeVO.getNameEN() );
               }

               industryTypes.add( mappingVO );
            }
         }
      }
      return industryTypes;
   }

   // 获得BusinessContractTemplate
   public List< MappingVO > getBusinessContractTemplates( final String localeLanguage )
   {
      return getBusinessContractTemplates( localeLanguage, null );
   }

   // 获得BusinessContractTemplate
   public List< MappingVO > getBusinessContractTemplates( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > businessContractTemplates = new ArrayList< MappingVO >();

      // 遍历BusinessContractTemplate Map
      if ( BUSINESS_CONTRACT_TEMPLATE_VO != null && BUSINESS_CONTRACT_TEMPLATE_VO.size() > 0 )
      {
         for ( BusinessContractTemplateVO businessContractTemplateVO : BUSINESS_CONTRACT_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( businessContractTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( businessContractTemplateVO.getCorpId() ) != null && businessContractTemplateVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( businessContractTemplateVO.getTemplateId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( businessContractTemplateVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( businessContractTemplateVO.getNameEN() );
               }

               businessContractTemplates.add( mappingVO );
            }
         }
      }
      return businessContractTemplates;
   }

   // 获得Business Type
   public BusinessTypeVO getBusinessTypeById( final String id )
   {
      // 遍历
      if ( BUSINESS_TYPE_VO != null && BUSINESS_TYPE_VO.size() > 0 )
      {
         for ( BusinessTypeVO businessTypeVO : BUSINESS_TYPE_VO )
         {
            if ( id.equals( businessTypeVO.getBusinessTypeId() ) )
            {
               return businessTypeVO;
            }
         }
      }

      return null;
   }

   // 获得Business Type
   public BusinessTypeVO getBusinessTypeByName( final String name, final String corpId )
   {
      // 遍历
      if ( BUSINESS_TYPE_VO != null && BUSINESS_TYPE_VO.size() > 0 )
      {
         for ( BusinessTypeVO businessTypeVO : BUSINESS_TYPE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( businessTypeVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( businessTypeVO.getCorpId() ) != null && KANUtil.filterEmpty( businessTypeVO.getCorpId() ).equals( corpId ) ) )
            {
               if ( ( KANUtil.filterEmpty( businessTypeVO.getNameZH() ) != null && KANUtil.filterEmpty( businessTypeVO.getNameZH() ).trim().equals( name ) )
                     || ( KANUtil.filterEmpty( businessTypeVO.getNameEN() ) != null && KANUtil.filterEmpty( businessTypeVO.getNameEN() ).trim().equals( name ) ) )
               {
                  return businessTypeVO;
               }
            }
         }
      }

      return null;
   }

   // 获得LaborContractTemplate
   public List< MappingVO > getLaborContractTemplates( final String localeLanguage )
   {
      return getLaborContractTemplates( localeLanguage, null );
   }

   // 获得LaborContractTemplate
   public List< MappingVO > getLaborContractTemplates( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > laborContractTemplates = new ArrayList< MappingVO >();

      // 遍历LaborContractTemplate Map
      if ( LABOR_CONTRACT_TEMPLATE_VO != null && LABOR_CONTRACT_TEMPLATE_VO.size() > 0 )
      {
         for ( LaborContractTemplateVO laborContractTemplateVO : LABOR_CONTRACT_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( laborContractTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( laborContractTemplateVO.getCorpId() ) != null && laborContractTemplateVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( laborContractTemplateVO.getTemplateId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( laborContractTemplateVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( laborContractTemplateVO.getNameEN() );
               }

               laborContractTemplates.add( mappingVO );
            }
         }
      }
      return laborContractTemplates;
   }

   // 获得LaborContractTemplate
   public LaborContractTemplateVO getLaborContractTemplatesByName( final String name, final String corpId )
   {
      // 遍历LaborContractTemplate Map
      if ( LABOR_CONTRACT_TEMPLATE_VO != null && LABOR_CONTRACT_TEMPLATE_VO.size() > 0 )
      {
         for ( LaborContractTemplateVO laborContractTemplateVO : LABOR_CONTRACT_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( laborContractTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( laborContractTemplateVO.getCorpId() ) != null && laborContractTemplateVO.getCorpId().equals( corpId ) ) )
            {
               if ( ( KANUtil.filterEmpty( laborContractTemplateVO.getNameZH() ) != null && KANUtil.filterEmpty( laborContractTemplateVO.getNameZH() ).trim().equalsIgnoreCase( name ) )
                     || ( ( KANUtil.filterEmpty( laborContractTemplateVO.getNameEN() ) != null && KANUtil.filterEmpty( laborContractTemplateVO.getNameEN() ).trim().equalsIgnoreCase( name ) ) ) )
               {
                  return laborContractTemplateVO;
               }

            }
         }
      }
      return null;
   }

   // 获得LaborContractTemplate
   public List< MappingVO > getResignTemplates( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > resignTemplates = new ArrayList< MappingVO >();

      // 遍历LaborContractTemplate Map
      if ( RESIGN_TEMPLATE_VO != null && RESIGN_TEMPLATE_VO.size() > 0 )
      {
         for ( ResignTemplateVO resignTemplateVO : RESIGN_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) != null && resignTemplateVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( resignTemplateVO.getTemplateId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( resignTemplateVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( resignTemplateVO.getNameEN() );
               }

               resignTemplates.add( mappingVO );
            }
         }
      }
      return resignTemplates;
   }

   // 获得ResignTemplateVO
   public ResignTemplateVO getResignTemplatesByName( final String name, final String corpId )
   {
      // 遍历LaborContractTemplate Map
      if ( RESIGN_TEMPLATE_VO != null && RESIGN_TEMPLATE_VO.size() > 0 )
      {
         for ( ResignTemplateVO resignTemplateVO : RESIGN_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) != null && resignTemplateVO.getCorpId().equals( corpId ) ) )
            {
               if ( ( KANUtil.filterEmpty( resignTemplateVO.getNameZH() ) != null && KANUtil.filterEmpty( resignTemplateVO.getNameZH() ).trim().equalsIgnoreCase( name ) )
                     || ( ( KANUtil.filterEmpty( resignTemplateVO.getNameEN() ) != null && KANUtil.filterEmpty( resignTemplateVO.getNameEN() ).trim().equalsIgnoreCase( name ) ) ) )
               {
                  return resignTemplateVO;
               }

            }
         }
      }
      return null;
   }

   // 获得ResignTemplateVO
   public ResignTemplateVO getResignTemplatesById( final String id, final String corpId )
   {
      // 遍历LaborContractTemplate Map
      if ( RESIGN_TEMPLATE_VO != null && RESIGN_TEMPLATE_VO.size() > 0 )
      {
         for ( ResignTemplateVO resignTemplateVO : RESIGN_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) != null && resignTemplateVO.getCorpId().equals( corpId ) ) )
            {
               if ( ( KANUtil.filterEmpty( resignTemplateVO.getTemplateId() ) != null && KANUtil.filterEmpty( resignTemplateVO.getTemplateId() ).trim().equalsIgnoreCase( id ) ) )
               {
                  return resignTemplateVO;
               }

            }
         }
      }
      return null;
   }

   // 获得Membership
   public List< MappingVO > getMemberships( final String localeLanguage )
   {
      return getMemberships( localeLanguage, null );
   }

   // 获得Membership
   public List< MappingVO > getMemberships( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > memberships = new ArrayList< MappingVO >();

      // 遍历Membership Map
      if ( MEMBERSHIP_VO != null && MEMBERSHIP_VO.size() > 0 )
      {
         for ( MembershipVO membershipVO : MEMBERSHIP_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( membershipVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( membershipVO.getCorpId() ) != null && membershipVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( membershipVO.getMembershipId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( membershipVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( membershipVO.getNameEN() );
               }

               memberships.add( mappingVO );
            }
         }
      }
      return memberships;
   }

   // 获得certification
   public List< MappingVO > getCertifications( final String localeLanguage )
   {
      return getCertifications( localeLanguage, null );
   }

   // 获得certification
   public List< MappingVO > getCertifications( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > certifications = new ArrayList< MappingVO >();

      // 遍历Membership Map
      if ( CERTIFICATION_VO != null && CERTIFICATION_VO.size() > 0 )
      {
         for ( CertificationVO certificationVO : CERTIFICATION_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( certificationVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( certificationVO.getCorpId() ) != null && certificationVO.getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( certificationVO.getCertificationId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( certificationVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( certificationVO.getNameEN() );
               }

               certifications.add( mappingVO );
            }
         }
      }
      return certifications;
   }

   // 获取SocialBenefitSolutions对应的 MappingVO
   public List< MappingVO > getSocialBenefitSolutions( final String localeLanguage )
   {
      return getSocialBenefitSolutions( localeLanguage, null );
   }

   // 获取SocialBenefitSolutions对应的 MappingVO
   // Reviewed by Kevin Jin at 2013-09-18
   public List< MappingVO > getSocialBenefitSolutions( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > socialBenefitSolutionHeaders = new ArrayList< MappingVO >();

      // 遍历SocialBenefitSolutionDTO
      for ( SocialBenefitSolutionDTO socialBenefitSolutionDTO : SOCIAL_BENEFIT_SOLUTION_DTO )
      {
         if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getCorpId() ) != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getCorpId().equals( corpId ) ) )
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameEN() );
               }

               socialBenefitSolutionHeaders.add( mappingVO );
            }
         }
      }

      return socialBenefitSolutionHeaders;
   }

   // 获取社保方案 - 按照社保方案Id
   // Reviewed by Kevin Jin at 2013-09-18
   public SocialBenefitSolutionDTO getSocialBenefitSolutionDTOByHeaderId( final String headerId )
   {
      // 遍历SocialBenefitSolutionDTOs
      if ( SOCIAL_BENEFIT_SOLUTION_DTO != null && SOCIAL_BENEFIT_SOLUTION_DTO.size() > 0 )
      {
         for ( SocialBenefitSolutionDTO socialBenefitSolutionDTO : SOCIAL_BENEFIT_SOLUTION_DTO )
         {
            if ( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId().trim().equals( headerId ) )
            {
               return socialBenefitSolutionDTO;
            }
         }
      }

      return null;
   }

   // 获得SocialBenefitSolutionDTO
   public SocialBenefitSolutionDTO getSocialBenefitSolutionDTOByName( final String name, final String corpId )
   {
      // 遍历SocialBenefitSolutionDTOs
      if ( SOCIAL_BENEFIT_SOLUTION_DTO != null && SOCIAL_BENEFIT_SOLUTION_DTO.size() > 0 )
      {
         for ( SocialBenefitSolutionDTO socialBenefitSolutionDTO : SOCIAL_BENEFIT_SOLUTION_DTO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getCorpId() ) != null && KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getCorpId() ).equals( corpId ) )
                  || "1".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getAccountId() ) )
            {
               if ( ( KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() ) != null && KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() ).trim().equals( name == null ? ""
                     : name.trim() ) )
                     || ( KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameEN() ) != null && KANUtil.filterEmpty( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameEN() ).trim().equals( name == null ? ""
                           : name.trim() ) ) )
               {
                  return socialBenefitSolutionDTO;
               }
            }
         }
      }

      return null;
   }

   // 获得CommercialBenefitSolutionDTO
   public CommercialBenefitSolutionDTO getCommercialBenefitSolutionDTOByName( final String name, final String corpId )
   {
      // 遍历COMMERCIAL_BENEFIT_SOLUTION_DTO
      if ( COMMERCIAL_BENEFIT_SOLUTION_DTO != null && COMMERCIAL_BENEFIT_SOLUTION_DTO.size() > 0 )
      {
         for ( CommercialBenefitSolutionDTO commercialBenefitSolutionDTO : COMMERCIAL_BENEFIT_SOLUTION_DTO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCorpId() ) != null && KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCorpId() ).equals( corpId ) )
                  || "1".equals( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getAccountId() ) )
            {
               if ( ( KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() ) != null && KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() ).trim().equals( name ) )
                     || ( KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameEN() ) != null && KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameEN() ).trim().equals( name ) ) )
               {
                  return commercialBenefitSolutionDTO;
               }
            }
         }
      }

      return null;
   }

   // 获取商保方案 - 按照商保方案Id
   // Reviewed by Kevin Jin at 2013-09-18
   public CommercialBenefitSolutionDTO getCommercialBenefitSolutionDTOByHeaderId( final String headerId )
   {
      // 遍历CommercialBenefitSolutionDTOs
      if ( COMMERCIAL_BENEFIT_SOLUTION_DTO != null && COMMERCIAL_BENEFIT_SOLUTION_DTO.size() > 0 )
      {
         for ( CommercialBenefitSolutionDTO commercialBenefitSolutionDTO : COMMERCIAL_BENEFIT_SOLUTION_DTO )
         {
            if ( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId().trim().equals( headerId ) )
            {
               return commercialBenefitSolutionDTO;
            }
         }
      }

      return null;
   }

   // 获取SocialBenefitSolution对应的 MappingVO
   public List< MappingVO > getCommercialBenefitSolutions( final String localeLanguage )
   {
      return getCommercialBenefitSolutions( localeLanguage, null );
   }

   // 获取SocialBenefitSolution对应的 MappingVO
   // Reviewed by Kevin Jin at 2013-11-21
   public List< MappingVO > getCommercialBenefitSolutions( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > commercialBenefitSolutionHeaders = new ArrayList< MappingVO >();

      // 遍历SocialBenefitSolutionDTO
      for ( CommercialBenefitSolutionDTO commercialBenefitSolutionDTO : COMMERCIAL_BENEFIT_SOLUTION_DTO )
      {
         if ( commercialBenefitSolutionDTO != null && commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO() != null )
         {
            if ( ( "1".equals( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getAccountId() ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCorpId() ) != null && commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCorpId().equals( corpId ) ) )
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameEN() );
               }

               commercialBenefitSolutionHeaders.add( mappingVO );
            }
         }
      }

      return commercialBenefitSolutionHeaders;
   }

   // 获取Column Group
   public List< MappingVO > getColumnGroup( final String localeLanguage )
   {
      return getColumnGroup( localeLanguage, null );
   }

   // 获取Column Group
   public List< MappingVO > getColumnGroup( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > columnGroups = new ArrayList< MappingVO >();

      // 遍历Column Group Map
      if ( COLUMN_GROUP_VO != null && COLUMN_GROUP_VO.size() > 0 )
      {
         for ( ColumnGroupVO columnGroupVO : COLUMN_GROUP_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) != null && corpId.equals( KANUtil.filterEmpty( columnGroupVO.getCorpId() ) ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( columnGroupVO.getGroupId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( columnGroupVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( columnGroupVO.getNameEN() );
               }

               columnGroups.add( mappingVO );
            }
         }
      }

      return columnGroups;
   }

   // 获取Column Option
   public List< MappingVO > getColumnOptions( final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > columnOptions = new ArrayList< MappingVO >();

      // 遍历Column Option List
      if ( COLUMN_OPTION_DTO != null && COLUMN_OPTION_DTO.size() > 0 )
      {
         for ( OptionDTO optionDTO : COLUMN_OPTION_DTO )
         {
            final OptionHeaderVO optionHeaderVO = optionDTO.getOptionHeaderVO();

            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( optionHeaderVO.getOptionHeaderId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( optionHeaderVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( optionHeaderVO.getNameEN() );
            }

            columnOptions.add( mappingVO );
         }
      }

      return columnOptions;
   }

   // SearchHeaders
   public List< MappingVO > getSearchHeadersByTableId( final String tableId, final String localeLanguage )
   {
      return getSearchHeadersByTableId( tableId, null, localeLanguage );
   }

   // SearchHeaders
   public List< MappingVO > getSearchHeadersByTableId( final String tableId, final String corpId, final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > searchHeaders = new ArrayList< MappingVO >();

      // 遍历SearchDTO List
      if ( SEARCH_DTO != null && SEARCH_DTO.size() > 0 )
      {
         for ( SearchDTO searchDTO : SEARCH_DTO )
         {
            final SearchHeaderVO searchHeaderVO = searchDTO.getSearchHeaderVO();

            if ( ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( searchHeaderVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                  && KANUtil.filterEmpty( searchHeaderVO.getCorpId() ) != null && corpId.equals( KANUtil.filterEmpty( searchHeaderVO.getCorpId() ) ) ) )
                  && searchHeaderVO.getTableId() != null && searchHeaderVO.getTableId().trim().equals( tableId ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( searchHeaderVO.getSearchHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( searchHeaderVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( searchHeaderVO.getNameEN() );
               }

               searchHeaders.add( mappingVO );
            }
         }
      }

      return searchHeaders;
   }

   // SearchDetails
   public List< MappingVO > getSearchDetailsBySearchHeaderId( final String searchHeaderId, final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > searchDetails = new ArrayList< MappingVO >();

      // 遍历SearchDTO List
      if ( SEARCH_DTO != null && SEARCH_DTO.size() > 0 )
      {
         List< SearchDetailVO > searchDetailVOs = new ArrayList< SearchDetailVO >();
         for ( SearchDTO searchDTO : SEARCH_DTO )
         {
            if ( searchDTO.getSearchHeaderVO().getSearchHeaderId().equals( searchHeaderId ) )
            {
               searchDetailVOs = searchDTO.getSearchDetailVOs();
               break;
            }
         }
         if ( searchDetailVOs != null && searchDetailVOs.size() > 0 )
         {
            for ( SearchDetailVO searchDetailVO : searchDetailVOs )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( searchDetailVO.getColumnId() );
               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( searchDetailVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( searchDetailVO.getNameEN() );
               }
               searchDetails.add( mappingVO );
            }
         }
      }

      return searchDetails;
   }

   // 获取ListDTO MappingVO形式
   public List< MappingVO > getLists( final String localeLanguage )
   {
      return getLists( localeLanguage, false, null );
   }

   public List< MappingVO > getLists( final String localeLanguage, final boolean isJavaObject )
   {
      return getLists( localeLanguage, isJavaObject, null );
   }

   public List< MappingVO > getLists( final String localeLanguage, final String corpId )
   {
      return getLists( localeLanguage, false, corpId );
   }

   // 获取ListDTO MappingVO形式
   public List< MappingVO > getLists( final String localeLanguage, final boolean isJavaObject, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > lists = new ArrayList< MappingVO >();

      // 存在LIST_DTO list
      if ( LIST_DTO != null && LIST_DTO.size() > 0 )
      {
         for ( ListDTO listDTO : LIST_DTO )
         {
            if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( listDTO.getListHeaderVO().getCorpId() ) != null )
            {
               continue;
            }
            if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( listDTO.getListHeaderVO().getCorpId() ) == null )
            {
               continue;
            }

            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( listDTO.getListHeaderVO().getListHeaderId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( listDTO.getListHeaderVO().getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( listDTO.getListHeaderVO().getNameEN() );
            }

            if ( !isJavaObject || ( isJavaObject && KANUtil.filterEmpty( listDTO.getListHeaderVO().getTableId(), "0" ) == null ) )
            {
               lists.add( mappingVO );
            }
         }
      }

      return lists;
   }

   // 按照SkillId获得对应的SkillDTOs
   public List< SkillDTO > getSkillDTOsByParentSkillId( final String skillId )
   {
      return getSkillDTOsByParentSkillId( SKILL_DTO, skillId, null );
   }

   public List< SkillDTO > getSkillDTOsByParentSkillId( final String skillId, final String corpId )
   {
      return getSkillDTOsByParentSkillId( SKILL_DTO, skillId, KANUtil.filterEmpty( corpId ) );
   }

   // 按照ParentSkillId获得对应的SkillDTO - 递归函数
   private List< SkillDTO > getSkillDTOsByParentSkillId( List< SkillDTO > skillDTOs, final String ParentSkillId, final String corpId )
   {
      // 实例化childSkillDTOs
      final List< SkillDTO > childSkillDTOs = new ArrayList< SkillDTO >();
      if ( skillDTOs != null && skillDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( SkillDTO skillDTO : skillDTOs )
         {

            // 如果DTO是目标对象，则返回
            if ( skillDTO.getSkillVO() != null && skillDTO.getSkillVO().getParentSkillId() != null && skillDTO.getSkillVO().getParentSkillId().equals( ParentSkillId ) )
            {
               if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) == null )
                     || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) != null && skillDTO.getSkillVO().getCorpId().equals( corpId ) ) )
               {
                  childSkillDTOs.add( skillDTO );
               }
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( skillDTO.getSkillDTOs() != null && skillDTO.getSkillDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               List< SkillDTO > skillDTOTemp = getSkillDTOsByParentSkillId( skillDTO.getSkillDTOs(), ParentSkillId, corpId );

               // 如果子节点中有目标对象，立即返回
               if ( skillDTOTemp != null )
               {
                  childSkillDTOs.addAll( skillDTOTemp );
               }
            }
         }
      }
      return childSkillDTOs;
   }

   public SkillDTO getSkillDTOBySkillId( final String skillId, final String corpId )
   {
      return getSkillDTOBySkillId( SKILL_DTO, skillId, KANUtil.filterEmpty( corpId ) );
   }

   // 按照ParentSkillId获得对应的SkillDTO - 递归函数
   private SkillDTO getSkillDTOBySkillId( List< SkillDTO > skillDTOs, final String skillId, final String corpId )
   {
      // 实例化childSkillDTOs
      if ( skillDTOs != null && skillDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( SkillDTO skillDTO : skillDTOs )
         {

            // 如果DTO是目标对象，则返回
            if ( skillDTO.getSkillVO() != null && skillDTO.getSkillVO().getSkillId() != null && skillDTO.getSkillVO().getSkillId().equals( skillId ) )
            {
               if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) == null )
                     || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) != null && skillDTO.getSkillVO().getCorpId().equals( corpId ) ) )
               {
                  return skillDTO;
               }
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( skillDTO.getSkillDTOs() != null && skillDTO.getSkillDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               SkillDTO skillDTOTemp = getSkillDTOBySkillId( skillDTO.getSkillDTOs(), skillId, corpId );

               // 如果子节点中有目标对象，立即返回
               if ( skillDTOTemp != null )
               {
                  return skillDTOTemp;
               }
            }
         }
      }
      return null;
   }

   // 按照StaffId获得对应的Position Count
   public int getPositionCountByStaffId( final String staffId )
   {
      final List< PositionDTO > positionDTOs = getPositionDTOsByStaffId( staffId );

      return positionDTOs == null ? 0 : positionDTOs.size();
   }

   // 按照StaffId获得对应的PositionDTO列表
   public List< PositionDTO > getPositionDTOsByStaffId( final String staffId )
   {
      return getPositionDTOsByStaffId( POSITION_DTO, staffId );
   }

   public List< PositionDTO > getPositionDTOsByCorpId( final String corpId )
   {
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();
      for ( PositionDTO positionDTO : POSITION_DTO )
      {
         // 如果是INhouse
         if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) == null )
         {
            continue;
         }
         // 如果是HRService
         if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) != null )
         {
            continue;
         }
         positionDTOs.add( positionDTO );
      }
      return positionDTOs;
   }

   // 按照StaffId获得对应的PositionDTO列表 - 递归函数
   private List< PositionDTO > getPositionDTOsByStaffId( final List< PositionDTO > positionDTOs, final String staffId )
   {
      // 当前用户所在的PositionDTO列表
      final List< PositionDTO > matchedPositionDTOs = new ArrayList< PositionDTO >();

      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // 如果DTO是目标对象，则返回
            if ( positionDTO.containsStaffId( staffId ) )
            {
               matchedPositionDTOs.add( positionDTO );
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // 返回值直接存入当前PositionDTO列表
               matchedPositionDTOs.addAll( getPositionDTOsByStaffId( positionDTO.getPositionDTOs(), staffId ) );
            }
         }
      }

      return matchedPositionDTOs;
   }

   // 按照PositionId获得对应的PositionVO
   public PositionVO getPositionVOByPositionId( final String positionId )
   {
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );
      return positionDTO != null ? positionDTO.getPositionVO() : null;
   }

   public PositionVO getRootPositionByPositionId( final String positionId )
   {
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );
      final String parentPositionId = positionDTO.getPositionVO().getParentPositionId();
      if ( KANUtil.filterEmpty( parentPositionId ) != null )
      {
         return getRootPositionByPositionId( parentPositionId );
      }
      else
      {
         return positionDTO.getPositionVO();
      }
   }

   public List< String > getParentPositionIdsByPositionId( final String positionId )
   {
      final List< String > parents = new ArrayList< String >();
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );
      return getParentPositionIdsByPositionId( positionDTO, parents );
   }

   public List< String > getParentPositionIdsByPositionId( final PositionDTO positionDTO, final List< String > parents )
   {
      if ( positionDTO != null )
      {
         final PositionDTO parentPositionDTO = getPositionDTOByPositionId( positionDTO.getPositionVO().getParentPositionId() );
         if ( parentPositionDTO != null && !"0".equals( parentPositionDTO.getPositionVO().getParentPositionId() ) )
         {
            parents.add( positionDTO.getPositionVO().getPositionId() );
            return getParentPositionIdsByPositionId( parentPositionDTO, parents );
         }
         else
         {
            return parents;
         }
      }

      return parents;
   }

   // 根据positionId获取BUHeader Position
   public PositionVO getBUHeaderPositionVOByPositionId( final String positionId )
   {
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );
      return getBUHeaderPositionVOByPositionId( positionDTO );
   }

   // 根据positionId获取BUHeader Position
   public PositionVO getBUHeaderPositionVOByPositionId( final PositionDTO positionDTO )
   {
      if ( positionDTO != null )
      {
         final PositionDTO parentPositionDTO = getPositionDTOByPositionId( positionDTO.getPositionVO().getParentPositionId() );
         if ( parentPositionDTO != null && !"0".equals( parentPositionDTO.getPositionVO().getParentPositionId() ) )
         {
            return getBUHeaderPositionVOByPositionId( parentPositionDTO );
         }
         else
         {
            return positionDTO.getPositionVO();
         }
      }

      return null;
   }

   // 按照PositionId获得对应的PositionDTO
   public PositionDTO getPositionDTOByPositionId( final String positionId )
   {
      return getPositionDTOByPositionId( POSITION_DTO, positionId );
   }

   // 按照PositionId获得对应的PositionDTO - 递归函数
   private PositionDTO getPositionDTOByPositionId( List< PositionDTO > positionDTOs, final String positionId )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // 如果DTO是目标对象，则返回
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getPositionId() != null && positionDTO.getPositionVO().getPositionId().equals( positionId ) )
            {
               return positionDTO;
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               positionDTO = getPositionDTOByPositionId( positionDTO.getPositionDTOs(), positionId );

               // 如果子节点中有目标对象，立即返回
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // 按照PositionId获得对应的PositionDTO
   public PositionDTO getPositionDTOByPositionName( final String corpId, final String positionName )
   {
      return getPositionDTOByPositionName( POSITION_DTO, corpId, positionName );
   }

   // 按照PositionId获得对应的PositionDTO - 递归函数
   private PositionDTO getPositionDTOByPositionName( List< PositionDTO > positionDTOs, final String corpId, final String positionName )
   {
      String corpID = KANUtil.filterEmpty( corpId );
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            final String corpID_p = KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() );
            // 如果DTO是目标对象，则返回
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getTitleZH() != null && positionDTO.getPositionVO().getTitleZH().equals( positionName )
                  && ( ( corpID_p == null && KANUtil.filterEmpty( corpId ) == null ) || ( corpID_p != null && KANUtil.filterEmpty( corpId ) != null && corpID.equals( corpID_p ) ) ) )
            {
               return positionDTO;
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               positionDTO = getPositionDTOByPositionName( positionDTO.getPositionDTOs(), corpId, positionName );

               // 如果子节点中有目标对象，立即返回
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // 按照PositionId获得对应的PositionDTO
   public PositionDTO getPositionDTOByPositionGradeId( final String positionGradeId )
   {
      return getPositionDTOByPositionGradeId( POSITION_DTO, positionGradeId );
   }

   // 按照PositionId获得对应的PositionDTO - 递归函数
   private PositionDTO getPositionDTOByPositionGradeId( List< PositionDTO > positionDTOs, final String positionGradeId )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // 如果DTO是目标对象，则返回
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getPositionGradeId() != null
                  && positionDTO.getPositionVO().getPositionGradeId().equals( positionGradeId ) )
            {
               return positionDTO;
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               positionDTO = getPositionDTOByPositionGradeId( positionDTO.getPositionDTOs(), positionGradeId );

               // 如果子节点中有目标对象，立即返回
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // 按照BranchId获得对应的PositionVO列表
   public List< PositionVO > getPositionVOsByBranchId( final String branchId )
   {
      // 初始化PositionVO列表
      final List< PositionVO > positionVOs = new ArrayList< PositionVO >();

      if ( branchId == null || branchId.trim().equals( "" ) )
      {
         return positionVOs;
      }

      if ( POSITION_VO != null && POSITION_VO.size() > 0 )
      {
         for ( PositionVO positionVO : POSITION_VO )
         {
            if ( positionVO.getBranchId() != null && positionVO.getBranchId().equals( branchId ) )
            {
               positionVOs.add( positionVO );
            }
         }
      }

      return positionVOs;
   }

   // 按照PositionId获得对应的Employee_PositionDTO
   public com.kan.base.domain.management.PositionDTO getEmployeePositionDTOByPositionId( final String positionId )
   {
      return getEmployeePositionDTOByPositionId( EMPLOYEE_POSITION_DTO, positionId );
   }

   // 按照PositionId获得对应的PositionDTO - 递归函数
   private com.kan.base.domain.management.PositionDTO getEmployeePositionDTOByPositionId( List< com.kan.base.domain.management.PositionDTO > positionDTOs, final String positionId )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( com.kan.base.domain.management.PositionDTO positionDTO : positionDTOs )
         {
            // 如果DTO是目标对象，则返回
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getPositionId() != null && positionDTO.getPositionVO().getPositionId().equals( positionId ) )
            {
               return positionDTO;
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               positionDTO = getEmployeePositionDTOByPositionId( positionDTO.getPositionDTOs(), positionId );

               // 如果子节点中有目标对象，立即返回
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // 按照ModuleId获得对应的AccountModuleDTO
   public AccountModuleDTO getAccountModuleDTOByModuleId( final String moduleId )
   {
      return getAccountModuleDTOByModuleId( MODULE_DTO, moduleId );
   }

   // 按照ModuleId获得对应的AccountModuleDTO - 递归函数
   private AccountModuleDTO getAccountModuleDTOByModuleId( final List< AccountModuleDTO > accountModuleDTOs, final String moduleId )
   {
      if ( accountModuleDTOs != null && accountModuleDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( AccountModuleDTO accountModuleDTO : accountModuleDTOs )
         {
            // 如果DTO是目标对象，则返回
            if ( accountModuleDTO.getModuleVO() != null && KANUtil.filterEmpty( accountModuleDTO.getModuleVO().getModuleId() ) != null
                  && KANUtil.filterEmpty( accountModuleDTO.getModuleVO().getModuleId() ).equals( moduleId ) )
            {
               return accountModuleDTO;
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( accountModuleDTO.getAccountModuleDTOs() != null && accountModuleDTO.getAccountModuleDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               accountModuleDTO = getAccountModuleDTOByModuleId( accountModuleDTO.getAccountModuleDTOs(), moduleId );

               // 如果子节点中有目标对象，立即返回
               if ( accountModuleDTO != null )
               {
                  return accountModuleDTO;
               }
            }
         }
      }

      return null;
   }

   // 从AccountModuleDTO中生成ModuleDTO
   public List< ModuleDTO > getModuleDTOs()
   {
      return getModuleDTOs( MODULE_DTO );
   }

   // 从AccountModuleDTO中生成ModuleDTO - 递归函数
   private List< ModuleDTO > getModuleDTOs( final List< AccountModuleDTO > accountModuleDTOs )
   {
      // 初始化当前层级的ModuleDTO对象列表
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      if ( accountModuleDTOs != null && accountModuleDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( AccountModuleDTO accountModuleDTO : accountModuleDTOs )
         {
            final ModuleDTO moduleDTO = new ModuleDTO();
            // 添加ModuleVO对象至DTO
            moduleDTO.setModuleVO( accountModuleDTO.getModuleVO() );

            // 如果当前DTO的子节点不为空，递归遍历
            if ( accountModuleDTO.getAccountModuleDTOs() != null && accountModuleDTO.getAccountModuleDTOs().size() > 0 )
            {
               // 递归遍历
               moduleDTO.getModuleDTOs().addAll( getModuleDTOs( accountModuleDTO.getAccountModuleDTOs() ) );
            }

            moduleDTOs.add( moduleDTO );
         }
      }

      return moduleDTOs;
   }

   // 按照StaffId获得对应的StaffDTO
   public StaffDTO getStaffDTOByStaffId( final String staffId )
   {
      if ( STAFF_DTO != null && STAFF_DTO.size() > 0 )
      {
         for ( StaffDTO staffDTO : STAFF_DTO )
         {
            if ( staffDTO.getStaffVO() != null && staffDTO.getStaffVO().getStaffId() != null && staffDTO.getStaffVO().getStaffId().equals( staffId ) )
            {
               return staffDTO;
            }
         }
      }

      return null;
   }

   // 获取ModuleVO列表
   public List< ModuleVO > getModuleVOs()
   {
      return MODULE_VO;
   }

   // 从AccountModuleDTO中生成已经设置过Right和Rule的ModuleVO列表
   public List< ModuleVO > getSelectedModuleVOs()
   {
      return getSelectedModuleVOs( MODULE_DTO );
   }

   // 从AccountModuleDTO中生成已经设置过Right和Rule的ModuleVO列表
   public List< ModuleVO > getClientSelectedModuleVOs()
   {
      return CLIENT_SELECT_MODULE_VO;
   }

   // 从AccountModuleDTO中生成已经设置过Right或Rule的ModuleVO列表 - 递归函数
   private List< ModuleVO > getSelectedModuleVOs( final List< AccountModuleDTO > accountModuleDTOs )
   {
      // 初始化当前层级的ModuleVO对象列表
      final List< ModuleVO > moduleVOs = new ArrayList< ModuleVO >();

      if ( accountModuleDTOs != null && accountModuleDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( AccountModuleDTO accountModuleDTO : accountModuleDTOs )
         {
            // 如果当前Module存在设置过的Right和Rule
            if ( ( accountModuleDTO.getModuleRightRelationVOs() != null && accountModuleDTO.getModuleRightRelationVOs().size() > 0 )
                  || ( accountModuleDTO.getModuleRuleRelationVOs() != null && accountModuleDTO.getModuleRuleRelationVOs().size() > 0 ) )
            {
               // 添加ModuleVO对象至列表
               moduleVOs.add( accountModuleDTO.getModuleVO() );
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( accountModuleDTO.getAccountModuleDTOs() != null && accountModuleDTO.getAccountModuleDTOs().size() > 0 )
            {
               // 递归遍历
               moduleVOs.addAll( getSelectedModuleVOs( accountModuleDTO.getAccountModuleDTOs() ) );
            }
         }
      }

      return moduleVOs;
   }

   // 获得Constant对应的MappingVO
   public List< MappingVO > getConstants( final String localeLanguage )
   {
      return getConstantsByScopeType( localeLanguage, null );
   }

   // 根据ScopeType获得Constant对应的MappingVO
   public List< MappingVO > getConstantsByScopeType( final String localeLanguage, final String scopeType )
   {
      final List< MappingVO > constants = new ArrayList< MappingVO >();

      // 遍历参数     
      for ( ConstantVO constantVO : CONSTANT_VO )
      {
         if ( scopeType == null || ( constantVO.getScopeType() != null && constantVO.getScopeType().equals( scopeType ) ) )
         {
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( constantVO.getConstantId() );
            mappingVO.setMappingTemp( constantVO.getPropertyName() );
            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( constantVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( constantVO.getNameEN() );
            }

            constants.add( mappingVO );
         }
      }

      return constants;
   }

   // 根据ScopeType获得Constant对应的MappingVO
   public List< MappingVO > getMessageTemplateByType( final String localeLanguage, final String scopeType )
   {
      final List< MappingVO > messageTemplates = new ArrayList< MappingVO >();

      // 遍历参数     
      for ( MessageTemplateVO messageTemplateVO : MESSAGE_TEMPLATE_VO )
      {
         if ( scopeType == null || ( messageTemplateVO.getTemplateType() != null && messageTemplateVO.getTemplateType().equals( scopeType ) ) )
         {
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( messageTemplateVO.getTemplateId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( messageTemplateVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( messageTemplateVO.getNameEN() );
            }

            messageTemplates.add( mappingVO );
         }
      }

      return messageTemplates;
   }

   // 根据ScopeType获得ConstantVO列表
   public List< ConstantVO > getConstantVOsByScopeType( final String scopeType )
   {
      final List< ConstantVO > constants = new ArrayList< ConstantVO >();

      // 遍历参数     
      for ( ConstantVO constantVO : CONSTANT_VO )
      {
         if ( constantVO.getScopeType() != null && constantVO.getScopeType().equals( scopeType ) )
         {
            constants.add( constantVO );
         }

      }

      return constants;
   }

   // 按照GroupId获得对应的GroupDTO
   public GroupDTO getGroupDTOByGroupId( final String groupId )
   {
      if ( POSITION_GROUP_DTO != null && POSITION_GROUP_DTO.size() > 0 )
      {
         for ( GroupDTO groupDTO : POSITION_GROUP_DTO )
         {
            if ( groupDTO.getGroupVO() != null && groupDTO.getGroupVO().getGroupId() != null && groupDTO.getGroupVO().getGroupId().trim().equals( groupId ) )
            {
               return groupDTO;
            }
         }
      }
      return null;
   }

   // 按照positionId获取GroupDTOs
   public List< GroupDTO > getGroupDTOsByPositionId( final String positionId )
   {
      final List< GroupDTO > groupDTOs = new ArrayList< GroupDTO >();

      if ( POSITION_GROUP_DTO != null && POSITION_GROUP_DTO.size() > 0 )
      {
         for ( GroupDTO groupDTO : POSITION_GROUP_DTO )
         {
            if ( groupDTO.getPositionGroupRelationVOs() != null && groupDTO.getPositionGroupRelationVOs().size() > 0 )
            {
               for ( PositionGroupRelationVO positionGroupRelationVO : groupDTO.getPositionGroupRelationVOs() )
               {
                  if ( KANUtil.filterEmpty( positionGroupRelationVO.getPositionId() ) != null && positionGroupRelationVO.getPositionId().trim().equals( positionId ) )
                  {
                     groupDTOs.add( groupDTO );
                     break;
                  }
               }
            }
         }
      }

      return groupDTOs;
   }

   // 按照OptionHeaderId获得对应的OptionDTO
   public OptionDTO getColumnOptionDTOByOptionHeaderId( final String optionHeaderId )
   {
      if ( COLUMN_OPTION_DTO != null && COLUMN_OPTION_DTO.size() > 0 )
      {
         for ( OptionDTO optionDTO : COLUMN_OPTION_DTO )
         {
            if ( optionDTO.getOptionHeaderVO() != null && optionDTO.getOptionHeaderVO().getOptionHeaderId() != null
                  && optionDTO.getOptionHeaderVO().getOptionHeaderId().trim().equals( optionHeaderId ) )
            {
               return optionDTO;
            }
         }
      }
      return null;
   }

   // 按照TableId获得对应的TableDTO
   public TableDTO getTableDTOByTableId( final String tableId )
   {
      if ( TABLE_DTO != null && TABLE_DTO.size() > 0 )
      {
         for ( TableDTO tableDTO : TABLE_DTO )
         {
            if ( tableDTO.getTableVO() != null && tableDTO.getTableVO().getTableId() != null && tableDTO.getTableVO().getTableId().trim().equals( tableId ) )
            {
               return tableDTO;
            }
         }
      }
      return null;
   }

   // 按照Access Action获得对应的TableDTO
   public TableDTO getTableDTOByAccessAction( final String accessAction )
   {
      if ( TABLE_DTO != null && TABLE_DTO.size() > 0 )
      {
         for ( TableDTO tableDTO : TABLE_DTO )
         {
            if ( tableDTO.getTableVO() != null && tableDTO.getTableVO().getAccessAction() != null && tableDTO.getTableVO().getAccessAction().trim().equals( accessAction ) )
            {
               return tableDTO;
            }
         }
      }
      return null;
   }

   // 按照Access Action获得对应的TableDTO
   public TableDTO getTableDTOByAccessAction( final List< String > accessActions, final String role )
   {
      if ( TABLE_DTO != null && TABLE_DTO.size() > 0 )
      {
         for ( TableDTO tableDTO : TABLE_DTO )
         {
            if ( tableDTO.getTableVO() != null && tableDTO.getTableVO().getAccessAction() != null && accessActions.contains( tableDTO.getTableVO().getAccessAction().trim() ) )
            {
               if ( role != null && role.equals( tableDTO.getTableVO().getRole() ) )
               {
                  if ( "1".equals( role ) && ( "1".equals( tableDTO.getTableVO().getRole() ) || "0".equals( tableDTO.getTableVO().getRole() ) ) )
                  {
                     return tableDTO;
                  }
                  else if ( "2".equals( role ) && ( "2".equals( tableDTO.getTableVO().getRole() ) || "0".equals( tableDTO.getTableVO().getRole() ) ) )
                  {
                     return tableDTO;
                  }
               }
               else
               {
                  if ( "0".equals( tableDTO.getTableVO().getRole() ) )
                  {
                     return tableDTO;
                  }
               }
            }
         }
      }
      return null;
   }

   // 获得Table对应的MappingVO
   // Added by Kevin Jin at 2014-03-08
   public List< MappingVO > getTables( final String localeLanguage, final String role, final String flag )
   {
      final List< MappingVO > tables = new ArrayList< MappingVO >();

      if ( KANUtil.filterEmpty( role ) != null || ACCOUNT_ID.equals( KANConstants.SUPER_ACCOUNT_ID ) )
      {
         // 遍历系统定义的Table       
         for ( TableDTO tableDTO : TABLE_DTO )
         {
            final TableVO tableVO = tableDTO.getTableVO();

            if ( tableVO != null
                  && ( KANUtil.filterEmpty( tableVO.getRole(), "0" ) == null || tableVO.getRole().equals( role ) || ACCOUNT_ID.equals( KANConstants.SUPER_ACCOUNT_ID ) ) )
            {
               if ( KANUtil.filterEmpty( flag ) == null
                     || ( flag.equals( TableVO.MANAGER ) && KANUtil.filterEmpty( tableVO.getCanManager() ) != null && tableVO.getCanManager().trim().equals( "1" ) )
                     || ( flag.equals( TableVO.LIST ) && KANUtil.filterEmpty( tableVO.getCanList() ) != null && tableVO.getCanList().trim().equals( "1" ) )
                     || ( flag.equals( TableVO.SEARCH ) && KANUtil.filterEmpty( tableVO.getCanSearch() ) != null && tableVO.getCanSearch().trim().equals( "1" ) )
                     || ( flag.equals( TableVO.REPORT ) && KANUtil.filterEmpty( tableVO.getCanReport() ) != null && tableVO.getCanReport().trim().equals( "1" ) )
                     || ( flag.equals( TableVO.IMPORT ) && KANUtil.filterEmpty( tableVO.getCanImport() ) != null && tableVO.getCanImport().trim().equals( "1" ) ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tableVO.getTableId() );

                  if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( tableVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( tableVO.getNameEN() );
                  }

                  tables.add( mappingVO );
               }
            }
         }

         // 排序  
         Collections.sort( tables, new Comparator< MappingVO >()
         {
            public int compare( MappingVO map1, MappingVO map2 )
            {
               return map1.getMappingValue().compareTo( map2.getMappingValue() );
            }
         } );
      }

      return tables;
   }

   // 按照positionId获得对应的 对应的StaffDTOs
   public List< StaffDTO > getStaffDTOsByPositionId( final String positionId )
   {
      final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

      for ( StaffDTO staffDTO : this.STAFF_DTO )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : staffDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId().equals( positionId ) )
            {
               staffDTOs.add( staffDTO );
            }
         }
      }

      return staffDTOs;
   }

   // 按照positionId获得对应的 对应的StaffDTOs
   // iclick by siuvan 过滤代理职位
   public List< StaffDTO > getNoAgentStaffDTOsByPositionId( final String positionId )
   {
      final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

      for ( StaffDTO staffDTO : this.STAFF_DTO )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : staffDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId().equals( positionId ) )
            {
               // 如果是代理职位，则只要在代理期间类的staff
               if ( !"2".equals( positionStaffRelationVO.getStaffType() ) )
               {
                  staffDTOs.add( staffDTO );
               }
            }
         }
      }

      return staffDTOs;
   }

   // 按照positionId获得对应的 对应的StaffDTOs
   public List< StaffDTO > getValidStaffDTOsByPositionId( final String positionId )
   {
      final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

      for ( StaffDTO staffDTO : this.STAFF_DTO )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : staffDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId().equals( positionId ) )
            {
               // 如果是代理职位，则只要在代理期间类的staff
               if ( "2".equals( positionStaffRelationVO.getStaffType() ) )
               {
                  String start = positionStaffRelationVO.getAgentStart();
                  String end = positionStaffRelationVO.getAgentEnd();
                  if ( start != null && end != null )
                  {
                     long startTime = KANUtil.createCalendar( start ).getTimeInMillis();
                     long endTime = KANUtil.createCalendar( end ).getTimeInMillis();
                     long nowTime = new Date().getTime();
                     if ( startTime < nowTime && nowTime < endTime )
                     {
                        staffDTOs.add( staffDTO );
                     }
                  }
               }
               else
               {
                  staffDTOs.add( staffDTO );
               }

            }
         }
      }

      return staffDTOs;
   }

   // 按照employeeId获得对应的 对应的StaffDTOs
   public List< StaffDTO > getStaffDTOsByEmployeeId( final String employeeId )
   {
      final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

      for ( StaffDTO staffDTO : this.STAFF_DTO )
      {
         if ( staffDTO != null && staffDTO.getStaffVO() != null )
         {
            if ( KANUtil.filterEmpty( staffDTO.getStaffVO().getEmployeeId() ) != null && KANUtil.filterEmpty( employeeId ) != null
                  && staffDTO.getStaffVO().getEmployeeId().equals( employeeId ) )
            {
               staffDTOs.add( staffDTO );
            }
         }
      }

      return staffDTOs;
   }

   // 按照userId 获取对应的staffDTO
   public StaffDTO getStaffDTOByUserId( final String userId )
   {
      StaffDTO staffDTO = null;
      for ( StaffDTO tempStaffDTO : this.STAFF_DTO )
      {
         if ( tempStaffDTO != null && tempStaffDTO.getStaffVO() != null )
         {
            if ( tempStaffDTO.getUserVO() != null && KANUtil.filterEmpty( tempStaffDTO.getUserVO().getUserId() ) != null && KANUtil.filterEmpty( userId ) != null
                  && tempStaffDTO.getUserVO().getUserId().equals( userId ) )
            {
               staffDTO = tempStaffDTO;
               break;
            }
         }
      }
      return staffDTO;
   }

   // 根据positionId获取下属positionId
   public List< String > getChildPositionIdsByPositionId( final String positionId )
   {
      final List< String > childPositionIds = new ArrayList< String >();
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );

      if ( positionDTO != null )
      {
         childPositionIds.addAll( positionDTO.getChildPositionIds() );
      }
      return childPositionIds;
   }

   // 根据positionId获取下属positionId(仅仅直线下属)
   public List< String > getAllChildPositionIdsByPositionId( final String parentPositionId )
   {
      final List< String > childPositionIds = new ArrayList< String >();
      final PositionDTO positionDTO = getPositionDTOByPositionId( parentPositionId );

      if ( positionDTO != null && positionDTO.getPositionDTOs() != null )
      {
         for ( PositionDTO subPositionDTO : positionDTO.getPositionDTOs() )
         {
            childPositionIds.add( subPositionDTO.getPositionVO().getPositionId() );
         }
      }

      return childPositionIds;
   }

   public List< String > getAllChildPositionIdsByPositionId( final List< String > childPositionIds, final List< PositionDTO > subPositionDTOs )
   {
      if ( subPositionDTOs != null && subPositionDTOs.size() > 0 )
      {
         for ( PositionDTO subPositionDTO : subPositionDTOs )
         {
            childPositionIds.addAll( subPositionDTO.getChildPositionIds() );

            if ( subPositionDTO.getPositionDTOs() != null && subPositionDTO.getChildPositionIds().size() > 0 )
            {
               getAllChildPositionIdsByPositionId( childPositionIds, subPositionDTO.getPositionDTOs() );
            }
         }
      }

      return childPositionIds;
   }

   // 按照ColumnId获得ColumnVO对象
   public ColumnVO getColumnVOByColumnId( final String columnId )
   {
      if ( TABLE_DTO != null && TABLE_DTO.size() > 0 )
      {
         for ( TableDTO tableDTO : TABLE_DTO )
         {
            final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( columnId );
            if ( columnVO != null )
            {
               return columnVO;
            }
         }
      }

      return null;
   }

   // 获取Calendar对应的MappingVO
   public List< MappingVO > getCalendar( final String localeLanguage )
   {
      return getCalendar( localeLanguage, null );
   }

   // 获取Calendar对应的MappingVO
   public List< MappingVO > getCalendar( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > calendars = new ArrayList< MappingVO >();

      // 遍历CALENDAR_DTO
      if ( CALENDAR_DTO != null && CALENDAR_DTO.size() > 0 )
      {
         for ( CalendarDTO calendarDTO : CALENDAR_DTO )
         {
            if ( ( KANUtil.filterEmpty( calendarDTO.getCalendarHeaderVO().getAccountId() ) != null && calendarDTO.getCalendarHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( calendarDTO.getCalendarHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( calendarDTO.getCalendarHeaderVO().getCorpId() ) != null && calendarDTO.getCalendarHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( calendarDTO.getCalendarHeaderVO().getHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( calendarDTO.getCalendarHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( calendarDTO.getCalendarHeaderVO().getNameEN() );
               }

               calendars.add( mappingVO );
            }
         }
      }

      return calendars;
   }

   // 获取BankTemplate对应的MappingVO
   public List< MappingVO > getBankTemplate( final String localeLanguage )
   {
      return getBankTemplate( localeLanguage, null );
   }

   // 获取BankTemplate对应的MappingVO
   public List< MappingVO > getBankTemplate( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > bankTemplates = new ArrayList< MappingVO >();

      // 遍历BANK_TEMPLATE_DTO
      if ( BANK_TEMPLATE_DTO != null && BANK_TEMPLATE_DTO.size() > 0 )
      {
         for ( BankTemplateDTO bankTemplateDTO : BANK_TEMPLATE_DTO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( bankTemplateDTO.getBankTemplateHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( bankTemplateDTO.getBankTemplateHeaderVO().getCorpId() ) != null && bankTemplateDTO.getBankTemplateHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( bankTemplateDTO.getBankTemplateHeaderVO().getTemplateHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( bankTemplateDTO.getBankTemplateHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( bankTemplateDTO.getBankTemplateHeaderVO().getNameEN() );
               }

               bankTemplates.add( mappingVO );
            }
         }
      }

      return bankTemplates;
   }

   // 获取TaxTemplate对应的MappingVO
   public List< MappingVO > getTaxTemplate( final String localeLanguage )
   {
      return getTaxTemplate( localeLanguage, null );
   }

   // 获取TaxTemplate对应的MappingVO
   public List< MappingVO > getTaxTemplate( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > taxTemplates = new ArrayList< MappingVO >();

      // 遍历TAX_TEMPLATE_DTO
      if ( TAX_TEMPLATE_DTO != null && TAX_TEMPLATE_DTO.size() > 0 )
      {
         for ( TaxTemplateDTO taxTemplateDTO : TAX_TEMPLATE_DTO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( taxTemplateDTO.getTaxTemplateHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( taxTemplateDTO.getTaxTemplateHeaderVO().getCorpId() ) != null && taxTemplateDTO.getTaxTemplateHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( taxTemplateDTO.getTaxTemplateHeaderVO().getTemplateHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( taxTemplateDTO.getTaxTemplateHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( taxTemplateDTO.getTaxTemplateHeaderVO().getNameEN() );
               }

               taxTemplates.add( mappingVO );
            }
         }
      }

      return taxTemplates;
   }

   // 获取Shift对应的MappingVO
   public List< MappingVO > getShift( final String localeLanguage )
   {
      return getShift( localeLanguage, null );
   }

   // 获取Shift对应的MappingVO
   public List< MappingVO > getShift( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > shifts = new ArrayList< MappingVO >();

      // 遍历SHIFT_DTO
      if ( SHIFT_DTO != null && SHIFT_DTO.size() > 0 )
      {
         for ( ShiftDTO shiftDTO : SHIFT_DTO )
         {
            if ( ( KANUtil.filterEmpty( shiftDTO.getShiftHeaderVO().getAccountId() ) != null && shiftDTO.getShiftHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( shiftDTO.getShiftHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( shiftDTO.getShiftHeaderVO().getCorpId() ) != null && shiftDTO.getShiftHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( shiftDTO.getShiftHeaderVO().getHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( shiftDTO.getShiftHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( shiftDTO.getShiftHeaderVO().getNameEN() );
               }

               shifts.add( mappingVO );
            }
         }
      }
      return shifts;
   }

   // 根据HeaderId获得CalendarDTO
   public CalendarDTO getCalendarDTOByHeaderId( final String headerId ) throws KANException
   {
      if ( CALENDAR_DTO != null && CALENDAR_DTO.size() > 0 )
      {
         for ( CalendarDTO calendarDTO : CALENDAR_DTO )
         {
            if ( calendarDTO.getCalendarHeaderVO().getHeaderId().equals( headerId.trim() ) )
            {
               return calendarDTO;
            }
         }
      }

      return null;
   }

   // 根据HeaderId获得ShiftDTO
   public ShiftDTO getShiftDTOByHeaderId( final String headerId ) throws KANException
   {
      if ( SHIFT_DTO != null && SHIFT_DTO.size() > 0 )
      {
         for ( ShiftDTO shiftDTO : SHIFT_DTO )
         {
            if ( shiftDTO.getShiftHeaderVO().getHeaderId().equals( headerId.trim() ) )
            {
               return shiftDTO;
            }
         }
      }

      return null;
   }

   // 获取Sick Leave Salary对应的MappingVO
   public List< MappingVO > getSickLeaveSalary( final String localeLanguage )
   {
      return getSickLeaveSalary( localeLanguage, null );
   }

   // 获取Sick Leave Salary对应的MappingVO
   public List< MappingVO > getSickLeaveSalary( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > sickLeaveSalarys = new ArrayList< MappingVO >();

      // 遍历SICK_LEAVE_SALARY_DTO
      if ( SICK_LEAVE_SALARY_DTO != null && SICK_LEAVE_SALARY_DTO.size() > 0 )
      {
         for ( SickLeaveSalaryDTO sickLeaveSalaryDTO : SICK_LEAVE_SALARY_DTO )
         {
            if ( ( KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getAccountId() ) != null && sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getCorpId() ) != null && sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getNameEN() );
               }

               sickLeaveSalarys.add( mappingVO );
            }
         }
      }
      return sickLeaveSalarys;
   }

   // 获取Annual Leave Rule对应的MappingVO
   public List< MappingVO > getAnnualLeaveRules( final String localeLanguage )
   {
      return getAnnualLeaveRules( localeLanguage, null );
   }

   // 获取Annual Leave Rule对应的MappingVO
   public List< MappingVO > getAnnualLeaveRules( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > annualLeaveRules = new ArrayList< MappingVO >();

      // 遍历ANNUAL_LEAVE_RULE_DTO
      if ( ANNUAL_LEAVE_RULE_DTO != null && ANNUAL_LEAVE_RULE_DTO.size() > 0 )
      {
         for ( AnnualLeaveRuleDTO annualLeaveRuleDTO : ANNUAL_LEAVE_RULE_DTO )
         {
            if ( ( KANUtil.filterEmpty( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getAccountId() ) != null && annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getCorpId() ) != null && annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // 初始化MappingVO对象
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getHeaderId() );

               if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getNameEN() );
               }

               annualLeaveRules.add( mappingVO );
            }
         }
      }

      return annualLeaveRules;
   }

   // 根据HeaderId获得SickLeaveSalaryDTO
   public SickLeaveSalaryDTO getSickLeaveSalaryDTOByHeaderId( final String headerId ) throws KANException
   {
      if ( SICK_LEAVE_SALARY_DTO != null && SICK_LEAVE_SALARY_DTO.size() > 0 )
      {
         for ( SickLeaveSalaryDTO sickLeaveSalaryDTO : SICK_LEAVE_SALARY_DTO )
         {
            if ( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getHeaderId().equals( headerId.trim() ) )
            {
               return sickLeaveSalaryDTO;
            }
         }
      }

      return null;
   }

   // 根据HeaderId获得AnnualLeaveRuleDTO
   public AnnualLeaveRuleDTO getAnnualLeaveRuleDTOByHeaderId( final String headerId ) throws KANException
   {
      if ( ANNUAL_LEAVE_RULE_DTO != null && ANNUAL_LEAVE_RULE_DTO.size() > 0 )
      {
         for ( AnnualLeaveRuleDTO annualLeaveRuleDTO : ANNUAL_LEAVE_RULE_DTO )
         {
            if ( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getHeaderId().equals( headerId.trim() ) )
            {
               return annualLeaveRuleDTO;
            }
         }
      }

      return null;
   }

   // 初始化月份周次，往前推3年
   public List< MappingVO > getWeeks( final String localeLanguage )
   {
      return getWeeks( localeLanguage, null );
   }

   // 初始化月份周次，往前推3年
   public List< MappingVO > getWeeks( final String localeLanguage, final String corpId )
   {
      return KANUtil.getWeeksByCondition( 162, 1 );
   }

   // 初始化月份，最近36月（含次月）
   public List< MappingVO > getLast36Months( final String localeLanguage )
   {
      return getLast36Months( localeLanguage, null );
   }

   // 初始化月份，最近36月（含次月）
   public List< MappingVO > getLast36Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 36, 1 );
   }

   // 初始化月份，最近12月（含次月）
   public List< MappingVO > getLast12Months( final String localeLanguage )
   {
      return getLast12Months( localeLanguage, null );
   }

   // 初始化月份，最近12月（含次月）
   public List< MappingVO > getLast12Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 12, 1 );
   }

   // 初始化月份，最近2月（含次月）
   public List< MappingVO > getLast2Months( final String localeLanguage )
   {
      return getLast2Months( localeLanguage, null );
   }

   // 初始化月份，最近2月（含次月）
   public List< MappingVO > getLast2Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 2, 1 );
   }

   // 初始化月份，最近4月（含后3个月）
   public List< MappingVO > getLast4Months( final String localeLanguage )
   {
      return getLast4Months( localeLanguage, null );
   }

   // 初始化月份，最近4月（含后3个月）
   public List< MappingVO > getLast4Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 4, 3 );
   }

   // 初始化年份，最近10年
   public List< MappingVO > getLast5Years( final String localeLanguage, final String corpId )
   {
      return KANUtil.getYears( Integer.valueOf( KANUtil.getYear( new Date() ) ) - 2, Integer.valueOf( KANUtil.getYear( new Date() ) ) + 3 );
   }

   // 根据TaxId获得TaxVO
   public TaxVO getTaxVOByTaxId( final String taxId ) throws KANException
   {
      if ( TAX_VO != null && TAX_VO.size() > 0 )
      {
         for ( TaxVO taxVO : TAX_VO )
         {
            if ( taxVO.getTaxId().equals( taxId ) )
            {
               return taxVO;
            }
         }
      }

      return null;
   }

   // 根据EntityId和BusinessTypeId获得TaxVO列表
   public List< TaxVO > getTaxVOs( final String entityId, final String businessTypeId ) throws KANException
   {
      final List< TaxVO > taxVOs = new ArrayList< TaxVO >();

      if ( TAX_VO != null && TAX_VO.size() > 0 )
      {
         for ( TaxVO taxVO : TAX_VO )
         {
            if ( taxVO.getEntityId().equals( entityId ) && taxVO.getBusinessTypeId().equals( businessTypeId ) )
            {
               taxVOs.add( taxVO );
            }
         }
      }

      return taxVOs;
   }

   // 根据BankId获得的BankVO
   public BankVO getBankVOByBankId( final String bankId )
   {
      // 遍历BankVO List
      if ( BANK_VO != null && BANK_VO.size() > 0 )
      {
         for ( BankVO bankVO : BANK_VO )
         {
            if ( bankVO.getBankId().trim().equals( bankId ) )
            {
               return bankVO;
            }
         }
      }

      return null;
   }

   // 根据ItemId获得ItemVO
   public ItemVO getItemVOByItemId( final String itemId ) throws KANException
   {
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( itemVO.getItemId().equals( itemId ) )
            {
               return itemVO;
            }
         }
      }

      return null;
   }

   // 根据ItemName获得ItemVO
   public ItemVO getItemVOByItemName( final String itemName, final String corpId ) throws KANException
   {
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( ( ( KANUtil.filterEmpty( corpId ) == null && itemVO.getCorpId() == null ) || ( KANUtil.filterEmpty( itemVO.getCorpId() ) == null || KANUtil.filterEmpty( itemVO.getCorpId() ).equals( corpId ) ) )
                  && ( ( KANUtil.filterEmpty( itemVO.getNameZH() ) != null && KANUtil.filterEmpty( itemVO.getNameZH() ).equals( itemName.trim() ) ) || ( KANUtil.filterEmpty( itemVO.getNameEN() ) != null && KANUtil.filterEmpty( itemVO.getNameEN() ).equals( itemName ) ) ) )
            {
               return itemVO;
            }
         }
      }

      return null;
   }

   // 根据ItemName获得ItemVO
   public ItemVO getItemVOByItemName( final String itemName ) throws KANException
   {
      return getItemVOByItemName( itemName, null );
   }

   // 根据ItemGroupId获得ItemGroupDTO
   public ItemGroupDTO getItemGroupDTOByItemGroupId( final String itemGroupId ) throws KANException
   {
      if ( ITEM_GROUP_DTO != null && ITEM_GROUP_DTO.size() > 0 )
      {
         for ( ItemGroupDTO itemGroupDTO : ITEM_GROUP_DTO )
         {
            if ( itemGroupDTO.getItemGroupVO().getItemGroupId().equals( itemGroupId ) )
            {
               return itemGroupDTO;
            }
         }
      }

      return null;
   }

   // 根据ItemId获得ItemGroupDTO
   public ItemGroupDTO getItemGroupDTOByItemId( final String itemId ) throws KANException
   {
      if ( ITEM_GROUP_DTO != null && ITEM_GROUP_DTO.size() > 0 )
      {
         for ( ItemGroupDTO itemGroupDTO : ITEM_GROUP_DTO )
         {
            if ( itemGroupDTO.getItemVOs() != null && itemGroupDTO.getItemVOs().size() > 0 )
            {
               for ( ItemVO itemVO : itemGroupDTO.getItemVOs() )
               {
                  if ( itemVO.getItemId() != null && itemVO.getItemId().equals( itemId ) )
                  {
                     return itemGroupDTO;
                  }
               }
            }
         }
      }

      return null;
   }

   // 根据ItemId以及ItemGroupType获得ItemGroupDTO
   public ItemGroupDTO getItemGroupDTOByItemId( final String itemId, final String itemGroupType ) throws KANException
   {
      if ( ITEM_GROUP_DTO != null && ITEM_GROUP_DTO.size() > 0 )
      {
         for ( ItemGroupDTO itemGroupDTO : ITEM_GROUP_DTO )
         {
            if ( itemGroupDTO.getItemGroupVO() != null
                  && ( KANUtil.filterEmpty( itemGroupType ) == null || ( KANUtil.filterEmpty( itemGroupDTO.getItemGroupVO().getItemGroupType() ) != null && itemGroupDTO.getItemGroupVO().getItemGroupType().equals( itemGroupType ) ) )
                  && itemGroupDTO.getItemVOs() != null && itemGroupDTO.getItemVOs().size() > 0 )
            {
               for ( ItemVO itemVO : itemGroupDTO.getItemVOs() )
               {
                  if ( itemVO.getItemId() != null && itemVO.getItemId().equals( itemId ) )
                  {
                     return itemGroupDTO;
                  }
               }
            }
         }
      }

      return null;
   }

   // Reviewed by Kevin Jin at 2014-05-13
   public boolean hasAuthority( final UserVO userVO, final String accessAction, final String rightId )
   {
      // 初始化ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      if ( moduleDTO != null )
      {
         final String moduleId = moduleDTO.getModuleVO().getModuleId();

         // 如果是供应商登录，默认都有权限
         if ( userVO != null && userVO.getRole().equals( KANConstants.ROLE_VENDOR ) )
         {
            return true;
         }

         // 根据AccountId 查看是否有权限
         // 判断全局是否有设置
         final AccountModuleDTO accountModuleDTO = getAccountModuleDTOByModuleId( moduleId );

         if ( accountModuleDTO != null && accountModuleDTO.getAccountRightVOs() != null && accountModuleDTO.getAccountRightVOs().size() > 0 )
         {
            for ( RightVO rightVO : accountModuleDTO.getAccountRightVOs() )
            {
               if ( rightVO.getRightId().equals( rightId ) )
               {
                  return true;
               }
            }
         }

         // 获得当前User的Positions
         /*  final List< MappingVO > positions = userVO.getPositions();

           for ( MappingVO mappingVO : positions )
           {*/
         // 初始化PositionDTO
         final PositionDTO postionDTO = getPositionDTOByPositionId( userVO.getPositionId() );
         if ( postionDTO != null )
         {
            // 判断职位是否有设置
            if ( postionDTO.getPositionModuleDTOs() != null && postionDTO.getPositionModuleDTOs().size() > 0 )
            {
               // 根据PositionIds查看是否有权限 
               for ( PositionModuleDTO positionModuleDTO : postionDTO.getPositionModuleDTOs() )
               {
                  if ( positionModuleDTO.getModuleVO() != null && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ) != null
                        && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ).equals( moduleId ) )
                  {
                     // 初始化RightVO列表
                     final List< RightVO > rightVOs = positionModuleDTO.getSelectedRightVOs();

                     for ( RightVO rightVO : rightVOs )
                     {
                        if ( rightVO.getRightId().equals( rightId ) )
                        {
                           return true;
                        }
                     }
                  }
               }
            }

            // 根据PostionGroup查看是否有权限
            final List< PositionGroupRelationVO > positionGroupRelationVOs = postionDTO.getPositionGroupRelationVOs();

            // 判断职位组是否有设置
            if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
            {
               for ( PositionGroupRelationVO positionGroupRelationVO : positionGroupRelationVOs )
               {
                  // 初始化GroupId
                  final String groupId = positionGroupRelationVO.getGroupId();
                  // 初始化GroupDTO
                  final GroupDTO groupDTO = getGroupDTOByGroupId( groupId );

                  if ( groupDTO != null )
                  {
                     // 初始化GroupModuleDTO
                     final GroupModuleDTO groupModuleDTO = groupDTO.getGroupModuleDTOByModuleId( moduleId );

                     if ( groupModuleDTO != null )
                     {
                        // 初始化GroupModuleDTO
                        final List< GroupModuleRightRelationVO > groupModuleRightrelationVOs = groupModuleDTO.getGroupModuleRightRelationVOs();

                        for ( GroupModuleRightRelationVO groupModuleRightRelationVO : groupModuleRightrelationVOs )
                        {
                           if ( groupModuleRightRelationVO.getRightId().equals( rightId ) )
                           {
                              return true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   /**
    * 判断当前position是否有菜单权限，针对一个user有多个position的处理
    * @param userVO
    * @param moduleDTO
    * @param rightId
    * @return
    */
   public boolean hasMenuAuthority( final UserVO userVO, ModuleDTO moduleDTO, final String rightId )
   {

      if ( moduleDTO != null && userVO != null )
      {
         final String moduleId = moduleDTO.getModuleVO().getModuleId();

         // 根据AccountId 查看是否有权限
         // 判断全局是否有设置
         final AccountModuleDTO accountModuleDTO = getAccountModuleDTOByModuleId( moduleId );

         if ( accountModuleDTO != null && accountModuleDTO.getAccountRightVOs() != null && accountModuleDTO.getAccountRightVOs().size() > 0 )
         {
            for ( RightVO rightVO : accountModuleDTO.getAccountRightVOs() )
            {
               if ( rightVO.getRightId().equals( rightId ) )
               {
                  return true;
               }
            }
         }

         // 获得当前User的Positions
         /*   final List< MappingVO > positions = userVO.getPositions();
         
         for ( MappingVO mappingVO : positions )
         {
            // 初始化PositionDTO
            final PositionDTO postionDTO = getPositionDTOByPositionId( mappingVO.getMappingId() );*/
         final PositionDTO postionDTO = getPositionDTOByPositionId( userVO.getPositionId() );

         // 判断职位是否有设置
         if ( postionDTO != null && postionDTO.getPositionModuleDTOs() != null && postionDTO.getPositionModuleDTOs().size() > 0 )
         {
            // 根据PositionIds查看是否有权限 
            for ( PositionModuleDTO positionModuleDTO : postionDTO.getPositionModuleDTOs() )
            {
               if ( positionModuleDTO.getModuleVO() != null && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ) != null
                     && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ).equals( moduleId ) )
               {
                  // 初始化RightVO列表
                  final List< RightVO > rightVOs = positionModuleDTO.getSelectedRightVOs();

                  for ( RightVO rightVO : rightVOs )
                  {
                     if ( rightVO.getRightId().equals( rightId ) )
                     {
                        return true;
                     }
                  }
               }
            }
         }

         // 判断职位组是否有设置
         if ( postionDTO != null && postionDTO.getPositionGroupRelationVOs() != null && postionDTO.getPositionGroupRelationVOs().size() > 0 )
         {
            // 根据PostionGroup查看是否有权限
            final List< PositionGroupRelationVO > positionGroupRelationVOs = postionDTO.getPositionGroupRelationVOs();
            for ( PositionGroupRelationVO positionGroupRelationVO : positionGroupRelationVOs )
            {
               // 初始化GroupId
               final String groupId = positionGroupRelationVO.getGroupId();
               // 初始化GroupDTO
               final GroupDTO groupDTO = getGroupDTOByGroupId( groupId );

               if ( groupDTO != null )
               {
                  // 初始化GroupModuleDTO
                  final GroupModuleDTO groupModuleDTO = groupDTO.getGroupModuleDTOByModuleId( moduleId );

                  if ( groupModuleDTO != null )
                  {
                     // 初始化GroupModuleDTO
                     final List< GroupModuleRightRelationVO > groupModuleRightrelationVOs = groupModuleDTO.getGroupModuleRightRelationVOs();

                     for ( GroupModuleRightRelationVO groupModuleRightRelationVO : groupModuleRightrelationVOs )
                     {
                        if ( groupModuleRightRelationVO.getRightId().equals( rightId ) )
                        {
                           return true;
                        }
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   /**
    * 取得当前用户的module规则(包括全局规则和职位规则)
    * @param loginUserVO
    * @param accessAction
    * @return
    * @author jack.sun
    * @date 2014-4-24
    */
   public Set< RuleVO > getRuleVOs( final UserVO loginUserVO, final String accessAction )
   {
      Set< RuleVO > ruleSet = new HashSet< RuleVO >();
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );
      if ( moduleDTO != null )
      {
         final String moduleId = moduleDTO.getModuleVO().getModuleId();

         // 根据accountId 获取规则（全局规则）
         AccountModuleDTO accountModuleDTO = getAccountModuleDTOByModuleId( moduleId );
         if ( accountModuleDTO != null )
         {
            List< RuleVO > accountRuleVOs = accountModuleDTO.getAccountRuleVOs();
            if ( accountRuleVOs != null )
            {
               ruleSet.addAll( accountRuleVOs );
            }
         }
         /*  final List< MappingVO > positions = loginUserVO.getPositions();
           for ( MappingVO mappingVO : positions )
           {
              PositionDTO postionDTO = getPositionDTOByPositionId( mappingVO.getMappingId() );*/
         final PositionDTO postionDTO = getPositionDTOByPositionId( loginUserVO.getPositionId() );
         if ( postionDTO == null )
            return ruleSet;
         List< PositionModuleDTO > positionModuleDTOs = postionDTO.getPositionModuleDTOs();
         if ( positionModuleDTOs != null )
         {
            // 根据positionIds 获取规则 
            for ( PositionModuleDTO positionModuleDTO : positionModuleDTOs )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId() != null )
               {
                  String moduleId1 = positionModuleDTO.getModuleVO().getModuleId();
                  if ( moduleId1.equals( moduleId ) )
                  {
                     List< RuleVO > ruleVOs = positionModuleDTO.getSelectedRuleVOs();
                     if ( ruleVOs != null )
                     {
                        ruleSet.addAll( ruleVOs );
                     }
                  }
               }
            }
         }
         // 根据postionGroup 查看是否有规则
         List< PositionGroupRelationVO > positionGroupRelationVOs = postionDTO.getPositionGroupRelationVOs();
         if ( positionGroupRelationVOs != null )
         {
            for ( PositionGroupRelationVO positionGroupRelationVO : positionGroupRelationVOs )
            {
               String groupId = positionGroupRelationVO.getGroupId();

               GroupDTO groupDTO = getGroupDTOByGroupId( groupId );
               if ( groupDTO != null )
               {
                  GroupModuleDTO groupModuleDTO = groupDTO.getGroupModuleDTOByModuleId( moduleId );
                  if ( groupModuleDTO != null )
                  {
                     List< GroupModuleRuleRelationVO > groupModuleRulerelationVOs = groupModuleDTO.getGroupModuleRuleRelationVOs();
                     for ( GroupModuleRuleRelationVO groupModuleRuleRelationVO : groupModuleRulerelationVOs )
                     {
                        RuleVO rule = KANConstants.getRuleVOByRuleId( groupModuleRuleRelationVO.getRuleId() );
                        if ( rule != null )
                        {
                           ruleSet.add( rule );
                        }

                     }
                  }
               }
            }
         }
      }
      return ruleSet;
   }

   /**
    * 取得当前用户的module规则(包括全局规则和职位规则)
    * @param loginUserVO
    * @param accessAction
    * @return
    * @author jack.sun
    * @date 2014-4-24
    */
   public Set< RuleVO > getRuleVOsByOwner( final String owner, final String accessAction )
   {
      Set< RuleVO > ruleSet = new HashSet< RuleVO >();

      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );
      if ( moduleDTO != null )
      {
         final String moduleId = moduleDTO.getModuleVO().getModuleId();

         // 根据accountId 获取规则（全局规则）
         AccountModuleDTO accountModuleDTO = getAccountModuleDTOByModuleId( moduleId );
         if ( accountModuleDTO != null )
         {
            List< RuleVO > accountRuleVOs = accountModuleDTO.getAccountRuleVOs();
            if ( accountRuleVOs != null )
            {
               ruleSet.addAll( accountRuleVOs );
            }
         }

         PositionDTO postionDTO = getPositionDTOByPositionId( owner );
         if ( postionDTO == null )
         {
            return ruleSet;
         }

         List< PositionModuleDTO > positionModuleDTOs = postionDTO.getPositionModuleDTOs();
         if ( positionModuleDTOs != null )
         {
            // 根据positionIds 获取规则 
            for ( PositionModuleDTO positionModuleDTO : positionModuleDTOs )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId() != null )
               {
                  String moduleId1 = positionModuleDTO.getModuleVO().getModuleId();
                  if ( moduleId1.equals( moduleId ) )
                  {
                     List< RuleVO > ruleVOs = positionModuleDTO.getSelectedRuleVOs();
                     if ( ruleVOs != null )
                     {
                        ruleSet.addAll( ruleVOs );
                     }
                  }
               }
            }
         }

         // 根据postionGroup 查看是否有规则
         List< PositionGroupRelationVO > positionGroupRelationVOs = postionDTO.getPositionGroupRelationVOs();
         if ( positionGroupRelationVOs != null )
         {
            for ( PositionGroupRelationVO positionGroupRelationVO : positionGroupRelationVOs )
            {
               String groupId = positionGroupRelationVO.getGroupId();

               GroupDTO groupDTO = getGroupDTOByGroupId( groupId );
               if ( groupDTO != null )
               {
                  GroupModuleDTO groupModuleDTO = groupDTO.getGroupModuleDTOByModuleId( moduleId );
                  if ( groupModuleDTO != null )
                  {
                     List< GroupModuleRuleRelationVO > groupModuleRulerelationVOs = groupModuleDTO.getGroupModuleRuleRelationVOs();
                     for ( GroupModuleRuleRelationVO groupModuleRuleRelationVO : groupModuleRulerelationVOs )
                     {
                        RuleVO rule = KANConstants.getRuleVOByRuleId( groupModuleRuleRelationVO.getRuleId() );
                        if ( rule != null )
                        {
                           ruleSet.add( rule );
                        }

                     }
                  }
               }
            }
         }
      }
      return ruleSet;
   }

   // 得到当前职位上绑定的员工，按语言显示姓名
   public String getStaffNamesByPositionId( final String localeLanguage, final String positionId )
   {
      String staffNames = "";

      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // 遍历Relation对象
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // 得到Constants中的staffVO
               final StaffDTO staffDTO = getStaffDTOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffDTO != null )
               {
                  final StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     // 如果是职位绑定多个员工，使用“,”分割
                     if ( !staffNames.trim().equals( "" ) )
                     {
                        staffNames = staffNames + ", ";
                     }

                     // 按照当前语言取得员工姓名
                     if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                     {
                        staffNames = staffNames + staffVO.getNameZH();
                     }
                     else
                     {
                        staffNames = staffNames + staffVO.getNameEN();
                     }

                     // 如果是代理员工
                     if ( positionStaffRelationVO.getStaffType() != null && positionStaffRelationVO.getStaffType().trim().equals( "2" ) )
                     {
                        if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                        {
                           staffNames = staffNames + "（代）";
                        }
                        else
                        {
                           staffNames = staffNames + "(Agent)";
                        }
                     }
                  }
               }
            }
         }
      }

      return ( staffNames != null && !staffNames.isEmpty() ? staffNames : "" );
   }

   // 得到当前职位上绑定的员工，按语言显示姓名,只显示前3个。
   public String get3StaffNamesByPositionId( final String localeLanguage, final String positionId )
   {
      String staffNames = "";

      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // 记录员工个数
         int index = 0;
         // 遍历Relation对象
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // 得到Constants中的staffVO
               final StaffDTO staffDTO = getStaffDTOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffDTO != null )
               {
                  final StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     // 按照当前语言取得员工姓名
                     if ( index < 3 )
                     {
                        // 如果是职位绑定多个员工，使用“,”分割
                        if ( !staffNames.trim().equals( "" ) )
                        {
                           staffNames = staffNames + ", ";
                        }
                        if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                        {
                           staffNames = staffNames + staffVO.getNameZH();
                        }
                        else
                        {
                           staffNames = staffNames + staffVO.getNameEN();
                        }

                        // 如果是代理员工
                        if ( positionStaffRelationVO.getStaffType() != null && positionStaffRelationVO.getStaffType().trim().equals( "2" ) )
                        {
                           if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                           {
                              staffNames = staffNames + "（代）";
                           }
                           else
                           {
                              staffNames = staffNames + "(Agent)";
                           }
                        }
                     }

                     index++;
                  }
               }
            }
         }
         if ( index > 3 )
         {
            staffNames = staffNames + ", 等(" + index + ")人";
         }
      }

      return ( staffNames != null && !staffNames.isEmpty() ? staffNames : "" );
   }

   // 得到当前职位上绑定的员工人数
   public int getStaffNumByPositionId( final String localeLanguage, final String positionId )
   {

      // 记录员工个数
      int index = 0;
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );
      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // 遍历Relation对象
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // 得到Constants中的staffVO
               final StaffDTO staffDTO = getStaffDTOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffDTO != null )
               {
                  final StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     index++;
                  }
               }
            }
         }
      }

      return index;
   }

   // 得到当前职位上绑定的员工，按语言显示姓名
   public List< StaffVO > getStaffArrayByPositionId( final String positionId )
   {
      List< StaffVO > staffNameArray = new ArrayList< StaffVO >();
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // 遍历Relation对象
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // 得到Constants中的staffVO
               final StaffDTO staffDTO = getStaffDTOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffDTO != null )
               {
                  final StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     staffNameArray.add( staffVO );
                  }
               }
            }
         }
      }

      return staffNameArray;
   }

   // Added by Jixiang.hu 2013-12-19
   public ImportDTO getImportDTOByImportHeadId( final String importHeadId, final String corpId )
   {

      if ( IMPORT_DTO != null && KANUtil.filterEmpty( importHeadId ) != null )
      {
         for ( ImportDTO importDTO : IMPORT_DTO )
         {
            if ( importDTO != null && importDTO.getImportHeaderVO() != null && importHeadId.equals( importDTO.getImportHeaderVO().getImportHeaderId() ) )
            {
               if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( importDTO.getImportHeaderVO().getCorpId() ) == null )
               {
                  return importDTO;
               }
               else if ( KANUtil.filterEmpty( corpId ) != null && corpId.equals( importDTO.getImportHeaderVO().getCorpId() ) )
               {
                  return importDTO;
               }

            }
         }
      }
      return null;
   }

   public ImportDTO getImportDTOByImportHeadId( final String importHeadId )
   {
      return getImportDTOByImportHeadId( importHeadId, null );
   }

   // Added by Jixiang.hu 2013-12-20
   public ImportDTO getImportDTOByTableId( final String tableId, final String corpId )
   {

      if ( IMPORT_DTO != null && KANUtil.filterEmpty( tableId ) != null )
      {
         for ( ImportDTO importDTO : IMPORT_DTO )
         {
            if ( importDTO != null && importDTO.getImportHeaderVO() != null && tableId.equals( importDTO.getImportHeaderVO().getTableId() ) )
            {
               if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( importDTO.getImportHeaderVO().getCorpId() ) == null )
               {
                  return importDTO;
               }
               else if ( KANUtil.filterEmpty( corpId ) != null && corpId.equals( importDTO.getImportHeaderVO().getCorpId() ) )
               {
                  return importDTO;
               }
            }
         }
      }
      return null;
   }

   // Added by Jixiang.hu 2013-12-20
   public ImportDTO getImportDTOByTableId( final String tableId )
   {
      return getImportDTOByTableId( tableId, null );
   }

   // Added by Jixiang.hu 2013-12-10
   public ImportDTO getImportDTOByAccessAction( final String accessAction, final String corpId )
   {

      final TableDTO tableDTO = getTableDTOByAccessAction( accessAction );
      if ( tableDTO != null && tableDTO.getTableVO() != null )
      {
         final String tableId = tableDTO.getTableVO().getTableId();
         if ( KANUtil.filterEmpty( tableId ) != null )
         {
            return getImportDTOByTableId( tableId, corpId );
         }
      }
      return null;
   }

   // Added by siuxia 2014-3-3
   public ReportDTO getReportDTOByReportHeaderId( final String reportHeaderId )
   {
      if ( REPORT_DTO != null && REPORT_DTO.size() > 0 && KANUtil.filterEmpty( reportHeaderId ) != null )
      {
         for ( ReportDTO reportDTO : REPORT_DTO )
         {
            if ( reportDTO != null && reportDTO.getReportHeaderVO() != null && reportHeaderId.equals( reportDTO.getReportHeaderVO().getReportHeaderId() ) )
            {
               return reportDTO;
            }
         }
      }

      return null;
   }

   // Added By siuxia at 2014-01-21 根据tableId获取ManagerDTO //
   public ManagerDTO getManagerDTOByTableId( final String tableId )
   {
      if ( MANAGER_DTO != null && MANAGER_DTO.size() > 0 )
      {
         for ( ManagerDTO managerDTO : MANAGER_DTO )
         {
            if ( managerDTO.getManagerHeaderVO().getTableId().trim().equals( tableId ) )
            {
               return managerDTO;
            }
         }
      }

      return null;
   }

   // Added By siuxia at 2014-02-14 
   public BankTemplateDTO getBankTemplateDTOByTemplateHeaderId( final String templateHeaderId )
   {
      if ( BANK_TEMPLATE_DTO != null && BANK_TEMPLATE_DTO.size() > 0 )
      {
         for ( BankTemplateDTO bankTemplateDTO : BANK_TEMPLATE_DTO )
         {
            if ( bankTemplateDTO.getBankTemplateHeaderVO().getTemplateHeaderId().equals( templateHeaderId ) )
            {
               return bankTemplateDTO;
            }
         }
      }

      return null;
   }

   // Added By siuxia at 2014-02-14 
   public TaxTemplateDTO getTaxTemplateDTOByTemplateHeaderId( final String templateHeaderId )
   {
      if ( TAX_TEMPLATE_DTO != null && TAX_TEMPLATE_DTO.size() > 0 )
      {
         for ( TaxTemplateDTO taxTemplateDTO : TAX_TEMPLATE_DTO )
         {
            if ( taxTemplateDTO.getTaxTemplateHeaderVO().getTemplateHeaderId().equals( templateHeaderId ) )
            {
               return taxTemplateDTO;
            }
         }
      }

      return null;
   }

   // 按照listHeaderId获得对应的ListDTO
   public ListDTO getListDTOByListHeaderId( final String listHeaderId )
   {
      if ( LIST_DTO != null && LIST_DTO.size() > 0 )
      {
         for ( ListDTO listDTO : LIST_DTO )
         {
            if ( listDTO.getListHeaderVO() != null && KANUtil.filterEmpty( listDTO.getListHeaderVO().getListHeaderId() ) != null
                  && listDTO.getListHeaderVO().getListHeaderId().trim().equals( listHeaderId ) )
            {
               return listDTO;
            }
         }
      }

      return null;
   }

   // 按照JavaObjectName获得对应的ListDTO
   public ListDTO getListDTOByJavaObjectName( final String javaObjectName )
   {
      return getListDTOByJavaObjectName( javaObjectName, null );
   }

   // 按照JavaObjectName获得对应的ListDTO
   public ListDTO getListDTOByJavaObjectName( final String javaObjectName, final String corpId )
   {
      if ( LIST_DTO != null && LIST_DTO.size() > 0 )
      {
         for ( ListDTO listDTO : LIST_DTO )
         {

            if ( listDTO.getListHeaderVO() != null && KANUtil.filterEmpty( listDTO.getListHeaderVO().getJavaObjectName() ) != null )
            {
               if ( ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( listDTO.getListHeaderVO().getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( listDTO.getListHeaderVO().getCorpId() ) != null ) )
                     && listDTO.getListHeaderVO().getJavaObjectName().trim().equals( javaObjectName ) )
               {
                  return listDTO;
               }
            }
         }
      }

      return null;
   }

   // 按照JavaObjectName获得对应的SearchDTO
   public SearchDTO getSearchDTOByJavaObjectName( final String javaObjectName )
   {
      return getSearchDTOByJavaObjectName( javaObjectName, null );
   }

   // 按照JavaObjectName获得对应的SearchDTO
   public SearchDTO getSearchDTOByJavaObjectName( final String javaObjectName, final String corpId )
   {
      if ( SEARCH_DTO != null && SEARCH_DTO.size() > 0 )
      {
         for ( SearchDTO searchDTO : SEARCH_DTO )
         {
            if ( searchDTO.getSearchHeaderVO() != null && KANUtil.filterEmpty( searchDTO.getSearchHeaderVO().getJavaObjectName() ) != null )
            {
               if ( ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( searchDTO.getSearchHeaderVO().getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( searchDTO.getSearchHeaderVO().getCorpId() ) != null ) )
                     && searchDTO.getSearchHeaderVO().getJavaObjectName().trim().equals( javaObjectName ) )
               {
                  return searchDTO;
               }
            }
         }
      }

      return null;
   }

   // 按照clinetId、listId获取MappingDTO
   public MappingDTO getMappingDTOByCondition( final String corpId, final String listId )
   {
      if ( MAPPING_DTO != null && MAPPING_DTO.size() > 0 )
      {
         for ( MappingDTO mappingDTO : MAPPING_DTO )
         {
            if ( mappingDTO != null && mappingDTO.getMappingHeaderVO() != null && KANUtil.filterEmpty( mappingDTO.getMappingHeaderVO().getCorpId() ) != null
                  && mappingDTO.getMappingHeaderVO().getCorpId().equals( corpId ) && KANUtil.filterEmpty( mappingDTO.getMappingHeaderVO().getListId() ) != null
                  && mappingDTO.getMappingHeaderVO().getListId().equals( listId ) )
            {
               return mappingDTO;
            }
         }
      }

      return null;
   }

   // 按照defineId、defineId获取MappingDTO
   public WorkflowDefineDTO getWorkflowDefineDTOByDefineId( final String defineId )
   {
      if ( WORKFLOW_MODULE_DTO != null && WORKFLOW_MODULE_DTO.size() > 0 )
      {
         for ( WorkflowModuleDTO workflowModuleDTO : WORKFLOW_MODULE_DTO )
         {
            if ( workflowModuleDTO != null && workflowModuleDTO.getWorkflowDefineDTO() != null && workflowModuleDTO.getWorkflowDefineDTO().size() > 0 )
            {
               for ( WorkflowDefineDTO workflowDefineDTO : workflowModuleDTO.getWorkflowDefineDTO() )
               {
                  if ( workflowDefineDTO != null )
                  {
                     WorkflowDefineVO workflowDefineVO = workflowDefineDTO.getWorkflowDefineVO();
                     if ( workflowDefineVO != null && workflowDefineVO.getDefineId().equals( defineId ) )
                     {
                        return workflowDefineDTO;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   // 按照defineId、listId获取MappingDTO
   public WorkflowModuleDTO getWorkflowModuleDTOByWorkflowModuleId( final String workflowModuleId )
   {
      if ( WORKFLOW_MODULE_DTO != null && WORKFLOW_MODULE_DTO.size() > 0 )
      {
         for ( WorkflowModuleDTO workflowModuleDTO : WORKFLOW_MODULE_DTO )
         {
            if ( workflowModuleDTO != null && workflowModuleDTO.getWorkflowModuleVO() != null
                  && workflowModuleDTO.getWorkflowModuleVO().getWorkflowModuleId().equals( workflowModuleId ) )
            {
               return workflowModuleDTO;
            }
         }
      }
      return null;
   }

   // 获取PositionDTO列表
   // Add by siuxia 2014-01-09
   public List< PositionDTO > getPositionDTOs( final String corpId )
   {
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

      if ( POSITION_DTO != null && POSITION_DTO.size() > 0 )
      {
         for ( PositionDTO positionDTO : POSITION_DTO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) != null && positionDTO.getPositionVO().getCorpId().equals( corpId ) ) )
            {
               positionDTOs.add( positionDTO );
            }
         }
      }

      return positionDTOs;
   }

   public List< PositionDTO > getIndependentDisplayPositionDTOs( final String corpId )
   {
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

      fetchIndependentDisplayPositionDTOs( POSITION_DTO, corpId, positionDTOs );

      return positionDTOs;
   }

   // 获取根为0和独立显示的
   private void fetchIndependentDisplayPositionDTOs( final List< PositionDTO > positionDTOs, final String corpId, final List< PositionDTO > targetPositionDTOs )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         for ( PositionDTO positionDTO : positionDTOs )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) != null && positionDTO.getPositionVO().getCorpId().equals( corpId ) ) )
            {

               if ( "1".equals( positionDTO.getPositionVO().getIsIndependentDisplay() ) || "0".equals( positionDTO.getPositionVO().getParentPositionId() ) )
               {
                  targetPositionDTOs.add( positionDTO );
               }

            }

            fetchIndependentDisplayPositionDTOs( positionDTO.getPositionDTOs(), corpId, targetPositionDTOs );
         }
      }
   }

   // 获取PositionVO列表
   public List< MappingVO > getPositions( final String localeLanguage )
   {
      return getPositions( localeLanguage, null );
   }

   // 获取PositionVO列表
   public List< MappingVO > getPositions( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > positions = new ArrayList< MappingVO >();

      // 遍历Branch List
      for ( PositionVO positionVO : POSITION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionVO.getCorpId() ) != null && positionVO.getCorpId().equals( corpId ) ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( positionVO.getPositionId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( positionVO.getTitleZH() );
            }
            else
            {
               mappingVO.setMappingValue( positionVO.getTitleEN() );
            }

            positions.add( mappingVO );
         }

      }

      return positions;
   }

   // 递归装载EmployeePosition
   private void fetchEmployeePositions( final List< MappingVO > employeePositions, final com.kan.base.domain.management.PositionDTO employeePositionDTO, final String localeLanguage )
   {
      // 初始化MappingVO对象
      final MappingVO mappingVO = new MappingVO();
      mappingVO.setMappingId( employeePositionDTO.getPositionVO().getPositionId() );

      if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
      {
         mappingVO.setMappingValue( employeePositionDTO.getPositionVO().getTitleZH() );
      }
      else
      {
         mappingVO.setMappingValue( employeePositionDTO.getPositionVO().getTitleEN() );
      }

      employeePositions.add( mappingVO );

      if ( employeePositionDTO.getPositionDTOs() != null && employeePositionDTO.getPositionDTOs().size() > 0 )
      {
         for ( com.kan.base.domain.management.PositionDTO subEmployeePositionDTO : employeePositionDTO.getPositionDTOs() )
         {
            fetchEmployeePositions( employeePositions, subEmployeePositionDTO, localeLanguage );
         }
      }
   }

   // 获取PositionVO(外部)列表
   public List< MappingVO > getEmployeePositions( final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > employeePositions = new ArrayList< MappingVO >();

      for ( com.kan.base.domain.management.PositionDTO employeePositionDTO : EMPLOYEE_POSITION_DTO )
      {
         if ( employeePositionDTO != null && employeePositionDTO.getPositionVO() != null )
         {
            // 递归装载EmployeePosition
            fetchEmployeePositions( employeePositions, employeePositionDTO, localeLanguage );
         }
      }

      return employeePositions;
   }

   //通过职位找对应的employeeId
   // modify by siuvan 过滤代理
   public Set< String > getEmployeeIdListByPositionId( final List< String > positionIds )
   {
      Set< String > employeeIdSet = new HashSet< String >();
      for ( String position : positionIds )
      {
         List< StaffDTO > staffList = getNoAgentStaffDTOsByPositionId( position );
         for ( StaffDTO staffDTO : staffList )
         {
            if ( staffDTO != null && staffDTO.getStaffVO() != null && KANUtil.filterEmpty( staffDTO.getStaffVO().getEmployeeId() ) != null )
            {
               employeeIdSet.add( staffDTO.getStaffVO().getEmployeeId() );
            }
         }
      }
      return employeeIdSet;
   }

   //通过职级找对应的position
   public Set< String > getPositionIdListByPositionGradeId( final List< String > positionGradeIds, final String corpId )
   {
      Set< String > positionIdSet = new HashSet< String >();
      if ( positionGradeIds != null && positionGradeIds.size() > 0 )
      {
         for ( PositionVO positionVO : POSITION_VO )
         {
            if ( StringUtils.equals( corpId, positionVO.getCorpId() ) )
            {
               String positionGradeId = positionVO.getPositionGradeId();
               String positionId = positionVO.getPositionId();
               if ( positionGradeId != null && ArrayUtils.contains( positionGradeIds.toArray(), positionGradeId ) )
               {
                  positionIdSet.add( positionId );
               }
            }
         }
      }
      return positionIdSet;
   }

   //通过职级找对应的position
   public Set< String > getPositionIdListByBranchId( final List< String > branchIds, final String corpId )
   {
      Set< String > positionIdSet = new HashSet< String >();
      if ( branchIds != null && branchIds.size() > 0 )
      {
         for ( PositionVO positionVO : POSITION_VO )
         {
            if ( StringUtils.equals( corpId, positionVO.getCorpId() ) )
            {
               String branchId = positionVO.getBranchId();
               String positionId = positionVO.getPositionId();
               if ( branchId != null && ArrayUtils.contains( branchIds.toArray(), branchId ) )
               {
                  positionIdSet.add( positionId );
               }
            }
         }
      }
      return positionIdSet;
   }

   public PositionVO getMainPositionVOByStaffId( final String staffId )
   {
      PositionVO positionVO = null;
      final StaffDTO staffDTO = getStaffDTOByStaffId( staffId );
      final List< PositionStaffRelationVO > positionStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
      if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationVOs )
         {
            if ( "1".equals( positionStaffRelationVO.getStaffType() ) )
            {
               positionVO = getPositionVOByPositionId( positionStaffRelationVO.getPositionId() );
               break;
            }
         }
      }
      return positionVO;
   }

   /***
    * 获取PositionVO MappingVO 列表
    * ID == positionId
    * Value == location + positionGrade + title + owner
    * @param localeLanguage
    * @param corpId
    * @return
    */
   public List< MappingVO > getPositionMappingVOs( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > positionMappingVOs = new ArrayList< MappingVO >();

      // 遍历Branch List
      for ( PositionVO positionVO : POSITION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionVO.getCorpId() ) != null && positionVO.getCorpId().equals( corpId ) ) )
         {
            final StringBuilder rs = new StringBuilder();
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( positionVO.getPositionId() );

            final String owner = getStaffNamesByPositionId( localeLanguage, positionVO.getPositionId() );
            if ( KANUtil.filterEmpty( owner ) == null )
            {

               final LocationVO locationVO = getLocationVOByLocationId( positionVO.getLocationId() );
               final PositionGradeVO positionGradeVO = getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );
               final BranchVO branchVO = getBranchVOByBranchId( positionVO.getBranchId() );

               if ( locationVO != null )
               {
                  rs.append( ( "zh".equalsIgnoreCase( localeLanguage ) ? locationVO.getNameZH() : locationVO.getNameEN() ) + " / " );
               }

               if ( positionGradeVO != null )
               {
                  rs.append( ( "zh".equalsIgnoreCase( localeLanguage ) ? positionGradeVO.getGradeNameZH() : positionGradeVO.getGradeNameEN() ) + " / " );
               }

               if ( branchVO != null )
               {
                  rs.append( ( "zh".equalsIgnoreCase( localeLanguage ) ? branchVO.getNameZH() : branchVO.getNameEN() ) + " / " );
               }

               rs.append( "zh".equalsIgnoreCase( localeLanguage ) ? positionVO.getTitleZH() : positionVO.getTitleEN() );

               rs.append( " / " + owner );
               mappingVO.setMappingValue( rs.toString() );
               positionMappingVOs.add( mappingVO );
            }
         }
      }

      return positionMappingVOs;
   }

   public List< MappingVO > getLineManagerNames( final String localeLanguage )
   {
      final List< MappingVO > lineMangerNames = new ArrayList< MappingVO >();
      fetchLineManagerNames( POSITION_DTO, lineMangerNames, localeLanguage, 0 );
      return lineMangerNames;
   }

   public void fetchLineManagerNames( final List< PositionDTO > positionDTOs, final List< MappingVO > lineMangerNames, final String localeLanguage, int level )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         for ( PositionDTO tempPositionDTO : positionDTOs )
         {
            if ( tempPositionDTO.getPositionVO() == null )
               continue;

            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempPositionDTO.getPositionVO().getPositionId() );

            String staffNames = "";
            for ( StaffDTO tempsStaffDTO : getStaffDTOsByPositionId( tempPositionDTO.getPositionVO().getPositionId() ) )
            {
               if ( KANUtil.filterEmpty( staffNames ) == null )
               {
                  staffNames = staffNames + ( "zh".equals( localeLanguage ) ? tempsStaffDTO.getStaffVO().getNameZH() : tempsStaffDTO.getStaffVO().getNameEN() );
               }
               else
               {
                  staffNames = staffNames + "、" + ( "zh".equals( localeLanguage ) ? tempsStaffDTO.getStaffVO().getNameZH() : tempsStaffDTO.getStaffVO().getNameEN() );
               }
            }

            String spaces = "";
            for ( int i = 0; i < level; i++ )
            {
               spaces = spaces + "  ";
            }

            mappingVO.setMappingValue( spaces + ( "zh".equals( localeLanguage ) ? tempPositionDTO.getPositionVO().getTitleZH() : tempPositionDTO.getPositionVO().getTitleEN() )
                  + " - " + staffNames );

            lineMangerNames.add( mappingVO );

            if ( tempPositionDTO.getPositionDTOs() != null && tempPositionDTO.getPositionDTOs().size() > 0 )
            {
               level++;
               fetchLineManagerNames( tempPositionDTO.getPositionDTOs(), lineMangerNames, localeLanguage, level );
            }
         }
      }
   }
}
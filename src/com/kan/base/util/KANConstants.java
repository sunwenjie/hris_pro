package com.kan.base.util;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableVO;
import com.kan.base.domain.management.BankVO;
import com.kan.base.domain.management.CalendarDTO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.ShiftDTO;
import com.kan.base.domain.system.*;
import com.kan.base.service.inf.define.*;
import com.kan.base.service.inf.management.*;
import com.kan.base.service.inf.message.MessageTemplateService;
import com.kan.base.service.inf.security.*;
import com.kan.base.service.inf.security.PositionGradeService;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.system.*;
import com.kan.base.util.Encrypt.Encrypt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Kevin Jin
 */
public class KANConstants implements KANConstantsService, Serializable
{
   /**
    * Serial Version UID
    */
   public static boolean ISDEBUG = false;

   private static final long serialVersionUID = -3671805776442545238L;

   public static final String HTTP = "https://";

   public static final String DEFAULT_ACCOUNTID = "100017";

   public static final String DEFAULT_CORPID = "300115";
   
   // 是否是任务刷新内存
   public static boolean TASK_INIT = false;

   // 添加Logger功能
   protected Log logger = LogFactory.getLog( getClass() );

   // Public Constants
   public static Map< String, KANAccountConstants > accountConstantsMap = new HashMap< String, KANAccountConstants >();

   // 登录黑名单
   public static Map< String, Integer > userBlackList = new HashMap< String, Integer >();

   // 登录错误6次添加黑名单
   public static int ERROR_COUNT_MAX = 6;

   // 用于系统调试，使用“,”分割，“0”表示正常模式
   public static String DEBUG_ACCOUNT_IDS;

   // 系统邮件地址后缀，@后面部分，跟账户名称搭配使用
   public static String MAIL_POSTFIX;

   // 部署域名
   public static String DOMAIN;

   // 项目名称
   public static String PROJECT_NAME;

   // 产品名称 - HR Service
   public static String PRODUCT_NAME_HRS;

   // 产品名称 - In House
   public static String PRODUCT_NAME_INH;

   // Version
   public static String VERSION;

   // 本地服务器
   public static String HOST;

   // 同步服务器（类似需要缓存同步）
   public static String SYNCHRO_SERVERS;

   // 月平均计薪日
   public static double AVG_SALARY_DAYS_PER_MONTH;

   // Position存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_POSITION;

   // BaseInfo存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_BASEINFO;

   // Employee存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE;

   // Employee Contract存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE_CONTRACT;

   // Client存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_CLIENT;

   // Client Contract存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_CLIENT_CONTRACT;

   // Client Order存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_CLIENT_ORDER;

   // Social Benefit存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_SB;

   // Commercial Benefit存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_CB;

   // Vendor存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_VENDOR;

   // Account存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_ACCOUNT;

   // Leave存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_LEAVE;

   // Logo存储子目录
   public static String SHAREFOLDER_SUB_DIRECTORY_LOGO;

   // 国家，省份，城市
   public static LocationDTO LOCATION_DTO = new LocationDTO();

   // 模块 - 根节点VO无用途，为空
   public static List< ModuleDTO > MODULE_DTO = new ArrayList< ModuleDTO >();

   // 模块 - 根节点VO无用途，为空
   public static List< ModuleDTO > CLIENT_MODULE_DTO = new ArrayList< ModuleDTO >();

   // 模块 - 根节点VO无用途，为空
   public static List< ModuleDTO > EMPLOYEE_CLIENT_MODULE_DTO = new ArrayList< ModuleDTO >();

   // 模块 - Base View
   public static List< ModuleBaseView > MODULE_BASEVIEW = new ArrayList< ModuleBaseView >();

   // 权限
   public static List< RightVO > RIGHT_VO = new ArrayList< RightVO >();

   // 规则
   public static List< RuleVO > RULE_VO = new ArrayList< RuleVO >();

   // 短信配置
   public static List< SMSConfigVO > SMS_CONFIG_VO = new ArrayList< SMSConfigVO >();

   // 表或视图
   public static List< TableVO > TABLE_VO = new ArrayList< TableVO >();

   // 工作流模块
   public static List< WorkflowModuleVO > WORKFLOW_MOFDULE_VO = new ArrayList< WorkflowModuleVO >();

   // 社保（系统）
   public static List< SocialBenefitDTO > SOCIAL_BENEFIT_DTO = new ArrayList< SocialBenefitDTO >();

   // 日历 - DTO（系统）
   public static List< CalendarDTO > CALENDAR_DTO = new ArrayList< CalendarDTO >();

   // 排班 - DTO（系统）
   public static List< ShiftDTO > SHIFT_DTO = new ArrayList< ShiftDTO >();

   // 起征（系统）
   public static List< IncomeTaxBaseVO > INCOME_TAX_BASE_VO = new ArrayList< IncomeTaxBaseVO >();

   // 税率 - DTO（系统）
   public static List< IncomeTaxRangeDTO > INCOME_TAX_RANGE_DTO = new ArrayList< IncomeTaxRangeDTO >();

   // 科目（系统）
   public static List< ItemVO > ITEM_VO = new ArrayList< ItemVO >();

   // 银行（系统）
   public static List< BankVO > BANK_VO = new ArrayList< BankVO >();

   // HRO
   public static String SYSTEM_ID = "1";

   // Super Account Id
   public static String SUPER_ACCOUNT_ID = "1";

   // Super Admin Id
   public static String SUPER_ADMIN_ID = "1";

   // 权限 - 新建 - 模块
   public final static String MODULE_RIGHT_NEW = "1";

   // 权限 - 查看 - 模块
   public final static String MODULE_RIGHT_VIEW = "2";

   // 权限 - 修改 - 模块
   public final static String MODULE_RIGHT_MODIFY = "3";

   // 权限 - 删除 - 模块
   public final static String MODULE_RIGHT_DELETE = "4";

   // 权限 - 列表 - 模块
   public final static String MODULE_RIGHT_LIST = "5";

   // 权限 - 提交 - 模块
   public final static String MODULE_RIGHT_SUBMIT = "6";

   public final static String MODULE_RIGHT_CONFIRM = "18";

   // 人力资源服务商
   public final static String ROLE_HR_SERVICE = "1";

   // 企业内部人事职能
   public final static String ROLE_IN_HOUSE = "2";

   // 供应商
   public final static String ROLE_VENDOR = "3";

   // 客户
   public final static String ROLE_CLIENT = "4";

   // 雇员
   public final static String ROLE_EMPLOYEE = "5";

   // 外包、派遣系统
   public static String SYS_HR_SERVICE = "1";

   // 企业内部eHR
   public static String SYS_IN_HOUSE = "2";

   // 平台
   public static String SYS_PLATFORM = "3";

   // 私钥 - 用来解密敏感数据 //读propertity Y7J6dDMkd0fj
   public static String PRIVATE_CODE = "";

   // City Service
   private CityService cityService;

   // Province Service
   private ProvinceService provinceService;

   // Country Service
   private CountryService countryService;

   // Account Service
   private AccountService accountService;

   // Options Service
   private OptionsService optionsService;

   // Email Service
   private EmailConfigurationService emailConfigurationService;

   // ShareFolder Service
   private ShareFolderConfigurationService shareFolderConfigurationService;

   // Module Service
   private ModuleService moduleService;

   // Right Service
   private RightService rightService;

   // Rule Service
   private RuleService ruleService;

   // SMSConfig Service
   private SMSConfigService smsConfigService;

   // Table Service
   private TableService tableService;

   // ColumnGroup Service
   private ColumnGroupService columnGroupService;

   // OptionHeader Service
   private OptionHeaderService optionHeaderService;

   // Column Service
   private ColumnService columnService;

   // SearchHeader Service
   private SearchHeaderService searchHeaderService;

   // ListHeader Service
   private ListHeaderService listHeaderService;

   // ReportHeader Service
   private ReportHeaderService reportHeaderService;

   // ImportHeader Service
   private ImportHeaderService importHeaderService;

   // ManagerHeader Service
   private ManagerHeaderService managerHeaderService;

   // MappingHeader Service
   private MappingHeaderService mappingHeaderService;

   // BankTemplateHeader Service
   private BankTemplateHeaderService bankTemplateHeaderService;

   // TaxTemplateHeader Service
   private TaxTemplateHeaderService taxTemplateHeaderService;

   // Staff Service
   private StaffService staffService;

   // Location Service
   private LocationService locationService;

   // system log service
   private LogService logService;

   // Branch Service
   private BranchService branchService;

   // Group Service
   private GroupService groupService;

   // Position Service
   private PositionService positionService;

   // Position Grade Service
   private PositionGradeService positionGradeService;

   // Skill Service
   private SkillService skillService;

   // Employee PositionService
   private com.kan.base.service.inf.management.PositionService employeePositionService;

   // Employee PositionCreadService
   private com.kan.base.service.inf.management.PositionGradeService employeePositionGradeService;

   // Workflow Module Service
   private WorkflowModuleService workflowModuleService;

   // Entity Service
   private EntityService entityService;

   // Contract Type Service
   private ContractTypeService contractTypeService;

   // Employee Status Service
   private EmployeeStatusService employeeStatusService;

   // Education Service
   private EducationService educationService;

   // Language Service
   private LanguageService languageService;

   // ExchangeRate Service
   private ExchangeRateService exchangeRateService;

   // Income Tax Base Service
   private IncomeTaxBaseService incomeTaxBaseService;

   // Income Tax Range Header Service
   private IncomeTaxRangeHeaderService incomeTaxRangeHeaderService;

   // Item Service
   private ItemService itemService;

   // Bank Service
   private BankService bankService;

   // Tax Service
   private TaxService taxService;

   // ItemGroup Service
   private ItemGroupService itemGroupService;

   // BusinessType Service
   private BusinessTypeService businessTypeService;

   // Industry Type Service
   private IndustryTypeService industryTypeService;

   // SocialBenefitHeader Service
   private SocialBenefitHeaderService socialBenefitHeaderService;

   // ItemMapping Service
   private ItemMappingService itemMappingService;

   // Business Contract Template Service
   private BusinessContractTemplateService businessContractTemplateService;

   // Labor Contract Template Service
   private LaborContractTemplateService laborContractTemplateService;

   // Resign Template Service
   private ResignTemplateService resignTemplateService;

   // Constant Service
   private ConstantService constantService;

   // Membership Service
   private MembershipService membershipService;

   // Certification Service
   private CertificationService certificationService;

   // SocialBenefitSolution Service
   private SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService;

   // CommercialBenefitSolution Service
   private CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService;

   // SickLeaveSalaryHeaderService Service
   private SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService;

   // AnnualLeaveRuleHeader Service
   private AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService;

   // CalendarHeader Service
   private CalendarHeaderService calendarHeaderService;

   // ShiftHeader Service
   private ShiftHeaderService shiftHeaderService;

   private MessageTemplateService messageTemplateService;

   public static String WKHTMLTOPDF_PATH;

   public static String WKHTMLTOPDF_PARAMETER;

   public static String WKHTMLTOPDF_TEMP_FILE_PATH;

   public static String WKHTMLTOPDF_HTML_CHARSET;

   // 初始化Singleton类方法
   public synchronized void init() throws KANException
   {
      System.setProperty( "java.rmi.server.hostname", HOST );

      isDebug();

      final Date startDate = new Date();

      // 初始化用户黑名单
      initUserBlackList();

      // 初始化LOCATION
      initLocation();

      // 初始化MODULE
      initModule();

      // 初始化RIGHT
      initRight();

      // 初始化RULE
      initRule();

      // 初始化SMSConfig
      initSMSConfig();

      // 初始化 Table
      initTable();

      // 初始化WorkflowModule
      initWorkflowModule();

      // 初始化SocialBenefit（System）
      initSocialBenefit();

      // 初始化IncomeTaxBase（System）
      initIncomeTaxBase();

      // 初始化IncomeTaxRange（System）
      initIncomeTaxRange();

      // 初始化Item（System）
      initItem();

      // 初始化Bank（System）
      initBank();

      // 初始化Calendar（System）
      initCalendar();

      // 初始化Shift（System）
      initShift();

      // 获得AccountBaseView列表
      final List< Object > accountBaseViews = accountService.getAccountBaseViews();

      if ( accountBaseViews != null )
      {
         // Super User特殊处理
         accountBaseViews.add( 0, new AccountBaseView( "1", "", "" ) );

         for ( Object accountBaseViewObject : accountBaseViews )
         {
            // 初始化AccountBaseView
            final AccountBaseView accountBaseView = ( AccountBaseView ) accountBaseViewObject;

            // 初始化需要调试的AccountIds
            final String[] debugAccountIdArray = KANUtil.filterEmpty( DEBUG_ACCOUNT_IDS, "0" ) != null ? KANUtil.filterEmpty( DEBUG_ACCOUNT_IDS, "0" ).trim().split( "," ) : null;

            if ( accountBaseView.getId().equals( "1" ) || debugAccountIdArray == null || containAccountId( debugAccountIdArray, accountBaseView.getId() ) )
            {
               // 默认KANAccountConstants是存在，并尝试从缓存获取
               KANAccountConstants kanAccountConstants = accountConstantsMap.get( accountBaseView.getId() );

               // 如果KANAccountConstants不存在，重新创建
               if ( kanAccountConstants == null )
               {
                  kanAccountConstants = new KANAccountConstants( ( ( AccountBaseView ) accountBaseView ).getId() );
                  accountConstantsMap.put( ( ( AccountBaseView ) accountBaseView ).getId(), kanAccountConstants );
               }

               kanAccountConstants.initShareFolderConfiguration( shareFolderConfigurationService );
               kanAccountConstants.initOptions( optionsService );
               kanAccountConstants.initEmailConfiguration( emailConfigurationService );
               kanAccountConstants.initTable( tableService );
               kanAccountConstants.initColumnGroup( columnGroupService );
               kanAccountConstants.initColumnOptions( optionHeaderService );
               kanAccountConstants.initSearchHeader( searchHeaderService );
               kanAccountConstants.initListHeader( listHeaderService );
               kanAccountConstants.initReportHeader( reportHeaderService );
               kanAccountConstants.initImportHeader( importHeaderService );
               kanAccountConstants.initMappingHeader( mappingHeaderService );
               kanAccountConstants.initManagerHeader( managerHeaderService );
               kanAccountConstants.initBankTemplateHeader( bankTemplateHeaderService );
               kanAccountConstants.initTaxTemplateHeader( taxTemplateHeaderService );
               // Position优于Staff加载,便于初始化上下级关系
               kanAccountConstants.initPosition( positionService );
               kanAccountConstants.initStaff( staffService );
               kanAccountConstants.initLocation( locationService );
               kanAccountConstants.initSystemLogModule( logService );
               kanAccountConstants.initSystemLogOperType();
               kanAccountConstants.initBranch( branchService );
               kanAccountConstants.initPositionGroup( groupService );
               kanAccountConstants.initPositionGrade( positionGradeService );
               kanAccountConstants.initModule( moduleService );
               kanAccountConstants.initConstant( constantService );
               kanAccountConstants.initWorkflow( workflowModuleService );
               kanAccountConstants.initSkill( skillService );
               kanAccountConstants.initEmployeePosition( employeePositionService );
               kanAccountConstants.initEmployeePositionGrade( employeePositionGradeService );
               kanAccountConstants.initEntity( entityService );
               kanAccountConstants.initContractType( contractTypeService );
               kanAccountConstants.initEmployeeStatus( employeeStatusService );
               kanAccountConstants.initEducation( educationService );
               kanAccountConstants.initLanguage( languageService );
               kanAccountConstants.initExchangeRate( exchangeRateService );
               kanAccountConstants.initItem( itemService );
               kanAccountConstants.initBank( bankService );
               kanAccountConstants.initTax( taxService );
               kanAccountConstants.initItemGroup( itemGroupService );
               kanAccountConstants.initBusinessType( businessTypeService );
               kanAccountConstants.initBusinessContractTemplate( businessContractTemplateService );
               kanAccountConstants.initLaborContractTemplate( laborContractTemplateService );
               kanAccountConstants.initResignTemplate( resignTemplateService );
               kanAccountConstants.initIndustryType( industryTypeService );
               kanAccountConstants.initItemMapping( itemMappingService );
               kanAccountConstants.initMembership( membershipService );
               kanAccountConstants.initCertification( certificationService );
               kanAccountConstants.initSocialBenefitSolution( socialBenefitSolutionHeaderService );
               kanAccountConstants.initCommercialBenefitSolution( commercialBenefitSolutionHeaderService );
               kanAccountConstants.initSickLeaveSalary( sickLeaveSalaryHeaderService );
               kanAccountConstants.initAnnualLeaveRule( annualLeaveRuleHeaderService );
               kanAccountConstants.initCalendar( calendarHeaderService );
               kanAccountConstants.initShift( shiftHeaderService );
               kanAccountConstants.initMessageTemplate( messageTemplateService );
            }
         }

         // 微信threadToken
         logger.info( "weixin api appid:" + WXUtil.APPID );
         logger.info( "weixin api appsecret:" + WXUtil.APPSECRET );

         // 启动定时获取access_token的线程
         new Thread( new TokenThread() ).start();
         
         // 变成true, 说明重启完毕, 之后再自行这个方法就是晚上自动刷新内存
         TASK_INIT = true;

         logger.info( "Loading Account: " + accountConstantsMap.size() + " counts" );
      }

      final Date endtDate = new Date();

      logger.info( "Starting " + ( endtDate.getTime() - startDate.getTime() ) / 1000 + "s" );
   }

   /**
    * 初始化黑名单
    */
   public void initUserBlackList()
   {
      userBlackList.clear();
   }

   public static boolean checkUserInBlackList( String userName )
   {
      String name = "";
      if ( KANUtil.filterEmpty( userName ) != null )
      {
         name = userName.toLowerCase();
      }
      Integer errorCount = userBlackList.get( name );
      return errorCount == null ? false : errorCount >= 6;
   }

   /**
    * 用于添加同步
    */
   public void addErrorCount( String userName ) throws KANException, RemoteException
   {
      String name = "";
      if ( KANUtil.filterEmpty( userName ) != null )
      {
         name = userName.toLowerCase();
      }
      Integer count = userBlackList.get( name );
      if ( count == null )
      {
         userBlackList.put( name, 1 );
      }
      else
      {
         userBlackList.put( name, userBlackList.get( name ) + 1 );
      }

      System.err.println( name + "第" + userBlackList.get( name ) + "次密码错误" );

   }

   /**
    * 用于删除同步
    */
   public void removeUserFromBlackList( String userName ) throws KANException, RemoteException
   {
      userBlackList.remove( userName.toLowerCase() );
   }

   // 初始化Location常量，包含Country，Province，City
   public void initLocation() throws KANException
   {
      try
      {
         // 获取CountryVO List
         final List< Object > countryVOs = countryService.getCountryVOs();

         // 先清空LOCATION对象
         LOCATION_DTO.getCountryDTOs().clear();

         // 遍历CountryVO List
         CountryVO countryVO;
         for ( Object countryObject : countryVOs )
         {
            countryVO = ( CountryVO ) countryObject;

            // 根据Country Id获取对应的ProvinceVO List
            final List< Object > provinceVOs = provinceService.getProvinceVOsByCountryId( Integer.parseInt( countryVO.getCountryId() ) );

            // 创建Country对象
            CountryDTO countryDTO = new CountryDTO();
            if ( provinceVOs != null )
            {
               // 遍历ProvinceVO List并逐个装进Country DTO
               ProvinceVO provinceVO;
               for ( Object provinceObject : provinceVOs )
               {
                  provinceVO = ( ProvinceVO ) provinceObject;

                  // 根据Province Id获取对应的CityVO list
                  final List< Object > cityVOs = cityService.getCityVOsByProvinceId( Integer.parseInt( provinceVO.getProvinceId() ) );
                  ProvinceDTO provinceDTO = new ProvinceDTO();
                  if ( cityVOs != null )
                  {
                     // 遍历CityVO List并装入Province DTO
                     CityVO cityVO;
                     for ( Object cityObject : cityVOs )
                     {
                        cityVO = ( CityVO ) cityObject;
                        provinceDTO.getCityVOs().put( cityVO.getCityId(), cityVO );
                     }
                  }

                  provinceDTO.setProvinceVO( provinceVO );
                  countryDTO.getProvinceDTOs().put( provinceDTO.getProvinceVO().getProvinceId(), provinceDTO );
               }
            }
            countryDTO.setCountryVO( countryVO );
            LOCATION_DTO.getCountryDTOs().put( countryDTO.getCountryVO().getCountryId(), countryDTO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Module常量
   public void initModule() throws KANException
   {
      try
      {
         // 先清空MODULE对象
         MODULE_DTO.clear();
         CLIENT_MODULE_DTO.clear();
         MODULE_BASEVIEW.clear();
         EMPLOYEE_CLIENT_MODULE_DTO.clear();

         MODULE_DTO = moduleService.getModuleDTOs();
         CLIENT_MODULE_DTO = moduleService.getClientModuleDTOs();
         EMPLOYEE_CLIENT_MODULE_DTO = moduleService.getEmployeeModuleDTOs();

         // 遍历Module Base View
         final List< Object > moduleBaseViews = moduleService.getActiveModuleBaseViews();
         if ( moduleBaseViews != null )
         {
            for ( Object object : moduleBaseViews )
            {
               MODULE_BASEVIEW.add( ( ModuleBaseView ) object );
            }
         }

         logger.info( "Loading Module: " + MODULE_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Right常量
   public void initRight() throws KANException
   {
      try
      {
         // 先清空RIGHT对象
         RIGHT_VO.clear();

         final List< Object > rightVOs = rightService.getRightVOs();

         if ( rightVOs != null )
         {
            for ( Object object : rightVOs )
            {
               RIGHT_VO.add( ( RightVO ) object );
            }
         }

         logger.info( "Loading Right: " + RIGHT_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化Rule常量
   public void initRule() throws KANException
   {
      try
      {
         // 先清空RULE对象
         RULE_VO.clear();

         final List< Object > ruleVOs = ruleService.getRuleVOs();

         if ( ruleVOs != null )
         {
            for ( Object object : ruleVOs )
            {
               RULE_VO.add( ( RuleVO ) object );
            }
         }

         logger.info( "Loading Rule: " + RULE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化SMS Config常量
   public void initSMSConfig() throws KANException
   {
      try
      {
         // 先清空SMS_CONFIG对象
         SMS_CONFIG_VO.clear();

         final List< Object > smsConfigVOs = smsConfigService.getSMSConfigVOs();

         if ( smsConfigVOs != null )
         {
            for ( Object object : smsConfigVOs )
            {
               SMS_CONFIG_VO.add( ( SMSConfigVO ) object );
            }
         }

         logger.info( "Loading SMS Config: " + SMS_CONFIG_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化 Table常量
   public void initTable() throws KANException
   {
      try
      {
         // 先清空TABLE对象
         TABLE_VO.clear();

         final List< Object > tableVOs = tableService.getAvailableTableVOs();

         if ( tableVOs != null && tableVOs.size() > 0 )
         {
            for ( Object object : tableVOs )
            {
               TABLE_VO.add( ( TableVO ) object );
            }
         }

         logger.info( "Loading Table: " + TABLE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化WorkflowModule
   public void initWorkflowModule() throws KANException
   {
      try
      {
         // 先清空WORKFLOW_MOFDULE对象
         WORKFLOW_MOFDULE_VO.clear();

         final List< Object > workflowModules = workflowModuleService.listWorkflowModuleVO();

         if ( workflowModules != null && workflowModules.size() > 0 )
         {
            for ( Object object : workflowModules )
            {
               WORKFLOW_MOFDULE_VO.add( ( WorkflowModuleVO ) object );
            }

            logger.info( "Loading Workflow Module: " + WORKFLOW_MOFDULE_VO.size() + " counts" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化SocialBenefit
   public void initSocialBenefit() throws KANException
   {
      try
      {
         // 先清空SOCIAL_BENEFIT_DTO对象
         SOCIAL_BENEFIT_DTO.clear();

         SOCIAL_BENEFIT_DTO = socialBenefitHeaderService.getSocialBenefitDTOs();

         logger.info( "Loading Social Benefit: " + SOCIAL_BENEFIT_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化 IncomeTaxBase常量
   public void initIncomeTaxBase() throws KANException
   {
      try
      {
         // 先清空INCOME_TAX_BASE_VO对象
         INCOME_TAX_BASE_VO.clear();

         final IncomeTaxBaseVO incomeTaxBaseVO = new IncomeTaxBaseVO();
         incomeTaxBaseVO.setStatus( IncomeTaxBaseVO.TRUE );

         final List< Object > incomeTaxBaseVOs = incomeTaxBaseService.getIncomeTaxBaseVOsByCondition( incomeTaxBaseVO );

         if ( incomeTaxBaseVOs != null && incomeTaxBaseVOs.size() > 0 )
         {
            for ( Object object : incomeTaxBaseVOs )
            {
               INCOME_TAX_BASE_VO.add( ( IncomeTaxBaseVO ) object );
            }
         }

         logger.info( "Loading Income Tax Base: " + INCOME_TAX_BASE_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化IncomeTaxRange
   public void initIncomeTaxRange() throws KANException
   {
      try
      {
         // 先清空INCOME_TAX_RANGE_DTO对象
         INCOME_TAX_RANGE_DTO.clear();

         INCOME_TAX_RANGE_DTO = incomeTaxRangeHeaderService.getIncomeTaxRangeDTOs();

         logger.info( "Loading Income Tax Range: " + INCOME_TAX_RANGE_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化 Item常量
   public void initItem() throws KANException
   {
      try
      {
         // 先清空ITEM对象
         ITEM_VO.clear();

         final List< Object > itemVOs = itemService.getItemVOsByAccountId( SYSTEM_ID );

         if ( itemVOs != null && itemVOs.size() > 0 )
         {
            for ( Object object : itemVOs )
            {
               ITEM_VO.add( ( ItemVO ) object );
            }
         }

         logger.info( "Loading Item: " + ITEM_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化 Bank常量
   public void initBank() throws KANException
   {
      try
      {
         // 先清空ITEM对象
         BANK_VO.clear();

         final List< Object > bankVOs = bankService.getBankVOsByAccountId( SUPER_ACCOUNT_ID );

         if ( bankVOs != null && bankVOs.size() > 0 )
         {
            for ( Object object : bankVOs )
            {
               BANK_VO.add( ( BankVO ) object );
            }
         }

         logger.info( "Loading Bank: " + BANK_VO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化日历
   public void initCalendar() throws KANException
   {
      try
      {
         // Clear First
         CALENDAR_DTO.clear();
         // 初始化CALENDAR_DTO
         final List< CalendarDTO > calendarDTOs = calendarHeaderService.getCalendarDTOsByAccountId( SYSTEM_ID );

         if ( calendarDTOs != null && calendarDTOs.size() > 0 )
         {
            // 遍历CALENDAR_DTO
            for ( Object calendarDTOObject : calendarDTOs )
            {
               final CalendarDTO calendarDTO = ( CalendarDTO ) calendarDTOObject;
               CALENDAR_DTO.add( calendarDTO );
            }
         }

         logger.info( "Loading Calendar: " + CALENDAR_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 初始化排班
   public void initShift() throws KANException
   {
      try
      {
         // Clear First
         SHIFT_DTO.clear();
         // 初始化CALENDAR_DTO
         final List< ShiftDTO > shiftDTOs = shiftHeaderService.getShiftDTOsByAccountId( SYSTEM_ID );

         if ( shiftDTOs != null && shiftDTOs.size() > 0 )
         {
            // 遍历CALENDAR_DTO
            for ( Object shiftDTOObject : shiftDTOs )
            {
               final ShiftDTO shiftDTO = ( ShiftDTO ) shiftDTOObject;
               SHIFT_DTO.add( shiftDTO );
            }
         }

         logger.info( "Loading Shift: " + SHIFT_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 获得Account对应的常量类
   public static KANAccountConstants getKANAccountConstants( final String accountId )
   {
      return accountConstantsMap.get( accountId );
   }

   // 初始化选项配置
   @Override
   public void initOptions( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initOptions( optionsService );
   }

   // 初始化邮件配置
   @Override
   public void initEmailConfiguration( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initEmailConfiguration( emailConfigurationService );
   }

   // 初始化ShareFolder配置
   @Override
   public void initShareFolderConfiguration( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initShareFolderConfiguration( shareFolderConfigurationService );
   }

   // 初始化Table配置
   @Override
   public void initTable( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initTable( tableService );
   }

   // 初始化ColumnGroup配置
   @Override
   public void initColumnGroup( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initColumnGroup( columnGroupService );
   }

   // 初始化ColumnOption配置
   @Override
   public void initColumnOption( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initColumnOptions( optionHeaderService );
   }

   // 初始化SearchHeader配置
   @Override
   public void initSearchHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initSearchHeader( searchHeaderService );
   }

   // 初始化ListHeader配置
   @Override
   public void initListHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initListHeader( listHeaderService );
   }

   // 初始化MappingHeader配置
   @Override
   public void initMappingHeader( String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initMappingHeader( mappingHeaderService );
   }

   // 初始化ImportHeader配置
   @Override
   public void initImportHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initImportHeader( importHeaderService );
   }

   // 初始化ReportHeader配置
   @Override
   public void initReportHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initReportHeader( reportHeaderService );
   }

   // 初始化ManagerHeader配置
   @Override
   public void initManagerHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initManagerHeader( managerHeaderService );
   }

   // 初始化BankTemplateHeader配置
   @Override
   public void initBankTemplateHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initBankTemplateHeader( bankTemplateHeaderService );
   }

   // 初始化TaxTemplateHeader配置
   @Override
   public void initTaxTemplateHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initTaxTemplateHeader( taxTemplateHeaderService );
   }

   // 初始化Module配置
   @Override
   public void initModule( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initModule( moduleService );
   }

   // 初始化Constant配置
   @Override
   public void initConstant( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initConstant( constantService );
   }

   // 初始化Staff配置
   @Override
   public void initStaff( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initStaff( staffService );
   }

   // 初始化Staff配置
   @Override
   public void initTableReport( final String accountId, final String tableId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initTableReport( tableService, tableId );
   }

   // 初始化Staff菜单配置
   @Override
   public void initStaffMenu( final String accountId, final String reportHeaderId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initStaffMenu( staffService, reportHeaderId );
   }

   // 初始化Staff配置
   @Override
   public void initStaff( final String accountId, final String staffId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initStaff( staffService, staffId );
   }

   @Override
   public void initStaffBaseView( final String accountId, final String staffId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initStaffBaseView( staffService, staffId );
   }

   @Override
   public void initStaffForDelete( final String accountId, final String staffId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initStaffForDelete( staffId );
   }

   // 初始化Position配置
   @Override
   public void initPosition( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initPosition( positionService );
   }

   @Override
   public void initPosition( final String accountId, final String positionId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initPosition( positionService, positionId );
   }

   // 初始化PositionGrade配置
   public void initPositionGrade( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initPositionGrade( positionGradeService );
   }

   // 初始化Group配置
   @Override
   public void initPositionGroup( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initPositionGroup( groupService );
   }

   // 初始化EmployeePosition配置
   @Override
   public void initEmployeePosition( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initEmployeePosition( employeePositionService );
   }

   // 初始化Group配置
   @Override
   public void initEmployeePositionGrade( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initEmployeePositionGrade( employeePositionGradeService );
   }

   // 初始化Branch配置
   @Override
   public void initBranch( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initBranch( branchService );
   }

   // 初始化Location配置
   @Override
   public void initLocation( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initLocation( locationService );
   }

   @Override
   public void initSystemLogModule( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initSystemLogModule( logService );
   }

   @Override
   public void initSystemLogOperType( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initSystemLogOperType();
   }

   // 初始化工作流配置
   @Override
   public void initWorkflow( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initWorkflow( workflowModuleService );
   }

   // 初始化工作流配置
   @Override
   public void initSkill( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initSkill( skillService );
   }

   // 初始化Entity配置
   @Override
   public void initEntity( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initEntity( entityService );
   }

   // 初始化Contract Type配置
   @Override
   public void initContractType( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initContractType( contractTypeService );
   }

   // 初始化Employee Status配置
   @Override
   public void initEmployeeStatus( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initEmployeeStatus( employeeStatusService );
   }

   // 初始化Eduation配置
   @Override
   public void initEducation( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initEducation( educationService );
   }

   // 初始化Language配置
   @Override
   public void initLanguage( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initLanguage( languageService );
   }

   // 初始化ExchangeRate配置
   @Override
   public void initExchangeRate( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initExchangeRate( exchangeRateService );
   }

   // 初始化Item配置
   @Override
   public void initItem( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initItem( itemService );
   }

   // 初始化Bank配置
   @Override
   public void initBank( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initBank( bankService );
   }

   // 初始化Tax配置
   @Override
   public void initTax( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initTax( taxService );
   }

   // 初始化ItemGroup配置
   @Override
   public void initItemGroup( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initItemGroup( itemGroupService );
   }

   // 初始化BusinessType配置
   @Override
   public void initBusinessType( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initBusinessType( businessTypeService );
   }

   // 初始化ItemMapping配置
   @Override
   public void initItemMapping( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initItemMapping( itemMappingService );
   }

   // 初始化Membership配置
   @Override
   public void initMembership( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initMembership( membershipService );
   }

   // 初始化Certification配置
   @Override
   public void initCertification( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initCertification( certificationService );
   }

   // 初始化社保方案
   // Reviewed by Kevin Jin at 2013-09-18
   @Override
   public void initSocialBenefitSolution( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initSocialBenefitSolution( socialBenefitSolutionHeaderService );
   }

   // 初始化商保方案
   // Reviewed by Kevin Jin at 2013-09-18
   @Override
   public void initCommercialBenefitSolution( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initCommercialBenefitSolution( commercialBenefitSolutionHeaderService );
   }

   // 初始化CalendarHeader配置
   @Override
   public void initCalendarHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initCalendar( calendarHeaderService );
   }

   // 初始化ShiftHeader配置
   @Override
   public void initShiftHeader( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initShift( shiftHeaderService );
   }

   // 初始化病假工资
   @Override
   public void initSickLeaveSalary( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initSickLeaveSalary( sickLeaveSalaryHeaderService );
   }

   // 初始化病假工资
   @Override
   public void initAnnualLeaveRule( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initAnnualLeaveRule( annualLeaveRuleHeaderService );
   }

   // 初始化BusinessContractTemplate配置
   @Override
   public void initBusinessContractTemplate( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initBusinessContractTemplate( businessContractTemplateService );
   }

   // 初始化initIndustryType配置
   public void initIndustryType( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initIndustryType( industryTypeService );
   }

   // 初始化MessageTemplate配置
   @Override
   public void initMessageTemplate( final String accountId ) throws KANException
   {
      accountConstantsMap.get( accountId ).initMessageTemplate( messageTemplateService );
   }

   // 获取 Right
   public static List< MappingVO > getRights( final String localeLanguage )
   {
      // 初始化返回值对象
      List< MappingVO > rights = new ArrayList< MappingVO >();

      // 遍历Right List
      for ( RightVO rightVO : RIGHT_VO )
      {
         // 初始化MappingVO对象
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( rightVO.getRightId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( rightVO.getNameZH() );
         }
         else
         {
            mappingVO.setMappingValue( rightVO.getNameEN() );
         }

         rights.add( mappingVO );
      }

      return rights;
   }

   // 获取 RightVOs
   public static List< RightVO > getRightVOs()
   {
      return RIGHT_VO;
   }

   // 获取 RightVO
   public static RightVO getRightVOByRightId( final String rightId )
   {
      // 遍历Right List
      for ( RightVO rightVO : RIGHT_VO )
      {
         if ( rightVO.getRightId() != null && rightVO.getRightId().trim().equalsIgnoreCase( rightId ) )
         {
            return rightVO;
         }
      }

      return null;
   }

   // 获取 Rules
   public static List< MappingVO > getRules( final String localeLanguage )
   {
      return getRules( localeLanguage, null );
   }

   // 获取 Rule按照RuleType
   public static List< MappingVO > getRules( final String localeLanguage, final String ruleType, final String moduleId )
   {
      // 初始化返回值对象
      final List< MappingVO > rules = new ArrayList< MappingVO >();

      // 按照ModuleId获得Constants中的DTO
      ModuleDTO moduleDTO = null;
      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         moduleDTO = KANConstants.getModuleDTOByModuleId( moduleId );
      }

      // 遍历Rule List
      for ( RuleVO ruleVO : RULE_VO )
      {
         if ( ruleType == null || ( ruleVO != null && ruleVO.getRuleType() != null && ruleVO.getRuleType().trim().equals( ruleType ) )
               && checkRuleVO( ruleVO, moduleDTO != null ? KANUtil.jasonArrayToStringArray( moduleDTO.getModuleVO().getRuleIds() ) : null ) )
         {
            // 初始化MappingVO对象
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( ruleVO.getRuleId() );

            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( ruleVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( ruleVO.getNameEN() );
            }

            rules.add( mappingVO );
         }
      }

      return rules;
   }

   // 获取 Rule按照RuleType
   public static List< MappingVO > getRules( final String localeLanguage, final String ruleType )
   {
      return getRules( localeLanguage, ruleType, null );
   }

   private static boolean checkRuleVO( final RuleVO ruleVO, final String[] ruleIds )
   {
      if ( ruleIds == null )
      {
         return true;
      }
      else
      {
         for ( String ruleId : ruleIds )
         {
            if ( ruleVO != null && ruleVO.getRuleId() != null && ruleId != null && ruleVO.getRuleId().trim().equals( ruleId.trim() ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // 获取 RuleVOs
   public static List< RuleVO > getRuleVOs()
   {
      return RULE_VO;
   }

   // 获取 RuleVO
   public static RuleVO getRuleVOByRuleId( final String ruleId )
   {
      // 遍历Rule List
      for ( RuleVO ruleVO : RULE_VO )
      {
         if ( ruleVO.getRuleId() != null && ruleVO.getRuleId().trim().equalsIgnoreCase( ruleId ) )
         {
            return ruleVO;
         }
      }

      return null;
   }

   // 获取 SMS Config
   public static List< MappingVO > getSMSConfigs( final String localeLanguage )
   {
      // 初始化返回值对象
      List< MappingVO > smsConfigs = new ArrayList< MappingVO >();

      // 遍历SMSConfig List
      for ( SMSConfigVO smsConfigVO : SMS_CONFIG_VO )
      {
         // 初始化MappingVO对象
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( smsConfigVO.getConfigId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( smsConfigVO.getNameZH() + " " + smsConfigVO.getPrice() + "元/条" );
         }
         else
         {
            mappingVO.setMappingValue( smsConfigVO.getNameEN() + " RMB " + smsConfigVO.getPrice() + "/per" );
         }

         smsConfigs.add( mappingVO );
      }

      return smsConfigs;
   }

   // 获取 SMSConfigVO
   public static SMSConfigVO getSMSConfigVOByConfigId( final String configId )
   {
      // 遍历SMSConfig List
      for ( SMSConfigVO smsConfigVO : SMS_CONFIG_VO )
      {
         if ( smsConfigVO.getConfigId() != null && smsConfigVO.getConfigId().trim().equalsIgnoreCase( configId ) )
         {
            return smsConfigVO;
         }
      }

      return null;
   }

   // 获取 Modules
   public static List< MappingVO > getModules( final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > modules = new ArrayList< MappingVO >();

      // 遍历Rule Map
      for ( ModuleDTO moduleDTO : MODULE_DTO )
      {
         if ( moduleDTO != null && moduleDTO.getModuleVO() != null )
         {
            modules.addAll( fetchModules( moduleDTO, localeLanguage ) );
         }
      }

      return modules;
   }

   // 获取 Modules by parentModuleId
   private static List< MappingVO > fetchModules( final ModuleDTO moduleDTO, final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > modules = new ArrayList< MappingVO >();

      if ( moduleDTO != null && moduleDTO.getModuleVO() != null )
      {
         modules.add( getMappingVOByModuleVO( moduleDTO.getModuleVO(), localeLanguage ) );

         if ( moduleDTO.getModuleDTOs() != null && moduleDTO.getModuleDTOs().size() > 0 )
         {
            for ( ModuleDTO subModuleDTO : moduleDTO.getModuleDTOs() )
            {
               modules.addAll( fetchModules( subModuleDTO, localeLanguage ) );
            }
         }
      }

      return modules;
   }

   // 按照ModuleId获得对应的ModuleDTO
   public static ModuleDTO getModuleDTOByModuleId( final String moduleId )
   {
      return getModuleDTOByModuleId( MODULE_DTO, moduleId );
   }

   // 按照ModuleId获得对应的ModuleDTO - 递归函数
   private static ModuleDTO getModuleDTOByModuleId( List< ModuleDTO > moduleDTOs, final String moduleId )
   {
      if ( moduleDTOs != null && moduleDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( ModuleDTO moduleDTO : moduleDTOs )
         {
            // 如果DTO是目标对象，则返回
            if ( moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getModuleId() != null && moduleDTO.getModuleVO().getModuleId().equals( moduleId ) )
            {
               return moduleDTO;
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( moduleDTO.getModuleDTOs() != null && moduleDTO.getModuleDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               moduleDTO = getModuleDTOByModuleId( moduleDTO.getModuleDTOs(), moduleId );

               // 如果子节点中有目标对象，立即返回
               if ( moduleDTO != null )
               {
                  return moduleDTO;
               }
            }
         }
      }

      return null;
   }

   // 按照AccessAction获得对应的ModuleDTO
   public static String getModuleIdByAccessAction( final String accessAction )
   {
      return getModuleDTOByAccessAction( MODULE_DTO, accessAction ).getModuleVO().getModuleId();
   }

   // 按照AccessAction获得对应的ModuleDTO
   public static ModuleDTO getModuleDTOByAccessAction( final String accessAction )
   {
      return getModuleDTOByAccessAction( MODULE_DTO, accessAction );
   }

   // 按照AccessAction获得对应的ModuleDTO - 递归函数
   private static ModuleDTO getModuleDTOByAccessAction( List< ModuleDTO > moduleDTOs, final String accessAction )
   {
      if ( moduleDTOs != null && moduleDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( ModuleDTO moduleDTO : moduleDTOs )
         {
            if ( moduleDTO.getModuleVO() != null )
            {
               // 初始化Access Action列表
               final List< String > accessActions = KANUtil.jasonArrayToStringList( moduleDTO.getModuleVO().getAccessAction() );

               // 如果DTO是目标对象，则返回
               if ( accessActions.contains( accessAction ) )
               {
                  return moduleDTO;
               }
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( moduleDTO.getModuleDTOs() != null && moduleDTO.getModuleDTOs().size() > 0 )
            {
               // 返回值重用当前节点内存
               moduleDTO = getModuleDTOByAccessAction( moduleDTO.getModuleDTOs(), accessAction );

               // 如果子节点中有目标对象，立即返回
               if ( moduleDTO != null )
               {
                  return moduleDTO;
               }
            }
         }
      }

      return null;
   }

   private static MappingVO getMappingVOByModuleVO( final ModuleVO moduleVO, final String localeLanguage )
   {
      // 初始化MappingVO对象
      final MappingVO mappingVO = new MappingVO();
      mappingVO.setMappingId( moduleVO.getModuleId() );

      if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
      {
         mappingVO.setMappingValue( moduleVO.getNameZH() );
      }
      else
      {
         mappingVO.setMappingValue( moduleVO.getNameEN() );
      }

      return mappingVO;
   }

   // 获取SocialBenefitHeader对应的 MappingVO
   public static List< MappingVO > getSocialBenefitHeaders( final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > socialBenefitHeaders = new ArrayList< MappingVO >();

      MappingVO mappingVO = null;

      // 遍历SocialBenefitDTO
      for ( SocialBenefitDTO socialBenefitDTO : SOCIAL_BENEFIT_DTO )
      {
         if ( socialBenefitDTO != null && socialBenefitDTO.getSocialBenefitHeaderVO() != null )
         {
            mappingVO = new MappingVO();
            mappingVO.setMappingId( socialBenefitDTO.getSocialBenefitHeaderVO().getHeaderId() );
            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( socialBenefitDTO.getSocialBenefitHeaderVO().getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( socialBenefitDTO.getSocialBenefitHeaderVO().getNameEN() );
            }
            socialBenefitHeaders.add( mappingVO );
         }
      }
      return socialBenefitHeaders;
   }

   // 获取SocialBenefitDTO - 按照社保HeaderId
   public static SocialBenefitDTO getSocialBenefitDTOBySBHeaderId( final String sbHeaderId )
   {
      // 遍历SocialBenefitDTO
      for ( SocialBenefitDTO socialBenefitDTO : SOCIAL_BENEFIT_DTO )
      {
         if ( socialBenefitDTO != null && socialBenefitDTO.getSocialBenefitHeaderVO() != null && socialBenefitDTO.getSocialBenefitHeaderVO().getHeaderId() != null
               && socialBenefitDTO.getSocialBenefitHeaderVO().getHeaderId().trim().equals( sbHeaderId ) )
         {
            return socialBenefitDTO;
         }
      }

      return null;
   }

   // 获得IncomeTaxBase对应的MappingVO
   public static List< MappingVO > getIncomeTaxBaseVOs( final String localeLanguage )
   {
      final List< MappingVO > tables = new ArrayList< MappingVO >();

      // 遍历系统定义的Table
      for ( IncomeTaxBaseVO incomeTaxBaseVO : INCOME_TAX_BASE_VO )
      {
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( incomeTaxBaseVO.getBaseId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( incomeTaxBaseVO.getNameZH() );
         }
         else
         {
            mappingVO.setMappingValue( incomeTaxBaseVO.getNameEN() );
         }
         mappingVO.setMappingTemp( incomeTaxBaseVO.getIsDefault() );

         tables.add( mappingVO );
      }

      return tables;
   }

   // 获得IncomeTaxBase对应的MappingVO
   public static List< MappingVO > getIncomeTaxRangeVOs( final String localeLanguage )
   {
      final List< MappingVO > tables = new ArrayList< MappingVO >();

      // 遍历系统定义的Table
      for ( IncomeTaxRangeDTO incomeTaxRangeDTO : INCOME_TAX_RANGE_DTO )
      {
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getHeaderId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getNameZH() );
         }
         else
         {
            mappingVO.setMappingValue( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getNameEN() );
         }
         mappingVO.setMappingTemp( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getIsDefault() );
         tables.add( mappingVO );
      }

      return tables;
   }

   // 获得Table对应的MappingVO
   public static List< MappingVO > getTables( final String localeLanguage )
   {
      final List< MappingVO > tables = new ArrayList< MappingVO >();

      // 遍历系统定义的Table
      for ( TableVO tableVO : TABLE_VO )
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

      return tables;
   }

   // 获得WorkflowModules
   public static List< MappingVO > getWorkflowModules( final String localeLanguage )
   {
      final List< MappingVO > workflowModules = new ArrayList< MappingVO >();

      // 遍历系统定义的WorkflowModule
      for ( WorkflowModuleVO workflowModuleVO : WORKFLOW_MOFDULE_VO )
      {
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( workflowModuleVO.getWorkflowModuleId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( workflowModuleVO.getNameZH() );
         }
         else
         {
            mappingVO.setMappingValue( workflowModuleVO.getNameEN() );
         }
         workflowModules.add( mappingVO );
      }

      return workflowModules;
   }

   // 按照ModuleId获取ModuleTitle
   public static String getModuleTitleByModuleId( final String moduleId )
   {
      if ( moduleId == null || moduleId.trim().equals( "" ) || MODULE_BASEVIEW == null || MODULE_BASEVIEW.size() <= 0 )
      {
         return "";
      }

      for ( ModuleBaseView moduleBaseView : MODULE_BASEVIEW )
      {
         if ( moduleBaseView.getId() != null && moduleBaseView.getId().trim().equals( moduleId ) )
         {
            return moduleBaseView.getName();
         }
      }

      return "";
   }

   // 获得工资的Items（System）
   public static List< MappingVO > getSalaryItems( final String localeLanguage )
   {
      return getItemsByType( "1", localeLanguage );
   }

   // 获得请假的Items（System）
   public static List< MappingVO > getLeaveItems( final String localeLanguage )
   {
      return getItemsByType( "6", localeLanguage );
   }

   // 获得加班的Items（System）
   public static List< MappingVO > getOtItems( final String localeLanguage )
   {
      return getItemsByType( "4", localeLanguage );
   }

   // 获得服务费的Items（System）
   public static List< MappingVO > getServiceFeeItems( final String localeLanguage )
   {
      return getItemsByType( "7", localeLanguage );
   }

   // 获得商保的Items（System）
   public static List< MappingVO > getCbItems( final String localeLanguage )
   {
      return getItemsByType( "8", localeLanguage );
   }

   // 获得社保的Items（System）
   public static List< MappingVO > getSbItems( final String localeLanguage )
   {
      return getItemsByType( "7", localeLanguage );
   }

   // 根据科目类型获得的Items（System）
   public static List< MappingVO > getItemsByType( final String type, final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > items = new ArrayList< MappingVO >();

      // 遍历ItemVO List
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( itemVO.getItemType() != null )
            {
               if ( itemVO.getItemType().trim().equals( type ) )
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

   // 根据ItemId获得的ItemVO
   public static ItemVO getItemVOByItemId( final String itemId )
   {
      // 遍历ItemVO List
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( itemVO.getItemId().trim().equals( itemId ) )
            {
               return itemVO;
            }
         }
      }

      return null;
   }

   // 根据BankId获得的BankVO
   public static BankVO getBankVOByBankId( final String bankId )
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

   // 获得有效的IncomeTaxBaseVO
   public static IncomeTaxBaseVO getValidIncomeTaxBaseVO()
   {
      if ( INCOME_TAX_BASE_VO != null && INCOME_TAX_BASE_VO.size() > 0 )
      {
         for ( IncomeTaxBaseVO incomeTaxBaseVO : INCOME_TAX_BASE_VO )
         {
            if ( KANUtil.filterEmpty( incomeTaxBaseVO.getStartDate() ) != null )
            {
               final long startDays = KANUtil.getDays( KANUtil.createCalendar( incomeTaxBaseVO.getStartDate() ) );
               long endDays = 0;
               if ( KANUtil.filterEmpty( incomeTaxBaseVO.getEndDate() ) != null )
               {
                  endDays = KANUtil.getDays( KANUtil.createCalendar( incomeTaxBaseVO.getEndDate() ) );
               }
               final long currentDays = KANUtil.getDays( new Date() );

               if ( currentDays >= startDays && ( currentDays <= endDays || endDays == 0 ) )
               {
                  return incomeTaxBaseVO;
               }
            }
         }
      }

      return null;
   }

   // 获得有效的IncomeTaxRangeDTO
   public static IncomeTaxRangeDTO getValidIncomeTaxRangeDTO()
   {
      if ( INCOME_TAX_RANGE_DTO != null && INCOME_TAX_RANGE_DTO.size() > 0 )
      {
         for ( IncomeTaxRangeDTO incomeTaxRangeDTO : INCOME_TAX_RANGE_DTO )
         {
            if ( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO() != null && KANUtil.filterEmpty( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getStartDate() ) != null )
            {
               final long startDays = KANUtil.getDays( KANUtil.createCalendar( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getStartDate() ) );
               long endDays = 0;
               if ( KANUtil.filterEmpty( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getEndDate() ) != null )
               {
                  endDays = KANUtil.getDays( KANUtil.createCalendar( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getEndDate() ) );
               }
               final long currentDays = KANUtil.getDays( new Date() );

               if ( currentDays >= startDays && ( currentDays <= endDays || endDays == 0 ) )
               {
                  return incomeTaxRangeDTO;
               }
            }
         }
      }

      return null;
   }

   // 获取IncomeTaxBaseVO - 按照BaseId
   public static IncomeTaxBaseVO getIncomeTaxBaseVOByBaseId( final String baseId )
   {
      // 遍历IncomeTaxBaseVO
      for ( IncomeTaxBaseVO incomeTaxBaseVO : INCOME_TAX_BASE_VO )
      {
         if ( incomeTaxBaseVO != null && KANUtil.filterEmpty( incomeTaxBaseVO.getBaseId() ) != null && KANUtil.filterEmpty( incomeTaxBaseVO.getBaseId() ).equals( baseId ) )
         {
            return incomeTaxBaseVO;
         }
      }

      return null;
   }

   // 获取IncomeTaxRangeDTO - 按照HeaderId
   public static IncomeTaxRangeDTO getIncomeTaxRangeDTOByHeaderId( final String headerId )
   {
      // 遍历IncomeTaxRangeDTO
      for ( IncomeTaxRangeDTO incomeTaxRangeDTO : INCOME_TAX_RANGE_DTO )
      {
         if ( incomeTaxRangeDTO != null && incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO() != null
               && KANUtil.filterEmpty( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getHeaderId() ) != null
               && KANUtil.filterEmpty( incomeTaxRangeDTO.getIncomeTaxRangeHeaderVO().getHeaderId() ).equals( headerId ) )
         {
            return incomeTaxRangeDTO;
         }
      }

      return null;
   }

   public OptionsService getOptionsService()
   {
      return optionsService;
   }

   public void setOptionsService( OptionsService optionsService )
   {
      this.optionsService = optionsService;
   }

   public EmailConfigurationService getEmailConfigurationService()
   {
      return emailConfigurationService;
   }

   public void setEmailConfigurationService( EmailConfigurationService emailConfigurationService )
   {
      this.emailConfigurationService = emailConfigurationService;
   }

   public ShareFolderConfigurationService getShareFolderConfigurationService()
   {
      return shareFolderConfigurationService;
   }

   public void setShareFolderConfigurationService( ShareFolderConfigurationService shareFolderConfigurationService )
   {
      this.shareFolderConfigurationService = shareFolderConfigurationService;
   }

   public AccountService getAccountService()
   {
      return accountService;
   }

   public void setAccountService( AccountService accountService )
   {
      this.accountService = accountService;
   }

   public void setDebugAccountIds( String debugAccountIds )
   {
      DEBUG_ACCOUNT_IDS = debugAccountIds;
   }

   public void setEmailPostfix( String emailPostfix )
   {
      MAIL_POSTFIX = emailPostfix;
   }

   public void setDomain( String domain )
   {
      DOMAIN = domain;
   }

   public void setProgectName( String progectName )
   {
      PROJECT_NAME = progectName;
   }

   public void setProductNameHRS( String productNameHRS )
   {
      PRODUCT_NAME_HRS = productNameHRS;
   }

   public void setProductNameINH( String productNameINH )
   {
      PRODUCT_NAME_INH = productNameINH;
   }

   public void setVersion( String version )
   {
      VERSION = version;
   }

   public void setHost( String host )
   {
      HOST = host;
   }

   public void setSynchroServers( String synchroServers )
   {
      SYNCHRO_SERVERS = synchroServers;
   }

   public void setAvgSalaryDaysPerMonth( double avgSalaryDaysPerMonth )
   {
      AVG_SALARY_DAYS_PER_MONTH = avgSalaryDaysPerMonth;
   }

   public void setPositionDirectory( String positionDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_POSITION = positionDirectory;
   }

   public void setBaseInfoDirectory( String baseInfoDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_BASEINFO = baseInfoDirectory;
   }

   public void setEmployeeDirectory( String employeeDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE = employeeDirectory;
   }

   public void setEmployeeContractDirectory( String employeeContractDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE_CONTRACT = employeeContractDirectory;
   }

   public void setClientDirectory( String clientDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_CLIENT = clientDirectory;
   }

   public void setClientContractDirectory( String clientContractDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_CLIENT_CONTRACT = clientContractDirectory;
   }

   public void setClientOrderDirectory( String clientOrderDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_CLIENT_ORDER = clientOrderDirectory;
   }

   public void setSbDirectory( String sbDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_SB = sbDirectory;
   }

   public void setCbDirectory( String cbDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_CB = cbDirectory;
   }

   public void setVendorDirectory( String vendorDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_VENDOR = vendorDirectory;
   }

   public void setAccountDirectory( String accountDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_ACCOUNT = accountDirectory;
   }

   public void setLogoDirectory( String logoDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_LOGO = logoDirectory;
   }

   public void setLeaveDirectory( String leaveDirectory )
   {
      SHAREFOLDER_SUB_DIRECTORY_LEAVE = leaveDirectory;
   }

   public CityService getCityService()
   {
      return cityService;
   }

   public void setCityService( CityService cityService )
   {
      this.cityService = cityService;
   }

   public ProvinceService getProvinceService()
   {
      return provinceService;
   }

   public void setProvinceService( ProvinceService provinceService )
   {
      this.provinceService = provinceService;
   }

   public CountryService getCountryService()
   {
      return countryService;
   }

   public void setCountryService( CountryService countryService )
   {
      this.countryService = countryService;
   }

   public ModuleService getModuleService()
   {
      return moduleService;
   }

   public void setModuleService( ModuleService moduleService )
   {
      this.moduleService = moduleService;
   }

   public RightService getRightService()
   {
      return rightService;
   }

   public void setRightService( RightService rightService )
   {
      this.rightService = rightService;
   }

   public RuleService getRuleService()
   {
      return ruleService;
   }

   public void setRuleService( RuleService ruleService )
   {
      this.ruleService = ruleService;
   }

   public TableService getTableService()
   {
      return tableService;
   }

   public void setTableService( TableService tableService )
   {
      this.tableService = tableService;
   }

   public StaffService getStaffService()
   {
      return staffService;
   }

   public void setStaffService( StaffService staffService )
   {
      this.staffService = staffService;
   }

   public ColumnGroupService getColumnGroupService()
   {
      return columnGroupService;
   }

   public void setColumnGroupService( ColumnGroupService columnGroupService )
   {
      this.columnGroupService = columnGroupService;
   }

   public ColumnService getColumnService()
   {
      return columnService;
   }

   public void setColumnService( ColumnService columnService )
   {
      this.columnService = columnService;
   }

   public SearchHeaderService getSearchHeaderService()
   {
      return searchHeaderService;
   }

   public void setSearchHeaderService( SearchHeaderService searchHeaderService )
   {
      this.searchHeaderService = searchHeaderService;
   }

   public ListHeaderService getListHeaderService()
   {
      return listHeaderService;
   }

   public void setListHeaderService( ListHeaderService listHeaderService )
   {
      this.listHeaderService = listHeaderService;
   }

   public MappingHeaderService getMappingHeaderService()
   {
      return mappingHeaderService;
   }

   public void setMappingHeaderService( MappingHeaderService mappingHeaderService )
   {
      this.mappingHeaderService = mappingHeaderService;
   }

   public SMSConfigService getSmsConfigService()
   {
      return smsConfigService;
   }

   public void setSmsConfigService( SMSConfigService smsConfigService )
   {
      this.smsConfigService = smsConfigService;
   }

   public LocationService getLocationService()
   {
      return locationService;
   }

   public void setLocationService( LocationService locationService )
   {
      this.locationService = locationService;
   }

   public LogService getLogService()
   {
      return logService;
   }

   public void setLogService( LogService logService )
   {
      this.logService = logService;
   }

   public BranchService getBranchService()
   {
      return branchService;
   }

   public void setBranchService( BranchService branchService )
   {
      this.branchService = branchService;
   }

   public GroupService getGroupService()
   {
      return groupService;
   }

   public void setGroupService( GroupService groupService )
   {
      this.groupService = groupService;
   }

   public PositionGradeService getPositionGradeService()
   {
      return positionGradeService;
   }

   public void setPositionGradeService( PositionGradeService positionGradeService )
   {
      this.positionGradeService = positionGradeService;
   }

   public PositionService getPositionService()
   {
      return positionService;
   }

   public void setPositionService( PositionService positionService )
   {
      this.positionService = positionService;
   }

   public WorkflowModuleService getWorkflowModuleService()
   {
      return workflowModuleService;
   }

   public void setWorkflowModuleService( WorkflowModuleService workflowModuleService )
   {
      this.workflowModuleService = workflowModuleService;
   }

   public OptionHeaderService getOptionHeaderService()
   {
      return optionHeaderService;
   }

   public void setOptionHeaderService( OptionHeaderService optionHeaderService )
   {
      this.optionHeaderService = optionHeaderService;
   }

   public SkillService getSkillService()
   {
      return skillService;
   }

   public void setSkillService( SkillService skillService )
   {
      this.skillService = skillService;
   }

   public EntityService getEntityService()
   {
      return entityService;
   }

   public void setEntityService( EntityService entityService )
   {
      this.entityService = entityService;
   }

   public ContractTypeService getContractTypeService()
   {
      return contractTypeService;
   }

   public void setContractTypeService( ContractTypeService contractTypeService )
   {
      this.contractTypeService = contractTypeService;
   }

   public EmployeeStatusService getEmployeeStatusService()
   {
      return employeeStatusService;
   }

   public void setEmployeeStatusService( EmployeeStatusService employeeStatusService )
   {
      this.employeeStatusService = employeeStatusService;
   }

   public EducationService getEducationService()
   {
      return educationService;
   }

   public void setEducationService( EducationService educationService )
   {
      this.educationService = educationService;
   }

   public com.kan.base.service.inf.management.PositionService getEmployeePositionService()
   {
      return employeePositionService;
   }

   public void setEmployeePositionService( com.kan.base.service.inf.management.PositionService employeePositionService )
   {
      this.employeePositionService = employeePositionService;
   }

   public LanguageService getLanguageService()
   {
      return languageService;
   }

   public void setLanguageService( LanguageService languageService )
   {
      this.languageService = languageService;
   }

   public ItemService getItemService()
   {
      return itemService;
   }

   public void setItemService( ItemService itemService )
   {
      this.itemService = itemService;
   }

   public ItemGroupService getItemGroupService()
   {
      return itemGroupService;
   }

   public void setItemGroupService( ItemGroupService itemGroupService )
   {
      this.itemGroupService = itemGroupService;
   }

   public BusinessTypeService getBusinessTypeService()
   {
      return businessTypeService;
   }

   public void setBusinessTypeService( BusinessTypeService businessTypeService )
   {
      this.businessTypeService = businessTypeService;
   }

   public SocialBenefitHeaderService getSocialBenefitHeaderService()
   {
      return socialBenefitHeaderService;
   }

   public void setSocialBenefitHeaderService( SocialBenefitHeaderService socialBenefitHeaderService )
   {
      this.socialBenefitHeaderService = socialBenefitHeaderService;
   }

   public IndustryTypeService getIndustryTypeService()
   {
      return industryTypeService;
   }

   public void setIndustryTypeService( IndustryTypeService industryTypeService )
   {
      this.industryTypeService = industryTypeService;
   }

   public ItemMappingService getItemMappingService()
   {
      return itemMappingService;
   }

   public void setItemMappingService( ItemMappingService itemMappingService )
   {
      this.itemMappingService = itemMappingService;
   }

   public BusinessContractTemplateService getBusinessContractTemplateService()
   {
      return businessContractTemplateService;
   }

   public void setBusinessContractTemplateService( BusinessContractTemplateService businessContractTemplateService )
   {
      this.businessContractTemplateService = businessContractTemplateService;
   }

   public LaborContractTemplateService getLaborContractTemplateService()
   {
      return laborContractTemplateService;
   }

   public void setLaborContractTemplateService( LaborContractTemplateService laborContractTemplateService )
   {
      this.laborContractTemplateService = laborContractTemplateService;
   }

   public ResignTemplateService getResignTemplateService()
   {
      return resignTemplateService;
   }

   public void setResignTemplateService( ResignTemplateService resignTemplateService )
   {
      this.resignTemplateService = resignTemplateService;
   }

   public ConstantService getConstantService()
   {
      return constantService;
   }

   public void setConstantService( ConstantService constantService )
   {
      this.constantService = constantService;
   }

   public MembershipService getMembershipService()
   {
      return membershipService;
   }

   public void setMembershipService( MembershipService membershipService )
   {
      this.membershipService = membershipService;
   }

   public CertificationService getCertificationService()
   {
      return certificationService;
   }

   public void setCertificationService( CertificationService certificationService )
   {
      this.certificationService = certificationService;
   }

   public SocialBenefitSolutionHeaderService getSocialBenefitSolutionHeaderService()
   {
      return socialBenefitSolutionHeaderService;
   }

   public void setSocialBenefitSolutionHeaderService( SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService )
   {
      this.socialBenefitSolutionHeaderService = socialBenefitSolutionHeaderService;
   }

   public CommercialBenefitSolutionHeaderService getCommercialBenefitSolutionHeaderService()
   {
      return commercialBenefitSolutionHeaderService;
   }

   public void setCommercialBenefitSolutionHeaderService( CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService )
   {
      this.commercialBenefitSolutionHeaderService = commercialBenefitSolutionHeaderService;
   }

   public CalendarHeaderService getCalendarHeaderService()
   {
      return calendarHeaderService;
   }

   public void setCalendarHeaderService( CalendarHeaderService calendarHeaderService )
   {
      this.calendarHeaderService = calendarHeaderService;
   }

   public ShiftHeaderService getShiftHeaderService()
   {
      return shiftHeaderService;
   }

   public void setShiftHeaderService( ShiftHeaderService shiftHeaderService )
   {
      this.shiftHeaderService = shiftHeaderService;
   }

   public com.kan.base.service.inf.management.PositionGradeService getEmployeePositionGradeService()
   {
      return employeePositionGradeService;
   }

   public void setEmployeePositionGradeService( com.kan.base.service.inf.management.PositionGradeService employeePositionGradeService )
   {
      this.employeePositionGradeService = employeePositionGradeService;
   }

   public TaxService getTaxService()
   {
      return taxService;
   }

   public void setTaxService( TaxService taxService )
   {
      this.taxService = taxService;
   }

   public ReportHeaderService getReportHeaderService()
   {
      return reportHeaderService;
   }

   public void setReportHeaderService( ReportHeaderService reportHeaderService )
   {
      this.reportHeaderService = reportHeaderService;
   }

   public ImportHeaderService getImportHeaderService()
   {
      return importHeaderService;
   }

   public void setImportHeaderService( ImportHeaderService importHeaderService )
   {
      this.importHeaderService = importHeaderService;
   }

   public BankService getBankService()
   {
      return bankService;
   }

   public void setBankService( BankService bankService )
   {
      this.bankService = bankService;
   }

   public final IncomeTaxBaseService getIncomeTaxBaseService()
   {
      return incomeTaxBaseService;
   }

   public final void setIncomeTaxBaseService( IncomeTaxBaseService incomeTaxBaseService )
   {
      this.incomeTaxBaseService = incomeTaxBaseService;
   }

   public final IncomeTaxRangeHeaderService getIncomeTaxRangeHeaderService()
   {
      return incomeTaxRangeHeaderService;
   }

   public final void setIncomeTaxRangeHeaderService( IncomeTaxRangeHeaderService incomeTaxRangeHeaderService )
   {
      this.incomeTaxRangeHeaderService = incomeTaxRangeHeaderService;
   }

   public ManagerHeaderService getManagerHeaderService()
   {
      return managerHeaderService;
   }

   public void setManagerHeaderService( ManagerHeaderService managerHeaderService )
   {
      this.managerHeaderService = managerHeaderService;
   }

   public BankTemplateHeaderService getBankTemplateHeaderService()
   {
      return bankTemplateHeaderService;
   }

   public void setBankTemplateHeaderService( BankTemplateHeaderService bankTemplateHeaderService )
   {
      this.bankTemplateHeaderService = bankTemplateHeaderService;
   }

   public TaxTemplateHeaderService getTaxTemplateHeaderService()
   {
      return taxTemplateHeaderService;
   }

   public void setTaxTemplateHeaderService( TaxTemplateHeaderService taxTemplateHeaderService )
   {
      this.taxTemplateHeaderService = taxTemplateHeaderService;
   }

   public MessageTemplateService getMessageTemplateService()
   {
      return messageTemplateService;
   }

   public void setMessageTemplateService( MessageTemplateService messageTemplateService )
   {
      this.messageTemplateService = messageTemplateService;
   }

   public SickLeaveSalaryHeaderService getSickLeaveSalaryHeaderService()
   {
      return sickLeaveSalaryHeaderService;
   }

   public void setSickLeaveSalaryHeaderService( SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService )
   {
      this.sickLeaveSalaryHeaderService = sickLeaveSalaryHeaderService;
   }

   public ExchangeRateService getExchangeRateService()
   {
      return exchangeRateService;
   }

   public void setExchangeRateService( ExchangeRateService exchangeRateService )
   {
      this.exchangeRateService = exchangeRateService;
   }

   /**
    * 获得所有的城市（key:城市名，value:cityId）
    * 
    * 
    */
   public final static Map< String, String > getCityMap()
   {
      return getCityMap( null );
   }

   /**
    * 获得所有的城市（key:城市名，value:cityId）
    */
   public final static Map< String, String > getCityMap( final String language )
   {
      final Map< String, String > cityMap = new TreeMap< String, String >();
      boolean isZH = language == null || language.equalsIgnoreCase( "ZH" );

      if ( LOCATION_DTO != null && LOCATION_DTO.getCountryDTOs() != null && LOCATION_DTO.getCountryDTOs().size() > 0 )
      {
         for ( CountryDTO countryDTO : LOCATION_DTO.getCountryDTOs().values() )
         {
            if ( countryDTO != null && countryDTO.getProvinceDTOs() != null )
            {
               for ( ProvinceDTO provinceDTO : countryDTO.getProvinceDTOs().values() )
               {
                  if ( provinceDTO != null && provinceDTO.getCityVOs() != null )
                  {
                     for ( Entry< String, CityVO > cityEntry : provinceDTO.getCityVOs().entrySet() )
                     {
                        cityMap.put( isZH ? cityEntry.getValue().getCityNameZH().replace( "市", "" ) : cityEntry.getValue().getCityNameEN(), cityEntry.getKey() );
                     }
                  }
               }
            }
         }
      }
      return cityMap;
   }

   @Override
   public void initLaborContractTemplate( String accountId ) throws KANException, RemoteException
   {
      accountConstantsMap.get( accountId ).initLaborContractTemplate( laborContractTemplateService );
   }

   // 判断是否存在目标AccountId
   private boolean containAccountId( final String[] debugAccountIdArray, final String accountId )
   {
      if ( debugAccountIdArray != null && debugAccountIdArray.length > 0 )
      {
         for ( String debugAccountId : debugAccountIdArray )
         {
            if ( KANUtil.filterEmpty( debugAccountId ) != null && KANUtil.filterEmpty( debugAccountId ).trim().equals( accountId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void initResignTemplate( String accountId ) throws KANException, RemoteException
   {
      accountConstantsMap.get( accountId ).initResignTemplate( resignTemplateService );
   }

   public static String getWkhtmltopdfPath()
   {
      return WKHTMLTOPDF_PATH;
   }

   public static void setWkhtmltopdfPath( String wkhtmltopdfPath )
   {
      KANConstants.WKHTMLTOPDF_PATH = wkhtmltopdfPath;
   }

   public static String getWkhtmltopdfParameter()
   {
      return WKHTMLTOPDF_PARAMETER;
   }

   public static void setWkhtmltopdfParameter( String wkhtmltopdfParameter )
   {
      KANConstants.WKHTMLTOPDF_PARAMETER = wkhtmltopdfParameter;
   }

   public static String getWkhtmltopdfTemp()
   {
      return WKHTMLTOPDF_TEMP_FILE_PATH;
   }

   public static void setWkhtmltopdfTemp( String wkhtmltopdfTemp )
   {
      KANConstants.WKHTMLTOPDF_TEMP_FILE_PATH = wkhtmltopdfTemp;
   }

   public AnnualLeaveRuleHeaderService getAnnualLeaveRuleHeaderService()
   {
      return annualLeaveRuleHeaderService;
   }

   public void setAnnualLeaveRuleHeaderService( AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService )
   {
      this.annualLeaveRuleHeaderService = annualLeaveRuleHeaderService;
   }

   public static String getHtmlCharset()
   {
      return WKHTMLTOPDF_HTML_CHARSET;
   }

   public static void setHtmlCharset( String htmlCharset )
   {
      KANConstants.WKHTMLTOPDF_HTML_CHARSET = htmlCharset;
   }

   @Override
   public Boolean validCaptcha( String sessionId, String captcha ) throws KANException, RemoteException
   {
      boolean flag = false;
      try
      {
         flag = CaptchaServiceSingleton.validate( sessionId, KANUtil.filterEmpty( captcha ) == null ? "" : captcha );
      }
      catch ( Exception e )
      {
         // e.printStackTrace();
         System.err.println( "验证码sessionId不存在." );
      }
      return flag;
   }

   public void setPrivateCode( final String privateCode )
   {
      PRIVATE_CODE = Encrypt.decodeUrl( privateCode );
   }

   /**
    * 判断所否是调试模式
    * 如果苏联接到21的数据库就算调试模式
    * @return
    */
   public static boolean isDebug()
   {
      try
      {
         final String path = "/KAN-HRO-INF/jdbc.properties";
         final String jdbcURL = KANUtil.getPropertiesValue( path, "jdbc.url" );
         ISDEBUG = !jdbcURL.contains( "10.11.110.121" );
      }
      catch ( Exception e )
      {
         return true;
      }
      return ISDEBUG;
   }

}

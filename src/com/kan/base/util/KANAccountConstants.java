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
   // ���Logger����
   protected Log logger = LogFactory.getLog( getClass() );

   /* Options Constants Start */

   public String ACCOUNT_ID;

   public String OPTIONS_LOGO_FILE = "images/logo/kanlogo_blue_en_s_s_new.png";

   public String OPTIONS_MOBILE_RIGHTS;

   public List< MappingVO > OPTIONS_CLIENT_LOGO_FILE = new ArrayList< MappingVO >();

   public List< MappingVO > OPTIONS_CLIENT_MOBILE_RIGHTS = new ArrayList< MappingVO >();

   // 1:�ܹ�ͼ��2:�б�
   public String OPTIONS_BRANCH_PREFER = "1";
   // 1:�ܹ�ͼ��2:��״
   public String OPTIONS_POSITION_PREFER = "1";
   // 0:��ͳ���Ӳ��Ż����²�������##1:ͳ���Ӳ��ż����²�������
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

   // ��ƽ����н��
   public double AVG_SALARY_DAYS_PER_MONTH;

   /**
    * Define
    */
   // ϵͳ����ı����ͼ������ϵͳ���������趨�����Զ�����ֶ� - DTO
   public List< TableDTO > TABLE_DTO = new ArrayList< TableDTO >();

   // �˻��Զ�����ֶη��� - VO
   public List< ColumnGroupVO > COLUMN_GROUP_VO = new ArrayList< ColumnGroupVO >();

   // �˻��Զ���ѡ�� - DTO
   public List< OptionDTO > COLUMN_OPTION_DTO = new ArrayList< OptionDTO >();

   // �˻��Զ������� - DTO
   public List< SearchDTO > SEARCH_DTO = new ArrayList< SearchDTO >();

   // �˻��Զ����б� - DTO
   public List< ListDTO > LIST_DTO = new ArrayList< ListDTO >();

   // �˻��Զ��屨�� - DTO��״̬���ѷ�����
   public List< ReportDTO > REPORT_DTO = new ArrayList< ReportDTO >();

   // �˻��Զ��嵼�� - DTO
   public List< ImportDTO > IMPORT_DTO = new ArrayList< ImportDTO >();

   // �˻��Զ���ҳ�� - DTO
   public List< ManagerDTO > MANAGER_DTO = new ArrayList< ManagerDTO >();

   // �˻��ͻ����뵼��ƥ�� - DTO
   public List< MappingDTO > MAPPING_DTO = new ArrayList< MappingDTO >();

   // �˻��Զ��幤��ģ�� - DTO
   public List< BankTemplateDTO > BANK_TEMPLATE_DTO = new ArrayList< BankTemplateDTO >();

   // �˻��Զ����˰ģ�� - DTO
   public List< TaxTemplateDTO > TAX_TEMPLATE_DTO = new ArrayList< TaxTemplateDTO >();

   /**
    * Security & System
    */
   // �˻���ЧԱ�� - DTO
   public List< StaffDTO > STAFF_DTO = new ArrayList< StaffDTO >();

   // �˻���ЧԱ�� - Base View
   public List< StaffBaseView > STAFF_BASEVIEW = new ArrayList< StaffBaseView >();

   // �˻���Чְλ - DTO
   public List< PositionDTO > POSITION_DTO = new ArrayList< PositionDTO >();

   // �˻���Чְλ - Base View
   public List< PositionBaseView > POSITION_BASEVIEW = new ArrayList< PositionBaseView >();

   // �˻���Чְλ - VO
   public List< PositionVO > POSITION_VO = new ArrayList< PositionVO >();

   // �˻���Чְλ�� - DTO
   public List< GroupDTO > POSITION_GROUP_DTO = new ArrayList< GroupDTO >();

   // �˻���Чְλ�� - Base View
   public List< GroupBaseView > POSITION_GROUP_BASEVIEW = new ArrayList< GroupBaseView >();

   // �˻���Чְλ�� - VO
   public List< GroupVO > POSITION_GROUP_VO = new ArrayList< GroupVO >();

   // �˻���Ч���� - DTO
   public List< SkillBaseView > SKILL_BASEVIEW = new ArrayList< SkillBaseView >();

   // �˻���Ч�ص� - VO
   public List< LocationVO > LOCATION_VO = new ArrayList< LocationVO >();

   // ϵͳ��־ģ��
   public List< LogVO > SYS_LOG_VO = new ArrayList< LogVO >();

   public List< MappingVO > SYS_LOG_OPER_TYPE = new ArrayList< MappingVO >();

   // �˻���Ч���� - VO
   public List< BranchVO > BRANCH_VO = new ArrayList< BranchVO >();

   // �˻���Ч���� - DTO
   public List< BranchDTO > BRANCH_DTO = new ArrayList< BranchDTO >();

   // �˻���Чְλ�ȼ� - VO
   public List< PositionGradeVO > POSITION_GRADE_VO = new ArrayList< PositionGradeVO >();

   // �˻�ȫ���趨��ģ�� - DTO
   public List< AccountModuleDTO > MODULE_DTO = new ArrayList< AccountModuleDTO >();

   // �˻�ȫ���趨��ģ�� - VO
   public List< ModuleVO > MODULE_VO = new ArrayList< ModuleVO >();

   // �˻�ȫ���趨��ģ�� - VO
   public List< ModuleVO > CLIENT_SELECT_MODULE_VO = new ArrayList< ModuleVO >();

   // �˻���������ϵͳ������
   public List< ConstantVO > CONSTANT_VO = new ArrayList< ConstantVO >();

   /**
    * Message
    */

   public List< MessageTemplateVO > MESSAGE_TEMPLATE_VO = new ArrayList< MessageTemplateVO >();

   /**
    * Management
    */
   // �˻���Ч����ʵ�� - VO
   public List< EntityVO > ENTITY_VO = new ArrayList< EntityVO >();

   // �˻���Ч˰�� - VO
   public List< TaxVO > TAX_VO = new ArrayList< TaxVO >();

   // �˻���Ч�Ͷ���ͬ���� - VO
   public List< ContractTypeVO > CONTRACT_TYPE_VO = new ArrayList< ContractTypeVO >();

   // �˻���Ч��Ӷ״̬ - VO
   public List< EmployeeStatusVO > EMPLOYEE_STATUS_VO = new ArrayList< EmployeeStatusVO >();

   // �˻���Ч�����̶� - VO
   public List< EducationVO > EDUCATION_VO = new ArrayList< EducationVO >();

   // �˻���Ч�������� - VO
   public List< LanguageVO > LANGUAGE_VO = new ArrayList< LanguageVO >();

   // �˻��л��� - VO
   public List< ExchangeRateVO > EXCHANGE_RATE_VO = new ArrayList< ExchangeRateVO >();

   // �˻���Ч���� - DTO
   public List< SkillDTO > SKILL_DTO = new ArrayList< SkillDTO >();

   // ��Ա��Чְλ - DTO
   public List< com.kan.base.domain.management.PositionDTO > EMPLOYEE_POSITION_DTO = new ArrayList< com.kan.base.domain.management.PositionDTO >();

   // �˻���Чְλ�ȼ� - VO
   public List< com.kan.base.domain.management.PositionGradeVO > EMPLOYEE_POSITION_GRADE_VO = new ArrayList< com.kan.base.domain.management.PositionGradeVO >();

   // �˻���Ч�Ŀ�Ŀ - VO
   public List< ItemVO > ITEM_VO = new ArrayList< ItemVO >();

   // �˻���Ч������ - VO
   public List< BankVO > BANK_VO = new ArrayList< BankVO >();

   // �˻���Ч�Ŀ�Ŀ���� - DTO
   public List< ItemGroupDTO > ITEM_GROUP_DTO = new ArrayList< ItemGroupDTO >();

   // �˻���Ч��ҵ������ - VO
   public List< BusinessTypeVO > BUSINESS_TYPE_VO = new ArrayList< BusinessTypeVO >();

   // �˻���Ч����ҵ���� - VO
   public List< IndustryTypeVO > INDUSTRY_TYPE_VO = new ArrayList< IndustryTypeVO >();

   // �˻���Ч��Ŀӳ�� - VO
   public List< ItemMappingVO > ITEM_MAPPING_VO = new ArrayList< ItemMappingVO >();

   // �˻���Ч�����ͬ - VO
   public List< BusinessContractTemplateVO > BUSINESS_CONTRACT_TEMPLATE_VO = new ArrayList< BusinessContractTemplateVO >();

   // �˻���Ч�����ͬ - VO
   public List< LaborContractTemplateVO > LABOR_CONTRACT_TEMPLATE_VO = new ArrayList< LaborContractTemplateVO >();

   //�˻���Ч�����˵� VO
   public List< ResignTemplateVO > RESIGN_TEMPLATE_VO = new ArrayList< ResignTemplateVO >();

   // �˻���Ч���Ż - VO
   public List< MembershipVO > MEMBERSHIP_VO = new ArrayList< MembershipVO >();

   //  �˻���Ч֤�� - ���� -VO
   public List< CertificationVO > CERTIFICATION_VO = new ArrayList< CertificationVO >();

   // �˻���Ч�籣���� - DTO
   public List< SocialBenefitSolutionDTO > SOCIAL_BENEFIT_SOLUTION_DTO = new ArrayList< SocialBenefitSolutionDTO >();

   // �˻���Ч�̱����� - DTO
   public List< CommercialBenefitSolutionDTO > COMMERCIAL_BENEFIT_SOLUTION_DTO = new ArrayList< CommercialBenefitSolutionDTO >();

   // �˻���Ч���� - DTO
   public List< CalendarDTO > CALENDAR_DTO = new ArrayList< CalendarDTO >();

   // �˻���Ч�Ű� - DTO
   public List< ShiftDTO > SHIFT_DTO = new ArrayList< ShiftDTO >();

   // �˻���Ч���ٷ��� - DTO
   public List< SickLeaveSalaryDTO > SICK_LEAVE_SALARY_DTO = new ArrayList< SickLeaveSalaryDTO >();

   // �˻���Ч��ٹ��� - DTO
   public List< AnnualLeaveRuleDTO > ANNUAL_LEAVE_RULE_DTO = new ArrayList< AnnualLeaveRuleDTO >();

   /**
    * Workflow
    */
   // �˻��趨�Ĺ����� - DTO
   public List< WorkflowModuleDTO > WORKFLOW_MODULE_DTO = new ArrayList< WorkflowModuleDTO >();

   /**
    * Business
    */

   // ����
   public KANAccountConstants( final String accountId )
   {
      ACCOUNT_ID = accountId;
   }

   // ��ʼ��Options����
   public void initOptions( final OptionsService optionsService ) throws KANException
   {
      try
      {
         final OptionsVO optionsVO = optionsService.getOptionsVOByAccountId( ACCOUNT_ID );

         if ( optionsVO != null )
         {
            // ��ȡDate Format��ʽ
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

            // ��ȡBRANCH�ܹ�չ����ʽ
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
            // ��ȡPOSITION�ܹ�չ����ʽ
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
            // �����Ƿ�ͳ���Ӳ�������
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
               //����ͳ�ƣ�Ĭ�ϸ�Ϊ��ͳ��
               OPTIONS_ISSUMSUBBRANCHHC = "0";
            }

            // ��ʼ��UseBrowserLanguage����
            if ( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equalsIgnoreCase( OptionsVO.TRUE ) )
            {
               OPTIONS_USE_BS_LANGUAGE = true;
            }
            else if ( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equalsIgnoreCase( OptionsVO.FALSE ) )
            {
               OPTIONS_USE_BS_LANGUAGE = false;
            }

            // ��ȡDate Format��ʽ
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.dateformat" );
            // ��ʼ��DateFormat����
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

            // ��ȡTime Format��ʽ
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "options.timeformat" );
            // ��ʼ��TimeFormat����
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

            // ��ȡPage Style��ʽ
            OPTIONS_PAGE_STYLE = optionsVO.getPageStyle();

            // ��ȡ���ŷ�������
            OPTIONS_SMS_CONFIG = optionsVO.getSmsGetway();

            // ��ʼ�������󶨺�ͬ����
            if ( optionsVO.getOrderBindContract() != null && optionsVO.getOrderBindContract().equalsIgnoreCase( OptionsVO.TRUE ) )
            {
               OPTIONS_ORDER_BIND_CONTRACT = true;
            }
            else if ( optionsVO.getOrderBindContract() != null && optionsVO.getOrderBindContract().equalsIgnoreCase( OptionsVO.FALSE ) )
            {
               OPTIONS_ORDER_BIND_CONTRACT = false;
            }

            // ��ȡ�籣�걨���� - ����
            OPTIONS_SB_GENERATE_CONDITION = optionsVO.getSbGenerateCondition();

            // ��ȡ�̱��깺���� - ����
            OPTIONS_CB_GENERATE_CONDITION = optionsVO.getCbGenerateCondition();

            // ��ȡ���㴦������ - ����
            OPTIONS_SETTLEMENT_GENERATE_CONDITION = optionsVO.getSettlementCondition();

            // ��ȡ�籣�걨���� - ����Э��
            OPTIONS_SB_GENERATE_CONDITION_SC = optionsVO.getSbGenerateConditionSC();

            // ��ȡ�̱��깺���� - ����Э��
            OPTIONS_CB_GENERATE_CONDITION_SC = optionsVO.getCbGenerateConditionSC();

            // ��ȡ���㴦������ - ����Э��
            OPTIONS_SETTLEMENT_GENERATE_CONDITION_SC = optionsVO.getSettlementConditionSC();

            // ��ȡ����
            mappingVOs = KANUtil.getMappings( Locale.CHINA, "def.list.detail.accuracy" );
            // ��ʼ��Accuracy����
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

            //��ƽ������
            AVG_SALARY_DAYS_PER_MONTH = Double.valueOf( optionsVO.getMonthAvg() == null || optionsVO.getMonthAvg().equals( "" ) ? "21.75" : optionsVO.getMonthAvg() );

            // ��ȡȡ��
            OPTIONS_ROUND = optionsVO.getRound();

            // ��ʼ��logoFile
            initLogoFile( optionsVO.getLogoFile(), optionsVO.getLogoFileSize() );

            // ��ȡHRSERVICE�ֻ�ģ��Ȩ��
            OPTIONS_MOBILE_RIGHTS = optionsVO.getMobileModuleRightIds();

            // ��ӿͻ���Ƭ
            OPTIONS_CLIENT_LOGO_FILE.clear();
            OPTIONS_CLIENT_LOGO_FILE.addAll( optionsVO.getClientLogoFiles() );

            // ��ȡINHOUSE �ֻ�ģ��Ȩ��
            OPTIONS_CLIENT_MOBILE_RIGHTS.clear();
            OPTIONS_CLIENT_MOBILE_RIGHTS.addAll( optionsVO.getClientMobileModuleRightIds() );

            // ��ȡ������˰
            OPTIONS_INDEPENDENCE_TAX = optionsVO.getIndependenceTax();

            // �Ӱ������С��λ
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
      // ���û�ҵ��ͷ���Ĭ��
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
            //1 ��ȡ���ط������ļ���
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
            //2 �Ƚ�Զ�̷������ļ��뱾�ط������ļ�
            String localLogoPath = KANUtil.basePath + "/" + localLogoFileName;
            File localLogoFile = new File( localLogoPath );

            File localLogoFileDir = new File( KANUtil.basePath + "/" + subName + ACCOUNT_ID + "/" + corpId + "/" );
            if ( !localLogoFileDir.exists() )
            {
               localLogoFileDir.mkdirs();
            }
            //2.1 ���ش���ͬ���ļ���
            if ( localLogoFile.isFile() )
            {
               // �Ա��ļ���С
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
                  logger.info( "�ļ����س���", e );
               }
            }
         }
      }
      return localLogoFileName;
   }

   // ��ʼ��Options����
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
            //1 ��ȡ���ط������ļ���
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
            //2 �Ƚ�Զ�̷������ļ��뱾�ط������ļ�
            String localLogoPath = KANUtil.basePath + "/" + localLogoFileName;
            File localLogoFile = new File( localLogoPath );

            File localLogoFileDir = new File( KANUtil.basePath + "/" + subName + ACCOUNT_ID + "/" );
            if ( !localLogoFileDir.exists() )
            {
               localLogoFileDir.mkdirs();
            }
            //2.1 ���ش���ͬ���ļ���
            if ( localLogoFile.isFile() )
            {
               // �Ա��ļ���С
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
                  logger.info( "�ļ����س���", e );
                  return;
               }
            }

            //logoFile��ֵ
            OPTIONS_LOGO_FILE = localLogoFileName;
         }
      }
   }

   // ��ʼ��Email Configuration����
   public void initEmailConfiguration( final EmailConfigurationService emailConfigurationService ) throws KANException
   {
      try
      {
         final EmailConfigurationVO emailConfigurationVO = emailConfigurationService.getEmailConfigurationVOByAccountId( ACCOUNT_ID );

         if ( emailConfigurationVO != null )
         {
            // ��ȡ�ʼ����������Ϣ
            OPTIONS_MAIL_SHOW_NAME = emailConfigurationVO.getShowName();

            // Decode�ʼ���������
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

            // �˿ڲ�Ϊ�ղ��������������ö˿�
            if ( emailConfigurationVO.getSmtpPort() != null && emailConfigurationVO.getSmtpPort().matches( ( "[0-9]*" ) ) )
            {
               MAIL_SMTP_PORT = emailConfigurationVO.getSmtpPort();
            }

            MAIL_USERNAME = emailConfigurationVO.getUsername();

            MAIL_PASSWORD = emailConfigurationVO.getPassword();

            // Decode�ʼ��ʼ���֤����
            if ( emailConfigurationVO.getSmtpAuthType() != null && emailConfigurationVO.getSmtpAuthType().equals( "1" ) )
            {
               MAIL_SMTP_AUTH_TYPE = true;
            }
            else if ( emailConfigurationVO.getSmtpAuthType() != null && emailConfigurationVO.getSmtpAuthType().equals( "2" ) )
            {
               MAIL_SMTP_AUTH_TYPE = false;
            }

            // Decode�ʼ��ʼ���ȫ����
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

            // �˿ڲ�Ϊ�ղ��������������ö˿�
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

   // ��ʼ��ShareFolder Configuration����
   public void initShareFolderConfiguration( final ShareFolderConfigurationService shareFolderConfigurationService ) throws KANException
   {
      try
      {
         final ShareFolderConfigurationVO shareFolderConfigurationVO = shareFolderConfigurationService.getShareFolderConfigurationVOByAccountId( ACCOUNT_ID );

         if ( shareFolderConfigurationVO != null )
         {
            SHAREFOLDER_HOST = shareFolderConfigurationVO.getHost();

            // �˿ڲ�Ϊ�ղ��������������ö˿�
            if ( shareFolderConfigurationVO.getPort() != null && shareFolderConfigurationVO.getPort().matches( ( "[0-9]*" ) ) )
            {
               SHAREFOLDER_PORT = shareFolderConfigurationVO.getPort();
            }

            SHAREFOLDER_USERNAME = shareFolderConfigurationVO.getUsername();

            SHAREFOLDER_PASSWORD = shareFolderConfigurationVO.getPassword();

            // ��׼��ShareFolderĿ¼�ַ���
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

   // ��ʼ��Tables����
   public void initTable( final TableService tableService ) throws KANException
   {
      try
      {
         // Clear First
         TABLE_DTO.clear();

         // ��ʼ��Table DTO
         TABLE_DTO = tableService.getTableDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Table: " + TABLE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��Tables����
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
         // ��ʼ��Table DTO

         logger.info( "Loading Account - " + ACCOUNT_ID + " Table: " + TABLE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��ColumnGroups����
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

   // ��ʼ��Column Options����
   public void initColumnOptions( final OptionHeaderService optionHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         COLUMN_OPTION_DTO.clear();

         // װ���û��Զ����ѡ��
         COLUMN_OPTION_DTO = optionHeaderService.getOptionDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Column Options: " + COLUMN_OPTION_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // ��ʼ��SearchHeader����
   public void initSearchHeader( final SearchHeaderService searchHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         SEARCH_DTO.clear();

         // װ���û��Զ��������
         SEARCH_DTO = searchHeaderService.getSearchDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Search: " + SEARCH_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // ��ʼ��ListHeader����
   public void initListHeader( final ListHeaderService listHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         LIST_DTO.clear();

         // װ���û��Զ��������
         LIST_DTO = listHeaderService.getListDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " List: " + LIST_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // ��ʼ��MappingHeader����
   public void initMappingHeader( MappingHeaderService mappingHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         MAPPING_DTO.clear();

         // װ�ؿͻ�ƥ��
         MAPPING_DTO = mappingHeaderService.getMappingDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Mapping: " + MAPPING_DTO.size() + " counts" );
      }
      catch ( KANException e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��ReportHeader����
   public void initReportHeader( final ReportHeaderService reportHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         REPORT_DTO.clear();

         // װ���û��Զ���ı���
         REPORT_DTO = reportHeaderService.getReportDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Report: " + REPORT_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // ��ʼ��ImportHeader����
   public void initImportHeader( final ImportHeaderService importHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         IMPORT_DTO.clear();

         // װ���û��Զ���ĵ���
         IMPORT_DTO = importHeaderService.getImportDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Import: " + IMPORT_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // ��ʼ��ManagerHeader����
   public void initManagerHeader( final ManagerHeaderService managerHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         MANAGER_DTO.clear();

         // װ���û��Զ����ҳ��
         MANAGER_DTO = managerHeaderService.getManagerDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Manager: " + MANAGER_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   // ��ʼ��BankTemplateHeader����
   public void initBankTemplateHeader( final BankTemplateHeaderService bankTemplateHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         BANK_TEMPLATE_DTO.clear();

         // װ���û��Զ����ҳ��
         BANK_TEMPLATE_DTO = bankTemplateHeaderService.getBankTemplateDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " BankTemplate: " + BANK_TEMPLATE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��TaxTemplateHeader����
   public void initTaxTemplateHeader( final TaxTemplateHeaderService taxTemplateHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         TAX_TEMPLATE_DTO.clear();

         // װ���û��Զ����ҳ��
         TAX_TEMPLATE_DTO = taxTemplateHeaderService.getTaxTemplateDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " TaxTemplate: " + TAX_TEMPLATE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��Staff����
   public void initStaff( final StaffService staffService ) throws KANException
   {
      try
      {
         // Clear First
         STAFF_DTO.clear();
         STAFF_BASEVIEW.clear();

         // ��ʼ��Staff DTO
         STAFF_DTO = staffService.getStaffDTOsByAccountId( ACCOUNT_ID );

         // ��ʼ�� StaffBaseView
         final List< Object > staffBaseViews = staffService.getStaffBaseViewsByAccountId( ACCOUNT_ID );
         if ( staffBaseViews != null )
         {
            // ����StaffBaseView List
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

   // ��ʼ��Staff �˵�����
   public void initStaffMenu( final StaffService staffService, final String reportHeaderId ) throws KANException
   {
      try
      {

         logger.info( "Loading Account - " + ACCOUNT_ID + " Staff menu start time: " + KANUtil.getDay( new Date() ) );

         //��ʼ��staff���¼�andͬ���ŵ� position
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
   //    * ���õ�ǰstaff�������¼�position(�����˴���)
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
   //      //��Ŀ��ڵ���ӽڵ�
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
   //         //����Ŀ��ڵ���ӽڵ�
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
   //               //�����ӽڵ�
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
   //    * ���õ�ǰstaff�������ϼ�position��Ӧ�ð����˴���position��
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
   //         //������Ľڵ㲻��Ҫ���ϼ��ˡ�
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

   // ��ʼ��Staff���� - ����
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

   // ��ʼ��Staff����ɾ�� - ����
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

   // ��ʼ��StaffBaseView���� - ����
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

   // ��ʼ��Location����
   public void initLocation( final LocationService locationService ) throws KANException
   {
      try
      {
         // Clear First
         LOCATION_VO.clear();

         final List< Object > locationVOs = locationService.getLocationVOsByAccountId( ACCOUNT_ID );

         if ( locationVOs != null )
         {
            // ����LocationVO List
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

   // ��ʼ��log module����
   public void initSystemLogModule( final LogService logService ) throws KANException
   {
      try
      {
         // Clear First
         SYS_LOG_VO.clear();

         final List< Object > logVOs = logService.getLogModules();

         if ( logVOs != null )
         {
            // ����LocationVO List
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

   // ��ʼ��log oper type����
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

   // ��ʼ��Branch����
   public void initBranch( final BranchService branchService ) throws KANException
   {
      try
      {
         // Clear First
         BRANCH_VO.clear();

         final List< Object > branchVOs = branchService.getBranchVOsByAccountId( ACCOUNT_ID );

         if ( branchVOs != null )
         {
            // ����BranchVO List
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

   // ����Ա��ID
   public List< String > getStaffIdsInBranch( final String branchId )
   {
      final List< StaffVO > staffVOsInBranch = new ArrayList< StaffVO >();
      // �����µ�ְλ
      final List< PositionVO > positionVOs = getPositionVOsByBranchId( branchId );
      // ְλ�����staff
      for ( PositionVO positionVO : positionVOs )
      {
         final List< StaffDTO > staffDTOs = getStaffDTOsByPositionId( positionVO.getPositionId() );
         for ( StaffDTO staffDTO : staffDTOs )
         {
            // ��������еĹ�ϵ
            final List< PositionStaffRelationVO > positionStaffRelations = staffDTO.getPositionStaffRelationVOs();
            if ( positionStaffRelations != null && positionStaffRelations.size() > 0 )
            {
               for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelations )
               {
                  // �ҵ� ����������ְλ�ϵĹ�ϵ
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

   // ��ʼ��Skill����
   public void initSkill( final SkillService skillService ) throws KANException
   {
      try
      {
         // Clear First
         SKILL_DTO.clear();
         SKILL_BASEVIEW.clear();

         // ��ʼ��Skill DTO
         SKILL_DTO = skillService.getSkillDTOsByAccountId( ACCOUNT_ID );
         // ��ʼ��SkillBaseview
         SKILL_BASEVIEW = skillService.getSkillBaseViewsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Skill: " + SKILL_BASEVIEW.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��EMPLOYEE_POSITION
   public void initEmployeePosition( final com.kan.base.service.inf.management.PositionService positionService ) throws KANException
   {
      try
      {
         // Clear First
         EMPLOYEE_POSITION_DTO.clear();

         // ��ʼ��Position DTO
         EMPLOYEE_POSITION_DTO = positionService.getPositionDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Employee Position: " + EMPLOYEE_POSITION_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��Position Grade����
   public void initEmployeePositionGrade( final com.kan.base.service.inf.management.PositionGradeService positionGradeService ) throws KANException
   {
      try
      {
         // Clear First
         EMPLOYEE_POSITION_GRADE_VO.clear();

         final List< Object > positionGradeVOs = positionGradeService.getPositionGradeVOsByAccountId( ACCOUNT_ID );

         if ( positionGradeVOs != null )
         {
            // ����PositionGradeVO List
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

   // ��ʼ��Position����
   public void initPosition( final PositionService positionService ) throws KANException
   {
      try
      {
         List< PositionDTO > POSITION_DTO_TEMP = new ArrayList< PositionDTO >();
         List< PositionBaseView > POSITION_BASEVIEW_TEMP = new ArrayList< PositionBaseView >();
         List< PositionVO > POSITION_VO_TEMP = new ArrayList< PositionVO >();

         // ��ʼ��Position DTO
         POSITION_DTO_TEMP = positionService.getPositionDTOsByAccountId( ACCOUNT_ID );

         // ��ʼ��Position View
         final List< Object > positionBaseViews = positionService.getPositionBaseViewsByAccountId( ACCOUNT_ID );

         if ( positionBaseViews != null )
         {
            // ����PositionBaseView List
            PositionBaseView positionBaseView;
            for ( Object positionBaseViewObject : positionBaseViews )
            {
               positionBaseView = ( PositionBaseView ) positionBaseViewObject;
               POSITION_BASEVIEW_TEMP.add( positionBaseView );
            }
         }

         // ��ʼ��Position VO
         final List< Object > positionVOs = positionService.getPositionVOsByAccountId( ACCOUNT_ID );

         if ( positionVOs != null )
         {
            // ����PositionVO List
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
    * ���ְλ����ȫˢ����.������ɺ���Ҫˢ������ְλ�ڴ���߸�����Ч
    * ����ˢ��ר��.���⵼�����.
    * ������Ҫ����
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

   // ��ʼ��Group����
   public void initPositionGroup( final GroupService groupService ) throws KANException
   {
      try
      {
         // Clear First
         POSITION_GROUP_DTO.clear();
         POSITION_GROUP_BASEVIEW.clear();
         POSITION_GROUP_VO.clear();

         // ��ʼ��Position Group DTO
         POSITION_GROUP_DTO = groupService.getGroupDTOsByAccountId( ACCOUNT_ID );

         // ��ʼ��Position Group View
         final List< Object > groupBaseViews = groupService.getGroupBaseViewsByAccountId( ACCOUNT_ID );

         if ( groupBaseViews != null )
         {
            // ����GroupBaseView List
            GroupBaseView groupBaseView;
            for ( Object groupBaseViewObject : groupBaseViews )
            {
               groupBaseView = ( GroupBaseView ) groupBaseViewObject;
               POSITION_GROUP_BASEVIEW.add( groupBaseView );
            }
         }

         // ��ʼ��Position Group VO
         final List< Object > groupVOs = groupService.getGroupVOsByAccountId( ACCOUNT_ID );

         if ( groupVOs != null )
         {
            // ����GroupVO List
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

   // ��ʼ��Position Grade����
   public void initPositionGrade( final PositionGradeService positionGradeService ) throws KANException
   {
      try
      {
         // Clear First
         POSITION_GRADE_VO.clear();

         final List< Object > positionGradeVOs = positionGradeService.getPositionGradeVOsByAccountId( ACCOUNT_ID );

         if ( positionGradeVOs != null )
         {
            // ����PositionGradeVO List
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

   // ��ʼ��Module����
   public void initModule( final ModuleService moduleService ) throws KANException
   {
      try
      {
         // Clear First
         MODULE_DTO.clear();
         MODULE_VO.clear();

         // ��ʼ��Module DTO
         MODULE_DTO = moduleService.getAccountModuleDTOsByAccountId( ACCOUNT_ID );

         // ��ʼ��Module VO
         final List< Object > moduleVOs = moduleService.getAccountModuleVOsByAccountId( ACCOUNT_ID );

         if ( moduleVOs != null )
         {
            // ����ModuleVO List
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

   // ��ʼ��Constant������
   public void initConstant( final ConstantService constantService ) throws KANException
   {
      try
      {
         // �����CONSTANT_VO����
         CONSTANT_VO.clear();

         // ��ʼ��ConstantVO
         final List< Object > constantVOs = constantService.getConstantVOsByAccountId( ACCOUNT_ID );
         // ����ConstantVO
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

   // ��ʼ��WorkFlwoModuleDTO����
   public void initWorkflow( final WorkflowModuleService workflowModuleService ) throws KANException
   {
      try
      {
         // Clear First
         this.WORKFLOW_MODULE_DTO.clear();

         // ��ʼ��Module DTO
         WORKFLOW_MODULE_DTO = workflowModuleService.getAccountWorkflowDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Workflow Module: " + WORKFLOW_MODULE_DTO.size() + " counts" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��Entity����
   public void initEntity( final EntityService entityService ) throws KANException
   {
      try
      {
         // Clear First
         ENTITY_VO.clear();

         // ��ʼ��EntityVO
         final List< Object > entityVOs = entityService.getEntityVOsByAccountId( ACCOUNT_ID );

         if ( entityVOs != null )
         {
            // ����EntityVO List
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

   // ��ʼ��Contract Type����
   public void initContractType( final ContractTypeService contractTypeService ) throws KANException
   {
      try
      {
         // Clear First
         CONTRACT_TYPE_VO.clear();

         // ��ʼ��ContractTypeVO
         final List< Object > contractTypeVOs = contractTypeService.getContractTypeVOsByAccountId( ACCOUNT_ID );

         if ( contractTypeVOs != null )
         {
            // ����ContractTypeVO List
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

   // ��ʼ��Employee Status����
   public void initEmployeeStatus( final EmployeeStatusService employeeStatusService ) throws KANException
   {
      try
      {
         // Clear First
         EMPLOYEE_STATUS_VO.clear();

         // ��ʼ��EmployeeStatusVO
         final List< Object > employeeStatusVOs = employeeStatusService.getEmployeeStatusVOsByAccountId( ACCOUNT_ID );

         if ( employeeStatusVOs != null )
         {
            // ����EmployeeStatusVO List
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

   // ��ʼ��Education����
   public void initEducation( final EducationService educationService ) throws KANException
   {
      try
      {
         // Clear First
         EDUCATION_VO.clear();

         // ��ʼ��EducationVO
         final List< Object > educationVOs = educationService.getEducationVOsByAccountId( ACCOUNT_ID );

         if ( educationVOs != null )
         {
            // ����EducationVO List
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

   // ��ʼ��Language����
   public void initLanguage( final LanguageService languageService ) throws KANException
   {
      try
      {

         // Clear First
         LANGUAGE_VO.clear();

         // ��ʼ��LanguageVO
         final List< Object > languageVOs = languageService.getLanguageVOsByAccountId( ACCOUNT_ID );

         if ( languageVOs != null )
         {
            // ����LanguageVO List
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

   // ��ʼ��ExchangeRate����
   public void initExchangeRate( final ExchangeRateService exchangeRateService ) throws KANException
   {
      try
      {

         // Clear First
         EXCHANGE_RATE_VO.clear();

         // ��ʼ��ExchangeRateVOs
         final List< Object > exchangeRateVOs = exchangeRateService.getExchangeRateVOsByAccountId( ACCOUNT_ID );

         if ( exchangeRateVOs != null )
         {
            // ����LanguageVO List
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

   // ��ʼ��Item����
   public void initItem( final ItemService itemService ) throws KANException
   {
      try
      {
         // Clear First
         ITEM_VO.clear();
         // ��ʼ��ItemVO List
         final List< Object > itemVOs = new ArrayList< Object >();

         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            itemVOs.addAll( itemService.getItemVOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }

         itemVOs.addAll( itemService.getItemVOsByAccountId( ACCOUNT_ID ) );

         if ( itemVOs != null )
         {
            // ����ItemVO List
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

   // ��ʼ��Bank����
   public void initBank( final BankService bankService ) throws KANException
   {
      try
      {
         // Clear First
         BANK_VO.clear();
         // ��ʼ��ItemVO List
         final List< Object > bankVOs = new ArrayList< Object >();

         if ( ACCOUNT_ID != KANConstants.SUPER_ACCOUNT_ID )
         {
            bankVOs.addAll( bankService.getBankVOsByAccountId( KANConstants.SUPER_ACCOUNT_ID ) );
         }
         bankVOs.addAll( bankService.getBankVOsByAccountId( ACCOUNT_ID ) );

         if ( bankVOs != null )
         {
            // ����BankVO List
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

   // ��ʼ��Tax����
   public void initTax( final TaxService taxService ) throws KANException
   {
      try
      {
         // Clear First
         TAX_VO.clear();
         // ��ʼ��TaxVO List
         final List< Object > taxVOs = taxService.getTaxVOsByAccountId( ACCOUNT_ID );

         if ( taxVOs != null )
         {
            // ����TaxVO List
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

   // ��ʼ��ItemGroup����
   public void initItemGroup( final ItemGroupService itemGroupService ) throws KANException
   {
      try
      {
         // Clear First
         ITEM_GROUP_DTO.clear();

         // ��ʼ��ItemGroup DTO
         ITEM_GROUP_DTO = itemGroupService.getItemGroupDTOsByAccountId( ACCOUNT_ID );

         logger.info( "Loading Account - " + ACCOUNT_ID + " Item Group: " + ITEM_GROUP_DTO.size() + " counts" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��BusinessType����
   public void initBusinessType( final BusinessTypeService businessTypeService ) throws KANException
   {
      try
      {
         // Clear First
         BUSINESS_TYPE_VO.clear();

         // ��ʼ��BusinessTypeVO List
         final List< Object > businessTypeVOs = businessTypeService.getBusinessTypeVOsByAccountId( ACCOUNT_ID );

         if ( businessTypeVOs != null )
         {
            // ����BusinessTypeVO List
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

   // ��ʼ��IndustryType����
   public void initIndustryType( final IndustryTypeService industryTypeService ) throws KANException
   {
      try
      {
         // Clear First
         INDUSTRY_TYPE_VO.clear();
         // ��ʼ��IndustryTypeVO List
         final List< Object > industryTypeVOs = industryTypeService.getIndustryTypeVOsByAccountId( ACCOUNT_ID );

         if ( industryTypeVOs != null )
         {
            // ����IndustryTypeVO List
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

   // ��ʼ��BusinessContractTemplate����
   public void initBusinessContractTemplate( final BusinessContractTemplateService businessContractTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         BUSINESS_CONTRACT_TEMPLATE_VO.clear();

         // ��ʼ��BusinessContractTemplateVO List
         final List< Object > businessContractTemplateVOs = businessContractTemplateService.getBusinessContractTemplateVOsByAccountId( ACCOUNT_ID );

         if ( businessContractTemplateVOs != null )
         {
            // ����BusinessContractTemplateVO List
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

   // ��ʼ��LaborContractTemplate����
   public void initLaborContractTemplate( final LaborContractTemplateService laborContractTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         LABOR_CONTRACT_TEMPLATE_VO.clear();

         // ��ʼ��LaborContractTemplateVO List
         final List< Object > laborContractTemplateVOs = laborContractTemplateService.getLaborContractTemplateVOsByAccountId( ACCOUNT_ID );

         if ( laborContractTemplateVOs != null )
         {
            // ����LaborContractTemplateVO List
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

   // ��ʼ��LaborContractTemplate����
   public void initResignTemplate( final ResignTemplateService resignTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         RESIGN_TEMPLATE_VO.clear();

         // ��ʼ��resignTemplateVO List
         final List< Object > resignTemplateVOs = resignTemplateService.getResignTemplateVOsByAccountId( ACCOUNT_ID );

         if ( resignTemplateVOs != null )
         {
            // ����resignTemplateVO List
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

   // ��ʼ��Membership����
   public void initMembership( final MembershipService membershipService ) throws KANException
   {
      try
      {
         // Clear First
         MEMBERSHIP_VO.clear();
         // ��ʼ��MembershipVO List
         final List< Object > membershipVOs = membershipService.getMembershipVOsByAccountId( ACCOUNT_ID );

         if ( membershipVOs != null && membershipVOs.size() > 0 )
         {
            // ����MembershipVO List
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

   // ��ʼ��certification����
   public void initCertification( final CertificationService certificationService ) throws KANException
   {
      try
      {
         // Clear First
         CERTIFICATION_VO.clear();
         // ��ʼ��CertificationVO List

         final List< Object > certificationVOs = certificationService.getCertificationVOsByAccountId( ACCOUNT_ID );

         if ( certificationVOs != null && certificationVOs.size() > 0 )
         {
            // ����MembershipVO List
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

   // ��ʼ���籣����
   public void initSocialBenefitSolution( final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         SOCIAL_BENEFIT_SOLUTION_DTO.clear();
         // ��ʼ��SOCIAL_BENEFIT_SOLUTION_DTO
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

   // ��ʼ���̱�����
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

   // ��ʼ�����ٹ���
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

   // ��ʼ����ٹ���
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

   // ��ʼ������
   public void initCalendar( final CalendarHeaderService calendarHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         CALENDAR_DTO.clear();

         // ��ʼ��CALENDAR_DTO
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

   // ��ʼ���Ű�
   public void initShift( final ShiftHeaderService shiftHeaderService ) throws KANException
   {
      try
      {
         // Clear First
         SHIFT_DTO.clear();

         // ��ʼ��SHIFT_DTO
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

   // ��ʼ��ItemMapping����
   public void initItemMapping( final ItemMappingService itemMappingService ) throws KANException
   {
      try
      {
         // Clear First
         ITEM_MAPPING_VO.clear();

         // ��ʼ��ItemMappingVO List
         final List< Object > itemMappingVOs = itemMappingService.getItemMappingVOsByAccountId( ACCOUNT_ID );

         if ( itemMappingVOs != null )
         {
            // ����ItemMappingVO List
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

   // ��ʼ����Ϣģ��
   public void initMessageTemplate( final MessageTemplateService messageTemplateService ) throws KANException
   {
      try
      {
         // Clear First
         MESSAGE_TEMPLATE_VO.clear();
         // ��ʼ��ItemMappingVO List
         final List< Object > messageTemplateVOs = messageTemplateService.getMessageTemplateVOByAccountId( ACCOUNT_ID );
         if ( messageTemplateVOs != null )
         {
            // ����ItemMappingVO List
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

   // ����PositionId��ȡPositionName
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

   // ����StaffId��ȡStaffBaseView
   public StaffBaseView getStaffBaseViewByStaffId( final String staffId )
   {
      // ����StaffBaseView List
      for ( StaffBaseView staffBaseView : STAFF_BASEVIEW )
      {
         if ( staffBaseView != null && staffBaseView.getId() != null && staffBaseView.getId().trim().equals( staffId ) )
         {
            return staffBaseView;
         }
      }

      return null;
   }

   // ����StaffId��ȡStaffName
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

   // ����StaffId��ȡStaffName
   public String getStaffNameByStaffId( final String staffId )
   {
      return getStaffNameByStaffId( staffId, false );
   }

   // ����StaffId��ȡStaffName
   // iClickҪȡ�Զ����ֶΡ�jiancheng��
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

   // ����UserId��ȡStaffName
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

   // ��ȡCountry ����
   public List< MappingVO > getCountries( final String localeLanguage )
   {
      return getCountries( localeLanguage, null );
   }

   // ��ȡCountry ����
   public List< MappingVO > getCountries( final String localeLanguage, final String corpId )
   {
      return KANConstants.LOCATION_DTO.getCountries( localeLanguage );
   }

   // ��ȡProvince
   public List< MappingVO > getProvinces( final String localeLanguage )
   {
      return getProvinces( localeLanguage, null );
   }

   // ��ȡProvince
   public List< MappingVO > getProvinces( final String localeLanguage, final String corpId )
   {
      return KANConstants.LOCATION_DTO.getProvinces( localeLanguage );
   }

   // ��ȡLocationVO
   public LocationVO getLocationVOByLocationId( final String locationId )
   {
      // ����Location List
      for ( LocationVO locationVO : LOCATION_VO )
      {
         if ( locationId != null && locationVO != null && locationVO.getLocationId() != null && locationId.equals( locationVO.getLocationId() ) )
         {
            return locationVO;
         }
      }

      return null;
   }

   // ��ȡLocation
   public List< MappingVO > getLocations( final String localeLanguage )
   {
      return getLocations( localeLanguage, null );
   }

   // ��ȡLocation
   public List< MappingVO > getLocations( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      List< MappingVO > locations = new ArrayList< MappingVO >();

      // ����Location List
      for ( LocationVO locationVO : LOCATION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( locationVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( locationVO.getCorpId() ) != null && locationVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

      // ����Location List
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

   // ��ȡLocation
   public List< MappingVO > getSystemLogModule( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      List< MappingVO > logVOs = new ArrayList< MappingVO >();

      // ����Location List
      for ( LogVO vo : SYS_LOG_VO )
      {
         // ��ʼ��MappingVO����
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

   // ��ȡLocation
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

   // ��ȡBranch - By parentId
   public List< MappingVO > getBranchsByParentBranchId( final String parentBranchId )
   {
      final List< MappingVO > branchs = new ArrayList< MappingVO >();
      return getBranchsByParentBranchId( branchs, BRANCH_DTO, parentBranchId );
   }

   // �ݹ��ȡBranch - By parentId
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

   // ��ȡBranch
   public List< MappingVO > getBranchs( final String localeLanguage )
   {
      return getBranchs( localeLanguage, null );
   }

   // ��ȡBranch
   public List< MappingVO > getBranchs( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      List< MappingVO > branchs = new ArrayList< MappingVO >();

      /*// ����Branch List
      for ( BranchVO branchVO : BRANCH_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchVO.getCorpId() ) != null && branchVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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
                  singleOption += "����";
               }
               else
               {
                  if ( i == 0 )
                  {
                     singleOption += "&nbsp;��";
                  }
                  else if ( i == 1 )
                  {
                     singleOption += "����";
                  }
                  else if ( i < level )
                  {
                     singleOption += "����";
                  }
                  else if ( i == level )
                  {
                     singleOption += ( index + 1 == branchDTOs.size() ? "����" : "����" );
                  }
               }
            }
            // �Ƿ�ѡ��
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

   // ����branchId��ȡBranchVO
   public List< MappingVO > getBUFunction()
   {
      // ��ʼ������ֵ����
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

   // ����name�ҵ�BUFunction��ID
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

   // ����branchId��ȡBranchVO
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
         // �����INhouse
         if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) == null )
         {
            continue;
         }
         // �����HRService
         if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) != null )
         {
            continue;
         }
         branchDTOs.add( branchDTO );
      }
      return branchDTOs;
   }

   // ����ְλ����ȡ���ţ���������Ϣ
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

   // ����ְλ��ȡ��ְλ
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

   // ��ȡPosition Group - MappingVO
   public List< MappingVO > getPositionGroups( final String localeLanguage )
   {
      // ��ʼ������ֵ����
      List< MappingVO > positionGroups = new ArrayList< MappingVO >();

      // ����Group List
      for ( GroupVO groupVO : POSITION_GROUP_VO )
      {
         // ��ʼ��MappingVO����
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

   // ��ȡPosition Group - MappingVO
   public List< MappingVO > getPositionGroups( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      List< MappingVO > positionGroups = new ArrayList< MappingVO >();

      // ����Group List
      for ( GroupVO groupVO : POSITION_GROUP_VO )
      {
         if ( KANUtil.filterEmpty( corpId ) == null || ( KANUtil.filterEmpty( corpId ) != null && groupVO.getCorpId() != null && groupVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // ��ȡPosition Group
   public List< GroupVO > getPositionGroupVOs()
   {
      return getPositionGroupVOs( null );
   }

   // ��ȡPosition Group
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

   // ����PositionGradeId��ȡPositionGradeVO
   public PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGradeId )
   {
      // ����PositionGradeVO List
      for ( PositionGradeVO positionGradeVO : POSITION_GRADE_VO )
      {
         if ( positionGradeVO.getPositionGradeId() != null && positionGradeVO.getPositionGradeId().trim().equals( positionGradeId ) )
         {
            return positionGradeVO;
         }
      }

      return null;
   }

   // ��ȡPosition Grade
   public List< MappingVO > getPositionGrades( final String localeLanguage )
   {
      return getPositionGrades( localeLanguage, null );
   }

   // ��ȡPosition Grade
   public List< MappingVO > getPositionGrades( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > positionGrades = new ArrayList< MappingVO >();

      // ����Position Grade List
      for ( PositionGradeVO positionGradeVO : POSITION_GRADE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ) != null && KANUtil.filterEmpty( positionGradeVO.getCorpId() ).equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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
    * ���ڵ���.
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

   // ����PositionGradeId��ȡPositionGradeVO
   public com.kan.base.domain.management.PositionGradeVO getEmployeePositionGradeVOByPositionGradeId( final String positionGradeId )
   {
      // ����PositionGradeVO List
      for ( com.kan.base.domain.management.PositionGradeVO positionGradeVO : EMPLOYEE_POSITION_GRADE_VO )
      {
         if ( positionGradeVO.getPositionGradeId() != null && positionGradeVO.getPositionGradeId().trim().equals( positionGradeId ) )
         {
            return positionGradeVO;
         }
      }

      return null;
   }

   // ��ȡPosition Grade
   public List< MappingVO > getEmployeePositionGrades( final String localeLanguage )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > positionGrades = new ArrayList< MappingVO >();

      // ����Position Grade List
      for ( com.kan.base.domain.management.PositionGradeVO positionGradeVO : EMPLOYEE_POSITION_GRADE_VO )
      {
         // ��ʼ��MappingVO����
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

   // ��ȡEntity
   public List< MappingVO > getEntities( final String localeLanguage )
   {
      return getEntities( localeLanguage, null );
   }

   // ��ȡEntity
   public List< MappingVO > getEntities( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > entities = new ArrayList< MappingVO >();

      // ����EntityVO List
      for ( EntityVO entityVO : ENTITY_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( entityVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( entityVO.getCorpId() ) != null && entityVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // ��ȡ��ImportHeaders
   public List< MappingVO > getImportHeaders( final String localeLanguage )
   {
      return getImportHeaders( localeLanguage, null );
   }

   // ��ȡ��ImportHeaders
   public List< MappingVO > getImportHeaders( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > importHeaders = new ArrayList< MappingVO >();

      // ����EntityVO List
      for ( ImportDTO importDTO : IMPORT_DTO )
      {
         ImportHeaderVO importHeaderVO = importDTO.getImportHeaderVO();
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( importHeaderVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( importHeaderVO.getCorpId() ) != null && importHeaderVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // ��ȡ��ImportHeaders
   public List< ImportHeaderVO > getImportHeaderVOs( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< ImportHeaderVO > importHeaders = new ArrayList< ImportHeaderVO >();

      // ����EntityVO List
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

   // ��ȡ��ReportHeaders
   public List< MappingVO > getReportHeaders( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > reportHeaders = new ArrayList< MappingVO >();

      // ����
      for ( ReportDTO reportDTO : REPORT_DTO )
      {
         if ( reportDTO != null && reportDTO.getReportHeaderVO() != null && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getReportHeaderId() ) != null )
         {
            // �����hr service
            if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getCorpId() ) != null )
            {
               continue;
            }
            // �����inhouse
            if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getCorpId() ) != null )
            {
               continue;
            }
            // ��ʼ��MappingVO����
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

   // ��ȡEntityVO
   public EntityVO getEntityVOByEntityId( final String entityId )
   {
      // ����EntityVO List
      for ( EntityVO entityVO : ENTITY_VO )
      {
         if ( entityVO.getEntityId() != null && entityVO.getEntityId().trim().equals( entityId ) )
         {
            return entityVO;
         }
      }

      return null;
   }

   // ��ȡEntityVO
   public EntityVO getEntityVOByEntityName( final String entityName, final String corpId )
   {
      // ����EntityVO List
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

   // ��ȡContractType
   public List< MappingVO > getContractTypes( final String localeLanguage )
   {
      return getContractTypes( localeLanguage, null );
   }

   // ��ȡContractType
   public List< MappingVO > getContractTypes( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > contractTypes = new ArrayList< MappingVO >();

      // ����ContractTypeVO List
      for ( ContractTypeVO contractTypeVO : CONTRACT_TYPE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( contractTypeVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( contractTypeVO.getCorpId() ) != null && contractTypeVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // ��ȡEmployeeStatus
   public List< MappingVO > getEmployeeStatuses( final String localeLanguage )
   {
      return getEmployeeStatuses( localeLanguage, null );
   }

   // ��ȡEmployeeStatus
   public List< MappingVO > getEmployeeStatuses( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > employeeStatuses = new ArrayList< MappingVO >();

      // ����EmployeeStatusVO List
      for ( EmployeeStatusVO employeeStatusVO : EMPLOYEE_STATUS_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( employeeStatusVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( employeeStatusVO.getCorpId() ) != null && employeeStatusVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // ��ȡEducation
   public List< MappingVO > getEducations( final String localeLanguage )
   {
      return getEducations( localeLanguage, null );
   }

   // ��ȡEducation
   public List< MappingVO > getEducations( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > educations = new ArrayList< MappingVO >();

      // ����EducationVO List
      for ( EducationVO educationVO : EDUCATION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( educationVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( educationVO.getCorpId() ) != null && educationVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // ��ȡLanguage
   public List< MappingVO > getLanguages( final String localeLanguage )
   {
      return getLanguages( localeLanguage, null );
   }

   // ��ȡLanguage
   public List< MappingVO > getLanguages( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > languages = new ArrayList< MappingVO >();

      // ����LanguageVO List
      for ( LanguageVO languageVO : LANGUAGE_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( languageVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( languageVO.getCorpId() ) != null && languageVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // ��ȡExchangeRate
   public ExchangeRateVO getExchangeRateVOByCurrencyCode( final String corpId, final String currencyCode )
   {
      // ����ExchangeRateVO List
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

   // �������Items
   public List< MappingVO > getItems( final String localeLanguage )
   {
      return getItems( localeLanguage, null );
   }

   // �������Items
   public List< MappingVO > getItems( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > items = new ArrayList< MappingVO >();

      // ����Item Map
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( itemVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && ( ( KANUtil.filterEmpty( itemVO.getCorpId() ) != null && itemVO.getCorpId().equals( corpId ) ) || itemVO.getAccountId().equals( "1" ) ) ) )
            {
               // ��ʼ��MappingVO����
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

   // �������Items
   public List< ItemVO > getItemVOsByCorpId( final String corpId )
   {
      // ��ʼ������ֵ����
      final List< ItemVO > items = new ArrayList< ItemVO >();

      // ����Item Map
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

   // ���ItemBaseView��type=0���п�Ŀ��type=1�������籣���������ʹ�ã�
   public List< ItemVO > getItemVOsByType( final String type )
   {
      // ��ʼ������ֵ����
      final List< ItemVO > itemVOs = new ArrayList< ItemVO >();

      // ����Item Map
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

   // �������Taxes
   public List< MappingVO > getTaxes( final String localeLanguage )
   {
      return getTaxes( localeLanguage, null );
   }

   // �������Taxes
   public List< MappingVO > getTaxes( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > taxs = new ArrayList< MappingVO >();

      // ����Tax Map
      if ( TAX_VO != null && TAX_VO.size() > 0 )
      {
         for ( TaxVO taxVO : TAX_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( taxVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( taxVO.getCorpId() ) != null && taxVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ����itemId��ȡItem MappingVO��ʽ
   public MappingVO getItemByItemId( final String itemId, final String localeLanguage )
   {
      // ��ʼ������ֵ����
      final MappingVO mappingVO = new MappingVO();

      // ����Item Map
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

   // �������ͻ�ÿ�Ŀ�б� �����ʡ����������𡢼Ӱࡢ�������ݼ١��籣���̱�������ѡ����ա��������ɱ���������
   public List< MappingVO > getItemsByType( final String type, final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > items = new ArrayList< MappingVO >();
      // ����Item Map
      if ( ITEM_VO != null && ITEM_VO.size() > 0 )
      {
         for ( ItemVO itemVO : ITEM_VO )
         {

            // ����ǡ�����ѡ���ӡ�Ӫҵ˰��
            if ( "141".equals( itemVO.getItemId() ) && "9".equals( type ) )
            {
               // ��ʼ��MappingVO����
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
                  // ��ʼ��MappingVO����
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

   // ��ù��ʵ�Items
   public List< MappingVO > getSalaryItems( final String localeLanguage )
   {
      return getSalaryItems( localeLanguage, null );
   }

   // ��ù��ʵ�Items
   public List< MappingVO > getSalaryItems( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > items = new ArrayList< MappingVO >();
      items.addAll( getItemsByType( "1", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "2", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "3", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "4", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "5", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "13", localeLanguage, corpId ) );
      return items;
   }

   // ��üӰ��Items
   public List< MappingVO > getOtItems( final String localeLanguage )
   {
      return getOtItems( localeLanguage, null );
   }

   // ��üӰ��Items
   public List< MappingVO > getOtItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "4", localeLanguage, corpId );
   }

   // �����ٵ�Items
   public List< MappingVO > getLeaveItems( final String localeLanguage )
   {
      return getLeaveItems( localeLanguage, null );
   }

   // �����ٵ�Items
   public List< MappingVO > getLeaveItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "6", localeLanguage, corpId );
   }

   // ��ÿ��ڣ�������١��Ӱࣩ��Items
   public List< MappingVO > getAttendanceItems( final String localeLanguage )
   {
      return getAttendanceItems( localeLanguage, null );
   }

   // ��ÿ��ڣ�������١��Ӱࣩ��Items
   public List< MappingVO > getAttendanceItems( final String localeLanguage, final String corpId )
   {
      List< MappingVO > returnList = new ArrayList< MappingVO >();
      returnList.addAll( getItemsByType( "4", localeLanguage, corpId ) );
      returnList.addAll( getItemsByType( "6", localeLanguage, corpId ) );
      return returnList;
   }

   // ����籣��Items
   public List< MappingVO > getSbItems( final String localeLanguage )
   {
      return getSbItems( localeLanguage, null );
   }

   // ����籣��Items
   public List< MappingVO > getSbItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "7", localeLanguage, corpId );
   }

   // ����̱���Items
   public List< MappingVO > getCbItems( final String localeLanguage )
   {
      return getCbItems( localeLanguage, null );
   }

   // ����̱���Items
   public List< MappingVO > getCbItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "8", localeLanguage, corpId );
   }

   // ��÷���ѵ�Items
   public List< MappingVO > getServiceFeeItems( final String localeLanguage )
   {
      return getServiceFeeItems( localeLanguage, null );
   }

   // ��÷���ѵ�Items
   public List< MappingVO > getServiceFeeItems( final String localeLanguage, final String corpId )
   {
      return getItemsByType( "9", localeLanguage, corpId );
   }

   // ���������Items
   public List< MappingVO > getOtherItems( final String localeLanguage )
   {
      return getOtherItems( localeLanguage, null );
   }

   // ���������Items
   public List< MappingVO > getOtherItems( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > items = new ArrayList< MappingVO >();
      items.addAll( getItemsByType( "10", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "11", localeLanguage, corpId ) );
      items.addAll( getItemsByType( "12", localeLanguage, corpId ) );

      return items;
   }

   // ���ItemGroup
   public List< MappingVO > getItemGroups( final String localeLanguage )
   {
      return getItemGroups( localeLanguage, null );
   }

   // ���ItemGroup
   public List< MappingVO > getItemGroups( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > itemGroups = new ArrayList< MappingVO >();

      // ����Item Map
      if ( ITEM_GROUP_DTO != null && ITEM_GROUP_DTO.size() > 0 )
      {
         for ( ItemGroupDTO itemGroupDTO : ITEM_GROUP_DTO )
         {
            // ��ʼ��ItemGroupVO
            final ItemGroupVO itemGroupVO = itemGroupDTO.getItemGroupVO();

            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( itemGroupVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( itemGroupVO.getCorpId() ) != null && itemGroupVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���Bank
   public List< MappingVO > getBanks( final String localeLanguage )
   {
      return getBanks( localeLanguage, null );
   }

   // ���Bank
   public List< MappingVO > getBanks( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > banks = new ArrayList< MappingVO >();

      // ����BANK_VO
      if ( BANK_VO != null && BANK_VO.size() > 0 )
      {
         for ( BankVO bankVO : BANK_VO )
         {
            if ( ( KANUtil.filterEmpty( bankVO.getAccountId() ) != null && bankVO.getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( bankVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( bankVO.getCorpId() ) != null && bankVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���BusinessType
   public List< MappingVO > getBusinessTypes( final String localeLanguage )
   {
      return getBusinessTypes( localeLanguage, null );
   }

   // ���BusinessType
   public List< MappingVO > getBusinessTypes( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > businessTypes = new ArrayList< MappingVO >();

      // ����BusinessType Map
      if ( BUSINESS_TYPE_VO != null && BUSINESS_TYPE_VO.size() > 0 )
      {
         for ( BusinessTypeVO businessTypeVO : BUSINESS_TYPE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( businessTypeVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( businessTypeVO.getCorpId() ) != null && businessTypeVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���ItemMapping
   public List< MappingVO > getItemMappings( final String localeLanguage )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > itemMappings = new ArrayList< MappingVO >();

      // ����BusinessType Map
      if ( ITEM_MAPPING_VO != null && ITEM_MAPPING_VO.size() > 0 )
      {
         for ( ItemMappingVO itemMappingVO : ITEM_MAPPING_VO )
         {
            // ��ʼ��MappingVO����
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

   // ���IndustryType
   public List< MappingVO > getIndustryTypes( final String localeLanguage )
   {
      return getIndustryTypes( localeLanguage, null );
   }

   // ���IndustryType
   public List< MappingVO > getIndustryTypes( final String localeLanguage, String corpId )
   {
      // ��ҵ���������ж��Ƿ�ΪINHOUSE  OR  HRSERVICE
      corpId = null;
      // ��ʼ������ֵ����
      final List< MappingVO > industryTypes = new ArrayList< MappingVO >();

      // ����IndustryType Map
      if ( INDUSTRY_TYPE_VO != null && INDUSTRY_TYPE_VO.size() > 0 )
      {
         for ( IndustryTypeVO industryTypeVO : INDUSTRY_TYPE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( industryTypeVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( industryTypeVO.getCorpId() ) != null && industryTypeVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���BusinessContractTemplate
   public List< MappingVO > getBusinessContractTemplates( final String localeLanguage )
   {
      return getBusinessContractTemplates( localeLanguage, null );
   }

   // ���BusinessContractTemplate
   public List< MappingVO > getBusinessContractTemplates( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > businessContractTemplates = new ArrayList< MappingVO >();

      // ����BusinessContractTemplate Map
      if ( BUSINESS_CONTRACT_TEMPLATE_VO != null && BUSINESS_CONTRACT_TEMPLATE_VO.size() > 0 )
      {
         for ( BusinessContractTemplateVO businessContractTemplateVO : BUSINESS_CONTRACT_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( businessContractTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( businessContractTemplateVO.getCorpId() ) != null && businessContractTemplateVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���Business Type
   public BusinessTypeVO getBusinessTypeById( final String id )
   {
      // ����
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

   // ���Business Type
   public BusinessTypeVO getBusinessTypeByName( final String name, final String corpId )
   {
      // ����
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

   // ���LaborContractTemplate
   public List< MappingVO > getLaborContractTemplates( final String localeLanguage )
   {
      return getLaborContractTemplates( localeLanguage, null );
   }

   // ���LaborContractTemplate
   public List< MappingVO > getLaborContractTemplates( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > laborContractTemplates = new ArrayList< MappingVO >();

      // ����LaborContractTemplate Map
      if ( LABOR_CONTRACT_TEMPLATE_VO != null && LABOR_CONTRACT_TEMPLATE_VO.size() > 0 )
      {
         for ( LaborContractTemplateVO laborContractTemplateVO : LABOR_CONTRACT_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( laborContractTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( laborContractTemplateVO.getCorpId() ) != null && laborContractTemplateVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���LaborContractTemplate
   public LaborContractTemplateVO getLaborContractTemplatesByName( final String name, final String corpId )
   {
      // ����LaborContractTemplate Map
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

   // ���LaborContractTemplate
   public List< MappingVO > getResignTemplates( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > resignTemplates = new ArrayList< MappingVO >();

      // ����LaborContractTemplate Map
      if ( RESIGN_TEMPLATE_VO != null && RESIGN_TEMPLATE_VO.size() > 0 )
      {
         for ( ResignTemplateVO resignTemplateVO : RESIGN_TEMPLATE_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( resignTemplateVO.getCorpId() ) != null && resignTemplateVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���ResignTemplateVO
   public ResignTemplateVO getResignTemplatesByName( final String name, final String corpId )
   {
      // ����LaborContractTemplate Map
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

   // ���ResignTemplateVO
   public ResignTemplateVO getResignTemplatesById( final String id, final String corpId )
   {
      // ����LaborContractTemplate Map
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

   // ���Membership
   public List< MappingVO > getMemberships( final String localeLanguage )
   {
      return getMemberships( localeLanguage, null );
   }

   // ���Membership
   public List< MappingVO > getMemberships( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > memberships = new ArrayList< MappingVO >();

      // ����Membership Map
      if ( MEMBERSHIP_VO != null && MEMBERSHIP_VO.size() > 0 )
      {
         for ( MembershipVO membershipVO : MEMBERSHIP_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( membershipVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( membershipVO.getCorpId() ) != null && membershipVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ���certification
   public List< MappingVO > getCertifications( final String localeLanguage )
   {
      return getCertifications( localeLanguage, null );
   }

   // ���certification
   public List< MappingVO > getCertifications( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > certifications = new ArrayList< MappingVO >();

      // ����Membership Map
      if ( CERTIFICATION_VO != null && CERTIFICATION_VO.size() > 0 )
      {
         for ( CertificationVO certificationVO : CERTIFICATION_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( certificationVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( certificationVO.getCorpId() ) != null && certificationVO.getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ��ȡSocialBenefitSolutions��Ӧ�� MappingVO
   public List< MappingVO > getSocialBenefitSolutions( final String localeLanguage )
   {
      return getSocialBenefitSolutions( localeLanguage, null );
   }

   // ��ȡSocialBenefitSolutions��Ӧ�� MappingVO
   // Reviewed by Kevin Jin at 2013-09-18
   public List< MappingVO > getSocialBenefitSolutions( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > socialBenefitSolutionHeaders = new ArrayList< MappingVO >();

      // ����SocialBenefitSolutionDTO
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

   // ��ȡ�籣���� - �����籣����Id
   // Reviewed by Kevin Jin at 2013-09-18
   public SocialBenefitSolutionDTO getSocialBenefitSolutionDTOByHeaderId( final String headerId )
   {
      // ����SocialBenefitSolutionDTOs
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

   // ���SocialBenefitSolutionDTO
   public SocialBenefitSolutionDTO getSocialBenefitSolutionDTOByName( final String name, final String corpId )
   {
      // ����SocialBenefitSolutionDTOs
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

   // ���CommercialBenefitSolutionDTO
   public CommercialBenefitSolutionDTO getCommercialBenefitSolutionDTOByName( final String name, final String corpId )
   {
      // ����COMMERCIAL_BENEFIT_SOLUTION_DTO
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

   // ��ȡ�̱����� - �����̱�����Id
   // Reviewed by Kevin Jin at 2013-09-18
   public CommercialBenefitSolutionDTO getCommercialBenefitSolutionDTOByHeaderId( final String headerId )
   {
      // ����CommercialBenefitSolutionDTOs
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

   // ��ȡSocialBenefitSolution��Ӧ�� MappingVO
   public List< MappingVO > getCommercialBenefitSolutions( final String localeLanguage )
   {
      return getCommercialBenefitSolutions( localeLanguage, null );
   }

   // ��ȡSocialBenefitSolution��Ӧ�� MappingVO
   // Reviewed by Kevin Jin at 2013-11-21
   public List< MappingVO > getCommercialBenefitSolutions( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > commercialBenefitSolutionHeaders = new ArrayList< MappingVO >();

      // ����SocialBenefitSolutionDTO
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

   // ��ȡColumn Group
   public List< MappingVO > getColumnGroup( final String localeLanguage )
   {
      return getColumnGroup( localeLanguage, null );
   }

   // ��ȡColumn Group
   public List< MappingVO > getColumnGroup( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > columnGroups = new ArrayList< MappingVO >();

      // ����Column Group Map
      if ( COLUMN_GROUP_VO != null && COLUMN_GROUP_VO.size() > 0 )
      {
         for ( ColumnGroupVO columnGroupVO : COLUMN_GROUP_VO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) != null && corpId.equals( KANUtil.filterEmpty( columnGroupVO.getCorpId() ) ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ��ȡColumn Option
   public List< MappingVO > getColumnOptions( final String localeLanguage )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > columnOptions = new ArrayList< MappingVO >();

      // ����Column Option List
      if ( COLUMN_OPTION_DTO != null && COLUMN_OPTION_DTO.size() > 0 )
      {
         for ( OptionDTO optionDTO : COLUMN_OPTION_DTO )
         {
            final OptionHeaderVO optionHeaderVO = optionDTO.getOptionHeaderVO();

            // ��ʼ��MappingVO����
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
      // ��ʼ������ֵ����
      final List< MappingVO > searchHeaders = new ArrayList< MappingVO >();

      // ����SearchDTO List
      if ( SEARCH_DTO != null && SEARCH_DTO.size() > 0 )
      {
         for ( SearchDTO searchDTO : SEARCH_DTO )
         {
            final SearchHeaderVO searchHeaderVO = searchDTO.getSearchHeaderVO();

            if ( ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( searchHeaderVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                  && KANUtil.filterEmpty( searchHeaderVO.getCorpId() ) != null && corpId.equals( KANUtil.filterEmpty( searchHeaderVO.getCorpId() ) ) ) )
                  && searchHeaderVO.getTableId() != null && searchHeaderVO.getTableId().trim().equals( tableId ) )
            {
               // ��ʼ��MappingVO����
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
      // ��ʼ������ֵ����
      final List< MappingVO > searchDetails = new ArrayList< MappingVO >();

      // ����SearchDTO List
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
               // ��ʼ��MappingVO����
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

   // ��ȡListDTO MappingVO��ʽ
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

   // ��ȡListDTO MappingVO��ʽ
   public List< MappingVO > getLists( final String localeLanguage, final boolean isJavaObject, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > lists = new ArrayList< MappingVO >();

      // ����LIST_DTO list
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

            // ��ʼ��MappingVO����
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

   // ����SkillId��ö�Ӧ��SkillDTOs
   public List< SkillDTO > getSkillDTOsByParentSkillId( final String skillId )
   {
      return getSkillDTOsByParentSkillId( SKILL_DTO, skillId, null );
   }

   public List< SkillDTO > getSkillDTOsByParentSkillId( final String skillId, final String corpId )
   {
      return getSkillDTOsByParentSkillId( SKILL_DTO, skillId, KANUtil.filterEmpty( corpId ) );
   }

   // ����ParentSkillId��ö�Ӧ��SkillDTO - �ݹ麯��
   private List< SkillDTO > getSkillDTOsByParentSkillId( List< SkillDTO > skillDTOs, final String ParentSkillId, final String corpId )
   {
      // ʵ����childSkillDTOs
      final List< SkillDTO > childSkillDTOs = new ArrayList< SkillDTO >();
      if ( skillDTOs != null && skillDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( SkillDTO skillDTO : skillDTOs )
         {

            // ���DTO��Ŀ������򷵻�
            if ( skillDTO.getSkillVO() != null && skillDTO.getSkillVO().getParentSkillId() != null && skillDTO.getSkillVO().getParentSkillId().equals( ParentSkillId ) )
            {
               if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) == null )
                     || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) != null && skillDTO.getSkillVO().getCorpId().equals( corpId ) ) )
               {
                  childSkillDTOs.add( skillDTO );
               }
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( skillDTO.getSkillDTOs() != null && skillDTO.getSkillDTOs().size() > 0 )
            {
               // ����ֵ���õ�ǰ�ڵ��ڴ�
               List< SkillDTO > skillDTOTemp = getSkillDTOsByParentSkillId( skillDTO.getSkillDTOs(), ParentSkillId, corpId );

               // ����ӽڵ�����Ŀ�������������
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

   // ����ParentSkillId��ö�Ӧ��SkillDTO - �ݹ麯��
   private SkillDTO getSkillDTOBySkillId( List< SkillDTO > skillDTOs, final String skillId, final String corpId )
   {
      // ʵ����childSkillDTOs
      if ( skillDTOs != null && skillDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( SkillDTO skillDTO : skillDTOs )
         {

            // ���DTO��Ŀ������򷵻�
            if ( skillDTO.getSkillVO() != null && skillDTO.getSkillVO().getSkillId() != null && skillDTO.getSkillVO().getSkillId().equals( skillId ) )
            {
               if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) == null )
                     || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) != null && skillDTO.getSkillVO().getCorpId().equals( corpId ) ) )
               {
                  return skillDTO;
               }
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( skillDTO.getSkillDTOs() != null && skillDTO.getSkillDTOs().size() > 0 )
            {
               // ����ֵ���õ�ǰ�ڵ��ڴ�
               SkillDTO skillDTOTemp = getSkillDTOBySkillId( skillDTO.getSkillDTOs(), skillId, corpId );

               // ����ӽڵ�����Ŀ�������������
               if ( skillDTOTemp != null )
               {
                  return skillDTOTemp;
               }
            }
         }
      }
      return null;
   }

   // ����StaffId��ö�Ӧ��Position Count
   public int getPositionCountByStaffId( final String staffId )
   {
      final List< PositionDTO > positionDTOs = getPositionDTOsByStaffId( staffId );

      return positionDTOs == null ? 0 : positionDTOs.size();
   }

   // ����StaffId��ö�Ӧ��PositionDTO�б�
   public List< PositionDTO > getPositionDTOsByStaffId( final String staffId )
   {
      return getPositionDTOsByStaffId( POSITION_DTO, staffId );
   }

   public List< PositionDTO > getPositionDTOsByCorpId( final String corpId )
   {
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();
      for ( PositionDTO positionDTO : POSITION_DTO )
      {
         // �����INhouse
         if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) == null )
         {
            continue;
         }
         // �����HRService
         if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) != null )
         {
            continue;
         }
         positionDTOs.add( positionDTO );
      }
      return positionDTOs;
   }

   // ����StaffId��ö�Ӧ��PositionDTO�б� - �ݹ麯��
   private List< PositionDTO > getPositionDTOsByStaffId( final List< PositionDTO > positionDTOs, final String staffId )
   {
      // ��ǰ�û����ڵ�PositionDTO�б�
      final List< PositionDTO > matchedPositionDTOs = new ArrayList< PositionDTO >();

      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // ���DTO��Ŀ������򷵻�
            if ( positionDTO.containsStaffId( staffId ) )
            {
               matchedPositionDTOs.add( positionDTO );
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // ����ֱֵ�Ӵ��뵱ǰPositionDTO�б�
               matchedPositionDTOs.addAll( getPositionDTOsByStaffId( positionDTO.getPositionDTOs(), staffId ) );
            }
         }
      }

      return matchedPositionDTOs;
   }

   // ����PositionId��ö�Ӧ��PositionVO
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

   // ����positionId��ȡBUHeader Position
   public PositionVO getBUHeaderPositionVOByPositionId( final String positionId )
   {
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );
      return getBUHeaderPositionVOByPositionId( positionDTO );
   }

   // ����positionId��ȡBUHeader Position
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

   // ����PositionId��ö�Ӧ��PositionDTO
   public PositionDTO getPositionDTOByPositionId( final String positionId )
   {
      return getPositionDTOByPositionId( POSITION_DTO, positionId );
   }

   // ����PositionId��ö�Ӧ��PositionDTO - �ݹ麯��
   private PositionDTO getPositionDTOByPositionId( List< PositionDTO > positionDTOs, final String positionId )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // ���DTO��Ŀ������򷵻�
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getPositionId() != null && positionDTO.getPositionVO().getPositionId().equals( positionId ) )
            {
               return positionDTO;
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // ����ֵ���õ�ǰ�ڵ��ڴ�
               positionDTO = getPositionDTOByPositionId( positionDTO.getPositionDTOs(), positionId );

               // ����ӽڵ�����Ŀ�������������
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // ����PositionId��ö�Ӧ��PositionDTO
   public PositionDTO getPositionDTOByPositionName( final String corpId, final String positionName )
   {
      return getPositionDTOByPositionName( POSITION_DTO, corpId, positionName );
   }

   // ����PositionId��ö�Ӧ��PositionDTO - �ݹ麯��
   private PositionDTO getPositionDTOByPositionName( List< PositionDTO > positionDTOs, final String corpId, final String positionName )
   {
      String corpID = KANUtil.filterEmpty( corpId );
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            final String corpID_p = KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() );
            // ���DTO��Ŀ������򷵻�
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getTitleZH() != null && positionDTO.getPositionVO().getTitleZH().equals( positionName )
                  && ( ( corpID_p == null && KANUtil.filterEmpty( corpId ) == null ) || ( corpID_p != null && KANUtil.filterEmpty( corpId ) != null && corpID.equals( corpID_p ) ) ) )
            {
               return positionDTO;
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // ����ֵ���õ�ǰ�ڵ��ڴ�
               positionDTO = getPositionDTOByPositionName( positionDTO.getPositionDTOs(), corpId, positionName );

               // ����ӽڵ�����Ŀ�������������
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // ����PositionId��ö�Ӧ��PositionDTO
   public PositionDTO getPositionDTOByPositionGradeId( final String positionGradeId )
   {
      return getPositionDTOByPositionGradeId( POSITION_DTO, positionGradeId );
   }

   // ����PositionId��ö�Ӧ��PositionDTO - �ݹ麯��
   private PositionDTO getPositionDTOByPositionGradeId( List< PositionDTO > positionDTOs, final String positionGradeId )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // ���DTO��Ŀ������򷵻�
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getPositionGradeId() != null
                  && positionDTO.getPositionVO().getPositionGradeId().equals( positionGradeId ) )
            {
               return positionDTO;
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // ����ֵ���õ�ǰ�ڵ��ڴ�
               positionDTO = getPositionDTOByPositionGradeId( positionDTO.getPositionDTOs(), positionGradeId );

               // ����ӽڵ�����Ŀ�������������
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // ����BranchId��ö�Ӧ��PositionVO�б�
   public List< PositionVO > getPositionVOsByBranchId( final String branchId )
   {
      // ��ʼ��PositionVO�б�
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

   // ����PositionId��ö�Ӧ��Employee_PositionDTO
   public com.kan.base.domain.management.PositionDTO getEmployeePositionDTOByPositionId( final String positionId )
   {
      return getEmployeePositionDTOByPositionId( EMPLOYEE_POSITION_DTO, positionId );
   }

   // ����PositionId��ö�Ӧ��PositionDTO - �ݹ麯��
   private com.kan.base.domain.management.PositionDTO getEmployeePositionDTOByPositionId( List< com.kan.base.domain.management.PositionDTO > positionDTOs, final String positionId )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( com.kan.base.domain.management.PositionDTO positionDTO : positionDTOs )
         {
            // ���DTO��Ŀ������򷵻�
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getPositionId() != null && positionDTO.getPositionVO().getPositionId().equals( positionId ) )
            {
               return positionDTO;
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // ����ֵ���õ�ǰ�ڵ��ڴ�
               positionDTO = getEmployeePositionDTOByPositionId( positionDTO.getPositionDTOs(), positionId );

               // ����ӽڵ�����Ŀ�������������
               if ( positionDTO != null )
               {
                  return positionDTO;
               }
            }
         }
      }

      return null;
   }

   // ����ModuleId��ö�Ӧ��AccountModuleDTO
   public AccountModuleDTO getAccountModuleDTOByModuleId( final String moduleId )
   {
      return getAccountModuleDTOByModuleId( MODULE_DTO, moduleId );
   }

   // ����ModuleId��ö�Ӧ��AccountModuleDTO - �ݹ麯��
   private AccountModuleDTO getAccountModuleDTOByModuleId( final List< AccountModuleDTO > accountModuleDTOs, final String moduleId )
   {
      if ( accountModuleDTOs != null && accountModuleDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( AccountModuleDTO accountModuleDTO : accountModuleDTOs )
         {
            // ���DTO��Ŀ������򷵻�
            if ( accountModuleDTO.getModuleVO() != null && KANUtil.filterEmpty( accountModuleDTO.getModuleVO().getModuleId() ) != null
                  && KANUtil.filterEmpty( accountModuleDTO.getModuleVO().getModuleId() ).equals( moduleId ) )
            {
               return accountModuleDTO;
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( accountModuleDTO.getAccountModuleDTOs() != null && accountModuleDTO.getAccountModuleDTOs().size() > 0 )
            {
               // ����ֵ���õ�ǰ�ڵ��ڴ�
               accountModuleDTO = getAccountModuleDTOByModuleId( accountModuleDTO.getAccountModuleDTOs(), moduleId );

               // ����ӽڵ�����Ŀ�������������
               if ( accountModuleDTO != null )
               {
                  return accountModuleDTO;
               }
            }
         }
      }

      return null;
   }

   // ��AccountModuleDTO������ModuleDTO
   public List< ModuleDTO > getModuleDTOs()
   {
      return getModuleDTOs( MODULE_DTO );
   }

   // ��AccountModuleDTO������ModuleDTO - �ݹ麯��
   private List< ModuleDTO > getModuleDTOs( final List< AccountModuleDTO > accountModuleDTOs )
   {
      // ��ʼ����ǰ�㼶��ModuleDTO�����б�
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      if ( accountModuleDTOs != null && accountModuleDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( AccountModuleDTO accountModuleDTO : accountModuleDTOs )
         {
            final ModuleDTO moduleDTO = new ModuleDTO();
            // ���ModuleVO������DTO
            moduleDTO.setModuleVO( accountModuleDTO.getModuleVO() );

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( accountModuleDTO.getAccountModuleDTOs() != null && accountModuleDTO.getAccountModuleDTOs().size() > 0 )
            {
               // �ݹ����
               moduleDTO.getModuleDTOs().addAll( getModuleDTOs( accountModuleDTO.getAccountModuleDTOs() ) );
            }

            moduleDTOs.add( moduleDTO );
         }
      }

      return moduleDTOs;
   }

   // ����StaffId��ö�Ӧ��StaffDTO
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

   // ��ȡModuleVO�б�
   public List< ModuleVO > getModuleVOs()
   {
      return MODULE_VO;
   }

   // ��AccountModuleDTO�������Ѿ����ù�Right��Rule��ModuleVO�б�
   public List< ModuleVO > getSelectedModuleVOs()
   {
      return getSelectedModuleVOs( MODULE_DTO );
   }

   // ��AccountModuleDTO�������Ѿ����ù�Right��Rule��ModuleVO�б�
   public List< ModuleVO > getClientSelectedModuleVOs()
   {
      return CLIENT_SELECT_MODULE_VO;
   }

   // ��AccountModuleDTO�������Ѿ����ù�Right��Rule��ModuleVO�б� - �ݹ麯��
   private List< ModuleVO > getSelectedModuleVOs( final List< AccountModuleDTO > accountModuleDTOs )
   {
      // ��ʼ����ǰ�㼶��ModuleVO�����б�
      final List< ModuleVO > moduleVOs = new ArrayList< ModuleVO >();

      if ( accountModuleDTOs != null && accountModuleDTOs.size() > 0 )
      {
         // ������ǰ�㼶������DTO
         for ( AccountModuleDTO accountModuleDTO : accountModuleDTOs )
         {
            // �����ǰModule�������ù���Right��Rule
            if ( ( accountModuleDTO.getModuleRightRelationVOs() != null && accountModuleDTO.getModuleRightRelationVOs().size() > 0 )
                  || ( accountModuleDTO.getModuleRuleRelationVOs() != null && accountModuleDTO.getModuleRuleRelationVOs().size() > 0 ) )
            {
               // ���ModuleVO�������б�
               moduleVOs.add( accountModuleDTO.getModuleVO() );
            }

            // �����ǰDTO���ӽڵ㲻Ϊ�գ��ݹ����
            if ( accountModuleDTO.getAccountModuleDTOs() != null && accountModuleDTO.getAccountModuleDTOs().size() > 0 )
            {
               // �ݹ����
               moduleVOs.addAll( getSelectedModuleVOs( accountModuleDTO.getAccountModuleDTOs() ) );
            }
         }
      }

      return moduleVOs;
   }

   // ���Constant��Ӧ��MappingVO
   public List< MappingVO > getConstants( final String localeLanguage )
   {
      return getConstantsByScopeType( localeLanguage, null );
   }

   // ����ScopeType���Constant��Ӧ��MappingVO
   public List< MappingVO > getConstantsByScopeType( final String localeLanguage, final String scopeType )
   {
      final List< MappingVO > constants = new ArrayList< MappingVO >();

      // ��������     
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

   // ����ScopeType���Constant��Ӧ��MappingVO
   public List< MappingVO > getMessageTemplateByType( final String localeLanguage, final String scopeType )
   {
      final List< MappingVO > messageTemplates = new ArrayList< MappingVO >();

      // ��������     
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

   // ����ScopeType���ConstantVO�б�
   public List< ConstantVO > getConstantVOsByScopeType( final String scopeType )
   {
      final List< ConstantVO > constants = new ArrayList< ConstantVO >();

      // ��������     
      for ( ConstantVO constantVO : CONSTANT_VO )
      {
         if ( constantVO.getScopeType() != null && constantVO.getScopeType().equals( scopeType ) )
         {
            constants.add( constantVO );
         }

      }

      return constants;
   }

   // ����GroupId��ö�Ӧ��GroupDTO
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

   // ����positionId��ȡGroupDTOs
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

   // ����OptionHeaderId��ö�Ӧ��OptionDTO
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

   // ����TableId��ö�Ӧ��TableDTO
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

   // ����Access Action��ö�Ӧ��TableDTO
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

   // ����Access Action��ö�Ӧ��TableDTO
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

   // ���Table��Ӧ��MappingVO
   // Added by Kevin Jin at 2014-03-08
   public List< MappingVO > getTables( final String localeLanguage, final String role, final String flag )
   {
      final List< MappingVO > tables = new ArrayList< MappingVO >();

      if ( KANUtil.filterEmpty( role ) != null || ACCOUNT_ID.equals( KANConstants.SUPER_ACCOUNT_ID ) )
      {
         // ����ϵͳ�����Table       
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

         // ����  
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

   // ����positionId��ö�Ӧ�� ��Ӧ��StaffDTOs
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

   // ����positionId��ö�Ӧ�� ��Ӧ��StaffDTOs
   // iclick by siuvan ���˴���ְλ
   public List< StaffDTO > getNoAgentStaffDTOsByPositionId( final String positionId )
   {
      final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

      for ( StaffDTO staffDTO : this.STAFF_DTO )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : staffDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId().equals( positionId ) )
            {
               // ����Ǵ���ְλ����ֻҪ�ڴ����ڼ����staff
               if ( !"2".equals( positionStaffRelationVO.getStaffType() ) )
               {
                  staffDTOs.add( staffDTO );
               }
            }
         }
      }

      return staffDTOs;
   }

   // ����positionId��ö�Ӧ�� ��Ӧ��StaffDTOs
   public List< StaffDTO > getValidStaffDTOsByPositionId( final String positionId )
   {
      final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

      for ( StaffDTO staffDTO : this.STAFF_DTO )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : staffDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId().equals( positionId ) )
            {
               // ����Ǵ���ְλ����ֻҪ�ڴ����ڼ����staff
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

   // ����employeeId��ö�Ӧ�� ��Ӧ��StaffDTOs
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

   // ����userId ��ȡ��Ӧ��staffDTO
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

   // ����positionId��ȡ����positionId
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

   // ����positionId��ȡ����positionId(����ֱ������)
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

   // ����ColumnId���ColumnVO����
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

   // ��ȡCalendar��Ӧ��MappingVO
   public List< MappingVO > getCalendar( final String localeLanguage )
   {
      return getCalendar( localeLanguage, null );
   }

   // ��ȡCalendar��Ӧ��MappingVO
   public List< MappingVO > getCalendar( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > calendars = new ArrayList< MappingVO >();

      // ����CALENDAR_DTO
      if ( CALENDAR_DTO != null && CALENDAR_DTO.size() > 0 )
      {
         for ( CalendarDTO calendarDTO : CALENDAR_DTO )
         {
            if ( ( KANUtil.filterEmpty( calendarDTO.getCalendarHeaderVO().getAccountId() ) != null && calendarDTO.getCalendarHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( calendarDTO.getCalendarHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( calendarDTO.getCalendarHeaderVO().getCorpId() ) != null && calendarDTO.getCalendarHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ��ȡBankTemplate��Ӧ��MappingVO
   public List< MappingVO > getBankTemplate( final String localeLanguage )
   {
      return getBankTemplate( localeLanguage, null );
   }

   // ��ȡBankTemplate��Ӧ��MappingVO
   public List< MappingVO > getBankTemplate( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > bankTemplates = new ArrayList< MappingVO >();

      // ����BANK_TEMPLATE_DTO
      if ( BANK_TEMPLATE_DTO != null && BANK_TEMPLATE_DTO.size() > 0 )
      {
         for ( BankTemplateDTO bankTemplateDTO : BANK_TEMPLATE_DTO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( bankTemplateDTO.getBankTemplateHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( bankTemplateDTO.getBankTemplateHeaderVO().getCorpId() ) != null && bankTemplateDTO.getBankTemplateHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ��ȡTaxTemplate��Ӧ��MappingVO
   public List< MappingVO > getTaxTemplate( final String localeLanguage )
   {
      return getTaxTemplate( localeLanguage, null );
   }

   // ��ȡTaxTemplate��Ӧ��MappingVO
   public List< MappingVO > getTaxTemplate( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > taxTemplates = new ArrayList< MappingVO >();

      // ����TAX_TEMPLATE_DTO
      if ( TAX_TEMPLATE_DTO != null && TAX_TEMPLATE_DTO.size() > 0 )
      {
         for ( TaxTemplateDTO taxTemplateDTO : TAX_TEMPLATE_DTO )
         {
            if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( taxTemplateDTO.getTaxTemplateHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( taxTemplateDTO.getTaxTemplateHeaderVO().getCorpId() ) != null && taxTemplateDTO.getTaxTemplateHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ��ȡShift��Ӧ��MappingVO
   public List< MappingVO > getShift( final String localeLanguage )
   {
      return getShift( localeLanguage, null );
   }

   // ��ȡShift��Ӧ��MappingVO
   public List< MappingVO > getShift( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > shifts = new ArrayList< MappingVO >();

      // ����SHIFT_DTO
      if ( SHIFT_DTO != null && SHIFT_DTO.size() > 0 )
      {
         for ( ShiftDTO shiftDTO : SHIFT_DTO )
         {
            if ( ( KANUtil.filterEmpty( shiftDTO.getShiftHeaderVO().getAccountId() ) != null && shiftDTO.getShiftHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( shiftDTO.getShiftHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( shiftDTO.getShiftHeaderVO().getCorpId() ) != null && shiftDTO.getShiftHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ����HeaderId���CalendarDTO
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

   // ����HeaderId���ShiftDTO
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

   // ��ȡSick Leave Salary��Ӧ��MappingVO
   public List< MappingVO > getSickLeaveSalary( final String localeLanguage )
   {
      return getSickLeaveSalary( localeLanguage, null );
   }

   // ��ȡSick Leave Salary��Ӧ��MappingVO
   public List< MappingVO > getSickLeaveSalary( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > sickLeaveSalarys = new ArrayList< MappingVO >();

      // ����SICK_LEAVE_SALARY_DTO
      if ( SICK_LEAVE_SALARY_DTO != null && SICK_LEAVE_SALARY_DTO.size() > 0 )
      {
         for ( SickLeaveSalaryDTO sickLeaveSalaryDTO : SICK_LEAVE_SALARY_DTO )
         {
            if ( ( KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getAccountId() ) != null && sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getCorpId() ) != null && sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ��ȡAnnual Leave Rule��Ӧ��MappingVO
   public List< MappingVO > getAnnualLeaveRules( final String localeLanguage )
   {
      return getAnnualLeaveRules( localeLanguage, null );
   }

   // ��ȡAnnual Leave Rule��Ӧ��MappingVO
   public List< MappingVO > getAnnualLeaveRules( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > annualLeaveRules = new ArrayList< MappingVO >();

      // ����ANNUAL_LEAVE_RULE_DTO
      if ( ANNUAL_LEAVE_RULE_DTO != null && ANNUAL_LEAVE_RULE_DTO.size() > 0 )
      {
         for ( AnnualLeaveRuleDTO annualLeaveRuleDTO : ANNUAL_LEAVE_RULE_DTO )
         {
            if ( ( KANUtil.filterEmpty( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getAccountId() ) != null && annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getAccountId().equals( "1" ) )
                  || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getCorpId() ) == null )
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getCorpId() ) != null && annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getCorpId().equals( corpId ) ) )
            {
               // ��ʼ��MappingVO����
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

   // ����HeaderId���SickLeaveSalaryDTO
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

   // ����HeaderId���AnnualLeaveRuleDTO
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

   // ��ʼ���·��ܴΣ���ǰ��3��
   public List< MappingVO > getWeeks( final String localeLanguage )
   {
      return getWeeks( localeLanguage, null );
   }

   // ��ʼ���·��ܴΣ���ǰ��3��
   public List< MappingVO > getWeeks( final String localeLanguage, final String corpId )
   {
      return KANUtil.getWeeksByCondition( 162, 1 );
   }

   // ��ʼ���·ݣ����36�£������£�
   public List< MappingVO > getLast36Months( final String localeLanguage )
   {
      return getLast36Months( localeLanguage, null );
   }

   // ��ʼ���·ݣ����36�£������£�
   public List< MappingVO > getLast36Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 36, 1 );
   }

   // ��ʼ���·ݣ����12�£������£�
   public List< MappingVO > getLast12Months( final String localeLanguage )
   {
      return getLast12Months( localeLanguage, null );
   }

   // ��ʼ���·ݣ����12�£������£�
   public List< MappingVO > getLast12Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 12, 1 );
   }

   // ��ʼ���·ݣ����2�£������£�
   public List< MappingVO > getLast2Months( final String localeLanguage )
   {
      return getLast2Months( localeLanguage, null );
   }

   // ��ʼ���·ݣ����2�£������£�
   public List< MappingVO > getLast2Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 2, 1 );
   }

   // ��ʼ���·ݣ����4�£�����3���£�
   public List< MappingVO > getLast4Months( final String localeLanguage )
   {
      return getLast4Months( localeLanguage, null );
   }

   // ��ʼ���·ݣ����4�£�����3���£�
   public List< MappingVO > getLast4Months( final String localeLanguage, final String corpId )
   {
      return KANUtil.getMonthsByCondition( 4, 3 );
   }

   // ��ʼ����ݣ����10��
   public List< MappingVO > getLast5Years( final String localeLanguage, final String corpId )
   {
      return KANUtil.getYears( Integer.valueOf( KANUtil.getYear( new Date() ) ) - 2, Integer.valueOf( KANUtil.getYear( new Date() ) ) + 3 );
   }

   // ����TaxId���TaxVO
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

   // ����EntityId��BusinessTypeId���TaxVO�б�
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

   // ����BankId��õ�BankVO
   public BankVO getBankVOByBankId( final String bankId )
   {
      // ����BankVO List
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

   // ����ItemId���ItemVO
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

   // ����ItemName���ItemVO
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

   // ����ItemName���ItemVO
   public ItemVO getItemVOByItemName( final String itemName ) throws KANException
   {
      return getItemVOByItemName( itemName, null );
   }

   // ����ItemGroupId���ItemGroupDTO
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

   // ����ItemId���ItemGroupDTO
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

   // ����ItemId�Լ�ItemGroupType���ItemGroupDTO
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
      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      if ( moduleDTO != null )
      {
         final String moduleId = moduleDTO.getModuleVO().getModuleId();

         // ����ǹ�Ӧ�̵�¼��Ĭ�϶���Ȩ��
         if ( userVO != null && userVO.getRole().equals( KANConstants.ROLE_VENDOR ) )
         {
            return true;
         }

         // ����AccountId �鿴�Ƿ���Ȩ��
         // �ж�ȫ���Ƿ�������
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

         // ��õ�ǰUser��Positions
         /*  final List< MappingVO > positions = userVO.getPositions();

           for ( MappingVO mappingVO : positions )
           {*/
         // ��ʼ��PositionDTO
         final PositionDTO postionDTO = getPositionDTOByPositionId( userVO.getPositionId() );
         if ( postionDTO != null )
         {
            // �ж�ְλ�Ƿ�������
            if ( postionDTO.getPositionModuleDTOs() != null && postionDTO.getPositionModuleDTOs().size() > 0 )
            {
               // ����PositionIds�鿴�Ƿ���Ȩ�� 
               for ( PositionModuleDTO positionModuleDTO : postionDTO.getPositionModuleDTOs() )
               {
                  if ( positionModuleDTO.getModuleVO() != null && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ) != null
                        && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ).equals( moduleId ) )
                  {
                     // ��ʼ��RightVO�б�
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

            // ����PostionGroup�鿴�Ƿ���Ȩ��
            final List< PositionGroupRelationVO > positionGroupRelationVOs = postionDTO.getPositionGroupRelationVOs();

            // �ж�ְλ���Ƿ�������
            if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
            {
               for ( PositionGroupRelationVO positionGroupRelationVO : positionGroupRelationVOs )
               {
                  // ��ʼ��GroupId
                  final String groupId = positionGroupRelationVO.getGroupId();
                  // ��ʼ��GroupDTO
                  final GroupDTO groupDTO = getGroupDTOByGroupId( groupId );

                  if ( groupDTO != null )
                  {
                     // ��ʼ��GroupModuleDTO
                     final GroupModuleDTO groupModuleDTO = groupDTO.getGroupModuleDTOByModuleId( moduleId );

                     if ( groupModuleDTO != null )
                     {
                        // ��ʼ��GroupModuleDTO
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
    * �жϵ�ǰposition�Ƿ��в˵�Ȩ�ޣ����һ��user�ж��position�Ĵ���
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

         // ����AccountId �鿴�Ƿ���Ȩ��
         // �ж�ȫ���Ƿ�������
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

         // ��õ�ǰUser��Positions
         /*   final List< MappingVO > positions = userVO.getPositions();
         
         for ( MappingVO mappingVO : positions )
         {
            // ��ʼ��PositionDTO
            final PositionDTO postionDTO = getPositionDTOByPositionId( mappingVO.getMappingId() );*/
         final PositionDTO postionDTO = getPositionDTOByPositionId( userVO.getPositionId() );

         // �ж�ְλ�Ƿ�������
         if ( postionDTO != null && postionDTO.getPositionModuleDTOs() != null && postionDTO.getPositionModuleDTOs().size() > 0 )
         {
            // ����PositionIds�鿴�Ƿ���Ȩ�� 
            for ( PositionModuleDTO positionModuleDTO : postionDTO.getPositionModuleDTOs() )
            {
               if ( positionModuleDTO.getModuleVO() != null && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ) != null
                     && KANUtil.filterEmpty( positionModuleDTO.getModuleVO().getModuleId() ).equals( moduleId ) )
               {
                  // ��ʼ��RightVO�б�
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

         // �ж�ְλ���Ƿ�������
         if ( postionDTO != null && postionDTO.getPositionGroupRelationVOs() != null && postionDTO.getPositionGroupRelationVOs().size() > 0 )
         {
            // ����PostionGroup�鿴�Ƿ���Ȩ��
            final List< PositionGroupRelationVO > positionGroupRelationVOs = postionDTO.getPositionGroupRelationVOs();
            for ( PositionGroupRelationVO positionGroupRelationVO : positionGroupRelationVOs )
            {
               // ��ʼ��GroupId
               final String groupId = positionGroupRelationVO.getGroupId();
               // ��ʼ��GroupDTO
               final GroupDTO groupDTO = getGroupDTOByGroupId( groupId );

               if ( groupDTO != null )
               {
                  // ��ʼ��GroupModuleDTO
                  final GroupModuleDTO groupModuleDTO = groupDTO.getGroupModuleDTOByModuleId( moduleId );

                  if ( groupModuleDTO != null )
                  {
                     // ��ʼ��GroupModuleDTO
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
    * ȡ�õ�ǰ�û���module����(����ȫ�ֹ����ְλ����)
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

         // ����accountId ��ȡ����ȫ�ֹ���
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
            // ����positionIds ��ȡ���� 
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
         // ����postionGroup �鿴�Ƿ��й���
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
    * ȡ�õ�ǰ�û���module����(����ȫ�ֹ����ְλ����)
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

         // ����accountId ��ȡ����ȫ�ֹ���
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
            // ����positionIds ��ȡ���� 
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

         // ����postionGroup �鿴�Ƿ��й���
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

   // �õ���ǰְλ�ϰ󶨵�Ա������������ʾ����
   public String getStaffNamesByPositionId( final String localeLanguage, final String positionId )
   {
      String staffNames = "";

      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // ����Relation����
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // �õ�Constants�е�staffVO
               final StaffDTO staffDTO = getStaffDTOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffDTO != null )
               {
                  final StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     // �����ְλ�󶨶��Ա����ʹ�á�,���ָ�
                     if ( !staffNames.trim().equals( "" ) )
                     {
                        staffNames = staffNames + ", ";
                     }

                     // ���յ�ǰ����ȡ��Ա������
                     if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                     {
                        staffNames = staffNames + staffVO.getNameZH();
                     }
                     else
                     {
                        staffNames = staffNames + staffVO.getNameEN();
                     }

                     // ����Ǵ���Ա��
                     if ( positionStaffRelationVO.getStaffType() != null && positionStaffRelationVO.getStaffType().trim().equals( "2" ) )
                     {
                        if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                        {
                           staffNames = staffNames + "������";
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

   // �õ���ǰְλ�ϰ󶨵�Ա������������ʾ����,ֻ��ʾǰ3����
   public String get3StaffNamesByPositionId( final String localeLanguage, final String positionId )
   {
      String staffNames = "";

      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // ��¼Ա������
         int index = 0;
         // ����Relation����
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // �õ�Constants�е�staffVO
               final StaffDTO staffDTO = getStaffDTOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffDTO != null )
               {
                  final StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     // ���յ�ǰ����ȡ��Ա������
                     if ( index < 3 )
                     {
                        // �����ְλ�󶨶��Ա����ʹ�á�,���ָ�
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

                        // ����Ǵ���Ա��
                        if ( positionStaffRelationVO.getStaffType() != null && positionStaffRelationVO.getStaffType().trim().equals( "2" ) )
                        {
                           if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                           {
                              staffNames = staffNames + "������";
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
            staffNames = staffNames + ", ��(" + index + ")��";
         }
      }

      return ( staffNames != null && !staffNames.isEmpty() ? staffNames : "" );
   }

   // �õ���ǰְλ�ϰ󶨵�Ա������
   public int getStaffNumByPositionId( final String localeLanguage, final String positionId )
   {

      // ��¼Ա������
      int index = 0;
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );
      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // ����Relation����
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // �õ�Constants�е�staffVO
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

   // �õ���ǰְλ�ϰ󶨵�Ա������������ʾ����
   public List< StaffVO > getStaffArrayByPositionId( final String positionId )
   {
      List< StaffVO > staffNameArray = new ArrayList< StaffVO >();
      final PositionDTO positionDTO = getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
      {
         // ����Relation����
         for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
         {
            if ( positionStaffRelationVO.getPositionId() != null && positionStaffRelationVO.getPositionId().trim().equals( positionId ) )
            {
               // �õ�Constants�е�staffVO
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

   // Added By siuxia at 2014-01-21 ����tableId��ȡManagerDTO //
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

   // ����listHeaderId��ö�Ӧ��ListDTO
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

   // ����JavaObjectName��ö�Ӧ��ListDTO
   public ListDTO getListDTOByJavaObjectName( final String javaObjectName )
   {
      return getListDTOByJavaObjectName( javaObjectName, null );
   }

   // ����JavaObjectName��ö�Ӧ��ListDTO
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

   // ����JavaObjectName��ö�Ӧ��SearchDTO
   public SearchDTO getSearchDTOByJavaObjectName( final String javaObjectName )
   {
      return getSearchDTOByJavaObjectName( javaObjectName, null );
   }

   // ����JavaObjectName��ö�Ӧ��SearchDTO
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

   // ����clinetId��listId��ȡMappingDTO
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

   // ����defineId��defineId��ȡMappingDTO
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

   // ����defineId��listId��ȡMappingDTO
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

   // ��ȡPositionDTO�б�
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

   // ��ȡ��Ϊ0�Ͷ�����ʾ��
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

   // ��ȡPositionVO�б�
   public List< MappingVO > getPositions( final String localeLanguage )
   {
      return getPositions( localeLanguage, null );
   }

   // ��ȡPositionVO�б�
   public List< MappingVO > getPositions( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > positions = new ArrayList< MappingVO >();

      // ����Branch List
      for ( PositionVO positionVO : POSITION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionVO.getCorpId() ) != null && positionVO.getCorpId().equals( corpId ) ) )
         {
            // ��ʼ��MappingVO����
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

   // �ݹ�װ��EmployeePosition
   private void fetchEmployeePositions( final List< MappingVO > employeePositions, final com.kan.base.domain.management.PositionDTO employeePositionDTO, final String localeLanguage )
   {
      // ��ʼ��MappingVO����
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

   // ��ȡPositionVO(�ⲿ)�б�
   public List< MappingVO > getEmployeePositions( final String localeLanguage )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > employeePositions = new ArrayList< MappingVO >();

      for ( com.kan.base.domain.management.PositionDTO employeePositionDTO : EMPLOYEE_POSITION_DTO )
      {
         if ( employeePositionDTO != null && employeePositionDTO.getPositionVO() != null )
         {
            // �ݹ�װ��EmployeePosition
            fetchEmployeePositions( employeePositions, employeePositionDTO, localeLanguage );
         }
      }

      return employeePositions;
   }

   //ͨ��ְλ�Ҷ�Ӧ��employeeId
   // modify by siuvan ���˴���
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

   //ͨ��ְ���Ҷ�Ӧ��position
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

   //ͨ��ְ���Ҷ�Ӧ��position
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
    * ��ȡPositionVO MappingVO �б�
    * ID == positionId
    * Value == location + positionGrade + title + owner
    * @param localeLanguage
    * @param corpId
    * @return
    */
   public List< MappingVO > getPositionMappingVOs( final String localeLanguage, final String corpId )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > positionMappingVOs = new ArrayList< MappingVO >();

      // ����Branch List
      for ( PositionVO positionVO : POSITION_VO )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionVO.getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionVO.getCorpId() ) != null && positionVO.getCorpId().equals( corpId ) ) )
         {
            final StringBuilder rs = new StringBuilder();
            // ��ʼ��MappingVO����
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
                  staffNames = staffNames + "��" + ( "zh".equals( localeLanguage ) ? tempsStaffDTO.getStaffVO().getNameZH() : tempsStaffDTO.getStaffVO().getNameEN() );
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
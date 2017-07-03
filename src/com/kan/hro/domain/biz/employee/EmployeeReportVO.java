package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnGroupDTO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.web.actions.biz.employee.EmployeeContractAction;

public class EmployeeReportVO extends EmployeeVO
{

   /**  
   * Serial Version UID:
   */
   private static final long serialVersionUID = 5698658203350105018L;

   public static Map< String, Double > EXCHANGE_RATE_MAP = new HashMap< String, Double >();

   static
   {
      EXCHANGE_RATE_MAP.put( "RMB", 1 / 6d );
      EXCHANGE_RATE_MAP.put( "HKD", 1 / 7.76d );
      EXCHANGE_RATE_MAP.put( "SGD", 1 / 1.23d );
      EXCHANGE_RATE_MAP.put( "NTD", 1 / 30d );
   }

   // ����ʵ�����ƣ����ģ�
   private String entityNameZH;

   // ����ʵ�����ƣ�Ӣ�ģ�
   private String entityNameEN;

   // ����ʵ�壨��ƣ�
   private String entityTitle;

   // �ϼ��������ƣ����ģ�
   private String parentBranchNameZH;

   // �ϼ��������ƣ�Ӣ�ģ�
   private String parentBranchNameEN;

   // �������ţ����ģ�
   private String branchNameZH;

   // �������ţ�Ӣ�ģ�
   private String branchNameEN;

   // ҵ������
   private String businessTypeId;

   // �ɱ�����/ Ӫ�ղ���
   private String settlementBranch;

   // ��Ա����
   private String templateId;

   // ��ͬ״̬
   private String contractStatus;

   // �Ͷ���ͬremark1
   private String contractRemark1;

   // ��Ӷ״̬
   private String employStatus;

   // ��ͬ��ʼ����
   private String contractStartDate;

   // ��ͬ��������
   private String contractEndDate;

   // ��ְ����
   private String resignDate;

   // ��ְԭ��
   private String leaveReasons;

   // ������������0-6���£�
   private String orderProbationMonth;

   // ������������0-6���£�
   private String probationMonth;

   // �����ڽ���ʱ��
   private String probationEndDate;

   // ѧУ����
   private String schoolNames;

   // רҵ
   private String majors;

   // ��ҵ����
   private String graduateDates;

   // ������ϵ��
   private String emergencyNames;

   //�������ϵ�˹�ϵ
   private String relationshipIds;

   // ������ϵ����ϵ��ʽ1
   private String phones;

   // ������ϵ����ϵ��ʽ2
   private String mobiles;

   // ��˾����
   private String bizEmail;

   // ��������
   private String personalEmail;

   // HR Comments
   private String contractRemark5;

   // ��̬�����б�
   private Map< String, Object > dynaColumns = new HashMap< String, Object >();

   /**
    * For Application
    */
   // �³�
   private String monthBegin;

   // ��ĩ
   private String monthEnd;

   // ����ʵ��ID
   private String entityId;

   // ��������
   private String rt;

   // ��ͬId
   private String contractId;

   // ��ְʱ������
   private String resignDateStart;

   // ��ְʱ������
   private String resignDateEnd;

   //�����ʱ��
   private String lastWorkDate;

   // н�ʷ�����ʼʱ��
   private String salaryStartDateStart;

   //н�ʷ��� ����ʱ��
   private String salaryStartDateEnd;

   // н�ʷ�����ʼʱ��
   private String salaryEndDateStart;

   //н�ʷ��� ����ʱ��
   private String salaryEndDateEnd;

   // ����
   private String currency;

   /**'
    * For Performance Report
    */
   private String yearly;
   private String yearPerformanceRating;
   private String yearPerformancePromotion;
   private String recommendTTCIncrease;
   private String ttcIncrease;
   private String newTTCLocal;
   private String newTTCUSD;
   private String newBaseSalaryLocal;
   private String newBaseSalaryUSD;
   private String newAnnualSalaryLocal;
   private String newAnnualSalaryUSD;
   private String newAnnualHousingAllowance;
   private String newAnnualChildrenEduAllowance;
   private String newAnnualGuaranteedAllowanceLocal;
   private String newAnnualGuatanteedAllowanceUSD;
   private String newMonthlyTarget;
   private String newQuarterlyTarget;
   private String newGPTarget;
   private String newTargetValueLocal;
   private String newTargetValueUSD;
   private String newJobGrade;
   private String newInternalTitle;
   private String newPositionEN;
   private String newPositionZH;
   private String newShareOptions;
   private String targetBonus;
   private String proposedBonus;
   private String proposedPayoutLocal;
   private String proposedPayoutUSD;
   private String comments;
   private String updateBy;
   private String updateDate;

   /**
    * For Export
    */
   private String titleNameList;

   private String titleIdList;

   //for contact
   private String search4Contact;

   /**
    * Mapping List
    */
   // ����
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   private List< MappingVO > branchs_zh = new ArrayList< MappingVO >();
   private List< MappingVO > branchs_en = new ArrayList< MappingVO >();

   // ��ͬ״̬
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // �칫�ص�
   private List< MappingVO > locations = new ArrayList< MappingVO >();

   // �Ͷ���ͬģ������
   private List< MappingVO > templates = new ArrayList< MappingVO >();

   // ��Ա��ְ״̬
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();

   // ����
   private List< MappingVO > banks = new ArrayList< MappingVO >();

   // ������ϵ�˹�ϵ
   private List< MappingVO > relationships = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   private List< MappingVO > salarys = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.branchs_zh = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( "zh", super.getCorpId() );
      this.branchs_en = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( "en", super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.locations = KANConstants.getKANAccountConstants( super.getAccountId() ).getLocations( request.getLocale().getLanguage(), super.getCorpId() );
      this.templates = KANConstants.getKANAccountConstants( super.getAccountId() ).getLaborContractTemplates( request.getLocale().getLanguage(), super.getCorpId() );
      // �����ǰ�˻�Ϊ iClick(accountId = 100011) 
      if ( getAccountId() != null && getAccountId().equals( "100011" ) )
         this.employStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.iClick.employStatuses" );
      else
         this.employStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.work.statuses" );

      this.banks = KANConstants.getKANAccountConstants( super.getAccountId() ).getBanks( request.getLocale().getLanguage(), super.getCorpId() );

      this.relationships = KANUtil.getMappings( this.getLocale(), "business.employee.emergency.relationshipIds" );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.entitys.add( 0, getEmptyMappingVO() );
      super.getStatuses().remove( 2 );
      super.getStatuses().remove( 3 );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update( Object object ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   public String getEntityNameZH()
   {
      return entityNameZH;
   }

   public void setEntityNameZH( String entityNameZH )
   {
      this.entityNameZH = entityNameZH;
   }

   public String getEntityNameEN()
   {
      return entityNameEN;
   }

   public void setEntityNameEN( String entityNameEN )
   {
      this.entityNameEN = entityNameEN;
   }

   public String getEntityTitle()
   {
      return entityTitle;
   }

   public void setEntityTitle( String entityTitle )
   {
      this.entityTitle = entityTitle;
   }

   public String getParentBranchNameZH()
   {
      if ( KANUtil.filterEmpty( super.get_tempParentBranchIds() ) == null )
      {
         return "";
      }

      if ( KANUtil.filterEmpty( super.get_tempParentBranchIds() ) != null && branchs_zh != null && branchs_zh.size() > 0 )
      {
         for ( String branchId : super.get_tempParentBranchIds().split( "," ) )
         {
            if ( KANUtil.filterEmpty( parentBranchNameZH ) == null )
            {
               parentBranchNameZH = decodeField( branchId, branchs_zh, true );
            }
            else
            {
               parentBranchNameZH = parentBranchNameZH + "��" + decodeField( branchId, branchs_zh, true );
            }
         }
      }

      return parentBranchNameZH;
   }

   public void setParentBranchNameZH( String parentBranchNameZH )
   {
      this.parentBranchNameZH = parentBranchNameZH;
   }

   public String getParentBranchNameEN()
   {
      if ( KANUtil.filterEmpty( super.get_tempParentBranchIds() ) == null )
      {
         return "";
      }

      if ( KANUtil.filterEmpty( super.get_tempParentBranchIds() ) != null && branchs_en != null && branchs_en.size() > 0 )
      {
         for ( String branchId : super.get_tempParentBranchIds().split( "," ) )
         {
            if ( KANUtil.filterEmpty( parentBranchNameEN ) == null )
            {
               parentBranchNameEN = decodeField( branchId, branchs_en, true );
            }
            else
            {
               parentBranchNameEN = parentBranchNameEN + "��" + decodeField( branchId, branchs_en, true );
            }
         }
      }

      return parentBranchNameEN;
   }

   public void setParentBranchNameEN( String parentBranchNameEN )
   {
      this.parentBranchNameEN = parentBranchNameEN;
   }

   public String getBranchNameZH()
   {
      if ( KANUtil.filterEmpty( super.get_tempBranchIds() ) == null )
      {
         return "";
      }

      if ( KANUtil.filterEmpty( super.get_tempBranchIds() ) != null && branchs_zh != null && branchs_zh.size() > 0 )
      {
         for ( String branchId : super.get_tempBranchIds().split( "," ) )
         {
            if ( KANUtil.filterEmpty( branchNameZH ) == null )
            {
               branchNameZH = decodeField( branchId, branchs_zh, true );
            }
            else
            {
               branchNameZH = branchNameZH + "��" + decodeField( branchId, branchs_zh, true );
            }
         }
      }

      return branchNameZH;
   }

   public void setBranchNameZH( String branchNameZH )
   {
      this.branchNameZH = branchNameZH;
   }

   public String getBranchNameEN()
   {
      if ( KANUtil.filterEmpty( super.get_tempBranchIds() ) == null )
      {
         return "";
      }

      if ( KANUtil.filterEmpty( super.get_tempBranchIds() ) != null && branchs_en != null && branchs_en.size() > 0 )
      {
         for ( String branchId : super.get_tempBranchIds().split( "," ) )
         {
            if ( KANUtil.filterEmpty( branchNameEN ) == null )
            {
               branchNameEN = decodeField( branchId, branchs_en, true );
            }
            else
            {
               branchNameEN = branchNameEN + "��" + decodeField( branchId, branchs_en, true );
            }
         }
      }

      return branchNameEN;
   }

   public void setBranchNameEN( String branchNameEN )
   {
      this.branchNameEN = branchNameEN;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
   }

   public String getContractRemark1()
   {
      return contractRemark1;
   }

   public void setContractRemark1( String contractRemark1 )
   {
      this.contractRemark1 = contractRemark1;
   }

   public String getTitleNameList()
   {
      return titleNameList;
   }

   public void setTitleNameList( String titleNameList )
   {
      this.titleNameList = titleNameList;
   }

   public String getTitleIdList()
   {
      return titleIdList;
   }

   public void setTitleIdList( String titleIdList )
   {
      this.titleIdList = titleIdList;
   }

   public String getMonthBegin()
   {
      return monthBegin;
   }

   public void setMonthBegin( String monthBegin )
   {
      this.monthBegin = monthBegin;
   }

   public String getMonthEnd()
   {
      return monthEnd;
   }

   public void setMonthEnd( String monthEnd )
   {
      this.monthEnd = monthEnd;
   }

   public String getEmployStatus()
   {
      return employStatus;
   }

   public void setEmployStatus( String employStatus )
   {
      this.employStatus = employStatus;
   }

   public String getResignDate()
   {
      return KANUtil.formatDate( resignDate, "yyyy-MM-dd", true );
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getBranchs_zh()
   {
      return branchs_zh;
   }

   public void setBranchs_zh( List< MappingVO > branchs_zh )
   {
      this.branchs_zh = branchs_zh;
   }

   public List< MappingVO > getBranchs_en()
   {
      return branchs_en;
   }

   public void setBranchs_en( List< MappingVO > branchs_en )
   {
      this.branchs_en = branchs_en;
   }

   public List< MappingVO > getLocations()
   {
      return locations;
   }

   public void setLocations( List< MappingVO > locations )
   {
      this.locations = locations;
   }

   public List< MappingVO > getTemplates()
   {
      return templates;
   }

   public void setTemplates( List< MappingVO > templates )
   {
      this.templates = templates;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getEmployStatuses()
   {
      return employStatuses;
   }

   public void setEmployStatuses( List< MappingVO > employStatuses )
   {
      this.employStatuses = employStatuses;
   }

   public String getLeaveReasons()
   {
      return leaveReasons;
   }

   public String getDecodeLeaveReasons()
   {
      if ( KANUtil.filterEmpty( leaveReasons ) != null )
      {
         final List< MappingVO > cnenMixed = KANUtil.getMappings( this.getLocale(), "business.employee.contract.LeaveReasons.cn.en.mixed" );

         if ( cnenMixed != null && cnenMixed.size() > 0 )
         {
            for ( MappingVO mappingVO : cnenMixed )
            {
               if ( mappingVO.getMappingValue().contains( leaveReasons ) )
               {
                  return mappingVO.getMappingValue();
               }
            }
         }
      }

      return "";
   }

   public void setLeaveReasons( String leaveReasons )
   {
      this.leaveReasons = leaveReasons;
   }

   @SuppressWarnings("unchecked")
   public Map< String, Object > getDynaColumns() throws KANException
   {
      try
      {
         if ( KANUtil.filterEmpty( contractRemark1 ) != null )
         {
            dynaColumns.putAll( JSONObject.fromObject( contractRemark1 ) );
         }
         if ( KANUtil.filterEmpty( getRemark1() ) != null )
         {
            dynaColumns.putAll( JSONObject.fromObject( getRemark1() ) );
         }
         dealDynaColumns( dynaColumns );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return dynaColumns;
   }

   public String getShortName()
   {
      String shortName = "";
      try
      {
         if ( KANUtil.filterEmpty( super.getRemark1() ) != null )
         {
            JSONObject jsonObject = JSONObject.fromObject( super.getRemark1() );

            if ( KANUtil.filterEmpty( "jiancheng" ) != null && jsonObject != null )
            {
               shortName = ( String ) jsonObject.get( "jiancheng" );
            }
         }
      }
      catch ( Exception e )
      {
         return "";
      }
      return shortName;
   }

   // �����Զ����ֶ�Ϊ��������Ҫת��
   private void dealDynaColumns( final Map< String, Object > dynaColumns ) throws KANException
   {
      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO employeeDTO = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByAccessAction( super.getRole().equals( KANConstants.ROLE_IN_HOUSE ) ? "HRO_BIZ_EMPLOYEE_IN_HOUSE"
            : "HRO_BIZ_EMPLOYEE" );
      final TableDTO employeeContractDTO = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByAccessAction( super.getRole().equals( KANConstants.ROLE_IN_HOUSE ) ? EmployeeContractAction.ACCESS_ACTION_SERVICE_IN_HOUSE
            : EmployeeContractAction.ACCESS_ACTION_SERVICE );
      replace( employeeDTO, dynaColumns );
      replace( employeeContractDTO, dynaColumns );
   }

   private void replace( final TableDTO tableDTO, final Map< String, Object > dynaColumns ) throws KANException
   {

      // ����Column Group
      if ( tableDTO != null && tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
         {
            // ����Column
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  // ֻ��ȡ�û��Զ����Column Value������������������
                  if ( columnVO.getAccountId() != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID )
                        && KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().equals( "2" ) )
                  {
                     // ����漰���Զ����ֶ�ֵ
                     if ( dynaColumns.get( columnVO.getNameDB() ) != null && !dynaColumns.get( columnVO.getNameDB() ).toString().equals( "null" ) )
                     {
                        final List< MappingVO > mappingVOs = getMappingVOsByCondition( columnVO );
                        dynaColumns.put( columnVO.getNameDB(), decodeField( ( String ) dynaColumns.get( columnVO.getNameDB() ), mappingVOs ) );
                     }
                  }
               }
            }
         }
      }
   }

   @SuppressWarnings("unchecked")
   public List< MappingVO > getMappingVOsByCondition( final ColumnVO columnVO ) throws KANException
   {
      // ��ʼ��MappingVO�б�
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // ���������� - ϵͳ����
      if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "1" ) )
      {
         // ���ϵͳ����ѡ���б�
         final List< MappingVO > systemOptions = KANUtil.getMappings( super.getLocale(), "def.column.option.type.system" );
         // ����ϵͳ����ѡ��
         if ( systemOptions != null && systemOptions.size() > 0 )
         {
            for ( MappingVO systemOption : systemOptions )
            {
               // ���ϵͳ����ѡ��
               if ( systemOption.getMappingId() != null && systemOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  mappingVOs = KANUtil.getMappings( super.getLocale(), systemOption.getMappingTemp() );
                  break;
               }
            }
         }
      }
      // ���������� - �˻�����
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "2" ) )
      {
         // ����˻�����ѡ���б�
         final List< MappingVO > accountOptions = KANUtil.getMappings( super.getLocale(), "def.column.option.type.account" );
         // �����˻�����ѡ��
         if ( accountOptions != null && accountOptions.size() > 0 )
         {
            for ( MappingVO accountOption : accountOptions )
            {
               // ����˻�����ѡ��
               if ( accountOption.getMappingId() != null && accountOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  // ��ʼ��Parameter Array
                  String parameters[];

                  if ( KANUtil.filterEmpty( super.getCorpId() ) != null )
                  {
                     parameters = new String[] { super.getLocale().getLanguage(), super.getCorpId() };
                  }
                  else
                  {
                     parameters = new String[] { super.getLocale().getLanguage() };
                  }

                  mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( super.getAccountId() ), accountOption.getMappingTemp(), parameters );
                  // ��ӿյ�MappingVO����
                  if ( mappingVOs != null )
                     mappingVOs.add( 0, KANUtil.getEmptyMappingVO( super.getLocale() ) );
                  break;
               }
            }
         }
      }
      // ���������� - �û��Զ���
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "3" ) )
      {
         mappingVOs = KANConstants.getKANAccountConstants( super.getAccountId() ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( super.getLocale().getLanguage() );
      }

      return mappingVOs;
   }

   public void setDynaColumns( Map< String, Object > dynaColumns )
   {
      this.dynaColumns = dynaColumns;
   }

   public String getYearly()
   {
      return yearly;
   }

   public void setYearly( String yearly )
   {
      this.yearly = yearly;
   }

   public String getYearPerformanceRating()
   {
      return yearPerformanceRating;
   }

   public void setYearPerformanceRating( String yearPerformanceRating )
   {
      this.yearPerformanceRating = yearPerformanceRating;
   }

   public String getYearPerformancePromotion()
   {
      return yearPerformancePromotion;
   }

   public void setYearPerformancePromotion( String yearPerformancePromotion )
   {
      this.yearPerformancePromotion = yearPerformancePromotion;
   }

   public String getRecommendTTCIncrease()
   {
      return recommendTTCIncrease;
   }

   public void setRecommendTTCIncrease( String recommendTTCIncrease )
   {
      this.recommendTTCIncrease = recommendTTCIncrease;
   }

   public String getTtcIncrease()
   {
      return ttcIncrease;
   }

   public void setTtcIncrease( String ttcIncrease )
   {
      this.ttcIncrease = ttcIncrease;
   }

   public String getNewTTCLocal()
   {
      return newTTCLocal;
   }

   public void setNewTTCLocal( String newTTCLocal )
   {
      this.newTTCLocal = newTTCLocal;
   }

   public String getNewTTCUSD()
   {
      return newTTCUSD;
   }

   public void setNewTTCUSD( String newTTCUSD )
   {
      this.newTTCUSD = newTTCUSD;
   }

   public String getNewBaseSalaryLocal()
   {
      return newBaseSalaryLocal;
   }

   public void setNewBaseSalaryLocal( String newBaseSalaryLocal )
   {
      this.newBaseSalaryLocal = newBaseSalaryLocal;
   }

   public String getNewBaseSalaryUSD()
   {
      return newBaseSalaryUSD;
   }

   public void setNewBaseSalaryUSD( String newBaseSalaryUSD )
   {
      this.newBaseSalaryUSD = newBaseSalaryUSD;
   }

   public String getNewAnnualSalaryLocal()
   {
      return newAnnualSalaryLocal;
   }

   public void setNewAnnualSalaryLocal( String newAnnualSalaryLocal )
   {
      this.newAnnualSalaryLocal = newAnnualSalaryLocal;
   }

   public String getNewAnnualSalaryUSD()
   {
      return newAnnualSalaryUSD;
   }

   public void setNewAnnualSalaryUSD( String newAnnualSalaryUSD )
   {
      this.newAnnualSalaryUSD = newAnnualSalaryUSD;
   }

   public String getNewAnnualHousingAllowance()
   {
      return newAnnualHousingAllowance;
   }

   public void setNewAnnualHousingAllowance( String newAnnualHousingAllowance )
   {
      this.newAnnualHousingAllowance = newAnnualHousingAllowance;
   }

   public String getNewAnnualChildrenEduAllowance()
   {
      return newAnnualChildrenEduAllowance;
   }

   public void setNewAnnualChildrenEduAllowance( String newAnnualChildrenEduAllowance )
   {
      this.newAnnualChildrenEduAllowance = newAnnualChildrenEduAllowance;
   }

   public String getNewAnnualGuaranteedAllowanceLocal()
   {
      return newAnnualGuaranteedAllowanceLocal;
   }

   public void setNewAnnualGuaranteedAllowanceLocal( String newAnnualGuaranteedAllowanceLocal )
   {
      this.newAnnualGuaranteedAllowanceLocal = newAnnualGuaranteedAllowanceLocal;
   }

   public String getNewAnnualGuatanteedAllowanceUSD()
   {
      return newAnnualGuatanteedAllowanceUSD;
   }

   public void setNewAnnualGuatanteedAllowanceUSD( String newAnnualGuatanteedAllowanceUSD )
   {
      this.newAnnualGuatanteedAllowanceUSD = newAnnualGuatanteedAllowanceUSD;
   }

   public String getNewMonthlyTarget()
   {
      return newMonthlyTarget;
   }

   public void setNewMonthlyTarget( String newMonthlyTarget )
   {
      this.newMonthlyTarget = newMonthlyTarget;
   }

   public String getNewQuarterlyTarget()
   {
      return newQuarterlyTarget;
   }

   public void setNewQuarterlyTarget( String newQuarterlyTarget )
   {
      this.newQuarterlyTarget = newQuarterlyTarget;
   }

   public String getNewGPTarget()
   {
      return newGPTarget;
   }

   public void setNewGPTarget( String newGPTarget )
   {
      this.newGPTarget = newGPTarget;
   }

   public String getNewTargetValueLocal()
   {
      return newTargetValueLocal;
   }

   public void setNewTargetValueLocal( String newTargetValueLocal )
   {
      this.newTargetValueLocal = newTargetValueLocal;
   }

   public String getNewTargetValueUSD()
   {
      return newTargetValueUSD;
   }

   public void setNewTargetValueUSD( String newTargetValueUSD )
   {
      this.newTargetValueUSD = newTargetValueUSD;
   }

   public String getNewJobGrade()
   {
      return newJobGrade;
   }

   public void setNewJobGrade( String newJobGrade )
   {
      this.newJobGrade = newJobGrade;
   }

   public String getNewInternalTitle()
   {
      return newInternalTitle;
   }

   public void setNewInternalTitle( String newInternalTitle )
   {
      this.newInternalTitle = newInternalTitle;
   }

   public String getNewPositionEN()
   {
      return newPositionEN;
   }

   public void setNewPositionEN( String newPositionEN )
   {
      this.newPositionEN = newPositionEN;
   }

   public String getNewPositionZH()
   {
      return newPositionZH;
   }

   public void setNewPositionZH( String newPositionZH )
   {
      this.newPositionZH = newPositionZH;
   }

   public String getNewShareOptions()
   {
      return newShareOptions;
   }

   public void setNewShareOptions( String newShareOptions )
   {
      this.newShareOptions = newShareOptions;
   }

   public String getTargetBonus()
   {
      return targetBonus;
   }

   public void setTargetBonus( String targetBonus )
   {
      this.targetBonus = targetBonus;
   }

   public String getProposedBonus()
   {
      return proposedBonus;
   }

   public void setProposedBonus( String proposedBonus )
   {
      this.proposedBonus = proposedBonus;
   }

   public String getProposedPayoutLocal()
   {
      return proposedPayoutLocal;
   }

   public void setProposedPayoutLocal( String proposedPayoutLocal )
   {
      this.proposedPayoutLocal = proposedPayoutLocal;
   }

   public String getProposedPayoutUSD()
   {
      return proposedPayoutUSD;
   }

   public void setProposedPayoutUSD( String proposedPayoutUSD )
   {
      this.proposedPayoutUSD = proposedPayoutUSD;
   }

   public String getComments()
   {
      return comments;
   }

   public void setComments( String comments )
   {
      this.comments = comments;
   }

   public String getUpdateBy()
   {
      return updateBy;
   }

   public void setUpdateBy( String updateBy )
   {
      this.updateBy = updateBy;
   }

   public String getUpdateDate()
   {
      return updateDate;
   }

   public void setUpdateDate( String updateDate )
   {
      this.updateDate = updateDate;
   }

   /**
    * decode Method List
    */
   public String getDecodeSettlementBranch()
   {
      return decodeField( this.getSettlementBranch(), this.branchs, true );
   }

   public String getDecodeBusinessType()
   {
      return decodeField( this.getBusinessTypeId(), this.businessTypes );
   }

   public String getDecodeTemplate()
   {
      return decodeField( this.getTemplateId(), this.templates );
   }

   public String getDecodeContractStatus()
   {
      return decodeField( this.getContractStatus(), this.contractStatuses );
   }

   public String getDecodeEmployStatus()
   {
      return decodeField( this.getEmployStatus(), this.employStatuses );
   }

   public String getDecodeBank()
   {
      return decodeField( super.getBankId(), this.banks );
   }

   public String getDecode_tempParentPositionOwners()
   {
      if ( KANUtil.filterEmpty( super.getRemark5() ) != null && "3".equals( getEmployStatus() ) )
      {
         return super.getRemark5();
      }

      String result = "";
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( super.getEmployeeId() );
         if ( staffDTOs != null && staffDTOs.size() == 1 )
         {
            final PositionVO positionVO = accountConstants.getMainPositionVOByStaffId( staffDTOs.get( 0 ).getStaffVO().getStaffId() );
            if ( positionVO != null )
            {
               final List< StaffDTO > parentStaffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getParentPositionId() );
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( StaffDTO staffDTO : parentStaffDTOs )
                  {
                     final StaffVO parentStaffVO = staffDTO.getStaffVO();
                     if ( parentStaffVO != null )
                     {
                        final PositionVO parentMainPositionVO = accountConstants.getMainPositionVOByStaffId( parentStaffVO.getStaffId() );
                        if ( parentMainPositionVO != null )
                        {
                           if ( KANUtil.filterEmpty( result ) == null )
                              result = accountConstants.getStaffNameByStaffId( parentStaffVO.getStaffId(), super.getAccountId().equals( "100017" ) ? true : false );
                           else
                              result = result + "��" + accountConstants.getStaffNameByStaffId( parentStaffVO.getStaffId(), super.getAccountId().equals( "100017" ) ? true : false );
                        }
                     }
                  }
               }
            }
         }
      }

      return result;
   }

   public String getDecode_tempPositionLocationIds()
   {
      String result = "";
      if ( KANUtil.filterEmpty( super.get_tempPositionLocationIds() ) != null )
      {
         for ( String locationId : super.get_tempPositionLocationIds().split( "," ) )
         {
            if ( KANUtil.filterEmpty( result ) == null )
               result = decodeField( locationId, locations );
            else
               result = result + "��" + decodeField( locationId, locations );
         }
      }
      return result;
   }

   public String getServiceYearNumber()
   {
      String yearNumber = "";
      if ( KANUtil.filterEmpty( super.getStartWorkDate() ) != null && KANUtil.filterEmpty( this.resignDate ) != null )
      {
         long workYears = ( KANUtil.getDays( KANUtil.createDate( this.resignDate ) ) - KANUtil.getDays( KANUtil.createDate( super.getStartWorkDate() ) ) ) / 365;
         yearNumber = String.valueOf( workYears );
      }
      return yearNumber;
   }

   public String getDecodeRelationshipIds()
   {
      String result = "";
      if ( KANUtil.filterEmpty( relationshipIds ) != null )
      {
         for ( String relationshipId : relationshipIds.split( "��" ) )
         {
            if ( KANUtil.filterEmpty( result ) == null )
               result = decodeField( relationshipId, relationships );
            else
               result = result + "��" + decodeField( relationshipId, relationships );
         }
      }
      return result;
   }

   public String getDecodeContactType()
   {
      String result = "";
      if ( KANUtil.filterEmpty( this.phones ) != null )
      {
         result = result + " T:" + phones;
      }
      if ( KANUtil.filterEmpty( this.mobiles ) != null )
      {
         result = result + " M:" + mobiles;
      }
      return result;
   }

   public String getDecodeGraduateDates()
   {
      String result = "";
      if ( KANUtil.filterEmpty( this.graduateDates ) != null )
      {
         for ( String graduateDate : graduateDates.split( "��" ) )
         {
            if ( KANUtil.filterEmpty( result ) == null )
               result = KANUtil.formatDate( graduateDate, "yyyy-MM-dd" );
            else
               result = result + "��" + KANUtil.formatDate( graduateDate, "yyyy-MM-dd" );
         }
      }

      return result;
   }

   // ��ȡ��λ�����ģ�
   public String getDecode_tempPositionIdsZH()
   {
      String result = "";
      if ( KANUtil.filterEmpty( super.get_tempPositionIds() ) != null )
      {
         for ( String id : super.get_tempPositionIds().split( "," ) )
         {
            final PositionVO positionVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionVOByPositionId( id );
            if ( positionVO != null )
            {
               if ( KANUtil.filterEmpty( result ) == null )
                  result = positionVO.getTitleZH();
               else
                  result = result + "��" + positionVO.getTitleZH();
            }
         }
      }

      return result;
   }

   // ��ȡ��λ��Ӣ�ģ�
   public String getDecode_tempPositionIdsEN()
   {
      String result = "";
      if ( KANUtil.filterEmpty( super.get_tempPositionIds() ) != null )
      {
         for ( String id : super.get_tempPositionIds().split( "," ) )
         {
            final PositionVO positionVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionVOByPositionId( id );
            if ( positionVO != null )
            {
               if ( KANUtil.filterEmpty( result ) == null )
                  result = positionVO.getTitleEN();
               else
                  result = result + "��" + positionVO.getTitleEN();
            }
         }
      }

      return result;
   }

   // �����ڽ���ʱ���Լ��ֶ���д
   public String getProbationEndDate()
   {
      //      String month = KANUtil.filterEmpty( probationMonth, "0" ) == null ? orderProbationMonth : probationMonth;
      //      if ( KANUtil.filterEmpty( month ) != null )
      //      {
      //         Date date = KANUtil.getDate( contractStartDate, 0, Integer.valueOf( month ), -1 );
      //         probationEndDate = KANUtil.formatDate( date, "yyyy-MM-dd", true );
      //      }
      return KANUtil.formatDate( probationEndDate, "yyyy-MM-dd", true );
   }

   public String getDecodeResidencyCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( super.getResidencyCityId(), super.getLocale().getLanguage() );
   }

   public void setProbationEndDate( String probationEndDate )
   {
      this.probationEndDate = probationEndDate;
   }

   public String getSchoolNames()
   {
      return schoolNames;
   }

   public void setSchoolNames( String schoolNames )
   {
      this.schoolNames = schoolNames;
   }

   public String getMajors()
   {
      return majors;
   }

   public void setMajors( String majors )
   {
      this.majors = majors;
   }

   public String getRelationshipIds()
   {
      return relationshipIds;
   }

   public void setRelationshipIds( String relationshipIds )
   {
      this.relationshipIds = relationshipIds;
   }

   public String getGraduateDates()
   {
      return graduateDates;
   }

   public void setGraduateDates( String graduateDates )
   {
      this.graduateDates = graduateDates;
   }

   public String getEmergencyNames()
   {
      return emergencyNames;
   }

   public void setEmergencyNames( String emergencyNames )
   {
      this.emergencyNames = emergencyNames;
   }

   public String getBizEmail()
   {
      return bizEmail;
   }

   public void setBizEmail( String bizEmail )
   {
      this.bizEmail = bizEmail;
   }

   public String getPersonalEmail()
   {
      return personalEmail;
   }

   public void setPersonalEmail( String personalEmail )
   {
      this.personalEmail = personalEmail;
   }

   public String getContractRemark5()
   {
      return contractRemark5;
   }

   public void setContractRemark5( String contractRemark5 )
   {
      this.contractRemark5 = contractRemark5;
   }

   public List< MappingVO > getBanks()
   {
      return banks;
   }

   public void setBanks( List< MappingVO > banks )
   {
      this.banks = banks;
   }

   public String getContractStartDate()
   {
      return KANUtil.formatDate( contractStartDate, "yyyy-MM-dd", true );
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return KANUtil.formatDate( contractEndDate, "yyyy-MM-dd", true );
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
   }

   public List< MappingVO > getRelationships()
   {
      return relationships;
   }

   public void setRelationships( List< MappingVO > relationships )
   {
      this.relationships = relationships;
   }

   public String getPhones()
   {
      return phones;
   }

   public void setPhones( String phones )
   {
      this.phones = phones;
   }

   public String getMobiles()
   {
      return mobiles;
   }

   public void setMobiles( String mobiles )
   {
      this.mobiles = mobiles;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getRt()
   {
      return rt;
   }

   public void setRt( String rt )
   {
      this.rt = rt;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getResignDateStart()
   {
      return resignDateStart;
   }

   public void setResignDateStart( String resignDateStart )
   {
      this.resignDateStart = resignDateStart;
   }

   public String getResignDateEnd()
   {
      return resignDateEnd;
   }

   public void setResignDateEnd( String resignDateEnd )
   {
      this.resignDateEnd = resignDateEnd;
   }

   public String getCurrency()
   {
      return currency;
   }

   public void setCurrency( String currency )
   {
      this.currency = currency;
   }

   public List< MappingVO > getSalarys()
   {
      return salarys;
   }

   public void setSalarys( List< MappingVO > salarys )
   {
      this.salarys = salarys;
   }

   /** ������Ա����Ч���� start **/
   // ����itemId��ȡн�귽����base
   public String getSalaryBaseByItemId( final String itemId )
   {
      if ( salarys != null && salarys.size() > 0 )
      {
         for ( MappingVO tempVO : salarys )
         {
            if ( tempVO.getMappingId().equals( itemId ) )
            {
               return formatNumber( tempVO.getMappingValue() );
            }
         }
      }

      return formatNumber( "0" );
   }

   // ��ȡ���ս�
   public String getCurrentYearEndBonus()
   {
      return getSalaryBaseByItemId( "18" );
   }

   // �����װ��������
   public String changeToUSD( final String number )
   {
      final ExchangeRateVO exchangeRateVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getExchangeRateVOByCurrencyCode( super.getCorpId(), getCurrencyCode() );

      if ( exchangeRateVO == null )
         return formatNumber( "0.00" );

      return formatNumber( String.valueOf( Double.valueOf( exchangeRateVO.getFromUSD() ) / Double.valueOf( exchangeRateVO.getToLocal() ) * Double.valueOf( number ) ) );
   }

   // ��ȡ��н
   public String getMontnlySalary()
   {
      return getSalaryBaseByItemId( "1" );
   }

   // ��ȡ��н(USD)
   public String getUSDMontnlySalary()
   {
      return changeToUSD( getMontnlySalary() );
   }

   // ��ȡ��н
   public String getAnnualSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getMontnlySalary() ) * 12 ) );
   }

   // ��ȡ��н(USD)
   public String getUSDAnnualSalary()
   {
      return changeToUSD( getAnnualSalary() );
   }

   // ��ȡס������
   public String getHousingAllowance()
   {
      return getSalaryBaseByItemId( "10201" );
   }

   // ��ȡ��Ů��������
   public String getChildenEducationAllowance()
   {
      return getSalaryBaseByItemId( "10145" );
   }

   // ��ȡÿ��̶�����= ��н + ס������ + ��Ů��������
   public String getAnnualFixedIncome()
   {
      return formatNumber( String.valueOf( Double.valueOf( getAnnualSalary() ) + Double.valueOf( getHousingAllowance() ) + Double.valueOf( getChildenEducationAllowance() ) ) );
   }

   // ��ȡÿ��̶�����(USD)= ��н + ס������ + ��Ů��������
   public String getUSDAnnualFixedIncome()
   {
      return changeToUSD( getAnnualFixedIncome() );
   }

   // ��ȡ�����¶Ƚ���
   public String getMonthlyTarget()
   {
      return getSalaryBaseByItemId( "10207" );
   }

   // ��ȡ���ۼ��Ƚ���
   public String getQuarterlyTarget()
   {
      return getSalaryBaseByItemId( "10153" );
   }

   // ��ȡ���۽�����ɣ�
   public String getBonusRebate()
   {
      return getSalaryBaseByItemId( "10155" );
   }
   
   // ��ȡ���۽�����ɣ��Ƿ��ǲ��̶���(1:yes;2:no)
   public int getYesGPTarget()
   {
     if ( salarys != null && salarys.size() > 0 )
     {
        for ( MappingVO tempVO : salarys )
        {
           if ( tempVO.getMappingId().equals( "10155" ) )
           {
              return Double.valueOf( tempVO.getMappingValue() ) == 0 ? 1 : 2;
           }
        }
     }

     return 2;
   }

   // ��ȡ���ս���
   public String getAnnualBonus()
   {
      if ( Double.valueOf( getMonthlyTarget() ) > 0 )
         return formatNumber( String.valueOf( Double.valueOf( getMonthlyTarget() ) * 12 ) );
      if ( Double.valueOf( getQuarterlyTarget() ) > 0 )
         return formatNumber( String.valueOf( Double.valueOf( getQuarterlyTarget() ) * 4 ) );

      return formatNumber( "0.00" );
   }

   // ��ȡ���ս���USD
   public String getUSDAnnualBonus()
   {
      return changeToUSD( getAnnualBonus() );
   }

   // ��ȡ���۽��𣨼��ȣ�
   @Deprecated
   public String getBonus()
   {
      return getSalaryBaseByItemId( "10207" );
   }

   // ��ȡ���۽���ʵ�ʣ�
   @Deprecated
   public String getBonusActual()
   {
      return getSalaryBaseByItemId( "10157" );
   }

   // ��ȡ���۽���ʵ�ʣ�(USD)
   public String getUSDBonusActual()
   {
      return changeToUSD( getBonusActual() );
   }

   // ��ȡTTC = �̶����� + �������
   public String getTTC()
   {
      return String.valueOf( Double.valueOf( getAnnualFixedIncome() ) + Double.valueOf( getAnnualBonus() ) );
   }

   // ��ȡTTC(USD)
   public String getUSDTTC()
   {
      return changeToUSD( getTTC() );
   }

   /** Ա����Ч���� end **/

   public String getSalaryStartDateStart()
   {
      return salaryStartDateStart;
   }

   public void setSalaryStartDateStart( String salaryStartDateStart )
   {
      this.salaryStartDateStart = salaryStartDateStart;
   }

   public String getSalaryStartDateEnd()
   {
      return salaryStartDateEnd;
   }

   public void setSalaryStartDateEnd( String salaryStartDateEnd )
   {
      this.salaryStartDateEnd = salaryStartDateEnd;
   }

   public String getSalaryEndDateStart()
   {
      return salaryEndDateStart;
   }

   public void setSalaryEndDateStart( String salaryEndDateStart )
   {
      this.salaryEndDateStart = salaryEndDateStart;
   }

   public String getSalaryEndDateEnd()
   {
      return salaryEndDateEnd;
   }

   public void setSalaryEndDateEnd( String salaryEndDateEnd )
   {
      this.salaryEndDateEnd = salaryEndDateEnd;
   }

   public String getLastWorkDate()
   {
      return KANUtil.formatDate( lastWorkDate, "yyyy-MM-dd", true );
   }

   public void setLastWorkDate( String lastWorkDate )
   {
      this.lastWorkDate = lastWorkDate;
   }

   public String getProbationMonth()
   {
      return probationMonth;
   }

   public void setProbationMonth( String probationMonth )
   {
      this.probationMonth = probationMonth;
   }

   public String getOrderProbationMonth()
   {
      return orderProbationMonth;
   }

   public void setOrderProbationMonth( String orderProbationMonth )
   {
      this.orderProbationMonth = orderProbationMonth;
   }

   public String getLocationId()
   {
      final JSONObject j = JSONObject.fromObject( getRemark1() );

      return ( j != null && j.get( "bangongdidian" ) != null ) ? j.get( "bangongdidian" ).toString() : "0";
   }

   // ��ȡ��������
   public String getCurrencyCode()
   {
      String currencyCode = "RMB";
      if ( KANUtil.filterEmpty( getLocationId(), "0" ) == null )
         currencyCode = "RMB";

      // Ko Lydia�Ƚ����⣬���ڴ�½����ȡHKD
      if ( "100012747".equals( super.getEmployeeId() ) )
         currencyCode = "HKD";

      switch ( getLocationId() )
      {
         case "59":
            currencyCode = "RMB";
            break;
         case "61":
            currencyCode = "RMB";
            break;
         case "63":
            currencyCode = "HKD";
            break;
         case "65":
            currencyCode = "RMB";
            break;
         case "67":
            currencyCode = "RMB";
            break;
         case "69":
            currencyCode = "SGD";
            break;

         default:
            break;
      }

      return currencyCode;
   }

   public String getSearch4Contact()
   {
      return search4Contact;
   }

   public void setSearch4Contact( String search4Contact )
   {
      this.search4Contact = search4Contact;
   }

   public String getShortBirthday()
   {
      return KANUtil.formatDate( getBirthday(), "MM-dd" );
   }
}

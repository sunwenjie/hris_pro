package com.kan.hro.domain.biz.employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kan.base.domain.management.CalendarDTO;
import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.cb.CBDTO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDTO;
import com.kan.hro.domain.biz.sb.SBDTO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;

public class ServiceContractDTO implements Serializable
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = -2815222029588088192L;

   // 添加Logger功能
   protected Log logger = LogFactory.getLog( getClass() );

   // Service Contract 
   private EmployeeContractVO employeeContractVO;

   // Client
   private ClientVO clientVO;

   // Client Order Header 
   private ClientOrderHeaderVO clientOrderHeaderVO;

   // Client Order Social Benefit - VO
   private List< ClientOrderSBVO > clientOrderSBVOs = new ArrayList< ClientOrderSBVO >();

   // Client Order Commercial Benefit - VO
   private List< ClientOrderCBVO > clientOrderCBVOs = new ArrayList< ClientOrderCBVO >();

   // Employee
   private EmployeeVO employeeVO;

   // Service Contract Salary
   private List< EmployeeContractSalaryVO > employeeContractSalaryVOs = new ArrayList< EmployeeContractSalaryVO >();

   // Service Contract Socical Benefit - DTO
   private List< EmployeeContractSBDTO > employeeContractSBDTOs = new ArrayList< EmployeeContractSBDTO >();

   // Service Contract Commercial Benefit - VO
   private List< EmployeeContractCBVO > employeeContractCBVOs = new ArrayList< EmployeeContractCBVO >();

   // Service Contract Leave - VO
   private List< EmployeeContractLeaveVO > employeeContractLeaveVOs = new ArrayList< EmployeeContractLeaveVO >();

   // Service Contract OT - VO
   private List< EmployeeContractOTVO > employeeContractOTVOs = new ArrayList< EmployeeContractOTVO >();

   // Service Contract Other - VO
   private List< EmployeeContractOtherVO > employeeContractOtherVOs = new ArrayList< EmployeeContractOtherVO >();

   // Timesheet
   private TimesheetDTO timesheetDTO;

   // Salary - DTO (Used for import)
   private List< SalaryDTO > salaryDTOs = new ArrayList< SalaryDTO >();

   // Social Benefit (Calculate Result) - DTO
   private List< SBDTO > sbDTOs = new ArrayList< SBDTO >();

   // Social Benefit Adjustment - DTO
   private List< SBAdjustmentDTO > sbAdjustmentDTOs = new ArrayList< SBAdjustmentDTO >();

   // Conmmercial Benefit (Calculate Result) - DTO
   private List< CBDTO > cbDTOs = new ArrayList< CBDTO >();

   // 缓存当前Monthly下所有ItemIds
   private List< String > existItemIds = new ArrayList< String >();

   // 缓存派送协议对应的已结算的个税
   private String taxAmountPersonal;

   // 缓存派送协议对应的已结算的税前工资
   private String addtionalBillAmountPersonal;

   // 开始当月不全的取当前合同开始日期，结束当月不全的取（入司日期跟1/1的较大值），当月全的就取（入司日期跟1/1的较大值）
   private String firstDayOfYearCircle;

   // 取结束日期（需考虑离职日期）跟12/31的较小值
   private String lastDayOfYearCircle;

   //全年工作小时数
   private String totalWorkHoursOfYear;

   //全年全勤小时数
   private String totalFullHoursOfYear;

   public EmployeeContractVO getEmployeeContractVO()
   {
      return employeeContractVO;
   }

   public void setEmployeeContractVO( EmployeeContractVO employeeContractVO )
   {
      this.employeeContractVO = employeeContractVO;
   }

   public final ClientVO getClientVO()
   {
      return clientVO;
   }

   public final void setClientVO( ClientVO clientVO )
   {
      this.clientVO = clientVO;
   }

   public ClientOrderHeaderVO getClientOrderHeaderVO()
   {
      return clientOrderHeaderVO;
   }

   public void setClientOrderHeaderVO( ClientOrderHeaderVO clientOrderHeaderVO )
   {
      this.clientOrderHeaderVO = clientOrderHeaderVO;
   }

   public final List< ClientOrderSBVO > getClientOrderSBVOs()
   {
      return clientOrderSBVOs;
   }

   public final void setClientOrderSBVOs( List< ClientOrderSBVO > clientOrderSBVOs )
   {
      this.clientOrderSBVOs = clientOrderSBVOs;
   }

   public List< ClientOrderCBVO > getClientOrderCBVOs()
   {
      return clientOrderCBVOs;
   }

   public void setClientOrderCBVOs( List< ClientOrderCBVO > clientOrderCBVOs )
   {
      this.clientOrderCBVOs = clientOrderCBVOs;
   }

   public List< EmployeeContractSalaryVO > getEmployeeContractSalaryVOs()
   {
      return employeeContractSalaryVOs;
   }

   public EmployeeContractSalaryVO getEmployeeContractSalaryVOByItemId( final String itemId )
   {
      if ( KANUtil.filterEmpty( itemId ) != null && employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
      {
         for ( EmployeeContractSalaryVO employeeContractSalaryVO : employeeContractSalaryVOs )
         {
            if ( KANUtil.filterEmpty( employeeContractSalaryVO.getItemId() ) != null && KANUtil.filterEmpty( employeeContractSalaryVO.getItemId() ).equals( itemId ) )
            {
               return employeeContractSalaryVO;
            }
         }
      }

      return null;
   }

   public void setEmployeeContractSalaryVOs( List< EmployeeContractSalaryVO > employeeContractSalaryVOs )
   {
      this.employeeContractSalaryVOs = employeeContractSalaryVOs;
   }

   public List< EmployeeContractSBDTO > getEmployeeContractSBDTOs()
   {
      return employeeContractSBDTOs;
   }

   public void setEmployeeContractSBDTOs( List< EmployeeContractSBDTO > employeeContractSBDTOs )
   {
      this.employeeContractSBDTOs = employeeContractSBDTOs;
   }

   public List< EmployeeContractCBVO > getEmployeeContractCBVOs()
   {
      return employeeContractCBVOs;
   }

   public void setEmployeeContractCBVOs( List< EmployeeContractCBVO > employeeContractCBVOs )
   {
      this.employeeContractCBVOs = employeeContractCBVOs;
   }

   public List< EmployeeContractLeaveVO > getEmployeeContractLeaveVOs()
   {
      return employeeContractLeaveVOs;
   }

   public void setEmployeeContractLeaveVOs( List< EmployeeContractLeaveVO > employeeContractLeaveVOs )
   {
      this.employeeContractLeaveVOs = employeeContractLeaveVOs;
   }

   public List< EmployeeContractOTVO > getEmployeeContractOTVOs()
   {
      return employeeContractOTVOs;
   }

   public void setEmployeeContractOTVOs( List< EmployeeContractOTVO > employeeContractOTVOs )
   {
      this.employeeContractOTVOs = employeeContractOTVOs;
   }

   public List< EmployeeContractOtherVO > getEmployeeContractOtherVOs()
   {
      return employeeContractOtherVOs;
   }

   public void setEmployeeContractOtherVOs( List< EmployeeContractOtherVO > employeeContractOtherVOs )
   {
      this.employeeContractOtherVOs = employeeContractOtherVOs;
   }

   public TimesheetDTO getTimesheetDTO()
   {
      return timesheetDTO;
   }

   public void setTimesheetDTO( TimesheetDTO timesheetDTO )
   {
      this.timesheetDTO = timesheetDTO;
   }

   public EmployeeVO getEmployeeVO()
   {
      return employeeVO;
   }

   public void setEmployeeVO( EmployeeVO employeeVO )
   {
      this.employeeVO = employeeVO;
   }

   public final List< SalaryDTO > getSalaryDTOs()
   {
      return salaryDTOs;
   }

   public final void setSalaryDTOs( List< SalaryDTO > salaryDTOs )
   {
      this.salaryDTOs = salaryDTOs;
   }

   public List< SBDTO > getSbDTOs()
   {
      return sbDTOs;
   }

   public void setSbDTOs( List< SBDTO > sbDTOs )
   {
      this.sbDTOs = sbDTOs;
   }

   public List< CBDTO > getCbDTOs()
   {
      return cbDTOs;
   }

   public void setCbDTOs( List< CBDTO > cbDTOs )
   {
      this.cbDTOs = cbDTOs;
   }

   public final List< String > getExistItemIds()
   {
      return existItemIds;
   }

   public final void setExistItemIds( List< String > existItemIds )
   {
      this.existItemIds = existItemIds;
   }

   public final String getTaxAmountPersonal()
   {
      return taxAmountPersonal;
   }

   public final void setTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = taxAmountPersonal;
   }

   public final String getAddtionalBillAmountPersonal()
   {
      return addtionalBillAmountPersonal;
   }

   public final void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public final List< SBAdjustmentDTO > getSbAdjustmentDTOs()
   {
      return sbAdjustmentDTOs;
   }

   public final void setSbAdjustmentDTOs( List< SBAdjustmentDTO > sbAdjustmentDTOs )
   {
      this.sbAdjustmentDTOs = sbAdjustmentDTOs;
   }

   // 按照ItemId获取EmployeeContractOTVO对象
   public EmployeeContractOTVO getEmployeeContractOTVOByItemId( final String itemId )
   {
      if ( this.getEmployeeContractOTVOs() != null && this.getEmployeeContractOTVOs().size() > 0 )
      {
         for ( EmployeeContractOTVO employeeContractOTVO : this.getEmployeeContractOTVOs() )
         {
            if ( employeeContractOTVO.getItemId() != null && employeeContractOTVO.getItemId().trim().equals( itemId ) )
            {
               return employeeContractOTVO;
            }
         }
      }

      return null;
   }

   // 社保计算
   public boolean calculateSB( final HttpServletRequest request ) throws KANException
   {
      if ( this.getEmployeeContractSBDTOs() != null && this.getEmployeeContractSBDTOs().size() > 0 )
      {
         // 按照社保方案遍历并处理
         for ( EmployeeContractSBDTO employeeContractSBDTO : this.getEmployeeContractSBDTOs() )
         {
            logger.info( "SB Calculate Start - " + this.getEmployeeContractVO().getContractId() + " / " + this.getEmployeeVO().getNameZH() );

            // 初始化EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBDTO.getEmployeeContractSBVO();

            // 初始化Monthly List
            final List< String > monthlys = new ArrayList< String >();

            // 加载社保方案
            final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( this.getEmployeeContractVO().getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );

            // 如果社保方案不存在
            if ( socialBenefitSolutionDTO == null || socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() == null
                  || socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() == null || socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs().size() == 0 )
            {
               continue;
            }

            // 加载系统社保
            final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSysHeaderId() );

            // 如果系统社保不存在
            if ( socialBenefitDTO == null || socialBenefitDTO.getSocialBenefitHeaderVO() == null || socialBenefitDTO.getSocialBenefitDetailVOs() == null
                  || socialBenefitDTO.getSocialBenefitDetailVOs().size() == 0 )
            {
               continue;
            }

            // 初始化系统社保中的列表数据
            socialBenefitDTO.getSocialBenefitHeaderVO().reset( null, request );

            String calendarId = this.getEmployeeContractVO().getCalendarId();
            if ( calendarId == null || calendarId.trim().equals( "" ) || calendarId.trim().equals( "0" ) )
            {
               calendarId = this.getClientOrderHeaderVO().getCalendarId();
            }

            // 初始化社保个人部分公司承担
            String personalSBBurden = employeeContractSBVO.getPersonalSBBurden();

            // 尝试从订单社保方案中获取
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               // 获取ClientOrderSBVO
               final ClientOrderSBVO clientOrderSBVO = getClientOrderSBVOBySolutionId( employeeContractSBVO.getSbSolutionId() );

               if ( clientOrderSBVO != null )
               {
                  personalSBBurden = clientOrderSBVO.getPersonalSBBurden();
               }
            }

            // 尝试从订单中获取
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               personalSBBurden = clientOrderHeaderVO.getPersonalSBBurden();
            }

            // 尝试从社保方案中获取
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               personalSBBurden = socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getPersonalSBBurden();
            }

            // 加载所需缴纳的月份
            monthlys.addAll( getAvailableMonthlys( this.getEmployeeContractVO().getAccountId(), this.getEmployeeContractVO().getMonthly(), calendarId, employeeContractSBDTO, socialBenefitDTO.getSocialBenefitHeaderVO(), socialBenefitSolutionDTO ) );

            if ( monthlys != null && monthlys.size() > 0 )
            {
               for ( String monthly : monthlys )
               {
                  // 初始化SBDTO
                  final SBDTO sbDTO = new SBDTO();

                  final String dynamicMonthly = monthly.split( "_" )[ 0 ];
                  String calculateFlag = monthly.split( "_" )[ 1 ];

                  /** 设置社保台帐 */
                  final SBHeaderVO sbHeaderVO = new SBHeaderVO();
                  // 账户信息
                  sbHeaderVO.setAccountId( this.getEmployeeContractVO().getAccountId() );
                  // 基础信息
                  sbHeaderVO.setEntityId( this.getEmployeeContractVO().getEntityId() );
                  sbHeaderVO.setBusinessTypeId( this.getEmployeeContractVO().getBusinessTypeId() );
                  sbHeaderVO.setClientId( this.getEmployeeContractVO().getClientId() );
                  sbHeaderVO.setCorpId( this.getEmployeeContractVO().getCorpId() );
                  sbHeaderVO.setClientNo( this.getClientVO().getNumber() );
                  sbHeaderVO.setClientNameZH( this.getClientVO().getNameZH() );
                  sbHeaderVO.setClientNameEN( this.getClientVO().getNameEN() );
                  sbHeaderVO.setOrderId( this.getEmployeeContractVO().getOrderId() );
                  sbHeaderVO.setContractId( this.getEmployeeContractVO().getContractId() );
                  sbHeaderVO.setContractStartDate( this.getEmployeeContractVO().getStartDate() );
                  sbHeaderVO.setContractEndDate( this.getEmployeeContractVO().getEndDate() );
                  sbHeaderVO.setContractBranch( this.getEmployeeContractVO().getBranch() );
                  sbHeaderVO.setContractOwner( this.getEmployeeContractVO().getOwner() );
                  sbHeaderVO.setEmployeeId( this.getEmployeeVO().getEmployeeId() );
                  sbHeaderVO.setEmployeeNameZH( this.getEmployeeVO().getNameZH() );
                  sbHeaderVO.setEmployeeNameEN( this.getEmployeeVO().getNameEN() );
                  // 雇员社保ID，非社保方案ID
                  sbHeaderVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                  sbHeaderVO.setEmployeeSBNameZH( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() );
                  sbHeaderVO.setEmployeeSBNameEN( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameEN() );
                  sbHeaderVO.setCityId( socialBenefitDTO.getSocialBenefitHeaderVO().getCityId() );

                  // 如果当前社保需要使用供应商
                  if ( employeeContractSBDTO.getVendorServiceVO() != null && employeeContractSBDTO.getVendorVO() != null )
                  {
                     sbHeaderVO.setVendorId( employeeContractSBDTO.getVendorServiceVO().getVendorId() );
                     sbHeaderVO.setVendorNameZH( employeeContractSBDTO.getVendorVO().getNameZH() );
                     sbHeaderVO.setVendorNameEN( employeeContractSBDTO.getVendorVO().getNameEN() );
                     sbHeaderVO.setVendorServiceIds( employeeContractSBDTO.getVendorServiceVO().getServiceIds() );
                     sbHeaderVO.setVendorServiceFee( employeeContractSBDTO.getVendorServiceVO().getServiceFee() );
                  }

                  sbHeaderVO.setWorkPlace( "" );
                  sbHeaderVO.setGender( this.getEmployeeVO().getSalutation() );
                  sbHeaderVO.setCertificateType( this.getEmployeeVO().getCertificateType() );
                  sbHeaderVO.setCertificateNumber( this.getEmployeeVO().getCertificateNumber() );
                  sbHeaderVO.setNeedMedicalCard( employeeContractSBVO.getNeedMedicalCard() );
                  sbHeaderVO.setNeedSBCard( employeeContractSBVO.getNeedSBCard() );
                  sbHeaderVO.setMedicalNumber( employeeContractSBVO.getMedicalNumber() );
                  sbHeaderVO.setSbNumber( employeeContractSBVO.getSbNumber() );
                  sbHeaderVO.setFundNumber( employeeContractSBVO.getFundNumber() );
                  sbHeaderVO.setPersonalSBBurden( personalSBBurden );
                  sbHeaderVO.setResidencyType( this.getEmployeeVO().getResidencyType() );
                  sbHeaderVO.setResidencyCityId( this.getEmployeeVO().getResidencyCityId() );
                  sbHeaderVO.setResidencyAddress( this.getEmployeeVO().getResidencyAddress() );
                  sbHeaderVO.setHighestEducation( this.getEmployeeVO().getHighestEducation() );
                  sbHeaderVO.setMaritalStatus( this.getEmployeeVO().getMaritalStatus() );
                  // 雇员状态 - 服务协议中的雇员状态
                  sbHeaderVO.setEmployStatus( this.getEmployeeContractVO().getEmployStatus() );
                  // 社保状态 - 历史月则为补缴
                  sbHeaderVO.setSbStatus( dynamicMonthly.equals( this.getEmployeeContractVO().getMonthly() ) ? employeeContractSBVO.getStatus() : "7" );
                  // 起缴年月
                  sbHeaderVO.setStartDate( employeeContractSBVO.getStartDate() );
                  // 退缴年月
                  sbHeaderVO.setEndDate( employeeContractSBVO.getEndDate() );
                  // 入职日期 - 服务协议开始时间
                  sbHeaderVO.setOnboardDate( this.getEmployeeContractVO().getStartDate() );
                  // 离职日期
                  sbHeaderVO.setResignDate( this.getEmployeeContractVO().getResignDate() );
                  // 社保操作月 - 跟费用月和所属月不同
                  sbHeaderVO.setMonthly( this.getEmployeeContractVO().getMonthly() );
                  // 缴纳状态 - 正常缴纳的社保默认为“正常”
                  String flag = KANUtil.filterEmpty( employeeContractSBVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractSBVO.getFlag() ).equals( "1" ) ? "1" : "2";
                  sbHeaderVO.setFlag( flag );
                  sbHeaderVO.setDeleted( "1" );
                  sbHeaderVO.setStatus( SBHeaderVO.TRUE );
                  sbHeaderVO.setCreateBy( BaseAction.getUserId( request, null ) );
                  sbHeaderVO.setModifyBy( BaseAction.getUserId( request, null ) );
                  sbDTO.setSbHeaderVO( sbHeaderVO );

                  if ( socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() != null && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs().size() > 0 )
                  {
                     for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() )
                     {
                        // 获取当前社保科目
                        final ItemVO itemVO = KANConstants.getItemVOByItemId( socialBenefitSolutionDetailVO.getItemId() );

                        // 获取当前社保明细（系统）
                        final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDTO.getSocialBenefitDetailVOByItemId( socialBenefitSolutionDetailVO.getItemId() );

                        // 获得当前社保基数
                        final EmployeeContractSBDetailVO employeeContractSBDetailVO = employeeContractSBDTO.getEmployeeContractSBDetailVOBySolutionDetailId( socialBenefitSolutionDetailVO.getDetailId() );

                        // 如果当前社保科目或社保明细（系统）为Null，跳出这一次循环
                        if ( itemVO == null || socialBenefitDetailVO == null )
                        {
                           continue;
                        }

                        // 初始化系统社保明细中的列表数据
                        socialBenefitDetailVO.reset( null, request );

                        // 加载科目所需缴纳的月份
                        final List< String > itemMonthlys = getAvailableMonthlys( this.getEmployeeContractVO().getAccountId(), this.getEmployeeContractVO().getMonthly(), calendarId, employeeContractSBDTO, socialBenefitDTO.getSocialBenefitHeaderVO(), socialBenefitDetailVO, socialBenefitSolutionDTO );

                        // 如果科目明细设置无需缴纳，跳出这一次循环
                        if ( itemMonthlys == null || itemMonthlys.size() == 0 )
                        {
                           continue;
                        }

                        if ( itemMonthlys.contains( dynamicMonthly + "_0" ) )
                        {
                           calculateFlag = "0";
                        }
                        else if ( itemMonthlys.contains( dynamicMonthly + "_1" ) )
                        {
                           calculateFlag = "1";
                        }

                        // 初始化个人和公司的基数 - 默认取社保下限
                        String basePersonal = socialBenefitSolutionDetailVO.getPersonalFloor();
                        String baseCompany = socialBenefitSolutionDetailVO.getCompanyFloor();

                        // 存在社保基数
                        if ( employeeContractSBDetailVO != null )
                        {
                           // 设置个人基数
                           if ( employeeContractSBDetailVO.getBasePersonal() != null && !employeeContractSBDetailVO.getBasePersonal().trim().equals( "" ) )
                           {
                              basePersonal = employeeContractSBDetailVO.getBasePersonal();
                           }
                           // 设置公司基数
                           if ( employeeContractSBDetailVO.getBaseCompany() != null && !employeeContractSBDetailVO.getBaseCompany().trim().equals( "" ) )
                           {
                              baseCompany = employeeContractSBDetailVO.getBaseCompany();
                           }
                        }

                        /** 设置社保台帐明细 */
                        final SBDetailVO sbDetailVO = new SBDetailVO();
                        sbDetailVO.setItemId( itemVO.getItemId() );
                        sbDetailVO.setItemNo( itemVO.getItemNo() );
                        sbDetailVO.setNameZH( itemVO.getNameZH() );
                        sbDetailVO.setNameEN( itemVO.getNameEN() );
                        sbDetailVO.setBasePersonal( basePersonal );
                        sbDetailVO.setBaseCompany( baseCompany );
                        sbDetailVO.setRatePersonal( socialBenefitSolutionDetailVO.getPersonalPercent() );
                        sbDetailVO.setRateCompany( socialBenefitSolutionDetailVO.getCompanyPercent() );
                        sbDetailVO.setFixPersonal( socialBenefitSolutionDetailVO.getPersonalFixAmount() );
                        sbDetailVO.setFixCompany( socialBenefitSolutionDetailVO.getCompanyFixAmount() );

                        // 科目个人部分
                        double amountPersonal = Double.valueOf( sbDetailVO.getBasePersonal() ) * Double.valueOf( sbDetailVO.getRatePersonal() ) / 100
                              + Double.valueOf( sbDetailVO.getFixPersonal() );
                        // 科目公司部分
                        double amountCompany = Double.valueOf( sbDetailVO.getBaseCompany() ) * Double.valueOf( sbDetailVO.getRateCompany() ) / 100
                              + Double.valueOf( sbDetailVO.getFixCompany() );

                        // 所有金额为“0”，跳出这一次循环
                        if ( amountPersonal == 0 && amountCompany == 0 )
                        {
                           continue;
                        }

                        // 初始化合计字符串
                        String amountPersonalString = String.valueOf( amountPersonal );
                        String amountCompanyString = String.valueOf( amountCompany );

                        // 初始化精度及截取 公司部分
                        String companyAccuracy = socialBenefitDetailVO.getDecodeCompanyAccuracyTemp();

                        // 初始化精度及截取 个人部分
                        String personalAccuracy = socialBenefitDetailVO.getDecodePersonalAccuracyTemp();
                        
                        String round = socialBenefitDetailVO.getRound();

                        // 如果明细没有设置精度，便取主表
                        if ( KANUtil.filterEmpty( companyAccuracy ) == null )
                        {
                           companyAccuracy = socialBenefitDTO.getSocialBenefitHeaderVO().getDecodeCompanyAccuracyTemp();
                        }
                        // 如果明细没有设置精度，便取主表
                        if ( KANUtil.filterEmpty( personalAccuracy ) == null )
                        {
                           personalAccuracy = socialBenefitDTO.getSocialBenefitHeaderVO().getDecodePersonalAccuracyTemp();
                        }

                        // 如果明细没有设置截取，便取主表
                        if ( KANUtil.filterEmpty( round, "0" ) == null )
                        {
                           round = socialBenefitDTO.getSocialBenefitHeaderVO().getRound();
                        }

                        // 小数格式化
                        if ( KANUtil.filterEmpty( personalAccuracy ) != null )
                        {
                           amountPersonalString = KANUtil.round( amountPersonal, Integer.valueOf( personalAccuracy ), round );
                        }
                        // 小数格式化
                        if ( KANUtil.filterEmpty( companyAccuracy ) != null )
                        {
                           amountCompanyString = KANUtil.round( amountCompany, Integer.valueOf( companyAccuracy ), round );
                        }
                        // 需要产生费用
                        if ( calculateFlag != null && calculateFlag.trim().equals( "1" ) )
                        {
                           sbDetailVO.setAmountPersonal( amountPersonalString );
                           sbDetailVO.setAmountCompany( amountCompanyString );
                        }
                        // 无需产生费用
                        else
                        {
                           sbDetailVO.setAmountPersonal( "0" );
                           sbDetailVO.setAmountCompany( "0" );
                        }

                        sbDetailVO.setMonthly( this.getEmployeeContractVO().getMonthly() );

                        // 初始化Attribute - 从方案Detail取
                        String attribute = socialBenefitSolutionDTO.getSBAttributeByItemId( socialBenefitDetailVO.getItemId() );

                        // 从方案Header取（未取到情况）
                        if ( KANUtil.filterEmpty( attribute, "0" ) == null )
                        {
                           attribute = socialBenefitSolutionDTO.getSBAttribute();
                        }

                        // 从政策Detail取（未取到情况）
                        if ( KANUtil.filterEmpty( attribute, "0" ) == null )
                        {
                           attribute = socialBenefitDetailVO.getAttribute();
                        }

                        // 从政策Header取（未取到情况）
                        if ( KANUtil.filterEmpty( attribute, "0" ) == null )
                        {
                           attribute = socialBenefitDTO.getSocialBenefitHeaderVO().getAttribute();
                        }

                        // 初始化MonthlyGap
                        int monthlyGap = 0;
                        if ( attribute != null )
                        {
                           if ( attribute.trim().equals( "2" ) )
                           {
                              monthlyGap = 1;
                           }
                           else if ( attribute.trim().equals( "3" ) )
                           {
                              monthlyGap = -1;
                           }
                        }

                        sbDetailVO.setAccountMonthly( KANUtil.getMonthly( dynamicMonthly, monthlyGap ) );
                        sbDetailVO.setDeleted( "1" );
                        sbDetailVO.setStatus( SBDetailVO.TRUE );
                        sbDetailVO.setCreateBy( BaseAction.getUserId( request, null ) );
                        sbDetailVO.setModifyBy( BaseAction.getUserId( request, null ) );
                        sbDTO.getSbDetailVOs().add( sbDetailVO );
                     }
                  }

                  if ( sbDTO.getSbDetailVOs() != null && sbDTO.getSbDetailVOs().size() > 0 )
                  {
                     this.getSbDTOs().add( sbDTO );
                  }
               }
            }

            logger.info( "SB Calculate End - " + this.getEmployeeContractVO().getContractId() + " / " + this.getEmployeeVO().getNameZH() );
         }
      }

      return true;
   }

   // 商保计算
   public boolean calculateCB( final HttpServletRequest request ) throws KANException
   {
      if ( this.getEmployeeContractCBVOs() != null && this.getEmployeeContractCBVOs().size() > 0 )
      {
         // 按照商保方案遍历并处理
         for ( EmployeeContractCBVO employeeContractCBVO : this.getEmployeeContractCBVOs() )
         {
            // 加载商保方案
            final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( this.getEmployeeContractVO().getAccountId() ).getCommercialBenefitSolutionDTOByHeaderId( employeeContractCBVO.getSolutionId() );

            // 如果商保方案不存在
            if ( commercialBenefitSolutionDTO == null )
            {
               continue;
            }

            // 加载商保方案 - 服务订单
            final ClientOrderCBVO clientOrderCBVO = getClientOrderCBVOBySolutionId( employeeContractCBVO.getSolutionId() );

            // 初始化商保方案中的列表数据
            commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().reset( null, request );

            // 获取当前需要处理的月份
            final String monthly = this.getEmployeeContractVO().getMonthly();

            // 初始化CBDTO
            final CBDTO cbDTO = new CBDTO();

            /** 设置商保台帐 */
            final CBHeaderVO cbHeaderVO = new CBHeaderVO();
            // 账户信息
            cbHeaderVO.setAccountId( this.getEmployeeContractVO().getAccountId() );
            // 基础信息
            cbHeaderVO.setEntityId( this.getEmployeeContractVO().getEntityId() );
            cbHeaderVO.setBusinessTypeId( this.getEmployeeContractVO().getBusinessTypeId() );
            cbHeaderVO.setClientId( this.getEmployeeContractVO().getClientId() );
            cbHeaderVO.setCorpId( this.getEmployeeContractVO().getCorpId() );
            cbHeaderVO.setClientNO( this.getClientVO().getNumber() );
            cbHeaderVO.setClientNameZH( this.getClientVO().getNameZH() );
            cbHeaderVO.setClientNameEN( this.getClientVO().getNameEN() );
            cbHeaderVO.setOrderId( this.getEmployeeContractVO().getOrderId() );
            cbHeaderVO.setContractId( this.getEmployeeContractVO().getContractId() );
            cbHeaderVO.setContractStartDate( this.getEmployeeContractVO().getStartDate() );
            cbHeaderVO.setContractEndDate( this.getEmployeeContractVO().getEndDate() );
            cbHeaderVO.setContractBranch( this.getEmployeeContractVO().getBranch() );
            cbHeaderVO.setContractOwner( this.getEmployeeContractVO().getOwner() );
            cbHeaderVO.setEmployeeId( this.getEmployeeVO().getEmployeeId() );
            cbHeaderVO.setEmployeeNameZH( this.getEmployeeVO().getNameZH() );
            cbHeaderVO.setEmployeeNameEN( this.getEmployeeVO().getNameEN() );
            // 雇员商保ID，非商保方案ID
            cbHeaderVO.setEmployeeCBId( employeeContractCBVO.getEmployeeCBId() );
            cbHeaderVO.setEmployeeCBNameZH( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() );
            cbHeaderVO.setEmployeeCBNameEN( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameEN() );
            cbHeaderVO.setWorkPlace( "" );
            cbHeaderVO.setGender( this.getEmployeeVO().getSalutation() );
            cbHeaderVO.setCertificateType( this.getEmployeeVO().getCertificateType() );
            cbHeaderVO.setCertificateNumber( this.getEmployeeVO().getCertificateNumber() );
            cbHeaderVO.setResidencyType( this.getEmployeeVO().getResidencyType() );
            cbHeaderVO.setResidencyCityId( this.getEmployeeVO().getResidencyCityId() );
            cbHeaderVO.setResidencyAddress( this.getEmployeeVO().getResidencyAddress() );
            cbHeaderVO.setHighestEducation( this.getEmployeeVO().getHighestEducation() );
            cbHeaderVO.setMaritalStatus( this.getEmployeeVO().getMaritalStatus() );
            // 雇员状态 - 服务协议中的雇员状态
            cbHeaderVO.setEmployStatus( this.getEmployeeContractVO().getEmployStatus() );
            // 商保状态
            cbHeaderVO.setCbStatus( employeeContractCBVO.getStatus() );
            // 申购日期
            cbHeaderVO.setStartDate( employeeContractCBVO.getStartDate() );
            // 退购日期
            cbHeaderVO.setEndDate( employeeContractCBVO.getEndDate() );
            // 入职日期 - 服务协议开始时间
            cbHeaderVO.setOnboardDate( this.getEmployeeContractVO().getStartDate() );
            // 离职日期
            cbHeaderVO.setResignDate( this.getEmployeeContractVO().getResignDate() );
            // 商保操作月 - 跟费用月和所属月不同
            cbHeaderVO.setMonthly( monthly );
            cbHeaderVO.setDeleted( "1" );
            cbHeaderVO.setStatus( CBHeaderVO.TRUE );
            cbHeaderVO.setCreateBy( BaseAction.getUserId( request, null ) );
            cbHeaderVO.setModifyBy( BaseAction.getUserId( request, null ) );
            cbDTO.setCbHeaderVO( cbHeaderVO );

            if ( commercialBenefitSolutionDTO.getCommercialBenefitSolutionDetailVOs() != null && commercialBenefitSolutionDTO.getCommercialBenefitSolutionDetailVOs().size() > 0 )
            {
               for ( CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO : commercialBenefitSolutionDTO.getCommercialBenefitSolutionDetailVOs() )
               {
                  // 获取当前商保科目
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( this.getEmployeeContractVO().getAccountId() ).getItemVOByItemId( commercialBenefitSolutionDetailVO.getItemId() );

                  // 如果当前社保科目为空，跳出这一次循环
                  if ( itemVO == null )
                  {
                     continue;
                  }

                  // 初始化商保方案明细中的列表数据
                  commercialBenefitSolutionDetailVO.reset( null, request );

                  /** 设置商保台帐明细 */
                  final CBDetailVO cbDetailVO = new CBDetailVO();
                  cbDetailVO.setItemId( itemVO.getItemId() );
                  cbDetailVO.setItemNo( itemVO.getItemNo() );
                  cbDetailVO.setNameZH( itemVO.getNameZH() );
                  cbDetailVO.setNameEN( itemVO.getNameEN() );

                  // 初始化商保计算方式
                  String calculateType = commercialBenefitSolutionDetailVO.getCalculateType();
                  if ( calculateType == null || calculateType.trim().equals( "" ) || calculateType.trim().equals( "0" ) )
                  {
                     calculateType = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCalculateType();
                  }

                  // 初始化Discount
                  double discount = 1;
                  // 初始化TempDiscount
                  final double tempDiscount = getDiscount( monthly, employeeContractCBVO.getStartDate(), employeeContractCBVO.getEndDate(), employeeContractCBVO.getStatus() );
                  // 如果商保是按天收取的，需要计算折扣
                  if ( calculateType != null && calculateType.trim().equals( "2" ) )
                  {
                     discount = tempDiscount;
                  }

                  // 不全月的情况
                  if ( tempDiscount < 1 )
                  {
                     // 初始化FreeShortOfMonth
                     String freeShortOfMonth = employeeContractCBVO.getFreeShortOfMonth();
                     if ( clientOrderCBVO != null && ( freeShortOfMonth == null || freeShortOfMonth.trim().equals( "0" ) ) )
                     {
                        freeShortOfMonth = clientOrderCBVO.getFreeShortOfMonth();
                     }
                     // 不全月免收服务费
                     if ( freeShortOfMonth != null && freeShortOfMonth.trim().equals( "1" ) )
                     {
                        discount = 0;
                     }

                     // 初始化ChargeFullMonth
                     String chargeFullMonth = employeeContractCBVO.getChargeFullMonth();
                     if ( clientOrderCBVO != null && ( chargeFullMonth == null || chargeFullMonth.trim().equals( "0" ) ) )
                     {
                        chargeFullMonth = clientOrderCBVO.getChargeFullMonth();
                     }
                     // 无论是否全月都按全月收
                     if ( chargeFullMonth != null && chargeFullMonth.trim().equals( "1" ) )
                     {
                        discount = 1;
                     }
                  }

                  // 采购成本
                  double amountPurchaseCost = Double.valueOf( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getPurchaseCost() ) != null ? commercialBenefitSolutionDetailVO.getPurchaseCost()
                        : "0" )
                        * discount;
                  // 销售成本
                  double amountSalesCost = Double.valueOf( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getSalesCost() ) != null ? commercialBenefitSolutionDetailVO.getSalesCost()
                        : "0" )
                        * discount;
                  // 销售价格
                  double amountSalesPrice = Double.valueOf( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getSalesPrice() ) != null ? commercialBenefitSolutionDetailVO.getSalesPrice()
                        : "0" )
                        * discount;

                  // 所有金额为“0”，跳出这一次循环
                  if ( amountPurchaseCost == 0 && amountSalesCost == 0 && amountSalesPrice == 0 )
                  {
                     continue;
                  }

                  // 初始化合计字符串
                  String amountPurchaseCostString = String.valueOf( amountPurchaseCost );
                  String amountSalesCostString = String.valueOf( amountSalesCost );
                  String amountSalesPriceString = String.valueOf( amountSalesPrice );

                  // 初始化精度及截取
                  String accuracy = commercialBenefitSolutionDetailVO.getDecodeAccuracyTemp();
                  String round = commercialBenefitSolutionDetailVO.getRound();

                  // 如果明细没有设置精度，便取主表
                  if ( KANUtil.filterEmpty( accuracy ) == null )
                  {
                     accuracy = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getDecodeAccuracyTemp();
                  }

                  // 如果明细没有设置截取，便取主表
                  if ( KANUtil.filterEmpty( round, "0" ) == null )
                  {
                     round = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getRound();
                  }

                  // 小数格式化
                  if ( KANUtil.filterEmpty( accuracy ) != null )
                  {
                     amountPurchaseCostString = KANUtil.round( amountPurchaseCost, Integer.valueOf( accuracy ), round );
                     amountSalesCostString = KANUtil.round( amountSalesCost, Integer.valueOf( accuracy ), round );
                     amountSalesPriceString = KANUtil.round( amountSalesPrice, Integer.valueOf( accuracy ), round );
                  }

                  cbDetailVO.setAmountPurchaseCost( amountPurchaseCostString );
                  cbDetailVO.setAmountSalesCost( amountSalesCostString );
                  cbDetailVO.setAmountSalesPrice( amountSalesPriceString );
                  cbDetailVO.setMonthly( monthly );
                  cbDetailVO.setDeleted( "1" );
                  cbDetailVO.setStatus( CBDetailVO.TRUE );
                  cbDetailVO.setCreateBy( BaseAction.getUserId( request, null ) );
                  cbDetailVO.setModifyBy( BaseAction.getUserId( request, null ) );
                  cbDTO.getCbDetailVOs().add( cbDetailVO );
               }
            }

            if ( cbDTO.getCbDetailVOs() != null && cbDTO.getCbDetailVOs().size() > 0 )
            {
               this.getCbDTOs().add( cbDTO );
            }
         }
      }

      return true;
   }

   private List< String > getAvailableMonthlys( final String accountId, final String monthly, final String calendarId, final EmployeeContractSBDTO employeeContractSBDTO,
         final SocialBenefitHeaderVO socialBenefitHeaderVO, final SocialBenefitSolutionDTO socialBenefitSolutionDTO ) throws KANException
   {
      // 初始化Start Rule
      String tempStartRule = socialBenefitSolutionDTO.getSBStartRule();

      if ( KANUtil.filterEmpty( tempStartRule, "0" ) == null )
      {
         tempStartRule = socialBenefitHeaderVO.getStartRule();
      }

      // 初始化Start Rule Remark
      String tempStartRuleRemark = socialBenefitSolutionDTO.getSBStartRuleRemark();

      if ( KANUtil.filterEmpty( tempStartRuleRemark, "0" ) == null )
      {
         tempStartRuleRemark = socialBenefitHeaderVO.getStartRuleRemark();
      }

      // 初始化End Rule
      String tempEndRule = socialBenefitSolutionDTO.getSBEndRule();

      if ( KANUtil.filterEmpty( tempEndRule, "0" ) == null )
      {
         tempEndRule = socialBenefitHeaderVO.getEndRule();
      }

      // 初始化End Rule Remark
      String tempEndRuleRemark = socialBenefitSolutionDTO.getSBEndRuleRemark();

      if ( KANUtil.filterEmpty( tempEndRuleRemark, "0" ) == null )
      {
         tempEndRuleRemark = socialBenefitHeaderVO.getEndRuleRemark();
      }

      return getAvailableMonthlys( accountId, monthly, calendarId, employeeContractSBDTO, tempStartRule, tempStartRuleRemark, tempEndRule, tempEndRuleRemark, socialBenefitHeaderVO.getMakeup(), socialBenefitHeaderVO.getMakeupMonth(), socialBenefitHeaderVO.getMakeupCrossYear() );
   }

   private List< String > getAvailableMonthlys( final String accountId, final String monthly, final String calendarId, final EmployeeContractSBDTO employeeContractSBDTO,
         final SocialBenefitHeaderVO socialBenefitHeaderVO, final SocialBenefitDetailVO socialBenefitDetailVO, final SocialBenefitSolutionDTO socialBenefitSolutionDTO )
         throws KANException
   {
      // 初始化Start Rule
      String tempStartRule = socialBenefitSolutionDTO.getSBStartRuleByItemId( socialBenefitDetailVO.getItemId() );

      if ( KANUtil.filterEmpty( tempStartRule, "0" ) == null )
      {
         tempStartRule = socialBenefitSolutionDTO.getSBStartRule();
      }

      if ( KANUtil.filterEmpty( tempStartRule, "0" ) == null )
      {
         tempStartRule = socialBenefitDetailVO.getStartRule();
      }

      if ( KANUtil.filterEmpty( tempStartRule, "0" ) == null )
      {
         tempStartRule = socialBenefitHeaderVO.getStartRule();
      }

      // 初始化Start Rule Remark
      String tempStartRuleRemark = socialBenefitSolutionDTO.getSBStartRuleRemarkByItemId( socialBenefitDetailVO.getItemId() );

      if ( KANUtil.filterEmpty( tempStartRuleRemark, "0" ) == null )
      {
         tempStartRuleRemark = socialBenefitSolutionDTO.getSBStartRuleRemark();
      }

      if ( KANUtil.filterEmpty( tempStartRuleRemark, "0" ) == null )
      {
         tempStartRuleRemark = socialBenefitDetailVO.getStartRuleRemark();
      }

      if ( KANUtil.filterEmpty( tempStartRuleRemark, "0" ) == null )
      {
         tempStartRuleRemark = socialBenefitHeaderVO.getStartRuleRemark();
      }

      // 初始化End Rule
      String tempEndRule = socialBenefitSolutionDTO.getSBEndRuleByItemId( socialBenefitDetailVO.getItemId() );

      if ( KANUtil.filterEmpty( tempEndRule, "0" ) == null )
      {
         tempEndRule = socialBenefitSolutionDTO.getSBEndRule();
      }

      if ( KANUtil.filterEmpty( tempEndRule, "0" ) == null )
      {
         tempEndRule = socialBenefitDetailVO.getEndRule();
      }

      if ( KANUtil.filterEmpty( tempEndRule, "0" ) == null )
      {
         tempEndRule = socialBenefitHeaderVO.getEndRule();
      }

      // 初始化End Rule Remark
      String tempEndRuleRemark = socialBenefitSolutionDTO.getSBEndRuleRemarkByItemId( socialBenefitDetailVO.getItemId() );

      if ( KANUtil.filterEmpty( tempEndRuleRemark, "0" ) == null )
      {
         tempEndRuleRemark = socialBenefitSolutionDTO.getSBEndRuleRemark();
      }

      if ( KANUtil.filterEmpty( tempEndRuleRemark, "0" ) == null )
      {
         tempEndRuleRemark = socialBenefitDetailVO.getEndRuleRemark();
      }

      if ( KANUtil.filterEmpty( tempEndRuleRemark, "0" ) == null )
      {
         tempEndRuleRemark = socialBenefitHeaderVO.getEndRuleRemark();
      }

      return getAvailableMonthlys( accountId, monthly, calendarId, employeeContractSBDTO, tempStartRule, tempStartRuleRemark, tempEndRule, tempEndRuleRemark, socialBenefitDetailVO.getMakeup(), socialBenefitDetailVO.getMakeupMonth(), socialBenefitDetailVO.getMakeupCrossYear() );
   }

   private List< String > getAvailableMonthlys( final String accountId, final String monthly, final String calendarId, final EmployeeContractSBDTO employeeContractSBDTO,
         final String startRule, final String startRuleRemark, final String endRule, final String endRuleRemark, final String makeup, final String makeupMonth,
         final String makeupCrossYear ) throws KANException
   {
      // 初始化Monthly List
      final List< String > monthlys = new ArrayList< String >();

      // 初始化社保开始日期，社保结束日期，社保状态
      String sbStartDate = null;
      String sbEndDate = null;
      String sbStatus = null;
      if ( employeeContractSBDTO != null )
      {
         sbStartDate = employeeContractSBDTO.getSBStartDate();
         sbEndDate = employeeContractSBDTO.getSBEndDate();
         sbStatus = employeeContractSBDTO.getSBStatus();
      }

      // 初始化操作年和操作月
      int monthlyYear = 0;
      int monthlyMonth = 0;
      if ( monthly != null && monthly.contains( "/" ) )
      {
         monthlyYear = Integer.valueOf( monthly.split( "/" )[ 0 ] );
         monthlyMonth = Integer.valueOf( monthly.split( "/" )[ 1 ] );
      }

      // 截取年月日 - Start
      int startYear = 0;
      int startMonth = 0;
      int startDay = 0;
      if ( sbStartDate != null && ( sbStartDate.contains( "/" ) || sbStartDate.contains( "-" ) ) )
      {
         startYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( sbStartDate ) ) );
         startMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( sbStartDate ) ) );
         startDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( sbStartDate ) ) );
      }

      // 截取年月日 - End
      int endYear = 0;
      int endMonth = 0;
      int endDay = 0;
      if ( sbEndDate != null && ( sbEndDate.contains( "/" ) || sbEndDate.contains( "-" ) ) )
      {
         endYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( sbEndDate ) ) );
         endMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( sbEndDate ) ) );
         endDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( sbEndDate ) ) );
      }

      // 规则日期
      int startRuleDay = 0;
      int endRuleDay = 0;
      if ( startRuleRemark != null && startRuleRemark.matches( "[0-9]+" ) )
      {
         startRuleDay = Integer.valueOf( startRuleRemark );
      }
      if ( endRuleRemark != null && endRuleRemark.matches( "[0-9]+" ) )
      {
         endRuleDay = Integer.valueOf( endRuleRemark );
      }

      // 初始化社保起缴日期
      Calendar startCalendar = KANUtil.createCalendar( sbStartDate );

      // 补缴设定
      if ( makeup != null )
         // 如果可以补缴
         if ( makeup.trim().equals( "1" ) )
         {
            // 如果不可以跨年补缴
            if ( makeupCrossYear != null && makeupCrossYear.trim().equals( "2" ) )
            {
               if ( monthlyYear > startYear )
               {
                  startYear = monthlyYear;
                  startMonth = 1;
                  startDay = 1;
               }
            }

            // 如果可补缴月份不为空
            if ( makeupMonth != null && !makeupMonth.trim().equals( "" ) && !makeupMonth.trim().equals( "0" ) )
            {
               // 如果需要补缴的月份大于可补缴月份
               if ( ( monthlyYear * 12 + monthlyMonth ) - ( startYear * 12 + startMonth ) > Integer.valueOf( makeupMonth ) )
               {
                  // 如果年份相同
                  if ( monthlyYear == startYear )
                  {
                     startMonth = monthlyMonth - Integer.valueOf( makeupMonth );
                     startDay = 1;
                  }
                  // 如果补缴年份超前
                  else if ( monthlyYear > startYear )
                  {
                     int temp = monthlyYear * 12 + monthlyMonth - Integer.valueOf( makeupMonth );
                     startYear = temp / 12;
                     startMonth = temp % 12;
                     startDay = 1;
                  }
               }
            }
         }
         // 如果不能补缴
         else if ( makeup.trim().equals( "2" ) )
         {
            startYear = monthlyYear;
            startMonth = monthlyMonth;

            // 初始化Calendar - 按照Monthly
            final Calendar tempStartCalendar = KANUtil.getFirstCalendar( monthly );

            if ( KANUtil.getDays( tempStartCalendar ) > KANUtil.getDays( startCalendar ) )
            {
               startCalendar = tempStartCalendar;
               startDay = 1;
            }
         }

      // 待申报加保
      if ( sbStatus.trim().equals( "2" ) )
      {
         // 如果未设加保规则，退出循环
         if ( KANUtil.filterEmpty( startRule, "0" ) == null || KANUtil.filterEmpty( startRuleRemark ) == null )
         {
            return monthlys;
         }

         if ( startYear * 12 + startMonth <= monthlyYear * 12 + monthlyMonth )
         {
            for ( int i = 0; i <= ( monthlyYear * 12 + monthlyMonth ) - ( startYear * 12 + startMonth ); i++ )
            {
               final int year = startYear + ( startMonth + i ) / 12 - ( ( startMonth + i ) % 12 == 0 ? 1 : 0 );
               final int month = ( startMonth + i ) % 12 == 0 ? 12 : ( startMonth + i ) % 12;

               final String dynamicMonthly = year + "/" + ( month > 9 ? month : "0" + month );

               if ( i == 0 )
               {
                  // 缴纳规则 - 按日期
                  if ( startRule != null && startRule.trim().equals( "1" ) )
                  {
                     if ( startDay != 0 && startRuleDay != 0 && startDay <= startRuleDay )
                     {
                        monthlys.add( dynamicMonthly + "_1" );
                     }
                  }
                  // 缴纳规则 - 按工作日天数
                  else if ( startRule != null && startRule.trim().equals( "2" ) )
                  {
                     int workDays = getWorkDays( accountId, calendarId, dynamicMonthly, startCalendar.getTime(), false );

                     if ( workDays != 0 && startRuleDay != 0 && workDays >= startRuleDay )
                     {
                        monthlys.add( dynamicMonthly + "_1" );
                     }
                  }
                  // 缴纳规则 - 按自然月天数
                  else if ( startRule != null && startRule.trim().equals( "3" ) )
                  {
                     // 操作当月天数
                     int monthDays = Integer.valueOf( KANUtil.getDay( KANUtil.getLastDate( dynamicMonthly ) ) );

                     if ( startDay != 0 && startRuleDay != 0 && monthDays - startDay + 1 >= startRuleDay )
                     {
                        monthlys.add( dynamicMonthly + "_1" );
                     }
                  }
               }
               else
               {
                  monthlys.add( dynamicMonthly + "_1" );
               }
            }
         }
      }

      // 正常缴纳
      if ( sbStatus.trim().equals( "3" ) )
      {
         // 如果未设加保规则，退出循环
         if ( KANUtil.filterEmpty( startRule, "0" ) == null || KANUtil.filterEmpty( startRuleRemark ) == null )
         {
            return monthlys;
         }

         // 缴纳规则 - 按日期
         if ( startRule != null && startRule.trim().equals( "1" ) )
         {
            boolean matchByDate = true;

            if ( startDay != 0 && startRuleDay != 0 && startYear == monthlyYear && startMonth == monthlyMonth && startDay > startRuleDay )
            {
               matchByDate = false;
            }

            if ( endDay != 0 && endRuleDay != 0 && endYear == monthlyYear && endMonth == monthlyMonth && endDay < endRuleDay )
            {
               matchByDate = false;
            }

            if ( matchByDate )
            {
               monthlys.add( monthly + "_1" );
            }
         }
         // 缴纳规则 - 按工作日天数
         else if ( startRule != null && startRule.trim().equals( "2" ) )
         {
            int workDays = getWorkDays( accountId, calendarId, monthly, startCalendar.getTime(), false );

            if ( workDays != 0 && startRuleDay != 0 && workDays >= startRuleDay )
            {
               monthlys.add( monthly + "_1" );
            }
         }
         // 缴纳规则 - 按自然月天数
         else if ( startRule != null && startRule.trim().equals( "3" ) )
         {
            boolean matchByMonthDays = true;

            // 操作当月天数
            int monthDays = Integer.valueOf( KANUtil.getDay( KANUtil.getLastDate( monthly ) ) );

            if ( startDay != 0 && startRuleDay != 0 && startYear == monthlyYear && startMonth == monthlyMonth && monthDays - startDay + 1 < startRuleDay )
            {
               matchByMonthDays = false;
            }

            if ( endDay != 0 && endRuleDay != 0 && endYear == monthlyYear && endMonth == monthlyMonth && endDay < endRuleDay )
            {
               matchByMonthDays = false;
            }

            if ( matchByMonthDays )
            {
               monthlys.add( monthly + "_1" );
            }
         }
      }

      // 待申报退保
      if ( sbStatus.trim().equals( "5" ) )
      {
         // 如果未设退保规则，退出循环
         if ( KANUtil.filterEmpty( endRule, "0" ) == null || KANUtil.filterEmpty( endRuleRemark ) == null )
         {
            return monthlys;
         }

         // 视作正常缴纳
         if ( endYear * 12 + endMonth > monthlyYear * 12 + monthlyMonth )
         {
            monthlys.add( monthly + "_1" );
         }
         else
         {
            // 加保日期，退保日期相同的情况不产生费用
            if ( KANUtil.filterEmpty( sbStartDate ) != null && KANUtil.filterEmpty( sbEndDate ) != null
                  && KANUtil.filterEmpty( sbStartDate ).equals( KANUtil.filterEmpty( sbEndDate ) ) )
            {
               monthlys.add( monthly + "_0" );
            }
            else
            {
               // 缴纳规则 - 按日期、按自然月天数
               if ( endRule != null && ( endRule.trim().equals( "1" ) || endRule.trim().equals( "3" ) ) )
               {
                  // 正常退保 - 产生费用
                  if ( endDay != 0 && endRuleDay != 0 && endDay >= endRuleDay )
                  {
                     monthlys.add( monthly + "_1" );
                  }
                  // 正常退保 - 不产生费用
                  else
                  {
                     monthlys.add( monthly + "_0" );
                  }
               }
               // 缴纳规则 - 工作日天数
               else if ( endRule != null && endRule.trim().equals( "2" ) )
               {
                  int workDays = getWorkDays( accountId, calendarId, monthly, KANUtil.createDate( sbEndDate ), true );

                  // 正常退保 - 产生费用
                  if ( workDays != 0 && endRuleDay != 0 && workDays >= endRuleDay )
                  {
                     monthlys.add( monthly + "_1" );
                  }
                  // 正常退保 - 不产生费用
                  else
                  {
                     monthlys.add( monthly + "_0" );
                  }
               }
            }
         }
      }

      return monthlys;
   }

   private int getWorkDays( final String accountId, final String calendarId, final String monthly, final Date targetDate, final boolean fromBegin ) throws KANException
   {
      int workDays = 0;

      if ( calendarId != null && !calendarId.trim().equals( "" ) && !calendarId.trim().equals( "0" ) && monthly != null && !monthly.trim().equals( "" ) )
      {
         // 初始化CalendarDTO
         final CalendarDTO calendarDTO = KANConstants.getKANAccountConstants( accountId ).getCalendarDTOByHeaderId( calendarId );
         final List< CalendarDetailVO > calendarDetailVOs = calendarDTO.getCalendarDetailVOsByMonthly( monthly );

         // 初始化正向和反向Calendar
         final Calendar startCalendar = KANUtil.getFirstCalendar( monthly );
         final Calendar endCalendar = KANUtil.getLastCalendar( monthly );

         // 从头开始
         if ( fromBegin )
         {
            // 循环直到大于Target Date
            while ( startCalendar.get( Calendar.DAY_OF_MONTH ) <= Integer.valueOf( KANUtil.getDay( targetDate ) ) )
            {
               // 标识 - 是否有日历设置
               boolean isSet = false;

               // 检查是否有特殊设定
               if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
               {
                  for ( CalendarDetailVO calendarDetailVO : calendarDetailVOs )
                  {
                     if ( Integer.valueOf( KANUtil.getDay( KANUtil.createDate( calendarDetailVO.getDay() ) ) ) == startCalendar.get( Calendar.DAY_OF_MONTH ) )
                     {
                        // 如果日历设置是工作日
                        if ( calendarDetailVO.getDayType().equals( "1" ) )
                        {
                           workDays++;
                        }
                        // 标记为存在特殊设置
                        isSet = true;
                        break;
                     }
                  }
               }

               // 如果日历未设置
               if ( !isSet )
               {
                  // 不是周六、周日，则归为工作日
                  if ( !( startCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY || startCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) )
                  {
                     workDays++;
                  }
               }

               // 遍历到相等，跳出
               if ( endCalendar.get( Calendar.DAY_OF_MONTH ) == Integer.valueOf( KANUtil.getDay( targetDate ) ) )
               {
                  break;
               }

               startCalendar.add( Calendar.DAY_OF_MONTH, 1 );
            }
         }
         else
         {
            // 循环直到小于Target Date
            while ( endCalendar.get( Calendar.DAY_OF_MONTH ) >= Integer.valueOf( KANUtil.getDay( targetDate ) ) )
            {
               // 标识 - 是否有日历设置
               boolean isSet = false;

               // 检查是否有特殊设定
               if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
               {
                  for ( CalendarDetailVO calendarDetailVO : calendarDetailVOs )
                  {
                     if ( Integer.valueOf( KANUtil.getDay( KANUtil.createDate( calendarDetailVO.getDay() ) ) ) == endCalendar.get( Calendar.DAY_OF_MONTH ) )
                     {
                        // 如果日历设置是工作日
                        if ( calendarDetailVO.getDayType().equals( "1" ) )
                        {
                           workDays++;
                        }
                        // 标记为存在特殊设置
                        isSet = true;
                        break;
                     }
                  }
               }

               // 如果日历未设置
               if ( !isSet )
               {
                  // 不是周六、周日，则归为工作日
                  if ( !( endCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY || endCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) )
                  {
                     workDays++;
                  }
               }

               // 遍历到相等，跳出
               if ( endCalendar.get( Calendar.DAY_OF_MONTH ) == Integer.valueOf( KANUtil.getDay( targetDate ) ) )
               {
                  break;
               }

               endCalendar.add( Calendar.DAY_OF_MONTH, -1 );
            }
         }
      }

      return workDays;
   }

   private double getDiscount( final String monthly, final String cbStartDate, final String cdEndDate, final String cbStatus )
   {
      // 初始化Discount
      double discount = 0;

      // 初始化操作年和操作月
      int monthlyYear = 0;
      int monthlyMonth = 0;
      if ( monthly != null && monthly.contains( "/" ) )
      {
         monthlyYear = Integer.valueOf( monthly.split( "/" )[ 0 ] );
         monthlyMonth = Integer.valueOf( monthly.split( "/" )[ 1 ] );
      }

      // 按照Monthly初始化Calendar，默认设为当月最后一天
      final Calendar calendar = KANUtil.getLastCalendar( monthly );

      // 截取年月日 - Start
      int startYear = 0;
      int startMonth = 0;
      int startDay = 0;
      if ( cbStartDate != null && ( cbStartDate.contains( "/" ) || cbStartDate.contains( "-" ) ) )
      {
         startYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( cbStartDate ) ) );
         startMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( cbStartDate ) ) );
         startDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( cbStartDate ) ) );
      }

      // 截取年月日 - End
      int endYear = 0;
      int endMonth = 0;
      int endDay = 0;
      if ( cdEndDate != null && ( cdEndDate.contains( "/" ) || cdEndDate.contains( "-" ) ) )
      {
         endYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( cdEndDate ) ) );
         endMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( cdEndDate ) ) );
         endDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( cdEndDate ) ) );
      }

      // 待申购
      if ( cbStatus.trim().equals( "2" ) )
      {
         // 加保月份 < 当前操作月
         if ( ( monthlyYear * 12 + monthlyMonth ) - ( startYear * 12 + startMonth ) > 0 )
         {
            discount = 1;
         }
         // 加保月份 = 当前操作月
         else if ( ( monthlyYear * 12 + monthlyMonth ) - ( startYear * 12 + startMonth ) == 0 )
         {
            // 全月
            if ( startDay == 1 )
            {
               discount = 1;
            }
            // 非全月
            else
            {
               discount = Double.valueOf( calendar.get( Calendar.DAY_OF_MONTH ) - startDay + 1 ) / Double.valueOf( calendar.get( Calendar.DAY_OF_MONTH ) );
            }
         }
      }
      // 正常购买
      else if ( cbStatus.trim().equals( "3" ) )
      {
         discount = 1;
      }
      // 待退购
      else if ( cbStatus.trim().equals( "5" ) )
      {
         // 退购月份晚于商保月份
         if ( ( monthlyYear * 12 + monthlyMonth ) < ( endYear * 12 + endMonth ) )
         {
            discount = 1;
         }
         // 退购月份等于商保月份
         else if ( ( monthlyYear * 12 + monthlyMonth ) == ( endYear * 12 + endMonth ) )
         {
            // 申购日期，退购日期相同的情况不产生费用
            if ( startDay == endDay )
            {
               discount = 0;
            }
            // 全月
            else if ( endDay == calendar.get( Calendar.DAY_OF_MONTH ) )
            {
               discount = 1;
            }
            // 非全月
            else
            {
               discount = Double.valueOf( endDay ) / Double.valueOf( calendar.get( Calendar.DAY_OF_MONTH ) );
            }
         }
      }

      return discount;
   }

   private ClientOrderSBVO getClientOrderSBVOBySolutionId( final String solutionId )
   {
      if ( this.getClientOrderSBVOs() != null && this.getClientOrderSBVOs().size() > 0 )
      {
         for ( ClientOrderSBVO clientOrderSBVO : this.getClientOrderSBVOs() )
         {
            if ( clientOrderSBVO.getSbSolutionId() != null && clientOrderSBVO.getSbSolutionId().trim().equals( solutionId ) )
            {
               return clientOrderSBVO;
            }
         }
      }

      return null;
   }

   private ClientOrderCBVO getClientOrderCBVOBySolutionId( final String solutionId )
   {
      if ( this.getClientOrderCBVOs() != null && this.getClientOrderCBVOs().size() > 0 )
      {
         for ( ClientOrderCBVO clientOrderCBVO : this.getClientOrderCBVOs() )
         {
            if ( clientOrderCBVO.getCbSolutionId() != null && clientOrderCBVO.getCbSolutionId().trim().equals( solutionId ) )
            {
               return clientOrderCBVO;
            }
         }
      }

      return null;
   }

   private List< EmployeeContractOtherVO > getEmployeeContractOtherVOsByItemType( final String itemType ) throws KANException
   {
      final List< EmployeeContractOtherVO > employeeContractOtherVOs = new ArrayList< EmployeeContractOtherVO >();

      if ( this.getEmployeeContractOtherVOs() != null && this.getEmployeeContractOtherVOs().size() > 0 )
      {
         for ( EmployeeContractOtherVO employeeContractOtherVO : this.getEmployeeContractOtherVOs() )
         {
            // 初始化ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( this.getEmployeeContractVO().getAccountId() ).getItemVOByItemId( employeeContractOtherVO.getItemId() );

            if ( itemVO.getItemType().trim().equals( itemType ) )
            {
               employeeContractOtherVOs.add( employeeContractOtherVO );
            }
         }
      }

      return employeeContractOtherVOs;
   }

   public List< EmployeeContractOtherVO > getOtherFeeEmployeeContractOtherVOs() throws KANException
   {
      return getEmployeeContractOtherVOsByItemType( "10" );
   }

   public List< EmployeeContractOtherVO > getThirdPartCostEmployeeContractOtherVOs() throws KANException
   {
      return getEmployeeContractOtherVOsByItemType( "11" );
   }

   public List< EmployeeContractOtherVO > getFundingEmployeeContractOtherVOs() throws KANException
   {
      return getEmployeeContractOtherVOsByItemType( "12" );
   }

   public void addSalaryDTOs( final List< SalaryDTO > salaryDTOs )
   {
      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         for ( SalaryDTO salaryDTO : salaryDTOs )
         {
            if ( this.getSalaryDTOs() == null || this.getSalaryDTOs().size() == 0 )
            {
               this.getSalaryDTOs().add( salaryDTO );
            }
            else
            {
               boolean findFlag = false;

               for ( SalaryDTO tempSalaryDTO : this.getSalaryDTOs() )
               {
                  if ( salaryDTO.getSalaryHeaderVO() != null && tempSalaryDTO.getSalaryHeaderVO() != null
                        && salaryDTO.getSalaryHeaderVO().getSalaryHeaderId().equals( tempSalaryDTO.getSalaryHeaderVO().getSalaryHeaderId() ) )
                  {
                     tempSalaryDTO.getSalaryDetailVOs().addAll( salaryDTO.getSalaryDetailVOs() );

                     findFlag = true;
                  }
               }

               if ( !findFlag )
               {
                  this.getSalaryDTOs().add( salaryDTO );
               }
            }
         }
      }
   }

   public void addTaxAmountPersonal( final String taxAmountPersonal )
   {
      if ( KANUtil.filterEmpty( taxAmountPersonal ) == null || Double.valueOf( taxAmountPersonal ) == 0 )
      {
         return;
      }

      if ( KANUtil.filterEmpty( this.taxAmountPersonal ) == null )
      {
         this.taxAmountPersonal = taxAmountPersonal;
      }
      else
      {
         this.taxAmountPersonal = String.valueOf( Double.valueOf( this.taxAmountPersonal ) + Double.valueOf( taxAmountPersonal ) );
      }
   }

   public void addAddtionalBillAmountPersonal( final String addtionalBillAmountPersonal )
   {
      if ( KANUtil.filterEmpty( addtionalBillAmountPersonal ) == null || Double.valueOf( addtionalBillAmountPersonal ) == 0 )
      {
         return;
      }

      if ( KANUtil.filterEmpty( this.addtionalBillAmountPersonal ) == null )
      {
         this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
      }
      else
      {
         this.addtionalBillAmountPersonal = String.valueOf( Double.valueOf( this.addtionalBillAmountPersonal ) + Double.valueOf( addtionalBillAmountPersonal ) );
      }
   }

   public String getFirstDayOfYearCircle()
   {
      return firstDayOfYearCircle;
   }

   public void setFirstDayOfYearCircle( String firstDayOfYearCircle )
   {
      this.firstDayOfYearCircle = firstDayOfYearCircle;
   }

   public String getLastDayOfYearCircle()
   {
      return lastDayOfYearCircle;
   }

   public void setLastDayOfYearCircle( String lastDayOfYearCircle )
   {
      this.lastDayOfYearCircle = lastDayOfYearCircle;
   }

   public String getTotalWorkHoursOfYear()
   {
      return totalWorkHoursOfYear;
   }

   public void setTotalWorkHoursOfYear( String totalWorkHoursOfYear )
   {
      this.totalWorkHoursOfYear = totalWorkHoursOfYear;
   }

   public String getTotalFullHoursOfYear()
   {
      return totalFullHoursOfYear;
   }

   public void setTotalFullHoursOfYear( String totalFullHoursOfYear )
   {
      this.totalFullHoursOfYear = totalFullHoursOfYear;
   }

}

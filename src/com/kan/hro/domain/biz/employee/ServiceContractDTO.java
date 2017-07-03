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

   // ���Logger����
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

   // ���浱ǰMonthly������ItemIds
   private List< String > existItemIds = new ArrayList< String >();

   // ��������Э���Ӧ���ѽ���ĸ�˰
   private String taxAmountPersonal;

   // ��������Э���Ӧ���ѽ����˰ǰ����
   private String addtionalBillAmountPersonal;

   // ��ʼ���²�ȫ��ȡ��ǰ��ͬ��ʼ���ڣ��������²�ȫ��ȡ����˾���ڸ�1/1�Ľϴ�ֵ��������ȫ�ľ�ȡ����˾���ڸ�1/1�Ľϴ�ֵ��
   private String firstDayOfYearCircle;

   // ȡ�������ڣ��迼����ְ���ڣ���12/31�Ľ�Сֵ
   private String lastDayOfYearCircle;

   //ȫ�깤��Сʱ��
   private String totalWorkHoursOfYear;

   //ȫ��ȫ��Сʱ��
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

   // ����ItemId��ȡEmployeeContractOTVO����
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

   // �籣����
   public boolean calculateSB( final HttpServletRequest request ) throws KANException
   {
      if ( this.getEmployeeContractSBDTOs() != null && this.getEmployeeContractSBDTOs().size() > 0 )
      {
         // �����籣��������������
         for ( EmployeeContractSBDTO employeeContractSBDTO : this.getEmployeeContractSBDTOs() )
         {
            logger.info( "SB Calculate Start - " + this.getEmployeeContractVO().getContractId() + " / " + this.getEmployeeVO().getNameZH() );

            // ��ʼ��EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBDTO.getEmployeeContractSBVO();

            // ��ʼ��Monthly List
            final List< String > monthlys = new ArrayList< String >();

            // �����籣����
            final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( this.getEmployeeContractVO().getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );

            // ����籣����������
            if ( socialBenefitSolutionDTO == null || socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() == null
                  || socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() == null || socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs().size() == 0 )
            {
               continue;
            }

            // ����ϵͳ�籣
            final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSysHeaderId() );

            // ���ϵͳ�籣������
            if ( socialBenefitDTO == null || socialBenefitDTO.getSocialBenefitHeaderVO() == null || socialBenefitDTO.getSocialBenefitDetailVOs() == null
                  || socialBenefitDTO.getSocialBenefitDetailVOs().size() == 0 )
            {
               continue;
            }

            // ��ʼ��ϵͳ�籣�е��б�����
            socialBenefitDTO.getSocialBenefitHeaderVO().reset( null, request );

            String calendarId = this.getEmployeeContractVO().getCalendarId();
            if ( calendarId == null || calendarId.trim().equals( "" ) || calendarId.trim().equals( "0" ) )
            {
               calendarId = this.getClientOrderHeaderVO().getCalendarId();
            }

            // ��ʼ���籣���˲��ֹ�˾�е�
            String personalSBBurden = employeeContractSBVO.getPersonalSBBurden();

            // ���ԴӶ����籣�����л�ȡ
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               // ��ȡClientOrderSBVO
               final ClientOrderSBVO clientOrderSBVO = getClientOrderSBVOBySolutionId( employeeContractSBVO.getSbSolutionId() );

               if ( clientOrderSBVO != null )
               {
                  personalSBBurden = clientOrderSBVO.getPersonalSBBurden();
               }
            }

            // ���ԴӶ����л�ȡ
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               personalSBBurden = clientOrderHeaderVO.getPersonalSBBurden();
            }

            // ���Դ��籣�����л�ȡ
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               personalSBBurden = socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getPersonalSBBurden();
            }

            // ����������ɵ��·�
            monthlys.addAll( getAvailableMonthlys( this.getEmployeeContractVO().getAccountId(), this.getEmployeeContractVO().getMonthly(), calendarId, employeeContractSBDTO, socialBenefitDTO.getSocialBenefitHeaderVO(), socialBenefitSolutionDTO ) );

            if ( monthlys != null && monthlys.size() > 0 )
            {
               for ( String monthly : monthlys )
               {
                  // ��ʼ��SBDTO
                  final SBDTO sbDTO = new SBDTO();

                  final String dynamicMonthly = monthly.split( "_" )[ 0 ];
                  String calculateFlag = monthly.split( "_" )[ 1 ];

                  /** �����籣̨�� */
                  final SBHeaderVO sbHeaderVO = new SBHeaderVO();
                  // �˻���Ϣ
                  sbHeaderVO.setAccountId( this.getEmployeeContractVO().getAccountId() );
                  // ������Ϣ
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
                  // ��Ա�籣ID�����籣����ID
                  sbHeaderVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                  sbHeaderVO.setEmployeeSBNameZH( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() );
                  sbHeaderVO.setEmployeeSBNameEN( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameEN() );
                  sbHeaderVO.setCityId( socialBenefitDTO.getSocialBenefitHeaderVO().getCityId() );

                  // �����ǰ�籣��Ҫʹ�ù�Ӧ��
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
                  // ��Ա״̬ - ����Э���еĹ�Ա״̬
                  sbHeaderVO.setEmployStatus( this.getEmployeeContractVO().getEmployStatus() );
                  // �籣״̬ - ��ʷ����Ϊ����
                  sbHeaderVO.setSbStatus( dynamicMonthly.equals( this.getEmployeeContractVO().getMonthly() ) ? employeeContractSBVO.getStatus() : "7" );
                  // �������
                  sbHeaderVO.setStartDate( employeeContractSBVO.getStartDate() );
                  // �˽�����
                  sbHeaderVO.setEndDate( employeeContractSBVO.getEndDate() );
                  // ��ְ���� - ����Э�鿪ʼʱ��
                  sbHeaderVO.setOnboardDate( this.getEmployeeContractVO().getStartDate() );
                  // ��ְ����
                  sbHeaderVO.setResignDate( this.getEmployeeContractVO().getResignDate() );
                  // �籣������ - �������º������²�ͬ
                  sbHeaderVO.setMonthly( this.getEmployeeContractVO().getMonthly() );
                  // ����״̬ - �������ɵ��籣Ĭ��Ϊ��������
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
                        // ��ȡ��ǰ�籣��Ŀ
                        final ItemVO itemVO = KANConstants.getItemVOByItemId( socialBenefitSolutionDetailVO.getItemId() );

                        // ��ȡ��ǰ�籣��ϸ��ϵͳ��
                        final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDTO.getSocialBenefitDetailVOByItemId( socialBenefitSolutionDetailVO.getItemId() );

                        // ��õ�ǰ�籣����
                        final EmployeeContractSBDetailVO employeeContractSBDetailVO = employeeContractSBDTO.getEmployeeContractSBDetailVOBySolutionDetailId( socialBenefitSolutionDetailVO.getDetailId() );

                        // �����ǰ�籣��Ŀ���籣��ϸ��ϵͳ��ΪNull��������һ��ѭ��
                        if ( itemVO == null || socialBenefitDetailVO == null )
                        {
                           continue;
                        }

                        // ��ʼ��ϵͳ�籣��ϸ�е��б�����
                        socialBenefitDetailVO.reset( null, request );

                        // ���ؿ�Ŀ������ɵ��·�
                        final List< String > itemMonthlys = getAvailableMonthlys( this.getEmployeeContractVO().getAccountId(), this.getEmployeeContractVO().getMonthly(), calendarId, employeeContractSBDTO, socialBenefitDTO.getSocialBenefitHeaderVO(), socialBenefitDetailVO, socialBenefitSolutionDTO );

                        // �����Ŀ��ϸ����������ɣ�������һ��ѭ��
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

                        // ��ʼ�����˺͹�˾�Ļ��� - Ĭ��ȡ�籣����
                        String basePersonal = socialBenefitSolutionDetailVO.getPersonalFloor();
                        String baseCompany = socialBenefitSolutionDetailVO.getCompanyFloor();

                        // �����籣����
                        if ( employeeContractSBDetailVO != null )
                        {
                           // ���ø��˻���
                           if ( employeeContractSBDetailVO.getBasePersonal() != null && !employeeContractSBDetailVO.getBasePersonal().trim().equals( "" ) )
                           {
                              basePersonal = employeeContractSBDetailVO.getBasePersonal();
                           }
                           // ���ù�˾����
                           if ( employeeContractSBDetailVO.getBaseCompany() != null && !employeeContractSBDetailVO.getBaseCompany().trim().equals( "" ) )
                           {
                              baseCompany = employeeContractSBDetailVO.getBaseCompany();
                           }
                        }

                        /** �����籣̨����ϸ */
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

                        // ��Ŀ���˲���
                        double amountPersonal = Double.valueOf( sbDetailVO.getBasePersonal() ) * Double.valueOf( sbDetailVO.getRatePersonal() ) / 100
                              + Double.valueOf( sbDetailVO.getFixPersonal() );
                        // ��Ŀ��˾����
                        double amountCompany = Double.valueOf( sbDetailVO.getBaseCompany() ) * Double.valueOf( sbDetailVO.getRateCompany() ) / 100
                              + Double.valueOf( sbDetailVO.getFixCompany() );

                        // ���н��Ϊ��0����������һ��ѭ��
                        if ( amountPersonal == 0 && amountCompany == 0 )
                        {
                           continue;
                        }

                        // ��ʼ���ϼ��ַ���
                        String amountPersonalString = String.valueOf( amountPersonal );
                        String amountCompanyString = String.valueOf( amountCompany );

                        // ��ʼ�����ȼ���ȡ ��˾����
                        String companyAccuracy = socialBenefitDetailVO.getDecodeCompanyAccuracyTemp();

                        // ��ʼ�����ȼ���ȡ ���˲���
                        String personalAccuracy = socialBenefitDetailVO.getDecodePersonalAccuracyTemp();
                        
                        String round = socialBenefitDetailVO.getRound();

                        // �����ϸû�����þ��ȣ���ȡ����
                        if ( KANUtil.filterEmpty( companyAccuracy ) == null )
                        {
                           companyAccuracy = socialBenefitDTO.getSocialBenefitHeaderVO().getDecodeCompanyAccuracyTemp();
                        }
                        // �����ϸû�����þ��ȣ���ȡ����
                        if ( KANUtil.filterEmpty( personalAccuracy ) == null )
                        {
                           personalAccuracy = socialBenefitDTO.getSocialBenefitHeaderVO().getDecodePersonalAccuracyTemp();
                        }

                        // �����ϸû�����ý�ȡ����ȡ����
                        if ( KANUtil.filterEmpty( round, "0" ) == null )
                        {
                           round = socialBenefitDTO.getSocialBenefitHeaderVO().getRound();
                        }

                        // С����ʽ��
                        if ( KANUtil.filterEmpty( personalAccuracy ) != null )
                        {
                           amountPersonalString = KANUtil.round( amountPersonal, Integer.valueOf( personalAccuracy ), round );
                        }
                        // С����ʽ��
                        if ( KANUtil.filterEmpty( companyAccuracy ) != null )
                        {
                           amountCompanyString = KANUtil.round( amountCompany, Integer.valueOf( companyAccuracy ), round );
                        }
                        // ��Ҫ��������
                        if ( calculateFlag != null && calculateFlag.trim().equals( "1" ) )
                        {
                           sbDetailVO.setAmountPersonal( amountPersonalString );
                           sbDetailVO.setAmountCompany( amountCompanyString );
                        }
                        // �����������
                        else
                        {
                           sbDetailVO.setAmountPersonal( "0" );
                           sbDetailVO.setAmountCompany( "0" );
                        }

                        sbDetailVO.setMonthly( this.getEmployeeContractVO().getMonthly() );

                        // ��ʼ��Attribute - �ӷ���Detailȡ
                        String attribute = socialBenefitSolutionDTO.getSBAttributeByItemId( socialBenefitDetailVO.getItemId() );

                        // �ӷ���Headerȡ��δȡ�������
                        if ( KANUtil.filterEmpty( attribute, "0" ) == null )
                        {
                           attribute = socialBenefitSolutionDTO.getSBAttribute();
                        }

                        // ������Detailȡ��δȡ�������
                        if ( KANUtil.filterEmpty( attribute, "0" ) == null )
                        {
                           attribute = socialBenefitDetailVO.getAttribute();
                        }

                        // ������Headerȡ��δȡ�������
                        if ( KANUtil.filterEmpty( attribute, "0" ) == null )
                        {
                           attribute = socialBenefitDTO.getSocialBenefitHeaderVO().getAttribute();
                        }

                        // ��ʼ��MonthlyGap
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

   // �̱�����
   public boolean calculateCB( final HttpServletRequest request ) throws KANException
   {
      if ( this.getEmployeeContractCBVOs() != null && this.getEmployeeContractCBVOs().size() > 0 )
      {
         // �����̱���������������
         for ( EmployeeContractCBVO employeeContractCBVO : this.getEmployeeContractCBVOs() )
         {
            // �����̱�����
            final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( this.getEmployeeContractVO().getAccountId() ).getCommercialBenefitSolutionDTOByHeaderId( employeeContractCBVO.getSolutionId() );

            // ����̱�����������
            if ( commercialBenefitSolutionDTO == null )
            {
               continue;
            }

            // �����̱����� - ���񶩵�
            final ClientOrderCBVO clientOrderCBVO = getClientOrderCBVOBySolutionId( employeeContractCBVO.getSolutionId() );

            // ��ʼ���̱������е��б�����
            commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().reset( null, request );

            // ��ȡ��ǰ��Ҫ������·�
            final String monthly = this.getEmployeeContractVO().getMonthly();

            // ��ʼ��CBDTO
            final CBDTO cbDTO = new CBDTO();

            /** �����̱�̨�� */
            final CBHeaderVO cbHeaderVO = new CBHeaderVO();
            // �˻���Ϣ
            cbHeaderVO.setAccountId( this.getEmployeeContractVO().getAccountId() );
            // ������Ϣ
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
            // ��Ա�̱�ID�����̱�����ID
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
            // ��Ա״̬ - ����Э���еĹ�Ա״̬
            cbHeaderVO.setEmployStatus( this.getEmployeeContractVO().getEmployStatus() );
            // �̱�״̬
            cbHeaderVO.setCbStatus( employeeContractCBVO.getStatus() );
            // �깺����
            cbHeaderVO.setStartDate( employeeContractCBVO.getStartDate() );
            // �˹�����
            cbHeaderVO.setEndDate( employeeContractCBVO.getEndDate() );
            // ��ְ���� - ����Э�鿪ʼʱ��
            cbHeaderVO.setOnboardDate( this.getEmployeeContractVO().getStartDate() );
            // ��ְ����
            cbHeaderVO.setResignDate( this.getEmployeeContractVO().getResignDate() );
            // �̱������� - �������º������²�ͬ
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
                  // ��ȡ��ǰ�̱���Ŀ
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( this.getEmployeeContractVO().getAccountId() ).getItemVOByItemId( commercialBenefitSolutionDetailVO.getItemId() );

                  // �����ǰ�籣��ĿΪ�գ�������һ��ѭ��
                  if ( itemVO == null )
                  {
                     continue;
                  }

                  // ��ʼ���̱�������ϸ�е��б�����
                  commercialBenefitSolutionDetailVO.reset( null, request );

                  /** �����̱�̨����ϸ */
                  final CBDetailVO cbDetailVO = new CBDetailVO();
                  cbDetailVO.setItemId( itemVO.getItemId() );
                  cbDetailVO.setItemNo( itemVO.getItemNo() );
                  cbDetailVO.setNameZH( itemVO.getNameZH() );
                  cbDetailVO.setNameEN( itemVO.getNameEN() );

                  // ��ʼ���̱����㷽ʽ
                  String calculateType = commercialBenefitSolutionDetailVO.getCalculateType();
                  if ( calculateType == null || calculateType.trim().equals( "" ) || calculateType.trim().equals( "0" ) )
                  {
                     calculateType = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getCalculateType();
                  }

                  // ��ʼ��Discount
                  double discount = 1;
                  // ��ʼ��TempDiscount
                  final double tempDiscount = getDiscount( monthly, employeeContractCBVO.getStartDate(), employeeContractCBVO.getEndDate(), employeeContractCBVO.getStatus() );
                  // ����̱��ǰ�����ȡ�ģ���Ҫ�����ۿ�
                  if ( calculateType != null && calculateType.trim().equals( "2" ) )
                  {
                     discount = tempDiscount;
                  }

                  // ��ȫ�µ����
                  if ( tempDiscount < 1 )
                  {
                     // ��ʼ��FreeShortOfMonth
                     String freeShortOfMonth = employeeContractCBVO.getFreeShortOfMonth();
                     if ( clientOrderCBVO != null && ( freeShortOfMonth == null || freeShortOfMonth.trim().equals( "0" ) ) )
                     {
                        freeShortOfMonth = clientOrderCBVO.getFreeShortOfMonth();
                     }
                     // ��ȫ�����շ����
                     if ( freeShortOfMonth != null && freeShortOfMonth.trim().equals( "1" ) )
                     {
                        discount = 0;
                     }

                     // ��ʼ��ChargeFullMonth
                     String chargeFullMonth = employeeContractCBVO.getChargeFullMonth();
                     if ( clientOrderCBVO != null && ( chargeFullMonth == null || chargeFullMonth.trim().equals( "0" ) ) )
                     {
                        chargeFullMonth = clientOrderCBVO.getChargeFullMonth();
                     }
                     // �����Ƿ�ȫ�¶���ȫ����
                     if ( chargeFullMonth != null && chargeFullMonth.trim().equals( "1" ) )
                     {
                        discount = 1;
                     }
                  }

                  // �ɹ��ɱ�
                  double amountPurchaseCost = Double.valueOf( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getPurchaseCost() ) != null ? commercialBenefitSolutionDetailVO.getPurchaseCost()
                        : "0" )
                        * discount;
                  // ���۳ɱ�
                  double amountSalesCost = Double.valueOf( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getSalesCost() ) != null ? commercialBenefitSolutionDetailVO.getSalesCost()
                        : "0" )
                        * discount;
                  // ���ۼ۸�
                  double amountSalesPrice = Double.valueOf( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getSalesPrice() ) != null ? commercialBenefitSolutionDetailVO.getSalesPrice()
                        : "0" )
                        * discount;

                  // ���н��Ϊ��0����������һ��ѭ��
                  if ( amountPurchaseCost == 0 && amountSalesCost == 0 && amountSalesPrice == 0 )
                  {
                     continue;
                  }

                  // ��ʼ���ϼ��ַ���
                  String amountPurchaseCostString = String.valueOf( amountPurchaseCost );
                  String amountSalesCostString = String.valueOf( amountSalesCost );
                  String amountSalesPriceString = String.valueOf( amountSalesPrice );

                  // ��ʼ�����ȼ���ȡ
                  String accuracy = commercialBenefitSolutionDetailVO.getDecodeAccuracyTemp();
                  String round = commercialBenefitSolutionDetailVO.getRound();

                  // �����ϸû�����þ��ȣ���ȡ����
                  if ( KANUtil.filterEmpty( accuracy ) == null )
                  {
                     accuracy = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getDecodeAccuracyTemp();
                  }

                  // �����ϸû�����ý�ȡ����ȡ����
                  if ( KANUtil.filterEmpty( round, "0" ) == null )
                  {
                     round = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getRound();
                  }

                  // С����ʽ��
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
      // ��ʼ��Start Rule
      String tempStartRule = socialBenefitSolutionDTO.getSBStartRule();

      if ( KANUtil.filterEmpty( tempStartRule, "0" ) == null )
      {
         tempStartRule = socialBenefitHeaderVO.getStartRule();
      }

      // ��ʼ��Start Rule Remark
      String tempStartRuleRemark = socialBenefitSolutionDTO.getSBStartRuleRemark();

      if ( KANUtil.filterEmpty( tempStartRuleRemark, "0" ) == null )
      {
         tempStartRuleRemark = socialBenefitHeaderVO.getStartRuleRemark();
      }

      // ��ʼ��End Rule
      String tempEndRule = socialBenefitSolutionDTO.getSBEndRule();

      if ( KANUtil.filterEmpty( tempEndRule, "0" ) == null )
      {
         tempEndRule = socialBenefitHeaderVO.getEndRule();
      }

      // ��ʼ��End Rule Remark
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
      // ��ʼ��Start Rule
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

      // ��ʼ��Start Rule Remark
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

      // ��ʼ��End Rule
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

      // ��ʼ��End Rule Remark
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
      // ��ʼ��Monthly List
      final List< String > monthlys = new ArrayList< String >();

      // ��ʼ���籣��ʼ���ڣ��籣�������ڣ��籣״̬
      String sbStartDate = null;
      String sbEndDate = null;
      String sbStatus = null;
      if ( employeeContractSBDTO != null )
      {
         sbStartDate = employeeContractSBDTO.getSBStartDate();
         sbEndDate = employeeContractSBDTO.getSBEndDate();
         sbStatus = employeeContractSBDTO.getSBStatus();
      }

      // ��ʼ��������Ͳ�����
      int monthlyYear = 0;
      int monthlyMonth = 0;
      if ( monthly != null && monthly.contains( "/" ) )
      {
         monthlyYear = Integer.valueOf( monthly.split( "/" )[ 0 ] );
         monthlyMonth = Integer.valueOf( monthly.split( "/" )[ 1 ] );
      }

      // ��ȡ������ - Start
      int startYear = 0;
      int startMonth = 0;
      int startDay = 0;
      if ( sbStartDate != null && ( sbStartDate.contains( "/" ) || sbStartDate.contains( "-" ) ) )
      {
         startYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( sbStartDate ) ) );
         startMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( sbStartDate ) ) );
         startDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( sbStartDate ) ) );
      }

      // ��ȡ������ - End
      int endYear = 0;
      int endMonth = 0;
      int endDay = 0;
      if ( sbEndDate != null && ( sbEndDate.contains( "/" ) || sbEndDate.contains( "-" ) ) )
      {
         endYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( sbEndDate ) ) );
         endMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( sbEndDate ) ) );
         endDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( sbEndDate ) ) );
      }

      // ��������
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

      // ��ʼ���籣�������
      Calendar startCalendar = KANUtil.createCalendar( sbStartDate );

      // �����趨
      if ( makeup != null )
         // ������Բ���
         if ( makeup.trim().equals( "1" ) )
         {
            // ��������Կ��겹��
            if ( makeupCrossYear != null && makeupCrossYear.trim().equals( "2" ) )
            {
               if ( monthlyYear > startYear )
               {
                  startYear = monthlyYear;
                  startMonth = 1;
                  startDay = 1;
               }
            }

            // ����ɲ����·ݲ�Ϊ��
            if ( makeupMonth != null && !makeupMonth.trim().equals( "" ) && !makeupMonth.trim().equals( "0" ) )
            {
               // �����Ҫ���ɵ��·ݴ��ڿɲ����·�
               if ( ( monthlyYear * 12 + monthlyMonth ) - ( startYear * 12 + startMonth ) > Integer.valueOf( makeupMonth ) )
               {
                  // ��������ͬ
                  if ( monthlyYear == startYear )
                  {
                     startMonth = monthlyMonth - Integer.valueOf( makeupMonth );
                     startDay = 1;
                  }
                  // ���������ݳ�ǰ
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
         // ������ܲ���
         else if ( makeup.trim().equals( "2" ) )
         {
            startYear = monthlyYear;
            startMonth = monthlyMonth;

            // ��ʼ��Calendar - ����Monthly
            final Calendar tempStartCalendar = KANUtil.getFirstCalendar( monthly );

            if ( KANUtil.getDays( tempStartCalendar ) > KANUtil.getDays( startCalendar ) )
            {
               startCalendar = tempStartCalendar;
               startDay = 1;
            }
         }

      // ���걨�ӱ�
      if ( sbStatus.trim().equals( "2" ) )
      {
         // ���δ��ӱ������˳�ѭ��
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
                  // ���ɹ��� - ������
                  if ( startRule != null && startRule.trim().equals( "1" ) )
                  {
                     if ( startDay != 0 && startRuleDay != 0 && startDay <= startRuleDay )
                     {
                        monthlys.add( dynamicMonthly + "_1" );
                     }
                  }
                  // ���ɹ��� - ������������
                  else if ( startRule != null && startRule.trim().equals( "2" ) )
                  {
                     int workDays = getWorkDays( accountId, calendarId, dynamicMonthly, startCalendar.getTime(), false );

                     if ( workDays != 0 && startRuleDay != 0 && workDays >= startRuleDay )
                     {
                        monthlys.add( dynamicMonthly + "_1" );
                     }
                  }
                  // ���ɹ��� - ����Ȼ������
                  else if ( startRule != null && startRule.trim().equals( "3" ) )
                  {
                     // ������������
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

      // ��������
      if ( sbStatus.trim().equals( "3" ) )
      {
         // ���δ��ӱ������˳�ѭ��
         if ( KANUtil.filterEmpty( startRule, "0" ) == null || KANUtil.filterEmpty( startRuleRemark ) == null )
         {
            return monthlys;
         }

         // ���ɹ��� - ������
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
         // ���ɹ��� - ������������
         else if ( startRule != null && startRule.trim().equals( "2" ) )
         {
            int workDays = getWorkDays( accountId, calendarId, monthly, startCalendar.getTime(), false );

            if ( workDays != 0 && startRuleDay != 0 && workDays >= startRuleDay )
            {
               monthlys.add( monthly + "_1" );
            }
         }
         // ���ɹ��� - ����Ȼ������
         else if ( startRule != null && startRule.trim().equals( "3" ) )
         {
            boolean matchByMonthDays = true;

            // ������������
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

      // ���걨�˱�
      if ( sbStatus.trim().equals( "5" ) )
      {
         // ���δ���˱������˳�ѭ��
         if ( KANUtil.filterEmpty( endRule, "0" ) == null || KANUtil.filterEmpty( endRuleRemark ) == null )
         {
            return monthlys;
         }

         // ������������
         if ( endYear * 12 + endMonth > monthlyYear * 12 + monthlyMonth )
         {
            monthlys.add( monthly + "_1" );
         }
         else
         {
            // �ӱ����ڣ��˱�������ͬ���������������
            if ( KANUtil.filterEmpty( sbStartDate ) != null && KANUtil.filterEmpty( sbEndDate ) != null
                  && KANUtil.filterEmpty( sbStartDate ).equals( KANUtil.filterEmpty( sbEndDate ) ) )
            {
               monthlys.add( monthly + "_0" );
            }
            else
            {
               // ���ɹ��� - �����ڡ�����Ȼ������
               if ( endRule != null && ( endRule.trim().equals( "1" ) || endRule.trim().equals( "3" ) ) )
               {
                  // �����˱� - ��������
                  if ( endDay != 0 && endRuleDay != 0 && endDay >= endRuleDay )
                  {
                     monthlys.add( monthly + "_1" );
                  }
                  // �����˱� - ����������
                  else
                  {
                     monthlys.add( monthly + "_0" );
                  }
               }
               // ���ɹ��� - ����������
               else if ( endRule != null && endRule.trim().equals( "2" ) )
               {
                  int workDays = getWorkDays( accountId, calendarId, monthly, KANUtil.createDate( sbEndDate ), true );

                  // �����˱� - ��������
                  if ( workDays != 0 && endRuleDay != 0 && workDays >= endRuleDay )
                  {
                     monthlys.add( monthly + "_1" );
                  }
                  // �����˱� - ����������
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
         // ��ʼ��CalendarDTO
         final CalendarDTO calendarDTO = KANConstants.getKANAccountConstants( accountId ).getCalendarDTOByHeaderId( calendarId );
         final List< CalendarDetailVO > calendarDetailVOs = calendarDTO.getCalendarDetailVOsByMonthly( monthly );

         // ��ʼ������ͷ���Calendar
         final Calendar startCalendar = KANUtil.getFirstCalendar( monthly );
         final Calendar endCalendar = KANUtil.getLastCalendar( monthly );

         // ��ͷ��ʼ
         if ( fromBegin )
         {
            // ѭ��ֱ������Target Date
            while ( startCalendar.get( Calendar.DAY_OF_MONTH ) <= Integer.valueOf( KANUtil.getDay( targetDate ) ) )
            {
               // ��ʶ - �Ƿ�����������
               boolean isSet = false;

               // ����Ƿ��������趨
               if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
               {
                  for ( CalendarDetailVO calendarDetailVO : calendarDetailVOs )
                  {
                     if ( Integer.valueOf( KANUtil.getDay( KANUtil.createDate( calendarDetailVO.getDay() ) ) ) == startCalendar.get( Calendar.DAY_OF_MONTH ) )
                     {
                        // ������������ǹ�����
                        if ( calendarDetailVO.getDayType().equals( "1" ) )
                        {
                           workDays++;
                        }
                        // ���Ϊ������������
                        isSet = true;
                        break;
                     }
                  }
               }

               // �������δ����
               if ( !isSet )
               {
                  // �������������գ����Ϊ������
                  if ( !( startCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY || startCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) )
                  {
                     workDays++;
                  }
               }

               // ��������ȣ�����
               if ( endCalendar.get( Calendar.DAY_OF_MONTH ) == Integer.valueOf( KANUtil.getDay( targetDate ) ) )
               {
                  break;
               }

               startCalendar.add( Calendar.DAY_OF_MONTH, 1 );
            }
         }
         else
         {
            // ѭ��ֱ��С��Target Date
            while ( endCalendar.get( Calendar.DAY_OF_MONTH ) >= Integer.valueOf( KANUtil.getDay( targetDate ) ) )
            {
               // ��ʶ - �Ƿ�����������
               boolean isSet = false;

               // ����Ƿ��������趨
               if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
               {
                  for ( CalendarDetailVO calendarDetailVO : calendarDetailVOs )
                  {
                     if ( Integer.valueOf( KANUtil.getDay( KANUtil.createDate( calendarDetailVO.getDay() ) ) ) == endCalendar.get( Calendar.DAY_OF_MONTH ) )
                     {
                        // ������������ǹ�����
                        if ( calendarDetailVO.getDayType().equals( "1" ) )
                        {
                           workDays++;
                        }
                        // ���Ϊ������������
                        isSet = true;
                        break;
                     }
                  }
               }

               // �������δ����
               if ( !isSet )
               {
                  // �������������գ����Ϊ������
                  if ( !( endCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY || endCalendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) )
                  {
                     workDays++;
                  }
               }

               // ��������ȣ�����
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
      // ��ʼ��Discount
      double discount = 0;

      // ��ʼ��������Ͳ�����
      int monthlyYear = 0;
      int monthlyMonth = 0;
      if ( monthly != null && monthly.contains( "/" ) )
      {
         monthlyYear = Integer.valueOf( monthly.split( "/" )[ 0 ] );
         monthlyMonth = Integer.valueOf( monthly.split( "/" )[ 1 ] );
      }

      // ����Monthly��ʼ��Calendar��Ĭ����Ϊ�������һ��
      final Calendar calendar = KANUtil.getLastCalendar( monthly );

      // ��ȡ������ - Start
      int startYear = 0;
      int startMonth = 0;
      int startDay = 0;
      if ( cbStartDate != null && ( cbStartDate.contains( "/" ) || cbStartDate.contains( "-" ) ) )
      {
         startYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( cbStartDate ) ) );
         startMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( cbStartDate ) ) );
         startDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( cbStartDate ) ) );
      }

      // ��ȡ������ - End
      int endYear = 0;
      int endMonth = 0;
      int endDay = 0;
      if ( cdEndDate != null && ( cdEndDate.contains( "/" ) || cdEndDate.contains( "-" ) ) )
      {
         endYear = Integer.valueOf( KANUtil.getYear( KANUtil.createDate( cdEndDate ) ) );
         endMonth = Integer.valueOf( KANUtil.getMonth( KANUtil.createDate( cdEndDate ) ) );
         endDay = Integer.valueOf( KANUtil.getDay( KANUtil.createDate( cdEndDate ) ) );
      }

      // ���깺
      if ( cbStatus.trim().equals( "2" ) )
      {
         // �ӱ��·� < ��ǰ������
         if ( ( monthlyYear * 12 + monthlyMonth ) - ( startYear * 12 + startMonth ) > 0 )
         {
            discount = 1;
         }
         // �ӱ��·� = ��ǰ������
         else if ( ( monthlyYear * 12 + monthlyMonth ) - ( startYear * 12 + startMonth ) == 0 )
         {
            // ȫ��
            if ( startDay == 1 )
            {
               discount = 1;
            }
            // ��ȫ��
            else
            {
               discount = Double.valueOf( calendar.get( Calendar.DAY_OF_MONTH ) - startDay + 1 ) / Double.valueOf( calendar.get( Calendar.DAY_OF_MONTH ) );
            }
         }
      }
      // ��������
      else if ( cbStatus.trim().equals( "3" ) )
      {
         discount = 1;
      }
      // ���˹�
      else if ( cbStatus.trim().equals( "5" ) )
      {
         // �˹��·������̱��·�
         if ( ( monthlyYear * 12 + monthlyMonth ) < ( endYear * 12 + endMonth ) )
         {
            discount = 1;
         }
         // �˹��·ݵ����̱��·�
         else if ( ( monthlyYear * 12 + monthlyMonth ) == ( endYear * 12 + endMonth ) )
         {
            // �깺���ڣ��˹�������ͬ���������������
            if ( startDay == endDay )
            {
               discount = 0;
            }
            // ȫ��
            else if ( endDay == calendar.get( Calendar.DAY_OF_MONTH ) )
            {
               discount = 1;
            }
            // ��ȫ��
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
            // ��ʼ��ItemVO
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

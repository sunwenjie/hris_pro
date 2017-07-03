package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kan.base.domain.management.ItemGroupDTO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SickLeaveSalaryDTO;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.domain.management.TaxVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.domain.system.IncomeTaxRangeDTO;
import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.attendance.LeaveDTO;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.domain.biz.attendance.OTDTO;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.cb.CBDTO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDTO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBDTO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;
import com.kan.hro.domain.biz.settlement.SettlementTempDTO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

public class ClientOrderDTO implements Serializable
{

   /**  
   * Serial Version UID  
   */
   private static final long serialVersionUID = 3249842900421942863L;

   private static final String BILL = "1";

   private static final String COST = "2";

   // AccountId - ������㣨Scope������������
   private String ACCOUNT_ID;

   // ���� - ������㣨Scope������������
   private String ACCURACY;

   // ��ȡ - ������㣨Scope������������
   private String ROUND;

   // Tax - ������㣨Scope������������
   private TaxVO TAX_VO;

   // ���۷�ʽ�������� - ������㣨Scope������������
   private String SALES_TYPE;

   // �������� - ������㣨Scope������������
   private int SETTLEMENT_NUMBER;

   // ������Ȼ����
   private Double DAYS;

   // �����ܽ��
   private double ORDER_AMOUNT;

   // ʵ�����ʣ���������˰ʹ�ã�
   private double AFTER_TAX_SALARY;

   //��ƽ������
   private double AVG_SALARY_DAYS_PER_MONTH;

   // ���ʵ���Header IDs����������˰ʹ�ã�
   private List< String > SALARY_HEADER_IDS = new ArrayList< String >();

   // �籣���������������籣�����շ�ʹ�ã�
   Map< String, Integer > SB_SUPPLEMENTARY_MONTHS = new HashMap< String, Integer >();

   // ��ʽ���� - ������㣨Scope����������Э�飩
   private Map< String, ConstantsDTO > CONSTANTS_VALUE = new HashMap< String, ConstantsDTO >();

   // Order Header
   private ClientOrderHeaderDTO clientOrderHeaderDTO;

   // Client
   private ClientDTO clientDTO;

   // Order Detail - DTO
   private List< ClientOrderDetailDTO > clientOrderDetailDTOs = new ArrayList< ClientOrderDetailDTO >();

   // Order Social Benefit - VO
   private List< ClientOrderSBVO > clientOrderSBVOs = new ArrayList< ClientOrderSBVO >();

   // Order Commercial Benefit - VO
   private List< ClientOrderCBVO > clientOrderCBVOs = new ArrayList< ClientOrderCBVO >();

   // Order Leave - VO
   private List< ClientOrderLeaveVO > clientOrderLeaveVOs = new ArrayList< ClientOrderLeaveVO >();

   // Order OT - VO
   private List< ClientOrderOTVO > clientOrderOTVOs = new ArrayList< ClientOrderOTVO >();

   // Order Other - VO
   private List< ClientOrderOtherVO > clientOrderOtherVOs = new ArrayList< ClientOrderOtherVO >();

   // Service Contract - DTO
   private List< ServiceContractDTO > serviceContractDTOs = new ArrayList< ServiceContractDTO >();

   // OrderHeaderTemp - VO (Calculate Result)
   private OrderHeaderTempVO orderHeaderTempVO;

   // Settlement - DTO, Temp (Calculate Result)
   private List< SettlementTempDTO > settlementTempDTOs = new ArrayList< SettlementTempDTO >();

   public ClientOrderHeaderDTO getClientOrderHeaderDTO()
   {
      return clientOrderHeaderDTO;
   }

   public void setClientOrderHeaderDTO( ClientOrderHeaderDTO clientOrderHeaderDTO )
   {
      this.clientOrderHeaderDTO = clientOrderHeaderDTO;
   }

   public ClientDTO getClientDTO()
   {
      return clientDTO;
   }

   public void setClientDTO( ClientDTO clientDTO )
   {
      this.clientDTO = clientDTO;
   }

   public List< ClientOrderDetailDTO > getClientOrderDetailDTOs()
   {
      return clientOrderDetailDTOs;
   }

   public void setClientOrderDetailDTOs( List< ClientOrderDetailDTO > clientOrderDetailDTOs )
   {
      this.clientOrderDetailDTOs = clientOrderDetailDTOs;
   }

   public List< ClientOrderSBVO > getClientOrderSBVOs()
   {
      return clientOrderSBVOs;
   }

   public void setClientOrderSBVOs( List< ClientOrderSBVO > clientOrderSBVOs )
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

   public List< ClientOrderLeaveVO > getClientOrderLeaveVOs()
   {
      return clientOrderLeaveVOs;
   }

   public void setClientOrderLeaveVOs( List< ClientOrderLeaveVO > clientOrderLeaveVOs )
   {
      this.clientOrderLeaveVOs = clientOrderLeaveVOs;
   }

   public List< ClientOrderOTVO > getClientOrderOTVOs()
   {
      return clientOrderOTVOs;
   }

   public void setClientOrderOTVOs( List< ClientOrderOTVO > clientOrderOTVOs )
   {
      this.clientOrderOTVOs = clientOrderOTVOs;
   }

   public List< ClientOrderOtherVO > getClientOrderOtherVOs()
   {
      return clientOrderOtherVOs;
   }

   public void setClientOrderOtherVOs( List< ClientOrderOtherVO > clientOrderOtherVOs )
   {
      this.clientOrderOtherVOs = clientOrderOtherVOs;
   }

   public List< ServiceContractDTO > getServiceContractDTOs()
   {
      return serviceContractDTOs;
   }

   public void setServiceContractDTOs( List< ServiceContractDTO > serviceContractDTOs )
   {
      this.serviceContractDTOs = serviceContractDTOs;
   }

   public OrderHeaderTempVO getOrderHeaderTempVO()
   {
      return orderHeaderTempVO;
   }

   public void setOrderHeaderTempVO( OrderHeaderTempVO orderHeaderTempVO )
   {
      this.orderHeaderTempVO = orderHeaderTempVO;
   }

   public List< SettlementTempDTO > getSettlementTempDTOs()
   {
      return settlementTempDTOs;
   }

   public void setSettlementTempDTOs( List< SettlementTempDTO > settlementTempDTOs )
   {
      this.settlementTempDTOs = settlementTempDTOs;
   }

   // ����ItemId��ȡClientOrderOTVO����
   public ClientOrderOTVO getClientOrderOTVOByItemId( final String itemId )
   {
      if ( this.clientOrderOTVOs != null && this.clientOrderOTVOs.size() > 0 )
      {
         for ( ClientOrderOTVO clientOrderOTVO : this.clientOrderOTVOs )
         {
            if ( clientOrderOTVO.getItemId() != null && clientOrderOTVO.getItemId().trim().equals( itemId ) )
            {
               return clientOrderOTVO;
            }
         }
      }

      return null;
   }

   // ����ItemId��ȡClientOrderOtherVO����
   public ClientOrderOtherVO getClientOrderOtherVOByItemId( final String itemId )
   {
      if ( this.clientOrderOtherVOs != null && this.clientOrderOtherVOs.size() > 0 )
      {
         for ( ClientOrderOtherVO clientOrderOtherVO : this.clientOrderOtherVOs )
         {
            if ( clientOrderOtherVO.getItemId() != null && clientOrderOtherVO.getItemId().trim().equals( itemId ) )
            {
               return clientOrderOtherVO;
            }
         }
      }

      return null;
   }

   // �ۼӳ�������isBase�������ж���ԭʼBase���Ǽ�������
   private void addConstantsValue( final String itemType, final String startDate, final String endDate, final double value, final boolean isBase )
   {
      if ( itemType != null && !itemType.trim().equals( "" ) )
      {
         if ( itemType.trim().equals( "1" ) )
         {
            if ( isBase )
            {
               final ConstantsDTO constantsDTO_402 = CONSTANTS_VALUE.get( "402" );
               constantsDTO_402.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "402", constantsDTO_402 );

               final ConstantsDTO constantsDTO_414 = CONSTANTS_VALUE.get( "414" );
               constantsDTO_414.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "414", constantsDTO_414 );
            }
            else
            {
               final ConstantsDTO constantsDTO_406 = CONSTANTS_VALUE.get( "406" );
               constantsDTO_406.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "406", constantsDTO_406 );

               final ConstantsDTO constantsDTO_415 = CONSTANTS_VALUE.get( "415" );
               constantsDTO_415.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "415", constantsDTO_415 );
            }
         }
         else if ( itemType.trim().equals( "2" ) )
         {
            if ( isBase )
            {
               final ConstantsDTO constantsDTO_404 = CONSTANTS_VALUE.get( "404" );
               constantsDTO_404.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "404", constantsDTO_404 );

               final ConstantsDTO constantsDTO_414 = CONSTANTS_VALUE.get( "414" );
               constantsDTO_414.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "414", constantsDTO_414 );
            }
            else
            {
               final ConstantsDTO constantsDTO_408 = CONSTANTS_VALUE.get( "408" );
               constantsDTO_408.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "408", constantsDTO_408 );

               final ConstantsDTO constantsDTO_415 = CONSTANTS_VALUE.get( "415" );
               constantsDTO_415.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "415", constantsDTO_415 );
            }
         }
         else if ( itemType.trim().equals( "3" ) )
         {
            if ( isBase )
            {
               final ConstantsDTO constantsDTO_403 = CONSTANTS_VALUE.get( "403" );
               constantsDTO_403.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "403", constantsDTO_403 );

               final ConstantsDTO constantsDTO_414 = CONSTANTS_VALUE.get( "414" );
               constantsDTO_414.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "414", constantsDTO_414 );
            }
            else
            {
               final ConstantsDTO constantsDTO_407 = CONSTANTS_VALUE.get( "407" );
               constantsDTO_407.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "407", constantsDTO_407 );

               final ConstantsDTO constantsDTO_415 = CONSTANTS_VALUE.get( "415" );
               constantsDTO_415.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "415", constantsDTO_415 );
            }
         }
         else if ( itemType.trim().equals( "4" ) )
         {
            if ( !isBase )
            {
               final ConstantsDTO constantsDTO_409 = CONSTANTS_VALUE.get( "409" );
               constantsDTO_409.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "409", constantsDTO_409 );

               final ConstantsDTO constantsDTO_415 = CONSTANTS_VALUE.get( "415" );
               constantsDTO_415.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "415", constantsDTO_415 );
            }
         }
         else if ( itemType.trim().equals( "5" ) )
         {
            if ( !isBase )
            {
               final ConstantsDTO constantsDTO_413 = CONSTANTS_VALUE.get( "413" );
               constantsDTO_413.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "413", constantsDTO_413 );
            }
         }
         else if ( itemType.trim().equals( "7" ) )
         {
            if ( !isBase )
            {
               final ConstantsDTO constantsDTO_416 = CONSTANTS_VALUE.get( "416" );
               constantsDTO_416.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "416", constantsDTO_416 );
            }
         }
         else if ( itemType.trim().equals( "8" ) )
         {
            if ( !isBase )
            {
               final ConstantsDTO constantsDTO_417 = CONSTANTS_VALUE.get( "417" );
               constantsDTO_417.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "417", constantsDTO_417 );
            }
         }
         else if ( itemType.trim().equals( "9" ) )
         {
            if ( !isBase )
            {
               final ConstantsDTO constantsDTO_420 = CONSTANTS_VALUE.get( "420" );
               constantsDTO_420.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "420", constantsDTO_420 );
            }
         }
         else if ( itemType.trim().equals( "10" ) )
         {
            if ( !isBase )
            {
               final ConstantsDTO constantsDTO_418 = CONSTANTS_VALUE.get( "418" );
               constantsDTO_418.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "418", constantsDTO_418 );
            }
         }
         else if ( itemType.trim().equals( "12" ) )
         {
            if ( !isBase )
            {
               final ConstantsDTO constantsDTO_419 = CONSTANTS_VALUE.get( "419" );
               constantsDTO_419.addConstantsDay( startDate, endDate, value );
               CONSTANTS_VALUE.put( "419", constantsDTO_419 );
            }
         }
      }
   }

   // ��ȡBaseFrom���
   private double getBaseFromValue( final SettlementTempDTO settlementTempDTO, final String baseFrom, final double base ) throws KANException
   {
      // ��BaseFrom��ȡ����Base���
      if ( baseFrom != null && !baseFrom.trim().equals( "" ) && !baseFrom.trim().equals( "0" ) )
      {
         double baseFromAmount = 0;
         final ItemGroupDTO itemGroupDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemGroupDTOByItemGroupId( baseFrom );

         if ( itemGroupDTO != null && itemGroupDTO.getItemVOs() != null && itemGroupDTO.getItemVOs().size() > 0 )
         {
            for ( ItemVO baseItemVO : itemGroupDTO.getItemVOs() )
            {
               if ( settlementTempDTO.getOrderDetailTempVOs() != null && settlementTempDTO.getOrderDetailTempVOs().size() > 0 )
               {
                  for ( OrderDetailTempVO orderDetailTempVO : settlementTempDTO.getOrderDetailTempVOs() )
                  {
                     if ( orderDetailTempVO.getItemId().equals( baseItemVO.getItemId() ) )
                     {
                        baseFromAmount = baseFromAmount + Double.valueOf( orderDetailTempVO.getBillAmountCompany() );
                     }
                  }
               }
            }
         }

         if ( baseFromAmount > 0 )
         {
            return baseFromAmount;
         }
      }

      return base;
   }

   // ��ȡFormular���
   private double getFormularValue( final String formular, final double base, final String day ) throws KANException
   {
      // ��Formular��ȡ������
      if ( formular != null && !formular.trim().equals( "" ) && !formular.trim().equals( "0" ) )
      {
         try
         {
            // ��ʼ��Formular Amount
            double formularAmount = 0;
            // ��ȡConstants
            final List< ConstantVO > constantVOs = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getConstantVOsByScopeType( "4" );
            // ��ʼ�����ʽ������
            final ExpressRunner runner = new ExpressRunner();
            // ��ʼ����ʽ����
            final IExpressContext< String, Object > context = new DefaultContext< String, Object >();

            // ���ñ��ʽ����
            if ( constantVOs != null && constantVOs.size() > 0 )
            {
               for ( ConstantVO constantVO : constantVOs )
               {
                  // ��421��Ϊ��������Ҫ��������
                  context.put( constantVO.getNameZH(), constantVO.getConstantId().equals( "421" ) ? base : CONSTANTS_VALUE.get( constantVO.getConstantId() ).getValue( day ) );
               }
            }

            try
            {
               // ִ�б��ʽ
               final Object expressReslut = runner.execute( formular, context, null, false, false );

               formularAmount = Double.valueOf( expressReslut.toString() );
            }
            catch ( final Exception e )
            {
            }

            // ����ʽ������������ǵ�ǰBase
            if ( formularAmount != 0 )
            {
               return formularAmount;
            }
         }
         catch ( final Exception e )
         {
            throw new KANException( e );
         }
      }

      return base;
   }

   // �������
   public boolean calculateSettlement( final List< String > flags ) throws KANException
   {
      if ( this.getClientOrderHeaderDTO() != null )
      {
         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDTO().getClientOrderHeaderVO();

         // �����ͷ���Э�鶼���� - ��������
         if ( clientOrderHeaderVO != null && this.getServiceContractDTOs() != null && this.getServiceContractDTOs().size() > 0 )
         {
            // ��ʼ������ʹ�ñ���
            ACCOUNT_ID = clientOrderHeaderVO.getAccountId();
            ACCURACY = KANConstants.getKANAccountConstants( ACCOUNT_ID ).OPTIONS_ACCURACY;
            ROUND = KANConstants.getKANAccountConstants( ACCOUNT_ID ).OPTIONS_ROUND;
            TAX_VO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getTaxVOByTaxId( clientOrderHeaderVO.getTaxId() );
            SALES_TYPE = clientOrderHeaderVO.getSalesType() != null ? clientOrderHeaderVO.getSalesType() : "";
            SETTLEMENT_NUMBER = this.serviceContractDTOs != null ? this.serviceContractDTOs.size() : 0;
            DAYS = Double.valueOf( KANUtil.getMaximumDaysOfMonth( clientOrderHeaderVO.getMonthly() ) );
            AVG_SALARY_DAYS_PER_MONTH = KANConstants.getKANAccountConstants( ACCOUNT_ID ).AVG_SALARY_DAYS_PER_MONTH;

            // ��ȡ�������͵�ϵͳ����
            final List< ConstantVO > constantVOs = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getConstantVOsByScopeType( "4" );

            // װ��OrderHeaderTempVO
            fetchClientOrderHeader( clientOrderHeaderVO );

            // ���շ���Э�����������
            for ( ServiceContractDTO serviceContractDTO : this.getServiceContractDTOs() )
            {
               // ��ÿ������Э�����
               AFTER_TAX_SALARY = 0;
               SALARY_HEADER_IDS = new ArrayList< String >();
               SB_SUPPLEMENTARY_MONTHS = new HashMap< String, Integer >();

               // ��ʼ��SettlementDTO
               final SettlementTempDTO settlementTempDTO = new SettlementTempDTO();

               // ÿ������Э���ʼ��һ��CONSTANTS_VALUE
               if ( constantVOs != null && constantVOs.size() > 0 )
               {
                  for ( ConstantVO constantVO : constantVOs )
                  {
                     if ( "479".equals( constantVO.getConstantId() ) )
                     {
                        CONSTANTS_VALUE.put( constantVO.getConstantId(), new ConstantsDTO( null, null, KANUtil.filterEmpty( serviceContractDTO.getFirstDayOfYearCircle() ) != null ? Double.valueOf( serviceContractDTO.getFirstDayOfYearCircle() )
                              : 0 ) );
                     }
                     else if ( "481".equals( constantVO.getConstantId() ) )
                     {
                        CONSTANTS_VALUE.put( constantVO.getConstantId(), new ConstantsDTO( null, null, KANUtil.filterEmpty( serviceContractDTO.getLastDayOfYearCircle() ) != null ? Double.valueOf( serviceContractDTO.getLastDayOfYearCircle() )
                              : 0 ) );
                     }
                     else if ( "483".equals( constantVO.getConstantId() ) )
                     {
                        CONSTANTS_VALUE.put( constantVO.getConstantId(), new ConstantsDTO( null, null, KANUtil.filterEmpty( serviceContractDTO.getTotalWorkHoursOfYear() ) != null ? Double.valueOf( serviceContractDTO.getTotalWorkHoursOfYear() )
                              : 0 ) );
                     }
                     else if ( "485".equals( constantVO.getConstantId() ) )
                     {
                        CONSTANTS_VALUE.put( constantVO.getConstantId(), new ConstantsDTO( null, null, KANUtil.filterEmpty( serviceContractDTO.getTotalFullHoursOfYear() ) != null ? Double.valueOf( serviceContractDTO.getTotalFullHoursOfYear() )
                              : 0 ) );
                     }
                     else
                     {
                        CONSTANTS_VALUE.put( constantVO.getConstantId(), new ConstantsDTO() );
                     }
                  }
               }

               // ��ʼ�����ڱ�ʼ����
               Calendar tsStartCalendar = null;
               if ( serviceContractDTO.getTimesheetDTO() != null && serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO() != null
                     && KANUtil.filterEmpty( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getStartDate() ) != null )
               {
                  tsStartCalendar = KANUtil.createCalendar( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getStartDate() );
               }

               // ��ʼ�����ڱ��������
               Calendar tsEndCalendar = null;
               if ( serviceContractDTO.getTimesheetDTO() != null && serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO() != null
                     && KANUtil.filterEmpty( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getEndDate() ) != null )
               {
                  tsEndCalendar = KANUtil.createCalendar( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getEndDate() );
               }

               // ��ʼ���������ڿ�ʼ����
               Calendar calculateStartCalendar = KANUtil.getStartCalendar( clientOrderHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleStartDay() );

               // ��ʼ���������ڽ�������
               Calendar calculateEndCalendar = KANUtil.getEndCalendar( clientOrderHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleEndDay() );

               // ��ʼ��Sick Leave ID
               String sickLeaveId = serviceContractDTO.getEmployeeContractVO().getSickLeaveSalaryId();
               if ( KANUtil.filterEmpty( sickLeaveId, "0" ) == null )
               {
                  sickLeaveId = clientOrderHeaderVO.getSickLeaveSalaryId();
               }

               // ��ʼ��
               SickLeaveSalaryDTO sickLeaveSalaryDTO = null;
               if ( KANUtil.filterEmpty( sickLeaveId, "0" ) != null )
               {
                  sickLeaveSalaryDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getSickLeaveSalaryDTOByHeaderId( sickLeaveId );
               }

               // ��ʼ��ServiceContractTempVO
               final ServiceContractTempVO serviceContractTempVO = new ServiceContractTempVO();

               // �����£�����ְ���������µ��������ͬ��ǩ�ȣ����迼�ǿ�Ŀ���������£�
               boolean incompleteCircle = false;

               // ���ڱ���ʼ���ڴ��ڽ������ڿ�ʼ����
               if ( tsStartCalendar != null && calculateStartCalendar != null && KANUtil.getDays( tsStartCalendar ) > KANUtil.getDays( calculateStartCalendar ) )
               {
                  incompleteCircle = true;
               }

               // ���ڱ��������С�ڽ������ڽ�������
               if ( tsEndCalendar != null && calculateEndCalendar != null && KANUtil.getDays( tsEndCalendar ) < KANUtil.getDays( calculateEndCalendar ) )
               {
                  incompleteCircle = true;
               }

               /** ���ý���̨�� */

               // �籣����
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_SB ) )
               {
                  // װ���籣
                  fetchSB( settlementTempDTO, serviceContractDTO.getSbDTOs() );

                  // װ���籣����
                  fetchSBAdjustment( settlementTempDTO, serviceContractDTO.getSbAdjustmentDTOs() );
               }

               // �̱�����
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_CB ) )
               {
                  // װ���̱�
                  fetchCB( settlementTempDTO, serviceContractDTO.getCbDTOs() );
               }

               // ���ʽ���
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_SALARY ) )
               {
                  // װ�ع��ʣ���ؿ�Ŀ��
                  fetchSalary( settlementTempDTO, serviceContractDTO, sickLeaveSalaryDTO, tsStartCalendar, tsEndCalendar, incompleteCircle );

                  // ����TimesheetId
                  if ( serviceContractDTO.getTimesheetDTO() != null && serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO() != null )
                  {
                     serviceContractTempVO.setTimesheetId( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getHeaderId() );
                  }
               }

               // �������ã��������ɱ�����
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER ) )
               {
                  // ��ʼ��EmployeeContractOtherVO���������б�
                  final List< EmployeeContractOtherVO > otherFeeEmployeeContractOtherVOs = serviceContractDTO.getOtherFeeEmployeeContractOtherVOs();
                  mergeOtherVOs( otherFeeEmployeeContractOtherVOs, this.getClientOrderOtherVOsByItemType( "10" ) );

                  // ��ʼ��EmployeeContractOtherVO�������ɱ��б�
                  final List< EmployeeContractOtherVO > thirdPartCostEmployeeContractOtherVOs = serviceContractDTO.getThirdPartCostEmployeeContractOtherVOs();
                  mergeOtherVOs( thirdPartCostEmployeeContractOtherVOs, this.getClientOrderOtherVOsByItemType( "11" ) );

                  // װ���������ã��������ɱ�
                  fetchOther( settlementTempDTO, serviceContractDTO, otherFeeEmployeeContractOtherVOs, calculateStartCalendar, calculateEndCalendar );
                  fetchOther( settlementTempDTO, serviceContractDTO, thirdPartCostEmployeeContractOtherVOs, calculateStartCalendar, calculateEndCalendar );
               }

               // ����ѽ���
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_SERVICE_FEE ) )
               {
                  // װ�ط����
                  fetchServiceFee( settlementTempDTO, serviceContractDTO, this.getClientOrderDetailDTOs(), calculateStartCalendar, calculateEndCalendar );
               }

               // ���ջ������
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER ) )
               {
                  // ��ʼ��EmployeeContractOtherVO���ջ����б�
                  final List< EmployeeContractOtherVO > fundingEmployeeContractOtherVOs = serviceContractDTO.getFundingEmployeeContractOtherVOs();
                  mergeOtherVOs( fundingEmployeeContractOtherVOs, this.getClientOrderOtherVOsByItemType( "12" ) );

                  // װ�ط��ջ���
                  fetchOther( settlementTempDTO, serviceContractDTO, fundingEmployeeContractOtherVOs, calculateStartCalendar, calculateEndCalendar );
               }

               // ����˰����
               if ( AFTER_TAX_SALARY != 0 )
               {
                  // ��ʼ����˰����
                  IncomeTaxBaseVO incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( serviceContractDTO.getEmployeeContractVO().getIncomeTaxBaseId() );

                  if ( incomeTaxBaseVO == null )
                  {
                     incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( clientOrderHeaderVO.getIncomeTaxBaseId() );

                     if ( incomeTaxBaseVO == null )
                     {
                        incomeTaxBaseVO = KANConstants.getValidIncomeTaxBaseVO();
                     }
                  }

                  double personalTaxBase = 0;

                  if ( incomeTaxBaseVO != null )
                  {
                     if ( serviceContractDTO.getEmployeeVO() != null && KANUtil.filterEmpty( serviceContractDTO.getEmployeeVO().getResidencyType(), "0" ) != null
                           && ( serviceContractDTO.getEmployeeVO().getResidencyType().equals( "5" ) || serviceContractDTO.getEmployeeVO().getResidencyType().equals( "6" ) ) )
                     {
                        if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBaseForeigner() ) != null )
                        {
                           personalTaxBase = Double.valueOf( incomeTaxBaseVO.getBaseForeigner() );
                        }
                     }
                     else
                     {
                        if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBase() ) != null )
                        {
                           personalTaxBase = Double.valueOf( incomeTaxBaseVO.getBase() );
                        }
                     }
                  }

                  // ��ʼ����˰����
                  IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( serviceContractDTO.getEmployeeContractVO().getIncomeTaxRangeHeaderId() );

                  if ( incomeTaxRangeDTO == null )
                  {
                     incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() );

                     if ( incomeTaxRangeDTO == null )
                     {
                        incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();
                     }
                  }

                  // ��ȡӦ˰���ʲ���
                  double taxSalaryBase = ( KANUtil.filterEmpty( serviceContractDTO.getAddtionalBillAmountPersonal() ) != null ? Double.valueOf( serviceContractDTO.getAddtionalBillAmountPersonal() )
                        : 0 )
                        - ( KANUtil.filterEmpty( serviceContractDTO.getTaxAmountPersonal() ) != null ? Double.valueOf( serviceContractDTO.getTaxAmountPersonal() ) : 0 )
                        + Double.valueOf( AFTER_TAX_SALARY ) - personalTaxBase;

                  IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = incomeTaxRangeDTO.getIncomeTaxRangeDetailVOByBase( String.valueOf( taxSalaryBase ) );

                  double tax = ( incomeTaxRangeDetailVO != null ? ( ( taxSalaryBase ) * Double.valueOf( incomeTaxRangeDetailVO.getPercentage() ) / 100 - Double.valueOf( incomeTaxRangeDetailVO.getDeduct() ) )
                        / ( 1 - Double.valueOf( incomeTaxRangeDetailVO.getPercentage() ) / 100 )
                        : 0 )
                        - ( KANUtil.filterEmpty( serviceContractDTO.getTaxAmountPersonal() ) != null ? Double.valueOf( serviceContractDTO.getTaxAmountPersonal() ) : 0 );

                  double beforeTaxSalary = Double.valueOf( AFTER_TAX_SALARY ) + tax + settlementTempDTO.getCostAmountPersonal();

                  // ��ü�����Ӧ˰����
                  double tempTaxSalaryBase = ( beforeTaxSalary
                        + ( KANUtil.filterEmpty( serviceContractDTO.getAddtionalBillAmountPersonal() ) != null ? Double.valueOf( serviceContractDTO.getAddtionalBillAmountPersonal() )
                              : 0 ) - settlementTempDTO.getCostAmountPersonal() - personalTaxBase );

                  // ���Ӧ˰���ʳ�����ǰ����
                  if ( incomeTaxRangeDetailVO != null && KANUtil.filterEmpty( incomeTaxRangeDetailVO.getRangeTo() ) != null
                        && tempTaxSalaryBase > Double.valueOf( incomeTaxRangeDetailVO.getRangeTo() ) )
                  {
                     incomeTaxRangeDetailVO = incomeTaxRangeDTO.getIncomeTaxRangeDetailVOByBase( String.valueOf( tempTaxSalaryBase ) );

                     tax = ( incomeTaxRangeDetailVO != null ? ( ( taxSalaryBase ) * Double.valueOf( incomeTaxRangeDetailVO.getPercentage() ) / 100 - Double.valueOf( incomeTaxRangeDetailVO.getDeduct() ) )
                           / ( 1 - Double.valueOf( incomeTaxRangeDetailVO.getPercentage() ) / 100 )
                           : 0 )
                           - ( KANUtil.filterEmpty( serviceContractDTO.getTaxAmountPersonal() ) != null ? Double.valueOf( serviceContractDTO.getTaxAmountPersonal() ) : 0 );

                     beforeTaxSalary = Double.valueOf( AFTER_TAX_SALARY ) + tax + settlementTempDTO.getCostAmountPersonal();
                  }

                  // ��ʼ��ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( "1" );

                  // ��ʼ��OrderDetailTempVO
                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setHeaderId( KANUtil.stringListToJasonArray( SALARY_HEADER_IDS ) );
                  orderDetailTempVO.setDetailType( "4" );
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // �����Կ�Ŀ��ʹ��Base From
                  orderDetailTempVO.setBase( "0" );
                  orderDetailTempVO.setQuantity( "1" );
                  orderDetailTempVO.setDiscount( "1" );
                  orderDetailTempVO.setMultiple( "1" );
                  orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                  orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                  orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                  orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                  orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                  orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                  orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                  orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( beforeTaxSalary, ACCURACY, ROUND ) );
                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( beforeTaxSalary, ACCURACY, ROUND ) );
                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( beforeTaxSalary, ACCURACY, ROUND ) );
                  orderDetailTempVO.setCostAmountPersonal( "0" );

                  // ��ʼ��BillAmountCompany
                  double billAmountCompany = Double.valueOf( orderDetailTempVO.getBillAmountCompany() );

                  if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
                  {
                     orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setTaxAmountActual( "0" );
                     orderDetailTempVO.setTaxAmountCost( "0" );
                     orderDetailTempVO.setTaxAmountSales( "0" );
                  }

                  // װ��OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }

               // ���÷���Э����ܽ��
               serviceContractTempVO.setBillAmountCompany( KANUtil.round( settlementTempDTO.getBillAmountCompany(), Integer.valueOf( ACCURACY ), ROUND ) );
               serviceContractTempVO.setBillAmountPersonal( KANUtil.round( settlementTempDTO.getBillAmountPersonal(), Integer.valueOf( ACCURACY ), ROUND ) );
               serviceContractTempVO.setCostAmountCompany( KANUtil.round( settlementTempDTO.getCostAmountCompany(), Integer.valueOf( ACCURACY ), ROUND ) );
               serviceContractTempVO.setCostAmountPersonal( KANUtil.round( settlementTempDTO.getCostAmountPersonal(), Integer.valueOf( ACCURACY ), ROUND ) );

               // ���ö������ܽ��
               this.getOrderHeaderTempVO().addBillAmountCompany( serviceContractTempVO.getBillAmountCompany() );
               this.getOrderHeaderTempVO().addBillAmountPersonal( serviceContractTempVO.getBillAmountPersonal() );
               this.getOrderHeaderTempVO().addCostAmountCompany( serviceContractTempVO.getCostAmountCompany() );
               this.getOrderHeaderTempVO().addCostAmountPersonal( serviceContractTempVO.getCostAmountPersonal() );

               // װ��ServiceContract
               fetchServiceContract( serviceContractTempVO, serviceContractDTO );
               settlementTempDTO.setServiceContractTempVO( serviceContractTempVO );

               // ���ڽ�����ϸ�����
               if ( settlementTempDTO.getOrderDetailTempVOs() != null
                     && settlementTempDTO.getOrderDetailTempVOs().size() > 0
                     && ( ( KANUtil.filterEmpty( serviceContractTempVO.getBillAmountCompany() ) != null && Double.valueOf( serviceContractTempVO.getBillAmountCompany() ) != 0 )
                           || ( KANUtil.filterEmpty( serviceContractTempVO.getBillAmountPersonal() ) != null && Double.valueOf( serviceContractTempVO.getBillAmountPersonal() ) != 0 )
                           || ( KANUtil.filterEmpty( serviceContractTempVO.getCostAmountCompany() ) != null && Double.valueOf( serviceContractTempVO.getCostAmountCompany() ) != 0 ) || ( KANUtil.filterEmpty( serviceContractTempVO.getCostAmountPersonal() ) != null && Double.valueOf( serviceContractTempVO.getCostAmountPersonal() ) != 0 ) ) )
               {
                  this.getSettlementTempDTOs().add( settlementTempDTO );
               }
            }

            // ��ʼ��RuleDiscount
            double ruleDiscount = 1;

            // �������������
            if ( this.getClientOrderHeaderDTO() != null && this.getClientOrderHeaderDTO().getClientOrderHeaderRuleVOs() != null
                  && this.getClientOrderHeaderDTO().getClientOrderHeaderRuleVOs().size() > 0 )
            {
               // ���������������
               for ( ClientOrderHeaderRuleVO clientOrderHeaderRuleVO : this.getClientOrderHeaderDTO().getClientOrderHeaderRuleVOs() )
               {
                  String ruleType = clientOrderHeaderRuleVO.getRuleType();
                  if ( ruleType == null || ruleType.trim().equals( "" ) || ruleType.trim().equals( "0" ) )
                  {
                     ruleType = null;
                  }
                  final double ruleValue = clientOrderHeaderRuleVO.getRuleValue() != null ? Double.valueOf( clientOrderHeaderRuleVO.getRuleValue() ) : 0;
                  final double ruleResult = clientOrderHeaderRuleVO.getRuleResult() != null ? Double.valueOf( clientOrderHeaderRuleVO.getRuleResult() ) : 0;

                  if ( ruleType != null )
                  {
                     // �����������ڵ��ڵ����
                     if ( ruleType.trim().equals( "1" ) )
                     {
                        if ( SETTLEMENT_NUMBER >= ruleValue )
                        {
                           ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                        }
                     }
                     // ���������ڵ��ڵ����
                     else if ( ruleType.trim().equals( "2" ) )
                     {
                        if ( this.getServiceFeeAmount() >= ruleValue )
                        {
                           ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                        }
                     }
                  }
               }
            }

            // ���������۴���Ӫҵ˰����
            if ( this.getSettlementTempDTOs() != null && this.getSettlementTempDTOs().size() > 0 )
            {
               // ��ʼ������Ӫ��
               double orderBillAmountCompany = 0;

               for ( SettlementTempDTO settlementTempDTO : this.getSettlementTempDTOs() )
               {
                  if ( settlementTempDTO.getOrderDetailTempVOs() != null && settlementTempDTO.getOrderDetailTempVOs().size() > 0 )
                  {
                     // ��ʼ������Э��Ӫҵ˰
                     double taxAmountSales = 0;
                     // ��ʼ������Э��Ӫ��
                     double contractBillAmountCompany = 0;

                     for ( OrderDetailTempVO orderDetailTempVO : settlementTempDTO.getOrderDetailTempVOs() )
                     {
                        // ������������������Ҫ����
                        if ( ruleDiscount < 1 )
                        {
                           // ��ʼ��ItemVO
                           final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( orderDetailTempVO.getItemId() );

                           if ( itemVO.getItemType() != null && itemVO.getItemType().trim().equals( "9" ) )
                           {
                              if ( orderDetailTempVO.getBillAmountCompany() != null && !orderDetailTempVO.getBillAmountCompany().trim().equals( "" ) )
                              {
                                 orderDetailTempVO.setBillAmountCompany( KANUtil.round( Double.valueOf( orderDetailTempVO.getBillAmountCompany() ) * ruleDiscount, Integer.valueOf( ACCURACY ), ROUND ) );
                              }

                              if ( orderDetailTempVO.getTaxAmountActual() != null && !orderDetailTempVO.getTaxAmountActual().trim().equals( "" ) )
                              {
                                 orderDetailTempVO.setTaxAmountActual( KANUtil.round( Double.valueOf( orderDetailTempVO.getTaxAmountActual() ) * ruleDiscount, Integer.valueOf( ACCURACY ), ROUND ) );
                              }

                              if ( orderDetailTempVO.getTaxAmountCost() != null && !orderDetailTempVO.getTaxAmountCost().trim().equals( "" ) )
                              {
                                 orderDetailTempVO.setTaxAmountCost( KANUtil.round( Double.valueOf( orderDetailTempVO.getTaxAmountCost() ) * ruleDiscount, Integer.valueOf( ACCURACY ), ROUND ) );
                              }

                              if ( orderDetailTempVO.getTaxAmountSales() != null && !orderDetailTempVO.getTaxAmountSales().trim().equals( "" ) )
                              {
                                 orderDetailTempVO.setTaxAmountSales( KANUtil.round( Double.valueOf( orderDetailTempVO.getTaxAmountSales() ) * ruleDiscount, Integer.valueOf( ACCURACY ), ROUND ) );
                              }

                           }
                        }

                        if ( orderDetailTempVO.getTaxAmountSales() != null && !orderDetailTempVO.getTaxAmountSales().trim().equals( "" ) )
                        {
                           taxAmountSales = taxAmountSales + Double.valueOf( orderDetailTempVO.getTaxAmountSales() );
                        }

                        // ����Э��Ӫ�ռ���
                        contractBillAmountCompany = contractBillAmountCompany + Double.valueOf( orderDetailTempVO.getBillAmountCompany() );
                     }

                     // ����Э��Ӫ�ռ��㣨��Ӫҵ˰��
                     contractBillAmountCompany = contractBillAmountCompany + taxAmountSales;
                     // ����Ӫ�ռ���
                     orderBillAmountCompany = orderBillAmountCompany + contractBillAmountCompany;

                     // ���ۡ���˰���������Э��Ӫ��
                     settlementTempDTO.getServiceContractTempVO().setBillAmountCompany( String.valueOf( contractBillAmountCompany ) );

                     // Ӫҵ˰����0�������Ӫҵ˰��Ŀ
                     if ( taxAmountSales > 0 )
                     {
                        // ��ʼ��Ӫҵ˰ItemVO
                        final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( "141" );

                        final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                        orderDetailTempVO.setItemId( itemVO.getItemId() );
                        orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                        orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                        orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                        orderDetailTempVO.setBase( String.valueOf( taxAmountSales ) );
                        orderDetailTempVO.setQuantity( "1" );
                        orderDetailTempVO.setDiscount( "1" );
                        orderDetailTempVO.setMultiple( "1" );
                        orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                        orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                        orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                        orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                        orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                        orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                        orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                        orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );
                        orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

                        // ��ʼ��ItemCap
                        double itemCap = 0;
                        if ( itemVO.getItemCap() != null )
                        {
                           itemCap = Double.valueOf( itemVO.getItemCap() );
                        }

                        // ��ʼ��ItemFloor
                        double itemFloor = 0;
                        if ( itemVO.getItemFloor() != null )
                        {
                           itemFloor = Double.valueOf( itemVO.getItemFloor() );
                        }

                        // ��ʼ��TaxAmountSales
                        taxAmountSales = Double.valueOf( taxAmountSales ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                              + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                        // Cap����
                        if ( itemCap != 0 && itemCap < taxAmountSales )
                        {
                           taxAmountSales = itemCap;
                        }

                        // Floor����
                        if ( itemFloor != 0 && itemFloor > taxAmountSales )
                        {
                           taxAmountSales = itemFloor;
                        }

                        // Sales TypeΪ��3���ǡ��������ʽ
                        if ( !SALES_TYPE.trim().equals( "3" ) )
                        {
                           orderDetailTempVO.setBillAmountCompany( KANUtil.round( taxAmountSales, Integer.valueOf( ACCURACY ), ROUND ) );
                           orderDetailTempVO.setCostAmountCompany( "0" );
                        }
                        orderDetailTempVO.setBillAmountPersonal( "0" );
                        orderDetailTempVO.setCostAmountPersonal( "0" );
                        orderDetailTempVO.setTaxAmountActual( "0" );
                        orderDetailTempVO.setTaxAmountCost( "0" );
                        orderDetailTempVO.setTaxAmountSales( "0" );

                        settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
                     }
                  }
               }

               // ���ۡ���˰�����趩��Ӫ��
               this.getOrderHeaderTempVO().setBillAmountCompany( String.valueOf( orderBillAmountCompany ) );

               // ����Order Amount
               this.getOrderHeaderTempVO().setOrderAmount( String.valueOf( ORDER_AMOUNT ) );
            }
         }
      }

      return true;
   }

   // װ��ClientOrderHeader
   private void fetchClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      if ( clientOrderHeaderVO != null )
      {
         // ��ʼ��OrderHeaderTempVO
         final OrderHeaderTempVO orderHeaderTempVO = new OrderHeaderTempVO();

         orderHeaderTempVO.setAccountId( clientOrderHeaderVO.getAccountId() );
         orderHeaderTempVO.setEntityId( clientOrderHeaderVO.getEntityId() );
         orderHeaderTempVO.setBusinessTypeId( clientOrderHeaderVO.getBusinessTypeId() );
         orderHeaderTempVO.setClientId( clientOrderHeaderVO.getClientId() );
         orderHeaderTempVO.setCorpId( clientOrderHeaderVO.getCorpId() );
         orderHeaderTempVO.setOrderId( clientOrderHeaderVO.getOrderHeaderId() );
         orderHeaderTempVO.setStartDate( clientOrderHeaderVO.getStartDate() );
         orderHeaderTempVO.setEndDate( clientOrderHeaderVO.getEndDate() );
         // ����Tax��Ϣ
         if ( TAX_VO != null )
         {
            orderHeaderTempVO.setTaxId( TAX_VO.getTaxId() );
            orderHeaderTempVO.setTaxNameZH( TAX_VO.getNameZH() );
            orderHeaderTempVO.setTaxNameEN( TAX_VO.getNameEN() );
            orderHeaderTempVO.setTaxRemark( "S: " + TAX_VO.getSaleTax() + "% / C: " + TAX_VO.getCostTax() + "%" );
         }
         orderHeaderTempVO.setBranch( clientOrderHeaderVO.getBranch() );
         orderHeaderTempVO.setOwner( clientOrderHeaderVO.getOwner() );
         orderHeaderTempVO.setMonthly( clientOrderHeaderVO.getMonthly() );
         orderHeaderTempVO.setDeleted( "1" );
         orderHeaderTempVO.setStatus( OrderHeaderTempVO.TRUE );

         this.setOrderHeaderTempVO( orderHeaderTempVO );
      }
   }

   // װ��ServiceContract
   private void fetchServiceContract( final ServiceContractTempVO serviceContractTempVO, final ServiceContractDTO serviceContractDTO ) throws KANException
   {
      if ( serviceContractDTO.getEmployeeContractVO() != null )
      {
         // ��ʼ��EmployeeContractVO
         final EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();
         serviceContractTempVO.setAccountId( employeeContractVO.getAccountId() );
         serviceContractTempVO.setEntityId( employeeContractVO.getEntityId() );
         serviceContractTempVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
         serviceContractTempVO.setClientId( employeeContractVO.getClientId() );
         serviceContractTempVO.setCorpId( employeeContractVO.getCorpId() );
         serviceContractTempVO.setOrderId( employeeContractVO.getOrderId() );
         serviceContractTempVO.setEmployeeId( employeeContractVO.getEmployeeId() );
         serviceContractTempVO.setEmployeeContractId( employeeContractVO.getContractId() );
         serviceContractTempVO.setStartDate( employeeContractVO.getStartDate() );
         serviceContractTempVO.setEndDate( employeeContractVO.getEndDate() );
         serviceContractTempVO.setBranch( employeeContractVO.getBranch() );
         serviceContractTempVO.setOwner( employeeContractVO.getOwner() );
         serviceContractTempVO.setMonthly( employeeContractVO.getMonthly() );
         serviceContractTempVO.setDeleted( "1" );
         serviceContractTempVO.setStatus( ServiceContractTempVO.TRUE );
      }

      if ( serviceContractDTO.getClientOrderHeaderVO() != null )
      {
         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

         // Salary Monthly����
         if ( KANUtil.filterEmpty( clientOrderHeaderVO.getSalaryMonth() ) != null && clientOrderHeaderVO.getSalaryMonth().equals( "2" ) )
         {
            serviceContractTempVO.setSalaryMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -1 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getSalaryMonth() ) != null && clientOrderHeaderVO.getSalaryMonth().equals( "3" ) )
         {
            serviceContractTempVO.setSalaryMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -2 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getSalaryMonth() ) != null && clientOrderHeaderVO.getSalaryMonth().equals( "4" ) )
         {
            serviceContractTempVO.setSalaryMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), 1 ) );
         }
         else
         {
            serviceContractTempVO.setSalaryMonth( clientOrderHeaderVO.getMonthly() );
         }

         // SB Monthly����
         if ( KANUtil.filterEmpty( clientOrderHeaderVO.getSbMonth() ) != null && clientOrderHeaderVO.getSbMonth().equals( "2" ) )
         {
            serviceContractTempVO.setSbMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -1 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getSbMonth() ) != null && clientOrderHeaderVO.getSbMonth().equals( "3" ) )
         {
            serviceContractTempVO.setSbMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -2 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getSbMonth() ) != null && clientOrderHeaderVO.getSbMonth().equals( "4" ) )
         {
            serviceContractTempVO.setSbMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), 1 ) );
         }
         else
         {
            serviceContractTempVO.setSbMonth( clientOrderHeaderVO.getMonthly() );
         }

         // CB Monthly����
         if ( KANUtil.filterEmpty( clientOrderHeaderVO.getCbMonth() ) != null && clientOrderHeaderVO.getCbMonth().equals( "2" ) )
         {
            serviceContractTempVO.setCbMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -1 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getCbMonth() ) != null && clientOrderHeaderVO.getCbMonth().equals( "3" ) )
         {
            serviceContractTempVO.setCbMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -2 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getCbMonth() ) != null && clientOrderHeaderVO.getCbMonth().equals( "4" ) )
         {
            serviceContractTempVO.setCbMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), 1 ) );
         }
         else
         {
            serviceContractTempVO.setCbMonth( clientOrderHeaderVO.getMonthly() );
         }

         // Fund Monthly����
         if ( KANUtil.filterEmpty( clientOrderHeaderVO.getFundMonth() ) != null && clientOrderHeaderVO.getFundMonth().equals( "2" ) )
         {
            serviceContractTempVO.setFundMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -1 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getFundMonth() ) != null && clientOrderHeaderVO.getFundMonth().equals( "3" ) )
         {
            serviceContractTempVO.setFundMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), -2 ) );
         }
         else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getFundMonth() ) != null && clientOrderHeaderVO.getFundMonth().equals( "4" ) )
         {
            serviceContractTempVO.setFundMonth( KANUtil.getMonthly( clientOrderHeaderVO.getMonthly(), 1 ) );
         }
         else
         {
            serviceContractTempVO.setFundMonth( clientOrderHeaderVO.getMonthly() );
         }
      }
   }

   // װ���籣
   private void fetchSB( final SettlementTempDTO settlementTempDTO, final List< SBDTO > sbDTOs ) throws KANException
   {
      if ( sbDTOs != null && sbDTOs.size() > 0 )
      {
         // �����籣����
         for ( SBDTO sbDTO : sbDTOs )
         {
            if ( sbDTO.getSbDetailVOs() != null && sbDTO.getSbDetailVOs().size() > 0 )
            {
               // ��ȡSBHeaderVO
               final SBHeaderVO sbHeaderVO = sbDTO.getSbHeaderVO();

               // �籣��������ͳ��
               if ( "7".equals( sbHeaderVO.getSbStatus() ) )
               {
                  if ( SB_SUPPLEMENTARY_MONTHS.get( sbHeaderVO.getSbSolutionId() ) != null )
                  {
                     SB_SUPPLEMENTARY_MONTHS.put( sbHeaderVO.getSbSolutionId(), SB_SUPPLEMENTARY_MONTHS.get( sbHeaderVO.getSbSolutionId() ) + 1 );
                  }
                  else
                  {
                     SB_SUPPLEMENTARY_MONTHS.put( sbHeaderVO.getSbSolutionId(), 1 );
                  }
               }

               for ( SBDetailVO sbDetailVO : sbDTO.getSbDetailVOs() )
               {
                  // ��ʼ��ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( sbDetailVO.getItemId() );

                  // ��ʼ��ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  // ��ʼ��ItemFloor
                  double itemFloor = 0;
                  if ( itemVO.getItemFloor() != null )
                  {
                     itemFloor = Double.valueOf( itemVO.getItemFloor() );
                  }

                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setHeaderId( sbDetailVO.getHeaderId() );
                  orderDetailTempVO.setDetailId( sbDetailVO.getDetailId() );
                  orderDetailTempVO.setDetailType( "1" );
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // �籣���̱���ʹ��Base From
                  orderDetailTempVO.setBase( "0" );
                  orderDetailTempVO.setQuantity( "1" );
                  orderDetailTempVO.setDiscount( "1" );
                  orderDetailTempVO.setMultiple( "1" );
                  orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                  orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                  orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                  orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                  orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                  orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                  orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                  orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

                  /** ��˾Ӫ�� */
                  // ��ʼ��BillAmountCompany
                  double billAmountCompany = Double.valueOf( sbDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < billAmountCompany )
                  {
                     billAmountCompany = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > billAmountCompany )
                  {
                     billAmountCompany = itemFloor;
                  }

                  // Sales TypeΪ��3���ǡ��������ʽ
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // ��ӳ���ֵ
                  addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                  /** �������� */
                  // ��ʼ��BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( sbDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < billAmountPersonal )
                  {
                     billAmountPersonal = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > billAmountPersonal )
                  {
                     billAmountPersonal = itemFloor;
                  }

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ��˾�ɱ� */
                  // ��ʼ��CostAmountCompany
                  double costAmountCompany = Double.valueOf( sbDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < costAmountCompany )
                  {
                     costAmountCompany = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > costAmountCompany )
                  {
                     costAmountCompany = itemFloor;
                  }

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ����֧�� */
                  // ��ʼ��CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( sbDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < costAmountPersonal )
                  {
                     costAmountPersonal = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > costAmountPersonal )
                  {
                     costAmountPersonal = itemFloor;
                  }

                  // ���˲��ֹ�˾�е��ж�
                  if ( KANUtil.filterEmpty( sbHeaderVO.getPersonalSBBurden() ) != null && KANUtil.filterEmpty( sbHeaderVO.getPersonalSBBurden() ).trim().equals( "1" ) )
                  {
                     orderDetailTempVO.addBillAmountCompany( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.addCostAmountCompany( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );
                  }

                  if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
                  {
                     orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setTaxAmountActual( "0" );
                     orderDetailTempVO.setTaxAmountCost( "0" );
                     orderDetailTempVO.setTaxAmountSales( "0" );
                  }

                  orderDetailTempVO.setDeleted( "1" );
                  orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

                  // װ��OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // װ���籣����
   private void fetchSBAdjustment( final SettlementTempDTO settlementTempDTO, final List< SBAdjustmentDTO > sbAdjustmentDTOs ) throws KANException
   {
      if ( sbAdjustmentDTOs != null && sbAdjustmentDTOs.size() > 0 )
      {
         // �����籣����
         for ( SBAdjustmentDTO sbAdjustmentDTO : sbAdjustmentDTOs )
         {
            if ( sbAdjustmentDTO.getSbAdjustmentDetailVOs() != null && sbAdjustmentDTO.getSbAdjustmentDetailVOs().size() > 0 )
            {
               // ��ȡSBAdjustmentHeaderVO
               final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentDTO.getSbAdjustmentHeaderVO();

               for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDTO.getSbAdjustmentDetailVOs() )
               {
                  // ��ʼ��ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

                  // ��ʼ��ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  // ��ʼ��ItemFloor
                  double itemFloor = 0;
                  if ( itemVO.getItemFloor() != null )
                  {
                     itemFloor = Double.valueOf( itemVO.getItemFloor() );
                  }

                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );
                  orderDetailTempVO.setDetailId( sbAdjustmentDetailVO.getAdjustmentDetailId() );
                  orderDetailTempVO.setDetailType( "3" );
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // �籣���̱���ʹ��Base From
                  orderDetailTempVO.setBase( "0" );
                  orderDetailTempVO.setQuantity( "1" );
                  orderDetailTempVO.setDiscount( "1" );
                  orderDetailTempVO.setMultiple( "1" );
                  orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                  orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                  orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                  orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                  orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                  orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                  orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                  orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

                  /** ��˾Ӫ�� */
                  // ��ʼ��BillAmountCompany
                  double billAmountCompany = Double.valueOf( sbAdjustmentDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < billAmountCompany )
                  {
                     billAmountCompany = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > billAmountCompany )
                  {
                     billAmountCompany = itemFloor;
                  }

                  // Sales TypeΪ��3���ǡ��������ʽ
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // ��ӳ���ֵ
                  addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                  /** �������� */
                  // ��ʼ��BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( sbAdjustmentDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < billAmountPersonal )
                  {
                     billAmountPersonal = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > billAmountPersonal )
                  {
                     billAmountPersonal = itemFloor;
                  }

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ��˾�ɱ� */
                  // ��ʼ��CostAmountCompany
                  double costAmountCompany = Double.valueOf( sbAdjustmentDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < costAmountCompany )
                  {
                     costAmountCompany = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > costAmountCompany )
                  {
                     costAmountCompany = itemFloor;
                  }

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ����֧�� */
                  // ��ʼ��CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( sbAdjustmentDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < costAmountPersonal )
                  {
                     costAmountPersonal = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > costAmountPersonal )
                  {
                     costAmountPersonal = itemFloor;
                  }

                  // ���˲��ֹ�˾�е��ж�
                  if ( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getPersonalSBBurden() ) != null
                        && KANUtil.filterEmpty( sbAdjustmentHeaderVO.getPersonalSBBurden() ).trim().equals( "1" ) )
                  {
                     orderDetailTempVO.addBillAmountCompany( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.addCostAmountCompany( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );
                  }

                  if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
                  {
                     orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setTaxAmountActual( "0" );
                     orderDetailTempVO.setTaxAmountCost( "0" );
                     orderDetailTempVO.setTaxAmountSales( "0" );
                  }

                  orderDetailTempVO.setDeleted( "1" );
                  orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

                  // װ��OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // װ���̱�
   private void fetchCB( final SettlementTempDTO settlementTempDTO, final List< CBDTO > cbDTOs ) throws KANException
   {
      if ( cbDTOs != null && cbDTOs.size() > 0 )
      {
         // �����̱�����
         for ( CBDTO cbDTO : cbDTOs )
         {
            if ( cbDTO.getCbDetailVOs() != null && cbDTO.getCbDetailVOs().size() > 0 )
            {
               for ( CBDetailVO cbDetailVO : cbDTO.getCbDetailVOs() )
               {
                  // ��ʼ��ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( cbDetailVO.getItemId() );

                  // ��ʼ��ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  // ��ʼ��ItemFloor
                  double itemFloor = 0;
                  if ( itemVO.getItemFloor() != null )
                  {
                     itemFloor = Double.valueOf( itemVO.getItemFloor() );
                  }

                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setHeaderId( cbDetailVO.getHeaderId() );
                  orderDetailTempVO.setDetailId( cbDetailVO.getDetailId() );
                  orderDetailTempVO.setDetailType( "2" );
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // �籣���̱���ʹ��Base From
                  orderDetailTempVO.setBase( "0" );
                  orderDetailTempVO.setQuantity( "1" );
                  orderDetailTempVO.setDiscount( "1" );
                  orderDetailTempVO.setMultiple( "1" );
                  orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                  orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                  orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                  orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                  orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                  orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                  orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                  orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

                  /** ��˾Ӫ�� */
                  // ��ʼ��BillAmountCompany
                  double billAmountCompany = Double.valueOf( cbDetailVO.getAmountSalesPrice() ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < billAmountCompany )
                  {
                     billAmountCompany = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > billAmountCompany )
                  {
                     billAmountCompany = itemFloor;
                  }

                  // Sales TypeΪ��3���ǡ��������ʽ
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // ��ӳ���ֵ
                  addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                  /** �������� */
                  // ��ʼ��BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( cbDetailVO.getAmountSalesPrice() ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < billAmountPersonal )
                  {
                     billAmountPersonal = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > billAmountPersonal )
                  {
                     billAmountPersonal = itemFloor;
                  }

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ��˾�ɱ� */
                  // ��ʼ��CostAmountCompany
                  double costAmountCompany = Double.valueOf( cbDetailVO.getAmountSalesCost() ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < costAmountCompany )
                  {
                     costAmountCompany = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > costAmountCompany )
                  {
                     costAmountCompany = itemFloor;
                  }

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ����֧�� */
                  // ��ʼ��CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( cbDetailVO.getAmountSalesCost() ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  // Cap����
                  if ( itemCap != 0 && itemCap < costAmountPersonal )
                  {
                     costAmountPersonal = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && itemFloor > costAmountPersonal )
                  {
                     costAmountPersonal = itemFloor;
                  }

                  orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
                  {
                     orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setTaxAmountActual( "0" );
                     orderDetailTempVO.setTaxAmountCost( "0" );
                     orderDetailTempVO.setTaxAmountSales( "0" );
                  }

                  orderDetailTempVO.setDeleted( "1" );
                  orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

                  // װ��OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // װ�ع��ʣ���ؿ�Ŀ��
   private void fetchSalary( final SettlementTempDTO settlementTempDTO, final ServiceContractDTO serviceContractDTO, final SickLeaveSalaryDTO sickLeaveSalaryDTO,
         final Calendar tsStartCalendar, final Calendar tsEndCalendar, final boolean incompleteCircle ) throws KANException
   {
      // ��ʼ��TimesheetDTO
      final TimesheetDTO timesheetDTO = serviceContractDTO.getTimesheetDTO();

      // �����ǲ - �п��ڵ����&& timesheetDTO.getTimesheetDetailVOs().size() > 0
      if ( timesheetDTO != null && timesheetDTO.getTimesheetHeaderVO() != null )
      {
         // ��ʼ�����Ƿ�ȫ�ڡ�
         boolean isNormal = true;

         if ( timesheetDTO.getTimesheetHeaderVO().getIsNormal() == null || !timesheetDTO.getTimesheetHeaderVO().getIsNormal().trim().equals( "1" ) )
         {
            isNormal = false;
         }

         // ��ʼ����������
         double totalWorkHoursBill = 0;
         double totalWorkDaysBill = 0;
         double totalWorkHoursCost = 0;
         double totalWorkDaysCost = 0;
         double totalFullHours = 0;
         double totalFullDays = 0;
         // �ۿ�Сʱ�������������ۿ - ����ItemId��
         DeductDTO totalDeductDaysBillDTO = new DeductDTO();
         DeductDTO totalDeductDaysCostDTO = new DeductDTO();
         // �ۿ�Сʱ�� - �������� - ����ItemId��
         DeductDTO totalDeductDaysBillDTO_BS = new DeductDTO();
         DeductDTO totalDeductDaysCostDTO_BS = new DeductDTO();
         // �ۿ��� - ����ItemId��
         Map< String, Double > totalDeductAmountBills = new HashMap< String, Double >();
         Map< String, Double > totalDeductAmountCosts = new HashMap< String, Double >();
         // ��ٿۿ��ܽ��
         double sickLeavePayDeductAmount = 0;
         // ÿ�²���ȫнСʱ��
         double fullpaySickLeaveHour = 0;

         //��ȡ�Ͷ���ͬ��ٹ�����ÿ�²���ȫ��Сʱ��
         List< EmployeeContractLeaveVO > employeeContractLeaveList = serviceContractDTO.getEmployeeContractLeaveVOs();
         if ( employeeContractLeaveList != null )
         {
            for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveList )
            {
               //��������Ϊ�������¡���ĿΪ������ȫн
               if ( "4".equals( employeeContractLeaveVO.getCycle() ) && "42".equals( employeeContractLeaveVO.getItemId() ) )
               {
                  fullpaySickLeaveHour = Double.valueOf( employeeContractLeaveVO.getBenefitQuantity() );
               }
            }
         }
         //�Ͷ���ͬľ��ÿ�²���ȫ��Сʱ�������ȡ���������ÿ�²���ȫ��Сʱ��
         if ( fullpaySickLeaveHour == 0 && clientOrderLeaveVOs.size() > 0 )
         {
            for ( ClientOrderLeaveVO clientOrderLeaveVO : clientOrderLeaveVOs )
            {
               //��������Ϊ�������¡���ĿΪ������ȫн
               if ( "4".equals( clientOrderLeaveVO.getCycle() ) && "42".equals( clientOrderLeaveVO.getItemId() ) )
               {
                  fullpaySickLeaveHour = Double.valueOf( clientOrderLeaveVO.getBenefitQuantity() );
               }
            }
         }

         // ��ʼ����������
         if ( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() != null && !timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays().trim().equals( "" ) )
         {
            totalWorkDaysBill = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() );
            totalWorkDaysCost = totalWorkDaysBill;
            totalWorkHoursBill = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() );
            totalWorkHoursCost = totalWorkHoursBill;
         }

         // ��ʼ��ȫ��Сʱ��
         if ( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() != null && !timesheetDTO.getTimesheetHeaderVO().getTotalFullHours().trim().equals( "" ) )
         {
            totalFullHours = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() );
         }

         // ��ʼ��ȫ������
         if ( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() != null && !timesheetDTO.getTimesheetHeaderVO().getTotalFullDays().trim().equals( "" ) )
         {
            totalFullDays = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() );
         }

         // ����Сʱ����ȫ��Сʱ����������
         final ConstantsDTO constantsDTO_410 = CONSTANTS_VALUE.get( "410" );
         constantsDTO_410.addConstantsDay( timesheetDTO.getTimesheetHeaderVO().getStartDate(), timesheetDTO.getTimesheetHeaderVO().getEndDate(), totalWorkDaysBill );
         CONSTANTS_VALUE.put( "410", constantsDTO_410 );

         final ConstantsDTO constantsDTO_411 = CONSTANTS_VALUE.get( "411" );
         constantsDTO_411.addConstantsDay( timesheetDTO.getTimesheetHeaderVO().getStartDate(), timesheetDTO.getTimesheetHeaderVO().getEndDate(), totalWorkDaysBill );
         CONSTANTS_VALUE.put( "411", constantsDTO_411 );

         final ConstantsDTO constantsDTO_412 = CONSTANTS_VALUE.get( "412" );
         constantsDTO_412.addConstantsDay( timesheetDTO.getTimesheetHeaderVO().getStartDate(), timesheetDTO.getTimesheetHeaderVO().getEndDate(), totalWorkDaysBill );
         CONSTANTS_VALUE.put( "412", constantsDTO_412 );

         final ConstantsDTO constantsDTO_422 = CONSTANTS_VALUE.get( "422" );
         constantsDTO_422.addConstantsDay( timesheetDTO.getTimesheetHeaderVO().getStartDate(), timesheetDTO.getTimesheetHeaderVO().getEndDate(), totalWorkDaysBill );
         CONSTANTS_VALUE.put( "422", constantsDTO_422 );
         //��������ٵ�itemId
         List< String > targetItemIds = new ArrayList< String >();
         /** ȱ��������ݼٿ�Ŀ��������Ǵ�н�򲿷ִ�н��������Сʱ�� */
         // Modify by siuvan @2014-12-30 ¹����ٲ��ۿ�
         if ( !isNormal )
         {
            if ( timesheetDTO.getLeaveDTOs() != null && timesheetDTO.getLeaveDTOs().size() > 0 )
            {

               for ( LeaveDTO leaveDTO : timesheetDTO.getLeaveDTOs() )
               {
                  // ¹����ٲ��ۿ�
                  if ( leaveDTO.getLeaveHeaderVO() != null && KANUtil.filterEmpty( leaveDTO.getLeaveHeaderVO().getDataFrom() ) != null
                        && leaveDTO.getLeaveHeaderVO().getDataFrom().equals( "2" ) )
                     continue;

                  if ( leaveDTO.getLeaveDetailVOs() != null && leaveDTO.getLeaveDetailVOs().size() > 0 )
                  {
                     for ( LeaveDetailVO leaveDetailVO : leaveDTO.getLeaveDetailVOs() )
                     {
                        // ���ڿ��ڱ�Χ�ڵ���٣�ֱ��������һ��ѭ��
                        if ( KANUtil.filterEmpty( leaveDetailVO.getTimesheetId() ) == null )
                        {
                           continue;
                        }

                        // ��ʼ��ItemVO
                        final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( leaveDTO.getLeaveHeaderVO().getItemId() );

                        targetItemIds.add( itemVO.getItemId() );

                        double leaveHours = 0;

                        // �����׼����û������
                        if ( "3".equals( leaveDetailVO.getStatus() ) && "1".equals( leaveDetailVO.getRetrieveStatus() ) )
                        {
                           leaveHours = Double.valueOf( leaveDetailVO.getEstimateBenefitHours() != null ? leaveDetailVO.getEstimateBenefitHours() : "0" )
                                 + Double.valueOf( leaveDetailVO.getEstimateLegalHours() != null ? leaveDetailVO.getEstimateLegalHours() : "0" );
                        }
                        // ��١����ٶ���׼
                        else if ( "3".equals( leaveDetailVO.getStatus() ) && "3".equals( leaveDetailVO.getRetrieveStatus() ) )
                        {
                           leaveHours = Double.valueOf( leaveDetailVO.getActualBenefitHours() != null ? leaveDetailVO.getActualBenefitHours() : "0" )
                                 + Double.valueOf( leaveDetailVO.getActualLegalHours() != null ? leaveDetailVO.getActualLegalHours() : "0" );
                        }

                        // ��ʼ��TimesheetDetailVO
                        final TimesheetDetailVO timesheetDetailVO = timesheetDTO.getTimesheetDetailVOByDay( leaveDetailVO.getDay() );

                        if ( timesheetDetailVO != null )
                        {
                           // ��ʼ��TimesheetDetailVO��ʣ��GAPСʱ��
                           double leftGapHours = Double.valueOf( timesheetDetailVO.getFullHours() != null ? timesheetDetailVO.getFullHours() : "0" )
                                 - Double.valueOf( timesheetDetailVO.getNorateWorkHours() != null ? timesheetDetailVO.getNorateWorkHours() : "0" );

                           // �ݼ�Сʱ����ʣ��GAPСʱ������ֵ
                           if ( leaveHours > leftGapHours )
                           {
                              leaveHours = leftGapHours;
                           }
                        }

                        // ����δ����Сʱ��
                        if ( timesheetDetailVO != null )
                        {
                           timesheetDetailVO.addNorateWorkHours( String.valueOf( leaveHours ) );
                        }

                        // ��ʼ��EmployeeVO
                        final EmployeeVO employeeVO = serviceContractDTO.getEmployeeVO();

                        // ��ʼ��EmployeeContractVO
                        final EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();

                        // ��ʼ���Զ�����ٴ�н���ʣ���ٿۿ��ÿСʱ��
                        double sickLeavePayRate = -1;
                        double sickLeavePayDeduct = 0;

                        if ( employeeVO != null && employeeContractVO != null )
                        {
                           sickLeavePayRate = getSickLeavePayRate( sickLeaveSalaryDTO, itemVO.getItemId(), employeeVO.getStartWorkDate(), employeeVO.getOnboardDate(), employeeContractVO.getStartDate(), employeeContractVO.getMonthly(), timesheetDTO.getTimesheetHeaderVO().getEndDate() );
                           sickLeavePayDeduct = getSickLeavePayDeduct( sickLeaveSalaryDTO, itemVO.getItemId(), employeeVO.getStartWorkDate(), employeeVO.getOnboardDate(), employeeContractVO.getStartDate(), employeeContractVO.getMonthly(), timesheetDTO.getTimesheetHeaderVO().getEndDate() );
                        }

                        // ��ٿۿ��ۼ�
                        sickLeavePayDeductAmount = sickLeavePayDeductAmount + sickLeavePayDeduct * leaveHours;

                        // �ô�н�ٻ򲿷ִ�н�ٻز�����Сʱ���͹�������
                        if ( KANUtil.jasonArrayToStringList( serviceContractDTO.getClientOrderHeaderVO().getExcludeDivideItemIds() ).contains( itemVO.getItemId() ) )
                        {
                           totalWorkHoursCost = totalWorkHoursCost + leaveHours;
                           totalWorkDaysCost = totalFullDays * totalWorkHoursCost / totalFullHours;
                           totalWorkHoursBill = totalWorkHoursBill + leaveHours;
                           totalWorkDaysBill = totalFullDays * totalWorkHoursBill / totalFullHours;
                           // �ز���������ϸ
                           if ( timesheetDetailVO != null )
                           {
                              if ( KANUtil.filterEmpty( itemVO.getBillRateCompany() ) != null || sickLeavePayRate >= 0 )
                              {
                                 timesheetDetailVO.addAdditionalWorkHoursBill( String.valueOf( leaveHours ) );
                              }
                              if ( KANUtil.filterEmpty( itemVO.getCostRateCompany() ) != null || sickLeavePayRate >= 0 )
                              {
                                 timesheetDetailVO.addAdditionalWorkHoursCost( String.valueOf( leaveHours ) );
                              }
                           }
                           continue;
                        }

                        //�۳�����ȫнСʱ��
                        if ( fullpaySickLeaveHour > 0 && "10253".equals( itemVO.getItemId() ) )
                        {
                           if ( fullpaySickLeaveHour - leaveHours >= 0 )
                           {
                              fullpaySickLeaveHour = fullpaySickLeaveHour - leaveHours;
                              totalWorkHoursCost = totalWorkHoursCost + leaveHours;
                              totalWorkDaysCost = totalFullDays * totalWorkHoursCost / totalFullHours;
                              totalWorkHoursBill = totalWorkHoursBill + leaveHours;
                              totalWorkDaysBill = totalFullDays * totalWorkHoursBill / totalFullHours;
                              if ( KANUtil.filterEmpty( itemVO.getBillRateCompany() ) != null || sickLeavePayRate >= 0 )
                              {
                                 timesheetDetailVO.addAdditionalWorkHoursBill( String.valueOf( leaveHours ) );
                              }
                              if ( KANUtil.filterEmpty( itemVO.getCostRateCompany() ) != null || sickLeavePayRate >= 0 )
                              {
                                 timesheetDetailVO.addAdditionalWorkHoursCost( String.valueOf( leaveHours ) );
                              }
                              continue;
                           }
                        }
                        // ��˾����
                        if ( KANUtil.filterEmpty( itemVO.getBillRateCompany() ) != null || sickLeavePayRate >= 0 )
                        {
                           // ����������ٿۿ�Сʱ - Bill
                           totalDeductDaysBillDTO.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) ) * totalFullDays / totalFullHours );
                           totalDeductDaysBillDTO_BS.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) )
                                 * ( sickLeavePayRate >= 0 ? ( ( 100 - sickLeavePayRate ) * Double.valueOf( itemVO.getBillRateCompany() ) ) / 10000
                                       : Double.valueOf( itemVO.getBillRateCompany() ) / 100 ) * totalFullDays / totalFullHours );
                           // �ز���������ϸ
                           if ( timesheetDetailVO != null )
                           {
                              timesheetDetailVO.addAdditionalWorkHoursBill( String.valueOf( leaveHours ) );
                           }

                           // ��Item Bill Rate���۵Ĺ���Сʱ��
                           final ConstantsDTO tempConstantsDTO_422 = CONSTANTS_VALUE.get( "422" );
                           tempConstantsDTO_422.addConstantsDay( null, null, ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours : ( leaveHours - fullpaySickLeaveHour ) )
                                 * ( sickLeavePayRate >= 0 ? sickLeavePayRate * Double.valueOf( itemVO.getCostRateCompany() ) / 10000
                                       : Double.valueOf( itemVO.getBillRateCompany() ) ) / 100 );
                           CONSTANTS_VALUE.put( "422", tempConstantsDTO_422 );
                        }

                        // ��˾�ɱ�
                        if ( KANUtil.filterEmpty( itemVO.getCostRateCompany() ) != null || sickLeavePayRate >= 0 )
                        {
                           // ����������ٿۿ�Сʱ - Cost
                           totalDeductDaysCostDTO.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) ) * totalFullDays / totalFullHours );
                           totalDeductDaysCostDTO_BS.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) )
                                 * ( sickLeavePayRate >= 0 ? ( ( 100 - sickLeavePayRate ) * Double.valueOf( itemVO.getCostRateCompany() ) ) / 10000
                                       : Double.valueOf( itemVO.getCostRateCompany() ) / 100 ) * totalFullDays / totalFullHours );
                           // �ز���������ϸ
                           if ( timesheetDetailVO != null )
                           {
                              timesheetDetailVO.addAdditionalWorkHoursCost( String.valueOf( leaveHours ) );
                           }
                        }
                        //����ȫнСʱ������Ϊ��0
                        fullpaySickLeaveHour = 0;
                     }
                  }
               }
            }
         }

         final String onBoardDate = serviceContractDTO.getEmployeeVO().getOnboardDate();
         final String resignDate = serviceContractDTO.getEmployeeContractVO().getResignDate();
         final String monthly = this.getClientOrderHeaderDTO().getClientOrderHeaderVO().getMonthly();
         // �ж��Ƿ�����ְ��������ְ��
         final boolean isFirstOrLastWorkMonth = isFirstOrLastWorkMonth( onBoardDate, resignDate, monthly, serviceContractDTO, this.serviceContractDTOs );
         final boolean hasMoreContract = hasMoreContracts( serviceContractDTO, this.serviceContractDTOs );

         /** ���ʡ�����������ͱ�����Ŀ���� */
         for ( EmployeeContractSalaryVO employeeContractSalaryVO : serviceContractDTO.getEmployeeContractSalaryVOs() )
         {
            // ��ʼ����Ŀ��ʼʱ��
            Calendar salaryStartCalendar = null;
            if ( KANUtil.filterEmpty( employeeContractSalaryVO.getStartDate() ) != null )
            {
               salaryStartCalendar = KANUtil.createCalendar( employeeContractSalaryVO.getStartDate() );
            }

            // ��ʼ����Ŀ����ʱ��
            Calendar salaryEndCalendar = null;
            if ( KANUtil.filterEmpty( employeeContractSalaryVO.getEndDate() ) != null )
            {
               salaryEndCalendar = KANUtil.createCalendar( employeeContractSalaryVO.getEndDate() );
            }
            else
            {
               // ���Ϊ��������ʱ��
               salaryEndCalendar = Calendar.getInstance();
               salaryEndCalendar.set( 9999, 12, 31, 0, 0, 0 );
            }

            if ( withinCircle( tsStartCalendar, tsEndCalendar, employeeContractSalaryVO.getCycle(), salaryStartCalendar, salaryEndCalendar ) )
            {
               // ������н�� - ��Ŀ����������
               boolean incompleteSalary = false;

               // н�� ��Ч��ʼ���ڴ��ڿ��ڱ���ʼ����
               if ( salaryStartCalendar != null && tsStartCalendar != null && KANUtil.getDays( salaryStartCalendar ) > KANUtil.getDays( tsStartCalendar ) )
               {
                  incompleteSalary = true;
               }

               // н�� ��Ч��������С�ڿ��ڱ��������
               if ( salaryEndCalendar != null && tsEndCalendar != null && KANUtil.getDays( salaryEndCalendar ) < KANUtil.getDays( tsEndCalendar ) )
               {
                  incompleteSalary = true;
               }

               // ��ʼ��ItemVO
               final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( employeeContractSalaryVO.getItemId() );

               //��Ŀ��������Ϊ������
               if ( "4".equals( itemVO.getCalculateType() ) )
               {
                  continue;
               }
               // ��ʼ��ItemCap
               double itemCap = 0;
               if ( itemVO.getItemCap() != null )
               {
                  itemCap = Double.valueOf( itemVO.getItemCap() );
               }

               if ( KANUtil.filterEmpty( employeeContractSalaryVO.getResultCap() ) != null )
               {
                  if ( itemCap == 0 || itemCap > Double.valueOf( employeeContractSalaryVO.getResultCap() ) )
                  {
                     itemCap = Double.valueOf( employeeContractSalaryVO.getResultCap() );
                  }
               }

               // ��ʼ��ItemFloor
               double itemFloor = 0;
               if ( itemVO.getItemFloor() != null )
               {
                  itemFloor = Double.valueOf( itemVO.getItemFloor() );
               }

               if ( KANUtil.filterEmpty( employeeContractSalaryVO.getResultFloor() ) != null )
               {
                  if ( itemFloor == 0 || itemFloor < Double.valueOf( employeeContractSalaryVO.getResultFloor() ) )
                  {
                     itemFloor = Double.valueOf( employeeContractSalaryVO.getResultFloor() );
                  }
               }

               // ��ʼ��Base
               double base = 0;
               if ( employeeContractSalaryVO.getBase() != null )
               {
                  base = Double.valueOf( employeeContractSalaryVO.getBase() );
               }

               //��ĿΪȫ�ڽ������Ϳ�Ŀ���Ƿ�ֱ������Ϊ���ǡ����Ƿ���Ϊ�����գ�û�й�ѡ
               if ( "13".equals( itemVO.getItemType() ) && "1".equals( employeeContractSalaryVO.getIsDeduct() ) )
               {
                  if ( targetItemIds.size() > 0 && Collections.disjoint( targetItemIds, KANUtil.jasonArrayToStringList( employeeContractSalaryVO.getExcludeDivideItemIds() ) ) )
                  {
                     base = 0;
                  }
               }

               // ��ʼ��Percentage
               double percentage = 1;
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getPercentage() ) != null
                     && Double.valueOf( employeeContractSalaryVO.getPercentage() ) != 0 )
               {
                  percentage = Double.valueOf( employeeContractSalaryVO.getPercentage() ) / 100;
               }

               // ��ʼ��Fix
               double fix = 0;
               if ( employeeContractSalaryVO != null && employeeContractSalaryVO.getFix() != null )
               {
                  fix = Double.valueOf( employeeContractSalaryVO.getFix() );
               }

               // ��ʼ��Discount
               double discount = 1;
               if ( employeeContractSalaryVO != null && employeeContractSalaryVO.getDiscount() != null )
               {
                  discount = Double.valueOf( employeeContractSalaryVO.getDiscount() ) / 100;
               }

               // ��ʼ��Multiple
               double multiple = 1;
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getMultiple(), "0" ) != null )
               {
                  multiple = Double.valueOf( employeeContractSalaryVO.getDecodeMultiple() );
               }

               // ��ʼ��SalaryType
               String salaryType = "1";
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getSalaryType(), "0" ) != null )
               {
                  salaryType = employeeContractSalaryVO.getSalaryType().trim();
               }

               // ��ʼ��DivideType
               String divideType = "1";
               String leaveDivideType = "1";
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getDivideType(), "0" ) != null )
               {
                  divideType = employeeContractSalaryVO.getDivideType().trim();
                  leaveDivideType = employeeContractSalaryVO.getDivideType().trim();
               }

               // ���������������������ʼ��DivideTypeIncomplete -> DivideType
               if ( ( incompleteCircle ) && employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getDivideTypeIncomplete(), "0" ) != null )
               {
                  divideType = employeeContractSalaryVO.getDivideTypeIncomplete().trim();
               }

               if ( divideType.equals( "4" ) )
               {
                  leaveDivideType = "4";
               }

               // Base���㣬��BaseFrom��Formular
               base = getFormularValue( employeeContractSalaryVO.getFormular(), ( getBaseFromValue( settlementTempDTO, employeeContractSalaryVO.getBaseFrom(), base ) * percentage + fix )
                     * discount * multiple, null );

               final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
               orderDetailTempVO.setItemId( itemVO.getItemId() );
               orderDetailTempVO.setItemNo( itemVO.getItemNo() );
               orderDetailTempVO.setNameZH( itemVO.getNameZH() );
               orderDetailTempVO.setNameEN( itemVO.getNameEN() );
               // �����Կ�Ŀ��ʹ��Base From
               orderDetailTempVO.setBase( String.valueOf( base ) );
               orderDetailTempVO.setQuantity( "1" );
               orderDetailTempVO.setDiscount( String.valueOf( discount ) );
               orderDetailTempVO.setMultiple( String.valueOf( multiple ) );
               orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
               orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
               orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
               orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
               orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
               orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
               orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
               orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

               // ��ʼ����Ŀ���
               double itemAmountBill = 0;
               double itemAmountCost = 0;

               if ( base != 0 )
               {
                  // ��ʼ������Сʱ���͹�������
                  if ( timesheetDTO.getTimesheetDetailVOs() == null || timesheetDTO.getTimesheetDetailVOs().size() == 0 )
                  {
                     totalWorkHoursBill = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() );
                     totalWorkHoursCost = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() );
                  }
                  else
                  {
                     totalWorkHoursBill = statWorkHours( timesheetDTO.getTimesheetDetailVOs(), salaryStartCalendar, salaryEndCalendar, BILL );
                     totalWorkHoursCost = statWorkHours( timesheetDTO.getTimesheetDetailVOs(), salaryStartCalendar, salaryEndCalendar, COST );
                  }

                  totalWorkDaysBill = totalFullDays * totalWorkHoursBill / totalFullHours;
                  totalWorkDaysCost = totalFullDays * totalWorkHoursCost / totalFullHours;

                  // �������Сʱ�����������ܹ�����ȫ��ʱ��
                  if ( totalWorkHoursBill == totalFullHours || totalWorkHoursCost == totalFullHours || totalWorkDaysBill == totalFullDays || totalWorkDaysCost == totalFullDays )
                  {
                     isNormal = true;
                  }
                  else
                  {
                     isNormal = false;
                  }

                  // ���¼�н
                  if ( salaryType.equals( "1" ) )
                  {
                     // ��������к�ͬ���ӵ��������н�귽�����ӡ�����divideType = 2����
                     if ( hasMoreContract || incompleteSalary && !"1".equals( divideType ) )
                     {
                        divideType = "2";
                     }
                     // ȫ��
                     if ( isNormal )
                     {
                        itemAmountBill = base;
                        itemAmountCost = base;
                     }
                     // ȱ��
                     else
                     {
                        // ������
                        if ( divideType.equals( "1" ) )
                        {
                           itemAmountBill = base;
                           itemAmountCost = base;
                        }
                        // ����ƽ����н��������(����)
                        else if ( divideType.equals( "3" ) )
                        {
                           itemAmountBill = base * totalWorkDaysBill / AVG_SALARY_DAYS_PER_MONTH;

                           // �����������������Base����������
                           if ( itemAmountBill >= base )
                           {
                              itemAmountBill = base - base * ( totalFullDays - totalWorkDaysBill ) / AVG_SALARY_DAYS_PER_MONTH;
                           }

                           itemAmountCost = base * totalWorkDaysCost / AVG_SALARY_DAYS_PER_MONTH;

                           // �����������������Base����������
                           if ( itemAmountCost >= base )
                           {
                              itemAmountCost = base - base * ( totalFullDays - totalWorkDaysCost ) / AVG_SALARY_DAYS_PER_MONTH;
                           }
                        }
                        // ����ƽ����н��������(����)
                        else if ( divideType.equals( "5" ) )
                        {
                           // ��������  *��1 - δ��������/21.75��
                           if ( isFirstOrLastWorkMonth )
                           {
                              itemAmountBill = base * ( 1 - ( totalFullDays - totalWorkDaysBill ) / AVG_SALARY_DAYS_PER_MONTH );
                              itemAmountCost = base * ( 1 - ( totalFullDays - totalWorkDaysCost ) / AVG_SALARY_DAYS_PER_MONTH );
                           }
                           else
                           {
                              // �����������ְ�£����divideTypeΪ3 һ���㷨
                              itemAmountBill = base * totalWorkDaysBill / AVG_SALARY_DAYS_PER_MONTH;

                              // �����������������Base����������
                              if ( itemAmountBill >= base )
                              {
                                 itemAmountBill = base - base * ( totalFullDays - totalWorkDaysBill ) / AVG_SALARY_DAYS_PER_MONTH;
                              }

                              itemAmountCost = base * totalWorkDaysCost / AVG_SALARY_DAYS_PER_MONTH;

                              // �����������������Base����������
                              if ( itemAmountCost >= base )
                              {
                                 itemAmountCost = base - base * ( totalFullDays - totalWorkDaysCost ) / AVG_SALARY_DAYS_PER_MONTH;
                              }
                           }

                        }
                        // ����Ȼ����������
                        else if ( divideType.equals( "4" ) )
                        {
                           double gapDays = DAYS - totalFullDays;

                           itemAmountBill = base * ( totalWorkDaysBill + timesheetDTO.getNonWorkingDays( salaryStartCalendar, salaryEndCalendar ) ) / ( totalFullDays + gapDays );
                           itemAmountCost = base * ( totalWorkDaysCost + timesheetDTO.getNonWorkingDays( salaryStartCalendar, salaryEndCalendar ) ) / ( totalFullDays + gapDays );
                        }
                        // ���¹�����������
                        else
                        {
                           itemAmountBill = base * totalWorkDaysBill / totalFullDays;
                           itemAmountCost = base * totalWorkDaysCost / totalFullDays;
                        }
                     }

                     // ��ٿۿ����
                     if ( ( totalDeductDaysBillDTO != null && totalDeductDaysBillDTO.getDeductDayDTOs() != null && totalDeductDaysBillDTO.getDeductDayDTOs().size() > 0 )
                           || ( totalDeductDaysCostDTO != null && totalDeductDaysCostDTO.getDeductDayDTOs() != null && totalDeductDaysCostDTO.getDeductDayDTOs().size() > 0 ) )
                     {
                        for ( String key : totalDeductDaysBillDTO.getItems() )
                        {
                           double totalDeductDaysBill = totalDeductDaysBillDTO.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );
                           double totalDeductDaysCost = totalDeductDaysCostDTO.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );
                           double totalDeductDaysBill_BS = totalDeductDaysBillDTO_BS.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );
                           double totalDeductDaysCost_BS = totalDeductDaysCostDTO_BS.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );

                           // ������ʱ�ۿ����� - ����������
                           final Map< String, Double > tempTotalDeductDaysBill = new HashMap< String, Double >();
                           tempTotalDeductDaysBill.put( key, totalDeductDaysBill );
                           final Map< String, Double > tempTotalDeductDaysCost = new HashMap< String, Double >();
                           tempTotalDeductDaysCost.put( key, totalDeductDaysCost );
                           final Map< String, Double > tempTotalDeductDaysBill_BS = new HashMap< String, Double >();
                           tempTotalDeductDaysBill_BS.put( key, totalDeductDaysBill_BS );
                           final Map< String, Double > tempTotalDeductDaysCostBS = new HashMap< String, Double >();
                           tempTotalDeductDaysCostBS.put( key, totalDeductDaysCost_BS );

                           // ��ʼ��ItemGroupDTO
                           final ItemGroupDTO itemGroupDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemGroupDTOByItemId( key, "1" );

                           // ��ʼ��BindItemId����3����ָ��ٿۿ�
                           String bindItemId = "3";

                           if ( itemGroupDTO != null && itemGroupDTO.getItemGroupVO() != null )
                           {
                              bindItemId = itemGroupDTO.getItemGroupVO().getBindItemId();
                           }

                           // ��ʼ��Total Bill Deduct Days 
                           final double totalBillDeductDays = ( itemVO != null && itemVO.getItemType().equals( "1" ) ) ? getTotalDeductDays( tempTotalDeductDaysBill_BS, new ArrayList< String >() )
                                 : getTotalDeductDays( tempTotalDeductDaysBill, KANUtil.jasonArrayToStringList( employeeContractSalaryVO.getExcludeDivideItemIds() ) );

                           // ��ʼ��Total Cost Deduct Days 
                           final double totalCostDeductDays = ( itemVO != null && itemVO.getItemType().equals( "1" ) ) ? getTotalDeductDays( tempTotalDeductDaysCostBS, new ArrayList< String >() )
                                 : getTotalDeductDays( tempTotalDeductDaysCost, KANUtil.jasonArrayToStringList( employeeContractSalaryVO.getExcludeDivideItemIds() ) );

                           // ����ƽ����н��������
                           if ( leaveDivideType.equals( "3" ) )
                           {

                              double tempTotalDeductAmountBill = base * totalBillDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // �����������������Base����������
                              if ( tempTotalDeductAmountBill > base )
                              {
                                 tempTotalDeductAmountBill = base - base * ( totalFullDays - totalBillDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }

                              //�������==ȫ�¹�������,����ٿۿ�����ڻ�������
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountBill = base * totalBillDeductDays / totalFullDays;
                              }

                              // ��ٿۿ��ۼ�
                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + tempTotalDeductAmountBill ) );

                              // ��ǰ��Ŀ��ٿۿ�
                              double tempTotalDeductAmountCost = base * totalCostDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // �����������������Base����������
                              if ( tempTotalDeductAmountCost >= base )
                              {
                                 tempTotalDeductAmountCost = base - base * ( totalFullDays - totalCostDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }
                              //�������==ȫ�¹�������,����ٿۿ�����ڻ�������
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountCost = base * totalBillDeductDays / totalFullDays;
                              }

                              // ��ٿۿ��ۼ�
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + tempTotalDeductAmountCost ) );
                           }
                           // ���¹�����������
                           else if ( leaveDivideType.equals( "2" ) )
                           {
                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + base
                                    * totalBillDeductDays / totalFullDays ) );
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + base
                                    * totalCostDeductDays / totalFullDays ) );
                           }
                           // ����Ȼ����������
                           else if ( leaveDivideType.equals( "4" ) )
                           {
                              double gapDays = DAYS - totalFullDays;

                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + base
                                    * totalBillDeductDays / ( totalFullDays + gapDays ) ) );
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + base
                                    * totalCostDeductDays / ( totalFullDays + gapDays ) ) );
                           }
                           // ����ƽ������-  ��ٿۿ����ƽ������һ��
                           else if ( leaveDivideType.equals( "5" ) )
                           {
                              double tempTotalDeductAmountBill = base * totalBillDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // �����������������Base����������
                              if ( tempTotalDeductAmountBill > base )
                              {
                                 tempTotalDeductAmountBill = base - base * ( totalFullDays - totalBillDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }

                              //�������==ȫ�¹�������,����ٿۿ�����ڻ�������
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountBill = base * totalBillDeductDays / totalFullDays;
                              }

                              // ��ٿۿ��ۼ�
                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + tempTotalDeductAmountBill ) );

                              // ��ǰ��Ŀ��ٿۿ�
                              double tempTotalDeductAmountCost = base * totalCostDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // �����������������Base����������
                              if ( tempTotalDeductAmountCost >= base )
                              {
                                 tempTotalDeductAmountCost = base - base * ( totalFullDays - totalCostDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }
                              //�������==ȫ�¹�������,����ٿۿ�����ڻ�������
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountCost = base * totalBillDeductDays / totalFullDays;
                              }

                              // ��ٿۿ��ۼ�
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + tempTotalDeductAmountCost ) );
                           }
                        }
                     }
                  }
                  // ��Сʱ��н
                  else if ( salaryType.equals( "2" ) )
                  {
                     itemAmountBill = base * totalWorkHoursBill;
                     itemAmountCost = base * totalWorkHoursCost;
                  }

                  // Bill Cap����
                  if ( itemCap != 0 && itemAmountBill > itemCap )
                  {
                     itemAmountBill = itemCap;
                  }

                  // Bill Floor����
                  if ( itemFloor != 0 && itemAmountBill < itemFloor )
                  {
                     itemAmountBill = itemFloor;
                  }

                  // Cost Cap����
                  if ( itemCap != 0 && itemAmountCost > itemCap )
                  {
                     itemAmountCost = itemCap;
                  }

                  // Cost Floor����
                  if ( itemFloor != 0 && itemAmountCost < itemFloor )
                  {
                     itemAmountCost = itemFloor;
                  }
               }

               /** ��˾Ӫ�� */
               // ��ʼ��BillAmountCompany
               double billAmountCompany = Double.valueOf( itemAmountBill ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

               // Sales TypeΪ��3���ǡ��������ʽ
               orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               // ��ӳ���ֵ
               addConstantsValue( itemVO.getItemType(), employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), base, true );
               addConstantsValue( itemVO.getItemType(), employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), billAmountCompany, false );

               // �������ʺ͹��ʵ������⴦��
               if ( itemVO.getItemId().trim().equals( "1" ) || itemVO.getItemId().trim().equals( "2" ) )
               {
                  final ConstantsDTO constantsDTO_401 = CONSTANTS_VALUE.get( "401" );
                  constantsDTO_401.addConstantsDay( employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), base );
                  CONSTANTS_VALUE.put( "401", constantsDTO_401 );

                  final ConstantsDTO constantsDTO_405 = CONSTANTS_VALUE.get( "405" );
                  constantsDTO_405.addConstantsDay( employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), billAmountCompany );
                  CONSTANTS_VALUE.put( "405", constantsDTO_405 );
               }

               // ��Ұ�塿���������⴦��
               if ( itemVO.getItemId().trim().equals( "10257" ) )
               {
                  final ConstantsDTO constantsDTO_423 = CONSTANTS_VALUE.get( "423" );
                  constantsDTO_423.addConstantsDay( employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), base );
                  CONSTANTS_VALUE.put( "423", constantsDTO_423 );
               }

               /** �������� */
               // ��ʼ��BillAmountPersonal
               double billAmountPersonal = Double.valueOf( itemAmountCost ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

               orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

               /** ��˾�ɱ� */
               // ��ʼ��CostAmountCompany
               double costAmountCompany = Double.valueOf( itemAmountBill ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

               orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               /** ����֧�� */
               // ��ʼ��CostAmountPersonal
               double costAmountPersonal = Double.valueOf( itemAmountCost ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                     + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

               orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

               if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
               {
                  orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
               }
               else
               {
                  orderDetailTempVO.setTaxAmountActual( "0" );
                  orderDetailTempVO.setTaxAmountCost( "0" );
                  orderDetailTempVO.setTaxAmountSales( "0" );
               }

               orderDetailTempVO.setDeleted( "1" );
               orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

               // װ��OrderDetailTempVO
               settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );

            }
         }

         /** ��ٿۿ��Ŀ���� */
         // ������ٹ̶��ۿ�
         if ( sickLeavePayDeductAmount != 0 )
         {
            // ��203��Ϊ���ٿۿ�
            totalDeductAmountBills.put( "203", totalDeductAmountBills.get( "203" ) + sickLeavePayDeductAmount );
            totalDeductAmountCosts.put( "203", totalDeductAmountCosts.get( "203" ) + sickLeavePayDeductAmount );
         }

         if ( totalDeductAmountBills.size() > 0 && totalDeductAmountCosts.size() > 0 )
         {
            for ( String key : totalDeductAmountBills.keySet() )
            {
               double totalDeductAmountBill = totalDeductAmountBills.get( key );
               double totalDeductAmountCost = totalDeductAmountCosts.get( key );

               // ��ʼ��ItemVO
               final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( key );

               // ��ʼ��ItemCap
               double itemCap = 0;

               if ( itemVO.getItemCap() != null )
               {
                  itemCap = Double.valueOf( itemVO.getItemCap() );
               }

               // ��ʼ��ItemFloor
               double itemFloor = 0;

               if ( itemVO.getItemFloor() != null )
               {
                  itemFloor = Double.valueOf( itemVO.getItemFloor() );
               }

               final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
               orderDetailTempVO.setItemId( itemVO.getItemId() );
               orderDetailTempVO.setItemNo( itemVO.getItemNo() );
               orderDetailTempVO.setNameZH( itemVO.getNameZH() );
               orderDetailTempVO.setNameEN( itemVO.getNameEN() );
               // �����Կ�Ŀ��ʹ��Base From����ٿۿ�BaseΪ��0����Discount��Multiple��Ϊ��1��
               orderDetailTempVO.setBase( "0" );
               orderDetailTempVO.setQuantity( "1" );
               orderDetailTempVO.setDiscount( String.valueOf( "1" ) );
               orderDetailTempVO.setMultiple( String.valueOf( "1" ) );
               orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
               orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
               orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
               orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
               orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
               orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
               orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
               orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

               // Bill Cap����
               if ( itemCap != 0 && totalDeductAmountBill > itemCap )
               {
                  totalDeductAmountBill = itemCap;
               }

               // Bill Floor����
               if ( itemFloor != 0 && totalDeductAmountBill < itemFloor )
               {
                  totalDeductAmountBill = itemFloor;
               }

               // Cost Cap����
               if ( itemCap != 0 && totalDeductAmountCost > itemCap )
               {
                  totalDeductAmountCost = itemCap;
               }

               // Cost Floor����
               if ( itemFloor != 0 && totalDeductAmountCost < itemFloor )
               {
                  totalDeductAmountCost = itemFloor;
               }

               /** ��˾Ӫ�� */
               // ��ʼ��BillAmountCompany
               double billAmountCompany = Double.valueOf( totalDeductAmountBill ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

               // Sales TypeΪ��3���ǡ��������ʽ
               orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               // ��ӳ���ֵ
               addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

               /** �������� */
               // ��ʼ��BillAmountPersonal
               double billAmountPersonal = Double.valueOf( totalDeductAmountCost ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

               orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

               /** ��˾�ɱ� */
               // ��ʼ��CostAmountCompany
               double costAmountCompany = Double.valueOf( totalDeductAmountBill ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

               orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               /** ����֧�� */
               // ��ʼ��CostAmountPersonal
               double costAmountPersonal = Double.valueOf( totalDeductAmountCost ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                     + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

               orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

               if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
               {
                  orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
               }
               else
               {
                  orderDetailTempVO.setTaxAmountActual( "0" );
                  orderDetailTempVO.setTaxAmountCost( "0" );
                  orderDetailTempVO.setTaxAmountSales( "0" );
               }

               orderDetailTempVO.setDeleted( "1" );
               orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

               // װ��OrderDetailTempVO
               settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
            }
         }

         /** �Ӱ��Ŀ���� */
         if ( timesheetDTO.getOtDTOs() != null && timesheetDTO.getOtDTOs().size() > 0 )
         {
            for ( OTDTO otDTO : timesheetDTO.getOtDTOs() )
            {
               if ( otDTO.getOtDetailVOs() != null && otDTO.getOtDetailVOs().size() > 0 )
               {
                  for ( OTDetailVO otDetailVO : otDTO.getOtDetailVOs() )
                  {
                     // ���ڿ��ڱ�Χ�ڵļӰֱ࣬��������һ��ѭ��
                     if ( KANUtil.filterEmpty( otDetailVO.getTimesheetId() ) == null )
                     {
                        continue;
                     }

                     // ��ʼ��ItemVO
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( otDetailVO.getItemId() );

                     // ��ʼ��EmployeeContractOTVO
                     final EmployeeContractOTVO employeeContractOTVO = serviceContractDTO.getEmployeeContractOTVOByItemId( otDetailVO.getItemId() );

                     // ��ʼ��ClientOrderOTVO
                     final ClientOrderOTVO clientOrderOTVO = this.getClientOrderOTVOByItemId( otDetailVO.getItemId() );

                     // ��ʼ��OT Calendar
                     final Calendar otCalendar = KANUtil.createCalendar( otDetailVO.getDay() );

                     // ��ʼ��OT Start Calendar
                     Calendar otStartCalendar = null;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getStartDate() ) != null )
                     {
                        otStartCalendar = KANUtil.createCalendar( employeeContractOTVO.getStartDate() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getStartDate() ) != null )
                     {
                        otStartCalendar = KANUtil.createCalendar( clientOrderOTVO.getStartDate() );
                     }

                     // ��ʼ��OT Hours
                     double otHours = 0;
                     // �����׼����û������
                     if ( otDetailVO.getStatus().equals( "5" ) && KANUtil.getDays( otCalendar ) >= KANUtil.getDays( tsStartCalendar )
                           && KANUtil.getDays( otCalendar ) <= KANUtil.getDays( tsEndCalendar )
                           && ( otStartCalendar == null || KANUtil.getDays( otCalendar ) >= KANUtil.getDays( otStartCalendar ) ) )
                     {
                        otHours = Double.valueOf( otDetailVO.getActualHours() );
                     }

                     // ��ʼ��ItemCap
                     double itemCap = 0;
                     if ( itemVO.getItemCap() != null )
                     {
                        itemCap = Double.valueOf( itemVO.getItemCap() );
                     }

                     String resultCapString = employeeContractOTVO != null ? employeeContractOTVO.getResultCap() : null;
                     if ( KANUtil.filterEmpty( resultCapString ) == null )
                     {
                        resultCapString = clientOrderOTVO != null ? clientOrderOTVO.getResultCap() : null;
                     }

                     if ( KANUtil.filterEmpty( resultCapString, "0" ) != null )
                     {
                        if ( itemCap == 0 || Double.valueOf( resultCapString ) < itemCap )
                        {
                           itemCap = Double.valueOf( resultCapString );
                        }
                     }

                     // ��ʼ��ItemFloor
                     double itemFloor = 0;
                     if ( itemVO.getItemFloor() != null )
                     {
                        itemFloor = Double.valueOf( itemVO.getItemFloor() );
                     }

                     String resultFloorString = employeeContractOTVO != null ? employeeContractOTVO.getResultFloor() : null;
                     if ( KANUtil.filterEmpty( resultFloorString ) == null )
                     {
                        resultFloorString = clientOrderOTVO != null ? clientOrderOTVO.getResultFloor() : null;
                     }

                     if ( KANUtil.filterEmpty( resultFloorString, "0" ) != null )
                     {
                        if ( itemFloor == 0 || Double.valueOf( resultFloorString ) > itemFloor )
                        {
                           itemFloor = Double.valueOf( resultFloorString );
                        }
                     }

                     // ��ʼ��Base
                     double base = 0;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getBase() ) != null )
                     {
                        base = Double.valueOf( employeeContractOTVO.getBase() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getBase() ) != null )
                     {
                        base = Double.valueOf( clientOrderOTVO.getBase() );
                     }

                     // ��ʼ��Percentage
                     double percentage = 1;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getPercentage() ) != null
                           && Double.valueOf( employeeContractOTVO.getPercentage() ) != 0 )
                     {
                        percentage = Double.valueOf( employeeContractOTVO.getPercentage() ) / 100;
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getPercentage() ) != null && Double.valueOf( clientOrderOTVO.getPercentage() ) != 0 )
                     {
                        percentage = Double.valueOf( clientOrderOTVO.getPercentage() ) / 100;
                     }

                     // ��ʼ��Fix
                     double fix = 0;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getFix() ) != null )
                     {
                        fix = Double.valueOf( employeeContractOTVO.getFix() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getFix() ) != null )
                     {
                        fix = Double.valueOf( clientOrderOTVO.getFix() );
                     }

                     // ��ʼ��Discount
                     double discount = 1;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getDiscount() ) != null )
                     {
                        discount = Double.valueOf( employeeContractOTVO.getDiscount() ) / 100;
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getDiscount() ) != null )
                     {
                        discount = Double.valueOf( clientOrderOTVO.getDiscount() ) / 100;
                     }

                     // ��ʼ��Multiple
                     double multiple = 1;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getMultiple(), "0" ) != null )
                     {
                        multiple = Double.valueOf( employeeContractOTVO.getDecodeMultiple() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getMultiple(), "0" ) != null )
                     {
                        multiple = Double.valueOf( clientOrderOTVO.getDecodeMultiple() );
                     }

                     // ��ʼ��BaseFrom
                     String baseFrom = employeeContractOTVO != null ? employeeContractOTVO.getBaseFrom() : null;
                     if ( clientOrderOTVO != null && KANUtil.filterEmpty( baseFrom ) == null )
                     {
                        baseFrom = clientOrderOTVO.getBaseFrom();
                     }

                     // ��ʼ��Formular
                     String formular = employeeContractOTVO != null ? employeeContractOTVO.getFormular() : null;
                     if ( clientOrderOTVO != null && KANUtil.filterEmpty( formular ) == null )
                     {
                        formular = clientOrderOTVO.getFormular();
                     }

                     // Base���㣬��BaseFrom��Formular
                     base = ( getFormularValue( formular, getBaseFromValue( settlementTempDTO, baseFrom, base ) * percentage + fix, otDetailVO.getDay() ) * discount * multiple );

                     final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                     orderDetailTempVO.setItemId( itemVO.getItemId() );
                     orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                     orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                     orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                     // �����Կ�Ŀ��ʹ��Base From
                     orderDetailTempVO.setBase( String.valueOf( base ) );
                     orderDetailTempVO.setQuantity( "1" );
                     orderDetailTempVO.setDiscount( String.valueOf( discount ) );
                     orderDetailTempVO.setMultiple( String.valueOf( multiple ) );
                     orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                     orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                     orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                     orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                     orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                     orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                     orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                     orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

                     // ��ʼ��OTAmount
                     double otAmount = base * otHours;

                     // Cap����
                     if ( itemCap != 0 && otAmount > itemCap )
                     {
                        otAmount = itemCap;
                     }

                     // Floor����
                     if ( itemFloor != 0 && otAmount < itemFloor )
                     {
                        otAmount = itemFloor;
                     }

                     /** ��˾Ӫ�� */
                     // ��ʼ��BillAmountCompany
                     double billAmountCompany = Double.valueOf( otAmount ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                           + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                     // Sales TypeΪ��3���ǡ��������ʽ
                     orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                     // ��ӳ���ֵ
                     addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                     /** �������� */
                     // ��ʼ��BillAmountPersonal
                     double billAmountPersonal = Double.valueOf( otAmount ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                           + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                     orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                     /** ��˾�ɱ� */
                     // ��ʼ��CostAmountCompany
                     double costAmountCompany = Double.valueOf( otAmount ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                           + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                     orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                     /** ����֧�� */
                     // ��ʼ��CostAmountPersonal
                     double costAmountPersonal = Double.valueOf( otAmount ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                           + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                     orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                     if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
                     {
                        orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                        orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                        orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     }
                     else
                     {
                        orderDetailTempVO.setTaxAmountActual( "0" );
                        orderDetailTempVO.setTaxAmountCost( "0" );
                        orderDetailTempVO.setTaxAmountSales( "0" );
                     }

                     orderDetailTempVO.setDeleted( "1" );
                     orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

                     // װ��OrderDetailTempVO
                     settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
                  }
               }
            }
         }
      }

      // ������ǲ - ��������������
      if ( serviceContractDTO.getSalaryDTOs() != null && serviceContractDTO.getSalaryDTOs().size() > 0 )
      {
         for ( SalaryDTO salaryDTO : serviceContractDTO.getSalaryDTOs() )
         {
            // ʵ�����ʻ�ȡ����������˰��
            if ( salaryDTO.getSalaryHeaderVO() != null && KANUtil.filterEmpty( salaryDTO.getSalaryHeaderVO().getActualSalary() ) != null
                  && Double.valueOf( salaryDTO.getSalaryHeaderVO().getActualSalary() ) != 0 )
            {
               AFTER_TAX_SALARY = AFTER_TAX_SALARY + Double.valueOf( salaryDTO.getSalaryHeaderVO().getActualSalary() );

               if ( !SALARY_HEADER_IDS.contains( salaryDTO.getSalaryHeaderVO().getSalaryHeaderId() ) )
               {
                  SALARY_HEADER_IDS.add( salaryDTO.getSalaryHeaderVO().getSalaryHeaderId() );
               }
            }

            for ( SalaryDetailVO salaryDetailVO : salaryDTO.getSalaryDetailVOs() )
            {
               // ��ʼ��ItemVO
               final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( salaryDetailVO.getItemId() );

               // ��ʼ��OrderDetailTempVO
               final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
               orderDetailTempVO.setDetailId( salaryDetailVO.getSalaryDetailId() );
               orderDetailTempVO.setDetailType( "5" );
               orderDetailTempVO.setItemId( itemVO.getItemId() );
               orderDetailTempVO.setItemNo( itemVO.getItemNo() );
               orderDetailTempVO.setNameZH( itemVO.getNameZH() );
               orderDetailTempVO.setNameEN( itemVO.getNameEN() );
               // �����Կ�Ŀ��ʹ��Base From
               orderDetailTempVO.setBase( "0" );
               orderDetailTempVO.setQuantity( "1" );
               orderDetailTempVO.setDiscount( "1" );
               orderDetailTempVO.setMultiple( "1" );
               orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
               orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
               orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
               orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
               orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
               orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
               orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
               orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );
               orderDetailTempVO.setBillAmountCompany( salaryDetailVO.getBillAmountCompany() );
               orderDetailTempVO.setCostAmountCompany( salaryDetailVO.getCostAmountCompany() );
               orderDetailTempVO.setBillAmountPersonal( salaryDetailVO.getBillAmountPersonal() );
               orderDetailTempVO.setCostAmountPersonal( salaryDetailVO.getCostAmountPersonal() );

               // ��ʼ��BillAmountCompany
               double billAmountCompany = Double.valueOf( salaryDetailVO.getBillAmountCompany() );

               if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
               {
                  orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
               }
               else
               {
                  orderDetailTempVO.setTaxAmountActual( "0" );
                  orderDetailTempVO.setTaxAmountCost( "0" );
                  orderDetailTempVO.setTaxAmountSales( "0" );
               }

               // װ��OrderDetailTempVO
               settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
            }
         }
      }
   }

   // װ���������������á��������ɱ������ջ���
   private void fetchOther( final SettlementTempDTO settlementTempDTO, final ServiceContractDTO serviceContractDTO, final List< EmployeeContractOtherVO > employeeContractOtherVOs,
         final Calendar calculateStartCalendar, final Calendar calculateEndCalendar ) throws KANException
   {
      if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
      {
         for ( EmployeeContractOtherVO employeeContractOtherVO : employeeContractOtherVOs )
         {
            if ( serviceContractDTO != null && serviceContractDTO.getExistItemIds() != null && !serviceContractDTO.getExistItemIds().contains( employeeContractOtherVO.getItemId() ) )
            {
               // ��ʼ��Other Start Calendar
               Calendar otherStartCalendar = null;
               if ( KANUtil.filterEmpty( employeeContractOtherVO.getStartDate() ) != null )
               {
                  otherStartCalendar = KANUtil.createCalendar( employeeContractOtherVO.getStartDate() );
               }

               // ��ʼ��Other End Calendar
               Calendar otherEndCalendar = null;
               if ( KANUtil.filterEmpty( employeeContractOtherVO.getEndDate() ) != null )
               {
                  otherEndCalendar = KANUtil.createCalendar( employeeContractOtherVO.getEndDate() );
               }

               if ( withinCircle( calculateStartCalendar, calculateEndCalendar, employeeContractOtherVO.getCycle(), otherStartCalendar, otherEndCalendar ) )
               {
                  // ��ʼ��ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( employeeContractOtherVO.getItemId() );

                  // ��ʼ��ClientOrderOtherVO
                  final ClientOrderOtherVO clientOrderOtherVO = this.getClientOrderOtherVOByItemId( employeeContractOtherVO.getItemId() );

                  // ��ʼ��ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  String resultCapString = employeeContractOtherVO.getResultCap();
                  if ( KANUtil.filterEmpty( resultCapString ) == null )
                  {
                     resultCapString = clientOrderOtherVO != null ? clientOrderOtherVO.getResultCap() : null;
                  }

                  if ( KANUtil.filterEmpty( resultCapString, "0" ) != null )
                  {
                     if ( itemCap == 0 || Double.valueOf( resultCapString ) < itemCap )
                     {
                        itemCap = Double.valueOf( resultCapString );
                     }
                  }

                  // ��ʼ��ItemFloor
                  double itemFloor = 0;
                  if ( itemVO.getItemFloor() != null )
                  {
                     itemFloor = Double.valueOf( itemVO.getItemFloor() );
                  }

                  String resultFloorString = employeeContractOtherVO.getResultFloor();
                  if ( KANUtil.filterEmpty( resultFloorString ) == null )
                  {
                     resultFloorString = clientOrderOtherVO != null ? clientOrderOtherVO.getResultFloor() : null;
                  }

                  if ( KANUtil.filterEmpty( resultFloorString, "0" ) != null )
                  {
                     if ( itemFloor == 0 || Double.valueOf( resultFloorString ) > itemFloor )
                     {
                        itemFloor = Double.valueOf( resultFloorString );
                     }
                  }

                  // ��ʼ��Base
                  double base = 0;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getBase() ) != null )
                  {
                     base = Double.valueOf( employeeContractOtherVO.getBase() );
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getBase() ) != null )
                  {
                     base = Double.valueOf( employeeContractOtherVO.getBase() );
                  }

                  // ��ʼ��Percentage
                  double percentage = 1;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getPercentage() ) != null && Double.valueOf( employeeContractOtherVO.getPercentage() ) != 0 )
                  {
                     percentage = Double.valueOf( employeeContractOtherVO.getPercentage() ) / 100;
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getPercentage() ) != null
                        && Double.valueOf( clientOrderOtherVO.getPercentage() ) != 0 )
                  {
                     percentage = Double.valueOf( clientOrderOtherVO.getPercentage() ) / 100;
                  }

                  // ��ʼ��Fix
                  double fix = 0;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getFix() ) != null )
                  {
                     fix = Double.valueOf( employeeContractOtherVO.getFix() );
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getFix() ) != null )
                  {
                     fix = Double.valueOf( clientOrderOtherVO.getFix() );
                  }

                  // ��ʼ��Discount
                  double discount = 1;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getDiscount() ) != null )
                  {
                     discount = Double.valueOf( employeeContractOtherVO.getDiscount() ) / 100;
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getDiscount() ) != null )
                  {
                     discount = Double.valueOf( clientOrderOtherVO.getDiscount() ) / 100;
                  }

                  // ��ʼ��Multiple
                  double multiple = 1;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getMultiple(), "0" ) != null )
                  {
                     multiple = Double.valueOf( employeeContractOtherVO.getDecodeMultiple() );
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getMultiple(), "0" ) != null )
                  {
                     multiple = Double.valueOf( clientOrderOtherVO.getDecodeMultiple() );
                  }

                  // ��ʼ��BaseFrom
                  String baseFrom = employeeContractOtherVO.getBaseFrom();
                  if ( KANUtil.filterEmpty( baseFrom ) == null && clientOrderOtherVO != null )
                  {
                     baseFrom = clientOrderOtherVO.getBaseFrom();
                  }

                  // ��ʼ��Formular
                  String formular = employeeContractOtherVO.getFormular();
                  if ( KANUtil.filterEmpty( formular ) == null && clientOrderOtherVO != null )
                  {
                     formular = clientOrderOtherVO.getFormular();
                  }

                  // Base���㣬��BaseFrom��Formular
                  base = getFormularValue( formular, ( getBaseFromValue( settlementTempDTO, baseFrom, base ) * percentage + fix ) * discount * multiple, null );

                  // Cap����
                  if ( itemCap != 0 && base > itemCap )
                  {
                     base = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && base < itemFloor )
                  {
                     base = itemFloor;
                  }

                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // �����Կ�Ŀ��ʹ��Base From
                  orderDetailTempVO.setBase( String.valueOf( base ) );
                  orderDetailTempVO.setQuantity( "1" );
                  orderDetailTempVO.setDiscount( String.valueOf( discount ) );
                  orderDetailTempVO.setMultiple( String.valueOf( multiple ) );
                  orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                  orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                  orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                  orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                  orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                  orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                  orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                  orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

                  /** ��˾Ӫ�� */
                  // ��ʼ��BillAmountCompany
                  double billAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Sales TypeΪ��3���ǡ��������ʽ
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // ��ӳ���ֵ
                  addConstantsValue( itemVO.getItemType(), employeeContractOtherVO.getStartDate(), employeeContractOtherVO.getEndDate(), billAmountCompany, false );

                  /** �������� */
                  // ��ʼ��BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ��˾�ɱ� */
                  // ��ʼ��CostAmountCompany
                  double costAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ����֧�� */
                  // ��ʼ��CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
                  {
                     orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setTaxAmountActual( "0" );
                     orderDetailTempVO.setTaxAmountCost( "0" );
                     orderDetailTempVO.setTaxAmountSales( "0" );
                  }

                  orderDetailTempVO.setDeleted( "1" );
                  orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

                  // װ��OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // װ�ط����
   private void fetchServiceFee( final SettlementTempDTO settlementTempDTO, final ServiceContractDTO serviceContractDTO, final List< ClientOrderDetailDTO > clientOrderDetailDTOs,
         final Calendar calculateStartCalendar, final Calendar calculateEndCalendar ) throws KANException
   {
      if ( clientOrderDetailDTOs != null && clientOrderDetailDTOs.size() > 0 )
      {
         for ( ClientOrderDetailDTO clientOrderDetailDTO : clientOrderDetailDTOs )
         {
            // ��ʼ��ClientOrderDetailVO
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailDTO.getClientOrderDetailVO();

            if ( serviceContractDTO != null
                  && ( KANUtil.filterEmpty( clientOrderDetailVO.getBaseFrom(), "0" ) != null || ( serviceContractDTO.getExistItemIds() != null && !serviceContractDTO.getExistItemIds().contains( clientOrderDetailVO.getItemId() ) ) ) )
            {
               // ��ʼ��Service Fee Start Calendar
               Calendar serviceFeeStartCalendar = null;
               if ( KANUtil.filterEmpty( clientOrderDetailVO.getStartDate() ) != null )
               {
                  serviceFeeStartCalendar = KANUtil.createCalendar( clientOrderDetailVO.getStartDate() );
               }

               // ��ʼ��Service Fee End Calendar
               Calendar serviceFeeEndCalendar = null;
               if ( KANUtil.filterEmpty( clientOrderDetailVO.getEndDate() ) != null )
               {
                  serviceFeeEndCalendar = KANUtil.createCalendar( clientOrderDetailVO.getEndDate() );
               }

               // ��ʼ��CalculateType
               final String calculateType = clientOrderDetailVO.getCalculateType();

               // ��� - �Ƿ���Ҫ��ȡ�����
               boolean needCharge = false;

               // �жϼƷѷ�ʽ�Ƿ�����Ҫ��
               if ( KANUtil.filterEmpty( calculateType, "0" ) != null
                     && ( ( calculateType.equals( "1" ) && CONSTANTS_VALUE.get( "416" ).getValue() != 0 )
                           || ( calculateType.equals( "2" ) && CONSTANTS_VALUE.get( "417" ).getValue() != 0 )
                           || ( calculateType.equals( "3" ) && CONSTANTS_VALUE.get( "415" ).getValue() != 0 ) || calculateType.equals( "4" ) ) )
               {
                  needCharge = true;
               }

               // �жϵ�ǰ�����Ƿ�����Ҫ��
               needCharge = withinCircle( calculateStartCalendar, calculateEndCalendar, clientOrderDetailVO.getCycle(), serviceFeeStartCalendar, serviceFeeEndCalendar );

               // ��ְ���շ�����б�
               final String onboardNoCharge = clientOrderDetailVO.getOnboardNoCharge();
               final String probationNoCharge = clientOrderDetailVO.getProbationNoCharge();
               Calendar onboardCalendar = null;

               if ( serviceContractDTO != null && serviceContractDTO.getEmployeeContractVO() != null && serviceContractDTO.getEmployeeContractVO().getStartDate() != null
                     && !serviceContractDTO.getEmployeeContractVO().getStartDate().trim().equals( "" ) )
               {
                  onboardCalendar = KANUtil.createCalendar( serviceContractDTO.getEmployeeContractVO().getStartDate() );

                  if ( onboardNoCharge != null && onboardNoCharge.trim().equals( "1" ) )
                  {
                     if ( KANUtil.getDays( calculateStartCalendar ) <= KANUtil.getDays( onboardCalendar )
                           && KANUtil.getDays( calculateEndCalendar ) >= KANUtil.getDays( onboardCalendar ) )
                     {
                        needCharge = false;
                     }
                  }

                  if ( probationNoCharge != null && probationNoCharge.trim().equals( "1" ) )
                  {
                     // ��ʼ������������
                     final String probationMonth = this.getClientOrderHeaderDTO().getClientOrderHeaderVO().getProbationMonth();
                     if ( probationMonth != null && !probationMonth.trim().equals( "" ) && !probationMonth.trim().equals( "1" ) )
                     {
                        final Calendar probationEndCalendar = onboardCalendar;
                        probationEndCalendar.add( Calendar.MONTH, Integer.valueOf( probationMonth ) );

                        if ( KANUtil.getDays( calculateEndCalendar ) >= KANUtil.getDays( probationEndCalendar ) )
                        {
                           needCharge = false;
                        }
                     }
                  }
               }

               // ��ְ���շ���ѣ��Զ���ְ���շ�����б�
               final String offDutyNoCharge = clientOrderDetailVO.getOffDutyNoCharge();
               final String resignNoCharge = clientOrderDetailVO.getResignNoCharge();
               Calendar resignCalendar = null;

               if ( serviceContractDTO != null && serviceContractDTO.getEmployeeContractVO() != null && serviceContractDTO.getEmployeeContractVO().getResignDate() != null
                     && !serviceContractDTO.getEmployeeContractVO().getResignDate().trim().equals( "" ) )
               {
                  resignCalendar = KANUtil.createCalendar( serviceContractDTO.getEmployeeContractVO().getResignDate() );

                  if ( offDutyNoCharge != null && offDutyNoCharge.trim().equals( "1" ) )
                  {
                     if ( KANUtil.getDays( calculateStartCalendar ) <= KANUtil.getDays( resignCalendar )
                           && KANUtil.getDays( calculateEndCalendar ) >= KANUtil.getDays( resignCalendar ) )
                     {
                        needCharge = false;
                     }
                  }

                  if ( resignNoCharge != null && resignNoCharge.trim().equals( "1" ) )
                  {
                     if ( KANUtil.getDays( calculateStartCalendar ) <= KANUtil.getDays( resignCalendar )
                           && KANUtil.getDays( calculateEndCalendar ) >= KANUtil.getDays( resignCalendar ) )
                     {
                        final String employStatus = serviceContractDTO.getEmployeeContractVO().getEmployStatus();
                        if ( employStatus != null && employStatus.trim().equals( "4" ) )
                        {
                           needCharge = false;
                        }
                     }
                  }
               }

               // �����˾�͸��˲��ֶ�Ϊ��0�������շ����
               if ( settlementTempDTO.getBillAmountCompany() == 0 && settlementTempDTO.getBillAmountPersonal() == 0 && settlementTempDTO.getCostAmountCompany() == 0
                     && settlementTempDTO.getCostAmountPersonal() == 0 )
               {
                  needCharge = false;
               }

               if ( needCharge )
               {
                  // ��ʼ��ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( clientOrderDetailVO.getItemId() );

                  // ��ʼ��ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  String resultCapString = clientOrderDetailVO.getResultCap();
                  if ( resultCapString == null || resultCapString.trim().equals( "" ) )
                  {
                     resultCapString = clientOrderDetailVO != null ? clientOrderDetailVO.getResultCap() : null;
                  }

                  if ( resultCapString != null && !resultCapString.trim().equals( "" ) && !resultCapString.trim().equals( "0" ) )
                  {
                     if ( itemCap == 0 || Double.valueOf( resultCapString ) < itemCap )
                     {
                        itemCap = Double.valueOf( resultCapString );
                     }
                  }

                  // ��ʼ��ItemFloor
                  double itemFloor = 0;
                  if ( itemVO.getItemFloor() != null )
                  {
                     itemFloor = Double.valueOf( itemVO.getItemFloor() );
                  }

                  String resultFloorString = clientOrderDetailVO.getResultFloor();
                  if ( resultFloorString == null || resultFloorString.trim().equals( "" ) )
                  {
                     resultFloorString = clientOrderDetailVO != null ? clientOrderDetailVO.getResultFloor() : null;
                  }

                  if ( resultFloorString != null && !resultFloorString.trim().equals( "" ) && !resultFloorString.trim().equals( "0" ) )
                  {
                     if ( itemFloor == 0 || Double.valueOf( resultFloorString ) > itemFloor )
                     {
                        itemFloor = Double.valueOf( resultFloorString );
                     }
                  }

                  // ��ʼ��Base
                  double base = 0;

                  // �жϼƷѷ�ʽ�Ƿ�����Ҫ��
                  if ( ( KANUtil.filterEmpty( calculateType, "0" ) == null
                  // �籣
                        || ( calculateType.equals( "1" ) && CONSTANTS_VALUE.get( "416" ).getValue() != 0 )
                        // �̱�
                        || ( calculateType.equals( "2" ) && CONSTANTS_VALUE.get( "417" ).getValue() != 0 )
                        // ����
                        || ( calculateType.equals( "3" ) && CONSTANTS_VALUE.get( "415" ).getValue() != 0 )
                  // ����
                  || calculateType.equals( "4" ) )
                        && KANUtil.filterEmpty( clientOrderDetailVO.getBase() ) != null
                        // ����ͷ�򶩵��ж�
                        && KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ) != null
                        && ( KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ).equals( "1" ) || ( KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ).equals( "2" ) && ORDER_AMOUNT == 0 ) ) )
                  {
                     base = Double.valueOf( clientOrderDetailVO.getBase() );
                  }

                  // ��ʼ��Percentage
                  double percentage = 1;
                  if ( KANUtil.filterEmpty( clientOrderDetailVO.getPercentage() ) != null && Double.valueOf( clientOrderDetailVO.getPercentage() ) != 0 )
                  {
                     percentage = Double.valueOf( clientOrderDetailVO.getPercentage() ) / 100;
                  }

                  // ��ʼ��Fix
                  double fix = 0;
                  if ( clientOrderDetailVO.getFix() != null && !clientOrderDetailVO.getFix().trim().equals( "" ) )
                  {
                     fix = Double.valueOf( clientOrderDetailVO.getFix() );
                  }

                  // ��ʼ��Discount
                  double discount = 1;
                  if ( clientOrderDetailVO.getDiscount() != null && !clientOrderDetailVO.getDiscount().trim().equals( "" ) )
                  {
                     discount = Double.valueOf( clientOrderDetailVO.getDiscount() ) / 100;
                  }

                  // ��ʼ��Multiple
                  double multiple = 1;
                  if ( clientOrderDetailVO.getMultiple() != null && KANUtil.filterEmpty( clientOrderDetailVO.getMultiple(), "0" ) != null )
                  {
                     multiple = Double.valueOf( clientOrderDetailVO.getDecodeMultiple() );
                  }

                  // Base���㣬��BaseFrom��Formular
                  base = getFormularValue( clientOrderDetailVO.getFormular(), ( getBaseFromValue( settlementTempDTO, clientOrderDetailVO.getBaseFrom(), base ) * percentage + fix )
                        * discount * multiple, null );

                  // Cap����
                  if ( itemCap != 0 && base > itemCap )
                  {
                     base = itemCap;
                  }

                  // Floor����
                  if ( itemFloor != 0 && base < itemFloor )
                  {
                     base = itemFloor;
                  }

                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // �����Կ�Ŀ��ʹ��Base From
                  orderDetailTempVO.setBase( String.valueOf( base ) );
                  orderDetailTempVO.setQuantity( "1" );
                  orderDetailTempVO.setDiscount( String.valueOf( discount ) );
                  orderDetailTempVO.setMultiple( String.valueOf( multiple ) );
                  orderDetailTempVO.setBillRateCompany( itemVO.getBillRateCompany() );
                  orderDetailTempVO.setBillRatePersonal( itemVO.getBillRatePersonal() );
                  orderDetailTempVO.setCostRateCompany( itemVO.getCostRateCompany() );
                  orderDetailTempVO.setCostRatePersonal( itemVO.getCostRatePersonal() );
                  orderDetailTempVO.setBillFixCompany( itemVO.getBillFixCompany() );
                  orderDetailTempVO.setBillFixPersonal( itemVO.getBillFixPersonal() );
                  orderDetailTempVO.setCostFixCompany( itemVO.getCostFixCompany() );
                  orderDetailTempVO.setCostFixPersonal( itemVO.getCostFixPersonal() );

                  // ���������
                  if ( base != 0 )
                  {
                     // ��������������
                     if ( KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ) != null && KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ).equals( "2" ) )
                     {
                        if ( CONSTANTS_VALUE.get( "412" ).getValue() > 0 )
                        {
                           base = base * CONSTANTS_VALUE.get( "411" ).getValue() / CONSTANTS_VALUE.get( "412" ).getValue();
                        }
                        else
                        {
                           base = 0;
                        }
                     }
                     // �������������㣨����н�٣�
                     else if ( KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ) != null && KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ).equals( "3" ) )
                     {
                        if ( CONSTANTS_VALUE.get( "412" ).getValue() > 0 )
                        {
                           base = base * CONSTANTS_VALUE.get( "422" ).getValue() / CONSTANTS_VALUE.get( "412" ).getValue();
                        }
                        else
                        {
                           base = 0;
                        }
                     }
                     // ��������������
                     else if ( KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ) != null && KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ).equals( "4" ) )
                     {
                        // ��ʼ��Gap Calendar
                        Calendar gapStartCalendar = calculateStartCalendar;
                        Calendar gapEndCalendar = calculateEndCalendar;

                        // ��ʼ��Gap Days
                        long gapDays = 0;

                        if ( ( onboardCalendar != null && KANUtil.getDays( onboardCalendar ) <= KANUtil.getDays( calculateEndCalendar ) )
                              || ( resignCalendar != null && KANUtil.getDays( resignCalendar ) >= KANUtil.getDays( calculateStartCalendar ) ) )
                        {
                           if ( onboardCalendar != null && KANUtil.getDays( calculateStartCalendar ) < KANUtil.getDays( onboardCalendar ) )
                           {
                              gapStartCalendar = onboardCalendar;

                              if ( KANUtil.filterEmpty( clientOrderDetailVO.getOnboardWithout() ) != null && clientOrderDetailVO.getOnboardWithout().trim().equals( "1" ) )
                              {
                                 gapDays = gapDays - 1;
                              }
                           }

                           if ( resignCalendar != null && KANUtil.getDays( calculateEndCalendar ) > KANUtil.getDays( resignCalendar ) )
                           {
                              gapEndCalendar = resignCalendar;

                              if ( KANUtil.filterEmpty( clientOrderDetailVO.getOffDutyWidthout() ) != null && clientOrderDetailVO.getOffDutyWidthout().trim().equals( "1" ) )
                              {
                                 gapDays = gapDays - 1;
                              }
                           }

                           gapDays = gapDays + KANUtil.getDays( gapEndCalendar ) - KANUtil.getDays( gapStartCalendar ) + 1;
                        }

                        base = base * gapDays / ( KANUtil.getDays( calculateEndCalendar ) - KANUtil.getDays( calculateStartCalendar ) + 1 );
                     }

                     // ������ϸ������
                     if ( clientOrderDetailDTO.getClientOrderDetailRuleVOs() != null && clientOrderDetailDTO.getClientOrderDetailRuleVOs().size() > 0 )
                     {
                        // ��ʼ��RulePrice��RuleDiscount
                        double rulePrice = -1;
                        double ruleDiscount = 1;

                        // ����������ϸ����
                        for ( ClientOrderDetailRuleVO clientOrderDetailRuleVO : clientOrderDetailDTO.getClientOrderDetailRuleVOs() )
                        {
                           String ruleType = clientOrderDetailRuleVO.getRuleType();
                           if ( ruleType == null || ruleType.trim().equals( "" ) || ruleType.trim().equals( "0" ) )
                           {
                              ruleType = null;
                           }
                           String chargeType = clientOrderDetailRuleVO.getChargeType();
                           if ( chargeType == null || chargeType.trim().equals( "" ) || chargeType.trim().equals( "0" ) )
                           {
                              chargeType = null;
                           }
                           final double ruleValue = clientOrderDetailRuleVO.getRuleValue() != null ? Double.valueOf( clientOrderDetailRuleVO.getRuleValue() ) : 0;
                           final double ruleResult = clientOrderDetailRuleVO.getRuleResult() != null ? Double.valueOf( clientOrderDetailRuleVO.getRuleResult() ) : 0;

                           if ( ruleType != null )
                           {
                              // �����������ڵ��ڵ����
                              if ( ruleType.trim().equals( "1" ) )
                              {
                                 if ( SETTLEMENT_NUMBER >= ruleValue )
                                 {
                                    if ( chargeType != null && chargeType.trim().equals( "1" ) )
                                    {
                                       rulePrice = rulePrice == -1 ? ruleResult : ( rulePrice > ruleResult ? ruleResult : rulePrice );
                                    }
                                    else if ( chargeType != null && chargeType.trim().equals( "2" ) )
                                    {
                                       ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                                    }
                                 }
                              }
                              // ��ְ�������ڵ��ڵ����
                              else if ( ruleType.trim().equals( "2" ) )
                              {
                                 if ( onboardCalendar != null )
                                 {
                                    final int onboardDay = onboardCalendar.get( Calendar.DAY_OF_MONTH );

                                    if ( onboardDay >= ruleValue )
                                    {
                                       if ( chargeType != null && chargeType.trim().equals( "1" ) )
                                       {
                                          rulePrice = rulePrice == -1 ? ruleResult : ( rulePrice > ruleResult ? ruleResult : rulePrice );
                                       }
                                       else if ( chargeType != null && chargeType.trim().equals( "2" ) )
                                       {
                                          ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                                       }
                                    }
                                 }
                              }
                              // ��ְ�������ڵ��ڵ����
                              else if ( ruleType.trim().equals( "3" ) )
                              {
                                 if ( resignCalendar != null )
                                 {
                                    final int resignDay = resignCalendar.get( Calendar.DAY_OF_MONTH );

                                    if ( resignDay <= ruleValue )
                                    {
                                       if ( chargeType != null && chargeType.trim().equals( "1" ) )
                                       {
                                          rulePrice = rulePrice == -1 ? ruleResult : ( rulePrice > ruleResult ? ruleResult : rulePrice );
                                       }
                                       else if ( chargeType != null && chargeType.trim().equals( "2" ) )
                                       {
                                          ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                                       }
                                    }
                                 }
                              }
                              // ��ְ����������С�ڵ��ڵ����
                              else if ( ruleType.trim().equals( "4" ) )
                              {
                                 if ( CONSTANTS_VALUE.get( "410" ).getValue() > 0 && CONSTANTS_VALUE.get( "410" ).getValue() <= ruleValue )
                                 {
                                    if ( chargeType != null && chargeType.trim().equals( "1" ) )
                                    {
                                       rulePrice = rulePrice == -1 ? ruleResult : ( rulePrice > ruleResult ? ruleResult : rulePrice );
                                    }
                                    else if ( chargeType != null && chargeType.trim().equals( "2" ) )
                                    {
                                       ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                                    }
                                 }
                              }
                              // ��ְ����������С�ڵ��ڵ����
                              else if ( ruleType.trim().equals( "5" ) )
                              {
                                 // ��ʼ��Gap Calendar
                                 Calendar gapStartCalendar = calculateStartCalendar;
                                 Calendar gapEndCalendar = calculateEndCalendar;

                                 // ��ʼ��Gap Days
                                 long gapDays = 0;

                                 if ( ( onboardCalendar != null && KANUtil.getDays( onboardCalendar ) <= KANUtil.getDays( calculateEndCalendar ) )
                                       || ( resignCalendar != null && KANUtil.getDays( resignCalendar ) >= KANUtil.getDays( calculateStartCalendar ) ) )
                                 {
                                    if ( onboardCalendar != null && KANUtil.getDays( calculateStartCalendar ) < KANUtil.getDays( onboardCalendar ) )
                                    {
                                       gapStartCalendar = onboardCalendar;
                                    }

                                    if ( resignCalendar != null && KANUtil.getDays( calculateEndCalendar ) > KANUtil.getDays( resignCalendar ) )
                                    {
                                       gapEndCalendar = resignCalendar;

                                       gapDays = gapDays + KANUtil.getDays( gapEndCalendar ) - KANUtil.getDays( gapStartCalendar ) + 1;
                                    }
                                 }

                                 if ( gapDays > 0 )
                                 {
                                    if ( gapDays <= ruleValue )
                                    {
                                       if ( chargeType != null && chargeType.trim().equals( "1" ) )
                                       {
                                          rulePrice = rulePrice == -1 ? ruleResult : ( rulePrice > ruleResult ? ruleResult : rulePrice );
                                       }
                                       else if ( chargeType != null && chargeType.trim().equals( "2" ) )
                                       {
                                          ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                                       }
                                    }
                                 }
                              }
                              // ��Ŀ�����ڵ��ڵ����
                              else if ( ruleType.trim().equals( "6" ) )
                              {
                                 if ( base >= ruleValue )
                                 {
                                    if ( chargeType != null && chargeType.trim().equals( "1" ) )
                                    {
                                       rulePrice = rulePrice == -1 ? ruleResult : ( rulePrice > ruleResult ? ruleResult : rulePrice );
                                    }
                                    else if ( chargeType != null && chargeType.trim().equals( "2" ) )
                                    {
                                       ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                                    }
                                 }
                              }
                           }
                        }

                        if ( rulePrice > 0 )
                        {
                           base = rulePrice;
                        }

                        if ( ruleDiscount > 0 )
                        {
                           base = base * ruleDiscount;
                        }
                     }
                  }

                  // �����ʽ������Ƿ���ȡ
                  boolean hasServiceFee = false;

                  // ���ö����ܽ��
                  if ( KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ) != null && KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ).equals( "2" )
                        && ORDER_AMOUNT == 0 )
                  {
                     ORDER_AMOUNT = base;
                     hasServiceFee = true;
                  }

                  // �籣���ɷ��ô���SB_SUPPLEMENTARY_MONTHS
                  if ( clientOrderDetailDTO.getClientOrderDetailSBRuleVOs() != null && clientOrderDetailDTO.getClientOrderDetailSBRuleVOs().size() > 0 )
                  {
                     for ( ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO : clientOrderDetailDTO.getClientOrderDetailSBRuleVOs() )
                     {
                        if ( clientOrderDetailSBRuleVO != null && KANUtil.filterEmpty( clientOrderDetailSBRuleVO.getSbRuleType() ) != null
                              && KANUtil.filterEmpty( clientOrderDetailSBRuleVO.getAmount() ) != null && Double.valueOf( clientOrderDetailSBRuleVO.getAmount() ) != 0
                              && SB_SUPPLEMENTARY_MONTHS.get( clientOrderDetailSBRuleVO.getSbSolutionId() ) != null
                              && SB_SUPPLEMENTARY_MONTHS.get( clientOrderDetailSBRuleVO.getSbSolutionId() ) > 0 )
                        {
                           // ���˴��շ�
                           if ( "2".equals( clientOrderDetailSBRuleVO.getSbRuleType() ) )
                           {
                              base = base + Double.valueOf( clientOrderDetailSBRuleVO.getAmount() );
                           }
                           // ���·��շ�
                           else if ( "3".equals( clientOrderDetailSBRuleVO.getSbRuleType() ) )
                           {
                              base = base + Double.valueOf( clientOrderDetailSBRuleVO.getAmount() ) * SB_SUPPLEMENTARY_MONTHS.get( clientOrderDetailSBRuleVO.getSbSolutionId() );
                           }
                        }
                     }
                  }

                  /** ��˾Ӫ�� */
                  // ��ʼ��BillAmountCompany
                  double billAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Sales TypeΪ��3���ǡ��������ʽ
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) && !hasServiceFee ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // ��ӳ���ֵ
                  addConstantsValue( itemVO.getItemType(), clientOrderDetailVO.getStartDate(), clientOrderDetailVO.getEndDate(), billAmountCompany, false );

                  /** �������� */
                  // ��ʼ��BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ��˾�ɱ� */
                  // ��ʼ��CostAmountCompany
                  double costAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** ����֧�� */
                  // ��ʼ��CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  orderDetailTempVO.setCostAmountPersonal( KANUtil.round( costAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  if ( TAX_VO != null && itemVO.getCompanyTax() != null && itemVO.getCompanyTax().trim().equals( "1" ) )
                  {
                     orderDetailTempVO.setTaxAmountActual( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getActualTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountCost( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getCostTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                     orderDetailTempVO.setTaxAmountSales( KANUtil.round( billAmountCompany * Double.valueOf( TAX_VO.getSaleTax() ) / 100, Integer.valueOf( ACCURACY ), ROUND ) );
                  }
                  else
                  {
                     orderDetailTempVO.setTaxAmountActual( "0" );
                     orderDetailTempVO.setTaxAmountCost( "0" );
                     orderDetailTempVO.setTaxAmountSales( "0" );
                  }

                  orderDetailTempVO.setDeleted( "1" );
                  orderDetailTempVO.setStatus( OrderDetailTempVO.TRUE );

                  // װ��OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // �ж��Ƿ���������
   private boolean withinCircle( final Calendar tsStartCalendar, final Calendar tsEndCalendar, final String circle, final Calendar itemStartCalendar, final Calendar itemEndCalendar )
   {
      // ��ʼ������״̬
      boolean withinCircle = true;

      // Circle������
      if ( circle != null && !circle.trim().equals( "" ) && !circle.trim().equals( "0" ) && !circle.trim().equals( "1" ) )
      {
         // һ���� - ֻ�жϿ�ʼʱ��
         if ( circle.trim().equals( "13" ) )
         {
            if ( itemStartCalendar != null
                  && ( KANUtil.getDays( itemStartCalendar ) < KANUtil.getDays( tsStartCalendar ) || KANUtil.getDays( itemStartCalendar ) > KANUtil.getDays( tsEndCalendar ) ) )
            {
               withinCircle = false;
            }
         }
         // 2-12���µ����
         else
         {
            if ( itemStartCalendar != null )
            {
               // ���Ա���ʱ��ѭ���Ƿ��ܹ�����
               boolean fired = false;

               // ѭ���� TS�Ľ���ʱ����ڵ�Item��ʼʱ��+���ڵ�ʱ��
               while ( KANUtil.getDays( itemStartCalendar ) <= KANUtil.getDays( tsEndCalendar ) )
               {
                  if ( KANUtil.getDays( itemStartCalendar ) >= KANUtil.getDays( tsStartCalendar ) )
                  {
                     fired = true;
                  }

                  itemStartCalendar.add( Calendar.MONTH, Integer.valueOf( circle.trim() ) );
               }

               if ( !fired )
               {
                  withinCircle = false;
               }
            }
         }
      }
      // Circleδ���û�ÿ����Ҫ���㣬������ͬ
      else
      {
         if ( itemStartCalendar != null && tsEndCalendar != null && KANUtil.getDays( itemStartCalendar ) > KANUtil.getDays( tsEndCalendar ) )
         {
            withinCircle = false;
         }

         if ( itemEndCalendar != null && tsEndCalendar != null && KANUtil.getDays( itemEndCalendar ) < KANUtil.getDays( tsStartCalendar ) )
         {
            withinCircle = false;
         }
      }

      return withinCircle;
   }

   // ͳ�ƹ���Сʱ��
   private double statWorkHours( final List< TimesheetDetailVO > timesheetDetailVOs, final Calendar itemStartCalendar, final Calendar itemEndCalendar, final String hoursFlag )
   {
      double workHours = 0;

      if ( timesheetDetailVOs != null && timesheetDetailVOs.size() > 0 )
      {
         for ( TimesheetDetailVO timesheetDetailVO : timesheetDetailVOs )
         {
            final Calendar timesheetDetailCalendar = KANUtil.createCalendar( timesheetDetailVO.getDay() );

            if ( timesheetDetailCalendar != null && ( itemStartCalendar == null || KANUtil.getDays( timesheetDetailCalendar ) >= KANUtil.getDays( itemStartCalendar ) )
                  && ( itemEndCalendar == null || KANUtil.getDays( timesheetDetailCalendar ) <= KANUtil.getDays( itemEndCalendar ) ) )
            {
               if ( hoursFlag != null )
               {
                  if ( hoursFlag.trim().equals( BILL ) )
                  {
                     workHours = workHours + Double.valueOf( KANUtil.filterEmpty( timesheetDetailVO.getWorkHours() ) != null ? timesheetDetailVO.getWorkHours() : "0" )
                           + Double.valueOf( KANUtil.filterEmpty( timesheetDetailVO.getAdditionalWorkHoursBill() ) != null ? timesheetDetailVO.getAdditionalWorkHoursBill() : "0" );
                  }
                  else
                  {
                     workHours = workHours + Double.valueOf( KANUtil.filterEmpty( timesheetDetailVO.getWorkHours() ) != null ? timesheetDetailVO.getWorkHours() : "0" )
                           + Double.valueOf( KANUtil.filterEmpty( timesheetDetailVO.getAdditionalWorkHoursCost() ) != null ? timesheetDetailVO.getAdditionalWorkHoursCost() : "0" );
                  }
               }
            }
         }
      }

      return workHours;
   }

   // �ϲ������Լ�����Э�����������
   private void mergeOtherVOs( List< EmployeeContractOtherVO > employeeContractOtherVOs, final List< ClientOrderOtherVO > clientOrderOtherVOs )
   {
      if ( clientOrderOtherVOs != null && clientOrderOtherVOs.size() > 0 )
      {
         for ( ClientOrderOtherVO clientOrderOtherVO : clientOrderOtherVOs )
         {
            boolean exist = false;

            if ( employeeContractOtherVOs == null )
            {
               employeeContractOtherVOs = new ArrayList< EmployeeContractOtherVO >();
            }
            else
            {
               for ( EmployeeContractOtherVO employeeContractOtherVO : employeeContractOtherVOs )
               {
                  if ( employeeContractOtherVO.getItemId() != null && employeeContractOtherVO.getItemId().trim().equals( clientOrderOtherVO.getItemId().trim() ) )
                  {
                     exist = true;
                  }
               }
            }

            if ( !exist )
            {
               final EmployeeContractOtherVO employeeContractOtherVO = new EmployeeContractOtherVO();
               employeeContractOtherVO.setItemId( clientOrderOtherVO.getItemId() );
               employeeContractOtherVO.setBase( clientOrderOtherVO.getBase() );
               employeeContractOtherVO.setBaseFrom( clientOrderOtherVO.getBaseFrom() );
               employeeContractOtherVO.setPercentage( clientOrderOtherVO.getPercentage() );
               employeeContractOtherVO.setFix( clientOrderOtherVO.getFix() );
               employeeContractOtherVO.setDiscount( clientOrderOtherVO.getDiscount() );
               employeeContractOtherVO.setMultiple( clientOrderOtherVO.getMultiple() );
               employeeContractOtherVO.setCycle( clientOrderOtherVO.getCycle() );
               employeeContractOtherVO.setStartDate( clientOrderOtherVO.getStartDate() );
               employeeContractOtherVO.setResultCap( clientOrderOtherVO.getResultCap() );
               employeeContractOtherVO.setResultFloor( clientOrderOtherVO.getResultFloor() );
               employeeContractOtherVO.setFormularType( clientOrderOtherVO.getFormularType() );
               employeeContractOtherVO.setFormular( clientOrderOtherVO.getFormular() );
               employeeContractOtherVO.setDescription( clientOrderOtherVO.getDescription() );
               employeeContractOtherVOs.add( employeeContractOtherVO );
            }
         }
      }
   }

   private List< ClientOrderOtherVO > getClientOrderOtherVOsByItemType( final String itemType ) throws KANException
   {
      final List< ClientOrderOtherVO > clientOrderOtherVOs = new ArrayList< ClientOrderOtherVO >();

      if ( this.getClientOrderOtherVOs() != null && this.getClientOrderOtherVOs().size() > 0 )
      {
         for ( ClientOrderOtherVO clientOrderOtherVO : this.getClientOrderOtherVOs() )
         {
            // ��ʼ��ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( clientOrderOtherVO.getItemId() );

            if ( itemVO != null && itemVO.getItemType() != null && itemVO.getItemType().trim().equals( itemType ) )
            {
               clientOrderOtherVOs.add( clientOrderOtherVO );
            }
         }
      }

      return clientOrderOtherVOs;
   }

   public double getBillAmountCompany()
   {
      return getAmount( "BC" );
   }

   public double getBillAmountPersonal()
   {
      return getAmount( "BP" );
   }

   public double getCostAmountCompany()
   {
      return getAmount( "CC" );
   }

   public double getCostAmountPersonal()
   {
      return getAmount( "CP" );
   }

   private double getAmount( final String flag )
   {
      double amount = 0;

      if ( flag != null && this.getSettlementTempDTOs() != null && this.getSettlementTempDTOs().size() > 0 )
      {
         for ( SettlementTempDTO settlementTempDTO : this.getSettlementTempDTOs() )
         {
            if ( flag.equals( "BC" ) )
            {
               amount = amount + Double.valueOf( settlementTempDTO.getBillAmountCompany() );
            }
            else if ( flag.equals( "BP" ) )
            {
               amount = amount + Double.valueOf( settlementTempDTO.getBillAmountPersonal() );
            }
            else if ( flag.equals( "CC" ) )
            {
               amount = amount + Double.valueOf( settlementTempDTO.getCostAmountCompany() );
            }
            else if ( flag.equals( "CP" ) )
            {
               amount = amount + Double.valueOf( settlementTempDTO.getCostAmountPersonal() );
            }
         }
      }

      return amount;
   }

   private double getServiceFeeAmount() throws KANException
   {
      double amount = 0;

      for ( SettlementTempDTO settlementTempDTO : this.getSettlementTempDTOs() )
      {
         if ( settlementTempDTO.getOrderDetailTempVOs() != null && settlementTempDTO.getOrderDetailTempVOs().size() > 0 )
         {
            for ( OrderDetailTempVO orderDetailTempVO : settlementTempDTO.getOrderDetailTempVOs() )
            {
               // ��ʼ��ItemVO
               final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( orderDetailTempVO.getItemId() );

               if ( itemVO.getItemType().trim().equals( "9" ) )
               {
                  amount = amount + Double.valueOf( orderDetailTempVO.getBillAmountCompany() );
               }
            }
         }
      }

      return amount;
   }

   // ����Exclude Leaves�������۳�����
   private double getTotalDeductDays( final Map< String, Double > totalDeductDays, final List< String > excludeDivideItemIds )
   {
      double tempTotalDeductDays = 0;

      if ( totalDeductDays != null && totalDeductDays.size() > 0 )
      {
         for ( String itemId : totalDeductDays.keySet() )
         {
            if ( excludeDivideItemIds != null && !excludeDivideItemIds.contains( itemId ) )
            {
               tempTotalDeductDays = tempTotalDeductDays + totalDeductDays.get( itemId );
            }
         }
      }

      return tempTotalDeductDays;
   }

   // ��ȡSick Leave Pay Rate
   private double getSickLeavePayRate( final SickLeaveSalaryDTO sickLeaveSalaryDTO, final String itemId, final String startWorkDate, final String onboardDate,
         final String contractStartDate, final String monthly, final String circleEndDay )
   {
      try
      {
         // ��ʼ��SickLeaveSalaryDetailVO
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = getSickLeaveSalaryDetailVO( sickLeaveSalaryDTO, itemId, startWorkDate, onboardDate, contractStartDate, monthly, circleEndDay );

         if ( sickLeaveSalaryDetailVO != null && KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getPercentage() ) != null )
         {
            return Double.valueOf( sickLeaveSalaryDetailVO.getPercentage() );
         }
      }
      catch ( final KANException e )
      {
         e.printStackTrace();
      }

      return -1;
   }

   // ��ȡSick Leave Pay Deduct
   private double getSickLeavePayDeduct( final SickLeaveSalaryDTO sickLeaveSalaryDTO, final String itemId, final String startWorkDate, final String onboardDate,
         final String contractStartDate, final String monthly, final String circleEndDay )
   {
      try
      {
         // ��ʼ��SickLeaveSalaryDetailVO
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = getSickLeaveSalaryDetailVO( sickLeaveSalaryDTO, itemId, startWorkDate, onboardDate, contractStartDate, monthly, circleEndDay );

         if ( sickLeaveSalaryDetailVO != null && KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getDeduct() ) != null )
         {
            return Double.valueOf( sickLeaveSalaryDetailVO.getDeduct() );
         }
      }
      catch ( final KANException e )
      {
         e.printStackTrace();
      }

      return 0;
   }

   // ��ȡSickLeaveSalaryDetailVO - ������
   private SickLeaveSalaryDetailVO getSickLeaveSalaryDetailVO( final SickLeaveSalaryDTO sickLeaveSalaryDTO, final String itemId, final String startWorkDate,
         final String onboardDate, final String contractStartDate, final String monthly, final String circleEndDay ) throws KANException
   {
      if ( sickLeaveSalaryDTO != null && sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO() != null
            && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getItemId() ) != null
            && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getItemId() ).trim().equals( itemId ) )
      {
         // ��ʼ��Gap Months
         int gapMonths = 0;

         // ��˾����
         if ( KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ) != null
               && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ).equals( "1" ) )
         {
            gapMonths = KANUtil.getGapMonth( onboardDate, KANUtil.getEndDate( monthly, circleEndDay ) );
         }
         // ��ʼ��������
         else if ( KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ) != null
               && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ).equals( "2" ) )
         {
            gapMonths = KANUtil.getGapMonth( startWorkDate, KANUtil.getEndDate( monthly, circleEndDay ) );
         }

         // �����˾���ںͿ�ʼ�������ڶ�û���裬ȡ��ǰ��ͬ�Ŀ�ʼ����
         if ( gapMonths == 0 )
         {
            gapMonths = KANUtil.getGapMonth( contractStartDate, KANUtil.getEndDate( monthly, circleEndDay ) );

            // Ĭ���������һ����
            if ( gapMonths == 0 )
            {
               gapMonths = 1;
            }
         }

         // ����
         if ( sickLeaveSalaryDTO.getSickLeaveSalaryDetailVOs() != null && sickLeaveSalaryDTO.getSickLeaveSalaryDetailVOs().size() > 0 && gapMonths > 0 )
         {
            for ( SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO : sickLeaveSalaryDTO.getSickLeaveSalaryDetailVOs() )
            {
               if ( KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getRangeFrom() ) != null && KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getRangeTo() ) != null
                     && Integer.valueOf( KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getRangeFrom() ) ) <= gapMonths
                     && Integer.valueOf( KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getRangeTo() ) ) >= gapMonths )
               {
                  return sickLeaveSalaryDetailVO;
               }
               else if ( KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getRangeFrom() ) != null && KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getRangeTo() ) == null
                     && Integer.valueOf( KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getRangeFrom() ) ) <= gapMonths || sickLeaveSalaryDetailVO.getRangeTo().equals( "0" ) )
               {
                  return sickLeaveSalaryDetailVO;
               }
            }
         }
      }

      return null;
   }

   /**
    * �ж��Ƿ�������ְ
    * @param onBoardDate
    * @param monthly
    * @return
    */
   private boolean isFirstOrLastWorkMonth( String onBoardDate, String resignDate, final String monthly, final ServiceContractDTO serviceContractDTO,
         final List< ServiceContractDTO > serviceContractDTOs )
   {
      // Ĭ�ϲ���
      boolean flag = false;
      try
      {
         // ���onboardDate Ϊ����ȡ��ͬ��ʼʱ�䡣���resignDate Ϊ����ȡ��ͬ����ʱ��
         final List< EmployeeContractVO > myContracts = new ArrayList< EmployeeContractVO >();
         if ( KANUtil.filterEmpty( onBoardDate ) == null || KANUtil.filterEmpty( resignDate ) == null )
         {
            for ( ServiceContractDTO tempDTO : serviceContractDTOs )
            {
               if ( tempDTO.getEmployeeVO().getEmployeeId().equals( serviceContractDTO.getEmployeeVO().getEmployeeId() ) )
               {
                  myContracts.add( tempDTO.getEmployeeContractVO() );
               }
            }
         }

         if ( KANUtil.filterEmpty( onBoardDate ) == null )
         {
            //��ȡ��������
            onBoardDate = getLastestDate( myContracts, true );
         }

         if ( KANUtil.filterEmpty( resignDate ) == null )
         {
            //��ȡ��������
            resignDate = getLastestDate( myContracts, false );
         }

         final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
         final String yearCalc = monthly.split( "/" )[ 0 ];
         final String monthlyCalc = monthly.split( "/" )[ 1 ];
         // �ж���ְ
         if ( KANUtil.filterEmpty( onBoardDate ) != null )
         {
            final Date dateOnBoard = sdf.parse( onBoardDate );
            final Calendar calOnboardDate = Calendar.getInstance();
            calOnboardDate.setTime( dateOnBoard );
            final int yearOnBoard = calOnboardDate.get( Calendar.YEAR );
            final int monthlyOnBoard = ( calOnboardDate.get( Calendar.MONTH ) + 1 );
            if ( yearOnBoard == Integer.valueOf( yearCalc ) && monthlyOnBoard == Integer.valueOf( monthlyCalc ) )
            {
               flag = true;
            }
         }
         // �ж���ְ
         if ( !flag && KANUtil.filterEmpty( resignDate ) != null )
         {
            final Date dateResign = sdf.parse( resignDate );
            final Calendar calResign = Calendar.getInstance();
            calResign.setTime( dateResign );
            final int yearResign = calResign.get( Calendar.YEAR );
            final int monthlyResign = ( calResign.get( Calendar.MONTH ) + 1 );
            if ( yearResign == Integer.valueOf( yearCalc ) && monthlyResign == Integer.valueOf( monthlyCalc ) )
            {
               flag = true;
            }
         }
      }
      catch ( Exception e )
      {
         System.err.println( "��ְ���ڸ�ʽ������" );
         return false;
      }
      return flag;
   }

   /**
    * @param myContracts
    * @param flag �ǲ��ǿ�ʼ����
    * @return
    */
   private String getLastestDate( List< EmployeeContractVO > myContracts, final boolean flag )
   {
      String resultDate = "";
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
      Calendar targetCal = Calendar.getInstance();
      for ( int i = 0; i < myContracts.size(); i++ )
      {
         final EmployeeContractVO employeeContractVO = myContracts.get( i );
         // ����ǿ�ʼʱ��
         if ( flag )
         {
            if ( targetCal == null && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) != null )
            {
               try
               {
                  targetCal = Calendar.getInstance();
                  targetCal.setTime( sdf.parse( employeeContractVO.getStartDate() ) );
                  resultDate = employeeContractVO.getStartDate();
               }
               catch ( Exception e )
               {
                  System.err.println( "��ʽ�����ڳ���getLastestDate();date=" + employeeContractVO.getStartDate() );
               }
            }
            else if ( targetCal != null && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) != null )
            {
               final Calendar tempCal = Calendar.getInstance();
               try
               {
                  tempCal.setTime( sdf.parse( employeeContractVO.getStartDate() ) );
                  if ( tempCal.getTimeInMillis() < targetCal.getTimeInMillis() )
                  {
                     targetCal.setTime( tempCal.getTime() );
                     resultDate = employeeContractVO.getStartDate();
                  }
               }
               catch ( Exception e )
               {
                  System.err.println( "��ʽ�����ڳ���getLastestDate();date=" + employeeContractVO.getStartDate() );
               }
            }
         }
         //����ǽ���ʱ��
         else
         {
            if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
            {
               resultDate = null;
               break;
            }
            else
            {
               if ( targetCal == null && KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null )
               {
                  try
                  {
                     targetCal = Calendar.getInstance();
                     targetCal.setTime( sdf.parse( employeeContractVO.getEndDate() ) );
                     resultDate = employeeContractVO.getEndDate();
                  }
                  catch ( Exception e )
                  {
                     System.err.println( "��ʽ�����ڳ���getLastestDate(end);date=" + employeeContractVO.getEndDate() );
                  }
               }
               else if ( targetCal != null && KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null )
               {
                  final Calendar tempCal = Calendar.getInstance();
                  try
                  {
                     tempCal.setTime( sdf.parse( employeeContractVO.getEndDate() ) );
                     if ( tempCal.getTimeInMillis() > targetCal.getTimeInMillis() )
                     {
                        targetCal.setTime( tempCal.getTime() );
                        resultDate = employeeContractVO.getEndDate();
                     }
                  }
                  catch ( Exception e )
                  {
                     System.err.println( "��ʽ�����ڳ���getLastestDate();date=" + employeeContractVO.getEndDate() );
                  }
               }
            }

         }
      }
      return resultDate;
   }

   /**
    * �жϵ�ǰ�Ľ����Ƿ��Ƕ�ֺ�ͬ
    * @param serviceContractDTO
    * @param serviceContractDTOs2
    * @return
    */
   private boolean hasMoreContracts( final ServiceContractDTO serviceContractDTO, final List< ServiceContractDTO > serviceContractDTOs )
   {
      boolean flag = false;
      for ( ServiceContractDTO tempDTO : serviceContractDTOs )
      {
         if ( tempDTO.getEmployeeVO().getEmployeeId().equals( serviceContractDTO.getEmployeeVO().getEmployeeId() )
               && !serviceContractDTO.getEmployeeContractVO().getContractId().equals( tempDTO.getEmployeeContractVO().getContractId() ) )
         {
            flag = true;
            break;
         }
      }
      return flag;
   }
}

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

   // AccountId - 结算计算（Scope：整个订单）
   private String ACCOUNT_ID;

   // 精度 - 结算计算（Scope：整个订单）
   private String ACCURACY;

   // 截取 - 结算计算（Scope：整个订单）
   private String ROUND;

   // Tax - 结算计算（Scope：整个订单）
   private TaxVO TAX_VO;

   // 销售方式（卖法） - 结算计算（Scope：整个订单）
   private String SALES_TYPE;

   // 费用人数 - 结算计算（Scope：整个订单）
   private int SETTLEMENT_NUMBER;

   // 当月自然天数
   private Double DAYS;

   // 订单总金额
   private double ORDER_AMOUNT;

   // 实发工资（仅供倒算税使用）
   private double AFTER_TAX_SALARY;

   //月平均天数
   private double AVG_SALARY_DAYS_PER_MONTH;

   // 工资导入Header IDs（仅供倒算税使用）
   private List< String > SALARY_HEADER_IDS = new ArrayList< String >();

   // 社保补缴月数（仅供社保补缴收费使用）
   Map< String, Integer > SB_SUPPLEMENTARY_MONTHS = new HashMap< String, Integer >();

   // 公式常量 - 结算计算（Scope：当个服务协议）
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

   // 按照ItemId获取ClientOrderOTVO对象
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

   // 按照ItemId获取ClientOrderOtherVO对象
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

   // 累加常量金额（“isBase”用来判断是原始Base还是计算结果）
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

   // 获取BaseFrom金额
   private double getBaseFromValue( final SettlementTempDTO settlementTempDTO, final String baseFrom, final double base ) throws KANException
   {
      // 从BaseFrom获取计算Base金额
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

   // 获取Formular金额
   private double getFormularValue( final String formular, final double base, final String day ) throws KANException
   {
      // 从Formular获取计算金额
      if ( formular != null && !formular.trim().equals( "" ) && !formular.trim().equals( "0" ) )
      {
         try
         {
            // 初始化Formular Amount
            double formularAmount = 0;
            // 获取Constants
            final List< ConstantVO > constantVOs = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getConstantVOsByScopeType( "4" );
            // 初始化表达式运算器
            final ExpressRunner runner = new ExpressRunner();
            // 初始化公式参数
            final IExpressContext< String, Object > context = new DefaultContext< String, Object >();

            // 设置表达式参数
            if ( constantVOs != null && constantVOs.size() > 0 )
            {
               for ( ConstantVO constantVO : constantVOs )
               {
                  // “421”为基数，需要特殊设置
                  context.put( constantVO.getNameZH(), constantVO.getConstantId().equals( "421" ) ? base : CONSTANTS_VALUE.get( constantVO.getConstantId() ).getValue( day ) );
               }
            }

            try
            {
               // 执行表达式
               final Object expressReslut = runner.execute( formular, context, null, false, false );

               formularAmount = Double.valueOf( expressReslut.toString() );
            }
            catch ( final Exception e )
            {
            }

            // 当公式运算产生金额，覆盖当前Base
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

   // 结算计算
   public boolean calculateSettlement( final List< String > flags ) throws KANException
   {
      if ( this.getClientOrderHeaderDTO() != null )
      {
         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDTO().getClientOrderHeaderVO();

         // 订单和服务协议都存在 - 结算条件
         if ( clientOrderHeaderVO != null && this.getServiceContractDTOs() != null && this.getServiceContractDTOs().size() > 0 )
         {
            // 初始化结算使用变量
            ACCOUNT_ID = clientOrderHeaderVO.getAccountId();
            ACCURACY = KANConstants.getKANAccountConstants( ACCOUNT_ID ).OPTIONS_ACCURACY;
            ROUND = KANConstants.getKANAccountConstants( ACCOUNT_ID ).OPTIONS_ROUND;
            TAX_VO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getTaxVOByTaxId( clientOrderHeaderVO.getTaxId() );
            SALES_TYPE = clientOrderHeaderVO.getSalesType() != null ? clientOrderHeaderVO.getSalesType() : "";
            SETTLEMENT_NUMBER = this.serviceContractDTOs != null ? this.serviceContractDTOs.size() : 0;
            DAYS = Double.valueOf( KANUtil.getMaximumDaysOfMonth( clientOrderHeaderVO.getMonthly() ) );
            AVG_SALARY_DAYS_PER_MONTH = KANConstants.getKANAccountConstants( ACCOUNT_ID ).AVG_SALARY_DAYS_PER_MONTH;

            // 获取结算类型的系统常量
            final List< ConstantVO > constantVOs = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getConstantVOsByScopeType( "4" );

            // 装载OrderHeaderTempVO
            fetchClientOrderHeader( clientOrderHeaderVO );

            // 按照服务协议遍历并处理
            for ( ServiceContractDTO serviceContractDTO : this.getServiceContractDTOs() )
            {
               // 按每个派送协议清空
               AFTER_TAX_SALARY = 0;
               SALARY_HEADER_IDS = new ArrayList< String >();
               SB_SUPPLEMENTARY_MONTHS = new HashMap< String, Integer >();

               // 初始化SettlementDTO
               final SettlementTempDTO settlementTempDTO = new SettlementTempDTO();

               // 每个服务协议初始化一次CONSTANTS_VALUE
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

               // 初始化考勤表开始日期
               Calendar tsStartCalendar = null;
               if ( serviceContractDTO.getTimesheetDTO() != null && serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO() != null
                     && KANUtil.filterEmpty( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getStartDate() ) != null )
               {
                  tsStartCalendar = KANUtil.createCalendar( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getStartDate() );
               }

               // 初始化考勤表结束日期
               Calendar tsEndCalendar = null;
               if ( serviceContractDTO.getTimesheetDTO() != null && serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO() != null
                     && KANUtil.filterEmpty( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getEndDate() ) != null )
               {
                  tsEndCalendar = KANUtil.createCalendar( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getEndDate() );
               }

               // 初始化结算周期开始日期
               Calendar calculateStartCalendar = KANUtil.getStartCalendar( clientOrderHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleStartDay() );

               // 初始化结算周期结束日期
               Calendar calculateEndCalendar = KANUtil.getEndCalendar( clientOrderHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleEndDay() );

               // 初始化Sick Leave ID
               String sickLeaveId = serviceContractDTO.getEmployeeContractVO().getSickLeaveSalaryId();
               if ( KANUtil.filterEmpty( sickLeaveId, "0" ) == null )
               {
                  sickLeaveId = clientOrderHeaderVO.getSickLeaveSalaryId();
               }

               // 初始化
               SickLeaveSalaryDTO sickLeaveSalaryDTO = null;
               if ( KANUtil.filterEmpty( sickLeaveId, "0" ) != null )
               {
                  sickLeaveSalaryDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getSickLeaveSalaryDTOByHeaderId( sickLeaveId );
               }

               // 初始化ServiceContractTempVO
               final ServiceContractTempVO serviceContractTempVO = new ServiceContractTempVO();

               // 不满月（入离职不覆盖整月的情况，合同续签等；还需考虑科目延续不满月）
               boolean incompleteCircle = false;

               // 考勤表起始日期大于结算周期开始日期
               if ( tsStartCalendar != null && calculateStartCalendar != null && KANUtil.getDays( tsStartCalendar ) > KANUtil.getDays( calculateStartCalendar ) )
               {
                  incompleteCircle = true;
               }

               // 考勤表结束日期小于结算周期结束日期
               if ( tsEndCalendar != null && calculateEndCalendar != null && KANUtil.getDays( tsEndCalendar ) < KANUtil.getDays( calculateEndCalendar ) )
               {
                  incompleteCircle = true;
               }

               /** 设置结算台帐 */

               // 社保结算
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_SB ) )
               {
                  // 装载社保
                  fetchSB( settlementTempDTO, serviceContractDTO.getSbDTOs() );

                  // 装载社保调整
                  fetchSBAdjustment( settlementTempDTO, serviceContractDTO.getSbAdjustmentDTOs() );
               }

               // 商保结算
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_CB ) )
               {
                  // 装载商保
                  fetchCB( settlementTempDTO, serviceContractDTO.getCbDTOs() );
               }

               // 工资结算
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_SALARY ) )
               {
                  // 装载工资（相关科目）
                  fetchSalary( settlementTempDTO, serviceContractDTO, sickLeaveSalaryDTO, tsStartCalendar, tsEndCalendar, incompleteCircle );

                  // 设置TimesheetId
                  if ( serviceContractDTO.getTimesheetDTO() != null && serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO() != null )
                  {
                     serviceContractTempVO.setTimesheetId( serviceContractDTO.getTimesheetDTO().getTimesheetHeaderVO().getHeaderId() );
                  }
               }

               // 其他费用，第三方成本结算
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER ) )
               {
                  // 初始化EmployeeContractOtherVO其他费用列表
                  final List< EmployeeContractOtherVO > otherFeeEmployeeContractOtherVOs = serviceContractDTO.getOtherFeeEmployeeContractOtherVOs();
                  mergeOtherVOs( otherFeeEmployeeContractOtherVOs, this.getClientOrderOtherVOsByItemType( "10" ) );

                  // 初始化EmployeeContractOtherVO第三方成本列表
                  final List< EmployeeContractOtherVO > thirdPartCostEmployeeContractOtherVOs = serviceContractDTO.getThirdPartCostEmployeeContractOtherVOs();
                  mergeOtherVOs( thirdPartCostEmployeeContractOtherVOs, this.getClientOrderOtherVOsByItemType( "11" ) );

                  // 装载其他费用，第三方成本
                  fetchOther( settlementTempDTO, serviceContractDTO, otherFeeEmployeeContractOtherVOs, calculateStartCalendar, calculateEndCalendar );
                  fetchOther( settlementTempDTO, serviceContractDTO, thirdPartCostEmployeeContractOtherVOs, calculateStartCalendar, calculateEndCalendar );
               }

               // 服务费结算
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_SERVICE_FEE ) )
               {
                  // 装载服务费
                  fetchServiceFee( settlementTempDTO, serviceContractDTO, this.getClientOrderDetailDTOs(), calculateStartCalendar, calculateEndCalendar );
               }

               // 风险基金结算
               if ( flags.contains( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER ) )
               {
                  // 初始化EmployeeContractOtherVO风险基金列表
                  final List< EmployeeContractOtherVO > fundingEmployeeContractOtherVOs = serviceContractDTO.getFundingEmployeeContractOtherVOs();
                  mergeOtherVOs( fundingEmployeeContractOtherVOs, this.getClientOrderOtherVOsByItemType( "12" ) );

                  // 装载风险基金
                  fetchOther( settlementTempDTO, serviceContractDTO, fundingEmployeeContractOtherVOs, calculateStartCalendar, calculateEndCalendar );
               }

               // 倒算税处理
               if ( AFTER_TAX_SALARY != 0 )
               {
                  // 初始化个税起征
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

                  // 初始化个税区间
                  IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( serviceContractDTO.getEmployeeContractVO().getIncomeTaxRangeHeaderId() );

                  if ( incomeTaxRangeDTO == null )
                  {
                     incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() );

                     if ( incomeTaxRangeDTO == null )
                     {
                        incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();
                     }
                  }

                  // 获取应税工资部分
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

                  // 获得计算后的应税工资
                  double tempTaxSalaryBase = ( beforeTaxSalary
                        + ( KANUtil.filterEmpty( serviceContractDTO.getAddtionalBillAmountPersonal() ) != null ? Double.valueOf( serviceContractDTO.getAddtionalBillAmountPersonal() )
                              : 0 ) - settlementTempDTO.getCostAmountPersonal() - personalTaxBase );

                  // 如果应税工资超出当前区间
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

                  // 初始化ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( "1" );

                  // 初始化OrderDetailTempVO
                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setHeaderId( KANUtil.stringListToJasonArray( SALARY_HEADER_IDS ) );
                  orderDetailTempVO.setDetailType( "4" );
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // 工资性科目不使用Base From
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

                  // 初始化BillAmountCompany
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

                  // 装载OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }

               // 设置服务协议汇总金额
               serviceContractTempVO.setBillAmountCompany( KANUtil.round( settlementTempDTO.getBillAmountCompany(), Integer.valueOf( ACCURACY ), ROUND ) );
               serviceContractTempVO.setBillAmountPersonal( KANUtil.round( settlementTempDTO.getBillAmountPersonal(), Integer.valueOf( ACCURACY ), ROUND ) );
               serviceContractTempVO.setCostAmountCompany( KANUtil.round( settlementTempDTO.getCostAmountCompany(), Integer.valueOf( ACCURACY ), ROUND ) );
               serviceContractTempVO.setCostAmountPersonal( KANUtil.round( settlementTempDTO.getCostAmountPersonal(), Integer.valueOf( ACCURACY ), ROUND ) );

               // 设置订单汇总金额
               this.getOrderHeaderTempVO().addBillAmountCompany( serviceContractTempVO.getBillAmountCompany() );
               this.getOrderHeaderTempVO().addBillAmountPersonal( serviceContractTempVO.getBillAmountPersonal() );
               this.getOrderHeaderTempVO().addCostAmountCompany( serviceContractTempVO.getCostAmountCompany() );
               this.getOrderHeaderTempVO().addCostAmountPersonal( serviceContractTempVO.getCostAmountPersonal() );

               // 装载ServiceContract
               fetchServiceContract( serviceContractTempVO, serviceContractDTO );
               settlementTempDTO.setServiceContractTempVO( serviceContractTempVO );

               // 存在结算明细的情况
               if ( settlementTempDTO.getOrderDetailTempVOs() != null
                     && settlementTempDTO.getOrderDetailTempVOs().size() > 0
                     && ( ( KANUtil.filterEmpty( serviceContractTempVO.getBillAmountCompany() ) != null && Double.valueOf( serviceContractTempVO.getBillAmountCompany() ) != 0 )
                           || ( KANUtil.filterEmpty( serviceContractTempVO.getBillAmountPersonal() ) != null && Double.valueOf( serviceContractTempVO.getBillAmountPersonal() ) != 0 )
                           || ( KANUtil.filterEmpty( serviceContractTempVO.getCostAmountCompany() ) != null && Double.valueOf( serviceContractTempVO.getCostAmountCompany() ) != 0 ) || ( KANUtil.filterEmpty( serviceContractTempVO.getCostAmountPersonal() ) != null && Double.valueOf( serviceContractTempVO.getCostAmountPersonal() ) != 0 ) ) )
               {
                  this.getSettlementTempDTOs().add( settlementTempDTO );
               }
            }

            // 初始化RuleDiscount
            double ruleDiscount = 1;

            // 订单主表规则处理
            if ( this.getClientOrderHeaderDTO() != null && this.getClientOrderHeaderDTO().getClientOrderHeaderRuleVOs() != null
                  && this.getClientOrderHeaderDTO().getClientOrderHeaderRuleVOs().size() > 0 )
            {
               // 遍历订单主表规则
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
                     // 订单人数大于等于的情况
                     if ( ruleType.trim().equals( "1" ) )
                     {
                        if ( SETTLEMENT_NUMBER >= ruleValue )
                        {
                           ruleDiscount = ruleDiscount > ( ruleResult / 100 ) ? ( ruleResult / 100 ) : ruleDiscount;
                        }
                     }
                     // 订单金额大于等于的情况
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

            // 遍历，打折处理，营业税处理
            if ( this.getSettlementTempDTOs() != null && this.getSettlementTempDTOs().size() > 0 )
            {
               // 初始化订单营收
               double orderBillAmountCompany = 0;

               for ( SettlementTempDTO settlementTempDTO : this.getSettlementTempDTOs() )
               {
                  if ( settlementTempDTO.getOrderDetailTempVOs() != null && settlementTempDTO.getOrderDetailTempVOs().size() > 0 )
                  {
                     // 初始化服务协议营业税
                     double taxAmountSales = 0;
                     // 初始化服务协议营收
                     double contractBillAmountCompany = 0;

                     for ( OrderDetailTempVO orderDetailTempVO : settlementTempDTO.getOrderDetailTempVOs() )
                     {
                        // 如果整个订单服务费需要打折
                        if ( ruleDiscount < 1 )
                        {
                           // 初始化ItemVO
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

                        // 服务协议营收计算
                        contractBillAmountCompany = contractBillAmountCompany + Double.valueOf( orderDetailTempVO.getBillAmountCompany() );
                     }

                     // 服务协议营收计算（加营业税）
                     contractBillAmountCompany = contractBillAmountCompany + taxAmountSales;
                     // 订单营收计算
                     orderBillAmountCompany = orderBillAmountCompany + contractBillAmountCompany;

                     // 打折、计税后重设服务协议营收
                     settlementTempDTO.getServiceContractTempVO().setBillAmountCompany( String.valueOf( contractBillAmountCompany ) );

                     // 营业税大于0，则添加营业税科目
                     if ( taxAmountSales > 0 )
                     {
                        // 初始化营业税ItemVO
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

                        // 初始化ItemCap
                        double itemCap = 0;
                        if ( itemVO.getItemCap() != null )
                        {
                           itemCap = Double.valueOf( itemVO.getItemCap() );
                        }

                        // 初始化ItemFloor
                        double itemFloor = 0;
                        if ( itemVO.getItemFloor() != null )
                        {
                           itemFloor = Double.valueOf( itemVO.getItemFloor() );
                        }

                        // 初始化TaxAmountSales
                        taxAmountSales = Double.valueOf( taxAmountSales ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                              + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                        // Cap限制
                        if ( itemCap != 0 && itemCap < taxAmountSales )
                        {
                           taxAmountSales = itemCap;
                        }

                        // Floor限制
                        if ( itemFloor != 0 && itemFloor > taxAmountSales )
                        {
                           taxAmountSales = itemFloor;
                        }

                        // Sales Type为“3”是“打包”方式
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

               // 打折、计税后重设订单营收
               this.getOrderHeaderTempVO().setBillAmountCompany( String.valueOf( orderBillAmountCompany ) );

               // 设置Order Amount
               this.getOrderHeaderTempVO().setOrderAmount( String.valueOf( ORDER_AMOUNT ) );
            }
         }
      }

      return true;
   }

   // 装载ClientOrderHeader
   private void fetchClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      if ( clientOrderHeaderVO != null )
      {
         // 初始化OrderHeaderTempVO
         final OrderHeaderTempVO orderHeaderTempVO = new OrderHeaderTempVO();

         orderHeaderTempVO.setAccountId( clientOrderHeaderVO.getAccountId() );
         orderHeaderTempVO.setEntityId( clientOrderHeaderVO.getEntityId() );
         orderHeaderTempVO.setBusinessTypeId( clientOrderHeaderVO.getBusinessTypeId() );
         orderHeaderTempVO.setClientId( clientOrderHeaderVO.getClientId() );
         orderHeaderTempVO.setCorpId( clientOrderHeaderVO.getCorpId() );
         orderHeaderTempVO.setOrderId( clientOrderHeaderVO.getOrderHeaderId() );
         orderHeaderTempVO.setStartDate( clientOrderHeaderVO.getStartDate() );
         orderHeaderTempVO.setEndDate( clientOrderHeaderVO.getEndDate() );
         // 备份Tax信息
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

   // 装载ServiceContract
   private void fetchServiceContract( final ServiceContractTempVO serviceContractTempVO, final ServiceContractDTO serviceContractDTO ) throws KANException
   {
      if ( serviceContractDTO.getEmployeeContractVO() != null )
      {
         // 初始化EmployeeContractVO
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
         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

         // Salary Monthly设置
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

         // SB Monthly设置
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

         // CB Monthly设置
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

         // Fund Monthly设置
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

   // 装载社保
   private void fetchSB( final SettlementTempDTO settlementTempDTO, final List< SBDTO > sbDTOs ) throws KANException
   {
      if ( sbDTOs != null && sbDTOs.size() > 0 )
      {
         // 遍历社保方案
         for ( SBDTO sbDTO : sbDTOs )
         {
            if ( sbDTO.getSbDetailVOs() != null && sbDTO.getSbDetailVOs().size() > 0 )
            {
               // 获取SBHeaderVO
               final SBHeaderVO sbHeaderVO = sbDTO.getSbHeaderVO();

               // 社保补缴月数统计
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
                  // 初始化ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( sbDetailVO.getItemId() );

                  // 初始化ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  // 初始化ItemFloor
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
                  // 社保和商保不使用Base From
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

                  /** 公司营收 */
                  // 初始化BillAmountCompany
                  double billAmountCompany = Double.valueOf( sbDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < billAmountCompany )
                  {
                     billAmountCompany = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > billAmountCompany )
                  {
                     billAmountCompany = itemFloor;
                  }

                  // Sales Type为“3”是“打包”方式
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // 添加常量值
                  addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                  /** 个人收入 */
                  // 初始化BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( sbDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < billAmountPersonal )
                  {
                     billAmountPersonal = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > billAmountPersonal )
                  {
                     billAmountPersonal = itemFloor;
                  }

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 公司成本 */
                  // 初始化CostAmountCompany
                  double costAmountCompany = Double.valueOf( sbDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < costAmountCompany )
                  {
                     costAmountCompany = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > costAmountCompany )
                  {
                     costAmountCompany = itemFloor;
                  }

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 个人支出 */
                  // 初始化CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( sbDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < costAmountPersonal )
                  {
                     costAmountPersonal = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > costAmountPersonal )
                  {
                     costAmountPersonal = itemFloor;
                  }

                  // 个人部分公司承担判断
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

                  // 装载OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // 装载社保调整
   private void fetchSBAdjustment( final SettlementTempDTO settlementTempDTO, final List< SBAdjustmentDTO > sbAdjustmentDTOs ) throws KANException
   {
      if ( sbAdjustmentDTOs != null && sbAdjustmentDTOs.size() > 0 )
      {
         // 遍历社保调整
         for ( SBAdjustmentDTO sbAdjustmentDTO : sbAdjustmentDTOs )
         {
            if ( sbAdjustmentDTO.getSbAdjustmentDetailVOs() != null && sbAdjustmentDTO.getSbAdjustmentDetailVOs().size() > 0 )
            {
               // 获取SBAdjustmentHeaderVO
               final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentDTO.getSbAdjustmentHeaderVO();

               for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDTO.getSbAdjustmentDetailVOs() )
               {
                  // 初始化ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

                  // 初始化ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  // 初始化ItemFloor
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
                  // 社保和商保不使用Base From
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

                  /** 公司营收 */
                  // 初始化BillAmountCompany
                  double billAmountCompany = Double.valueOf( sbAdjustmentDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < billAmountCompany )
                  {
                     billAmountCompany = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > billAmountCompany )
                  {
                     billAmountCompany = itemFloor;
                  }

                  // Sales Type为“3”是“打包”方式
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // 添加常量值
                  addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                  /** 个人收入 */
                  // 初始化BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( sbAdjustmentDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < billAmountPersonal )
                  {
                     billAmountPersonal = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > billAmountPersonal )
                  {
                     billAmountPersonal = itemFloor;
                  }

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 公司成本 */
                  // 初始化CostAmountCompany
                  double costAmountCompany = Double.valueOf( sbAdjustmentDetailVO.getAmountCompany() ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < costAmountCompany )
                  {
                     costAmountCompany = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > costAmountCompany )
                  {
                     costAmountCompany = itemFloor;
                  }

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 个人支出 */
                  // 初始化CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( sbAdjustmentDetailVO.getAmountPersonal() ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < costAmountPersonal )
                  {
                     costAmountPersonal = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > costAmountPersonal )
                  {
                     costAmountPersonal = itemFloor;
                  }

                  // 个人部分公司承担判断
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

                  // 装载OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // 装载商保
   private void fetchCB( final SettlementTempDTO settlementTempDTO, final List< CBDTO > cbDTOs ) throws KANException
   {
      if ( cbDTOs != null && cbDTOs.size() > 0 )
      {
         // 遍历商保方案
         for ( CBDTO cbDTO : cbDTOs )
         {
            if ( cbDTO.getCbDetailVOs() != null && cbDTO.getCbDetailVOs().size() > 0 )
            {
               for ( CBDetailVO cbDetailVO : cbDTO.getCbDetailVOs() )
               {
                  // 初始化ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( cbDetailVO.getItemId() );

                  // 初始化ItemCap
                  double itemCap = 0;
                  if ( itemVO.getItemCap() != null )
                  {
                     itemCap = Double.valueOf( itemVO.getItemCap() );
                  }

                  // 初始化ItemFloor
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
                  // 社保和商保不使用Base From
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

                  /** 公司营收 */
                  // 初始化BillAmountCompany
                  double billAmountCompany = Double.valueOf( cbDetailVO.getAmountSalesPrice() ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < billAmountCompany )
                  {
                     billAmountCompany = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > billAmountCompany )
                  {
                     billAmountCompany = itemFloor;
                  }

                  // Sales Type为“3”是“打包”方式
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // 添加常量值
                  addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                  /** 个人收入 */
                  // 初始化BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( cbDetailVO.getAmountSalesPrice() ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < billAmountPersonal )
                  {
                     billAmountPersonal = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > billAmountPersonal )
                  {
                     billAmountPersonal = itemFloor;
                  }

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 公司成本 */
                  // 初始化CostAmountCompany
                  double costAmountCompany = Double.valueOf( cbDetailVO.getAmountSalesCost() ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < costAmountCompany )
                  {
                     costAmountCompany = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && itemFloor > costAmountCompany )
                  {
                     costAmountCompany = itemFloor;
                  }

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 个人支出 */
                  // 初始化CostAmountPersonal
                  double costAmountPersonal = Double.valueOf( cbDetailVO.getAmountSalesCost() ) * Double.valueOf( orderDetailTempVO.getCostRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixPersonal() );

                  // Cap限制
                  if ( itemCap != 0 && itemCap < costAmountPersonal )
                  {
                     costAmountPersonal = itemCap;
                  }

                  // Floor限制
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

                  // 装载OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // 装载工资（相关科目）
   private void fetchSalary( final SettlementTempDTO settlementTempDTO, final ServiceContractDTO serviceContractDTO, final SickLeaveSalaryDTO sickLeaveSalaryDTO,
         final Calendar tsStartCalendar, final Calendar tsEndCalendar, final boolean incompleteCircle ) throws KANException
   {
      // 初始化TimesheetDTO
      final TimesheetDTO timesheetDTO = serviceContractDTO.getTimesheetDTO();

      // 灵活派遣 - 有考勤的情况&& timesheetDTO.getTimesheetDetailVOs().size() > 0
      if ( timesheetDTO != null && timesheetDTO.getTimesheetHeaderVO() != null )
      {
         // 初始化“是否全勤”
         boolean isNormal = true;

         if ( timesheetDTO.getTimesheetHeaderVO().getIsNormal() == null || !timesheetDTO.getTimesheetHeaderVO().getIsNormal().trim().equals( "1" ) )
         {
            isNormal = false;
         }

         // 初始化考勤数据
         double totalWorkHoursBill = 0;
         double totalWorkDaysBill = 0;
         double totalWorkHoursCost = 0;
         double totalWorkDaysCost = 0;
         double totalFullHours = 0;
         double totalFullDays = 0;
         // 扣款小时数（放置其他扣款） - 按照ItemId放
         DeductDTO totalDeductDaysBillDTO = new DeductDTO();
         DeductDTO totalDeductDaysCostDTO = new DeductDTO();
         // 扣款小时数 - 基本工资 - 按照ItemId放
         DeductDTO totalDeductDaysBillDTO_BS = new DeductDTO();
         DeductDTO totalDeductDaysCostDTO_BS = new DeductDTO();
         // 扣款金额 - 按照ItemId放
         Map< String, Double > totalDeductAmountBills = new HashMap< String, Double >();
         Map< String, Double > totalDeductAmountCosts = new HashMap< String, Double >();
         // 请假扣款总金额
         double sickLeavePayDeductAmount = 0;
         // 每月病假全薪小时数
         double fullpaySickLeaveHour = 0;

         //获取劳动合同请假规则中每月病假全勤小时数
         List< EmployeeContractLeaveVO > employeeContractLeaveList = serviceContractDTO.getEmployeeContractLeaveVOs();
         if ( employeeContractLeaveList != null )
         {
            for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveList )
            {
               //服务周期为：服务月。科目为：病假全薪
               if ( "4".equals( employeeContractLeaveVO.getCycle() ) && "42".equals( employeeContractLeaveVO.getItemId() ) )
               {
                  fullpaySickLeaveHour = Double.valueOf( employeeContractLeaveVO.getBenefitQuantity() );
               }
            }
         }
         //劳动合同木有每月病假全勤小时数，则获取结算规则中每月病假全勤小时数
         if ( fullpaySickLeaveHour == 0 && clientOrderLeaveVOs.size() > 0 )
         {
            for ( ClientOrderLeaveVO clientOrderLeaveVO : clientOrderLeaveVOs )
            {
               //服务周期为：服务月。科目为：病假全薪
               if ( "4".equals( clientOrderLeaveVO.getCycle() ) && "42".equals( clientOrderLeaveVO.getItemId() ) )
               {
                  fullpaySickLeaveHour = Double.valueOf( clientOrderLeaveVO.getBenefitQuantity() );
               }
            }
         }

         // 初始化工作天数
         if ( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() != null && !timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays().trim().equals( "" ) )
         {
            totalWorkDaysBill = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() );
            totalWorkDaysCost = totalWorkDaysBill;
            totalWorkHoursBill = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() );
            totalWorkHoursCost = totalWorkHoursBill;
         }

         // 初始化全勤小时数
         if ( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() != null && !timesheetDTO.getTimesheetHeaderVO().getTotalFullHours().trim().equals( "" ) )
         {
            totalFullHours = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() );
         }

         // 初始化全勤天数
         if ( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() != null && !timesheetDTO.getTimesheetHeaderVO().getTotalFullDays().trim().equals( "" ) )
         {
            totalFullDays = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() );
         }

         // 工作小时数和全勤小时数特殊设置
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
         //所有有请假的itemId
         List< String > targetItemIds = new ArrayList< String >();
         /** 缺勤情况，休假科目处理，如果是带薪或部分带薪补增工作小时数 */
         // Modify by siuvan @2014-12-30 鹿港请假不扣款
         if ( !isNormal )
         {
            if ( timesheetDTO.getLeaveDTOs() != null && timesheetDTO.getLeaveDTOs().size() > 0 )
            {

               for ( LeaveDTO leaveDTO : timesheetDTO.getLeaveDTOs() )
               {
                  // 鹿港请假不扣款
                  if ( leaveDTO.getLeaveHeaderVO() != null && KANUtil.filterEmpty( leaveDTO.getLeaveHeaderVO().getDataFrom() ) != null
                        && leaveDTO.getLeaveHeaderVO().getDataFrom().equals( "2" ) )
                     continue;

                  if ( leaveDTO.getLeaveDetailVOs() != null && leaveDTO.getLeaveDetailVOs().size() > 0 )
                  {
                     for ( LeaveDetailVO leaveDetailVO : leaveDTO.getLeaveDetailVOs() )
                     {
                        // 不在考勤表范围内的请假，直接跳往下一层循环
                        if ( KANUtil.filterEmpty( leaveDetailVO.getTimesheetId() ) == null )
                        {
                           continue;
                        }

                        // 初始化ItemVO
                        final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( leaveDTO.getLeaveHeaderVO().getItemId() );

                        targetItemIds.add( itemVO.getItemId() );

                        double leaveHours = 0;

                        // 请假批准，但没有销假
                        if ( "3".equals( leaveDetailVO.getStatus() ) && "1".equals( leaveDetailVO.getRetrieveStatus() ) )
                        {
                           leaveHours = Double.valueOf( leaveDetailVO.getEstimateBenefitHours() != null ? leaveDetailVO.getEstimateBenefitHours() : "0" )
                                 + Double.valueOf( leaveDetailVO.getEstimateLegalHours() != null ? leaveDetailVO.getEstimateLegalHours() : "0" );
                        }
                        // 请假、销假都批准
                        else if ( "3".equals( leaveDetailVO.getStatus() ) && "3".equals( leaveDetailVO.getRetrieveStatus() ) )
                        {
                           leaveHours = Double.valueOf( leaveDetailVO.getActualBenefitHours() != null ? leaveDetailVO.getActualBenefitHours() : "0" )
                                 + Double.valueOf( leaveDetailVO.getActualLegalHours() != null ? leaveDetailVO.getActualLegalHours() : "0" );
                        }

                        // 初始化TimesheetDetailVO
                        final TimesheetDetailVO timesheetDetailVO = timesheetDTO.getTimesheetDetailVOByDay( leaveDetailVO.getDay() );

                        if ( timesheetDetailVO != null )
                        {
                           // 初始化TimesheetDetailVO的剩余GAP小时数
                           double leftGapHours = Double.valueOf( timesheetDetailVO.getFullHours() != null ? timesheetDetailVO.getFullHours() : "0" )
                                 - Double.valueOf( timesheetDetailVO.getNorateWorkHours() != null ? timesheetDetailVO.getNorateWorkHours() : "0" );

                           // 休假小时大于剩余GAP小时数，则赋值
                           if ( leaveHours > leftGapHours )
                           {
                              leaveHours = leftGapHours;
                           }
                        }

                        // 缓存未打折小时数
                        if ( timesheetDetailVO != null )
                        {
                           timesheetDetailVO.addNorateWorkHours( String.valueOf( leaveHours ) );
                        }

                        // 初始化EmployeeVO
                        final EmployeeVO employeeVO = serviceContractDTO.getEmployeeVO();

                        // 初始化EmployeeContractVO
                        final EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();

                        // 初始化自定义请假带薪比率，请假扣款金额（每小时）
                        double sickLeavePayRate = -1;
                        double sickLeavePayDeduct = 0;

                        if ( employeeVO != null && employeeContractVO != null )
                        {
                           sickLeavePayRate = getSickLeavePayRate( sickLeaveSalaryDTO, itemVO.getItemId(), employeeVO.getStartWorkDate(), employeeVO.getOnboardDate(), employeeContractVO.getStartDate(), employeeContractVO.getMonthly(), timesheetDTO.getTimesheetHeaderVO().getEndDate() );
                           sickLeavePayDeduct = getSickLeavePayDeduct( sickLeaveSalaryDTO, itemVO.getItemId(), employeeVO.getStartWorkDate(), employeeVO.getOnboardDate(), employeeContractVO.getStartDate(), employeeContractVO.getMonthly(), timesheetDTO.getTimesheetHeaderVO().getEndDate() );
                        }

                        // 请假扣款累计
                        sickLeavePayDeductAmount = sickLeavePayDeductAmount + sickLeavePayDeduct * leaveHours;

                        // 用带薪假或部分带薪假回补工作小时数和工作天数
                        if ( KANUtil.jasonArrayToStringList( serviceContractDTO.getClientOrderHeaderVO().getExcludeDivideItemIds() ).contains( itemVO.getItemId() ) )
                        {
                           totalWorkHoursCost = totalWorkHoursCost + leaveHours;
                           totalWorkDaysCost = totalFullDays * totalWorkHoursCost / totalFullHours;
                           totalWorkHoursBill = totalWorkHoursBill + leaveHours;
                           totalWorkDaysBill = totalFullDays * totalWorkHoursBill / totalFullHours;
                           // 回补至考勤明细
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

                        //扣除病假全薪小时数
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
                        // 公司收入
                        if ( KANUtil.filterEmpty( itemVO.getBillRateCompany() ) != null || sickLeavePayRate >= 0 )
                        {
                           // 缓存所有请假扣款小时 - Bill
                           totalDeductDaysBillDTO.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) ) * totalFullDays / totalFullHours );
                           totalDeductDaysBillDTO_BS.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) )
                                 * ( sickLeavePayRate >= 0 ? ( ( 100 - sickLeavePayRate ) * Double.valueOf( itemVO.getBillRateCompany() ) ) / 10000
                                       : Double.valueOf( itemVO.getBillRateCompany() ) / 100 ) * totalFullDays / totalFullHours );
                           // 回补至考勤明细
                           if ( timesheetDetailVO != null )
                           {
                              timesheetDetailVO.addAdditionalWorkHoursBill( String.valueOf( leaveHours ) );
                           }

                           // 按Item Bill Rate打折的工作小时数
                           final ConstantsDTO tempConstantsDTO_422 = CONSTANTS_VALUE.get( "422" );
                           tempConstantsDTO_422.addConstantsDay( null, null, ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours : ( leaveHours - fullpaySickLeaveHour ) )
                                 * ( sickLeavePayRate >= 0 ? sickLeavePayRate * Double.valueOf( itemVO.getCostRateCompany() ) / 10000
                                       : Double.valueOf( itemVO.getBillRateCompany() ) ) / 100 );
                           CONSTANTS_VALUE.put( "422", tempConstantsDTO_422 );
                        }

                        // 公司成本
                        if ( KANUtil.filterEmpty( itemVO.getCostRateCompany() ) != null || sickLeavePayRate >= 0 )
                        {
                           // 缓存所有请假扣款小时 - Cost
                           totalDeductDaysCostDTO.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) ) * totalFullDays / totalFullHours );
                           totalDeductDaysCostDTO_BS.addDeductDay( itemVO.getItemId(), leaveDetailVO.getDay(), ( "10253".equals( itemVO.getItemId() ) != true ? leaveHours
                                 : ( leaveHours - fullpaySickLeaveHour ) )
                                 * ( sickLeavePayRate >= 0 ? ( ( 100 - sickLeavePayRate ) * Double.valueOf( itemVO.getCostRateCompany() ) ) / 10000
                                       : Double.valueOf( itemVO.getCostRateCompany() ) / 100 ) * totalFullDays / totalFullHours );
                           // 回补至考勤明细
                           if ( timesheetDetailVO != null )
                           {
                              timesheetDetailVO.addAdditionalWorkHoursCost( String.valueOf( leaveHours ) );
                           }
                        }
                        //病假全薪小时数设置为：0
                        fullpaySickLeaveHour = 0;
                     }
                  }
               }
            }
         }

         final String onBoardDate = serviceContractDTO.getEmployeeVO().getOnboardDate();
         final String resignDate = serviceContractDTO.getEmployeeContractVO().getResignDate();
         final String monthly = this.getClientOrderHeaderDTO().getClientOrderHeaderVO().getMonthly();
         // 判断是否是入职或者是离职日
         final boolean isFirstOrLastWorkMonth = isFirstOrLastWorkMonth( onBoardDate, resignDate, monthly, serviceContractDTO, this.serviceContractDTOs );
         final boolean hasMoreContract = hasMoreContracts( serviceContractDTO, this.serviceContractDTOs );

         /** 工资、补助、奖金和报销科目处理 */
         for ( EmployeeContractSalaryVO employeeContractSalaryVO : serviceContractDTO.getEmployeeContractSalaryVOs() )
         {
            // 初始化科目开始时间
            Calendar salaryStartCalendar = null;
            if ( KANUtil.filterEmpty( employeeContractSalaryVO.getStartDate() ) != null )
            {
               salaryStartCalendar = KANUtil.createCalendar( employeeContractSalaryVO.getStartDate() );
            }

            // 初始化科目结束时间
            Calendar salaryEndCalendar = null;
            if ( KANUtil.filterEmpty( employeeContractSalaryVO.getEndDate() ) != null )
            {
               salaryEndCalendar = KANUtil.createCalendar( employeeContractSalaryVO.getEndDate() );
            }
            else
            {
               // 如果为空则给最大时间
               salaryEndCalendar = Calendar.getInstance();
               salaryEndCalendar.set( 9999, 12, 31, 0, 0, 0 );
            }

            if ( withinCircle( tsStartCalendar, tsEndCalendar, employeeContractSalaryVO.getCycle(), salaryStartCalendar, salaryEndCalendar ) )
            {
               // 不满月薪酬 - 科目延续不满月
               boolean incompleteSalary = false;

               // 薪酬 有效起始日期大于考勤表起始日期
               if ( salaryStartCalendar != null && tsStartCalendar != null && KANUtil.getDays( salaryStartCalendar ) > KANUtil.getDays( tsStartCalendar ) )
               {
                  incompleteSalary = true;
               }

               // 薪酬 有效结束日期小于考勤表结束日期
               if ( salaryEndCalendar != null && tsEndCalendar != null && KANUtil.getDays( salaryEndCalendar ) < KANUtil.getDays( tsEndCalendar ) )
               {
                  incompleteSalary = true;
               }

               // 初始化ItemVO
               final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( employeeContractSalaryVO.getItemId() );

               //科目计算类型为不计算
               if ( "4".equals( itemVO.getCalculateType() ) )
               {
                  continue;
               }
               // 初始化ItemCap
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

               // 初始化ItemFloor
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

               // 初始化Base
               double base = 0;
               if ( employeeContractSalaryVO.getBase() != null )
               {
                  base = Double.valueOf( employeeContractSalaryVO.getBase() );
               }

               //科目为全勤奖金类型科目，是否直接折算为“是”、是否视为工作日，没有勾选
               if ( "13".equals( itemVO.getItemType() ) && "1".equals( employeeContractSalaryVO.getIsDeduct() ) )
               {
                  if ( targetItemIds.size() > 0 && Collections.disjoint( targetItemIds, KANUtil.jasonArrayToStringList( employeeContractSalaryVO.getExcludeDivideItemIds() ) ) )
                  {
                     base = 0;
                  }
               }

               // 初始化Percentage
               double percentage = 1;
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getPercentage() ) != null
                     && Double.valueOf( employeeContractSalaryVO.getPercentage() ) != 0 )
               {
                  percentage = Double.valueOf( employeeContractSalaryVO.getPercentage() ) / 100;
               }

               // 初始化Fix
               double fix = 0;
               if ( employeeContractSalaryVO != null && employeeContractSalaryVO.getFix() != null )
               {
                  fix = Double.valueOf( employeeContractSalaryVO.getFix() );
               }

               // 初始化Discount
               double discount = 1;
               if ( employeeContractSalaryVO != null && employeeContractSalaryVO.getDiscount() != null )
               {
                  discount = Double.valueOf( employeeContractSalaryVO.getDiscount() ) / 100;
               }

               // 初始化Multiple
               double multiple = 1;
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getMultiple(), "0" ) != null )
               {
                  multiple = Double.valueOf( employeeContractSalaryVO.getDecodeMultiple() );
               }

               // 初始化SalaryType
               String salaryType = "1";
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getSalaryType(), "0" ) != null )
               {
                  salaryType = employeeContractSalaryVO.getSalaryType().trim();
               }

               // 初始化DivideType
               String divideType = "1";
               String leaveDivideType = "1";
               if ( employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getDivideType(), "0" ) != null )
               {
                  divideType = employeeContractSalaryVO.getDivideType().trim();
                  leaveDivideType = employeeContractSalaryVO.getDivideType().trim();
               }

               // 如果不满月条件成立，初始化DivideTypeIncomplete -> DivideType
               if ( ( incompleteCircle ) && employeeContractSalaryVO != null && KANUtil.filterEmpty( employeeContractSalaryVO.getDivideTypeIncomplete(), "0" ) != null )
               {
                  divideType = employeeContractSalaryVO.getDivideTypeIncomplete().trim();
               }

               if ( divideType.equals( "4" ) )
               {
                  leaveDivideType = "4";
               }

               // Base计算，含BaseFrom和Formular
               base = getFormularValue( employeeContractSalaryVO.getFormular(), ( getBaseFromValue( settlementTempDTO, employeeContractSalaryVO.getBaseFrom(), base ) * percentage + fix )
                     * discount * multiple, null );

               final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
               orderDetailTempVO.setItemId( itemVO.getItemId() );
               orderDetailTempVO.setItemNo( itemVO.getItemNo() );
               orderDetailTempVO.setNameZH( itemVO.getNameZH() );
               orderDetailTempVO.setNameEN( itemVO.getNameEN() );
               // 工资性科目不使用Base From
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

               // 初始化科目金额
               double itemAmountBill = 0;
               double itemAmountCost = 0;

               if ( base != 0 )
               {
                  // 初始化工作小时数和工作天数
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

                  // 如果工作小时数或工作天数能够满足全勤时间
                  if ( totalWorkHoursBill == totalFullHours || totalWorkHoursCost == totalFullHours || totalWorkDaysBill == totalFullDays || totalWorkDaysCost == totalFullDays )
                  {
                     isNormal = true;
                  }
                  else
                  {
                     isNormal = false;
                  }

                  // 按月计薪
                  if ( salaryType.equals( "1" ) )
                  {
                     // 如果当月有合同交接的情况或者薪酬方案交接。则按照divideType = 2来算
                     if ( hasMoreContract || incompleteSalary && !"1".equals( divideType ) )
                     {
                        divideType = "2";
                     }
                     // 全勤
                     if ( isNormal )
                     {
                        itemAmountBill = base;
                        itemAmountCost = base;
                     }
                     // 缺勤
                     else
                     {
                        // 不折算
                        if ( divideType.equals( "1" ) )
                        {
                           itemAmountBill = base;
                           itemAmountCost = base;
                        }
                        // 按月平均计薪天数折算(正算)
                        else if ( divideType.equals( "3" ) )
                        {
                           itemAmountBill = base * totalWorkDaysBill / AVG_SALARY_DAYS_PER_MONTH;

                           // 如果折算下来金额大于Base，采用逆算
                           if ( itemAmountBill >= base )
                           {
                              itemAmountBill = base - base * ( totalFullDays - totalWorkDaysBill ) / AVG_SALARY_DAYS_PER_MONTH;
                           }

                           itemAmountCost = base * totalWorkDaysCost / AVG_SALARY_DAYS_PER_MONTH;

                           // 如果折算下来金额大于Base，采用逆算
                           if ( itemAmountCost >= base )
                           {
                              itemAmountCost = base - base * ( totalFullDays - totalWorkDaysCost ) / AVG_SALARY_DAYS_PER_MONTH;
                           }
                        }
                        // 按月平均计薪天数折算(倒算)
                        else if ( divideType.equals( "5" ) )
                        {
                           // 基本工资  *（1 - 未报道天数/21.75）
                           if ( isFirstOrLastWorkMonth )
                           {
                              itemAmountBill = base * ( 1 - ( totalFullDays - totalWorkDaysBill ) / AVG_SALARY_DAYS_PER_MONTH );
                              itemAmountCost = base * ( 1 - ( totalFullDays - totalWorkDaysCost ) / AVG_SALARY_DAYS_PER_MONTH );
                           }
                           else
                           {
                              // 如果不是入离职月，则和divideType为3 一样算法
                              itemAmountBill = base * totalWorkDaysBill / AVG_SALARY_DAYS_PER_MONTH;

                              // 如果折算下来金额大于Base，采用逆算
                              if ( itemAmountBill >= base )
                              {
                                 itemAmountBill = base - base * ( totalFullDays - totalWorkDaysBill ) / AVG_SALARY_DAYS_PER_MONTH;
                              }

                              itemAmountCost = base * totalWorkDaysCost / AVG_SALARY_DAYS_PER_MONTH;

                              // 如果折算下来金额大于Base，采用逆算
                              if ( itemAmountCost >= base )
                              {
                                 itemAmountCost = base - base * ( totalFullDays - totalWorkDaysCost ) / AVG_SALARY_DAYS_PER_MONTH;
                              }
                           }

                        }
                        // 按自然月天数折算
                        else if ( divideType.equals( "4" ) )
                        {
                           double gapDays = DAYS - totalFullDays;

                           itemAmountBill = base * ( totalWorkDaysBill + timesheetDTO.getNonWorkingDays( salaryStartCalendar, salaryEndCalendar ) ) / ( totalFullDays + gapDays );
                           itemAmountCost = base * ( totalWorkDaysCost + timesheetDTO.getNonWorkingDays( salaryStartCalendar, salaryEndCalendar ) ) / ( totalFullDays + gapDays );
                        }
                        // 按月工作天数折算
                        else
                        {
                           itemAmountBill = base * totalWorkDaysBill / totalFullDays;
                           itemAmountCost = base * totalWorkDaysCost / totalFullDays;
                        }
                     }

                     // 请假扣款计算
                     if ( ( totalDeductDaysBillDTO != null && totalDeductDaysBillDTO.getDeductDayDTOs() != null && totalDeductDaysBillDTO.getDeductDayDTOs().size() > 0 )
                           || ( totalDeductDaysCostDTO != null && totalDeductDaysCostDTO.getDeductDayDTOs() != null && totalDeductDaysCostDTO.getDeductDayDTOs().size() > 0 ) )
                     {
                        for ( String key : totalDeductDaysBillDTO.getItems() )
                        {
                           double totalDeductDaysBill = totalDeductDaysBillDTO.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );
                           double totalDeductDaysCost = totalDeductDaysCostDTO.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );
                           double totalDeductDaysBill_BS = totalDeductDaysBillDTO_BS.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );
                           double totalDeductDaysCost_BS = totalDeductDaysCostDTO_BS.getTotalDeductDays( key, employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate() );

                           // 创建临时扣款天数 - 按照请假类别
                           final Map< String, Double > tempTotalDeductDaysBill = new HashMap< String, Double >();
                           tempTotalDeductDaysBill.put( key, totalDeductDaysBill );
                           final Map< String, Double > tempTotalDeductDaysCost = new HashMap< String, Double >();
                           tempTotalDeductDaysCost.put( key, totalDeductDaysCost );
                           final Map< String, Double > tempTotalDeductDaysBill_BS = new HashMap< String, Double >();
                           tempTotalDeductDaysBill_BS.put( key, totalDeductDaysBill_BS );
                           final Map< String, Double > tempTotalDeductDaysCostBS = new HashMap< String, Double >();
                           tempTotalDeductDaysCostBS.put( key, totalDeductDaysCost_BS );

                           // 初始化ItemGroupDTO
                           final ItemGroupDTO itemGroupDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemGroupDTOByItemId( key, "1" );

                           // 初始化BindItemId，“3”是指请假扣款
                           String bindItemId = "3";

                           if ( itemGroupDTO != null && itemGroupDTO.getItemGroupVO() != null )
                           {
                              bindItemId = itemGroupDTO.getItemGroupVO().getBindItemId();
                           }

                           // 初始化Total Bill Deduct Days 
                           final double totalBillDeductDays = ( itemVO != null && itemVO.getItemType().equals( "1" ) ) ? getTotalDeductDays( tempTotalDeductDaysBill_BS, new ArrayList< String >() )
                                 : getTotalDeductDays( tempTotalDeductDaysBill, KANUtil.jasonArrayToStringList( employeeContractSalaryVO.getExcludeDivideItemIds() ) );

                           // 初始化Total Cost Deduct Days 
                           final double totalCostDeductDays = ( itemVO != null && itemVO.getItemType().equals( "1" ) ) ? getTotalDeductDays( tempTotalDeductDaysCostBS, new ArrayList< String >() )
                                 : getTotalDeductDays( tempTotalDeductDaysCost, KANUtil.jasonArrayToStringList( employeeContractSalaryVO.getExcludeDivideItemIds() ) );

                           // 按月平均计薪天数折算
                           if ( leaveDivideType.equals( "3" ) )
                           {

                              double tempTotalDeductAmountBill = base * totalBillDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // 如果折算下来金额大于Base，采用逆算
                              if ( tempTotalDeductAmountBill > base )
                              {
                                 tempTotalDeductAmountBill = base - base * ( totalFullDays - totalBillDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }

                              //请假天数==全月工作天数,则请假扣款金额等于基本工资
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountBill = base * totalBillDeductDays / totalFullDays;
                              }

                              // 请假扣款累计
                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + tempTotalDeductAmountBill ) );

                              // 当前科目请假扣款
                              double tempTotalDeductAmountCost = base * totalCostDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // 如果折算下来金额大于Base，采用逆算
                              if ( tempTotalDeductAmountCost >= base )
                              {
                                 tempTotalDeductAmountCost = base - base * ( totalFullDays - totalCostDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }
                              //请假天数==全月工作天数,则请假扣款金额等于基本工资
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountCost = base * totalBillDeductDays / totalFullDays;
                              }

                              // 请假扣款累计
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + tempTotalDeductAmountCost ) );
                           }
                           // 按月工作天数折算
                           else if ( leaveDivideType.equals( "2" ) )
                           {
                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + base
                                    * totalBillDeductDays / totalFullDays ) );
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + base
                                    * totalCostDeductDays / totalFullDays ) );
                           }
                           // 按自然月天数折算
                           else if ( leaveDivideType.equals( "4" ) )
                           {
                              double gapDays = DAYS - totalFullDays;

                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + base
                                    * totalBillDeductDays / ( totalFullDays + gapDays ) ) );
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + base
                                    * totalCostDeductDays / ( totalFullDays + gapDays ) ) );
                           }
                           // 按月平均倒算-  请假扣款和月平均正算一样
                           else if ( leaveDivideType.equals( "5" ) )
                           {
                              double tempTotalDeductAmountBill = base * totalBillDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // 如果折算下来金额大于Base，采用逆算
                              if ( tempTotalDeductAmountBill > base )
                              {
                                 tempTotalDeductAmountBill = base - base * ( totalFullDays - totalBillDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }

                              //请假天数==全月工作天数,则请假扣款金额等于基本工资
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountBill = base * totalBillDeductDays / totalFullDays;
                              }

                              // 请假扣款累计
                              totalDeductAmountBills.put( bindItemId, ( ( totalDeductAmountBills.get( bindItemId ) == null ? 0 : totalDeductAmountBills.get( bindItemId ) ) + tempTotalDeductAmountBill ) );

                              // 当前科目请假扣款
                              double tempTotalDeductAmountCost = base * totalCostDeductDays / AVG_SALARY_DAYS_PER_MONTH;

                              // 如果折算下来金额大于Base，采用逆算
                              if ( tempTotalDeductAmountCost >= base )
                              {
                                 tempTotalDeductAmountCost = base - base * ( totalFullDays - totalCostDeductDays ) / AVG_SALARY_DAYS_PER_MONTH;
                              }
                              //请假天数==全月工作天数,则请假扣款金额等于基本工资
                              if ( totalBillDeductDays == totalFullDays )
                              {
                                 tempTotalDeductAmountCost = base * totalBillDeductDays / totalFullDays;
                              }

                              // 请假扣款累计
                              totalDeductAmountCosts.put( bindItemId, ( ( totalDeductAmountCosts.get( bindItemId ) == null ? 0 : totalDeductAmountCosts.get( bindItemId ) ) + tempTotalDeductAmountCost ) );
                           }
                        }
                     }
                  }
                  // 按小时计薪
                  else if ( salaryType.equals( "2" ) )
                  {
                     itemAmountBill = base * totalWorkHoursBill;
                     itemAmountCost = base * totalWorkHoursCost;
                  }

                  // Bill Cap限制
                  if ( itemCap != 0 && itemAmountBill > itemCap )
                  {
                     itemAmountBill = itemCap;
                  }

                  // Bill Floor限制
                  if ( itemFloor != 0 && itemAmountBill < itemFloor )
                  {
                     itemAmountBill = itemFloor;
                  }

                  // Cost Cap限制
                  if ( itemCap != 0 && itemAmountCost > itemCap )
                  {
                     itemAmountCost = itemCap;
                  }

                  // Cost Floor限制
                  if ( itemFloor != 0 && itemAmountCost < itemFloor )
                  {
                     itemAmountCost = itemFloor;
                  }
               }

               /** 公司营收 */
               // 初始化BillAmountCompany
               double billAmountCompany = Double.valueOf( itemAmountBill ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

               // Sales Type为“3”是“打包”方式
               orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               // 添加常量值
               addConstantsValue( itemVO.getItemType(), employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), base, true );
               addConstantsValue( itemVO.getItemType(), employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), billAmountCompany, false );

               // 基本工资和工资调整特殊处理
               if ( itemVO.getItemId().trim().equals( "1" ) || itemVO.getItemId().trim().equals( "2" ) )
               {
                  final ConstantsDTO constantsDTO_401 = CONSTANTS_VALUE.get( "401" );
                  constantsDTO_401.addConstantsDay( employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), base );
                  CONSTANTS_VALUE.put( "401", constantsDTO_401 );

                  final ConstantsDTO constantsDTO_405 = CONSTANTS_VALUE.get( "405" );
                  constantsDTO_405.addConstantsDay( employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), billAmountCompany );
                  CONSTANTS_VALUE.put( "405", constantsDTO_405 );
               }

               // 【野村】调整给特殊处理
               if ( itemVO.getItemId().trim().equals( "10257" ) )
               {
                  final ConstantsDTO constantsDTO_423 = CONSTANTS_VALUE.get( "423" );
                  constantsDTO_423.addConstantsDay( employeeContractSalaryVO.getStartDate(), employeeContractSalaryVO.getEndDate(), base );
                  CONSTANTS_VALUE.put( "423", constantsDTO_423 );
               }

               /** 个人收入 */
               // 初始化BillAmountPersonal
               double billAmountPersonal = Double.valueOf( itemAmountCost ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

               orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

               /** 公司成本 */
               // 初始化CostAmountCompany
               double costAmountCompany = Double.valueOf( itemAmountBill ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

               orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               /** 个人支出 */
               // 初始化CostAmountPersonal
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

               // 装载OrderDetailTempVO
               settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );

            }
         }

         /** 请假扣款科目处理 */
         // 存在请假固定扣款
         if ( sickLeavePayDeductAmount != 0 )
         {
            // “203”为病假扣款
            totalDeductAmountBills.put( "203", totalDeductAmountBills.get( "203" ) + sickLeavePayDeductAmount );
            totalDeductAmountCosts.put( "203", totalDeductAmountCosts.get( "203" ) + sickLeavePayDeductAmount );
         }

         if ( totalDeductAmountBills.size() > 0 && totalDeductAmountCosts.size() > 0 )
         {
            for ( String key : totalDeductAmountBills.keySet() )
            {
               double totalDeductAmountBill = totalDeductAmountBills.get( key );
               double totalDeductAmountCost = totalDeductAmountCosts.get( key );

               // 初始化ItemVO
               final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( key );

               // 初始化ItemCap
               double itemCap = 0;

               if ( itemVO.getItemCap() != null )
               {
                  itemCap = Double.valueOf( itemVO.getItemCap() );
               }

               // 初始化ItemFloor
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
               // 工资性科目不使用Base From，请假扣款Base为“0”，Discount，Multiple都为“1”
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

               // Bill Cap限制
               if ( itemCap != 0 && totalDeductAmountBill > itemCap )
               {
                  totalDeductAmountBill = itemCap;
               }

               // Bill Floor限制
               if ( itemFloor != 0 && totalDeductAmountBill < itemFloor )
               {
                  totalDeductAmountBill = itemFloor;
               }

               // Cost Cap限制
               if ( itemCap != 0 && totalDeductAmountCost > itemCap )
               {
                  totalDeductAmountCost = itemCap;
               }

               // Cost Floor限制
               if ( itemFloor != 0 && totalDeductAmountCost < itemFloor )
               {
                  totalDeductAmountCost = itemFloor;
               }

               /** 公司营收 */
               // 初始化BillAmountCompany
               double billAmountCompany = Double.valueOf( totalDeductAmountBill ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

               // Sales Type为“3”是“打包”方式
               orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               // 添加常量值
               addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

               /** 个人收入 */
               // 初始化BillAmountPersonal
               double billAmountPersonal = Double.valueOf( totalDeductAmountCost ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                     + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

               orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

               /** 公司成本 */
               // 初始化CostAmountCompany
               double costAmountCompany = Double.valueOf( totalDeductAmountBill ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                     + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

               orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

               /** 个人支出 */
               // 初始化CostAmountPersonal
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

               // 装载OrderDetailTempVO
               settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
            }
         }

         /** 加班科目处理 */
         if ( timesheetDTO.getOtDTOs() != null && timesheetDTO.getOtDTOs().size() > 0 )
         {
            for ( OTDTO otDTO : timesheetDTO.getOtDTOs() )
            {
               if ( otDTO.getOtDetailVOs() != null && otDTO.getOtDetailVOs().size() > 0 )
               {
                  for ( OTDetailVO otDetailVO : otDTO.getOtDetailVOs() )
                  {
                     // 不在考勤表范围内的加班，直接跳往下一层循环
                     if ( KANUtil.filterEmpty( otDetailVO.getTimesheetId() ) == null )
                     {
                        continue;
                     }

                     // 初始化ItemVO
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( otDetailVO.getItemId() );

                     // 初始化EmployeeContractOTVO
                     final EmployeeContractOTVO employeeContractOTVO = serviceContractDTO.getEmployeeContractOTVOByItemId( otDetailVO.getItemId() );

                     // 初始化ClientOrderOTVO
                     final ClientOrderOTVO clientOrderOTVO = this.getClientOrderOTVOByItemId( otDetailVO.getItemId() );

                     // 初始化OT Calendar
                     final Calendar otCalendar = KANUtil.createCalendar( otDetailVO.getDay() );

                     // 初始化OT Start Calendar
                     Calendar otStartCalendar = null;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getStartDate() ) != null )
                     {
                        otStartCalendar = KANUtil.createCalendar( employeeContractOTVO.getStartDate() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getStartDate() ) != null )
                     {
                        otStartCalendar = KANUtil.createCalendar( clientOrderOTVO.getStartDate() );
                     }

                     // 初始化OT Hours
                     double otHours = 0;
                     // 请假批准，但没有销假
                     if ( otDetailVO.getStatus().equals( "5" ) && KANUtil.getDays( otCalendar ) >= KANUtil.getDays( tsStartCalendar )
                           && KANUtil.getDays( otCalendar ) <= KANUtil.getDays( tsEndCalendar )
                           && ( otStartCalendar == null || KANUtil.getDays( otCalendar ) >= KANUtil.getDays( otStartCalendar ) ) )
                     {
                        otHours = Double.valueOf( otDetailVO.getActualHours() );
                     }

                     // 初始化ItemCap
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

                     // 初始化ItemFloor
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

                     // 初始化Base
                     double base = 0;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getBase() ) != null )
                     {
                        base = Double.valueOf( employeeContractOTVO.getBase() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getBase() ) != null )
                     {
                        base = Double.valueOf( clientOrderOTVO.getBase() );
                     }

                     // 初始化Percentage
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

                     // 初始化Fix
                     double fix = 0;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getFix() ) != null )
                     {
                        fix = Double.valueOf( employeeContractOTVO.getFix() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getFix() ) != null )
                     {
                        fix = Double.valueOf( clientOrderOTVO.getFix() );
                     }

                     // 初始化Discount
                     double discount = 1;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getDiscount() ) != null )
                     {
                        discount = Double.valueOf( employeeContractOTVO.getDiscount() ) / 100;
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getDiscount() ) != null )
                     {
                        discount = Double.valueOf( clientOrderOTVO.getDiscount() ) / 100;
                     }

                     // 初始化Multiple
                     double multiple = 1;
                     if ( employeeContractOTVO != null && KANUtil.filterEmpty( employeeContractOTVO.getMultiple(), "0" ) != null )
                     {
                        multiple = Double.valueOf( employeeContractOTVO.getDecodeMultiple() );
                     }
                     else if ( clientOrderOTVO != null && KANUtil.filterEmpty( clientOrderOTVO.getMultiple(), "0" ) != null )
                     {
                        multiple = Double.valueOf( clientOrderOTVO.getDecodeMultiple() );
                     }

                     // 初始化BaseFrom
                     String baseFrom = employeeContractOTVO != null ? employeeContractOTVO.getBaseFrom() : null;
                     if ( clientOrderOTVO != null && KANUtil.filterEmpty( baseFrom ) == null )
                     {
                        baseFrom = clientOrderOTVO.getBaseFrom();
                     }

                     // 初始化Formular
                     String formular = employeeContractOTVO != null ? employeeContractOTVO.getFormular() : null;
                     if ( clientOrderOTVO != null && KANUtil.filterEmpty( formular ) == null )
                     {
                        formular = clientOrderOTVO.getFormular();
                     }

                     // Base计算，含BaseFrom和Formular
                     base = ( getFormularValue( formular, getBaseFromValue( settlementTempDTO, baseFrom, base ) * percentage + fix, otDetailVO.getDay() ) * discount * multiple );

                     final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                     orderDetailTempVO.setItemId( itemVO.getItemId() );
                     orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                     orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                     orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                     // 工资性科目不使用Base From
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

                     // 初始化OTAmount
                     double otAmount = base * otHours;

                     // Cap限制
                     if ( itemCap != 0 && otAmount > itemCap )
                     {
                        otAmount = itemCap;
                     }

                     // Floor限制
                     if ( itemFloor != 0 && otAmount < itemFloor )
                     {
                        otAmount = itemFloor;
                     }

                     /** 公司营收 */
                     // 初始化BillAmountCompany
                     double billAmountCompany = Double.valueOf( otAmount ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                           + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                     // Sales Type为“3”是“打包”方式
                     orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                     // 添加常量值
                     addConstantsValue( itemVO.getItemType(), null, null, billAmountCompany, false );

                     /** 个人收入 */
                     // 初始化BillAmountPersonal
                     double billAmountPersonal = Double.valueOf( otAmount ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                           + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                     orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                     /** 公司成本 */
                     // 初始化CostAmountCompany
                     double costAmountCompany = Double.valueOf( otAmount ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                           + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                     orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                     /** 个人支出 */
                     // 初始化CostAmountPersonal
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

                     // 装载OrderDetailTempVO
                     settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
                  }
               }
            }
         }
      }

      // 基础派遣 - 计算结果导入的情况
      if ( serviceContractDTO.getSalaryDTOs() != null && serviceContractDTO.getSalaryDTOs().size() > 0 )
      {
         for ( SalaryDTO salaryDTO : serviceContractDTO.getSalaryDTOs() )
         {
            // 实发工资获取（用作带算税）
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
               // 初始化ItemVO
               final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( salaryDetailVO.getItemId() );

               // 初始化OrderDetailTempVO
               final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
               orderDetailTempVO.setDetailId( salaryDetailVO.getSalaryDetailId() );
               orderDetailTempVO.setDetailType( "5" );
               orderDetailTempVO.setItemId( itemVO.getItemId() );
               orderDetailTempVO.setItemNo( itemVO.getItemNo() );
               orderDetailTempVO.setNameZH( itemVO.getNameZH() );
               orderDetailTempVO.setNameEN( itemVO.getNameEN() );
               // 工资性科目不使用Base From
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

               // 初始化BillAmountCompany
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

               // 装载OrderDetailTempVO
               settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
            }
         }
      }
   }

   // 装载其他（其他费用、第三方成本、风险基金）
   private void fetchOther( final SettlementTempDTO settlementTempDTO, final ServiceContractDTO serviceContractDTO, final List< EmployeeContractOtherVO > employeeContractOtherVOs,
         final Calendar calculateStartCalendar, final Calendar calculateEndCalendar ) throws KANException
   {
      if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
      {
         for ( EmployeeContractOtherVO employeeContractOtherVO : employeeContractOtherVOs )
         {
            if ( serviceContractDTO != null && serviceContractDTO.getExistItemIds() != null && !serviceContractDTO.getExistItemIds().contains( employeeContractOtherVO.getItemId() ) )
            {
               // 初始化Other Start Calendar
               Calendar otherStartCalendar = null;
               if ( KANUtil.filterEmpty( employeeContractOtherVO.getStartDate() ) != null )
               {
                  otherStartCalendar = KANUtil.createCalendar( employeeContractOtherVO.getStartDate() );
               }

               // 初始化Other End Calendar
               Calendar otherEndCalendar = null;
               if ( KANUtil.filterEmpty( employeeContractOtherVO.getEndDate() ) != null )
               {
                  otherEndCalendar = KANUtil.createCalendar( employeeContractOtherVO.getEndDate() );
               }

               if ( withinCircle( calculateStartCalendar, calculateEndCalendar, employeeContractOtherVO.getCycle(), otherStartCalendar, otherEndCalendar ) )
               {
                  // 初始化ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( employeeContractOtherVO.getItemId() );

                  // 初始化ClientOrderOtherVO
                  final ClientOrderOtherVO clientOrderOtherVO = this.getClientOrderOtherVOByItemId( employeeContractOtherVO.getItemId() );

                  // 初始化ItemCap
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

                  // 初始化ItemFloor
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

                  // 初始化Base
                  double base = 0;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getBase() ) != null )
                  {
                     base = Double.valueOf( employeeContractOtherVO.getBase() );
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getBase() ) != null )
                  {
                     base = Double.valueOf( employeeContractOtherVO.getBase() );
                  }

                  // 初始化Percentage
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

                  // 初始化Fix
                  double fix = 0;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getFix() ) != null )
                  {
                     fix = Double.valueOf( employeeContractOtherVO.getFix() );
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getFix() ) != null )
                  {
                     fix = Double.valueOf( clientOrderOtherVO.getFix() );
                  }

                  // 初始化Discount
                  double discount = 1;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getDiscount() ) != null )
                  {
                     discount = Double.valueOf( employeeContractOtherVO.getDiscount() ) / 100;
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getDiscount() ) != null )
                  {
                     discount = Double.valueOf( clientOrderOtherVO.getDiscount() ) / 100;
                  }

                  // 初始化Multiple
                  double multiple = 1;
                  if ( KANUtil.filterEmpty( employeeContractOtherVO.getMultiple(), "0" ) != null )
                  {
                     multiple = Double.valueOf( employeeContractOtherVO.getDecodeMultiple() );
                  }
                  else if ( clientOrderOtherVO != null && KANUtil.filterEmpty( clientOrderOtherVO.getMultiple(), "0" ) != null )
                  {
                     multiple = Double.valueOf( clientOrderOtherVO.getDecodeMultiple() );
                  }

                  // 初始化BaseFrom
                  String baseFrom = employeeContractOtherVO.getBaseFrom();
                  if ( KANUtil.filterEmpty( baseFrom ) == null && clientOrderOtherVO != null )
                  {
                     baseFrom = clientOrderOtherVO.getBaseFrom();
                  }

                  // 初始化Formular
                  String formular = employeeContractOtherVO.getFormular();
                  if ( KANUtil.filterEmpty( formular ) == null && clientOrderOtherVO != null )
                  {
                     formular = clientOrderOtherVO.getFormular();
                  }

                  // Base计算，含BaseFrom和Formular
                  base = getFormularValue( formular, ( getBaseFromValue( settlementTempDTO, baseFrom, base ) * percentage + fix ) * discount * multiple, null );

                  // Cap限制
                  if ( itemCap != 0 && base > itemCap )
                  {
                     base = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && base < itemFloor )
                  {
                     base = itemFloor;
                  }

                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // 工资性科目不使用Base From
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

                  /** 公司营收 */
                  // 初始化BillAmountCompany
                  double billAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Sales Type为“3”是“打包”方式
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // 添加常量值
                  addConstantsValue( itemVO.getItemType(), employeeContractOtherVO.getStartDate(), employeeContractOtherVO.getEndDate(), billAmountCompany, false );

                  /** 个人收入 */
                  // 初始化BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 公司成本 */
                  // 初始化CostAmountCompany
                  double costAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 个人支出 */
                  // 初始化CostAmountPersonal
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

                  // 装载OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // 装载服务费
   private void fetchServiceFee( final SettlementTempDTO settlementTempDTO, final ServiceContractDTO serviceContractDTO, final List< ClientOrderDetailDTO > clientOrderDetailDTOs,
         final Calendar calculateStartCalendar, final Calendar calculateEndCalendar ) throws KANException
   {
      if ( clientOrderDetailDTOs != null && clientOrderDetailDTOs.size() > 0 )
      {
         for ( ClientOrderDetailDTO clientOrderDetailDTO : clientOrderDetailDTOs )
         {
            // 初始化ClientOrderDetailVO
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailDTO.getClientOrderDetailVO();

            if ( serviceContractDTO != null
                  && ( KANUtil.filterEmpty( clientOrderDetailVO.getBaseFrom(), "0" ) != null || ( serviceContractDTO.getExistItemIds() != null && !serviceContractDTO.getExistItemIds().contains( clientOrderDetailVO.getItemId() ) ) ) )
            {
               // 初始化Service Fee Start Calendar
               Calendar serviceFeeStartCalendar = null;
               if ( KANUtil.filterEmpty( clientOrderDetailVO.getStartDate() ) != null )
               {
                  serviceFeeStartCalendar = KANUtil.createCalendar( clientOrderDetailVO.getStartDate() );
               }

               // 初始化Service Fee End Calendar
               Calendar serviceFeeEndCalendar = null;
               if ( KANUtil.filterEmpty( clientOrderDetailVO.getEndDate() ) != null )
               {
                  serviceFeeEndCalendar = KANUtil.createCalendar( clientOrderDetailVO.getEndDate() );
               }

               // 初始化CalculateType
               final String calculateType = clientOrderDetailVO.getCalculateType();

               // 标记 - 是否需要收取服务费
               boolean needCharge = false;

               // 判断计费方式是否满足要求
               if ( KANUtil.filterEmpty( calculateType, "0" ) != null
                     && ( ( calculateType.equals( "1" ) && CONSTANTS_VALUE.get( "416" ).getValue() != 0 )
                           || ( calculateType.equals( "2" ) && CONSTANTS_VALUE.get( "417" ).getValue() != 0 )
                           || ( calculateType.equals( "3" ) && CONSTANTS_VALUE.get( "415" ).getValue() != 0 ) || calculateType.equals( "4" ) ) )
               {
                  needCharge = true;
               }

               // 判断当前周期是否满足要求
               needCharge = withinCircle( calculateStartCalendar, calculateEndCalendar, clientOrderDetailVO.getCycle(), serviceFeeStartCalendar, serviceFeeEndCalendar );

               // 入职不收服务费判别
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
                     // 初始化试用期月数
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

               // 离职不收服务费，自动离职不收服务费判别
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

               // 如果公司和个人部分都为“0”，不收服务费
               if ( settlementTempDTO.getBillAmountCompany() == 0 && settlementTempDTO.getBillAmountPersonal() == 0 && settlementTempDTO.getCostAmountCompany() == 0
                     && settlementTempDTO.getCostAmountPersonal() == 0 )
               {
                  needCharge = false;
               }

               if ( needCharge )
               {
                  // 初始化ItemVO
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getItemVOByItemId( clientOrderDetailVO.getItemId() );

                  // 初始化ItemCap
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

                  // 初始化ItemFloor
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

                  // 初始化Base
                  double base = 0;

                  // 判断计费方式是否满足要求
                  if ( ( KANUtil.filterEmpty( calculateType, "0" ) == null
                  // 社保
                        || ( calculateType.equals( "1" ) && CONSTANTS_VALUE.get( "416" ).getValue() != 0 )
                        // 商保
                        || ( calculateType.equals( "2" ) && CONSTANTS_VALUE.get( "417" ).getValue() != 0 )
                        // 工资
                        || ( calculateType.equals( "3" ) && CONSTANTS_VALUE.get( "415" ).getValue() != 0 )
                  // 结算
                  || calculateType.equals( "4" ) )
                        && KANUtil.filterEmpty( clientOrderDetailVO.getBase() ) != null
                        // 按人头或订单判断
                        && KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ) != null
                        && ( KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ).equals( "1" ) || ( KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ).equals( "2" ) && ORDER_AMOUNT == 0 ) ) )
                  {
                     base = Double.valueOf( clientOrderDetailVO.getBase() );
                  }

                  // 初始化Percentage
                  double percentage = 1;
                  if ( KANUtil.filterEmpty( clientOrderDetailVO.getPercentage() ) != null && Double.valueOf( clientOrderDetailVO.getPercentage() ) != 0 )
                  {
                     percentage = Double.valueOf( clientOrderDetailVO.getPercentage() ) / 100;
                  }

                  // 初始化Fix
                  double fix = 0;
                  if ( clientOrderDetailVO.getFix() != null && !clientOrderDetailVO.getFix().trim().equals( "" ) )
                  {
                     fix = Double.valueOf( clientOrderDetailVO.getFix() );
                  }

                  // 初始化Discount
                  double discount = 1;
                  if ( clientOrderDetailVO.getDiscount() != null && !clientOrderDetailVO.getDiscount().trim().equals( "" ) )
                  {
                     discount = Double.valueOf( clientOrderDetailVO.getDiscount() ) / 100;
                  }

                  // 初始化Multiple
                  double multiple = 1;
                  if ( clientOrderDetailVO.getMultiple() != null && KANUtil.filterEmpty( clientOrderDetailVO.getMultiple(), "0" ) != null )
                  {
                     multiple = Double.valueOf( clientOrderDetailVO.getDecodeMultiple() );
                  }

                  // Base计算，含BaseFrom和Formular
                  base = getFormularValue( clientOrderDetailVO.getFormular(), ( getBaseFromValue( settlementTempDTO, clientOrderDetailVO.getBaseFrom(), base ) * percentage + fix )
                        * discount * multiple, null );

                  // Cap限制
                  if ( itemCap != 0 && base > itemCap )
                  {
                     base = itemCap;
                  }

                  // Floor限制
                  if ( itemFloor != 0 && base < itemFloor )
                  {
                     base = itemFloor;
                  }

                  final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
                  orderDetailTempVO.setItemId( itemVO.getItemId() );
                  orderDetailTempVO.setItemNo( itemVO.getItemNo() );
                  orderDetailTempVO.setNameZH( itemVO.getNameZH() );
                  orderDetailTempVO.setNameEN( itemVO.getNameEN() );
                  // 工资性科目不使用Base From
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

                  // 服务费折算
                  if ( base != 0 )
                  {
                     // 按工作天数折算
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
                     // 按工作天数折算（含带薪假）
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
                     // 按日历天数折算
                     else if ( KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ) != null && KANUtil.filterEmpty( clientOrderDetailVO.getDivideType() ).equals( "4" ) )
                     {
                        // 初始化Gap Calendar
                        Calendar gapStartCalendar = calculateStartCalendar;
                        Calendar gapEndCalendar = calculateEndCalendar;

                        // 初始化Gap Days
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

                     // 订单明细规则处理
                     if ( clientOrderDetailDTO.getClientOrderDetailRuleVOs() != null && clientOrderDetailDTO.getClientOrderDetailRuleVOs().size() > 0 )
                     {
                        // 初始化RulePrice和RuleDiscount
                        double rulePrice = -1;
                        double ruleDiscount = 1;

                        // 遍历订单明细规则
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
                              // 费用人数大于等于的情况
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
                              // 入职日期晚于等于的情况
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
                              // 离职日期早于等于的情况
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
                              // 在职天数工作日小于等于的情况
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
                              // 在职天数日历日小于等于的情况
                              else if ( ruleType.trim().equals( "5" ) )
                              {
                                 // 初始化Gap Calendar
                                 Calendar gapStartCalendar = calculateStartCalendar;
                                 Calendar gapEndCalendar = calculateEndCalendar;

                                 // 初始化Gap Days
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
                              // 科目金额大于等于的情况
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

                  // 打包方式管理费是否收取
                  boolean hasServiceFee = false;

                  // 设置订单总金额
                  if ( KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ) != null && KANUtil.filterEmpty( clientOrderDetailVO.getPackageType() ).equals( "2" )
                        && ORDER_AMOUNT == 0 )
                  {
                     ORDER_AMOUNT = base;
                     hasServiceFee = true;
                  }

                  // 社保补缴费用处理SB_SUPPLEMENTARY_MONTHS
                  if ( clientOrderDetailDTO.getClientOrderDetailSBRuleVOs() != null && clientOrderDetailDTO.getClientOrderDetailSBRuleVOs().size() > 0 )
                  {
                     for ( ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO : clientOrderDetailDTO.getClientOrderDetailSBRuleVOs() )
                     {
                        if ( clientOrderDetailSBRuleVO != null && KANUtil.filterEmpty( clientOrderDetailSBRuleVO.getSbRuleType() ) != null
                              && KANUtil.filterEmpty( clientOrderDetailSBRuleVO.getAmount() ) != null && Double.valueOf( clientOrderDetailSBRuleVO.getAmount() ) != 0
                              && SB_SUPPLEMENTARY_MONTHS.get( clientOrderDetailSBRuleVO.getSbSolutionId() ) != null
                              && SB_SUPPLEMENTARY_MONTHS.get( clientOrderDetailSBRuleVO.getSbSolutionId() ) > 0 )
                        {
                           // 按人次收费
                           if ( "2".equals( clientOrderDetailSBRuleVO.getSbRuleType() ) )
                           {
                              base = base + Double.valueOf( clientOrderDetailSBRuleVO.getAmount() );
                           }
                           // 按月份收费
                           else if ( "3".equals( clientOrderDetailSBRuleVO.getSbRuleType() ) )
                           {
                              base = base + Double.valueOf( clientOrderDetailSBRuleVO.getAmount() ) * SB_SUPPLEMENTARY_MONTHS.get( clientOrderDetailSBRuleVO.getSbSolutionId() );
                           }
                        }
                     }
                  }

                  /** 公司营收 */
                  // 初始化BillAmountCompany
                  double billAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixCompany() );

                  // Sales Type为“3”是“打包”方式
                  orderDetailTempVO.setBillAmountCompany( KANUtil.round( SALES_TYPE.trim().equals( "3" ) && !hasServiceFee ? 0 : billAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  // 添加常量值
                  addConstantsValue( itemVO.getItemType(), clientOrderDetailVO.getStartDate(), clientOrderDetailVO.getEndDate(), billAmountCompany, false );

                  /** 个人收入 */
                  // 初始化BillAmountPersonal
                  double billAmountPersonal = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getBillRatePersonal() ) / 100
                        + Double.valueOf( orderDetailTempVO.getBillFixPersonal() );

                  orderDetailTempVO.setBillAmountPersonal( KANUtil.round( billAmountPersonal, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 公司成本 */
                  // 初始化CostAmountCompany
                  double costAmountCompany = Double.valueOf( base ) * Double.valueOf( orderDetailTempVO.getCostRateCompany() ) / 100
                        + Double.valueOf( orderDetailTempVO.getCostFixCompany() );

                  orderDetailTempVO.setCostAmountCompany( KANUtil.round( costAmountCompany, Integer.valueOf( ACCURACY ), ROUND ) );

                  /** 个人支出 */
                  // 初始化CostAmountPersonal
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

                  // 装载OrderDetailTempVO
                  settlementTempDTO.getOrderDetailTempVOs().add( orderDetailTempVO );
               }
            }
         }
      }
   }

   // 判断是否在周期内
   private boolean withinCircle( final Calendar tsStartCalendar, final Calendar tsEndCalendar, final String circle, final Calendar itemStartCalendar, final Calendar itemEndCalendar )
   {
      // 初始化返回状态
      boolean withinCircle = true;

      // Circle有设置
      if ( circle != null && !circle.trim().equals( "" ) && !circle.trim().equals( "0" ) && !circle.trim().equals( "1" ) )
      {
         // 一次性 - 只判断开始时间
         if ( circle.trim().equals( "13" ) )
         {
            if ( itemStartCalendar != null
                  && ( KANUtil.getDays( itemStartCalendar ) < KANUtil.getDays( tsStartCalendar ) || KANUtil.getDays( itemStartCalendar ) > KANUtil.getDays( tsEndCalendar ) ) )
            {
               withinCircle = false;
            }
         }
         // 2-12个月的情况
         else
         {
            if ( itemStartCalendar != null )
            {
               // 用以保存时间循环是否能够包含
               boolean fired = false;

               // 循环， TS的结束时间大于等Item开始时间+周期的时间
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
      // Circle未设置或每月需要结算，处理相同
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

   // 统计工作小时数
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

   // 合并订单以及服务协议的其他设置
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
            // 初始化ItemVO
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
               // 初始化ItemVO
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

   // 按照Exclude Leaves获得折算扣除天数
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

   // 获取Sick Leave Pay Rate
   private double getSickLeavePayRate( final SickLeaveSalaryDTO sickLeaveSalaryDTO, final String itemId, final String startWorkDate, final String onboardDate,
         final String contractStartDate, final String monthly, final String circleEndDay )
   {
      try
      {
         // 初始化SickLeaveSalaryDetailVO
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

   // 获取Sick Leave Pay Deduct
   private double getSickLeavePayDeduct( final SickLeaveSalaryDTO sickLeaveSalaryDTO, final String itemId, final String startWorkDate, final String onboardDate,
         final String contractStartDate, final String monthly, final String circleEndDay )
   {
      try
      {
         // 初始化SickLeaveSalaryDetailVO
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

   // 获取SickLeaveSalaryDetailVO - 按条件
   private SickLeaveSalaryDetailVO getSickLeaveSalaryDetailVO( final SickLeaveSalaryDTO sickLeaveSalaryDTO, final String itemId, final String startWorkDate,
         final String onboardDate, final String contractStartDate, final String monthly, final String circleEndDay ) throws KANException
   {
      if ( sickLeaveSalaryDTO != null && sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO() != null
            && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getItemId() ) != null
            && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getItemId() ).trim().equals( itemId ) )
      {
         // 初始化Gap Months
         int gapMonths = 0;

         // 入司日期
         if ( KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ) != null
               && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ).equals( "1" ) )
         {
            gapMonths = KANUtil.getGapMonth( onboardDate, KANUtil.getEndDate( monthly, circleEndDay ) );
         }
         // 开始工作日期
         else if ( KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ) != null
               && KANUtil.filterEmpty( sickLeaveSalaryDTO.getSickLeaveSalaryHeaderVO().getBaseOn() ).equals( "2" ) )
         {
            gapMonths = KANUtil.getGapMonth( startWorkDate, KANUtil.getEndDate( monthly, circleEndDay ) );
         }

         // 如果入司日期和开始工作日期都没有设，取当前合同的开始日期
         if ( gapMonths == 0 )
         {
            gapMonths = KANUtil.getGapMonth( contractStartDate, KANUtil.getEndDate( monthly, circleEndDay ) );

            // 默认情况工作一个月
            if ( gapMonths == 0 )
            {
               gapMonths = 1;
            }
         }

         // 遍历
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
    * 判断是否是入离职
    * @param onBoardDate
    * @param monthly
    * @return
    */
   private boolean isFirstOrLastWorkMonth( String onBoardDate, String resignDate, final String monthly, final ServiceContractDTO serviceContractDTO,
         final List< ServiceContractDTO > serviceContractDTOs )
   {
      // 默认不是
      boolean flag = false;
      try
      {
         // 如果onboardDate 为空则取合同开始时间。如果resignDate 为空则取合同结束时间
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
            //获取最大的日期
            onBoardDate = getLastestDate( myContracts, true );
         }

         if ( KANUtil.filterEmpty( resignDate ) == null )
         {
            //获取最大的日期
            resignDate = getLastestDate( myContracts, false );
         }

         final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
         final String yearCalc = monthly.split( "/" )[ 0 ];
         final String monthlyCalc = monthly.split( "/" )[ 1 ];
         // 判断入职
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
         // 判断离职
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
         System.err.println( "在职日期格式有问题" );
         return false;
      }
      return flag;
   }

   /**
    * @param myContracts
    * @param flag 是不是开始日期
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
         // 如果是开始时间
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
                  System.err.println( "格式化日期出错getLastestDate();date=" + employeeContractVO.getStartDate() );
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
                  System.err.println( "格式化日期出错getLastestDate();date=" + employeeContractVO.getStartDate() );
               }
            }
         }
         //如果是结束时间
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
                     System.err.println( "格式化日期出错getLastestDate(end);date=" + employeeContractVO.getEndDate() );
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
                     System.err.println( "格式化日期出错getLastestDate();date=" + employeeContractVO.getEndDate() );
                  }
               }
            }

         }
      }
      return resultDate;
   }

   /**
    * 判断当前的结算是否是多分合同
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

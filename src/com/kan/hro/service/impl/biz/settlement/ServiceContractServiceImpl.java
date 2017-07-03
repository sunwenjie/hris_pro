package com.kan.hro.service.impl.biz.settlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.payment.PaymentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.EMPPaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDTO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.settlement.AdjustmentDTO;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.settlement.SettlementDTO;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class ServiceContractServiceImpl extends ContextService implements ServiceContractService
{
   // 注入ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 注入EmployeeDao
   private EmployeeDao employeeDao;

   // 注入EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // 注入TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // 注入OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // 注入PaymentAdjustmentHeaderDao
   private PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao;

   // 注入PaymentAdjustmentDetailDao
   private PaymentAdjustmentDetailDao paymentAdjustmentDetailDao;

   // 注入PaymentHeaderDao
   private PaymentHeaderDao paymentHeaderDao;

   // 注入PaymentDetailDao
   private PaymentDetailDao paymentDetailDao;

   // 注入AdjustmentHeaderDao
   private AdjustmentHeaderDao adjustmentHeaderDao;

   // 注入AdjustmentDetailDao
   private AdjustmentDetailDao adjustmentDetailDao;

   public final ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public final void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public final void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public final TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public final void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
   }

   public final OrderDetailDao getOrderDetailDao()
   {
      return orderDetailDao;
   }

   public final void setOrderDetailDao( OrderDetailDao orderDetailDao )
   {
      this.orderDetailDao = orderDetailDao;
   }

   public final PaymentAdjustmentHeaderDao getPaymentAdjustmentHeaderDao()
   {
      return paymentAdjustmentHeaderDao;
   }

   public final void setPaymentAdjustmentHeaderDao( PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao )
   {
      this.paymentAdjustmentHeaderDao = paymentAdjustmentHeaderDao;
   }

   public final PaymentAdjustmentDetailDao getPaymentAdjustmentDetailDao()
   {
      return paymentAdjustmentDetailDao;
   }

   public final void setPaymentAdjustmentDetailDao( PaymentAdjustmentDetailDao paymentAdjustmentDetailDao )
   {
      this.paymentAdjustmentDetailDao = paymentAdjustmentDetailDao;
   }

   public final PaymentHeaderDao getPaymentHeaderDao()
   {
      return paymentHeaderDao;
   }

   public final void setPaymentHeaderDao( PaymentHeaderDao paymentHeaderDao )
   {
      this.paymentHeaderDao = paymentHeaderDao;
   }

   public final PaymentDetailDao getPaymentDetailDao()
   {
      return paymentDetailDao;
   }

   public final void setPaymentDetailDao( PaymentDetailDao paymentDetailDao )
   {
      this.paymentDetailDao = paymentDetailDao;
   }

   public final AdjustmentHeaderDao getAdjustmentHeaderDao()
   {
      return adjustmentHeaderDao;
   }

   public final void setAdjustmentHeaderDao( AdjustmentHeaderDao adjustmentHeaderDao )
   {
      this.adjustmentHeaderDao = adjustmentHeaderDao;
   }

   public final AdjustmentDetailDao getAdjustmentDetailDao()
   {
      return adjustmentDetailDao;
   }

   public final void setAdjustmentDetailDao( AdjustmentDetailDao adjustmentDetailDao )
   {
      this.adjustmentDetailDao = adjustmentDetailDao;
   }

   @Override
   public PagedListHolder getServiceContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ServiceContractDao serviceContractDao = ( ServiceContractDao ) getDao();
      pagedListHolder.setHolderSize( serviceContractDao.countServiceContractVOsByCondition( ( ServiceContractVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( serviceContractDao.getServiceContractVOsByCondition( ( ServiceContractVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( serviceContractDao.getServiceContractVOsByCondition( ( ServiceContractVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ServiceContractVO getServiceContractVOByContractId( final String contractId ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).getServiceContractVOByContractId( contractId );
   }

   @Override
   public int updateServiceContract( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).updateServiceContract( serviceContractVO );
   }

   @Override
   public int insertServiceContract( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).insertServiceContract( serviceContractVO );
   }

   @Override
   public int deleteServiceContract( final String contractId ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).deleteServiceContract( contractId );
   }

   @Override
   public List< Object > getServiceContractVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).getServiceContractVOsByOrderHeaderId( orderHeaderId );
   }

   @Override
   // 用于薪酬调整数据获取
   // Added by Kevin Jin at 2013-12-30
   public EMPPaymentDTO getEMPPaymentDTOByCondition( final ServiceContractVO serviceContractVO, final String adjustmentStatus ) throws KANException
   {
      // 初始化EmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( serviceContractVO.getEmployeeContractId() );
      // 初始化EntityVO
      final EntityVO entityVO = KANConstants.getKANAccountConstants( employeeContractVO.getAccountId() ).getEntityVOByEntityId( employeeContractVO.getEntityId() );

      // 初始化PaymentHeaderVO（搜索条件）
      PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
      paymentHeaderVO.setCorpId( serviceContractVO.getCorpId() );
      paymentHeaderVO.setContractId( serviceContractVO.getEmployeeContractId() );
      paymentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      // 获取新建状态的PaymentHeaderVO
      paymentHeaderVO.setStatus( "1" );
      final List< Object > paymentHeaderVOs = getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

      // 如果当前服务协议已存在新建状态的薪酬数据
      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         return null;
      }

      // 初始化EMPPaymentDTO
      final EMPPaymentDTO empPaymentDTO = new EMPPaymentDTO();

      // 装载EmployeeVO
      empPaymentDTO.setEmployeeVO( getEmployeeDao().getEmployeeVOByEmployeeId( serviceContractVO.getEmployeeId() ) );

      // 装载PaymentDTO列表（当月已经存在，合并计税使用）
      paymentHeaderVO = new PaymentHeaderVO();
      paymentHeaderVO.setAccountId( serviceContractVO.getAccountId() );
      paymentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

      // 如果法务实体需要独立报税
      if ( entityVO != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ) != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ).equals( "1" ) )
      {
         paymentHeaderVO.setEntityId( entityVO.getEntityId() );
      }

      paymentHeaderVO.setEmployeeId( serviceContractVO.getEmployeeId() );
      paymentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      fetchPayment( empPaymentDTO, paymentHeaderVO );

      // 装载PaymentAdjustmentDTO（当月已经批准、发放的调整，合并计税使用）
      final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
      paymentAdjustmentHeaderVO.setAccountId( serviceContractVO.getAccountId() );
      paymentAdjustmentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

      // 如果法务实体需要独立报税
      if ( entityVO != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ) != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ).equals( "1" ) )
      {
         paymentAdjustmentHeaderVO.setEntityId( serviceContractVO.getEntityId() );
      }

      paymentAdjustmentHeaderVO.setEmployeeId( serviceContractVO.getEmployeeId() );
      paymentAdjustmentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      paymentAdjustmentHeaderVO.setStatus( adjustmentStatus != null ? adjustmentStatus + ", 3, 5" : "3, 5" );
      fetchPaymentAdjustment( empPaymentDTO, paymentAdjustmentHeaderVO );

      return empPaymentDTO;
   }

   @Override
   // 用于薪酬数据获取
   // Added by Kevin Jin at 2013-12-04
   public List< EMPPaymentDTO > getEMPPaymentDTOsByCondition( final ServiceContractVO serviceContractVO ) throws KANException
   {
      // 初始化EMPPaymentDTO List
      final List< EMPPaymentDTO > empPaymentDTOs = new ArrayList< EMPPaymentDTO >();

      // 获取符合工资计算的数据
      final List< Object > serviceContractVOs = ( ( ServiceContractDao ) getDao() ).getPaymentServiceContractVOsByCondition( serviceContractVO );

      final String optionIndependenceTax = KANConstants.getKANAccountConstants( serviceContractVO.getAccountId() ).OPTIONS_INDEPENDENCE_TAX;

      if ( serviceContractVOs != null && serviceContractVOs.size() > 0 )
      {
         for ( Object serviceContractVOObject : serviceContractVOs )
         {
            // 类型转换
            final ServiceContractVO tempServiceContractVO = ( ServiceContractVO ) serviceContractVOObject;

            // 初始化EntityVO
            final EntityVO entityVO = KANConstants.getKANAccountConstants( tempServiceContractVO.getAccountId() ).getEntityVOByEntityId( tempServiceContractVO.getEntityId() );

            boolean independenceTax = false;

            // 判断是否独立报税
            if ( ( KANUtil.filterEmpty( optionIndependenceTax ) != null && KANUtil.filterEmpty( optionIndependenceTax ).equals( "1" ) ) )
            {
               independenceTax = true;
            }

            // 初始化EmployeeVO
            final EmployeeVO employeeVO = getEmployeeDao().getEmployeeVOByEmployeeId( tempServiceContractVO.getEmployeeId() );

            if ( employeeVO != null )
            {
               // 初始化PaymentHeaderVO（搜索条件）
               PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setContractId( tempServiceContractVO.getEmployeeContractId() );
               paymentHeaderVO.setMonthly( tempServiceContractVO.getMonthly() );
               // 获取新建状态的PaymentHeaderVO
               paymentHeaderVO.setStatus( "1" );
               final List< Object > paymentHeaderVOs = getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

               // 如果当前服务协议已存在新建状态的薪酬数据
               if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
               {
                  continue;
               }

               // 初始化EMPPaymentDTO
               final EMPPaymentDTO empPaymentDTO = getEMPPaymentDTO( empPaymentDTOs, tempServiceContractVO.getEmployeeId(), independenceTax && entityVO != null ? entityVO.getEntityId()
                     : null );
               empPaymentDTO.setEmployeeVO( getEmployeeDao().getEmployeeVOByEmployeeId( tempServiceContractVO.getEmployeeId() ) );

               // 装载PaymentDTO列表（当月已经存在，合并计税使用）
               paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setAccountId( tempServiceContractVO.getAccountId() );
               paymentHeaderVO.setCorpId( tempServiceContractVO.getCorpId() );

               // 如果法务实体需要独立报税
               if ( independenceTax && entityVO != null )
               {
                  paymentHeaderVO.setEntityId( entityVO.getEntityId() );
               }

               paymentHeaderVO.setEmployeeId( tempServiceContractVO.getEmployeeId() );
               paymentHeaderVO.setMonthly( tempServiceContractVO.getMonthly() );
               fetchPayment( empPaymentDTO, paymentHeaderVO );

               // 装载PaymentAdjustmentDTO（当月已经批准、发放的调整，合并计税使用）
               final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
               paymentAdjustmentHeaderVO.setAccountId( tempServiceContractVO.getAccountId() );
               paymentAdjustmentHeaderVO.setCorpId( tempServiceContractVO.getCorpId() );

               // 如果法务实体需要独立报税
               if ( independenceTax )
               {
                  paymentAdjustmentHeaderVO.setEntityId( tempServiceContractVO.getEntityId() );
               }

               paymentAdjustmentHeaderVO.setEmployeeId( tempServiceContractVO.getEmployeeId() );
               paymentAdjustmentHeaderVO.setMonthly( tempServiceContractVO.getMonthly() );
               paymentAdjustmentHeaderVO.setStatus( "3, 5" );
               fetchPaymentAdjustment( empPaymentDTO, paymentAdjustmentHeaderVO );

               // 装载SettlementDTO列表（未计算薪酬的数据）
               fetchSettlement( empPaymentDTO, tempServiceContractVO );
            }
         }
      }

      // 初始化AdjustmentHeaderVO
      final AdjustmentHeaderVO adjustmentHeaderVO = new AdjustmentHeaderVO();
      adjustmentHeaderVO.setAccountId( serviceContractVO.getAccountId() );
      adjustmentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      adjustmentHeaderVO.setEntityId( serviceContractVO.getEntityId() );
      adjustmentHeaderVO.setBusinessTypeId( serviceContractVO.getBusinessTypeId() );
      adjustmentHeaderVO.setClientId( serviceContractVO.getClientId() );
      adjustmentHeaderVO.setCorpId( serviceContractVO.getCorpId() );
      adjustmentHeaderVO.setOrderId( serviceContractVO.getOrderId() );
      adjustmentHeaderVO.setEmployeeId( serviceContractVO.getEmployeeId() );
      adjustmentHeaderVO.setContractId( serviceContractVO.getContractId() );

      // 获取符合工资计算的结算调整数据
      final List< Object > adjustmentHeaderVOs = this.getAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOsByCondition( adjustmentHeaderVO );

      if ( adjustmentHeaderVOs != null && adjustmentHeaderVOs.size() > 0 )
      {
         for ( Object adjustmentHeaderVOObject : adjustmentHeaderVOs )
         {
            // 类型转换
            final AdjustmentHeaderVO tempAdjustmentHeaderVO = ( AdjustmentHeaderVO ) adjustmentHeaderVOObject;

            // 初始化EntityVO
            final EntityVO entityVO = KANConstants.getKANAccountConstants( tempAdjustmentHeaderVO.getAccountId() ).getEntityVOByEntityId( tempAdjustmentHeaderVO.getEntityId() );

            boolean independenceTax = false;

            // 判断是否独立报税
            if ( entityVO != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ) != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ).equals( "1" ) )
            {
               independenceTax = true;
            }

            // 初始化EmployeeVO
            final EmployeeVO employeeVO = getEmployeeDao().getEmployeeVOByEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() );

            if ( employeeVO != null )
            {
               // 初始化EMPPaymentDTO
               final EMPPaymentDTO empPaymentDTO = getEMPPaymentDTO( empPaymentDTOs, tempAdjustmentHeaderVO.getEmployeeId(), independenceTax && entityVO != null ? entityVO.getEntityId()
                     : null );
               empPaymentDTO.setEmployeeVO( getEmployeeDao().getEmployeeVOByEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() ) );

               // 装载PaymentDTO列表（当月已经存在，合并计税使用）
               if ( empPaymentDTO.getPaymentDTOs() == null || empPaymentDTO.getPaymentDTOs().size() == 0 )
               {
                  final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
                  paymentHeaderVO.setAccountId( tempAdjustmentHeaderVO.getAccountId() );
                  paymentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

                  // 如果法务实体需要独立报税
                  if ( independenceTax && entityVO != null )
                  {
                     paymentHeaderVO.setEntityId( entityVO.getEntityId() );
                  }
                  paymentHeaderVO.setEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() );
                  paymentHeaderVO.setMonthly( tempAdjustmentHeaderVO.getMonthly() );
                  fetchPayment( empPaymentDTO, paymentHeaderVO );
               }

               // 装载PaymentAdjustmentDTO（当月已经批准、发放的调整，合并计税使用）
               if ( empPaymentDTO.getPaymentDTOs() == null || empPaymentDTO.getPaymentDTOs().size() == 0 )
               {
                  final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
                  paymentAdjustmentHeaderVO.setAccountId( tempAdjustmentHeaderVO.getAccountId() );
                  paymentAdjustmentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

                  // 如果法务实体需要独立报税
                  if ( independenceTax )
                  {
                     paymentAdjustmentHeaderVO.setEntityId( tempAdjustmentHeaderVO.getEntityId() );
                  }

                  paymentAdjustmentHeaderVO.setEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() );
                  paymentAdjustmentHeaderVO.setMonthly( tempAdjustmentHeaderVO.getMonthly() );
                  paymentAdjustmentHeaderVO.setStatus( "3, 5" );
                  fetchPaymentAdjustment( empPaymentDTO, paymentAdjustmentHeaderVO );
               }

               // 装载AdjustmentDTO列表（未计算薪酬的数据）
               fetchAdjustment( empPaymentDTO, tempAdjustmentHeaderVO );
            }
         }
      }

      return empPaymentDTOs;
   }

   // 装载PaymentDTO列表
   // Added by Kevin Jin at 2013-12-04
   private void fetchPayment( final EMPPaymentDTO empPaymentDTO, final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      // 获取已提交或已发放的PaymentHeaderVO
      paymentHeaderVO.setStatus( "2, 3" );
      final List< Object > paymentHeaderVOs = getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            // 初始化PaymentDTO
            final PaymentDTO paymentDTO = new PaymentDTO();

            // 转换PaymentHeaderVO
            final PaymentHeaderVO tempPaymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

            // 装载PaymentHeaderVO
            paymentDTO.setPaymentHeaderVO( tempPaymentHeaderVO );

            // 初始化PaymentDetailVO列表
            final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( tempPaymentHeaderVO.getPaymentHeaderId() );

            if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
            {
               for ( Object paymentDetailVOObject : paymentDetailVOs )
               {
                  paymentDTO.getPaymentDetailVOs().add( ( PaymentDetailVO ) paymentDetailVOObject );
               }
            }

            empPaymentDTO.addPaymentDTO( paymentDTO );
         }
      }
   }

   // 装载PaymentAdjustmentDTO列表
   // Added by Kevin Jin at 2013-12-04
   private void fetchPaymentAdjustment( final EMPPaymentDTO empPaymentDTO, final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      final List< Object > paymentAdjustmentHeaderVOs = getPaymentAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOsByCondition( paymentAdjustmentHeaderVO );

      if ( paymentAdjustmentHeaderVOs != null && paymentAdjustmentHeaderVOs.size() > 0 )
      {
         for ( Object paymentAdjustmentHeaderVOObject : paymentAdjustmentHeaderVOs )
         {
            // 初始化PaymentAdjustmentDTO
            final PaymentAdjustmentDTO paymentAdjustmentDTO = new PaymentAdjustmentDTO();

            // 转换PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO tempPaymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) paymentAdjustmentHeaderVOObject;

            // 装载PaymentAdjustmentHeaderVO
            paymentAdjustmentDTO.setPaymentAdjustmentHeaderVO( tempPaymentAdjustmentHeaderVO );

            // 初始化PaymentAdjustmentDetailVO列表
            final List< Object > paymentAdjustmentDetailVOs = this.getPaymentAdjustmentDetailDao().getPaymentAdjustmentDetailVOsByAdjustmentHeaderId( tempPaymentAdjustmentHeaderVO.getAdjustmentHeaderId() );

            if ( paymentAdjustmentDetailVOs != null && paymentAdjustmentDetailVOs.size() > 0 )
            {
               for ( Object paymentAdjustmentDetailVOObject : paymentAdjustmentDetailVOs )
               {
                  paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().add( ( PaymentAdjustmentDetailVO ) paymentAdjustmentDetailVOObject );
               }
            }

            empPaymentDTO.addPaymentAdjustmentDTO( paymentAdjustmentDTO );
         }
      }
   }

   // 装载SettlementDTO列表
   // Added by Kevin Jin at 2013-12-04
   private void fetchSettlement( final EMPPaymentDTO empPaymentDTO, final ServiceContractVO serviceContractVO ) throws KANException
   {
      final SettlementDTO settlementDTO = new SettlementDTO();

      // 装载ClientOrderHeaderVO
      if ( KANUtil.filterEmpty( serviceContractVO.getOrderId() ) != null )
      {
         settlementDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( serviceContractVO.getOrderId() ) );
      }

      // 装载EmployeeContactVO
      if ( KANUtil.filterEmpty( serviceContractVO.getEmployeeContractId() ) != null )
      {
         settlementDTO.setEmployeeContractVO( this.getEmployeeContractDao().getEmployeeContractVOByContractId( serviceContractVO.getEmployeeContractId() ) );
      }

      // 装载TimesheetHeaderVO
      if ( KANUtil.filterEmpty( serviceContractVO.getTimesheetId() ) != null )
      {
         settlementDTO.setTimesheetHeaderVO( getTimesheetHeaderDao().getTimesheetHeaderVOByHeaderId( serviceContractVO.getTimesheetId() ) );
      }

      // 装载ServiceContractVO
      settlementDTO.setServiceContractVO( serviceContractVO );

      // 装载OrderDetailVO列表
      final List< Object > orderDetailVOs = getOrderDetailDao().getOrderDetailVOsByContractId( serviceContractVO.getContractId() );

      if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
      {
         for ( Object orderDetailVOObject : orderDetailVOs )
         {
            settlementDTO.getOrderDetailVOs().add( ( OrderDetailVO ) orderDetailVOObject );
         }
      }

      empPaymentDTO.getSettlementDTOs().add( settlementDTO );
   }

   // 装载AdjustmentDTO列表
   // Added by Kevin Jin at 2014-01-16
   private void fetchAdjustment( final EMPPaymentDTO empPaymentDTO, final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      final AdjustmentDTO adjustmentDTO = new AdjustmentDTO();

      // 装载AdjustmentHeaderVO
      adjustmentDTO.setAdjustmentHeaderVO( adjustmentHeaderVO );

      // 装载ClientOrderHeaderVO
      if ( KANUtil.filterEmpty( adjustmentHeaderVO.getOrderId() ) != null )
      {
         adjustmentDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( adjustmentHeaderVO.getOrderId() ) );
      }

      // 装载EmployeeContactVO
      if ( KANUtil.filterEmpty( adjustmentHeaderVO.getContractId() ) != null )
      {
         adjustmentDTO.setEmployeeContractVO( this.getEmployeeContractDao().getEmployeeContractVOByContractId( adjustmentHeaderVO.getContractId() ) );
      }

      // 装载AdjustmentDetailVO列表
      final List< Object > adjustmentDetailVOs = getAdjustmentDetailDao().getAdjustmentDetailVOsByAdjustmentHeaderId( adjustmentHeaderVO.getAdjustmentHeaderId() );

      if ( adjustmentDetailVOs != null && adjustmentDetailVOs.size() > 0 )
      {
         for ( Object adjustmentDetailVOObject : adjustmentDetailVOs )
         {
            adjustmentDTO.getAdjustmentDetailVOs().add( ( AdjustmentDetailVO ) adjustmentDetailVOObject );
         }
      }

      empPaymentDTO.getAdjustmentDTOs().add( adjustmentDTO );
   }

   // 获取EMPPaymentDTO，如果不存在则新建
   // Added by Kevin Jin at 2013-12-04
   private EMPPaymentDTO getEMPPaymentDTO( final List< EMPPaymentDTO > empPaymentDTOs, final String employeeId, final String entityId )
   {
      for ( EMPPaymentDTO empPaymentDTO : empPaymentDTOs )
      {
         if ( empPaymentDTO.getEmployeeVO() != null
               && KANUtil.filterEmpty( empPaymentDTO.getEmployeeVO().getEmployeeId() ) != null
               && empPaymentDTO.getEmployeeVO().getEmployeeId().equals( employeeId )
               && ( KANUtil.filterEmpty( entityId ) == null || ( KANUtil.filterEmpty( empPaymentDTO.getEntityId() ) != null && KANUtil.filterEmpty( empPaymentDTO.getEntityId() ).equals( entityId ) ) ) )
         {
            return empPaymentDTO;
         }
      }

      // 初始化EMPPaymentDTO
      final EMPPaymentDTO empPaymentDTO = new EMPPaymentDTO();

      // 需要独立报税的情况
      if ( KANUtil.filterEmpty( entityId ) != null )
      {
         empPaymentDTO.setEntityId( entityId );
      }

      empPaymentDTOs.add( empPaymentDTO );

      return empPaymentDTO;
   }
}

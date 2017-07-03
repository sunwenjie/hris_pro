package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.message.MessageMailDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.cb.CBBatchDao;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.dao.inf.biz.cb.CBHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.payment.PaymentBatchDao;
import com.kan.hro.dao.inf.biz.payment.PaymentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.BatchDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.EMPPaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.payment.PaymentBatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class PaymentBatchServiceImpl extends ContextService implements PaymentBatchService
{
   // 注入PaymentHeaderDao
   private PaymentHeaderDao paymentHeaderDao;

   // 注入PaymentDetailDao
   private PaymentDetailDao paymentDetailDao;

   // 注入BatchDao
   private BatchDao batchDao;

   // 注入ServiceContractDao
   private ServiceContractDao serviceContractDao;

   // 注入OrderHeaderDao
   private OrderHeaderDao orderHeaderDao;

   // 注入OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // 注入ClientOrderHeaderService
   private ClientOrderHeaderService clientOrderHeaderService;

   // 注入BatchTempService
   private BatchTempService batchTempService;

   // 注入ClientOrderHeaderService
   private ServiceContractService serviceContractService;

   // 注入TimesheetBatchDao
   private TimesheetBatchDao timesheetBatchDao;

   // 注入TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // 注入SBBatchDao
   private SBBatchDao sbBatchDao;

   // 注入SBHeaderDao
   private SBHeaderDao sbHeaderDao;

   // 注入SBDetailDao
   private SBDetailDao sbDetailDao;

   // 注入CBBatchDao
   private CBBatchDao cbBatchDao;

   // 注入CBHeaderDao
   private CBHeaderDao cbHeaderDao;

   // 注入CBDetailDao
   private CBDetailDao cbDetailDao;

   // 注入SBAdjustmentHeaderDao
   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;

   // 注入SBAdjustmentDetailDao
   private SBAdjustmentDetailDao sbAdjustmentDetailDao;

   // 注入EmployeeDao
   private EmployeeDao employeeDao;

   // 注入MessageMailDao
   private MessageMailDao messageMailDao;

   // 注入SalaryHeaderDao
   private SalaryHeaderDao salaryHeaderDao;

   // 注入SalaryDetailDao
   private SalaryDetailDao salaryDetailDao;

   // 注入VendorDao
   private VendorDao vendorDao;

   public PaymentHeaderDao getPaymentHeaderDao()
   {
      return paymentHeaderDao;
   }

   public void setPaymentHeaderDao( PaymentHeaderDao paymentHeaderDao )
   {
      this.paymentHeaderDao = paymentHeaderDao;
   }

   public PaymentDetailDao getPaymentDetailDao()
   {
      return paymentDetailDao;
   }

   public void setPaymentDetailDao( PaymentDetailDao paymentDetailDao )
   {
      this.paymentDetailDao = paymentDetailDao;
   }

   public final ServiceContractDao getServiceContractDao()
   {
      return serviceContractDao;
   }

   public final void setServiceContractDao( ServiceContractDao serviceContractDao )
   {
      this.serviceContractDao = serviceContractDao;
   }

   public final ClientOrderHeaderService getClientOrderHeaderService()
   {
      return clientOrderHeaderService;
   }

   public final void setClientOrderHeaderService( ClientOrderHeaderService clientOrderHeaderService )
   {
      this.clientOrderHeaderService = clientOrderHeaderService;
   }

   public final BatchTempService getBatchTempService()
   {
      return batchTempService;
   }

   public final void setBatchTempService( BatchTempService batchTempService )
   {
      this.batchTempService = batchTempService;
   }

   public final ServiceContractService getServiceContractService()
   {
      return serviceContractService;
   }

   public final void setServiceContractService( ServiceContractService serviceContractService )
   {
      this.serviceContractService = serviceContractService;
   }

   public final BatchDao getBatchDao()
   {
      return batchDao;
   }

   public final void setBatchDao( BatchDao batchDao )
   {
      this.batchDao = batchDao;
   }

   public final OrderHeaderDao getOrderHeaderDao()
   {
      return orderHeaderDao;
   }

   public final void setOrderHeaderDao( OrderHeaderDao orderHeaderDao )
   {
      this.orderHeaderDao = orderHeaderDao;
   }

   public final OrderDetailDao getOrderDetailDao()
   {
      return orderDetailDao;
   }

   public final void setOrderDetailDao( OrderDetailDao orderDetailDao )
   {
      this.orderDetailDao = orderDetailDao;
   }

   public final TimesheetBatchDao getTimesheetBatchDao()
   {
      return timesheetBatchDao;
   }

   public final void setTimesheetBatchDao( TimesheetBatchDao timesheetBatchDao )
   {
      this.timesheetBatchDao = timesheetBatchDao;
   }

   public final TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public final void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
   }

   public final SBBatchDao getSbBatchDao()
   {
      return sbBatchDao;
   }

   public final void setSbBatchDao( SBBatchDao sbBatchDao )
   {
      this.sbBatchDao = sbBatchDao;
   }

   public final SBHeaderDao getSbHeaderDao()
   {
      return sbHeaderDao;
   }

   public final void setSbHeaderDao( SBHeaderDao sbHeaderDao )
   {
      this.sbHeaderDao = sbHeaderDao;
   }

   public final SBDetailDao getSbDetailDao()
   {
      return sbDetailDao;
   }

   public final void setSbDetailDao( SBDetailDao sbDetailDao )
   {
      this.sbDetailDao = sbDetailDao;
   }

   public final CBBatchDao getCbBatchDao()
   {
      return cbBatchDao;
   }

   public final void setCbBatchDao( CBBatchDao cbBatchDao )
   {
      this.cbBatchDao = cbBatchDao;
   }

   public final CBHeaderDao getCbHeaderDao()
   {
      return cbHeaderDao;
   }

   public final void setCbHeaderDao( CBHeaderDao cbHeaderDao )
   {
      this.cbHeaderDao = cbHeaderDao;
   }

   public final CBDetailDao getCbDetailDao()
   {
      return cbDetailDao;
   }

   public final void setCbDetailDao( CBDetailDao cbDetailDao )
   {
      this.cbDetailDao = cbDetailDao;
   }

   public final SBAdjustmentHeaderDao getSbAdjustmentHeaderDao()
   {
      return sbAdjustmentHeaderDao;
   }

   public final void setSbAdjustmentHeaderDao( SBAdjustmentHeaderDao sbAdjustmentHeaderDao )
   {
      this.sbAdjustmentHeaderDao = sbAdjustmentHeaderDao;
   }

   public final SBAdjustmentDetailDao getSbAdjustmentDetailDao()
   {
      return sbAdjustmentDetailDao;
   }

   public final void setSbAdjustmentDetailDao( SBAdjustmentDetailDao sbAdjustmentDetailDao )
   {
      this.sbAdjustmentDetailDao = sbAdjustmentDetailDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public MessageMailDao getMessageMailDao()
   {
      return messageMailDao;
   }

   public void setMessageMailDao( MessageMailDao messageMailDao )
   {
      this.messageMailDao = messageMailDao;
   }

   public final SalaryHeaderDao getSalaryHeaderDao()
   {
      return salaryHeaderDao;
   }

   public final void setSalaryHeaderDao( SalaryHeaderDao salaryHeaderDao )
   {
      this.salaryHeaderDao = salaryHeaderDao;
   }

   public final SalaryDetailDao getSalaryDetailDao()
   {
      return salaryDetailDao;
   }

   public final void setSalaryDetailDao( SalaryDetailDao salaryDetailDao )
   {
      this.salaryDetailDao = salaryDetailDao;
   }

   public final VendorDao getVendorDao()
   {
      return vendorDao;
   }

   public final void setVendorDao( VendorDao vendorDao )
   {
      this.vendorDao = vendorDao;
   }

   @Override
   public PagedListHolder getPaymentBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PaymentBatchDao paymentBatchDao = ( PaymentBatchDao ) getDao();
      pagedListHolder.setHolderSize( paymentBatchDao.countPaymentBatchVOsByCondition( ( PaymentBatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( paymentBatchDao.getPaymentBatchVOsByCondition( ( PaymentBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentBatchDao.getPaymentBatchVOsByCondition( ( PaymentBatchVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         final PaymentBatchVO holderBatchVO = ( PaymentBatchVO ) pagedListHolder.getObject();
         for ( Object paymentBatchVOObject : pagedListHolder.getSource() )
         {
            final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) paymentBatchVOObject;
            paymentBatchVO.setAccountId( holderBatchVO.getAccountId() );
            paymentBatchVO.setCorpId( holderBatchVO.getCorpId() );
            paymentBatchVO.setRulePublic( holderBatchVO.getRulePublic() );
            paymentBatchVO.setRulePrivateIds( holderBatchVO.getRulePrivateIds() );
            paymentBatchVO.setRulePositionIds( holderBatchVO.getRulePositionIds() );
            paymentBatchVO.setRuleBranchIds( holderBatchVO.getRuleBranchIds() );
            paymentBatchVO.setRuleBusinessTypeIds( holderBatchVO.getRuleBusinessTypeIds() );
            paymentBatchVO.setRuleEntityIds( holderBatchVO.getRuleEntityIds() );
            fetchPaymentBatchVO( paymentBatchVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * FetchPaymentBatchVO
    * 计算公司及个人数值合计(指定批次)
    * @param pagedListHolder
    * @throws KANException
    */
   private void fetchPaymentBatchVO( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      final PaymentHeaderVO condPaymentHeaderVO = new PaymentHeaderVO();
      condPaymentHeaderVO.setBatchId( paymentBatchVO.getBatchId() );
      condPaymentHeaderVO.setStatus( paymentBatchVO.getStatus() );
      condPaymentHeaderVO.setCorpId( paymentBatchVO.getCorpId() );
      condPaymentHeaderVO.setAccountId( paymentBatchVO.getAccountId() );
      condPaymentHeaderVO.setRulePublic( paymentBatchVO.getRulePublic() );
      condPaymentHeaderVO.setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
      condPaymentHeaderVO.setRulePositionIds( paymentBatchVO.getRulePositionIds() );
      condPaymentHeaderVO.setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
      condPaymentHeaderVO.setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
      condPaymentHeaderVO.setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );
      final List< Object > paymentHeaderVOs = this.paymentHeaderDao.getPaymentHeaderVOsByCondition( condPaymentHeaderVO );
      //final List< Object > paymentDetailVOs = this.paymentDetailDao.getPaymentDetailVOsByBatchVO( paymentBatchVO );
      final List< Object > paymentDetailVOs = new ArrayList< Object >();
      // 初始化包含的雇员集合
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderObject : paymentHeaderVOs )
         {
            // 通过header判断detail 权限由header控制
            final PaymentDetailVO paymentDetailVO = new PaymentDetailVO();
            paymentDetailVO.setPaymentHeaderId( ( ( PaymentHeaderVO ) paymentHeaderObject ).getPaymentHeaderId() );
            paymentDetailVO.setStatus( paymentBatchVO.getStatus() );
            paymentDetailVO.setRulePublic( paymentBatchVO.getRulePublic() );
            paymentDetailVO.setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
            paymentDetailVO.setRulePositionIds( paymentBatchVO.getRulePositionIds() );
            paymentDetailVO.setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
            paymentDetailVO.setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
            paymentDetailVO.setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );
            paymentDetailVOs.addAll( this.paymentDetailDao.getPaymentDetailVOsByCondition( paymentDetailVO ) );
         }
      }

      // 计算营收成本合计
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {

         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;

            if ( paymentBatchVO.getStatus().equals( paymentDetailVO.getStatus() ) )
            {
               paymentBatchVO.addBillAmountCompany( paymentDetailVO.getBillAmountCompany() );
               paymentBatchVO.addBillAmountPersonal( paymentDetailVO.getBillAmountPersonal() );
               paymentBatchVO.addCostAmountCompany( paymentDetailVO.getCostAmountCompany() );
               paymentBatchVO.addCostAmountPersonal( paymentDetailVO.getCostAmountPersonal() );
            }
         }
      }

      //优化后的写法 
      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

            if ( paymentBatchVO.getStatus().equals( paymentHeaderVO.getStatus() ) )
            {
               // 组装Employee名称 
               if ( !isEmployeeVOExist( paymentHeaderVO, employeeMappingVOs ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( paymentHeaderVO.getEmployeeId() );
                  mappingVO.setMappingValue( paymentHeaderVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( paymentHeaderVO.getEmployeeNameEN() );
                  employeeMappingVOs.add( mappingVO );
               }

               // 计算个税
               paymentBatchVO.addTaxAmountPersonal( paymentHeaderVO.getTaxAmountPersonal() );
               // 个人支出添加个税数值
               paymentBatchVO.addCostAmountPersonal( paymentHeaderVO.getTaxAmountPersonal() );
            }

         }
      }
      paymentBatchVO.setEmployees( employeeMappingVOs );
   }

   /**  
    * IsEmployeeVOExist
    * 判断雇员是否存在
    * @param tempPaymentHeaderVO
    * @param employeeMappingVOs
    * @return
    */
   private boolean isEmployeeVOExist( final PaymentHeaderVO PaymentHeaderVO, final List< MappingVO > employeeMappingVOs )
   {
      if ( employeeMappingVOs != null && employeeMappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : employeeMappingVOs )
         {
            if ( mappingVO.getMappingId().equals( PaymentHeaderVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   /**  
    * CcountAmount
    * 计算公司及个人数值合计(指定PaymentHeaderVO)
    * @param paymentHeaderVO
    * @throws KANException
    */
   private void countAmount( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      // 清空PaymentHeaderVO数据
      paymentHeaderVO.setBillAmountCompany( "0" );
      paymentHeaderVO.setBillAmountPersonal( "0" );
      paymentHeaderVO.setCostAmountCompany( "0" );
      paymentHeaderVO.setCostAmountPersonal( "0" );

      final List< Object > paymentDetailVOs = this.paymentDetailDao.getPaymentDetailVOsByHeaderId( paymentHeaderVO.getPaymentHeaderId() );

      // 计算营收成本合计
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;

            paymentHeaderVO.addBillAmountCompany( paymentDetailVO.getBillAmountCompany() );
            paymentHeaderVO.addBillAmountPersonal( paymentDetailVO.getBillAmountPersonal() );
            paymentHeaderVO.addCostAmountCompany( paymentDetailVO.getCostAmountCompany() );
            paymentHeaderVO.addCostAmountPersonal( paymentDetailVO.getCostAmountPersonal() );
         }
      }

   }

   @Override
   public PaymentBatchVO getPaymentBatchVOByBatchId( final String batchId ) throws KANException
   {
      final PaymentBatchVO paymentBatchVO = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( batchId );

      // 计算合计
      if ( paymentBatchVO != null )
      {
         fetchPaymentBatchVO( paymentBatchVO );
      }

      return paymentBatchVO;
   }

   @Override
   public int updatePaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return ( ( PaymentBatchDao ) getDao() ).updatePaymentBatch( paymentBatchVO );
   }

   @Override
   public int insertPaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return ( ( PaymentBatchDao ) getDao() ).insertPaymentBatch( paymentBatchVO );
   }

   @Override
   public List< Object > getPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      final List< Object > paymentBatchVOs = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOsByCondition( paymentBatchVO );

      // 计算合计
      if ( paymentBatchVOs != null && paymentBatchVOs.size() > 0 )
      {
         for ( Object paymentBatchVOObject : paymentBatchVOs )
         {
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRulePublic( paymentBatchVO.getRulePublic() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRulePositionIds( paymentBatchVO.getRulePositionIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );
            fetchPaymentBatchVO( ( PaymentBatchVO ) paymentBatchVOObject );
         }
      }

      return paymentBatchVOs;
   }

   @Override
   public int rollback( final PaymentBatchVO paymentBatchVO, final String role ) throws KANException
   {
      int rows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 初始化选中项ID
         final String selectedIds = paymentBatchVO.getSelectedIds();
         // 初始化PageFlag
         final String pageFlag = paymentBatchVO.getPageFlag() != null ? paymentBatchVO.getPageFlag().trim() : "";

         // 如果有选择项
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // 获得所选ID Array
            final String[] selectedIdArray = selectedIds.split( "," );

            rows = selectedIdArray.length;

            // 遍历
            for ( String encodedSelectId : selectedIdArray )
            {

               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // 按pageFlag退回
               if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_BATCH ) )
               {
                  rollbackBatch( selectId, role );
               }
               else if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_HEADER ) )
               {
                  rollbackHeader( selectId, role );
               }
            }

            // 尝试退回父对象
            tryRollbackBatch( paymentBatchVO.getBatchId() );

         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   /**  
    * Rollback Batch
    *	按批次退回
    *
    *	@param paymentBatchId
    *	@throws KANException
    */
   private void rollbackBatch( final String paymentBatchId, final String role ) throws KANException
   {
      // 初始化PaymentHeaderVO列表
      final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByBatchId( paymentBatchId );

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         // 遍历
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            // 删除PaymentHeaderVO
            rollbackHeader( ( ( PaymentHeaderVO ) paymentHeaderVOObject ).getPaymentHeaderId(), role );
         }
      }

      // 退回PaymentBatchVO
      ( ( PaymentBatchDao ) getDao() ).deletePaymentBatch( paymentBatchId );
   }

   /**  
    * Rollback Header
    *	按服务协议退回
    *
    *	@param paymentHeaderId
    *	@throws KANException
    */
   private void rollbackHeader( final String paymentHeaderId, final String role ) throws KANException
   {
      // 初始化PaymentDetailVO列表
      final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( paymentHeaderId );

      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         // 遍历
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            // 删除PaymentDetailVO
            rollbackDetail( ( ( PaymentDetailVO ) paymentDetailVOObject ).getPaymentDetailId() );
         }
      }

      /** In House - 退回结算信息 - 开始 */
      if ( KANUtil.filterEmpty( role ) != null && role.equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // 初始化PaymentHeaderVO
         final PaymentHeaderVO paymentHeaderVO = this.getPaymentHeaderDao().getPaymentHeaderVOByHeaderId( paymentHeaderId );

         // 初始化OrderDetailVO列表
         final List< Object > orderDetailVOs = this.getOrderDetailDao().getOrderDetailVOsByContractId( paymentHeaderVO.getOrderContractId() );

         if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
         {
            for ( Object orderDetailVOObject : orderDetailVOs )
            {
               // 初始化OrderDetailVO
               final OrderDetailVO orderDetailVO = ( ( OrderDetailVO ) orderDetailVOObject );

               /** 社保，社保调整，商保退回处理 - 开始  */
               if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ) != null )
               {
                  // 社保退回至提交待结算状态
                  if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "1" ) )
                  {
                     final SBDetailVO sbDetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( orderDetailVO.getDetailId() );
                     sbDetailVO.setStatus( "4" );
                     this.getSbDetailDao().updateSBDetail( sbDetailVO );

                     final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( sbDetailVO.getHeaderId() );

                     if ( sbHeaderVO != null && KANUtil.filterEmpty( sbHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( sbHeaderVO.getStatus() ).equals( "5" ) )
                     {
                        sbHeaderVO.setStatus( "4" );
                        this.getSbHeaderDao().updateSBHeader( sbHeaderVO );

                        final SBBatchVO sbBatchVO = this.getSbBatchDao().getSBBatchVOByBatchId( sbHeaderVO.getBatchId() );

                        if ( sbBatchVO != null && KANUtil.filterEmpty( sbBatchVO.getStatus() ) != null && KANUtil.filterEmpty( sbBatchVO.getStatus() ).equals( "5" ) )
                        {
                           sbBatchVO.setStatus( "4" );
                           this.getSbBatchDao().updateSBBatch( sbBatchVO );
                        }
                     }
                  }
                  // 商保退回至提交待结算状态
                  else if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "2" ) )
                  {
                     final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( orderDetailVO.getDetailId() );
                     cbDetailVO.setStatus( "4" );
                     this.getCbDetailDao().updateCBDetail( cbDetailVO );

                     final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( cbDetailVO.getHeaderId() );

                     if ( cbHeaderVO != null && KANUtil.filterEmpty( cbHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( cbHeaderVO.getStatus() ).equals( "5" ) )
                     {
                        cbHeaderVO.setStatus( "4" );
                        this.getCbHeaderDao().updateCBHeader( cbHeaderVO );

                        final CBBatchVO cbBatchVO = this.getCbBatchDao().getCBBatchVOByBatchId( cbHeaderVO.getBatchId() );

                        if ( cbBatchVO != null && KANUtil.filterEmpty( cbBatchVO.getStatus() ) != null && KANUtil.filterEmpty( cbBatchVO.getStatus() ).equals( "5" ) )
                        {
                           cbBatchVO.setStatus( "4" );
                           this.getCbBatchDao().updateCBBatch( cbBatchVO );
                        }
                     }
                  }
                  // 社保调整退回至提交待结算状态
                  else if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "3" ) )
                  {
                     // 获得SBAdjustmentDetailVO
                     final SBAdjustmentDetailVO sbAdjustmentDetailVO = this.getSbAdjustmentDetailDao().getSBAdjustmentDetailVOByAdjustmentDetailId( orderDetailVO.getDetailId() );

                     // 获得SBAdjustmentHeaderVO
                     final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = this.getSbAdjustmentHeaderDao().getSBAdjustmentHeaderVOByAdjustmentHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );
                     sbAdjustmentHeaderVO.setStatus( "3" );
                     this.getSbAdjustmentHeaderDao().updateSBAdjustmentHeader( sbAdjustmentHeaderVO );
                  }
                  // 工资导入退回至提交状态
                  else if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "4" ) || KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "5" ) )
                  {
                     final List< String > salaryHeaderIds = KANUtil.jasonArrayToStringList( orderDetailVO.getHeaderId() );
                     final String salaryDetailId = orderDetailVO.getDetailId();

                     // 关联主表ID不为空的情况
                     if ( salaryHeaderIds != null && salaryHeaderIds.size() > 0 )
                     {
                        for ( String salaryHeaderId : salaryHeaderIds )
                        {
                           final SalaryHeaderVO salaryHeaderVO = this.getSalaryHeaderDao().getSalaryHeaderVOByHeaderId( salaryHeaderId );

                           if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( salaryHeaderVO.getStatus() ).equals( "3" ) )
                           {
                              salaryHeaderVO.setStatus( "2" );
                              salaryHeaderVO.setModifyDate( new Date() );
                              this.getSalaryHeaderDao().updateSalaryHeader( salaryHeaderVO );

                              final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByHeaderId( salaryHeaderVO.getSalaryHeaderId() );

                              if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
                              {
                                 for ( Object salaryDetaillVOObject : salaryDetailVOs )
                                 {
                                    final SalaryDetailVO tempSalaryDetailVO = ( SalaryDetailVO ) salaryDetaillVOObject;
                                    tempSalaryDetailVO.setStatus( "2" );
                                    tempSalaryDetailVO.setModifyDate( new Date() );
                                    this.getSalaryDetailDao().updateSalaryDetail( tempSalaryDetailVO );
                                 }
                              }
                           }
                        }
                     }

                     // 关联从表ID不为空的情况
                     if ( KANUtil.filterEmpty( salaryDetailId ) != null )
                     {
                        // 从表数据修改
                        final SalaryDetailVO salaryDetailVO = this.getSalaryDetailDao().getSalaryDetailVOByDetailId( salaryDetailId );
                        salaryDetailVO.setStatus( "2" );
                        salaryDetailVO.setModifyDate( new Date() );
                        this.getSalaryDetailDao().updateSalaryDetail( salaryDetailVO );

                        // 标记判断Salary Detail是否已全部退回
                        boolean submited = true;
                        final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByHeaderId( salaryDetailVO.getSalaryHeaderId() );

                        if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
                        {
                           for ( Object salaryDetaillVOObject : salaryDetailVOs )
                           {
                              final SalaryDetailVO tempSalaryDetailVO = ( SalaryDetailVO ) salaryDetaillVOObject;

                              if ( KANUtil.filterEmpty( tempSalaryDetailVO.getStatus() ) != null && !KANUtil.filterEmpty( tempSalaryDetailVO.getStatus() ).equals( "2" ) )
                              {
                                 submited = false;
                              }
                           }
                        }

                        if ( submited )
                        {
                           final SalaryHeaderVO salaryHeaderVO = this.getSalaryHeaderDao().getSalaryHeaderVOByHeaderId( salaryDetailVO.getSalaryHeaderId() );

                           if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( salaryHeaderVO.getStatus() ).equals( "3" ) )
                           {
                              salaryHeaderVO.setStatus( "2" );
                              salaryHeaderVO.setModifyDate( new Date() );
                              this.getSalaryHeaderDao().updateSalaryHeader( salaryHeaderVO );
                           }
                        }
                     }
                  }
               }
               /** 社保，社保调整，商保退回处理 - 结束  */

               // 物理删除OrderDetailVO列表
               this.getOrderDetailDao().deleteOrderDetail( orderDetailVO.getOrderDetailId() );
            }
         }

         /** 考勤表退回处理 - 开始  */
         final ServiceContractVO serviceContractVO = this.getServiceContractDao().getServiceContractVOByContractId( paymentHeaderVO.getOrderContractId() );

         // 获得OrderHeaderId
         final String orderHeaderId = serviceContractVO.getOrderHeaderId();

         if ( serviceContractVO != null && KANUtil.filterEmpty( serviceContractVO.getTimesheetId() ) != null )
         {
            // 初始化TimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = this.getTimesheetHeaderDao().getTimesheetHeaderVOByHeaderId( serviceContractVO.getTimesheetId() );
            // TimesheetHeaderVO退回至批准状态
            timesheetHeaderVO.setStatus( "3" );
            // 更改TimesheetHeaderVO
            this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetHeaderVO );

            // 获得TimesheetBatchVO
            final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );

            if ( timesheetBatchVO != null && KANUtil.filterEmpty( timesheetBatchVO.getStatus() ) != null && KANUtil.filterEmpty( timesheetBatchVO.getStatus() ).equals( "5" ) )
            {
               // TimesheetBatchVO退回至批准状态
               timesheetBatchVO.setStatus( "3" );
               // 退回考勤批次
               this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
            }
         }
         /** 考勤表退回处理 - 结束 */

         // 物理删除结算Contract信息
         this.getServiceContractDao().deleteServiceContract( paymentHeaderVO.getOrderContractId() );

         // 初始化ServiceContractVO列表
         final List< Object > serviceContractVOs = this.getServiceContractDao().getServiceContractVOsByOrderHeaderId( orderHeaderId );

         // 如果PaymentHeaderVO对应OrderHeaderVO已经没有下级ServiceContractVO数据，删除OrderHeaderVO
         if ( serviceContractVOs == null || serviceContractVOs.size() == 0 )
         {
            // 初始化OrderHeaderVO
            final OrderHeaderVO orderHeaderVO = this.getOrderHeaderDao().getOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // 获取结算批次
            final String batchId = orderHeaderVO.getBatchId();

            // 删除OrderHeaderVO
            this.getOrderHeaderDao().deleteOrderHeader( orderHeaderId );

            // 初始化OrderHeaderVO列表
            final List< Object > orderHeaderVOs = this.getOrderHeaderDao().getOrderHeaderVOsByBatchId( batchId );

            // 如果OrderHeaderVO对应BatchVO已经没有下级OrderHeaderVO数据，删除BatchVO
            if ( orderHeaderVOs == null || orderHeaderVOs.size() == 0 )
            {
               // 删除BatchVO
               this.getBatchDao().deleteBatch( batchId );
            }
         }
      }
      /** In House - 退回结算信息 - 结束 */

      // 退回PaymentHeaderVO
      this.getPaymentHeaderDao().deletePaymentHeader( paymentHeaderId );
   }

   /**  
    * Rollback Detail
    *	按科目退回
    *
    *	@param paymentDetailId
    *	@param status
    *	@throws KANException
    */
   private void rollbackDetail( final String paymentDetailId ) throws KANException
   {
      final PaymentDetailVO paymentDetailVO = this.getPaymentDetailDao().getPaymentDetailVOByDetailId( paymentDetailId );

      if ( paymentDetailVO != null && paymentDetailVO.getStatus() != null && paymentDetailVO.getStatus().equals( "1" ) )
      {
         // 删除PaymentDetailVO
         this.getPaymentDetailDao().deletePaymentDetail( paymentDetailId );
      }
   }

   @Override
   public int submit( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      int returnBatchFlag = -1;
      try
      {
         // 开启事务
         startTransaction();

         // 初始化勾选的ID数组
         final String selectedIds = paymentBatchVO.getSelectedIds();
         // 初始化PageFlag
         final String pageFlag = paymentBatchVO.getPageFlag() != null ? paymentBatchVO.getPageFlag().trim() : "";
         // 初始化SubAction
         final String subAction = paymentBatchVO.getSubAction() != null ? paymentBatchVO.getSubAction().trim() : "";

         // 如果有选择项
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // 获得所选ID Array
            final String[] selectedIdArray = selectedIds.split( "," );
            // 初始化状态字符串
            String status = "0";

            // 如果是提交
            if ( subAction.equalsIgnoreCase( BaseAction.SUBMIT_OBJECTS ) )
            {
               status = "2";
            }
            // 如果是发放
            else if ( subAction.equalsIgnoreCase( BaseAction.ISSUE_OBJECTS ) )
            {
               status = "3";
            }

            // 遍历
            for ( String encodedSelectId : selectedIdArray )
            {
               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_BATCH ) )
               {
                  paymentBatchVO.setBatchId( KANUtil.encodeStringWithCryptogram( selectId ) );
                  submitBatch( selectId, status, paymentBatchVO.getModifyBy(), paymentBatchVO );
               }
               else if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_HEADER ) )
               {
                  submitHeader( selectId, status, paymentBatchVO.getModifyBy() );
               }

               // 尝试更改其父对象状态
               returnBatchFlag = trySubmitBatch( paymentBatchVO.getBatchId(), status, paymentBatchVO.getModifyBy() );
            }
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
      return returnBatchFlag;
   }

   /**  
    * Submit Batch
    *	提交批次
    *
    *	@param batchId
    *	@param status
    *	@param userId
    *	@throws KANException
    */
   private void submitBatch( final String batchId, final String status, final String userId, final PaymentBatchVO condPaymentBatchVO ) throws KANException
   {
      // 初始化PaymentHeaderVO List
      final PaymentHeaderVO tempHeaderVO = new PaymentHeaderVO();
      tempHeaderVO.setBatchId( batchId );
      //      tempHeaderVO.setNotIn( condPaymentBatchVO.getNotIn() );
      //      tempHeaderVO.setHasIn( condPaymentBatchVO.getHasIn() );
      tempHeaderVO.setAccountId( condPaymentBatchVO.getAccountId() );
      tempHeaderVO.setRulePublic( condPaymentBatchVO.getRulePublic() );
      tempHeaderVO.setRulePrivateIds( condPaymentBatchVO.getRulePrivateIds() );
      tempHeaderVO.setRulePositionIds( condPaymentBatchVO.getRulePositionIds() );
      tempHeaderVO.setRuleBranchIds( condPaymentBatchVO.getRuleBranchIds() );
      tempHeaderVO.setRuleBusinessTypeIds( condPaymentBatchVO.getRuleBusinessTypeIds() );
      tempHeaderVO.setRuleEntityIds( condPaymentBatchVO.getRuleEntityIds() );
      tempHeaderVO.setCorpId( condPaymentBatchVO.getCorpId() );
      final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByCondition( tempHeaderVO );

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         // 遍历
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;
            submitHeader( paymentHeaderVO.getPaymentHeaderId(), status, userId );
         }
      }

      // 更改该批次
      final PaymentBatchVO paymentBatchVO = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( batchId );
      paymentBatchVO.setStatus( status );
      paymentBatchVO.setModifyBy( userId );
      paymentBatchVO.setModifyDate( new Date() );
      ( ( PaymentBatchDao ) getDao() ).updatePaymentBatch( paymentBatchVO );

   }

   /**  
    * Submit Header
    *	提交服务协议
    *
    *	@param headerId
    *	@param status
    *	@param userId
    *	@throws KANException
    */
   private void submitHeader( final String headerId, final String status, final String userId ) throws KANException
   {
      // 初始化PaymentDetailVO List
      final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( headerId );

      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         // 遍历
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;
            submitDetail( paymentDetailVO.getPaymentDetailId(), status, userId );
         }
      }

      // 提交服务协议
      final PaymentHeaderVO paymentHeaderVO = this.getPaymentHeaderDao().getPaymentHeaderVOByHeaderId( headerId );
      paymentHeaderVO.setStatus( status );
      paymentHeaderVO.setModifyBy( userId );
      paymentHeaderVO.setModifyDate( new Date() );
      this.getPaymentHeaderDao().updatePaymentHeader( paymentHeaderVO );

      // 将服务协议结算数据标记成已计算薪酬
      final ServiceContractVO serviceContractVO = this.getServiceContractDao().getServiceContractVOByContractId( paymentHeaderVO.getContractId() );
      if ( serviceContractVO != null )
      {
         serviceContractVO.setPaymentFlag( ServiceContractVO.TRUE );
         this.getServiceContractDao().updateServiceContract( serviceContractVO );
      }
      // noticeByEmail( paymentHeaderVO );
   }

   /**  
    * Submit Detail
    *	提交科目
    *
    *	@param detailId
    *	@param status
    *	@param userId
    *	@throws KANException
    */
   private void submitDetail( final String detailId, final String status, final String userId ) throws KANException
   {
      final PaymentDetailVO paymentDetailVO = this.getPaymentDetailDao().getPaymentDetailVOByDetailId( detailId );

      if ( paymentDetailVO != null && paymentDetailVO.getStatus() != null && paymentDetailVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) )
      {
         paymentDetailVO.setStatus( status );
         paymentDetailVO.setModifyBy( userId );
         paymentDetailVO.setModifyDate( new Date() );
         this.getPaymentDetailDao().updatePaymentDetail( paymentDetailVO );
      }
   }

   /**  
    * Try Submit Batch
    *	尝试提交批次 - 提交子对象但不清楚父对象是否提交的情况使用
    *
    *	@param batchId
    *	@param status
    *	@param userId
    *	@return
    *	@throws KANException
    */
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = -1;

      if ( status != null && !status.isEmpty() )
      {
         // 初始化PaymentBatchVO
         final PaymentBatchVO paymentBatchVO = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
         // 初始化PaymentHeaderVO列表
         final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

         if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;

            // 遍历
            for ( Object paymentHeaderVOObject : paymentHeaderVOs )
            {
               final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

               if ( paymentHeaderVO.getStatus() != null && !paymentHeaderVO.getStatus().isEmpty() && Integer.valueOf( paymentHeaderVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }

            }

            // 如果薪资方案已全部是需要更改的状态，修改批次的状态
            if ( headerFlag == paymentHeaderVOs.size() )
            {
               paymentBatchVO.setStatus( status );
               paymentBatchVO.setModifyBy( userId );
               paymentBatchVO.setModifyDate( new Date() );
               updatePaymentBatch( paymentBatchVO );
               submitRows = 0;
            }
         }
         else
         {
            submitRows = 0;
         }
      }

      return submitRows;
   }

   /**  
    * Try Rollback Batch
    *	尝试退回批次 - 提交子对象但不清楚父对象是否退回的情况使用
    *
    * @param batchId
    * @param rollbackBatchFlag
    *	@return
    *	@throws KANException
    */
   private int tryRollbackBatch( final String batchId ) throws KANException
   {
      // 初始化PaymentHeaderVO列表
      final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

      if ( paymentHeaderVOs == null || paymentHeaderVOs.size() == 0 )
      {
         return ( ( PaymentBatchDao ) getDao() ).deletePaymentBatch( KANUtil.decodeStringFromAjax( batchId ) );
      }

      return 0;
   }

   @Override
   // Added by Kevin Jin at 2013-12-04
   public int insertPaymentBatch( final PaymentBatchVO paymentBatchVO, final ServiceContractVO serviceContractVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // 开启事务
         startTransaction();

         rows = insertPaymentBatch_nt( paymentBatchVO, serviceContractVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Added by Kevin Jin at 2013-12-29
   public int insertPaymentBatch_nt( final PaymentBatchVO paymentBatchVO, final ServiceContractVO serviceContractVO ) throws KANException
   {
      int rows = 0;

      try
      {
         final List< EMPPaymentDTO > empPaymentDTOs = this.getServiceContractService().getEMPPaymentDTOsByCondition( serviceContractVO );

         // 遍历并逐个计算结算数据
         if ( empPaymentDTOs != null && empPaymentDTOs.size() > 0 )
         {
            for ( EMPPaymentDTO empPaymentDTO : empPaymentDTOs )
            {
               empPaymentDTO.calculatePayment( paymentBatchVO );
            }

            // 遍历
            for ( EMPPaymentDTO empPaymentDTO : empPaymentDTOs )
            {
               // 获得PaymentDTO List
               final List< PaymentDTO > paymentDTOs = empPaymentDTO.getPaymentDTOs();

               if ( paymentDTOs != null && paymentDTOs.size() > 0 )
               {
                  if ( rows == 0 )
                  {
                     // 添加批次
                     insertPaymentBatch( paymentBatchVO );

                     // 一个批次添加成功
                     rows = 1;
                  }

                  for ( PaymentDTO paymentDTO : paymentDTOs )
                  {
                     // 获得PaymentHeaderVO
                     final PaymentHeaderVO paymentHeaderVO = paymentDTO.getPaymentHeaderVO();

                     if ( paymentHeaderVO != null && KANUtil.filterEmpty( paymentHeaderVO.getPaymentHeaderId() ) == null )
                     {
                        // 设置BatchId
                        paymentHeaderVO.setBatchId( paymentBatchVO.getBatchId() );

                        if ( KANUtil.filterEmpty( paymentHeaderVO.getVendorId() ) != null )
                        {
                           // 初始化VendorVO
                           final VendorVO vendorVO = getVendorDao().getVendorVOByVendorId( paymentHeaderVO.getVendorId() );

                           if ( vendorVO != null )
                           {
                              paymentHeaderVO.setVendorNameZH( vendorVO.getNameZH() );
                              paymentHeaderVO.setVendorNameEN( vendorVO.getNameEN() );
                           }
                        }

                        // 插入SBHeaderVO
                        this.getPaymentHeaderDao().insertPaymentHeader( paymentHeaderVO );

                        // 获得PaymentDetailVO List
                        final List< PaymentDetailVO > paymentDetailVOs = paymentDTO.getPaymentDetailVOs();

                        if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
                        {
                           // 遍历
                           for ( PaymentDetailVO paymentDetailVO : paymentDetailVOs )
                           {
                              // 设置HeaderId
                              paymentDetailVO.setPaymentHeaderId( paymentHeaderVO.getPaymentHeaderId() );
                              // 插入PaymentDetailVO
                              this.getPaymentDetailDao().insertPaymentDetail( paymentDetailVO );
                           }
                        }
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( paymentBatchVO.getBatchId() ) != null )
         {
            // 批次执行结束时间
            paymentBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            paymentBatchVO.setDeleted( PaymentBatchVO.TRUE );
            paymentBatchVO.setStatus( PaymentBatchVO.TRUE );
            // 修改批次
            updatePaymentBatch( paymentBatchVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Added by Kevin Jin at 2013-12-29
   public int insertPaymentBatchInHouse( final PaymentBatchVO paymentBatchVO, final BatchTempVO batchTempVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final ServiceContractVO serviceContractVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // 开启事务
         startTransaction();

         final List< String > flags = new ArrayList< String >();
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SALARY );
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SB );
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_CB );
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER );

         clientOrderHeaderVO.setSettlementFlags( flags );

         final List< ClientOrderDTO > clientOrderDTOs = this.getClientOrderHeaderService().getClientOrderDTOsByCondition( clientOrderHeaderVO );

         // 遍历并逐个计算订单
         if ( clientOrderDTOs != null && clientOrderDTOs.size() > 0 )
         {
            for ( ClientOrderDTO clientOrderDTO : clientOrderDTOs )
            {
               clientOrderDTO.calculateSettlement( flags );
            }

            // 调用Service方法存储数据
            this.getBatchTempService().insertBatchTemp_nt( batchTempVO, clientOrderDTOs );

            // 提交批次
            batchTempVO.setSelectedIds( batchTempVO.getEncodedId() );
            batchTempVO.setPageFlag( BatchTempService.BATCH );
            this.getBatchTempService().submitBatchTemp_nt( batchTempVO );

            // 调用Service方法存储数据
            rows = insertPaymentBatch_nt( paymentBatchVO, serviceContractVO );
         }
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   // 获取工资单列表
   /* Added by siuxia at 2014-03-14 */
   @Override
   public PagedListHolder getPaymentDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException
   {
      // 初始化PaymentBatchVO
      final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) pagedListHolder.getObject();
      // 获 取pageFlag
      final String pageFlag = paymentBatchVO.getPageFlag();
      // 初始化包含的科目
      final List< ItemVO > itemVOs = new ArrayList< ItemVO >();

      List< Object > source = new ArrayList< Object >();

      // 按Batch获取
      if ( KANUtil.filterEmpty( pageFlag ) != null && pageFlag.trim().equalsIgnoreCase( PAGE_FLAG_BATCH ) )
      {
         // 初始化PaymentBatchVO列表
         final List< Object > paymentBatchVOs = new ArrayList< Object >();

         // 存在选中的batchId
         if ( KANUtil.filterEmpty( paymentBatchVO.getSelectedIds() ) != null )
         {
            for ( String selectId : paymentBatchVO.getSelectedIds().split( "," ) )
            {
               // 装载PaymentBatchVO
               paymentBatchVOs.add( ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectId ) ) );
            }
         }
         else
         {
            // 装载所有有效PaymentBatchVO
            paymentBatchVOs.addAll( ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOsByCondition( paymentBatchVO ) );
         }

         source = getPaymentDTOsByPaymentBatchVOs( paymentBatchVOs, paymentBatchVO );
      }
      // 按Head获取
      else if ( KANUtil.filterEmpty( pageFlag ) != null && pageFlag.trim().equalsIgnoreCase( PAGE_FLAG_HEADER ) )
      {
         // 初始化PaymentHeaderVO列表
         final List< Object > paymentHeaderVOs = new ArrayList< Object >();

         // 存在选中的headerId
         if ( KANUtil.filterEmpty( paymentBatchVO.getSelectedIds() ) != null )
         {
            for ( String selectId : paymentBatchVO.getSelectedIds().split( "," ) )
            {
               // 装载PaymentHeaderVO
               paymentHeaderVOs.add( this.getPaymentHeaderDao().getPaymentHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectId ) ) );
            }
         }
         else
         {
            final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
            paymentHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( paymentBatchVO.getBatchId() ) );
            paymentHeaderVO.setAccountId( paymentBatchVO.getAccountId() );
            paymentHeaderVO.setCorpId( paymentBatchVO.getCorpId() );

            paymentHeaderVO.setRulePublic( paymentBatchVO.getRulePublic() );
            paymentHeaderVO.setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
            paymentHeaderVO.setRulePositionIds( paymentBatchVO.getRulePositionIds() );
            paymentHeaderVO.setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
            paymentHeaderVO.setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
            paymentHeaderVO.setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );

            // 按照批次装载PaymentHeaderVO
            paymentHeaderVOs.addAll( this.getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO ) );
         }

         source = getPaymentDTOsByPaymentHeaderVOs( paymentHeaderVOs, itemVOs );
      }

      dealPaymentDTOs( source );

      if ( itemVOs != null && itemVOs.size() > 0 )
      {
         pagedListHolder.setAdditionalObject( itemVOs );
      }
      pagedListHolder.setSource( source );

      return pagedListHolder;
   }

   // 按批次获取PayslipDTO列表
   private List< Object > getPaymentDTOsByPaymentBatchVOs( final List< Object > paymentBatchVOs, final PaymentBatchVO paymentBatch ) throws KANException
   {
      // 初始化返回值
      final List< Object > paymentDTOs = new ArrayList< Object >();

      // 存在PaymentBatchVO列表
      if ( paymentBatchVOs != null && paymentBatchVOs.size() > 0 )
      {
         for ( Object paymentBatchVOObject : paymentBatchVOs )
         {
            // 初始化PaymentBatchVO
            final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) paymentBatchVOObject;

            // 初始化PaymentHeaderVO
            final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
            paymentHeaderVO.setBatchId( paymentBatchVO.getBatchId() );
            paymentHeaderVO.setAccountId( paymentBatchVO.getAccountId() );
            paymentHeaderVO.setCorpId( paymentBatchVO.getCorpId() );
            paymentHeaderVO.setRulePublic( paymentBatch.getRulePublic() );
            paymentHeaderVO.setRulePrivateIds( paymentBatch.getRulePrivateIds() );
            paymentHeaderVO.setRulePositionIds( paymentBatch.getRulePositionIds() );
            paymentHeaderVO.setRuleBranchIds( paymentBatch.getRuleBranchIds() );
            paymentHeaderVO.setRuleBusinessTypeIds( paymentBatch.getRuleBusinessTypeIds() );
            paymentHeaderVO.setRuleEntityIds( paymentBatch.getRuleEntityIds() );

            // 获取PaymentHeaderVO列表
            final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

            // 存在PaymentHeaderVO列表
            if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
            {
               paymentDTOs.addAll( getPaymentDTOsByPaymentHeaderVOs( paymentHeaderVOs, null ) );
            }
         }
      }

      return paymentDTOs;
   }

   // 按Head获取PayslipDTO列表
   private List< Object > getPaymentDTOsByPaymentHeaderVOs( final List< Object > paymentHeaderVOs, final List< ItemVO > itemVOs ) throws KANException
   {
      // 初始化返回值
      final List< Object > paymentDTOs = new ArrayList< Object >();

      // 存在PaymentHeaderVO列表
      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            // 计算合计
            countAmount( ( PaymentHeaderVO ) paymentHeaderVOObject );
            // 初始化PaymentDTO
            final PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentHeaderVO( ( PaymentHeaderVO ) paymentHeaderVOObject );

            // 获取PaymentDetailVO列表
            final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( ( ( PaymentHeaderVO ) paymentHeaderVOObject ).getPaymentHeaderId() );

            // 存在PaymentDetailVO列表
            if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
            {
               int count = 0;

               for ( Object paymentDetailVOObject : paymentDetailVOs )
               {
                  final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;
                  paymentDetailVO.setAccountId( ( ( PaymentHeaderVO ) paymentHeaderVOObject ).getAccountId() );

                  final Map< String, String > currItems = getItems( paymentDTO.getPaymentDetailVOs() );

                  if ( count == 0 || !isExist( currItems, paymentDetailVO.getItemId() ) )
                  {
                     paymentDTO.getPaymentDetailVOs().add( paymentDetailVO );
                  }
                  else
                  {
                     paymentDTO.dealPaymentDetailVO( paymentDetailVO );
                  }

                  count++;

                  // 获取包含所有科目的集合
                  if ( itemVOs != null )
                  {
                     fetchItemVOs( itemVOs, paymentDetailVO );
                  }
               }
            }

            paymentDTOs.add( paymentDTO );
         }
      }

      return paymentDTOs;
   }

   private void fetchItemVOs( final List< ItemVO > itemVOs, final PaymentDetailVO paymentDetailVO )
   {
      // 判断科目是否存在科目集合中，不存在则新增
      if ( itemVOs != null && itemVOs.size() > 0 )
      {
         for ( ItemVO itemVO : itemVOs )
         {
            if ( itemVO.getItemId().equals( paymentDetailVO.getItemId() ) )
            {
               return;
            }
         }
      }

      // 初始化新增ItemVO
      final ItemVO itemVO = new ItemVO();
      itemVO.setItemId( paymentDetailVO.getItemId() );
      itemVO.setItemType( paymentDetailVO.getItemType() );
      itemVO.setItemNo( paymentDetailVO.getItemNo() );
      itemVOs.add( itemVO );
   }

   // PaymentDTO List处理，使之其中PaymentDetailVOs科目一致
   private void dealPaymentDTOs( final List< Object > paymentDTOs )
   {
      if ( paymentDTOs != null && paymentDTOs.size() > 0 )
      {
         final Map< String, String > allItems = new HashMap< String, String >();

         for ( Object paymentDTOObject : paymentDTOs )
         {
            fetchItems( allItems, paymentDTOObject );
         }

         for ( Object paymentDTOObject : paymentDTOs )
         {
            final List< PaymentDetailVO > paymentDetailVOs = ( ( PaymentDTO ) paymentDTOObject ).getPaymentDetailVOs();

            final Map< String, String > currItems = getItems( paymentDetailVOs );

            if ( currItems != null && currItems.size() > 0 )
            {
               for ( Entry< String, String > item : allItems.entrySet() )
               {
                  if ( !isExist( currItems, item.getKey() ) )
                  {
                     final PaymentDetailVO tempDetailVO = new PaymentDetailVO();
                     tempDetailVO.setItemId( item.getKey() );
                     tempDetailVO.setItemType( item.getValue() );

                     ( ( PaymentDTO ) paymentDTOObject ).getPaymentDetailVOs().add( tempDetailVO );
                  }
               }
            }
         }
      }
   }

   // 装载Item，从一个PaymentDTO中的所有PaymentDetailVO获取
   private void fetchItems( final Map< String, String > allItems, final Object paymentDTOObject )
   {
      final PaymentDTO paymentDTO = ( PaymentDTO ) paymentDTOObject;

      if ( paymentDTO != null && paymentDTO.getPaymentDetailVOs() != null && paymentDTO.getPaymentDetailVOs().size() > 0 )
      {
         for ( PaymentDetailVO paymentDetailVO : paymentDTO.getPaymentDetailVOs() )
         {
            if ( allItems.size() == 0 || !isExist( allItems, paymentDetailVO.getItemId() ) )
            {
               allItems.put( paymentDetailVO.getItemId(), paymentDetailVO.getItemType() );
            }
         }
      }
   }

   // 由集合返回一个Map
   private Map< String, String > getItems( final List< ? > paymentDetailVOs )
   {
      final Map< String, String > items = new HashMap< String, String >();

      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;
            items.put( paymentDetailVO.getItemId(), paymentDetailVO.getItemType() );
         }
      }

      return items;
   }

   // 返回itemId是否存在于Map
   private boolean isExist( final Map< String, String > items, final String itemId )
   {
      if ( KANUtil.filterEmpty( itemId ) != null && items.size() > 0 )
      {
         for ( String key : items.keySet() )
         {
            if ( key.equals( itemId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // 工资发放邮件通知  直接发送。不经过系统邮件轮询
   @SuppressWarnings("unused")
   private void noticeByEmail( PaymentHeaderVO paymentHeaderVO )
   {
      try
      {
         List< Object > paymentDetailObjects = paymentDetailDao.getPaymentDetailVOsByHeaderId( paymentHeaderVO.getPaymentHeaderId() );
         final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( paymentHeaderVO.getEmployeeId() );
         if ( employeeVO != null && ( KANUtil.filterEmpty( employeeVO.getEmail1() ) != null || KANUtil.filterEmpty( employeeVO.getEmail2() ) != null ) )
         {
            final StringBuffer sb = new StringBuffer();
            sb.append( employeeVO.getNameZH() ).append( ",你好！你的" ).append( paymentHeaderVO.getMonthly() ).append( "工资单已生成如下:<br/>" );
            sb.append( "<table border='1'>" );
            sb.append( "<tr><td>姓名</td><td>月份</td>" );
            for ( Object obj : paymentDetailObjects )
            {
               final ItemVO itemVO = KANConstants.getKANAccountConstants( paymentHeaderVO.getAccountId() ).getItemVOByItemId( ( ( PaymentDetailVO ) obj ).getItemId() );
               sb.append( "<td>" ).append( itemVO.getNameZH() ).append( "</td>" );
            }
            sb.append( "<td>个人收入</td><td>个人支出</td><td>个税</td><td>实发</td></tr>" );
            sb.append( "<tr>" ).append( "<td>" + employeeVO.getNameZH() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getMonthly() + "</td>" );
            for ( Object obj : paymentDetailObjects )
            {
               ( ( PaymentDetailVO ) obj ).setAccountId( paymentHeaderVO.getAccountId() );
               sb.append( "<td>" ).append( ( ( PaymentDetailVO ) obj ).getAmountPersonal() ).append( "</td>" );
            }
            sb.append( "<td>" + paymentHeaderVO.getBillAmountPersonal() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getCostAmountPersonal() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getTaxAmountPersonal() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getAfterTaxSalary() + "</td>" );
            sb.append( "</tr></table>" );

            final MessageMailVO messageMailVO = new MessageMailVO();
            messageMailVO.setSystemId( "1" );
            messageMailVO.setAccountId( paymentHeaderVO.getAccountId() );
            messageMailVO.setCorpId( paymentHeaderVO.getCorpId() );
            messageMailVO.setTitle( paymentHeaderVO.getMonthly() + "月工资单" );
            messageMailVO.setContent( sb.toString() );
            messageMailVO.setContentType( "2" );
            messageMailVO.setReception( KANUtil.filterEmpty( employeeVO.getEmail1() ) == null ? employeeVO.getEmail2() : employeeVO.getEmail1() );
            messageMailVO.setCreateBy( "system" );
            messageMailVO.setCreateDate( new Date() );
            new Mail( messageMailVO.getAccountId(), messageMailVO.getReception(), messageMailVO.getTitle(), messageMailVO.getContent() ).send( true );
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
   }
}

package com.kan.hro.service.impl.biz.payment;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportBatchDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportBatchVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportBatchService;

public class PaymentAdjustmentImportBatchServiceImpl extends ContextService implements PaymentAdjustmentImportBatchService
{

   private PaymentAdjustmentImportBatchDao paymentAdjustmentImportBatchDao;
   
   private PaymentAdjustmentImportHeaderDao paymentAdjustmentImportHeaderDao;
   
   private PaymentAdjustmentImportDetailDao paymentAdjustmentImportDetailDao;
   
   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getPaymentAdjustmentImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( paymentAdjustmentImportBatchDao.countPaymentAdjustmentImportBatchVOsByCondition( ( PaymentAdjustmentImportBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( paymentAdjustmentImportBatchDao.getPaymentAdjustmentImportBatchVOsByCondition( ( PaymentAdjustmentImportBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentAdjustmentImportBatchDao.getPaymentAdjustmentImportBatchVOsByCondition( ( PaymentAdjustmentImportBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public PaymentAdjustmentImportBatchVO getPaymentAdjustmentImportBatchById( String batchId ) throws KANException
   {
      return paymentAdjustmentImportBatchDao.getPaymentAdjustmentImportBatchVOByBatchId( batchId );
   }

   @Override
   public int updatePaymentAdjustmentImportBatch( final PaymentAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException
   {
      int i = 0;
      try
      {
         // 开启事务
         this.startTransaction();
         i = i + paymentAdjustmentImportHeaderDao.insertPaymentAdjustmentHeaderTempToHeader( sbAdjustmentImportBatchVO.getBatchId() );
         i = i + paymentAdjustmentImportDetailDao.insertPaymentAdjustmentDetailTempToDetail( sbAdjustmentImportBatchVO.getBatchId() );
         paymentAdjustmentImportHeaderDao.updateHeaderStatus(sbAdjustmentImportBatchVO.getBatchId());
         paymentAdjustmentImportBatchDao.updateBathStatus( sbAdjustmentImportBatchVO );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return i;
   }

   @Override
   public int backBatch( final PaymentAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         paymentAdjustmentImportDetailDao.deletePaymentAdjustmentImportDetailTempByBatchId( sbAdjustmentImportBatchVO.getBatchId() );
         paymentAdjustmentImportHeaderDao.deletePaymentAdjustmentImportHeaderTempByBatchId( sbAdjustmentImportBatchVO.getBatchId() );
         commonBatchDao.deleteCommonBatch( sbAdjustmentImportBatchVO.getBatchId() );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   public PaymentAdjustmentImportBatchDao getPaymentAdjustmentImportBatchDao()
   {
      return paymentAdjustmentImportBatchDao;
   }

   public void setPaymentAdjustmentImportBatchDao( PaymentAdjustmentImportBatchDao paymentAdjustmentImportBatchDao )
   {
      this.paymentAdjustmentImportBatchDao = paymentAdjustmentImportBatchDao;
   }

   public PaymentAdjustmentImportHeaderDao getPaymentAdjustmentImportHeaderDao()
   {
      return paymentAdjustmentImportHeaderDao;
   }

   public void setPaymentAdjustmentImportHeaderDao( PaymentAdjustmentImportHeaderDao paymentAdjustmentImportHeaderDao )
   {
      this.paymentAdjustmentImportHeaderDao = paymentAdjustmentImportHeaderDao;
   }

   public PaymentAdjustmentImportDetailDao getPaymentAdjustmentImportDetailDao()
   {
      return paymentAdjustmentImportDetailDao;
   }

   public void setPaymentAdjustmentImportDetailDao( PaymentAdjustmentImportDetailDao paymentAdjustmentImportDetailDao )
   {
      this.paymentAdjustmentImportDetailDao = paymentAdjustmentImportDetailDao;
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }
}

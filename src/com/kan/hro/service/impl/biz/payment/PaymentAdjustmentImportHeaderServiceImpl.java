package com.kan.hro.service.impl.biz.payment;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportBatchDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportHeaderService;

public class PaymentAdjustmentImportHeaderServiceImpl extends ContextService implements PaymentAdjustmentImportHeaderService
{
   private PaymentAdjustmentImportBatchDao paymentAdjustmentImportBatchDao;
   
   private PaymentAdjustmentImportHeaderDao paymentAdjustmentImportHeaderDao;
   
   private PaymentAdjustmentImportDetailDao paymentAdjustmentImportDetailDao;
   
   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getPaymentAdjustmentImportHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( paymentAdjustmentImportHeaderDao.countPaymentAdjustmentImportHeaderVOsByCondition( ( PaymentAdjustmentImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( paymentAdjustmentImportHeaderDao.getPaymentAdjustmentImportHeaderVOsByCondition( ( PaymentAdjustmentImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentAdjustmentImportHeaderDao.getPaymentAdjustmentImportHeaderVOsByCondition( ( PaymentAdjustmentImportHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public int backUpRecord( String[] ids, String batchId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         paymentAdjustmentImportDetailDao.deleteDetailTempRecord( ids );
         paymentAdjustmentImportHeaderDao.deleteHeaderTempRecord( ids );
         int count = paymentAdjustmentImportHeaderDao.getHeaderCountByBatchId( batchId );
         if ( count == 0 )
         {
            commonBatchDao.deleteCommonBatch( batchId );
         }
         // 提交事务
         this.commitTransaction();
         return count;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }
   
   @Override
   public PaymentAdjustmentImportHeaderVO getPaymentAdjustmentImportHeaderVOsById( final String headerId,final String accountId ) throws KANException
   {
      return paymentAdjustmentImportHeaderDao.getPaymentAdjustmentImportHeaderVOsById( headerId ,accountId);
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
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
}

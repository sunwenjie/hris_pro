package com.kan.hro.service.impl.biz.payment;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportDetailDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportDetailVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportDetailService;

public class PaymentAdjustmentImportDetailServiceImpl extends ContextService implements PaymentAdjustmentImportDetailService
{
   private PaymentAdjustmentImportDetailDao paymentAdjustmentImportDetailDao;

   @Override
   public PagedListHolder getPaymentAdjustmentImportDetailVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( paymentAdjustmentImportDetailDao.countPaymentAdjustmentImportDetailVOsByCondition( ( PaymentAdjustmentImportDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( paymentAdjustmentImportDetailDao.getPaymentAdjustmentImportDetailVOsByCondition( ( PaymentAdjustmentImportDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentAdjustmentImportDetailDao.getPaymentAdjustmentImportDetailVOsByCondition( ( PaymentAdjustmentImportDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
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

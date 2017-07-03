package com.kan.hro.service.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentDetailDao;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.service.inf.biz.payment.PaymentDetailService;

public class PaymentDetailServiceImpl extends ContextService implements PaymentDetailService
{

   @Override
   public PagedListHolder getPaymentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PaymentDetailDao paymentDetailDao = ( PaymentDetailDao ) getDao();
      pagedListHolder.setHolderSize( paymentDetailDao.countPaymentDetailVOsByCondition( ( PaymentDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( paymentDetailDao.getPaymentDetailVOsByCondition( ( PaymentDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentDetailDao.getPaymentDetailVOsByCondition( ( PaymentDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public PaymentDetailVO getPaymentDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( PaymentDetailDao ) getDao() ).getPaymentDetailVOByDetailId( detailId );
   }

   @Override
   public int updatePaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      return ( ( PaymentDetailDao ) getDao() ).updatePaymentDetail( paymentDetailVO );
   }

   @Override
   public int insertPaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      return ( ( PaymentDetailDao ) getDao() ).insertPaymentDetail( paymentDetailVO );
   }

   @Override
   public int deletePaymentDetail( final String paymentDetailId ) throws KANException
   {
      return ( ( PaymentDetailDao ) getDao() ).deletePaymentDetail( paymentDetailId );
   }

   @Override
   public List< Object > getPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      return ( ( PaymentDetailDao ) getDao() ).getPaymentDetailVOsByCondition( paymentDetailVO );
   }

   @Override
   public List< Object > getPaymentDetailVOsByPaymentHeaderCond( PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return ( ( PaymentDetailDao ) getDao() ).getPaymentDetailVOsByPaymentHeaderCond( paymentHeaderVO );
   }

}

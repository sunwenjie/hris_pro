package com.kan.hro.service.impl.biz.payment;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentReportDao;
import com.kan.hro.domain.biz.payment.PaymentReportVO;
import com.kan.hro.service.inf.biz.payment.PaymentReportService;

public class PaymentReportServiceImpl extends ContextService implements PaymentReportService
{

   @Override
   public PagedListHolder getAVGPaymentReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PaymentReportDao dao = ( PaymentReportDao ) getDao();
      final PaymentReportVO object = ( PaymentReportVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( dao.countAVGPaymentReportVOsByCondition( object ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getAVGPaymentReportVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getAVGPaymentReportVOsByCondition( object ) );
      }
      return pagedListHolder;
   }
}

package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentReportDao;
import com.kan.hro.domain.biz.payment.PaymentReportVO;

public class PaymentReportDaoImpl extends Context implements PaymentReportDao
{

   @Override
   public int countAVGPaymentReportVOsByCondition( final PaymentReportVO paymentReportVO ) throws KANException
   {
      return ( Integer ) select( "countAVGPaymentReportVOsByCondition", paymentReportVO );
   }

   @Override
   public List< Object > getAVGPaymentReportVOsByCondition( final PaymentReportVO paymentReportVO ) throws KANException
   {
      return selectList( "getAVGPaymentReportVOsByCondition", paymentReportVO );
   }

   @Override
   public List< Object > getAVGPaymentReportVOsByCondition( final PaymentReportVO paymentReportVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAVGPaymentReportVOsByCondition", paymentReportVO, rowBounds );
   }

}

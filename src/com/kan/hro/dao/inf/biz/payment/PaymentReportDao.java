package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentReportVO;

public interface PaymentReportDao
{
   public abstract int countAVGPaymentReportVOsByCondition( final PaymentReportVO paymentReportVO ) throws KANException;

   public abstract List< Object > getAVGPaymentReportVOsByCondition( final PaymentReportVO paymentReportVO ) throws KANException;

   public abstract List< Object > getAVGPaymentReportVOsByCondition( final PaymentReportVO paymentReportVO, final RowBounds rowBounds ) throws KANException;
}

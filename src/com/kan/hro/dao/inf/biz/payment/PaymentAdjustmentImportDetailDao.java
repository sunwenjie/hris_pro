package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportDetailVO;

public interface PaymentAdjustmentImportDetailDao
{
   public abstract int countPaymentAdjustmentImportDetailVOsByCondition( final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO ) throws KANException;
   
   public abstract List< Object > getPaymentAdjustmentImportDetailVOsByCondition( final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentImportDetailVOsByCondition( final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO, final RowBounds rowBounds ) throws KANException;
   
   public abstract void deletePaymentAdjustmentImportDetailTempByBatchId( final String batchId );
   
   public abstract int insertPaymentAdjustmentDetailTempToDetail( final String batchId );

   public abstract void deleteDetailTempRecord( final String[] ids );
}

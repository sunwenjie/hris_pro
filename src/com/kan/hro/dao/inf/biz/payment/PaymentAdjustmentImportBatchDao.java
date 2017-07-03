package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportBatchVO;

public interface PaymentAdjustmentImportBatchDao
{
   public abstract int countPaymentAdjustmentImportBatchVOsByCondition( final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO ) throws KANException;
   
   public abstract List< Object > getPaymentAdjustmentImportBatchVOsByCondition( final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentImportBatchVOsByCondition( final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract PaymentAdjustmentImportBatchVO getPaymentAdjustmentImportBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract void updateBathStatus( final PaymentAdjustmentImportBatchVO submitObject );

   public abstract void updatePaymentAdjustmentImportHeaderBybatchId( String batchId );
}

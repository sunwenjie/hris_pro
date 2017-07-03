package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportHeaderVO;

public interface PaymentAdjustmentImportHeaderDao
{
   public abstract int countPaymentAdjustmentImportHeaderVOsByCondition( final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentImportHeaderVOsByCondition( final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentImportHeaderVOsByCondition( final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO, final RowBounds rowBounds )throws KANException;

   public abstract void deletePaymentAdjustmentImportHeaderTempByBatchId( final String batchId ) throws KANException;

   public abstract int insertPaymentAdjustmentHeaderTempToHeader( final String batchId ) throws KANException;

   public abstract void deleteHeaderTempRecord( final String[] ids ) throws KANException;

   public abstract PaymentAdjustmentImportHeaderVO getPaymentAdjustmentImportHeaderVOsById( final String headerId, final String accountId ) throws KANException;

   public abstract int getHeaderCountByBatchId( final String batchId );

   public abstract void updateHeaderStatus( final String batchId ) throws KANException;
}

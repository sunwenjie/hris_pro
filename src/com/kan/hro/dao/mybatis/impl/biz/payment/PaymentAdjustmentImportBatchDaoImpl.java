package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportBatchDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportBatchVO;

public class PaymentAdjustmentImportBatchDaoImpl extends Context implements PaymentAdjustmentImportBatchDao
{

   @Override
   public int countPaymentAdjustmentImportBatchVOsByCondition( PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentAdjustmentImportBatchVOsByCondition", paymentAdjustmentImportBatchVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentImportBatchVOsByCondition( PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO ) throws KANException
   {
      return selectList( "getPaymentAdjustmentImportBatchVOsByCondition", paymentAdjustmentImportBatchVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentImportBatchVOsByCondition( PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPaymentAdjustmentImportBatchVOsByCondition", paymentAdjustmentImportBatchVO, rowBounds );
   }

   @Override
   public PaymentAdjustmentImportBatchVO getPaymentAdjustmentImportBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( PaymentAdjustmentImportBatchVO ) select( "getPaymentAdjustmentImportBatchVOByBatchId", batchId );
   }

   @Override
   public void updateBathStatus( final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO )
   {
      update( "updatePaymentAdjustmentImportBatchStatus", paymentAdjustmentImportBatchVO );
   }
   
   @Override
   public void updatePaymentAdjustmentImportHeaderBybatchId( String batchId)
   {
      update( "updatePaymentAdjustmentImportHeaderBybatchId", batchId );
   }
}

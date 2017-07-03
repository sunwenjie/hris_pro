package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportDetailDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportDetailVO;

public class PaymentAdjustmentImportDetailDaoImpl extends Context implements PaymentAdjustmentImportDetailDao
{
   @Override
   public int insertPaymentAdjustmentDetailTempToDetail( final String batchId )
   {
      return insert("insertPaymentAdjustmentDetailTempToDetail", batchId);
   }
   
   @Override
   public void deletePaymentAdjustmentImportDetailTempByBatchId( final String batchId )
   {
      delete("deletePaymentAdjustmentImportDetailTempByBatchId", batchId);
   }

   @Override
   public int countPaymentAdjustmentImportDetailVOsByCondition( final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentAdjustmentImportDetailVOsByCondition", paymentAdjustmentImportDetailVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentImportDetailVOsByCondition( final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO ) throws KANException
   {
      return selectList( "getPaymentAdjustmentImportDetailVOsByCondition", paymentAdjustmentImportDetailVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentImportDetailVOsByCondition( final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPaymentAdjustmentImportDetailVOsByCondition", paymentAdjustmentImportDetailVO ,rowBounds);
   }

   @Override
   public void deleteDetailTempRecord( String[] ids )
   {
      delete("deletePaymentAdjustmentImportDetailTempRecord", ids);
   }
}

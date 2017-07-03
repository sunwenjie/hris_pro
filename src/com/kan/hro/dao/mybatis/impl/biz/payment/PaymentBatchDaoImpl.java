package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentBatchDao;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;

public class PaymentBatchDaoImpl extends Context implements PaymentBatchDao
{

   @Override
   public int countPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentBatchVOsByCondition", paymentBatchVO );
   }

   @Override
   public List< Object > getPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return selectList( "getPaymentBatchVOsByCondition", paymentBatchVO );
   }

   @Override
   public List< Object > getPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO,final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPaymentBatchVOsByCondition", paymentBatchVO, rowBounds );
   }

   @Override
   public PaymentBatchVO getPaymentBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( PaymentBatchVO ) select( "getPaymentBatchVOByBatchId", batchId );
   }
   
   @Override
   public int insertPaymentBatch(final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return insert( "insertPaymentBatch", paymentBatchVO );
   }

   @Override
   public int updatePaymentBatch(final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return update( "updatePaymentBatch", paymentBatchVO );
   }

   @Override
   public int deletePaymentBatch(final String paymentBatchId ) throws KANException
   {
      return delete( "deletePaymentBatch", paymentBatchId );
   }

}

package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportHeaderVO;

public class PaymentAdjustmentImportHeaderDaoImpl extends Context implements PaymentAdjustmentImportHeaderDao
{
   @Override
   public int insertPaymentAdjustmentHeaderTempToHeader( final String batchId )
   {
      return insert( "insertPaymentAdjustmentHeaderTempToHeader", batchId );
   }

   @Override
   public void deletePaymentAdjustmentImportHeaderTempByBatchId( final String batchId )
   {
      delete( "deletePaymentAdjustmentImportHeaderTempByBatchId", batchId );
   }

   @Override
   public int countPaymentAdjustmentImportHeaderVOsByCondition( final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentAdjustmentImportHeaderVOsByCondition", paymentAdjustmentImportHeaderVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentImportHeaderVOsByCondition( final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO ) throws KANException
   {
      return selectList( "getPaymentAdjustmentImportHeaderVOsByCondition", paymentAdjustmentImportHeaderVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentImportHeaderVOsByCondition( final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPaymentAdjustmentImportHeaderVOsByCondition", paymentAdjustmentImportHeaderVO, rowBounds );
   }

   @Override
   public void deleteHeaderTempRecord( String[] ids ) throws KANException
   {
      delete( "deletePaymentAdjustmentImportHeaderTempRecord", ids );
   }
   
   @Override
   public int getHeaderCountByBatchId( String batchId )
   {
      return ( Integer ) select( "getPaymentAdjustmentImportHeaderCountByBatchId", batchId );
   }

   @Override
   public PaymentAdjustmentImportHeaderVO getPaymentAdjustmentImportHeaderVOsById( final String headerId, final String accountId ) throws KANException
   {
      Map< String, String > args = new HashMap< String, String >();
      args.put( "headerId", headerId );
      args.put( "accountId", accountId );
      return ( PaymentAdjustmentImportHeaderVO ) select( "getPaymentAdjustmentImportHeaderVOsById", args );
   }

   @Override
   public void updateHeaderStatus( final String batchId ) throws KANException
   {
      update( "updatePaymentAdjustmentImportHeaderStatus", batchId );
   }
}

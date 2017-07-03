package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;

public class PaymentAdjustmentDetailDaoImpl extends Context implements PaymentAdjustmentDetailDao
{
   private PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao;

   public PaymentAdjustmentHeaderDao getPaymentAdjustmentHeaderDao()
   {
      return paymentAdjustmentHeaderDao;
   }

   public void setPaymentAdjustmentHeaderDao( PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao )
   {
      this.paymentAdjustmentHeaderDao = paymentAdjustmentHeaderDao;
   }

   @Override
   public int countPaymentAdjustmentDetailVOsByCondition( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentAdjustmentDetailVOsByCondition", paymentAdjustmentDetailVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentDetailVOsByCondition( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      return selectList( "getPaymentAdjustmentDetailVOsByCondition", paymentAdjustmentDetailVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentDetailVOsByCondition( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPaymentAdjustmentDetailVOsByCondition", paymentAdjustmentDetailVO, rowBounds );
   }

   @Override
   public PaymentAdjustmentDetailVO getPaymentAdjustmentDetailVOByAdjustmentDetailId( String AdjustmentDetailId ) throws KANException
   {
      return ( PaymentAdjustmentDetailVO ) select( "getPaymentAdjustmentDetailVOByAdjustmentDetailId", AdjustmentDetailId );
   }

   @Override
   public int insertPaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      encodeNumber( paymentAdjustmentDetailVO );
      return insert( "insertPaymentAdjustmentDetail", paymentAdjustmentDetailVO );
   }

   @Override
   public int updatePaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      encodeNumber( paymentAdjustmentDetailVO );
      return update( "updatePaymentAdjustmentDetail", paymentAdjustmentDetailVO );
   }

   @Override
   public int deletePaymentAdjustmentDetail( final String AdjustmentDetailId ) throws KANException
   {
      return delete( "deletePaymentAdjustmentDetail", AdjustmentDetailId );
   }

   @Override
   public List< Object > getPaymentAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return selectList( "getPaymentAdjustmentDetailVOsByAdjustmentHeaderId", adjustmentHeaderId );
   }

   private void encodeNumber( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderDao.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( paymentAdjustmentDetailVO.getAdjustmentHeaderId() );
      if ( paymentAdjustmentHeaderVO != null )
      {
         final String increment = Cryptogram.getIncrement( Cryptogram.getPublicCode( paymentAdjustmentHeaderVO.getEmployeeId() ) );
         paymentAdjustmentDetailVO.setBillAmountPersonal( String.valueOf( Double.parseDouble( increment ) + Double.parseDouble( paymentAdjustmentDetailVO.getBillAmountPersonal() ) ) );
         paymentAdjustmentDetailVO.setCostAmountPersonal( String.valueOf( Double.parseDouble( increment ) + Double.parseDouble( paymentAdjustmentDetailVO.getCostAmountPersonal() ) ) );
         paymentAdjustmentDetailVO.setTaxAmountPersonal( String.valueOf( Double.parseDouble( increment ) + Double.parseDouble( paymentAdjustmentDetailVO.getTaxAmountPersonal() ) ) );
         paymentAdjustmentDetailVO.setAddtionalBillAmountPersonal( String.valueOf( Double.parseDouble( increment )
               + Double.parseDouble( paymentAdjustmentDetailVO.getAddtionalBillAmountPersonal() ) ) );
      }
   }
}

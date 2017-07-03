package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;

public class PaymentAdjustmentHeaderDaoImpl extends Context implements PaymentAdjustmentHeaderDao
{

   @Override
   public int countPaymentAdjustmentHeaderVOsByCondition( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentAdjustmentHeaderVOsByCondition", paymentAdjustmentHeaderVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentHeaderVOsByCondition( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      return selectList( "getPaymentAdjustmentHeaderVOsByCondition", paymentAdjustmentHeaderVO );
   }

   @Override
   public List< Object > getPaymentAdjustmentHeaderVOsByCondition( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPaymentAdjustmentHeaderVOsByCondition", paymentAdjustmentHeaderVO, rowBounds );
   }

   @Override
   public PaymentAdjustmentHeaderVO getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( String adjustmentHeaderId ) throws KANException
   {
      return ( PaymentAdjustmentHeaderVO ) select( "getPaymentAdjustmentHeaderVOByAdjustmentHeaderId", adjustmentHeaderId );
   }

   @Override
   public int insertPaymentAdjustmentHeader( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      encodeNumber( paymentAdjustmentHeaderVO );
      return insert( "insertPaymentAdjustmentHeader", paymentAdjustmentHeaderVO );
   }

   @Override
   public int updatePaymentAdjustmentHeader( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      encodeNumber( paymentAdjustmentHeaderVO );
      return update( "updatePaymentAdjustmentHeader", paymentAdjustmentHeaderVO );
   }

   @Override
   public int deletePaymentAdjustmentHeader( String adjustmentHeaderId ) throws KANException
   {
      return delete( "deletePaymentAdjustmentHeader", adjustmentHeaderId );
   }

   @Override
   public List< Object > getGroupPaymentAdjustmentHeaderVOsByCondition( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      return selectList( "getGroupPaymentAdjustmentHeaderVOsByCondition", paymentAdjustmentHeaderVO );
   }

   @Override
   public List< Object > getEmployeeGroupPaymentAdjustmentHeaderVOsByCondition( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      return selectList( "getEmployeeGroupPaymentAdjustmentHeaderVOsByCondition", paymentAdjustmentHeaderVO );
   }

   private void encodeNumber( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      final String increment = Cryptogram.getIncrement( Cryptogram.getPublicCode( paymentAdjustmentHeaderVO.getEmployeeId() ) );
      paymentAdjustmentHeaderVO.setBillAmountPersonal( add( increment, paymentAdjustmentHeaderVO.getBillAmountPersonal() ) );
      paymentAdjustmentHeaderVO.setCostAmountPersonal( add( increment, paymentAdjustmentHeaderVO.getCostAmountPersonal() ) );
      paymentAdjustmentHeaderVO.setTaxAmountPersonal( add( increment, paymentAdjustmentHeaderVO.getTaxAmountPersonal() ) );
      paymentAdjustmentHeaderVO.setAddtionalBillAmountPersonal( add( increment, paymentAdjustmentHeaderVO.getAddtionalBillAmountPersonal() ) );
      paymentAdjustmentHeaderVO.setTaxAgentAmountPersonal( add( increment, paymentAdjustmentHeaderVO.getTaxAgentAmountPersonal() ) );
   }

   private String add( String a, String b )
   {
      String num1 = KANUtil.filterEmpty( a ) == null ? "0" : a;
      String num2 = KANUtil.filterEmpty( b ) == null ? "0" : b;
      return String.valueOf( Double.parseDouble( num1 ) + Double.parseDouble( num2 ) );
   }
}

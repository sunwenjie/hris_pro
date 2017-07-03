package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;

public class PaymentHeaderDaoImpl extends Context implements PaymentHeaderDao
{
   @Override
   public int countPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentHeaderVOsByCondition", paymentHeaderVO );
   }

   @Override
   public List< Object > getPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      //return selectList( "getPaymentHeaderVOsByCondition", paymentHeaderVO );
      return getPaymentHeaderVOsByConditionDecode( paymentHeaderVO );
   }

   @Override
   public PaymentHeaderVO summaryPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return ( PaymentHeaderVO ) select( "summaryPaymentHeaderVOsByCondition", paymentHeaderVO );
   }

   @Override
   public List< Object > getPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      //return selectList( "getPaymentHeaderVOsByCondition", paymentHeaderVO, rowBounds );
      return getPaymentHeaderVOsByConditionDecode( paymentHeaderVO, rowBounds );
   }

   @Override
   public PaymentHeaderVO getPaymentHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( PaymentHeaderVO ) select( "getPaymentHeaderVOByHeaderId", headerId );
   }

   @Override
   public int updatePaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      encodeNumber( paymentHeaderVO );
      return update( "updatePaymentHeader", paymentHeaderVO );
   }

   @Override
   public int insertPaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      encodeNumber( paymentHeaderVO );
      return insert( "insertPaymentHeader", paymentHeaderVO );
   }

   @Override
   public int deletePaymentHeader( final String paymentHeaderId ) throws KANException
   {
      return delete( "deletePaymentHeader", paymentHeaderId );
   }

   @Override
   public List< Object > getMonthliesByPaymentHeaderVO( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return selectList( "getPaymentMonthliesByPaymentHeaderVO", paymentHeaderVO );
   }

   @Override
   public List< Object > getPaymentHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getPaymentHeaderVOsByBatchId", batchId );
   }

   private void encodeNumber( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      final Double increment = Double.parseDouble( Cryptogram.getIncrement( Cryptogram.getPublicCode( paymentHeaderVO.getEmployeeId() ) ) );
      paymentHeaderVO.setBillAmountCompany( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getBillAmountCompany() ) ) );
      paymentHeaderVO.setBillAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getBillAmountPersonal() ) ) );
      paymentHeaderVO.setCostAmountCompany( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getCostAmountCompany() ) ) );
      paymentHeaderVO.setCostAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getCostAmountPersonal() ) ) );
      paymentHeaderVO.setTaxAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getTaxAmountPersonal() ) ) );
      paymentHeaderVO.setAnnualBonusTax( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getAnnualBonusTax() ) ) );
      paymentHeaderVO.setAnnualBonus( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getAnnualBonus() ) ) );
      paymentHeaderVO.setAddtionalBillAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getAddtionalBillAmountPersonal() ) ) );
      if ( KANUtil.filterEmpty( paymentHeaderVO.getRemark5() ) == null )
      {
         paymentHeaderVO.setRemark5( "0" );
      }
      paymentHeaderVO.setRemark5( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getRemark5() ) ) );
      paymentHeaderVO.setTaxAgentAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentHeaderVO.getTaxAgentAmountPersonal() ) ) );

   }

   private void decodeNumber( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      // 被增加的值
      final Double incrementValue = Double.parseDouble( Cryptogram.getIncrement( Cryptogram.getPublicCode( paymentHeaderVO.getEmployeeId() ) ) );
      paymentHeaderVO.setBillAmountCompany( String.valueOf( Double.parseDouble( paymentHeaderVO.getBillAmountCompany() ) - incrementValue ) );
      paymentHeaderVO.setBillAmountPersonal( String.valueOf( Double.parseDouble( paymentHeaderVO.getBillAmountPersonal() ) - incrementValue ) );
      paymentHeaderVO.setCostAmountCompany( String.valueOf( Double.parseDouble( paymentHeaderVO.getCostAmountCompany() ) - incrementValue ) );
      paymentHeaderVO.setCostAmountPersonal( String.valueOf( Double.parseDouble( paymentHeaderVO.getCostAmountPersonal() ) - incrementValue ) );
      paymentHeaderVO.setTaxAmountPersonal( String.valueOf( Double.parseDouble( paymentHeaderVO.getTaxAmountPersonal() ) - incrementValue ) );
      paymentHeaderVO.setAnnualBonusTax( String.valueOf( Double.parseDouble( paymentHeaderVO.getAnnualBonusTax() ) - incrementValue ) );
      paymentHeaderVO.setAnnualBonus( String.valueOf( Double.parseDouble( paymentHeaderVO.getAnnualBonus() ) - incrementValue ) );
      paymentHeaderVO.setAddtionalBillAmountPersonal( String.valueOf( Double.parseDouble( paymentHeaderVO.getAddtionalBillAmountPersonal() ) - incrementValue ) );
      paymentHeaderVO.setRemark5( String.valueOf( Double.parseDouble( paymentHeaderVO.getRemark5() ) - incrementValue ) );
      paymentHeaderVO.setTaxAgentAmountPersonal( String.valueOf( Double.parseDouble( paymentHeaderVO.getTaxAgentAmountPersonal() ) - incrementValue ) );
   }

   public List< Object > getPaymentHeaderVOsByConditionDecode( PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      final List< Object > objects = selectList( "getEncodePaymentHeaderVOsByCondition", paymentHeaderVO );
      for ( Object obj : objects )
      {
         decodeNumber( ( PaymentHeaderVO ) obj );
      }
      return objects;
   }

   public List< Object > getPaymentHeaderVOsByConditionDecode( PaymentHeaderVO paymentHeaderVO, RowBounds rowBounds ) throws KANException
   {
      final List< Object > objects = selectList( "getEncodePaymentHeaderVOsByCondition", paymentHeaderVO, rowBounds );
      for ( Object obj : objects )
      {
         decodeNumber( ( PaymentHeaderVO ) obj );
      }
      return objects;
   }
}

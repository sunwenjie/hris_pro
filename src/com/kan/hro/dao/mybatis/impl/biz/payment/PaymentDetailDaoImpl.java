package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PaymentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;

public class PaymentDetailDaoImpl extends Context implements PaymentDetailDao
{
   private PaymentHeaderDao paymentHeaderDao;

   public PaymentHeaderDao getPaymentHeaderDao()
   {
      return paymentHeaderDao;
   }

   public void setPaymentHeaderDao( PaymentHeaderDao paymentHeaderDao )
   {
      this.paymentHeaderDao = paymentHeaderDao;
   }

   @Override
   public int countPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      return ( Integer ) select( "countPaymentDetailVOsByCondition", paymentDetailVO );
   }

   @Override
   public List< Object > getPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      //return selectList( "getPaymentDetailVOsByCondition", paymentDetailVO );
      return getPaymentDetailVOsByConditionDecode( paymentDetailVO );
   }

   @Override
   public List< Object > getPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO, final RowBounds rowBounds ) throws KANException
   {
      //return selectList( "getPaymentDetailVOsByCondition", paymentDetailVO, rowBounds );
      return getPaymentDetailVOsByConditionDecode( paymentDetailVO, rowBounds );
   }

   @Override
   public PaymentDetailVO getPaymentDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( PaymentDetailVO ) select( "getPaymentDetailVOByDetailId", detailId );
   }

   @Override
   public int updatePaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      encodeNumber( paymentDetailVO );
      return update( "updatePaymentDetail", paymentDetailVO );
   }

   @Override
   public int insertPaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      encodeNumber( paymentDetailVO );
      return insert( "insertPaymentDetail", paymentDetailVO );
   }

   @Override
   public int deletePaymentDetail( final String paymentDetailId ) throws KANException
   {
      return delete( "deletePaymentDetail", paymentDetailId );
   }

   @Override
   public List< Object > getPaymentDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getPaymentDetailVOsByHeaderId", headerId );
   }

   @Override
   public List< Object > getPaymentDetailVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getPaymentDetailVOsByContractId", contractId );
   }

   @Override
   public List< Object > getPaymentDetailVOsByBatchVO( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return selectList( "getPaymentDetailVOsByBatchVO", paymentBatchVO );
   }

   @Override
   public List< Object > getPaymentDetailVOsByPaymentHeaderCond( PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return selectList( "getPaymentDetailVOsByPaymentHeaderCond", paymentHeaderVO );
   }

   private List< Object > getPaymentDetailVOsByConditionDecode( PaymentDetailVO paymentDetailVO ) throws KANException
   {
      final List< Object > objects = selectList( "getEncodePaymentDetailVOsByCondition", paymentDetailVO );
      for ( Object obj : objects )
      {
         decodeNumber( ( PaymentDetailVO ) obj );
      }
      return objects;
   }

   private List< Object > getPaymentDetailVOsByConditionDecode( PaymentDetailVO paymentDetailVO, RowBounds rowBounds ) throws KANException
   {
      final List< Object > objects = selectList( "getEncodePaymentDetailVOsByCondition", paymentDetailVO, rowBounds );
      for ( Object obj : objects )
      {
         decodeNumber( ( PaymentDetailVO ) obj );
      }
      return objects;
   }

   private void encodeNumber( final PaymentDetailVO paymentDetailVO ) throws KANException
   {
      // 加密可能没有employeeId
      final PaymentHeaderVO paymentHeaderVO = paymentHeaderDao.getPaymentHeaderVOByHeaderId( paymentDetailVO.getPaymentHeaderId() );
      if ( paymentHeaderVO != null )
      {
         final Double increment = Double.parseDouble( Cryptogram.getIncrement( Cryptogram.getPublicCode( paymentHeaderVO.getEmployeeId() ) ) );
         paymentDetailVO.setBillAmountCompany( String.valueOf( increment + Double.parseDouble( paymentDetailVO.getBillAmountCompany() ) ) );
         paymentDetailVO.setBillAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentDetailVO.getBillAmountPersonal() ) ) );
         paymentDetailVO.setCostAmountCompany( String.valueOf( increment + Double.parseDouble( paymentDetailVO.getCostAmountCompany() ) ) );
         paymentDetailVO.setCostAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentDetailVO.getCostAmountPersonal() ) ) );
         paymentDetailVO.setAddtionalBillAmountPersonal( String.valueOf( increment + Double.parseDouble( paymentDetailVO.getAddtionalBillAmountPersonal() ) ) );
      }
   }

   private void decodeNumber( PaymentDetailVO paymentDetailVO ) throws KANException
   {
      final Double increment = Double.parseDouble( Cryptogram.getIncrement( Cryptogram.getPublicCode( paymentDetailVO.getEmployeeId() ) ) );
      paymentDetailVO.setBillAmountCompany( String.valueOf( Double.parseDouble( paymentDetailVO.getBillAmountCompany() ) - increment ) );
      paymentDetailVO.setBillAmountPersonal( String.valueOf( Double.parseDouble( paymentDetailVO.getBillAmountPersonal() ) - increment ) );
      paymentDetailVO.setCostAmountCompany( String.valueOf( Double.parseDouble( paymentDetailVO.getCostAmountCompany() ) - increment ) );
      paymentDetailVO.setCostAmountPersonal( String.valueOf( Double.parseDouble( paymentDetailVO.getCostAmountPersonal() ) - increment ) );
      paymentDetailVO.setAddtionalBillAmountPersonal( String.valueOf( Double.parseDouble( paymentDetailVO.getAddtionalBillAmountPersonal() ) - increment ) );
   }

}

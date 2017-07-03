package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PayslipDetailViewDao;
import com.kan.hro.domain.biz.payment.PayslipDetailView;

public class PayslipDetailViewDaoImpl extends Context implements PayslipDetailViewDao
{

   @Override
   public int countPayslipDetailViewsByCondition( final PayslipDetailView payslipDetailView ) throws KANException
   {
      return ( Integer ) select( "countPayslipDetailViewsByCondition", payslipDetailView );
   }

   @Override
   public List< Object > getPayslipDetailViewsByCondition( final PayslipDetailView payslipDetailView ) throws KANException
   {
      return selectList( "getPayslipDetailViewsByCondition", payslipDetailView );
   }

   @Override
   public List< Object > getPayslipDetailViewsByCondition( final PayslipDetailView payslipDetailView, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPayslipDetailViewsByCondition", payslipDetailView, rowBounds );
   }

}

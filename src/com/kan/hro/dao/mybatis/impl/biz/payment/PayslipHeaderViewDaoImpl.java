package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.PayslipHeaderViewDao;
import com.kan.hro.domain.biz.payment.PayslipHeaderView;

public class PayslipHeaderViewDaoImpl extends Context implements PayslipHeaderViewDao
{
   @Override
   public List< Object > getPayslipHeaderViewsByCondition( final PayslipHeaderView payslipHeaderView ) throws KANException
   {
      return selectList( "getPayslipHeaderViewsByCondition", payslipHeaderView );
   }

   @Override
   public List< Object > getPayslipHeaderViewsByCondition( final PayslipHeaderView payslipHeaderView, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPayslipHeaderViewsByCondition", payslipHeaderView, rowBounds );
   }

}

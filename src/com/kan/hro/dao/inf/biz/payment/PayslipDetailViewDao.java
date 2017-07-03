package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PayslipDetailView;

public interface PayslipDetailViewDao
{
   public abstract int countPayslipDetailViewsByCondition( final PayslipDetailView paymentView ) throws KANException;

   public abstract List< Object > getPayslipDetailViewsByCondition( final PayslipDetailView paymentView ) throws KANException;

   public abstract List< Object > getPayslipDetailViewsByCondition( final PayslipDetailView paymentView, final RowBounds rowBounds ) throws KANException;
}

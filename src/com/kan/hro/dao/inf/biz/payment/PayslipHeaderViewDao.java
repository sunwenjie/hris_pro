package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PayslipHeaderView;

public interface PayslipHeaderViewDao
{
   public abstract List< Object > getPayslipHeaderViewsByCondition( final PayslipHeaderView paymentView ) throws KANException;

   public abstract List< Object > getPayslipHeaderViewsByCondition( final PayslipHeaderView paymentView, final RowBounds rowBounds ) throws KANException;
}

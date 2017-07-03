package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.IncomeTaxYearView;

public interface IncomeTaxYearViewDao
{
   public abstract List< Object > getIncomeTaxYearViewsByCondition( final IncomeTaxYearView incomeTaxYearView ) throws KANException;

   public abstract List< Object > getIncomeTaxYearViewsByCondition( final IncomeTaxYearView incomeTaxYearView, final RowBounds rowBounds ) throws KANException;

   public abstract IncomeTaxYearView getIncomeTaxYearViewByCondition( final IncomeTaxYearView incomeTaxYearView ) throws KANException;
}

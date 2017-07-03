package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.IncomeTaxYearViewDao;
import com.kan.hro.domain.biz.payment.IncomeTaxYearView;

public class IncomeTaxYearViewDaoImpl extends Context implements IncomeTaxYearViewDao
{

   @Override
   public List< Object > getIncomeTaxYearViewsByCondition( IncomeTaxYearView incomeTaxYearView ) throws KANException
   {
      return selectList( "getIncomeTaxYearViewsByCondition", incomeTaxYearView );
   }

   @Override
   public List< Object > getIncomeTaxYearViewsByCondition( IncomeTaxYearView incomeTaxYearView, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getIncomeTaxYearViewsByCondition", incomeTaxYearView, rowBounds );
   }

   @Override
   public IncomeTaxYearView getIncomeTaxYearViewByCondition( IncomeTaxYearView incomeTaxYearView ) throws KANException
   {
      return ( IncomeTaxYearView ) select( "getIncomeTaxYearViewByCondition", incomeTaxYearView );
   }

}

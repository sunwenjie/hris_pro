package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.TaxTemplateDetailDao;
import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.util.KANException;

public class TaxTemplateDetailDaoImpl extends Context implements TaxTemplateDetailDao
{

   @Override
   public int countTaxTemplateDetailVOsByCondition( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException
   {
      return ( Integer ) select( "countTaxTemplateDetailVOsByCondition", taxTemplateDetailVO );
   }

   @Override
   public List< Object > getTaxTemplateDetailVOsByCondition( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException
   {
      return selectList( "getTaxTemplateDetailVOsByCondition", taxTemplateDetailVO );
   }

   @Override
   public List< Object > getTaxTemplateDetailVOsByCondition( final TaxTemplateDetailVO taxTemplateDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTaxTemplateDetailVOsByCondition", taxTemplateDetailVO, rowBounds );
   }

   @Override
   public TaxTemplateDetailVO getTaxTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException
   {
      return ( TaxTemplateDetailVO ) select( "getTaxTemplateDetailVOByTemplateDetailId", templateDetailId );
   }

   @Override
   public int insertTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException
   {
      return insert( "insertTaxTemplateDetail", taxTemplateDetailVO );
   }

   @Override
   public int updateTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException
   {
      return update( "updateTaxTemplateDetail", taxTemplateDetailVO );
   }

   @Override
   public int deleteTaxTemplateDetail( final String templateDetailId ) throws KANException
   {
      return delete( "deleteTaxTemplateDetail", templateDetailId );
   }

   @Override
   public List< Object > getTaxTemplateDetailVOsByTemplateHeaderId( String templateHeaderId ) throws KANException
   {
      return selectList( "getTaxTemplateDetailVOsByTemplateHeaderId", templateHeaderId );
   }

}

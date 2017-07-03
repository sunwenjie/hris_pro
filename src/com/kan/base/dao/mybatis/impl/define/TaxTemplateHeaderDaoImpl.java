package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.TaxTemplateHeaderDao;
import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.util.KANException;

public class TaxTemplateHeaderDaoImpl extends Context implements TaxTemplateHeaderDao
{

   @Override
   public int countTaxTemplateHeaderVOsByCondition( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countTaxTemplateHeaderVOsByCondition", taxTemplateHeaderVO );
   }

   @Override
   public List< Object > getTaxTemplateHeaderVOsByCondition( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException
   {
      return selectList( "getTaxTemplateHeaderVOsByCondition", taxTemplateHeaderVO );
   }

   @Override
   public List< Object > getTaxTemplateHeaderVOsByCondition( final TaxTemplateHeaderVO taxTemplateHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTaxTemplateHeaderVOsByCondition", taxTemplateHeaderVO, rowBounds );
   }

   @Override
   public TaxTemplateHeaderVO getTaxTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException
   {
      return ( TaxTemplateHeaderVO ) select( "getTaxTemplateHeaderVOByTemplateHeaderId", templateHeaderId );
   }

   @Override
   public int insertTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException
   {
      return insert( "insertTaxTemplateHeader", taxTemplateHeaderVO );
   }

   @Override
   public int updateTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException
   {
      return update( "updateTaxTemplateHeader", taxTemplateHeaderVO );
   }

   @Override
   public int deleteTaxTemplateHeader( final String templateHeaderId ) throws KANException
   {
      return delete( "deleteTaxTemplateHeader", templateHeaderId );
   }

   @Override
   public List< Object > getTaxTemplateHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getTaxTemplateHeaderVOsByAccountId", accountId );
   }

}

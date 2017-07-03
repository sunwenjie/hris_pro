package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.TaxDao;
import com.kan.base.domain.management.TaxVO;
import com.kan.base.util.KANException;

public class TaxDaoImpl extends Context implements TaxDao
{

   @Override
   public int countTaxVOsByCondition( final TaxVO taxVO ) throws KANException
   {
      return ( Integer ) select( "countTaxVOsByCondition", taxVO );
   }

   @Override
   public List< Object > getTaxVOsByCondition( final TaxVO taxVO ) throws KANException
   {
      return selectList( "getTaxVOsByCondition", taxVO );
   }

   @Override
   public List< Object > getTaxVOsByCondition( final TaxVO taxVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTaxVOsByCondition", taxVO, rowBounds );
   }

   @Override
   public TaxVO getTaxVOByTaxId( final String taxId ) throws KANException
   {
      return ( TaxVO ) select( "getTaxVOByTaxId", taxId );
   }

   @Override
   public int insertTax( final TaxVO taxVO ) throws KANException
   {
      return insert( "insertTax", taxVO );
   }

   @Override
   public int updateTax( final TaxVO taxVO ) throws KANException
   {
      return update( "updateTax", taxVO );
   }

   @Override
   public int deleteTax( final TaxVO taxVO ) throws KANException
   {
      return delete( "deleteTax", taxVO );
   }

   @Override
   public List< Object > getTaxVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getTaxVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getTaxVOsByTaxVO( final TaxVO taxVO ) throws KANException
   {
      return selectList( "getTaxVOsByTaxVO", taxVO );
   }

}

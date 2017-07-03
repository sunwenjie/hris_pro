package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.IncomeTaxRangeHeaderDao;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.util.KANException;

public class IncomeTaxRangeHeaderDaoImpl extends Context implements IncomeTaxRangeHeaderDao
{

   @Override
   public int countIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countIncomeTaxRangeHeaderVOsByCondition", incomeTaxRangeHeaderVO );
   }

   @Override
   public List< Object > getIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return selectList( "getIncomeTaxRangeHeaderVOsByCondition", incomeTaxRangeHeaderVO );
   }

   @Override
   public List< Object > getIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getIncomeTaxRangeHeaderVOsByCondition", incomeTaxRangeHeaderVO, rowBounds );
   }

   @Override
   public IncomeTaxRangeHeaderVO getIncomeTaxRangeHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( IncomeTaxRangeHeaderVO ) select( "getIncomeTaxRangeHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return insert( "insertIncomeTaxRangeHeader", incomeTaxRangeHeaderVO );
   }

   @Override
   public int updateIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return update( "updateIncomeTaxRangeHeader", incomeTaxRangeHeaderVO );
   }

   @Override
   public int deleteIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return delete( "deleteIncomeTaxRangeHeader", incomeTaxRangeHeaderVO );
   }


}

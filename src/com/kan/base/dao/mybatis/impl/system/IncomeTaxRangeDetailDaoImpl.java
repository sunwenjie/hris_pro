package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.IncomeTaxRangeDetailDao;
import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.util.KANException;

public class IncomeTaxRangeDetailDaoImpl extends Context implements IncomeTaxRangeDetailDao
{

   @Override
   public int countIncomeTaxRangeDetailVOsByCondition( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      return ( Integer ) select( "countIncomeTaxRangeDetailVOsByCondition", incomeTaxRangeDetailVO );
   }

   @Override
   public List< Object > getIncomeTaxRangeDetailVOsByCondition( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      return selectList( "getIncomeTaxRangeDetailVOsByCondition", incomeTaxRangeDetailVO );
   }

   @Override
   public List< Object > getIncomeTaxRangeDetailVOsByCondition( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getIncomeTaxRangeDetailVOsByCondition", incomeTaxRangeDetailVO, rowBounds );
   }

   @Override
   public IncomeTaxRangeDetailVO getIncomeTaxRangeDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( IncomeTaxRangeDetailVO ) select( "getIncomeTaxRangeDetailVOByDetailId", detailId );
   }

   @Override
   public int insertIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      return insert( "insertIncomeTaxRangeDetail", incomeTaxRangeDetailVO );
   }

   @Override
   public int updateIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      return update( "updateIncomeTaxRangeDetail", incomeTaxRangeDetailVO );
   }

   @Override
   public int deleteIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      return delete( "deleteIncomeTaxRangeDetail", incomeTaxRangeDetailVO );
   }


}

package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.IncomeTaxBaseDao;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.util.KANException;

public class IncomeTaxBaseDaoImpl extends Context implements IncomeTaxBaseDao
{

   @Override
   public int countIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return ( Integer ) select( "countIncomeTaxBaseVOsByCondition", incomeTaxBaseVO );
   }

   @Override
   public List< Object > getIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return selectList( "getIncomeTaxBaseVOsByCondition", incomeTaxBaseVO );
   }

   @Override
   public List< Object > getIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getIncomeTaxBaseVOsByCondition", incomeTaxBaseVO, rowBounds );
   }

   @Override
   public IncomeTaxBaseVO getIncomeTaxBaseVOByBaseId( final String baseId ) throws KANException
   {
      return ( IncomeTaxBaseVO ) select( "getIncomeTaxBaseVOByBaseId", baseId );
   }

   @Override
   public int insertIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return insert( "insertIncomeTaxBase", incomeTaxBaseVO );
   }

   @Override
   public int updateIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return update( "updateIncomeTaxBase", incomeTaxBaseVO );
   }

   @Override
   public int deleteIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return delete( "deleteIncomeTaxBase", incomeTaxBaseVO );
   }

}

package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.ConstantDao;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.util.KANException;

public class ConstantDaoImpl extends Context implements ConstantDao
{

   @Override
   public int countConstantVOsByCondition( final ConstantVO constantVO ) throws KANException
   {
      return ( Integer ) select( "countConstantVOsByCondition", constantVO );
   }

   @Override
   public List< Object > getConstantVOsByCondition( final ConstantVO constantVO ) throws KANException
   {
      return selectList( "getConstantVOsByCondition", constantVO );
   }

   @Override
   public List< Object > getConstantVOsByCondition( final ConstantVO constantVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getConstantVOsByCondition", constantVO, rowBounds );
   }

   @Override
   public ConstantVO getConstantVOByConstantId( final String constantId ) throws KANException
   {
      return ( ConstantVO ) select( "getConstantVOByConstantId", constantId );
   }

   @Override
   public int insertConstant( final ConstantVO constantVO ) throws KANException
   {
      return insert( "insertConstant", constantVO );
   }

   @Override
   public int updateConstant( final ConstantVO constantVO ) throws KANException
   {
      return update( "updateConstant", constantVO );
   }

   @Override
   public int deleteConstant( final ConstantVO constantVO ) throws KANException
   {
      return delete( "deleteConstant", constantVO );
   }

}

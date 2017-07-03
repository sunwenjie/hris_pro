package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ColumnDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.util.KANException;

public class ColumnDaoImpl extends Context implements ColumnDao
{

   @Override
   public int countColumnVOsByCondition( final ColumnVO columnVO ) throws KANException
   {
      return ( Integer ) select( "countColumnVOsByCondition", columnVO );
   }

   @Override
   public List< Object > getColumnVOsByCondition( final ColumnVO columnVO ) throws KANException
   {
      return selectList( "getColumnVOsByCondition", columnVO );
   }

   @Override
   public List< Object > getColumnVOsByCondition( final ColumnVO columnVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getColumnVOsByCondition", columnVO, rowBounds );
   }

   @Override
   public ColumnVO getColumnVOByColumnId( final String columnId ) throws KANException
   {
      return ( ColumnVO ) select( "getColumnVOByColumnId", columnId );
   }

   @Override
   public int insertColumn( final ColumnVO columnVO ) throws KANException
   {
      return insert( "insertColumn", columnVO );
   }

   @Override
   public List< Object > getAccountColumnVOsByCondition( final ColumnVO columnVO ) throws KANException
   {
      return selectList( "getAccountColumnVOsByCondition", columnVO );
   }

   @Override
   public int updateColumn( final ColumnVO columnVO ) throws KANException
   {
      return update( "updateColumn", columnVO );
   }

   @Override
   public int deleteColumn( final String defColumnId ) throws KANException
   {
      return delete( "deleteColumn", defColumnId );
   }

}

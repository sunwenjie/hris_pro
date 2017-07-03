package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ColumnGroupDao;
import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.util.KANException;

public class ColumnGroupDaoImpl extends Context implements ColumnGroupDao
{

   @Override
   public int countColumnGroupVOsByCondition( final ColumnGroupVO columnGroupVO ) throws KANException
   {
      return ( Integer ) select( "countColumnGroupVOsByCondition", columnGroupVO );
   }

   @Override
   public List< Object > getColumnGroupVOsByCondition( final ColumnGroupVO columnGroupVO ) throws KANException
   {
      return selectList( "getColumnGroupVOsByCondition", columnGroupVO );
   }

   @Override
   public List< Object > getColumnGroupVOsByCondition( final ColumnGroupVO columnGroupVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getColumnGroupVOsByCondition", columnGroupVO, rowBounds );
   }

   @Override
   public ColumnGroupVO getColumnGroupVOByGroupId( final String groupId ) throws KANException
   {
      return ( ColumnGroupVO ) select( "getColumnGroupVOByGroupId", groupId );
   }

   @Override
   public int insertColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException
   {
      return insert( "insertColumnGroup", columnGroupVO );
   }

   @Override
   public int updateColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException
   {
      return update( "updateColumnGroup", columnGroupVO );
   }

   @Override
   public int deleteColumnGroup( final String groupId ) throws KANException
   {
      return delete( "deleteColumnGroup", groupId );
   }

   @Override
   public List< Object > getColumnGroupVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getColumnGroupVOsByAccountId", accountId );
   }

}

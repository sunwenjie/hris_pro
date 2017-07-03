package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.TableDao;
import com.kan.base.domain.define.TableVO;
import com.kan.base.util.KANException;

public class TableDaoImpl extends Context implements TableDao
{

   @Override
   public int countTableVOsByCondition( final TableVO tableVO ) throws KANException
   {
      return ( Integer ) select( "countTableVOsByCondition", tableVO );
   }

   @Override
   public List< Object > getTableVOsByCondition( final TableVO tableVO ) throws KANException
   {
      return selectList( "getTableVOsByCondition", tableVO );
   }

   @Override
   public List< Object > getTableVOsByCondition( final TableVO tableVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTableVOsByCondition", tableVO, rowBounds );
   }

   @Override
   public TableVO getTableVOByTableId( final String tableId ) throws KANException
   {
      return ( TableVO ) select( "getTableVOByTableId", tableId );
   }

   @Override
   public int insertTable( final TableVO tableVO ) throws KANException
   {
      return insert( "insertTable", tableVO );
   }

   @Override
   public int updateTable( final TableVO tableVO ) throws KANException
   {
      return update( "updateTable", tableVO );
   }

   @Override
   public int deleteTable( final String tableId ) throws KANException
   {
      return delete( "deleteTable", tableId );
   }

}

package com.kan.base.dao.mybatis.impl.define;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.TableRelationDao;
import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.util.KANException;

public class TableRelationDaoImpl extends Context implements TableRelationDao
{
   @Override
   public List< Object > getTableVOByTableId( final String tableId ) throws KANException
   {
      return selectList( "getTableRelationVOByTableId", tableId );
   }

   @Override
   public int countTableRelationVOsByCondition( TableRelationVO tableRelationVO ) throws KANException
   {
      return ( Integer ) select( "countTableRelationVOsByCondition", tableRelationVO );
   }

   @Override
   public List< Object > getTableRelationVOsByCondition( TableRelationVO tableRelationVO ) throws KANException
   {
      return selectList( "getTableRelationVOsByCondition", tableRelationVO );
   }

   @Override
   public List< Object > getTableRelationVOsByCondition( TableRelationVO tableRelationVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTableRelationVOsByCondition", tableRelationVO, rowBounds );
   }

   @Override
   public int insertTableRelationVO( TableRelationVO tableRelationVO ) throws KANException
   {
      return insert( "insertTableRelation", tableRelationVO );
   }

   @Override
   public int updateTableRelationVO( TableRelationVO tableRelationVO ) throws KANException
   {
      return update( "updateTableRelation", tableRelationVO );
   }

   @Override
   public int deleteTableRelationVO( String id ) throws KANException
   {
      return delete( "deleteTableRelation", id );
   }

   @Override
   public TableRelationVO getTableRelationVOTableRelationId( String tableRelationId ) throws KANException
   {
      return ( TableRelationVO ) select( "getTableRelationVOByTableRelationId", tableRelationId );
   }
}

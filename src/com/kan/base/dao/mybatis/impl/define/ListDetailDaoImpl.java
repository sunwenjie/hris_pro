package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.util.KANException;

public class ListDetailDaoImpl extends Context implements ListDetailDao
{

   @Override
   public int countListDetailVOsByCondition( final ListDetailVO listDetailVO ) throws KANException
   {
      return ( Integer ) select( "countListDetailVOsByCondition", listDetailVO );
   }

   @Override
   public List< Object > getListDetailVOsByCondition( final ListDetailVO listDetailVO ) throws KANException
   {
      return selectList( "getListDetailVOsByCondition", listDetailVO );
   }

   @Override
   public List< Object > getListDetailVOsByCondition( final ListDetailVO listDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getListDetailVOsByCondition", listDetailVO, rowBounds );
   }

   @Override
   public ListDetailVO getListDetailVOByListDetailId( final String listDetailId ) throws KANException
   {
      return ( ListDetailVO ) select( "getListDetailVOByListDetailId", listDetailId );
   }

   @Override
   public int insertListDetail( final ListDetailVO listDetailVO ) throws KANException
   {
      return insert( "insertListDetail", listDetailVO );
   }

   @Override
   public int updateListDetail( final ListDetailVO listDetailVO ) throws KANException
   {
      return update( "updateListDetail", listDetailVO );
   }

   @Override
   public int deleteListDetail( final String defListDetailId ) throws KANException
   {
      return delete( "deleteListDetail", defListDetailId );
   }

   @Override
   public List< Object > getListDetailVOsByListHeaderId( final String listHeaderId ) throws KANException
   {
      return selectList( "getListDetailVOsByListHeaderId", listHeaderId );
   }

}

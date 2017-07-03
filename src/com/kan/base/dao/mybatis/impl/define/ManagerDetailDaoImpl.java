package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ManagerDetailDao;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.util.KANException;

public class ManagerDetailDaoImpl extends Context implements ManagerDetailDao
{

   @Override
   public int countManagerDetailVOsByCondition( final ManagerDetailVO managerDetailVO ) throws KANException
   {
      return ( Integer ) select( "countManagerDetailVOsByCondition", managerDetailVO );
   }

   @Override
   public List< Object > getManagerDetailVOsByCondition( final ManagerDetailVO managerDetailVO ) throws KANException
   {
      return selectList( "getManagerDetailVOsByCondition", managerDetailVO );
   }

   @Override
   public List< Object > getManagerDetailVOsByCondition( final ManagerDetailVO managerDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getManagerDetailVOsByCondition", managerDetailVO, rowBounds );
   }

   @Override
   public ManagerDetailVO getManagerDetailVOByManagerDetailId( final String managerDetailId ) throws KANException
   {
      return ( ManagerDetailVO ) select( "getManagerDetailVOByManagerDetailId", managerDetailId );
   }

   @Override
   public int insertManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException
   {
      return insert( "insertManagerDetail", managerDetailVO );
   }

   @Override
   public int updateManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException
   {
      return update( "updateManagerDetail", managerDetailVO );
   }

   @Override
   public int deleteManagerDetail( final String managerDetailId ) throws KANException
   {
      return delete( "deleteManagerDetail", managerDetailId );
   }

   @Override
   public List< Object > getManagerDetailVOsByManagerHeaderId( final String managerHeaderId ) throws KANException
   {
      return selectList( "getManagerDetailVOsByManagerHeaderId", managerHeaderId );
   }

}

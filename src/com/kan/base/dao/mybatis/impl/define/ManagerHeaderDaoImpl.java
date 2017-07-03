package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ManagerHeaderDao;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.util.KANException;

public class ManagerHeaderDaoImpl extends Context implements ManagerHeaderDao
{

   @Override
   public int countManagerHeaderVOsByCondition( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countManagerHeaderVOsByCondition", managerHeaderVO );
   }

   @Override
   public List< Object > getManagerHeaderVOsByCondition( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return selectList( "getManagerHeaderVOsByCondition", managerHeaderVO );
   }

   @Override
   public List< Object > getManagerHeaderVOsByCondition( final ManagerHeaderVO managerHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getManagerHeaderVOsByCondition", managerHeaderVO, rowBounds );
   }

   @Override
   public ManagerHeaderVO getManagerHeaderVOByManagerHeaderId( final String managerHeaderId ) throws KANException
   {
      return ( ManagerHeaderVO ) select( "getManagerHeaderVOByManagerHeaderId", managerHeaderId );
   }

   @Override
   public int insertManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return insert( "insertManagerHeader", managerHeaderVO );
   }

   @Override
   public int updateManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return update( "updateManagerHeader", managerHeaderVO );
   }

   @Override
   public int deleteManagerHeader( final String managerHeaderId ) throws KANException
   {
      return delete( "deleteManagerHeader", managerHeaderId );
   }

   @Override
   public List< Object > getManagerHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getManagerHeaderVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getAccountManagerHeaderVOsByCondition( ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return selectList( "getAccountManagerHeaderVOsByCondition", managerHeaderVO );
   }
}

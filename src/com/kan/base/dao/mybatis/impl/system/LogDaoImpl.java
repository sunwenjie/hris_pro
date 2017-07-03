package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.system.LogVO;
import com.kan.base.util.KANException;

public class LogDaoImpl extends Context implements LogDao
{

   @Override
   public int insertLog( LogVO logVO ) throws KANException
   {
      return insert( "insertLog", logVO );
   }

   @Override
   public int countLogVOsByCondition( LogVO logVO ) throws KANException
   {
      return ( Integer ) select( "countLogVOsByCondition", logVO );
   }

   @Override
   public List< Object > getLogVOsByCondition( LogVO logVO ) throws KANException
   {
      return selectList( "getLogVOsByCondition", logVO );
   }

   @Override
   public List< Object > getLogVOsByCondition( LogVO logVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLogVOsByCondition", logVO, rowBounds );
   }

   @Override
   public LogVO getLogVOById( String id ) throws KANException
   {
      return ( LogVO ) select( "getLogVOById", id );
   }

   @Override
   public List< Object > getLogModules() throws KANException
   {
      return selectList( "getLogModules", null );
   }

   @Override
   public LogVO getPreLog( final LogVO logVO ) throws KANException
   {
      List< Object > objects = selectList( "getPreLog", logVO );
      return objects.size() == 0 ? null : ( LogVO ) objects.get( 0 );
   }

}
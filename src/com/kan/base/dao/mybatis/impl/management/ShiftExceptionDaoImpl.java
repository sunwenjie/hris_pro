package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ShiftExceptionDao;
import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.util.KANException;

public class ShiftExceptionDaoImpl extends Context implements ShiftExceptionDao
{

   @Override
   public int countShiftExceptionVOsByCondition( final ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      return ( Integer ) select( "countShiftExceptionVOsByCondition", shiftExceptionVO );
   }

   @Override
   public List< Object > getShiftExceptionVOsByCondition( final ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      return selectList( "getShiftExceptionVOsByCondition", shiftExceptionVO );
   }

   @Override
   public List< Object > getShiftExceptionVOsByCondition( final ShiftExceptionVO shiftExceptionVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getShiftExceptionVOsByCondition", shiftExceptionVO, rowBounds );
   }

   @Override
   public ShiftExceptionVO getShiftExceptionVOByExceptionId( final String exceptionId ) throws KANException
   {
      return ( ShiftExceptionVO ) select( "getShiftExceptionVOByExceptionId", exceptionId );
   }

   @Override
   public int insertShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      return insert( "insertShiftException", shiftExceptionVO );
   }

   @Override
   public int updateShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      return update( "updateShiftException", shiftExceptionVO );
   }

   @Override
   public int deleteShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      return delete( "deleteShiftException", shiftExceptionVO );
   }

   @Override
   public List< Object > getShiftExceptionVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getShiftExceptionVOsByHeaderId", headerId );
   }

}

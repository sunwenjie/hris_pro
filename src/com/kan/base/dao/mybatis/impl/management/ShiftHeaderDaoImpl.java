package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ShiftHeaderDao;
import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.util.KANException;

public class ShiftHeaderDaoImpl extends Context implements ShiftHeaderDao
{

   @Override
   public int countShiftHeaderVOsByCondition( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countShiftHeaderVOsByCondition", shiftHeaderVO );
   }

   @Override
   public List< Object > getShiftHeaderVOsByCondition( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      return selectList( "getShiftHeaderVOsByCondition", shiftHeaderVO );
   }

   @Override
   public List< Object > getShiftHeaderVOsByCondition( final ShiftHeaderVO shiftHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getShiftHeaderVOsByCondition", shiftHeaderVO, rowBounds );
   }

   @Override
   public ShiftHeaderVO getShiftHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ShiftHeaderVO ) select( "getShiftHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      return insert( "insertShiftHeader", shiftHeaderVO );
   }

   @Override
   public int updateShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      return update( "updateShiftHeader", shiftHeaderVO );
   }

   @Override
   public int deleteShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      return delete( "deleteShiftHeader", shiftHeaderVO );
   }

   @Override
   public List< Object > getShiftHeaderVOsByAccountId( final String accoutnId ) throws KANException
   {
      return selectList( "getShiftHeaderVOsByAccountId", accoutnId );
   }

}

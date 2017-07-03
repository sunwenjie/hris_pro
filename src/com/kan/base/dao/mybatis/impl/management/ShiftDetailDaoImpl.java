package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ShiftDetailDao;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.util.KANException;

public class ShiftDetailDaoImpl extends Context implements ShiftDetailDao
{

   @Override
   public int countShiftDetailVOsByCondition( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      return ( Integer ) select( "countShiftDetailVOsByCondition", shiftDetailVO );
   }

   @Override
   public List< Object > getShiftDetailVOsByCondition( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      return selectList( "getShiftDetailVOsByCondition", shiftDetailVO );
   }

   @Override
   public List< Object > getShiftDetailVOsByCondition( final ShiftDetailVO shiftDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getShiftDetailVOsByCondition", shiftDetailVO, rowBounds );
   }

   @Override
   public ShiftDetailVO getShiftDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ShiftDetailVO ) select( "getShiftDetailVOByDetailId", detailId );
   }

   @Override
   public int insertShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      return insert( "insertShiftDetail", shiftDetailVO );
   }

   @Override
   public int updateShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      return update( "updateShiftDetail", shiftDetailVO );
   }

   @Override
   public int deleteShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      return delete( "deleteShiftDetail", shiftDetailVO );
   }

   @Override
   public List< Object > getShiftDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getShiftDetailVOsByHeaderId", headerId );
   }

}

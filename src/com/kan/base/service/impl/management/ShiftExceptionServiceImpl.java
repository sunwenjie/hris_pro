package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ShiftExceptionDao;
import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ShiftExceptionService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ShiftExceptionServiceImpl extends ContextService implements ShiftExceptionService
{

   @Override
   public PagedListHolder getShiftExceptionVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ShiftExceptionDao shiftExceptionDao = ( ShiftExceptionDao ) getDao();
      pagedListHolder.setHolderSize( shiftExceptionDao.countShiftExceptionVOsByCondition( ( ShiftExceptionVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( shiftExceptionDao.getShiftExceptionVOsByCondition( ( ShiftExceptionVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( shiftExceptionDao.getShiftExceptionVOsByCondition( ( ShiftExceptionVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ShiftExceptionVO getShiftExceptionVOByExceptionId( String exceptionId ) throws KANException
   {
      return ( ( ShiftExceptionDao ) getDao() ).getShiftExceptionVOByExceptionId( exceptionId );
   }

   @Override
   public int insertShiftException( ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      if ( shiftExceptionVO.getShiftPeriodArray() != null && shiftExceptionVO.getShiftPeriodArray().length > 0 )
      {
         shiftExceptionVO.setExceptionPeriod( KANUtil.toJasonArray( shiftExceptionVO.getShiftPeriodArray(), "," ) );
      }

      return ( ( ShiftExceptionDao ) getDao() ).insertShiftException( shiftExceptionVO );
   }

   @Override
   public int updateShiftException( ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      if ( shiftExceptionVO.getShiftPeriodArray() != null && shiftExceptionVO.getShiftPeriodArray().length > 0 )
      {
         shiftExceptionVO.setExceptionPeriod( KANUtil.toJasonArray( shiftExceptionVO.getShiftPeriodArray(), "," ) );
      }

      return ( ( ShiftExceptionDao ) getDao() ).updateShiftException( shiftExceptionVO );
   }

   @Override
   public int deleteShiftExceptionl( ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      shiftExceptionVO.setDeleted( ShiftExceptionVO.FALSE );
      return ( ( ShiftExceptionDao ) getDao() ).updateShiftException( shiftExceptionVO );
   }

   @Override
   public List< Object > getAvailableShiftExceptions( ShiftExceptionVO shiftExceptionVO ) throws KANException
   {
      shiftExceptionVO.setStatus( ShiftExceptionVO.TRUE );
      return ( ( ShiftExceptionDao ) getDao() ).getShiftExceptionVOsByCondition( shiftExceptionVO );
   }

}

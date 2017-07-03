package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ShiftDetailDao;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ShiftDetailService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ShiftDetailServiceImpl extends ContextService implements ShiftDetailService
{

   @Override
   public PagedListHolder getShiftDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ShiftDetailDao shiftDetailDao = ( ShiftDetailDao ) getDao();
      pagedListHolder.setHolderSize( shiftDetailDao.countShiftDetailVOsByCondition( ( ShiftDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( shiftDetailDao.getShiftDetailVOsByCondition( ( ShiftDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( shiftDetailDao.getShiftDetailVOsByCondition( ( ShiftDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ShiftDetailVO getShiftDetailVOByDetailId( final String headerId ) throws KANException
   {
      return ( ( ShiftDetailDao ) getDao() ).getShiftDetailVOByDetailId( headerId );
   }

   @Override
   public int insertShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      if ( shiftDetailVO.getShiftPeriodArray() != null && shiftDetailVO.getShiftPeriodArray().length > 0 )
      {
         shiftDetailVO.setShiftPeriod( KANUtil.toJasonArray( shiftDetailVO.getShiftPeriodArray(), "," ) );
      }

      return ( ( ShiftDetailDao ) getDao() ).insertShiftDetail( shiftDetailVO );
   }

   @Override
   public int updateShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      if ( shiftDetailVO.getShiftPeriodArray() != null && shiftDetailVO.getShiftPeriodArray().length > 0 )
      {
         shiftDetailVO.setShiftPeriod( KANUtil.toJasonArray( shiftDetailVO.getShiftPeriodArray(), "," ) );
      }

      return ( ( ShiftDetailDao ) getDao() ).updateShiftDetail( shiftDetailVO );
   }

   @Override
   public int deleteShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      shiftDetailVO.setDeleted( ShiftDetailVO.FALSE );
      return ( ( ShiftDetailDao ) getDao() ).updateShiftDetail( shiftDetailVO );
   }

   @Override
   public List< Object > getAvailableShiftDetailVOs( final ShiftDetailVO shiftDetailVO ) throws KANException
   {
      shiftDetailVO.setStatus( ShiftDetailVO.TRUE );
      return ( ( ShiftDetailDao ) getDao() ).getShiftDetailVOsByCondition( shiftDetailVO );
   }

}

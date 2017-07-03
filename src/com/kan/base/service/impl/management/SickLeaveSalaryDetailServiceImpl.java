package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.SickLeaveSalaryDetailDao;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SickLeaveSalaryDetailService;
import com.kan.base.util.KANException;

public class SickLeaveSalaryDetailServiceImpl extends ContextService implements SickLeaveSalaryDetailService
{

   @Override
   public PagedListHolder getSickLeaveSalaryDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SickLeaveSalaryDetailDao sickLeaveSalaryDetailDao = ( SickLeaveSalaryDetailDao ) getDao();
      pagedListHolder.setHolderSize( sickLeaveSalaryDetailDao.countSickLeaveSalaryDetailVOsByCondition( ( SickLeaveSalaryDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( sickLeaveSalaryDetailDao.getSickLeaveSalaryDetailVOsByCondition( ( SickLeaveSalaryDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sickLeaveSalaryDetailDao.getSickLeaveSalaryDetailVOsByCondition( ( SickLeaveSalaryDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }
   
   @Override
   public SickLeaveSalaryDetailVO getSickLeaveSalaryDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( SickLeaveSalaryDetailDao ) getDao() ).getSickLeaveSalaryDetailVOByDetailId( detailId );
   }

   @Override
   public int insertSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      return ( ( SickLeaveSalaryDetailDao ) getDao() ).insertSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );
   }

   @Override
   public int updateSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      return ( ( SickLeaveSalaryDetailDao ) getDao() ).updateSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );
   }

   @Override
   public int deleteSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      sickLeaveSalaryDetailVO.setDeleted( ShiftDetailVO.FALSE );
      return ( ( SickLeaveSalaryDetailDao ) getDao() ).updateSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );
   }

   @Override
   public List< Object > getAvailableSickLeaveSalaryDetailVOs( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      sickLeaveSalaryDetailVO.setStatus( SickLeaveSalaryDetailVO.TRUE );
      return ( ( SickLeaveSalaryDetailDao ) getDao() ).getSickLeaveSalaryDetailVOsByCondition( sickLeaveSalaryDetailVO );
   }

   @Override
   public List< Object > getSickLeaveSalaryDetailByHeaderId( String headerId ) throws KANException
   {
      return ( ( SickLeaveSalaryDetailDao ) getDao() ).getSickLeaveSalaryDetailVOsByHeaderId( headerId );
   }

}

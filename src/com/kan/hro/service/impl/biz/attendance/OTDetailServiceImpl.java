package com.kan.hro.service.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.OTDetailDao;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.service.inf.biz.attendance.OTDetailService;

public class OTDetailServiceImpl extends ContextService implements OTDetailService
{

   @Override
   public PagedListHolder getOTDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OTDetailDao otDetailDao = ( OTDetailDao ) getDao();
      pagedListHolder.setHolderSize( otDetailDao.countOTDetailVOsByCondition( ( OTDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( otDetailDao.getOTDetailVOsByCondition( ( OTDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( otDetailDao.getOTDetailVOsByCondition( ( OTDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public OTDetailVO getOTDetailVOByOTDetailId( final String otDetailId ) throws KANException
   {
      return ( ( OTDetailDao ) getDao() ).getOTDetailVOByOTDetailId( otDetailId );
   }

   @Override
   public int insertOTDetail( final OTDetailVO otDetailVO ) throws KANException
   {
      return ( ( OTDetailDao ) getDao() ).insertOTDetail( otDetailVO );
   }

   @Override
   public int updateOTDetail( final OTDetailVO otDetailVO ) throws KANException
   {
      return ( ( OTDetailDao ) getDao() ).updateOTDetail( otDetailVO );
   }

   @Override
   public int deleteOTDetail( final OTDetailVO otDetailVO ) throws KANException
   {
      return ( ( OTDetailDao ) getDao() ).deleteOTDetail( otDetailVO );
   }

   @Override
   public List< Object > getOTDetailVOsByOTHeaderId( final String otHeaderId ) throws KANException
   {
      return ( ( OTDetailDao ) getDao() ).getOTDetailVOsByOTHeaderId( otHeaderId );
   }

   @Override
   public List< Object > getOTDetailVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( OTDetailDao ) getDao() ).getOTDetailVOsByContractId( contractId );
   }

}

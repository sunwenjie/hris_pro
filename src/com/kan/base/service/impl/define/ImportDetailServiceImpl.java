package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ImportDetailDao;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ImportDetailService;
import com.kan.base.util.KANException;

public class ImportDetailServiceImpl extends ContextService implements ImportDetailService
{

   @Override
   public PagedListHolder getImportDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ImportDetailDao importDetailDao = ( ImportDetailDao ) getDao();
      pagedListHolder.setHolderSize( importDetailDao.countImportDetailVOsByCondition( ( ImportDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( importDetailDao.getImportDetailVOsByCondition( ( ImportDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( importDetailDao.getImportDetailVOsByCondition( ( ImportDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ImportDetailVO getImportDetailVOByImportDetailId( final String importDetailId ) throws KANException
   {
      return ( ( ImportDetailDao ) getDao() ).getImportDetailVOByImportDetailId( importDetailId );
   }

   @Override
   public int insertImportDetail( final ImportDetailVO importDetailVO ) throws KANException
   {
      return ( ( ImportDetailDao ) getDao() ).insertImportDetail( importDetailVO );
   }

   @Override
   public int updateImportDetail( final ImportDetailVO importDetailVO ) throws KANException
   {
      return ( ( ImportDetailDao ) getDao() ).updateImportDetail( importDetailVO );
   }

   @Override
   public int deleteImportDetail( final ImportDetailVO importDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      importDetailVO.setDeleted( ImportDetailVO.FALSE );
      return ( ( ImportDetailDao ) getDao() ).updateImportDetail( importDetailVO );
   }

   @Override
   public List< Object > getImportDetailVOsByImportHeaderId( final String importHeaderId ) throws KANException
   {
      return ( ( ImportDetailDao ) getDao() ).getImportDetailVOsByImportHeaderId( importHeaderId );
   }

   @Override
   public int deleteImportDetail( String importHeaderId ) throws KANException
   {
      final ImportDetailDao importDetailDao = ( ImportDetailDao ) getDao();
      final List< Object > importDetailVOs = importDetailDao.getImportDetailVOsByImportHeaderId( importHeaderId );
      ImportDetailVO importDetailVO = null;
      for ( Object importDetailObject : importDetailVOs )
      {
         importDetailVO = ( ImportDetailVO ) importDetailObject;
         importDetailVO.setDeleted( ImportDetailVO.FALSE );
         importDetailDao.updateImportDetail( importDetailVO );
      }
      return 0;
   }

}

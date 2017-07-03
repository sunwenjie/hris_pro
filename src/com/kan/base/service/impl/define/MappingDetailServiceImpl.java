package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.MappingDetailDao;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.MappingDetailService;
import com.kan.base.util.KANException;

public class MappingDetailServiceImpl extends ContextService implements MappingDetailService
{

   @Override
   public PagedListHolder getMappingDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final MappingDetailDao mappingDetailDao = ( MappingDetailDao ) getDao();
      pagedListHolder.setHolderSize( mappingDetailDao.countMappingDetailVOsByCondition( ( MappingDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( mappingDetailDao.getMappingDetailVOsByCondition( ( MappingDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( mappingDetailDao.getMappingDetailVOsByCondition( ( MappingDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public MappingDetailVO getMappingDetailVOByMappingDetailId( final String mappingDetailId ) throws KANException
   {
      return ( ( MappingDetailDao ) getDao() ).getMappingDetailVOByMappingDetailId( mappingDetailId );
   }

   @Override
   public int insertMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException
   {
      return ( ( MappingDetailDao ) getDao() ).insertMappingDetail( mappingDetailVO );
   }

   @Override
   public int updateMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException
   {
      return ( ( MappingDetailDao ) getDao() ).updateMappingDetail( mappingDetailVO );
   }

   @Override
   public int deleteMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      mappingDetailVO.setDeleted( MappingDetailVO.FALSE );
      return ( ( MappingDetailDao ) getDao() ).updateMappingDetail( mappingDetailVO );
   }

   @Override
   public List< Object > getMappingDetailVOsByMappingHeaderId( final String mappingHeaderId ) throws KANException
   {
      return ( ( MappingDetailDao ) getDao() ).getMappingDetailVOsByMappingHeaderId( mappingHeaderId );
   }

   @Override
   public int deleteMappingDetail( String mappingHeaderId ) throws KANException
   {
      final MappingDetailDao mappingDetailDao = ( MappingDetailDao ) getDao();
      final List< Object > mappingDetailVOs = mappingDetailDao.getMappingDetailVOsByMappingHeaderId( mappingHeaderId );
      MappingDetailVO mappingDetailVO = null;
      for ( Object mappingDetailObject : mappingDetailVOs )
      {
         mappingDetailVO = ( MappingDetailVO ) mappingDetailObject;
         mappingDetailVO.setDeleted( ImportDetailVO.FALSE );
         mappingDetailDao.updateMappingDetail( mappingDetailVO );
      }
      return 0;
   }

}

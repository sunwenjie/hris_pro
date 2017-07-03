package com.kan.base.service.impl.common;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANException;

public class CommonBatchServiceImpl extends ContextService implements CommonBatchService
{

   @Override
   public PagedListHolder getCommonBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final CommonBatchDao commonBatchDao = ( CommonBatchDao ) getDao();
      pagedListHolder.setHolderSize( commonBatchDao.countCommonBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( commonBatchDao.getCommonBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( commonBatchDao.getCommonBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public CommonBatchVO getCommonBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( ( CommonBatchDao ) getDao() ).getCommonBatchVOByBatchId( batchId );
   }

   @Override
   public int updateCommonBatch( CommonBatchVO commonBatchVO ) throws KANException
   {
      return ( ( CommonBatchDao ) getDao() ).updateCommonBatch( commonBatchVO );
   }

   @Override
   public int insertCommonBatch( CommonBatchVO commonBatchVO ) throws KANException
   {
      return ( ( CommonBatchDao ) getDao() ).insertCommonBatch( commonBatchVO );
   }

}

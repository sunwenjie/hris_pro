package com.kan.hro.service.impl.biz.payment;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.CommonBatchDao;
import com.kan.hro.domain.biz.payment.CommonBatchVO;
import com.kan.hro.service.inf.biz.payment.CommonBatchService;

public class CommonBatchServiceImpl extends ContextService implements CommonBatchService
{

   @Override
   public PagedListHolder getSalaryBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final CommonBatchDao commonBatchDao = ( CommonBatchDao ) getDao();
      pagedListHolder.setHolderSize( commonBatchDao.countSalaryBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( commonBatchDao.getSalaryBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( commonBatchDao.getSalaryBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject() ) );
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

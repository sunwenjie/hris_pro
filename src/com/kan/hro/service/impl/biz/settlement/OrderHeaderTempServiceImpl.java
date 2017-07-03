package com.kan.hro.service.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderTempDao;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderTempService;

public class OrderHeaderTempServiceImpl extends ContextService implements OrderHeaderTempService
{

   @Override
   public PagedListHolder getOrderHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OrderHeaderTempDao orderHeaderTempDao = ( OrderHeaderTempDao ) getDao();
      pagedListHolder.setHolderSize( orderHeaderTempDao.countOrderHeaderTempVOsByCondition( ( OrderHeaderTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( orderHeaderTempDao.getOrderHeaderTempVOsByCondition( ( OrderHeaderTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( orderHeaderTempDao.getOrderHeaderTempVOsByCondition( ( OrderHeaderTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public OrderHeaderTempVO getOrderHeaderTempVOByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ( OrderHeaderTempDao ) getDao() ).getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );
   }

   @Override
   public int updateOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException
   {
      return ( ( OrderHeaderTempDao ) getDao() ).updateOrderHeaderTemp( orderHeaderTempVO );
   }

   @Override
   public int insertOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException
   {
      return ( ( OrderHeaderTempDao ) getDao() ).insertOrderHeaderTemp( orderHeaderTempVO );
   }

   @Override
   public int deleteOrderHeaderTemp( final String orderHeaderTempId ) throws KANException
   {
      return ( ( OrderHeaderTempDao ) getDao() ).deleteOrderHeaderTemp( orderHeaderTempId );
   }

   @Override
   public List< Object > getOrderHeaderTempVOsByBatchId( final String batchId ) throws KANException
   {
      return ( ( OrderHeaderTempDao ) getDao() ).getOrderHeaderTempVOsByBatchId( batchId );
   }

}

package com.kan.hro.service.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderDao;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderService;

public class OrderHeaderServiceImpl extends ContextService implements OrderHeaderService
{

   @Override
   public PagedListHolder getOrderHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OrderHeaderDao orderHeaderDao = ( OrderHeaderDao ) getDao();
      pagedListHolder.setHolderSize( orderHeaderDao.countOrderHeaderVOsByCondition( ( OrderHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( orderHeaderDao.getOrderHeaderVOsByCondition( ( OrderHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( orderHeaderDao.getOrderHeaderVOsByCondition( ( OrderHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public OrderHeaderVO getOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ( OrderHeaderDao ) getDao() ).getOrderHeaderVOByOrderHeaderId( orderHeaderId );
   }

   @Override
   public int updateOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException
   {
      return ( ( OrderHeaderDao ) getDao() ).updateOrderHeader( orderHeaderVO );
   }

   @Override
   public int insertOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException
   {
      return ( ( OrderHeaderDao ) getDao() ).insertOrderHeader( orderHeaderVO );
   }

   @Override
   public int deleteOrderHeader( final String orderId ) throws KANException
   {
      return ( ( OrderHeaderDao ) getDao() ).deleteOrderHeader( orderId );
   }

   @Override
   public List< Object > getOrderHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return ( ( OrderHeaderDao ) getDao() ).getOrderHeaderVOsByBatchId( batchId );
   }

}

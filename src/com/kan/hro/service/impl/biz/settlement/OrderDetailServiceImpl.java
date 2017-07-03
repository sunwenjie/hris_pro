package com.kan.hro.service.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.service.inf.biz.settlement.OrderDetailService;

public class OrderDetailServiceImpl extends ContextService implements OrderDetailService
{

   @Override
   public PagedListHolder getOrderDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OrderDetailDao orderDetailDao = ( OrderDetailDao ) getDao();
      pagedListHolder.setHolderSize( orderDetailDao.countOrderDetailVOsByCondition( ( OrderDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( orderDetailDao.getOrderDetailVOsByCondition( ( OrderDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( orderDetailDao.getOrderDetailVOsByCondition( ( OrderDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public OrderDetailVO getOrderDetailVOByOrderDetailId( final String orderDetailId ) throws KANException
   {
      return ( ( OrderDetailDao ) getDao() ).getOrderDetailVOByOrderDetailId( orderDetailId );
   }

   @Override
   public int updateOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException
   {
      return ( ( OrderDetailDao ) getDao() ).updateOrderDetail( orderDetailVO );
   }

   @Override
   public int insertOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException
   {
      return ( ( OrderDetailDao ) getDao() ).insertOrderDetail( orderDetailVO );
   }

   @Override
   public int deleteOrderDetail( final String orderDetailId ) throws KANException
   {
      return ( ( OrderDetailDao ) getDao() ).deleteOrderDetail( orderDetailId );
   }

   @Override
   public List< Object > getOrderDetailVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( OrderDetailDao ) getDao() ).getOrderDetailVOsByContractId( contractId );
   }

}

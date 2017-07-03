package com.kan.hro.service.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailTempDao;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;
import com.kan.hro.service.inf.biz.settlement.OrderDetailTempService;

public class OrderDetailTempServiceImpl extends ContextService implements OrderDetailTempService
{

   @Override
   public PagedListHolder getOrderDetailTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OrderDetailTempDao orderDetailTempDao = ( OrderDetailTempDao ) getDao();
      pagedListHolder.setHolderSize( orderDetailTempDao.countOrderDetailTempVOsByCondition( ( OrderDetailTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( orderDetailTempDao.getOrderDetailTempVOsByCondition( ( OrderDetailTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( orderDetailTempDao.getOrderDetailTempVOsByCondition( ( OrderDetailTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public OrderDetailTempVO getOrderDetailTempVOByOrderDetailId( final String orderDetailId ) throws KANException
   {
      return ( ( OrderDetailTempDao ) getDao() ).getOrderDetailTempVOByOrderDetailId( orderDetailId );
   }

   @Override
   public int updateOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException
   {
      return ( ( OrderDetailTempDao ) getDao() ).updateOrderDetailTemp( orderDetailTempVO );
   }

   @Override
   public int insertOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException
   {
      return ( ( OrderDetailTempDao ) getDao() ).insertOrderDetailTemp( orderDetailTempVO );
   }

   @Override
   public int deleteOrderDetailTemp( final String orderDetailTempId ) throws KANException
   {
      return ( ( OrderDetailTempDao ) getDao() ).deleteOrderDetailTemp( orderDetailTempId );
   }

   @Override
   public List< Object > getOrderDetailTempVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( OrderDetailTempDao ) getDao() ).getOrderDetailTempVOsByContractId( contractId );
   }

}

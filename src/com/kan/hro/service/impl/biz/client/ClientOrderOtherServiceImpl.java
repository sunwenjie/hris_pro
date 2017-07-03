package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderOtherDao;
import com.kan.hro.domain.biz.client.ClientOrderOtherVO;
import com.kan.hro.service.inf.biz.client.ClientOrderOtherService;

public class ClientOrderOtherServiceImpl extends ContextService implements ClientOrderOtherService
{

   @Override
   public PagedListHolder getClientOrderOtherVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ClientOrderOtherDao clientOrderOtherDao = ( ClientOrderOtherDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderOtherDao.countClientOrderOtherVOsByCondition( ( ClientOrderOtherVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderOtherDao.getClientOrderOtherVOsByCondition( ( ClientOrderOtherVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderOtherDao.getClientOrderOtherVOsByCondition( ( ClientOrderOtherVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderOtherVO getClientOrderOtherVOByClientOrderOtherId( String clientOrderOtherId ) throws KANException
   {
      return ( ( ClientOrderOtherDao ) getDao() ).getClientOrderOtherVOByClientOrderOtherId( clientOrderOtherId );
   }

   @Override
   public int updateClientOrderOther( ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      return ( ( ClientOrderOtherDao ) getDao() ).updateClientOrderOther( clientOrderOtherVO );
   }

   @Override
   public int insertClientOrderOther( ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      return ( ( ClientOrderOtherDao ) getDao() ).insertClientOrderOther( clientOrderOtherVO );
   }

   @Override
   public int deleteClientOrderOther( ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýClientOrderOtherVO
      clientOrderOtherVO.setDeleted( ClientOrderOtherVO.FALSE );
      return ( ( ClientOrderOtherDao ) getDao() ).updateClientOrderOther( clientOrderOtherVO );
   }

   @Override
   public List< Object > getClientOrderOtherVOsByOrderHeaderId( String orderHeaderId ) throws KANException
   {
      return ( ( ClientOrderOtherDao ) getDao() ).getClientOrderOtherVOsByOrderHeaderId( orderHeaderId );
   }

}

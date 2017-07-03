package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderRuleDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderRuleVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderRuleService;

public class ClientOrderHeaderRuleServiceImpl extends ContextService implements ClientOrderHeaderRuleService
{

   @Override
   public PagedListHolder getClientOrderHeaderRuleVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ClientOrderHeaderRuleDao clientOrderHeaderRuleDao = ( ClientOrderHeaderRuleDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderHeaderRuleDao.countClientOrderHeaderRuleVOsByCondition( ( ClientOrderHeaderRuleVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderHeaderRuleDao.getClientOrderHeaderRuleVOsByCondition( ( ClientOrderHeaderRuleVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderHeaderRuleDao.getClientOrderHeaderRuleVOsByCondition( ( ClientOrderHeaderRuleVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderHeaderRuleVO getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( String clientOrderHeaderRuleId ) throws KANException
   {
      return ( ( ClientOrderHeaderRuleDao ) getDao() ).getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( clientOrderHeaderRuleId );
   }

   @Override
   public int updateClientOrderHeaderRule( ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      return ( ( ClientOrderHeaderRuleDao ) getDao() ).updateClientOrderHeaderRule( clientOrderHeaderRuleVO );
   }

   @Override
   public int insertClientOrderHeaderRule( ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      return ( ( ClientOrderHeaderRuleDao ) getDao() ).insertClientOrderHeaderRule( clientOrderHeaderRuleVO );
   }

   @Override
   public int deleteClientOrderHeaderRule( ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientOrderHeaderRuleVO
      clientOrderHeaderRuleVO.setDeleted( ClientOrderHeaderRuleVO.FALSE );
      return ( ( ClientOrderHeaderRuleDao ) getDao() ).updateClientOrderHeaderRule( clientOrderHeaderRuleVO );
   }

   @Override
   public List< Object > getClientOrderHeaderRuleVOsByClientOrderHeaderId( String clientOrderHeaderId ) throws KANException
   {
      return ( ( ClientOrderHeaderRuleDao ) getDao() ).getClientOrderHeaderRuleVOsByClientOrderHeaderId( clientOrderHeaderId );
   }

}

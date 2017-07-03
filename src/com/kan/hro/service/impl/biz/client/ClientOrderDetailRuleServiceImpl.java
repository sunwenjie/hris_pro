package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailRuleDao;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailRuleService;

public class ClientOrderDetailRuleServiceImpl extends ContextService implements ClientOrderDetailRuleService
{

   @Override
   public PagedListHolder getClientOrderDetailRuleVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ClientOrderDetailRuleDao clientOrderDetailRuleDao = ( ClientOrderDetailRuleDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderDetailRuleDao.countClientOrderDetailRuleVOsByCondition( ( ClientOrderDetailRuleVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderDetailRuleDao.getClientOrderDetailRuleVOsByCondition( ( ClientOrderDetailRuleVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderDetailRuleDao.getClientOrderDetailRuleVOsByCondition( ( ClientOrderDetailRuleVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderDetailRuleVO getClientOrderDetailRuleVOByClientOrderDetailRuleId( String clientOrderDetailRuleId ) throws KANException
   {
      return ( ( ClientOrderDetailRuleDao ) getDao() ).getClientOrderDetailRuleVOByClientOrderDetailRuleId( clientOrderDetailRuleId );
   }

   @Override
   public int updateClientOrderDetailRule( ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      return ( ( ClientOrderDetailRuleDao ) getDao() ).updateClientOrderDetailRule( clientOrderDetailRuleVO );
   }

   @Override
   public int insertClientOrderDetailRule( ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      return ( ( ClientOrderDetailRuleDao ) getDao() ).insertClientOrderDetailRule( clientOrderDetailRuleVO );
   }

   @Override
   public int deleteClientOrderDetailRule( ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientOrderDetailRuleVO
      clientOrderDetailRuleVO.setDeleted( ClientOrderDetailRuleVO.FALSE );
      return ( ( ClientOrderDetailRuleDao ) getDao() ).updateClientOrderDetailRule( clientOrderDetailRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailRuleVOsByClientOrderDetailId( String clientOrderDetailId ) throws KANException
   {
      return ( ( ClientOrderDetailRuleDao ) getDao() ).getClientOrderDetailRuleVOsByClientOrderDetailId( clientOrderDetailId );
   }

}

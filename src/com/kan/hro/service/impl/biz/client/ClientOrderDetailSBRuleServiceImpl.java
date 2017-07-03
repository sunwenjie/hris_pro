package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailSBRuleDao;
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailSBRuleService;

public class ClientOrderDetailSBRuleServiceImpl extends ContextService implements ClientOrderDetailSBRuleService
{

   @Override
   public PagedListHolder getClientOrderDetailSBRuleVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ClientOrderDetailSBRuleDao clientOrderDetailSBRuleDao = ( ClientOrderDetailSBRuleDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderDetailSBRuleDao.countClientOrderDetailSBRuleVOsByCondition( ( ClientOrderDetailSBRuleVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderDetailSBRuleDao.getClientOrderDetailSBRuleVOsByCondition( ( ClientOrderDetailSBRuleVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderDetailSBRuleDao.getClientOrderDetailSBRuleVOsByCondition( ( ClientOrderDetailSBRuleVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderDetailSBRuleVO getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( String clientOrderDetailSBRuleId ) throws KANException
   {
      return ( ( ClientOrderDetailSBRuleDao ) getDao() ).getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( clientOrderDetailSBRuleId );
   }

   @Override
   public int updateClientOrderDetailSBRule( ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      return ( ( ClientOrderDetailSBRuleDao ) getDao() ).updateClientOrderDetailSBRule( clientOrderDetailSBRuleVO );
   }

   @Override
   public int insertClientOrderDetailSBRule( ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      return ( ( ClientOrderDetailSBRuleDao ) getDao() ).insertClientOrderDetailSBRule( clientOrderDetailSBRuleVO );
   }

   @Override
   public int deleteClientOrderDetailSBRule( ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientOrderDetailSBRuleVO
      clientOrderDetailSBRuleVO.setDeleted( ClientOrderDetailSBRuleVO.FALSE );
      return ( ( ClientOrderDetailSBRuleDao ) getDao() ).updateClientOrderDetailSBRule( clientOrderDetailSBRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailSBRuleVOsByClientOrderDetailId( String clientOrderDetailId ) throws KANException
   {
      return ( ( ClientOrderDetailSBRuleDao ) getDao() ).getClientOrderDetailSBRuleVOsByClientOrderDetailId( clientOrderDetailId );
   }

}

package com.kan.hro.service.impl.biz.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailRuleDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailSBRuleDao;
import com.kan.hro.domain.biz.client.ClientOrderDetailDTO;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;

public class ClientOrderDetailServiceImpl extends ContextService implements ClientOrderDetailService
{
   // 注入ClientOrderDetailRuleDao
   private ClientOrderDetailRuleDao clientOrderDetailRuleDao;

   // 注入ClientOrderDetailSBRuleDao
   private ClientOrderDetailSBRuleDao clientOrderDetailSBRuleDao;

   public ClientOrderDetailRuleDao getClientOrderDetailRuleDao()
   {
      return clientOrderDetailRuleDao;
   }

   public void setClientOrderDetailRuleDao( ClientOrderDetailRuleDao clientOrderDetailRuleDao )
   {
      this.clientOrderDetailRuleDao = clientOrderDetailRuleDao;
   }

   public final ClientOrderDetailSBRuleDao getClientOrderDetailSBRuleDao()
   {
      return clientOrderDetailSBRuleDao;
   }

   public final void setClientOrderDetailSBRuleDao( ClientOrderDetailSBRuleDao clientOrderDetailSBRuleDao )
   {
      this.clientOrderDetailSBRuleDao = clientOrderDetailSBRuleDao;
   }

   @Override
   public PagedListHolder getClientOrderDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientOrderDetailDao clientOrderDetailDao = ( ClientOrderDetailDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderDetailDao.countClientOrderDetailVOsByCondition( ( ClientOrderDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderDetailDao.getClientOrderDetailVOsByCondition( ( ClientOrderDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderDetailDao.getClientOrderDetailVOsByCondition( ( ClientOrderDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderDetailVO getClientOrderDetailVOByClientOrderDetailId( final String clientOrderDetailId ) throws KANException
   {
      return ( ( ClientOrderDetailDao ) getDao() ).getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );
   }

   @Override
   public int updateClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      return ( ( ClientOrderDetailDao ) getDao() ).updateClientOrderDetail( clientOrderDetailVO );
   }

   @Override
   public int insertClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      return ( ( ClientOrderDetailDao ) getDao() ).insertClientOrderDetail( clientOrderDetailVO );
   }

   @Override
   public int deleteClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      // 标记删除clientOrderDetailVO
      clientOrderDetailVO.setDeleted( ClientOrderDetailVO.FALSE );
      return ( ( ClientOrderDetailDao ) getDao() ).updateClientOrderDetail( clientOrderDetailVO );
   }

   @Override
   public List< Object > getClientOrderDetailVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return ( ( ClientOrderDetailDao ) getDao() ).getClientOrderDetailVOsByClientOrderHeaderId( clientOrderHeaderId );
   }

   @Override
   public List< ClientOrderDetailDTO > getClientOrderDetailDTOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      // 初始化ClientOrderDetailDTO List
      final List< ClientOrderDetailDTO > clientOrderDetailDTOs = new ArrayList< ClientOrderDetailDTO >();
      // 初始化ClientOrderDetailVO List
      final List< Object > clientOrderDetailVOs = getClientOrderDetailVOsByClientOrderHeaderId( clientOrderHeaderId );

      // 遍历并逐个创建ClientOrderDetailDTO对象
      if ( clientOrderDetailVOs != null && clientOrderDetailVOs.size() > 0 )
      {
         for ( Object clientOrderDetailVOObject : clientOrderDetailVOs )
         {
            final ClientOrderDetailDTO clientOrderDetailDTO = new ClientOrderDetailDTO();
            final ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) clientOrderDetailVOObject;

            // 装载ClientOrderDetailVO
            clientOrderDetailDTO.setClientOrderDetailVO( clientOrderDetailVO );

            // 装载ClientOrderDetailRuleVO列表
            final List< Object > clientOrderDetailRuleVOs = this.getClientOrderDetailRuleDao().getClientOrderDetailRuleVOsByClientOrderDetailId( clientOrderDetailVO.getOrderDetailId() );

            if ( clientOrderDetailRuleVOs != null && clientOrderDetailRuleVOs.size() > 0 )
            {
               for ( Object clientOrderDetailRuleVOObject : clientOrderDetailRuleVOs )
               {
                  clientOrderDetailDTO.getClientOrderDetailRuleVOs().add( ( ClientOrderDetailRuleVO ) clientOrderDetailRuleVOObject );
               }
            }

            // 装载ClientOrderDetailSBRuleVO列表
            final List< Object > clientOrderDetailSBRuleVOs = this.getClientOrderDetailSBRuleDao().getClientOrderDetailSBRuleVOsByClientOrderDetailId( clientOrderDetailVO.getOrderDetailId() );

            if ( clientOrderDetailSBRuleVOs != null && clientOrderDetailSBRuleVOs.size() > 0 )
            {
               for ( Object clientOrderDetailSBRuleVOObject : clientOrderDetailSBRuleVOs )
               {
                  clientOrderDetailDTO.getClientOrderDetailSBRuleVOs().add( ( ClientOrderDetailSBRuleVO ) clientOrderDetailSBRuleVOObject );
               }
            }

            clientOrderDetailDTOs.add( clientOrderDetailDTO );
         }
      }

      return clientOrderDetailDTOs;
   }

}

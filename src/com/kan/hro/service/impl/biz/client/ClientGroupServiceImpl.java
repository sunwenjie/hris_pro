package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientDao;
import com.kan.hro.dao.inf.biz.client.ClientGroupDao;
import com.kan.hro.domain.biz.client.ClientBaseView;
import com.kan.hro.domain.biz.client.ClientGroupDTO;
import com.kan.hro.domain.biz.client.ClientGroupVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientGroupService;

public class ClientGroupServiceImpl extends ContextService implements ClientGroupService
{
   // 注入ClientDao
   private ClientDao clientDao;

   public ClientDao getClientDao()
   {
      return clientDao;
   }

   public void setClientDao( ClientDao clientDao )
   {
      this.clientDao = clientDao;
   }

   @Override
   public PagedListHolder getClientGroupVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ClientGroupDao clientGroupDao = ( ClientGroupDao ) getDao();
      pagedListHolder.setHolderSize( clientGroupDao.countClientGroupVOsByCondition( ( ClientGroupVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientGroupDao.getClientGroupVOsByCondition( ( ClientGroupVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientGroupDao.getClientGroupVOsByCondition( ( ClientGroupVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientGroupVO getClientGroupVOByClientGroupId( String clientGroupId ) throws KANException
   {
      return ( ( ClientGroupDao ) getDao() ).getClientGroupVOByClientGroupId( clientGroupId );
   }

   @Override
   public int updateClientGroup( ClientGroupVO clientGroupVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         ( ( ClientGroupDao ) getDao() ).updateClientGroup( clientGroupVO );

         // 删除该集团下所有的客户
         final ClientVO clientVOForDel = new ClientVO();
         clientVOForDel.setGroupId( clientGroupVO.getClientGroupId() );
         clientVOForDel.setModifyBy( clientGroupVO.getModifyBy() );
         clientVOForDel.setModifyDate( clientGroupVO.getModifyDate() );
         this.getClientDao().delClientAndGroupRelationByGroupId( clientVOForDel );

         // 获取要添加的clientId数组
         final String[] clientIdArray = clientGroupVO.getClientIdArray();

         // 遍历数组
         if ( clientIdArray != null && clientIdArray.length > 0 )
         {
            for ( String clientId : clientIdArray )
            {
               final ClientVO clientVO = this.clientDao.getClientVOByClientId( clientId );

               if ( clientVO != null )
               {
                  clientVO.setGroupId( clientGroupVO.getClientGroupId() );
                  clientVO.setModifyBy( clientGroupVO.getModifyBy() );
                  clientVO.setModifyDate( clientGroupVO.getModifyDate() );
                  // 更新client
                  this.clientDao.updateClient( clientVO );
               }

            }
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int insertClientGroup( ClientGroupVO clientGroupVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 将clientGroupVO插入数据库
         ( ( ClientGroupDao ) getDao() ).insertClientGroup( clientGroupVO );
         // 获取要添加的clientId数组
         final String[] clientIdArray = clientGroupVO.getClientIdArray();
         // 遍历数组
         if ( clientIdArray != null && clientIdArray.length > 0 )
         {
            for ( String clientId : clientIdArray )
            {
               final ClientVO clientVO = this.clientDao.getClientVOByClientId( clientId );

               if ( clientVO != null )
               {
                  clientVO.setGroupId( clientGroupVO.getClientGroupId() );
                  clientVO.setModifyBy( clientGroupVO.getModifyBy() );
                  clientVO.setModifyDate( clientGroupVO.getModifyDate() );
                  // 更新client
                  this.clientDao.updateClient( clientVO );
               }

            }
         }
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int deleteClientGroup( ClientGroupVO clientGroupVO ) throws KANException
   {
      clientGroupVO.setDeleted( ClientGroupVO.FALSE );
      return ( ( ClientGroupDao ) getDao() ).updateClientGroup( clientGroupVO );
   }

   @Override
   public List< Object > getClientGroupBaseViews( final String accountId ) throws KANException
   {
      return ( ( ClientGroupDao ) getDao() ).getClientGroupBaseViews( accountId );
   }

   @Override
   public Object getClientGroupBaseViewByClientGroupVO( final ClientGroupVO clientGroupVO ) throws KANException
   {
      return ( ( ClientGroupDao ) getDao() ).getClientBaseViewByClientGroupVO( clientGroupVO );
   }

   @Override
   public Object getClientGroupDTOsByClientGroupVO( ClientGroupVO clientGroupVOTemp ) throws KANException
   {
      // 新建ClientGroupDTO用于return
      final ClientGroupDTO clientGroupDTO = new ClientGroupDTO();

      // 获得主键
      final String clientGroupId = clientGroupVOTemp.getClientGroupId();
      // 获得账号ID
      final String accountId = clientGroupVOTemp.getAccountId();
      // 获得主键对应VO对象
      ClientGroupVO clientGroupVO = ( ( ClientGroupDao ) getDao() ).getClientGroupVOByClientGroupId( clientGroupId );
      // 将VO加入到DTO中
      clientGroupDTO.setClientGroupVO( clientGroupVO );

      // 新增查询用clientBaseView
      final ClientBaseView clientBaseView = new ClientBaseView();
      clientBaseView.setGroupId( clientGroupId );
      clientBaseView.setAccountId( accountId );
      final List< Object > clientBaseViewList = this.clientDao.getClientBaseViewsByCondition( clientBaseView );
      // 如果BaseViewList不为空，则添加到clientGroupVOTemp里
      if ( clientBaseViewList.size() > 0 )
      {
         for ( Object objectClientBaseView : clientBaseViewList )
         {
            final ClientBaseView clientBaseViewTemp = ( ClientBaseView ) objectClientBaseView;
            clientGroupDTO.getClientBaseViews().add( clientBaseViewTemp );
         }
      }

      return clientGroupDTO;
   }

}

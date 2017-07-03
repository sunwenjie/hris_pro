package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.ContentUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.hro.dao.inf.biz.client.ClientContactDao;
import com.kan.hro.dao.inf.biz.client.ClientUserDao;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.service.inf.biz.client.ClientContactService;
import com.kan.hro.service.inf.cp.biz.client.CPClientContactService;

public class ClientContactServiceImpl extends ContextService implements ClientContactService, CPClientContactService
{

   // 注入ClientUserDao
   private ClientUserDao clientUserDao;

   public ClientUserDao getClientUserDao()
   {
      return clientUserDao;
   }

   public void setClientUserDao( final ClientUserDao clientUserDao )
   {
      this.clientUserDao = clientUserDao;
   }

   @Override
   public PagedListHolder getClientContactVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientContactDao clientContactDao = ( ClientContactDao ) getDao();
      pagedListHolder.setHolderSize( clientContactDao.countClientContactVOsByCondition( ( ClientContactVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientContactDao.getClientContactVOsByCondition( ( ClientContactVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientContactDao.getClientContactVOsByCondition( ( ClientContactVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ClientContactVO getClientContactVOByClientContactId( final String clientContactId ) throws KANException
   {
      return ( ( ClientContactDao ) getDao() ).getClientContactVOByClientContactId( clientContactId );
   }

   @Override
   public List< Object > getClientContactVOsByCondition( final ClientContactVO clientContactVO ) throws KANException
   {
      return ( ( ClientContactDao ) getDao() ).getClientContactVOsByCondition( clientContactVO );
   }

   @Override
   public List< Object > getClientContactVOsByClientId( final String clientId ) throws KANException
   {
      return ( ( ClientContactDao ) getDao() ).getClientContactVOsByClientId( clientId );
   }

   @Override
   public int updateClientContact( final ClientContactVO clientContactVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 先修改主表
         ( ( ClientContactDao ) getDao() ).updateClientContact( clientContactVO );

         // 如果UserName不为空，则新增VendorUser对象
         if ( KANUtil.filterEmpty( clientContactVO.getUsername() ) != null )
         {
            // 初始化ClientUserVO
            final ClientUserVO clientUserVO = new ClientUserVO();
            clientUserVO.setUsername( clientContactVO.getUsername() );
            clientUserVO.setAccountId( clientContactVO.getAccountId() );
            clientUserVO.setClientId( clientContactVO.getClientId() );
            clientUserVO.setCorpId( clientContactVO.getCorpId() );
            clientUserVO.setStatus( ClientUserVO.TRUE );

            // 获取ClientUserVO列表，当前添加对象是否重复
            final List< Object > clientUserVOs = this.getClientUserDao().getClientUserVOsByCondition( clientUserVO );

            // 用户名合法
            if ( clientUserVOs != null && clientUserVOs.size() == 0 )
            {
               clientUserVO.setClientContactId( clientContactVO.getClientContactId() );
               clientUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
               clientUserVO.setCreateBy( clientContactVO.getCreateBy() );
               clientUserVO.setModifyBy( clientContactVO.getModifyBy() );

               // 添加ClientUserVO
               this.getClientUserDao().insertClientUser( clientUserVO );
               
               if ( KANUtil.filterEmpty( clientContactVO.getBizEmail()) != null )
               {
                  //调试中
                  new Mail( clientContactVO.getAccountId(), clientContactVO.getBizEmail(), ContentUtil.getMailContent_UserCreate( new Object[] { clientUserVO,clientContactVO } ) ).send( true );
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
   public int insertClientContact( final ClientContactVO clientContactVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 先添加客户联系人
         ( ( ClientContactDao ) getDao() ).insertClientContact( clientContactVO );

         // 如果UserName不为空，则新增ClientContactVO对象
         if ( KANUtil.filterEmpty( clientContactVO.getUsername() ) != null )
         {
            // 初始化ClientUserVO
            final ClientUserVO clientUserVO = new ClientUserVO();
            clientUserVO.setUsername( clientContactVO.getUsername() );
            clientUserVO.setAccountId( clientContactVO.getAccountId() );
            clientUserVO.setClientId( clientContactVO.getClientId() );
            clientUserVO.setCorpId( clientContactVO.getCorpId() );
            clientUserVO.setStatus( ClientUserVO.TRUE );

            // 获取ClientUserVO列表，当前添加对象是否重复
            final List< Object > clientUserVOs = this.getClientUserDao().getClientUserVOsByCondition( clientUserVO );

            // 用户名合法
            if ( clientUserVOs != null && clientUserVOs.size() == 0 )
            {
               clientUserVO.setClientContactId( clientContactVO.getClientContactId() );
               clientUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
               clientUserVO.setCreateBy( clientContactVO.getCreateBy() );
               clientUserVO.setModifyBy( clientContactVO.getModifyBy() );
               clientUserVO.setRole( KANConstants.ROLE_CLIENT );
               // 添加ClientUserVO
               this.getClientUserDao().insertClientUser( clientUserVO );

               if ( KANUtil.filterEmpty( clientContactVO.getBizEmail() ) != null )
               {
                  //调试中
                  new Mail( clientContactVO.getAccountId(), clientContactVO.getBizEmail(), ContentUtil.getMailContent_UserCreate( new Object[] { clientUserVO, clientContactVO } ) ).send( true );
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
   public int deleteClientContact( final ClientContactVO clientContactVO ) throws KANException
   {
      // 标记删除ClientContactVO
      clientContactVO.setDeleted( ClientContactVO.FALSE );
      return ( ( ClientContactDao ) getDao() ).updateClientContact( clientContactVO );
   }

}

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

   // ע��ClientUserDao
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
         // ��������
         startTransaction();

         // ���޸�����
         ( ( ClientContactDao ) getDao() ).updateClientContact( clientContactVO );

         // ���UserName��Ϊ�գ�������VendorUser����
         if ( KANUtil.filterEmpty( clientContactVO.getUsername() ) != null )
         {
            // ��ʼ��ClientUserVO
            final ClientUserVO clientUserVO = new ClientUserVO();
            clientUserVO.setUsername( clientContactVO.getUsername() );
            clientUserVO.setAccountId( clientContactVO.getAccountId() );
            clientUserVO.setClientId( clientContactVO.getClientId() );
            clientUserVO.setCorpId( clientContactVO.getCorpId() );
            clientUserVO.setStatus( ClientUserVO.TRUE );

            // ��ȡClientUserVO�б���ǰ��Ӷ����Ƿ��ظ�
            final List< Object > clientUserVOs = this.getClientUserDao().getClientUserVOsByCondition( clientUserVO );

            // �û����Ϸ�
            if ( clientUserVOs != null && clientUserVOs.size() == 0 )
            {
               clientUserVO.setClientContactId( clientContactVO.getClientContactId() );
               clientUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
               clientUserVO.setCreateBy( clientContactVO.getCreateBy() );
               clientUserVO.setModifyBy( clientContactVO.getModifyBy() );

               // ���ClientUserVO
               this.getClientUserDao().insertClientUser( clientUserVO );
               
               if ( KANUtil.filterEmpty( clientContactVO.getBizEmail()) != null )
               {
                  //������
                  new Mail( clientContactVO.getAccountId(), clientContactVO.getBizEmail(), ContentUtil.getMailContent_UserCreate( new Object[] { clientUserVO,clientContactVO } ) ).send( true );
               }
            }
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
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
         // ��������
         startTransaction();

         // ����ӿͻ���ϵ��
         ( ( ClientContactDao ) getDao() ).insertClientContact( clientContactVO );

         // ���UserName��Ϊ�գ�������ClientContactVO����
         if ( KANUtil.filterEmpty( clientContactVO.getUsername() ) != null )
         {
            // ��ʼ��ClientUserVO
            final ClientUserVO clientUserVO = new ClientUserVO();
            clientUserVO.setUsername( clientContactVO.getUsername() );
            clientUserVO.setAccountId( clientContactVO.getAccountId() );
            clientUserVO.setClientId( clientContactVO.getClientId() );
            clientUserVO.setCorpId( clientContactVO.getCorpId() );
            clientUserVO.setStatus( ClientUserVO.TRUE );

            // ��ȡClientUserVO�б���ǰ��Ӷ����Ƿ��ظ�
            final List< Object > clientUserVOs = this.getClientUserDao().getClientUserVOsByCondition( clientUserVO );

            // �û����Ϸ�
            if ( clientUserVOs != null && clientUserVOs.size() == 0 )
            {
               clientUserVO.setClientContactId( clientContactVO.getClientContactId() );
               clientUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
               clientUserVO.setCreateBy( clientContactVO.getCreateBy() );
               clientUserVO.setModifyBy( clientContactVO.getModifyBy() );
               clientUserVO.setRole( KANConstants.ROLE_CLIENT );
               // ���ClientUserVO
               this.getClientUserDao().insertClientUser( clientUserVO );

               if ( KANUtil.filterEmpty( clientContactVO.getBizEmail() ) != null )
               {
                  //������
                  new Mail( clientContactVO.getAccountId(), clientContactVO.getBizEmail(), ContentUtil.getMailContent_UserCreate( new Object[] { clientUserVO, clientContactVO } ) ).send( true );
               }
            }
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public int deleteClientContact( final ClientContactVO clientContactVO ) throws KANException
   {
      // ���ɾ��ClientContactVO
      clientContactVO.setDeleted( ClientContactVO.FALSE );
      return ( ( ClientContactDao ) getDao() ).updateClientContact( clientContactVO );
   }

}

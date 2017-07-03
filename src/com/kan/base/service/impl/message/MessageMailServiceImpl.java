package com.kan.base.service.impl.message;

import java.util.Date;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.message.MessageMailDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.EmailConfigurationService;
import com.kan.base.service.inf.message.MessageMailService;
import com.kan.base.util.KANException;

public class MessageMailServiceImpl extends ContextService implements MessageMailService
{

   @Override
   public MessageMailVO getMessageMailVOByMailId(final  String meaiId ) throws KANException
   {
      MessageMailVO messageMailVO = ( ( MessageMailDao ) getDao() ).getMessageMailVOByMailId( meaiId );
      return messageMailVO;
   }

   @Override
   public int updateMessageMail(final  MessageMailVO messageMailVO ) throws KANException
   {
      return ( ( MessageMailDao ) getDao() ).updateMessageMail( messageMailVO );
   }

   @Override
   public int insertMessageMail(final  MessageMailVO messageMailVO ,final String accountId ) throws KANException
   {
      // 初始化Service接口
      final EmailConfigurationService emailConfigurationService = ( EmailConfigurationService ) ServiceLocator.getService("emailConfigurationService" );
      EmailConfigurationVO emailConfiguration =  emailConfigurationService.getEmailConfigurationVOByAccountId( accountId );
      
      
      
      //根据配置设置邮件信息
      messageMailVO.setSentAs( emailConfiguration.getAccountName() );
      messageMailVO.setSmtpHost( emailConfiguration.getSmtpHost() );
      messageMailVO.setSmtpPort( emailConfiguration.getSmtpPort() );
      messageMailVO.setUsername( emailConfiguration.getUsername() );
      messageMailVO.setPassword(emailConfiguration.getPassword());
      messageMailVO.setSmtpAuthType( emailConfiguration.getSmtpAuthType() );
      messageMailVO.setShowName( emailConfiguration.getShowName());
      
      return ( ( MessageMailDao ) getDao() ).insertMessageMail( messageMailVO );
   }

   @Override
   public void deleteMessageMail(final MessageMailVO ...messageMailVOs ) throws KANException
   {
      if(messageMailVOs!=null && messageMailVOs.length>0){
         try
         {
            startTransaction();
            MessageMailDao dao = ( MessageMailDao ) getDao();
            for ( MessageMailVO messageMailVO : messageMailVOs )
            {
               MessageMailVO messageMail = dao.getMessageMailVOByMailId( messageMailVO.getMailId() );
               messageMail.setDeleted( BaseVO.FALSE );
               messageMail.setModifyBy( messageMailVO.getModifyBy() );
               messageMail.setModifyDate( new Date() );
               dao.updateMessageMail( messageMail );
            }
            commitTransaction();
         }
         catch ( Exception e )
         {
            rollbackTransaction();
            throw new KANException( e );
         }
         
      }
   }
   
   @Override
   public void deleteMessageMailByMailId(final String modufyUserId ,final String ...mailIds ) throws KANException
   {
      if(mailIds!=null && mailIds.length>0){
         try
         {
            startTransaction();
            MessageMailDao dao = ( MessageMailDao ) getDao();
            for ( String mailId : mailIds )
            {
               MessageMailVO messageMail = dao.getMessageMailVOByMailId( mailId );
               messageMail.setDeleted( BaseVO.FALSE );
               messageMail.setModifyBy( modufyUserId );
               messageMail.setModifyDate( new Date() );
               dao.updateMessageMail( messageMail );
            }
            commitTransaction();
         }
         catch ( Exception e )
         {
            rollbackTransaction();
            throw new KANException( e );
         }
         
      }
   }

   @Override
   public PagedListHolder getMessageMailVOsByCondition(final  PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final MessageMailDao messageMailDao = ( MessageMailDao ) getDao();
      pagedListHolder.setHolderSize( messageMailDao.countMessageMailVOsByCondition( ( MessageMailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( messageMailDao.getMessageMailVOsByCondition( ( MessageMailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( messageMailDao.getMessageMailVOsByCondition( ( MessageMailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;

   }
   
   @Override
   public int insertDraftBoxMessageMail(final  MessageMailVO messageMailVO ,final String accountId ) throws KANException
   {
      // 初始化Service接口
      final EmailConfigurationService emailConfigurationService = ( EmailConfigurationService ) ServiceLocator.getService("emailConfigurationService" );
      EmailConfigurationVO emailConfiguration =  emailConfigurationService.getEmailConfigurationVOByAccountId( accountId );
      
      
      
      //根据配置设置邮件信息
      messageMailVO.setSentAs( emailConfiguration.getAccountName() );
      messageMailVO.setSmtpHost( emailConfiguration.getSmtpHost() );
      messageMailVO.setSmtpPort( emailConfiguration.getSmtpPort() );
      messageMailVO.setUsername( emailConfiguration.getUsername() );
      messageMailVO.setPassword(emailConfiguration.getPassword());
      messageMailVO.setSmtpAuthType( emailConfiguration.getSmtpAuthType() );
      messageMailVO.setShowName( emailConfiguration.getShowName());
      
      return ( ( MessageMailDao ) getDao() ).insertDraftBoxMessageMail( messageMailVO );
   }

}

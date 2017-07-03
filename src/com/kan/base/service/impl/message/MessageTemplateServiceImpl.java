package com.kan.base.service.impl.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.EmailConfigurationDao;
import com.kan.base.dao.inf.management.OptionsDao;
import com.kan.base.dao.inf.management.ShareFolderConfigurationDao;
import com.kan.base.dao.inf.message.MessageTemplateDao;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.message.MessageTemplateService;
import com.kan.base.util.KANException;

public class MessageTemplateServiceImpl extends ContextService implements MessageTemplateService
{
   private UserDao userDao;

   private OptionsDao optionsDao;

   private EmailConfigurationDao emailConfigurationDao;

   private ShareFolderConfigurationDao ftpConfigurationDao;

   public UserDao getUserDao()
   {
      return userDao;
   }

   public void setUserDao( UserDao userDao )
   {
      this.userDao = userDao;
   }

   public OptionsDao getOptionsDao()
   {
      return optionsDao;
   }

   public void setOptionsDao( OptionsDao optionsDao )
   {
      this.optionsDao = optionsDao;
   }

   public EmailConfigurationDao getEmailConfigurationDao()
   {
      return emailConfigurationDao;
   }

   public void setEmailConfigurationDao( EmailConfigurationDao emailConfigurationDao )
   {
      this.emailConfigurationDao = emailConfigurationDao;
   }

   public ShareFolderConfigurationDao getFtpConfigurationDao()
   {
      return ftpConfigurationDao;
   }

   public void setFtpConfigurationDao( ShareFolderConfigurationDao ftpConfigurationDao )
   {
      this.ftpConfigurationDao = ftpConfigurationDao;
   }

   @Override
   public PagedListHolder getMessageTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final MessageTemplateDao messageTemplateDao = ( MessageTemplateDao ) getDao();
      pagedListHolder.setHolderSize( messageTemplateDao.countMessageTemplateVOsByCondition( ( MessageTemplateVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( messageTemplateDao.getMessageTemplateVOsByCondition( ( MessageTemplateVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( messageTemplateDao.getMessageTemplateVOsByCondition( ( MessageTemplateVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public MessageTemplateVO getMessageTemplateVOByTemplateId( final String templateId ) throws KANException
   {
      return ( ( MessageTemplateDao ) getDao() ).getMessageTemplateVOByTemplateId( templateId );
   }

   @Override
   public int updateMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException
   {
      return ( ( MessageTemplateDao ) getDao() ).updateMessageTemplate( messageTemplateVO );
   }

   @Override
   public int insertMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException
   {
      return ( ( MessageTemplateDao ) getDao() ).insertMessageTemplate( messageTemplateVO );

   }

   @Override
   public void deleteMessageTemplate( final String templateId ) throws KANException
   {
      ( ( MessageTemplateDao ) getDao() ).deleteMessageTemplate( templateId );
   }

   
   @Override
   public List<Object> getMessageTemplateVOByAccountId( final String accountId ) throws KANException
   {
      return ( ( MessageTemplateDao ) getDao() ).getMessageTemplateVOByAccountId( accountId );
   }
}

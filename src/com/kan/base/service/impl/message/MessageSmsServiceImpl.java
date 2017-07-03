package com.kan.base.service.impl.message;

import java.util.Date;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.message.MessageSmsDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.message.MessageSmsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.message.MessageSmsService;
import com.kan.base.util.KANException;

public class MessageSmsServiceImpl extends ContextService implements MessageSmsService
{

   @Override
   public MessageSmsVO getMessageSmsVOBySmsId( final String smsId ) throws KANException
   {
      MessageSmsVO messageSmsVO = ( ( MessageSmsDao ) getDao() ).getMessageSmsVOBySmsId( smsId );
      return messageSmsVO;
   }

   @Override
   public int updateMessageSms(final MessageSmsVO messageSmsVO ) throws KANException
   {
      return ( ( MessageSmsDao ) getDao() ).updateMessageSms( messageSmsVO );
   }

   @Override
   public int insertMessageSms(final MessageSmsVO messageSmsVO ) throws KANException
   {
      return ( ( MessageSmsDao ) getDao() ).insertMessageSms( messageSmsVO );
   }

   @Override
   public void deleteMessageSms( final MessageSmsVO  ...messageSmsVOs ) throws KANException
   {
      if(messageSmsVOs!=null && messageSmsVOs.length>0){
         try
         {
            startTransaction();
            MessageSmsDao dao = ( MessageSmsDao ) getDao();
            for ( MessageSmsVO messageSmsVO : messageSmsVOs )
            {
               MessageSmsVO messageSms = dao.getMessageSmsVOBySmsId( messageSmsVO.getSmsId() );
               messageSms.setModifyBy( messageSmsVO.getModifyBy() );
               messageSms.setModifyDate( new Date() );
               messageSms.setDeleted( BaseVO.FALSE );
               dao.updateMessageSms( messageSms );
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
   public void deleteMessageSmsBySmsId(final String modufyUserId, final String  ...smsIds ) throws KANException
   {
      if(smsIds!=null && smsIds.length>0){
         try
         {
            startTransaction();
            MessageSmsDao dao = ( MessageSmsDao ) getDao();
            for ( String smsId : smsIds )
            {
               MessageSmsVO messageSms = dao.getMessageSmsVOBySmsId( smsId );
               messageSms.setModifyBy( modufyUserId );
               messageSms.setModifyDate( new Date() );
               messageSms.setDeleted( BaseVO.FALSE );
               dao.updateMessageSms( messageSms );
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
   public PagedListHolder getMessageSmsVOsByCondition( final PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final MessageSmsDao messageSmsDao = ( MessageSmsDao ) getDao();
      pagedListHolder.setHolderSize( messageSmsDao.countMessageSmsVOsByCondition( ( MessageSmsVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( messageSmsDao.getMessageSmsVOsByCondition( ( MessageSmsVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( messageSmsDao.getMessageSmsVOsByCondition( ( MessageSmsVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;

   }

}

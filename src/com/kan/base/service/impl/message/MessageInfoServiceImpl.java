package com.kan.base.service.impl.message;

import java.util.Date;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.message.MessageInfoDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.message.MessageInfoVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.message.MessageInfoService;
import com.kan.base.util.KANException;

public class MessageInfoServiceImpl extends ContextService implements MessageInfoService
{

   @Override
   public MessageInfoVO getMessageInfoVOByInfoId(final  String infoId ) throws KANException
   {
      MessageInfoVO messageInfoVO = ( ( MessageInfoDao ) getDao() ).getMessageInfoVOByInfoId( infoId );
      return messageInfoVO;
   }

   @Override
   public int updateMessageInfo(final  MessageInfoVO messageInfoVO ) throws KANException
   {
      return ( ( MessageInfoDao ) getDao() ).updateMessageInfo( messageInfoVO );
   }

   @Override
   public int insertMessageInfo(final  MessageInfoVO messageInfoVO ,final String accountId ) throws KANException
   {
      return ( ( MessageInfoDao ) getDao() ).insertMessageInfo( messageInfoVO );
   }

   @Override
   public void deleteMessageInfo(final MessageInfoVO ...messageInfoVOs ) throws KANException
   {
      if(messageInfoVOs!=null && messageInfoVOs.length>0){
         try
         {
            startTransaction();
            MessageInfoDao dao = ( MessageInfoDao ) getDao();
            for ( MessageInfoVO messageInfoVO : messageInfoVOs )
            {
               MessageInfoVO messageInfo = dao.getMessageInfoVOByInfoId( messageInfoVO.getInfoId() );
               messageInfo.setDeleted( BaseVO.FALSE );
               messageInfo.setModifyBy( messageInfoVO.getModifyBy() );
               messageInfo.setModifyDate( new Date() );
               dao.updateMessageInfo( messageInfo );
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
   public void deleteMessageInfoByInfoId(final String modifyUserId ,final String ...infoIds ) throws KANException
   {
      if(infoIds!=null && infoIds.length>0){
         try
         {
            startTransaction();
            MessageInfoDao dao = ( MessageInfoDao ) getDao();
            for ( String infoId : infoIds )
            {
               MessageInfoVO messageInfo = dao.getMessageInfoVOByInfoId( infoId );
               messageInfo.setDeleted( BaseVO.FALSE );
               messageInfo.setModifyBy( modifyUserId );
               messageInfo.setModifyDate( new Date() );
               dao.updateMessageInfo( messageInfo );
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
   public PagedListHolder getMessageInfoVOsByCondition(final  PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final MessageInfoDao messageInfoDao = ( MessageInfoDao ) getDao();
      pagedListHolder.setHolderSize( messageInfoDao.countMessageInfoVOsByCondition( ( MessageInfoVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( messageInfoDao.getMessageInfoVOsByCondition( ( MessageInfoVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( messageInfoDao.getMessageInfoVOsByCondition( ( MessageInfoVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;

   }

   @Override
   public int getNotReadCount( MessageInfoVO messageInfoVO ) throws KANException
   {
      
      return ( ( MessageInfoDao ) getDao() ).getNotReadCount( messageInfoVO );
   }

   @Override
   public void setMessageInfosReaded( final String modifyUserId ,final String ...infoIds ) throws KANException
   {
      if(infoIds!=null && infoIds.length>0){
         try
         {
            startTransaction();
            MessageInfoDao dao = ( MessageInfoDao ) getDao();
            for ( String infoId : infoIds )
            {
               MessageInfoVO messageInfo = dao.getMessageInfoVOByInfoId( infoId );
               messageInfo.setModifyBy( modifyUserId );
               messageInfo.setModifyDate( new Date() );
               // 接收者标为已读
               messageInfo.setReceptionStatus( "3" );
               dao.updateMessageInfo( messageInfo );
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
   public int getAllCount( MessageInfoVO messageInfoVO ) throws KANException
   {
      return ( ( MessageInfoDao ) getDao() ).getAllCount( messageInfoVO );
   }

}

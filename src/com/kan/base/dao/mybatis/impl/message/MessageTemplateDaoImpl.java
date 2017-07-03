package com.kan.base.dao.mybatis.impl.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.message.MessageTemplateDao;
import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.util.KANException;

public class MessageTemplateDaoImpl extends Context implements MessageTemplateDao
{

   @Override
   public int countMessageTemplateVOsByCondition( final MessageTemplateVO messageTemplageVO ) throws KANException
   {
      return ( Integer ) select( "countMessageTemplateVOsByCondition", messageTemplageVO );
   }

   @Override
   public List< Object > getMessageTemplateVOsByCondition( final MessageTemplateVO messageTemplageVO ) throws KANException
   {
      return selectList( "getMessageTemplateVOsByCondition", messageTemplageVO );
   }

   @Override
   public List< Object > getMessageTemplateVOsByCondition( final MessageTemplateVO messageTemplageVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMessageTemplateVOsByCondition", messageTemplageVO, rowBounds );
   }

   @Override
   public int updateMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException
   {
      return insert( "updateMessageTemplate", messageTemplateVO );
   }

   @Override
   public int insertMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException
   {
      return insert( "insertMessageTemplate", messageTemplateVO );
   }

   @Override
   public int deleteMessageTemplate( final String templateId ) throws KANException
   {
      return delete( "deleteMessageTemplate", templateId );
   }

   @Override
   public MessageTemplateVO getMessageTemplateVOByTemplateId( final String templateId ) throws KANException
   {
      return ( MessageTemplateVO ) select( "getMessageTemplateVOByTemplateId", templateId );
   }
   
   @Override
   public List< Object > getMessageTemplateVOByAccountId( final String templateId ) throws KANException
   {
      return selectList( "getMessageTemplateVOByAccountId", templateId );
   }
}
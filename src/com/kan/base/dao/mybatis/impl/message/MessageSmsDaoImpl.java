package com.kan.base.dao.mybatis.impl.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.message.MessageSmsDao;
import com.kan.base.domain.message.MessageSmsVO;
import com.kan.base.util.KANException;

public class MessageSmsDaoImpl extends Context implements MessageSmsDao
{
   @Override
   public int countMessageSmsVOsByCondition( final MessageSmsVO messageSmsVO ) throws KANException
   {
      return ( Integer ) select( "countMessageSmsVOsByCondition", messageSmsVO );
   }

   @Override
   public List< Object > getMessageSmsVOsByCondition( final MessageSmsVO messageSmsVO ) throws KANException
   {
      return selectList( "getMessageSmsVOsByCondition", messageSmsVO );
   }

   @Override
   public List< Object > getMessageSmsVOsByCondition( final MessageSmsVO messageSmsVO,final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMessageSmsVOsByCondition", messageSmsVO, rowBounds );
   }
   @Override
   public MessageSmsVO getMessageSmsVOBySmsId( String smsId ) throws KANException
   {
      return ( MessageSmsVO ) select( "getMessageSmsVOBySmsId", smsId );
   }

   @Override
   public int updateMessageSms( final MessageSmsVO messageSmsVO ) throws KANException
   {
      return update( "updateMessageSms", messageSmsVO );
   }

   @Override
   public int insertMessageSms( final MessageSmsVO messageSmsVO ) throws KANException
   {
      return insert( "insertMessageSms", messageSmsVO );
   }

   @Override
   public int deleteMessageSms( final String smsId ) throws KANException
   {
      return delete( "deleteMessageSms", smsId );
   }

}
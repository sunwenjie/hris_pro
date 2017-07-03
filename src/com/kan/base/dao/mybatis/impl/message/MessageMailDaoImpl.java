package com.kan.base.dao.mybatis.impl.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.message.MessageMailDao;
import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.util.KANException;

public class MessageMailDaoImpl extends Context implements MessageMailDao
{
   @Override
   public int countMessageMailVOsByCondition(final MessageMailVO messageMailVO ) throws KANException
   {
      return ( Integer ) select( "countMessageMailVOsByCondition", messageMailVO );
   }

   @Override
   public List< Object > getMessageMailVOsByCondition(final  MessageMailVO messageMailVO ) throws KANException
   {
      return selectList( "getMessageMailVOsByCondition", messageMailVO );
   }

   @Override
   public List< Object > getMessageMailVOsByCondition(final  MessageMailVO messageMailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMessageMailVOsByCondition", messageMailVO, rowBounds );
   }
   @Override
   public MessageMailVO getMessageMailVOByMailId(final  String mailId ) throws KANException
   {
      return ( MessageMailVO ) select( "getMessageMailVOByMailId", mailId );
   }

   @Override
   public int updateMessageMail(final  MessageMailVO messageMailVO ) throws KANException
   {
      return update( "updateMessageMail", messageMailVO );
   }

   @Override
   public int insertMessageMail(final  MessageMailVO messageMailVO ) throws KANException
   {
      return insert( "insertMessageMail", messageMailVO );
   }

   @Override
   public int deleteMessageMail(final  String mailId ) throws KANException
   {
      return delete( "deleteMessageMail", mailId );
   }

   @Override
   public int insertDraftBoxMessageMail( MessageMailVO messageMailVO ) throws KANException
   {
      return insert( "insertDraftBoxMessageMail", messageMailVO );
   }

}
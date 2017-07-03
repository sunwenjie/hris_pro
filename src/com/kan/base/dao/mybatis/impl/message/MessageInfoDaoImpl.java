package com.kan.base.dao.mybatis.impl.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.message.MessageInfoDao;
import com.kan.base.domain.message.MessageInfoVO;
import com.kan.base.util.KANException;

public class MessageInfoDaoImpl extends Context implements MessageInfoDao
{
   @Override
   public int countMessageInfoVOsByCondition(final MessageInfoVO messageInfoVO ) throws KANException
   {
      return ( Integer ) select( "countMessageInfoVOsByCondition", messageInfoVO );
   }

   @Override
   public List< Object > getMessageInfoVOsByCondition(final  MessageInfoVO messageInfoVO ) throws KANException
   {
      return selectList( "getMessageInfoVOsByCondition", messageInfoVO );
   }

   @Override
   public List< Object > getMessageInfoVOsByCondition(final  MessageInfoVO messageInfoVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMessageInfoVOsByCondition", messageInfoVO, rowBounds );
   }
   @Override
   public MessageInfoVO getMessageInfoVOByInfoId( final String infoId ) throws KANException
   {
      return ( MessageInfoVO ) select( "getMessageInfoVOByInfoId", infoId );
   }

   @Override
   public int updateMessageInfo(final  MessageInfoVO messageInfoVO ) throws KANException
   {
      return update( "updateMessageInfo", messageInfoVO );
   }

   @Override
   public int insertMessageInfo(final  MessageInfoVO messageInfoVO ) throws KANException
   {
      return insert( "insertMessageInfo", messageInfoVO );
   }

   @Override
   public int deleteMessageInfo(final  String infoId ) throws KANException
   {
      return delete( "deleteMessageInfo", infoId );
   }

   @Override
   public int getNotReadCount(final  MessageInfoVO messageInfoVO ) throws KANException
   {
      
      return ( Integer ) select( "getNotReadCount", messageInfoVO );
   }

   @Override
   public int getAllCount( MessageInfoVO messageInfoVO ) throws KANException
   {
      return ( Integer ) select( "getAllCount", messageInfoVO );
   }

}
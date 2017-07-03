package com.kan.base.dao.inf.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.message.MessageInfoVO;
import com.kan.base.util.KANException;

public interface MessageInfoDao
{

   public abstract int countMessageInfoVOsByCondition( final MessageInfoVO messageInfoVO ) throws KANException;

   public abstract List< Object > getMessageInfoVOsByCondition( final MessageInfoVO messageInfoVO ) throws KANException;

   public abstract List< Object > getMessageInfoVOsByCondition( final MessageInfoVO messageInfoVO, RowBounds rowBounds ) throws KANException;
   
   public abstract MessageInfoVO getMessageInfoVOByInfoId( final String infoId ) throws KANException;

   public abstract int updateMessageInfo( final MessageInfoVO messageInfoVO ) throws KANException;

   public abstract int insertMessageInfo( final MessageInfoVO messageInfoVO ) throws KANException;

   public abstract int deleteMessageInfo( final  String infoId ) throws KANException;

   public abstract int getNotReadCount( MessageInfoVO messageInfoVO )throws KANException;
   
   public abstract int getAllCount( MessageInfoVO messageInfoVO )throws KANException;

}
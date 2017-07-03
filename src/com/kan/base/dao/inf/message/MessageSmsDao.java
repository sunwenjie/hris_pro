package com.kan.base.dao.inf.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.message.MessageSmsVO;
import com.kan.base.util.KANException;

public interface MessageSmsDao
{

   public abstract int countMessageSmsVOsByCondition( final MessageSmsVO messageSmsVO ) throws KANException;

   public abstract List< Object > getMessageSmsVOsByCondition( final MessageSmsVO messageSmsVO ) throws KANException;

   public abstract List< Object > getMessageSmsVOsByCondition( final MessageSmsVO messageSmsVO, RowBounds rowBounds ) throws KANException;
   
   public abstract MessageSmsVO getMessageSmsVOBySmsId( final String smsId ) throws KANException;

   public abstract int updateMessageSms( final MessageSmsVO messageSmsVO ) throws KANException;

   public abstract int insertMessageSms( final MessageSmsVO messageSmsVO ) throws KANException;

   public abstract int deleteMessageSms( final String smsId ) throws KANException;

}
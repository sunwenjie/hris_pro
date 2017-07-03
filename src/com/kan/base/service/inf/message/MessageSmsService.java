package com.kan.base.service.inf.message;

import com.kan.base.domain.message.MessageSmsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface MessageSmsService
{
   public abstract PagedListHolder getMessageSmsVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException;
   
   public abstract MessageSmsVO getMessageSmsVOBySmsId( final String smsId ) throws KANException;

   public abstract int updateMessageSms( final MessageSmsVO messageSmsVO ) throws KANException;

   public abstract int insertMessageSms( final MessageSmsVO messageSmsVO) throws KANException;

   public abstract void deleteMessageSms( final MessageSmsVO ...messageSmsVO ) throws KANException;
   
   public abstract void deleteMessageSmsBySmsId(final String modufyUserId, final String  ...smsId ) throws KANException;

}

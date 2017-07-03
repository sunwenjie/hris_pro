package com.kan.base.service.inf.message;

import java.util.List;

import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface MessageTemplateService
{

   public abstract PagedListHolder getMessageTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract MessageTemplateVO getMessageTemplateVOByTemplateId( final String templateId ) throws KANException;

   public abstract int updateMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException;

   public abstract int insertMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException;

   public abstract void deleteMessageTemplate( final String templateId ) throws KANException;

   public abstract List<Object> getMessageTemplateVOByAccountId( final String accountId ) throws KANException;
}

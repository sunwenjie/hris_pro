package com.kan.base.service.inf.message;

import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface MessageMailService
{
   public abstract PagedListHolder getMessageMailVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException;

   public abstract MessageMailVO getMessageMailVOByMailId( final String mailId ) throws KANException;

   public abstract int updateMessageMail( final MessageMailVO messageMailVO ) throws KANException;

   public abstract int insertMessageMail( final MessageMailVO messageMailVO, final String accountId ) throws KANException;

   public abstract void deleteMessageMail( final MessageMailVO ...messageMailVO ) throws KANException;
   
   public abstract void deleteMessageMailByMailId(final String modufyUserId ,final String ...mailIds ) throws KANException;

   public abstract int insertDraftBoxMessageMail( final MessageMailVO messageMailVO ,final String accountId) throws KANException;
}

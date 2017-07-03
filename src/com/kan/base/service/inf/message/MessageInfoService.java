package com.kan.base.service.inf.message;

import com.kan.base.domain.message.MessageInfoVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface MessageInfoService
{
   public abstract PagedListHolder getMessageInfoVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException;
   
   public abstract MessageInfoVO getMessageInfoVOByInfoId( final String infoId ) throws KANException;

   public abstract int updateMessageInfo( final MessageInfoVO messageInfoVO ) throws KANException;

   public abstract int insertMessageInfo( final MessageInfoVO messageInfoVO ,final String accountId) throws KANException;

   public abstract void deleteMessageInfo( final MessageInfoVO ...messageInfoVO ) throws KANException;
   
   public abstract void deleteMessageInfoByInfoId(final String modifyUserId ,final String ...infoIds ) throws KANException;

   public abstract int getNotReadCount( final MessageInfoVO messageInfoVO) throws KANException;
   
   public abstract void setMessageInfosReaded( final String modifyUserId ,final String ...infoIds ) throws KANException;
   
   public abstract int getAllCount( MessageInfoVO messageInfoVO )throws KANException;
}

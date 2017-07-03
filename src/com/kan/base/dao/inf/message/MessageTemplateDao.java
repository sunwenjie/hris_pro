package com.kan.base.dao.inf.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.util.KANException;

public interface MessageTemplateDao
{
   
   public abstract int countMessageTemplateVOsByCondition( final MessageTemplateVO messageTemplateVO ) throws KANException;

   public abstract List< Object > getMessageTemplateVOsByCondition( final MessageTemplateVO messageTemplateVO ) throws KANException;

   public abstract List< Object > getMessageTemplateVOsByCondition( final MessageTemplateVO messageTemplateVO, RowBounds rowBounds ) throws KANException;

   public abstract MessageTemplateVO getMessageTemplateVOByTemplateId( final String templateId ) throws KANException;
   
   public abstract int updateMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException;

   public abstract int insertMessageTemplate( final MessageTemplateVO messageTemplateVO ) throws KANException;

   public abstract int deleteMessageTemplate( final String templateId ) throws KANException;

   public abstract List< Object > getMessageTemplateVOByAccountId(final String accountId )throws KANException;

}
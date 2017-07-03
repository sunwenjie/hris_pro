package com.kan.base.dao.inf.message;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.util.KANException;

public interface MessageMailDao
{

   public abstract int countMessageMailVOsByCondition( final MessageMailVO messageMailVO ) throws KANException;

   public abstract List< Object > getMessageMailVOsByCondition( final MessageMailVO messageMailVO ) throws KANException;

   public abstract List< Object > getMessageMailVOsByCondition( final MessageMailVO messageMailVO, RowBounds rowBounds ) throws KANException;

   
   public abstract MessageMailVO getMessageMailVOByMailId( final String mailId ) throws KANException;

   public abstract int updateMessageMail( final MessageMailVO messageMailVO ) throws KANException;

   public abstract int insertMessageMail( final MessageMailVO messageMailVO ) throws KANException;

   public abstract int deleteMessageMail( final String mailId ) throws KANException;

   public abstract int insertDraftBoxMessageMail( final MessageMailVO messageMailVO ) throws KANException;
}
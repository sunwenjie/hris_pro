package com.kan.base.service.inf.message;

import com.kan.base.domain.message.MessageRegularVO;
import com.kan.base.util.KANException;

public interface MessageRegularService
{
   public abstract int updateMessageRegular( final MessageRegularVO messageRegularVO ) throws KANException;

   public abstract int insertMessageRegular( final MessageRegularVO messageRegularVO ) throws KANException;

   public abstract int deleteMessageRegular( final String regularId ) throws KANException;

}

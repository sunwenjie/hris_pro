package com.kan.base.service.impl.message;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.message.MessageRegularDao;
import com.kan.base.domain.message.MessageRegularVO;
import com.kan.base.service.inf.message.MessageRegularService;
import com.kan.base.util.KANException;

public class MessageRegularServiceImpl extends ContextService implements MessageRegularService
{

   @Override
   public int updateMessageRegular( MessageRegularVO messageRegularVO ) throws KANException
   {
      return ( ( MessageRegularDao ) getDao() ).updateMessageRegular( messageRegularVO );
   }

   @Override
   public int insertMessageRegular( MessageRegularVO messageRegularVO ) throws KANException
   {
      return ( ( MessageRegularDao ) getDao() ).insertMessageRegular( messageRegularVO );
   }

   @Override
   public int deleteMessageRegular( String regularId ) throws KANException
   {
      return ( ( MessageRegularDao ) getDao() ).deleteMessageRegular( regularId );

   }

}

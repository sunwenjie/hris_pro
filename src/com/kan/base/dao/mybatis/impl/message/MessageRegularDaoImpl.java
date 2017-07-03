package com.kan.base.dao.mybatis.impl.message;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.message.MessageRegularDao;
import com.kan.base.domain.message.MessageRegularVO;
import com.kan.base.util.KANException;

public class MessageRegularDaoImpl extends Context implements MessageRegularDao
{

   @Override
   public int updateMessageRegular( MessageRegularVO messageRegularVO ) throws KANException
   {
      return update( "updateMessageRegular", messageRegularVO );
   }

   @Override
   public int insertMessageRegular( MessageRegularVO messageRegularVO ) throws KANException
   {
      return insert( "insertMessageRegular", messageRegularVO );
   }

   @Override
   public int deleteMessageRegular( String regularId ) throws KANException
   {
      return delete( "deleteMessageRegular", regularId );
   }
   

}
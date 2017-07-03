package com.kan.base.dao.mybatis.impl.management;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.OptionsDao;
import com.kan.base.domain.management.OptionsVO;
import com.kan.base.util.KANException;

public class OptionsDaoImpl extends Context implements OptionsDao
{

   @Override
   public OptionsVO getOptionsVOByOptionsId( String optionsId ) throws KANException
   {
      return ( OptionsVO ) select( "getOptionsVOByOptionsId", optionsId );
   }

   @Override
   public int updateOptions( OptionsVO optionsVO ) throws KANException
   {
      return update( "updateOptions", optionsVO );
   }

   @Override
   public int insertOptions( OptionsVO optionsVO ) throws KANException
   {
      return insert( "insertOptions", optionsVO );
   }

   @Override
   public int deleteOptions( String optionsId ) throws KANException
   {
      return delete( "deleteOptions", optionsId );
   }

   @Override
   public OptionsVO getOptionsVOByAccountId( String accountId ) throws KANException
   {
      return ( OptionsVO ) select( "getOptionsVOByAccountId", accountId );
   }

}
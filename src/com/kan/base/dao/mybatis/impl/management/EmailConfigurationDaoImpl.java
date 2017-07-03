package com.kan.base.dao.mybatis.impl.management;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.EmailConfigurationDao;
import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.util.KANException;

public class EmailConfigurationDaoImpl extends Context implements EmailConfigurationDao
{

   @Override
   public EmailConfigurationVO getEmailConfigurationVOByConfigurationId( String configurationId ) throws KANException
   {
      return ( EmailConfigurationVO ) select( "getEmailConfigurationVOByConfigurationId", configurationId );
   }
   

   @Override
   public EmailConfigurationVO getEmailConfigurationVOByAccountId( String accountId ) throws KANException
   {
      return ( EmailConfigurationVO ) select( "getEmailConfigurationVOByAccountId", accountId );
   }

   @Override
   public int updateEmailConfiguration( EmailConfigurationVO emailConfigurationVO ) throws KANException
   {
      return update( "updateEmailConfiguration", emailConfigurationVO );
   }

   @Override
   public int insertEmailConfiguration( EmailConfigurationVO emailConfigurationVO ) throws KANException
   {
      return insert( "insertEmailConfiguration", emailConfigurationVO );
   }

   @Override
   public int deleteEmailConfiguration( EmailConfigurationVO emailConfigurationVO ) throws KANException
   {
      return delete( "deleteEmailConfiguration", emailConfigurationVO );
   }

}
package com.kan.base.service.impl.management;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.EmailConfigurationDao;
import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.service.inf.management.EmailConfigurationService;
import com.kan.base.util.KANException;

public class EmailConfigurationServiceImpl extends ContextService implements EmailConfigurationService
{

   @Override
   public EmailConfigurationVO getEmailConfigurationVOByConfigurationId( String configurationId ) throws KANException
   {
      return ( ( EmailConfigurationDao ) getDao() ).getEmailConfigurationVOByConfigurationId( configurationId );
   }

   @Override
   public EmailConfigurationVO getEmailConfigurationVOByAccountId( String accountId ) throws KANException
   {
      return ( ( EmailConfigurationDao ) getDao() ).getEmailConfigurationVOByAccountId( accountId );
   }

   @Override
   public int updateEmailConfiguration( EmailConfigurationVO emailConfigurationVO ) throws KANException
   {
      return ( ( EmailConfigurationDao ) getDao() ).updateEmailConfiguration( emailConfigurationVO );
   }

   @Override
   public int insertEmailConfiguration( EmailConfigurationVO emailConfigurationVO ) throws KANException
   {
      return ( ( EmailConfigurationDao ) getDao() ).insertEmailConfiguration( emailConfigurationVO );
   }

   @Override
   public void deleteEmailConfiguration( EmailConfigurationVO emailConfigurationVO ) throws KANException
   {
      ( ( EmailConfigurationDao ) getDao() ).deleteEmailConfiguration( emailConfigurationVO );
   }

}

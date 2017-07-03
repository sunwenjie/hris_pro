package com.kan.base.service.inf.management;

import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.util.KANException;

public interface EmailConfigurationService
{

   public abstract EmailConfigurationVO getEmailConfigurationVOByConfigurationId( final String configurationId ) throws KANException;

   public abstract EmailConfigurationVO getEmailConfigurationVOByAccountId( final String accountId ) throws KANException;

   public abstract int updateEmailConfiguration( final EmailConfigurationVO emailConfigurationVO ) throws KANException;

   public abstract int insertEmailConfiguration( final EmailConfigurationVO emailConfigurationVO ) throws KANException;

   public abstract void deleteEmailConfiguration( final EmailConfigurationVO emailConfigurationVO ) throws KANException;

}

package com.kan.base.service.inf.management;

import com.kan.base.domain.management.ShareFolderConfigurationVO;
import com.kan.base.util.KANException;

public interface ShareFolderConfigurationService
{

   public abstract ShareFolderConfigurationVO getShareFolderConfigurationVOByConfigurationId( final String configurationId ) throws KANException;
   
   public abstract ShareFolderConfigurationVO getShareFolderConfigurationVOByAccountId( final String accountId ) throws KANException;

   public abstract int updateShareFolderConfiguration( final ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException;

   public abstract int insertShareFolderConfiguration( final ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException;

   public abstract void deleteShareFolderConfiguration( final ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException;

}

package com.kan.base.dao.inf.management;

import com.kan.base.domain.management.ShareFolderConfigurationVO;
import com.kan.base.util.KANException;

public interface ShareFolderConfigurationDao
{

   public abstract ShareFolderConfigurationVO getShareFolderConfigurationVOByConfigurationId( final String configurationId ) throws KANException;
   
   public abstract ShareFolderConfigurationVO getShareFolderConfigurationVOByAccountId( final String accountId ) throws KANException;

   public abstract int updateShareFolderConfiguration( final ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException;

   public abstract int insertShareFolderConfiguration( final ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException;

   public abstract int deleteShareFolderConfiguration( final ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException;

}
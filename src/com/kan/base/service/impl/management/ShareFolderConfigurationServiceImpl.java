package com.kan.base.service.impl.management;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ShareFolderConfigurationDao;
import com.kan.base.domain.management.ShareFolderConfigurationVO;
import com.kan.base.service.inf.management.ShareFolderConfigurationService;
import com.kan.base.util.KANException;

public class ShareFolderConfigurationServiceImpl extends ContextService implements ShareFolderConfigurationService
{

   @Override
   public ShareFolderConfigurationVO getShareFolderConfigurationVOByConfigurationId( String configurationId ) throws KANException
   {
      return ( ( ShareFolderConfigurationDao ) getDao() ).getShareFolderConfigurationVOByConfigurationId( configurationId );
   }

   @Override
   public ShareFolderConfigurationVO getShareFolderConfigurationVOByAccountId( String accountId ) throws KANException
   {
      return ( ( ShareFolderConfigurationDao ) getDao() ).getShareFolderConfigurationVOByAccountId( accountId );
   }

   @Override
   public int updateShareFolderConfiguration( ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException
   {
      return ( ( ShareFolderConfigurationDao ) getDao() ).updateShareFolderConfiguration( shareFolderConfigurationVO );
   }

   @Override
   public int insertShareFolderConfiguration( ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException
   {
      return ( ( ShareFolderConfigurationDao ) getDao() ).insertShareFolderConfiguration( shareFolderConfigurationVO );
   }

   @Override
   public void deleteShareFolderConfiguration( ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException
   {
      ( ( ShareFolderConfigurationDao ) getDao() ).deleteShareFolderConfiguration( shareFolderConfigurationVO );
   }

}

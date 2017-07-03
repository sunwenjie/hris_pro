package com.kan.base.dao.mybatis.impl.management;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ShareFolderConfigurationDao;
import com.kan.base.domain.management.ShareFolderConfigurationVO;
import com.kan.base.util.KANException;

public class ShareFolderConfigurationDaoImpl extends Context implements ShareFolderConfigurationDao
{

   @Override
   public ShareFolderConfigurationVO getShareFolderConfigurationVOByConfigurationId( String configurationId ) throws KANException
   {
      return ( ShareFolderConfigurationVO ) select( "getShareFolderConfigurationVOByConfigurationId", configurationId );
   }

   @Override
   public ShareFolderConfigurationVO getShareFolderConfigurationVOByAccountId( String accountId ) throws KANException
   {
      return ( ShareFolderConfigurationVO ) select( "getShareFolderConfigurationVOByAccountId", accountId );
   }

   @Override
   public int updateShareFolderConfiguration( ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException
   {
      return update( "updateShareFolderConfiguration", shareFolderConfigurationVO );
   }

   @Override
   public int insertShareFolderConfiguration( ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException
   {
      return insert( "insertShareFolderConfiguration", shareFolderConfigurationVO );
   }

   @Override
   public int deleteShareFolderConfiguration( ShareFolderConfigurationVO shareFolderConfigurationVO ) throws KANException
   {
      return delete( "deleteShareFolderConfiguration", shareFolderConfigurationVO );
   }

}
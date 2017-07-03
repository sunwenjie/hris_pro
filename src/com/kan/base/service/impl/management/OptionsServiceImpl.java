package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.List;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.OptionsDao;
import com.kan.base.dao.inf.security.EntityDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.OptionsVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.service.inf.management.OptionsService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientDao;
import com.kan.hro.domain.biz.client.ClientBaseView;

public class OptionsServiceImpl extends ContextService implements OptionsService
{

   private ClientDao clientDao;

   private EntityDao entityDao;
   private ModuleDao moduleDao;

   public ClientDao getClientDao()
   {
      return clientDao;
   }

   public void setClientDao( ClientDao clientDao )
   {
      this.clientDao = clientDao;
   }

   public EntityDao getEntityDao()
   {
      return entityDao;
   }

   public void setEntityDao( EntityDao entityDao )
   {
      this.entityDao = entityDao;
   }

   @Override
   public OptionsVO getOptionsVOByOptionsId( String optionsId ) throws KANException
   {
      return ( ( OptionsDao ) getDao() ).getOptionsVOByOptionsId( optionsId );
   }

   @Override
   public int updateOptions( OptionsVO optionsVO ) throws KANException
   {
      return ( ( OptionsDao ) getDao() ).updateOptions( optionsVO );
   }

   @Override
   public int insertOptions( OptionsVO optionsVO ) throws KANException
   {
      return ( ( OptionsDao ) getDao() ).insertOptions( optionsVO );
   }

   @Override
   public void deleteOptions( String optionsId ) throws KANException
   {
      ( ( OptionsDao ) getDao() ).deleteOptions( optionsId );
   }

   @Override
   public OptionsVO getOptionsVOByAccountId( String accountId ) throws KANException
   {
      final OptionsVO optionsVO = ( ( OptionsDao ) getDao() ).getOptionsVOByAccountId( accountId );
      if ( optionsVO != null )
      {
         final List< Object > clientBaseViews = clientDao.getClientBaseViewsByAccountId4LogoFile( accountId );
         ClientBaseView clientBaseView;
         List< MappingVO > clientLogoFiles = new ArrayList< MappingVO >();
         List< MappingVO > clientMobileModuleRightIds = new ArrayList< MappingVO >();
         for ( Object obj : clientBaseViews )
         {
            clientBaseView = ( ClientBaseView ) obj;
            clientLogoFiles.add( new MappingVO( clientBaseView.getId(), clientBaseView.getLogoFile(), clientBaseView.getLogoFileSize() ) );
            clientMobileModuleRightIds.add( new MappingVO( clientBaseView.getId(), clientBaseView.getMobileModuleRightIds() ) );
         }
         optionsVO.setClientLogoFiles( clientLogoFiles );
         optionsVO.setClientMobileModuleRightIds( clientMobileModuleRightIds );
         clientBaseView = null;
      }
      return optionsVO;
   }

   @Override
   public int updateOptionsSyncEntity( OptionsVO optionsVO ) throws KANException
   {
      // 同时修改法务实体账户下的独立报税
      int result = 0;
      try
      {
         startTransaction();
         result = ( ( OptionsDao ) getDao() ).updateOptions( optionsVO );
         final String independenceTax = optionsVO.getIndependenceTax();
         final List< Object > entityVOs = entityDao.getEntityVOsByAccountId( optionsVO.getAccountId() );
         EntityVO entityVO;
         for ( Object obj : entityVOs )
         {
            entityVO = ( EntityVO ) obj;
            entityVO.setIndependenceTax( independenceTax );
            entityDao.updateEntity( entityVO );
         }
         entityVO = null;
         ModuleVO clientModuleVO = new ModuleVO();
         clientModuleVO.setModuleIdArray( optionsVO.getModuleIdArray() );
         clientModuleVO.setAccountId( optionsVO.getAccountId() );
         clientModuleVO.setMenuRole( KANConstants.ROLE_CLIENT );
         clientModuleVO.setCreateBy( optionsVO.getCreateBy() );
         clientModuleVO.setCreateDate( optionsVO.getCreateDate() );
         updateClientModule( clientModuleVO );
         ModuleVO employeeModuleVO = new ModuleVO();
         employeeModuleVO.setModuleIdArray( optionsVO.getEmployeeModuleIdArray() );
         employeeModuleVO.setAccountId( optionsVO.getAccountId() );
         employeeModuleVO.setMenuRole( KANConstants.ROLE_EMPLOYEE );
         employeeModuleVO.setCreateBy( optionsVO.getCreateBy() );
         employeeModuleVO.setCreateDate( optionsVO.getCreateDate() );
         updateClientModule( employeeModuleVO );
         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         e.printStackTrace();
      }
      return result;
   }

   private void updateClientModule( final ModuleVO moduleVO ) throws KANException
   {
      String[] moduleIdArray = moduleVO.getModuleIdArray();
      moduleDao.deleteClientModule( moduleVO );
      if ( moduleIdArray != null )
      {
         for ( String moduleId : moduleIdArray )
         {
            ModuleVO module = new ModuleVO();
            module.setModuleId( moduleId );
            module.setAccountId( moduleVO.getAccountId() );
            module.setMenuRole( moduleVO.getMenuRole() );
            module.setCreateBy( moduleVO.getCreateBy() );
            module.setCreateDate( moduleVO.getCreateDate() );
            moduleDao.insertClientModule( module );
         }
      }
   }
   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }
   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }
}

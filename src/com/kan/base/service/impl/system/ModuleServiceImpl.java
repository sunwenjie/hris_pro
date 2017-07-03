package com.kan.base.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.ModuleRightRelationDao;
import com.kan.base.dao.inf.security.ModuleRuleRelationDao;
import com.kan.base.dao.inf.system.AccountModuleRelationDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.security.ModuleRightRelationVO;
import com.kan.base.domain.security.ModuleRuleRelationVO;
import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.system.AccountModuleRelationVO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

/**
 * 处理所有跟Module相关的Service，System Module，Account Module，Position Module，Group Module
 * 
 * @author Kevin
 */
public class ModuleServiceImpl extends ContextService implements ModuleService
{

   // 注入ModuleRightRelationDao
   private ModuleRightRelationDao moduleRightRelationDao;

   public ModuleRightRelationDao getModuleRightRelationDao()
   {
      return moduleRightRelationDao;
   }

   public void setModuleRightRelationDao( final ModuleRightRelationDao moduleRightRelationDao )
   {
      this.moduleRightRelationDao = moduleRightRelationDao;
   }

   // 注入ModuleRuleRelationDao
   private ModuleRuleRelationDao moduleRuleRelationDao;

   public ModuleRuleRelationDao getModuleRuleRelationDao()
   {
      return moduleRuleRelationDao;
   }

   public void setModuleRuleRelationDao( final ModuleRuleRelationDao moduleRuleRelationDao )
   {
      this.moduleRuleRelationDao = moduleRuleRelationDao;
   }

   // 注入AccountModuleRelationDao
   private AccountModuleRelationDao accountModuleRelationDao;

   public AccountModuleRelationDao getAccountModuleRelationDao()
   {
      return accountModuleRelationDao;
   }

   public void setAccountModuleRelationDao( AccountModuleRelationDao accountModuleRelationDao )
   {
      this.accountModuleRelationDao = accountModuleRelationDao;
   }

   @Override
   public PagedListHolder getModuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ModuleDao moduleDao = ( ModuleDao ) getDao();
      pagedListHolder.setHolderSize( moduleDao.countModuleVOsByCondition( ( ModuleVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( moduleDao.getModuleVOsByCondition( ( ModuleVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( moduleDao.getModuleVOsByCondition( ( ModuleVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ModuleVO getModuleVOByModuleId( final String moduleId ) throws KANException
   {
      return ( ( ModuleDao ) getDao() ).getModuleVOByModuleId( moduleId );
   }

   @Override
   public int updateModule( final ModuleVO moduleVO ) throws KANException
   {
      return ( ( ModuleDao ) getDao() ).updateModule( moduleVO );
   }

   @Override
   public int insertModule( final ModuleVO moduleVO ) throws KANException
   {
      return ( ( ModuleDao ) getDao() ).insertModule( moduleVO );

   }

   @Override
   public void deleteModule( final ModuleVO moduleVO ) throws KANException
   {
      ( ( ModuleDao ) getDao() ).deleteModule( moduleVO );
   }

   @Override
   public List< Object > getModuleVOsByParentModuleId( final String parentModuleId ) throws KANException
   {
      return ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( parentModuleId );
   }

   @Override
   /* Add by Kevin at 2013-06-13 */
   public List< ModuleDTO > getModuleDTOs() throws KANException
   {
      // 创建ModuleDTO List，用于返回数据
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      // 获得根节点，默认根节点的父节点值为“0”
      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // 递归遍历
         moduleDTOs.add( fetchModuleDTO( ( ModuleVO ) rootModuleObject ) );
      }

      return moduleDTOs;
   }

   @Override
   public List< ModuleDTO > getClientModuleDTOs() throws KANException
   {
      // 创建ModuleDTO List，用于返回数据
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getClientModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // 递归遍历
         moduleDTOs.add( fetchClientModuleDTO( ( ModuleVO ) rootModuleObject ) );
      }
      return moduleDTOs;
   }

   @Override
   public List< ModuleDTO > getEmployeeModuleDTOs() throws KANException
   {
      // 创建ModuleDTO List，用于返回数据
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getEmployeeModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // 递归遍历
         if ( StringUtils.equals( ( ( ModuleVO ) rootModuleObject ).getModuleId(), "451" ) )
         {
            ( ( ModuleVO ) rootModuleObject ).setDefaultAction( ( ( ModuleVO ) rootModuleObject ).getModifyAction() );
            ( ( ModuleVO ) rootModuleObject ).setListAction( ( ( ModuleVO ) rootModuleObject ).getModifyAction() );
         }
         moduleDTOs.add( getEmployeeModuleVOsByParentModuleId( ( ModuleVO ) rootModuleObject ) );
      }
      return moduleDTOs;
   }

   // 递归方法
   /* Add by Kevin at 2013-06-13 */
   private ModuleDTO fetchModuleDTO( final ModuleVO moduleVO ) throws KANException
   {
      final ModuleDTO moduleDTO = new ModuleDTO();
      moduleDTO.setModuleVO( moduleVO );

      // 继续查找下一层Module
      final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( moduleVO.getModuleId() );

      for ( Object subModuleObject : subModuleVOs )
      {
         // 递归调用
         moduleDTO.getModuleDTOs().add( fetchModuleDTO( ( ModuleVO ) subModuleObject ) );
      }

      return moduleDTO;
   }

   // 递归方法
   private ModuleDTO fetchClientModuleDTO( final ModuleVO moduleVO ) throws KANException
   {
      final ModuleDTO moduleDTO = new ModuleDTO();
      moduleDTO.setModuleVO( moduleVO );

      // 继续查找下一层Module
      final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getClientModuleVOsByParentModuleId( moduleVO.getModuleId() );

      for ( Object subModuleObject : subModuleVOs )
      {
         // 递归调用
         moduleDTO.getModuleDTOs().add( fetchClientModuleDTO( ( ModuleVO ) subModuleObject ) );
      }

      return moduleDTO;
   }

   // 递归方法
   private ModuleDTO getEmployeeModuleVOsByParentModuleId( final ModuleVO moduleVO ) throws KANException
   {
      final ModuleDTO moduleDTO = new ModuleDTO();
      moduleDTO.setModuleVO( moduleVO );

      // 继续查找下一层Module
      final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getEmployeeModuleVOsByParentModuleId( moduleVO.getModuleId() );

      for ( Object subModuleObject : subModuleVOs )
      {
         // 递归调用
         if ( StringUtils.equals( ( ( ModuleVO ) subModuleObject ).getModuleId(), "451" ) )
         {
            ( ( ModuleVO ) subModuleObject ).setDefaultAction( ( ( ModuleVO ) subModuleObject ).getModifyAction() );
            ( ( ModuleVO ) subModuleObject ).setListAction( ( ( ModuleVO ) subModuleObject ).getModifyAction() );
         }
         moduleDTO.getModuleDTOs().add( getEmployeeModuleVOsByParentModuleId( ( ModuleVO ) subModuleObject ) );
      }

      return moduleDTO;
   }

   @Override
   public int updateAccountModuleRelation( final ModuleVO moduleVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 清除当前module对应的right和rule relation
         deleteGlobalRightAndRuleRelation( moduleVO );

         // 重新建立module与right的对应关系
         insertGlobalRightRelation( moduleVO );

         // 重新建立module与rule的对应关系
         insertGlobalRuleRelation( moduleVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   // 清除当前module对应的right和rule relation
   private int deleteGlobalRightAndRuleRelation( final ModuleVO moduleVO ) throws KANException
   {
      try
      {
         // 获得moduleId
         final String moduleId = moduleVO.getModuleId();
         // 新建moduleRightRelationVO以删除module对应的moduleRightRelationVOs
         ModuleRightRelationVO moduleRightRelationVO = new ModuleRightRelationVO();
         moduleRightRelationVO.setModuleId( moduleId );
         moduleRightRelationVO.setAccountId( moduleVO.getAccountId() );
         // 删除moduleId对应的moduleRightRelationVOs
         this.moduleRightRelationDao.deleteModuleRightRelationByCondition( moduleRightRelationVO );

         // 新建moduleRuleRelationVO以删除module对应的moduleRuleRelationVOs
         ModuleRuleRelationVO moduleRuleRelationVO = new ModuleRuleRelationVO();
         moduleRuleRelationVO.setModuleId( moduleId );
         moduleRuleRelationVO.setAccountId( moduleVO.getAccountId() );
         //         moduleRuleRelationVO.setModifyBy( moduleVO.getModifyBy() );
         // 删除moduleId对应的moduleRuleRelationVOs
         this.moduleRuleRelationDao.deleteModuleRuleRelationByCondition( moduleRuleRelationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   // 重新建立module与right的对应关系
   private int insertGlobalRightRelation( final ModuleVO moduleVO ) throws KANException
   {
      try
      {
         final String moduleId = moduleVO.getModuleId();
         final String[] rightIdArray = moduleVO.getRightIdArray();
         if ( rightIdArray != null && rightIdArray.length > 0 )
         {
            for ( String rightId : rightIdArray )
            {
               // 新建moduleRightRelationVO以添加
               ModuleRightRelationVO moduleRightRelationVO = new ModuleRightRelationVO();
               moduleRightRelationVO.setModuleId( moduleId );
               moduleRightRelationVO.setRightId( rightId );
               moduleRightRelationVO.setAccountId( moduleVO.getAccountId() );
               moduleRightRelationVO.setModifyBy( moduleVO.getModifyBy() );
               moduleRightRelationVO.setCreateBy( moduleVO.getModifyBy() );
               // 添加moduleRightRelationVO
               this.moduleRightRelationDao.insertModuleRightRelation( moduleRightRelationVO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   // 重新建立module与rule的对应关系
   private int insertGlobalRuleRelation( final ModuleVO moduleVO ) throws KANException
   {
      try
      {
         final String moduleId = moduleVO.getModuleId();
         final String[] ruleIdArray = moduleVO.getRuleIdArray();
         if ( ruleIdArray != null && ruleIdArray.length > 0 )
         {
            for ( String array : ruleIdArray )
            {
               final String ruleId = array.split( "_" )[ 1 ];

               // 新建moduleRuleRelationVO以添加
               ModuleRuleRelationVO moduleRuleRelationVO = new ModuleRuleRelationVO();
               moduleRuleRelationVO.setModuleId( moduleId );
               moduleRuleRelationVO.setRuleId( ruleId );
               moduleRuleRelationVO.setAccountId( moduleVO.getAccountId() );
               moduleRuleRelationVO.setModifyBy( moduleVO.getModifyBy() );
               moduleRuleRelationVO.setCreateBy( moduleVO.getModifyBy() );
               // 添加moduleRuleRelationVO
               this.moduleRuleRelationDao.insertModuleRuleRelation( moduleRuleRelationVO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< AccountModuleDTO > getAccountModuleDTOsByAccountId( String accountId ) throws KANException
   {
      // 创建ModuleDTO List，用于返回数据
      final List< AccountModuleDTO > accountModuleDTOs = new ArrayList< AccountModuleDTO >();

      // 获得根节点，默认根节点的父节点值为“0”
      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // 递归遍历
         final AccountModuleDTO accountModuleDTO = fetchAccountModuleDTO( accountId, ( ModuleVO ) rootModuleObject );
         if ( accountModuleDTO != null )
         {
            accountModuleDTOs.add( accountModuleDTO );
         }
      }

      return accountModuleDTOs;
   }

   // 递归方法
   /* Add by Kevin at 2013-06-24 */
   private AccountModuleDTO fetchAccountModuleDTO( final String accountId, final ModuleVO moduleVO ) throws KANException
   {
      // 按照给定的AccountId和ModuleId查找是否存在Module
      final AccountModuleRelationVO accountModuleRelationVO = new AccountModuleRelationVO();
      accountModuleRelationVO.setAccountId( accountId );
      accountModuleRelationVO.setModuleId( moduleVO.getModuleId() );
      final List< Object > accountModuleRelationVOs = this.accountModuleRelationDao.getAccountModuleRelationVOsByCondition( accountModuleRelationVO );

      // 如果Account有当前模块的访问权限
      if ( accountModuleRelationVOs != null && accountModuleRelationVOs.size() > 0 )
      {
         // 添加ModuleVO至当前的AccountModuleDTO
         final AccountModuleDTO accountModuleDTO = new AccountModuleDTO();
         accountModuleDTO.setModuleVO( moduleVO );

         // 获得当前账户Module所关联的Right
         final ModuleRightRelationVO moduleRightRelationVO = new ModuleRightRelationVO();
         moduleRightRelationVO.setAccountId( accountId );
         moduleRightRelationVO.setModuleId( moduleVO.getModuleId() );
         final List< Object > moduleRightRelationVOs = this.moduleRightRelationDao.getModuleRightRelationVOsByCondition( moduleRightRelationVO );
         if ( moduleRightRelationVOs != null && moduleRightRelationVOs.size() > 0 )
         {
            for ( Object moduleRightRelationVOObject : moduleRightRelationVOs )
            {
               accountModuleDTO.getModuleRightRelationVOs().add( ( ModuleRightRelationVO ) moduleRightRelationVOObject );
            }
         }

         // 获得当前账户Module所关联的Rule
         final ModuleRuleRelationVO moduleRuleRelationVO = new ModuleRuleRelationVO();
         moduleRuleRelationVO.setAccountId( accountId );
         moduleRuleRelationVO.setModuleId( moduleVO.getModuleId() );
         final List< Object > moduleRuleRelationVOs = moduleRuleRelationDao.getModuleRuleRelationVOsByCondition( moduleRuleRelationVO );
         if ( moduleRuleRelationVOs != null && moduleRuleRelationVOs.size() > 0 )
         {
            for ( Object moduleRuleRelationVOObject : moduleRuleRelationVOs )
            {
               accountModuleDTO.getModuleRuleRelationVOs().add( ( ModuleRuleRelationVO ) moduleRuleRelationVOObject );
            }
         }

         // 继续查找下一层Module
         final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( moduleVO.getModuleId() );
         for ( Object subModuleObject : subModuleVOs )
         {
            // 递归调用
            final AccountModuleDTO subAccountModuleDTO = fetchAccountModuleDTO( accountId, ( ModuleVO ) subModuleObject );
            if ( subAccountModuleDTO != null )
            {
               accountModuleDTO.getAccountModuleDTOs().add( subAccountModuleDTO );
            }
         }

         return accountModuleDTO;
      }
      else
      {
         return null;
      }
   }

   @Override
   public List< Object > getAccountModuleVOsByAccountId( final String accountId ) throws KANException
   {
      final List< Object > accountModuleRelationVOs = this.accountModuleRelationDao.getAccountModuleRelationVOsByAccountId( accountId );
      final List< Object > moduleVOs = new ArrayList< Object >();

      if ( accountModuleRelationVOs != null && accountModuleRelationVOs.size() > 0 )
      {
         for ( Object accountModuleRelationVOObject : accountModuleRelationVOs )
         {
            final ModuleDTO moduleDTO = KANConstants.getModuleDTOByModuleId( ( ( AccountModuleRelationVO ) accountModuleRelationVOObject ).getModuleId() );
            if ( moduleDTO != null && moduleDTO.getModuleVO() != null )
            {
               moduleVOs.add( moduleDTO.getModuleVO() );
            }
         }
      }

      return moduleVOs;
   }

   @Override
   public List< Object > getActiveModuleBaseViews() throws KANException
   {
      return ( ( ModuleDao ) getDao() ).getActiveModuleBaseViews();
   }

   @Override
   public void updateAccountClientModuleRelation( final ModuleVO moduleVO ) throws KANException
   {
      try
      {
         startTransaction();
         String[] moduleIdArray = moduleVO.getModuleIdArray();
         ( ( ModuleDao ) getDao() ).deleteClientModule( moduleVO );
         if ( moduleIdArray != null )
         {
            for ( String moduleId : moduleIdArray )
            {
               ModuleVO module = new ModuleVO();
               module.setModuleId( moduleId );
               module.setAccountId( moduleVO.getAccountId() );
               module.setRole( moduleVO.getRole() );
               module.setCreateBy( moduleVO.getCreateBy() );
               module.setCreateDate( moduleVO.getCreateDate() );
               ( ( ModuleDao ) getDao() ).insertClientModule( module );
            }
         }
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public List< ModuleVO > getClientModuleVOs( final String accountId, final String clientId, final String role ) throws KANException
   {
      List< ModuleVO > moduleVOs = new ArrayList< ModuleVO >();
      List< Object > list = ( ( ModuleDao ) getDao() ).getClientModuleVOs( accountId, clientId, role );
      for ( Object object : list )
      {
         moduleVOs.add( ( ModuleVO ) object );
      }
      return moduleVOs;
   }
}

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
 * �������и�Module��ص�Service��System Module��Account Module��Position Module��Group Module
 * 
 * @author Kevin
 */
public class ModuleServiceImpl extends ContextService implements ModuleService
{

   // ע��ModuleRightRelationDao
   private ModuleRightRelationDao moduleRightRelationDao;

   public ModuleRightRelationDao getModuleRightRelationDao()
   {
      return moduleRightRelationDao;
   }

   public void setModuleRightRelationDao( final ModuleRightRelationDao moduleRightRelationDao )
   {
      this.moduleRightRelationDao = moduleRightRelationDao;
   }

   // ע��ModuleRuleRelationDao
   private ModuleRuleRelationDao moduleRuleRelationDao;

   public ModuleRuleRelationDao getModuleRuleRelationDao()
   {
      return moduleRuleRelationDao;
   }

   public void setModuleRuleRelationDao( final ModuleRuleRelationDao moduleRuleRelationDao )
   {
      this.moduleRuleRelationDao = moduleRuleRelationDao;
   }

   // ע��AccountModuleRelationDao
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
      // ����ModuleDTO List�����ڷ�������
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      // ��ø��ڵ㣬Ĭ�ϸ��ڵ�ĸ��ڵ�ֵΪ��0��
      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // �ݹ����
         moduleDTOs.add( fetchModuleDTO( ( ModuleVO ) rootModuleObject ) );
      }

      return moduleDTOs;
   }

   @Override
   public List< ModuleDTO > getClientModuleDTOs() throws KANException
   {
      // ����ModuleDTO List�����ڷ�������
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getClientModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // �ݹ����
         moduleDTOs.add( fetchClientModuleDTO( ( ModuleVO ) rootModuleObject ) );
      }
      return moduleDTOs;
   }

   @Override
   public List< ModuleDTO > getEmployeeModuleDTOs() throws KANException
   {
      // ����ModuleDTO List�����ڷ�������
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getEmployeeModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // �ݹ����
         if ( StringUtils.equals( ( ( ModuleVO ) rootModuleObject ).getModuleId(), "451" ) )
         {
            ( ( ModuleVO ) rootModuleObject ).setDefaultAction( ( ( ModuleVO ) rootModuleObject ).getModifyAction() );
            ( ( ModuleVO ) rootModuleObject ).setListAction( ( ( ModuleVO ) rootModuleObject ).getModifyAction() );
         }
         moduleDTOs.add( getEmployeeModuleVOsByParentModuleId( ( ModuleVO ) rootModuleObject ) );
      }
      return moduleDTOs;
   }

   // �ݹ鷽��
   /* Add by Kevin at 2013-06-13 */
   private ModuleDTO fetchModuleDTO( final ModuleVO moduleVO ) throws KANException
   {
      final ModuleDTO moduleDTO = new ModuleDTO();
      moduleDTO.setModuleVO( moduleVO );

      // ����������һ��Module
      final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( moduleVO.getModuleId() );

      for ( Object subModuleObject : subModuleVOs )
      {
         // �ݹ����
         moduleDTO.getModuleDTOs().add( fetchModuleDTO( ( ModuleVO ) subModuleObject ) );
      }

      return moduleDTO;
   }

   // �ݹ鷽��
   private ModuleDTO fetchClientModuleDTO( final ModuleVO moduleVO ) throws KANException
   {
      final ModuleDTO moduleDTO = new ModuleDTO();
      moduleDTO.setModuleVO( moduleVO );

      // ����������һ��Module
      final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getClientModuleVOsByParentModuleId( moduleVO.getModuleId() );

      for ( Object subModuleObject : subModuleVOs )
      {
         // �ݹ����
         moduleDTO.getModuleDTOs().add( fetchClientModuleDTO( ( ModuleVO ) subModuleObject ) );
      }

      return moduleDTO;
   }

   // �ݹ鷽��
   private ModuleDTO getEmployeeModuleVOsByParentModuleId( final ModuleVO moduleVO ) throws KANException
   {
      final ModuleDTO moduleDTO = new ModuleDTO();
      moduleDTO.setModuleVO( moduleVO );

      // ����������һ��Module
      final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getEmployeeModuleVOsByParentModuleId( moduleVO.getModuleId() );

      for ( Object subModuleObject : subModuleVOs )
      {
         // �ݹ����
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
         // ��������
         this.startTransaction();

         // �����ǰmodule��Ӧ��right��rule relation
         deleteGlobalRightAndRuleRelation( moduleVO );

         // ���½���module��right�Ķ�Ӧ��ϵ
         insertGlobalRightRelation( moduleVO );

         // ���½���module��rule�Ķ�Ӧ��ϵ
         insertGlobalRuleRelation( moduleVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   // �����ǰmodule��Ӧ��right��rule relation
   private int deleteGlobalRightAndRuleRelation( final ModuleVO moduleVO ) throws KANException
   {
      try
      {
         // ���moduleId
         final String moduleId = moduleVO.getModuleId();
         // �½�moduleRightRelationVO��ɾ��module��Ӧ��moduleRightRelationVOs
         ModuleRightRelationVO moduleRightRelationVO = new ModuleRightRelationVO();
         moduleRightRelationVO.setModuleId( moduleId );
         moduleRightRelationVO.setAccountId( moduleVO.getAccountId() );
         // ɾ��moduleId��Ӧ��moduleRightRelationVOs
         this.moduleRightRelationDao.deleteModuleRightRelationByCondition( moduleRightRelationVO );

         // �½�moduleRuleRelationVO��ɾ��module��Ӧ��moduleRuleRelationVOs
         ModuleRuleRelationVO moduleRuleRelationVO = new ModuleRuleRelationVO();
         moduleRuleRelationVO.setModuleId( moduleId );
         moduleRuleRelationVO.setAccountId( moduleVO.getAccountId() );
         //         moduleRuleRelationVO.setModifyBy( moduleVO.getModifyBy() );
         // ɾ��moduleId��Ӧ��moduleRuleRelationVOs
         this.moduleRuleRelationDao.deleteModuleRuleRelationByCondition( moduleRuleRelationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   // ���½���module��right�Ķ�Ӧ��ϵ
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
               // �½�moduleRightRelationVO�����
               ModuleRightRelationVO moduleRightRelationVO = new ModuleRightRelationVO();
               moduleRightRelationVO.setModuleId( moduleId );
               moduleRightRelationVO.setRightId( rightId );
               moduleRightRelationVO.setAccountId( moduleVO.getAccountId() );
               moduleRightRelationVO.setModifyBy( moduleVO.getModifyBy() );
               moduleRightRelationVO.setCreateBy( moduleVO.getModifyBy() );
               // ���moduleRightRelationVO
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

   // ���½���module��rule�Ķ�Ӧ��ϵ
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

               // �½�moduleRuleRelationVO�����
               ModuleRuleRelationVO moduleRuleRelationVO = new ModuleRuleRelationVO();
               moduleRuleRelationVO.setModuleId( moduleId );
               moduleRuleRelationVO.setRuleId( ruleId );
               moduleRuleRelationVO.setAccountId( moduleVO.getAccountId() );
               moduleRuleRelationVO.setModifyBy( moduleVO.getModifyBy() );
               moduleRuleRelationVO.setCreateBy( moduleVO.getModifyBy() );
               // ���moduleRuleRelationVO
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
      // ����ModuleDTO List�����ڷ�������
      final List< AccountModuleDTO > accountModuleDTOs = new ArrayList< AccountModuleDTO >();

      // ��ø��ڵ㣬Ĭ�ϸ��ڵ�ĸ��ڵ�ֵΪ��0��
      final List< Object > rootModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // �ݹ����
         final AccountModuleDTO accountModuleDTO = fetchAccountModuleDTO( accountId, ( ModuleVO ) rootModuleObject );
         if ( accountModuleDTO != null )
         {
            accountModuleDTOs.add( accountModuleDTO );
         }
      }

      return accountModuleDTOs;
   }

   // �ݹ鷽��
   /* Add by Kevin at 2013-06-24 */
   private AccountModuleDTO fetchAccountModuleDTO( final String accountId, final ModuleVO moduleVO ) throws KANException
   {
      // ���ո�����AccountId��ModuleId�����Ƿ����Module
      final AccountModuleRelationVO accountModuleRelationVO = new AccountModuleRelationVO();
      accountModuleRelationVO.setAccountId( accountId );
      accountModuleRelationVO.setModuleId( moduleVO.getModuleId() );
      final List< Object > accountModuleRelationVOs = this.accountModuleRelationDao.getAccountModuleRelationVOsByCondition( accountModuleRelationVO );

      // ���Account�е�ǰģ��ķ���Ȩ��
      if ( accountModuleRelationVOs != null && accountModuleRelationVOs.size() > 0 )
      {
         // ���ModuleVO����ǰ��AccountModuleDTO
         final AccountModuleDTO accountModuleDTO = new AccountModuleDTO();
         accountModuleDTO.setModuleVO( moduleVO );

         // ��õ�ǰ�˻�Module��������Right
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

         // ��õ�ǰ�˻�Module��������Rule
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

         // ����������һ��Module
         final List< Object > subModuleVOs = ( ( ModuleDao ) getDao() ).getModuleVOsByParentModuleId( moduleVO.getModuleId() );
         for ( Object subModuleObject : subModuleVOs )
         {
            // �ݹ����
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
         // �ع�����
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

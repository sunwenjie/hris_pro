package com.kan.base.service.impl.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.GroupDao;
import com.kan.base.dao.inf.security.GroupModuleRightRelationDao;
import com.kan.base.dao.inf.security.GroupModuleRuleRelationDao;
import com.kan.base.dao.inf.security.PositionGroupRelationDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleDTO;
import com.kan.base.domain.security.GroupModuleRightRelationVO;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.GroupService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class GroupServiceImpl extends ContextService implements GroupService
{

   private PositionGroupRelationDao positionGroupRelationDao;

   public PositionGroupRelationDao getPositionGroupRelationDao()
   {
      return positionGroupRelationDao;
   }

   public void setPositionGroupRelationDao( PositionGroupRelationDao positionGroupRelationDao )
   {
      this.positionGroupRelationDao = positionGroupRelationDao;
   }

   // 注入GroupModuleRightRelationDao
   private GroupModuleRightRelationDao groupModuleRightRelationDao;

   public GroupModuleRightRelationDao getGroupModuleRightRelationDao()
   {
      return groupModuleRightRelationDao;
   }

   public void setGroupModuleRightRelationDao( GroupModuleRightRelationDao groupModuleRightRelationDao )
   {
      this.groupModuleRightRelationDao = groupModuleRightRelationDao;
   }

   // 注入GroupModuleRuleRelationDao
   private GroupModuleRuleRelationDao groupModuleRuleRelationDao;

   public GroupModuleRuleRelationDao getGroupModuleRuleRelationDao()
   {
      return groupModuleRuleRelationDao;
   }

   public void setGroupModuleRuleRelationDao( GroupModuleRuleRelationDao groupModuleRuleRelationDao )
   {
      this.groupModuleRuleRelationDao = groupModuleRuleRelationDao;
   }

   // 注入ModuleDao
   private ModuleDao moduleDao;

   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }

   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }

   @Override
   public PagedListHolder getGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final GroupDao groupDao = ( GroupDao ) getDao();
      pagedListHolder.setHolderSize( groupDao.countGroupVOsByCondition( ( GroupVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( groupDao.getGroupVOsByCondition( ( GroupVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( groupDao.getGroupVOsByCondition( ( GroupVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public GroupVO getGroupVOByGroupId( final String groupId ) throws KANException
   {
      return ( ( GroupDao ) getDao() ).getGroupVOByGroupId( groupId );
   }

   @Override
   public int updateGroup( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         if ( "1".equals( groupVO.getDataRole() ) || "2".equals( groupVO.getDataRole() ) )
         {
            groupVO.setHrFunction( "1" );
         }
         else
         {
            groupVO.setHrFunction( "2" );
         }
         // Update职位组对象
         ( ( GroupDao ) getDao() ).updateGroup( groupVO );

         // 建立当前Group与Position的关系
         insertGroupPositionRelation( groupVO );

         // 建立当前Group与Module的关系（模态框已修改，不用再次修改）
         insertGroupModuleRuleRelation( groupVO );

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

   @Override
   // Add by Kevin at 2013-06-14
   public int updateGroupModule( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 重新建立Position与Module的关联
         insertGroupModuleRelation( groupVO );

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

   @Override
   public int insertGroup( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         if ( "1".equals( groupVO.getDataRole() ) || "2".equals( groupVO.getDataRole() ) )
         {
            groupVO.setHrFunction( "1" );
         }
         else
         {
            groupVO.setHrFunction( "2" );
         }
         
         ( ( GroupDao ) getDao() ).insertGroup( groupVO );

         // 建立当前Group与Position的关系
         insertGroupPositionRelation( groupVO );

         // 建立当前Group与Module的关系
         insertGroupModuleRelation( groupVO );

         insertGroupModuleRuleRelation( groupVO );

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

   // 建立当前Group与Position的关系
   private int insertGroupPositionRelation( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // 删除Group和Position的对应关系
         this.positionGroupRelationDao.deletePositionGroupRelationByGroupId( groupVO.getGroupId() );

         if ( groupVO.getPositionIdArray() != null && groupVO.getPositionIdArray().length > 0 )
         {
            for ( String positionId : groupVO.getPositionIdArray() )
            {
               final PositionGroupRelationVO positionGroupRelationVO = new PositionGroupRelationVO();
               positionGroupRelationVO.setGroupId( groupVO.getGroupId() );
               positionGroupRelationVO.setPositionId( positionId );
               positionGroupRelationVO.setCreateBy( groupVO.getModifyBy() );
               positionGroupRelationVO.setModifyBy( groupVO.getModifyBy() );
               this.positionGroupRelationDao.insertPositionGroupRelation( positionGroupRelationVO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   // 建立当前Group与Module的对应关系
   private int insertGroupModuleRelation( final GroupVO groupVO ) throws KANException
   {
      try
      {

         // 建立Group和Module的Relation
         if ( groupVO.getModuleIdArray() != null && groupVO.getModuleIdArray().length > 0 )
         {
            for ( String moduleId : groupVO.getModuleIdArray() )
            {
               insertGroupModuleRightRelation( groupVO, moduleId );
               //               insertGroupModuleRuleRelation( groupVO, moduleId );
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   /**  
    * UpdateGroupModuleRelationPopup
    * 模态框修改groupId对应单条ModuleId的权限规则
    * @param positionVO
    * @param moduleId
    * @return
    * @throws KANException
    */
   @Override
   public int updateGroupModuleRelationPopup( final GroupVO groupVO, final String moduleId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 重新建立Group与Module的关联
         insertGroupModuleRightRelation( groupVO, moduleId );
         //         insertGroupModuleRuleRelation( groupVO, moduleId );

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

   /**  
    * InsertGroupModuleRightRelation
    * 添加GroupId 对应 ModuleId的权限
    * @param groupVO
    * @param moduleId
    * @throws KANException
    */
   private void insertGroupModuleRightRelation( final GroupVO groupVO, final String moduleId ) throws KANException
   {
      // 先删除GroupId对应 ModuleId所有权限
      this.groupModuleRightRelationDao.deleteGroupModuleRightRelationByCondition( new GroupModuleRightRelationVO( groupVO.getGroupId(), moduleId, null ) );

      // Group 添加权限
      if ( groupVO.getRightIdArray() != null && groupVO.getRightIdArray().length > 0 )
      {
         // 循环添加Group、Module和Right的关系
         for ( String rightId : groupVO.getRightIdArray() )
         {
            final GroupModuleRightRelationVO groupModuleRightRelationVO = new GroupModuleRightRelationVO();
            groupModuleRightRelationVO.setGroupId( groupVO.getGroupId() );
            groupModuleRightRelationVO.setModuleId( moduleId );
            groupModuleRightRelationVO.setRightId( rightId );
            groupModuleRightRelationVO.setCreateBy( groupVO.getModifyBy() );
            groupModuleRightRelationVO.setModifyBy( groupVO.getModifyBy() );
            this.groupModuleRightRelationDao.insertGroupModuleRightRelation( groupModuleRightRelationVO );
         }
      }
   }

   //   /**  
   //    * InsertGroupModuleRuleRelation
   //    * 添加GroupId 对应 ModuleId的规则
   //    * @param groupVO
   //    * @param moduleId
   //    * @throws KANException
   //    */
   //   private void insertGroupModuleRuleRelation( final GroupVO groupVO, final String moduleId ) throws KANException
   //   {
   //      // 先删除Group及Module对应关系表
   //      this.groupModuleRuleRelationDao.deleteGroupModuleRuleRelationByCondition( new GroupModuleRuleRelationVO( groupVO.getGroupId(), moduleId, null ) );
   //
   //      // Group 添加规则
   //      if ( groupVO.getRuleIdArray() != null && groupVO.getRuleIdArray().length > 0 )
   //      {
   //         // 循环添加Group、Module和Rule的关系
   //         for ( String ruleId : groupVO.getRuleIdArray() )
   //         {
   //            if ( ruleId != null && ruleId.split( "_" ).length == 2 )
   //            {
   //               final GroupModuleRuleRelationVO groupModuleRuleRelationVO = new GroupModuleRuleRelationVO();
   //               groupModuleRuleRelationVO.setGroupId( groupVO.getGroupId() );
   //               groupModuleRuleRelationVO.setModuleId( moduleId );
   //               groupModuleRuleRelationVO.setRuleId( ruleId.split( "_" )[ 1 ] );
   //               groupModuleRuleRelationVO.setCreateBy( groupVO.getModifyBy() );
   //               groupModuleRuleRelationVO.setModifyBy( groupVO.getModifyBy() );
   //               this.groupModuleRuleRelationDao.insertGroupModuleRuleRelation( groupModuleRuleRelationVO );
   //            }
   //         }
   //      }
   //
   //   }

   private void insertGroupModuleRuleRelation( final GroupVO groupVO ) throws KANException
   {
      GroupModuleRuleRelationVO groupModuleRuleRelationVO = new GroupModuleRuleRelationVO();
      groupModuleRuleRelationVO.setGroupId( groupVO.getGroupId() );
      this.groupModuleRuleRelationDao.deleteGroupModuleRuleRelationByGroupId( groupModuleRuleRelationVO );
      if ( !"0".equals( groupVO.getDataRole() ) )
      {
         for ( String ruleId : groupVO.getRuleIds() )
         {
            groupModuleRuleRelationVO = new GroupModuleRuleRelationVO();
            groupModuleRuleRelationVO.setRuleId( ruleId );
            groupModuleRuleRelationVO.setGroupId( groupVO.getGroupId() );
            groupModuleRuleRelationVO.setCreateBy( groupVO.getModifyBy() );
            groupModuleRuleRelationVO.setModifyBy( groupVO.getModifyBy() );

            //部门
            if ( ruleId.equals( "3" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getBranchIds() ) );
            }
            //职位
            if ( ruleId.equals( "4" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getPositionIds() ) );
            }
            //职级
            if ( ruleId.equals( "5" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getPositionGradeIds() ) );
            }
            //项目
            if ( ruleId.equals( "6" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getBusinessTypeIds() ) );
            }
            //法务主体
            if ( ruleId.equals( "7" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getEntityIds() ) );
            }
            this.groupModuleRuleRelationDao.insertGroupModuleRuleRelation( groupModuleRuleRelationVO );
         }
      }
   }

   @Override
   public int deleteGroup( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 从常量获取当前节点开始的职位组DTO
         final GroupDTO groupDTO = KANConstants.getKANAccountConstants( groupVO.getAccountId() ).getGroupDTOByGroupId( groupVO.getGroupId() );
         if ( groupDTO != null )
         {
            // 标记删除Group与Position的关系
            if ( groupDTO.getPositionGroupRelationVOs() != null && groupDTO.getPositionGroupRelationVOs().size() > 0 )
            {
               for ( PositionGroupRelationVO positionGroupRelationVO : groupDTO.getPositionGroupRelationVOs() )
               {
                  positionGroupRelationVO.setDeleted( PositionGroupRelationVO.FALSE );
                  positionGroupRelationVO.setModifyBy( groupVO.getModifyBy() );
                  positionGroupRelationVO.setModifyDate( groupVO.getModifyDate() );
                  this.positionGroupRelationDao.updatePositionGroupRelation( positionGroupRelationVO );
               }
            }

            // 标记删除Group与Module的关系
            if ( groupDTO.getGroupModuleDTOs() != null && groupDTO.getGroupModuleDTOs().size() > 0 )
            {
               for ( GroupModuleDTO groupModuleDTO : groupDTO.getGroupModuleDTOs() )
               {
                  // 标记删除Module与Right的关系
                  if ( groupModuleDTO.getGroupModuleRightRelationVOs() != null && groupModuleDTO.getGroupModuleRightRelationVOs().size() > 0 )
                  {
                     for ( GroupModuleRightRelationVO groupModuleRightRelationVO : groupModuleDTO.getGroupModuleRightRelationVOs() )
                     {
                        groupModuleRightRelationVO.setDeleted( GroupModuleRightRelationVO.FALSE );
                        groupModuleRightRelationVO.setModifyBy( groupVO.getModifyBy() );
                        groupModuleRightRelationVO.setModifyDate( groupVO.getModifyDate() );
                        this.groupModuleRightRelationDao.updateGroupModuleRightRelation( groupModuleRightRelationVO );
                     }
                  }

                  // 标记删除Module与Rule的关系
                  if ( groupModuleDTO.getGroupModuleRuleRelationVOs() != null && groupModuleDTO.getGroupModuleRuleRelationVOs().size() > 0 )
                  {
                     for ( GroupModuleRuleRelationVO groupModuleRuleRelationVO : groupModuleDTO.getGroupModuleRuleRelationVOs() )
                     {
                        groupModuleRuleRelationVO.setDeleted( GroupModuleRuleRelationVO.FALSE );
                        groupModuleRuleRelationVO.setModifyBy( groupVO.getModifyBy() );
                        groupModuleRuleRelationVO.setModifyDate( groupVO.getModifyDate() );
                        this.groupModuleRuleRelationDao.updateGroupModuleRuleRelation( groupModuleRuleRelationVO );
                     }
                  }
               }
            }
         }

         groupVO.setDeleted( GroupVO.FALSE );
         ( ( GroupDao ) getDao() ).updateGroup( groupVO );

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

   @Override
   public List< Object > getGroupVOsByAccountId( String accountId ) throws KANException
   {
      return ( ( GroupDao ) getDao() ).getGroupVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getRelationVOsByPositionId( String positionId ) throws KANException
   {
      return ( ( PositionGroupRelationDao ) getPositionGroupRelationDao() ).getPositionGroupRelationVOsByPositionId( positionId );
   }

   @Override
   public List< GroupDTO > getGroupDTOsByAccountId( String accountId ) throws KANException
   {
      // 创建GroupDTO List，用于返回数据
      final List< GroupDTO > groupDTOs = new ArrayList< GroupDTO >();

      // 获得Account所有有效的职位组
      final List< Object > groupVOs = ( ( GroupDao ) getDao() ).getGroupVOsByAccountId( accountId );
      for ( Object groupVOObject : groupVOs )
      {
         final GroupDTO groupDTO = new GroupDTO();

         // 添加Group对象至GroupDTO
         groupDTO.setGroupVO( ( GroupVO ) groupVOObject );

         // 设置GroupVO的Module操作权限
         groupDTO.setGroupModuleDTOs( getGroupModuleDTOsByGroupId( ( ( GroupVO ) groupVOObject ).getGroupId() ) );

         // 设置GroupVO对应的Position
         final List< Object > positionGroupRelationVOs = positionGroupRelationDao.getPositionGroupRelationVOsByGroupId( ( ( GroupVO ) groupVOObject ).getGroupId() );
         if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
         {
            for ( Object positionGroupRelationVOObject : positionGroupRelationVOs )
            {
               groupDTO.getPositionGroupRelationVOs().add( ( PositionGroupRelationVO ) positionGroupRelationVOObject );
            }
         }

         // 设置GroupVO对应的Rule
         final List< Object > groupModuleRuleRelationVOs = groupModuleRuleRelationDao.getGroupModuleRuleRelationVOsByGroupId( ( ( GroupVO ) groupVOObject ).getGroupId() );
         if ( groupModuleRuleRelationVOs != null && groupModuleRuleRelationVOs.size() > 0 )
         {
            for ( Object groupModuleRuleRelationVO : groupModuleRuleRelationVOs )
            {
               groupDTO.getGroupModuleRuleRelationVOs().add( ( GroupModuleRuleRelationVO ) groupModuleRuleRelationVO );
            }
         }

         // 添加至GroupDTO列表
         groupDTOs.add( groupDTO );
      }

      return groupDTOs;
   }

   /* Add by Kevin at 2013-06-24 */
   private List< GroupModuleDTO > getGroupModuleDTOsByGroupId( final String groupId ) throws KANException
   {
      // 创建GroupModuleDTO List，用于返回数据
      final List< GroupModuleDTO > groupModuleDTOs = new ArrayList< GroupModuleDTO >();

      // 获得当前职位组所关联的Module，Right
      final GroupModuleRightRelationVO groupModuleRightRelationVO = new GroupModuleRightRelationVO( groupId, null, null );
      groupModuleRightRelationVO.setStatus( "1" );
      final List< Object > groupModuleRightRelationVOs = this.getGroupModuleRightRelationVOsByCondition( groupModuleRightRelationVO );

      if ( groupModuleRightRelationVOs != null && groupModuleRightRelationVOs.size() > 0 )
      {
         for ( Object groupModuleRightRelationVOObject : groupModuleRightRelationVOs )
         {
            final String moduleId = ( ( GroupModuleRightRelationVO ) groupModuleRightRelationVOObject ).getModuleId();
            // 从列表中获得GroupModuleDTO实例
            final GroupModuleDTO groupModuleDTO = getGroupModuleDTOFormList( groupModuleDTOs, moduleId );

            // 初始化GroupModuleDTO中的ModuleVO对象
            if ( groupModuleDTO != null )
            {
               if ( groupModuleDTO.getModuleVO() != null && groupModuleDTO.getModuleVO().getModuleId() == null )
               {
                  groupModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // 初始化Module对应的权限
               groupModuleDTO.getGroupModuleRightRelationVOs().add( ( GroupModuleRightRelationVO ) groupModuleRightRelationVOObject );
            }
         }
      }

      // 获得当前职位组所关联的Module，Rule
      final GroupModuleRuleRelationVO groupModuleRuleRelationVO = new GroupModuleRuleRelationVO( groupId, null, null );
      groupModuleRuleRelationVO.setStatus( "1" );
      final List< Object > groupModuleRuleRelationVOs = this.getGroupModuleRuleRelationVOsByCondition( groupModuleRuleRelationVO );

      if ( groupModuleRuleRelationVOs != null && groupModuleRuleRelationVOs.size() > 0 )
      {
         for ( Object groupModuleRuleRelationVOObject : groupModuleRuleRelationVOs )
         {
            final String moduleId = ( ( GroupModuleRuleRelationVO ) groupModuleRuleRelationVOObject ).getModuleId();
            // 从列表中获得GroupModuleDTO实例
            final GroupModuleDTO groupModuleDTO = getGroupModuleDTOFormList( groupModuleDTOs, moduleId );

            // 初始化GroupModuleDTO中的ModuleVO对象
            if ( groupModuleDTO != null )
            {
               if ( groupModuleDTO.getModuleVO() != null && groupModuleDTO.getModuleVO().getModuleId() == null )
               {
                  groupModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // 初始化Module对应的权限
               groupModuleDTO.getGroupModuleRuleRelationVOs().add( ( GroupModuleRuleRelationVO ) groupModuleRuleRelationVOObject );
            }
         }
      }

      return groupModuleDTOs;
   }

   // 从列表中获得GroupModuleDTO
   private GroupModuleDTO getGroupModuleDTOFormList( final List< GroupModuleDTO > groupModuleDTOs, final String moduleId )
   {
      // 如果当前需要查找的DTO列表不为空，并且ModuleId也不为空
      if ( groupModuleDTOs != null && moduleId != null )
      {
         // DTO列表中含有的对象数大于0
         if ( groupModuleDTOs.size() > 0 )
         {
            // 遍历DTO列表找到匹配的并返回
            for ( GroupModuleDTO groupModuleDTO : groupModuleDTOs )
            {
               if ( groupModuleDTO.getModuleVO() != null && groupModuleDTO.getModuleVO().getModuleId().equals( moduleId ) )
               {
                  return groupModuleDTO;
               }
            }
         }

         // 如果找不到目标对象，则创建实例
         final GroupModuleDTO groupModuleDTO = new GroupModuleDTO();
         groupModuleDTOs.add( groupModuleDTO );
         return groupModuleDTO;
      }

      return null;
   }

   @Override
   public List< Object > getGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException
   {
      return ( ( GroupModuleRightRelationDao ) getGroupModuleRightRelationDao() ).getGroupModuleRightRelationVOsByCondition( groupModuleRightRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return ( ( GroupModuleRuleRelationDao ) getGroupModuleRuleRelationDao() ).getGroupModuleRuleRelationVOsByCondition( groupModuleRuleRelationVO );
   }

   @Override
   public List< Object > getGroupBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( GroupDao ) getDao() ).getGroupBaseViewsByAccountId( accountId );
   }

   @Override
   public int countPositionGroupRelationVOsByGroupId( final String groupId ) throws KANException
   {
      return this.positionGroupRelationDao.countPositionGroupRelationVOsByGroupId( groupId );
   }

}

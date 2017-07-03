package com.kan.base.service.impl.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.BranchDao;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.security.PositionGroupRelationDao;
import com.kan.base.dao.inf.security.PositionModuleRightRelationDao;
import com.kan.base.dao.inf.security.PositionModuleRuleRelationDao;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionModuleDTO;
import com.kan.base.domain.security.PositionModuleRightRelationVO;
import com.kan.base.domain.security.PositionModuleRuleRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class PositionServiceImpl extends ContextService implements PositionService
{

   // 注入PositionGroupRelationDao
   private PositionGroupRelationDao positionGroupRelationDao;
   // 注入PositionStaffRelationDao
   private PositionStaffRelationDao positionStaffRelationDao;
   // 注入PositionModuleRightRelationDao
   private PositionModuleRightRelationDao positionModuleRightRelationDao;
   // 注入PositionModuleRuleRelationDao
   private PositionModuleRuleRelationDao positionModuleRuleRelationDao;
   // 注入ModuleDaos
   private ModuleDao moduleDao;
   // 注入StaffDao
   private StaffDao staffDao;
   // 注入EmployeeDao
   private EmployeeDao employeeDao;
   // 注入BranchDao
   private BranchDao branchDao;

   public BranchDao getBranchDao()
   {
      return branchDao;
   }

   public void setBranchDao( BranchDao branchDao )
   {
      this.branchDao = branchDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }

   public PositionGroupRelationDao getPositionGroupRelationDao()
   {
      return positionGroupRelationDao;
   }

   public void setPositionGroupRelationDao( PositionGroupRelationDao positionGroupRelationDao )
   {
      this.positionGroupRelationDao = positionGroupRelationDao;
   }

   public PositionStaffRelationDao getPositionStaffRelationDao()
   {
      return positionStaffRelationDao;
   }

   public void setPositionStaffRelationDao( PositionStaffRelationDao positionStaffRelationDao )
   {
      this.positionStaffRelationDao = positionStaffRelationDao;
   }

   public PositionModuleRightRelationDao getPositionModuleRightRelationDao()
   {
      return positionModuleRightRelationDao;
   }

   public void setPositionModuleRightRelationDao( PositionModuleRightRelationDao positionModuleRightRelationDao )
   {
      this.positionModuleRightRelationDao = positionModuleRightRelationDao;
   }

   public PositionModuleRuleRelationDao getPositionModuleRuleRelationDao()
   {
      return positionModuleRuleRelationDao;
   }

   public void setPositionModuleRuleRelationDao( PositionModuleRuleRelationDao positionModuleRuleRelationDao )
   {
      this.positionModuleRuleRelationDao = positionModuleRuleRelationDao;
   }

   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }

   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }

   @Override
   public PagedListHolder getPositionVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PositionDao positionDao = ( PositionDao ) getDao();
      pagedListHolder.setHolderSize( positionDao.countPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( positionDao.getPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( positionDao.getPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public PositionVO getPositionVOByPositionId( final String positionId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionVOByPositionId( positionId );
   }

   @Override
   // Code review by Kevin at 2013-06-14
   public int updatePosition( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 未修改前remove掉staffId对应的EmployeeVO的“_temp...”字段
         removeEmployeeVOTempColumn( positionVO );

         // Update PositionVO
         ( ( PositionDao ) getDao() ).updatePosition( positionVO );

         // 重新建立Position与Group的关联
         insertPositionGroupRelation( positionVO );

         // 重新建立Position与Staff的关联
         insertPositionStaffRelation( positionVO );

         // 更改职位时，需要对该职位下级职位上的EmployeeVO的_tempParentPositionOwners更新
         updateSubPositionVOs( positionVO );

         // 重新建立Position与Module的关联（模态框修改已入数据库、不用再次获取）
         //         insertPositionModuleRelation( positionVO );

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

   // 更改职位时，需要对该职位下级职位上的EmployeeVO的_tempParentPositionOwners更新
   private void updateSubPositionVOs( final PositionVO parentPositionVO ) throws KANException
   {
      final PositionVO searchSubPositionVO = new PositionVO();
      searchSubPositionVO.setAccountId( parentPositionVO.getAccountId() );
      searchSubPositionVO.setParentPositionId( parentPositionVO.getPositionId() );

      final List< Object > subPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( searchSubPositionVO );
      if ( subPositionVOs != null && subPositionVOs.size() > 0 )
      {
         final StringBuffer staffIds = new StringBuffer();
         for ( String staffInfos : parentPositionVO.getStaffIdArray() )
         {
            // StaffInfos 保存着StaffId，StaffType，AgentStart和AgentEnd
            final String[] staffInfoArray = staffInfos.split( "_" );
            if ( staffInfoArray != null && staffInfoArray.length > 1 )
            {
               if ( KANUtil.filterEmpty( staffIds.toString() ) == null )
               {
                  staffIds.append( staffInfoArray[ 0 ] );
               }
               else
               {
                  staffIds.append( "," + staffInfoArray[ 0 ] );
               }
            }
         }

         for ( Object subPositionVOObject : subPositionVOs )
         {
            final PositionVO subPositionVO = ( PositionVO ) subPositionVOObject;
            final List< Object > subPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( subPositionVO.getPositionId() );

            // 存在获取PositionStaffRelationVO列表
            if ( subPositionStaffRelationVOs != null && subPositionStaffRelationVOs.size() > 0 )
            {
               for ( Object o : subPositionStaffRelationVOs )
               {
                  final PositionStaffRelationVO subPositionStaffRelationVO = ( PositionStaffRelationVO ) o;
                  if ( subPositionStaffRelationVO != null && KANUtil.filterEmpty( subPositionStaffRelationVO.getStaffId() ) != null )
                  {
                     final StaffVO staffVO = this.getStaffDao().getStaffVOByStaffId( subPositionStaffRelationVO.getStaffId() );
                     if ( staffVO != null )
                     {
                        final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
                        if ( employeeVO != null )
                        {
                           employeeVO.set_tempParentPositionOwners( staffIds.toString() );
                           // 持久化EmployeeVO
                           this.getEmployeeDao().updateEmployee( employeeVO );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   // Add by siuvan.xia at 2014-06-30
   private void removeEmployeeVOTempColumn( final PositionVO positionVO ) throws KANException
   {
      // 获取PositionStaffRelationVO列表
      final List< Object > positionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( positionVO.getPositionId() );

      // 存在获取PositionStaffRelationVO列表
      if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( Object o : positionStaffRelationVOs )
         {
            final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) o;
            if ( positionStaffRelationVO != null && KANUtil.filterEmpty( positionStaffRelationVO.getStaffId() ) != null )
            {
               final StaffVO staffVO = this.getStaffDao().getStaffVOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffVO != null )
               {
                  final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
                  if ( employeeVO != null )
                  {
                     final List< String > _tempPositionIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempPositionIds() );
                     final List< String > _tempBranchIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempBranchIds() );
                     final List< String > _tempParentBranchIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentBranchIds() );
                     final List< String > _tempParentPositionIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentPositionIds() );
                     final List< String > _tempParentPositionOwnerArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentPositionOwners() );
                     final List< String > _tempParentPositionBranchIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentPositionBranchIds() );
                     final List< String > _tempPositionLocationIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempPositionLocationIds() );
                     final List< String > _tempPositionGradeIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempPositionGradeIds() );

                     if ( _tempPositionIdArray != null && _tempPositionIdArray.size() > 0 )
                     {
                        _tempPositionIdArray.remove( positionVO.getPositionId() );
                     }
                     if ( _tempBranchIdArray != null && _tempBranchIdArray.size() > 0 )
                     {
                        _tempBranchIdArray.remove( positionVO.getBranchId() );
                     }
                     if ( _tempParentBranchIdArray != null && _tempParentBranchIdArray.size() > 0 )
                     {
                        final BranchVO branchVO = this.getBranchDao().getBranchVOByBranchId( positionVO.getBranchId() );
                        if ( branchVO != null )
                        {
                           _tempParentBranchIdArray.remove( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId()
                                 : branchVO.getParentBranchId() );
                        }
                     }
                     if ( _tempParentPositionIdArray != null && _tempParentPositionIdArray.size() > 0 )
                     {
                        _tempParentPositionIdArray.remove( positionVO.getParentPositionId() );
                     }

                     if ( _tempParentPositionOwnerArray != null && _tempParentPositionOwnerArray.size() > 0 )
                     {
                        // 获取上级职位和Staff的关系集合
                        final List< Object > parentPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( positionVO.getParentPositionId() );

                        if ( parentPositionStaffRelationVOs != null && parentPositionStaffRelationVOs.size() > 0 )
                        {
                           for ( Object parentObject : parentPositionStaffRelationVOs )
                           {
                              _tempParentPositionOwnerArray.remove( ( ( PositionStaffRelationVO ) parentObject ).getStaffId() );
                           }
                        }
                     }

                     final PositionVO parentPositionVO = this.getPositionVOByPositionId( positionVO.getParentPositionId() );
                     if ( parentPositionVO != null && _tempParentPositionBranchIdArray != null && _tempParentPositionBranchIdArray.size() > 0 )
                     {
                        _tempParentPositionBranchIdArray.remove( parentPositionVO.getBranchId() );
                     }

                     if ( positionVO != null && _tempPositionLocationIdArray != null && _tempPositionLocationIdArray.size() > 0 )
                     {
                        _tempPositionLocationIdArray.remove( positionVO.getLocationId() );
                     }

                     if ( _tempPositionGradeIdArray != null && _tempPositionGradeIdArray.size() > 0 )
                     {
                        _tempPositionGradeIdArray.remove( positionVO.getPositionGradeId() );
                     }

                     employeeVO.set_tempPositionIds( KANUtil.stringListToJasonArray( _tempPositionIdArray, "," ) );
                     employeeVO.set_tempBranchIds( KANUtil.stringListToJasonArray( _tempBranchIdArray, "," ) );
                     employeeVO.set_tempParentBranchIds( KANUtil.stringListToJasonArray( _tempParentBranchIdArray, "," ) );
                     employeeVO.set_tempParentPositionIds( KANUtil.stringListToJasonArray( _tempParentPositionIdArray, "," ) );
                     employeeVO.set_tempParentPositionOwners( KANUtil.stringListToJasonArray( _tempParentPositionOwnerArray, "," ) );
                     employeeVO.set_tempParentPositionBranchIds( KANUtil.stringListToJasonArray( _tempParentPositionBranchIdArray, "," ) );
                     employeeVO.set_tempPositionLocationIds( KANUtil.stringListToJasonArray( _tempPositionLocationIdArray, "," ) );
                     employeeVO.set_tempPositionGradeIds( KANUtil.stringListToJasonArray( _tempPositionGradeIdArray, "," ) );

                     if ( KANUtil.filterEmpty( employeeVO.get_tempPositionIds() ) != null )
                     {
                        employeeVO.set_tempPositionIds( employeeVO.get_tempPositionIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempBranchIds() ) != null )
                     {
                        employeeVO.set_tempBranchIds( employeeVO.get_tempBranchIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentBranchIds() ) != null )
                     {
                        employeeVO.set_tempParentBranchIds( employeeVO.get_tempParentBranchIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentPositionIds() ) != null )
                     {
                        employeeVO.set_tempParentPositionIds( employeeVO.get_tempParentPositionIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentPositionOwners() ) != null )
                     {
                        employeeVO.set_tempParentPositionOwners( employeeVO.get_tempParentPositionOwners().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentPositionBranchIds() ) != null )
                     {
                        employeeVO.set_tempParentPositionBranchIds( employeeVO.get_tempParentPositionBranchIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempPositionLocationIds() ) != null )
                     {
                        employeeVO.set_tempPositionLocationIds( employeeVO.get_tempPositionLocationIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempPositionGradeIds() ) != null )
                     {
                        employeeVO.set_tempPositionGradeIds( employeeVO.get_tempPositionGradeIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     // 持久化EmployeeVO
                     this.getEmployeeDao().updateEmployee( employeeVO );
                  }
               }
            }
         }
      }
   }

   @Override
   // Add by Kevin at 2013-06-14
   public int updatePositionModule( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 重新建立Position与Module的关联
         insertPositionModuleRelation( positionVO );

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
    * UpdatePositionModuleRelationPopup
    *	模态框修改PositionId对应单条ModuleId的权限规则
    *	@param positionVO
    *	@param moduleId
    *	@return
    *	@throws KANException
    */
   @Override
   public int updatePositionModuleRelationPopup( final PositionVO positionVO, final String moduleId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 重新建立Position与Module的关联
         insertPositionModuleRightRelation( positionVO, moduleId );
         insertPositionModuleRuleRelation( positionVO, moduleId );

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
   public int insertPosition( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // Insert PositionVO
         ( ( PositionDao ) getDao() ).insertPosition( positionVO );

         // 重新建立Position与Group的关联
         insertPositionGroupRelation( positionVO );

         // 重新建立Position与Staff的关联
         insertPositionStaffRelation( positionVO );

         // 重新建立Position与Module的关联
         insertPositionModuleRelation( positionVO );

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
   public int deletePosition( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 从常量获取当前节点开始的职位树
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( positionVO.getAccountId() ).getPositionDTOByPositionId( positionVO.getPositionId() );

         // 递归调用标记删除方法
         deletePosition( positionDTO, positionVO.getModifyBy(), positionVO.getModifyDate() );

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

   // 标记删除Position的方法，递归
   private void deletePosition( final PositionDTO positionDTO, final String modifyBy, final Date modifyDate ) throws KANException
   {
      if ( positionDTO != null )
      {
         if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
         {
            for ( PositionDTO subPositionDTO : positionDTO.getPositionDTOs() )
            {
               deletePosition( subPositionDTO, modifyBy, modifyDate );
            }
         }

         // 标记删除Position与Group的关系
         if ( positionDTO.getPositionGroupRelationVOs() != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
         {
            for ( PositionGroupRelationVO positionGroupRelationVO : positionDTO.getPositionGroupRelationVOs() )
            {
               positionGroupRelationVO.setDeleted( PositionGroupRelationVO.FALSE );
               positionGroupRelationVO.setModifyBy( modifyBy );
               positionGroupRelationVO.setModifyDate( modifyDate );
               this.positionGroupRelationDao.updatePositionGroupRelation( positionGroupRelationVO );
            }
         }

         // 标记删除Position与Staff的关系
         if ( positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
         {
            for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
            {
               positionStaffRelationVO.setDeleted( PositionStaffRelationVO.FALSE );
               positionStaffRelationVO.setModifyBy( modifyBy );
               positionStaffRelationVO.setModifyDate( modifyDate );
               this.positionStaffRelationDao.updatePositionStaffRelation( positionStaffRelationVO );
            }
         }

         // 标记删除Position与Module的关系
         if ( positionDTO.getPositionModuleDTOs() != null && positionDTO.getPositionModuleDTOs().size() > 0 )
         {
            for ( PositionModuleDTO positionModuleDTO : positionDTO.getPositionModuleDTOs() )
            {
               // 标记删除Module与Right的关系
               if ( positionModuleDTO.getPositionModuleRightRelationVOs() != null && positionModuleDTO.getPositionModuleRightRelationVOs().size() > 0 )
               {
                  for ( PositionModuleRightRelationVO positionModuleRightRelationVO : positionModuleDTO.getPositionModuleRightRelationVOs() )
                  {
                     positionModuleRightRelationVO.setDeleted( PositionModuleRightRelationVO.FALSE );
                     positionModuleRightRelationVO.setModifyBy( modifyBy );
                     positionModuleRightRelationVO.setModifyDate( modifyDate );
                     this.positionModuleRightRelationDao.updatePositionModuleRightRelation( positionModuleRightRelationVO );
                  }
               }

               // 标记删除Module与Rule的关系
               if ( positionModuleDTO.getPositionModuleRuleRelationVOs() != null && positionModuleDTO.getPositionModuleRuleRelationVOs().size() > 0 )
               {
                  for ( PositionModuleRuleRelationVO positionModuleRuleRelationVO : positionModuleDTO.getPositionModuleRuleRelationVOs() )
                  {
                     positionModuleRuleRelationVO.setDeleted( PositionModuleRuleRelationVO.FALSE );
                     positionModuleRuleRelationVO.setModifyBy( modifyBy );
                     positionModuleRuleRelationVO.setModifyDate( modifyDate );
                     this.positionModuleRuleRelationDao.updatePositionModuleRuleRelation( positionModuleRuleRelationVO );
                  }
               }
            }
         }

         // 最后标记删除Position
         if ( positionDTO.getPositionVO() != null )
         {
            final PositionVO positionVO = positionDTO.getPositionVO();
            positionVO.setDeleted( PositionVO.FALSE );
            positionVO.setModifyBy( modifyBy );
            positionVO.setModifyDate( modifyDate );
            ( ( PositionDao ) getDao() ).updatePosition( positionVO );
         }
      }
   }

   // 建立Position与Group之间的关联
   private int insertPositionGroupRelation( final PositionVO positionVO ) throws KANException
   {
    
         // 先删除Position对应关系表
         this.positionGroupRelationDao.deletePositionGroupRelationByPositionId( positionVO.getPositionId() );
         // Position 需要添加职位组
         if ( positionVO.getGroupIdArray() != null && positionVO.getGroupIdArray().length > 0 )
         {

            // 循环添加Position跟Group的关系
            for ( String groupId : positionVO.getGroupIdArray() )
            {
               final PositionGroupRelationVO positionGroupRelationVO = new PositionGroupRelationVO();
               positionGroupRelationVO.setPositionId( positionVO.getPositionId() );
               positionGroupRelationVO.setGroupId( groupId );
               positionGroupRelationVO.setCreateBy( positionVO.getModifyBy() );
               positionGroupRelationVO.setModifyBy( positionVO.getModifyBy() );
               this.positionGroupRelationDao.insertPositionGroupRelation( positionGroupRelationVO );
            }
         }
      
     
      return 0;
   }

   // 建立Position与Staff之间的关联 & 反关联StaffId对应的EmployeeVO中的4个“_temp”字段
   // Modify by Siuvan Xia at 2014-8-29
   private int insertPositionStaffRelation( final PositionVO positionVO ) throws KANException
   {
     
         // 先删除Position对应关系表
         this.positionStaffRelationDao.deletePositionStaffRelationByPositionId( positionVO.getPositionId() );

         // Position 需要添加员工
         if ( positionVO.getStaffIdArray() != null && positionVO.getStaffIdArray().length > 0 )
         {
            // 循环添加Position跟Staff的关系
            for ( String staffInfos : positionVO.getStaffIdArray() )
            {
               // StaffInfos 保存着StaffId，StaffType，AgentStart和AgentEnd
               final String[] staffInfoArray = staffInfos.split( "_" );
               String staffId = "";
               if ( staffInfoArray != null && staffInfoArray.length > 1 )
               {
                  staffId = staffInfoArray[ 0 ];
                  final PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
                  positionStaffRelationVO.setPositionId( positionVO.getPositionId() );
                  positionStaffRelationVO.setStaffId( staffInfoArray[ 0 ] );
                  positionStaffRelationVO.setStaffType( staffInfoArray[ 1 ] );
                  if ( staffInfoArray[ 1 ] != null && staffInfoArray[ 1 ].trim().equals( "2" ) && staffInfoArray.length > 3 )
                  {

                     if ( KANUtil.filterEmpty( staffInfoArray[ 2 ] ) == null )
                     {
                        staffInfoArray[ 2 ] = null;
                     }

                     if ( KANUtil.filterEmpty( staffInfoArray[ 3 ] ) == null )
                     {
                        staffInfoArray[ 3 ] = null;
                     }

                     positionStaffRelationVO.setAgentStart( staffInfoArray[ 2 ] );
                     positionStaffRelationVO.setAgentEnd( staffInfoArray[ 3 ] );
                  }
                  positionStaffRelationVO.setCreateBy( positionVO.getModifyBy() );
                  positionStaffRelationVO.setModifyBy( positionVO.getModifyBy() );
                  this.positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
               }

               if ( KANUtil.filterEmpty( staffId ) != null )
               {
                  // 获取StaffVO
                  final StaffVO staffVO = this.getStaffDao().getStaffVOByStaffId( staffId );

                  if ( staffVO != null )
                  {
                     // 获取EmployeeVO
                     final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );

                     // 获取当前staffId对应的PositionStaffRelationVO列表
                     final List< Object > positionStaffRelationVOs = this.positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( staffId );

                     // 存在PositionStaffRelationVO列表
                     if ( employeeVO != null && positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                     {
                        // 初始化职位字符串
                        final StringBuffer _tempPositionIds = new StringBuffer();

                        // 初始化部门字符串
                        final StringBuffer _tempBranchIds = new StringBuffer();

                        // 初始化上级部门字符串
                        final StringBuffer _tempParentBranchIds = new StringBuffer();

                        // 初始化上级职位字符串
                        final StringBuffer _tempParentPositionIds = new StringBuffer();

                        // 初始化上级职位所属人字符串
                        final StringBuffer _tempParentPositionOwners = new StringBuffer();

                        // 初始化上级职位部门字符串
                        final StringBuffer _tempParentPositionBranchIds = new StringBuffer();

                        // 初始化工作地址字符串
                        final StringBuffer _tempPositionLocationIds = new StringBuffer();

                        // 初始化职级字符串
                        final StringBuffer _tempPositionGradeIds = new StringBuffer();

                        for ( Object o : positionStaffRelationVOs )
                        {
                           final PositionVO staffPositionVO = ( ( PositionDao ) getDao() ).getPositionVOByPositionId( ( ( PositionStaffRelationVO ) o ).getPositionId() );
                           if ( staffPositionVO != null )
                           {
                              // 职位
                              if ( KANUtil.filterEmpty( _tempPositionIds.toString() ) == null )
                              {
                                 _tempPositionIds.append( staffPositionVO.getPositionId() );
                              }
                              else
                              {
                                 if ( !ArrayUtils.contains( _tempPositionIds.toString().split( "," ), staffPositionVO.getPositionId() ) )
                                    _tempPositionIds.append( "," + staffPositionVO.getPositionId() );
                              }

                              // 工作部门
                              if ( KANUtil.filterEmpty( _tempBranchIds.toString() ) == null )
                              {
                                 _tempBranchIds.append( staffPositionVO.getBranchId() );
                              }
                              else
                              {
                                 if ( !ArrayUtils.contains( _tempBranchIds.toString().split( "," ), staffPositionVO.getBranchId() ) )
                                    _tempBranchIds.append( "," + staffPositionVO.getBranchId() );
                              }

                              // 上级部门
                              if ( KANUtil.filterEmpty( staffPositionVO.getBranchId(), "0" ) != null )
                              {
                                 final BranchVO branchVO = this.getBranchDao().getBranchVOByBranchId( staffPositionVO.getBranchId() );
                                 if ( branchVO != null && KANUtil.filterEmpty( _tempParentBranchIds.toString() ) == null )
                                 {
                                    _tempParentBranchIds.append( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId()
                                          : branchVO.getParentBranchId() );
                                 }
                                 else if ( branchVO != null )
                                 {
                                    if ( !ArrayUtils.contains( _tempParentBranchIds.toString().split( "," ), ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId()
                                          : branchVO.getParentBranchId() ) ) )
                                       _tempParentBranchIds.append( ","
                                             + ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId() : branchVO.getParentBranchId() ) );
                                 }
                              }

                              // 上级职位
                              if ( KANUtil.filterEmpty( staffPositionVO.getParentPositionId() ) != null )
                              {
                                 if ( KANUtil.filterEmpty( _tempParentPositionIds.toString() ) == null )
                                 {
                                    _tempParentPositionIds.append( staffPositionVO.getParentPositionId() );
                                 }
                                 else
                                 {
                                    if ( !ArrayUtils.contains( _tempParentPositionIds.toString().split( "," ), staffPositionVO.getParentPositionId() ) )
                                       _tempParentPositionIds.append( "," + staffPositionVO.getParentPositionId() );
                                 }
                              }

                              // 上级职位所属人
                              if ( KANUtil.filterEmpty( staffPositionVO.getParentPositionId() ) != null )
                              {
                                 final List< Object > parentPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( staffPositionVO.getParentPositionId() );
                                 if ( parentPositionStaffRelationVOs != null && parentPositionStaffRelationVOs.size() > 0 )
                                 {
                                    for ( Object parentObject : parentPositionStaffRelationVOs )
                                    {
                                       final PositionStaffRelationVO parentPositionStaffRelationVO = ( PositionStaffRelationVO ) parentObject;
                                       if ( parentPositionStaffRelationVO != null )
                                       {
                                          if ( KANUtil.filterEmpty( _tempParentPositionOwners.toString() ) == null )
                                          {
                                             _tempParentPositionOwners.append( parentPositionStaffRelationVO.getStaffId() );
                                          }
                                          else
                                          {
                                             if ( !ArrayUtils.contains( _tempParentPositionOwners.toString().split( "," ), parentPositionStaffRelationVO.getStaffId() ) )
                                                _tempParentPositionOwners.append( "," + parentPositionStaffRelationVO.getStaffId() );
                                          }
                                       }
                                    }
                                 }
                              }

                              // 上级职位部门
                              if ( KANUtil.filterEmpty( staffPositionVO.getParentPositionId() ) != null )
                              {
                                 final PositionVO parentPositionVO = this.getPositionVOByPositionId( staffPositionVO.getParentPositionId() );
                                 if ( parentPositionVO != null )
                                 {
                                    if ( KANUtil.filterEmpty( _tempParentPositionBranchIds.toString() ) == null )
                                    {
                                       _tempParentPositionBranchIds.append( parentPositionVO.getBranchId() );
                                    }
                                    else
                                    {
                                       if ( !ArrayUtils.contains( _tempParentPositionBranchIds.toString().split( "," ), parentPositionVO.getBranchId() ) )
                                          _tempParentPositionBranchIds.append( "," + parentPositionVO.getBranchId() );
                                    }
                                 }
                              }

                              // 工作地址
                              if ( KANUtil.filterEmpty( staffPositionVO.getLocationId() ) != null )
                              {
                                 if ( KANUtil.filterEmpty( _tempPositionLocationIds.toString() ) == null )
                                 {
                                    _tempPositionLocationIds.append( staffPositionVO.getLocationId() );
                                 }
                                 else
                                 {
                                    if ( !ArrayUtils.contains( _tempPositionLocationIds.toString().split( "," ), staffPositionVO.getLocationId() ) )
                                       _tempPositionLocationIds.append( "," + staffPositionVO.getLocationId() );
                                 }
                              }

                              // 职级
                              if ( KANUtil.filterEmpty( staffPositionVO.getPositionGradeId(), "0" ) != null )
                              {
                                 if ( KANUtil.filterEmpty( _tempPositionGradeIds.toString() ) == null )
                                 {
                                    _tempPositionGradeIds.append( staffPositionVO.getPositionGradeId() );
                                 }
                                 else
                                 {
                                    if ( !ArrayUtils.contains( _tempPositionGradeIds.toString().split( "," ), staffPositionVO.getPositionGradeId() ) )
                                       _tempPositionGradeIds.append( "," + staffPositionVO.getPositionGradeId() );
                                 }
                              }
                           }
                        }

                        employeeVO.set_tempPositionIds( _tempPositionIds.toString() );
                        employeeVO.set_tempBranchIds( _tempBranchIds.toString() );
                        employeeVO.set_tempParentBranchIds( _tempParentBranchIds.toString() );
                        employeeVO.set_tempParentPositionIds( _tempParentPositionIds.toString() );
                        employeeVO.set_tempParentPositionOwners( _tempParentPositionOwners.toString() );
                        employeeVO.set_tempParentPositionBranchIds( _tempParentPositionBranchIds.toString() );
                        employeeVO.set_tempPositionLocationIds( _tempPositionLocationIds.toString() );
                        employeeVO.set_tempPositionGradeIds( _tempPositionGradeIds.toString() );

                        // 持久化EmployeeVO
                        this.getEmployeeDao().updateEmployee( employeeVO );
                     }
                  }
               }
            }
         }
     

      return 0;
   }

   // 建立Position与Module之间的关联
   private int insertPositionModuleRelation( final PositionVO positionVO ) throws KANException
   {
      try
      {
         if ( positionVO.getModuleIdArray() != null && positionVO.getModuleIdArray().length > 0 )
         {
            for ( String moduleId : positionVO.getModuleIdArray() )
            {
               // 添加PositionId 对应 ModuleId的权限、规则
               insertPositionModuleRightRelation( positionVO, moduleId );
               insertPositionModuleRuleRelation( positionVO, moduleId );
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
    * InsertPositionModuleRightRelation
    *	添加PositionId 对应 ModuleId的权限
    *	@param positionVO
    *	@param moduleId
    *	@param moduleId
    *	@throws KANException
    */
   private void insertPositionModuleRightRelation( final PositionVO positionVO, final String moduleId ) throws KANException
   {
      // 先删除PositionId对应 ModuleId所有权限
      this.positionModuleRightRelationDao.deletePositionModuleRightRelationByCondition( new PositionModuleRightRelationVO( positionVO.getPositionId(), moduleId, null ) );

      // Position 添加权限
      if ( positionVO.getRightIdArray() != null && positionVO.getRightIdArray().length > 0 )
      {
         // 循环添加Position、Module和Right的关系
         for ( String rightId : positionVO.getRightIdArray() )
         {
            final PositionModuleRightRelationVO positionModuleRightRelationVO = new PositionModuleRightRelationVO();
            positionModuleRightRelationVO.setPositionId( positionVO.getPositionId() );
            positionModuleRightRelationVO.setModuleId( moduleId );
            positionModuleRightRelationVO.setRightId( rightId );
            positionModuleRightRelationVO.setCreateBy( positionVO.getModifyBy() );
            positionModuleRightRelationVO.setModifyBy( positionVO.getModifyBy() );
            this.positionModuleRightRelationDao.insertPositionModuleRightRelation( positionModuleRightRelationVO );
         }
      }

   }

   /**  
    * InsertPositionModuleRuleRelation
    *	添加PositionId 对应 ModuleId的规则
    *	@param positionVO
    *	@param moduleId
    *	@param moduleId
    *	@throws KANException
    */
   private void insertPositionModuleRuleRelation( final PositionVO positionVO, final String moduleId ) throws KANException
   {
      // 先删除Position及Module对应关系表
      this.positionModuleRuleRelationDao.deletePositionModuleRuleRelationByCondition( new PositionModuleRuleRelationVO( positionVO.getPositionId(), moduleId, null ) );

      // Position 添加规则
      if ( positionVO.getRuleIdArray() != null && positionVO.getRuleIdArray().length > 0 )
      {
         // 循环添加Position、Module和Rule的关系
         for ( String ruleId : positionVO.getRuleIdArray() )
         {
            if ( ruleId != null && ruleId.split( "_" ).length == 2 )
            {
               final PositionModuleRuleRelationVO positionModuleRuleRelationVO = new PositionModuleRuleRelationVO();
               positionModuleRuleRelationVO.setPositionId( positionVO.getPositionId() );
               positionModuleRuleRelationVO.setModuleId( moduleId );
               positionModuleRuleRelationVO.setRuleId( ruleId.split( "_" )[ 1 ] );
               positionModuleRuleRelationVO.setCreateBy( positionVO.getModifyBy() );
               positionModuleRuleRelationVO.setModifyBy( positionVO.getModifyBy() );
               this.positionModuleRuleRelationDao.insertPositionModuleRuleRelation( positionModuleRuleRelationVO );
            }
         }
      }

   }

   @Override
   public List< Object > getPositionVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getRelationVOsByGroupId( final String groupId ) throws KANException
   {
      return ( ( PositionGroupRelationDao ) getPositionGroupRelationDao() ).getPositionGroupRelationVOsByGroupId( groupId );
   }

   @Override
   /* Add by Kevin at 2013-06-08 */
   public List< Object > getRelationVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionStaffRelationDao ) getPositionStaffRelationDao() ).getPositionStaffRelationVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getRelationVOsByStaffId( final String staffId ) throws KANException
   {
      return ( ( PositionStaffRelationDao ) getPositionStaffRelationDao() ).getPositionStaffRelationVOsByStaffId( staffId );
   }

   @Override
   public List< Object > getPositionBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionBaseViewsByAccountId( accountId );
   }

   @Override
   /* Add by Kevin at 2013-06-06 */
   public List< PositionDTO > getPositionDTOsByAccountId( final String accountId ) throws KANException
   {
      // 创建PositionDTO List，用于返回数据
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

      // 新建PositionVO用于传参
      final PositionVO positionVO = new PositionVO();
      // 默认根节点的父节点值为“0”
      positionVO.setParentPositionId( "0" );
      positionVO.setAccountId( accountId );

      // 获得根节点
      final List< Object > rootPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( positionVO );

      for ( Object rootPositionObject : rootPositionVOs )
      {
         // 递归遍历
         positionDTOs.add( fetchPositionDTO( ( PositionVO ) rootPositionObject ) );
      }

      return positionDTOs;
   }

   // 递归方法
   /* Add by Kevin at 2013-06-06 */
   private PositionDTO fetchPositionDTO( final PositionVO positionVO ) throws KANException
   {
      final PositionDTO positionDTO = new PositionDTO();
      // 设置PositionVO对象
      positionDTO.setPositionVO( positionVO );

      // 设置PositionVO对应的Group
      final List< Object > positionGroupRelationVOs = positionGroupRelationDao.getPositionGroupRelationVOsByPositionId( positionVO.getPositionId() );
      if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
      {
         for ( Object positionGroupRelationVOObject : positionGroupRelationVOs )
         {
            positionDTO.getPositionGroupRelationVOs().add( ( PositionGroupRelationVO ) positionGroupRelationVOObject );
         }
      }

      // 设置PositionVO绑定的员工
      final List< Object > positionStaffRelationVOs = positionStaffRelationDao.getPositionStaffRelationVOsByPositionId( positionVO.getPositionId() );
      if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
         {
            positionDTO.getPositionStaffRelationVOs().add( ( PositionStaffRelationVO ) positionStaffRelationVOObject );
         }
      }

      // 设置PositionVO的Module操作权限
      positionDTO.setPositionModuleDTOs( getPositionModuleDTOsByPositionId( positionVO.getPositionId() ) );

      // 继续查找下一层Position
      final PositionVO subPositionVO = new PositionVO();
      subPositionVO.setAccountId( positionVO.getAccountId() );
      subPositionVO.setParentPositionId( positionVO.getPositionId() );
      final List< Object > subPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( subPositionVO );

      for ( Object subPositionObject : subPositionVOs )
      {
         // 递归调用
         positionDTO.getPositionDTOs().add( fetchPositionDTO( ( PositionVO ) subPositionObject ) );
      }

      return positionDTO;
   }

   /* Add by Kevin at 2013-06-17, Need to merge the account public right & rule */
   private List< PositionModuleDTO > getPositionModuleDTOsByPositionId( final String positionId ) throws KANException
   {
      // 创建ModuleDTO List，用于返回数据
      final List< PositionModuleDTO > positionModuleDTOs = new ArrayList< PositionModuleDTO >();

      // 获得当前职位所关联的Module，Right
      final PositionModuleRightRelationVO positionModuleRightRelationVO = new PositionModuleRightRelationVO( positionId, null, null );
      positionModuleRightRelationVO.setStatus( "1" );
      final List< Object > positionModuleRightRelationVOs = this.getPositionModuleRightRelationVOsByCondition( positionModuleRightRelationVO );

      if ( positionModuleRightRelationVOs != null && positionModuleRightRelationVOs.size() > 0 )
      {
         for ( Object positionModuleRightRelationVOObject : positionModuleRightRelationVOs )
         {
            // 初始化ModuleDTO对象
            final String moduleId = ( ( PositionModuleRightRelationVO ) positionModuleRightRelationVOObject ).getModuleId();
            // 从列表中获得PositionModuleDTO实例
            final PositionModuleDTO positionModuleDTO = getPositionModuleDTOFormList( positionModuleDTOs, moduleId );

            // 初始化PositionModuleDTO中的ModuleVO对象
            if ( positionModuleDTO != null )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId() == null )
               {
                  positionModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // 初始化Module对应的权限
               positionModuleDTO.getPositionModuleRightRelationVOs().add( ( PositionModuleRightRelationVO ) positionModuleRightRelationVOObject );
            }
         }
      }

      // 获得当前职位所关联的Module，Rule
      final PositionModuleRuleRelationVO positionModuleRuleRelationVO = new PositionModuleRuleRelationVO( positionId, null, null );
      positionModuleRuleRelationVO.setStatus( "1" );
      final List< Object > positionModuleRuleRelationVOs = this.getPositionModuleRuleRelationVOsByCondition( positionModuleRuleRelationVO );

      if ( positionModuleRuleRelationVOs != null && positionModuleRuleRelationVOs.size() > 0 )
      {
         for ( Object positionModuleRuleRelationVOObject : positionModuleRuleRelationVOs )
         {
            // 初始化PositionModuleDTO对象
            final String moduleId = ( ( PositionModuleRuleRelationVO ) positionModuleRuleRelationVOObject ).getModuleId();
            // 从列表中获得PositionModuleDTO实例
            final PositionModuleDTO positionModuleDTO = getPositionModuleDTOFormList( positionModuleDTOs, moduleId );

            // 初始化PositionModuleDTO中的ModuleVO对象
            if ( positionModuleDTO != null )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId() == null )
               {
                  positionModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // 初始化Module对应的权限
               positionModuleDTO.getPositionModuleRuleRelationVOs().add( ( PositionModuleRuleRelationVO ) positionModuleRuleRelationVOObject );
            }
         }
      }

      return positionModuleDTOs;
   }

   // 从列表中获得PositionModuleDTO
   private PositionModuleDTO getPositionModuleDTOFormList( final List< PositionModuleDTO > positionModuleDTOs, final String moduleId )
   {
      // 如果当前需要查找的DTO列表不为空，并且ModuleId也不为空
      if ( positionModuleDTOs != null && moduleId != null )
      {
         // DTO列表中含有的对象数大于0
         if ( positionModuleDTOs.size() > 0 )
         {
            // 遍历DTO列表找到匹配的并返回
            for ( PositionModuleDTO positionModuleDTO : positionModuleDTOs )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId().equals( moduleId ) )
               {
                  return positionModuleDTO;
               }
            }
         }

         // 如果找不到目标对象，则创建实例
         final PositionModuleDTO positionModuleDTO = new PositionModuleDTO();
         positionModuleDTOs.add( positionModuleDTO );
         return positionModuleDTO;
      }

      return null;
   }

   @Override
   public List< Object > getPositionModuleRightRelationVOsByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException
   {
      return ( ( PositionModuleRightRelationDao ) getPositionModuleRightRelationDao() ).getPositionModuleRightRelationVOsByCondition( positionModuleRightRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRuleRelationVOsByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException
   {
      return ( ( PositionModuleRuleRelationDao ) getPositionModuleRuleRelationDao() ).getPositionModuleRuleRelationVOsByCondition( positionModuleRuleRelationVO );
   }

   @Override
   public List< PositionVO > getPositionVOsByPositionVO( PositionVO positionVO ) throws KANException
   {
      // 初始化返回用PositionVOs
      List< PositionVO > positionVOs = new ArrayList< PositionVO >();
      // 从数据库中获取未转换的PositionVO集合PositionVOs
      List< Object > objectPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOByPositionVO( positionVO );
      // 遍历objectPositionVOs添加入positionVOs中
      for ( Object objectPositionVO : objectPositionVOs )
      {
         PositionVO positionVOTemp = ( PositionVO ) objectPositionVO;
         positionVOs.add( positionVOTemp );
      }
      return positionVOs;
   }

   @Override
   public List< Object > getPositionVOsByEmployeeId( StaffVO staffVO ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionVOsByEmployeeId( staffVO );
   }

   @Override
   public int deletePositionStaffRelationByPositionId( String positionId ) throws KANException
   {
      return positionStaffRelationDao.deletePositionStaffRelationByPositionId( positionId );
   }

   @Override
   public int insertPositionStaffRelation( PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByStaffId( final String staffId ) throws KANException
   {
      return positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( staffId );
   }

}

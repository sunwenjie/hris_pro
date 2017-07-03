package com.kan.hro.service.impl.biz.employee;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.security.PositionGroupRelationDao;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JsonMapper;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeBatchDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeTempDao;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeBatchVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeBatchService;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeService;

public class EmployeePositionChangeBatchServiceImpl extends ContextService implements EmployeePositionChangeBatchService
{
   private CommonBatchDao commonBatchDao;

   private EmployeePositionChangeTempDao employeePositionChangeTempDao;

   private EmployeePositionChangeService employeePositionChangeService;

   private LogDao logDao;

   private PositionGroupRelationDao positionGroupRelationDao;

   private PositionDao positionDao;

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   public PositionGroupRelationDao getPositionGroupRelationDao()
   {
      return positionGroupRelationDao;
   }

   public void setPositionGroupRelationDao( PositionGroupRelationDao positionGroupRelationDao )
   {
      this.positionGroupRelationDao = positionGroupRelationDao;
   }

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

   public EmployeePositionChangeService getEmployeePositionChangeService()
   {
      return employeePositionChangeService;
   }

   public void setEmployeePositionChangeService( EmployeePositionChangeService employeePositionChangeService )
   {
      this.employeePositionChangeService = employeePositionChangeService;
   }

   public EmployeePositionChangeTempDao getEmployeePositionChangeTempDao()
   {
      return employeePositionChangeTempDao;
   }

   public void setEmployeePositionChangeTempDao( EmployeePositionChangeTempDao employeePositionChangeTempDao )
   {
      this.employeePositionChangeTempDao = employeePositionChangeTempDao;
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   @Override
   public PagedListHolder getEmployeePositionChangeBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeePositionChangeBatchVO object = ( EmployeePositionChangeBatchVO ) pagedListHolder.getObject();
      final EmployeePositionChangeBatchDao dao = ( EmployeePositionChangeBatchDao ) getDao();
      pagedListHolder.setHolderSize( dao.countEmployeePositionChangeBatchVOsByCondition( object ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getEmployeePositionChangeBatchVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getEmployeePositionChangeBatchVOsByCondition( object ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeePositionChangeBatchVO getEmployeePositionChangeBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( ( EmployeePositionChangeBatchDao ) getDao() ).getEmployeePositionChangeBatchVOByBatchId( batchId );
   }

   @Override
   public int submitEmployeePositionChangeBatch( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException
   {
      int rows = 0;
      try
      {
         final EmployeePositionChangeBatchDao dao = ( EmployeePositionChangeBatchDao ) getDao();
         employeePositionChangeBatchVO.setStatus( "2" );

         startTransaction();

         dao.updateEmployeePositionChangeBatch( employeePositionChangeBatchVO );

         final List< Object > tempVOList = this.getEmployeePositionChangeTempDao().getEmployeePositionChangeTempVOsByBatchId( employeePositionChangeBatchVO.getBatchId() );
         if ( tempVOList != null && tempVOList.size() > 0 )
         {
            for ( Object o : tempVOList )
            {
               final EmployeePositionChangeTempVO tempVO = ( EmployeePositionChangeTempVO ) o;
               tempVO.setStatus( "2" );
               tempVO.setModifyBy( employeePositionChangeBatchVO.getModifyBy() );
               tempVO.setModifyDate( new Date() );

               EmployeePositionChangeVO tempEmployeePositionChangeVO = generateEmployeePositionChangeVO( tempVO );
               tempEmployeePositionChangeVO.setRemark1( tempVO.getRemark1() );
               tempEmployeePositionChangeVO.setRemark2( tempVO.getRemark2() );
               tempEmployeePositionChangeVO.setRemark4( tempVO.getBatchId() );
               tempEmployeePositionChangeVO.setRemark5( tempVO.getPositionChangeId());
               this.getEmployeePositionChangeService().insertEmployeePositionChange( tempEmployeePositionChangeVO );
               tempEmployeePositionChangeVO.setLocale( employeePositionChangeBatchVO.getLocale() );
               insertLog( tempEmployeePositionChangeVO, employeePositionChangeBatchVO.getIp() );
               rows = rows + 1;
            }
         }

         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }
      return rows;
   }

   @Override
   public int rollbackEmployeePositionChangeBatch( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException
   {
      int rows = 0;
      try
      {
         final EmployeePositionChangeBatchDao dao = ( EmployeePositionChangeBatchDao ) getDao();
         startTransaction();

         // 直接删除
         dao.deleteEmployeePositionChangeBatch( employeePositionChangeBatchVO );

         final List< Object > tempVOList = this.getEmployeePositionChangeTempDao().getEmployeePositionChangeTempVOsByBatchId( employeePositionChangeBatchVO.getBatchId() );
         if ( tempVOList != null && tempVOList.size() > 0 )
         {
            for ( Object object : tempVOList )
            {
               final EmployeePositionChangeTempVO tempVO = ( EmployeePositionChangeTempVO ) object;
               this.getEmployeePositionChangeTempDao().deleteEmployeePositionChangeTemp( tempVO );
               rows = rows + 1;
            }
         }

         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   /***
    * 创建批量异动批次
    */
   @Override
   public int insertEmployeePositionChangeBatch( CommonBatchVO commonBatchVO, List< EmployeePositionChangeTempVO > listTempVO ) throws KANException
   {
      int rows = 0;

      KANAccountConstants constants = KANConstants.getKANAccountConstants( commonBatchVO.getAccountId() );
      try
      {
         startTransaction();

         // 创建一个批次
         this.getCommonBatchDao().insertCommonBatch( commonBatchVO );

         for ( EmployeePositionChangeTempVO tempVO : listTempVO )
         {
            tempVO.setBatchId( commonBatchVO.getBatchId() );
            tempVO.setStatus( "1" );

            final List< StaffDTO > staffDTOs = constants.getStaffDTOsByPositionId( tempVO.getOldPositionId() );
            // 如果职位只有一个人，使用新异动
            if ( staffDTOs != null && staffDTOs.size() == 1 )
            {
               tempVO.setNewPositionId( tempVO.getOldPositionId() );
               tempVO.setSubmitFlag( 3 );//表示快速异动
               setPositionChangeOtherInfo( tempVO );
            }
            // 如果职位存在多个人，需要新增一个职位
            else
            {
               final PositionVO newPositionVO = new PositionVO();
               newPositionVO.setAccountId( commonBatchVO.getAccountId() );
               newPositionVO.setCorpId( commonBatchVO.getCorpId() );
               newPositionVO.setLocationId( tempVO.getLocationId() );
               newPositionVO.setBranchId( tempVO.getNewBranchId() );
               newPositionVO.setPositionGradeId( tempVO.getNewPositionGradeId() );
               newPositionVO.setTitleZH( tempVO.getNewPositionNameZH() );
               newPositionVO.setTitleEN( tempVO.getNewPositionNameEN() );
               newPositionVO.setParentPositionId( tempVO.getNewParentPositionId() );
               newPositionVO.setIsVacant( "1" );
               newPositionVO.setNeedPublish( "2" );
               newPositionVO.setStatus( "1" );

               positionDao.insertPosition( newPositionVO );

               List< Object > positionGroupRelationVOs = positionGroupRelationDao.getPositionGroupRelationVOsByPositionId( tempVO.getOldPositionId() );
               if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
               {
                  for ( int i = 0; i < positionGroupRelationVOs.size(); i++ )
                  {
                     final PositionGroupRelationVO dbPositionGroupRelationVO = ( PositionGroupRelationVO ) positionGroupRelationVOs.get( i );

                     final PositionGroupRelationVO newPositionGroupRelationVO = new PositionGroupRelationVO();
                     newPositionGroupRelationVO.setPositionId( newPositionVO.getPositionId() );
                     newPositionGroupRelationVO.setGroupId( dbPositionGroupRelationVO.getGroupId() );
                     newPositionGroupRelationVO.setCreateBy( commonBatchVO.getCreateBy() );
                     newPositionGroupRelationVO.setModifyBy( commonBatchVO.getCreateBy() );
                     this.positionGroupRelationDao.insertPositionGroupRelation( newPositionGroupRelationVO );
                  }
               }

               tempVO.setNewPositionId( newPositionVO.getPositionId() );
               tempVO.setSubmitFlag( 1 );
               setPositionChangeOtherInfo( tempVO );
            }

            rows = rows + this.getEmployeePositionChangeTempDao().insertEmployeePositionChangeTemp( tempVO );
         }

         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }

      try
      {
         // 加载缓存
         BaseAction.constantsInit( "initBranch", commonBatchVO.getAccountId() );
         BaseAction.constantsInit( "initPositionGroup", commonBatchVO.getAccountId() );
         BaseAction.constantsInit( "initPosition", commonBatchVO.getAccountId() );
      }
      catch ( MalformedURLException | RemoteException | NotBoundException e )
      {
         logger.error( "批量异动加载组织架构缓存出错!" );
         e.printStackTrace();
      }
      
      return rows;
   }

   private void insertLog( final EmployeePositionChangeVO tempEmployeePositionChangeVO, final String ip ) throws KANException
   {
      LogVO logVO = new LogVO();
      logVO.setEmployeeId( tempEmployeePositionChangeVO.getEmployeeId() );
      logVO.setChangeReason( tempEmployeePositionChangeVO.getRemark3() );
      logVO.setEmployeeNameZH( tempEmployeePositionChangeVO.getEmployeeNameZH() );
      logVO.setEmployeeNameEN( tempEmployeePositionChangeVO.getEmployeeNameEN() );
      logVO.setType( String.valueOf( Operate.ADD.getIndex() ) );
      logVO.setModule( EmployeePositionChangeVO.class.getCanonicalName() );
      logVO.setContent( tempEmployeePositionChangeVO == null ? "" : JsonMapper.toLogJson( tempEmployeePositionChangeVO ) );
      logVO.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
      logVO.setOperateBy( tempEmployeePositionChangeVO.getDecodeCreateBy() );
      logVO.setpKey( tempEmployeePositionChangeVO.getPositionChangeId() );
      logVO.setIp( ip );

      this.getLogDao().insertLog( logVO );
   }

   private EmployeePositionChangeVO generateEmployeePositionChangeVO( EmployeePositionChangeTempVO tempVO )
   {
      EmployeePositionChangeVO employeePositionChangeVO = new EmployeePositionChangeVO();
      employeePositionChangeVO.setAccountId( tempVO.getAccountId() );
      employeePositionChangeVO.setCorpId( tempVO.getCorpId() );
      employeePositionChangeVO.setEmployeeId( tempVO.getEmployeeId() );
      employeePositionChangeVO.setStaffId( tempVO.getStaffId() );
      employeePositionChangeVO.setOldBranchId( tempVO.getOldBranchId() );
      employeePositionChangeVO.setOldStaffPositionRelationId( tempVO.getOldStaffPositionRelationId() );
      employeePositionChangeVO.setOldPositionId( tempVO.getOldPositionId() );
      employeePositionChangeVO.setOldStartDate( tempVO.getOldStartDate() );
      employeePositionChangeVO.setOldEndDate( tempVO.getOldEndDate() );
      employeePositionChangeVO.setNewBranchId( tempVO.getNewBranchId() );
      employeePositionChangeVO.setNewPositionId( tempVO.getNewPositionId() );
      employeePositionChangeVO.setNewStartDate( tempVO.getNewStartDate() );
      employeePositionChangeVO.setNewEndDate( tempVO.getNewEndDate() );
      employeePositionChangeVO.setEffectiveDate( tempVO.getEffectiveDate() );
      employeePositionChangeVO.setIsImmediatelyEffective( "0" );
      employeePositionChangeVO.setDeleted( "1" );
      employeePositionChangeVO.setPositionStatus( tempVO.getPositionStatus() );
      employeePositionChangeVO.setIsChildChange( tempVO.getIsChildChange() );
      employeePositionChangeVO.setStatus( "1" );
      employeePositionChangeVO.setSubmitFlag( tempVO.getSubmitFlag() );
      employeePositionChangeVO.setRemark3( tempVO.getRemark3() );
      employeePositionChangeVO.setCreateBy( tempVO.getCreateBy() );
      employeePositionChangeVO.setCreateDate( new Date() );
      employeePositionChangeVO.setModifyBy( tempVO.getCreateBy() );
      employeePositionChangeVO.setModifyDate( new Date() );

      employeePositionChangeVO.setOldBranchNameZH( tempVO.getOldBranchNameZH() );
      employeePositionChangeVO.setOldBranchNameEN( tempVO.getOldBranchNameEN() );
      employeePositionChangeVO.setOldPositionNameZH( tempVO.getOldPositionNameZH() );
      employeePositionChangeVO.setOldPositionNameEN( tempVO.getOldPositionNameEN() );

      employeePositionChangeVO.setNewBranchNameZH( tempVO.getNewBranchNameZH() );
      employeePositionChangeVO.setNewBranchNameEN( tempVO.getNewBranchNameEN() );
      employeePositionChangeVO.setNewPositionNameZH( tempVO.getNewPositionNameZH() );
      employeePositionChangeVO.setNewPositionNameEN( tempVO.getNewPositionNameEN() );
      employeePositionChangeVO.setOldParentBranchId( tempVO.getOldParentBranchId() );
      employeePositionChangeVO.setOldParentBranchNameZH( tempVO.getOldParentBranchNameZH() );
      employeePositionChangeVO.setOldParentBranchNameEN( tempVO.getOldParentBranchNameEN() );
      employeePositionChangeVO.setOldParentPositionId( tempVO.getOldParentPositionId() );
      employeePositionChangeVO.setOldParentPositionNameZH( tempVO.getOldParentPositionNameZH() );
      employeePositionChangeVO.setOldParentPositionNameEN( tempVO.getOldParentPositionNameEN() );
      employeePositionChangeVO.setOldPositionGradeId( tempVO.getOldPositionGradeId() );
      employeePositionChangeVO.setOldPositionGradeNameZH( tempVO.getOldPositionGradeNameZH() );
      employeePositionChangeVO.setOldPositionGradeNameEN( tempVO.getOldPositionGradeNameEN() );
      employeePositionChangeVO.setOldParentPositionOwnersZH( tempVO.getOldParentPositionOwnersZH() );
      employeePositionChangeVO.setOldParentPositionOwnersEN( tempVO.getOldParentPositionOwnersEN() );
      employeePositionChangeVO.setNewParentBranchId( tempVO.getNewParentBranchId() );
      employeePositionChangeVO.setNewParentBranchNameZH( tempVO.getNewParentBranchNameZH() );
      employeePositionChangeVO.setNewParentBranchNameEN( tempVO.getNewParentBranchNameEN() );
      employeePositionChangeVO.setNewParentPositionId( tempVO.getNewParentPositionId() );
      employeePositionChangeVO.setNewParentPositionNameZH( tempVO.getNewParentPositionNameZH() );
      employeePositionChangeVO.setNewParentPositionNameEN( tempVO.getNewParentPositionNameEN() );
      employeePositionChangeVO.setNewPositionGradeId( tempVO.getNewPositionGradeId() );
      employeePositionChangeVO.setNewPositionGradeNameZH( tempVO.getNewPositionGradeNameZH() );
      employeePositionChangeVO.setNewPositionGradeNameEN( tempVO.getNewPositionGradeNameEN() );
      employeePositionChangeVO.setEmployeeNo( tempVO.getEmployeeNo() );
      employeePositionChangeVO.setEmployeeNameZH( tempVO.getEmployeeNameZH() );
      employeePositionChangeVO.setEmployeeNameEN( tempVO.getEmployeeNameEN() );
      employeePositionChangeVO.setEmployeeCertificateNumber( tempVO.getEmployeeCertificateNumber() );
      employeePositionChangeVO.setNewParentPositionOwnersZH( tempVO.getNewParentPositionOwnersZH() );
      employeePositionChangeVO.setNewParentPositionOwnersEN( tempVO.getNewParentPositionOwnersEN() );

      return employeePositionChangeVO;
   }

   private void setPositionChangeOtherInfo( final EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException
   {
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( employeePositionChangeTempVO.getAccountId() );

      /*** old ***/
      final PositionVO oldPosiitonVO = accountConstants.getPositionVOByPositionId( employeePositionChangeTempVO.getOldPositionId() );
      if ( oldPosiitonVO != null )
      {
         //职位
         employeePositionChangeTempVO.setOldPositionNameZH( oldPosiitonVO.getTitleZH() );
         employeePositionChangeTempVO.setOldPositionNameEN( oldPosiitonVO.getTitleEN() );

         //职级
         final PositionGradeVO oldParentPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( oldPosiitonVO.getPositionGradeId() );
         if ( oldParentPositionGradeVO != null )
         {
            employeePositionChangeTempVO.setOldPositionGradeId( oldParentPositionGradeVO.getPositionGradeId() );
            employeePositionChangeTempVO.setOldPositionGradeNameZH( oldParentPositionGradeVO.getGradeNameZH() );
            employeePositionChangeTempVO.setOldPositionGradeNameEN( oldParentPositionGradeVO.getGradeNameEN() );
         }

         //上级领导
         final PositionVO oldParentPositionVO = accountConstants.getPositionVOByPositionId( oldPosiitonVO.getParentPositionId() );
         if ( oldParentPositionVO != null )
         {
            employeePositionChangeTempVO.setOldParentPositionId( oldParentPositionVO.getPositionId() );
            employeePositionChangeTempVO.setOldParentPositionNameZH( oldParentPositionVO.getTitleZH() );
            employeePositionChangeTempVO.setOldParentPositionNameEN( oldParentPositionVO.getTitleEN() );

            employeePositionChangeTempVO.setOldParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", oldParentPositionVO.getPositionId() ) );
            employeePositionChangeTempVO.setOldParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", oldParentPositionVO.getPositionId() ) );
         }

         //部门
         final BranchVO oldBranchVO = accountConstants.getBranchVOByBranchId( oldPosiitonVO.getBranchId() );
         if ( oldBranchVO != null )
         {
            employeePositionChangeTempVO.setOldBranchId( oldBranchVO.getBranchId() );
            employeePositionChangeTempVO.setOldBranchNameZH( oldBranchVO.getNameZH() );
            employeePositionChangeTempVO.setOldBranchNameEN( oldBranchVO.getNameEN() );

            //BU
            final BranchVO oldParentBranchVO = accountConstants.getBranchVOByBranchId( oldBranchVO.getParentBranchId() );
            if ( oldParentBranchVO != null )
            {
               employeePositionChangeTempVO.setOldParentBranchId( oldParentBranchVO.getBranchId() );
               employeePositionChangeTempVO.setOldParentBranchNameZH( oldParentBranchVO.getNameZH() );
               employeePositionChangeTempVO.setOldParentBranchNameEN( oldParentBranchVO.getNameEN() );
            }
         }
      }

      /*** new ***/
      final PositionVO newPosiitonVO = positionDao.getPositionVOByPositionId( employeePositionChangeTempVO.getNewPositionId() );
      if ( newPosiitonVO != null )
      {
         //职级
         final PositionGradeVO newParentPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( employeePositionChangeTempVO.getNewPositionGradeId() );
         if ( newParentPositionGradeVO != null )
         {
            employeePositionChangeTempVO.setNewPositionGradeNameZH( newParentPositionGradeVO.getGradeNameZH() );
            employeePositionChangeTempVO.setNewPositionGradeNameEN( newParentPositionGradeVO.getGradeNameEN() );
         }

         //上级领导
         final PositionVO newParentPositionVO = accountConstants.getPositionVOByPositionId( employeePositionChangeTempVO.getNewParentPositionId() );
         if ( newParentPositionVO != null )
         {
            employeePositionChangeTempVO.setNewParentPositionId( newParentPositionVO.getPositionId() );
            employeePositionChangeTempVO.setNewParentPositionNameZH( newParentPositionVO.getTitleZH() );
            employeePositionChangeTempVO.setNewParentPositionNameEN( newParentPositionVO.getTitleEN() );

            employeePositionChangeTempVO.setNewParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", newParentPositionVO.getPositionId() ) );
            employeePositionChangeTempVO.setNewParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", newParentPositionVO.getPositionId() ) );
         }

         //部门
         final BranchVO newBranchVO = accountConstants.getBranchVOByBranchId( employeePositionChangeTempVO.getNewBranchId() );
         if ( newBranchVO != null )
         {
            employeePositionChangeTempVO.setNewBranchNameZH( newBranchVO.getNameZH() );
            employeePositionChangeTempVO.setNewBranchNameEN( newBranchVO.getNameEN() );

            //BU
            final BranchVO newParentBranchVO = accountConstants.getBranchVOByBranchId( employeePositionChangeTempVO.getNewParentBranchId() );
            if ( newParentBranchVO != null )
            {
               employeePositionChangeTempVO.setNewParentBranchNameZH( newParentBranchVO.getNameZH() );
               employeePositionChangeTempVO.setNewParentBranchNameEN( newParentBranchVO.getNameEN() );
            }
         }
      }

   }
}

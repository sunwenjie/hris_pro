package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.Locale;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JsonMapper;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeBatchDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeTempDao;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeBatchVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeTempService;

public class EmployeePositionChangeTempServiceImpl extends ContextService implements EmployeePositionChangeTempService
{
   private EmployeePositionChangeBatchDao employeePositionChangeBatchDao;

   private EmployeePositionChangeDao employeePositionChangeDao;

   private LogDao logDao;

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

   public EmployeePositionChangeBatchDao getEmployeePositionChangeBatchDao()
   {
      return employeePositionChangeBatchDao;
   }

   public void setEmployeePositionChangeBatchDao( EmployeePositionChangeBatchDao employeePositionChangeBatchDao )
   {
      this.employeePositionChangeBatchDao = employeePositionChangeBatchDao;
   }

   public EmployeePositionChangeDao getEmployeePositionChangeDao()
   {
      return employeePositionChangeDao;
   }

   public void setEmployeePositionChangeDao( EmployeePositionChangeDao employeePositionChangeDao )
   {
      this.employeePositionChangeDao = employeePositionChangeDao;
   }

   @Override
   public void getEmployeePositionChangeTempVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeePositionChangeTempDao dao = ( EmployeePositionChangeTempDao ) getDao();
      final EmployeePositionChangeTempVO object = ( EmployeePositionChangeTempVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( dao.countEmployeePositionChangeTempVOsByCondition( object ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getEmployeePositionChangeTempVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getEmployeePositionChangeTempVOsByCondition( object ) );
      }
   }

   @Override
   public int submitEmployeePositionChangeTempVOByPositionChangeIds( String[] positionChangeIds, String userId, String ip, Locale locale ) throws KANException
   {
      int rows = 0;
      String batchId = "";
      try
      {
         final EmployeePositionChangeTempDao dao = ( EmployeePositionChangeTempDao ) getDao();
         startTransaction();

         for ( int i = 0; i < positionChangeIds.length; i++ )
         {
            final EmployeePositionChangeTempVO tempVO = dao.getEmployeePositionChangeTempVOByPositionChangeId( positionChangeIds[ i ] );
            if ( tempVO != null )
            {
               tempVO.setStatus( "2" );
               tempVO.setModifyBy( userId );
               tempVO.setModifyDate( new Date() );
               dao.updateEmployeePositionChangeTemp( tempVO );

               EmployeePositionChangeVO tempEmployeePositionChangeVO = generateEmployeePositionChangeVO( tempVO );
               tempEmployeePositionChangeVO.setRemark1( tempVO.getRemark1() );
               tempEmployeePositionChangeVO.setRemark2( tempVO.getRemark2() );
               tempEmployeePositionChangeVO.setRemark4( tempVO.getBatchId() );
               tempEmployeePositionChangeVO.setRemark5( tempVO.getPositionChangeId());
               this.getEmployeePositionChangeDao().insertEmployeePositionChange( tempEmployeePositionChangeVO );
               tempEmployeePositionChangeVO = this.getEmployeePositionChangeDao().getEmployeePositionChangeVOByPositionChangeId( tempEmployeePositionChangeVO.getPositionChangeId() );
               tempEmployeePositionChangeVO.setLocale( locale );
               insertLog( tempEmployeePositionChangeVO, ip );

               if ( i == 0 )
                  batchId = tempVO.getBatchId();

               rows = rows + 1;
            }
         }

         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            final EmployeePositionChangeTempVO searchVO = new EmployeePositionChangeTempVO();
            searchVO.setBatchId( batchId );
            searchVO.setStatus( "1" );

            if ( dao.countEmployeePositionChangeTempVOsByCondition( searchVO ) == 0 )
            {
               final EmployeePositionChangeBatchVO batchVO = this.getEmployeePositionChangeBatchDao().getEmployeePositionChangeBatchVOByBatchId( batchId );
               if ( batchVO != null )
               {
                  batchVO.setStatus( "2" );
                  batchVO.setModifyBy( userId );
                  batchVO.setModifyDate( new Date() );
                  this.getEmployeePositionChangeBatchDao().updateEmployeePositionChangeBatch( batchVO );
               }
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

   @Override
   public int rollbackEmployeePositionChangeTempVOByPositionChangeIds( String[] positionChangeIds, String userId ) throws KANException
   {
      int rows = 0;
      String batchId = "";
      try
      {
         final EmployeePositionChangeTempDao dao = ( EmployeePositionChangeTempDao ) getDao();
         startTransaction();

         // 直接删除
         for ( int i = 0; i < positionChangeIds.length; i++ )
         {
            final EmployeePositionChangeTempVO tempVO = dao.getEmployeePositionChangeTempVOByPositionChangeId( positionChangeIds[ i ] );
            if ( tempVO != null )
            {
               dao.deleteEmployeePositionChangeTemp( tempVO );
               if ( i == 0 )
                  batchId = tempVO.getBatchId();
               rows = rows + 1;
            }
         }

         // 直接删除
         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            final EmployeePositionChangeTempVO searchVO = new EmployeePositionChangeTempVO();
            searchVO.setBatchId( batchId );
            searchVO.setStatus( "2" );
            int updateSize = dao.countEmployeePositionChangeTempVOsByCondition( searchVO );
            searchVO.setStatus( "1" );
            int newSize = dao.countEmployeePositionChangeTempVOsByCondition( searchVO );

            final EmployeePositionChangeBatchVO batchVO = this.getEmployeePositionChangeBatchDao().getEmployeePositionChangeBatchVOByBatchId( batchId );
            if ( newSize == 0 )
            {
               if ( updateSize > 0 )
               {
                  if ( batchVO != null )
                  {
                     batchVO.setStatus( "2" );
                     batchVO.setModifyBy( userId );
                     batchVO.setModifyDate( new Date() );
                     this.getEmployeePositionChangeBatchDao().updateEmployeePositionChangeBatch( batchVO );
                  }
               }
               else
               {
                  if ( batchVO != null )
                  {
                     this.getEmployeePositionChangeBatchDao().deleteEmployeePositionChangeBatch( batchVO );
                  }
               }
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

}

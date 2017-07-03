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
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentBatchDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentTempDao;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentTempVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentTempService;

public class EmployeeSalaryAdjustmentTempServiceImpl extends ContextService implements EmployeeSalaryAdjustmentTempService
{
   private EmployeeSalaryAdjustmentBatchDao employeeSalaryAdjustmentBatchDao;

   private EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao;

   private LogDao logDao;

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

   public EmployeeSalaryAdjustmentBatchDao getEmployeeSalaryAdjustmentBatchDao()
   {
      return employeeSalaryAdjustmentBatchDao;
   }

   public void setEmployeeSalaryAdjustmentBatchDao( EmployeeSalaryAdjustmentBatchDao employeeSalaryAdjustmentBatchDao )
   {
      this.employeeSalaryAdjustmentBatchDao = employeeSalaryAdjustmentBatchDao;
   }

   public EmployeeSalaryAdjustmentDao getEmployeeSalaryAdjustmentDao()
   {
      return employeeSalaryAdjustmentDao;
   }

   public void setEmployeeSalaryAdjustmentDao( EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao )
   {
      this.employeeSalaryAdjustmentDao = employeeSalaryAdjustmentDao;
   }

   @Override
   public void getEmployeeSalaryAdjustmentTempVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeSalaryAdjustmentTempDao dao = ( EmployeeSalaryAdjustmentTempDao ) getDao();
      final EmployeeSalaryAdjustmentTempVO object = ( EmployeeSalaryAdjustmentTempVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( dao.countEmployeeSalaryAdjustmentTempVOsByCondition( object ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getEmployeeSalaryAdjustmentTempVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getEmployeeSalaryAdjustmentTempVOsByCondition( object ) );
      }
   }

   @Override
   public int submitEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentIds( String[] salaryAdjustmentIds, String userId, String ip, Locale locale ) throws KANException
   {
      int rows = 0;
      String batchId = "";
      try
      {
         final EmployeeSalaryAdjustmentTempDao dao = ( EmployeeSalaryAdjustmentTempDao ) getDao();
         startTransaction();

         for ( int i = 0; i < salaryAdjustmentIds.length; i++ )
         {
            final EmployeeSalaryAdjustmentTempVO tempVO = dao.getEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentId( salaryAdjustmentIds[ i ] );
            if ( tempVO != null )
            {
               tempVO.setStatus( "2" );
               tempVO.setModifyBy( userId );
               tempVO.setModifyDate( new Date() );
               dao.updateEmployeeSalaryAdjustmentTemp( tempVO );

               EmployeeSalaryAdjustmentVO tempEmployeeSalaryAdjustmentVO = generateEmployeeSalaryAdjustmentVO( tempVO );
               this.getEmployeeSalaryAdjustmentDao().insertEmployeeSalaryAdjustment( tempEmployeeSalaryAdjustmentVO );
               tempEmployeeSalaryAdjustmentVO = this.getEmployeeSalaryAdjustmentDao().getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( tempEmployeeSalaryAdjustmentVO.getSalaryAdjustmentId() );
               tempEmployeeSalaryAdjustmentVO.setLocale( locale );
               insertLog( tempEmployeeSalaryAdjustmentVO, ip );

               if ( i == 0 )
                  batchId = tempVO.getBatchId();

               rows = rows + 1;
            }
         }

         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            final EmployeeSalaryAdjustmentTempVO searchVO = new EmployeeSalaryAdjustmentTempVO();
            searchVO.setBatchId( batchId );
            searchVO.setStatus( "1" );

            if ( dao.countEmployeeSalaryAdjustmentTempVOsByCondition( searchVO ) == 0 )
            {
               final EmployeeSalaryAdjustmentBatchVO batchVO = this.getEmployeeSalaryAdjustmentBatchDao().getEmployeeSalaryAdjustmentBatchVOByBatchId( batchId );
               if ( batchVO != null )
               {
                  batchVO.setStatus( "2" );
                  batchVO.setModifyBy( userId );
                  batchVO.setModifyDate( new Date() );
                  this.getEmployeeSalaryAdjustmentBatchDao().updateEmployeeSalaryAdjustmentBatch( batchVO );
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
   public int rollbackEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentIds( String[] salaryAdjustmentIds, String userId ) throws KANException
   {
      int rows = 0;
      String batchId = "";
      try
      {
         final EmployeeSalaryAdjustmentTempDao dao = ( EmployeeSalaryAdjustmentTempDao ) getDao();
         startTransaction();

         // 直接删除
         for ( int i = 0; i < salaryAdjustmentIds.length; i++ )
         {
            final EmployeeSalaryAdjustmentTempVO tempVO = dao.getEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentId( salaryAdjustmentIds[ i ] );
            if ( tempVO != null )
            {
               dao.deleteEmployeeSalaryAdjustmentTemp( tempVO );
               if ( i == 0 )
                  batchId = tempVO.getBatchId();
               rows = rows + 1;
            }
         }

         // 直接删除
         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            final EmployeeSalaryAdjustmentTempVO searchVO = new EmployeeSalaryAdjustmentTempVO();
            searchVO.setBatchId( batchId );
            searchVO.setStatus( "2" );
            int updateSize = dao.countEmployeeSalaryAdjustmentTempVOsByCondition( searchVO );
            searchVO.setStatus( "1" );
            int newSize = dao.countEmployeeSalaryAdjustmentTempVOsByCondition( searchVO );

            final EmployeeSalaryAdjustmentBatchVO batchVO = this.getEmployeeSalaryAdjustmentBatchDao().getEmployeeSalaryAdjustmentBatchVOByBatchId( batchId );
            if ( newSize == 0 )
            {
               if ( updateSize > 0 )
               {
                  if ( batchVO != null )
                  {
                     batchVO.setStatus( "2" );
                     batchVO.setModifyBy( userId );
                     batchVO.setModifyDate( new Date() );
                     this.getEmployeeSalaryAdjustmentBatchDao().updateEmployeeSalaryAdjustmentBatch( batchVO );
                  }
               }
               else
               {
                  if ( batchVO != null )
                  {
                     this.getEmployeeSalaryAdjustmentBatchDao().deleteEmployeeSalaryAdjustmentBatch( batchVO );
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

   private EmployeeSalaryAdjustmentVO generateEmployeeSalaryAdjustmentVO( EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO )
   {
      final EmployeeSalaryAdjustmentVO newObject = new EmployeeSalaryAdjustmentVO();
      newObject.setAccountId( employeeSalaryAdjustmentTempVO.getAccountId() );
      newObject.setCorpId( employeeSalaryAdjustmentTempVO.getCorpId() );
      newObject.setEmployeeId( employeeSalaryAdjustmentTempVO.getEmployeeId() );
      newObject.setContractId( employeeSalaryAdjustmentTempVO.getContractId() );
      newObject.setEmployeeSalaryId( employeeSalaryAdjustmentTempVO.getEmployeeSalaryId() );
      newObject.setOldBase( employeeSalaryAdjustmentTempVO.getOldBase() );
      newObject.setOldStartDate( employeeSalaryAdjustmentTempVO.getOldStartDate() );
      newObject.setOldEndDate( employeeSalaryAdjustmentTempVO.getOldEndDate() );
      newObject.setNewBase( employeeSalaryAdjustmentTempVO.getNewBase() );
      newObject.setNewStartDate( employeeSalaryAdjustmentTempVO.getNewStartDate() );
      newObject.setNewEndDate( employeeSalaryAdjustmentTempVO.getNewEndDate() );
      newObject.setEffectiveDate( employeeSalaryAdjustmentTempVO.getEffectiveDate() );
      newObject.setRemark3( employeeSalaryAdjustmentTempVO.getRemark3() );
      newObject.setRemark4( employeeSalaryAdjustmentTempVO.getSalaryAdjustmentId() );
      newObject.setDeleted( "1" );
      newObject.setStatus( "1" );
      newObject.setItemId( employeeSalaryAdjustmentTempVO.getItemId() );
      newObject.setItemNameZH( employeeSalaryAdjustmentTempVO.getItemNameZH() );
      newObject.setItemNameEN( employeeSalaryAdjustmentTempVO.getItemNameEN() );
      newObject.setCreateBy( employeeSalaryAdjustmentTempVO.getModifyBy() );
      newObject.setModifyBy( employeeSalaryAdjustmentTempVO.getModifyBy() );
      newObject.setCreateDate( new Date() );
      newObject.setModifyDate( new Date() );

      return newObject;
   }

   private void insertLog( final EmployeeSalaryAdjustmentVO tempEmployeeSalaryAdjustmentVO, final String ip ) throws KANException
   {
      LogVO logVO = new LogVO();
      logVO.setEmployeeId( tempEmployeeSalaryAdjustmentVO.getEmployeeId() );
      logVO.setChangeReason( tempEmployeeSalaryAdjustmentVO.getRemark3() );
      logVO.setEmployeeNameZH( tempEmployeeSalaryAdjustmentVO.getEmployeeNameZH() );
      logVO.setEmployeeNameEN( tempEmployeeSalaryAdjustmentVO.getEmployeeNameEN() );
      logVO.setType( String.valueOf( Operate.ADD.getIndex() ) );
      logVO.setModule( EmployeeSalaryAdjustmentVO.class.getCanonicalName() );
      logVO.setContent( tempEmployeeSalaryAdjustmentVO == null ? "" : JsonMapper.toLogJson( tempEmployeeSalaryAdjustmentVO ) );
      logVO.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
      logVO.setOperateBy( tempEmployeeSalaryAdjustmentVO.getDecodeCreateBy() );
      logVO.setpKey( tempEmployeeSalaryAdjustmentVO.getSalaryAdjustmentId() );
      logVO.setIp( ip );

      this.getLogDao().insertLog( logVO );
   }

}

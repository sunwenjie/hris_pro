package com.kan.hro.service.impl.biz.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JsonMapper;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentBatchDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentTempDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentTempVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentBatchService;

public class EmployeeSalaryAdjustmentBatchServiceImpl extends ContextService implements EmployeeSalaryAdjustmentBatchService
{
   private CommonBatchDao commonBatchDao;
   private ClientOrderHeaderDao clientOrderHeaderDao;
   private EmployeeContractSalaryDao employeeContractSalaryDao;
   private EmployeeSalaryAdjustmentTempDao employeeSalaryAdjustmentTempDao;
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

   public EmployeeSalaryAdjustmentDao getEmployeeSalaryAdjustmentDao()
   {
      return employeeSalaryAdjustmentDao;
   }

   public void setEmployeeSalaryAdjustmentDao( EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao )
   {
      this.employeeSalaryAdjustmentDao = employeeSalaryAdjustmentDao;
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   public EmployeeSalaryAdjustmentTempDao getEmployeeSalaryAdjustmentTempDao()
   {
      return employeeSalaryAdjustmentTempDao;
   }

   public void setEmployeeSalaryAdjustmentTempDao( EmployeeSalaryAdjustmentTempDao employeeSalaryAdjustmentTempDao )
   {
      this.employeeSalaryAdjustmentTempDao = employeeSalaryAdjustmentTempDao;
   }

   @Override
   public PagedListHolder getEmployeeSalaryAdjustmentBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeSalaryAdjustmentBatchVO object = ( EmployeeSalaryAdjustmentBatchVO ) pagedListHolder.getObject();
      final EmployeeSalaryAdjustmentBatchDao dao = ( EmployeeSalaryAdjustmentBatchDao ) getDao();
      pagedListHolder.setHolderSize( dao.countEmployeeSalaryAdjustmentBatchVOsByCondition( object ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getEmployeeSalaryAdjustmentBatchVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getEmployeeSalaryAdjustmentBatchVOsByCondition( object ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeSalaryAdjustmentBatchVO getEmployeeSalaryAdjustmentBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( ( EmployeeSalaryAdjustmentBatchDao ) getDao() ).getEmployeeSalaryAdjustmentBatchVOByBatchId( batchId );
   }

   @Override
   public int submitEmployeeSalaryAdjustmentBatch( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException
   {
      int rows = 0;
      try
      {
         final EmployeeSalaryAdjustmentBatchDao dao = ( EmployeeSalaryAdjustmentBatchDao ) getDao();
         startTransaction();

         // 标记为更新
         employeeSalaryAdjustmentBatchVO.setStatus( "2" );
         dao.updateEmployeeSalaryAdjustmentBatch( employeeSalaryAdjustmentBatchVO );

         final List< Object > tempVOList = this.getEmployeeSalaryAdjustmentTempDao().getEmployeeSalaryAdjustmentTempVOsByBatchId( employeeSalaryAdjustmentBatchVO.getBatchId() );
         if ( tempVOList != null && tempVOList.size() > 0 )
         {
            for ( Object object : tempVOList )
            {
               final EmployeeSalaryAdjustmentTempVO tempVO = ( EmployeeSalaryAdjustmentTempVO ) object;
               tempVO.setStatus( "2" );
               tempVO.setModifyBy( employeeSalaryAdjustmentBatchVO.getModifyBy() );
               tempVO.setModifyDate( new Date() );
               this.getEmployeeSalaryAdjustmentTempDao().updateEmployeeSalaryAdjustmentTemp( tempVO );

               EmployeeSalaryAdjustmentVO tempEmployeeSalaryAdjustmentVO = generateEmployeeSalaryAdjustmentVO( tempVO );
               this.getEmployeeSalaryAdjustmentDao().insertEmployeeSalaryAdjustment( tempEmployeeSalaryAdjustmentVO );
               tempEmployeeSalaryAdjustmentVO = this.getEmployeeSalaryAdjustmentDao().getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( tempEmployeeSalaryAdjustmentVO.getSalaryAdjustmentId() );
               tempEmployeeSalaryAdjustmentVO.setLocale( employeeSalaryAdjustmentBatchVO.getLocale() );
               insertLog( tempEmployeeSalaryAdjustmentVO, employeeSalaryAdjustmentBatchVO.getIp() );
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

   @Override
   public int rollbackEmployeeSalaryAdjustmentBatch( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException
   {
      int rows = 0;
      try
      {
         final EmployeeSalaryAdjustmentBatchDao dao = ( EmployeeSalaryAdjustmentBatchDao ) getDao();
         startTransaction();

         // 直接删除
         dao.deleteEmployeeSalaryAdjustmentBatch( employeeSalaryAdjustmentBatchVO );

         final List< Object > tempVOList = this.getEmployeeSalaryAdjustmentTempDao().getEmployeeSalaryAdjustmentTempVOsByBatchId( employeeSalaryAdjustmentBatchVO.getBatchId() );
         if ( tempVOList != null && tempVOList.size() > 0 )
         {
            for ( Object object : tempVOList )
            {
               final EmployeeSalaryAdjustmentTempVO tempVO = ( EmployeeSalaryAdjustmentTempVO ) object;
               this.getEmployeeSalaryAdjustmentTempDao().deleteEmployeeSalaryAdjustmentTemp( tempVO );
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

   @Override
   public int insertEmployeeSalaryAdjustmentBatch( CommonBatchVO commonBatchVO, List< EmployeeSalaryAdjustmentTempVO > listTempVO ) throws KANException
   {
      int rows = 0;
      try
      {
         // 开启事务
         startTransaction();

         // 创建一个批次
         this.getCommonBatchDao().insertCommonBatch( commonBatchVO );

         for ( EmployeeSalaryAdjustmentTempVO tempVO : listTempVO )
         {
            //            // 如果需要新建薪酬方案
            //            if ( tempVO.getNeedNewSalarySolution() )
            //            {
            //               final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempVO.getOrderId() );
            //               final EmployeeContractSalaryVO tempEmployeeContractSalary_todoAdd = generateEmployeeContractSalaryVO( tempVO, clientOrderHeaderVO );
            //               this.getEmployeeContractSalaryDao().insertEmployeeContractSalary( tempEmployeeContractSalary_todoAdd );
            //               tempVO.setEmployeeSalaryId( tempEmployeeContractSalary_todoAdd.getEmployeeSalaryId() );
            //            }
            tempVO.setBatchId( commonBatchVO.getBatchId() );
            tempVO.setStatus( "1" );
            rows = rows + this.getEmployeeSalaryAdjustmentTempDao().insertEmployeeSalaryAdjustmentTemp( tempVO );
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
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

   /***
    * 构造一个EmployeeContractSalaryVO
    * @param employeeSalaryAdjustmentTempVO
    * @return EmployeeContractSalaryVO
    */
   private static EmployeeContractSalaryVO generateEmployeeContractSalaryVO( final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO,
         final ClientOrderHeaderVO clientOrderHeaderVO )
   {
      final EmployeeContractSalaryVO employeeContractSalaryVO_db = new EmployeeContractSalaryVO();
      employeeContractSalaryVO_db.setContractId( employeeSalaryAdjustmentTempVO.getContractId() );
      employeeContractSalaryVO_db.setItemId( employeeSalaryAdjustmentTempVO.getItemId() );
      employeeContractSalaryVO_db.setBase( "0.00" );// 初始化为“0”
      employeeContractSalaryVO_db.setCycle( "1" );// 初始化一个月
      final Calendar calendar = new GregorianCalendar();
      calendar.setTime( KANUtil.createDate( employeeSalaryAdjustmentTempVO.getNewStartDate() ) );
      calendar.add( Calendar.DATE, -1 );
      employeeContractSalaryVO_db.setStartDate( KANUtil.formatDate( calendar.getTime(), "yyyy-MM-dd" ) );
      employeeContractSalaryVO_db.setEndDate( KANUtil.formatDate( calendar.getTime(), "yyyy-MM-dd" ) );
      employeeContractSalaryVO_db.setShowToTS( "2" );
      employeeContractSalaryVO_db.setProbationUsing( "2" );
      employeeContractSalaryVO_db.setBaseFrom( "0" );
      employeeContractSalaryVO_db.setResultCap( "0.00" );
      employeeContractSalaryVO_db.setResultFloor( "0.00" );
      employeeContractSalaryVO_db.setStatus( "1" );
      employeeContractSalaryVO_db.setDeleted( "1" );
      employeeContractSalaryVO_db.setDescription( "Import system generation" );
      employeeContractSalaryVO_db.setCreateBy( employeeSalaryAdjustmentTempVO.getCreateBy() );
      employeeContractSalaryVO_db.setCreateDate( new Date() );
      employeeContractSalaryVO_db.setModifyBy( employeeSalaryAdjustmentTempVO.getModifyBy() );
      employeeContractSalaryVO_db.setModifyDate( new Date() );
      // 继承结算规则设置
      if ( clientOrderHeaderVO != null )
      {
         employeeContractSalaryVO_db.setSalaryType( clientOrderHeaderVO.getSalaryType() );
         employeeContractSalaryVO_db.setDivideType( clientOrderHeaderVO.getDivideType() );
         employeeContractSalaryVO_db.setDivideTypeIncomplete( clientOrderHeaderVO.getDivideTypeIncomplete() );
         employeeContractSalaryVO_db.setExcludeDivideItemIds( clientOrderHeaderVO.getExcludeDivideItemIds() );
      }

      return employeeContractSalaryVO_db;
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

}

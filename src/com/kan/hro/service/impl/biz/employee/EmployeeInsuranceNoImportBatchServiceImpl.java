package com.kan.hro.service.impl.biz.employee;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeInsuranceNoImportBatchDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeInsuranceNoImportHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;
import com.kan.hro.service.inf.biz.employee.EmployeeInsuranceNoImportBatchService;

public class EmployeeInsuranceNoImportBatchServiceImpl extends ContextService implements EmployeeInsuranceNoImportBatchService
{

   private EmployeeInsuranceNoImportBatchDao employeeInsuranceNoImportBatchDao;
   
   private EmployeeInsuranceNoImportHeaderDao employeeInsuranceNoImportHeaderDao;
   
   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getEmployeeInsuranceNoImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( employeeInsuranceNoImportBatchDao.countEmployeeInsuranceNoImportBatchVOsByCondition( ( EmployeeInsuranceNoImportBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeInsuranceNoImportBatchDao.getEmployeeInsuranceNoImportBatchVOsByCondition( ( EmployeeInsuranceNoImportBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeInsuranceNoImportBatchDao.getEmployeeInsuranceNoImportBatchVOsByCondition( ( EmployeeInsuranceNoImportBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeInsuranceNoImportBatchVO getEmployeeInsuranceNoImportBatchById( String batchId ) throws KANException
   {
      return employeeInsuranceNoImportBatchDao.getEmployeeInsuranceNoImportBatchVOByBatchId( batchId );
   }

   @Override
   public int updateEmployeeInsuranceNoImportBatch( final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO ) throws KANException
   {
      int i = 0;
      try
      {
         // 开启事务
         this.startTransaction();
         i = i + employeeInsuranceNoImportHeaderDao.updateEmployeeContractCarsNumber( employeeInsuranceNoImportBatchVO.getBatchId() );
         i = i + employeeInsuranceNoImportHeaderDao.updateEmployeeContractCBNumber( employeeInsuranceNoImportBatchVO.getBatchId() );
         employeeInsuranceNoImportHeaderDao.updateHeaderStatus(employeeInsuranceNoImportBatchVO.getBatchId());
         employeeInsuranceNoImportBatchDao.updateBathStatus( employeeInsuranceNoImportBatchVO );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return i;
   }

   @Override
   public int backBatch( final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         employeeInsuranceNoImportHeaderDao.deleteEmployeeInsuranceNoImportHeaderTempByBatchId( employeeInsuranceNoImportBatchVO.getBatchId() );
         commonBatchDao.deleteCommonBatch( employeeInsuranceNoImportBatchVO.getBatchId() );
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

   public EmployeeInsuranceNoImportBatchDao getEmployeeInsuranceNoImportBatchDao()
   {
      return employeeInsuranceNoImportBatchDao;
   }

   public void setEmployeeInsuranceNoImportBatchDao( EmployeeInsuranceNoImportBatchDao employeeInsuranceNoImportBatchDao )
   {
      this.employeeInsuranceNoImportBatchDao = employeeInsuranceNoImportBatchDao;
   }

   public EmployeeInsuranceNoImportHeaderDao getEmployeeInsuranceNoImportHeaderDao()
   {
      return employeeInsuranceNoImportHeaderDao;
   }

   public void setEmployeeInsuranceNoImportHeaderDao( EmployeeInsuranceNoImportHeaderDao employeeInsuranceNoImportHeaderDao )
   {
      this.employeeInsuranceNoImportHeaderDao = employeeInsuranceNoImportHeaderDao;
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }
}

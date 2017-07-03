package com.kan.hro.service.impl.biz.employee;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeInsuranceNoImportBatchDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeInsuranceNoImportHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportHeaderVO;
import com.kan.hro.service.inf.biz.employee.EmployeeInsuranceNoImportHeaderService;

public class EmployeeInsuranceNoImportHeaderServiceImpl extends ContextService implements EmployeeInsuranceNoImportHeaderService
{
   private EmployeeInsuranceNoImportBatchDao employeeInsuranceNoImportBatchDao;

   private EmployeeInsuranceNoImportHeaderDao employeeInsuranceNoImportHeaderDao;

   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getEmployeeInsuranceNoImportHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( employeeInsuranceNoImportHeaderDao.countEmployeeInsuranceNoImportHeaderVOsByCondition( ( EmployeeInsuranceNoImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeInsuranceNoImportHeaderDao.getEmployeeInsuranceNoImportHeaderVOsByCondition( ( EmployeeInsuranceNoImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeInsuranceNoImportHeaderDao.getEmployeeInsuranceNoImportHeaderVOsByCondition( ( EmployeeInsuranceNoImportHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public int backUpRecord( String[] ids, String batchId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         employeeInsuranceNoImportHeaderDao.deleteHeaderTempRecord( ids );
         int count = employeeInsuranceNoImportHeaderDao.getHeaderCountByBatchId( batchId );
         if ( count == 0 )
         {
            commonBatchDao.deleteCommonBatch( batchId );
         }
         // 提交事务
         this.commitTransaction();
         return count;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public EmployeeInsuranceNoImportHeaderVO getEmployeeInsuranceNoImportHeaderVOsById( final String headerId, final String accountId ) throws KANException
   {
      return employeeInsuranceNoImportHeaderDao.getEmployeeInsuranceNoImportHeaderVOsById( headerId, accountId );
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

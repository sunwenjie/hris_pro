package com.kan.hro.service.impl.biz.employee;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractImportBatchDao;
import com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractImportBatchService;

public class EmployeeContractImportBatchServiceImpl extends ContextService implements EmployeeContractImportBatchService
{
   @Override
   public PagedListHolder getEmployeeContractImportBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeContractImportBatchDao employeeContractImportBatchDao = ( EmployeeContractImportBatchDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractImportBatchDao.countEmployeeContractImportBatchVOsByCondition( ( EmployeeContractImportBatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractImportBatchDao.getEmployeeContractImportBatchVOsByCondition( ( EmployeeContractImportBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractImportBatchDao.getEmployeeContractImportBatchVOsByCondition( ( EmployeeContractImportBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractImportBatchVO getEmployeeContractImportBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( ( EmployeeContractImportBatchDao ) getDao() ).getEmployeeContractImportBatchVOByBatchId( batchId );
   }

   @Override
   public int updateEmployeeContractImportBatch( EmployeeContractImportBatchVO commonBatchVO ) throws KANException
   {
      return ( ( EmployeeContractImportBatchDao ) getDao() ).updateEmployeeContractImportBatch( commonBatchVO );
   }

   @Override
   public int insertEmployeeContractImportBatch( EmployeeContractImportBatchVO commonBatchVO ) throws KANException
   {
      return ( ( EmployeeContractImportBatchDao ) getDao() ).insertEmployeeContractImportBatch( commonBatchVO );
   }

}

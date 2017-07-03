package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTTempService;

public class EmployeeContractOTTempServiceImpl extends ContextService implements EmployeeContractOTTempService
{
   @Override
   public PagedListHolder getEmployeeContractOTTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractOTTempDao employeeContractOTTempDao = ( EmployeeContractOTTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractOTTempDao.countEmployeeContractOTTempVOsByCondition( ( EmployeeContractOTVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractOTTempDao.getEmployeeContractOTTempVOsByCondition( ( EmployeeContractOTVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractOTTempDao.getEmployeeContractOTTempVOsByCondition( ( EmployeeContractOTVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractOTVO getEmployeeContractOTTempVOByEmployeeOTId( final String employeeOTId ) throws KANException
   {
      return ( ( EmployeeContractOTTempDao ) getDao() ).getEmployeeContractOTTempVOByEmployeeOTId( employeeOTId );
   }

   @Override
   public int insertEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return ( ( EmployeeContractOTTempDao ) getDao() ).insertEmployeeContractOTTemp( employeeContractOTVO );
   }

   @Override
   public int updateEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return ( ( EmployeeContractOTTempDao ) getDao() ).updateEmployeeContractOTTemp( employeeContractOTVO );
   }

   @Override
   public int deleteEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      employeeContractOTVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractOTTempDao ) getDao() ).updateEmployeeContractOTTemp( employeeContractOTVO );
   }

   @Override
   public List< Object > getEmployeeContractOTTempVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractOTTempDao ) getDao() ).getEmployeeContractOTTempVOsByContractId( contractId );
   }
}

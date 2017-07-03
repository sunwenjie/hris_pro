package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveTempService;

public class EmployeeContractLeaveTempServiceImpl extends ContextService implements EmployeeContractLeaveTempService
{
   @Override
   public PagedListHolder getEmployeeContractLeaveTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractLeaveTempDao employeeContractLeaveTempDao = ( EmployeeContractLeaveTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractLeaveTempDao.countEmployeeContractLeaveTempVOsByCondition( ( EmployeeContractLeaveVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractLeaveTempDao.getEmployeeContractLeaveTempVOsByCondition( ( EmployeeContractLeaveVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractLeaveTempDao.getEmployeeContractLeaveTempVOsByCondition( ( EmployeeContractLeaveVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractLeaveVO getEmployeeContractLeaveTempVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException
   {
      return ( ( EmployeeContractLeaveTempDao ) getDao() ).getEmployeeContractLeaveTempVOByEmployeeLeaveId( employeeLeaveId );
   }

   @Override
   public int insertEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return ( ( EmployeeContractLeaveTempDao ) getDao() ).insertEmployeeContractLeaveTemp( employeeContractLeaveVO );
   }

   @Override
   public int updateEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return ( ( EmployeeContractLeaveTempDao ) getDao() ).updateEmployeeContractLeaveTemp( employeeContractLeaveVO );
   }

   @Override
   public int deleteEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      employeeContractLeaveVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractLeaveTempDao ) getDao() ).updateEmployeeContractLeaveTemp( employeeContractLeaveVO );
   }

   @Override
   public List< Object > getEmployeeContractLeaveTempVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractLeaveTempDao ) getDao() ).getEmployeeContractLeaveTempVOsByContractId( contractId );
   }
}

package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;

public class EmployeeContractOTServiceImpl extends ContextService implements EmployeeContractOTService
{
   @Override
   public PagedListHolder getEmployeeContractOTVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractOTDao employeeContractOTDao = ( EmployeeContractOTDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractOTDao.countEmployeeContractOTVOsByCondition( ( EmployeeContractOTVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractOTDao.getEmployeeContractOTVOsByCondition( ( EmployeeContractOTVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractOTDao.getEmployeeContractOTVOsByCondition( ( EmployeeContractOTVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractOTVO getEmployeeContractOTVOByEmployeeOTId( final String employeeOTId ) throws KANException
   {
      return ( ( EmployeeContractOTDao ) getDao() ).getEmployeeContractOTVOByEmployeeOTId( employeeOTId );
   }

   @Override
   public int insertEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return ( ( EmployeeContractOTDao ) getDao() ).insertEmployeeContractOT( employeeContractOTVO );
   }

   @Override
   public int updateEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return ( ( EmployeeContractOTDao ) getDao() ).updateEmployeeContractOT( employeeContractOTVO );
   }

   @Override
   public int deleteEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      employeeContractOTVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractOTDao ) getDao() ).updateEmployeeContractOT( employeeContractOTVO );
   }

   @Override
   public List< Object > getEmployeeContractOTVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractOTDao ) getDao() ).getEmployeeContractOTVOsByContractId( contractId );
   }
}

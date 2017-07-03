package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSettlementDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSettlementVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSettlementService;

public class EmployeeContractSettlementServiceImpl extends ContextService implements EmployeeContractSettlementService
{
   @Override
   public PagedListHolder getEmployeeContractSettlementVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeContractSettlementDao employeeContractSettlementDao = ( EmployeeContractSettlementDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSettlementDao.countEmployeeContractSettlementVOsByCondition( ( EmployeeContractSettlementVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSettlementDao.getEmployeeContractSettlementVOsByCondition( ( EmployeeContractSettlementVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSettlementDao.getEmployeeContractSettlementVOsByCondition( ( EmployeeContractSettlementVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSettlementVO getEmployeeContractSettlementVOByEmployeeSettlementId( String employeeSettlementId ) throws KANException
   {
      return ( ( EmployeeContractSettlementDao ) getDao() ).getEmployeeContractSettlementVOByEmployeeSettlementId( employeeSettlementId );
   }

   @Override
   public int insertEmployeeContractSettlement( EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      return ( ( EmployeeContractSettlementDao ) getDao() ).insertEmployeeContractSettlement( employeeContractSettlementVO );
   }

   @Override
   public int updateEmployeeContractSettlement( EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      return ( ( EmployeeContractSettlementDao ) getDao() ).updateEmployeeContractSettlement( employeeContractSettlementVO );
   }

   @Override
   public int deleteEmployeeContractSettlement( EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      employeeContractSettlementVO.setDeleted( "2" );
      return ( ( EmployeeContractSettlementDao ) getDao() ).updateEmployeeContractSettlement( employeeContractSettlementVO );
   }

   @Override
   public List< Object > getEmployeeContractSettlementVOsByContractId( String contractId ) throws KANException
   {
      return ( ( EmployeeContractSettlementDao ) getDao() ).getEmployeeContractSettlementVOsByContractId( contractId );
   }

}

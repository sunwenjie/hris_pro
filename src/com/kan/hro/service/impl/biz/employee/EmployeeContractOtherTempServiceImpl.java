package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOtherTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOtherTempService;

public class EmployeeContractOtherTempServiceImpl extends ContextService implements EmployeeContractOtherTempService
{

   @Override
   public PagedListHolder getEmployeeContractOtherTempVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeContractOtherTempDao employeeContractOtherTempDao = ( EmployeeContractOtherTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractOtherTempDao.countEmployeeContractOtherTempVOsByCondition( ( EmployeeContractOtherVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractOtherTempDao.getEmployeeContractOtherTempVOsByCondition( ( EmployeeContractOtherVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractOtherTempDao.getEmployeeContractOtherTempVOsByCondition( ( EmployeeContractOtherVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractOtherVO getEmployeeContractOtherTempVOByEmployeeOtherId( String employeeOtherId ) throws KANException
   {
      return ( ( EmployeeContractOtherTempDao ) getDao() ).getEmployeeContractOtherTempVOByEmployeeOtherId( employeeOtherId );
   }

   @Override
   public int insertEmployeeContractOtherTemp( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return ( ( EmployeeContractOtherTempDao ) getDao() ).insertEmployeeContractOtherTemp( employeeContractOtherVO );
   }

   @Override
   public int updateEmployeeContractOtherTemp( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return ( ( EmployeeContractOtherTempDao ) getDao() ).updateEmployeeContractOtherTemp( employeeContractOtherVO );
   }

   @Override
   public int deleteEmployeeContractOtherTemp( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      employeeContractOtherVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractOtherTempDao ) getDao() ).updateEmployeeContractOtherTemp( employeeContractOtherVO );
   }

   @Override
   public List< Object > getEmployeeContractOtherTempVOsByContractId( String contractId ) throws KANException
   {
      return ( ( EmployeeContractOtherTempDao ) getDao() ).getEmployeeContractOtherTempVOsByContractId( contractId );
   }

}

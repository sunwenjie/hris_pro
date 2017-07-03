package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOtherDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOtherService;

public class EmployeeContractOtherServiceImpl extends ContextService implements EmployeeContractOtherService
{

   @Override
   public PagedListHolder getEmployeeContractOtherVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeContractOtherDao employeeContractOtherDao = ( EmployeeContractOtherDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractOtherDao.countEmployeeContractOtherVOsByCondition( ( EmployeeContractOtherVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractOtherDao.getEmployeeContractOtherVOsByCondition( ( EmployeeContractOtherVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractOtherDao.getEmployeeContractOtherVOsByCondition( ( EmployeeContractOtherVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractOtherVO getEmployeeContractOtherVOByEmployeeOtherId( String employeeOtherId ) throws KANException
   {
      return ( ( EmployeeContractOtherDao ) getDao() ).getEmployeeContractOtherVOByEmployeeOtherId( employeeOtherId );
   }

   @Override
   public int insertEmployeeContractOther( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return ( ( EmployeeContractOtherDao ) getDao() ).insertEmployeeContractOther( employeeContractOtherVO );
   }

   @Override
   public int updateEmployeeContractOther( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return ( ( EmployeeContractOtherDao ) getDao() ).updateEmployeeContractOther( employeeContractOtherVO );
   }

   @Override
   public int deleteEmployeeContractOther( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      employeeContractOtherVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractOtherDao ) getDao() ).updateEmployeeContractOther( employeeContractOtherVO );
   }

   @Override
   public List< Object > getEmployeeContractOtherVOsByContractId( String contractId ) throws KANException
   {
      return ( ( EmployeeContractOtherDao ) getDao() ).getEmployeeContractOtherVOsByContractId( contractId );
   }

}

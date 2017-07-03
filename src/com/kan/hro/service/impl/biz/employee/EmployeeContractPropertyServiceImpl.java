package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractPropertyDao;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractPropertyService;

public class EmployeeContractPropertyServiceImpl extends ContextService implements EmployeeContractPropertyService
{

   @Override
   public PagedListHolder getEmployeeContractPropertyVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeContractPropertyDao employeeContractPropertyDao = ( EmployeeContractPropertyDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractPropertyDao.countEmployeeContractPropertyVOsByCondition( ( EmployeeContractPropertyVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractPropertyDao.getEmployeeContractPropertyVOsByCondition( ( EmployeeContractPropertyVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractPropertyDao.getEmployeeContractPropertyVOsByCondition( ( EmployeeContractPropertyVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractPropertyVO getEmployeeContractPropertyVOByEmployeeContractPropertyId( String employeeContractPropertyId ) throws KANException
   {
      return ( ( EmployeeContractPropertyDao ) getDao() ).getEmployeeContractPropertyVOByEmployeeContractPropertyId( employeeContractPropertyId );
   }

   @Override
   public int updateEmployeeContractProperty( EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException
   {
      return ( ( EmployeeContractPropertyDao ) getDao() ).updateEmployeeContractProperty( employeeContractPropertyVO );
   }

   @Override
   public int insertEmployeeContractProperty( EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException
   {
      return ( ( EmployeeContractPropertyDao ) getDao() ).insertEmployeeContractProperty( employeeContractPropertyVO );
   }

   @Override
   public int deleteEmployeeContractProperty( EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýemployeeContractPropertyVO
      employeeContractPropertyVO.setDeleted( EmployeeContractPropertyVO.FALSE );
      return ( ( EmployeeContractPropertyDao ) getDao() ).updateEmployeeContractProperty( employeeContractPropertyVO );
   }

   @Override
   public List< Object > getEmployeeContractPropertyVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractPropertyDao ) getDao() ).getEmployeeContractPropertyVOsByContractId( contractId );
   }

}

package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDetailDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBDetailService;

public class EmployeeContractSBDetailServiceImpl extends ContextService implements EmployeeContractSBDetailService
{
   @Override
   public PagedListHolder getEmployeeContractSBDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSBDetailDao employeeContractSBDetailDao = ( EmployeeContractSBDetailDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBDetailDao.countEmployeeContractSBDetailVOsByCondition( ( EmployeeContractSBDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBDetailDao.getEmployeeContractSBDetailVOsByCondition( ( EmployeeContractSBDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBDetailDao.getEmployeeContractSBDetailVOsByCondition( ( EmployeeContractSBDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSBDetailVO getEmployeeContractSBDetailVOByEmployeeSBDetailId( final String employeeSBDetailId ) throws KANException
   {
      return ( ( EmployeeContractSBDetailDao ) getDao() ).getEmployeeContractSBDetailVOByEmployeeSBDetailId( employeeSBDetailId );
   }

   @Override
   public int insertEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException
   {
      return ( ( EmployeeContractSBDetailDao ) getDao() ).insertEmployeeContractSBDetail( employeeContractSBDetailVO );
   }

   @Override
   public int updateEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException
   {
      return ( ( EmployeeContractSBDetailDao ) getDao() ).updateEmployeeContractSBDetail( employeeContractSBDetailVO );
   }

   @Override
   public int deleteEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException
   {

      EmployeeContractSBDetailDao dao = ( EmployeeContractSBDetailDao ) getDao();
      EmployeeContractSBDetailVO employeeContractSBDetailVO_DB = dao.getEmployeeContractSBDetailVOByEmployeeSBDetailId( employeeContractSBDetailVO.getEmployeeSBDetailId() );
      employeeContractSBDetailVO_DB.setDeleted( BaseVO.FALSE );
      employeeContractSBDetailVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeContractSBDetail( employeeContractSBDetailVO_DB );
   }

   @Override
   public List< Object > getEmployeeContractSBDetailVOsByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      if ( employeeSBId == null || employeeSBId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeContractSBDetailDao ) getDao() ).getEmployeeContractSBDetailVOsByEmployeeSBId( employeeSBId );
      }
   }
}

package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeMembershipDao;
import com.kan.hro.domain.biz.employee.EmployeeMembershipVO;
import com.kan.hro.service.inf.biz.employee.EmployeeMembershipService;

public class EmployeeMembershipServiceImpl extends ContextService implements EmployeeMembershipService
{

   @Override
   public PagedListHolder getEmployeeMembershipVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeMembershipDao employeeMembershipDao = ( EmployeeMembershipDao ) getDao();
      pagedListHolder.setHolderSize( employeeMembershipDao.countEmployeeMembershipVOsByCondition( ( EmployeeMembershipVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeMembershipDao.getEmployeeMembershipVOsByCondition( ( EmployeeMembershipVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeMembershipDao.getEmployeeMembershipVOsByCondition( ( EmployeeMembershipVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeMembershipVO getEmployeeMembershipVOByEmployeeMembershipId( final String employeeMembershipId ) throws KANException
   {

      return ( ( EmployeeMembershipDao ) getDao() ).getEmployeeMembershipVOByEmployeeMembershipId( employeeMembershipId );
   }

   @Override
   public int insertEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException
   {

      return ( ( EmployeeMembershipDao ) getDao() ).insertEmployeeMembership( employeeMembershipVO );
   }

   @Override
   public int updateEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException
   {

      return ( ( EmployeeMembershipDao ) getDao() ).updateEmployeeMembership( employeeMembershipVO );
   }

   @Override
   public int deleteEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException
   {

      EmployeeMembershipDao dao = ( EmployeeMembershipDao ) getDao();
      EmployeeMembershipVO employeeMembershipVO_DB = dao.getEmployeeMembershipVOByEmployeeMembershipId( employeeMembershipVO.getEmployeeMembershipId() );
      employeeMembershipVO_DB.setDeleted( BaseVO.FALSE );
      employeeMembershipVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeMembership( employeeMembershipVO_DB );
   }

   @Override
   public List< Object > getEmployeeMembershipVOsByEmployeeId( String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeMembershipDao ) getDao() ).getEmployeeMembershipVOsByEmployeeId( employeeId );
      }
   }
}

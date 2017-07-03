package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeEmergencyDao;
import com.kan.hro.domain.biz.employee.EmployeeEmergencyVO;
import com.kan.hro.service.inf.biz.employee.EmployeeEmergencyService;

public class EmployeeEmergencyServiceImpl extends ContextService implements EmployeeEmergencyService
{

   @Override
   public PagedListHolder getEmployeeEmergencyVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeEmergencyDao employeeEmergencyDao = ( EmployeeEmergencyDao ) getDao();
      pagedListHolder.setHolderSize( employeeEmergencyDao.countEmployeeEmergencyVOsByCondition( ( EmployeeEmergencyVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeEmergencyDao.getEmployeeEmergencyVOsByCondition( ( EmployeeEmergencyVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeEmergencyDao.getEmployeeEmergencyVOsByCondition( ( EmployeeEmergencyVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeEmergencyVO getEmployeeEmergencyVOByEmployeeEmergencyId( final String employeeEmergencyId ) throws KANException
   {

      return ( ( EmployeeEmergencyDao ) getDao() ).getEmployeeEmergencyVOByEmployeeEmergencyId( employeeEmergencyId );
   }

   @Override
   public int insertEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException
   {

      return ( ( EmployeeEmergencyDao ) getDao() ).insertEmployeeEmergency( employeeEmergencyVO );
   }

   @Override
   public int updateEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException
   {

      return ( ( EmployeeEmergencyDao ) getDao() ).updateEmployeeEmergency( employeeEmergencyVO );
   }

   @Override
   public int deleteEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException
   {

      EmployeeEmergencyDao dao = ( EmployeeEmergencyDao ) getDao();
      EmployeeEmergencyVO employeeEmergencyVO_DB = dao.getEmployeeEmergencyVOByEmployeeEmergencyId( employeeEmergencyVO.getEmployeeEmergencyId() );
      employeeEmergencyVO_DB.setDeleted( BaseVO.FALSE );
      employeeEmergencyVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeEmergency( employeeEmergencyVO_DB );
   }

   @Override
   public List< Object > getEmployeeEmergencyVOsByEmployeeId( String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeEmergencyDao ) getDao() ).getEmployeeEmergencyVOsByEmployeeId( employeeId );
      }
   }
}

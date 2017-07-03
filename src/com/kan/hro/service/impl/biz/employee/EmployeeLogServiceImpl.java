package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeLogDao;
import com.kan.hro.domain.biz.employee.EmployeeLogVO;
import com.kan.hro.service.inf.biz.employee.EmployeeLogService;

public class EmployeeLogServiceImpl extends ContextService implements EmployeeLogService
{

   @Override
   public PagedListHolder getEmployeeLogVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeLogDao employeeLogDao = ( EmployeeLogDao ) getDao();
      pagedListHolder.setHolderSize( employeeLogDao.countEmployeeLogVOsByCondition( ( EmployeeLogVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeLogDao.getEmployeeLogVOsByCondition( ( EmployeeLogVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeLogDao.getEmployeeLogVOsByCondition( ( EmployeeLogVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeLogVO getEmployeeLogVOByEmployeeLogId( final String employeeLogId ) throws KANException
   {

      return ( ( EmployeeLogDao ) getDao() ).getEmployeeLogVOByEmployeeLogId( employeeLogId );
   }

   @Override
   public int insertEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException
   {

      return ( ( EmployeeLogDao ) getDao() ).insertEmployeeLog( employeeLogVO );
   }

   @Override
   public int updateEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException
   {

      return ( ( EmployeeLogDao ) getDao() ).updateEmployeeLog( employeeLogVO );
   }

   @Override
   public int deleteEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException
   {
      EmployeeLogDao dao = ( EmployeeLogDao ) getDao();
      EmployeeLogVO employeeLogVO_DB = dao.getEmployeeLogVOByEmployeeLogId( employeeLogVO.getEmployeeLogId() );
      employeeLogVO_DB.setDeleted( BaseVO.FALSE );
      employeeLogVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeLog( employeeLogVO_DB );
   }

   @Override
   public List< Object > getEmployeeLogVOsByEmployeeId( String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeLogDao ) getDao() ).getEmployeeLogVOsByEmployeeId( employeeId );
      }
   }

}

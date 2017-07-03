package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeWorkDao;
import com.kan.hro.domain.biz.employee.EmployeeWorkVO;
import com.kan.hro.service.inf.biz.employee.EmployeeWorkService;

public class EmployeeWorkServiceImpl extends ContextService implements EmployeeWorkService
{

   @Override
   public PagedListHolder getEmployeeWorkVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeWorkDao employeeWorkDao = ( EmployeeWorkDao ) getDao();
      pagedListHolder.setHolderSize( employeeWorkDao.countEmployeeWorkVOsByCondition( ( EmployeeWorkVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeWorkDao.getEmployeeWorkVOsByCondition( ( EmployeeWorkVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeWorkDao.getEmployeeWorkVOsByCondition( ( EmployeeWorkVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeWorkVO getEmployeeWorkVOByEmployeeWorkId( final String employeeWorkId ) throws KANException
   {

      return ( ( EmployeeWorkDao ) getDao() ).getEmployeeWorkVOByEmployeeWorkId( employeeWorkId );
   }

   @Override
   public int insertEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException
   {

      return ( ( EmployeeWorkDao ) getDao() ).insertEmployeeWork( employeeWorkVO );
   }

   @Override
   public int updateEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException
   {

      return ( ( EmployeeWorkDao ) getDao() ).updateEmployeeWork( employeeWorkVO );
   }

   @Override
   public int deleteEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException
   {

      EmployeeWorkDao dao = ( EmployeeWorkDao ) getDao();
      EmployeeWorkVO employeeWorkVO_DB = dao.getEmployeeWorkVOByEmployeeWorkId( employeeWorkVO.getEmployeeWorkId() );
      employeeWorkVO_DB.setDeleted( BaseVO.FALSE );
      employeeWorkVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeWork( employeeWorkVO_DB );
   }

   @Override
   public List< Object > getEmployeeWorkVOsByEmployeeId( String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeWorkDao ) getDao() ).getEmployeeWorkVOsByEmployeeId( employeeId );
      }
   }

   @Override
   public List< Object > getCompanyNameByName( String name ) throws KANException
   {
      if ( name == null || name.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeWorkDao ) getDao() ).getCompanyNameByName( name );
      }
   }

}

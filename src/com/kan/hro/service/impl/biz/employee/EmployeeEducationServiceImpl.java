package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeEducationDao;
import com.kan.hro.domain.biz.employee.EmployeeEducationVO;
import com.kan.hro.service.inf.biz.employee.EmployeeEducationService;

public class EmployeeEducationServiceImpl extends ContextService implements EmployeeEducationService
{

   @Override
   public PagedListHolder getEmployeeEducationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeEducationDao employeeEducationDao = ( EmployeeEducationDao ) getDao();
      pagedListHolder.setHolderSize( employeeEducationDao.countEmployeeEducationVOsByCondition( ( EmployeeEducationVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeEducationDao.getEmployeeEducationVOsByCondition( ( EmployeeEducationVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeEducationDao.getEmployeeEducationVOsByCondition( ( EmployeeEducationVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeEducationVO getEmployeeEducationVOByEmployeeEducationId( final String employeeEducationId ) throws KANException
   {

      return ( ( EmployeeEducationDao ) getDao() ).getEmployeeEducationVOByEmployeeEducationId( employeeEducationId );
   }

   @Override
   public int insertEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException
   {

      return ( ( EmployeeEducationDao ) getDao() ).insertEmployeeEducation( employeeEducationVO );
   }

   @Override
   public int updateEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException
   {

      return ( ( EmployeeEducationDao ) getDao() ).updateEmployeeEducation( employeeEducationVO );
   }

   @Override
   public int deleteEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException
   {

      EmployeeEducationDao dao = ( EmployeeEducationDao ) getDao();
      EmployeeEducationVO employeeEducationVO_DB = dao.getEmployeeEducationVOByEmployeeEducationId( employeeEducationVO.getEmployeeEducationId() );
      employeeEducationVO_DB.setDeleted( BaseVO.FALSE );
      employeeEducationVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeEducation( employeeEducationVO_DB );
   }

   @Override
   public List< Object > getEmployeeEducationVOsByEmployeeId( String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeEducationDao ) getDao() ).getEmployeeEducationVOsByEmployeeId( employeeId );
      }
   }

   @Override
   public List< Object > getSchoolNameBySchoolName( String name ) throws KANException
   {
      return ( ( EmployeeEducationDao ) getDao() ).getSchoolNameBySchoolName( name );
   }

   @Override
   public List< Object > getMajorByMajor( String name ) throws KANException
   {
      return ( ( EmployeeEducationDao ) getDao() ).getMajorByMajor( name );
   }
}

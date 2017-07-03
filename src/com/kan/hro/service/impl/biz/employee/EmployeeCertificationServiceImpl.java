package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeCertificationDao;
import com.kan.hro.domain.biz.employee.EmployeeCertificationVO;
import com.kan.hro.service.inf.biz.employee.EmployeeCertificationService;

public class EmployeeCertificationServiceImpl extends ContextService implements EmployeeCertificationService
{

   @Override
   public PagedListHolder getEmployeeCertificationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeCertificationDao employeeCertificationDao = ( EmployeeCertificationDao ) getDao();
      pagedListHolder.setHolderSize( employeeCertificationDao.countEmployeeCertificationVOsByCondition( ( EmployeeCertificationVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeCertificationDao.getEmployeeCertificationVOsByCondition( ( EmployeeCertificationVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeCertificationDao.getEmployeeCertificationVOsByCondition( ( EmployeeCertificationVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeCertificationVO getEmployeeCertificationVOByEmployeeCertificationId( final String employeeCertificationId ) throws KANException
   {

      return ( ( EmployeeCertificationDao ) getDao() ).getEmployeeCertificationVOByEmployeeCertificationId( employeeCertificationId );
   }

   @Override
   public int insertEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException
   {

      return ( ( EmployeeCertificationDao ) getDao() ).insertEmployeeCertification( employeeCertificationVO );
   }

   @Override
   public int updateEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException
   {

      return ( ( EmployeeCertificationDao ) getDao() ).updateEmployeeCertification( employeeCertificationVO );
   }

   @Override
   public int deleteEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException
   {

      EmployeeCertificationDao dao = ( EmployeeCertificationDao ) getDao();
      EmployeeCertificationVO employeeCertificationVO_DB = dao.getEmployeeCertificationVOByEmployeeCertificationId( employeeCertificationVO.getEmployeeCertificationId() );
      employeeCertificationVO_DB.setDeleted( BaseVO.FALSE );
      employeeCertificationVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeCertification( employeeCertificationVO_DB );
   }

   @Override
   public List< Object > getEmployeeCertificationVOsByEmployeeId( String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeCertificationDao ) getDao() ).getEmployeeCertificationVOsByEmployeeId( employeeId );
      }
   }
}

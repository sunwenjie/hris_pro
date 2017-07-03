package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeLanguageDao;
import com.kan.hro.domain.biz.employee.EmployeeLanguageVO;
import com.kan.hro.service.inf.biz.employee.EmployeeLanguageService;

public class EmployeeLanguageServiceImpl extends ContextService implements EmployeeLanguageService
{
   @Override
   public PagedListHolder getEmployeeLanguageVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeLanguageDao employeeLanguageDao = ( EmployeeLanguageDao ) getDao();
      pagedListHolder.setHolderSize( employeeLanguageDao.countEmployeeLanguageVOsByCondition( ( EmployeeLanguageVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeLanguageDao.getEmployeeLanguageVOsByCondition( ( EmployeeLanguageVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeLanguageDao.getEmployeeLanguageVOsByCondition( ( EmployeeLanguageVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeLanguageVO getEmployeeLanguageVOByEmployeeLanguageId( final String employeeLanguageId ) throws KANException
   {

      return ( ( EmployeeLanguageDao ) getDao() ).getEmployeeLanguageVOByEmployeeLanguageId( employeeLanguageId );
   }

   @Override
   public int insertEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException
   {

      return ( ( EmployeeLanguageDao ) getDao() ).insertEmployeeLanguage( employeeLanguageVO );
   }

   @Override
   public int updateEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException
   {

      return ( ( EmployeeLanguageDao ) getDao() ).updateEmployeeLanguage( employeeLanguageVO );
   }

   @Override
   public int deleteEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException
   {

      EmployeeLanguageDao dao = ( EmployeeLanguageDao ) getDao();
      EmployeeLanguageVO employeeLanguageVO_DB = dao.getEmployeeLanguageVOByEmployeeLanguageId( employeeLanguageVO.getEmployeeLanguageId() );
      employeeLanguageVO_DB.setDeleted( BaseVO.FALSE );
      employeeLanguageVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeLanguage( employeeLanguageVO_DB );
   }

   @Override
   public List< Object > getEmployeeLanguageVOsByEmployeeId( final String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeLanguageDao ) getDao() ).getEmployeeLanguageVOsByEmployeeId( employeeId );
      }
   }
}

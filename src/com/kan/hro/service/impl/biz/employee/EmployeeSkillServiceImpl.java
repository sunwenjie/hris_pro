package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeSkillDao;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSkillService;

public class EmployeeSkillServiceImpl extends ContextService implements EmployeeSkillService
{
   @Override
   public PagedListHolder getEmployeeSkillVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeSkillDao employeeSkillDao = ( EmployeeSkillDao ) getDao();
      pagedListHolder.setHolderSize( employeeSkillDao.countEmployeeSkillVOsByCondition( ( EmployeeSkillVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeSkillDao.getEmployeeSkillVOsByCondition( ( EmployeeSkillVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeSkillDao.getEmployeeSkillVOsByCondition( ( EmployeeSkillVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public List< Object > getEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {
      return ( ( EmployeeSkillDao ) getDao() ).getEmployeeSkillVOsByCondition( employeeSkillVO );
   }

   @Override
   public EmployeeSkillVO getEmployeeSkillVOByEmployeeSkillId( final String employeeSkillId ) throws KANException
   {

      return ( ( EmployeeSkillDao ) getDao() ).getEmployeeSkillVOByEmployeeSkillId( employeeSkillId );
   }

   @Override
   public int insertEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {

      return ( ( EmployeeSkillDao ) getDao() ).insertEmployeeSkill( employeeSkillVO );
   }

   @Override
   public int updateEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {

      return ( ( EmployeeSkillDao ) getDao() ).updateEmployeeSkill( employeeSkillVO );
   }

   @Override
   public int deleteEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {

      EmployeeSkillDao dao = ( EmployeeSkillDao ) getDao();
      EmployeeSkillVO employeeSkillVO_DB = dao.getEmployeeSkillVOByEmployeeSkillId( employeeSkillVO.getEmployeeSkillId() );
      employeeSkillVO_DB.setDeleted( BaseVO.FALSE );
      employeeSkillVO_DB.setModifyDate( new Date() );
      return dao.updateEmployeeSkill( employeeSkillVO_DB );
   }

   @Override
   public List< Object > getEmployeeSkillVOsByEmployeeId( final String employeeId ) throws KANException
   {
      if ( employeeId == null || employeeId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeSkillDao ) getDao() ).getEmployeeSkillVOsByEmployeeId( employeeId );
      }
   }

}

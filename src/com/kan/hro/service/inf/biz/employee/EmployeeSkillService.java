package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;

public interface EmployeeSkillService
{
   public abstract PagedListHolder getEmployeeSkillVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO ) throws KANException;

   public abstract EmployeeSkillVO getEmployeeSkillVOByEmployeeSkillId( final String employeeSkillId ) throws KANException;

   public abstract int insertEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException;

   public abstract int updateEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException;

   public abstract int deleteEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException;

   public abstract List< Object > getEmployeeSkillVOsByEmployeeId( final String employeeId ) throws KANException;
}

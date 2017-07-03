package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;

public interface EmployeeSkillDao
{
   public abstract int countEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO ) throws KANException;

   public abstract List< Object > getEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO ) throws KANException;

   public abstract List< Object > getEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeSkillVO getEmployeeSkillVOByEmployeeSkillId( final String employeeSkillId ) throws KANException;

   public abstract int insertEmployeeSkill ( final EmployeeSkillVO employeeSkillVO) throws KANException;

   public abstract int updateEmployeeSkill ( final EmployeeSkillVO employeeSkillVO ) throws KANException;

   public abstract int deleteEmployeeSkill ( final String employeeSkillId  ) throws KANException;
   
   public abstract List< Object > getEmployeeSkillVOsByEmployeeId( final String employeeId ) throws KANException;
   
}

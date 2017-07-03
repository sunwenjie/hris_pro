package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeEducationVO;

public interface EmployeeEducationDao
{
   public abstract int countEmployeeEducationVOsByCondition( final EmployeeEducationVO employeeEducationVO ) throws KANException;

   public abstract List< Object > getEmployeeEducationVOsByCondition( final EmployeeEducationVO employeeEducationVO ) throws KANException;

   public abstract List< Object > getEmployeeEducationVOsByCondition( final EmployeeEducationVO employeeEducationVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeEducationVO getEmployeeEducationVOByEmployeeEducationId( final String employeeEducationId ) throws KANException;

   public abstract int insertEmployeeEducation ( final EmployeeEducationVO employeeEducationVO) throws KANException;

   public abstract int updateEmployeeEducation ( final EmployeeEducationVO employeeEducationVO ) throws KANException;

   public abstract int deleteEmployeeEducation ( final String employeeEducationId ) throws KANException;
   
   public abstract List< Object > getEmployeeEducationVOsByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > getSchoolNameBySchoolName(final String name )throws KANException;

   public abstract List< Object > getMajorByMajor( final String name )throws KANException;
   
}

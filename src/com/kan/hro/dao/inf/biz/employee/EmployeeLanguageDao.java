package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeLanguageVO;

public interface EmployeeLanguageDao
{
   public abstract int countEmployeeLanguageVOsByCondition( final EmployeeLanguageVO employeeLanguageVO ) throws KANException;

   public abstract List< Object > getEmployeeLanguageVOsByCondition( final EmployeeLanguageVO employeeLanguageVO ) throws KANException;

   public abstract List< Object > getEmployeeLanguageVOsByCondition( final EmployeeLanguageVO employeeLanguageVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeLanguageVO getEmployeeLanguageVOByEmployeeLanguageId( final String employeeLanguageId ) throws KANException;

   public abstract int insertEmployeeLanguage ( final EmployeeLanguageVO employeeLanguageVO) throws KANException;

   public abstract int updateEmployeeLanguage ( final EmployeeLanguageVO employeeLanguageVO ) throws KANException;

   public abstract int deleteEmployeeLanguage ( final String employeeLanguageId  ) throws KANException;
   
   public abstract List< Object > getEmployeeLanguageVOsByEmployeeId( final String employeeId ) throws KANException;
   
}

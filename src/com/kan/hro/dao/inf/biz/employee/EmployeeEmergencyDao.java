package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeEmergencyVO;

public interface EmployeeEmergencyDao
{
   public abstract int countEmployeeEmergencyVOsByCondition( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException;

   public abstract List< Object > getEmployeeEmergencyVOsByCondition( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException;

   public abstract List< Object > getEmployeeEmergencyVOsByCondition( final EmployeeEmergencyVO employeeEmergencyVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeEmergencyVO getEmployeeEmergencyVOByEmployeeEmergencyId( final String employeeEmergencyId ) throws KANException;

   public abstract int insertEmployeeEmergency ( final EmployeeEmergencyVO employeeEmergencyVO) throws KANException;

   public abstract int updateEmployeeEmergency ( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException;

   public abstract int deleteEmployeeEmergency ( final String employeeEmergencyId) throws KANException;
   
   public abstract List< Object > getEmployeeEmergencyVOsByEmployeeId( final String employeeId ) throws KANException;
   
}

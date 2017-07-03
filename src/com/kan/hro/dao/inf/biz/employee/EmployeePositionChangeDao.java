package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;

public interface EmployeePositionChangeDao
{
   public int countEmployeePositionChangeVOsByCondition( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public List< Object > getEmployeePositionChangeVOsByCondition( final EmployeePositionChangeVO employeePositionChangeVO, RowBounds rowBounds ) throws KANException;

   public List< Object > getEmployeePositionChangeVOsByCondition( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public void insertEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public EmployeePositionChangeVO getEmployeePositionChangeVOByPositionChangeId( String positionChangeId ) throws KANException;

   public void updateEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public void deleteEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public List< Object > getEmployeePositionChangeVOsByDateAndStatus( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public void updateEmployeePositionChangeStatus( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public int getEffectivePositionChangeVOCountByEmployeeId( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;
}

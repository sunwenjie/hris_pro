package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;

public interface EmployeeContractSBDetailDao
{
   public abstract int countEmployeeContractSBDetailVOsByCondition( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBDetailVOsByCondition( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBDetailVOsByCondition( final EmployeeContractSBDetailVO employeeContractSBDetailVO, RowBounds rowBounds ) throws KANException;
                                              
   public abstract EmployeeContractSBDetailVO getEmployeeContractSBDetailVOByEmployeeSBDetailId( final String employeeSBDetailId ) throws KANException;

   public abstract int insertEmployeeContractSBDetail ( final EmployeeContractSBDetailVO employeeContractSBDetailVO) throws KANException;

   public abstract int updateEmployeeContractSBDetail ( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException;

   public abstract int deleteEmployeeContractSBDetail ( final String employeeSBDetailId  ) throws KANException;
   
   public abstract List< Object > getEmployeeContractSBDetailVOsByEmployeeSBId( final String employeeSBId ) throws KANException;
   
}

package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;

public interface EmployeeContractCBDao
{
   public abstract int countEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractCBVO getEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public abstract int insertEmployeeContractCB ( final EmployeeContractCBVO employeeContractCBVO) throws KANException;

   public abstract int updateEmployeeContractCB ( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int deleteEmployeeContractCB ( final String employeeCBId  ) throws KANException;
   
   public abstract List< Object > getEmployeeContractCBVOsByContractId( final String contractId ) throws KANException;

   public abstract int countFullEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getFullEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, final RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractCBVO getFullEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public List< Object > getFullEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;
   
   public List< Object > getEmployeeContractCBVOsBySolutionNameZH( final EmployeeContractCBVO employeeContractCBVO) throws KANException;


}

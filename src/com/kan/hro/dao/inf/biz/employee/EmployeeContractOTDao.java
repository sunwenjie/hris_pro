package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;

public interface EmployeeContractOTDao
{
   public abstract int countEmployeeContractOTVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOTVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOTVOsByCondition( final EmployeeContractOTVO employeeContractOTVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractOTVO getEmployeeContractOTVOByEmployeeOTId( final String employeeOTId ) throws KANException;

   public abstract int insertEmployeeContractOT ( final EmployeeContractOTVO employeeContractOTVO) throws KANException;

   public abstract int updateEmployeeContractOT ( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract int deleteEmployeeContractOT ( final String employeeOTId  ) throws KANException;
   
   public abstract List< Object > getEmployeeContractOTVOsByContractId( final String contractId ) throws KANException;
   
}

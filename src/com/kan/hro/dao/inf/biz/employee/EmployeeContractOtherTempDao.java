package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;

public interface EmployeeContractOtherTempDao
{
   public abstract int countEmployeeContractOtherTempVOsByCondition( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOtherTempVOsByCondition( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOtherTempVOsByCondition( final EmployeeContractOtherVO employeeContractOtherVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractOtherVO getEmployeeContractOtherTempVOByEmployeeOtherId( final String employeeOtherId ) throws KANException;

   public abstract int insertEmployeeContractOtherTemp( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract int updateEmployeeContractOtherTemp( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract int deleteEmployeeContractOtherTemp( final String employeeOtherId ) throws KANException;

   public abstract List< Object > getEmployeeContractOtherTempVOsByContractId( final String contractId ) throws KANException;

}

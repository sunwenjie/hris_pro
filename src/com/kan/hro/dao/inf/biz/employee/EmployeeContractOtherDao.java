package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;

public interface EmployeeContractOtherDao
{
   public abstract int countEmployeeContractOtherVOsByCondition( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOtherVOsByCondition( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOtherVOsByCondition( final EmployeeContractOtherVO employeeContractOtherVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractOtherVO getEmployeeContractOtherVOByEmployeeOtherId( final String employeeOtherId ) throws KANException;

   public abstract int insertEmployeeContractOther ( final EmployeeContractOtherVO employeeContractOtherVO) throws KANException;

   public abstract int updateEmployeeContractOther ( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract int deleteEmployeeContractOther ( final String employeeOtherId  ) throws KANException;
   
   public abstract List< Object > getEmployeeContractOtherVOsByContractId( final String contractId ) throws KANException;
   
}

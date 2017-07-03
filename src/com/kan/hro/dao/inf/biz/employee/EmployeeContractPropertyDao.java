package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;

public interface EmployeeContractPropertyDao
{
   public abstract int countEmployeeContractPropertyVOsByCondition( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException;

   public abstract List< Object > getEmployeeContractPropertyVOsByCondition( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException;

   public abstract List< Object > getEmployeeContractPropertyVOsByCondition( final EmployeeContractPropertyVO employeeContractPropertyVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractPropertyVO getEmployeeContractPropertyVOByEmployeeContractPropertyId( final String employeeContractPropertyId ) throws KANException;

   public abstract int updateEmployeeContractProperty( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException;

   public abstract int insertEmployeeContractProperty( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException;

   public abstract int deleteEmployeeContractProperty( final String propertyId ) throws KANException;

   public abstract List< Object > getEmployeeContractPropertyVOsByContractId( final String contractId ) throws KANException;

}

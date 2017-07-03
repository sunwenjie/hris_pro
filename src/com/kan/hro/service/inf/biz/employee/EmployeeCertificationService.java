package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeCertificationVO;

public interface EmployeeCertificationService
{
   public abstract PagedListHolder getEmployeeCertificationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeCertificationVO getEmployeeCertificationVOByEmployeeCertificationId( final String employeeCertificationId ) throws KANException;

   public abstract int insertEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException;

   public abstract int updateEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException;

   public abstract int deleteEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException;
   
   public abstract List< Object > getEmployeeCertificationVOsByEmployeeId( final String employeeId ) throws KANException;
}

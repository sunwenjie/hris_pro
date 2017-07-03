package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeCertificationVO;

public interface EmployeeCertificationDao
{
   public abstract int countEmployeeCertificationVOsByCondition( final EmployeeCertificationVO employeeCertificationVO ) throws KANException;

   public abstract List< Object > getEmployeeCertificationVOsByCondition( final EmployeeCertificationVO employeeCertificationVO ) throws KANException;

   public abstract List< Object > getEmployeeCertificationVOsByCondition( final EmployeeCertificationVO employeeCertificationVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeCertificationVO getEmployeeCertificationVOByEmployeeCertificationId( final String employeeCertificationId ) throws KANException;

   public abstract int insertEmployeeCertification ( final EmployeeCertificationVO employeeCertificationVO) throws KANException;

   public abstract int updateEmployeeCertification ( final EmployeeCertificationVO employeeCertificationVO ) throws KANException;

   public abstract int deleteEmployeeCertification ( final String employeeCertificationId  ) throws KANException;
   
   public abstract List< Object > getEmployeeCertificationVOsByEmployeeId( final String employeeId ) throws KANException;
   
}

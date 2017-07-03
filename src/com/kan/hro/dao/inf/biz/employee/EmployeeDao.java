package com.kan.hro.dao.inf.biz.employee;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeBaseView;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public interface EmployeeDao
{
   public abstract int countEmployeeVOsByCondition( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getEmployeeVOsByCondition( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getEmployeeVOsByCondition( final EmployeeVO employeeVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeVO getEmployeeVOByEmployeeId( final String employeeId ) throws KANException;

   public abstract int updateEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract int insertEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract int deleteEmployee( final String employeeId ) throws KANException;

   public abstract List< Object > getEmployeeBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getEmployeeBaseViewsByCondition( final EmployeeBaseView employeeBaseView ) throws KANException;

   public abstract List< Object > getEmployeeContractBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getEmployeeVOsByPositionId( final String positionId ) throws KANException;

   public abstract List< Object > getEmployeeNosByEmployeeNoList( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > employeeByLogon( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getEmployeeVOsByAbsEquEmpNo( final String employeeNo ) throws KANException;

   public abstract List< Object > getEmployeeVOsByTempParentPositionOwners( final String _tempParentPositionOwners ) throws KANException;

   public abstract List< Object > emailIsRegister( final Map< String, Object > parameters ) throws KANException;

   public abstract int transferEmployeeHROwner( Map< String, Object > parameterMap ) throws KANException;

}

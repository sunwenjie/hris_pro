package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;

public class EmployeeContractOTDaoImpl extends Context implements EmployeeContractOTDao
{

   @Override
   public int countEmployeeContractOTVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractOTVOsByCondition", employeeContractOTVO );
   }

   @Override
   public List< Object > getEmployeeContractOTVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return selectList( "getEmployeeContractOTVOsByCondition", employeeContractOTVO );
   }

   @Override
   public List< Object > getEmployeeContractOTVOsByCondition( final EmployeeContractOTVO employeeContractOTVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractOTVOsByCondition", employeeContractOTVO, rowBounds );
   }

   @Override
   public EmployeeContractOTVO getEmployeeContractOTVOByEmployeeOTId( final String employeeOTId ) throws KANException
   {
      return ( EmployeeContractOTVO ) select( "getEmployeeContractOTVOByEmployeeOTId", employeeOTId );
   }

   @Override
   public int insertEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return insert( "insertEmployeeContractOT", employeeContractOTVO );
   }

   @Override
   public int updateEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return update( "updateEmployeeContractOT", employeeContractOTVO );
   }

   @Override
   public int deleteEmployeeContractOT( final String employeeOTId  ) throws KANException
   {
      return delete( "deleteEmployeeContractOT", employeeOTId );
   }
   @Override
   public  List< Object > getEmployeeContractOTVOsByContractId( final String contractId ) throws KANException{
      return selectList( "getEmployeeContractOTVOsByContractId", contractId );
   }

}

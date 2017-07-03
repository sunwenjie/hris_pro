package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOtherTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;

public class EmployeeContractOtherTempDaoImpl extends Context implements EmployeeContractOtherTempDao
{

   @Override
   public int countEmployeeContractOtherTempVOsByCondition( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {

      return ( Integer ) ( insert( "countEmployeeContractOtherTempVOsByCondition", employeeContractOtherVO ) );
   }

   @Override
   public List< Object > getEmployeeContractOtherTempVOsByCondition( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return selectList( "getEmployeeContractOtherTempVOsByCondition", employeeContractOtherVO );
   }

   @Override
   public List< Object > getEmployeeContractOtherTempVOsByCondition( EmployeeContractOtherVO employeeContractOtherVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractOtherTempVOsByCondition", employeeContractOtherVO, rowBounds );
   }

   @Override
   public EmployeeContractOtherVO getEmployeeContractOtherTempVOByEmployeeOtherId( String employeeOtherId ) throws KANException
   {
      return ( EmployeeContractOtherVO ) select( "getEmployeeContractOtherTempVOByEmployeeOtherId", employeeOtherId );
   }

   @Override
   public int insertEmployeeContractOtherTemp( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return insert( "insertEmployeeContractOtherTemp", employeeContractOtherVO );
   }

   @Override
   public int updateEmployeeContractOtherTemp( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return update( "updateEmployeeContractOtherTemp", employeeContractOtherVO );
   }

   @Override
   public int deleteEmployeeContractOtherTemp( String employeeOtherId ) throws KANException
   {
      return delete( "deleteEmployeeContractOtherTemp", employeeOtherId );
   }

   @Override
   public List< Object > getEmployeeContractOtherTempVOsByContractId( String contractId ) throws KANException
   {
      return ( List< Object > ) selectList( "getEmployeeContractOtherTempVOsByContractId", contractId );
   }

}

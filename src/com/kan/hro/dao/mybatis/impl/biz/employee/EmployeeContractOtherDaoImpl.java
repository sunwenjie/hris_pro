package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOtherDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;

public class EmployeeContractOtherDaoImpl extends Context implements EmployeeContractOtherDao
{

   @Override
   public int countEmployeeContractOtherVOsByCondition( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {

      return ( Integer ) ( select( "countEmployeeContractOtherVOsByCondition", employeeContractOtherVO ) );
   }

   @Override
   public List< Object > getEmployeeContractOtherVOsByCondition( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return selectList( "getEmployeeContractOtherVOsByCondition", employeeContractOtherVO );
   }

   @Override
   public List< Object > getEmployeeContractOtherVOsByCondition( EmployeeContractOtherVO employeeContractOtherVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractOtherVOsByCondition", employeeContractOtherVO, rowBounds );
   }

   @Override
   public EmployeeContractOtherVO getEmployeeContractOtherVOByEmployeeOtherId( String employeeOtherId ) throws KANException
   {
      return ( EmployeeContractOtherVO ) select( "getEmployeeContractOtherVOByEmployeeOtherId", employeeOtherId );
   }

   @Override
   public int insertEmployeeContractOther( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return insert( "insertEmployeeContractOther", employeeContractOtherVO );
   }

   @Override
   public int updateEmployeeContractOther( EmployeeContractOtherVO employeeContractOtherVO ) throws KANException
   {
      return update( "updateEmployeeContractOther", employeeContractOtherVO );
   }

   @Override
   public int deleteEmployeeContractOther( String employeeOtherId ) throws KANException
   {
      return delete( "deleteEmployeeContractOther", employeeOtherId );
   }

   @Override
   public List< Object > getEmployeeContractOtherVOsByContractId( String contractId ) throws KANException
   {
      return ( List< Object > ) selectList( "getEmployeeContractOtherVOsByContractId", contractId );
   }

}

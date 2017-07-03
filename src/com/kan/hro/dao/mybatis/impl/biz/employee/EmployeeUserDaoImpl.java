package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeUserDao;
import com.kan.hro.domain.biz.employee.EmployeeBaseView;
import com.kan.hro.domain.biz.employee.EmployeeUserVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class EmployeeUserDaoImpl extends Context implements EmployeeUserDao
{

   @Override
   public EmployeeUserVO getEmployeeUserVOByCondition(final EmployeeUserVO employeeUserVO ) throws KANException
   {
      // TODO Auto-generated method stub
      List objectList = ( List ) selectList( "getEmployeeUserVOByCondition", employeeUserVO );
      
      if(objectList==null||objectList.size()!=1){
         return null;
      }
      
      return ( EmployeeUserVO ) objectList.get( 0 );
   }

   @Override
   public int updateEmployeeUser(final EmployeeUserVO employeeUserVO ) throws KANException
   {
      return update( "updateEmployeeUser", employeeUserVO );
   }
   
   @Override
   public boolean checkUsernameExist(final EmployeeUserVO employeeUserVO ) throws KANException
   {
      boolean returnVal = false;
      int count =   (Integer)select( "checkUsernameExist", employeeUserVO );
      
      if(count>0){
         returnVal = true;
      }
      
      return returnVal;
   }

   @Override
   public int insertEmployeeUser(final  EmployeeUserVO employeeUserVO ) throws KANException
   {
      return insert( "insertEmployeeUser", employeeUserVO );
   }

  
}

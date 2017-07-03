package com.kan.hro.service.impl.biz.employee;

import com.kan.base.core.ContextService;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeUserDao;
import com.kan.hro.domain.biz.employee.EmployeeUserVO;
import com.kan.hro.service.inf.biz.employee.EmployeeUserService;

public class EmployeeUserServiceImpl extends ContextService implements EmployeeUserService
{
   private  EmployeeUserDao employeeUserDao;
   
   @Override
   public EmployeeUserVO login( EmployeeUserVO employeeUserVO ) throws KANException
   {
      return employeeUserDao.getEmployeeUserVOByCondition( employeeUserVO );
   }

   @Override
   public int updateEmployeeUser( EmployeeUserVO employeeUserVO ) throws KANException
   {
      return employeeUserDao.updateEmployeeUser( employeeUserVO );
   }
   
   @Override
   public EmployeeUserVO getEmployeeUserById( String employeeUserId ) throws KANException
   {
      // TODO Auto-generated method stub
      EmployeeUserVO employeeUserVO = new EmployeeUserVO();
      employeeUserVO.setEmployeeUserId( employeeUserId );
      return employeeUserDao.getEmployeeUserVOByCondition( employeeUserVO );
   }
   
   @Override
   public EmployeeUserVO getEmployeeUserByEmployeeId( String employeeId ) throws KANException
   {
      // TODO Auto-generated method stub
      EmployeeUserVO employeeUserVO = new EmployeeUserVO();
      employeeUserVO.setEmployeeId( employeeId );
      return employeeUserDao.getEmployeeUserVOByCondition( employeeUserVO );
   }
   
   public boolean checkUsernameExist( final EmployeeUserVO employeeUserVO ) throws KANException{
      return employeeUserDao.checkUsernameExist( employeeUserVO );
      
   }
   
   public EmployeeUserDao getEmployeeUserDao()
   {
      return employeeUserDao;
   }

   public void setEmployeeUserDao( EmployeeUserDao employeeUserDao )
   {
      this.employeeUserDao = employeeUserDao;
   }


   
   

}

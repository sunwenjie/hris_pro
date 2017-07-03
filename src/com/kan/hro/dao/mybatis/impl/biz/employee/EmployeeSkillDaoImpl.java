package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeSkillDao;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;

public class EmployeeSkillDaoImpl extends Context implements EmployeeSkillDao
{

   @Override
   public int countEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeSkillVOsByCondition", employeeSkillVO );
   }

   @Override
   public List< Object > getEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {

      return selectList( "getEmployeeSkillVOsByCondition", employeeSkillVO );
   }

   @Override
   public List< Object > getEmployeeSkillVOsByCondition( final EmployeeSkillVO employeeSkillVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeSkillVOsByCondition", employeeSkillVO, rowBounds );
   }

   @Override
   public EmployeeSkillVO getEmployeeSkillVOByEmployeeSkillId( final String employeeSkillId ) throws KANException
   {

      return ( EmployeeSkillVO ) select( "getEmployeeSkillVOByEmployeeSkillId", employeeSkillId );
   }

   @Override
   public int insertEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {

      return insert( "insertEmployeeSkill", employeeSkillVO );
   }

   @Override
   public int updateEmployeeSkill( final EmployeeSkillVO employeeSkillVO ) throws KANException
   {

      return update( "updateEmployeeSkill", employeeSkillVO );
   }

   @Override
   public int deleteEmployeeSkill( final String employeeSkillId  ) throws KANException
   {
      return delete( "deleteEmployeeSkill", employeeSkillId );
   }
   @Override
   public  List< Object > getEmployeeSkillVOsByEmployeeId( final String employeeId ) throws KANException{
      return selectList( "getEmployeeSkillVOsByEmployeeId", employeeId );
   }

}

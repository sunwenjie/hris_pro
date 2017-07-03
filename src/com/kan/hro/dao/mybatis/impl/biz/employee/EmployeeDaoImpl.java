package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeBaseView;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class EmployeeDaoImpl extends Context implements EmployeeDao
{

   @Override
   public int countEmployeeVOsByCondition( final EmployeeVO employeeVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeVOsByCondition", employeeVO );
   }

   @Override
   public List< Object > getEmployeeVOsByCondition( final EmployeeVO employeeVO ) throws KANException
   {
      return selectList( "getEmployeeVOsByCondition", employeeVO );
   }

   @Override
   public List< Object > getEmployeeVOsByCondition( final EmployeeVO employeeVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeVOsByCondition", employeeVO, rowBounds );
   }

   @Override
   public EmployeeVO getEmployeeVOByEmployeeId( final String employeeId ) throws KANException
   {
      return ( EmployeeVO ) select( "getEmployeeVOByEmployeeId", employeeId );
   }

   @Override
   public int updateEmployee( final EmployeeVO employeeVO ) throws KANException
   {
      return update( "updateEmployee", employeeVO );
   }

   @Override
   public int insertEmployee( final EmployeeVO employeeVO ) throws KANException
   {
      return insert( "insertEmployee", employeeVO );
   }

   @Override
   public int deleteEmployee( final String employeeId ) throws KANException
   {
      return delete( "deleteEmployee", employeeId );
   }

   @Override
   public List< Object > getEmployeeBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getEmployeeBaseViews", accountId );
   }

   @Override
   public List< Object > getEmployeeBaseViewsByCondition( final EmployeeBaseView employeeBaseView ) throws KANException
   {
      if ( employeeBaseView.getConditions().equals( "name" ) )
      {
         return selectList( "getEmployeeBaseViewsByName", employeeBaseView );
      }
      else if ( employeeBaseView.getConditions().equals( "employeeNo" ) )
      {
         return selectList( "getEmployeeBaseViewsByEmployeeNo", employeeBaseView );
      }
      else
      {
         return selectList( "getEmployeeBaseViewsByNumber", employeeBaseView );
      }
   }

   @Override
   public List< Object > getEmployeeContractBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getEmployeeContractBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getEmployeeVOsByPositionId( final String positionId ) throws KANException
   {
      return selectList( "getEmployeeVOsByPositionId", positionId );
   }

   @Override
   public List< Object > getEmployeeNosByEmployeeNoList( final EmployeeVO employeeVO ) throws KANException
   {
      return selectList( "getEmployeeNosByEmployeeNoList", employeeVO );
   }

   @Override
   public List< Object > employeeByLogon( final EmployeeVO employeeVO ) throws KANException
   {
      return selectList( "employeeByLogon", employeeVO );
   }

   @Override
   public List< Object > getEmployeeVOsByAbsEquEmpNo( final String employeeNo ) throws KANException
   {
      String _employeeNo = employeeNo;
      if ( KANUtil.filterEmpty( _employeeNo ) != null )
      {
         _employeeNo = _employeeNo.replaceAll( "¡¡", "" ).trim();
      }
      return selectList( "getEmployeeVOsByAbsEquEmpNo", _employeeNo );
   }

   @Override
   public List< Object > getEmployeeVOsByTempParentPositionOwners( final String _tempParentPositionOwners ) throws KANException
   {
      return selectList( "getEmployeeVOsByTempParentPositionOwners", _tempParentPositionOwners );
   }

   @Override
   public List< Object > emailIsRegister( final Map< String, Object > parameters ) throws KANException
   {
      return selectList( "emailIsRegister", parameters );
   }

   @Override
   public int transferEmployeeHROwner( Map< String, Object > parameterMap ) throws KANException
   {
      return update( "transferEmployeeHROwner", parameterMap );
   }

}

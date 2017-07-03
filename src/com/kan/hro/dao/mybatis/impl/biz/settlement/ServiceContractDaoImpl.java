package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;

public class ServiceContractDaoImpl extends Context implements ServiceContractDao
{
   private EmployeeDao employeeDao;

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   @Override
   public int countServiceContractVOsByCondition( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return ( Integer ) select( "countServiceContractVOsByCondition", serviceContractVO );
   }

   @Override
   public List< Object > getServiceContractVOsByCondition( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return selectList( "getServiceContractVOsByCondition", serviceContractVO );
   }

   @Override
   public List< Object > getServiceContractVOsByCondition( final ServiceContractVO serviceContractVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getServiceContractVOsByCondition", serviceContractVO, rowBounds );
   }

   @Override
   public ServiceContractVO getServiceContractVOByContractId( final String contractId ) throws KANException
   {
      return ( ServiceContractVO ) select( "getServiceContractVOByContractId", contractId );
   }

   @Override
   public int updateServiceContract( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return update( "updateServiceContract", serviceContractVO );
   }

   @Override
   public int insertServiceContract( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return insert( "insertServiceContract", serviceContractVO );
   }

   @Override
   public int deleteServiceContract( final String contractId ) throws KANException
   {
      return delete( "deleteServiceContract", contractId );
   }

   @Override
   public List< Object > getServiceContractVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return selectList( "getServiceContractVOsByOrderHeaderId", orderHeaderId );
   }

   @Override
   public ServiceContractVO getServiceContractVOByContractTempId( final String contractTempId ) throws KANException
   {
      return ( ServiceContractVO ) select( "getServiceContractVOByContractTempId", contractTempId );
   }

   @Override
   public List< Object > getPaymentServiceContractVOsByCondition( ServiceContractVO serviceContractVO ) throws KANException
   {
      return selectList( "getPaymentServiceContractVOsByCondition", serviceContractVO );
   }

}

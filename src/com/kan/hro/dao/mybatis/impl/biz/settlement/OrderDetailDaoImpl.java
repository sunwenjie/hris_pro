package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;

public class OrderDetailDaoImpl extends Context implements OrderDetailDao
{
   private EmployeeDao employeeDao;

   private ServiceContractDao serviceContractDao;

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final ServiceContractDao getServiceContractDao()
   {
      return serviceContractDao;
   }

   public final void setServiceContractDao( ServiceContractDao serviceContractDao )
   {
      this.serviceContractDao = serviceContractDao;
   }

   @Override
   public int countOrderDetailVOsByCondition( final OrderDetailVO orderDetailVO ) throws KANException
   {
      return ( Integer ) select( "countOrderDetailVOsByCondition", orderDetailVO );
   }

   @Override
   public List< Object > getOrderDetailVOsByCondition( final OrderDetailVO orderDetailVO ) throws KANException
   {
      return selectList( "getOrderDetailVOsByCondition", orderDetailVO );
   }

   @Override
   public List< Object > getOrderDetailVOsByCondition( final OrderDetailVO orderDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOrderDetailVOsByCondition", orderDetailVO, rowBounds );
   }

   @Override
   public OrderDetailVO getOrderDetailVOByOrderDetailId( final String orderDetailId ) throws KANException
   {
      return ( OrderDetailVO ) select( "getOrderDetailVOByOrderDetailId", orderDetailId );
   }

   @Override
   public int updateOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException
   {
      return update( "updateOrderDetail", orderDetailVO );
   }

   @Override
   public int insertOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException
   {
      return insert( "insertOrderDetail", orderDetailVO );
   }

   @Override
   public int deleteOrderDetail( final String orderDetailId ) throws KANException
   {
      return delete( "deleteOrderDetail", orderDetailId );
   }

   @Override
   public List< Object > getOrderDetailVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getOrderDetailVOsByContractId", contractId );
   }

}

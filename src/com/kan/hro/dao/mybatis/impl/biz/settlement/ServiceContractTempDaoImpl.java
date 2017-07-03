package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractTempDao;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;

public class ServiceContractTempDaoImpl extends Context implements ServiceContractTempDao
{

   @Override
   public int countServiceContractTempVOsByCondition( final ServiceContractTempVO serviceContractTempVO ) throws KANException
   {
      return ( Integer ) select( "countServiceContractTempVOsByCondition", serviceContractTempVO );
   }

   @Override
   public List< Object > getServiceContractTempVOsByCondition( final ServiceContractTempVO serviceContractTempVO ) throws KANException
   {
      return selectList( "getServiceContractTempVOsByCondition", serviceContractTempVO );
   }

   @Override
   public List< Object > getServiceContractTempVOsByCondition( final ServiceContractTempVO serviceContractTempVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getServiceContractTempVOsByCondition", serviceContractTempVO, rowBounds );
   }

   @Override
   public ServiceContractTempVO getServiceContractTempVOByContractId( final String contractId ) throws KANException
   {
      return ( ServiceContractTempVO ) select( "getServiceContractTempVOByContractId", contractId );
   }

   @Override
   public int updateServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException
   {
      return update( "updateServiceContractTemp", serviceContractTempVO );
   }

   @Override
   public int insertServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException
   {
      return insert( "insertServiceContractTemp", serviceContractTempVO );
   }

   @Override
   public int deleteServiceContractTemp( final String contractId ) throws KANException
   {
      return delete( "deleteServiceContractTemp", contractId );
   }

   @Override
   public List< Object > getServiceContractTempVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return selectList( "getServiceContractTempVOsByOrderHeaderId", orderHeaderId );
   }
   
   @Override
   public List< Object > getServiceContractTempVOsForEmployee( final ServiceContractTempVO serviceContractTempVO ) throws KANException
   {
      return selectList( "getServiceContractTempVOsForEmployee", serviceContractTempVO );
   }

}

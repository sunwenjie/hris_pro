package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;

public interface ServiceContractTempDao
{
   public abstract int countServiceContractTempVOsByCondition(final ServiceContractTempVO serviceContractTempVO) throws KANException ; 
   
   public abstract List< Object > getServiceContractTempVOsByCondition( final ServiceContractTempVO serviceContractTempVO ) throws KANException;

   public abstract List< Object > getServiceContractTempVOsByCondition( final ServiceContractTempVO serviceContractTempVO, RowBounds rowBounds ) throws KANException;

   public abstract ServiceContractTempVO getServiceContractTempVOByContractId( final String contractId ) throws KANException;
   
   public abstract int insertServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException;

   public abstract int updateServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException;

   public abstract int deleteServiceContractTemp( final String contractId ) throws KANException;
   
   public abstract List< Object > getServiceContractTempVOsByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract List< Object > getServiceContractTempVOsForEmployee( ServiceContractTempVO serviceContractTempVO ) throws KANException;
}

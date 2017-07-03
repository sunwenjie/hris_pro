package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;

public interface ServiceContractDao
{
   public abstract int countServiceContractVOsByCondition(final ServiceContractVO serviceContractVO) throws KANException ; 
   
   public abstract List< Object > getServiceContractVOsByCondition( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract List< Object > getServiceContractVOsByCondition( final ServiceContractVO serviceContractVO, RowBounds rowBounds ) throws KANException;

   public abstract ServiceContractVO getServiceContractVOByContractId( final String contractTempId ) throws KANException;
   
   public abstract ServiceContractVO getServiceContractVOByContractTempId( final String contractTempId ) throws KANException;
   
   public abstract List< Object > getServiceContractVOsByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract int insertServiceContract( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract int updateServiceContract( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract int deleteServiceContract( final String contractId ) throws KANException;
   
   public abstract List< Object > getPaymentServiceContractVOsByCondition( final ServiceContractVO serviceContractVO ) throws KANException;
}

package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;

public interface ClientOrderSBDao
{
   public abstract int countClientOrderSBVOsByCondition( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract List< Object > getClientOrderSBVOsByCondition( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract List< Object > getClientOrderSBVOsByCondition( final ClientOrderSBVO clientOrderSBVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderSBVO getClientOrderSBVOByClientOrderSBId( final String clientOrderSBId ) throws KANException;

   public abstract int updateClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract int insertClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract int deleteClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract List< Object > getClientOrderSBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

   public abstract List< Object > getClientOrderSBVOsByEmployeeContractId( final String employeeContractId ) throws KANException;
}

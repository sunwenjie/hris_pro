package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;

public interface ClientOrderCBDao
{
   public abstract int countClientOrderCBVOsByCondition( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract List< Object > getClientOrderCBVOsByCondition( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract List< Object > getClientOrderCBVOsByCondition( final ClientOrderCBVO clientOrderCBVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderCBVO getClientOrderCBVOByClientOrderCBId( final String clientOrderCBId ) throws KANException;

   public abstract int updateClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract int insertClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract int deleteClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract List< Object > getClientOrderCBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

   public abstract List< Object > getClientOrderCBVOsByEmployeeContractId( final String employeeContractId ) throws KANException;
}

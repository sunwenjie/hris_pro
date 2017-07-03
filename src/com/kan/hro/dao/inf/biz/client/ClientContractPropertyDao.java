package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;

public interface ClientContractPropertyDao
{
   public abstract int countClientContractPropertyVOsByCondition( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException;

   public abstract List< Object > getClientContractPropertyVOsByCondition( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException;

   public abstract List< Object > getClientContractPropertyVOsByCondition( final ClientContractPropertyVO clientContractPropertyVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientContractPropertyVO getClientContractPropertyVOByClientContractPropertyId( final String clientContractPropertyId ) throws KANException;

   public abstract int updateClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException;

   public abstract int insertClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException;

   public abstract int deleteClientContractProperty( final String clientContractPropertyId ) throws KANException;

   public abstract List< Object > getClientContractPropertyVOsByContractId( final String contractId ) throws KANException;
}

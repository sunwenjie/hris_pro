package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContractVO;

public interface ClientContractDao
{
   public abstract int countClientContractVOsByCondition( final ClientContractVO clientContractVO ) throws KANException;

   public abstract List< Object > getClientContractVOsByCondition( final ClientContractVO clientContractVO ) throws KANException;

   public abstract List< Object > getClientContractVOsByCondition( final ClientContractVO clientContractVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientContractVO getClientContractVOByContractId( final String contractId ) throws KANException;

   public abstract int updateClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract int insertClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract int deleteClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract List< Object > getClientContractBaseViews( final String accountId ) throws KANException;

   public abstract List< Object > getClientContractVOsByClientId( final String clientId ) throws KANException;
   
   public abstract int getArchiveClientContractCount( final String clientId );
}

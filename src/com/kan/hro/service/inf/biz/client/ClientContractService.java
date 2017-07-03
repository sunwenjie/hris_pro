package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.domain.system.ConstantVO;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.service.inf.base.biz.client.BaseClientContractService;

public interface ClientContractService extends BaseClientContractService
{
   public abstract int updateClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract int updateClientContract( final ClientContractVO clientContractVO, final List< ConstantVO > constantsVOs ) throws KANException;

   public abstract int submitClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract int insertClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract int deleteClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract List< Object > getClientContractBaseViews( final String accountId ) throws KANException;

   public abstract List< Object > getClientContractVOsByClientId( final String clientId ) throws KANException;

   public abstract int chopClientContract( final ClientContractVO clientContractVO ) throws KANException;

   public abstract int getArchiveClientContractCount( final String clientId );
}

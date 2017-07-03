package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.service.inf.base.biz.client.BaseClientContactService;

public interface ClientContactService extends BaseClientContactService
{
   public abstract int updateClientContact( final ClientContactVO clientContactVO ) throws KANException;

   public abstract int insertClientContact( final ClientContactVO clientContactVO ) throws KANException;

   public abstract int deleteClientContact( final ClientContactVO clientContactVO ) throws KANException;

   public abstract List< Object > getClientContactVOsByCondition( final ClientContactVO clientContactVO ) throws KANException;

   public abstract List< Object > getClientContactVOsByClientId( final String clientId ) throws KANException;
}

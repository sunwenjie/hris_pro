package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.service.inf.base.biz.client.BaseClientUserService;

public interface ClientUserService extends BaseClientUserService
{
   public abstract int insertClientUser( final ClientUserVO clientContactVO ) throws KANException;

   public abstract int updateClientUser( final ClientUserVO clientContactVO ) throws KANException;

   public abstract int deleteClientUser( final ClientUserVO clientContactVO ) throws KANException;
   
   public abstract List<Object> getClientUserVOByCondition( final ClientUserVO clientContactVO ) throws KANException;
   
   public abstract boolean isExistByCondition(final ClientUserVO clientContactVO )throws KANException;

   public abstract ClientUserVO getClientUserByName( final ClientUserVO clientUserVO );
}

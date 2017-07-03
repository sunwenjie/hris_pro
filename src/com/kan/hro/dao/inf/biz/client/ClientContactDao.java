package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContactVO;

public interface ClientContactDao
{
   public abstract int countClientContactVOsByCondition( final ClientContactVO clientContactVO ) throws KANException;

   public abstract List< Object > getClientContactVOsByCondition( final ClientContactVO clientContactVO ) throws KANException;

   public abstract List< Object > getClientContactVOsByCondition( final ClientContactVO clientContactVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientContactVO getClientContactVOByClientContactId( final String clientContactId ) throws KANException;

   public abstract int updateClientContact( final ClientContactVO clientContactVO ) throws KANException;

   public abstract int insertClientContact( final ClientContactVO clientContactVO ) throws KANException;

   public abstract int deleteClientContact( final ClientContactVO clientContactVO ) throws KANException;

   public abstract List< Object > getClientContactVOsByClientId( final String clientId ) throws KANException;
}

package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientUserVO;

public interface ClientUserDao
{
   public abstract int countClientUserVOsByCondition( final ClientUserVO clientContactVO ) throws KANException;

   public abstract List< Object > getClientUserVOsByCondition( final ClientUserVO clientContactVO ) throws KANException;

   public abstract List< Object > getClientUserVOsByCondition( final ClientUserVO clientContactVO, final RowBounds rowBounds ) throws KANException;

   public abstract ClientUserVO getClientUserVOByUserId( final String userId ) throws KANException;

   public abstract int insertClientUser( final ClientUserVO clientContactVO ) throws KANException;

   public abstract int updateClientUser( final ClientUserVO clientContactVO ) throws KANException;

   public abstract int deleteClientUser( final ClientUserVO clientContactVO ) throws KANException;
   
   public abstract ClientUserVO getClientUserByName( ClientUserVO clientUserVO );
}

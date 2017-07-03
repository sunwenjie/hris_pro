package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientGroupVO;

public interface ClientGroupDao
{
   public abstract int countClientGroupVOsByCondition( final ClientGroupVO clientGroupVO ) throws KANException;

   public abstract List< Object > getClientGroupVOsByCondition( final ClientGroupVO clientGroupVO ) throws KANException;

   public abstract List< Object > getClientGroupVOsByCondition( final ClientGroupVO clientGroupVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientGroupVO getClientGroupVOByClientGroupId( final String ClientGroupId ) throws KANException;

   public abstract int updateClientGroup( final ClientGroupVO clientGroupVO ) throws KANException;

   public abstract int insertClientGroup( final ClientGroupVO clientGroupVO ) throws KANException;

   public abstract int deleteClientGroup( final ClientGroupVO clientGroupVO ) throws KANException;
   
   public abstract List< Object > getClientGroupBaseViews ( final String accountId ) throws KANException;

   public abstract Object getClientBaseViewByClientGroupVO( final ClientGroupVO clientGroupVO ) throws KANException;

}

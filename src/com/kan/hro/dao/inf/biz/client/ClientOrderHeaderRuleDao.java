package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderHeaderRuleVO;

public interface ClientOrderHeaderRuleDao
{
   public abstract int countClientOrderHeaderRuleVOsByCondition( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderRuleVOsByCondition( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderRuleVOsByCondition( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderHeaderRuleVO getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( final String clientOrderHeaderRuleId ) throws KANException;

   public abstract int updateClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract int insertClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract int deleteClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderRuleVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

}

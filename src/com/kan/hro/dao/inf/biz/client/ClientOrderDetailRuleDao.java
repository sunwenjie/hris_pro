package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;

public interface ClientOrderDetailRuleDao
{
   public abstract int countClientOrderDetailRuleVOsByCondition( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailRuleVOsByCondition( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailRuleVOsByCondition( final ClientOrderDetailRuleVO clientOrderDetailRuleVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderDetailRuleVO getClientOrderDetailRuleVOByClientOrderDetailRuleId( final String clientOrderDetailRuleId ) throws KANException;

   public abstract int updateClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract int insertClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract int deleteClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailRuleVOsByClientOrderDetailId( final String clientOrderDetailId ) throws KANException;

}

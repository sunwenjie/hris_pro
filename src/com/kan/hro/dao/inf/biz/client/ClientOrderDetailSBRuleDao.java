package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;

public interface ClientOrderDetailSBRuleDao
{
   public abstract int countClientOrderDetailSBRuleVOsByCondition( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailSBRuleVOsByCondition( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailSBRuleVOsByCondition( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderDetailSBRuleVO getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( final String clientOrderDetailSBRuleId ) throws KANException;

   public abstract int updateClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract int insertClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract int deleteClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailSBRuleVOsByClientOrderDetailId( final String clientOrderDetailId ) throws KANException;

}

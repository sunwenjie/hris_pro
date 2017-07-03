package com.kan.hro.service.inf.cp.biz.client;

import java.util.List;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.service.inf.base.biz.client.BaseClientUserService;

public interface CPClientUserService extends BaseClientUserService
{
   public abstract List<Object> getClientUserVOByCondition( final ClientUserVO clientContactVO ) throws KANException;
   
   public abstract boolean isExistByCondition(final ClientUserVO clientContactVO )throws KANException;
}

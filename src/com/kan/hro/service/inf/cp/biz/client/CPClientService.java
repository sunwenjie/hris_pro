package com.kan.hro.service.inf.cp.biz.client;

import java.util.List;

import com.kan.base.util.KANException;
import com.kan.hro.service.inf.base.biz.client.BaseClientService;

public interface CPClientService extends BaseClientService
{
   public abstract List< Object > getClientFullViews( final String accountId ) throws KANException;
}

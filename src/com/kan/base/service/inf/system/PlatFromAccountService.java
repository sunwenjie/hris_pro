package com.kan.base.service.inf.system;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.util.KANException;

public interface PlatFromAccountService
{
   public abstract boolean initAccount( final AccountVO accountVO ) throws KANException;
}

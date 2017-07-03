package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.SMSConfigVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SMSConfigService
{
   public abstract PagedListHolder getSMSConfigVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SMSConfigVO getSMSConfigVOByConfigId( final String configId ) throws KANException;

   public abstract int updateSMSConfig( final SMSConfigVO configVO ) throws KANException;

   public abstract int insertSMSConfig( final SMSConfigVO configVO ) throws KANException;

   public abstract void deleteSMSConfig( final SMSConfigVO configVO ) throws KANException;
   
   public abstract List< Object > getSMSConfigVOs() throws KANException;

}

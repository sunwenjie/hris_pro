package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface LogService
{

   public abstract int insertLog( final LogVO logVO ) throws KANException;

   public abstract PagedListHolder getLogVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract LogVO getLogVOById( final String id ) throws KANException;

   public abstract List< Object > getLogModules() throws KANException;

   public abstract LogVO getPreLog( final LogVO logVO ) throws KANException;
}

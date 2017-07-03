package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ManagerHeaderService
{
   public abstract PagedListHolder getManagerHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ManagerHeaderVO getManagerHeaderVOByManagerHeaderId( final String managerHeaderId ) throws KANException;

   public abstract int insertManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException;

   public abstract int updateManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException;

   public abstract int deleteManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException;

   public abstract List< ManagerDTO > getManagerDTOsByAccountId( final String accountId ) throws KANException;

}

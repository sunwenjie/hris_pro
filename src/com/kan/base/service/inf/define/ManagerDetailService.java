package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ManagerDetailService
{
   public abstract PagedListHolder getManagerDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ManagerDetailVO getManagerDetailVOByManagerDetailId( final String managerDetailId ) throws KANException;

   public abstract int insertManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException;

   public abstract int updateManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException;

   public abstract int deleteManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException;

   public abstract List< Object > getManagerDetailVOsByManagerHeaderId( final String managerHeaderId ) throws KANException;
}

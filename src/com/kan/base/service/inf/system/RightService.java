package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.RightVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface RightService
{

   public abstract PagedListHolder getRightVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract RightVO getRightVOByRightId( final String rightId ) throws KANException;

   public abstract int updateRight( final RightVO rightVO ) throws KANException;

   public abstract int insertRight( final RightVO rightVO ) throws KANException;

   public abstract void deleteRight( final RightVO rightVO ) throws KANException;

   public abstract List< Object > getRightVOs() throws KANException;

}

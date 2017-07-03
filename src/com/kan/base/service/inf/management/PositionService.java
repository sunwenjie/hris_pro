package com.kan.base.service.inf.management;
import java.util.List;

import com.kan.base.domain.management.PositionDTO;
import com.kan.base.domain.management.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PositionService
{
   public abstract PagedListHolder getPositionVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PositionVO getPositionVOByPositionId( final String positionId ) throws KANException;

   public abstract int updatePosition( final PositionVO positionVO ) throws KANException;

   public abstract int insertPosition( final PositionVO positionVO ) throws KANException;

   public abstract int deletePosition( final PositionVO positionVO ) throws KANException;

   public abstract List< PositionDTO > getPositionDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List<Object> getPositionBaseViewsByAccountId( final String accountId ) throws KANException;
}

package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PositionGradeService
{
   public abstract PagedListHolder getPositionGradeVOsByCondition( final PagedListHolder pagedListGradeHolder, final boolean isPaged ) throws KANException;

   public abstract PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGardeId ) throws KANException;

   public abstract int insertPositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract void deletePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract List< Object > getPositionGradeVOsByAccountId( final String accountId ) throws KANException;
   
}

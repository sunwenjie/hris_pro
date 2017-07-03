package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PositionGradeService
{
   public abstract PagedListHolder getPositionGradeVOByCondition( final PagedListHolder pagedListGradeHolder, final boolean isPaged ) throws KANException;

   public abstract PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGardeId ) throws KANException;

   public abstract PositionGradeVO getPositionGradeVOByGradeName( final String gradeName ) throws KANException;

   public abstract int insertPositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract void deletePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;
   
   public abstract List<Object> getPositionGradeVOsByAccountId(final String accountId) throws KANException;
}

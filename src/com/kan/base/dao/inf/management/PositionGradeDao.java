package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.util.KANException;

public interface PositionGradeDao
{
   public abstract int countPositionGradeVOByCondition( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract List< Object > getPositionGradeVOByCondition( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract List< Object > getPositionGradeVOByCondition( final PositionGradeVO positionGradeVO, final RowBounds rowBounds ) throws KANException;

   public abstract PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGardeId ) throws KANException;

   public abstract PositionGradeVO getPositionGradeVOByGradeName( final String gradeName ) throws KANException;

   public abstract int insertPositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract int deletePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;
   
   public abstract List< Object > getPositionGradeVOsByAccountId( final String accountId ) throws KANException;
}

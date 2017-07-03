package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.util.KANException;

public interface PositionGradeDao
{
   public abstract int countPositionGradeVOsByCondition( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract List< Object > getPositionGradeVOsByCondition( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract List< Object > getPositionGradeVOsByCondition( final PositionGradeVO positionGradeVO, final RowBounds rowBounds ) throws KANException;

   public abstract PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGardeId ) throws KANException;

   public abstract int insertPositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract int deletePositionGrade( final String positionGardeId ) throws KANException;

   public abstract int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException;

   public abstract List< Object > getPositionGradeVOsByAccountId( final String accountId ) throws KANException;
}

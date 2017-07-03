package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionColumnRightRelationVO;
import com.kan.base.util.KANException;

public interface PositionColumnRightRelationDao
{
   public abstract int countPositionColumnRightRelationVOsByCondition( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionColumnRightRelationVOsByCondition( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionColumnRightRelationVOsByCondition( final PositionColumnRightRelationVO positionColumnRightRelationVO, RowBounds rowBounds )
         throws KANException;

   public abstract PositionColumnRightRelationVO getPositionColumnRightRelationVOByPositionColumnRightRelationId( final String positionStaffRelationId ) throws KANException;

   public abstract int insertPositionColumnRightRelation( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException;

   public abstract int updatePositionColumnRightRelation( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException;

   public abstract int deletePositionColumnRightRelationByCondition( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionColumnRightRelationVOsByPositionId( final String positionId ) throws KANException;

}

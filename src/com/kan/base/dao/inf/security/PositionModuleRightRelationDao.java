package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionModuleRightRelationVO;
import com.kan.base.util.KANException;

public interface PositionModuleRightRelationDao
{
   public abstract int countPositionModuleRightRelationVOsByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionModuleRightRelationVOsByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionModuleRightRelationVOsByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO, RowBounds rowBounds )
         throws KANException;

   public abstract PositionModuleRightRelationVO getPositionModuleRightRelationVOByPositionModuleRightRelationId( final String positionModuleRightRelationId ) throws KANException;

   public abstract int insertPositionModuleRightRelation( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException;

   public abstract int updatePositionModuleRightRelation( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException;

   public abstract int deletePositionModuleRightRelationByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionModuleRightRelationVOsByPositionId( final String positionId ) throws KANException;

}

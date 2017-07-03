package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.GroupColumnRightRelationVO;
import com.kan.base.util.KANException;

public interface GroupColumnRightRelationDao
{
   public abstract int countPositionGroupColumnRightRelationVOsByCondition(final GroupColumnRightRelationVO positionGroupColumnRightRelationVO) throws KANException ; 
   
   public abstract List< Object > getPositionGroupColumnRightRelationVOsByCondition( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionGroupColumnRightRelationVOsByCondition( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO, RowBounds rowBounds ) throws KANException;

   public abstract GroupColumnRightRelationVO getPositionGroupColumnRightRelationVOByPositionGroupColumnRightRelationId( final String positionGroupStaffRelationId ) throws KANException;

   public abstract int insertPositionGroupColumnRightRelation( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException;

   public abstract int updatePositionGroupColumnRightRelation( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException;

   public abstract int deletePositionGroupColumnRightRelationByCondition( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionGroupColumnRightRelationVOsByGroupId( final String groupId ) throws KANException;
   
}

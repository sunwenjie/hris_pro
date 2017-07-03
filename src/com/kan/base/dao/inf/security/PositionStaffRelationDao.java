package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.util.KANException;

public interface PositionStaffRelationDao
{
   public abstract int countPositionStaffRelationVOsByCondition(final PositionStaffRelationVO positionStaffRelationVO) throws KANException ; 
   
   public abstract List< Object > getPositionStaffRelationVOsByCondition( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException;

   public abstract List< Object > getPositionStaffRelationVOsByCondition( final PositionStaffRelationVO positionStaffRelationVO, RowBounds rowBounds ) throws KANException;

   public abstract PositionStaffRelationVO getPositionStaffRelationVOByPositionStaffRelationId( final String positionStaffRelationId ) throws KANException;

   public abstract int insertPositionStaffRelation( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException;

   public abstract int updatePositionStaffRelation( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException;

   public abstract int deletePositionStaffRelationByStaffId( final String staffId ) throws KANException;
   
   public abstract int deletePositionStaffRelationByPositionId( final String positionId ) throws KANException;
   
   public abstract List< Object > getPositionStaffRelationVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getPositionStaffRelationVOsByStaffId( final String staffId ) throws KANException;
   
   public abstract List< Object > getPositionStaffRelationVOsByPositionId( final String positionId ) throws KANException;

   public abstract void deletePositionStaffRelationByStaffIdAndPostionId( final PositionStaffRelationVO positionStaffRelationVO )throws KANException;
   
   public abstract PositionStaffRelationVO getPositionStaffRelationVOByStaffAndPositionId( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException;
   
}

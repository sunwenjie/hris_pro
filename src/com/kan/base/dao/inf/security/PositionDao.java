package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.KANException;

public interface PositionDao
{
   public abstract int countPositionVOsByCondition( final PositionVO positionVO ) throws KANException;

   public abstract List< Object > getPositionVOsByCondition( final PositionVO positionVO ) throws KANException;

   public abstract List< Object > getPositionVOsByCondition( final PositionVO positionVO, RowBounds rowBounds ) throws KANException;

   public abstract PositionVO getPositionVOByPositionId( final String positionId ) throws KANException;

   public abstract int insertPosition( final PositionVO positionVO ) throws KANException;

   public abstract int updatePosition( final PositionVO positionVO ) throws KANException;

   public abstract int deletePosition( final PositionVO positionVO ) throws KANException;

   public abstract List< Object > getPositionVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getPositionBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getPositionVOsByParentPositionId( final PositionVO positionVO ) throws KANException;

   public abstract List< Object > getPositionVOByPositionVO( final PositionVO positionVO ) throws KANException;

   public abstract List< Object > getPositionVOsByStaffIds( final PositionVO positionVO ) throws KANException;
   
   public abstract List< Object > getPositionVOsByEmployeeId( final StaffVO  staffVO) throws KANException;
   
   public abstract int getPositionVOBalancesByPositionVO( final PositionVO positionVO) throws KANException;

}

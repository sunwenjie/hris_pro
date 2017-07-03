package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.RightVO;
import com.kan.base.util.KANException;

public interface RightDao
{

   public abstract int countRightVOsByCondition( final RightVO rightVO ) throws KANException;

   public abstract List< Object > getRightVOsByCondition( final RightVO rightVO ) throws KANException;

   public abstract List< Object > getRightVOsByCondition( final RightVO rightVO, RowBounds rowBounds ) throws KANException;

   public abstract RightVO getRightVOByRightId( final String rightId ) throws KANException;

   public abstract int updateRight( final RightVO rightVO ) throws KANException;

   public abstract int insertRight( final RightVO rightVO ) throws KANException;

   public abstract int deleteRight( final RightVO rightVO ) throws KANException;
   
   public abstract List< Object > getRightVOs() throws KANException;

}
package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.util.KANException;

public interface ManagerDetailDao
{
   public abstract int countManagerDetailVOsByCondition( final ManagerDetailVO managerDetailVO ) throws KANException;

   public abstract List< Object > getManagerDetailVOsByCondition( final ManagerDetailVO managerDetailVO ) throws KANException;

   public abstract List< Object > getManagerDetailVOsByCondition( final ManagerDetailVO managerDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract ManagerDetailVO getManagerDetailVOByManagerDetailId( final String managerDetailId ) throws KANException;

   public abstract int insertManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException;

   public abstract int updateManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException;

   public abstract int deleteManagerDetail( final String managerDetailId ) throws KANException;

   public abstract List< Object > getManagerDetailVOsByManagerHeaderId( final String managerDetailId ) throws KANException;
}
